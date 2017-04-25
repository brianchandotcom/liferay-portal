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
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class StringBundlerCheck extends BaseFileCheck {

	public StringBundlerCheck(int maxLineLength) {
		_maxLineLength = maxLineLength;
	}

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		return _formatStringBundler(fileName, content);
	}

	private String _formatStringBundler(String fileName, String content) {
		Matcher matcher = _sbAppendPattern.matcher(content);

		matcherIteration:
		while (matcher.find()) {
			String appendValue = stripQuotes(matcher.group(2), CharPool.QUOTE);

			appendValue = StringUtil.replace(appendValue, "+\n", "+ ");

			if (!appendValue.contains(" + ")) {
				continue;
			}

			String[] appendValueParts = StringUtil.split(appendValue, " + ");

			for (String appendValuePart : appendValueParts) {
				if ((getLevel(appendValuePart) != 0) ||
					Validator.isNumber(appendValuePart)) {

					continue matcherIteration;
				}
			}

			addMessage(
				fileName, "Incorrect use of '+' inside StringBundler",
				getLineCount(content, matcher.start(1)));
		}

		matcher = _sbAppendWithStartingSpacePattern.matcher(content);

		while (matcher.find()) {
			String firstLine = matcher.group(1);

			if (firstLine.endsWith("\\n\");")) {
				continue;
			}

			if ((_maxLineLength != -1) &&
				(getLineLength(firstLine) >= _maxLineLength)) {

				addMessage(
					fileName,
					"Do not append string starting with space to StringBundler",
					getLineCount(content, matcher.start(3)));
			}
			else {
				content = StringUtil.replaceFirst(
					content, "\");\n", " \");\n", matcher.start(2));
				content = StringUtil.replaceFirst(
					content, "(\" ", "(\"", matcher.start(3));
			}
		}

		return content;
	}

	private final int _maxLineLength;
	private final Pattern _sbAppendPattern = Pattern.compile(
		"\\s*\\w*(sb|SB)[0-9]?\\.append\\(\\s*(\\S.*?)\\);\n", Pattern.DOTALL);
	private final Pattern _sbAppendWithStartingSpacePattern = Pattern.compile(
		"\n(\t*\\w*(sb|SB)[0-9]?\\.append\\(\".*\"\\);)\n\\s*\\w*(sb|SB)" +
			"[0-9]?\\.append\\(\" .*\"\\);\n");

}