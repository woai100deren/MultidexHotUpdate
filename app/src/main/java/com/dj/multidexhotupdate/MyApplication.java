package com.dj.multidexhotupdate;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import com.dj.multidexhotupdate.utils.AssetsFileUtil;
import com.dj.multidexhotupdate.utils.ClassLoaderHookHelper;

import java.io.File;

public class MyApplication extends Application {
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

        Log.e("BugTag2", "" + getClassLoader());//PathClassLoader
        Log.e("BugTag2", "" + getClassLoader().getParent());//BootClassLoader

        String fixPath = "MainActivity.dex";
        try {
            String path = AssetsFileUtil.copyAssetToCache(this, fixPath);
            File fixFile = new File(path);
            if (Build.VERSION.SDK_INT >= 23) {
                ClassLoaderHookHelper.hookV23(getClassLoader(), fixFile, getCacheDir());
            } else if (Build.VERSION.SDK_INT >= 19) {
                ClassLoaderHookHelper.hookV19(getClassLoader(), fixFile, getCacheDir());
            } else if (Build.VERSION.SDK_INT >= 14) {
                ClassLoaderHookHelper.hookV14(getClassLoader(), fixFile, getCacheDir());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
