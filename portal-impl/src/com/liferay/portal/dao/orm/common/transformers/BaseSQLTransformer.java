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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Manuel de la Peña
 */
public abstract class BaseSQLTransformer implements Transformer {

	public BaseSQLTransformer(DB db) {
		this.db = db;
	}

	public void register(Function<String, String>... transformations) {
		Collections.addAll(_transformations, transformations);
	}

	@Override
	public String transform(String sql) {
		if (sql == null) {
			return sql;
		}

		String newSQL = sql;

		for (Function<String, String> transformation : _transformations) {
			newSQL = transformation.apply(newSQL);
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Original SQL " + sql);
			_log.debug("Modified SQL " + newSQL);
		}

		return newSQL;
	}

	protected String replaceCastText(Matcher matcher) {
		return matcher.replaceAll("$1");
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

	protected Function<String, String> bitwiseCheckDefaultTransformation =
		(String sql) -> sql;

	protected Function<String, String> bitwiseCheckTransformation =
		(String sql) -> {
			Matcher matcher = _bitwiseCheckPattern.matcher(sql);

			return matcher.replaceAll("($1 & $2)");
		};

	protected Function<String, String> booleanTransformation =
		(String sql) -> StringUtil.replace(
			sql, new String[] {"[$FALSE$]", "[$TRUE$]"},
			new String[] {db.getTemplateFalse(), db.getTemplateTrue()});

	protected Function<String, String> castClobTextTransformation =
		(String sql) -> {
			Matcher matcher = castClobTextPattern.matcher(sql);

			return replaceCastText(matcher);
		};

	protected Function<String, String> castLongTransformation =
		(String sql) -> {
			Matcher matcher = castLongPattern.matcher(sql);

			return matcher.replaceAll("$1");
		};

	protected Function<String, String> castTextTransformation =
		(String sql) -> replaceCastText(_castTextPattern.matcher(sql));
	protected Function<String, String> crossJoinDefaultTransformation =
		(String sql) -> sql;
	protected Function<String, String> inStrDefaultTransformation =
		(String sql) -> sql;

	protected Function<String, String> integerDivisionTransformation =
		(String sql) -> {
			Matcher matcher = integerDivisionPattern.matcher(sql);

			return matcher.replaceAll("$1 / $2");
		};

	protected Function<String, String> nullDateTransformation =
		(String sql) -> StringUtil.replace(sql, "[$NULL_DATE$]", "NULL");
	protected Function<String, String> substrDefaultTransformation =
		(String sql) -> sql;

	private static final Log _log = LogFactoryUtil.getLog(
		BaseSQLTransformer.class);

	private static final Pattern _bitwiseCheckPattern = Pattern.compile(
		"BITAND\\((.+?),(.+?)\\)");
	private static final Pattern _castTextPattern = Pattern.compile(
		"CAST_TEXT\\((.+?)\\)", Pattern.CASE_INSENSITIVE);

	private final List<Function<String, String>> _transformations =
		new ArrayList<>();

}