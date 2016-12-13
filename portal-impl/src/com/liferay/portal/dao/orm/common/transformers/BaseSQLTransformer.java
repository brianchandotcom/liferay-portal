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

package com.liferay.portal.dao.orm.common.transformers;

import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Manuel de la Peña
 */
public abstract class BaseSQLTransformer implements Transformer {

	@Override
	public void setDB(DB db) {
		this.db = db;
	}

	@Override
	public String transform(String sql) {
		if (sql == null) {
			return sql;
		}

		String newSQL = sql;

		newSQL = replaceBitwiseCheck(newSQL);
		newSQL = _booleanTransformation.apply(newSQL);
		newSQL = replaceCastClobText(newSQL);
		newSQL = replaceCastLong(newSQL);
		newSQL = _castTextTransformation.apply(newSQL);
		newSQL = replaceCrossJoin(newSQL);
		newSQL = replaceInStr(newSQL);
		newSQL = replaceIntegerDivision(newSQL);
		newSQL = replaceNullDate(newSQL);
		newSQL = replaceSubstr(newSQL);

		newSQL = postTransform(db.isSupportsStringCaseSensitiveQuery(), newSQL);

		if (_log.isDebugEnabled()) {
			_log.debug("Original SQL " + sql);
			_log.debug("Modified SQL " + newSQL);
		}

		return newSQL;
	}

	protected String postTransform(
		boolean supportsStringCaseSensitiveQuery, String sql) {

		return sql;
	}

	protected String replaceBitwiseCheck(String sql) {
		Matcher matcher = _bitwiseCheckPattern.matcher(sql);

		return matcher.replaceAll("($1 & $2)");
	}

	protected String replaceCastClobText(String sql) {
		Matcher matcher = castClobTextPattern.matcher(sql);

		return replaceCastText(matcher);
	}

	protected String replaceCastLong(String sql) {
		Matcher matcher = castLongPattern.matcher(sql);

		return matcher.replaceAll("$1");
	}

	protected String replaceCastText(Matcher matcher) {
		return matcher.replaceAll("$1");
	}

	protected String replaceCrossJoin(String sql) {
		return sql;
	}

	protected String replaceInStr(String sql) {
		return sql;
	}

	protected String replaceIntegerDivision(String sql) {
		Matcher matcher = integerDivisionPattern.matcher(sql);

		return matcher.replaceAll("$1 / $2");
	}

	protected String replaceMod(String sql) {
		Matcher matcher = modPattern.matcher(sql);

		return matcher.replaceAll("$1 % $2");
	}

	protected String replaceNullDate(String sql) {
		return StringUtil.replace(sql, "[$NULL_DATE$]", "NULL");
	}

	protected String replaceReplace(String sql) {
		return sql.replaceAll("(?i)replace\\(", "str_replace(");
	}

	protected String replaceSubstr(String sql) {
		return sql;
	}

	protected static final Pattern castClobTextPattern = Pattern.compile(
		"CAST_CLOB_TEXT\\((.+?)\\)", Pattern.CASE_INSENSITIVE);
	protected static final Pattern castLongPattern = Pattern.compile(
		"CAST_LONG\\((.+?)\\)", Pattern.CASE_INSENSITIVE);
	protected static final Pattern instrPattern = Pattern.compile(
		"INSTR\\((.+?),(.+?)\\)", Pattern.CASE_INSENSITIVE);
	protected static final Pattern integerDivisionPattern = Pattern.compile(
		"INTEGER_DIV\\((.+?),(.+?)\\)", Pattern.CASE_INSENSITIVE);
	protected static final Pattern modPattern = Pattern.compile(
		"MOD\\((.+?),(.+?)\\)", Pattern.CASE_INSENSITIVE);
	protected static final Pattern substrPattern = Pattern.compile(
		"SUBSTR\\((.+?),(.+?),(.+?)\\)", Pattern.CASE_INSENSITIVE);

	protected DB db;

	private Function<String, String> _booleanTransformation =
		(String sql) -> StringUtil.replace(
			sql, new String[] {"[$FALSE$]", "[$TRUE$]"},
			new String[] {
				db.getTemplateFalse(), db.getTemplateTrue()
			}
	);

	private Function<String, String> _castTextTransformation = 
		(String sql) -> replaceCastText(_castTextPattern.matcher(sql));

	private static final Log _log = LogFactoryUtil.getLog(
		BaseSQLTransformer.class);

	private static final Pattern _bitwiseCheckPattern = Pattern.compile(
		"BITAND\\((.+?),(.+?)\\)");
	private static final Pattern _castTextPattern = Pattern.compile(
		"CAST_TEXT\\((.+?)\\)", Pattern.CASE_INSENSITIVE);

}