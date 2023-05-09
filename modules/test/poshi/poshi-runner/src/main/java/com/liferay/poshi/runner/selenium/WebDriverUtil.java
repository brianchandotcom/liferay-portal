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
import com.liferay.poshi.core.util.OSDetector;
import com.liferay.poshi.core.util.PropsValues;
import com.liferay.poshi.core.util.StringPool;
import com.liferay.poshi.core.util.StringUtil;
import com.liferay.poshi.core.util.Validator;
import com.liferay.poshi.runner.logger.ParallelPrintStream;
import com.liferay.poshi.runner.util.ProxyUtil;

import java.io.File;
import java.io.IOException;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
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
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;

/**
 * @author Brian Wing Shun Chan
 * @author Kenji Heigel
 * @author Michael Hashimoto
 */
public class WebDriverUtil extends PropsValues {

	public static LiferaySelenium getLiferaySelenium(String testName) {
		if (!_webDrivers.containsKey(testName)) {
			startWebDriver(testName);
		}

		return (LiferaySelenium)_webDrivers.get(testName);
	}

	public static WebDriver getWebDriver(String testName) {
		return _webDrivers.get(testName);
	}

	public static synchronized void startWebDriver(String testName) {
		if (_webDrivers.containsKey(testName)) {
			throw new RuntimeException(
				"WebDriver instance already started for: " + testName);
		}

		String portalURL = PORTAL_URL;

		if (TCAT_ENABLED) {
			portalURL = "http://localhost:8180/console";
		}

		if (BROWSER_TYPE.equals("chrome")) {
			_webDrivers.put(
				testName,
				new ChromeWebDriverImpl(portalURL, _getChromeDriver()));
		}
		else if (BROWSER_TYPE.equals("edge")) {
			if (SELENIUM_EDGE_DRIVER_EXECUTABLE != null) {
				System.setProperty(
					"webdriver.edge.driver",
					SELENIUM_EXECUTABLE_DIR_NAME +
						SELENIUM_EDGE_DRIVER_EXECUTABLE);
			}

			_webDrivers.put(
				testName, new EdgeWebDriverImpl(portalURL, _getEdgeDriver()));
		}
		else if (BROWSER_TYPE.equals("firefox")) {
			_webDrivers.put(
				testName,
				new FirefoxWebDriverImpl(portalURL, _getFirefoxDriver()));
		}
		else if (BROWSER_TYPE.equals("internetexplorer")) {
			if (SELENIUM_IE_DRIVER_EXECUTABLE != null) {
				System.setProperty(
					"webdriver.ie.driver",
					SELENIUM_EXECUTABLE_DIR_NAME +
						SELENIUM_IE_DRIVER_EXECUTABLE);
			}

			_webDrivers.put(
				testName,
				new InternetExplorerWebDriverImpl(
					portalURL, _getInternetExplorerDriver()));
		}
		else if (BROWSER_TYPE.equals("safari")) {
			_webDrivers.put(
				testName,
				new SafariWebDriverImpl(portalURL, _getSafariDriver()));
		}

		if (!_webDrivers.containsKey(testName)) {
			throw new RuntimeException("Invalid browser type " + BROWSER_TYPE);
		}

		LiferaySelenium liferaySelenium = (LiferaySelenium)_webDrivers.get(
			testName);

		liferaySelenium.setTestName(testName);
	}

	public static void stopWebDriver(String testName) {
		WebDriver webDriver = _webDrivers.get(testName);

		if (webDriver != null) {
			webDriver.quit();
		}

		_webDrivers.remove(testName);
	}

	private static WebDriver _getChromeDriver() {
		if (Validator.isNotNull(SELENIUM_REMOTE_DRIVER_URL)) {
			return _getChromeRemoteDriver();
		}

		_validateWebDriverBinary("webdriver.chrome.driver", "chromedriver");

		ChromeOptions chromeOptions = _getDefaultChromeOptions();

		if (Validator.isNotNull(PropsValues.BROWSER_CHROME_BIN_FILE)) {
			chromeOptions.setBinary(PropsValues.BROWSER_CHROME_BIN_FILE);
		}

		if (PropsValues.TEST_RUN_TYPE.equals("parallel")) {
			ChromeDriverService chromeDriverService =
				ChromeDriverService.createServiceWithConfig(chromeOptions);

			Thread thread = Thread.currentThread();

			chromeDriverService.sendOutputTo(
				ParallelPrintStream.getPrintStream(thread.getName()));

			return new ChromeDriver(chromeDriverService, chromeOptions);
		}

		return new ChromeDriver(chromeOptions);
	}

	private static WebDriver _getChromeRemoteDriver() {
		return _getRemoteWebDriver(_getDefaultChromeOptions());
	}

	private static ChromeOptions _getDefaultChromeOptions() {
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

		chromeOptions.addArguments("--remote-allow-origins=*");

		return chromeOptions;
	}

	private static InternetExplorerOptions
		_getDefaultInternetExplorerOptions() {

		InternetExplorerOptions internetExplorerOptions =
			new InternetExplorerOptions();

		_setGenericCapabilities(internetExplorerOptions);

		internetExplorerOptions.destructivelyEnsureCleanSession();
		internetExplorerOptions.introduceFlakinessByIgnoringSecurityDomains();

		return internetExplorerOptions;
	}

	private static WebDriver _getEdgeDriver() {
		if (Validator.isNotNull(SELENIUM_REMOTE_DRIVER_URL)) {
			return _getEdgeRemoteDriver();
		}

		return new EdgeDriver();
	}

	private static WebDriver _getEdgeRemoteDriver() {
		EdgeOptions edgeOptions = new EdgeOptions();

		_setGenericCapabilities(edgeOptions);

		edgeOptions.setCapability("platform", "WINDOWS");

		return _getRemoteWebDriver(edgeOptions);
	}

	private static WebDriver _getFirefoxDriver() {
		if (Validator.isNotNull(SELENIUM_REMOTE_DRIVER_URL)) {
			return _getFirefoxRemoteDriver();
		}

		_validateWebDriverBinary("webdriver.gecko.driver", "geckodriver");

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

		firefoxOptions.setCapability("marionette", true);

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

	private static WebDriver _getFirefoxRemoteDriver() {
		FirefoxOptions firefoxOptions = new FirefoxOptions();

		_setGenericCapabilities(firefoxOptions);

		return _getRemoteWebDriver(firefoxOptions);
	}

	private static WebDriver _getInternetExplorerDriver() {
		if (Validator.isNotNull(SELENIUM_REMOTE_DRIVER_URL)) {
			return _getInternetExplorerRemoteDriver();
		}

		return new InternetExplorerDriver(_getDefaultInternetExplorerOptions());
	}

	private static WebDriver _getInternetExplorerRemoteDriver() {
		InternetExplorerOptions internetExplorerOptions =
			_getDefaultInternetExplorerOptions();

		internetExplorerOptions.setCapability(
			"platform", PropsValues.SELENIUM_DESIRED_CAPABILITIES_PLATFORM);
		internetExplorerOptions.setCapability(
			"version", PropsValues.BROWSER_VERSION);

		return _getRemoteWebDriver(internetExplorerOptions);
	}

	private static RemoteWebDriver _getRemoteWebDriver(
		Capabilities capabilities) {

		RemoteWebDriver remoteWebDriver = new RemoteWebDriver(
			_REMOTE_DRIVER_URL, capabilities);

		remoteWebDriver.setFileDetector(new LocalFileDetector());

		return remoteWebDriver;
	}

	private static WebDriver _getSafariDriver() {
		if (Validator.isNotNull(SELENIUM_REMOTE_DRIVER_URL)) {
			return _getSafariRemoteDriver();
		}

		_setGenericCapabilities(new SafariOptions());

		return new SafariDriver();
	}

	private static WebDriver _getSafariRemoteDriver() {
		SafariOptions safariOptions = new SafariOptions();

		_setGenericCapabilities(safariOptions);

		return _getRemoteWebDriver(safariOptions);
	}

	private static void _setGenericCapabilities(
		MutableCapabilities mutableCapabilities) {

		mutableCapabilities.setCapability(
			CapabilityType.UNHANDLED_PROMPT_BEHAVIOUR, "ignore");

		for (Map.Entry<String, Object> entry :
				_genericCapabilities.entrySet()) {

			mutableCapabilities.setCapability(entry.getKey(), entry.getValue());
		}

		if (PropsValues.PROXY_SERVER_ENABLED) {
			mutableCapabilities.setCapability(
				CapabilityType.PROXY, ProxyUtil.getSeleniumProxy());
		}
	}

	private static void _validateWebDriverBinary(
		String webDriverBinaryPropertyName, String webDriverBinaryName) {

		if ((SELENIUM_EXECUTABLE_DIR_NAME != null) &&
			(SELENIUM_CHROME_DRIVER_EXECUTABLE != null)) {

			System.setProperty(
				webDriverBinaryPropertyName,
				SELENIUM_EXECUTABLE_DIR_NAME +
					SELENIUM_CHROME_DRIVER_EXECUTABLE);
		}

		String webDriverChromeDriverPath = System.getProperty(
			webDriverBinaryPropertyName);

		if (webDriverChromeDriverPath == null) {
			throw new RuntimeException(
				StringUtil.combine(
					"Please set the system property \"",
					webDriverBinaryPropertyName, "\" to a valid ",
					webDriverBinaryName, " binary"));
		}

		System.out.println(
			StringUtil.combine(
				"Using \"", webDriverChromeDriverPath, "\" as \"",
				webDriverBinaryPropertyName, "\" path"));
	}

	private static final URL _REMOTE_DRIVER_URL;

	private static final Map<String, Object> _genericCapabilities =
		new HashMap<String, Object>() {
			{
				if (PropsValues.PROXY_SERVER_ENABLED) {
					put(CapabilityType.ACCEPT_INSECURE_CERTS, true);
				}
			}
		};
	private static final Map<String, WebDriver> _webDrivers = new HashMap<>();

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

}