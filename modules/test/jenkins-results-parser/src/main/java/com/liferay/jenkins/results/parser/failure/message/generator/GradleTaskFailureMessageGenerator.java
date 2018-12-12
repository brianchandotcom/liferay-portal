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
public class GradleTaskFailureMessageGenerator
	extends BaseFailureMessageGenerator {

	@Override
	public Element getMessageElement(String consoleText) {
		if (!consoleText.contains(_TOKEN_WHAT_WENT_WRONG)) {
			return null;
		}

		int start = consoleText.lastIndexOf(_TOKEN_WHAT_WENT_WRONG);

		int whereIndex = consoleText.lastIndexOf(_TOKEN_WHERE, start);

		if (whereIndex != -1) {
			start = whereIndex;
		}

		start = consoleText.lastIndexOf("\n", start);

		return getConsoleTextSnippetElementByStart(consoleText, start);
	}

	private static final String _TOKEN_WHAT_WENT_WRONG = "* What went wrong:";

	private static final String _TOKEN_WHERE = "* Where:";

}