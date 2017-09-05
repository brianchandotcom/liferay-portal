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
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.tools.ToolsUtil;
import com.liferay.source.formatter.util.RegexUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class JavaLineBreakCheck extends LineBreakCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws Exception {

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
						fileName, "Line should not start with ';'", lineCount);
				}

				if (!trimmedLine.startsWith(StringPool.DOUBLE_SLASH) &&
					!trimmedLine.startsWith(StringPool.STAR) &&
					trimmedLine.startsWith(StringPool.PERIOD)) {

					addMessage(
						fileName, "Line should not start with '.'", lineCount);
				}

				int lineLength = getLineLength(line);

				if (!line.startsWith("import ") &&
					!line.startsWith("package ") &&
					!RegexUtil.matches(line, "\\s*\\*.*") &&
					(lineLength <= getMaxLineLength())) {

					_checkLineBreaks(line, previousLine, fileName, lineCount);
				}

				previousLine = line;
			}
		}

		content = _fixIncorrectLineBreaksInsideChains(content, fileName);

		content = _fixIncorrectLineBreaks(content, fileName);

		content = _fixLineStartingWithCloseParenthesis(content, fileName);

		content = _fixMultiLineComment(content);

		content = _fixArrayLineBreaks(content);

		content = _fixClassOrEnumLineLineBreaks(content);

		content = fixRedundantCommaInsideArray(content);

		return content;
	}

	private void _checkLambdaLineBreaks(
		String line, String fileName, int lineCount) {

		if (!line.endsWith(StringPool.OPEN_CURLY_BRACE) ||
			(getLevel(line) <= 0)) {

			return;
		}

		int pos = line.indexOf("->");

		if ((pos == -1) || ToolsUtil.isInsideQuotes(line, pos)) {
			return;
		}

		int x = 1;

		while (true) {
			String s = line.substring(0, x);

			if (getLevel(s) > 0) {
				addMessage(
					fileName, "There should be a line break after '" + s + "'",
					lineCount);

				return;
			}

			x++;
		}
	}

	private void _checkLineBreaks(
		String line, String previousLine, String fileName, int lineCount) {

		checkLineBreaks(line, previousLine, fileName, lineCount);

		String trimmedLine = StringUtil.trimLeading(line);

		if (previousLine.contains("\t/*") || trimmedLine.startsWith("//") |
			trimmedLine.startsWith("/*") || trimmedLine.endsWith("*/")) {

			return;
		}

		_checkLambdaLineBreaks(trimmedLine, fileName, lineCount);

		if (trimmedLine.startsWith("},") && !trimmedLine.equals("},")) {
			addMessage(
				fileName, "There should be a line break after '},'", lineCount);
		}

		if (previousLine.endsWith(StringPool.PERIOD)) {
			int x = trimmedLine.indexOf(CharPool.OPEN_PARENTHESIS);

			if ((x != -1) &&
				((getLineLength(previousLine) + x) < getMaxLineLength()) &&
				(trimmedLine.endsWith(StringPool.OPEN_PARENTHESIS) ||
				 (trimmedLine.charAt(x + 1) != CharPool.CLOSE_PARENTHESIS))) {

				addMessage(fileName, "Incorrect line break", lineCount);
			}
		}

		String strippedQuotesLine = stripQuotes(trimmedLine);

		if (RegexUtil.matches(line, ".*(\\(|->( \\{)?)")) {
			int x = line.lastIndexOf(" && ");
			int y = line.lastIndexOf(" || ");

			int z = Math.max(x, y);

			if (z != -1) {
				addMessage(
					fileName,
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
						fileName, "There should be a line break after '+'",
						lineCount);
				}
			}
		}

		if (RegexUtil.matches(line, ".*(\\(|\\^|\\&|\\||->( \\{)?)")) {
			int x = trimmedLine.length() + 1;

			while (true) {
				x = trimmedLine.lastIndexOf(StringPool.COMMA, x - 1);

				if (x == -1) {
					break;
				}

				if (ToolsUtil.isInsideQuotes(trimmedLine, x)) {
					continue;
				}

				String linePart = trimmedLine.substring(
					x, trimmedLine.length() - 1);

				linePart = StringUtil.removeSubstring(linePart, "->");

				if ((getLevel(linePart) == 0) &&
					(getLevel(linePart, "<", ">") == 0)) {

					addMessage(
						fileName,
						"There should be a line break after '" +
							trimmedLine.substring(0, x + 1) + "'",
						lineCount);

					break;
				}
			}
		}

		if (RegexUtil.matches(trimmedLine, "^[^(].*\\+$") &&
			(getLevel(trimmedLine) > 0)) {

			addMessage(
				fileName, "There should be a line break after '('", lineCount);
		}

		if (!trimmedLine.contains("\t//") && !line.endsWith("{") &&
			strippedQuotesLine.contains("{") &&
			!strippedQuotesLine.contains("}")) {

			addMessage(
				fileName, "There should be a line break after '{'", lineCount);
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
						fileName,
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
						fileName,
						"There should be a line break after '" + linePart + "'",
						lineCount);
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
				fileName, "There should be a line break before 'throws'",
				lineCount);
		}

		if (line.endsWith(StringPool.PERIOD) &&
			line.contains(StringPool.EQUAL)) {

			addMessage(
				fileName, "There should be a line break after '='", lineCount);
		}

		if (RegexUtil.matches(trimmedLine, "^\\} (catch|else|finally) .*")) {
			addMessage(
				fileName, "There should be a line break after '}'", lineCount);
		}

		Matcher matcher = _INCORRECT_LINE_BREAK_PATTERN_6.matcher(trimmedLine);

		if (matcher.find() && (getLevel(matcher.group(4)) > 1)) {
			x = trimmedLine.indexOf("(", matcher.start(4));

			String linePart = trimmedLine.substring(0, x + 1);

			addMessage(
				fileName,
				"There should be a line break after '" + linePart + "'",
				lineCount);
		}

		if (RegexUtil.matches(trimmedLine, "for \\(.*[^;{]")) {
			x = trimmedLine.length();

			while (true) {
				x = trimmedLine.lastIndexOf(StringPool.SEMICOLON, x - 1);

				if (x == -1) {
					break;
				}

				if (!ToolsUtil.isInsideQuotes(trimmedLine, x)) {
					addMessage(
						fileName,
						"There should be a line break after '" +
							trimmedLine.substring(0, x + 1) + "'",
						lineCount);
				}
			}
		}
	}

	private String _fixArrayLineBreaks(String content) {
		Matcher matcher = _ARRAY_PATTERN.matcher(content);

		while (matcher.find()) {
			String newLine =
				matcher.group(4) + matcher.group(2) + matcher.group(5) +
					matcher.group(6);

			if (getLineLength(newLine) <= getMaxLineLength()) {
				return StringUtil.replace(
					content, matcher.group(),
					matcher.group(1) + "\n" + newLine + "\n");
			}
		}

		return content;
	}

	private String _fixClassOrEnumLineLineBreaks(String content) {
		Matcher matcher = _CLASS_OR_ENUM_PATTERN.matcher(content);

		while (matcher.find()) {
			String indent = matcher.group(2);
			String match = matcher.group(1);

			String inBetweenCurlyBraces = matcher.group(9);

			if (inBetweenCurlyBraces != null) {
				if (Validator.isNull(inBetweenCurlyBraces)) {

					// Case: public class Test {}

					return StringUtil.replaceFirst(
						content, "}", "\n" + indent + "}", matcher.end(9));
				}

				// Case: public enum Test { VALUE1, VALUE2 }

				return StringUtil.replaceFirst(
					content, inBetweenCurlyBraces + "}",
					"\n\n\t" + indent + StringUtil.trim(inBetweenCurlyBraces) +
						"\n\n" + indent + "}",
					matcher.start(8));
			}

			String firstTrailingNonWhitespace = matcher.group(12);

			String trailingWhitespace = matcher.group(11);

			if (!trailingWhitespace.contains("\n") &&
				!firstTrailingNonWhitespace.equals("}")) {

				return StringUtil.replace(content, match, match + "\n");
			}

			String formattedClassLine = _getFormattedClassLine(indent, match);

			if (formattedClassLine != null) {
				content = StringUtil.replace(
					content, match, formattedClassLine);
			}
		}

		return content;
	}

	private String _fixIncorrectLineBreaks(String content, String fileName) {
		while (true) {
			Matcher matcher = _INCORRECT_LINE_BREAK_PATTERN_1.matcher(content);

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

			matcher = _INCORRECT_LINE_BREAK_PATTERN_2.matcher(content);

			while (matcher.find()) {
				String tabs = matcher.group(2);

				Pattern pattern = RegexUtil.getPattern(
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

			matcher = _INCORRECT_LINE_BREAK_PATTERN_3.matcher(content);

			if (matcher.find()) {
				content = StringUtil.replaceFirst(
					content, "{", "{\n" + matcher.group(1) + "\t",
					matcher.start());
			}

			matcher = _INCORRECT_LINE_BREAK_PATTERN_4.matcher(content);

			while (matcher.find()) {
				if (content.charAt(matcher.end()) != CharPool.NEW_LINE) {
					continue;
				}

				String singleLine =
					matcher.group(1) +
						StringUtil.trimLeading(matcher.group(2)) +
							matcher.group(3);

				if (getLineLength(singleLine) <= getMaxLineLength()) {
					content = StringUtil.replace(
						content, matcher.group(), "\n" + singleLine);

					break;
				}
			}

			break;
		}

		Matcher matcher = _INCORRECT_LINE_BREAK_PATTERN_5.matcher(content);

		while (matcher.find()) {
			if (getLevel(matcher.group()) == 0) {
				int lineCount = getLineCount(content, matcher.start());

				addMessage(
					fileName,
					"There should be a line break before '" + matcher.group(1) +
						"'",
					lineCount);
			}
		}

		return content;
	}

	private String _fixIncorrectLineBreaksInsideChains(
		String content, String fileName) {

		Matcher matcher = _INCORRECT_LINE_BREAK_INSIDE_CHAIN_PATTERN_1.matcher(
			content);

		while (matcher.find()) {
			String linePart = matcher.group(2);

			if (RegexUtil.matches(linePart, "\\)[^\\)]+[\\(;]")) {
				return StringUtil.insert(
					content, "\n" + matcher.group(1), matcher.start(2));
			}
		}

		matcher = _INCORRECT_LINE_BREAK_INSIDE_CHAIN_PATTERN_2.matcher(content);

		while (matcher.find()) {
			int x = matcher.end();

			while (true) {
				x = content.indexOf(StringPool.CLOSE_PARENTHESIS, x + 1);

				if (x == -1) {
					return content;
				}

				if (ToolsUtil.isInsideQuotes(content, x)) {
					continue;
				}

				String s = content.substring(matcher.end(), x);

				if (getLevel(s) != 0) {
					continue;
				}

				char c = content.charAt(x - 1);

				if (c == CharPool.TAB) {
					break;
				}

				int y = content.lastIndexOf(StringPool.TAB, x);

				s = content.substring(y + 1, x);

				addMessage(
					fileName, "There should be a line break after '" + s + "'",
					getLineCount(content, x));

				break;
			}
		}

		matcher = _INCORRECT_LINE_BREAK_INSIDE_CHAIN_PATTERN_3.matcher(content);

		while (matcher.find()) {
			if (getLevel(matcher.group(1)) <= 0) {
				String methodName = matcher.group(2);

				if (!methodName.equals("concat")) {
					addMessage(
						fileName,
						"Chaining on method '" + methodName +
							"' is allowed, but incorrect styling",
						"chaining.markdown",
						getLineCount(content, matcher.end(1)));
				}
			}
		}

		matcher = _INCORRECT_LINE_BREAK_INSIDE_CHAIN_PATTERN_4.matcher(content);

		while (matcher.find()) {
			String s = matcher.group(2);

			if (!RegexUtil.matches(s, "\\)+;?")) {
				addMessage(
					fileName,
					"There should be a line break after '" + matcher.group(1) +
						"'",
					getLineCount(content, matcher.start()));
			}
		}

		return content;
	}

	private String _fixLineStartingWithCloseParenthesis(
		String content, String fileName) {

		Matcher matcher = _LINE_STARTING_WITH_CLOSE_PARENTHESIS_PATTERN.matcher(
			content);

		while (matcher.find()) {
			String lastCharacterPreviousLine = matcher.group(1);

			if (lastCharacterPreviousLine.equals(StringPool.OPEN_PARENTHESIS)) {
				addMessage(
					fileName, "Line should not start with ')'",
					getLineCount(content, matcher.start(1)));

				return content;
			}

			int x = matcher.end(2) + 1;

			int y = x - 1;

			while (true) {
				if (ToolsUtil.isInsideQuotes(content, y) ||
					(getLevel(content.substring(y, x)) != 0)) {

					y--;

					continue;
				}

				String trimmedLine = StringUtil.trimLeading(
					getLine(content, getLineCount(content, y)));

				if (trimmedLine.startsWith(").") ||
					trimmedLine.startsWith("@")) {

					break;
				}

				return StringUtil.replaceFirst(
					content, "\n" + matcher.group(2), StringPool.BLANK,
					matcher.start());
			}
		}

		return content;
	}

	private String _fixMultiLineComment(String content) {
		Matcher matcher = _INCORRECT_MULTI_LINE_COMMENT_PATTERN.matcher(
			content);

		return matcher.replaceAll("$1$2$3");
	}

	private String _getFormattedClassLine(String indent, String classLine) {
		while (classLine.contains(StringPool.TAB + StringPool.SPACE)) {
			classLine = StringUtil.replace(
				classLine, StringPool.TAB + StringPool.SPACE, StringPool.TAB);
		}

		String classSingleLine = StringUtil.replace(
			classLine.substring(1),
			new String[] {StringPool.TAB, StringPool.NEW_LINE},
			new String[] {StringPool.BLANK, StringPool.SPACE});

		classSingleLine = indent + classSingleLine;

		List<String> lines = new ArrayList<>();

		outerWhile:
		while (true) {
			if (getLineLength(classSingleLine) <= getMaxLineLength()) {
				lines.add(classSingleLine);

				break;
			}

			String newIndent = indent;
			String newLine = classSingleLine;

			int x = -1;

			while (true) {
				int y = newLine.indexOf(" extends ", x + 1);

				if (y == -1) {
					x = newLine.indexOf(" implements ", x + 1);
				}
				else {
					x = y;
				}

				if (x == -1) {
					break;
				}

				String linePart = newLine.substring(0, x);

				if ((getLevel(linePart, "<", ">") == 0) &&
					(getLineLength(linePart) <= getMaxLineLength())) {

					if (lines.isEmpty()) {
						newIndent = newIndent + StringPool.TAB;
					}

					lines.add(linePart);

					newLine = newIndent + newLine.substring(x + 1);

					if (getLineLength(newLine) <= getMaxLineLength()) {
						lines.add(newLine);

						break outerWhile;
					}

					x = -1;
				}
			}

			if (lines.isEmpty()) {
				return null;
			}

			x = newLine.length();

			while (true) {
				x = newLine.lastIndexOf(", ", x - 1);

				if (x == -1) {
					return null;
				}

				String linePart = newLine.substring(0, x + 1);

				if ((getLevel(linePart, "<", ">") == 0) &&
					(getLineLength(linePart) <= getMaxLineLength())) {

					lines.add(linePart);

					if (linePart.contains("\textends")) {
						newIndent = newIndent + "\t\t";
					}
					else if (linePart.contains("\timplements")) {
						newIndent = newIndent + "\t\t   ";
					}

					newLine = newIndent + newLine.substring(x + 2);

					if (getLineLength(newLine) <= getMaxLineLength()) {
						lines.add(newLine);

						break outerWhile;
					}

					x = newLine.length();
				}
			}
		}

		String formattedClassLine = null;

		for (String line : lines) {
			if (formattedClassLine == null) {
				formattedClassLine = "\n" + line;
			}
			else {
				formattedClassLine = formattedClassLine + "\n" + line;
			}
		}

		return formattedClassLine;
	}

	private static final Pattern _ARRAY_PATTERN = RegexUtil.getPattern(
		"(\n\t*.* =) ((new \\w*\\[\\] )?\\{)\n(\t*)([^\t\\{].*)\n\t*(\\};?)\n");

	private static final Pattern _CLASS_OR_ENUM_PATTERN = RegexUtil.getPattern(
		"(\n(\t*)(private|protected|public) ((abstract|static) )*" +
			"(class|enum|interface) ([\\s\\S]*?)\\{)((.*)\\})?" +
				"(\\Z|\n(\\s*)(\\S))");

	private static final Pattern _INCORRECT_LINE_BREAK_INSIDE_CHAIN_PATTERN_1 =
		RegexUtil.getPattern("\n(\t*)\\).*?\\((.+)");

	private static final Pattern _INCORRECT_LINE_BREAK_INSIDE_CHAIN_PATTERN_2 =
		RegexUtil.getPattern("\t\\)\\..*\\(\n");

	private static final Pattern _INCORRECT_LINE_BREAK_INSIDE_CHAIN_PATTERN_3 =
		RegexUtil.getPattern("\n(.*\\S)\\)\\.(.*)\\(\n");

	private static final Pattern _INCORRECT_LINE_BREAK_INSIDE_CHAIN_PATTERN_4 =
		RegexUtil.getPattern("\t(\\)\\.[^\\)\\(]+\\()(.+)\n");

	private static final Pattern _INCORRECT_LINE_BREAK_PATTERN_1 =
		RegexUtil.getPattern("\n(\t*)(.*\\) \\{)([\t ]*\\}\n)");

	private static final Pattern _INCORRECT_LINE_BREAK_PATTERN_2 =
		RegexUtil.getPattern("\n(\t*).*\\}\n(\t*)\\);");

	private static final Pattern _INCORRECT_LINE_BREAK_PATTERN_3 =
		RegexUtil.getPattern("\n(\t*)\\{.+(?<!\\}\\){0,10}(,|;)?)\n");

	private static final Pattern _INCORRECT_LINE_BREAK_PATTERN_4 =
		RegexUtil.getPattern("\n(\t+\\{)\n(.*[^;])\n\t+(\\},?)");

	private static final Pattern _INCORRECT_LINE_BREAK_PATTERN_5 =
		RegexUtil.getPattern(", (new .*\\(.*\\) \\{)\n");

	private static final Pattern _INCORRECT_LINE_BREAK_PATTERN_6 =
		RegexUtil.getPattern("^(((else )?if|for|try|while) \\()?\\(*(.*\\()$");

	private static final Pattern _INCORRECT_MULTI_LINE_COMMENT_PATTERN =
		RegexUtil.getPattern(
			"(\n\t*/\\*)\n\t*(.*?)\n\t*(\\*/\n)", Pattern.DOTALL);

	private static final Pattern _LINE_STARTING_WITH_CLOSE_PARENTHESIS_PATTERN =
		RegexUtil.getPattern("(.)\n+(\t+)\\)[^.].*\n");

}