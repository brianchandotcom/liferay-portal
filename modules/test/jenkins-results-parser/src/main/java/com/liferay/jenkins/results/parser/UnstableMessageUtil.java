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

package com.liferay.jenkins.results.parser;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Brian Wing Shun Chan
 */
public class UnstableMessageUtil {

	public static String getUnstableMessage(String buildURL) throws Exception {
		StringBuilder sb = new StringBuilder();

		JSONObject testReportJSONObject = JenkinsResultsParserUtil.toJSONObject(
			JenkinsResultsParserUtil.getLocalURL(
				buildURL + "testReport/api/json"));

		int failCount = testReportJSONObject.getInt("failCount");

		int passCount = 0;
		int totalCount = 0;

		if (!testReportJSONObject.has("passCount") &&
			testReportJSONObject.has("totalCount")) {

			totalCount = testReportJSONObject.getInt("totalCount");

			passCount = totalCount - failCount;
		}
		else {
			passCount = testReportJSONObject.getInt("passCount");

			totalCount = failCount + passCount;
		}

		sb.append("<h6>Job Results:</h6><p>");
		sb.append(passCount);
		sb.append(" Test");

		if (passCount != 1) {
			sb.append("s");
		}

		sb.append(" Passed.<br />");
		sb.append(failCount);
		sb.append(" Test");

		if (failCount != 1) {
			sb.append("s");
		}

		sb.append(" Failed.</p><ol>");

		List<String> runBuildURLs = new ArrayList<>();

		JSONObject jsonObject = JenkinsResultsParserUtil.toJSONObject(
			JenkinsResultsParserUtil.getLocalURL(buildURL + "api/json"));

		if (jsonObject.has("runs")) {
			JSONArray runsJSONArray = jsonObject.getJSONArray("runs");

			for (int i = 0; i < runsJSONArray.length(); i++) {
				JSONObject runJSONObject = runsJSONArray.getJSONObject(i);

				String runBuildURL = runJSONObject.getString("url");

				if (!runBuildURL.endsWith(
						"/" + jsonObject.getInt("number") + "/")) {

					continue;
				}

				JSONObject runBuildURLJSONObject =
					JenkinsResultsParserUtil.toJSONObject(
						JenkinsResultsParserUtil.getLocalURL(
							runBuildURL + "api/json"));

				String result = runBuildURLJSONObject.getString("result");

				if (!result.equals("SUCCESS")) {
					runBuildURLs.add(runBuildURL);
				}
			}
		}
		else {
			runBuildURLs.add(buildURL);
		}

		int failureCount = _getUnstableMessage(runBuildURLs, sb);

		sb.append("</ol>");

		if (failureCount > 3) {
			sb.append("<p><strong>Click <a href=\"");
			sb.append(buildURL);
			sb.append("/testReport/\">here</a> for more failures.</strong>");
			sb.append("</p>");
		}

		return sb.toString();
	}

	private static void _getFailureMessage(
			String failureBuildURL, StringBuilder sb)
		throws Exception {

		sb.append("<li><strong><a href=\"");
		sb.append(failureBuildURL);
		sb.append("\">");

		JSONObject failureJSONObject = JenkinsResultsParserUtil.toJSONObject(
			JenkinsResultsParserUtil.getLocalURL(failureBuildURL + "api/json"));

		sb.append(
			JenkinsResultsParserUtil.fixJSON(
				failureJSONObject.getString("fullDisplayName")));

		sb.append("</a></strong>");

		GenericFailureMessageGenerator genericFailureMessageGenerator =
			new GenericFailureMessageGenerator();

		String consoleOutput = JenkinsResultsParserUtil.toString(
			JenkinsResultsParserUtil.getLocalURL(
				failureBuildURL + "/logText/progressiveText"));

		sb.append(
			genericFailureMessageGenerator.getMessage(
				failureBuildURL, consoleOutput, null));

		sb.append("</li>");
	}

	private static int _getUnstableMessage(
			List<String> runBuildURLs, StringBuilder sb)
		throws Exception {

		int failureCount = 0;

		for (String runBuildURL : runBuildURLs) {
			JSONObject runBuildURLJSONObject =
				JenkinsResultsParserUtil.toJSONObject(
					JenkinsResultsParserUtil.getLocalURL(
						runBuildURL + "api/json"));

			String result = runBuildURLJSONObject.getString("result");

			if (result.equals("FAILURE")) {
				if (failureCount == 3) {
					failureCount++;

					sb.append("<li>...</li>");

					return failureCount;
				}

				_getFailureMessage(runBuildURL, sb);

				failureCount++;

				continue;
			}

			JSONObject testReportJSONObject =
				JenkinsResultsParserUtil.toJSONObject(
					JenkinsResultsParserUtil.getLocalURL(
						runBuildURL + "testReport/api/json"));

			JSONArray suitesJSONArray = testReportJSONObject.getJSONArray(
				"suites");

			for (int i = 0; i < suitesJSONArray.length(); i++) {
				JSONObject suiteJSONObject = suitesJSONArray.getJSONObject(i);

				JSONArray casesJSONArray = suiteJSONObject.getJSONArray(
					"cases");

				for (int j = 0; j < casesJSONArray.length(); j++) {
					JSONObject caseJSONObject = casesJSONArray.getJSONObject(j);

					String status = caseJSONObject.getString("status");

					if (status.equals("FIXED") || status.equals("PASSED") ||
						status.equals("SKIPPED")) {

						continue;
					}

					if (failureCount == 3) {
						failureCount++;

						sb.append("<li>...</li>");

						return failureCount;
					}

					sb.append("<li><a href=\"");

					String runBuildHREF = runBuildURL;

					runBuildHREF = runBuildHREF.replace("[", "_");
					runBuildHREF = runBuildHREF.replace("]", "_");
					runBuildHREF = runBuildHREF.replace("#", "_");

					sb.append(runBuildHREF);

					sb.append("/testReport/");

					String testClassName = caseJSONObject.getString(
						"className");

					int x = testClassName.lastIndexOf(".");

					String testPackageName = testClassName.substring(0, x);

					sb.append(testPackageName);

					sb.append("/");

					String testSimpleClassName = testClassName.substring(x + 1);

					sb.append(testSimpleClassName);

					sb.append("/");

					String testMethodName = caseJSONObject.getString("name");

					String testMethodNameURL = testMethodName;

					testMethodNameURL = testMethodNameURL.replace("[", "_");
					testMethodNameURL = testMethodNameURL.replace("]", "_");
					testMethodNameURL = testMethodNameURL.replace("#", "_");

					if (testPackageName.equals("junit.framework")) {
						testMethodNameURL = testMethodNameURL.replace(".", "_");
					}

					sb.append(testMethodNameURL);

					sb.append("\">");
					sb.append(testSimpleClassName);
					sb.append(".");
					sb.append(testMethodName);

					String jobVariant = JenkinsResultsParserUtil.getJobVariant(
						runBuildURLJSONObject);

					if (jobVariant.contains("functional") &&
						testClassName.contains("EvaluateLogTest")) {

						sb.append("[");
						sb.append(
							JenkinsResultsParserUtil.getAxisVariable(
								runBuildURLJSONObject));
						sb.append("]");
					}

					sb.append("</a>");

					if (jobVariant.contains("functional")) {
						sb.append(" - ");

						String description = runBuildURLJSONObject.getString(
							"description");

						x = description.indexOf(">Jenkins Report<") + 22;

						if (description.length() > x) {
							description = description.substring(x);

							description = description.replace("\"", "\"");

							sb.append(description);

							sb.append(" - ");
						}

						sb.append("<a href=\"");
						sb.append(runBuildURL);
						sb.append("/console\">Console Output</a>");
					}

					sb.append("</li>");

					failureCount++;
				}
			}
		}

		return failureCount;
	}

}