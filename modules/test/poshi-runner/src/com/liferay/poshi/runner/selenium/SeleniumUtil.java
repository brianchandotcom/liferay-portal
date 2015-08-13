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

import com.liferay.poshi.runner.PoshiRunnerGetterUtil;
import com.liferay.poshi.runner.util.PropsValues;

/**
 * @author Brian Wing Shun Chan
 */
public class SeleniumUtil extends PropsValues {

	public static LiferaySelenium getSelenium() {
		return _instance._getSelenium();
	}

	public static void startSelenium() {
		_instance._startSelenium();
	}

	public static void stopSelenium() {
		_instance._stopSelenium();
	}

	private LiferaySelenium _getSelenium() {
		if (_selenium == null) {
			_startSelenium();
		}

		return _selenium;
	}

	private void _startSelenium() {
		String projectDirName = PoshiRunnerGetterUtil.getProjectDirName();

		String portalURL = PORTAL_URL;

		if (TCAT_ENABLED) {
			portalURL = "http://localhost:8180/console";
		}

		if (MOBILE_DEVICE_TYPE.equals("android")) {
			_selenium = new AndroidMobileDriverImpl(projectDirName, portalURL);
		}
		else if (MOBILE_DEVICE_TYPE.equals("ios")) {
			_selenium = new IOSMobileDriverImpl(projectDirName, portalURL);
		}
		else {
			if (BROWSER_TYPE.equals("*chrome") ||
				BROWSER_TYPE.equals("*firefox")) {

				_selenium = new FirefoxWebDriverImpl(projectDirName, portalURL);
			}
			else if (BROWSER_TYPE.equals("*googlechrome")) {
				System.setProperty(
					"webdriver.chrome.driver",
					SELENIUM_EXECUTABLE_DIR_NAME +
						SELENIUM_CHROME_DRIVER_EXECUTABLE);

				_selenium = new ChromeWebDriverImpl(projectDirName, portalURL);
			}
			else if ((BROWSER_TYPE.equals("*iehta") ||
					  BROWSER_TYPE.equals("*iexplore")) &&
					 !SELENIUM_REMOTE_DRIVER_ENABLED) {

				System.setProperty(
					"webdriver.ie.driver",
					SELENIUM_EXECUTABLE_DIR_NAME +
						SELENIUM_IE_DRIVER_EXECUTABLE);

				_selenium = new InternetExplorerWebDriverImpl(
					projectDirName, portalURL);
			}
			else if ((BROWSER_TYPE.equals("*iehta") ||
					  BROWSER_TYPE.equals("*iexplore")) &&
					 SELENIUM_REMOTE_DRIVER_ENABLED) {

				_selenium = new InternetExplorerRemoteWebDriverImpl(
					projectDirName, portalURL);
			}
			else if (BROWSER_TYPE.equals("*safari")) {
				_selenium = new SafariWebDriverImpl(projectDirName, portalURL);
			}
			else {
				throw new RuntimeException(
					"Invalid browser type " + BROWSER_TYPE);
			}
		}
	}

	@SuppressWarnings("deprecation")
	private void _stopSelenium() {
		if (_selenium != null) {
			_selenium.stop();

			_selenium.stopLogger();
		}

		_selenium = null;
	}

	private static final SeleniumUtil _instance = new SeleniumUtil();

	private LiferaySelenium _selenium;

}