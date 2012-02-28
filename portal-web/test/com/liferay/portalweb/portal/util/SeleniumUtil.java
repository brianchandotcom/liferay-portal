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

import com.thoughtworks.selenium.Selenium;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
/**
 * @author Brian Wing Shun Chan
 */
public class SeleniumUtil {

	public static LiferayDefaultSelenium getSelenium() {
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

	private LiferayDefaultSelenium _getSelenium() {
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

		_selenium = new LiferayDefaultSelenium();
		_selenium.get(portalURL);
/*
		_selenium.start();

		_selenium.setContext(this.getClass().getName());
*/
	}

	private void _stopSelenium() {
/*
		if (_selenium != null) {
			_selenium.stop();
		}

		_selenium = null;
*/
		_selenium.close();

	}

	private static SeleniumUtil _instance = new SeleniumUtil();

	private String _timestamp;
	private LiferayDefaultSelenium _selenium;

}