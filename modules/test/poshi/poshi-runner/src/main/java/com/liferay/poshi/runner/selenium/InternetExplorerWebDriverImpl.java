/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.poshi.runner.selenium;

import org.openqa.selenium.WebDriver;

/**
 * @author Brian Wing Shun Chan
 */
public class InternetExplorerWebDriverImpl extends BaseWebDriverImpl {

	public InternetExplorerWebDriverImpl(
		String browserURL, WebDriver webDriver) {

		super(browserURL, webDriver);
	}

	@Override
	public void javaScriptMouseDown(String locator) {
		if (poshiProperties.browserVersion.equals("10.0")) {
			executeJavaScriptEvent(locator, "MSPointerEvent", "MSPointerDown");
		}
		else if (poshiProperties.browserVersion.equals("11.0")) {
			executeJavaScriptEvent(locator, "MouseEvent", "pointerdown");
		}
		else {
			super.javaScriptMouseDown(locator);
		}
	}

	@Override
	public void javaScriptMouseUp(String locator) {
		if (poshiProperties.browserVersion.equals("10.0")) {
			executeJavaScriptEvent(locator, "MSPointerEvent", "MSPointerUp");
		}
		else if (poshiProperties.browserVersion.equals("11.0")) {
			executeJavaScriptEvent(locator, "MouseEvent", "pointerup");
		}
		else {
			super.javaScriptMouseUp(locator);
		}
	}

}