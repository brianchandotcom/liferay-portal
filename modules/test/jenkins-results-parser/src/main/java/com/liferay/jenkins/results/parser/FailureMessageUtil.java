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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.tools.ant.Project;

import org.json.JSONObject;

/**
 * @author Peter Yoo
 */
public class FailureMessageUtil {

	public static String getFailureMessage(Project project, String buildURL)
		throws Exception {

		StringBuilder sb = new StringBuilder();

		String consoleOutput = JenkinsResultsParserUtil.toString(
			JenkinsResultsParserUtil.getLocalURL(
				buildURL + "/logText/progressiveText"));

		if (consoleOutput.indexOf("--abort") != -1) {
			sb.append("<p>Please fix <strong>rebase errors</strong> on ");

			sb.append("<strong><a href=\\\"https://github.com/");
			sb.append(project.getProperty("github.pull.request.head.username"));
			sb.append("/");
			sb.append(project.getProperty("repository"));
			sb.append("/tree/");
			sb.append(project.getProperty("github.pull.request.head.branch"));
			sb.append("\\\">");
			sb.append(project.getProperty("github.pull.request.head.username"));
			sb.append("/");
			sb.append(project.getProperty("github.pull.request.head.branch"));
			sb.append("</a></strong>");

			sb.append(".</p>");
		}
		else if (consoleOutput.indexOf("fatal: Could not parse object") != -1) {
			sb.append("<p>Please update ");

			sb.append("<strong><a href=\\\"https://github.com/");
			sb.append(project.getProperty("github.pull.request.head.username"));
			sb.append("/");
			sb.append(project.getProperty("portal.repository"));
			sb.append("/blob/");
			sb.append(project.getProperty("github.pull.request.head.branch"));
			sb.append("/git-commit-plugins\\\">");
			sb.append("git-commit-plugins");
			sb.append("</a></strong>");

			sb.append(" to an existing git id from ");

			sb.append("<strong><a href=\\\"https://github.com/liferay/");
			sb.append(project.getProperty("plugins.repository"));
			sb.append("/commits/");
			sb.append(project.getProperty("plugins.branch.name"));
			sb.append("\\\">");
			sb.append(project.getProperty("plugins.repository"));
			sb.append("/");
			sb.append(project.getProperty("plugins.branch.name"));
			sb.append("</a>");

			sb.append(".</strong></p>");
		}
		else if (buildURL.contains("portal-acceptance")) {
			JSONObject jsonObject = JenkinsResultsParserUtil.toJSONObject(
				JenkinsResultsParserUtil.getLocalURL(buildURL + "api/json"));

			String jobVariant = JenkinsResultsParserUtil.getJobVariant(
				jsonObject);

			if (buildURL.contains("plugins") ||
				jobVariant.contains("plugins")) {

				sb.append("<p>To include a plugin fix for this pull request, ");
				sb.append("please edit your <a href=\\\"https://github.com/");
				sb.append(
					project.getProperty("github.pull.request.head.username"));
				sb.append("/");
				sb.append(project.getProperty("portal.repository"));
				sb.append("/blob/");
				sb.append(
					project.getProperty("github.pull.request.head.branch"));
				sb.append("/git-commit-plugins\\\">git-commit-plugins</a>. ");

				sb.append("Click <a href=\\\"https://in.liferay.com/web/");
				sb.append("global.engineering/blog/-/blogs/new-tests-for-the-");
				sb.append("pull-request-tester-\\\">here</a> for more details");
				sb.append(".</p>");
			}
		}

		for (Pattern pattern : _messagePatterns) {
			Matcher messageMatcher = pattern.matcher(consoleOutput);

			if (messageMatcher.find()) {
				sb.append("<pre>");
				sb.append(messageMatcher.group("snippet"));
				sb.append("</pre>");

				return sb.toString();
			}
		}

		int x = consoleOutput.indexOf("[exec] * Exception is:");

		if (x != -1) {
			x = x + 500;

			x = consoleOutput.indexOf("\n", x);
		}

		if (x == -1) {
			x = consoleOutput.indexOf("merge-test-results:");
		}

		if (x == -1) {
			x = consoleOutput.indexOf("BUILD FAILED");

			if (x != -1) {
				x = consoleOutput.indexOf("Total time:", x);
			}
		}

		if (x != -1) {
			consoleOutput = consoleOutput.substring(0, x);
		}

		Matcher matcher = _pattern.matcher(consoleOutput);

		while (matcher.find()) {
			x = matcher.start() + 1;
		}

		if (x != -1) {
			consoleOutput = consoleOutput.substring(x);
		}

		if (consoleOutput.length() > 2500) {
			int y = consoleOutput.length() - 2500;

			y = consoleOutput.indexOf("\n", y);

			consoleOutput = consoleOutput.substring(y);
		}

		sb.append("<pre>");
		sb.append(JenkinsResultsParserUtil.fixJSON(consoleOutput));
		sb.append("</pre>");

		return sb.toString();
	}

	private static final Pattern[] _messagePatterns = new Pattern[] {
		Pattern.compile(
			"(?<snippet>^\\s+\\[exec\\] " +
				"com.liferay.source.formatter.SourceFormatterTest > " +
				"testSourceFormatter FAILED.*?)^\\s+\\[exec\\]\\s+\\:",
			Pattern.DOTALL | Pattern.MULTILINE)
	};
	private static final Pattern _pattern = Pattern.compile(
		"\\n[a-z\\-\\.]+\\:\\n");

}