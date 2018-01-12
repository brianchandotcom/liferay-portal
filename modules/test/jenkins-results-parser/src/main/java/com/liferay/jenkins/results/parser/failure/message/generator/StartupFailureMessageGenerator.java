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

import org.dom4j.Element;

/**
 * @author Yi-Chen Tsai
 */
public class StartupFailureMessageGenerator
	extends BaseFailureMessageGenerator {

	@Override
	public Element getMessageElement(Build build) {
		String consoleText = build.getConsoleText();

		if (!consoleText.contains(_TOKEN_UNRESOLVED_REQUIREMENT)) {
			return null;
		}

		int start = consoleText.indexOf(_TOKEN_UNRESOLVED_REQUIREMENT);

		start = consoleText.lastIndexOf(_TOKEN_COULD_NOT_RESOLVE_MODULE, start);

		start = consoleText.lastIndexOf("\n", start);

		int end = consoleText.indexOf(_TOKEN_DELETING, start);

		end = consoleText.lastIndexOf("\n", end);

		return Dom4JUtil.getNewElement(
			"div", null,
			Dom4JUtil.getNewElement(
				"p", null, "Startup error: ",
				Dom4JUtil.getNewElement(
					"strong", null, "Unresolved Requirement(s)")),
			getConsoleTextSnippetElement(consoleText, true, start, end));
	}

	private static final String _TOKEN_COULD_NOT_RESOLVE_MODULE =
		"Could not resolve module:";

	private static final String _TOKEN_DELETING = "Deleting:";

	private static final String _TOKEN_UNRESOLVED_REQUIREMENT =
		"Unresolved requirement:";

}