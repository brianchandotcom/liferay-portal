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

import com.liferay.poshi.core.util.OSDetector;
import com.liferay.poshi.core.util.PropsValues;
import com.liferay.poshi.core.util.StringPool;
import com.liferay.poshi.core.util.StringUtil;
import com.liferay.poshi.core.util.Validator;
import com.liferay.poshi.runner.util.ProxyUtil;

import java.io.File;
import java.io.IOException;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.UnexpectedAlertBehaviour;
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
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;

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

	private WebDriver _getChromeDriver() {
		System.setProperty(
			"webdriver.chrome.driver",
			SELENIUM_EXECUTABLE_DIR_NAME + SELENIUM_CHROME_DRIVER_EXECUTABLE);

		ChromeOptions chromeOptions = new ChromeOptions();

		_setGenericCapabilities(chromeOptions);

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

	private WebDriver _getChromeRemoteDriver() {
		ChromeOptions chromeOptions = new ChromeOptions();

		_setGenericCapabilities(chromeOptions);

		return new RemoteWebDriver(_REMOTE_DRIVER_URL, chromeOptions);
	}

	private InternetExplorerOptions _getDefaultInternetExplorerOptions() {
		InternetExplorerOptions internetExplorerOptions =
			new InternetExplorerOptions();

		_setGenericCapabilities(internetExplorerOptions);

		internetExplorerOptions.destructivelyEnsureCleanSession();
		internetExplorerOptions.introduceFlakinessByIgnoringSecurityDomains();

		return internetExplorerOptions;
	}

	private WebDriver _getEdgeDriver() {
		return new EdgeDriver();
	}

	private WebDriver _getEdgeRemoteDriver() {
		EdgeOptions edgeOptions = new EdgeOptions();

		_setGenericCapabilities(edgeOptions);

		edgeOptions.setCapability("platform", "WINDOWS");

		return new RemoteWebDriver(_REMOTE_DRIVER_URL, edgeOptions);
	}

	private WebDriver _getFirefoxDriver() {
		System.setProperty("webdriver.firefox.marionette", "false");
		System.setProperty(
			"webdriver.gecko.driver",
			SELENIUM_EXECUTABLE_DIR_NAME + SELENIUM_GECKO_DRIVER_EXECUTABLE);

		FirefoxOptions firefoxOptions = new FirefoxOptions();

		_setGenericCapabilities(firefoxOptions);

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

	private WebDriver _getFirefoxRemoteDriver() {
		FirefoxOptions firefoxOptions = new FirefoxOptions();

		_setGenericCapabilities(firefoxOptions);

		return new RemoteWebDriver(_REMOTE_DRIVER_URL, firefoxOptions);
	}

	private WebDriver _getInternetExplorerDriver() {
		return new InternetExplorerDriver(_getDefaultInternetExplorerOptions());
	}

	private WebDriver _getInternetExplorerRemoteDriver() {
		InternetExplorerOptions internetExplorerOptions =
			_getDefaultInternetExplorerOptions();

		internetExplorerOptions.setCapability(
			"platform", PropsValues.SELENIUM_DESIRED_CAPABILITIES_PLATFORM);
		internetExplorerOptions.setCapability(
			"version", PropsValues.BROWSER_VERSION);

		return new RemoteWebDriver(_REMOTE_DRIVER_URL, internetExplorerOptions);
	}

	private WebDriver _getSafariDriver() {
		SafariOptions safariOptions = new SafariOptions();

		_setGenericCapabilities(safariOptions);

		return new SafariDriver();
	}

	private WebDriver _getSafariRemoteDriver() {
		SafariOptions safariOptions = new SafariOptions();

		_setGenericCapabilities(safariOptions);

		return new RemoteWebDriver(_REMOTE_DRIVER_URL, safariOptions);
	}

	private WebDriver _getWebDriver() {
		return _webDriver;
	}

	private void _setGenericCapabilities(
		MutableCapabilities mutableCapabilities) {

		for (Map.Entry<String, Object> entry :
				_genericCapabilities.entrySet()) {

			mutableCapabilities.setCapability(entry.getKey(), entry.getValue());
		}
	}

	private void _startWebDriver() {
		if (Validator.isNotNull(SELENIUM_REMOTE_DRIVER_URL)) {
			if (BROWSER_TYPE.equals("chrome")) {
				_webDriver = _getChromeRemoteDriver();
			}

			if (BROWSER_TYPE.equals("edge")) {
				_webDriver = _getEdgeRemoteDriver();
			}

			if (BROWSER_TYPE.equals("firefox")) {
				_webDriver = _getFirefoxRemoteDriver();
			}

			if (BROWSER_TYPE.equals("internetexplorer")) {
				_webDriver = _getInternetExplorerRemoteDriver();
			}

			if (BROWSER_TYPE.equals("safari")) {
				_webDriver = _getSafariRemoteDriver();
			}
		}
		else if (BROWSER_TYPE.equals("chrome")) {
			_webDriver = _getChromeDriver();
		}
		else if (BROWSER_TYPE.equals("edge")) {
			if (SELENIUM_EDGE_DRIVER_EXECUTABLE != null) {
				System.setProperty(
					"webdriver.edge.driver",
					SELENIUM_EXECUTABLE_DIR_NAME +
						SELENIUM_EDGE_DRIVER_EXECUTABLE);
			}

			_webDriver = _getEdgeDriver();
		}
		else if (BROWSER_TYPE.equals("firefox")) {
			_webDriver = _getFirefoxDriver();
		}
		else if (BROWSER_TYPE.equals("internetexplorer")) {
			if (SELENIUM_IE_DRIVER_EXECUTABLE != null) {
				System.setProperty(
					"webdriver.ie.driver",
					SELENIUM_EXECUTABLE_DIR_NAME +
						SELENIUM_IE_DRIVER_EXECUTABLE);
			}

			_webDriver = _getInternetExplorerDriver();
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

	private static final URL _REMOTE_DRIVER_URL;

	private static final Map<String, Object> _genericCapabilities =
		new HashMap<String, Object>() {
			{
				if (PropsValues.PROXY_SERVER_ENABLED) {
					put(CapabilityType.ACCEPT_INSECURE_CERTS, true);
					put(CapabilityType.ACCEPT_SSL_CERTS, true);
					put(CapabilityType.PROXY, ProxyUtil.getSeleniumProxy());
				}

				put(
					CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR,
					UnexpectedAlertBehaviour.IGNORE);
			}
		};

	private static final WebDriverUtil _webDriverUtil = new WebDriverUtil();

	static {
		try {
			if (Validator.isNull(SELENIUM_REMOTE_DRIVER_URL)) {
				_REMOTE_DRIVER_URL = new URL("http://localhost:4444/wd/hub");
			}
			else if (SELENIUM_REMOTE_DRIVER_URL.matches(".*\\/wd\\/hub\\/?$")) {
				_REMOTE_DRIVER_URL = new URL(SELENIUM_REMOTE_DRIVER_URL);
			}
			else {
				_REMOTE_DRIVER_URL = new URL(
					SELENIUM_REMOTE_DRIVER_URL + "/wd/hub");
			}
		}
		catch (MalformedURLException malformedURLException) {
			throw new RuntimeException(malformedURLException);
		}
	}

	private WebDriver _webDriver;

}