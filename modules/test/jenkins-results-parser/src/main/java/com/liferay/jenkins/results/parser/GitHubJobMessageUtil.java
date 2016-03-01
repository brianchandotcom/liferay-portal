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

		List<Build> builds = _getBuilds(buildURL, project);

		int failureCount = _getBuildCount(builds, "FAILURE");
		int successCount = _getBuildCount(builds, "SUCCESS");
		int unstableCount = _getBuildCount(builds, "UNSTABLE");

		if (builds.size() == 1) {
			Build jobBuild = builds.get(0);

			if (jobBuild.result.equals("ABORTED")) {
				project.setProperty(
					"report.html.content", jobBuild.getMessage());

				return;
			}
		}

		if (!builds.isEmpty()) {
			int messagePublishedCount = 0;

			sb.append(
				_getHeaderText(failureCount, successCount, unstableCount));

			for (Build runBuild : builds) {
				String result = runBuild.result;

				if (!result.equals("FAILURE") && !result.equals("UNSTABLE")) {
					continue;
				}

				if (messagePublishedCount == _MAX_COUNT) {
					sb.append("<li>...</li>");

					break;
				}

				sb.append(runBuild.getMessage());

				messagePublishedCount++;
			}

			sb.append("</ol>");

			if ((failureCount + unstableCount) > _MAX_COUNT) {
				sb.append("<p><strong>Click <a href=\"");
				sb.append(buildURL);
				sb.append(
					"/testReport/\">here</a> for more failures.</strong>");
				sb.append("</p>");
			}

			project.setProperty("report.html.content", sb.toString());

			return;
		}

		String topLevelSharedDir = project.getProperty("top.level.shared.dir");

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

		project.setProperty("report.html.content", sb.toString());
	}

	private static int _getBuildCount(List<Build> builds, String result) {
		int count = 0;

		for (Build build : builds) {
			String buildResult = build.result;

			if (buildResult.equals(result)) {
				count++;
			}
		}

		return count;
	}

	private static List<Build> _getBuilds(String buildURL, Project project)
		throws Exception {

		List<Build> builds = new ArrayList<>();

		JSONObject jsonObject = JenkinsResultsParserUtil.toJSONObject(
			JenkinsResultsParserUtil.getLocalURL(buildURL + "/api/json"));

		String buildNumber = String.valueOf(jsonObject.get("number"));

		Build build = new Build(jsonObject, project, buildURL);

		String result = build.result;

		if (result.equals("SUCCESS")) {
			return builds;
		}

		if (result.equals("ABORTED")) {
			builds.add(build);

			return builds;
		}

		if (jsonObject.has("runs")) {
			JSONArray runsJSONArray = jsonObject.getJSONArray("runs");

			for (int i = 0; i < runsJSONArray.length(); i++) {
				JSONObject runJSONObject = runsJSONArray.getJSONObject(i);

				String runBuildNumber = String.valueOf(
					runJSONObject.get("number"));

				if (!buildNumber.equals(runBuildNumber)) {
					continue;
				}

				String runBuildURL = runJSONObject.getString("url");

				Build runBuild = new Build(project, runBuildURL);

				builds.add(runBuild);
			}
		}
		else {
			builds.add(build);
		}

		return builds;
	}

	private static String _getCountLine(
		int count, String description, String type) {

		StringBuilder sb = new StringBuilder();

		sb.append(count);
		sb.append(" ");
		sb.append(type);
		sb.append(count == 1 ? " " : "s ");
		sb.append(description);
		sb.append(".");
		sb.append("<br />");

		return sb.toString();
	}

	private static String _getHeaderText(
		int failureCount, int successCount, int unstableCount) {

		StringBuilder sb = new StringBuilder();

		sb.append("<h6>Job Results:</h6><p>");

		sb.append(_getCountLine(successCount, "Passed", "Test"));
		sb.append(_getCountLine(failureCount, "Failed", "Test"));
		sb.append(_getCountLine(unstableCount, "Unstable", "Test"));

		sb.append("</p><ol>");

		return sb.toString();
	}

	private static final int _MAX_COUNT = 3;

	private static class Build {

		public Build(JSONObject jsonObject, Project project, String url) {
			this.project = project;
			this.url = url;

			name = JenkinsResultsParserUtil.fixJSON(
				jsonObject.getString("fullDisplayName"));
			result = jsonObject.getString("result");
		}

		public Build(Project project, String url) throws Exception {
			this(
				JenkinsResultsParserUtil.toJSONObject(
					JenkinsResultsParserUtil.getLocalURL(url + "/api/json")),
				project, url);
		}

		public String getMessage() throws Exception {
			if (result.equals("ABORTED")) {
				return ("<pre>Build was aborted</pre>");
			}

			StringBuilder sb = new StringBuilder();

			sb.append("<li><strong>");
			sb.append(result);
			sb.append(" ");
			sb.append("<a href=\"");
			sb.append(url);
			sb.append("\">");
			sb.append(name);
			sb.append("</a></strong>");

			if (result.equals("FAILURE")) {
				sb.append(FailureMessageUtil.getFailureMessage(project, url));
			}

			if (result.equals("UNSTABLE")) {
				JSONObject jsonObject = JenkinsResultsParserUtil.toJSONObject(
					JenkinsResultsParserUtil.getLocalURL(
						url + "/testReport/api/json"));

				sb.append("<p>");
				sb.append(
					_getCountLine(
						jsonObject.getInt("passCount"), "Passed", "Case"));
				sb.append(
					_getCountLine(
						jsonObject.getInt("failCount"), "Failed", "Case"));
				sb.append(
					_getCountLine(
						jsonObject.getInt("skipCount"), "Skipped", "Case"));
				sb.append("</p>");
				sb.append(UnstableMessageUtil.getUnstableMessage(url));
			}

			sb.append("</li>");

			return sb.toString();
		}

		public final String name;
		public final Project project;
		public final String result;
		public final String url;

	};

}