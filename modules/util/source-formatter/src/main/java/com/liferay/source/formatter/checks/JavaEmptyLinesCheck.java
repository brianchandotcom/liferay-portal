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
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Tuple;
import com.liferay.portal.tools.ToolsUtil;

import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class JavaEmptyLinesCheck extends BaseFileCheck {

	@Override
	public Tuple process(String fileName, String absolutePath, String content)
		throws Exception {

		if (hasGeneratedTag(content)) {
			return new Tuple(content, Collections.emptySet());
		}

		content = _fixMissingEmptyLines(content);

		content = _fixRedundantEmptyLines(content);

		content = _fixIncorrectEmptyLineBeforeCloseCurlyBrace(
			content, fileName);

		content = _fixMissingEmptyLineAfterSettingVariable(content);

		return new Tuple(content, Collections.emptySet());
	}

	private String _fixIncorrectEmptyLineBeforeCloseCurlyBrace(
		String content, String fileName) {

		Matcher matcher1 = _incorrectCloseCurlyBracePattern1.matcher(content);

		while (matcher1.find()) {
			String lastLine = StringUtil.trimLeading(matcher1.group(1));

			if (lastLine.startsWith("// ")) {
				continue;
			}

			String tabs = matcher1.group(2);

			int tabCount = tabs.length();

			int pos = matcher1.start();

			while (true) {
				pos = content.lastIndexOf("\n" + tabs, pos - 1);

				if (content.charAt(pos + tabCount + 1) == CharPool.TAB) {
					continue;
				}

				String codeBlock = content.substring(pos + 1, matcher1.end());

				String firstLine = codeBlock.substring(
					0, codeBlock.indexOf(CharPool.NEW_LINE) + 1);

				Matcher matcher2 = _incorrectCloseCurlyBracePattern2.matcher(
					firstLine);

				if (matcher2.find()) {
					break;
				}

				return StringUtil.replaceFirst(
					content, "\n\n" + tabs + "}\n", "\n" + tabs + "}\n", pos);
			}
		}

		return content;
	}

	private String _fixMissingEmptyLineAfterSettingVariable(String content) {
		Matcher matcher = _setVariablePattern.matcher(content);

		while (matcher.find()) {
			if (content.charAt(matcher.end()) == CharPool.NEW_LINE) {
				continue;
			}

			int x = content.indexOf(";\n", matcher.end());

			if (x == -1) {
				return content;
			}

			String nextCommand = content.substring(matcher.end() - 1, x + 1);

			if (nextCommand.contains("{\n")) {
				continue;
			}

			String variableName = matcher.group(1);

			Pattern pattern2 = Pattern.compile("\\W(" + variableName + ")\\.");

			Matcher matcher2 = pattern2.matcher(nextCommand);

			if (!matcher2.find()) {
				continue;
			}

			x = matcher2.start(1);

			if (ToolsUtil.isInsideQuotes(nextCommand, x)) {
				continue;
			}

			x += matcher.end();

			int y = content.lastIndexOf("\ttry (", x);

			if (y != -1) {
				int z = content.indexOf(") {\n", y);

				if (z > x) {
					continue;
				}
			}

			return StringUtil.replaceFirst(
				content, "\n", "\n\n", matcher.end(2));
		}

		return content;
	}

	private String _fixMissingEmptyLines(String content) {
		Matcher matcher = _missingEmptyLinePattern1.matcher(content);

		while (matcher.find()) {
			if (getLevel(matcher.group()) == 0) {
				return StringUtil.replaceFirst(
					content, "\n", "\n\n", matcher.start());
			}
		}

		matcher = _missingEmptyLinePattern2.matcher(content);

		while (matcher.find()) {
			String match = matcher.group();

			if (!match.contains(StringPool.OPEN_PARENTHESIS)) {
				continue;
			}

			String whitespace = matcher.group(1);

			int x = content.indexOf(
				whitespace + StringPool.CLOSE_CURLY_BRACE + "\n",
				matcher.end());
			int y = content.indexOf(
				whitespace + StringPool.CLOSE_CURLY_BRACE + "\n\n",
				matcher.end());

			if ((x != -1) && (x != y)) {
				return StringUtil.replaceFirst(content, "\n", "\n\n", x + 1);
			}
		}

		matcher = _missingEmptyLinePattern3.matcher(content);

		while (matcher.find()) {
			if ((getLevel(matcher.group()) != 0) &&
				(content.charAt(matcher.end()) != CharPool.NEW_LINE)) {

				return StringUtil.replaceFirst(
					content, "\n", "\n\n", matcher.end() - 1);
			}
		}

		return content;
	}

	private String _fixRedundantEmptyLines(String content) {
		Matcher matcher = _redundantEmptyLines1.matcher(content);

		if (matcher.find()) {
			return StringUtil.replaceFirst(
				content, "\n", StringPool.BLANK, matcher.start());
		}

		matcher = _redundantEmptyLines2.matcher(content);

		if (matcher.find()) {
			return StringUtil.replaceFirst(
				content, "\n", StringPool.BLANK, matcher.end() - 1);
		}

		return content;
	}

	private final Pattern _incorrectCloseCurlyBracePattern1 = Pattern.compile(
		"\n(.+)\n\n(\t+)}\n");
	private final Pattern _incorrectCloseCurlyBracePattern2 = Pattern.compile(
		"(\t| )@?(class|enum|interface|new)\\s");
	private final Pattern _missingEmptyLinePattern1 = Pattern.compile(
		"(\t| = |return )new .*\\(.*\\) \\{\n\t+[^{\t]");
	private final Pattern _missingEmptyLinePattern2 = Pattern.compile(
		"(\n\t*)(public|private|protected) [^;]+? \\{");
	private final Pattern _missingEmptyLinePattern3 = Pattern.compile(
		"\n.*\\) \\{\n");
	private final Pattern _redundantEmptyLines1 = Pattern.compile(
		"\n\npublic ((abstract|static) )*(class|enum|interface) ");
	private final Pattern _redundantEmptyLines2 = Pattern.compile(
		" \\* @author .*\n \\*\\/\n\n");
	private final Pattern _setVariablePattern = Pattern.compile(
		"\t[A-Z]\\w+ (\\w+) =\\s+((?!\\{\n).)*?;\n", Pattern.DOTALL);

}