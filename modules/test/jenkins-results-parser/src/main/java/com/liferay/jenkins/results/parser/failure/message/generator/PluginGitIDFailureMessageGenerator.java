/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.failure.message.generator;

import com.liferay.jenkins.results.parser.Build;
import com.liferay.jenkins.results.parser.Dom4JUtil;
import com.liferay.jenkins.results.parser.TopLevelBuild;

import java.util.Map;

import org.dom4j.Element;

/**
 * @author Peter Yoo
 */
public class PluginGitIDFailureMessageGenerator
	extends BaseFailureMessageGenerator {

	@Override
	public Element getMessageElement(Build build) {
		String consoleText = build.getConsoleText();

		if (!consoleText.contains("fatal: Could not parse object")) {
			return null;
		}

		int end = consoleText.indexOf("merge-test-results:");
		TopLevelBuild topLevelBuild = build.getTopLevelBuild();

		return Dom4JUtil.getNewElement(
			"p", null, "Please update ",
			Dom4JUtil.getNewElement(
				"strong", null,
				getGitCommitPluginsAnchorElement(topLevelBuild)),
			" to an existing Git ID from ",
			Dom4JUtil.getNewElement(
				"strong", null, getPluginsBranchAnchorElement(topLevelBuild)),
			".", getConsoleTextSnippetElementByEnd(consoleText, true, end));
	}

	protected Element getPluginsBranchAnchorElement(
		TopLevelBuild topLevelBuild) {

		String gitRepositoryName = topLevelBuild.getBaseGitRepositoryName();

		String pluginsGitRepositoryName = "liferay-plugins";

		if (gitRepositoryName.endsWith("-ee")) {
			pluginsGitRepositoryName += "-ee";
		}

		Map<String, String> pluginsGitRepositoryGitDetailsTempMap =
			topLevelBuild.getBaseGitRepositoryDetailsTempMap();

		StringBuilder sb = new StringBuilder();

		sb.append("https://github.com/liferay/");
		sb.append(pluginsGitRepositoryName);
		sb.append("/commits/");
		sb.append(
			pluginsGitRepositoryGitDetailsTempMap.get(
				"github.upstream.branch.name"));

		return Dom4JUtil.getNewAnchorElement(
			sb.toString(), pluginsGitRepositoryName, "/",
			pluginsGitRepositoryGitDetailsTempMap.get(
				"github.upstream.branch.name"));
	}

}