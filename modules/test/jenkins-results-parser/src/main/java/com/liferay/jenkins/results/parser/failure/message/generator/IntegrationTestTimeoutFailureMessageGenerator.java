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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Element;

/**
 * @author Peter Yoo
 */
public class IntegrationTestTimeoutFailureMessageGenerator
	extends BaseFailureMessageGenerator {

	@Override
	public Element getMessageElement(Build build) {
		Matcher matcher = _pattern.matcher(build.getConsoleText());

		if (!matcher.find()) {
			return null;
		}

		Element messageElement = Dom4JUtil.getNewElement(
			"div", null,
			Dom4JUtil.getNewElement(
				"p", null,
				Dom4JUtil.getNewElement(
					"strong", null, matcher.group("testName")),
				" was aborted because it exceeded the timeout period."));

		String snippet = matcher.group(0);

		messageElement.add(
			getConsoleTextSnippetElement(snippet, false, 0, snippet.length()));

		return messageElement;
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