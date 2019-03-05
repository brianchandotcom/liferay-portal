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
import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.tools.ToolsUtil;

import java.io.IOException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class JavaLongLinesCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws IOException {

		if (absolutePath.endsWith("Table.java") &&
			!absolutePath.endsWith("/Table.java")) {

			return content;
		}

		try (UnsyncBufferedReader unsyncBufferedReader =
				new UnsyncBufferedReader(new UnsyncStringReader(content))) {

			String line = null;

			int lineNumber = 0;

			while ((line = unsyncBufferedReader.readLine()) != null) {
				lineNumber++;

				if (line.matches("\\s*\\*.*") ||
					(getLineLength(line) <= getMaxLineLength())) {

					continue;
				}

				String trimmedLine = StringUtil.trimLeading(line);

				if (!isExcludedPath(
						_LINE_LENGTH_EXCLUDES, absolutePath, lineNumber) &&
					!_isAnnotationParameter(content, trimmedLine) &&
					(trimmedLine.startsWith("//") ||
					 line.contains(StringPool.QUOTE))) {

					addMessage(fileName, "> " + getMaxLineLength(), lineNumber);
				}
			}
		}

		return content;
	}

	private boolean _isAnnotationParameter(String content, String line) {
		int x = -1;

		while (true) {
			x = line.indexOf(StringPool.COMMA_AND_SPACE, x + 1);

			if (x == -1) {
				break;
			}

			if (!ToolsUtil.isInsideQuotes(line, x)) {
				return false;
			}
		}

		Matcher matcher = _annotationPattern.matcher(content);

		while (matcher.find()) {
			x = matcher.end();

			while (true) {
				x = content.indexOf(StringPool.CLOSE_PARENTHESIS, x + 1);

				if (x == -1) {
					break;
				}

				String annotationParameters = content.substring(
					matcher.end() - 2, x + 1);

				if (getLevel(annotationParameters) == 0) {
					if (annotationParameters.contains(line)) {
						return true;
					}

					break;
				}
			}
		}

		return false;
	}

	private static final String _LINE_LENGTH_EXCLUDES = "line.length.excludes";

	private static final Pattern _annotationPattern = Pattern.compile(
		"\n\t*@(.+)\\(\n");

}