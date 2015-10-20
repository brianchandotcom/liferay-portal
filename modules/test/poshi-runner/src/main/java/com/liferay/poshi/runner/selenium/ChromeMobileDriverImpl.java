/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.poshi.runner.selenium;

import com.liferay.poshi.runner.util.PropsValues;

import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.net.URL;

import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * @author Kenji Heigel
 */
public class ChromeMobileDriverImpl extends BaseMobileDriverImpl {

	public ChromeMobileDriverImpl(String browserURL) {
		super(browserURL, new AndroidDriver(_url, _desiredCapabilities));
	}

	@Override
	public void hideKeyboard() {
		context("NATIVE_APP");

		boolean keyboardVisible = false;

		try {
			keyboardVisible = isKeyboardVisible();
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		if (keyboardVisible) {
			super.hideKeyboard();
		}

		context("WEBVIEW_1");
	}

	@Override
	public boolean isInViewport(String locator) {
		return true;
	}

	public boolean isKeyboardVisible() throws Exception {
		Runtime runtime = Runtime.getRuntime();

		StringBuilder sb = new StringBuilder(4);

		sb.append(PropsValues.MOBILE_ANDROID_HOME);
		sb.append("/platform-tools/adb -s ");
		sb.append(PropsValues.MOBILE_DEVICE_NAME);
		sb.append(" shell dumpsys input_method | grep mInputShown");

		Process process = runtime.exec(sb.toString());

		InputStreamReader inputStreamReader = new InputStreamReader(
			process.getInputStream());

		BufferedReader inputBufferedReader = new BufferedReader(
			inputStreamReader);

		String inputShownString = inputBufferedReader.readLine();

		inputShownString = inputShownString.replaceAll(".*mInputShown=", "");

		return Boolean.parseBoolean(inputShownString);
	}

	@Override
	protected void tap(String locator) {
		TouchAction touchAction = new TouchAction(this);

		hideKeyboard();

		super.swipeWebElementIntoView(locator);

		int elementPositionCenterX = WebDriverHelper.getElementPositionCenterX(
			this, locator);

		int screenPositionX = elementPositionCenterX * 2;

		int elementPositionCenterY = WebDriverHelper.getElementPositionCenterY(
			this, locator);
		int navigationBarHeight = 162;
		int viewportPositionTop = WebDriverHelper.getScrollOffsetY(this);

		int screenPositionY =
			((elementPositionCenterY - viewportPositionTop) * 2) +
				navigationBarHeight;

		context("NATIVE_APP");

		touchAction.tap(screenPositionX, screenPositionY);

		touchAction.perform();

		context("WEBVIEW_1");
	}

	private static final DesiredCapabilities _desiredCapabilities;
	private static final URL _url;

	static {
		_desiredCapabilities = DesiredCapabilities.android();

		_desiredCapabilities.setCapability("browserName", "Chrome");
		_desiredCapabilities.setCapability(
			"deviceName", PropsValues.MOBILE_DEVICE_NAME);
		_desiredCapabilities.setCapability("platformName", "Android");
		_desiredCapabilities.setCapability("platformVersion", "5.0.1");

		URL url = null;

		try {
			url = new URL("http://0.0.0.0:4723/wd/hub/");
		}
		catch (Exception e) {
		}

		_url = url;
	}

}