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
public class PluginFailureMessageGenerator extends BaseFailureMessageGenerator {

	@Override
	public String getMessage(
			String buildURL, String consoleOutput, Project project)
		throws Exception {

		if (!buildURL.contains("portal-acceptance")) {
			return null;
		}

		JSONObject jsonObject = JenkinsResultsParserUtil.toJSONObject(
			JenkinsResultsParserUtil.getLocalURL(buildURL + "api/json"));

		String jobVariant = JenkinsResultsParserUtil.getJobVariant(jsonObject);

		if (!buildURL.contains("plugins") && !jobVariant.contains("plugins")) {
			return null;
		}

		StringBuilder sb = new StringBuilder();

		Matcher matcher = _pattern.matcher(consoleOutput);

		if (matcher.find()) {
			String group = matcher.group(0);

			sb.append("<p>");
			sb.append(group);
			sb.append("</p>");
			sb.append("<ul>");

			int x = matcher.start() + group.length() + 1;

			int count = Integer.parseInt(matcher.group(1));

			for (int i = 0; i < count; i++) {
				if (i == 10) {
					sb.append("<li>...</li>");

					break;
				}

				int y = consoleOutput.indexOf("\n", x);

				String pluginName = consoleOutput.substring(x, y);

				sb.append("<li>");
				sb.append(pluginName.replace("[echo] ", ""));
				sb.append("</li>");

				x = y + 1;
			}

			sb.append("</ul>");
		}
		else {
			sb.append(
				"<p>To include a plugin fix for this pull request, please ");
			sb.append("edit your <a href=\"https://github.com/");
			sb.append(project.getProperty("github.origin.name"));
			sb.append("/");
			sb.append(project.getProperty("portal.repository"));
			sb.append("/blob/");
			sb.append(project.getProperty("github.sender.branch.name"));
			sb.append("/git-commit-plugins\">git-commit-plugins</a>. ");

			sb.append("Click <a href=\"https://in.liferay.com/web/");
			sb.append(
				"global.engineering/blog/-/blogs/new-tests-for-the-pull-");
			sb.append("request-tester-\">here</a> for more details.</p>");

			int end = consoleOutput.indexOf("merge-test-results:");

			sb.append(getConsoleOutputSnippet(consoleOutput, true, end));
		}

		return sb.toString();
	}

	private static final Pattern _pattern = Pattern.compile(
		"(\\d+) of \\d+ plugins? failed to compile:");

}