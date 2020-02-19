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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.checks.util.JSPSourceUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class JSPEmptyLinesCheck extends BaseEmptyLinesCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		content = fixMissingEmptyLines(absolutePath, content);

		content = fixMissingEmptyLinesAroundComments(content);

		content = fixRedundantEmptyLines(content);

		content = fixIncorrectEmptyLineAfterOpenCurlyBrace(content);

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
		Matcher matcher = _missingEmptyLinePattern1.matcher(content);

		while (matcher.find()) {
			if (!JSPSourceUtil.isJSSource(content, matcher.start())) {
				return StringUtil.replaceFirst(
					content, "\n", "\n\n", matcher.start() + 1);
			}
		}

		matcher = _missingEmptyLinePattern2.matcher(content);

		while (matcher.find()) {
			if (!JSPSourceUtil.isJSSource(content, matcher.start())) {
				return StringUtil.replaceFirst(
					content, "\n", "\n\n", matcher.start());
			}
		}

		matcher = _missingEmptyLinePattern3.matcher(content);

		while (matcher.find()) {
			if (!JSPSourceUtil.isJSSource(content, matcher.start())) {
				return StringUtil.replaceFirst(
					content, "\n", "\n\n", matcher.start() + 1);
			}
		}

		matcher = _missingEmptyLinePattern4.matcher(content);

		while (matcher.find()) {
			if (!JSPSourceUtil.isJSSource(content, matcher.start())) {
				return StringUtil.replaceFirst(
					content, "\n", "\n\n", matcher.start() + 1);
			}
		}

		return content;
	}

	private String _fixRedundantEmptyLines(String content) {
		Matcher matcher = _redundantEmptyLinePattern1.matcher(content);

		while (matcher.find()) {
			if (!JSPSourceUtil.isJSSource(content, matcher.start())) {
				return StringUtil.replaceFirst(
					content, "\n", StringPool.BLANK, matcher.start() + 1);
			}
		}

		matcher = _redundantEmptyLinePattern2.matcher(content);

		while (matcher.find()) {
			if (!JSPSourceUtil.isJSSource(content, matcher.start())) {
				return StringUtil.replaceFirst(
					content, "\n", StringPool.BLANK, matcher.start() + 1);
			}
		}

		return content;
	}

	private static final Pattern _missingEmptyLinePattern1 = Pattern.compile(
		"[\t\n](--)?%>\n\t*(?!-->)\\S");
	private static final Pattern _missingEmptyLinePattern2 = Pattern.compile(
		"\\S(?!<\\!--)\n\t*<%(--)?\n");
	private static final Pattern _missingEmptyLinePattern3 = Pattern.compile(
		"[\t\n]<%\n\t*//");
	private static final Pattern _missingEmptyLinePattern4 = Pattern.compile(
		"[\t\n]//.*\n\t*%>\n");
	private static final Pattern _redundantEmptyLinePattern1 = Pattern.compile(
		"[\n\t]<%\n\n(\t*)[^/\n\t]");
	private static final Pattern _redundantEmptyLinePattern2 = Pattern.compile(
		"[\n\t][^/\n\t].*\n\n\t*%>");

}