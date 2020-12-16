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

package com.liferay.jenkins.results.parser.spira.result;

import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.TestClassResult;
import com.liferay.jenkins.results.parser.TestResult;

import java.util.List;

/**
 * @author Michael Hashimoto
 */
public class JUnitAxisSpiraTestResultDetails
	extends BaseAxisSpiraTestResultDetails {

	protected JUnitAxisSpiraTestResultDetails(
		JUnitAxisSpiraTestResult jUnitAxisSpiraTestResult) {

		super(jUnitAxisSpiraTestResult);

		_jUnitAxisSpiraTestResult = jUnitAxisSpiraTestResult;
	}

	protected String getTestMethodsSummary() {
		TestClassResult testClassResult =
			_jUnitAxisSpiraTestResult.getTestClassResult();

		if (testClassResult == null) {
			return "";
		}

		List<TestResult> testResults = testClassResult.getTestResults();

		if ((testResults == null) || testResults.isEmpty()) {
			return "";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("<h4>Test Methods - ");

		sb.append(
			JenkinsResultsParserUtil.toDurationString(
				testClassResult.getDuration()));

		sb.append(" - ");
		sb.append(testClassResult.getStatus());
		sb.append("</h4><ul>");

		for (TestResult testResult : testResults) {
			sb.append("<li>");
			sb.append(testResult.getTestName());
			sb.append(" - ");

			sb.append(
				JenkinsResultsParserUtil.toDurationString(
					testResult.getDuration()));

			sb.append(" - ");
			sb.append(testResult.getStatus());
			sb.append("</li>");
		}

		sb.append("</ul>");

		return sb.toString();
	}

	private final JUnitAxisSpiraTestResult _jUnitAxisSpiraTestResult;

}