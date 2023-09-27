/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.failure.message.generator;

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
	public Element getMessageElement(String consoleText) {
		Matcher matcher = _pattern.matcher(consoleText);

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