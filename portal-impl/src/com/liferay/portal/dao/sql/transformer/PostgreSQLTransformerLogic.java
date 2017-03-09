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

import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Manuel de la Peña
 */
public class PostgreSQLTransformerLogic extends BaseSQLTransformerLogic {

	public PostgreSQLTransformerLogic(DB db) {
		super(db);

		_functions = new Function[] {
			getBitwiseCheckFunction(), getBooleanFunction(),
			getCastClobTextFunction(), getCastLongFunction(),
			getCastTextFunction(), _getInStrFunction(),
			getIntegerDivisionFunction(), _getNullDateFunction(),
			_getNegativeComparisonFunction()
		};
	}

	@Override
	public Function<String, String>[] getFunctions() {
		return _functions;
	}

	@Override
	protected String replaceCastText(Matcher matcher) {
		return matcher.replaceAll("CAST($1 AS TEXT)");
	}

	private Function<String, String> _getInStrFunction() {
		Pattern instrPattern = getInstrPattern();

		return (String sql) -> {
			Matcher matcher = instrPattern.matcher(sql);

			return matcher.replaceAll("POSITION($2 in $1)");
		};
	}

	private Function<String, String> _getNegativeComparisonFunction() {
		return (String sql) -> {
			Matcher matcher = _negativeComparisonPattern.matcher(sql);

			return matcher.replaceAll("$1 ($2)");
		};
	}

	private Function<String, String> _getNullDateFunction() {
		return (String sql) -> StringUtil.replace(
			sql, "[$NULL_DATE$]", "CAST(NULL AS TIMESTAMP)");
	}

	private static final Pattern _negativeComparisonPattern = Pattern.compile(
		"(!?=)( -([0-9]+)?)", Pattern.CASE_INSENSITIVE);

	private final Function<String, String>[] _functions;

}