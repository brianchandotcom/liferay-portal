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

import com.liferay.poshi.runner.util.OSDetector;
import com.liferay.poshi.runner.util.PropsValues;
import com.liferay.poshi.runner.util.StringPool;
import com.liferay.poshi.runner.util.StringUtil;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

/**
 * @author Brian Wing Shun Chan
 * @author Kenji Heigel
 * @author Michael Hashimoto
 */
public class WebDriverUtil extends PropsValues {

	public static WebDriver getWebDriver() {
		return _instance._getWebDriver();
	}

	public static void startWebDriver() {
		_instance._startWebDriver();
	}

	public static void stopWebDriver() {
		_instance._stopWebDriver();
	}

	private WebDriver _getAndroidDriver() {
		DesiredCapabilities desiredCapabilities = DesiredCapabilities.android();

		desiredCapabilities.setCapability("browserName", "Browser");
		desiredCapabilities.setCapability("deviceName", "deviceName");
		desiredCapabilities.setCapability(
			"newCommandTimeout", PropsValues.TIMEOUT_EXPLICIT_WAIT);
		desiredCapabilities.setCapability("platformName", "Android");
		desiredCapabilities.setCapability("platformVersion", "4.4");

		URL url = null;

		try {
			url = new URL("http://0.0.0.0:4723/wd/hub/");
		}
		catch (MalformedURLException murle) {
		}

		return new AndroidDriver(url, desiredCapabilities);
	}

	private WebDriver _getChromeAndroidDriver() {
		DesiredCapabilities desiredCapabilities = DesiredCapabilities.android();

		desiredCapabilities.setCapability("browserName", "Chrome");
		desiredCapabilities.setCapability(
			"deviceName", PropsValues.MOBILE_DEVICE_NAME);
		desiredCapabilities.setCapability(
			"newCommandTimeout", PropsValues.TIMEOUT_EXPLICIT_WAIT);
		desiredCapabilities.setCapability("platformName", "Android");
		desiredCapabilities.setCapability("platformVersion", "5.0.1");

		URL url = null;

		try {
			url = new URL("http://0.0.0.0:4723/wd/hub/");
		}
		catch (MalformedURLException murle) {
		}

		return new AndroidDriver(url, desiredCapabilities);
	}

	private WebDriver _getChromeDriver() {
		System.setProperty(
			"webdriver.chrome.driver",
			SELENIUM_EXECUTABLE_DIR_NAME + SELENIUM_CHROME_DRIVER_EXECUTABLE);

		DesiredCapabilities desiredCapabilities = DesiredCapabilities.chrome();

		Map<String, Object> preferences = new HashMap<>();

		String outputDirName = PropsValues.OUTPUT_DIR_NAME;

		if (OSDetector.isWindows()) {
			outputDirName = StringUtil.replace(
				outputDirName, StringPool.FORWARD_SLASH, StringPool.BACK_SLASH);
		}

		preferences.put("download.default_directory", outputDirName);

		preferences.put("download.prompt_for_download", false);

		desiredCapabilities.setCapability("chrome.prefs", preferences);

		return new ChromeDriver(desiredCapabilities);
	}

	private WebDriver _getEdgeDriver() {
		return new EdgeDriver();
	}

	private WebDriver _getEdgeRemoteDriver() {
		DesiredCapabilities desiredCapabilities = DesiredCapabilities.edge();

		desiredCapabilities.setCapability("platform", "WINDOWS");

		URL url = null;

		try {
			url = new URL(
				PropsValues.SELENIUM_REMOTE_DRIVER_HUB + ":4444/wd/hub");
		}
		catch (MalformedURLException murle) {
		}

		return new RemoteWebDriver(url, desiredCapabilities);
	}

	private WebDriver _getFirefoxDriver() {
		FirefoxProfile firefoxProfile = new FirefoxProfile();

		try {
			firefoxProfile.addExtension(
				WebDriverUtil.class,
				"/META-INF/resources/firefox/extensions/jserrorcollector.xpi");
		}
		catch (Exception e) {
		}

		String outputDirName = PropsValues.OUTPUT_DIR_NAME;

		if (OSDetector.isWindows()) {
			outputDirName = StringUtil.replace(
				outputDirName, StringPool.FORWARD_SLASH, StringPool.BACK_SLASH);
		}

		firefoxProfile.setPreference("browser.download.dir", outputDirName);

		firefoxProfile.setPreference("browser.download.folderList", 2);
		firefoxProfile.setPreference(
			"browser.download.manager.showWhenStarting", false);
		firefoxProfile.setPreference("browser.download.useDownloadDir", true);
		firefoxProfile.setPreference(
			"browser.helperApps.alwaysAsk.force", false);
		firefoxProfile.setPreference(
			"browser.helperApps.neverAsk.saveToDisk",
			"application/excel,application/msword,application/pdf," +
				"application/zip,audio/mpeg3,image/jpeg,image/png,text/plain");
		firefoxProfile.setPreference("dom.max_chrome_script_run_time", 300);
		firefoxProfile.setPreference("dom.max_script_run_time", 300);

		return new FirefoxDriver(firefoxProfile);
	}

	private WebDriver _getInternetExplorerDriver() {
		DesiredCapabilities desiredCapabilities =
			DesiredCapabilities.internetExplorer();

		desiredCapabilities.setCapability(
			InternetExplorerDriver.
				INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,
			true);

		return new InternetExplorerDriver(desiredCapabilities);
	}

	private WebDriver _getInternetExplorerRemoteDriver() {
		DesiredCapabilities desiredCapabilities =
			DesiredCapabilities.internetExplorer();

		desiredCapabilities.setCapability(
			InternetExplorerDriver.
				INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,
			true);
		desiredCapabilities.setCapability(
			"platform", PropsValues.SELENIUM_DESIRED_CAPABILITIES_PLATFORM);
		desiredCapabilities.setCapability(
			"version", PropsValues.BROWSER_VERSION);

		URL url = null;

		try {
			url = new URL(
				PropsValues.SELENIUM_REMOTE_DRIVER_HUB + ":4444/wd/hub");
		}
		catch (MalformedURLException murle) {
		}

		return new RemoteWebDriver(url, desiredCapabilities);
	}

	private WebDriver _getIOSMobileDriver() {
		DesiredCapabilities desiredCapabilities = DesiredCapabilities.iphone();

		desiredCapabilities.setCapability("browserName", "Safari");
		desiredCapabilities.setCapability("deviceName", "iPhone 5s");
		desiredCapabilities.setCapability(
			"newCommandTimeout", PropsValues.TIMEOUT_EXPLICIT_WAIT);
		desiredCapabilities.setCapability("platformName", "iOS");
		desiredCapabilities.setCapability(
			"platformVersion", PropsValues.BROWSER_VERSION);

		URL url = null;

		try {
			url = new URL("http://0.0.0.0:4723/wd/hub/");
		}
		catch (Exception e) {
		}

		return new IOSDriver(url, desiredCapabilities);
	}

	private WebDriver _getSafariDriver() {
		return new SafariDriver();
	}

	private WebDriver _getWebDriver() {
		return _webDriver;
	}

	private void _startWebDriver() {
		if (BROWSER_TYPE.equals("android")) {
			_webDriver = _getAndroidDriver();
		}
		else if (BROWSER_TYPE.equals("androidchrome")) {
			_webDriver = _getChromeAndroidDriver();
		}
		else if (BROWSER_TYPE.equals("chrome")) {
			_webDriver = _getChromeDriver();
		}
		else if (BROWSER_TYPE.equals("edge") &&
				 !SELENIUM_REMOTE_DRIVER_ENABLED) {

			_webDriver = _getEdgeDriver();
		}
		else if (BROWSER_TYPE.equals("edge") &&
				 SELENIUM_REMOTE_DRIVER_ENABLED) {

			_webDriver = _getEdgeRemoteDriver();
		}
		else if (BROWSER_TYPE.equals("firefox")) {
			_webDriver = _getFirefoxDriver();
		}
		else if (BROWSER_TYPE.equals("internetexplorer") &&
				 !SELENIUM_REMOTE_DRIVER_ENABLED) {

			System.setProperty(
				"webdriver.ie.driver",
				SELENIUM_EXECUTABLE_DIR_NAME + SELENIUM_IE_DRIVER_EXECUTABLE);

			_webDriver = _getInternetExplorerDriver();
		}
		else if (BROWSER_TYPE.equals("internetexplorer") &&
				 SELENIUM_REMOTE_DRIVER_ENABLED) {

			_webDriver = _getInternetExplorerRemoteDriver();
		}
		else if (BROWSER_TYPE.equals("iossafari")) {
			_webDriver = _getIOSMobileDriver();
		}
		else if (BROWSER_TYPE.equals("safari")) {
			_webDriver = _getSafariDriver();
		}
		else {
			throw new RuntimeException("Invalid browser type " + BROWSER_TYPE);
		}
	}

	private void _stopWebDriver() {
		if (_webDriver != null) {
			_webDriver.quit();
		}

		_webDriver = null;
	}

	private static final WebDriverUtil _instance = new WebDriverUtil();

	private WebDriver _webDriver;

}