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

import java.io.File;

import java.util.ArrayList;
import java.util.List;

import org.apache.tools.ant.Project;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Peter Yoo
 */
public class GitHubJobMessageUtil {

	public static void getGitHubJobMessage(Project project) throws Exception {
		StringBuilder sb = new StringBuilder();

		String buildURL = project.getProperty("build.url");

		List<JobResult> jobResults = getGitHubJobMessage(buildURL, project);

		int failureCount = countByResult(jobResults, "FAILURE");
		int unstableCount = countByResult(jobResults, "UNSTABLE");
		int successCount = countByResult(jobResults, "SUCCESS");

		if (jobResults.size() > 0) {
			JobResult firstJobResult = jobResults.get(0);
			int failureAndUnstableCount = 0;

			if (firstJobResult._result.equals("ABORTED")) {
				sb.append(firstJobResult.getMessage());
			}
			else {
				sb.append(
					generateHeaderText(
						failureCount, successCount, unstableCount));

				for (JobResult jobResult : jobResults) {
					if (!jobResult._result.equals("FAILURE") &&
						!jobResult._result.equals("UNSTABLE")) {

						continue;
					}

					failureAndUnstableCount++;

					if (failureAndUnstableCount == (_MAX_MESSAGE_COUNT + 1)) {
						sb.append("<li>...</li>");

						break;
					}

					sb.append(jobResult.getMessage());
				}

				sb.append("</ol>");

				if (failureAndUnstableCount == (_MAX_MESSAGE_COUNT + 1)) {
					sb.append("<p><strong>Click <a href=\"");
					sb.append(buildURL);
					sb.append(
						"/testReport/\">here</a> for more failures.</strong>");
					sb.append("</p>");
				}
			}
		}

		if (sb.length() == 0) {
			String topLevelSharedDir = project.getProperty(
				"top.level.shared.dir");

			topLevelSharedDir = topLevelSharedDir.replace(
				"${user.dir}", System.getProperty("user.dir"));

			File javacOutputFile = new File(
				topLevelSharedDir + "/javac.output.txt");

			if (javacOutputFile.exists()) {
				sb.append("<h6>Job Results:</h6>");
				sb.append("<p>0 Tests Passed.<br />1 Test Failed.</p>");
				sb.append("<pre>");

				String javacOutputFileContent = JenkinsResultsParserUtil.read(
					javacOutputFile);

				if (javacOutputFileContent.length() > 5000) {
					javacOutputFileContent = javacOutputFileContent.substring(
						javacOutputFileContent.length() - 5000);
				}

				sb.append(javacOutputFileContent);
				sb.append("</pre>");
			}
		}

		project.setProperty("report.html.content", sb.toString());
	}

	private static int countByResult(
		List<JobResult> jobResults, String result) {

		int count = 0;

		for (JobResult jobResult : jobResults) {
			if (jobResult._result.equals(result)) {
				count++;
			}
		}

		return count;
	}

	private static String generateCountLine(
		int count, String description, String type) {

		StringBuilder sb = new StringBuilder();
		sb.append(count);
		sb.append(" ");
		sb.append(type);
		sb.append(count == 1 ? " " : "s ");
		sb.append(description);
		sb.append(".");
		sb.append("<br/>");
		return sb.toString();
	}

	private static String generateHeaderText(
		int failedCount, int passedCount, int unstableCount) {

		StringBuilder sb = new StringBuilder();

		sb.append("<h6>Job Results:</h6><p>");

		sb.append(generateCountLine(passedCount, "Passed", "Test"));
		sb.append(generateCountLine(failedCount, "Failed", "Test"));
		sb.append(generateCountLine(unstableCount, "Unstable", "Test"));

		sb.append("</p><ol>");

		return sb.toString();
	}

	private static List<JobResult> getGitHubJobMessage(
			String buildURL, Project project)
		throws Exception {

		JSONObject jsonObject = JenkinsResultsParserUtil.toJSONObject(
			JenkinsResultsParserUtil.getLocalURL(buildURL + "/api/json"));

		String buildNumber = jsonObject.get("number").toString();

		List<JobResult> jobResults = new ArrayList<>();

		JobResult jobResult = new JobResult(jsonObject, project, buildURL);

		if (jobResult._result.equals("SUCCESS")) {
			return jobResults;
		}

		if (jobResult._result.equals("ABORTED")) {
			jobResults.add(jobResult);
			return jobResults;
		}

		if (jsonObject.has("runs")) {
			JSONArray runsJSONArray = jsonObject.getJSONArray("runs");

			for (int i = 0; i < runsJSONArray.length(); i++) {
				JSONObject runJSONObject = runsJSONArray.getJSONObject(i);

				String runBuildURL = runJSONObject.getString("url");
				String runBuildNumber = runJSONObject.get("number").toString();

				if (!buildNumber.equals(runBuildNumber)) {
					continue;
				}

				jobResult = new JobResult(project, runBuildURL);

				jobResults.add(jobResult);
			}
		}
		else {
			jobResults.add(jobResult);
		}

		return jobResults;
	}

	private static final int _MAX_MESSAGE_COUNT = 3;

	private static class JobResult {

		public JobResult(JSONObject jsonObject, Project project, String url) {
			_name = JenkinsResultsParserUtil.fixJSON(
				jsonObject.getString("fullDisplayName"));
			_project = project;
			_result = jsonObject.getString("result");
			_url = url;
		}

		public JobResult(Project project, String url) throws Exception {
			this(
				JenkinsResultsParserUtil.toJSONObject(
					JenkinsResultsParserUtil.getLocalURL(url + "/api/json")),
				project, url);
		}

		public String getMessage() throws Exception {
			if (_result.equals("ABORTED")) {
				return ("<pre>Build was aborted</pre>");
			}

			StringBuilder sb = new StringBuilder();

			sb.append("<li><strong>");
			sb.append(_result);
			sb.append(" ");
			sb.append("<a href=\"");
			sb.append(_url);
			sb.append("\">");
			sb.append(_name);
			sb.append("</a></strong>");

			if (_result.equals("FAILURE")) {
				sb.append(FailureMessageUtil.getFailureMessage(_project, _url));
			}

			if (_result.equals("UNSTABLE")) {
				JSONObject jsonObject = JenkinsResultsParserUtil.toJSONObject(
					JenkinsResultsParserUtil.getLocalURL(
						_url + "/testReport/api/json"));
				int failCount = jsonObject.getInt("failCount");
				int skipCount = jsonObject.getInt("skipCount");
				int passCount = jsonObject.getInt("passCount");

				sb.append("<p>");
				sb.append(generateCountLine(passCount, "Passed", "Case"));
				sb.append(generateCountLine(failCount, "Failed", "Case"));
				sb.append(generateCountLine(skipCount, "Skipped", "Case"));
				sb.append("</p>");
				sb.append(UnstableMessageUtil.getUnstableMessage(_url));
			}

			sb.append("</li>");

			return sb.toString();
		}

		private final String _name;
		private final Project _project;
		private final String _result;
		private final String _url;

	};

}