/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.io.unsync.UnsyncBufferedReader;
import com.liferay.petra.io.unsync.UnsyncStringReader;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.check.util.SourceUtil;

import java.io.IOException;

import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class MarkdownWhitespaceCheck extends WhitespaceCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws IOException {

		content = _formatWhitespace(fileName, absolutePath, content);

		return content.trim();
	}

	@Override
	protected String formatDoubleSpace(String line) {
		String trimmedLine = StringUtil.trim(line);

		if (trimmedLine.startsWith("*") || trimmedLine.matches("[0-9]+\\..*")) {
			return line;
		}

		return super.formatDoubleSpace(line);
	}

	private String _formatWhitespace(
			String fileName, String absolutePath, String content)
		throws IOException {

		StringBundler sb = new StringBundler();

		try (UnsyncBufferedReader unsyncBufferedReader =
				new UnsyncBufferedReader(new UnsyncStringReader(content))) {

			String line = null;
			int lineNumber = 0;
			int[] multiLineStringsPositions = SourceUtil.getMultiLinePositions(
				content, _codeBlockPattern);

			while ((line = unsyncBufferedReader.readLine()) != null) {
				lineNumber++;

				if (SourceUtil.isInsideMultiLines(
						lineNumber, multiLineStringsPositions)) {

					sb.append(line);
					sb.append(StringPool.NEW_LINE);

					continue;
				}

				sb.append(trimLine(fileName, absolutePath, line));
				sb.append(StringPool.NEW_LINE);
			}
		}

		if (sb.length() > 0) {
			sb.setIndex(sb.index() - 1);
		}

		return sb.toString();
	}

	private static final Pattern _codeBlockPattern = Pattern.compile(
		"```.+?```", Pattern.DOTALL);

}