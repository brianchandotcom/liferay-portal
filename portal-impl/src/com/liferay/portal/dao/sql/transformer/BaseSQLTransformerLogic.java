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
import com.liferay.portal.kernel.dao.db.DB;
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

	public BaseSQLTransformerLogic(DB db) {
		_db = db;
	}

	@Override
	public List<Function<String, String>> getFunctions() {
		return _functions;
	}

	protected String replaceBitwiseCheck(Matcher matcher) {
		return matcher.replaceAll("($1 & $2)");
	}

	protected String replaceBitwiseCheck(String sql) {
		return replaceBitwiseCheck(_bitwiseCheckPattern.matcher(sql));
	}

	protected String replaceBoolean(String sql) {
		return StringUtil.replace(
			sql, new String[] {"[$FALSE$]", "[$TRUE$]"},
			new String[] {_db.getTemplateFalse(), _db.getTemplateTrue()});
	}

	protected String replaceCastClobText(Matcher matcher) {
		return replaceCastText(matcher);
	}

	protected String replaceCastClobText(String sql) {
		return replaceCastClobText(_castClobTestPattern.matcher(sql));
	}

	protected String replaceCastLong(Matcher matcher) {
		return matcher.replaceAll("$1");
	}

	protected String replaceCastLong(String sql) {
		return replaceCastLong(_castLongPattern.matcher(sql));
	}

	protected String replaceCastText(Matcher matcher) {
		return matcher.replaceAll("$1");
	}

	protected String replaceCastText(String sql) {
		return replaceCastText(_castTextPattern.matcher(sql));
	}

	protected String replaceConcat(String sql) {
		SQLFunctionTransformer sqlFunctionTransformer =
			new SQLFunctionTransformer(
				"CONCAT(", StringPool.BLANK, " + ", StringPool.BLANK);

		return sqlFunctionTransformer.transform(sql);
	}

	protected String replaceDropTableIfExistsText(Matcher matcher) {
		return matcher.replaceAll("DROP TABLE IF EXISTS $1");
	}

	protected String replaceDropTableIfExistsText(String sql) {
		return replaceDropTableIfExistsText(
			_dropTableIfExistsTextPattern.matcher(sql));
	}

	protected String replaceInstr(Matcher matcher) {
		return matcher.replaceAll("CHARINDEX($2, $1)");
	}

	protected String replaceInstr(String sql) {
		return replaceInstr(_instrPattern.matcher(sql));
	}

	protected String replaceIntegerDivision(Matcher matcher) {
		return matcher.replaceAll("$1 / $2");
	}

	protected String replaceIntegerDivision(String sql) {
		return replaceIntegerDivision(_integerDivisionPattern.matcher(sql));
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

	protected String replaceMod(Matcher matcher) {
		return matcher.replaceAll("$1 % $2");
	}

	protected String replaceMod(String sql) {
		return replaceMod(_modPattern.matcher(sql));
	}

	protected String replaceNullDate(String sql) {
		return StringUtil.replace(sql, "[$NULL_DATE$]", "NULL");
	}

	protected String replaceSubstr(Matcher matcher) {
		return matcher.replaceAll("SUBSTRING($1, $2, $3)");
	}

	protected String replaceSubstr(String sql) {
		return replaceSubstr(_substrPattern.matcher(sql));
	}

	protected void setFunctions(List<Function<String, String>> functions) {
		_functions = functions;
	}

	private static final String _LOWER_CLOSE = StringPool.CLOSE_PARENTHESIS;

	private static final String _LOWER_OPEN = "lower(";

	private static final Pattern _bitwiseCheckPattern = Pattern.compile(
		"BITAND\\(\\s*(.+?)\\s*,\\s*(.+?)\\s*\\)");
	private static final Pattern _castClobTestPattern = Pattern.compile(
		"CAST_CLOB_TEXT\\((.+?)\\)", Pattern.CASE_INSENSITIVE);
	private static final Pattern _castLongPattern = Pattern.compile(
		"CAST_LONG\\((.+?)\\)", Pattern.CASE_INSENSITIVE);
	private static final Pattern _castTextPattern = Pattern.compile(
		"CAST_TEXT\\((.+?)\\)", Pattern.CASE_INSENSITIVE);
	private static final Pattern _dropTableIfExistsTextPattern =
		Pattern.compile(
			"DROP_TABLE_IF_EXISTS\\((.+?)\\)", Pattern.CASE_INSENSITIVE);
	private static final Pattern _instrPattern = Pattern.compile(
		"INSTR\\(\\s*(.+?)\\s*,\\s*(.+?)\\s*\\)", Pattern.CASE_INSENSITIVE);
	private static final Pattern _integerDivisionPattern = Pattern.compile(
		"INTEGER_DIV\\(\\s*(.+?)\\s*,\\s*(.+?)\\s*\\)",
		Pattern.CASE_INSENSITIVE);
	private static final Pattern _modPattern = Pattern.compile(
		"MOD\\(\\s*(.+?)\\s*,\\s*(.+?)\\s*\\)", Pattern.CASE_INSENSITIVE);
	private static final Pattern _substrPattern = Pattern.compile(
		"SUBSTR\\(\\s*(.+?)\\s*,\\s*(.+?)\\s*,\\s*(.+?)\\s*\\)",
		Pattern.CASE_INSENSITIVE);

	private final DB _db;
	private List<Function<String, String>> _functions;

}