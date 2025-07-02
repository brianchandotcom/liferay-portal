/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.test.report.generator.csv.playwright;

/**
 * @author Davi Santos
 */
public class PlaywrightTestReport {

	public PlaywrightTestReport(
		String className, String testFilePath, String testName,
		boolean ignored) {

		_className = className;
		_testFilePath = testFilePath;
		_testName = testName;
		_ignored = ignored;
	}

	public String getClassName() {
		return _className;
	}

	public String getTestFilePath() {
		return _testFilePath;
	}

	public String getTestName() {
		return _testName;
	}

	public boolean isIgnored() {
		return _ignored;
	}

	private final String _className;
	private final boolean _ignored;
	private final String _testFilePath;
	private final String _testName;

}