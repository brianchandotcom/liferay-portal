/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.failure.message.generator;

import com.liferay.jenkins.results.parser.Dom4JUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Element;

/**
 * @author Kevin Yen
 */
public class PoshiValidationFailureMessageGenerator
	extends BaseFailureMessageGenerator {

	@Override
	public Element getMessageElement(String consoleText) {
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
		"\\n(.*errors in (Poshi|POSHI)[\\s\\S]+?FAILED)");

}