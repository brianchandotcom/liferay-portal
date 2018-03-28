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
public class JSPStylingCheck extends StylingCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		_checkChaining(fileName, content);

		_checkLineBreak(fileName, content);

		content = _fixEmptyJavaSourceTag(content);

		content = _fixIncorrectClosingTag(content);

		content = StringUtil.replace(
			content,
			new String[] {
				"alert('<%= LanguageUtil.", "alert(\"<%= LanguageUtil.",
				"confirm('<%= LanguageUtil.", "confirm(\"<%= LanguageUtil.",
				";;\n"
			},
			new String[] {
				"alert('<%= UnicodeLanguageUtil.",
				"alert(\"<%= UnicodeLanguageUtil.",
				"confirm('<%= UnicodeLanguageUtil.",
				"confirm(\"<%= UnicodeLanguageUtil.", ";\n"
			});

		int pos = content.indexOf("debugger.");

		if (pos != -1) {
			addMessage(
				fileName, "Do not use debugger", getLineCount(content, pos));
		}

		if (!fileName.endsWith("test.jsp")) {
			pos = content.indexOf("System.out.print");

			if (pos != -1) {
				addMessage(
					fileName, "Do not call 'System.out.print'",
					getLineCount(content, pos));
			}
		}

		return formatStyling(content);
	}

	@Override
	protected boolean isJavaSource(String content, int pos) {
		return JSPSourceUtil.isJavaSource(content, pos, true);
	}

	private void _checkChaining(String fileName, String content) {
		Matcher matcher = _chainingPattern.matcher(content);

		if (matcher.find()) {
			addMessage(
				fileName, "Avoid chaining on 'getClass'", "chaining.markdown",
				getLineCount(content, matcher.start()));
		}
	}

	private void _checkLineBreak(String fileName, String content) {
		Matcher matcher = _incorrectLineBreakPattern.matcher(content);

		if (matcher.find()) {
			addMessage(
				fileName, "There should be a line break after '}'",
				getLineCount(content, matcher.start(1)));
		}
	}

	private String _fixEmptyJavaSourceTag(String content) {
		Matcher matcher = _emptyJavaSourceTagPattern.matcher(content);

		if (matcher.find()) {
			return StringUtil.replace(
				content, matcher.group(), StringPool.BLANK);
		}

		return content;
	}

	private String _fixIncorrectClosingTag(String content) {
		Matcher matcher = _incorrectClosingTagPattern.matcher(content);

		if (matcher.find()) {
			return StringUtil.replaceFirst(
				content, " />\n", "\n" + matcher.group(1) + "/>\n",
				matcher.end(1));
		}

		return content;
	}

	private final Pattern _chainingPattern = Pattern.compile(
		"\\WgetClass\\(\\)\\.");
	private final Pattern _emptyJavaSourceTagPattern = Pattern.compile(
		"\n\t*<%\n+\t*%>\n");
	private final Pattern _incorrectClosingTagPattern = Pattern.compile(
		"\n(\t*)\t((?!<\\w).)* />\n");
	private final Pattern _incorrectLineBreakPattern = Pattern.compile(
		"[\n\t]\\} ?(catch|else|finally) ");

}