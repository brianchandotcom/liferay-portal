/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Hugo Huijser
 */
public abstract class BaseLineBreakCheck extends BaseFileCheck {

	protected void checkLineBreaks(
		String line, String previousLine, String fileName, int lineNumber) {

		String trimmedLine = StringUtil.trimLeading(line);

		String strippedQuotesLine = stripQuotes(trimmedLine);

		int strippedQuotesLineOpenParenthesisCount = StringUtil.count(
			strippedQuotesLine, CharPool.OPEN_PARENTHESIS);

		if (!trimmedLine.startsWith(StringPool.OPEN_PARENTHESIS) &&
			trimmedLine.endsWith(") {") &&
			(strippedQuotesLineOpenParenthesisCount > 0) &&
			(getLevel(trimmedLine) > 0)) {

			addMessage(fileName, "Incorrect line break", lineNumber);
		}

		if (!trimmedLine.matches("(return )?\\(.*") &&
			(trimmedLine.endsWith(StringPool.COMMA) ||
			 trimmedLine.endsWith("->")) &&
			(getLevel(trimmedLine) > 0)) {

			addMessage(
				fileName, "There should be a line break after '('", lineNumber);
		}

		if (line.endsWith(" +") || line.endsWith(" -") || line.endsWith(" *") ||
			line.endsWith(" /")) {

			int x = line.indexOf(" = ");

			if ((x != -1) && (getLevel(line, "{", "}") == 0)) {
				int y = line.indexOf(CharPool.QUOTE);

				if ((y == -1) || (x < y)) {
					addMessage(
						fileName, "There should be a line break after '='",
						lineNumber);
				}
			}

			x = line.indexOf(" -> ");

			if ((x != -1) && (getLevel(line, "{", "}") == 0)) {
				int y = line.indexOf(CharPool.QUOTE);

				if ((y == -1) || (x < y)) {
					addMessage(
						fileName, "There should be a line break after '->'",
						lineNumber);
				}
			}
		}
	}

}