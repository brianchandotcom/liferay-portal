/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class FTLIfStatementCheck extends BaseIfStatementCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		Matcher matcher = _ifStatementPattern.matcher(content);

		while (matcher.find()) {
			String ifClause = "if (" + matcher.group(2) + ") {";

			checkIfClauseParentheses(
				ifClause, fileName, getLineNumber(content, matcher.start(2)),
				true);
		}

		return content;
	}

	private static final Pattern _ifStatementPattern = Pattern.compile(
		"[\t\n]<#(else)?if ([^?\n]*)>\n");

}