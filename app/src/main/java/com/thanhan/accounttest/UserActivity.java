package com.thanhan.accounttest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.huawei.hmf.tasks.OnFailureListener;
import com.huawei.hmf.tasks.OnSuccessListener;
import com.huawei.hmf.tasks.Task;
import com.huawei.hms.support.account.request.AccountAuthParams;
import com.huawei.hms.support.account.service.AccountAuthService;

public class UserActivity extends AppCompatActivity {
    // AccountAuthService provides a set of APIs, including silentSignIn, getSignInIntent, and signOut.
    private AccountAuthService mAuthService;
    // Set HUAWEI ID sign-in authorization parameters.
    private AccountAuthParams mAuthParam;
    // Define the request code for signInIntent.
    private static final int REQUEST_CODE_SIGN_IN = 1000;
    // Define the log tag.
    private static final String TAG = "Account";
    ImageView img;
    TextView info;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        matching();

        info.setText(getIntent().getStringExtra("name") + "\n" + getIntent().getStringExtra("email"));

        Glide.with(UserActivity.this)
                .load(getIntent().getStringExtra("photo"))
                .into(img);

        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelAuthorization();
            }
        });
    }
    private void cancelAuthorization() {
        Task<Void> task = mAuthService.cancelAuthorization();
        task.addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.i(TAG, "cancelAuthorization success");
            }
        });
        task.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                Log.i(TAG, "cancelAuthorization failure:" + e.getClass().getSimpleName());
            }
        });
    }
    private void signOut() {
        Task<Void> signOutTask = mAuthService.signOut();
        signOutTask.addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.i(TAG, "signOut Success");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                Log.i(TAG, "signOut fail");
            }
        });
    }
    private void matching() {
        img = (ImageView) findViewById(R.id.img_avt);
        info = (TextView) findViewById(R.id.tv_info);
    }
}