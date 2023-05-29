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

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Alan Huang
 */
public class PoshiIndentationCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		return _fixTripleQuotesIndentation(content);
	}

	private String _fixCurlIndentation(
		String tripleQuotesContent, String indent) {

		String trimmedContent = tripleQuotesContent.trim();

		String[] lines = trimmedContent.split("\n");

		if (lines.length < 2) {
			return tripleQuotesContent;
		}

		int leadingTabCount = indent.length();

		lines[0] = _fixTabs(leadingTabCount + 1, lines[0]);

		int level = leadingTabCount + 2;

		for (int i = 1; i < lines.length; i++) {
			String trimmedLine = StringUtil.trim(lines[i]);

			lines[i] = _fixTabs(level, trimmedLine);

			trimmedLine = trimmedLine.replaceAll(
				"(.*?)\\$\\{.*?\\}(.*)", "$1$2");

			level += getLevel(trimmedLine, "[{", "}]");

			trimmedLine = StringUtil.removeSubstrings(trimmedLine, "[{", "}]");

			level += getLevel(
				trimmedLine,
				new String[] {
					StringPool.OPEN_BRACKET, StringPool.OPEN_CURLY_BRACE
				},
				new String[] {
					StringPool.CLOSE_BRACKET, StringPool.CLOSE_CURLY_BRACE
				});

			if (trimmedLine.endsWith("'{")) {
				level++;
			}
		}

		StringBundler sb = new StringBundler((lines.length * 2) + 2);

		for (String line : lines) {
			sb.append(CharPool.NEW_LINE);
			sb.append(line);
		}

		sb.append(CharPool.NEW_LINE);
		sb.append(indent);

		return sb.toString();
	}

	private String _fixTableIndentation(
		String tripleQuotesContent, String indent) {

		String trimmedContent = tripleQuotesContent.trim();

		if (!trimmedContent.startsWith("|") && !trimmedContent.endsWith("|")) {
			return tripleQuotesContent;
		}

		String[] lines = trimmedContent.split("\n");

		for (int i = 0; i < lines.length; i++) {
			if (Validator.isNull(lines[i])) {
				continue;
			}

			String trimmedLine = StringUtil.trim(lines[i]);

			if (!trimmedLine.startsWith("|") && !trimmedLine.endsWith("|")) {
				return tripleQuotesContent;
			}

			lines[i] = indent + StringPool.TAB + trimmedLine;
		}

		StringBundler sb = new StringBundler((lines.length * 2) + 2);

		for (String line : lines) {
			sb.append(CharPool.NEW_LINE);
			sb.append(line);
		}

		sb.append(CharPool.NEW_LINE);
		sb.append(indent);

		return sb.toString();
	}

	private String _fixTabs(int expectedTabCount, String line) {
		if (line.equals("]") || line.equals("]'") || line.equals("],") ||
			line.equals("}") || line.equals("}'") || line.equals("},") ||
			line.equals("}]") || line.equals("}]'") || line.equals("}],")) {

			expectedTabCount--;
		}

		StringBundler sb = new StringBundler(expectedTabCount + 1);

		for (int i = 0; i < expectedTabCount; i++) {
			sb.append(CharPool.TAB);
		}

		sb.append(StringUtil.trim(line));

		return sb.toString();
	}

	private String _fixTripleQuotesIndentation(String content) {
		StringBuffer sb = new StringBuffer();

		Matcher matcher = _tripleQuotesPattern.matcher(content);

		while (matcher.find()) {
			String replacement = null;
			String variableName = matcher.group(3);

			if (variableName.matches("(?i)\\w*curl")) {
				replacement = _fixCurlIndentation(
					matcher.group(4), matcher.group(1));
			}
			else if (variableName.equals("table")) {
				replacement = _fixTableIndentation(
					matcher.group(4), matcher.group(1));
			}

			if ((replacement != null) &&
				!StringUtil.equals(matcher.group(4), replacement)) {

				matcher.appendReplacement(
					sb,
					Matcher.quoteReplacement(
						StringUtil.replaceFirst(
							matcher.group(), matcher.group(4), replacement)));
			}
		}

		if (sb.length() > 0) {
			matcher.appendTail(sb);

			return sb.toString();
		}

		return content;
	}

	private static final Pattern _tripleQuotesPattern = Pattern.compile(
		"(?:\n)(\t+)(var )?(\\w+) = '''(.+?)'''[,;)]", Pattern.DOTALL);

}