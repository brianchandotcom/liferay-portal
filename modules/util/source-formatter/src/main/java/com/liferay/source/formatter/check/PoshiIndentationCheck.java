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

		Matcher matcher = _curlPattern.matcher(content);

		while (matcher.find()) {
			if (StringUtil.contains(matcher.group(1), "'''")) {
				continue;
			}

			String match = matcher.group();

			String[] lines = match.split("\n");

			int leadingTabCount = getLeadingTabCount(lines[0]);

			lines[1] = _fixTabs(leadingTabCount + 1, lines[1]);
			lines[lines.length - 1] = _fixTabs(
				leadingTabCount, lines[lines.length - 1]);

			int level = leadingTabCount + 2;

			for (int i = 2; i < (lines.length - 1); i++) {
				String trimmedLine = StringUtil.trim(lines[i]);

				lines[i] = _fixTabs(level, trimmedLine);

				trimmedLine = trimmedLine.replaceAll(
					"(.*?)\\$\\{.*?\\}(.*)", "$1$2");

				level += getLevel(trimmedLine, "[{", "}]");

				trimmedLine = StringUtil.removeSubstrings(
					trimmedLine, "[{", "}]");

				level += getLevel(
					trimmedLine,
					new String[] {
						StringPool.OPEN_CURLY_BRACE, StringPool.OPEN_BRACKET
					},
					new String[] {
						StringPool.CLOSE_CURLY_BRACE, StringPool.CLOSE_BRACKET
					});

				if (trimmedLine.endsWith("'{")) {
					level++;
				}
			}

			StringBundler sb = new StringBundler(lines.length * 2);

			for (String line : lines) {
				if (Validator.isNull(line.trim())) {
					continue;
				}

				sb.append(line);
				sb.append(CharPool.NEW_LINE);
			}

			sb.setIndex(sb.index() - 1);

			String replacement = sb.toString();

			if (!match.equals(replacement)) {
				return StringUtil.replaceFirst(
					content, match, replacement, matcher.start());
			}
		}

		return content;
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

	private static final Pattern _curlPattern = Pattern.compile(
		"(?<=\n)\t*var curl = '''(\n.*?\n\t*)''';(?=\n)", Pattern.DOTALL);

}