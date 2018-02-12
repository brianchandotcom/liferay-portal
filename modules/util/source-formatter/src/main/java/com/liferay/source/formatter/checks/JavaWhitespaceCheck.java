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

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Hugo Huijser
 */
public class JavaWhitespaceCheck extends WhitespaceCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws Exception {

		content = _formatWhitespace(fileName, content);

		content = StringUtil.replace(content, "\n\n\n", "\n\n");

		return content;
	}

	private String _formatWhitespace(String fileName, String content)
		throws Exception {

		StringBundler sb = new StringBundler();

		try (UnsyncBufferedReader unsyncBufferedReader =
				new UnsyncBufferedReader(new UnsyncStringReader(content))) {

			String line = null;
			String previousLine = StringPool.BLANK;

			while ((line = unsyncBufferedReader.readLine()) != null) {
				line = trimLine(fileName, line);

				String trimmedLine = StringUtil.trimLeading(line);

				if (trimmedLine.startsWith("*\t")) {
					line = StringUtil.replaceFirst(line, "*\t", "* ");
				}

				if (trimmedLine.startsWith(StringPool.DOUBLE_SLASH) ||
					trimmedLine.startsWith(StringPool.STAR)) {

					sb.append(line);
					sb.append("\n");

					previousLine = line;

					continue;
				}

				if (line.contains("\t ") && !previousLine.matches(".*[&|^]") &&
					!previousLine.contains("\t((") &&
					!previousLine.contains("\t<") &&
					!previousLine.contains("\t ") &&
					!previousLine.contains("\tfor (") &&
					!previousLine.contains("\timplements ") &&
					!previousLine.contains("\tthrows ")) {

					line = StringUtil.replace(line, "\t ", "\t");
				}

				line = formatIncorrectSyntax(line, ",}", "}", false);

				line = formatWhitespace(line, trimmedLine, true);

				if (!trimmedLine.equals("{") && line.endsWith("{") &&
					!line.endsWith(" {")) {

					line = StringUtil.replaceLast(
						line, CharPool.OPEN_CURLY_BRACE, " {");
				}

				int lineLeadingTabCount = getLeadingTabCount(line);
				int previousLineLeadingTabCount = getLeadingTabCount(
					previousLine);

				if (((lineLeadingTabCount - 2) ==
						previousLineLeadingTabCount) &&
					(previousLineLeadingTabCount > 0) &&
					line.endsWith(StringPool.SEMICOLON) &&
					!previousLine.contains("\tfor (") &&
					!previousLine.contains("\ttry (")) {

					line = StringUtil.replaceFirst(
						line, CharPool.TAB, StringPool.BLANK);
				}

				sb.append(line);
				sb.append("\n");

				previousLine = line;
			}
		}

		content = sb.toString();

		if (content.endsWith("\n")) {
			content = content.substring(0, content.length() - 1);
		}

		return content;
	}

}