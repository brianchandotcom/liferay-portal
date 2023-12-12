/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

import java.io.File;

import java.net.URL;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Charlotte Wong
 * @author Kyle Miho
 */
public class GenerateTestrayCSVUtil {

	public static void generate(
			String projectBuildDir, String projectTestrayBuildId)
		throws Exception {

		StringBuilder sb = new StringBuilder();

		sb.append("Case Name,Case History URL,Error Message\n");

		StringBuilder didNotRunFailuresStringBuilder = new StringBuilder();
		StringBuilder uniqueFailuresStringBuilder = new StringBuilder();
		StringBuilder upstreamFailuresStringBuilder = new StringBuilder();

		List<TestcaseResult> allTestcases = _getTestcaseResults(
			projectTestrayBuildId);

		for (TestcaseResult testcaseResult : allTestcases) {
			List<TestcaseResult> testcaseHistory = _getTestcaseResultHistory(
				testcaseResult);

			FailureStatus failureStatus = _getFailureType(
				testcaseResult, testcaseHistory);

			if (failureStatus == FailureStatus.COMMON) {
				System.out.println(
					"--- We think that " +
						testcaseResult._getTestrayCaseName() +
							" is a common failure---");

				upstreamFailuresStringBuilder.append(
					testcaseResult._getTestrayCaseName());
				upstreamFailuresStringBuilder.append(",");
				upstreamFailuresStringBuilder.append(
					testcaseResult._getHistoryURL());
				upstreamFailuresStringBuilder.append(",");
				upstreamFailuresStringBuilder.append(
					testcaseResult._getErrorMessage());
				upstreamFailuresStringBuilder.append("\n");
			}
			else if (failureStatus == FailureStatus.DID_NOT_RUN) {
				System.out.println(
					"--- We think that " +
						testcaseResult._getTestrayCaseName() +
							" failed to run---");

				didNotRunFailuresStringBuilder.append(
					testcaseResult._getTestrayCaseName());
				didNotRunFailuresStringBuilder.append(",");
				didNotRunFailuresStringBuilder.append(
					testcaseResult._getHistoryURL());
				didNotRunFailuresStringBuilder.append(",");
				didNotRunFailuresStringBuilder.append(
					testcaseResult._getErrorMessage());
				didNotRunFailuresStringBuilder.append("\n");
			}
			else if (failureStatus == FailureStatus.UNIQUE) {
				System.out.println(
					"--- We think that " +
						testcaseResult._getTestrayCaseName() +
							" is a unique failure---");

				uniqueFailuresStringBuilder.append(
					testcaseResult._getTestrayCaseName());
				uniqueFailuresStringBuilder.append(",");
				uniqueFailuresStringBuilder.append(
					testcaseResult._getHistoryURL());
				uniqueFailuresStringBuilder.append(",");
				uniqueFailuresStringBuilder.append(
					testcaseResult._getErrorMessage());
				uniqueFailuresStringBuilder.append("\n");
			}
		}

		sb.append("Unique failures\n");
		sb.append(uniqueFailuresStringBuilder.toString());
		sb.append("\n");
		sb.append("Upstream failures\n");
		sb.append(upstreamFailuresStringBuilder.toString());

		try {
			JenkinsResultsParserUtil.write(
				new File(projectBuildDir, "testray-results.csv"),
				sb.toString());
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	private static boolean _areSimilarErrors(String error1, String error2) {
		Double distance = StringUtils.getJaroWinklerDistance(error1, error2);

		if (distance > 0.8) {
			return true;
		}

		return false;
	}

	private static FailureStatus _getFailureType(
		TestcaseResult testcaseResult,
		List<TestcaseResult> testcaseResultsHistory) {

		if (Objects.equals(
				testcaseResult._getErrorMessage(),
				"Failed prior to running test")) {

			return FailureStatus.DID_NOT_RUN;
		}

		for (TestcaseResult historyTestcaseResult : testcaseResultsHistory) {
			if (Objects.equals(
					testcaseResult._getTestrayRunId(),
					historyTestcaseResult._getTestrayRunId())) {

				continue;
			}

			if (_areSimilarErrors(
					testcaseResult._getErrorMessage(),
					historyTestcaseResult._getErrorMessage()) &&
				(_getPRAuthor(testcaseResult) != _getPRAuthor(
					historyTestcaseResult))) {

				return FailureStatus.COMMON;
			}
		}

		return FailureStatus.UNIQUE;
	}

	private static String _getPRAuthor(TestcaseResult testcaseResult) {
		PullRequest pullRequest = testcaseResult._getPullRequestObject();

		if (pullRequest == null) {
			return null;
		}

		return pullRequest._getAuthor();
	}

	private static PullRequest _getPullRequest(String testrayBuildReportURL)
		throws Exception {

		String slaveName = "";
		String batchName = "";
		String batchNumber = "";

		String[] buildReportText = testrayBuildReportURL.split("/");

		try {
			slaveName = buildReportText[1];
			batchName = buildReportText[2];
			batchNumber = buildReportText[3];
		}
		catch (Exception exception) {
			System.out.println("Exception: " + exception);
		}

		String tempURL =
			"https://" + slaveName + ".liferay.com/job/" + batchName + "/" +
				batchNumber;

		TopLevelBuildReport topLevelBuildReport =
			BuildReportFactory.newTopLevelBuildReport(new URL(tempURL));

		Map<String, String> buildParameters =
			topLevelBuildReport.getBuildParameters();

		return new PullRequest(
			buildParameters.get("GITHUB_SENDER_USERNAME"),
			buildParameters.get("GITHUB_PULL_REQUEST_NUMBER"));
	}

	private static List<TestcaseResult> _getTestcaseResultHistory(
			TestcaseResult testcaseResult)
		throws Exception {

		JSONObject jsonObject = JenkinsResultsParserUtil.toJSONObject(
			"https://testray.liferay.com/home/-/testray/case_results/" +
				testcaseResult._getTestrayCaseResultId() + "/history.json");

		JSONArray resultsJSONArray = jsonObject.optJSONArray("data");

		if ((resultsJSONArray == null) || (resultsJSONArray.length() == 0)) {
			return null;
		}

		List<TestcaseResult> testcaseHistory = new ArrayList<>();

		JSONObject resultJSONObject;

		for (int i = 0; i < resultsJSONArray.length(); i++) {
			resultJSONObject = resultsJSONArray.optJSONObject(i);

			if (resultJSONObject == null) {
				continue;
			}

			testcaseHistory.add(new TestcaseResult(resultJSONObject));
		}

		return testcaseHistory;
	}

	private static List<TestcaseResult> _getTestcaseResults(
		String projectTestrayBuildId) {

		List<TestcaseResult> resultTestcases = new ArrayList<>();

		int currentPage = 1;
		long previousTestrayCaseResultId = 0;

		while (true) {
			try {
				JSONObject jsonObject = JenkinsResultsParserUtil.toJSONObject(
					"https://testray.liferay.com/home/-/testray" +
						"/case_results.json?cur=" + currentPage +
							"&testrayBuildId=" + projectTestrayBuildId +
								"&statuses=3");

				JSONArray resultsJSONArray = jsonObject.optJSONArray("data");

				if ((resultsJSONArray == null) ||
					(resultsJSONArray.length() == 0)) {

					break;
				}

				JSONObject resultJSONObject = resultsJSONArray.getJSONObject(0);

				long currentTestrayCaseResultId = Long.valueOf(
					resultJSONObject.getString("testrayCaseResultId"));

				if (currentTestrayCaseResultId == previousTestrayCaseResultId) {
					break;
				}

				for (int i = 0; i < resultsJSONArray.length(); i++) {
					resultJSONObject = resultsJSONArray.optJSONObject(i);

					if (resultJSONObject == null) {
						continue;
					}

					TestcaseResult testcaseResult = new TestcaseResult(
						resultJSONObject);

					if (_isTestCaseResult(testcaseResult)) {
						resultTestcases.add(testcaseResult);
					}
				}

				currentPage++;

				previousTestrayCaseResultId = currentTestrayCaseResultId;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		}

		return resultTestcases;
	}

	private static boolean _isTestCaseResult(TestcaseResult testcaseResult) {
		String testcaseResultName = testcaseResult._getTestrayCaseName();

		if (testcaseResultName.contains("Top Level Build")) {
			return false;
		}

		return true;
	}

	private static class PullRequest {

		public String toString() {
			return "### PullRequest ###\nAuthor: " + _author + "\n" +
				"PullRequest: " + _pullRequestNumber + "####\n";
		}

		private PullRequest(String author, String pullRequestNumber) {
			_author = author;
			_pullRequestNumber = Integer.valueOf(pullRequestNumber);
		}

		private String _getAuthor() {
			return _author;
		}

		private final String _author;
		private final int _pullRequestNumber;

	}

	private static class TestcaseResult {

		public String toString() {
			return "### TestcaseResult ###\nPRINFO: " +
				_getPullRequestObject() + "\nTestrayCaseName: " +
					_testrayCaseName + "\nTestrayCaseResultId: " +
						_testrayCaseResultId + "\nTestrayRunId: " +
							_testrayRunId + "\n";
		}

		private TestcaseResult(JSONObject resultJSONObject) {
			_resultJSONObject = resultJSONObject;

			_testrayCaseName = resultJSONObject.getString("testrayCaseName");
			_errorMessage = resultJSONObject.getString("errors");
			_historyURL = resultJSONObject.getString("htmlURL") + "/history";
			_pullRequest = null;

			String temp = resultJSONObject.getString("testrayCaseResultId");

			temp = temp.replace("\"", "");

			_testrayCaseResultId = Long.parseLong(temp);

			temp = resultJSONObject.getString("testrayRunId");
			temp = temp.replace("\"", "");

			_testrayRunId = Long.parseLong(temp);
		}

		private String _getErrorMessage() {
			return _errorMessage;
		}

		private String _getHistoryURL() {
			return _historyURL;
		}

		private PullRequest _getPullRequestObject() {
			if (_pullRequest == null) {
				JSONObject jsonObject = _resultJSONObject.getJSONObject(
					"attachments");

				try {
					_pullRequest = _getPullRequest(
						jsonObject.getString("Build Report (Top Level)"));
				}
				catch (Exception exception) {
					System.out.println(exception);
				}
			}

			return _pullRequest;
		}

		private String _getTestrayCaseName() {
			return _testrayCaseName;
		}

		private long _getTestrayCaseResultId() {
			return _testrayCaseResultId;
		}

		private long _getTestrayRunId() {
			return _testrayRunId;
		}

		private final String _errorMessage;
		private final String _historyURL;
		private PullRequest _pullRequest;
		private final JSONObject _resultJSONObject;
		private final String _testrayCaseName;
		private final long _testrayCaseResultId;
		private final long _testrayRunId;

	}

	private enum FailureStatus {

		COMMON, DID_NOT_RUN, UNIQUE

	}

}