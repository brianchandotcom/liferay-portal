/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

import java.io.File;
import java.io.IOException;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.ArrayList;
import java.util.Arrays;
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
		String projectBuildDir, String projectTestrayBuildId) {

		StringBuilder sb = new StringBuilder();

		sb.append(
			JenkinsResultsParserUtil.join(
				_CSV_DELIMITER, "Case Name", "Case History URL",
				"Error Message", "Failure Type"));
		sb.append("\n");

		List<TestrayCaseResult> allTestrayCaseResults = _getTestrayCaseResults(
			projectTestrayBuildId);

		sb.append(
			_generate(allTestrayCaseResults, TestrayCaseResult.Type.UNIQUE));

		sb.append(
			_generate(
				allTestrayCaseResults, TestrayCaseResult.Type.DID_NOT_RUN));

		sb.append(
			_generate(allTestrayCaseResults, TestrayCaseResult.Type.COMMON));

		try {
			JenkinsResultsParserUtil.write(
				new File(projectBuildDir, "testray-results.csv"),
				sb.toString());
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private static String _generate(
		List<TestrayCaseResult> allTestrayCaseResults,
		TestrayCaseResult.Type testrayCaseResultType) {

		StringBuilder sb = new StringBuilder();

		for (TestrayCaseResult testrayCaseResult : allTestrayCaseResults) {
			if (testrayCaseResultType != testrayCaseResult.getType()) {
				continue;
			}

			sb.append(testrayCaseResult.generateCSV());
			sb.append("\n");
		}

		if (sb.length() == 0) {
			sb.append(
				JenkinsResultsParserUtil.join(
					_CSV_DELIMITER, "NONE", "N/A", "N/A",
					testrayCaseResultType.toString()));
			sb.append("\n");
		}

		return sb.toString();
	}

	private static List<TestrayCaseResult> _getTestrayCaseResults(
		String projectTestrayBuildId) {

		List<TestrayCaseResult> testrayCaseResults = new ArrayList<>();

		int currentPage = 1;
		long previousTestrayCaseResultId = 0;

		while (true) {
			String testrayCaseResultsURL = JenkinsResultsParserUtil.combine(
				"https://testray.liferay.com/home/-/testray",
				"/case_results.json?cur=", String.valueOf(currentPage),
				"&testrayBuildId=", projectTestrayBuildId, "&statuses=3");

			JSONObject jsonObject = null;

			try {
				jsonObject = JenkinsResultsParserUtil.toJSONObject(
					testrayCaseResultsURL);
			}
			catch (IOException ioException) {
				throw new RuntimeException(ioException);
			}

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

				TestrayCaseResult testrayCaseResult = new TestrayCaseResult(
					resultJSONObject);

				if (!testrayCaseResult.isTopLevelBuildResult()) {
					testrayCaseResults.add(testrayCaseResult);
				}
			}

			currentPage++;

			previousTestrayCaseResultId = currentTestrayCaseResultId;
		}

		return testrayCaseResults;
	}

	private static final String _CSV_DELIMITER = ",";

	private static final List<String> _didNotRunErrorMessages = Arrays.asList(
		"Aborted prior to running test", "Failed prior to running test",
		"Failed for unknown reason");

	private static class TestrayCaseResult {

		public TestrayCaseResult(JSONObject resultJSONObject) {
			_resultJSONObject = resultJSONObject;
		}

		public String generateCSV() {
			return JenkinsResultsParserUtil.join(
				_CSV_DELIMITER, _cleanCSVData(getTestrayCaseName()),
				_cleanCSVData(getHistoryURL()),
				_cleanCSVData(getErrorMessage()),
				_cleanCSVData(getType().toString()));
		}

		public String getErrorMessage() {
			return _resultJSONObject.getString("errors");
		}

		public String getHistoryURL() {
			return _resultJSONObject.getString("htmlURL") + "/history";
		}

		public String getPullRequestAuthor() {
			if (!JenkinsResultsParserUtil.isNullOrEmpty(_pullRequestAuthor)) {
				return _pullRequestAuthor;
			}

			String testrayBuildReportURL = getTestrayBuildReportURL();

			if (testrayBuildReportURL.isEmpty()) {
				_pullRequestAuthor = "Unknown";

				return _pullRequestAuthor;
			}

			String[] buildReportText = testrayBuildReportURL.split("/");

			URL topLevelBuildReportURL = null;

			try {
				topLevelBuildReportURL = new URL(
					JenkinsResultsParserUtil.combine(
						"https://", buildReportText[1], ".liferay.com/job/",
						buildReportText[2], "/", buildReportText[3]));
			}
			catch (MalformedURLException malformedURLException) {
				throw new RuntimeException(malformedURLException);
			}

			try {
				TopLevelBuildReport topLevelBuildReport =
					BuildReportFactory.newTopLevelBuildReport(
						topLevelBuildReportURL);

				Map<String, String> buildParameters =
					topLevelBuildReport.getBuildParameters();

				_pullRequestAuthor = buildParameters.get(
					"GITHUB_SENDER_USERNAME");
			}
			catch (Exception exception) {
				_pullRequestAuthor = "Unknown";
			}

			return _pullRequestAuthor;
		}

		public String getTestrayBuildReportURL() {
			JSONObject jsonObject = _resultJSONObject.getJSONObject(
				"attachments");

			return jsonObject.optString("Build Report (Top Level)");
		}

		public String getTestrayCaseName() {
			return _resultJSONObject.getString("testrayCaseName");
		}

		public List<TestrayCaseResult> getTestrayCaseResultHistory() {
			JSONObject jsonObject = null;

			try {
				jsonObject = JenkinsResultsParserUtil.toJSONObject(
					"https://testray.liferay.com/home/-/testray/case_results/" +
						getTestrayCaseResultId() + "/history.json");
			}
			catch (IOException ioException) {
				throw new RuntimeException(ioException);
			}

			JSONArray resultsJSONArray = jsonObject.optJSONArray("data");

			if ((resultsJSONArray == null) ||
				(resultsJSONArray.length() == 0)) {

				return null;
			}

			List<TestrayCaseResult> testrayCaseResultHistory =
				new ArrayList<>();

			JSONObject resultJSONObject;

			for (int i = 0; i < resultsJSONArray.length(); i++) {
				resultJSONObject = resultsJSONArray.optJSONObject(i);

				if (resultJSONObject == null) {
					continue;
				}

				testrayCaseResultHistory.add(
					new TestrayCaseResult(resultJSONObject));
			}

			return testrayCaseResultHistory;
		}

		public long getTestrayCaseResultId() {
			return _resultJSONObject.getLong("testrayCaseResultId");
		}

		public long getTestrayRunId() {
			return _resultJSONObject.getLong("testrayRunId");
		}

		public Type getType() {
			if (_type != null) {
				return _type;
			}

			if (_didNotRunErrorMessages.contains(getErrorMessage())) {
				_type = Type.DID_NOT_RUN;
			}
			else {
				for (TestrayCaseResult historyTestrayCaseResult :
						getTestrayCaseResultHistory()) {

					if (Objects.equals(
							getTestrayRunId(),
							historyTestrayCaseResult.getTestrayRunId())) {

						continue;
					}

					if (isSimilarError(historyTestrayCaseResult) &&
						!Objects.equals(
							getPullRequestAuthor(),
							historyTestrayCaseResult.getPullRequestAuthor())) {

						_type = Type.COMMON;

						break;
					}
				}
			}

			if (_type == null) {
				_type = Type.UNIQUE;
			}

			return _type;
		}

		public boolean isSimilarError(
			TestrayCaseResult otherTestrayCaseResult) {

			return _isWithinJaroWinklerDistance(
				getErrorMessage(), otherTestrayCaseResult.getErrorMessage(),
				0.8F);
		}

		public boolean isTopLevelBuildResult() {
			String testrayCaseName = getTestrayCaseName();

			if (testrayCaseName.contains("Top Level Build")) {
				return true;
			}

			return false;
		}

		public enum Type {

			COMMON("Common"), DID_NOT_RUN("Did not run"), UNIQUE("Unique");

			@Override
			public String toString() {
				return _description;
			}

			private Type(String description) {
				_description = description;
			}

			private final String _description;

		}

		private String _cleanCSVData(String string) {
			return string.replace(_CSV_DELIMITER, ".");
		}

		private boolean _isWithinJaroWinklerDistance(
			String string1, String string2, double maxDistance) {

			try {
				Double distance = StringUtils.getJaroWinklerDistance(
					string1, string2);

				if (distance > maxDistance) {
					return true;
				}

				return false;
			}
			catch (IllegalArgumentException illegalArgumentException) {
				if (string1 == string2) {
					return true;
				}

				return false;
			}
		}

		private String _pullRequestAuthor;
		private final JSONObject _resultJSONObject;
		private Type _type;

	}

}