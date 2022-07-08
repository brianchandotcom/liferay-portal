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

package com.liferay.source.formatter.check;

import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.check.util.SourceUtil;

import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class MarkdownWhitespaceCheck extends WhitespaceCheck {

	@Override
	protected String formatDoubleSpace(String line) {
		String trimmedLine = StringUtil.trim(line);

		if (trimmedLine.startsWith("*") || trimmedLine.matches("[0-9]+\\..*")) {
			return line;
		}

		return super.formatDoubleSpace(line);
	}

	@Override
	protected String trimLine(
		String fileName, String absolutePath, String content, String line,
		int lineNumber) {

		int[] multiLineStringsPositions = SourceUtil.getMultiLinePositions(
			content, _codeBlockPattern);

		if (SourceUtil.isInsideMultiLines(
				lineNumber, multiLineStringsPositions)) {

			return line;
		}

		return trimLine(fileName, absolutePath, line);
	}

	private static final Pattern _codeBlockPattern = Pattern.compile(
		"```.+?```", Pattern.DOTALL);

}