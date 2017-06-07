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

package com.liferay.jenkins.results.parser.failure.message.generator;

import com.liferay.jenkins.results.parser.Build;
import com.liferay.jenkins.results.parser.Dom4JUtil;
import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.TopLevelBuild;

import java.io.IOException;

import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Element;

import org.json.JSONObject;

/**
 * @author Peter Yoo
 */
public class PluginFailureMessageGenerator extends BaseFailureMessageGenerator {

	@Override
	public String getMessage(
		String buildURL, String consoleOutput, Hashtable<?, ?> properties) {

		if (!buildURL.contains("portal-acceptance")) {
			return null;
		}

		JSONObject jsonObject = null;

		try {
			jsonObject = JenkinsResultsParserUtil.toJSONObject(
				JenkinsResultsParserUtil.getLocalURL(buildURL + "api/json"));
		}
		catch (IOException ioe) {
			throw new RuntimeException("Unable to download JSON", ioe);
		}

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
			sb.append(properties.get("github.origin.name"));
			sb.append("/");
			sb.append(properties.get("portal.repository"));
			sb.append("/blob/");
			sb.append(properties.get("github.sender.branch.name"));
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

	@Override
	public Element getMessageElement(Build build) {
		String buildURL = build.getBuildURL();

		if (!buildURL.contains("portal-acceptance")) {
			return null;
		}

		String jobVariant = build.getParameterValue("JOB_VARIANT");

		if (!buildURL.contains("plugins") && !jobVariant.contains("plugins")) {
			return null;
		}

		String consoleText = build.getConsoleText();

		Matcher matcher = _pattern.matcher(consoleText);

		Element messageElement = Dom4JUtil.getNewElement("div");

		Element paragraphElement = Dom4JUtil.getNewElement("p", messageElement);

		if (matcher.find()) {
			String group = matcher.group(0);

			paragraphElement.addText(group);

			Element pluginsListElement = Dom4JUtil.getNewElement(
				"ul", messageElement);

			int x = matcher.start() + group.length() + 1;

			int count = Integer.parseInt(matcher.group(1));

			for (int i = 0; i < count; i++) {
				Element pluginListItemElement = Dom4JUtil.getNewElement(
					"li", pluginsListElement);

				if (i == 10) {
					pluginListItemElement.addText("...");

					break;
				}

				int y = consoleText.indexOf("\n", x);

				String pluginName = consoleText.substring(x, y);

				pluginListItemElement.addText(
					pluginName.replace("[echo] ", ""));

				x = y + 1;
			}
		}
		else {
			TopLevelBuild topLevelBuild = build.getTopLevelBuild();

			int end = consoleText.indexOf("merge-test-results:");

			Dom4JUtil.addToElement(
				paragraphElement,
				"To include a plugin fix for this pull request, ",
				"please edit your ",
				getGitCommitPluginsAnchorElement(topLevelBuild), ". Click ",
				Dom4JUtil.getNewAnchorElement(_blogURL, "here"),
				" for more details.",
				getConsoleOutputSnippetElement(consoleText, true, end));
		}

		return messageElement;
	}

	private static final String _blogURL =
		"https://in.liferay.com/web/global.engineering/blog/-/blogs" +
			"/new-tests-for-the-pull-request-tester-";
	private static final Pattern _pattern = Pattern.compile(
		"(\\d+) of \\d+ plugins? failed to compile:");

}