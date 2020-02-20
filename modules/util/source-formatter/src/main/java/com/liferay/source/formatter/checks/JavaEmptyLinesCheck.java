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
public class JavaEmptyLinesCheck extends BaseEmptyLinesCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		content = fixMissingEmptyLines(absolutePath, content);

		content = fixMissingEmptyLinesAroundComments(content);

		content = fixRedundantEmptyLines(content);

		content = fixMissingEmptyLineAfterSettingVariable(content);

		content = _fixRedundantEmptyLineInLambdaExpression(content);

		content = _fixIncorrectEmptyLineInsideStatement(content);

		return content;
	}

	private String _fixIncorrectEmptyLineInsideStatement(String content) {
		int pos = -1;

		outerLoop:
		while (true) {
			int previousPos = pos;

			pos = content.indexOf("\n\n", pos + 1);

			if (pos == -1) {
				return content;
			}

			if (previousPos == -1) {
				continue;
			}

			String s1 = content.substring(previousPos, pos);

			if (getLevel(s1) <= 0) {
				continue;
			}

			String lineBefore = StringUtil.trim(
				getLine(content, getLineNumber(content, previousPos)));

			if (lineBefore.startsWith("//")) {
				continue;
			}

			String lineAfter = StringUtil.trim(
				getLine(content, getLineNumber(content, pos + 2)));

			if (lineAfter.startsWith("//")) {
				continue;
			}

			int x = s1.length();

			while (true) {
				x = s1.lastIndexOf("(", x - 1);

				if (x == -1) {
					break;
				}

				String s2 = s1.substring(x);

				if (getLevel(s2) > 0) {
					if (getLevel(s2, "{", "}") > 0) {
						continue outerLoop;
					}

					String s3 = StringUtil.trim(s1.substring(0, x));

					if (s3.endsWith("\ttry")) {
						continue outerLoop;
					}

					break;
				}
			}

			return StringUtil.replaceFirst(content, "\n\n", "\n", pos);
		}
	}

	private String _fixRedundantEmptyLineInLambdaExpression(String content) {
		Matcher matcher = _redundantEmptyLinePattern.matcher(content);

		while (matcher.find()) {
			if (getLevel(matcher.group(1)) == 0) {
				return StringUtil.replaceFirst(
					content, "\n\n", "\n", matcher.start());
			}
		}

		return content;
	}

	private static final Pattern _redundantEmptyLinePattern = Pattern.compile(
		"\n(.*)-> \\{\n\n[\t ]*(?!// )\\S");

}