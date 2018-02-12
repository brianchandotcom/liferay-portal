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
import com.liferay.portal.kernel.util.StringBundler;
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

		content = _formatArrayInitializer(content);

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

	private String _formatArrayInitializer(String content) {
		Matcher matcher = _arrayInitializationPattern.matcher(content);

		while (matcher.find()) {
			String whitespace = matcher.group(6);

			if (!whitespace.contains(StringPool.NEW_LINE)) {
				return StringUtil.replaceFirst(
					content, matcher.group(5), StringPool.BLANK,
					matcher.start(5));
			}

			int lineCount = getLineCount(content, matcher.end(3));

			String line = getLine(content, lineCount);

			if (getLineLength(line) > (getMaxLineLength() - 2)) {
				String whitespace2 = matcher.group(3);

				if (whitespace2.contains(StringPool.NEW_LINE)) {
					return content;
				}

				return StringUtil.replaceFirst(
					content, matcher.group(),
					StringBundler.concat(
						matcher.group(1), whitespace, matcher.group(4),
						matcher.group(8)));
			}

			if (matcher.group(9) == null) {
				return StringUtil.replaceFirst(
					content, matcher.group(5), StringPool.BLANK,
					matcher.start(5));
			}

			content = StringUtil.replaceFirst(
				content, matcher.group(),
				StringBundler.concat(
					matcher.group(1), matcher.group(3), matcher.group(4),
					" {\n"),
				matcher.start());

			int level = 1;
			int start = lineCount + 1;

			int count = start;

			while (true) {
				level += getLevel(getLine(content, count), "{", "}");

				if (level != 0) {
					count++;

					continue;
				}

				for (int i = start; i <= count; i++) {
					content = StringUtil.replaceFirst(
						content, StringPool.TAB, StringPool.BLANK,
						getLineStartPos(content, i));
				}

				return content;
			}
		}

		return content;
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
	private final Pattern _arrayInitializationPattern = Pattern.compile(
		"(\\W\\w+(\\[\\])+)(\\s+)(\\w+ =)((\\s+)new \\w+(\\[\\])+)( \\{(\n)?)");
	private final Pattern _emptyArrayPattern = Pattern.compile(
		"((\\[\\])+) \\{\\}");

}