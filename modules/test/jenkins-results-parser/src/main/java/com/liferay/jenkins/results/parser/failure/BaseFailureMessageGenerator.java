package com.liferay.jenkins.results.parser.failure;

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

import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.tools.ant.Project;

/**
 * @author Peter Yoo
 */
public abstract class BaseFailureMessageGenerator {

	public abstract String getMessage(
			String buildURL, String consoleOutput, Project project)
		throws Exception;

	protected int findSnippetStart(String consoleOutput, int end) {
		Matcher matcher = _pattern.matcher(consoleOutput);
		int start = 0;

		while (matcher.find()) {
			int x = matcher.start() + 1;

			if (x >= end) {
				return start;
			}

			start = x;
		}

		return start;
	}

	protected String getConsoleOutputSnippet(String consoleOutput, int end) {
		if (end == -1) {
			end = consoleOutput.length();
		}

		int start = findSnippetStart(consoleOutput, end);

		if ((end - start) > 2500) {
			start = end - 2500;

			start = consoleOutput.indexOf("\n", start);
		}

		return "<pre>" +
			JenkinsResultsParserUtil.fixJSON(
				consoleOutput.substring(start, end)) + "</pre>";
	}

	private static final Pattern _pattern = Pattern.compile(
		"\\n[a-z\\-\\.]+\\:\\n");

}