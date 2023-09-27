/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.failure.message.generator;

import org.dom4j.Element;

/**
 * @author Peter Yoo
 */
public class GenericFailureMessageGenerator
	extends BaseFailureMessageGenerator {

	@Override
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

		return getConsoleTextSnippetElementByEnd(consoleText, true, -1);
	}

	@Override
	public boolean isGenericCIFailure() {
		return true;
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

		return getConsoleTextSnippetElementByEnd(consoleText, true, end);
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

		return getConsoleTextSnippetElementByEnd(consoleText, true, end);
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

		return getConsoleTextSnippetElementByEnd(consoleText, true, end);
	}

}