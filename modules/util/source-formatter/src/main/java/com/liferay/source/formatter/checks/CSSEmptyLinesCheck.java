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

import com.liferay.portal.kernel.util.StringUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class CSSEmptyLinesCheck extends BaseEmptyLinesCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		content = fixMissingEmptyLinesAroundComments(content);

		return _fixEmptyLines(content);
	}

	private String _fixEmptyLines(String content) {
		Matcher matcher = _emptyLineAfterOpenCurlyBrace.matcher(content);

		if (matcher.find()) {
			return StringUtil.replaceFirst(
				content, "\n\n", "\n", matcher.start());
		}

		matcher = _emptyLineBeforeCloseCurlyBrace.matcher(content);

		if (matcher.find()) {
			return StringUtil.replaceFirst(
				content, "\n\n", "\n", matcher.start());
		}

		matcher = _missingEmptyLineAfterComment.matcher(content);

		if (matcher.find()) {
			return StringUtil.replaceFirst(
				content, "\n", "\n\n", matcher.start());
		}

		matcher = _missingEmptyLineBeforeComment.matcher(content);

		if (matcher.find()) {
			return StringUtil.replaceFirst(
				content, "\n", "\n\n", matcher.start());
		}

		return content;
	}

	private static final Pattern _emptyLineAfterOpenCurlyBrace =
		Pattern.compile("\\{\n\n\t*(?!(/\\* --|//))\\S");
	private static final Pattern _emptyLineBeforeCloseCurlyBrace =
		Pattern.compile("\n\n\t*\\}");
	private static final Pattern _missingEmptyLineAfterComment =
		Pattern.compile("-- \\*/\n.");
	private static final Pattern _missingEmptyLineBeforeComment =
		Pattern.compile(".\n\t*/\\* --");

}