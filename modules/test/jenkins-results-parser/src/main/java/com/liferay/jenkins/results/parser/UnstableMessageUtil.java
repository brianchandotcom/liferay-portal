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

import com.liferay.jenkins.results.parser.failure.message.generator.GenericFailureMessageGenerator;

import java.util.ArrayList;
import java.util.List;

import org.apache.tools.ant.Project;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Brian Wing Shun Chan
 */
public class UnstableMessageUtil {

	public static String getUnstableMessage(Project project, String buildURL)
		throws Exception {

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

		int failureCount = _getFailureCount(project, runBuildURLs, sb);

		sb.append("</ol>");

		if (failureCount > 3) {
			sb.append("<p><strong>Click <a href=\"");
			sb.append(buildURL);
			sb.append("/testReport/\">here</a> for more failures.</strong>");
			sb.append("</p>");
		}

		return sb.toString();
	}

	private static int _getFailureCount(
			Project project, List<String> runBuildURLs, StringBuilder sb)
		throws Exception {

		int failureCount = 0;
		int firefoxVNCFailureCount = 0;

		int messageBeginIndex = sb.length();

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
				}

				if (failureCount < 3) {
					_getFailureMessage(runBuildURL, sb);
				}

				failureCount++;

				continue;
			}

			if (result.equals("UNSTABLE")) {
				String consoleText = JenkinsResultsParserUtil.toString(
					JenkinsResultsParserUtil.getLocalURL(
						runBuildURL + "/consoleText"));
				System.out.println("loaded.");

				int cursor = consoleText.indexOf(_FF_VNC_ERROR_MARKER);

				while (cursor != -1) {
					firefoxVNCFailureCount++;

					cursor = consoleText.indexOf(
						_FF_VNC_ERROR_MARKER,
						cursor + _FF_VNC_ERROR_MARKER.length());
				}
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

						continue;
					}

					if (failureCount > 3) {
						continue;
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

					String jobVariant = JenkinsResultsParserUtil.getJobVariant(
						runBuildURLJSONObject);

					if (jobVariant.contains("functional")) {
						String testName = testMethodName.substring(
							5, testMethodName.length() - 1);

						sb.append(testName);

						sb.append("</a> - ");
						sb.append("<a href=\"");

						String logURL = _getLogURL(
							jobVariant, project, runBuildURL);

						sb.append(logURL);

						sb.append("/");
						sb.append(testName.replace("#", "_"));
						sb.append("/index.html.gz\">Poshi Report</a> - ");
						sb.append("<a href=\"");
						sb.append(logURL);
						sb.append("/");
						sb.append(testName.replace("#", "_"));
						sb.append("/summary.html.gz\">Poshi Summary</a> - ");
						sb.append("<a href=\"");
						sb.append(logURL);
						sb.append(
							"/jenkins-console.txt.gz\">Console Output</a>");

						if (Boolean.parseBoolean(
								project.getProperty("record.liferay.log"))) {

							sb.append(" - ");
							sb.append("<a href=\"");
							sb.append(logURL);
							sb.append("/liferay-log.txt.gz\">Liferay Log</a>");
						}
					}
					else {
						sb.append(testSimpleClassName);
						sb.append(".");
						sb.append(testMethodName);
						sb.append("</a>");
					}

					sb.append("</li>");

					failureCount++;
				}
			}
		}

		if (firefoxVNCFailureCount > 0) {
			sb.delete(messageBeginIndex, sb.length());

			if (firefoxVNCFailureCount == failureCount) {
				sb.append("All tests failed due to the Firefox VNC error. ");
			}
			else {
				sb.append(firefoxVNCFailureCount);
				sb.append(" tests failed due to the Firefox VNC error. ");
				sb.append(failureCount - firefoxVNCFailureCount);
				sb.append(" additional tests failed for other reasons. ");
			}

			sb.append("See <a href=\"https://issues.liferay.com");
			sb.append("/browse/LRQA-28169\">LRQA-28169</a> for more details.");

			String hostName = JenkinsResultsParserUtil.getHostName("UNKNOWN");

			String message = hostName + " VNC Failure";
			String from = "root@" + hostName;

			StringBuilder toSB = new StringBuilder();

			toSB.append("kevin.yen@liferay.com, ");
			toSB.append("kiyoshi.lee@liferay.com, ");
			toSB.append("leslie.wong@liferay.com, ");
			toSB.append("michael.hashimoto@liferay.com, ");
			toSB.append("peter.yoo@liferay.com");

			JenkinsResultsParserUtil.sendEmail(
				message, from, message, toSB.toString());
		}

		return failureCount;
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

	private static String _getLogURL(
			String jobVariant, Project project, String runBuildURL)
		throws Exception {

		StringBuilder sb = new StringBuilder();

		sb.append(project.getProperty("log.base.url"));
		sb.append("/");
		sb.append(project.getProperty("env.MASTER_HOSTNAME"));
		sb.append("/");
		sb.append(project.getProperty("env.TOP_LEVEL_START_TIME"));
		sb.append("/");
		sb.append(project.getProperty("env.JOB_NAME"));
		sb.append("/");
		sb.append(project.getProperty("env.BUILD_NUMBER"));
		sb.append("/");
		sb.append(jobVariant);
		sb.append("/");
		sb.append(JenkinsResultsParserUtil.getAxisVariable(runBuildURL));

		return sb.toString();
	}

	private static final String _FF_VNC_ERROR_MARKER =
		"org.openqa.selenium.WebDriverException: Failed to connect to binary " +
			"FirefoxBinary";

}