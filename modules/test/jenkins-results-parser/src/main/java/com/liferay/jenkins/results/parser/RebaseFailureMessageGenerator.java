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

		if (!consoleOutput.contains("--abort")) {
			return null;
		}

		StringBuilder sb = new StringBuilder();

		sb.append("<p>Please fix <strong>rebase errors</strong> on <strong>");
		sb.append("<a href=\"https://github.com/");
		sb.append(project.getProperty("github.pull.request.head.username"));
		sb.append("/");
		sb.append(project.getProperty("repository"));
		sb.append("/tree/");
		sb.append(project.getProperty("github.pull.request.head.branch"));
		sb.append("\">");
		sb.append(project.getProperty("github.pull.request.head.username"));
		sb.append("/");
		sb.append(project.getProperty("github.pull.request.head.branch"));
		sb.append("</a></strong>.</p>");

		int end = consoleOutput.indexOf("BUILD FAILED");

		end = consoleOutput.indexOf("Total time:", end);

		sb.append(getConsoleOutputSnippet(consoleOutput, end));

		return sb.toString();
	}

}