/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StringUtil;

import com.thoughtworks.selenium.CommandProcessor;
import com.thoughtworks.selenium.DefaultSelenium;

/**
 * @author Brian Wing Shun Chan
 */
public class LiferayDefaultSelenium
	extends DefaultSelenium implements LiferaySelenium {

	public LiferayDefaultSelenium(CommandProcessor processor) {
		super(processor);
	}

	public LiferayDefaultSelenium(
		String serverHost, int serverPort, String browserStartCommand,
		String browserURL) {

		super(serverHost, serverPort, browserStartCommand, browserURL);
	}

	public void downloadFile(String value) {
		if (!_BROWSER_TYPE.equals("*chrome") &&
			!_BROWSER_TYPE.equals("*firefox") &&
			!_BROWSER_TYPE.equals("*iehta") &&
			!_BROWSER_TYPE.equals("*iexplore")) {

			return;
		}

		try {
			String[] commands = {
				RuntimeVariables.replace(
					_SELENIUM_EXECUTABLE_DIR +
						TestPropsValues.SELENIUM_DOWNLOAD_FILE),
				TestPropsValues.OUTPUT_DIR + value
			};

			Runtime runtime = Runtime.getRuntime();

			Thread.sleep(1000);

			runtime.exec(commands);

			Thread.sleep(10000);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getCurrentDay() {
		return commandProcessor.getString(
			"getCurrentDay", new String[0]);
	}

	public String getCurrentMonth() {
		return commandProcessor.getString(
			"getCurrentMonth", new String[0]);
	}

	public String getCurrentYear() {
		return commandProcessor.getString(
			"getCurrentYear", new String[0]);
	}

	public String getFirstNumber(String locator) {
		return commandProcessor.getString(
			"getFirstNumber", new String[] {locator,});
	}

	public String getFirstNumberIncrement(String locator) {
		return commandProcessor.getString(
			"getFirstNumberIncrement", new String[] {locator,});
	}

	public boolean isPartialText(String locator, String value) {
		return commandProcessor.getBoolean(
			"isPartialText", new String[] {locator, value,});
	}

	public void saveScreenShotAndSource() throws Exception {
		String screenShotName = null;

		if (TestPropsValues.SAVE_SCREENSHOT) {
			screenShotName = getScreenshotFileName();

			captureEntirePageScreenshot(
				_OUTPUT_SCREENSHOTS_DIR + screenShotName + ".jpg", "");
		}

		if (TestPropsValues.SAVE_SOURCE) {
			String content = getHtmlSource();

			screenShotName = getScreenshotFileName();

			FileUtil.write(
				_OUTPUT_SCREENSHOTS_DIR + screenShotName + ".html", content);
		}
	}

	public void setBrowserOption() {
		if (!_BROWSER_TYPE.equals("*chrome") &&
			!_BROWSER_TYPE.equals("*firefox")) {

			return;
		}

		try {
			String command = RuntimeVariables.replace(
				_SELENIUM_EXECUTABLE_DIR +
					TestPropsValues.SELENIUM_SET_BROWSER_OPTION);

			Runtime runtime = Runtime.getRuntime();

			runtime.exec(command);

			Thread.sleep(10000);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void setTimeout(String timeout) {
		super.setTimeout(timeout);

		_timeout = timeout;
	}

	public void uploadFile(String location, String value) {
		String text = TestPropsValues.OUTPUT_DIR + value;

		super.type(location, text);
	}

	@Override
	public void waitForPageToLoad(String timeout) {
		super.waitForPageToLoad(_timeout);
	}

	protected String getScreenshotFileName() {
		Thread currentThread = Thread.currentThread();

		StackTraceElement[] stackTraceElements = currentThread.getStackTrace();

		for (int i = 1; i < stackTraceElements.length; i++) {
			StackTraceElement stackTraceElement = stackTraceElements[i];

			String className = stackTraceElement.getClassName();

			if ((className.startsWith("com.liferay.portalweb.plugins") ||
				 className.startsWith("com.liferay.portalweb.portal") ||
				 className.startsWith("com.liferay.portalweb.portlet") ||
				 className.startsWith("com.liferay.portalweb.properties")) &&
				 className.endsWith("Test")) {

				String dirName = className.substring(
					22, className.length());

				dirName = StringUtil.replace(dirName, ".", "/") + "/";

				String fileName = stackTraceElement.getFileName();
				int lineNumber = stackTraceElement.getLineNumber();

				FileUtil.mkdirs(_OUTPUT_SCREENSHOTS_DIR + dirName);

				return dirName + fileName + "-" + lineNumber;
			}
		}

		throw new RuntimeException("Unable to find screenshot file name");
	}

	private static final String _BROWSER_TYPE = TestPropsValues.BROWSER_TYPE;

	private static final String _OUTPUT_SCREENSHOTS_DIR =
		TestPropsValues.OUTPUT_DIR + "screenshots/";

	private static final String _SELENIUM_EXECUTABLE_DIR =
		TestPropsValues.SELENIUM_EXECUTABLE_DIR;

	private String _timeout = "90000";

}