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

import org.openqa.selenium.WebDriver;

/**
 * @author Kenji Heigel
 */
public class IOSMobileDriverImpl extends BaseMobileDriverImpl {

	public IOSMobileDriverImpl(String browserURL, WebDriver webDriver) {
		super(browserURL, webDriver);
	}

	@Override
	protected void tap(String locator) {
		TouchAction touchAction = new TouchAction(this);

		int screenPositionX = WebDriverHelper.getElementPositionCenterX(
			this, locator);

		int navigationBarHeight = 44;

		int screenPositionY =
			WebDriverHelper.getElementPositionCenterY(this, locator) +
				navigationBarHeight;

		context("NATIVE_APP");

		touchAction.tap(screenPositionX, screenPositionY);

		touchAction.perform();

		context("WEBVIEW_1");
	}

}