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

/**
 * @author Peter Yoo
 */
public class IntegrationTestTimeoutFailureMessageGenerator
	extends BaseFailureMessageGenerator {

	@Override
	public String getMessage(
			String buildURL, String consoleOutput, Project project)
		throws Exception {

		Matcher matcher = _pattern.matcher(consoleOutput);

		if (!matcher.find()) {
			return null;
		}

		StringBuilder sb = new StringBuilder();

		sb.append("<p><strong>");
		sb.append(matcher.group("testName"));
		sb.append("</strong> was aborted because it exceeded ");
		sb.append("the timeout period.</p>");

		String snippet = matcher.group(0);

		sb.append(getConsoleOutputSnippet(snippet, false, 0, snippet.length()));

		return sb.toString();
	}

	private static final Pattern _pattern;

	static {
		StringBuilder sb = new StringBuilder();

		sb.append("Execution failed for task '(?<testName>[^']*)'\\.\\s*");
		sb.append("\\[exec\\] > Process 'Gradle Test Executor \\d+' finished ");
		sb.append("with non-zero exit value 200");

		_pattern = Pattern.compile(sb.toString());
	}

}