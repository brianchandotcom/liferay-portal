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

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.tools.ToolsUtil;
import com.liferay.source.formatter.checks.util.PoshiSourceUtil;
import com.liferay.source.formatter.checks.util.SourceUtil;

import java.io.IOException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Alan Huang
 */
public class PoshiWhitespaceCheck extends WhitespaceCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws IOException {

		content = _formatWhitespace(content);
		content = _formatWhitespace(fileName, absolutePath, content);
		content = _formatWhitespaceOnCommand(content);

		return super.doProcess(fileName, absolutePath, content);
	}

	private String _formatWhitespace(String content) {
		int[] multiLineCommentsPositions =
			PoshiSourceUtil.getMultiLinePositions(
				content, _multiLineCommentsPattern);
		int[] multiLineStringPositions = PoshiSourceUtil.getMultiLinePositions(
			content, _multiLineStringPattern);

		Matcher matcher = _incorrectWhitespacePattern.matcher(content);

		while (matcher.find()) {
			String match = matcher.group(1);

			int pos = matcher.start(1);

			if (pos == -1) {
				match = matcher.group(2);

				pos = matcher.start(2);
			}

			if (!ToolsUtil.isInsideQuotes(content, pos) &&
				!PoshiSourceUtil.isInsideMultiLines(
					getLineNumber(content, pos), multiLineCommentsPositions) &&
				!PoshiSourceUtil.isInsideMultiLines(
					getLineNumber(content, pos), multiLineStringPositions)) {

				return StringUtil.replaceFirst(
					content, match, StringPool.BLANK, matcher.start());
			}
		}

		return content;
	}

	private String _formatWhitespace(
			String fileName, String absolutePath, String content)
		throws IOException {

		int[] multiLineCommentsPositions =
			PoshiSourceUtil.getMultiLinePositions(
				content, _multiLineCommentsPattern);
		int[] multiLineStringPositions = PoshiSourceUtil.getMultiLinePositions(
			content, _multiLineStringPattern);

		StringBundler sb = new StringBundler();

		try (UnsyncBufferedReader unsyncBufferedReader =
				new UnsyncBufferedReader(new UnsyncStringReader(content))) {

			int lineNumber = 0;

			String line = null;

			while ((line = unsyncBufferedReader.readLine()) != null) {
				lineNumber++;

				line = trimLine(fileName, absolutePath, line);

				if (PoshiSourceUtil.isInsideMultiLines(
						lineNumber, multiLineCommentsPositions) ||
					PoshiSourceUtil.isInsideMultiLines(
						lineNumber, multiLineStringPositions)) {

					sb.append(line);
					sb.append("\n");

					continue;
				}

				String trimmedLine = StringUtil.trimLeading(line);

				line = formatWhitespace(line, trimmedLine, false);

				line = formatIncorrectSyntax(line, "){", ") {", false);
				line = formatIncorrectSyntax(line, "for \\([^:]*[^:\"'\\s](:)");
				line = formatIncorrectSyntax(line, "for \\([^:]*:([^:\"'\\s])");

				sb.append(line);

				sb.append("\n");
			}
		}

		return sb.toString();
	}

	private String _formatWhitespaceOnCommand(String content) {
		int[] multiLineCommentsPositions =
			PoshiSourceUtil.getMultiLinePositions(
				content, _multiLineCommentsPattern);
		int[] multiLineStringPositions = PoshiSourceUtil.getMultiLinePositions(
			content, _multiLineStringPattern);

		Matcher matcher = _incorrectCommandPattern.matcher(content);

		while (matcher.find()) {
			int lineNumber = SourceUtil.getLineNumber(content, matcher.start());

			if (!PoshiSourceUtil.isInsideMultiLines(
					lineNumber, multiLineCommentsPositions) &&
				!PoshiSourceUtil.isInsideMultiLines(
					lineNumber, multiLineStringPositions)) {

				return StringUtil.replaceFirst(
					content, matcher.group(),
					StringBundler.concat(
						matcher.group(1), StringPool.SPACE, matcher.group(3),
						StringPool.SPACE, matcher.group(4)),
					matcher.start());
			}
		}

		return content;
	}

	private static final Pattern _incorrectCommandPattern = Pattern.compile(
		"(\n\t*(function|macro|test))(?! \\w+ \\{)[\t ]+(\\w+)\\s*(\\{)");
	private static final Pattern _incorrectWhitespacePattern = Pattern.compile(
		"\\)(\\s+);|(\n\t*)\\{");
	private static final Pattern _multiLineCommentsPattern = Pattern.compile(
		"[ \t]/\\*.*?\\*/", Pattern.DOTALL);
	private static final Pattern _multiLineStringPattern = Pattern.compile(
		"'''.*?'''", Pattern.DOTALL);

}