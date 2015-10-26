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

package com.liferay.poshi.runner;

import com.liferay.poshi.runner.selenium.LiferaySeleniumHelper;
import com.liferay.poshi.runner.util.FileUtil;
import com.liferay.poshi.runner.util.PropsValues;

import java.io.BufferedReader;
import java.io.StringReader;

/**
 * @author Michael Hashimoto
 */
public class PoshiRunnerConsoleEvaluator {

	public static void main(String[] args) throws Exception {
		if (!FileUtil.exists(_TEST_CONSOLE_SHUT_DOWN_FILE_NAME)) {
			return;
		}

		StringBuilder sb = new StringBuilder();

		if (FileUtil.exists(_TEST_POSHI_WARNINGS_FILE_NAME)) {
			String poshiWarningsFileContent = FileUtil.read(
				_TEST_POSHI_WARNINGS_FILE_NAME);

			sb.append(poshiWarningsFileContent.trim());
		}

		String consoleShutDownFileContent = FileUtil.read(
			_TEST_CONSOLE_SHUT_DOWN_FILE_NAME);

		StringReader stringReader = new StringReader(
			consoleShutDownFileContent);

		BufferedReader bufferedReader = new BufferedReader(stringReader);

		String line = null;

		while ((line = bufferedReader.readLine()) != null) {
			if (LiferaySeleniumHelper.isIgnorableErrorLine(line)) {
				continue;
			}

			if (line.contains("ERROR") || line.contains("SEVERE")) {
				sb.append("\n<value><![CDATA[SHUT_DOWN_ERROR: ");
				sb.append(line.trim());
				sb.append("]]></value>");
			}
		}

		String poshiWarningsFileContent = sb.toString();

		FileUtil.write(
			_TEST_POSHI_WARNINGS_FILE_NAME, poshiWarningsFileContent.trim());
	}

	private static final String _TEST_CONSOLE_SHUT_DOWN_FILE_NAME =
		PropsValues.TEST_CONSOLE_SHUT_DOWN_FILE_NAME;

	private static final String _TEST_POSHI_WARNINGS_FILE_NAME =
		PropsValues.TEST_POSHI_WARNINGS_FILE_NAME;

}