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
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.tools.ToolsUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class JavaIfStatementCheck extends IfStatementCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws Exception {

		Matcher matcher = _ifStatementPattern.matcher(content);

		while (matcher.find()) {
			String ifClause = matcher.group();

			if (ifClause.contains("{\t")) {
				continue;
			}

			String lineEnding = matcher.group(3);

			if (lineEnding.equals(StringPool.SEMICOLON) &&
				ifClause.contains("{\n")) {

				continue;
			}

			String type = matcher.group(1);

			if (!type.equals("while") &&
				lineEnding.equals(StringPool.SEMICOLON)) {

				addMessage(
					fileName, "Incorrect " + type + " statement",
					getLineCount(content, matcher.start()));
			}
			else {
				String newIfClause = _formatIfClause(
					ifClause, fileName, getLineCount(content, matcher.start()));

				if (!ifClause.equals(newIfClause)) {
					return StringUtil.replace(content, ifClause, newIfClause);
				}
			}
		}

		return content;
	}

	private String _fixIfClause(String ifClause, String line, int delta) {
		if (StringUtil.count(ifClause, line) > 1) {
			return ifClause;
		}

		String newLine = line;

		String whitespace = StringPool.BLANK;
		int whitespaceLength = Math.abs(delta);

		while (whitespaceLength > 0) {
			if (whitespaceLength >= 4) {
				whitespace += StringPool.TAB;

				whitespaceLength -= 4;
			}
			else {
				whitespace += StringPool.SPACE;

				whitespaceLength -= 1;
			}
		}

		if (delta > 0) {
			if (!line.contains(StringPool.TAB + whitespace)) {
				newLine = StringUtil.replaceLast(
					newLine, CharPool.TAB, StringPool.FOUR_SPACES);
			}

			newLine = StringUtil.replaceLast(
				newLine, StringPool.TAB + whitespace, StringPool.TAB);
		}
		else {
			newLine = StringUtil.replaceLast(
				newLine, CharPool.TAB, StringPool.TAB + whitespace);
		}

		newLine = StringUtil.replaceLast(
			newLine, StringPool.FOUR_SPACES, StringPool.TAB);

		return StringUtil.replace(ifClause, line, newLine);
	}

	private String _formatIfClause(String ifClause) throws Exception {
		String strippedQuotesIfClause = stripQuotes(ifClause);

		if (strippedQuotesIfClause.contains("!(") ||
			strippedQuotesIfClause.contains("//")) {

			return ifClause;
		}

		UnsyncBufferedReader unsyncBufferedReader = new UnsyncBufferedReader(
			new UnsyncStringReader(ifClause));

		String line = null;

		String previousLine = null;
		int previousLineLength = 0;

		int previousLineLeadingWhitespace = 0;
		int previousLineLevel = 0;
		boolean previousLineIsStartCriteria = true;

		int baseLeadingWhitespace = 0;
		int insideMethodCallExpectedWhitespace = 0;
		int level = -1;

		while ((line = unsyncBufferedReader.readLine()) != null) {
			String originalLine = line;

			String trimmedLine = StringUtil.trimLeading(line);

			int x = _getIncorrectLineBreakPos(line, previousLine);

			if (x != -1) {
				String leadingWhitespace = line.substring(
					0, line.indexOf(trimmedLine));

				return StringUtil.replace(
					ifClause, line,
					StringBundler.concat(
						line.substring(0, x), "\n", leadingWhitespace,
						line.substring(x + 1)));
			}

			if ((previousLineLength > 0) && previousLineIsStartCriteria &&
				(previousLineLevel >= 0) && previousLine.matches(".*[|&^]")) {

				Matcher matcher = _ifStatementCriteriaPattern.matcher(
					trimmedLine);

				while (matcher.find()) {
					if (ToolsUtil.isInsideQuotes(trimmedLine, matcher.end())) {
						continue;
					}

					String linePart = trimmedLine.substring(0, matcher.end());

					int linePartLevel = getLevel(linePart);

					if ((linePartLevel <= 0) &&
						((previousLineLength + linePart.length()) <
							getMaxLineLength())) {

						if (linePart.equals(trimmedLine)) {
							return StringUtil.replace(
								ifClause, previousLine + "\n" + originalLine,
								previousLine + StringPool.SPACE + trimmedLine);
						}
						else {
							String newPreviousLine =
								previousLine + StringPool.SPACE + linePart;
							String newLine = StringUtil.replaceFirst(
								originalLine, linePart, StringPool.BLANK);

							return StringUtil.replace(
								ifClause, previousLine + "\n" + originalLine,
								newPreviousLine + "\n" + newLine);
						}
					}
				}
			}

			line = StringUtil.replace(
				line, CharPool.TAB, StringPool.FOUR_SPACES);

			int leadingWhitespace = line.length() - trimmedLine.length();

			if (Validator.isNull(previousLine)) {
				baseLeadingWhitespace =
					line.indexOf(CharPool.OPEN_PARENTHESIS) + 1;
			}
			else if (previousLine.endsWith("|") || previousLine.endsWith("&") ||
					 previousLine.endsWith("^")) {

				int expectedLeadingWhitespace = baseLeadingWhitespace + level;

				if (leadingWhitespace != expectedLeadingWhitespace) {
					return _fixIfClause(
						ifClause, originalLine,
						leadingWhitespace - expectedLeadingWhitespace);
				}
			}
			else {
				int expectedLeadingWhitespace = 0;

				if (previousLine.contains(StringPool.TAB + "else if (")) {
					expectedLeadingWhitespace = baseLeadingWhitespace + 3;
				}
				else if (previousLine.contains(StringPool.TAB + "if (")) {
					expectedLeadingWhitespace = baseLeadingWhitespace + 4;
				}
				else if (previousLine.contains(StringPool.TAB + "while (")) {
					expectedLeadingWhitespace = baseLeadingWhitespace + 5;
				}

				if (previousLine.endsWith(StringPool.COMMA) &&
					(insideMethodCallExpectedWhitespace > 0)) {

					if (previousLineLevel < 0) {
						insideMethodCallExpectedWhitespace -= 4;
					}

					expectedLeadingWhitespace =
						insideMethodCallExpectedWhitespace;
				}
				else {
					if (expectedLeadingWhitespace == 0) {
						expectedLeadingWhitespace =
							previousLineLeadingWhitespace + 4;
					}

					if (previousLine.endsWith(StringPool.OPEN_PARENTHESIS)) {
						insideMethodCallExpectedWhitespace =
							expectedLeadingWhitespace;
					}
				}

				if (leadingWhitespace != expectedLeadingWhitespace) {
					return _fixIfClause(
						ifClause, originalLine,
						leadingWhitespace - expectedLeadingWhitespace);
				}
			}

			if (line.endsWith(") {")) {
				return ifClause;
			}

			int lineLevel = getLevel(trimmedLine);

			level += lineLevel;

			if (Validator.isNotNull(previousLine)) {
				if (!previousLine.endsWith("|") &&
					!previousLine.endsWith("&") &&
					!previousLine.endsWith("^")) {

					previousLineIsStartCriteria = false;
				}
				else {
					previousLineIsStartCriteria = true;
				}
			}

			previousLine = originalLine;
			previousLineLength = line.length();

			previousLineLevel = lineLevel;
			previousLineLeadingWhitespace = leadingWhitespace;
		}

		return ifClause;
	}

	private String _formatIfClause(
			String ifClause, String fileName, int lineCount)
		throws Exception {

		String ifClauseSingleLine = StringUtil.replace(
			ifClause,
			new String[] {
				StringPool.TAB + StringPool.SPACE, StringPool.TAB,
				StringPool.OPEN_PARENTHESIS + StringPool.NEW_LINE,
				StringPool.NEW_LINE
			},
			new String[] {
				StringPool.TAB, StringPool.BLANK, StringPool.OPEN_PARENTHESIS,
				StringPool.SPACE
			});

		checkIfClauseParentheses(ifClauseSingleLine, fileName, lineCount);

		return _formatIfClause(ifClause);
	}

	private int _getIncorrectLineBreakPos(String line, String previousLine) {
		for (int x = line.length();;) {
			int y = line.lastIndexOf(" || ", x - 1);
			int z = line.lastIndexOf(" && ", x - 1);

			x = Math.max(y, z);

			if (x == -1) {
				return x;
			}

			if (ToolsUtil.isInsideQuotes(line, x)) {
				continue;
			}

			if (Validator.isNotNull(previousLine) &&
				(previousLine.endsWith(StringPool.PERIOD) ||
				 (getLevel(line.substring(0, x)) < 0))) {

				return x + 3;
			}

			if (!line.endsWith(" ||") && !line.endsWith(" &&")) {
				continue;
			}

			if (getLevel(line.substring(x)) > 0) {
				return x + 3;
			}
		}
	}

	private final Pattern _ifStatementCriteriaPattern = Pattern.compile(
		".*?( [|&^]+( |\\Z)|\\) \\{\\Z)");
	private final Pattern _ifStatementPattern = Pattern.compile(
		"\t+(catch|(else )?if|while) \\(.*?(\\) \\{|;)\n", Pattern.DOTALL);

}