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

package com.liferay.source.formatter;

import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Tuple;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.tools.ToolsUtil;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class JavaSourceTabCalculator {

	public void calculateTabs(
			String fileName, String content, int lineCount, int tabLevel,
			SourceProcessor sourceProcessor)
		throws Exception {

		content = stripTrailingComments(content);

		try (UnsyncBufferedReader unsyncBufferedReader =
				new UnsyncBufferedReader(new UnsyncStringReader(content))) {

			String line = null;

			boolean forClause = false;
			boolean ifClause = false;
			boolean multiLineComment = false;

			_extraTabMap = new HashMap<>();
			_ignoreTabCheck = new HashSet<>();
			_printIncorrectTabMessage = true;

			String remainingContent = content;

			while ((line = unsyncBufferedReader.readLine()) != null) {
				lineCount++;

				String trimmedLine = StringUtil.trimLeading(line);

				int leadingTabCount = _javaSourceProcessor.getLeadingTabCount(
					line);

				if (trimmedLine.startsWith("/*") &&
					!trimmedLine.startsWith("/**")) {

					multiLineComment = true;
				}

				if (!multiLineComment) {
					if (trimmedLine.startsWith("for (") ||
						trimmedLine.startsWith("try (")) {

						forClause = true;
					}

					if (line.endsWith(") {")) {
						forClause = false;
					}

					if (Validator.isNotNull(line) && !ifClause) {
						checkTabLevel(
							trimmedLine, leadingTabCount, tabLevel, fileName,
							lineCount, sourceProcessor);
					}

					if (trimmedLine.startsWith("catch (") ||
						trimmedLine.startsWith("if (") ||
						trimmedLine.startsWith("else if (") ||
						trimmedLine.startsWith("while (")) {

						ifClause = true;
					}

					if (!trimmedLine.startsWith("*") &&
						!trimmedLine.startsWith("/*") &&
						!trimmedLine.startsWith("//")) {

						tabLevel = calculateTabLevel(tabLevel, line);
						calculateExtraTabs(
							trimmedLine, lineCount, remainingContent, forClause,
							ifClause);
					}
				}
				else if (line.endsWith("*/")) {
					multiLineComment = false;
				}

				remainingContent = StringUtil.replaceFirst(
					remainingContent, line + "\n", "");

				if (line.endsWith(StringPool.SEMICOLON) ||
					line.endsWith(") {")) {

					ifClause = false;
				}
			}
		}
	}

	public void calculateTabs(
			String fileName, String content, SourceProcessor sourceProcessor)
		throws Exception {

		calculateTabs(fileName, content, 0, 0, sourceProcessor);
	}

	protected void addExtraTabs(int lineCount, int extra) {
		for (int i = lineCount + 1; i <= lineCount + extra; i++) {
			Integer count = _extraTabMap.get(i);

			if (count == null) {
				count = 0;
			}

			_extraTabMap.put(i, count + 1);
		}
	}

	protected void addIgnoreTabChecks(int lineCount, int extra) {
		for (int i = lineCount + 1; i <= lineCount + extra; i++) {
			_ignoreTabCheck.add(i);
		}
	}

	protected void calculateExtraTabs(
		String line, int lineCount, String remainingContent, boolean forClause,
		boolean ifClause) {

		if (Validator.isNull(line)) {
			return;
		}

		Matcher matcher1 = _methodDeclarationPattern.matcher(remainingContent);

		if (matcher1.find()) {
			String match = matcher1.group();

			Matcher matcher2 = _throwsExceptionPattern.matcher(match);

			if (matcher2.find()) {
				addExtraTabs(lineCount, StringUtil.count(match, '\n') - 1);
			}
		}

		int lineTabLevel = calculateTabLevel(0, line);

		if (skipCalculateExtraTabs(line, lineTabLevel, forClause, ifClause)) {
			return;
		}

		String[] texts =
			new String[] {"{\n", ";\n", ",\n", "\n)", "\t)", "\t}", ")\n"};

		if (line.endsWith(":")) {
			if (line.startsWith("case ") || line.startsWith("default")) {
				texts = new String[] {":\n", "\t}"};
			}
			else if (!line.endsWith(" :")) {
				return;
			}
		}

		if (line.endsWith(",") &&
			(line.startsWith("extends ") || line.startsWith("implements ") ||
			 line.startsWith("throws "))) {

			texts = new String[] {"{\n", ";\n"};
		}

		if (forClause && (line.endsWith("=") || line.endsWith("("))) {
			texts = new String[] {"{\n", ";\n"};
		}

		int x = -1;

		outerLoop:
		while (true) {
			Tuple tuple = getFirstOccurenceTuple(
				remainingContent, texts, x + 1);

			x = (Integer)tuple.getObject(1);

			if (x == -1) {
				return;
			}

			String matchingText = (String)tuple.getObject(0);

			String s = StringUtil.trimLeading(remainingContent.substring(0, x));

			int level = calculateTabLevel(0, s);

			if ((line.startsWith("case ") || line.startsWith("default")) &&
				line.endsWith(":")) {

				if (!s.contains("\n") || (level != 0)) {
					continue;
				}
			}

			if ((line.startsWith("for (") || line.startsWith("try (")) &&
				matchingText.equals(";\n") && s.contains("\n")) {

				if (level > 1) {
					continue;
				}
			}
			else if (level > 0) {
				continue;
			}

			if (matchingText.equals("{\n")) {
				int y = -1;

				while (true) {
					Tuple tuple2 = getFirstOccurenceTuple(
						s, new String[] {"\tnew ", " new ", " ->"}, y + 1);

					y = (Integer)tuple2.getObject(1);

					if (y == -1) {
						break;
					}

					if (calculateTabLevel(0, s.substring(y)) == 0) {
						continue outerLoop;
					}
				}
			}

			if (matchingText.equals(",\n") &&
				(_javaSourceProcessor.getLevel(s, "<", ">") > 0)) {

				continue;
			}

			int extra = StringUtil.count(s, '\n');

			if (matchingText.equals("\t)") || matchingText.equals("\t}") ||
				matchingText.equals(":\n")) {

				extra = extra - 1;
			}

			if (StringUtil.trim(s).endsWith("Exception") &&
				s.contains("\tthrows ")) {

				extra = extra - 1;
			}

			if (line.startsWith("extends ") && !line.endsWith(",")) {
				int pos = remainingContent.indexOf("\n");

				String remainingContentMinusFirstLine = StringUtil.trim(
					remainingContent.substring(pos));

				if (remainingContentMinusFirstLine.startsWith("implements ")) {
					continue;
				}

				extra = 1;
			}

			if (!forClause && !ifClause &&
				((lineTabLevel > 1) ||
				 (line.startsWith("(") && !line.contains(")") &&
				  (lineTabLevel == 1)))) {

				addIgnoreTabChecks(lineCount, extra);

				return;
			}

			if (line.startsWith("-(") || line.endsWith("&") ||
				line.endsWith("|") || (line.endsWith("+") && (extra > 2))) {

				addIgnoreTabChecks(lineCount, extra);

				return;
			}

			if (line.endsWith("(") && forClause) {
				if (s.contains(";\n")) {
					addIgnoreTabChecks(lineCount, extra);
				}

				return;
			}

			if ((lineTabLevel == 0) && !line.startsWith(")") &&
				line.endsWith("(")) {

				addIgnoreTabChecks(lineCount, extra);

				return;
			}

			addExtraTabs(lineCount, extra);

			String lineMinusLastChar = StringUtil.trim(
				line.substring(0, line.length() - 1));

			if (lineMinusLastChar.endsWith(")") && (lineTabLevel < 0)) {
				addExtraTabs(lineCount, extra);
			}

			if (line.endsWith(",") &&
				(line.startsWith("extends ") ||
				 line.startsWith("implements "))) {

				addExtraTabs(lineCount, extra);
				addExtraTabs(lineCount, extra);
			}

			return;
		}
	}

	protected int calculateTabLevel(int level, String text) {
		return _javaSourceProcessor.getLevel(
			text,
			new String[] {
				StringPool.OPEN_CURLY_BRACE, StringPool.OPEN_PARENTHESIS
			},
			new String[] {
				StringPool.CLOSE_CURLY_BRACE, StringPool.CLOSE_PARENTHESIS
			},
			level);
	}

	protected void checkTabLevel(
		String trimmedLine, int leadingTabCount, int tabLevel, String fileName,
		int lineCount, SourceProcessor sourceProcessor) {

		if (_ignoreTabCheck.contains(lineCount)) {
			_printIncorrectTabMessage = true;

			return;
		}

		if (hasCorrectTabCount(
				trimmedLine, leadingTabCount, tabLevel, lineCount)) {

			_printIncorrectTabMessage = true;
		}
		else {
			if (_printIncorrectTabMessage) {
				sourceProcessor.processMessage(
					fileName, "Incorrect tab or line break", lineCount);
			}

			_printIncorrectTabMessage = false;
		}
	}

	protected Tuple getFirstOccurenceTuple(
		String s, String[] texts, int startIndex) {

		String matchingText = null;
		int firstIndexOf = -1;

		for (String text : texts) {
			int x = s.indexOf(text, startIndex);

			if (x == -1) {
				continue;
			}

			if ((firstIndexOf == -1) || (x < firstIndexOf)) {
				firstIndexOf = x;
				matchingText = text;
			}
		}

		return new Tuple(matchingText, firstIndexOf);
	}

	protected boolean hasCorrectTabCount(
		String trimmedLine, int leadingTabCount, int tabLevel, int lineCount) {

		Integer extraTabs = _extraTabMap.get(lineCount);

		if (extraTabs == null) {
			extraTabs = 0;
		}

		if (trimmedLine.startsWith(StringPool.CLOSE_CURLY_BRACE) ||
			trimmedLine.startsWith(StringPool.CLOSE_PARENTHESIS)) {

			if ((leadingTabCount + 1) == (tabLevel + extraTabs)) {
				return true;
			}

			return false;
		}

		if (leadingTabCount == (tabLevel + extraTabs)) {
			return true;
		}

		return false;
	}

	protected boolean skipCalculateExtraTabs(
		String line, int lineTabLevel, boolean forClause, boolean ifClause) {

		if (line.startsWith("@") || line.equals("}") || line.equals(")")) {
			return true;
		}

		if (!forClause && !ifClause &&
			((lineTabLevel > 1) ||
			 (line.startsWith("(") && !line.contains(")") &&
			  (lineTabLevel == 1)))) {

			return false;
		}

		if ((lineTabLevel == 0) && !line.startsWith(")") &&
			line.endsWith("(")) {

			return false;
		}

		if ((line.endsWith("(") || line.endsWith("{")) && !forClause) {
			return true;
		}

		if (line.endsWith(";")) {
			return true;
		}

		if (line.endsWith(",") && !line.startsWith("extends ") &&
			!line.startsWith("implements ") && !line.startsWith("throws ") &&
			!line.startsWith("<")) {

			return true;
		}

		if (line.endsWith(")") && !line.startsWith("(") &&
			!line.startsWith("return (")) {

			return true;
		}

		if (line.startsWith("(") && (lineTabLevel > 0)) {
			return true;
		}

		return false;
	}

	protected String stripTrailingComments(String content) {
		Matcher matcher = _trailingCommentPattern.matcher(content);

		while (matcher.find()) {
			String match = matcher.group();

			if (!ToolsUtil.isInsideQuotes(
					match, matcher.start(2) - matcher.start())) {

				String replacement = StringUtil.replace(
					match, matcher.group(2), StringPool.BLANK);

				content = StringUtil.replace(content, match, replacement);
			}
		}

		return content;
	}

	private Map<Integer, Integer> _extraTabMap;
	private Set<Integer> _ignoreTabCheck;
	private final JavaSourceProcessor _javaSourceProcessor =
		new JavaSourceProcessor();
	private final Pattern _methodDeclarationPattern = Pattern.compile(
		"^\\s*(private|protected|public) .*?(\\{|;)\n", Pattern.DOTALL);
	private boolean _printIncorrectTabMessage;
	private final Pattern _throwsExceptionPattern = Pattern.compile(
		"\t((default .*)|(throws (E|(.*(Error|Exception|Fault|Throwable)))))" +
			"(;| \\{)$",
		Pattern.DOTALL);
	private final Pattern _trailingCommentPattern = Pattern.compile(
		"(\n|\t).*\\S(( |\t)+//.*?)\n");

}