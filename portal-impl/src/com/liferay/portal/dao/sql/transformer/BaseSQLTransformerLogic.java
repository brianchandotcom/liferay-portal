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

package com.liferay.portal.dao.sql.transformer;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.internal.dao.sql.transformer.SQLFunctionTransformer;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.List;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Manuel de la Peña
 * @author Brian Wing Shun Chan
 */
public abstract class BaseSQLTransformerLogic implements SQLTransformerLogic {

	@Override
	public List<Function<String, String>> getFunctions() {
		return _functions;
	}

	protected String replaceBitwiseCheck(String sql) {
		Matcher matcher = bitwiseCheckPattern.matcher(sql);

		return matcher.replaceAll("($1 & $2)");
	}

	protected String replaceBoolean(
		String sql, String templateFalse, String templateTrue) {

		return StringUtil.replace(
			sql, new String[] {"[$FALSE$]", "[$TRUE$]"},
			new String[] {templateFalse, templateTrue});
	}

	protected String replaceCastClobText(String sql) {
		Matcher matcher = castClobTestPattern.matcher(sql);

		return matcher.replaceAll("$1");
	}

	protected String replaceCastLong(String sql) {
		Matcher matcher = castLongPattern.matcher(sql);

		return matcher.replaceAll("$1");
	}

	protected String replaceCastText(String sql) {
		Matcher matcher = castTextPattern.matcher(sql);

		return matcher.replaceAll("$1");
	}

	protected String replaceConcat(String sql) {
		SQLFunctionTransformer sqlFunctionTransformer =
			new SQLFunctionTransformer(
				"CONCAT(", StringPool.BLANK, " + ", StringPool.BLANK);

		return sqlFunctionTransformer.transform(sql);
	}

	protected String replaceDropTableIfExistsText(String sql) {
		Matcher matcher = dropTableIfExistsTextPattern.matcher(sql);

		return matcher.replaceAll("DROP TABLE IF EXISTS $1");
	}

	protected String replaceInstr(String sql) {
		Matcher matcher = instrPattern.matcher(sql);

		return matcher.replaceAll("CHARINDEX($2, $1)");
	}

	protected String replaceIntegerDivision(String sql) {
		Matcher matcher = integerDivisionPattern.matcher(sql);

		return matcher.replaceAll("$1 / $2");
	}

	protected String replaceLength(String sql) {
		return StringUtil.replace(sql, "LENGTH(", "LEN(");
	}

	protected String replaceLower(String sql) {
		int x = sql.indexOf(_LOWER_OPEN);

		if (x == -1) {
			return sql;
		}

		StringBuilder sb = new StringBuilder(sql.length());

		int y = 0;

		while (true) {
			sb.append(sql.substring(y, x));

			y = sql.indexOf(_LOWER_CLOSE, x);

			if (y == -1) {
				sb.append(sql.substring(x));

				break;
			}

			sb.append(sql.substring(x + _LOWER_OPEN.length(), y));

			y++;

			x = sql.indexOf(_LOWER_OPEN, y);

			if (x == -1) {
				sb.append(sql.substring(y));

				break;
			}
		}

		sql = sb.toString();

		return sql;
	}

	protected String replaceMod(String sql) {
		Matcher matcher = modPattern.matcher(sql);

		return matcher.replaceAll("$1 % $2");
	}

	protected String replaceNullDate(String sql) {
		return StringUtil.replace(sql, "[$NULL_DATE$]", "NULL");
	}

	protected String replaceSubstr(String sql) {
		Matcher matcher = substrPattern.matcher(sql);

		return matcher.replaceAll("SUBSTRING($1, $2, $3)");
	}

	protected void setFunctions(List<Function<String, String>> functions) {
		_functions = functions;
	}

	protected static final Pattern bitwiseCheckPattern = Pattern.compile(
		"BITAND\\(\\s*(.+?)\\s*,\\s*(.+?)\\s*\\)");
	protected static final Pattern castClobTestPattern = Pattern.compile(
		"CAST_CLOB_TEXT\\((.+?)\\)", Pattern.CASE_INSENSITIVE);
	protected static final Pattern castLongPattern = Pattern.compile(
		"CAST_LONG\\((.+?)\\)", Pattern.CASE_INSENSITIVE);
	protected static final Pattern castTextPattern = Pattern.compile(
		"CAST_TEXT\\((.+?)\\)", Pattern.CASE_INSENSITIVE);
	protected static final Pattern dropTableIfExistsTextPattern =
		Pattern.compile(
			"DROP_TABLE_IF_EXISTS\\((.+?)\\)", Pattern.CASE_INSENSITIVE);
	protected static final Pattern instrPattern = Pattern.compile(
		"INSTR\\(\\s*(.+?)\\s*,\\s*(.+?)\\s*\\)", Pattern.CASE_INSENSITIVE);
	protected static final Pattern integerDivisionPattern = Pattern.compile(
		"INTEGER_DIV\\(\\s*(.+?)\\s*,\\s*(.+?)\\s*\\)",
		Pattern.CASE_INSENSITIVE);
	protected static final Pattern modPattern = Pattern.compile(
		"MOD\\(\\s*(.+?)\\s*,\\s*(.+?)\\s*\\)", Pattern.CASE_INSENSITIVE);
	protected static final Pattern substrPattern = Pattern.compile(
		"SUBSTR\\(\\s*(.+?)\\s*,\\s*(.+?)\\s*,\\s*(.+?)\\s*\\)",
		Pattern.CASE_INSENSITIVE);

	private static final String _LOWER_CLOSE = StringPool.CLOSE_PARENTHESIS;

	private static final String _LOWER_OPEN = "lower(";

	private List<Function<String, String>> _functions;

}