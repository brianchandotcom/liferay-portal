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

import io.appium.java_client.TouchAction;
import io.appium.java_client.ios.IOSDriver;

import java.net.URL;

import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * @author Kenji Heigel
 */
public class IOSMobileDriverImpl extends BaseMobileDriverImpl {

	public IOSMobileDriverImpl(String projectDirName, String browserURL) {
		super(
			projectDirName, browserURL,
			new IOSDriver(_url, _desiredCapabilities));
	}

	protected void tap(String locator) {
		TouchAction touchAction = new TouchAction(this);

		int screenPositionX = WebDriverHelper.getElementPositionCenterX(
			this, locator);

		int navigationBarHeight = 50;

		int screenPositionY =
			WebDriverHelper.getElementPositionCenterY(this, locator) +
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

		_desiredCapabilities.setCapability("browserName", "Safari");
		_desiredCapabilities.setCapability("deviceName", "iPhone 5s");
		_desiredCapabilities.setCapability("platformName", "iOS");
		_desiredCapabilities.setCapability("platformVersion", "8.2");

		URL url = null;

		try {
			url = new URL("http://0.0.0.0:4723/wd/hub/");
		}
		catch (Exception e) {
		}

		_url = url;
	}

}