/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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