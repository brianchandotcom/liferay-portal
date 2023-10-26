/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

import java.io.File;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Charlotte Wong
 * @author Kyle Miho
 */
public class GenerateTestrayCsvUtil {

	public void generate(String projectBuildDir, String projectTestrayBuildId) {
		StringBuilder sb = new StringBuilder();
		StringBuilder uniqueFailuresStringBuilder = new StringBuilder();
		StringBuilder upstreamFailuresStringBuilder = new StringBuilder();

		sb.append("Case Name,Component Name,Team Name,");

		sb.append("Recent Failures Count,Case History URL\n");

		for (JSONObject resultJSONObject :
				getResultJSONObjects(projectTestrayBuildId)) {

			int status = resultJSONObject.optInt("status");

			if (status != 3) {
				continue;
			}

			String testyCaseHistoryURL =
				resultJSONObject.getString("htmlURL") + "/history";

			int recentFailures1 = getRecentFailures(resultJSONObject, 25);
			int recentFailures2 = getRecentFailures(resultJSONObject, 5);

			StringBuilder recentFailuresMessage = new StringBuilder();

			if (recentFailures2 == 5) {
				recentFailuresMessage.append("Failed ");
				recentFailuresMessage.append(recentFailures2);
				recentFailuresMessage.append(" of last 5");
			}
			else {
				recentFailuresMessage.append("Failed ");
				recentFailuresMessage.append(recentFailures1);
				recentFailuresMessage.append(" of last 25");
			}

			if (isUniqueFailure(resultJSONObject)) {
				uniqueFailuresStringBuilder.append(
					resultJSONObject.getString("testrayCaseName"));
				uniqueFailuresStringBuilder.append(",");
				uniqueFailuresStringBuilder.append(
					resultJSONObject.getString("testrayComponentName"));
				uniqueFailuresStringBuilder.append(",");
				uniqueFailuresStringBuilder.append(
					resultJSONObject.getString("testrayTeamName"));
				uniqueFailuresStringBuilder.append(",");
				uniqueFailuresStringBuilder.append(
					recentFailuresMessage.toString());
				uniqueFailuresStringBuilder.append(",");
				uniqueFailuresStringBuilder.append(testyCaseHistoryURL);
				uniqueFailuresStringBuilder.append("\n");
			}
			else {
				System.out.println(
					"IGNORED: " + testyCaseHistoryURL + ", " +
						recentFailuresMessage);

				upstreamFailuresStringBuilder.append(
					resultJSONObject.getString("testrayCaseName"));
				upstreamFailuresStringBuilder.append(",");
				upstreamFailuresStringBuilder.append(
					resultJSONObject.getString("testrayComponentName"));
				upstreamFailuresStringBuilder.append(",");
				upstreamFailuresStringBuilder.append(
					resultJSONObject.getString("testrayTeamName"));
				upstreamFailuresStringBuilder.append(",");
				upstreamFailuresStringBuilder.append(
					recentFailuresMessage.toString());
				upstreamFailuresStringBuilder.append(",");
				upstreamFailuresStringBuilder.append(testyCaseHistoryURL);
				upstreamFailuresStringBuilder.append("\n");
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

	public int getRecentFailures(
		JSONObject resultJSONObject, int casesChecked) {

		try {
			JSONObject historyJSONObject =
				JenkinsResultsParserUtil.toJSONObject(
					resultJSONObject.getString("htmlURL") + "/history.json");

			JSONArray resultsJSONArray = historyJSONObject.optJSONArray("data");

			if ((resultsJSONArray == null) ||
				(resultsJSONArray.length() == 0)) {

				System.out.println("No results found");

				return 0;
			}

			int failures = 0;
			int count = 0;

			for (int i = 0; i < resultsJSONArray.length(); i++) {
				JSONObject jsonObject = resultsJSONArray.optJSONObject(i);

				if (jsonObject == null) {
					continue;
				}

				int status = jsonObject.optInt("status");

				if (status == 0) {
					continue;
				}

				count++;

				if (status == 3) {
					failures++;
				}

				if (count >= casesChecked) {
					break;
				}
			}

			return failures;
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	public List<JSONObject> getResultJSONObjects(String projectTestrayBuildId) {
		List<JSONObject> resultJSONObjects = new ArrayList<>();

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

					resultJSONObjects.add(resultJSONObject);
				}

				currentPage++;

				previousTestrayCaseResultId = currentTestrayCaseResultId;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		}

		return resultJSONObjects;
	}

	public boolean isPassingFailureThreshold(
		JSONObject resultJSONObject, int maxFailures, int casesChecked) {

		try {
			JSONObject historyJSONObject =
				JenkinsResultsParserUtil.toJSONObject(
					resultJSONObject.getString("htmlURL") + "/history.json");

			JSONArray resultsJSONArray = historyJSONObject.optJSONArray("data");

			if ((resultsJSONArray == null) ||
				(resultsJSONArray.length() == 0)) {

				return false;
			}

			int failures = 0;
			int count = 0;

			for (int i = 0; i < resultsJSONArray.length(); i++) {
				JSONObject jsonObject = resultsJSONArray.optJSONObject(i);

				if (jsonObject == null) {
					continue;
				}

				int status = jsonObject.optInt("status");

				if (status == 0) {
					continue;
				}

				count++;

				if (status == 3) {
					failures++;
				}

				if (count >= casesChecked) {
					break;
				}
			}

			if (failures >= maxFailures) {
				return true;
			}
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}

		return false;
	}

	public boolean isUniqueFailure(JSONObject resultJSONObject) {
		if (isPassingFailureThreshold(resultJSONObject, 5, 5) ||
			isPassingFailureThreshold(resultJSONObject, 8, 25)) {

			return false;
		}

		return true;
	}

}