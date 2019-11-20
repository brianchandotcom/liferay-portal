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
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.tools.ToolsUtil;
import com.liferay.source.formatter.checks.util.SourceUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public abstract class EmptyLinesCheck extends BaseFileCheck {

	protected String fixEmptyLinesBetweenTags(String content) {
		Matcher matcher = _emptyLineBetweenTagsPattern1.matcher(content);

		while (matcher.find()) {
			String tabs1 = matcher.group(1);
			String tabs2 = matcher.group(4);

			if (!tabs1.equals(tabs2)) {
				continue;
			}

			String lineBreaks = matcher.group(3);
			String tagName1 = matcher.group(2);
			String tagName2 = matcher.group(5);

			if (tagName1.endsWith(":when") ||
				(ArrayUtil.contains(_STYLING_TAG_NAMES, tagName1) &&
				 ArrayUtil.contains(_STYLING_TAG_NAMES, tagName2))) {

				if (lineBreaks.equals("\n\n")) {
					return StringUtil.replaceFirst(
						content, "\n\n", "\n", matcher.end(1));
				}
			}
			else if (lineBreaks.equals("\n")) {
				return StringUtil.replaceFirst(
					content, "\n", "\n\n", matcher.end(1));
			}
		}

		matcher = _emptyLineBetweenTagsPattern2.matcher(content);

		while (matcher.find()) {
			int lineNumber = getLineNumber(content, matcher.start());

			String line = getLine(content, lineNumber);

			String tabs = SourceUtil.getIndent(line);

			if (!tabs.equals(matcher.group(3))) {
				continue;
			}

			String trimmedLine = StringUtil.trimLeading(line);

			if (!trimmedLine.startsWith(StringPool.LESS_THAN) ||
				line.contains("\"hidden\"")) {

				continue;
			}

			line = getLine(content, lineNumber + 1);

			if (line.contains("\"hidden\"")) {
				continue;
			}

			String tagName1 = matcher.group(2);

			if (tagName1 == null) {
				int pos = trimmedLine.indexOf(CharPool.SPACE);

				tagName1 = trimmedLine.substring(1, pos);
			}

			String tagName2 = matcher.group(4);

			if (!tagName1.equals(tagName2) &&
				(!ArrayUtil.contains(_STYLING_TAG_NAMES, tagName1) ||
				 !ArrayUtil.contains(_STYLING_TAG_NAMES, tagName2))) {

				return StringUtil.replaceFirst(
					content, "\n", "\n\n", matcher.start());
			}
		}

		matcher = _missingEmptyLineBetweenTagsPattern1.matcher(content);

		while (matcher.find()) {
			String tabs1 = matcher.group(1);
			String tabs2 = matcher.group(2);

			if (tabs1.equals(tabs2)) {
				return StringUtil.replaceFirst(
					content, "\n", "\n\n", matcher.end(1));
			}
		}

		matcher = _missingEmptyLineBetweenTagsPattern2.matcher(content);

		while (matcher.find()) {
			String tabs1 = matcher.group(1);
			String tabs2 = matcher.group(2);

			if (tabs1.equals(tabs2)) {
				return StringUtil.replaceFirst(
					content, "\n", "\n\n", matcher.end(1));
			}
		}

		return content;
	}

	protected String fixEmptyLinesInMultiLineTags(String content) {
		Matcher matcher = _emptyLineInMultiLineTagsPattern1.matcher(content);

		if (matcher.find()) {
			return StringUtil.replaceFirst(
				content, "\n\n", "\n", matcher.start());
		}

		matcher = _emptyLineInMultiLineTagsPattern2.matcher(content);

		while (matcher.find()) {
			String tabs1 = matcher.group(1);
			String tabs2 = matcher.group(2);

			if (tabs1.length() == (tabs2.length() + 1)) {
				return StringUtil.replaceFirst(
					content, "\n\n", "\n", matcher.start());
			}
		}

		return content;
	}

	protected String fixEmptyLinesInNestedTags(String content) {
		content = fixEmptyLinesInNestedTags(
			content, _emptyLineInNestedTagsPattern1, true);

		return fixEmptyLinesInNestedTags(
			content, _emptyLineInNestedTagsPattern2, false);
	}

	protected String fixEmptyLinesInNestedTags(
		String content, Pattern pattern, boolean startTag) {

		Matcher matcher = pattern.matcher(content);

		while (matcher.find()) {
			String tabs2 = null;

			if (startTag) {
				String secondLine = matcher.group(3);

				if (secondLine.equals("<%") || secondLine.startsWith("<%--") ||
					secondLine.startsWith("<!--")) {

					continue;
				}

				tabs2 = matcher.group(2);
			}
			else {
				String firstLine = matcher.group(2);

				if (firstLine.equals("%>") || firstLine.endsWith("-->")) {
					continue;
				}

				tabs2 = matcher.group(3);
			}

			String tabs1 = matcher.group(1);

			if ((startTag && ((tabs1.length() + 1) == tabs2.length())) ||
				(!startTag && ((tabs1.length() - 1) == tabs2.length()))) {

				content = StringUtil.replaceFirst(
					content, StringPool.NEW_LINE, StringPool.BLANK,
					matcher.end(1));
			}
		}

		return content;
	}

	protected String fixIncorrectEmptyLineAfterOpenCurlyBrace(String content) {
		Matcher matcher = _incorrectOpenCurlyBracePattern.matcher(content);

		while (matcher.find()) {
			if (!isJavaSource(content, matcher.end())) {
				continue;
			}

			boolean requiresEmptyLine = false;

			String s = matcher.group(2);

			if (s != null) {
				if (getLevel(s) != 0) {
					continue;
				}

				int x = _getMatchingClosingCurlyBracePos(
					content, matcher.end() - 2);

				s = StringUtil.trim(content.substring(x + 1));

				if (!s.startsWith("}")) {
					requiresEmptyLine = true;
				}
			}
			else if (matcher.group(1) == null) {
				requiresEmptyLine = true;
			}

			String lineBreaks = matcher.group(4);

			if (requiresEmptyLine && lineBreaks.equals("\n")) {
				return StringUtil.replaceFirst(
					content, "\n", "\n\n", matcher.start(4));
			}

			if (!requiresEmptyLine && lineBreaks.equals("\n\n")) {
				return StringUtil.replaceFirst(
					content, "\n\n", "\n", matcher.start(4));
			}
		}

		return content;
	}

	protected String fixIncorrectEmptyLineBeforeCloseCurlyBrace(
		String content) {

		Matcher matcher1 = _incorrectCloseCurlyBracePattern1.matcher(content);

		while (matcher1.find()) {
			if (!isJavaSource(content, matcher1.end())) {
				continue;
			}

			String lastLine = StringUtil.trimLeading(matcher1.group(1));

			if (lastLine.startsWith("// ")) {
				continue;
			}

			String tabs = matcher1.group(2);

			int tabCount = tabs.length();

			int pos = matcher1.start();

			while (true) {
				pos = content.lastIndexOf("\n" + tabs, pos - 1);

				char c = content.charAt(pos + tabCount + 1);

				if ((c == CharPool.NEW_LINE) || (c == CharPool.TAB)) {
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

		matcher1 = _incorrectCloseCurlyBracePattern3.matcher(content);

		while (matcher1.find()) {
			if (!isJavaSource(content, matcher1.end())) {
				continue;
			}

			String lineBreaks = matcher1.group(1);

			int x = _getMatchingOpenCurlyBracePos(content, matcher1.end());

			int lineNumber = getLineNumber(content, x);

			String lineAfterOpenCurlyBrace = getLine(content, lineNumber + 1);

			if (Validator.isNull(lineAfterOpenCurlyBrace) &&
				lineBreaks.equals("\n")) {

				String nextLineAfterOpenCurlyBrace = StringUtil.trim(
					getLine(content, lineNumber + 2));

				if (nextLineAfterOpenCurlyBrace.startsWith("//")) {
					continue;
				}

				return StringUtil.replaceFirst(
					content, "\n", "\n\n", matcher1.start(1));
			}

			if (Validator.isNotNull(lineAfterOpenCurlyBrace) &&
				lineBreaks.equals("\n\n")) {

				return StringUtil.replaceFirst(
					content, "\n\n", "\n", matcher1.start(1));
			}
		}

		return content;
	}

	protected String fixMissingEmptyLineAfterSettingVariable(String content) {
		Matcher matcher = _setVariablePattern.matcher(content);

		while (matcher.find()) {
			if (!isJavaSource(content, matcher.start())) {
				continue;
			}

			if (content.charAt(matcher.end()) == CharPool.NEW_LINE) {
				continue;
			}

			int x = content.indexOf(";\n", matcher.end());

			if (x == -1) {
				return content;
			}

			String nextCommand = content.substring(matcher.end(), x + 1);

			if (nextCommand.contains("{\n") ||
				nextCommand.matches("\t*%>[\\S\\s]*")) {

				continue;
			}

			String variableName = matcher.group(2);

			Pattern pattern2 = Pattern.compile("\\W(" + variableName + ")\\W");

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
				content, "\n", "\n\n", matcher.end(3));
		}

		return content;
	}

	protected String fixMissingEmptyLines(String absolutePath, String content) {
		Matcher matcher = _missingEmptyLinePattern1.matcher(content);

		while (matcher.find()) {
			if (isJavaSource(content, matcher.start()) &&
				(getLevel(matcher.group()) == 0)) {

				return StringUtil.replaceFirst(
					content, "\n", "\n\n", matcher.start());
			}
		}

		matcher = _missingEmptyLinePattern2.matcher(content);

		while (matcher.find()) {
			if (!isJavaSource(content, matcher.start())) {
				continue;
			}

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
			if (isJavaSource(content, matcher.start()) &&
				(getLevel(matcher.group(1)) != 0)) {

				return StringUtil.replaceFirst(
					content, "\n", "\n\n", matcher.end(1));
			}
		}

		matcher = _missingEmptyLinePattern4.matcher(content);

		while (matcher.find()) {
			if (isJavaSource(content, matcher.start())) {
				return StringUtil.replaceFirst(
					content, "\n", "\n\n", matcher.start());
			}
		}

		matcher = _missingEmptyLinePattern5.matcher(content);

		while (matcher.find()) {
			if (isJavaSource(content, matcher.start())) {
				return StringUtil.replaceFirst(
					content, "\n", "\n\n", matcher.start() + 1);
			}
		}

		matcher = _missingEmptyLinePattern6.matcher(content);

		while (matcher.find()) {
			if (isJavaSource(content, matcher.start())) {
				return StringUtil.replaceFirst(
					content, "\n", "\n\n", matcher.start());
			}
		}

		matcher = _missingEmptyLinePattern7.matcher(content);

		while (matcher.find()) {
			if (isJavaSource(content, matcher.start())) {
				return StringUtil.replaceFirst(
					content, "\n", "\n\n", matcher.start());
			}
		}

		return content;
	}

	protected String fixMissingEmptyLinesAroundComments(String content) {
		Matcher matcher = _missingEmptyLineAfterComment.matcher(content);

		while (matcher.find()) {
			if (isJavaSource(content, matcher.start())) {
				return StringUtil.replaceFirst(
					content, "\n", "\n\n", matcher.start() + 1);
			}
		}

		matcher = _missingEmptyLineBeforeComment.matcher(content);

		while (matcher.find()) {
			if (isJavaSource(content, matcher.start())) {
				return StringUtil.replaceFirst(
					content, "\n", "\n\n", matcher.start() + 1);
			}
		}

		return content;
	}

	protected String fixRedundantEmptyLines(String content) {
		outerLoop:
		while (true) {
			Matcher matcher = _redundantEmptyLinePattern1.matcher(content);

			while (matcher.find()) {
				if (!isJavaSource(content, matcher.start())) {
					continue;
				}

				String previousLine = StringUtil.trim(matcher.group(1));

				if (previousLine.startsWith("import ") ||
					previousLine.startsWith("package ")) {

					continue;
				}

				content = StringUtil.replaceFirst(
					content, "\n", StringPool.BLANK, matcher.end(1));

				continue outerLoop;
			}

			matcher = _redundantEmptyLinePattern2.matcher(content);

			while (matcher.find()) {
				if (!isJavaSource(content, matcher.start())) {
					continue;
				}

				String nextLine = matcher.group(1);

				if (nextLine.startsWith("import ") ||
					nextLine.startsWith("package ") ||
					nextLine.startsWith("/*")) {

					continue;
				}

				content = StringUtil.replaceFirst(
					content, "\n", StringPool.BLANK, matcher.start() + 1);

				continue outerLoop;
			}

			matcher = _redundantEmptyLinePattern3.matcher(content);

			while (matcher.find()) {
				if (!isJavaSource(content, matcher.start())) {
					continue;
				}

				content = StringUtil.replaceFirst(
					content, "\n", StringPool.BLANK, matcher.start() + 1);

				continue outerLoop;
			}

			matcher = _redundantEmptyLinePattern4.matcher(content);

			while (matcher.find()) {
				if (!isJavaSource(content, matcher.start())) {
					continue;
				}

				content = StringUtil.replaceFirst(
					content, "\n", StringPool.BLANK, matcher.start());

				continue outerLoop;
			}

			matcher = _redundantEmptyLinePattern5.matcher(content);

			while (matcher.find()) {
				if (!isJavaSource(content, matcher.start())) {
					continue;
				}

				content = StringUtil.replaceFirst(
					content, "\n", StringPool.BLANK, matcher.start());

				continue outerLoop;
			}

			break;
		}

		return content;
	}

	protected boolean isJavaSource(String content, int pos) {
		return true;
	}

	private int _getMatchingClosingCurlyBracePos(String content, int start) {
		int x = start;

		while (true) {
			x = content.indexOf(CharPool.CLOSE_CURLY_BRACE, x + 1);

			if (getLevel(content.substring(start, x + 1), "{", "}") == 0) {
				return x;
			}
		}
	}

	private int _getMatchingOpenCurlyBracePos(String content, int start) {
		int x = start;

		while (true) {
			x = content.lastIndexOf(CharPool.OPEN_CURLY_BRACE, x - 1);

			int y = content.lastIndexOf("\n", x - 1);

			String s = StringUtil.trim(content.substring(y, x));

			if (s.startsWith("//") || s.startsWith("*")) {
				continue;
			}

			if (getLevel(content.substring(x, start), "{", "}") == 0) {
				return x;
			}
		}
	}

	private static final String[] _STYLING_TAG_NAMES = {
		"dd", "dt", "li", "span", "td", "th", "tr"
	};

	private static final Pattern _emptyLineBetweenTagsPattern1 =
		Pattern.compile("\n(\t*)</([-\\w:]+)>(\n*)(\t*)<([-\\w:]+)[> \n]");
	private static final Pattern _emptyLineBetweenTagsPattern2 =
		Pattern.compile("(\\S</(\\w+)>| />)\n(\t+)<([-\\w:]+)[> \n]");
	private static final Pattern _emptyLineInMultiLineTagsPattern1 =
		Pattern.compile("\n\t*<[-\\w:#]+\n\n\t*\\w");
	private static final Pattern _emptyLineInMultiLineTagsPattern2 =
		Pattern.compile("\n(\t*)\\S*[^>]\n\n(\t*)(/?)>\n");
	private static final Pattern _emptyLineInNestedTagsPattern1 =
		Pattern.compile("\n(\t*)(?:<\\w.*[^/])?>\n\n(\t*)(<.*)\n");
	private static final Pattern _emptyLineInNestedTagsPattern2 =
		Pattern.compile("\n(\t*)(.*>)\n\n(\t*)</.*(\n|$)");
	private static final Pattern _incorrectCloseCurlyBracePattern1 =
		Pattern.compile("\n(.+)\n\n(\t+)}\n");
	private static final Pattern _incorrectCloseCurlyBracePattern2 =
		Pattern.compile("(\t| )@?(class|enum|interface|new)\\s");
	private static final Pattern _incorrectCloseCurlyBracePattern3 =
		Pattern.compile("\n\t*\\}(\n+)\t*\\}\\)*;\n");
	private static final Pattern _incorrectOpenCurlyBracePattern =
		Pattern.compile(
			"\n.*?(\\Wnew (.*\\)) |\\[\\] (\\w+ = )?)?\\{(\n+)\t*\\{\n");
	private static final Pattern _missingEmptyLineAfterComment =
		Pattern.compile("\n\t*// .*\n[\t ]*(?!// )\\S");
	private static final Pattern _missingEmptyLineBeforeComment =
		Pattern.compile("\n[\t ]*(?!// )\\S.*\n\t*// ");
	private static final Pattern _missingEmptyLineBetweenTagsPattern1 =
		Pattern.compile("\n(\t*)/>\n(\t*)<[-\\w:]+[> \n]");
	private static final Pattern _missingEmptyLineBetweenTagsPattern2 =
		Pattern.compile(
			"\n(\t*)<.* />\n(\t*)<([-\\w:]+|\\w((?!</| />).)*[^/]>)\n");
	private static final Pattern _missingEmptyLinePattern1 = Pattern.compile(
		"(\t| = | -> |return )new .*\\(.*\\) \\{\n\t+[^{\t]");
	private static final Pattern _missingEmptyLinePattern2 = Pattern.compile(
		"(\n\t*)(public|private|protected) [^;]+? \\{");
	private static final Pattern _missingEmptyLinePattern3 = Pattern.compile(
		"\n(.*\\) \\{)\n[\t ]*[^ \n\t\\}]");
	private static final Pattern _missingEmptyLinePattern4 = Pattern.compile(
		"[^%{:/\n]\n\t*(for|if|try) [({]");
	private static final Pattern _missingEmptyLinePattern5 = Pattern.compile(
		"[\t\n]\\}\n[\t ]*(?!(%>|/?\\*|\\}|\\)|//|catch |else |finally " +
			"|while ))\\S");
	private static final Pattern _missingEmptyLinePattern6 = Pattern.compile(
		"[^%{:\\s]\n\t*(break|continue|return|throw)[ ;]");
	private static final Pattern _missingEmptyLinePattern7 = Pattern.compile(
		"[^%{\\s]\n\t*break;");
	private static final Pattern _redundantEmptyLinePattern1 = Pattern.compile(
		"\n(.*)\n\npublic ((abstract|static) )*(class|enum|interface) ");
	private static final Pattern _redundantEmptyLinePattern2 = Pattern.compile(
		"\n\t* \\*/\n\n\t*(.+)\n");
	private static final Pattern _redundantEmptyLinePattern3 = Pattern.compile(
		"[\n\t](catch |else |finally |for |if |try |while ).*\\{\n\n\t+\\w");
	private static final Pattern _redundantEmptyLinePattern4 = Pattern.compile(
		"\\{\n\n\t*\\}");
	private static final Pattern _redundantEmptyLinePattern5 = Pattern.compile(
		"\\}\n\n\t*(catch|else( if)?|finally) [\\(\\{]");
	private static final Pattern _setVariablePattern = Pattern.compile(
		"(?<=[\n\t])[A-Z]\\w+(<.+>)? (\\w+) =\\s+((?!\\{\n).)*?;\n",
		Pattern.DOTALL);

}