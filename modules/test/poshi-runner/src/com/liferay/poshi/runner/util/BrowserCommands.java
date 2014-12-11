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

package com.liferay.poshi.runner.util;

import org.openqa.selenium.WebDriver;

/**
 * @author Brian Wing Shun Chan
 * @author Michael Hashimoto
 */
public class BrowserCommands {

	public static void downloadTempFile(String value) {
		if (_SELENIUM_IMPLEMENTATION.equals(WebDriver.class.getName())) {
			if (_BROWSER_TYPE.equals("*chrome") ||
				_BROWSER_TYPE.equals("*firefox") ||
				_BROWSER_TYPE.equals("*googlechrome")) {

				return;
			}
		}

		try {
			Thread.sleep(5000);

			Runtime runtime = Runtime.getRuntime();

			String command = _BROWSER_COMMANDS_DIR_NAME + "download_file.exe";

			runtime.exec(command);

			Thread.sleep(30000);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void killBrowser() {
		try {
			Runtime runtime = Runtime.getRuntime();

			if (OSDetector.isWindows()) {
				runtime.exec(new String[] {"tskill", "firefox"});
			}
			else {
				runtime.exec(new String[] {"killall", "firefox"});
			}
		}
		catch (Exception e) {
		}
	}

	public static void setBrowserOption() {
		if (_SELENIUM_IMPLEMENTATION.equals(WebDriver.class.getName())) {
			if (_BROWSER_TYPE.equals("*chrome") ||
				_BROWSER_TYPE.equals("*firefox") ||
				_BROWSER_TYPE.equals("*googlechrome")) {

				return;
			}
		}

		try {
			Runtime runtime = Runtime.getRuntime();

			String[] commands = {
				_BROWSER_COMMANDS_DIR_NAME + "set_browser_option.exe",
				_OUTPUT_DIR_NAME
			};

			runtime.exec(commands);

			Thread.sleep(10000);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static final String _BROWSER_COMMANDS_DIR_NAME =
		PropsValues.BROWSER_COMMANDS_DIR_NAME;

	private static final String _BROWSER_TYPE = PropsValues.BROWSER_TYPE;

	private static final String _OUTPUT_DIR_NAME = PropsValues.OUTPUT_DIR_NAME;

	private static final String _SELENIUM_IMPLEMENTATION =
		PropsValues.SELENIUM_IMPLEMENTATION;

}