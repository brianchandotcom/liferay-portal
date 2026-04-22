/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

/**
 * @author Michael Hashimoto
 */
public class TestReportFailureReport extends BaseFailureReport {

	@Override
	public String getMessage() {
		return JenkinsResultsParserUtil.combine(
			"Error Details: ", _testReport.getErrorDetails(),
			"\n\nError Stack Trace", _testReport.getErrorStackTrace());
	}

	protected TestReportFailureReport(
		BuildReport buildReport, TestReport testReport) {

		super(buildReport);

		_testReport = testReport;
	}

	private final TestReport _testReport;

}