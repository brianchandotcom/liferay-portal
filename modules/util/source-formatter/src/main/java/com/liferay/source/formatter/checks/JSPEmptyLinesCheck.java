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
import com.liferay.source.formatter.checks.util.JSPSourceUtil;
import com.liferay.source.formatter.util.RegexUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class JSPEmptyLinesCheck extends EmptyLinesCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		content = fixMissingEmptyLines(content);

		content = fixRedundantEmptyLines(content);

		content = fixIncorrectEmptyLineBeforeCloseCurlyBrace(content);

		content = fixMissingEmptyLineAfterSettingVariable(content);

		content = fixEmptyLinesInMultiLineTags(content);

		content = fixEmptyLinesInNestedTags(content);

		content = fixEmptyLinesBetweenTags(content);

		content = _fixMissingEmptyLines(content);

		content = _fixRedundantEmptyLines(content);

		return content;
	}

	@Override
	protected boolean isJavaSource(String content, int pos) {
		return JSPSourceUtil.isJavaSource(content, pos);
	}

	private String _fixMissingEmptyLines(String content) {
		while (true) {
			Matcher matcher = _MISSING_EMPTY_LINE_PATTERN_1.matcher(content);

			if (matcher.find()) {
				content = StringUtil.replaceFirst(
					content, "\n", "\n\n", matcher.start() + 1);

				continue;
			}

			matcher = _MISSING_EMPTY_LINE_PATTERN_2.matcher(content);

			if (matcher.find()) {
				content = StringUtil.replaceFirst(
					content, "\n", "\n\n", matcher.start());

				continue;
			}

			matcher = _MISSING_EMPTY_LINE_PATTERN_3.matcher(content);

			if (matcher.find()) {
				content = StringUtil.replaceFirst(
					content, "\n", "\n\n", matcher.start() + 1);

				continue;
			}

			matcher = _MISSING_EMPTY_LINE_PATTERN_4.matcher(content);

			if (matcher.find()) {
				content = StringUtil.replaceFirst(
					content, "\n", "\n\n", matcher.start() + 1);

				continue;
			}

			break;
		}

		return content;
	}

	private String _fixRedundantEmptyLines(String content) {
		while (true) {
			Matcher matcher = _REDUNDANT_EMPTY_LINE_PATTERN_1.matcher(content);

			if (matcher.find()) {
				content = StringUtil.replaceFirst(
					content, "\n", StringPool.BLANK, matcher.start() + 1);

				continue;
			}

			matcher = _REDUNDANT_EMPTY_LINE_PATTERN_2.matcher(content);

			if (matcher.find()) {
				content = StringUtil.replaceFirst(
					content, "\n", StringPool.BLANK, matcher.start() + 1);

				continue;
			}

			break;
		}

		return content;
	}

	private static final Pattern _MISSING_EMPTY_LINE_PATTERN_1 =
		RegexUtil.getPattern("[\t\n](--)?%>\n\t*(?!-->)\\S");

	private static final Pattern _MISSING_EMPTY_LINE_PATTERN_2 =
		RegexUtil.getPattern("\\S(?!<\\!--)\n\t*<%(--)?\n");

	private static final Pattern _MISSING_EMPTY_LINE_PATTERN_3 =
		RegexUtil.getPattern("[\t\n]<%\n\t*//");

	private static final Pattern _MISSING_EMPTY_LINE_PATTERN_4 =
		RegexUtil.getPattern("[\t\n]//.*\n\t*%>\n");

	private static final Pattern _REDUNDANT_EMPTY_LINE_PATTERN_1 =
		RegexUtil.getPattern("[\n\t]<%\n\n(\t*)[^/\n\t]");

	private static final Pattern _REDUNDANT_EMPTY_LINE_PATTERN_2 =
		RegexUtil.getPattern("[\n\t][^/\n\t].*\n\n\t*%>");

}