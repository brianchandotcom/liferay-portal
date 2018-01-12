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
 * @author Yi-Chen Tsai
 */
public class PoshiTestFailureMessageGenerator
	extends BaseFailureMessageGenerator {

	@Override
	public Element getMessageElement(Build build) {
		String consoleText = build.getConsoleText();

		Matcher poshiTestFailureMatcher = _poshiTestFailurePattern.matcher(
			consoleText);

		if (!poshiTestFailureMatcher.find()) {
			return null;
		}

		String failedPoshiTaskToken = poshiTestFailureMatcher.group(1);

		int end = consoleText.indexOf(failedPoshiTaskToken);

		end = consoleText.indexOf(_TOKEN_TRY, end);

		end = consoleText.lastIndexOf("\n", end);

		int start = consoleText.lastIndexOf(_TOKEN_JAVA_LANG_EXCEPTION, end);

		start = consoleText.lastIndexOf("\n", start);

		return Dom4JUtil.getNewElement(
			"div", null,
			Dom4JUtil.getNewElement(
				"p", null, "POSHI Test Failure: ",
				Dom4JUtil.getNewElement("strong", null, failedPoshiTaskToken)),
			getConsoleTextSnippetElement(consoleText, true, start, end));
	}

	private static final String _TOKEN_JAVA_LANG_EXCEPTION =
		"java.lang.Exception";

	private static final String _TOKEN_TRY = "Try:";

	private static final Pattern _poshiTestFailurePattern = Pattern.compile(
		"(?:\\n.*)(Execution failed for task.*Poshi.*)\\n");

}