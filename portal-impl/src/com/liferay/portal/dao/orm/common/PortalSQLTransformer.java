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

import com.liferay.portal.dao.orm.common.transformation.function.provider.SQLTransformationFunctionProvider;
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
public class PortalSQLTransformer implements Transformer {

	private static DB _db;

	public static Function<String, String> noTransformationFunction =
		(String sql) -> sql;

	public static Function<String, String>
		bitwiseCheckDefaultTransformationFunction = noTransformationFunction;
	public static final Pattern bitwiseCheckPattern = Pattern.compile(
		"BITAND\\((.+?),(.+?)\\)");

	public static Function<String, String> bitwiseCheckTransformationFunction =
		(String sql) -> {
			Matcher matcher = bitwiseCheckPattern.matcher(sql);

			return matcher.replaceAll("($1 & $2)");
		};

	public static Function<String, String> booleanTransformationFunction =
		(String sql) -> StringUtil.replace(
			sql, new String[] {"[$FALSE$]", "[$TRUE$]"},
			new String[] {_db.getTemplateFalse(), _db.getTemplateTrue()});
	public static final Pattern castClobTextPattern = Pattern.compile(
		"CAST_CLOB_TEXT\\((.+?)\\)", Pattern.CASE_INSENSITIVE);

	public static Function<String, String> castClobTextTransformationFunction =
		(String sql) -> {
			Matcher matcher = castClobTextPattern.matcher(sql);

			return _replaceCastText(matcher);
		};

	public static final Pattern castLongPattern = Pattern.compile(
		"CAST_LONG\\((.+?)\\)", Pattern.CASE_INSENSITIVE);

	public static Function<String, String> castLongTransformationFunction =
		(String sql) -> {
			Matcher matcher = castLongPattern.matcher(sql);

			return matcher.replaceAll("$1");
		};

	public static final Pattern castTextPattern = Pattern.compile(
		"CAST_TEXT\\((.+?)\\)", Pattern.CASE_INSENSITIVE);
	public static Function<String, String> castTextTransformationFunction =
		(String sql) -> _replaceCastText(castTextPattern.matcher(sql));
	public static Function<String, String>
		crossJoinDefaultTransformationFunction = noTransformationFunction;
	public static Function<String, String> inStrDefaultTransformationFunction =
		noTransformationFunction;
	public static final Pattern instrPattern = Pattern.compile(
		"INSTR\\((.+?),(.+?)\\)", Pattern.CASE_INSENSITIVE);
	public static final Pattern integerDivisionPattern = Pattern.compile(
		"INTEGER_DIV\\((.+?),(.+?)\\)", Pattern.CASE_INSENSITIVE);

	public static Function<String, String>
		integerDivisionTransformationFunction = (String sql) -> {
			Matcher matcher = integerDivisionPattern.matcher(sql);

			return matcher.replaceAll("$1 / $2");
		};

	public static final Pattern modPattern = Pattern.compile(
		"MOD\\((.+?),(.+?)\\)", Pattern.CASE_INSENSITIVE);
	public static Function<String, String> nullDateTransformationFunction =
		(String sql) -> StringUtil.replace(sql, "[$NULL_DATE$]", "NULL");
	public static Function<String, String> substrDefaultTransformationFunction =
		noTransformationFunction;
	public static final Pattern substrPattern = Pattern.compile(
		"SUBSTR\\((.+?),(.+?),(.+?)\\)", Pattern.CASE_INSENSITIVE);

	public static Transformer buildSQLTransformer(
		DB db,
		SQLTransformationFunctionProvider sqlTransformationFunctionProvider) {

		Function<String, String>[] transformationFunctions =
			sqlTransformationFunctionProvider.getTransformationFunctions();

		return new Builder().bind(db).register(transformationFunctions).build();
	}

	@Override
	public String transform(String sql) {
		if (sql == null) {
			return sql;
		}

		String newSQL = sql;

		for (Function<String, String> transformationFunction :
				_transformationFunctions) {

			newSQL = transformationFunction.apply(newSQL);
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Original SQL " + sql);
			_log.debug("Modified SQL " + newSQL);
		}

		return newSQL;
	}

	private final List<Function<String, String>> _transformationFunctions =
		new ArrayList<>();

	private static class Builder
		implements Build, DBConfigurator, TransformationFunctions {

		@Override
		public TransformationFunctions bind(DB db) {
			_transformer._setDb(db);

			return this;
		}

		@Override
		public Transformer build() {
			return _transformer;
		}

		@Override
		public Build register(
			Function<String, String>... transformationFunctions) {

			_transformer._register(transformationFunctions);

			return this;
		}

		private final PortalSQLTransformer _transformer =
			new PortalSQLTransformer();

	}

	private interface Build {

		public Transformer build();

	}

	private static String _replaceCastText(Matcher matcher) {
		return matcher.replaceAll("$1");
	}

	private void _register(Function<String, String>... transformations) {
		Collections.addAll(_transformationFunctions, transformations);
	}

	private void _setDb(DB db) {
		_db = db;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PortalSQLTransformer.class);

	private interface DBConfigurator {

		public TransformationFunctions bind(DB db);

	}

	private interface TransformationFunctions {

		public Build register(
			Function<String, String>... transformationFunctions);

	}

}