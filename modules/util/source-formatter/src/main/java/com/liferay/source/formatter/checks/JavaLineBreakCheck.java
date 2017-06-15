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
import com.liferay.portal.tools.ToolsUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class JavaLineBreakCheck extends BaseFileCheck {

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
					!line.matches("\\s*\\*.*") &&
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

		content = _fixClassLineLineBreaks(content);

		return content;
	}

	private String _fixIncorrectLineBreaksInsideChains(
		String content, String fileName) {

		Matcher matcher = _incorrectLineBreakInsideChainPattern1.matcher(
			content);

		while (matcher.find()) {
			String linePart = matcher.group(2);

			if (linePart.matches("\\)[^\\)]+[\\(;]")) {
				return StringUtil.insert(
					content, "\n" + matcher.group(1), matcher.start(2));
			}
		}

		matcher = _incorrectLineBreakInsideChainPattern2.matcher(content);

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

		int lineLeadingTabCount = getLeadingTabCount(line);
		int previousLineLeadingTabCount = getLeadingTabCount(previousLine);

		if (previousLine.endsWith(StringPool.COMMA) &&
			previousLine.contains(StringPool.OPEN_PARENTHESIS) &&
			!previousLine.contains("for (") &&
			(lineLeadingTabCount > previousLineLeadingTabCount)) {

			addMessage(
				fileName, "There should be a line break after '('",
				lineCount - 1);
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

		int strippedQuotesLineOpenParenthesisCount = StringUtil.count(
			strippedQuotesLine, CharPool.OPEN_PARENTHESIS);

		if (!trimmedLine.startsWith(StringPool.OPEN_PARENTHESIS) &&
			trimmedLine.endsWith(") {") &&
			(strippedQuotesLineOpenParenthesisCount > 0) &&
			(getLevel(trimmedLine) > 0)) {

			addMessage(fileName, "Incorrect line break", lineCount);
		}

		if (line.matches(".*(\\(|->( \\{)?)")) {
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

		if (line.matches(".*(\\(|\\^|\\&|\\||->( \\{)?)")) {
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

		if (trimmedLine.matches("^[^(].*\\+$") && (getLevel(trimmedLine) > 0)) {
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
		else if (trimmedLine.endsWith(StringPool.COMMA) &&
				 !trimmedLine.startsWith("for (")) {

			if (getLevel(trimmedLine) > 0) {
				addMessage(fileName, "Incorrect line break", lineCount);
			}
		}

		if (line.endsWith(" +") || line.endsWith(" -") || line.endsWith(" *") ||
			line.endsWith(" /")) {

			x = line.indexOf(" = ");

			if (x != -1) {
				int y = line.indexOf(CharPool.QUOTE);

				if ((y == -1) || (x < y)) {
					addMessage(
						fileName, "There should be a line break after '='",
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

		if (trimmedLine.matches("^\\} (catch|else|finally) .*")) {
			addMessage(
				fileName, "There should be a line break after '}'", lineCount);
		}

		Matcher matcher = _incorrectLineBreakPattern6.matcher(trimmedLine);

		if (matcher.find() && (getLevel(matcher.group(4)) > 1)) {
			x = trimmedLine.indexOf("(", matcher.start(4));

			String linePart = trimmedLine.substring(0, x + 1);

			addMessage(
				fileName,
				"There should be a line break after '" + linePart + "'",
				lineCount);
		}
	}

	private String _fixArrayLineBreaks(String content) {
		Matcher matcher = _arrayPattern.matcher(content);

		while (matcher.find()) {
			String newLine =
				matcher.group(3) + matcher.group(2) + matcher.group(4) +
					matcher.group(5);

			if (getLineLength(newLine) <= getMaxLineLength()) {
				return StringUtil.replace(
					content, matcher.group(),
					matcher.group(1) + "\n" + newLine + "\n");
			}
		}

		return content;
	}

	private String _fixClassLineLineBreaks(String content) {
		Matcher matcher = _classPattern.matcher(content);

		while (matcher.find()) {
			String firstTrailingNonWhitespace = matcher.group(9);
			String match = matcher.group(1);
			String trailingWhitespace = matcher.group(8);

			if (!trailingWhitespace.contains("\n") &&
				!firstTrailingNonWhitespace.equals("}")) {

				return StringUtil.replace(content, match, match + "\n");
			}

			String formattedClassLine = _getFormattedClassLine(
				matcher.group(2), match);

			if (formattedClassLine != null) {
				content = StringUtil.replace(
					content, match, formattedClassLine);
			}
		}

		return content;
	}

	private String _fixIncorrectLineBreaks(String content, String fileName) {
		while (true) {
			Matcher matcher = _incorrectLineBreakPattern1.matcher(content);

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

			matcher = _incorrectLineBreakPattern2.matcher(content);

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

			matcher = _incorrectLineBreakPattern3.matcher(content);

			if (matcher.find()) {
				content = StringUtil.replaceFirst(
					content, "{", "{\n" + matcher.group(1) + "\t",
					matcher.start());
			}

			matcher = _incorrectLineBreakPattern4.matcher(content);

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

			matcher = _redundantCommaPattern.matcher(content);

			if (matcher.find()) {
				content = StringUtil.replaceFirst(
					content, StringPool.COMMA, StringPool.BLANK,
					matcher.start());

				continue;
			}

			break;
		}

		Matcher matcher = _incorrectLineBreakPattern5.matcher(content);

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

	private String _fixLineStartingWithCloseParenthesis(
		String content, String fileName) {

		Matcher matcher = _lineStartingWithOpenParenthesisPattern.matcher(
			content);

		while (matcher.find()) {
			String tabs = matcher.group(2);

			int lineCount = getLineCount(content, matcher.start(2));

			String lastCharacterPreviousLine = matcher.group(1);

			if (lastCharacterPreviousLine.equals(StringPool.OPEN_PARENTHESIS)) {
				addMessage(
					fileName, "Line should not start with ')'",
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

	private final Pattern _arrayPattern = Pattern.compile(
		"(\n\t*.* =) (new \\w*\\[\\] \\{)\n(\t*)(.+)\n\t*(\\};)\n");
	private final Pattern _classPattern = Pattern.compile(
		"(\n(\t*)(private|protected|public) ((abstract|static) )*" +
			"(class|enum|interface) ([\\s\\S]*?) \\{)\n(\\s*)(\\S)");
	private final Pattern _incorrectLineBreakInsideChainPattern1 =
		Pattern.compile("\n(\t*)\\).*?\\((.+)");
	private final Pattern _incorrectLineBreakInsideChainPattern2 =
		Pattern.compile("\t\\)\\..*\\(\n");
	private final Pattern _incorrectLineBreakPattern1 = Pattern.compile(
		"\n(\t*)(.*\\) \\{)([\t ]*\\}\n)");
	private final Pattern _incorrectLineBreakPattern2 = Pattern.compile(
		"\n(\t*).*\\}\n(\t*)\\);");
	private final Pattern _incorrectLineBreakPattern3 = Pattern.compile(
		"\n(\t*)\\{.+(?<!\\}(,|;)?)\n");
	private final Pattern _incorrectLineBreakPattern4 = Pattern.compile(
		"\n(\t+\\{)\n(.*[^;])\n\t+(\\},?)");
	private final Pattern _incorrectLineBreakPattern5 = Pattern.compile(
		", (new .*\\(.*\\) \\{)\n");
	private final Pattern _incorrectLineBreakPattern6 = Pattern.compile(
		"^(((else )?if|for|try|while) \\()?\\(*(.*\\()$");
	private final Pattern _incorrectMultiLineCommentPattern = Pattern.compile(
		"(\n\t*/\\*)\n\t*(.*?)\n\t*(\\*/\n)", Pattern.DOTALL);
	private final Pattern _lineStartingWithOpenParenthesisPattern =
		Pattern.compile("(.)\n+(\t+)\\)[^.].*\n");
	private final Pattern _redundantCommaPattern = Pattern.compile(",\n\t+\\}");

}