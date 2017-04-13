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

import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.tools.ToolsUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class FTLTagCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		content = _formatMultiParameterTags(content);
		content = _formatSingleParameterTags(content);

		return _formatAssignTags(content);
	}

	private String _formatAssignTags(String content) {
		Matcher matcher = _incorrectAssignTagPattern.matcher(content);

		content = matcher.replaceAll("$1 />\n");

		matcher = _assignTagsBlockPattern.matcher(content);

		while (matcher.find()) {
			String match = matcher.group();

			String tabs = matcher.group(2);

			String replacement = StringUtil.removeSubstrings(
				match, "<#assign ", "<#assign\n", " />", "\n/>", "\t/>");

			replacement = StringUtil.removeChar(replacement, CharPool.TAB);

			String[] lines = StringUtil.splitLines(replacement);

			StringBundler sb = new StringBundler((3 * lines.length) + 5);

			sb.append(tabs);
			sb.append("<#assign");

			for (String line : lines) {
				sb.append("\n\t");
				sb.append(tabs);
				sb.append(line);
			}

			sb.append(StringPool.NEW_LINE);
			sb.append(tabs);
			sb.append("/>\n\n");

			content = StringUtil.replace(content, match, sb.toString());
		}

		return content;
	}

	private String _formatMultiParameterTags(String content) {
		Matcher matcher = _multiParameterTagPattern.matcher(content);

		while (matcher.find()) {
			String match = matcher.group();

			if (match.contains("><")) {
				continue;
			}

			String strippedMatch = stripQuotes(match, CharPool.QUOTE);

			if (StringUtil.count(strippedMatch, CharPool.EQUAL) <= 1) {
				continue;
			}

			String replacement = match;

			String tabs = matcher.group(1);

			int x = -1;

			while (true) {
				x = replacement.indexOf(
					StringPool.EQUAL, x + tabs.length() + 2);

				if (x == -1) {
					break;
				}

				if (ToolsUtil.isInsideQuotes(replacement, x)) {
					continue;
				}

				int y = replacement.lastIndexOf(StringPool.SPACE, x);

				if (y == -1) {
					break;
				}

				replacement =
					replacement.substring(0, y) + StringPool.NEW_LINE + tabs +
						StringPool.TAB + replacement.substring(y + 1);
			}

			if (!match.equals(replacement)) {
				replacement = StringUtil.replaceLast(
					replacement, "/>", StringPool.NEW_LINE + tabs + "/>");

				content = StringUtil.replace(content, match, replacement);
			}
		}

		return content;
	}

	private String _formatSingleParameterTags(String content) {
		Matcher matcher = _singleParameterTagPattern.matcher(content);

		while (matcher.find()) {
			String match = matcher.group();

			String replacement = match;

			String group1 = matcher.group(1);
			String group2 = matcher.group(2);

			if (group2 != null) {
				replacement = StringUtil.replaceFirst(
					replacement, group1 + StringPool.SPACE, group1);
			}

			String group3 = matcher.group(3);

			if (group3.startsWith(StringPool.SPACE)) {
				replacement = StringUtil.replaceLast(
					replacement, group3, group3.substring(1));
			}

			content = StringUtil.replace(content, match, replacement);
		}

		return content;
	}

	private final Pattern _assignTagsBlockPattern = Pattern.compile(
		"((\t*)<#assign[^<#/>]*=[^<#/>]*/>(\n|$)+){2,}", Pattern.MULTILINE);
	private final Pattern _incorrectAssignTagPattern = Pattern.compile(
		"(<#assign .*=.*[^/])>(\n|$)");
	private final Pattern _multiParameterTagPattern = Pattern.compile(
		"\n(\t*)<@.+=.+=.+/>");
	private final Pattern _singleParameterTagPattern = Pattern.compile(
		"(<@[\\w\\.]+ \\w+)( )?=([^=]+?)/>");

}