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
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.tools.ToolsUtil;
import com.liferay.source.formatter.checks.util.JSPSourceUtil;
import com.liferay.source.formatter.checks.util.SourceUtil;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class JSPCoreTaglibCheck extends BaseJSPTermsCheck {

	@Override
	public boolean isLiferaySourceCheck() {
		return true;
	}

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws IOException {

		int lineCount = StringUtil.count(content, CharPool.NEW_LINE) + 1;

		Matcher matcher = _ifStatementPattern.matcher(content);

		outerLoop:
		while (matcher.find()) {
			List<Clause> clauses = new ArrayList<>();

			int lineNumber = getLineNumber(content, matcher.end(2));

			while (true) {
				Clause clause = _getClause(content, lineNumber);

				if (clause == null) {
					break;
				}

				clauses.add(clause);

				for (int i = clause.getEndLineNumber() + 1; i <= lineCount;
					 i++) {

					String line = StringUtil.trim(getLine(content, i));

					if (Validator.isNull(line)) {
						continue;
					}

					if (line.startsWith("else ")) {
						lineNumber = i;

						break;
					}

					String newContent = _formatClauses(content, clauses);

					if (!newContent.equals(content)) {
						populateContentsMap(fileName, newContent);

						Set<String> missingTaglibPrefixes =
							getMissingTaglibPrefixes(
								fileName,
								SetUtil.fromArray(new String[] {"c"}));

						if (missingTaglibPrefixes.isEmpty()) {
							return newContent;
						}
					}

					Clause firstClause = clauses.get(0);

					if (clauses.size() == 1) {
						addMessage(
							fileName, "Use 'c:if' tag instead of if-statement",
							firstClause.getStartLineNumber());
					}
					else {
						addMessage(
							fileName,
							"Use 'c:choose` tag instead of if/else-statement",
							firstClause.getStartLineNumber());
					}

					continue outerLoop;
				}
			}
		}

		return content;
	}

	private String _formatClauses(String content, List<Clause> clauses) {
		String originalContent = content;

		if (clauses.size() == 1) {
			Clause clause = clauses.get(0);

			content = _formatTag(
				content, null, clause.getEndLineNumber(), TagType.IF);

			return _formatTag(
				content, clause.getCondition(), clause.getStartLineNumber(),
				TagType.IF);
		}

		for (int i = clauses.size() - 1; i >= 0; i--) {
			Clause clause = clauses.get(i);

			String condition = clause.getCondition();

			if (i == 0) {
				content = _formatTag(
					content, null, clause.getEndLineNumber(), TagType.WHEN);

				content = _formatTag(
					content, condition, clause.getStartLineNumber(),
					TagType.CHOOSE_WHEN);

				continue;
			}

			if (condition != null) {
				return originalContent;
			}

			if (i == (clauses.size() - 1)) {
				content = _formatTag(
					content, null, clause.getEndLineNumber(),
					TagType.CHOOSE_OTHERWISE);

				content = _formatTag(
					content, null, clause.getStartLineNumber(),
					TagType.OTHERWISE);

				continue;
			}

			content = _formatTag(
				content, null, clause.getEndLineNumber(), TagType.WHEN);

			content = _formatTag(
				content, condition, clause.getStartLineNumber(), TagType.WHEN);
		}

		int x = content.indexOf(_CHOOSE_PLACEHOLDER);

		int startLineNumber = getLineNumber(content, x) + 1;

		content = StringUtil.replaceFirst(
			content, _CHOOSE_PLACEHOLDER, "<c:choose>");

		x = content.indexOf(_CHOOSE_PLACEHOLDER);

		int endLineNumber = getLineNumber(content, x) - 1;

		content = StringUtil.replaceFirst(
			content, _CHOOSE_PLACEHOLDER, "</c:choose>");

		for (int i = startLineNumber; i <= endLineNumber; i++) {
			content = StringUtil.insert(
				content, "\t", getLineStartPos(content, i));
		}

		return content;
	}

	private String _formatTag(
		String content, String condition, int lineNumber, TagType tagType) {

		String minIndent = null;

		for (int i = lineNumber + 1;; i++) {
			String line = getLine(content, i);

			if (line == null) {
				return null;
			}

			String trimmedLine = StringUtil.trim(line);

			if (!trimmedLine.equals("%>")) {
				if (Validator.isNotNull(line)) {
					minIndent = _getMinIndent(line, minIndent);
				}

				continue;
			}

			if (minIndent == null) {
				content = _removeLine(content, i);

				break;
			}

			String indent = SourceUtil.getIndent(line);

			if (!indent.equals(minIndent)) {
				content = StringUtil.replaceFirst(
					content, line, minIndent + "%>",
					getLineStartPos(content, i));
			}

			content = _insertLine(content, minIndent + "<%", lineNumber + 1);

			break;
		}

		String line = getLine(content, lineNumber);

		content = StringUtil.replaceFirst(
			content, line, _getTag(condition, line, tagType),
			getLineStartPos(content, lineNumber));

		minIndent = null;

		for (int i = lineNumber - 1;; i--) {
			if (lineNumber == 0) {
				return null;
			}

			line = getLine(content, i);

			String trimmedLine = StringUtil.trim(line);

			if (!trimmedLine.equals("<%")) {
				if (Validator.isNotNull(line)) {
					minIndent = _getMinIndent(line, minIndent);
				}

				continue;
			}

			if (minIndent == null) {
				content = _removeLine(content, i);

				break;
			}

			String indent = SourceUtil.getIndent(line);

			if (!indent.equals(minIndent)) {
				content = StringUtil.replaceFirst(
					content, line, minIndent + "<%",
					getLineStartPos(content, i));
			}

			content = _insertLine(content, minIndent + "%>", lineNumber);

			break;
		}

		return content;
	}

	private Clause _getClause(String content, int lineNumber) {
		int startPos = getLineStartPos(content, lineNumber + 1) - 2;

		if (!isJavaSource(content, startPos)) {
			return null;
		}

		String startLine = getLine(content, lineNumber);

		Matcher matcher1 = _elseIfStatementPattern.matcher(
			getLine(content, lineNumber));

		if (!matcher1.find()) {
			return null;
		}

		int endPos = startPos;

		while (true) {
			endPos = content.indexOf("}", endPos + 1);

			if (endPos == -1) {
				return null;
			}

			if (!isJavaSource(content, endPos) ||
				ToolsUtil.isInsideQuotes(content, endPos)) {

				continue;
			}

			int level = ToolsUtil.getLevel(
				content.substring(startPos, endPos + 1), "{", "}");

			if (level == 0) {
				break;
			}
		}

		String body = content.substring(startPos, endPos + 1);

		Matcher matcher2 = _branchStatementPattern.matcher(body);

		if (matcher2.find()) {
			return null;
		}

		int pos = body.indexOf("%>\n");

		if ((pos == -1) ||
			JSPSourceUtil.isJSSource(content, startPos + pos + 2)) {

			return null;
		}

		int endLineNumber = getLineNumber(content, endPos);

		if (Objects.equals(
				getLine(content, endLineNumber),
				SourceUtil.getIndent(startLine) + "}")) {

			return new Clause(matcher1.group(4), lineNumber, endLineNumber);
		}

		return null;
	}

	private String _getMinIndent(String line, String minIndent) {
		String indent = SourceUtil.getIndent(line);

		if ((minIndent == null) || (indent.length() < minIndent.length())) {
			return indent;
		}

		return minIndent;
	}

	private String _getTag(String condition, String line, TagType tagType) {
		String indent = SourceUtil.getIndent(line);

		if (tagType.equals(TagType.CHOOSE_WHEN)) {
			if (condition != null) {
				return StringBundler.concat(
					indent, _CHOOSE_PLACEHOLDER, "\n", indent,
					"<c:when test=\"<%= ", condition, " %>\">");
			}

			return StringBundler.concat(
				indent, "</c:when>\n", indent, _CHOOSE_PLACEHOLDER);
		}

		if (tagType.equals(TagType.CHOOSE_OTHERWISE)) {
			return StringBundler.concat(
				indent, "</c:otherwise>\n", indent, _CHOOSE_PLACEHOLDER);
		}

		if (tagType.equals(TagType.IF)) {
			if (condition != null) {
				return StringBundler.concat(
					indent, "<c:if test=\"<%= ", condition, " %>\">");
			}

			return indent + "</c:if>";
		}

		if (tagType.equals(TagType.OTHERWISE)) {
			return indent + "<c:otherwise>";
		}

		if (tagType.equals(TagType.WHEN)) {
			if (condition != null) {
				return StringBundler.concat(
					indent, "<c:when test=\"<%= ", condition, " %>\">");
			}

			return indent + "</c:when>";
		}

		return null;
	}

	private String _insertLine(String content, String line, int lineNumber) {
		return StringUtil.insert(
			content, line + "\n", getLineStartPos(content, lineNumber));
	}

	private String _removeLine(String content, int lineNumber) {
		return StringUtil.replaceFirst(
			content, getLine(content, lineNumber), "",
			getLineStartPos(content, lineNumber));
	}

	private static final String _CHOOSE_PLACEHOLDER = "CHOOSE_PLACEHOLDER";

	private static final Pattern _branchStatementPattern = Pattern.compile(
		"[\n\t](break|return)[\\s;]");
	private static final Pattern _elseIfStatementPattern = Pattern.compile(
		"^(\t*)(else \\{|(else )?if \\((.+)\\) \\{)$");
	private static final Pattern _ifStatementPattern = Pattern.compile(
		"\n(\t*)if \\((.*)\\) \\{\n");

	private class Clause {

		public Clause(
			String condition, int startLineNumber, int endLineNumber) {

			_condition = condition;
			_startLineNumber = startLineNumber;
			_endLineNumber = endLineNumber;
		}

		public String getCondition() {
			return _condition;
		}

		public int getEndLineNumber() {
			return _endLineNumber;
		}

		public int getStartLineNumber() {
			return _startLineNumber;
		}

		private final String _condition;
		private final int _endLineNumber;
		private final int _startLineNumber;

	}

	private enum TagType {

		CHOOSE_OTHERWISE, CHOOSE_WHEN, IF, OTHERWISE, WHEN

	}

}