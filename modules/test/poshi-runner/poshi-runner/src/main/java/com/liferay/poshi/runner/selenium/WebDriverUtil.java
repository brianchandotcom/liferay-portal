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
import com.liferay.poshi.runner.util.Validator;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;

import java.io.File;
import java.io.IOException;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
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
		return _webDriverUtil._getWebDriver();
	}

	public static void startWebDriver() {
		_webDriverUtil._startWebDriver();
	}

	public static void stopWebDriver() {
		_webDriverUtil._stopWebDriver();
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
		catch (MalformedURLException malformedURLException) {
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
		catch (MalformedURLException malformedURLException) {
		}

		return new AndroidDriver(url, desiredCapabilities);
	}

	private WebDriver _getChromeDriver() {
		System.setProperty(
			"webdriver.chrome.driver",
			SELENIUM_EXECUTABLE_DIR_NAME + SELENIUM_CHROME_DRIVER_EXECUTABLE);

		ChromeOptions chromeOptions = new ChromeOptions();

		Map<String, Object> preferences = new HashMap<>();

		String outputDirName = PropsValues.OUTPUT_DIR_NAME;

		try {
			File file = new File(outputDirName);

			outputDirName = file.getCanonicalPath();
		}
		catch (IOException ioException) {
			System.out.println(
				"Unable to get canonical path for " + outputDirName);
		}

		if (OSDetector.isWindows()) {
			outputDirName = StringUtil.replace(
				outputDirName, StringPool.FORWARD_SLASH, StringPool.BACK_SLASH);
		}

		preferences.put("download.default_directory", outputDirName);

		preferences.put("download.prompt_for_download", false);

		preferences.put("profile.default_content_settings.popups", 0);

		chromeOptions.setExperimentalOption("prefs", preferences);

		if (Validator.isNotNull(PropsValues.BROWSER_CHROME_BIN_ARGS)) {
			chromeOptions.addArguments(
				PropsValues.BROWSER_CHROME_BIN_ARGS.split("\\s+"));
		}

		if (Validator.isNotNull(PropsValues.BROWSER_CHROME_BIN_FILE)) {
			chromeOptions.setBinary(PropsValues.BROWSER_CHROME_BIN_FILE);
		}

		return new ChromeDriver(chromeOptions);
	}

	private WebDriver _getEdgeDriver() {
		return new EdgeDriver();
	}

	private WebDriver _getEdgeRemoteDriver() {
		EdgeOptions edgeOptions = new EdgeOptions();

		edgeOptions.setCapability("platform", "WINDOWS");

		URL url = null;

		try {
			url = new URL(
				PropsValues.SELENIUM_REMOTE_DRIVER_HUB + ":4444/wd/hub");
		}
		catch (MalformedURLException malformedURLException) {
		}

		return new RemoteWebDriver(url, edgeOptions);
	}

	private WebDriver _getFirefoxDriver() {
		System.setProperty("webdriver.firefox.marionette", "false");
		System.setProperty(
			"webdriver.gecko.driver",
			SELENIUM_EXECUTABLE_DIR_NAME + SELENIUM_GECKO_DRIVER_EXECUTABLE);

		FirefoxOptions firefoxOptions = new FirefoxOptions();

		String outputDirName = PropsValues.OUTPUT_DIR_NAME;

		if (OSDetector.isWindows()) {
			outputDirName = StringUtil.replace(
				outputDirName, StringPool.FORWARD_SLASH, StringPool.BACK_SLASH);
		}

		firefoxOptions.addPreference("browser.download.dir", outputDirName);
		firefoxOptions.addPreference("browser.download.folderList", 2);
		firefoxOptions.addPreference(
			"browser.download.manager.showWhenStarting", false);
		firefoxOptions.addPreference("browser.download.useDownloadDir", true);
		firefoxOptions.addPreference(
			"browser.helperApps.alwaysAsk.force", false);
		firefoxOptions.addPreference(
			"browser.helperApps.neverAsk.saveToDisk",
			"application/excel,application/msword,application/pdf," +
				"application/zip,audio/mpeg3,image/jpeg,image/png,text/plain");
		firefoxOptions.addPreference("dom.max_chrome_script_run_time", 300);
		firefoxOptions.addPreference("dom.max_script_run_time", 300);

		if (Validator.isNotNull(PropsValues.BROWSER_FIREFOX_BIN_FILE)) {
			File file = new File(PropsValues.BROWSER_FIREFOX_BIN_FILE);

			FirefoxBinary firefoxBinary = new FirefoxBinary(file);

			firefoxOptions.setBinary(firefoxBinary);
		}

		firefoxOptions.setCapability("locationContextEnabled", false);

		try {
			FirefoxProfile firefoxProfile = new FirefoxProfile();

			firefoxProfile.addExtension(
				WebDriverUtil.class,
				"/META-INF/resources/firefox/extensions/jserrorcollector.xpi");

			firefoxOptions.setProfile(firefoxProfile);
		}
		catch (Exception exception) {
			System.out.println(
				"Unable to add the jserrorcollector.xpi extension to the " +
					"Firefox profile.");
		}

		return new FirefoxDriver(firefoxOptions);
	}

	private WebDriver _getInternetExplorerDriver() {
		InternetExplorerOptions internetExplorerOptions =
			new InternetExplorerOptions();

		internetExplorerOptions.setCapability(
			InternetExplorerDriver.
				INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,
			true);

		return new InternetExplorerDriver(internetExplorerOptions);
	}

	private WebDriver _getInternetExplorerRemoteDriver() {
		InternetExplorerOptions internetExplorerOptions =
			new InternetExplorerOptions();

		internetExplorerOptions.setCapability(
			InternetExplorerDriver.
				INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,
			true);
		internetExplorerOptions.setCapability(
			"platform", PropsValues.SELENIUM_DESIRED_CAPABILITIES_PLATFORM);
		internetExplorerOptions.setCapability(
			"version", PropsValues.BROWSER_VERSION);

		URL url = null;

		try {
			url = new URL(
				PropsValues.SELENIUM_REMOTE_DRIVER_HUB + ":4444/wd/hub");
		}
		catch (MalformedURLException malformedURLException) {
		}

		return new RemoteWebDriver(url, internetExplorerOptions);
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
		catch (Exception exception) {
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

			System.setProperty(
				"webdriver.edge.driver",
				SELENIUM_EXECUTABLE_DIR_NAME + "MicrosoftWebDriver.exe");

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

	private static final WebDriverUtil _webDriverUtil = new WebDriverUtil();

	private WebDriver _webDriver;

}