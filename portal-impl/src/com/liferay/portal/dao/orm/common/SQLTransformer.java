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

package com.liferay.portal.dao.orm.common;

import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBFactoryUtil;
import com.liferay.portal.kernel.dao.db.DBType;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
public class SQLTransformer {

	public static void reloadSQLTransformer() {
		_instance._reloadSQLTransformer();
	}

	public static String transform(String sql) {
		return _instance._transform(sql);
	}

	public static String transformFromHqlToJpql(String sql) {
		return _instance._transformFromHqlToJpql(sql);
	}

	public static String transformFromJpqlToHql(String sql) {
		return _instance._transformFromJpqlToHql(sql);
	}

	private SQLTransformer() {
		_reloadSQLTransformer();
	}

	private void _reloadSQLTransformer() {
		if (_transformedSqls == null) {
			_transformedSqls = new ConcurrentHashMap<>();
		}
		else {
			_transformedSqls.clear();
		}

		_vendorDB2 = false;
		_vendorHypersonic = false;
		_vendorMySQL = false;
		_vendorOracle = false;
		_vendorPostgreSQL = false;
		_vendorSQLServer = false;
		_vendorSybase = false;

		DB db = DBFactoryUtil.getDB();

		DBType dbType = db.getDBType();

		_db = db;

		if (dbType == DBType.DB2) {
			_vendorDB2 = true;
		}
		else if (dbType == DBType.HYPERSONIC) {
			_vendorHypersonic = true;
		}
		else if (dbType == DBType.MYSQL) {
			_vendorMySQL = true;
		}
		else if (dbType == DBType.ORACLE) {
			_vendorOracle = true;
		}
		else if (dbType == DBType.POSTGRESQL) {
			_vendorPostgreSQL = true;
		}
		else if (dbType == DBType.SQLSERVER) {
			_vendorSQLServer = true;
		}
		else if (dbType == DBType.SYBASE) {
			_vendorSybase = true;
		}
	}

	private String _removeLower(String sql) {
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

	private String _replaceBitwiseCheck(String sql) {
		Matcher matcher = _bitwiseCheckPattern.matcher(sql);

		if (_vendorMySQL || _vendorPostgreSQL || _vendorSQLServer ||
			_vendorSybase) {

			return matcher.replaceAll("($1 & $2)");
		}
		else {
			return sql;
		}
	}

	private String _replaceBoolean(String newSQL) {
		return StringUtil.replace(
			newSQL, new String[] {"[$FALSE$]", "[$TRUE$]"},
			new String[] {_db.getTemplateFalse(), _db.getTemplateTrue()});
	}

	private String _replaceCastClobText(String sql) {
		Matcher matcher = _castClobTextPattern.matcher(sql);

		if (_vendorOracle) {
			return matcher.replaceAll("DBMS_LOB.SUBSTR($1, 4000, 1)");
		}

		return _replaceCastText(matcher);
	}

	private String _replaceCastLong(String sql) {
		Matcher matcher = _castLongPattern.matcher(sql);

		if (_vendorHypersonic) {
			return matcher.replaceAll("CONVERT($1, SQL_BIGINT)");
		}
		else if (_vendorSybase) {
			return matcher.replaceAll("CONVERT(BIGINT, $1)");
		}
		else {
			return matcher.replaceAll("$1");
		}
	}

	private String _replaceCastText(Matcher matcher) {
		if (_vendorDB2) {
			return matcher.replaceAll("CAST($1 AS VARCHAR(254))");
		}
		else if (_vendorHypersonic) {
			return matcher.replaceAll("CONVERT($1, SQL_VARCHAR)");
		}
		else if (_vendorOracle) {
			return matcher.replaceAll("CAST($1 AS VARCHAR(4000))");
		}
		else if (_vendorPostgreSQL) {
			return matcher.replaceAll("CAST($1 AS TEXT)");
		}
		else if (_vendorSQLServer) {
			return matcher.replaceAll("CAST($1 AS NVARCHAR(MAX))");
		}
		else if (_vendorSybase) {
			return matcher.replaceAll("CAST($1 AS NVARCHAR(5461))");
		}
		else {
			return matcher.replaceAll("$1");
		}
	}

	private String _replaceCastText(String sql) {
		return _replaceCastText(_castTextPattern.matcher(sql));
	}

	private String _replaceCrossJoin(String sql) {
		if (_vendorSybase) {
			return StringUtil.replace(sql, "CROSS JOIN", StringPool.COMMA);
		}

		return sql;
	}

	private String _replaceEscape(String sql) {
		return StringUtil.replace(sql, "LIKE ?", "LIKE ? ESCAPE '\\'");
	}

	private String _replaceIntegerDivision(String sql) {
		Matcher matcher = _integerDivisionPattern.matcher(sql);

		if (_vendorMySQL) {
			return matcher.replaceAll("$1 DIV $2");
		}
		else if (_vendorOracle) {
			return matcher.replaceAll("TRUNC($1 / $2)");
		}
		else {
			return matcher.replaceAll("$1 / $2");
		}
	}

	private String _replaceLike(String sql) {
		Matcher matcher = _likePattern.matcher(sql);

		return matcher.replaceAll(
			"LIKE COALESCE(CAST(? AS VARCHAR(32672)),'')");
	}

	private String _replaceMod(String sql) {
		Matcher matcher = _modPattern.matcher(sql);

		return matcher.replaceAll("$1 % $2");
	}

	private String _replaceNegativeComparison(String sql) {
		Matcher matcher = _negativeComparisonPattern.matcher(sql);

		return matcher.replaceAll("$1 ($2)");
	}

	private String _replaceNotEqualsBlankStringComparison(String sql) {
		return StringUtil.replace(sql, " != ''", " IS NOT NULL");
	}

	private String _replaceReplace(String newSQL) {
		return newSQL.replaceAll("(?i)replace\\(", "str_replace(");
	}

	private String _transform(String sql) {
		if (sql == null) {
			return sql;
		}

		String newSQL = sql;

		newSQL = _replaceBitwiseCheck(newSQL);
		newSQL = _replaceBoolean(newSQL);
		newSQL = _replaceCastClobText(newSQL);
		newSQL = _replaceCastLong(newSQL);
		newSQL = _replaceCastText(newSQL);
		newSQL = _replaceCrossJoin(newSQL);
		newSQL = _replaceIntegerDivision(newSQL);

		if (_vendorDB2) {
			newSQL = _replaceLike(newSQL);
		}
		else if (_vendorMySQL) {
			DB db = DBFactoryUtil.getDB();

			if (!db.isSupportsStringCaseSensitiveQuery()) {
				newSQL = _removeLower(newSQL);
			}
		}
		else if (_vendorOracle) {
			newSQL = _replaceEscape(newSQL);
			newSQL = _replaceNotEqualsBlankStringComparison(newSQL);
		}
		else if (_vendorPostgreSQL) {
			newSQL = _replaceNegativeComparison(newSQL);
		}
		else if (_vendorSQLServer) {
			newSQL = _replaceMod(newSQL);
		}
		else if (_vendorSybase) {
			newSQL = _replaceMod(newSQL);
			newSQL = _replaceReplace(newSQL);
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Original SQL " + sql);
			_log.debug("Modified SQL " + newSQL);
		}

		return newSQL;
	}

	private String _transformFromHqlToJpql(String sql) {
		String newSQL = _transformedSqls.get(sql);

		if (newSQL != null) {
			return newSQL;
		}

		newSQL = _transform(sql);

		newSQL = _transformPositionalParams(newSQL);

		newSQL = StringUtil.replace(newSQL, _HQL_NOT_EQUALS, _JPQL_NOT_EQUALS);
		newSQL = StringUtil.replace(
			newSQL, _HQL_COMPOSITE_ID_MARKER, _JPQL_DOT_SEPARTOR);

		_transformedSqls.put(sql, newSQL);

		return newSQL;
	}

	private String _transformFromJpqlToHql(String sql) {
		String newSQL = _transformedSqls.get(sql);

		if (newSQL != null) {
			return newSQL;
		}

		newSQL = _transform(sql);

		Matcher matcher = _jpqlCountPattern.matcher(newSQL);

		if (matcher.find()) {
			String countExpression = matcher.group(1);
			String entityAlias = matcher.group(3);

			if (entityAlias.equals(countExpression)) {
				newSQL = matcher.replaceFirst(_HQL_COUNT_SQL);
			}
		}

		_transformedSqls.put(sql, newSQL);

		return newSQL;
	}

	private String _transformPositionalParams(String queryString) {
		if (queryString.indexOf(CharPool.QUESTION) == -1) {
			return queryString;
		}

		StringBundler sb = new StringBundler();

		int i = 1;
		int from = 0;
		int to = 0;

		while ((to = queryString.indexOf(CharPool.QUESTION, from)) != -1) {
			sb.append(queryString.substring(from, to));
			sb.append(StringPool.QUESTION);
			sb.append(i++);

			from = to + 1;
		}

		sb.append(queryString.substring(from));

		return sb.toString();
	}

	private static final String _HQL_COMPOSITE_ID_MARKER = "\\.id\\.";

	private static final String _HQL_COUNT_SQL = "SELECT COUNT(*) FROM $2 $3";

	private static final String _HQL_NOT_EQUALS = "!=";

	private static final String _JPQL_DOT_SEPARTOR = ".";

	private static final String _JPQL_NOT_EQUALS = "<>";

	private static final String _LOWER_CLOSE = StringPool.CLOSE_PARENTHESIS;

	private static final String _LOWER_OPEN = "lower(";

	private static final Log _log = LogFactoryUtil.getLog(SQLTransformer.class);

	private static final SQLTransformer _instance = new SQLTransformer();

	private static final Pattern _bitwiseCheckPattern = Pattern.compile(
		"BITAND\\((.+?),(.+?)\\)");
	private static final Pattern _castClobTextPattern = Pattern.compile(
		"CAST_CLOB_TEXT\\((.+?)\\)", Pattern.CASE_INSENSITIVE);
	private static final Pattern _castLongPattern = Pattern.compile(
		"CAST_LONG\\((.+?)\\)", Pattern.CASE_INSENSITIVE);
	private static final Pattern _castTextPattern = Pattern.compile(
		"CAST_TEXT\\((.+?)\\)", Pattern.CASE_INSENSITIVE);
	private static final Pattern _integerDivisionPattern = Pattern.compile(
		"INTEGER_DIV\\((.+?),(.+?)\\)", Pattern.CASE_INSENSITIVE);
	private static final Pattern _jpqlCountPattern = Pattern.compile(
		"SELECT COUNT\\((\\S+)\\) FROM (\\S+) (\\S+)");
	private static final Pattern _likePattern = Pattern.compile(
		"LIKE \\?", Pattern.CASE_INSENSITIVE);
	private static final Pattern _modPattern = Pattern.compile(
		"MOD\\((.+?),(.+?)\\)", Pattern.CASE_INSENSITIVE);
	private static final Pattern _negativeComparisonPattern = Pattern.compile(
		"(!?=)( -([0-9]+)?)", Pattern.CASE_INSENSITIVE);

	private DB _db;
	private Map<String, String> _transformedSqls;
	private boolean _vendorDB2;
	private boolean _vendorHypersonic;
	private boolean _vendorMySQL;
	private boolean _vendorOracle;
	private boolean _vendorPostgreSQL;
	private boolean _vendorSQLServer;
	private boolean _vendorSybase;

}