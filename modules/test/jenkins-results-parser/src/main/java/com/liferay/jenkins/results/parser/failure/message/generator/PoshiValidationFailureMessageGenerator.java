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

import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Element;

/**
 * @author Kevin Yen
 */
public class PoshiValidationFailureMessageGenerator
	extends BaseFailureMessageGenerator {

	@Override
	public String getMessage(
		String buildURL, String consoleOutput, Hashtable<?, ?> properties) {

		Matcher poshiFailureMatcher = _poshiFailurePattern.matcher(
			consoleOutput);

		if (poshiFailureMatcher.find()) {
			String poshiFailureMessage = poshiFailureMatcher.group(1);

			return "<p>POSHI Validation Failure</p><pre><code>" +
				poshiFailureMessage + "</code></pre>";
		}

		return null;
	}

	@Override
	public Element getMessageElement(Build build) {
		String consoleText = build.getConsoleText();

		Matcher poshiFailureMatcher = _poshiFailurePattern.matcher(consoleText);

		if (!poshiFailureMatcher.find()) {
			return null;
		}

		return Dom4JUtil.getNewElement(
			"div", null,
			Dom4JUtil.getNewElement(
				"p", null, "POSHI Validation Failure",
				Dom4JUtil.toCodeSnippetElement(poshiFailureMatcher.group(1))));
	}

	private static final Pattern _poshiFailurePattern = Pattern.compile(
		"\\n(.*errors in POSHI[\\s\\S]+?FAILED)");

}