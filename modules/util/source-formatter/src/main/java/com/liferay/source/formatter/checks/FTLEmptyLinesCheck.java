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
import com.liferay.source.formatter.util.RegexUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class FTLEmptyLinesCheck extends EmptyLinesCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		content = fixEmptyLinesInMultiLineTags(content);

		content = fixEmptyLinesInNestedTags(content);

		content = fixEmptyLinesBetweenTags(content);

		content = _fixMissingEmptyLinesAroundComments(content);

		return content;
	}

	private String _fixMissingEmptyLinesAroundComments(String content) {
		Matcher matcher = _MISSING_EMPTY_LINE_AFTER_COMMENT_PATTERN.matcher(
			content);

		if (matcher.find()) {
			return StringUtil.replaceFirst(
				content, "\n", "\n\n", matcher.start());
		}

		matcher = _MISSING_EMPTY_LINE_BEFORE_COMMENT_PATTERN.matcher(content);

		if (matcher.find()) {
			return StringUtil.replaceFirst(
				content, "\n", "\n\n", matcher.start());
		}

		return content;
	}

	private static final Pattern _MISSING_EMPTY_LINE_AFTER_COMMENT_PATTERN =
		RegexUtil.getPattern("-->\n[^\n]");

	private static final Pattern _MISSING_EMPTY_LINE_BEFORE_COMMENT_PATTERN =
		RegexUtil.getPattern("[^\n]\n\t*<#--");

}