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

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Hugo Huijser
 */
public class XMLCustomSQLIndentationCheck extends BaseFileCheck {

	@Override
	public boolean isPortalCheck() {
		return true;
	}

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws Exception {

		if (isSubrepository() || isReadOnly(absolutePath)) {
			return content;
		}

		if (fileName.contains("/custom-sql/")) {
			_checkIndentation(fileName, StringUtil.splitLines(content));
		}

		return content;
	}

	private void _checkIndentation(String fileName, String[] lines)
		throws Exception {

		boolean insideSQL = false;
		int sqlStartLineIndex = -1;
		int level = 0;

		for (int i = 0; i < lines.length; i++) {
			String line = lines[i];

			if (insideSQL) {
				if (line.contains("]]>")) {
					_checkSQLIndentation(
						fileName, level, lines, sqlStartLineIndex, i - 1);

					insideSQL = false;

					level--;
				}
				else {
					continue;
				}
			}

			if (!insideSQL && _startsWithKeyword(line)) {
				addMessage(fileName, "Use CDATA markup", i + 1);

				return;
			}

			if (line.startsWith("</") || line.contains("\t</")) {
				level--;
			}

			_checkLine(fileName, line, level, i + 1);

			if (line.matches("\t*<[^?/].*[^/]>")) {
				level++;
			}
			else if (line.contains("<![CDATA[")) {
				insideSQL = true;
				level++;
				sqlStartLineIndex = i + 1;
			}
		}
	}

	private void _checkLine(
		String fileName, String line, int expectedIndentation, int lineCount) {

		if (line.matches("\\s*\\[.*\\]")) {
			return;
		}

		int actualIndentation = getLeadingTabCount(line);

		if (actualIndentation != expectedIndentation) {
			addMessage(
				fileName,
				StringBundler.concat(
					"Line starts with '", String.valueOf(actualIndentation),
					"' tabs, but '", String.valueOf(expectedIndentation),
					"' tabs are expected"),
				lineCount);
		}
	}

	private void _checkSQLIndentation(
		String fileName, int expectedIndentation, String[] lines,
		int startLineIndex, int endLineIndex) {

		String firstLine = null;

		while (true) {
			firstLine = lines[startLineIndex];

			if (!firstLine.matches("\\s*\\[.*\\]")) {
				break;
			}

			startLineIndex++;
		}

		_checkLine(
			fileName, firstLine, expectedIndentation, startLineIndex + 1);

		boolean insideOnStatement = false;

		String previousLine = lines[startLineIndex];

		for (int i = startLineIndex + 1; i <= endLineIndex; i++) {
			String line = lines[i];

			if (_startsWithKeyword(previousLine) ||
				previousLine.endsWith("\t(") || previousLine.endsWith(" =") ||
				previousLine.endsWith("EXISTS") ||
				previousLine.endsWith(" IN")) {

				expectedIndentation++;
			}
			else if (_startsWithKeyword(line)) {
				expectedIndentation--;

				insideOnStatement = false;
			}
			else if (previousLine.endsWith(" ON")) {
				insideOnStatement = true;
			}

			if (line.contains("\t)")) {
				int matchingOpenParenthesesLineIndex =
					_getMatchingOpenParenthesesLineIndex(lines, i);

				expectedIndentation = getLeadingTabCount(
					lines[matchingOpenParenthesesLineIndex]);

				_checkLine(fileName, line, expectedIndentation, i + 1);

				String beforeOpenParenthesesLine =
					lines[matchingOpenParenthesesLineIndex - 1];

				if (beforeOpenParenthesesLine.endsWith(" =") ||
					beforeOpenParenthesesLine.endsWith("EXISTS") ||
					beforeOpenParenthesesLine.endsWith(" IN")) {

					expectedIndentation--;
				}
			}
			else if (insideOnStatement) {
				_checkLine(fileName, line, expectedIndentation + 1, i + 1);
			}
			else {
				_checkLine(fileName, line, expectedIndentation, i + 1);
			}

			previousLine = line;
		}
	}

	private int _getMatchingOpenParenthesesLineIndex(
		String[] lines, int index) {

		int level = -1;

		for (int i = index - 1;; i--) {
			String line = lines[i];

			level += getLevel(line);

			if (level == 0) {
				return i;
			}
		}
	}

	private boolean _startsWithKeyword(String line) {
		String trimmedLine = StringUtil.trim(line);

		if (trimmedLine.matches(
				"((DELETE )?FROM|GROUP BY|HAVING|ORDER BY|SELECT|SET|UPDATE|" +
					"WHERE|((CROSS|LEFT|RIGHT) )?((INNER|OUTER) )?JOIN)" +
						"(\\s.*)?")) {

			return true;
		}

		return false;
	}

}