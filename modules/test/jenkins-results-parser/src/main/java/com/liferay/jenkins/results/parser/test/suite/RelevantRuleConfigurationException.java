/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.test.suite;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Kenji Heigel
 */
public class RelevantRuleConfigurationException extends Exception {

	public static void addException(
		RelevantRuleConfigurationException relevantRuleConfigurationException) {

		_relevantRuleConfigurationExceptions.add(
			relevantRuleConfigurationException);
	}

	public static List<RelevantRuleConfigurationException> getExceptions() {
		return _relevantRuleConfigurationExceptions;
	}

	public RelevantRuleConfigurationException(String message) {
		super(message);
	}

	public RelevantRuleConfigurationException(
		String message, Throwable throwable) {

		super(message, throwable);
	}

	private static final List<RelevantRuleConfigurationException>
		_relevantRuleConfigurationExceptions = new ArrayList<>();

}