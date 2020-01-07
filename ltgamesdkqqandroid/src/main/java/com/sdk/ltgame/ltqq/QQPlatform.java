package com.sdk.ltgame.ltqq;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.gentop.ltgame.ltgamesdkcore.common.LTGameOptions;
import com.gentop.ltgame.ltgamesdkcore.common.LTGameSdk;
import com.gentop.ltgame.ltgamesdkcore.common.Target;
import com.gentop.ltgame.ltgamesdkcore.impl.OnLoginStateListener;
import com.gentop.ltgame.ltgamesdkcore.impl.OnRechargeListener;
import com.gentop.ltgame.ltgamesdkcore.model.LoginObject;
import com.gentop.ltgame.ltgamesdkcore.model.RechargeObject;
import com.gentop.ltgame.ltgamesdkcore.platform.AbsPlatform;
import com.gentop.ltgame.ltgamesdkcore.platform.IPlatform;
import com.gentop.ltgame.ltgamesdkcore.platform.PlatformFactory;
import com.gentop.ltgame.ltgamesdkcore.uikit.BaseActionActivity;
import com.gentop.ltgame.ltgamesdkcore.util.LTGameUtil;
import com.sdk.ltgame.ltqq.uikit.QQActionActivity;


public class QQPlatform extends AbsPlatform {

    private QQHelper mHelper;

    private QQPlatform(Context context, String appId, String appKey, int target) {
        super(context, appId, appKey, target);
    }

    @Override
    public void recharge(Activity activity, int target, RechargeObject object, OnRechargeListener listener) {

    }

    @Override
    public void login(Activity activity, int target, LoginObject object, OnLoginStateListener listener) {
        mHelper = new QQHelper(activity, object.getQqAppID(), object.getmAdID(), object.isLoginOut(), target, listener);
        mHelper.loginAction();
    }

    @Override
    public Class getUIKitClazz() {
        return QQActionActivity.class;
    }

    @Override
    public void onActivityResult(BaseActionActivity activity, int requestCode, int resultCode, Intent data) {
        mHelper.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 工厂类
     */
    public static class Factory implements PlatformFactory {

        @Override
        public IPlatform create(Context context, int target) {
            IPlatform platform = null;
            LTGameOptions options = LTGameSdk.options();
            if (!LTGameUtil.isAnyEmpty(options.getLtAppId(), options.getLtAppKey())) {
                platform = new QQPlatform(context, options.getLtAppId(), options.getLtAppKey(),
                        target);
            }
            return platform;
        }

        @Override
        public int getPlatformTarget() {
            return Target.PLATFORM_QQ;
        }

        @Override
        public boolean checkLoginPlatformTarget(int target) {
            return target == Target.LOGIN_QQ;
        }

        @Override
        public boolean checkRechargePlatformTarget(int target) {
            return false;
        }
    }
}
