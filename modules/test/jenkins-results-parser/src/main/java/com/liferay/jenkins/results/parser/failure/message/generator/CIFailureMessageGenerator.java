/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.failure.message.generator;

/**
 * @author Yi-Chen Tsai
 */
public class CIFailureMessageGenerator extends BaseFailureMessageGenerator {

	@Override
	public String getMessage(String consoleText) {
		int index = consoleText.indexOf(_TOKEN_CI_ERROR);

		if (index == -1) {
			return null;
		}

		return consoleText.substring(index, consoleText.indexOf("\n", index));
	}

	@Override
	public boolean isGenericCIFailure() {
		return true;
	}

	private static final String _TOKEN_CI_ERROR = "A CI failure has occurred.";

}