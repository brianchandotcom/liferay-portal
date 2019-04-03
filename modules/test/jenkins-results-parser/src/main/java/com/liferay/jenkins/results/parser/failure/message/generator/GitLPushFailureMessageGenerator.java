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

import org.dom4j.Element;

/**
 * @author Yi-Chen Tsai
 */
public class GitLPushFailureMessageGenerator
	extends BaseFailureMessageGenerator {

	@Override
	public Element getMessageElement(String consoleText) {
		int index = consoleText.indexOf(_TOKEN_GIT_LPUSH_VALIDATION_FAILURE);

		if (index == -1) {
			return null;
		}

		int end = consoleText.lastIndexOf(_TOKEN_ERROR, index);

		end = consoleText.indexOf("\n", end);

		return getConsoleTextSnippetElementByEnd(consoleText, false, end);
	}

	private static final String _TOKEN_ERROR = "error:";

	private static final String _TOKEN_GIT_LPUSH_VALIDATION_FAILURE =
		"A git-lpush validation failure has occurred.";

}