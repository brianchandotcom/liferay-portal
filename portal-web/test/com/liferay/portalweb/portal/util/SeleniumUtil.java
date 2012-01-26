/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portalweb.portal.util;

import com.liferay.portal.kernel.util.Time;
import com.liferay.portalweb.portal.util.TestPropsValues;

/**
 * @author Brian Wing Shun Chan
 */
public class SeleniumUtil {

	public static LiferaySelenium getSelenium() {
		return _instance._getSelenium();
	}

	public static String getTimestamp() {
		return _instance._getTimestamp();
	}

	public static void startSelenium() {
		_instance._startSelenium();
	}

	public static void stopSelenium() {
		_instance._stopSelenium();
	}

	private SeleniumUtil() {
		_timestamp = Time.getTimestamp();
	}

	private LiferaySelenium _getSelenium() {
		if (_selenium == null) {
			_startSelenium();
		}

		return _selenium;
	}

	private String _getTimestamp() {
		return _timestamp;
	}

	private void _startSelenium() {
		String seleniumHost = TestPropsValues.SELENIUM_HOST;
		int seleniumPort = TestPropsValues.SELENIUM_PORT;
		String browserType = TestPropsValues.BROWSER_TYPE;
		String portalURL = TestPropsValues.PORTAL_URL;

		_selenium = new LiferayDefaultSelenium(
			seleniumHost, seleniumPort, browserType, portalURL);

		_selenium.start();

		_selenium.setContext(this.getClass().getName());
	}

	private void _stopSelenium() {
		if (_selenium != null) {
			_selenium.stop();
		}

		_selenium = null;
	}

	private static SeleniumUtil _instance = new SeleniumUtil();

	private LiferaySelenium _selenium;
	private String _timestamp;

}