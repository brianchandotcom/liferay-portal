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

package com.liferay.portalweb.portal.util.liferayselenium;

import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portalweb.portal.util.RuntimeVariables;
import com.liferay.portalweb.portal.util.TestPropsValues;

import com.thoughtworks.selenium.CommandProcessor;
import com.thoughtworks.selenium.Selenium;

import java.lang.reflect.Field;

/**
 * @author Brian Wing Shun Chan
 */
public abstract class BaseSeleniumImpl
	extends SeleniumWrapper implements LiferaySelenium {

	public BaseSeleniumImpl(String projectDir, Selenium selenium) {
		super(selenium);

		_projectDir = projectDir;

		initCommandProcessor();

		selenium.start();
	}

	public void downloadTempFile(String value) {
		LiferaySeleniumHelper.downloadTempFile(value);
	}

	public String getCurrentDay() {
		return _commandProcessor.getString("getCurrentDay", new String[0]);
	}

	public String getCurrentMonth() {
		return _commandProcessor.getString("getCurrentMonth", new String[0]);
	}

	public String getCurrentYear() {
		return _commandProcessor.getString("getCurrentYear", new String[0]);
	}

	public String getFirstNumber(String locator) {
		return _commandProcessor.getString(
			"getFirstNumber", new String[] {locator,});
	}

	public String getFirstNumberIncrement(String locator) {
		return _commandProcessor.getString(
			"getFirstNumberIncrement", new String[] {locator,});
	}

	public boolean isElementNotPresent(String locator) {
		return !isElementPresent(locator);
	}

	public boolean isPartialText(String locator, String value) {
		value = RuntimeVariables.replace(value);

		return _commandProcessor.getBoolean(
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
		LiferaySeleniumHelper.setBrowserOption();
	}

	@Override
	public void setTimeout(String timeout) {
		super.setTimeout(timeout);

		_timeout = timeout;
	}

	public void uploadCommonFile(String location, String value) {
		super.type(
			location,
			_projectDir + "portal-web\\test\\com\\liferay\\portalweb\\" +
				"dependencies\\" + value);
	}

	public void uploadFile(String location, String value) {
		super.type(location, value);
	}

	public void uploadTempFile(String location, String value) {
		super.type(location, TestPropsValues.OUTPUT_DIR + value);
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

				String dirName = className.substring(22);

				dirName = StringUtil.replace(dirName, ".", "/") + "/";

				String fileName = stackTraceElement.getFileName();
				int lineNumber = stackTraceElement.getLineNumber();

				FileUtil.mkdirs(_OUTPUT_SCREENSHOTS_DIR + dirName);

				return dirName + fileName + "-" + lineNumber;
			}
		}

		throw new RuntimeException("Unable to find screenshot file name");
	}

	protected void initCommandProcessor() {
		try {
			Selenium selenium = getWrappedSelenium();

			Class<?> clazz = selenium.getClass();

			Field field = clazz.getDeclaredField("commandProcessor");

			field.setAccessible(true);

			_commandProcessor = (CommandProcessor)field.get(selenium);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static final String _OUTPUT_SCREENSHOTS_DIR =
		TestPropsValues.OUTPUT_DIR + "screenshots/";

	private CommandProcessor _commandProcessor;
	private String _projectDir;
	private String _timeout = "90000";

}