/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.osb.faro.functional.test.util;

import com.cucumber.listener.ExtentCucumberFormatter;

import com.liferay.osb.faro.functional.test.driver.FaroSelenium;
import com.liferay.osb.faro.functional.test.driver.FaroWebDriver;
import com.liferay.poshi.core.util.PropsValues;
import com.liferay.poshi.core.util.StringPool;
import com.liferay.poshi.runner.selenium.WebDriverUtil;

import java.io.File;
import java.io.IOException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Date;

/**
 * @author Cheryl Tang
 */
public class FaroSeleniumUtil {

	public static void createReport() {
		File reportFile = new File("build/reports/cucumber/index.html");

		ExtentCucumberFormatter.initiateExtentCucumberFormatter(
			reportFile, false);
	}

	public static synchronized File getDependenciesDir() throws IOException {
		if (_dependenciesDir != null) {
			return _dependenciesDir;
		}

		_dependenciesDir = new File("test-classes/functional/dependencies/");

		if (!_dependenciesDir.exists() && !_dependenciesDir.mkdirs()) {
			throw new IOException("Unable to get dependencies dir");
		}

		return _dependenciesDir;
	}

	public static FaroSelenium getFaroSelenium() {
		return _faroSeleniumUtil._getFaroSelenium();
	}

	public static synchronized File getScreenshotsDir() throws IOException {
		if (_screenshotsDir != null) {
			return _screenshotsDir;
		}

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");

		_screenshotsDir = new File(
			"test-results/functional/screenshots/" +
				dateFormat.format(new Date()));

		if (!_screenshotsDir.exists() && !_screenshotsDir.mkdirs()) {
			throw new IOException("Unable to get screenshots dir");
		}

		return _screenshotsDir;
	}

	public static void startSelenium() {
		_faroSeleniumUtil._startFaroSelenium();
	}

	private FaroSelenium _getFaroSelenium() {
		if (_faroSelenium == null) {
			_startFaroSelenium();
		}

		return _faroSelenium;
	}

	private void _startFaroSelenium() {
		_faroSelenium = new FaroWebDriver(
			PropsValues.PORTAL_URL, WebDriverUtil.getWebDriver(StringPool.BLANK));
	}

	private static File _dependenciesDir;
	private static FaroSelenium _faroSelenium;
	private static final FaroSeleniumUtil _faroSeleniumUtil =
		new FaroSeleniumUtil();
	private static File _screenshotsDir;

}