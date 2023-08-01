/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.poshi.runner;

import com.liferay.poshi.core.PoshiProperties;
import com.liferay.poshi.core.util.FileUtil;
import com.liferay.poshi.runner.selenium.LiferaySeleniumUtil;

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
			if (LiferaySeleniumUtil.isIgnorableErrorLine(line)) {
				continue;
			}

			if (line.contains("ERROR") || line.contains("SEVERE")) {
				sb.append("\n<value><![CDATA[SHUT_DOWN_ERROR: ");
				sb.append(line.trim());
				sb.append("]]></value>");
			}
			else if (line.contains("WARN")) {
				sb.append("\n<value><![CDATA[SHUT_DOWN_WARNING: ");
				sb.append(line.trim());
				sb.append("]]></value>");
			}
		}

		String poshiWarningsFileContent = sb.toString();

		FileUtil.write(
			_TEST_POSHI_WARNINGS_FILE_NAME, poshiWarningsFileContent.trim());
	}

	private static final String _TEST_CONSOLE_SHUT_DOWN_FILE_NAME;

	private static final String _TEST_POSHI_WARNINGS_FILE_NAME;

	static {
		PoshiProperties poshiProperties = PoshiProperties.getPoshiProperties();

		_TEST_CONSOLE_SHUT_DOWN_FILE_NAME =
			poshiProperties.testLiferayConsoleShutDownFileName;

		_TEST_POSHI_WARNINGS_FILE_NAME =
			poshiProperties.testPoshiWarningsFileName;
	}

}