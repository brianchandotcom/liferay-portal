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

import org.dom4j.Element;

/**
 * @author Yi-Chen Tsai
 */
public class InvalidGitCommitSHAFailureMessageGenerator
	extends BaseFailureMessageGenerator {

	@Override
	public Element getMessageElement(Build build) {
		String consoleText = build.getConsoleText();

		if (!consoleText.contains(_TOKEN_WAS_NOT_FOUND_IN_BRANCH)) {
			return null;
		}

		int start = consoleText.lastIndexOf(_TOKEN_WAS_NOT_FOUND_IN_BRANCH);

		start = consoleText.lastIndexOf(_TOKEN_SHA, start);

		int end = consoleText.indexOf("\n", start);

		return getConsoleTextSnippetElement(consoleText, false, start, end);
	}

	private static final String _TOKEN_SHA = "SHA";

	private static final String _TOKEN_WAS_NOT_FOUND_IN_BRANCH =
		" was not found in branch '";

}