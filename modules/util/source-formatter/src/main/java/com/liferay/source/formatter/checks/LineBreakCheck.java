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

package com.liferay.source.formatter.checks;

import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public abstract class LineBreakCheck extends BaseFileCheck {

	protected void checkLineBreaks(
		String line, String previousLine, String fileName, int lineCount) {

		int lineLeadingTabCount = getLeadingTabCount(line);
		int previousLineLeadingTabCount = getLeadingTabCount(previousLine);

		if (previousLine.endsWith(StringPool.COMMA) &&
			previousLine.contains(StringPool.OPEN_PARENTHESIS) &&
			!previousLine.contains("for (") &&
			(lineLeadingTabCount > previousLineLeadingTabCount)) {

			addMessage(
				fileName, "There should be a line break after '('",
				lineCount - 1);
		}

		String trimmedLine = StringUtil.trimLeading(line);

		String strippedQuotesLine = stripQuotes(trimmedLine);

		int strippedQuotesLineOpenParenthesisCount = StringUtil.count(
			strippedQuotesLine, CharPool.OPEN_PARENTHESIS);

		if (!trimmedLine.startsWith(StringPool.OPEN_PARENTHESIS) &&
			trimmedLine.endsWith(") {") &&
			(strippedQuotesLineOpenParenthesisCount > 0) &&
			(getLevel(trimmedLine) > 0)) {

			addMessage(fileName, "Incorrect line break", lineCount);
		}

		if (!trimmedLine.contains(StringPool.COMMA_AND_SPACE) &&
			trimmedLine.endsWith(StringPool.COMMA) &&
			!trimmedLine.startsWith("for (") && (getLevel(trimmedLine) > 0)) {

			addMessage(fileName, "Incorrect line break", lineCount);
		}

		if (line.endsWith(" +") || line.endsWith(" -") || line.endsWith(" *") ||
			line.endsWith(" /")) {

			int x = line.indexOf(" = ");

			if (x != -1) {
				int y = line.indexOf(CharPool.QUOTE);

				if ((y == -1) || (x < y)) {
					addMessage(
						fileName, "There should be a line break after '='",
						lineCount);
				}
			}
		}
	}

	protected String fixRedundantCommaInsideArray(String content) {
		Matcher matcher = _redundantCommaPattern.matcher(content);

		if (matcher.find()) {
			return StringUtil.replaceFirst(
				content, StringPool.COMMA, StringPool.BLANK, matcher.start());
		}

		return content;
	}

	private final Pattern _redundantCommaPattern = Pattern.compile(",\n\t+\\}");

}