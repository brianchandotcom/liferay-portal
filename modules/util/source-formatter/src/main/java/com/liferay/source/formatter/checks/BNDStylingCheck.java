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

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

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

		content = _formatSingleValueOnMultipleLines(content);

		return content;
	}

	private String _fixIncorrectIndent(String content) {
		Matcher matcher = _incorrectIndentPattern.matcher(content);

		if (matcher.find()) {
			return StringUtil.replaceFirst(
				content, matcher.group(1), StringPool.TAB, matcher.start());
		}

		return content;
	}

	private String _fixTrailingSemiColon(String content) {
		Matcher matcher = _trailingSemiColonPattern.matcher(content);

		if (matcher.find()) {
			return StringUtil.replaceFirst(
				content, StringPool.SEMICOLON, StringPool.BLANK,
				matcher.start());
		}

		return content;
	}

	private String _formatSingleValueOnMultipleLines(String content) {
		Matcher matcher = _singleValueOnMultipleLinesPattern.matcher(content);

		if (matcher.find()) {
			content = StringUtil.replaceFirst(
				content, matcher.group(1), StringPool.SPACE, matcher.start());
		}

		return content;
	}

	private final Pattern _incorrectIndentPattern = Pattern.compile(
		"\n[^\t].*:\\\\\n(\t{2,})[^\t]");
	private final Pattern _singleValueOnMultipleLinesPattern = Pattern.compile(
		"\n.*:(\\\\\n\t).*(\n[^\t]|\\Z)");
	private final Pattern _trailingSemiColonPattern = Pattern.compile(
		";(\n|\\Z)");

}