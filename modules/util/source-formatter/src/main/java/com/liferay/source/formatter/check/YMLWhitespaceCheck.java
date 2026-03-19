/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.io.unsync.UnsyncBufferedReader;
import com.liferay.petra.io.unsync.UnsyncStringReader;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.tools.ToolsUtil;
import com.liferay.source.formatter.check.util.SourceUtil;
import com.liferay.source.formatter.check.util.YMLSourceUtil;

import java.io.IOException;

/**
 * @author Hugo Huijser
 * @author Alan Huang
 */
public class YMLWhitespaceCheck extends WhitespaceCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws IOException {

		if (content.endsWith("\n")) {
			content = content.substring(0, content.length() - 1);
		}

		return _formatWhitespaceAroundBracketAndCurlyBrace(content);
	}

	private String _formatWhitespaceAroundBracketAndCurlyBrace(String content)
		throws IOException {

		StringBundler sb = new StringBundler();

		try (UnsyncBufferedReader unsyncBufferedReader =
				new UnsyncBufferedReader(new UnsyncStringReader(content))) {

			String blockStyleLeadingSpaces = null;
			boolean insideBlockStyle = false;
			String leadingSpaces = null;
			String line = null;

			while ((line = unsyncBufferedReader.readLine()) != null) {
				if (insideBlockStyle) {
					leadingSpaces = SourceUtil.getLeadingSpaces(line);

					if (leadingSpaces.length() >
							blockStyleLeadingSpaces.length()) {

						sb.append(line);
						sb.append("\n");

						continue;
					}
				}

				line = _removeWhitespaceAfterOpenBracket(line);
				line = _removeWhitespaceAfterOpenCurlyBrace(line);
				line = _removeWhitespaceBeforeCloseBracket(line);
				line = _removeWhitespaceBeforeCloseCurlyBrace(line);

				sb.append(line);

				sb.append("\n");

				if (!YMLSourceUtil.isBlockStyle(line)) {
					continue;
				}

				blockStyleLeadingSpaces = SourceUtil.getLeadingSpaces(line);
				insideBlockStyle = true;
			}
		}

		if (sb.length() > 0) {
			sb.setIndex(sb.index() - 1);
		}

		return sb.toString();
	}

	private String _removeWhitespaceAfterOpenBracket(String line) {
		String trimmedLine = line.trim();

		if (trimmedLine.startsWith("elif [") ||
			trimmedLine.startsWith("if [")) {

			return line;
		}

		int x = line.indexOf("[ ");

		if ((x == -1) || ToolsUtil.isInsideQuotes(line, x) ||
			(getLevel(line, "[", "]") != 0)) {

			return line;
		}

		return StringUtil.replaceFirst(
			line, StringPool.SPACE, StringPool.BLANK, x);
	}

	private String _removeWhitespaceAfterOpenCurlyBrace(String line) {
		int x = line.indexOf("{ ");

		if (x == -1) {
			return line;
		}

		if (x == 0) {
			return StringUtil.replaceFirst(
				line, StringPool.SPACE, StringPool.BLANK, 0);
		}

		if (ToolsUtil.isInsideQuotes(line, x) ||
			(getLevel(line, "{", "}") != 0)) {

			return line;
		}

		char c = line.charAt(x - 1);

		if (c == CharPool.OPEN_CURLY_BRACE) {
			return line;
		}

		return StringUtil.replaceFirst(
			line, StringPool.SPACE, StringPool.BLANK, x);
	}

	private String _removeWhitespaceBeforeCloseBracket(String line) {
		String trimmedLine = line.trim();

		if (trimmedLine.startsWith("elif [") ||
			trimmedLine.startsWith("if [")) {

			return line;
		}

		int x = line.indexOf(" ]");

		if ((x == -1) || ToolsUtil.isInsideQuotes(line, x) ||
			(getLevel(line, "[", "]") != 0)) {

			return line;
		}

		return StringUtil.replaceFirst(
			line, StringPool.SPACE, StringPool.BLANK, x);
	}

	private String _removeWhitespaceBeforeCloseCurlyBrace(String line) {
		int x = line.indexOf(" }");

		if ((x == -1) || ToolsUtil.isInsideQuotes(line, x) ||
			(getLevel(line, "{", "}") != 0)) {

			return line;
		}

		if (x == (line.length() - 2)) {
			return StringUtil.replaceFirst(
				line, StringPool.SPACE, StringPool.BLANK, x);
		}

		char c = line.charAt(x + 2);

		if (c == CharPool.CLOSE_CURLY_BRACE) {
			return line;
		}

		return StringUtil.replaceFirst(
			line, StringPool.SPACE, StringPool.BLANK, x);
	}

}