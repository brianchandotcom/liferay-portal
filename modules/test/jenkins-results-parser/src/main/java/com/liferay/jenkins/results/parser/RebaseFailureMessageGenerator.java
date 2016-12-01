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
public class RebaseFailureMessageGenerator extends BaseFailureMessageGenerator {

	@Override
	public String getMessage(
			String buildURL, String consoleOutput, Project project)
		throws Exception {

		if (!consoleOutput.contains(_REBASE_END_STRING) ||
			!consoleOutput.contains(_REBASE_START_STRING) ||
			!consoleOutput.contains("CONFLICT")) {

			return null;
		}

		StringBuilder sb = new StringBuilder();

		sb.append("<p>Please fix <strong>rebase errors</strong> on <strong>");
		sb.append("<a href=\"https://github.com/");
		sb.append(project.getProperty("github.origin.name"));
		sb.append("/");
		sb.append(project.getProperty("repository"));
		sb.append("/tree/");
		sb.append(project.getProperty("github.sender.branch.name"));
		sb.append("\">");
		sb.append(project.getProperty("github.origin.name"));
		sb.append("/");
		sb.append(project.getProperty("github.sender.branch.name"));
		sb.append("</a></strong>.</p>");

		int end = consoleOutput.indexOf(_REBASE_END_STRING);

		end = consoleOutput.lastIndexOf("\n", end);

		int start = consoleOutput.lastIndexOf(_REBASE_START_STRING, end);

		start = consoleOutput.lastIndexOf("\n", start);

		sb.append(getConsoleOutputSnippet(consoleOutput, true, start, end));

		return sb.toString();
	}

	private static final String _REBASE_END_STRING =
		"The copy of the patch that failed is found in";

	private static final String _REBASE_START_STRING =
		"First, rewinding head to replay your work on top of it...";

}