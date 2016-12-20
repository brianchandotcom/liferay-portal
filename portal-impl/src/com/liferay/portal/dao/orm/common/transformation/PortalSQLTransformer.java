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

package com.liferay.portal.dao.orm.common.transformation;

import com.liferay.portal.dao.orm.common.transformation.providers.SQLTransformationProvider;
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

	public static Function<String, String> bitwiseCheckDefaultTransformation =
		(String sql) -> sql;
	public static final Pattern bitwiseCheckPattern = Pattern.compile(
		"BITAND\\((.+?),(.+?)\\)");

	public static Function<String, String> bitwiseCheckTransformation =
		(String sql) -> {
			Matcher matcher = bitwiseCheckPattern.matcher(sql);

			return matcher.replaceAll("($1 & $2)");
		};

	public static Function<String, String> booleanTransformation =
		(String sql) -> StringUtil.replace(
			sql, new String[] {"[$FALSE$]", "[$TRUE$]"},
			new String[] {_db.getTemplateFalse(), _db.getTemplateTrue()});
	public static final Pattern castClobTextPattern = Pattern.compile(
		"CAST_CLOB_TEXT\\((.+?)\\)", Pattern.CASE_INSENSITIVE);

	public static Function<String, String> castClobTextTransformation =
		(String sql) -> {
			Matcher matcher = castClobTextPattern.matcher(sql);

			return _replaceCastText(matcher);
		};

	public static final Pattern castLongPattern = Pattern.compile(
		"CAST_LONG\\((.+?)\\)", Pattern.CASE_INSENSITIVE);

	public static Function<String, String> castLongTransformation =
		(String sql) -> {
			Matcher matcher = castLongPattern.matcher(sql);

			return matcher.replaceAll("$1");
		};

	public static final Pattern castTextPattern = Pattern.compile(
		"CAST_TEXT\\((.+?)\\)", Pattern.CASE_INSENSITIVE);
	public static Function<String, String> castTextTransformation =
		(String sql) -> _replaceCastText(castTextPattern.matcher(sql));
	public static Function<String, String> crossJoinDefaultTransformation =
		(String sql) -> sql;
	public static Function<String, String> inStrDefaultTransformation =
		(String sql) -> sql;
	public static final Pattern instrPattern = Pattern.compile(
		"INSTR\\((.+?),(.+?)\\)", Pattern.CASE_INSENSITIVE);
	public static final Pattern integerDivisionPattern = Pattern.compile(
		"INTEGER_DIV\\((.+?),(.+?)\\)", Pattern.CASE_INSENSITIVE);

	public static Function<String, String> integerDivisionTransformation =
		(String sql) -> {
			Matcher matcher = integerDivisionPattern.matcher(sql);

			return matcher.replaceAll("$1 / $2");
		};

	public static final Pattern modPattern = Pattern.compile(
		"MOD\\((.+?),(.+?)\\)", Pattern.CASE_INSENSITIVE);
	public static Function<String, String> nullDateTransformation =
		(String sql) -> StringUtil.replace(sql, "[$NULL_DATE$]", "NULL");
	public static Function<String, String> substrDefaultTransformation =
		(String sql) -> sql;
	public static final Pattern substrPattern = Pattern.compile(
		"SUBSTR\\((.+?),(.+?),(.+?)\\)", Pattern.CASE_INSENSITIVE);

	public static Transformer buildSQLTransformer(
		DB db, SQLTransformationProvider sqlTransformationProvider) {

		Function<String, String>[] transformations =
			sqlTransformationProvider.getTransformations();

		return new Builder().bind(db).register(transformations).build();
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

	public interface Build {

		public Transformer build();

	}

	public interface DBConfigurator {

		public Transformation bind(DB db);

	}

	public interface Transformation {

		public Build register(Function<String, String>... transformations);

	}

	private static String _replaceCastText(Matcher matcher) {
		return matcher.replaceAll("$1");
	}

	private void _register(Function<String, String>... transformations) {
		Collections.addAll(_transformations, transformations);
	}

	private void _setDb(DB db) {
		_db = db;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PortalSQLTransformer.class);

	private final List<Function<String, String>> _transformations =
		new ArrayList<>();

	private static class Builder
		implements Build, DBConfigurator, Transformation {

		@Override
		public Transformation bind(DB db) {
			_transformer._setDb(db);

			return this;
		}

		@Override
		public Transformer build() {
			return _transformer;
		}

		@Override
		public Build register(Function<String, String>... transformations) {
			_transformer._register(transformations);

			return this;
		}

		private final PortalSQLTransformer _transformer =
			new PortalSQLTransformer();

	}

}