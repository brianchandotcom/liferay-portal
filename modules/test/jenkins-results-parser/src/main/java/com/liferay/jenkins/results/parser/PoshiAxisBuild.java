/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.json.JSONObject;

/**
 * @author Kenji Heigel
 */
public class PoshiAxisBuild extends AxisBuild {

	@Override
	public List<TestResult> getTestResults() {
		String status = getStatus();

		if ((status == null) || !status.equals("completed")) {
			return Collections.emptyList();
		}

		List<TestResult> testResults = new ArrayList<>();

		String result = getResult();

		if (result.equals("SUCCESS") || result.equals("UNSTABLE")) {
			testResults.addAll(super.getTestResults());
		}

		List<String> existingTestNames = new ArrayList<>();

		for (TestResult testResult : testResults) {
			String testName = testResult.getTestName();

			String testNameRegex = "test\\[([^\\]]+)\\]";

			if (!testName.matches(testNameRegex)) {
				continue;
			}

			existingTestNames.add(testName.replaceAll(testNameRegex, "$1"));
		}

		for (String poshiTestName : _getPoshiTestNames()) {
			if (existingTestNames.contains(poshiTestName)) {
				continue;
			}

			JSONObject caseJSONObject = new JSONObject();

			caseJSONObject.put(
				"className", "com.liferay.poshi.runner.PoshiRunner"
			).put(
				"duration", getDuration()
			).put(
				"errorDetails", "The build failed prior to running the test."
			).put(
				"errorStackTrace", ""
			).put(
				"name", "test[" + poshiTestName + "]"
			).put(
				"status", "FAILED"
			);

			testResults.add(
				TestResultFactory.newTestResult(this, caseJSONObject));
		}

		return testResults;
	}

	protected PoshiAxisBuild(String url) {
		this(url, null);
	}

	protected PoshiAxisBuild(String url, BatchBuild parentBatchBuild) {
		super(url, parentBatchBuild);
	}

	private List<String> _getPoshiTestNames() {
		List<String> poshiTestNames = new ArrayList<>();

		if (fromArchive) {
			return poshiTestNames;
		}

		BuildDatabase buildDatabase = BuildDatabaseUtil.getBuildDatabase(
			getTopLevelBuild());

		Properties startProperties = buildDatabase.getProperties(
			getJobVariant() + "/start.properties");

		String runTestCaseMethodGroup = JenkinsResultsParserUtil.getProperty(
			startProperties, "RUN_TEST_CASE_METHOD_GROUP");

		String poshiTestNamesKey = JenkinsResultsParserUtil.combine(
			"RUN_TEST_CASE_METHOD_GROUP_", runTestCaseMethodGroup, "_",
			getAxisNumber());

		String poshiTestNamesString = JenkinsResultsParserUtil.getProperty(
			startProperties, poshiTestNamesKey);

		if ((poshiTestNamesString != null) && !poshiTestNamesString.isEmpty()) {
			Collections.addAll(poshiTestNames, poshiTestNamesString.split(","));
		}

		return poshiTestNames;
	}

}