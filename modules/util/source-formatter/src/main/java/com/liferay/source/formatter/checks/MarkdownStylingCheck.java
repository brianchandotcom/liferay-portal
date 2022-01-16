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
public class MarkdownStylingCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		content = _formatCodeSyntax(content);

		content = _formatNumberedList(content);

		return _formatHeaders(content);
	}

	private String _formatCodeSyntax(String content) {
		Matcher matcher = _incorrectCodeSyntaxPattern.matcher(content);

		if (matcher.find()) {
			return StringUtil.replaceFirst(
				content, matcher.group(), "`" + matcher.group(1) + "`");
		}

		return content;
	}

	private String _formatHeaders(String content) {
		Matcher matcher = _incorrectHeaderNotationPattern.matcher(content);

		content = matcher.replaceAll("$1$2$4");

		matcher = _boldHeaderPattern.matcher(content);

		return matcher.replaceAll("$1$2$4$6");
	}

	private String _formatNumberedList(String content) {
		content = content.replaceAll("(?<!\n)(\n[ \t]*\\d+\\. )", "\n$1");

		return content.replaceAll("(\n[ \t]*)(\\d+)(\\. )", "$11$3");
	}

	private static final Pattern _boldHeaderPattern = Pattern.compile(
		"(\\A|\n)(#+ ?)(\\*+)([^\\*\n]+)(\\*+)(\n)");
	private static final Pattern _incorrectCodeSyntaxPattern = Pattern.compile(
		"```(.+?)```");
	private static final Pattern _incorrectHeaderNotationPattern =
		Pattern.compile("(\\A|\n)(#+[^#\n]+)(#+)(\n)");

}