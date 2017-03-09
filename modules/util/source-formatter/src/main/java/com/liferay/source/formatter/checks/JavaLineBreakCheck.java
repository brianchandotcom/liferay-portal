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

import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Tuple;
import com.liferay.portal.tools.ToolsUtil;
import com.liferay.source.formatter.SourceFormatterArgs;
import com.liferay.source.formatter.SourceFormatterMessage;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class JavaLineBreakCheck extends BaseFileCheck {

	public JavaLineBreakCheck(SourceFormatterArgs sourceFormatterArgs) {
		_sourceFormatterArgs = sourceFormatterArgs;
	}

	@Override
	public Tuple process(String fileName, String absolutePath, String content)
		throws Exception {

		if (hasGeneratedTag(content)) {
			return new Tuple(content, Collections.emptySet());
		}

		Set<SourceFormatterMessage> sourceFormatterMessages = new HashSet<>();

		try (UnsyncBufferedReader unsyncBufferedReader =
				new UnsyncBufferedReader(new UnsyncStringReader(content))) {

			String line = null;
			String previousLine = StringPool.BLANK;

			int lineCount = 0;

			while ((line = unsyncBufferedReader.readLine()) != null) {
				lineCount++;

				String trimmedLine = StringUtil.trimLeading(line);

				if (trimmedLine.startsWith(StringPool.SEMICOLON)) {
					addMessage(
						sourceFormatterMessages, fileName,
						"Line should not start with ';'", lineCount);
				}

				if (trimmedLine.startsWith(StringPool.EQUAL)) {
					addMessage(
						sourceFormatterMessages, fileName,
						"Line should not start with '='", lineCount);
				}

				if (!trimmedLine.startsWith(StringPool.DOUBLE_SLASH) &&
					!trimmedLine.startsWith(StringPool.STAR)) {

					if (trimmedLine.startsWith(StringPool.PERIOD)) {
						addMessage(
							sourceFormatterMessages, fileName,
							"Line should not start with '.'", lineCount);
					}

					if (previousLine.endsWith(StringPool.OPEN_PARENTHESIS) &&
						trimmedLine.startsWith(StringPool.CLOSE_PARENTHESIS)) {

						addMessage(
							sourceFormatterMessages, fileName,
							"Line should not start with ')'", lineCount);
					}
				}

				int lineLength = getLineLength(line);

				if (!line.startsWith("import ") &&
					!line.startsWith("package ") &&
					!line.matches("\\s*\\*.*") &&
					(lineLength <= _sourceFormatterArgs.getMaxLineLength())) {

					_checkLineBreaks(
						sourceFormatterMessages, line, previousLine, fileName,
						lineCount);
				}

				previousLine = line;
			}
		}

		content = _fixIncorrectLineBreaks(
			sourceFormatterMessages, content, fileName);

		content = _fixLineStartingWithCloseParenthesis(
			sourceFormatterMessages, content, fileName);

		content = _fixMultiLineComment(content);

		return new Tuple(content, sourceFormatterMessages);
	}

	private void _checkLineBreaks(
		Set<SourceFormatterMessage> sourceFormatterMessages, String line,
		String previousLine, String fileName, int lineCount) {

		String trimmedLine = StringUtil.trimLeading(line);

		if (previousLine.contains("\t/*") || trimmedLine.startsWith("//") ||
			trimmedLine.endsWith("*/")) {

			return;
		}

		if (trimmedLine.startsWith("},") && !trimmedLine.equals("},")) {
			addMessage(
				sourceFormatterMessages, fileName,
				"There should be a line break after '},'", lineCount);
		}

		int lineLeadingTabCount = getLeadingTabCount(line);
		int previousLineLeadingTabCount = getLeadingTabCount(previousLine);

		if (previousLine.endsWith(StringPool.COMMA) &&
			previousLine.contains(StringPool.OPEN_PARENTHESIS) &&
			!previousLine.contains("for (") &&
			(lineLeadingTabCount > previousLineLeadingTabCount)) {

			addMessage(
				sourceFormatterMessages, fileName,
				"There should be a line break after '('", lineCount - 1);
		}

		if (previousLine.endsWith(StringPool.PERIOD)) {
			int x = trimmedLine.indexOf(CharPool.OPEN_PARENTHESIS);

			if ((x != -1) &&
				((getLineLength(previousLine) + x) <
					_sourceFormatterArgs.getMaxLineLength()) &&
				(trimmedLine.endsWith(StringPool.OPEN_PARENTHESIS) ||
				 (trimmedLine.charAt(x + 1) != CharPool.CLOSE_PARENTHESIS))) {

				addMessage(
					sourceFormatterMessages, fileName, "Incorrect line break",
					lineCount);
			}
		}

		String strippedQuotesLine = stripQuotes(trimmedLine);

		int strippedQuotesLineOpenParenthesisCount = StringUtil.count(
			strippedQuotesLine, CharPool.OPEN_PARENTHESIS);

		if (!trimmedLine.startsWith(StringPool.OPEN_PARENTHESIS) &&
			trimmedLine.endsWith(") {") &&
			(strippedQuotesLineOpenParenthesisCount > 0) &&
			(getLevel(trimmedLine) > 0)) {

			addMessage(
				sourceFormatterMessages, fileName, "Incorrect line break",
				lineCount);
		}

		if (line.endsWith(StringPool.OPEN_PARENTHESIS)) {
			int x = line.lastIndexOf(" && ");
			int y = line.lastIndexOf(" || ");

			int z = Math.max(x, y);

			if (z != -1) {
				addMessage(
					sourceFormatterMessages, fileName,
					"There should be a line break after '" +
						line.substring(z + 1, z + 3) + "'",
					lineCount);
			}

			int pos = strippedQuotesLine.indexOf(" + ");

			if (pos != -1) {
				String linePart = strippedQuotesLine.substring(0, pos);

				if ((getLevel(linePart, "(", ")") == 0) &&
					(getLevel(linePart, "[", "]") == 0)) {

					addMessage(
						sourceFormatterMessages, fileName,
						"There should be a line break after '+'", lineCount);
				}
			}

			x = trimmedLine.length() + 1;

			while (true) {
				x = trimmedLine.lastIndexOf(StringPool.COMMA, x - 1);

				if (x == -1) {
					break;
				}

				if (ToolsUtil.isInsideQuotes(trimmedLine, x)) {
					continue;
				}

				String linePart = trimmedLine.substring(x);

				if ((getLevel(linePart) == 1) &&
					(getLevel(linePart, "<", ">") == 0)) {

					addMessage(
						sourceFormatterMessages, fileName,
						"There should be a line break after '" +
							trimmedLine.substring(0, x + 1) + "'",
						lineCount);

					break;
				}
			}
		}

		if (trimmedLine.matches("\\)\\..*\\([^)].*")) {
			int pos = trimmedLine.indexOf(StringPool.OPEN_PARENTHESIS);

			addMessage(
				sourceFormatterMessages, fileName,
				"There should be a line break after '" +
					trimmedLine.substring(0, pos + 1) + "'",
				lineCount);
		}

		if (trimmedLine.matches("^[^(].*\\+$") && (getLevel(trimmedLine) > 0)) {
			addMessage(
				sourceFormatterMessages, fileName,
				"There should be a line break after '('", lineCount);
		}

		if (!trimmedLine.contains("\t//") && !line.endsWith("{") &&
			strippedQuotesLine.contains("{") &&
			!strippedQuotesLine.contains("}")) {

			addMessage(
				sourceFormatterMessages, fileName,
				"There should be a line break after '{'", lineCount);
		}

		if (previousLine.endsWith(StringPool.OPEN_PARENTHESIS) ||
			previousLine.endsWith(StringPool.PLUS)) {

			int x = -1;

			while (true) {
				x = trimmedLine.indexOf(StringPool.COMMA_AND_SPACE, x + 1);

				if (x == -1) {
					break;
				}

				if (ToolsUtil.isInsideQuotes(trimmedLine, x)) {
					continue;
				}

				String linePart = trimmedLine.substring(0, x + 1);

				int level = getLevel(linePart);

				if ((previousLine.endsWith(StringPool.OPEN_PARENTHESIS) &&
					 (level < 0)) ||
					(previousLine.endsWith(StringPool.PLUS) && (level <= 0))) {

					addMessage(
						sourceFormatterMessages, fileName,
						"There should be a line break after '" + linePart + "'",
						lineCount);
				}
			}
		}

		int x = trimmedLine.indexOf(StringPool.COMMA_AND_SPACE);

		if (x != -1) {
			if (!ToolsUtil.isInsideQuotes(trimmedLine, x)) {
				String linePart = trimmedLine.substring(0, x + 1);

				if (getLevel(linePart) < 0) {
					addMessage(
						sourceFormatterMessages, fileName,
						"There should be a line break after '" + linePart + "'",
						lineCount);
				}
			}
		}
		else if (trimmedLine.endsWith(StringPool.COMMA) &&
				 !trimmedLine.startsWith("for (")) {

			if (getLevel(trimmedLine) > 0) {
				addMessage(
					sourceFormatterMessages, fileName, "Incorrect line break",
					lineCount);
			}
		}

		if (line.endsWith(" +") || line.endsWith(" -") || line.endsWith(" *") ||
			line.endsWith(" /")) {

			x = line.indexOf(" = ");

			if (x != -1) {
				int y = line.indexOf(CharPool.QUOTE);

				if ((y == -1) || (x < y)) {
					addMessage(
						sourceFormatterMessages, fileName,
						"There should be a line break after '='", lineCount);
				}
			}
		}

		if (line.endsWith(" throws") ||
			((previousLine.endsWith(StringPool.COMMA) ||
			  previousLine.endsWith(StringPool.OPEN_PARENTHESIS)) &&
			 line.contains(" throws ") &&
			 (line.endsWith(StringPool.OPEN_CURLY_BRACE) ||
			  line.endsWith(StringPool.SEMICOLON)))) {

			addMessage(
				sourceFormatterMessages, fileName,
				"There should be a line break before 'throws'", lineCount);
		}

		if (line.endsWith(StringPool.PERIOD) &&
			line.contains(StringPool.EQUAL)) {

			addMessage(
				sourceFormatterMessages, fileName,
				"There should be a line break after '='", lineCount);
		}

		if (trimmedLine.matches("^\\} (catch|else|finally) .*")) {
			addMessage(
				sourceFormatterMessages, fileName,
				"There should be a line break after '}'", lineCount);
		}
	}

	private String _fixIncorrectLineBreaks(
		Set<SourceFormatterMessage> sourceFormatterMessages, String content,
		String fileName) {

		while (true) {
			Matcher matcher = _incorrectLineBreakPattern1.matcher(content);

			if (matcher.find()) {
				content = StringUtil.replaceFirst(
					content, StringPool.NEW_LINE, StringPool.BLANK,
					matcher.start());

				continue;
			}

			matcher = _incorrectLineBreakPattern2.matcher(content);

			if (matcher.find()) {
				content = StringUtil.replaceFirst(
					content, StringPool.NEW_LINE, StringPool.BLANK,
					matcher.start());

				continue;
			}

			matcher = _incorrectLineBreakPattern4.matcher(content);

			while (matcher.find()) {
				String matchingLine = matcher.group(2);

				if (!matchingLine.startsWith(StringPool.DOUBLE_SLASH) &&
					!matchingLine.startsWith(StringPool.STAR)) {

					content = StringUtil.replaceFirst(
						content, matcher.group(3),
						"\n" + matcher.group(1) + "}\n", matcher.start(3) - 1);

					break;
				}
			}

			matcher = _incorrectLineBreakPattern5.matcher(content);

			while (matcher.find()) {
				String tabs = matcher.group(2);

				Pattern pattern = Pattern.compile(
					"\n" + tabs + "([^\t]{2})(?!.*\n" + tabs + "[^\t])",
					Pattern.DOTALL);

				Matcher matcher2 = pattern.matcher(
					content.substring(0, matcher.start(2)));

				if (matcher2.find()) {
					String match = matcher2.group(1);

					if (!match.equals(").")) {
						content = StringUtil.replaceFirst(
							content, "\n" + matcher.group(2), StringPool.BLANK,
							matcher.end(1));

						break;
					}
				}
			}

			matcher = _incorrectLineBreakPattern6.matcher(content);

			if (matcher.find()) {
				content = StringUtil.replaceFirst(
					content, "{", "{\n" + matcher.group(1) + "\t",
					matcher.start());
			}

			matcher = _incorrectLineBreakPattern7.matcher(content);

			while (matcher.find()) {
				if (content.charAt(matcher.end()) != CharPool.NEW_LINE) {
					continue;
				}

				String singleLine =
					matcher.group(1) +
						StringUtil.trimLeading(matcher.group(2)) +
							matcher.group(3);

				if (getLineLength(singleLine) <=
						_sourceFormatterArgs.getMaxLineLength()) {

					content = StringUtil.replace(
						content, matcher.group(), "\n" + singleLine);

					break;
				}
			}

			matcher = _redundantCommaPattern.matcher(content);

			if (matcher.find()) {
				content = StringUtil.replaceFirst(
					content, StringPool.COMMA, StringPool.BLANK,
					matcher.start());

				continue;
			}

			break;
		}

		Matcher matcher = _incorrectLineBreakPattern3.matcher(content);

		while (matcher.find()) {
			if (getLevel(matcher.group()) == 0) {
				int lineCount = getLineCount(content, matcher.start());

				addMessage(
					sourceFormatterMessages, fileName,
					"There should be a line break before '" + matcher.group(1) +
						"'",
					lineCount);
			}
		}

		return content;
	}

	private String _fixLineStartingWithCloseParenthesis(
		Set<SourceFormatterMessage> sourceFormatterMessages, String content,
		String fileName) {

		Matcher matcher = _lineStartingWithOpenParenthesisPattern.matcher(
			content);

		while (matcher.find()) {
			String tabs = matcher.group(2);

			int lineCount = getLineCount(content, matcher.start(2));

			String lastCharacterPreviousLine = matcher.group(1);

			if (lastCharacterPreviousLine.equals(StringPool.OPEN_PARENTHESIS)) {
				addMessage(
					sourceFormatterMessages, fileName,
					"Line should not start with ')'",
					getLineCount(content, matcher.start(1)));

				return content;
			}

			while (true) {
				lineCount--;

				String line = getLine(content, lineCount);

				if (getLeadingTabCount(line) != tabs.length()) {
					continue;
				}

				String trimmedLine = StringUtil.trimLeading(line);

				if (trimmedLine.startsWith(").") ||
					trimmedLine.startsWith("@")) {

					break;
				}

				return StringUtil.replaceFirst(
					content, "\n" + tabs, StringPool.BLANK, matcher.start());
			}
		}

		return content;
	}

	private String _fixMultiLineComment(String content) {
		Matcher matcher = _incorrectMultiLineCommentPattern.matcher(content);

		return matcher.replaceAll("$1$2$3");
	}

	private final Pattern _incorrectLineBreakPattern1 = Pattern.compile(
		"\t(catch |else |finally |for |if |try |while ).*\\{\n\n\t+\\w");
	private final Pattern _incorrectLineBreakPattern2 = Pattern.compile(
		"\\{\n\n\t*\\}");
	private final Pattern _incorrectLineBreakPattern3 = Pattern.compile(
		", (new .*\\(.*\\) \\{)\n");
	private final Pattern _incorrectLineBreakPattern4 = Pattern.compile(
		"\n(\t*)(.*\\) \\{)([\t ]*\\}\n)");
	private final Pattern _incorrectLineBreakPattern5 = Pattern.compile(
		"\n(\t*).*\\}\n(\t*)\\);");
	private final Pattern _incorrectLineBreakPattern6 = Pattern.compile(
		"\n(\t*)\\{.+(?<!\\}(,|;)?)\n");
	private final Pattern _incorrectLineBreakPattern7 = Pattern.compile(
		"\n(\t+\\{)\n(.*[^;])\n\t+(\\},?)");
	private final Pattern _incorrectMultiLineCommentPattern = Pattern.compile(
		"(\n\t*/\\*)\n\t*(.*?)\n\t*(\\*/\n)", Pattern.DOTALL);
	private final Pattern _lineStartingWithOpenParenthesisPattern =
		Pattern.compile("(.)\n+(\t+)\\)[^.].*\n");
	private final Pattern _redundantCommaPattern = Pattern.compile(",\n\t+\\}");
	private final SourceFormatterArgs _sourceFormatterArgs;

}