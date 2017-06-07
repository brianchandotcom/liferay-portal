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
import com.liferay.jenkins.results.parser.TopLevelBuild;

import java.util.Hashtable;
import java.util.Map;

import org.dom4j.Element;

/**
 * @author Peter Yoo
 */
public class PluginGitIDFailureMessageGenerator
	extends BaseFailureMessageGenerator {

	@Override
	public String getMessage(
		String buildURL, String consoleOutput, Hashtable<?, ?> properties) {

		if (!consoleOutput.contains("fatal: Could not parse object")) {
			return null;
		}

		StringBuilder sb = new StringBuilder();

		sb.append("<p>Please update ");

		sb.append("<strong><a href=\"https://github.com/");
		sb.append(properties.get("github.origin.name"));
		sb.append("/");
		sb.append(properties.get("portal.repository"));
		sb.append("/blob/");
		sb.append(properties.get("github.sender.branch.name"));
		sb.append("/git-commit-plugins\">git-commit-plugins</a></strong> to ");
		sb.append("an existing Git ID from <strong>");
		sb.append("<a href=\"https://github.com/liferay/");
		sb.append(properties.get("plugins.repository"));
		sb.append("/commits/");
		sb.append(properties.get("plugins.branch.name"));
		sb.append("\">");
		sb.append(properties.get("plugins.repository"));
		sb.append("/");
		sb.append(properties.get("plugins.branch.name"));
		sb.append("</a>.</strong></p>");

		int end = consoleOutput.indexOf("merge-test-results:");

		sb.append(getConsoleOutputSnippet(consoleOutput, true, end));

		return sb.toString();
	}

	@Override
	public Element getMessageElement(Build build) {
		String consoleText = build.getConsoleText();

		if (!consoleText.contains("fatal: Could not parse object")) {
			return null;
		}

		int end = consoleText.indexOf("merge-test-results:");
		TopLevelBuild topLevelBuild = build.getTopLevelBuild();

		Element messageElement = Dom4JUtil.getNewElement(
			"p", null, "Please update ",
			Dom4JUtil.getNewElement(
				"strong", null,
				getGitCommitPluginsAnchorElement(topLevelBuild)),
			" to an existing Git ID from ",
			Dom4JUtil.getNewElement(
				"strong", null, getPluginsBranchAnchorElement(topLevelBuild)),
			".", getConsoleOutputSnippetElement(consoleText, true, end));

		return messageElement;
	}

	protected Element getPluginsBranchAnchorElement(
		TopLevelBuild topLevelBuild) {

		String repositoryName = topLevelBuild.getBaseRepositoryName();

		String pluginsRepositoryName = "liferay-plugins";

		if (repositoryName.endsWith("-ee")) {
			pluginsRepositoryName += "-ee";
		}

		Map<String, String> pluginsRepositoryGitDetailsTempMap =
			topLevelBuild.getBaseGitRepositoryDetailsTempMap();

		StringBuilder sb = new StringBuilder();

		sb.append("https://github.com/liferay/");
		sb.append(pluginsRepositoryName);
		sb.append("/commits/");
		sb.append(
			pluginsRepositoryGitDetailsTempMap.get(
				"github.upstream.branch.name"));

		return Dom4JUtil.getNewAnchorElement(
			sb.toString(), pluginsRepositoryName, "/",
			pluginsRepositoryGitDetailsTempMap.get(
				"github.upstream.branch.name"));
	}

}