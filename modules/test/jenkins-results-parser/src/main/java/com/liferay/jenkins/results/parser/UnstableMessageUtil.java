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

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Brian Wing Shun Chan
 */
public class UnstableMessageUtil {

	public static String getUnstableMessage(String runBuildURL)
		throws Exception {

		StringBuilder sb = new StringBuilder();
		sb.append("<ul>");

		JSONObject testReportJSONObject = JenkinsResultsParserUtil.toJSONObject(
			JenkinsResultsParserUtil.getLocalURL(
				runBuildURL + "testReport/api/json"));

		JSONArray suitesJSONArray = testReportJSONObject.getJSONArray("suites");

		for (int i = 0; i < suitesJSONArray.length(); i++) {
			JSONObject suiteJSONObject = suitesJSONArray.getJSONObject(i);

			JSONArray casesJSONArray = suiteJSONObject.getJSONArray("cases");

			for (int j = 0; j < casesJSONArray.length(); j++) {
				JSONObject caseJSONObject = casesJSONArray.getJSONObject(j);

				String status = caseJSONObject.getString("status");

				if (status.equals("FIXED") || status.equals("PASSED") ||
					status.equals("SKIPPED")) {

					continue;
				}

				sb.append("<li><a href=\"");

				String runBuildHREF = runBuildURL;

				runBuildHREF = runBuildHREF.replace("[", "_");
				runBuildHREF = runBuildHREF.replace("]", "_");
				runBuildHREF = runBuildHREF.replace("#", "_");

				sb.append(runBuildHREF);

				sb.append("/testReport/");

				String testClassName = caseJSONObject.getString("className");

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

				sb.append("\\\">");
				sb.append(testSimpleClassName);
				sb.append(".");
				sb.append(testMethodName);

				JSONObject runBuildURLJSONObject =
					JenkinsResultsParserUtil.toJSONObject(
						JenkinsResultsParserUtil.getLocalURL(
							runBuildURL + "api/json"));

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

						description = description.replace("\"", "\\\"");

						sb.append(description);
						sb.append(" - ");
					}

					sb.append("<a href=\\\"");
					sb.append(runBuildURL);
					sb.append("/console\\\">Console Output</a>");
				}

				sb.append("<pre>");
				sb.append(
					truncate(500, caseJSONObject.getString("errorDetails")));
				sb.append("</pre>");

				sb.append("</li>");
			}
		}

		sb.append("</ul>");
		return sb.toString();
	}

	private static String truncate(int maxLength, String message) {
		if (message.length() <= maxLength) {
			return message;
		}

		if (message.contains("\n")) {
			String newMessage = message.substring(
				0, message.lastIndexOf("\n", maxLength));

			if (newMessage.length() > (.75 * maxLength)) {
				return newMessage;
			}
		}

		return message.substring(0, maxLength);
	}

}