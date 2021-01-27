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

import com.liferay.jenkins.results.parser.AxisBuild;
import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.TestResult;
import com.liferay.jenkins.results.parser.spira.BaseSpiraArtifact;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseAxisSpiraTestResultDetails
	extends BaseSpiraTestResultDetails {

	protected BaseAxisSpiraTestResultDetails(
		AxisSpiraTestResult axisSpiraTestResult) {

		super(axisSpiraTestResult);

		_axisSpiraTestResult = axisSpiraTestResult;
	}

	protected String getTestFailuresSummary() {
		List<TestResult> failedTestResults =
			_axisSpiraTestResult.getFailedTestResults();

		if (failedTestResults == null) {
			failedTestResults = new ArrayList<>();
		}

		List<TestResult> commonFailedTestResults =
			_axisSpiraTestResult.getCommonFailedTestResults();

		if (commonFailedTestResults != null) {
			failedTestResults.addAll(commonFailedTestResults);
		}

		if (failedTestResults.isEmpty()) {
			return "";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("<h4>Test Failures</h4><ul>");

		for (TestResult failedTestResult : failedTestResults) {
			sb.append("<li><details><summary>");
			sb.append(failedTestResult.getTestName());
			sb.append("</summary><pre>");

			sb.append(
				BaseSpiraArtifact.fixStringForJSON(
					failedTestResult.getErrorDetails()));

			sb.append("\n");

			sb.append(
				BaseSpiraArtifact.fixStringForJSON(
					failedTestResult.getErrorStackTrace()));

			sb.append("</pre></details></li>");
		}

		sb.append("</ul>");

		return sb.toString();
	}

	@Override
	protected List<String> getTestrayListItems() {
		List<String> testrayListItems = new ArrayList<>();

		String artifactBaseURLContent = getArtifactBaseURLContent();

		if (artifactBaseURLContent.contains("liferay-log.txt.gz")) {
			testrayListItems.add(
				JenkinsResultsParserUtil.combine(
					"<li>Liferay Log: <a href=\"", getArtifactBaseURL(),
					"/liferay-log.txt.gz\" target=\"_blank\">",
					"liferay-log.txt</a></li>"));
		}

		for (int i = 1; i < 5; i++) {
			String liferayLogFileName = "liferay-log-" + i + ".txt";

			if (!artifactBaseURLContent.contains(liferayLogFileName)) {
				break;
			}

			testrayListItems.add(
				JenkinsResultsParserUtil.combine(
					"<li>Liferay Log (", String.valueOf(i), "): <a href=\"",
					getArtifactBaseURL(), "/", liferayLogFileName,
					".gz\" target=\"_blank\">", liferayLogFileName,
					"</a></li>"));
		}

		return testrayListItems;
	}

	@Override
	protected String getTestWarningsSummary() {
		AxisBuild axisBuild = _axisSpiraTestResult.getAxisBuild();

		if (axisBuild == null) {
			return "";
		}

		List<String> warningMessages = axisBuild.getWarningMessages();

		if ((warningMessages == null) || warningMessages.isEmpty()) {
			return "";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("<h4>Test Warnings</h4><ul>");

		for (int i = 0; i < warningMessages.size(); i++) {
			sb.append("<li><details><summary>Warning Message #");
			sb.append(i + 1);
			sb.append("</summary><pre>");
			sb.append(
				BaseSpiraArtifact.fixStringForJSON(warningMessages.get(i)));
			sb.append("</pre></details></li>");
		}

		sb.append("</ul>");

		return sb.toString();
	}

	private final AxisSpiraTestResult _axisSpiraTestResult;

}