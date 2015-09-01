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

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * @author Brian Wing Shun Chan
 */
public class InternetExplorerWebDriverImpl extends BaseWebDriverImpl {

	public InternetExplorerWebDriverImpl(
		String projectDirName, String browserURL) {

		super(projectDirName, browserURL, new InternetExplorerDriver());
	}

	public InternetExplorerWebDriverImpl(
		String projectDirName, String browserURL, WebDriver webDriver) {

		super(projectDirName, browserURL, webDriver);
	}

	@Override
	public void javaScriptMouseDown(String locator) {
		if (PropsValues.SELENIUM_DESIRED_CAPABILITIES_VERSION.equals("11.0")) {
			WebDriverHelper.executeJavaScriptMouseEvent(
				this, locator, "pointerdown");
		}
		else {
			super.javaScriptMouseDown(locator);
		}
	}

	@Override
	public void javaScriptMouseUp(String locator) {
		if (PropsValues.SELENIUM_DESIRED_CAPABILITIES_VERSION.equals("11.0")) {
			WebDriverHelper.executeJavaScriptMouseEvent(
				this, locator, "pointerup");
		}
		else {
			super.javaScriptMouseUp(locator);
		}
	}

	private static final DesiredCapabilities _desiredCapabilities;

	static {
		_desiredCapabilities = DesiredCapabilities.internetExplorer();

		_desiredCapabilities.setCapability(
			InternetExplorerDriver.
				INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,
			true);
	}

}