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
public abstract class BaseFailureMessageGenerator
	implements FailureMessageGenerator {

	@Override
	public abstract String getMessage(
			String buildURL, String consoleOutput, Project project)
		throws Exception;

	protected String getConsoleOutputSnippet(
		String consoleOutput, boolean truncateTop, int end) {

		if (end == -1) {
			end = consoleOutput.length();
		}

		int start = getSnippetStart(consoleOutput, end);

		return getConsoleOutputSnippet(consoleOutput, truncateTop, start, end);
	}

	protected String getConsoleOutputSnippet(
		String consoleOutput, boolean truncateTop, int start, int end) {

		if ((end - start) > 2500) {
			if (truncateTop) {
				start = end - 2500;

				start = consoleOutput.indexOf("\n", start);
			}
			else {
				end = start + 2500;

				end = consoleOutput.lastIndexOf("\n", end);
			}
		}

		consoleOutput = consoleOutput.substring(start, end);

		consoleOutput = consoleOutput.replaceFirst("^\\s*\\n", "");
		consoleOutput = consoleOutput.replaceFirst("\\n\\s*$", "");

		return "<pre><code>" + consoleOutput + "</code></pre>";
	}

	protected int getSnippetStart(String consoleOutput, int end) {
		int start = 0;

		Matcher matcher = _pattern.matcher(consoleOutput);

		while (matcher.find()) {
			int x = matcher.start() + 1;

			if (x >= end) {
				return start;
			}

			start = x;
		}

		return start;
	}

	private static final Pattern _pattern = Pattern.compile(
		"\\n[a-z\\-\\.]+\\:\\n");

}