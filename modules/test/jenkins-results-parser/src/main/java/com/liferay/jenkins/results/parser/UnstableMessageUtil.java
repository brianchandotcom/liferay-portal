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
/*
 * @author Peter Yoo
 */
public class UnstableMessageUtil {

	public static String getUnstableMessage(String buildURL) throws Exception {
		StringBuilder sb = new StringBuilder();

		JSONObject testReportJSONObject = JenkinsResultsParserUtil.toJSONObject(
			JenkinsResultsParserUtil.getLocalURL(
				buildURL + "testReport/api/json"));

		int failCount = testReportJSONObject.getInt("failCount");
		int totalCount = testReportJSONObject.getInt("totalCount");

		int passCount = totalCount - failCount;

		sb.append("<h6>Job Results:</h6>");
		sb.append("<p>");
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

		sb.append(" Failed.</p>");

		sb.append("<ol>");

		List<String> runBuildURLs = new ArrayList<>();

		JSONObject jsonObject = JenkinsResultsParserUtil.toJSONObject(
			JenkinsResultsParserUtil.getLocalURL(buildURL + "api/json"));

		if (jsonObject.has("runs")) {
			JSONArray runsJSONArray = jsonObject.getJSONArray("runs");

			for (int i = 0; i < runsJSONArray.length(); i++) {
				JSONObject runJSONObject = runsJSONArray.getJSONObject(i);

				String runBuildURL = runJSONObject.getString("url");

				if (runBuildURL.endsWith(
						"/" + jsonObject.getString("number") + "/")) {

					String runResult = (JenkinsResultsParserUtil.toJSONObject(
						JenkinsResultsParserUtil.getLocalURL(
							runBuildURL + "api/json"))).getString("result");

					if (!runResult.equals("SUCCESS")) {
						runBuildURLs.add(runBuildURL);
					}
				}
			}
		}
		else {
			runBuildURLs.add(buildURL);
		}

		int failureCount = 0;

		topLoop: for (String runBuildURLString : runBuildURLs) {
			testReportJSONObject = JenkinsResultsParserUtil.toJSONObject(
				JenkinsResultsParserUtil.getLocalURL(
					runBuildURLString + "testReport/api/json"));

			JSONArray suitesJSONArray = testReportJSONObject.getJSONArray(
				"suites");

			for (int i = 0; i < suitesJSONArray.length(); i++) {
				JSONObject suiteJSONObject = suitesJSONArray.getJSONObject(i);

				JSONArray casesJSONArray = suiteJSONObject.getJSONArray(
					"cases");

				for (int j = 0; j < casesJSONArray.length(); j++) {
					JSONObject caseJSONObject = casesJSONArray.getJSONObject(j);

					String status = caseJSONObject.getString("status");

					if (!status.equals("FIXED") && !status.equals("PASSED") &&
						!status.equals("SKIPPED")) {

						if (failureCount == 3) {
							failureCount++;
							sb.append("<li>...</li>");
							break topLoop;
						}

						JSONObject jobJSONObject =
							JenkinsResultsParserUtil.toJSONObject(
								JenkinsResultsParserUtil.getLocalURL(
									runBuildURLString + "api/json"));

						String testClassName = caseJSONObject.getString(
							"className");
						String testMethodName = caseJSONObject.getString(
							"name");

						int x = testClassName.lastIndexOf(".");

						String testPackageName = testClassName.substring(0, x);
						String testSimpleClassName = testClassName.substring(
							x + 1);

						runBuildURLString = runBuildURLString.replace("[", "_");
						runBuildURLString = runBuildURLString.replace("]", "_");
						runBuildURLString = runBuildURLString.replace("#", "_");

						sb.append("<li><a href=\\\"");
						sb.append(runBuildURLString);
						sb.append("/testReport/");
						sb.append(testPackageName);
						sb.append("/");
						sb.append(testSimpleClassName);
						sb.append("/");

						String testMethodNameURLString = testMethodName;

						testMethodNameURLString =
							testMethodNameURLString.replace("[", "_");
						testMethodNameURLString =
							testMethodNameURLString.replace("]", "_");
						testMethodNameURLString =
							testMethodNameURLString.replace("#", "_");

						if (testPackageName.equals("junit.framework")) {
							testMethodNameURLString =
								testMethodNameURLString.replace(".", "_");
						}

						sb.append(testMethodNameURLString);
						sb.append("\\\">");
						sb.append(testSimpleClassName);
						sb.append(".");
						sb.append(testMethodName);

						String jobVariant =
							JenkinsResultsParserUtil.getJobVariant(
								jobJSONObject);

						if (jobVariant.contains("functional") &&
							testClassName.contains("EvaluateLogTest")) {

							sb.append("[");
							sb.append(
								JenkinsResultsParserUtil.getAxisVariable(
									jobJSONObject));
							sb.append("]");
						}

						sb.append("</a>");

						if (jobVariant.contains("functional")) {
							sb.append(" - ");

							String description = jobJSONObject.getString(
								"description");

							x = description.indexOf(">Jenkins Report<");

							x = x + 22;

							if (description.length() > x) {
								description = description.substring(x);

								description = description.replace("\"", "\\\"");

								sb.append(description);
								sb.append(" - ");
							}

							sb.append("<a href=\\\"");
							sb.append(runBuildURLString);
							sb.append("/console\\\">Console Output</a>");
						}

						sb.append("</li>");

						failureCount++;
					}
				}
			}
		}

		sb.append("</ol>");

		if (failureCount > 3) {
			sb.append("<p><strong>Click <a href=\\\"");
			sb.append(buildURL);
			sb.append("/testReport/\\\">here</a> for more failures.</strong>");
			sb.append("</p>");
		}

		return sb.toString();
	}

}