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
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Manuel de la Peña
 */
public class SybaseSQLTransformerLogic extends BaseSQLTransformerLogic {

	public SybaseSQLTransformerLogic(DB db) {
		super(db);

		_functions = new Function[] {
			getBitwiseCheckFunction(), getBooleanFunction(),
			getCastClobTextFunction(), getCastLongFunction(),
			getCastTextFunction(), _getCrossJoinFunction(), _getInStrFunction(),
			getIntegerDivisionFunction(), getNullDateFunction(),
			_getSubstrFunction(), _getModFunction(), _getReplaceFunction()
		};
	}

	@Override
	public Function<String, String>[] getFunctions() {
		return _functions;
	}

	@Override
	protected String replaceCastLong(Matcher matcher) {
		return matcher.replaceAll("CONVERT(BIGINT, $1)");
	}

	@Override
	protected String replaceCastText(Matcher matcher) {
		return matcher.replaceAll("CAST($1 AS NVARCHAR(5461))");
	}

	private Function<String, String> _getCrossJoinFunction() {
		return (String sql) -> StringUtil.replace(
			sql, "CROSS JOIN", StringPool.COMMA);
	}

	private Function<String, String> _getInStrFunction() {
		Pattern pattern = getInstrPattern();

		return (String sql) -> {
			Matcher matcher = pattern.matcher(sql);

			return matcher.replaceAll("CHARINDEX($2, $1)");
		};
	}

	private Function<String, String> _getModFunction() {
		Pattern pattern = getModPattern();

		return (String sql) -> {
			Matcher matcher = pattern.matcher(sql);

			return matcher.replaceAll("$1 % $2");
		};
	}

	private Function<String, String> _getReplaceFunction() {
		return (String sql) -> sql.replaceAll("(?i)replace\\(", "str_replace(");
	}

	private Function<String, String> _getSubstrFunction() {
		Pattern pattern = getSubstrPattern();

		return (String sql) -> {
			Matcher matcher = pattern.matcher(sql);

			return matcher.replaceAll("SUBSTRING($1, $2, $3)");
		};
	}

	private final Function<String, String>[] _functions;

}