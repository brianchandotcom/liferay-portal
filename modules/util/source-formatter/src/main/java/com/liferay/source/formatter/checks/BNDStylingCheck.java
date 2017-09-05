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
import com.liferay.portal.tools.ToolsUtil;
import com.liferay.source.formatter.util.RegexUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class BNDStylingCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		content = StringUtil.replace(
			content, new String[] {"/\n", "/,\\\n", " \\\n"},
			new String[] {"\n", ",\\\n", "\\\n"});

		content = _fixIncorrectIndent(content);

		content = _fixTrailingSemiColon(content);

		content = _formatMultipleValuesOnSingleLine(content);
		content = _formatSingleValueOnMultipleLines(content);

		return content;
	}

	private String _fixIncorrectIndent(String content) {
		Matcher matcher = _INCORRECT_INDEX_PATTERN.matcher(content);

		if (matcher.find()) {
			return StringUtil.replaceFirst(
				content, matcher.group(1), StringPool.TAB, matcher.start());
		}

		return content;
	}

	private String _fixTrailingSemiColon(String content) {
		Matcher matcher = _TRAILING_SEMI_COLON_PATTERN.matcher(content);

		if (matcher.find()) {
			return StringUtil.replaceFirst(
				content, StringPool.SEMICOLON, StringPool.BLANK,
				matcher.start());
		}

		return content;
	}

	private String _formatMultipleValuesOnSingleLine(String content) {
		Matcher matcher = _MULTIPLE_VALUES_ON_SINGLE_LINE_PATTERN.matcher(
			content);

		while (matcher.find()) {
			if (ToolsUtil.isInsideQuotes(content, matcher.start())) {
				continue;
			}

			int x = content.lastIndexOf(CharPool.NEW_LINE, matcher.start());

			String s = content.substring(x + 1, matcher.start());

			if (s.contains("-Description: ")) {
				continue;
			}

			content = StringUtil.insert(content, "\\\n\t", matcher.start() + 1);

			if (s.startsWith(StringPool.TAB)) {
				return content;
			}

			x = content.indexOf(": ", x + 1);

			if ((x == -1) || (x > matcher.start())) {
				continue;
			}

			return StringUtil.replaceFirst(
				content, StringPool.SPACE, "\\\n\t", x);
		}

		return content;
	}

	private String _formatSingleValueOnMultipleLines(String content) {
		Matcher matcher = _SINGLE_VALUE_ON_MULTIPLE_LINES_PATTERN.matcher(
			content);

		if (matcher.find()) {
			content = StringUtil.replaceFirst(
				content, matcher.group(1), StringPool.SPACE, matcher.start());
		}

		return content;
	}

	private static final Pattern _INCORRECT_INDEX_PATTERN =
		RegexUtil.getPattern("\n[^\t].*:\\\\\n(\t{2,})[^\t]");

	private static final Pattern _MULTIPLE_VALUES_ON_SINGLE_LINE_PATTERN =
		RegexUtil.getPattern(",(?!\\\\(\n|\\Z)).");

	private static final Pattern _SINGLE_VALUE_ON_MULTIPLE_LINES_PATTERN =
		RegexUtil.getPattern("\n.*:(\\\\\n\t).*(\n[^\t]|\\Z)");

	private static final Pattern _TRAILING_SEMI_COLON_PATTERN =
		RegexUtil.getPattern(";(\n|\\Z)");

}