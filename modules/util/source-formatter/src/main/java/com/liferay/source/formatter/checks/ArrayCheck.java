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
import com.liferay.portal.tools.ToolsUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class ArrayCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		_checkInefficientAddAllCalls(
			fileName, content, _addAllArraysAsListPattern);
		_checkInefficientAddAllCalls(
			fileName, content, _addAllListUtilFromArrayPattern);

		return _formatEmptyArray(content);
	}

	private void _checkInefficientAddAllCalls(
		String fileName, String content, Pattern pattern) {

		Matcher matcher = pattern.matcher(content);

		while (matcher.find()) {
			if (!ToolsUtil.isInsideQuotes(content, matcher.start())) {
				addMessage(
					fileName, "Use Collections.addAll", "collections.markdown",
					getLineCount(content, matcher.start()));
			}
		}
	}

	private String _formatEmptyArray(String content) {
		Matcher matcher = _emptyArrayPattern.matcher(content);

		while (matcher.find()) {
			if (ToolsUtil.isInsideQuotes(content, matcher.end(1))) {
				continue;
			}

			String replacement = StringUtil.replace(
				matcher.group(1), "[]", "[0]");

			return StringUtil.replaceFirst(
				content, matcher.group(), replacement, matcher.start());
		}

		return content;
	}

	private final Pattern _addAllArraysAsListPattern = Pattern.compile(
		"\\.addAll\\(\\s*Arrays\\.asList\\(");
	private final Pattern _addAllListUtilFromArrayPattern = Pattern.compile(
		"\\.addAll\\(\\s*ListUtil\\.fromArray\\(");
	private final Pattern _emptyArrayPattern = Pattern.compile(
		"((\\[\\])+) \\{\\}");

}