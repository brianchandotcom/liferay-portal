/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.failure.message.generator;

import com.liferay.jenkins.results.parser.Dom4JUtil;

import org.dom4j.Element;

/**
 * @author Yi-Chen Tsai
 */
public class CITestSuiteValidationFailureMessageGenerator
	extends BaseFailureMessageGenerator {

	@Override
	public Element getMessageElement(String consoleText) {
		if (!consoleText.contains(_TOKEN_IS_NOT_A_VALID_TEST_SUITE)) {
			return null;
		}

		int start = consoleText.lastIndexOf(_TOKEN_IS_NOT_A_VALID_TEST_SUITE);

		start = consoleText.lastIndexOf(_TOKEN_THE_CI_TEST_SUITE, start);

		int end = consoleText.indexOf(
			_TOKEN_COMMENT_CI_HELP_FOR_DETAILS, start);

		return Dom4JUtil.getNewElement(
			"div", null,
			Dom4JUtil.getNewElement(
				"p", null, consoleText.substring(start, end)),
			Dom4JUtil.getNewElement(
				"p", null, _TOKEN_COMMENT_CI_HELP_FOR_DETAILS));
	}

	private static final String _TOKEN_COMMENT_CI_HELP_FOR_DETAILS =
		"Comment 'ci:help' for details.";

	private static final String _TOKEN_IS_NOT_A_VALID_TEST_SUITE =
		"is not a valid test suite.";

	private static final String _TOKEN_THE_CI_TEST_SUITE = "The CI test suite";

}