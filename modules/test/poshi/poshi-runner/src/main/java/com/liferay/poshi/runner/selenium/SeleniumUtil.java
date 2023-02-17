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

import com.liferay.poshi.core.selenium.LiferaySelenium;
import com.liferay.poshi.core.util.PropsValues;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;

/**
 * @author Brian Wing Shun Chan
 */
public class SeleniumUtil extends PropsValues {

	public static LiferaySelenium getSelenium(String testName) {
		if (!_seleniums.containsKey(testName)) {
			startSelenium(testName);
		}

		return _seleniums.get(testName);
	}

	public static synchronized void startSelenium(String testName) {
		String portalURL = PORTAL_URL;

		if (TCAT_ENABLED) {
			portalURL = "http://localhost:8180/console";
		}

		WebDriverUtil.startWebDriver(testName);

		WebDriver webDriver = WebDriverUtil.getWebDriver(testName);

		if (BROWSER_TYPE.equals("chrome")) {
			_seleniums.put(
				testName, new ChromeWebDriverImpl(portalURL, webDriver));
		}
		else if (BROWSER_TYPE.equals("edge") &&
				 !SELENIUM_REMOTE_DRIVER_ENABLED) {

			_seleniums.put(
				testName, new EdgeWebDriverImpl(portalURL, webDriver));
		}
		else if (BROWSER_TYPE.equals("edge") &&
				 SELENIUM_REMOTE_DRIVER_ENABLED) {

			_seleniums.put(
				testName, new EdgeRemoteWebDriverImpl(portalURL, webDriver));
		}
		else if (BROWSER_TYPE.equals("firefox")) {
			_seleniums.put(
				testName, new FirefoxWebDriverImpl(portalURL, webDriver));
		}
		else if (BROWSER_TYPE.equals("internetexplorer") &&
				 !SELENIUM_REMOTE_DRIVER_ENABLED) {

			System.setProperty(
				"webdriver.ie.driver",
				SELENIUM_EXECUTABLE_DIR_NAME + SELENIUM_IE_DRIVER_EXECUTABLE);

			_seleniums.put(
				testName,
				new InternetExplorerWebDriverImpl(portalURL, webDriver));
		}
		else if (BROWSER_TYPE.equals("internetexplorer") &&
				 SELENIUM_REMOTE_DRIVER_ENABLED) {

			_seleniums.put(
				testName,
				new InternetExplorerRemoteWebDriverImpl(portalURL, webDriver));
		}
		else if (BROWSER_TYPE.equals("safari")) {
			_seleniums.put(
				testName, new SafariWebDriverImpl(portalURL, webDriver));
		}
		else {
			throw new RuntimeException("Invalid browser type " + BROWSER_TYPE);
		}

		LiferaySelenium liferaySelenium = _seleniums.get(testName);

		liferaySelenium.setTestName(testName);
	}

	public static void stopSelenium(String testName) {
		LiferaySelenium liferaySelenium = _seleniums.get(testName);

		if (liferaySelenium != null) {
			WebDriverUtil.stopWebDriver(testName);

			liferaySelenium.stop();

			liferaySelenium.stopLogger();
		}

		_seleniums.remove(testName);
	}

	private static final Map<String, LiferaySelenium> _seleniums =
		new HashMap<>();

}