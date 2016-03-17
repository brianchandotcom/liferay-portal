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
public class LocalGitFailureMessageGenerator
	extends BaseFailureMessageGenerator {

	@Override
	public String getMessage(
			String buildURL, String consoleOutput, Project project)
		throws Exception {

		if (!consoleOutput.contains(_LOCAL_GIT_FAILURE_START_STRING) ||
			!consoleOutput.contains(_LOCAL_GIT_FAILURE_END_STRING)) {

			return null;
		}

		StringBuilder sb = new StringBuilder();

		sb.append(
			"<p>Unable to synchronize with <strong>local git</strong>.</p>");

		int end = consoleOutput.indexOf(_LOCAL_GIT_FAILURE_END_STRING);

		int start = consoleOutput.lastIndexOf(
			_LOCAL_GIT_FAILURE_START_STRING, end);

		consoleOutput = consoleOutput.substring(start, end);

		int minIndex = consoleOutput.length();

		for (String string : new String[] {"error: ", "fatal: "}) {
			int index = consoleOutput.indexOf(string);

			if (index != -1) {
				if (index < minIndex) {
					minIndex = index;
				}
			}
		}

		int gitCommandIndex = consoleOutput.lastIndexOf("+ git", minIndex);

		if (gitCommandIndex != -1) {
			start = gitCommandIndex;
		}

		start = consoleOutput.lastIndexOf("\n", start);

		end = consoleOutput.lastIndexOf("\n");

		sb.append(getConsoleOutputSnippet(consoleOutput, start, end, false));

		return sb.toString();
	}

	private static final String _LOCAL_GIT_FAILURE_END_STRING = "BUILD FAILED";

	private static final String _LOCAL_GIT_FAILURE_START_STRING =
		"Too many retries while synchronizing GitHub pull request.";

}