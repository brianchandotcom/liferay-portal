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
import com.liferay.source.formatter.checks.util.JavaSourceUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class JavaCombineLinesCheck extends BaseFileCheck {

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

				if (line.startsWith("import ") || line.startsWith("package ") ||
					line.matches("\\s*\\*.*")) {

					previousLine = line;

					continue;
				}

				int lineLength = getLineLength(line);

				if (lineLength > getMaxLineLength()) {
					previousLine = line;

					continue;
				}

				int lineLeadingTabCount = getLeadingTabCount(line);
				int previousLineLeadingTabCount = getLeadingTabCount(
					previousLine);
				String trimmedLine = StringUtil.trimLeading(line);

				if (!trimmedLine.startsWith(StringPool.DOUBLE_SLASH) &&
					!trimmedLine.startsWith(StringPool.STAR)) {

					String strippedQuotesLine = stripQuotes(trimmedLine);

					String indent = StringPool.BLANK;

					if (!trimmedLine.startsWith(StringPool.CLOSE_CURLY_BRACE) &&
						strippedQuotesLine.contains(
							StringPool.CLOSE_CURLY_BRACE)) {

						if ((getLevel(strippedQuotesLine, "{", "}") < 0) &&
							(lineLeadingTabCount > 0)) {

							for (int i = 0; i < lineLeadingTabCount - 1; i++) {
								indent += StringPool.TAB;
							}

							int x = line.lastIndexOf(
								CharPool.CLOSE_CURLY_BRACE);

							content = StringUtil.replace(
								content, "\n" + line + "\n",
								"\n" + line.substring(0, x) + "\n" + indent +
									line.substring(x) + "\n");

							return content;
						}
					}

					if (!previousLine.contains("\tthrows ") &&
						!previousLine.contains(" throws ") &&
						(previousLineLeadingTabCount ==
							(lineLeadingTabCount - 1))) {

						int x = -1;

						while (true) {
							x = previousLine.indexOf(", ", x + 1);

							if (x == -1) {
								break;
							}

							if (ToolsUtil.isInsideQuotes(previousLine, x)) {
								continue;
							}

							String linePart = previousLine.substring(0, x);

							linePart = stripQuotes(linePart);

							if ((getLevel(linePart, "(", ")") != 0) ||
								(getLevel(linePart, "<", ">") != 0)) {

								continue;
							}

							linePart = previousLine.substring(x);

							linePart = stripQuotes(linePart, CharPool.QUOTE);

							if ((getLevel(linePart, "(", ")") != 0) ||
								(getLevel(linePart, "<", ">") != 0)) {

								continue;
							}

							if (Validator.isNull(indent)) {
								for (int i = 0; i < lineLeadingTabCount - 1;
									 i++) {

									indent += StringPool.TAB;
								}
							}

							content = StringUtil.replace(
								content, "\n" + previousLine + "\n",
								"\n" + previousLine.substring(0, x + 1) + "\n" +
									indent + previousLine.substring(x + 2) +
										"\n");

							return content;
						}
					}
				}

				String combinedLinesContent = _getCombinedLinesContent(
					content, fileName, absolutePath, line, trimmedLine,
					lineLength, lineCount, previousLine, lineLeadingTabCount,
					previousLineLeadingTabCount);

				if ((combinedLinesContent != null) &&
					!combinedLinesContent.equals(content)) {

					return combinedLinesContent;
				}

				previousLine = line;
			}
		}

		content = _getCombinedLinesContent(content);
		content = _getCombinedLinesContent(content, _combinedLinesPattern1);
		content = _getCombinedLinesContent(content, _combinedLinesPattern2);

		return content;
	}

	private String _getCombinedLinesContent(String content) {
		Matcher matcher = _combinedLinesPattern3.matcher(content);

		content = matcher.replaceAll("$1 $3");

		matcher = _combinedLinesPattern4.matcher(content);

		return matcher.replaceAll("$1 $3");
	}

	private String _getCombinedLinesContent(String content, Pattern pattern) {
		Matcher matcher = pattern.matcher(content);

		while (matcher.find()) {
			String tabs = matcher.group(1);

			int x = matcher.start(1);

			String openChar = matcher.group(matcher.groupCount());

			int y = -1;

			if (openChar.equals(StringPool.OPEN_CURLY_BRACE)) {
				y = content.indexOf(
					StringPool.NEW_LINE + tabs + StringPool.CLOSE_CURLY_BRACE,
					x);
			}
			else if (openChar.equals(StringPool.OPEN_PARENTHESIS)) {
				y = content.indexOf(
					StringPool.NEW_LINE + tabs + StringPool.CLOSE_PARENTHESIS,
					x);
			}

			y = content.indexOf(CharPool.NEW_LINE, y + 1);

			if (y < x) {
				return content;
			}

			String match = content.substring(x, y);

			String replacement = match;

			while (replacement.contains("\n\t")) {
				replacement = StringUtil.replace(replacement, "\n\t", "\n");
			}

			replacement = StringUtil.replace(
				replacement, new String[] {",\n", "\n"},
				new String[] {StringPool.COMMA_AND_SPACE, StringPool.BLANK});

			if (getLineLength(replacement) <= getMaxLineLength()) {
				return _getCombinedLinesContent(
					StringUtil.replace(content, match, replacement), pattern);
			}
		}

		return content;
	}

	private String _getCombinedLinesContent(
		String content, String line, String trimmedLine, int lineLength,
		int lineCount, String previousLine, String linePart,
		boolean addToPreviousLine, boolean extraSpace,
		int numNextLinesRemoveLeadingTab) {

		int previousLineStartPos = getLineStartPos(content, lineCount - 1);

		if (linePart == null) {
			String combinedLine = previousLine;

			if (extraSpace) {
				combinedLine += StringPool.SPACE;
			}

			combinedLine += trimmedLine;

			String nextLine = getLine(content, lineCount + 1);

			if (nextLine == null) {
				return null;
			}

			if (numNextLinesRemoveLeadingTab > 0) {
				int nextLineStartPos = getLineStartPos(content, lineCount + 1);

				for (int i = 0; i < numNextLinesRemoveLeadingTab; i++) {
					content = StringUtil.replaceFirst(
						content, StringPool.TAB, StringPool.BLANK,
						nextLineStartPos);

					nextLineStartPos =
						content.indexOf(CharPool.NEW_LINE, nextLineStartPos) +
							1;
				}
			}

			return StringUtil.replaceFirst(
				content, previousLine + "\n" + line, combinedLine,
				previousLineStartPos);
		}

		String firstLine = previousLine;
		String secondLine = line;

		if (addToPreviousLine) {
			if (extraSpace) {
				firstLine += StringPool.SPACE;
			}

			firstLine += linePart;

			secondLine = StringUtil.replaceFirst(
				line, linePart, StringPool.BLANK);
		}
		else {
			if (((linePart.length() + lineLength) <= getMaxLineLength()) &&
				(line.endsWith(StringPool.OPEN_CURLY_BRACE) ||
				 line.endsWith(StringPool.SEMICOLON))) {

				firstLine = StringUtil.replaceLast(
					firstLine, StringUtil.trim(linePart), StringPool.BLANK);

				if (extraSpace) {
					secondLine = StringUtil.replaceLast(
						line, StringPool.TAB,
						StringPool.TAB + linePart + StringPool.SPACE);
				}
				else {
					secondLine = StringUtil.replaceLast(
						line, StringPool.TAB, StringPool.TAB + linePart);
				}
			}
			else {
				return null;
			}
		}

		firstLine = StringUtil.trimTrailing(firstLine);

		return StringUtil.replaceFirst(
			content, previousLine + "\n" + line, firstLine + "\n" + secondLine,
			previousLineStartPos);
	}

	private String _getCombinedLinesContent(
		String content, String fileName, String absolutePath, String line,
		String trimmedLine, int lineLength, int lineCount, String previousLine,
		int lineTabCount, int previousLineTabCount) {

		if (Validator.isNull(line) || Validator.isNull(previousLine) ||
			isExcludedPath(
				_FIT_ON_SINGLE_LINE_EXCLUDES, absolutePath, lineCount)) {

			return null;
		}

		String trimmedPreviousLine = StringUtil.trimLeading(previousLine);

		String strippedQuotesLine = stripQuotes(line);
		String strippedQuotesPreviousLine = stripQuotes(previousLine);

		if (strippedQuotesLine.contains("// ") ||
			strippedQuotesLine.contains("/*") ||
			strippedQuotesLine.contains("*/") ||
			strippedQuotesPreviousLine.contains("// ") ||
			strippedQuotesPreviousLine.contains("/*") ||
			strippedQuotesPreviousLine.contains("*/")) {

			return null;
		}

		if (!trimmedPreviousLine.equals("return") &&
			previousLine.matches(".*\\w") &&
			trimmedLine.startsWith(StringPool.OPEN_PARENTHESIS)) {

			return _getCombinedLinesContent(
				content, line, trimmedLine, lineLength, lineCount, previousLine,
				StringPool.OPEN_PARENTHESIS, true, false, 0);
		}

		if (trimmedPreviousLine.matches("((else )?if|for|try|while) \\(")) {
			return _getCombinedLinesContent(
				content, line, trimmedLine, lineLength, lineCount, previousLine,
				null, false, false, 0);
		}

		if (previousLine.endsWith("= new")) {
			return _getCombinedLinesContent(
				content, line, trimmedLine, lineLength, lineCount, previousLine,
				"new", false, true, 0);
		}

		if (trimmedLine.startsWith("+ ") || trimmedLine.startsWith("- ") ||
			trimmedLine.startsWith("|| ") || trimmedLine.startsWith("&& ")) {

			int pos = trimmedLine.indexOf(CharPool.SPACE);

			String linePart = trimmedLine.substring(0, pos);

			return _getCombinedLinesContent(
				content, line, trimmedLine, lineLength, lineCount, previousLine,
				linePart, true, true, 0);
		}

		if (previousLine.endsWith("<") && !previousLine.endsWith(" <")) {
			return _getCombinedLinesContent(
				content, line, trimmedLine, lineLength, lineCount, previousLine,
				"<", false, false, 0);
		}

		int previousLineLength = getLineLength(previousLine);

		if ((trimmedLine.length() + previousLineLength) < getMaxLineLength()) {
			if (trimmedPreviousLine.startsWith("for ") &&
				previousLine.endsWith(StringPool.COLON) &&
				line.endsWith(StringPool.OPEN_CURLY_BRACE)) {

				return _getCombinedLinesContent(
					content, line, trimmedLine, lineLength, lineCount,
					previousLine, null, false, true, 0);
			}

			if (previousLine.endsWith(">") &&
				!trimmedPreviousLine.startsWith("<") &&
				(getLevel(previousLine, "<", ">") == 0) &&
				!trimmedLine.startsWith("extends") &&
				!trimmedLine.startsWith("implements")) {

				String beforePrevousLine = getLine(content, lineCount - 2);

				if (!beforePrevousLine.endsWith(".")) {
					return _getCombinedLinesContent(
						content, line, trimmedLine, lineLength, lineCount,
						previousLine, null, false, true, 0);
				}
			}

			if (line.endsWith(StringPool.SEMICOLON) &&
				!previousLine.endsWith(StringPool.COLON) &&
				!previousLine.endsWith(StringPool.OPEN_BRACKET) &&
				!previousLine.endsWith(StringPool.OPEN_CURLY_BRACE) &&
				!previousLine.endsWith(StringPool.OPEN_PARENTHESIS) &&
				!previousLine.endsWith(StringPool.PERIOD) &&
				(previousLine.contains("[") || !previousLine.contains("]")) &&
				(lineTabCount == (previousLineTabCount + 1))) {

				return _getCombinedLinesContent(
					content, line, trimmedLine, lineLength, lineCount,
					previousLine, null, false, true, 0);
			}

			if ((trimmedPreviousLine.startsWith("if ") ||
				 trimmedPreviousLine.startsWith("else ")) &&
				(previousLine.endsWith("||") || previousLine.endsWith("&&")) &&
				line.endsWith(StringPool.OPEN_CURLY_BRACE)) {

				return _getCombinedLinesContent(
					content, line, trimmedLine, lineLength, lineCount,
					previousLine, null, false, true, 0);
			}

			if (trimmedLine.startsWith("throws") &&
				(line.endsWith(StringPool.OPEN_CURLY_BRACE) ||
				 line.endsWith(StringPool.SEMICOLON)) &&
				(lineTabCount == (previousLineTabCount + 1))) {

				return _getCombinedLinesContent(
					content, line, trimmedLine, lineLength, lineCount,
					previousLine, null, false, true, 0);
			}

			if (previousLine.endsWith(StringPool.EQUAL) ||
				previousLine.endsWith("->")) {

				if (line.endsWith(StringPool.OPEN_CURLY_BRACE)) {
					addMessage(
						fileName,
						"'" + trimmedLine + "' should be added to previous " +
							"line",
						lineCount);

					return null;
				}

				if ((previousLine.endsWith(" =") ||
					 previousLine.endsWith(" ->")) &&
					line.endsWith(StringPool.OPEN_PARENTHESIS)) {

					for (int i = 0;; i++) {
						String nextLine = getLine(content, lineCount + i + 1);

						if (Validator.isNull(nextLine) ||
							nextLine.endsWith(") {")) {

							if (trimmedPreviousLine.startsWith("try (") &&
								trimmedLine.startsWith("new ") &&
								(getLevel(nextLine) == -1)) {

								return null;
							}

							addMessage(
								fileName,
								"'" + trimmedLine + "' should be added to " +
									"previous line",
								lineCount);

							return null;
						}

						if (nextLine.endsWith(") +")) {
							return null;
						}

						if (nextLine.endsWith(StringPool.SEMICOLON)) {
							return _getCombinedLinesContent(
								content, line, trimmedLine, lineLength,
								lineCount, previousLine, null, false, true,
								i + 1);
						}
					}
				}
			}

			if (trimmedPreviousLine.equals("return")) {
				for (int i = 0;; i++) {
					String nextLine = getLine(content, lineCount + i + 1);

					if (nextLine.endsWith(StringPool.SEMICOLON)) {
						return _getCombinedLinesContent(
							content, line, trimmedLine, lineLength, lineCount,
							previousLine, null, false, true, i + 1);
					}
				}
			}
		}

		if ((trimmedLine.length() + previousLineLength) <= getMaxLineLength()) {
			if (previousLine.endsWith(StringPool.OPEN_PARENTHESIS) &&
				line.matches(".*\\)( \\{)?") && (getLevel(line) < 0)) {

				return _getCombinedLinesContent(
					content, line, trimmedLine, lineLength, lineCount,
					previousLine, null, false, false, 0);
			}

			if ((previousLine.endsWith(StringPool.PERIOD) &&
				 !line.endsWith(StringPool.OPEN_PARENTHESIS)) ||
				((previousLine.endsWith(StringPool.OPEN_BRACKET) ||
				  previousLine.endsWith(StringPool.OPEN_PARENTHESIS)) &&
				 !trimmedPreviousLine.startsWith(").") &&
				 line.endsWith(StringPool.SEMICOLON))) {

				return _getCombinedLinesContent(
					content, line, trimmedLine, lineLength, lineCount,
					previousLine, null, false, false, 0);
			}

			if (previousLine.endsWith(StringPool.OPEN_PARENTHESIS) &&
				trimmedLine.equals(");")) {

				return _getCombinedLinesContent(
					content, line, trimmedLine, lineLength, lineCount,
					previousLine, null, false, false, 0);
			}
		}

		if ((previousLine.endsWith(StringPool.EQUAL) ||
			 previousLine.endsWith("->")) &&
			line.endsWith(StringPool.SEMICOLON)) {

			String tempLine = trimmedLine;

			for (int pos = 0;;) {
				pos = tempLine.indexOf(CharPool.DASH);

				if (pos == -1) {
					pos = tempLine.indexOf(CharPool.PLUS);
				}

				if (pos == -1) {
					pos = tempLine.indexOf(CharPool.SLASH);
				}

				if (pos == -1) {
					pos = tempLine.indexOf(CharPool.STAR);
				}

				if (pos == -1) {
					pos = tempLine.indexOf("||");
				}

				if (pos == -1) {
					pos = tempLine.indexOf("&&");
				}

				if (pos == -1) {
					break;
				}

				String linePart = tempLine.substring(0, pos);

				if (getLevel(linePart) == 0) {
					return null;
				}

				tempLine =
					tempLine.substring(0, pos) + tempLine.substring(pos + 1);
			}

			int x = trimmedLine.indexOf(CharPool.OPEN_PARENTHESIS);

			if (x == 0) {
				x = trimmedLine.indexOf(CharPool.OPEN_PARENTHESIS, 1);
			}

			if (x != -1) {
				int y = trimmedLine.indexOf(CharPool.CLOSE_PARENTHESIS, x);
				int z = trimmedLine.indexOf(CharPool.QUOTE);

				if (((x + 1) != y) && ((z == -1) || (z > x))) {
					char previousChar = trimmedLine.charAt(x - 1);

					if ((previousChar != CharPool.CLOSE_PARENTHESIS) &&
						(previousChar != CharPool.OPEN_PARENTHESIS) &&
						(previousChar != CharPool.SPACE) &&
						(previousLineLength + 1 + x) < getMaxLineLength()) {

						String linePart = trimmedLine.substring(0, x + 1);

						if (getLevel(linePart, "{", "}") > 0) {
							return null;
						}

						if (linePart.startsWith(StringPool.OPEN_PARENTHESIS) &&
							!linePart.contains(StringPool.CLOSE_PARENTHESIS)) {

							return null;
						}

						return _getCombinedLinesContent(
							content, line, trimmedLine, lineLength, lineCount,
							previousLine, linePart, true, true, 0);
					}
				}
			}
		}

		if (previousLine.endsWith(StringPool.PLUS) &&
			(lineTabCount == (previousLineTabCount + 1))) {

			int x = -1;

			while (true) {
				x = trimmedLine.indexOf(" +", x + 1);

				if ((x == -1) ||
					(previousLineLength + 3 + x) > getMaxLineLength()) {

					break;
				}

				if (ToolsUtil.isInsideQuotes(trimmedLine, x)) {
					continue;
				}

				String linePart = trimmedLine.substring(0, x + 2);

				if (getLevel(linePart) != 0) {
					continue;
				}

				if (trimmedLine.equals(linePart)) {
					addMessage(fileName, "Incorrect line break", lineCount);

					return null;
				}

				return _getCombinedLinesContent(
					content, line, trimmedLine, lineLength, lineCount,
					previousLine, linePart + StringPool.SPACE, true, true, 0);
			}
		}

		if (previousLine.endsWith(StringPool.COMMA) &&
			(previousLineTabCount == lineTabCount) &&
			!line.matches(".*[\\^\\|\\&]") &&
			!trimmedPreviousLine.matches("[\\)\\}],")) {

			String nextLine = getLine(content, lineCount + 1);

			int nextLineTabCount = getLeadingTabCount(nextLine);

			if (nextLineTabCount != (lineTabCount + 1)) {
				int x = -1;

				while (true) {
					x = trimmedLine.indexOf(CharPool.COMMA, x + 1);

					if (x == -1) {
						break;
					}

					if (ToolsUtil.isInsideQuotes(trimmedLine, x)) {
						continue;
					}

					String linePart = trimmedLine.substring(0, x);

					if ((getLevel(linePart, "(", ")") == 0) &&
						(getLevel(linePart, "{", "}") == 0) &&
						(getLevel(linePart, "<", ">") == 0)) {

						break;
					}
				}

				if (x != -1) {
					while ((previousLineLength + 1 + x) < getMaxLineLength()) {
						String linePart = trimmedLine.substring(0, x + 1);

						if (!ToolsUtil.isInsideQuotes(trimmedLine, x) &&
							JavaSourceUtil.isValidJavaParameter(linePart)) {

							if (trimmedLine.equals(linePart)) {
								return _getCombinedLinesContent(
									content, line, trimmedLine, lineLength,
									lineCount, previousLine, null, false, true,
									0);
							}
							else {
								return _getCombinedLinesContent(
									content, line, trimmedLine, lineLength,
									lineCount, previousLine,
									linePart + StringPool.SPACE, true, true, 0);
							}
						}

						String partAfterComma = trimmedLine.substring(x + 1);

						int pos = partAfterComma.indexOf(CharPool.COMMA);

						if (pos == -1) {
							break;
						}

						x = x + pos + 1;
					}
				}
				else if ((trimmedLine.length() + previousLineLength) <
							getMaxLineLength()) {

					if (!trimmedLine.startsWith("new ") ||
						!line.endsWith(StringPool.OPEN_CURLY_BRACE)) {

						return _getCombinedLinesContent(
							content, line, trimmedLine, lineLength, lineCount,
							previousLine, null, false, true, 0);
					}

					if (getLevel(line) != 0) {
						return _getCombinedLinesContent(
							content, line, trimmedLine, lineLength, lineCount,
							previousLine, null, false, true, 0);
					}
				}
			}
		}

		if (trimmedPreviousLine.matches("^[^<].*[\\w>]$") &&
			(previousLineTabCount == (lineTabCount - 1)) &&
			(getLevel(previousLine, "<", ">") == 0)) {

			int x = trimmedLine.indexOf(" = ");

			if ((x != -1) && !ToolsUtil.isInsideQuotes(trimmedLine, x) &&
				((previousLineLength + 2 + x) < getMaxLineLength())) {

				String linePart = trimmedLine.substring(0, x + 3);

				return _getCombinedLinesContent(
					content, line, trimmedLine, lineLength, lineCount,
					previousLine, linePart, true, true, 0);
			}
			else if (trimmedLine.endsWith(" =") &&
					 ((trimmedLine.length() + previousLineLength) <
						 getMaxLineLength())) {

				for (int i = 0;; i++) {
					String nextLine = getLine(content, lineCount + i + 1);

					if (nextLine.endsWith(StringPool.SEMICOLON)) {
						return _getCombinedLinesContent(
							content, line, trimmedLine, lineLength, lineCount,
							previousLine, null, false, true, i + 1);
					}
				}
			}
		}

		if (!previousLine.endsWith(StringPool.OPEN_PARENTHESIS)) {
			return null;
		}

		int x = -1;

		while (true) {
			x = trimmedLine.indexOf(") ", x + 1);

			if (x == -1) {
				break;
			}

			String linePart1 = trimmedLine.substring(0, x);

			if (ToolsUtil.isInsideQuotes(trimmedLine, x) ||
				(getLevel(linePart1) != 0)) {

				continue;
			}

			String linePart2 = trimmedLine.substring(x + 2);

			if (linePart2.matches("[!=<>\\+\\-\\*]+ .*")) {
				int y = trimmedLine.indexOf(StringPool.SPACE, x + 2);

				if ((previousLineLength + y) <= getMaxLineLength()) {
					return _getCombinedLinesContent(
						content, line, trimmedLine, lineLength, lineCount,
						previousLine, trimmedLine.substring(0, y), true, true,
						0);
				}
			}
		}

		if (StringUtil.count(previousLine, CharPool.OPEN_PARENTHESIS) > 1) {
			int pos = trimmedPreviousLine.lastIndexOf(
				CharPool.OPEN_PARENTHESIS, trimmedPreviousLine.length() - 2);

			if (pos > 0) {
				char c = trimmedPreviousLine.charAt(pos - 1);

				if ((c != CharPool.OPEN_PARENTHESIS) &&
					!Character.isWhitespace(c)) {

					String filePart = trimmedPreviousLine.substring(pos + 1);

					if (!filePart.contains(StringPool.CLOSE_PARENTHESIS) &&
						!filePart.contains(StringPool.QUOTE)) {

						return _getCombinedLinesContent(
							content, line, trimmedLine, lineLength, lineCount,
							previousLine, filePart, false, false, 0);
					}
				}
			}
		}

		if ((trimmedLine.length() + previousLineLength) > getMaxLineLength()) {
			return null;
		}

		if ((getLevel(trimmedLine) < 0) &&
			(line.matches(".*[|&^]") || line.endsWith(StringPool.COMMA) ||
			 (trimmedPreviousLine.startsWith("new ") &&
			  line.endsWith(") {")))) {

			return _getCombinedLinesContent(
				content, line, trimmedLine, lineLength, lineCount, previousLine,
				null, false, false, 0);
		}

		return null;
	}

	private static final String _FIT_ON_SINGLE_LINE_EXCLUDES =
		"fit.on.single.line.excludes";

	private final Pattern _combinedLinesPattern1 = Pattern.compile(
		"\n(\t*).+(=|\\]) (\\{)\n");
	private final Pattern _combinedLinesPattern2 = Pattern.compile(
		"\n(\t*)@.+(\\()\n");
	private final Pattern _combinedLinesPattern3 = Pattern.compile(
		"(\n\t*(private|protected|public) void)\n\t+(\\w+\\(\\)( \\{)?\n)");
	private final Pattern _combinedLinesPattern4 = Pattern.compile(
		"(\n\t*(extends|implements))\n\t+([\\w.]+ \\{\n)");

}