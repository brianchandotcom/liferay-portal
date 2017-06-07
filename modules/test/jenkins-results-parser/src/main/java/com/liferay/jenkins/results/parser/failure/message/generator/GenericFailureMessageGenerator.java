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

import java.util.Hashtable;

import org.dom4j.Element;

/**
 * @author Peter Yoo
 */
public class GenericFailureMessageGenerator
	extends BaseFailureMessageGenerator {

	@Override
	public String getMessage(
		String buildURL, String consoleOutput, Hashtable<?, ?> properties) {

		String message = getExceptionSnippet(consoleOutput);

		if (message != null) {
			return message;
		}

		message = getMergeTestResultsSnippet(consoleOutput);

		if (message != null) {
			return message;
		}

		message = getBuildFailedSnippet(consoleOutput);

		if (message != null) {
			return message;
		}

		return getConsoleOutputSnippet(consoleOutput, true, -1);
	}

	@Override
	public Element getMessageElement(Build build) {
		String consoleText = build.getConsoleText();

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

		return getConsoleOutputSnippetElement(consoleText, true, -1);
	}

	protected String getBuildFailedSnippet(String consoleOutput) {
		int end = consoleOutput.indexOf("BUILD FAILED");

		if (end == -1) {
			return null;
		}

		end = consoleOutput.indexOf("Total time:", end);

		return getConsoleOutputSnippet(consoleOutput, true, end);
	}

	protected Element getBuildFailedSnippetElement(String consoleOutput) {
		int end = consoleOutput.indexOf("BUILD FAILED");

		if (end == -1) {
			return null;
		}

		end = consoleOutput.indexOf("Total time:", end);

		return getConsoleOutputSnippetElement(consoleOutput, true, end);
	}

	protected String getExceptionSnippet(String consoleOutput) {
		int end = consoleOutput.indexOf("[exec] * Exception is:");

		if (end == -1) {
			return null;
		}

		end = consoleOutput.indexOf("\n", end + 500);

		return getConsoleOutputSnippet(consoleOutput, true, end);
	}

	protected Element getExceptionSnippetElement(String consoleOutput) {
		int end = consoleOutput.indexOf("[exec] * Exception is:");

		if (end == -1) {
			return null;
		}

		end = consoleOutput.indexOf("\n", end + 500);

		return getConsoleOutputSnippetElement(consoleOutput, true, end);
	}

	protected String getMergeTestResultsSnippet(String consoleOutput) {
		int end = consoleOutput.indexOf("merge-test-results:");

		if (end == -1) {
			return null;
		}

		return getConsoleOutputSnippet(consoleOutput, true, end);
	}

	protected Element getMergeTestResultsSnippetElement(String consoleOutput) {
		int end = consoleOutput.indexOf("merge-test-results:");

		if (end == -1) {
			return null;
		}

		return getConsoleOutputSnippetElement(consoleOutput, true, end);
	}

}