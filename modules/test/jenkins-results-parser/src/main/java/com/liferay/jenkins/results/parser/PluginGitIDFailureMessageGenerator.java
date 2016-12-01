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

import org.apache.tools.ant.Project;

/**
 * @author Peter Yoo
 */
public class PluginGitIDFailureMessageGenerator
	extends BaseFailureMessageGenerator {

	@Override
	public String getMessage(
			String buildURL, String consoleOutput, Project project)
		throws Exception {

		if (!consoleOutput.contains("fatal: Could not parse object")) {
			return null;
		}

		StringBuilder sb = new StringBuilder();

		sb.append("<p>Please update ");

		sb.append("<strong><a href=\"https://github.com/");
		sb.append(project.getProperty("github.origin.name"));
		sb.append("/");
		sb.append(project.getProperty("portal.repository"));
		sb.append("/blob/");
		sb.append(project.getProperty("github.sender.branch.name"));
		sb.append("/git-commit-plugins\">git-commit-plugins</a></strong> to ");
		sb.append("an existing git id from <strong>");
		sb.append("<a href=\"https://github.com/liferay/");
		sb.append(project.getProperty("plugins.repository"));
		sb.append("/commits/");
		sb.append(project.getProperty("plugins.branch.name"));
		sb.append("\">");
		sb.append(project.getProperty("plugins.repository"));
		sb.append("/");
		sb.append(project.getProperty("plugins.branch.name"));
		sb.append("</a>.</strong></p>");

		int end = consoleOutput.indexOf("merge-test-results:");

		sb.append(getConsoleOutputSnippet(consoleOutput, true, end));

		return sb.toString();
	}

}