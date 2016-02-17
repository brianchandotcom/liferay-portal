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

		int messageCount = 0;

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

				messageCount++;

				if (messageCount == _MAX_MESSAGE_COUNT) {
					sb.append("<li>...</li></ul>");

					return sb.toString();
				}

				JSONObject runBuildURLJSONObject =
					JenkinsResultsParserUtil.toJSONObject(
						JenkinsResultsParserUtil.getLocalURL(
							runBuildURL + "api/json"));

				String axis = JenkinsResultsParserUtil.getAxisVariable(
					runBuildURLJSONObject);

				String jobVariant = JenkinsResultsParserUtil.getJobVariant(
					runBuildURLJSONObject);

				sb.append("<li>");
				sb.append(
					_getRunBuildAnchor(
						axis, caseJSONObject, jobVariant, runBuildURL));

				if (jobVariant.contains("functional")) {
					sb.append(" - ");

					String description = runBuildURLJSONObject.getString(
						"description");

					int x = description.indexOf(">Jenkins Report<") + 22;

					if (description.length() > x) {
						description = description.substring(x);

						sb.append(description);
						sb.append(" - ");
					}

					sb.append("<a href=\"");
					sb.append(runBuildURL);
					sb.append("/console\">Console Output</a>");
				}

				sb.append("<pre>");
				sb.append(
					_processError(
						caseJSONObject.getString("errorDetails"),
						caseJSONObject.getString("errorStackTrace")));
				sb.append("</pre>");

				sb.append("</li>");
			}
		}

		sb.append("</ul>");
		return sb.toString();
	}

	private static String _fixURL(String link) {
		String newLink = link;

		newLink = newLink.replace("[", "_");
		newLink = newLink.replace("]", "_");
		newLink = newLink.replace("#", "_");

		return newLink;
	}

	private static String _getRunBuildAnchor(
		String axis, JSONObject caseJSONObject, String jobVariant,
		String runBuildURL) {

		StringBuilder sb = new StringBuilder();

		sb.append("<a href=\"");

		String runBuildHREF = runBuildURL;

		runBuildHREF = _fixURL(runBuildHREF);

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

		testMethodNameURL = _fixURL(testMethodNameURL);

		if (testPackageName.equals("junit.framework")) {
			testMethodNameURL = testMethodNameURL.replace(".", "_");
		}

		sb.append(testMethodNameURL);

		sb.append("\">");
		sb.append(testSimpleClassName);
		sb.append(".");
		sb.append(testMethodName);

		if (jobVariant.contains("functional") &&
			testClassName.contains("EvaluateLogTest")) {

			sb.append("[");
			sb.append(axis);
			sb.append("]");
		}

		sb.append("</a>");

		return sb.toString();
}

	private static String _processError(
		String errorDetails, String errorStackTrace) {

		String message = errorStackTrace;

		int x = message.indexOf("Caused by:");

		if (x != -1) {
			message = message.substring(message.lastIndexOf("\n", x));
		}

		if (!message.contains(errorDetails)) {
			message = errorDetails + "\n" + message;
		}

		return _truncate((2500/_MAX_MESSAGE_COUNT), message);
	}

	private static String _truncate(int maxLength, String message) {
		if (message.length() <= maxLength) {
			return message;
		}

		if (message.contains("\n")) {
			String newMessage = message.substring(
				0, message.lastIndexOf("\n", maxLength));

			if (newMessage.length() > _MIN_MESSAGE_TRUNCATED_LENGTH) {
				return newMessage;
			}
		}

		return message.substring(0, maxLength);
	}

	private static final int _MAX_MESSAGE_COUNT = 3;

	private static final int _MAX_MESSAGE_LENGTH = 2500;

	private static final int _MIN_MESSAGE_TRUNCATED_LENGTH = Math.round(
		_MAX_MESSAGE_LENGTH * .75f);

}