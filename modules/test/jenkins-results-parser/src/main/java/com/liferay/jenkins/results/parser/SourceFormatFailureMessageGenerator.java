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

import java.util.Hashtable;

import org.dom4j.Element;

/**
 * @author Peter Yoo
 */
public class SourceFormatFailureMessageGenerator
	extends BaseFailureMessageGenerator {

	@Override
	public String getMessage(
		String buildURL, String consoleOutput, Hashtable<?, ?> properties) {

		if (!consoleOutput.contains(_SOURCE_FORMAT_STRING)) {
			return null;
		}

		consoleOutput = consoleOutput.substring(
			consoleOutput.lastIndexOf("format-source:"));

		int end = consoleOutput.indexOf("merge-test-results:");

		return getConsoleOutputSnippet(consoleOutput, true, end);
	}

	@Override
	public Element getMessageElement(Build build) {
		String consoleText = build.getConsoleText();

		if (!consoleText.contains(_SOURCE_FORMAT_STRING)) {
			return null;
		}

		consoleText = consoleText.substring(
			consoleText.lastIndexOf("format-source:"));

		int end = consoleText.indexOf("merge-test-results:");

		return getConsoleOutputSnippetElement(consoleText, true, end);
	}

	private static final String _SOURCE_FORMAT_STRING =
		"at com.liferay.source.formatter.SourceFormatter.format";

}