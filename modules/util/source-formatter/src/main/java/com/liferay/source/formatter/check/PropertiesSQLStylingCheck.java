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

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.source.formatter.check.util.SourceUtil;

import java.io.IOException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Alan Huang
 */
public class PropertiesSQLStylingCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws IOException {

		Matcher matcher = _sqlPattern1.matcher(content);

		while (matcher.find()) {
			String originalSqlClause = matcher.group(1);

			String sqlClause = originalSqlClause.replaceAll("\\\\\n *", "");

			sqlClause = _removeRedundantParenthesis(sqlClause);

			int x = sqlClause.indexOf("(");

			if (x == -1) {
				return content;
			}

			int y = x;
			String s = StringPool.BLANK;

			while (true) {
				y = sqlClause.indexOf(")", y + 1);

				if (y == -1) {
					return content;
				}

				s = sqlClause.substring(x, y + 1);

				int level = getLevel(s, "(", ")");

				if (level != 0) {
					continue;
				}

				if ((s.indexOf(" AND ") != -1) || (s.indexOf(" OR ") != -1)) {
					sqlClause = StringUtil.insert(sqlClause, "\\\n", y);
					sqlClause = StringUtil.insert(sqlClause, "\\\n", x + 1);
				}

				x = x + 1;
				x = sqlClause.indexOf("(", x);

				if (x == -1) {
					break;
				}

				y = x;
			}

			sqlClause = StringUtil.replace(sqlClause, " AND ", " AND \\\n");
			sqlClause = StringUtil.replace(sqlClause, " OR ", " OR \\\n");

			sqlClause = _checkIndentation(sqlClause);

			sqlClause = _sort(sqlClause);

			sqlClause = "\\\n" + sqlClause;

			if (!sqlClause.equals(originalSqlClause)) {
				return StringUtil.replaceFirst(
					content, originalSqlClause, sqlClause, matcher.start(1));
			}
		}

		return content;
	}

	private String _checkIndentation(String sqlClause) throws IOException {
		StringBundler sb = new StringBundler();

		try (UnsyncBufferedReader unsyncBufferedReader =
				new UnsyncBufferedReader(new UnsyncStringReader(sqlClause))) {

			int level = 2;

			String line = StringPool.BLANK;

			while ((line = unsyncBufferedReader.readLine()) != null) {
				if (line.startsWith("((") && line.endsWith("))")) {
					line = line.substring(1, line.length() - 1);
				}

				sb.append(_fixIndentation(line, level));
				sb.append("\n");

				level += getLevel(line, "(", ")");
			}
		}

		if (sb.index() > 0) {
			sb.setIndex(sb.index() - 1);
		}

		return sb.toString();
	}

	private int _compareTo(String sqlClause, String nextSQLClause) {
		if (sqlClause.endsWith("\")") && nextSQLClause.endsWith("\")")) {
			sqlClause = sqlClause.substring(0, sqlClause.length() - 2);
			nextSQLClause = nextSQLClause.substring(
				0, nextSQLClause.length() - 2);
		}

		return sqlClause.compareTo(nextSQLClause);
	}

	private String _fixIndentation(String line, int level) {
		String trimmedLine = StringUtil.trim(line);

		if (Validator.isNull(trimmedLine)) {
			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler();

		for (int i = 0; i < level; i++) {
			if ((i == (level - 1)) && trimmedLine.startsWith(")")) {
				break;
			}

			sb.append(StringPool.FOUR_SPACES);
		}

		sb.append(trimmedLine);

		return sb.toString();
	}

	private String _getSQLClause(String line) {
		Matcher matcher = _sqlPattern2.matcher(line);

		if (matcher.find()) {
			return matcher.group(1);
		}

		return null;
	}

	private String _removeRedundantParenthesis(String sqlClause) {
		int x = sqlClause.indexOf("(((");

		if (x == -1) {
			return sqlClause;
		}

		int y = x;
		String s = StringPool.BLANK;

		while (true) {
			y = sqlClause.indexOf(")))", y + 1);

			if (y == -1) {
				return sqlClause;
			}

			s = sqlClause.substring(x, y + 3);

			int level = getLevel(s, "(((", ")))");

			if (level != 0) {
				continue;
			}

			s = s.substring(1, s.length() - 1);

			sqlClause =
				sqlClause.substring(0, x) + s + sqlClause.substring(y + 3);

			x = x + 1;
			x = sqlClause.indexOf("(((", x);

			if (x == -1) {
				break;
			}

			y = x;
		}

		return sqlClause;
	}

	private String _sort(String sqlClauses) {
		Matcher matcher = _sqlPattern2.matcher(sqlClauses);

		while (matcher.find()) {
			int lineNumber = getLineNumber(sqlClauses, matcher.start());

			if (Validator.isNull(matcher.group(4))) {
				continue;
			}

			String nextSQLClause = _getSQLClause(
				SourceUtil.getLine(sqlClauses, lineNumber + 1));

			if (nextSQLClause == null) {
				continue;
			}

			String sqlClause = matcher.group(1);

			if (_compareTo(sqlClause, nextSQLClause) > 0) {
				sqlClauses = StringUtil.replaceFirst(
					sqlClauses, nextSQLClause, sqlClause,
					getLineStartPos(sqlClauses, lineNumber + 1));

				return StringUtil.replaceFirst(
					sqlClauses, sqlClause, nextSQLClause,
					getLineStartPos(sqlClauses, lineNumber));
			}
		}

		return sqlClauses;
	}

	private static final Pattern _sqlPattern1 = Pattern.compile(
		"(?<=\\A|\n) +test\\.batch\\.run\\.property\\.query.+]=([\\s\\S]*?" +
			"[^\\\\])(?=(\\Z|\n))");
	private static final Pattern _sqlPattern2 = Pattern.compile(
		"\\s(\\(.* ([!=]=|~) .+\\))( (AND|OR) )?(\\\\)?");

}