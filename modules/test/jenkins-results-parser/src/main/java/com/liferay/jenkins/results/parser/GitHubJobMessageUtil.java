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

		JSONObject jsonObject = JenkinsResultsParserUtil.toJSONObject(
			JenkinsResultsParserUtil.getLocalURL(buildURL + "api/json"));

		String topLevelSharedDir = project.getProperty("top.level.shared.dir");

		topLevelSharedDir = topLevelSharedDir.replace(
			"${user.dir}", System.getProperty("user.dir"));

		File javacOutputFile = new File(
			topLevelSharedDir + "/javac.output.txt");

		String result = jsonObject.getString("result");

		if (result.equals("ABORTED")) {
			sb.append("<pre>Build was aborted</pre>");
		}
		else if (result.equals("FAILURE")) {
			if (jsonObject.has("runs")) {
				JSONArray runsJSONArray = jsonObject.getJSONArray("runs");

				List<String> runBuildURLs = new ArrayList<>();
				List<String> failureBuildURLs = new ArrayList<>();

				for (int i = 0; i < runsJSONArray.length(); i++) {
					JSONObject runsJSONObject = runsJSONArray.getJSONObject(i);

					String runBuildURL = runsJSONObject.getString("url");

					if (!runBuildURL.endsWith(
							"/"+ jsonObject.get("number") + "/")) {

						continue;
					}

					JSONObject runBuildURLJSONObject =
						JenkinsResultsParserUtil.toJSONObject(
							JenkinsResultsParserUtil.getLocalURL(
								runBuildURL + "api/json"));

					String runBuildURLResult = runBuildURLJSONObject.getString(
						"result");

					if (!runBuildURLResult.equals("SUCCESS")) {
						failureBuildURLs.add(runBuildURL);
					}

					runBuildURLs.add(runBuildURL);
				}

				sb.append("<h6>Job Results:</h6>");
				sb.append("<p>");
				sb.append(runBuildURLs.size());
				sb.append(" Test");

				if (runBuildURLs.size() != 1) {
					sb.append("s");
				}

				sb.append(" Passed.<br />");
				sb.append(failureBuildURLs.size());
				sb.append(" Test");

				if (failureBuildURLs.size() != 1) {
					sb.append("s");
				}

				sb.append(" Failed.</p>");
				sb.append("<ol>");

				for (int i = 0; i < failureBuildURLs.size(); i++) {
					String failureBuildURL = failureBuildURLs.get(i);

					JSONObject failureJSONObject =
						JenkinsResultsParserUtil.toJSONObject(
							JenkinsResultsParserUtil.getLocalURL(
								failureBuildURL + "api/json"));

					sb.append("<li><strong><a href=\\\"");
					sb.append(failureBuildURL);
					sb.append("\\\">");
					sb.append(
						JenkinsResultsParserUtil.fixJSON(
							failureJSONObject.getString("fullDisplayName")));
					sb.append("</a></strong>");
					sb.append(
						FailureMessageUtil.getFailureMessage(
							project, failureBuildURL));
					sb.append("</li>");

					if (i >= 2) {
						break;
					}
				}

				sb.append("</ol>");
			}
			else {
				sb.append(
					FailureMessageUtil.getFailureMessage(project, buildURL));
			}
		}
		else if (result.equals("UNSTABLE")) {
			sb.append(UnstableMessageUtil.getUnstableMessage(buildURL));
		}
		else if (javacOutputFile.exists()) {
			sb.append("<h6>Job Results:</h6>");
			sb.append("<p>0 Tests Passed.<br />1 Test Failed.</p>");
			sb.append("<pre>");

			String javacOutputFileContent = JenkinsResultsParserUtil.read(
				javacOutputFile);

			if (javacOutputFileContent.length() > 5000) {
				javacOutputFileContent = javacOutputFileContent.substring(
					javacOutputFileContent.length() - 5000);
			}

			sb.append(JenkinsResultsParserUtil.fixJSON(javacOutputFileContent));
			sb.append("</pre>");
		}

		project.setProperty("report.html.content", sb.toString());
	}

}