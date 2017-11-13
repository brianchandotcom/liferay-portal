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
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class XMLCustomSQLStylingCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws Exception {

		if (fileName.contains("/custom-sql/")) {
			_checkClosingParenthesis(fileName, content);
			_checkMissingParentheses(fileName, content);
			_checkScalability(fileName, absolutePath, content);

			content = _fixIncorrectAndOr(content);
			content = _fixMissingLineBreakAfterOpenParenthesis(content);
			content = _fixMissingLineBreakBeforeOpenParenthesis(content);
			content = _fixSinglePredicateClause(content);
			content = _formatSingleLineClauseWithMultiplePredicates(
				fileName, content);
		}

		return content;
	}

	private String _addTabs(String content, int start, int end) {
		for (int i = start; i <= end; i++) {
			int lineStartPos = getLineStartPos(content, i);

			content =
				content.substring(0, lineStartPos) + "\t" +
					content.substring(lineStartPos);
		}

		return content;
	}

	private void _checkClosingParenthesis(String fileName, String content) {
		int startPos = -1;

		while (true) {
			startPos = content.indexOf("\t(\n", startPos + 1);

			if (startPos == -1) {
				return;
			}

			int endPos = _getCloseParenthesisPos(content, startPos);

			int endLineCount = getLineCount(content, endPos);
			int endLineStartPos = content.lastIndexOf("\n", endPos);

			char c = content.charAt(endPos - 1);

			if (c != CharPool.TAB) {
				addMessage(
					fileName,
					"There should be a line break after '" +
						StringUtil.trim(
							content.substring(endLineStartPos, endPos)),
					endLineCount);

				continue;
			}

			int endLineTabCount = endPos - endLineStartPos - 1;

			int startLineStartPos = content.lastIndexOf("\n", startPos);

			int startLineTabCount = startPos - startLineStartPos;

			if (endLineTabCount != startLineTabCount) {
				addMessage(
					fileName,
					StringBundler.concat(
						"Line starts with '", String.valueOf(endLineTabCount),
						"' tabs, but '", String.valueOf(startLineTabCount),
						"' tabs are expected"),
					endLineCount);
			}
		}
	}

	private void _checkMissingParentheses(String fileName, String content) {
		Matcher matcher = _missingParenthesesPattern1.matcher(content);

		while (matcher.find()) {
			addMessage(
				fileName, "Missing parentheses",
				getLineCount(content, matcher.start()));
		}

		matcher = _missingParenthesesPattern2.matcher(content);

		while (matcher.find()) {
			String nextLine = getLine(
				content, getLineCount(content, matcher.end()));

			if (!nextLine.endsWith(" IN") && !nextLine.endsWith("EXISTS")) {
				addMessage(
					fileName, "Missing parentheses",
					getLineCount(content, matcher.end()));
			}
		}
	}

	private void _checkScalability(
		String fileName, String absolutePath, String content) {

		Matcher matcher = _whereNotInSQLPattern.matcher(content);

		while (matcher.find()) {
			int x = content.lastIndexOf("<sql id=", matcher.start());

			int y = content.indexOf(CharPool.QUOTE, x);

			int z = content.indexOf(CharPool.QUOTE, y + 1);

			String id = content.substring(y + 1, z);

			x = id.lastIndexOf(CharPool.PERIOD);

			y = id.lastIndexOf(CharPool.PERIOD, x - 1);

			String entityName = id.substring(y + 1, x);

			if (!isExcludedPath(
					_CUSTOM_FINDER_SCALABILITY_EXCLUDES, absolutePath,
					entityName)) {

				addMessage(
					fileName,
					"Avoid using WHERE ... NOT IN: " + id + ", see LPS-51315");
			}
		}
	}

	private String _fixIncorrectAndOr(String content) {
		Matcher matcher = _incorrectAndOrpattern.matcher(content);

		if (matcher.find()) {
			String whitespace = matcher.group(3);

			if (whitespace.equals(StringPool.SPACE)) {
				return StringUtil.replaceFirst(
					content, matcher.group(),
					StringPool.SPACE + matcher.group(2) + matcher.group(1),
					matcher.start() - 1);
			}

			return StringUtil.replaceFirst(
				content, matcher.group(1), StringPool.SPACE,
				matcher.start() - 1);
		}

		return content;
	}

	private String _fixMissingLineBreakAfterOpenParenthesis(String content) {
		Matcher matcher = _missingLineBreakAfterOpenParenthesisPattern.matcher(
			content);

		while (matcher.find()) {
			if (getLevel(matcher.group()) == 0) {
				continue;
			}

			int startPos = matcher.end(1);

			int startLineCount = getLineCount(content, startPos);

			int endPos = _getCloseParenthesisPos(content, startPos);

			int endLineCount = getLineCount(content, endPos);

			content = _addTabs(content, startLineCount + 1, endLineCount - 1);

			return StringUtil.replaceFirst(
				content, "\t(", "\t(\n\t" + matcher.group(1), matcher.start());
		}

		return content;
	}

	private String _fixMissingLineBreakBeforeOpenParenthesis(String content) {
		Matcher matcher = _missingLineBreakBeforeOpenParenthesisPattern.matcher(
			content);

		if (!matcher.find()) {
			return content;
		}

		int startPos = matcher.end() - 2;

		int startLineCount = getLineCount(content, startPos);

		int endPos = _getCloseParenthesisPos(content, startPos);

		int endLineCount = getLineCount(content, endPos);

		content = _addTabs(content, startLineCount + 1, endLineCount);

		return StringUtil.replaceFirst(
			content, "(\n", "\n\t" + matcher.group(1) + "(\n", matcher.start());
	}

	private String _fixSinglePredicateClause(String content) {
		Matcher matcher = _multiLineSinglePredicatePattern.matcher(content);

		while (matcher.find()) {
			String line = StringUtil.trim(matcher.group(1));

			if (!line.startsWith("[") || !line.endsWith("]")) {
				return StringUtil.replace(
					content, matcher.group(), "\t(" + line + ")");
			}
		}

		return content;
	}

	private String _formatSingleLineClauseWithMultiplePredicates(
		String fileName, String content) {

		Matcher matcher = _singleLineClauseWitMultiplePredicatesPattern.matcher(
			content);

		while (matcher.find()) {
			String afterOperator = matcher.group(5);
			String beforeOperator = matcher.group(3);
			String indent = matcher.group(1);
			String match = matcher.group(2);
			String operator = matcher.group(4);

			StringBundler sb = new StringBundler(11);

			if (beforeOperator.equals(")")) {
				sb.append(") ");
				sb.append(operator);
				sb.append("\n");
				sb.append(indent);
				sb.append(afterOperator);

				return StringUtil.replaceFirst(
					content, match, sb.toString(), matcher.start());
			}

			int lineCount = getLineCount(content, matcher.start(3));

			if ((getLevel(match) != 0) || !match.startsWith("(")) {
				addMessage(fileName, "One SQL predicate per line", lineCount);

				continue;
			}

			int beforeOperatorlevel = getLevel(beforeOperator);

			if ((beforeOperatorlevel < 0) || (beforeOperatorlevel > 1)) {
				addMessage(fileName, "One SQL predicate per line", lineCount);

				continue;
			}

			if (beforeOperatorlevel == 0) {
				sb.append(beforeOperator);
				sb.append(StringPool.SPACE);
				sb.append(operator);
				sb.append("\n");
				sb.append(indent);
				sb.append(afterOperator);

				return StringUtil.replaceFirst(
					content, match, sb.toString(), matcher.start());
			}

			sb.append("(\n\t");
			sb.append(indent);
			sb.append(beforeOperator.substring(1));
			sb.append(StringPool.SPACE);
			sb.append(operator);
			sb.append("\n\t");
			sb.append(indent);

			int pos = afterOperator.lastIndexOf(StringPool.CLOSE_PARENTHESIS);

			sb.append(afterOperator.substring(0, pos));

			sb.append("\n");
			sb.append(indent);
			sb.append(afterOperator.substring(pos));

			return StringUtil.replaceFirst(
				content, match, sb.toString(), matcher.start());
		}

		return content;
	}

	private int _getCloseParenthesisPos(String content, int startPos) {
		int endPos = startPos;

		while (true) {
			endPos = content.indexOf(")", endPos + 1);

			if (getLevel(content.substring(startPos, endPos + 1)) == 0) {
				return endPos;
			}
		}
	}

	private static final String _CUSTOM_FINDER_SCALABILITY_EXCLUDES =
		"custom.finder.scalability.excludes";

	private final Pattern _incorrectAndOrpattern = Pattern.compile(
		"(\n\t*)(AND|OR|\\[\\$AND_OR_CONNECTOR\\$\\])( |\n)");
	private final Pattern _missingLineBreakAfterOpenParenthesisPattern =
		Pattern.compile("(\t+)\\(.+\n");
	private final Pattern _missingLineBreakBeforeOpenParenthesisPattern =
		Pattern.compile("\n(\t+).*[^\t\n]\\(\n");
	private final Pattern _missingParenthesesPattern1 = Pattern.compile(
		"[^\\)\\]\\s]\\s+(AND|OR|\\[\\$AND_OR_CONNECTOR\\$\\])\\s");
	private final Pattern _missingParenthesesPattern2 = Pattern.compile(
		"\\s(AND|OR|\\[\\$AND_OR_CONNECTOR\\$\\])\\s+[^\\(\\[<\\s]");
	private final Pattern _multiLineSinglePredicatePattern = Pattern.compile(
		"\t\\(\n(.*)\n\t*\\)");
	private final Pattern _singleLineClauseWitMultiplePredicatesPattern =
		Pattern.compile(
			"\n(\t*)((.*\\)) (AND|OR|\\[\\$AND_OR_CONNECTOR\\$\\]) (\\(.*))");
	private final Pattern _whereNotInSQLPattern = Pattern.compile(
		"WHERE[ \t\n]+\\(*[a-zA-z0-9.]+ NOT IN");

}