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
 * @author Peter Yoo
 */
public class GenericFailureMessageGenerator
	extends BaseFailureMessageGenerator {

	@Override
	public Element getMessageElement(Build build) {
		return getMessageElement(build.getConsoleText());
	}

	public Element getMessageElement(String consoleText) {
		Element message = getExceptionSnippetElement(consoleText);

		if (message != null) {
			return message;
		}

		message = getMergeTestResultsSnippetElement(consoleText);

		if (message != null) {
			return message;
		}

		message = getBuildFailedSnippetElement(consoleText);

		if (message != null) {
			return message;
		}

		return getConsoleTextSnippetElement(consoleText, true, -1);
	}

	protected String getBuildFailedSnippet(String consoleText) {
		int end = consoleText.indexOf("BUILD FAILED");

		if (end == -1) {
			return null;
		}

		end = consoleText.indexOf("Total time:", end);

		return getConsoleTextSnippet(consoleText, true, end);
	}

	protected Element getBuildFailedSnippetElement(String consoleText) {
		int end = consoleText.indexOf("BUILD FAILED");

		if (end == -1) {
			return null;
		}

		end = consoleText.indexOf("Total time:", end);

		return getConsoleTextSnippetElement(consoleText, true, end);
	}

	protected String getExceptionSnippet(String consoleText) {
		int end = consoleText.indexOf("[exec] * Exception is:");

		if (end == -1) {
			return null;
		}

		end = consoleText.indexOf("\n", end + 500);

		return getConsoleTextSnippet(consoleText, true, end);
	}

	protected Element getExceptionSnippetElement(String consoleText) {
		int end = consoleText.indexOf("[exec] * Exception is:");

		if (end == -1) {
			return null;
		}

		end = consoleText.indexOf("\n", end + 500);

		return getConsoleTextSnippetElement(consoleText, true, end);
	}

	protected String getMergeTestResultsSnippet(String consoleText) {
		int end = consoleText.indexOf("merge-test-results:");

		if (end == -1) {
			return null;
		}

		return getConsoleTextSnippet(consoleText, true, end);
	}

	protected Element getMergeTestResultsSnippetElement(String consoleText) {
		int end = consoleText.indexOf("merge-test-results:");

		if (end == -1) {
			return null;
		}

		return getConsoleTextSnippetElement(consoleText, true, end);
	}

}