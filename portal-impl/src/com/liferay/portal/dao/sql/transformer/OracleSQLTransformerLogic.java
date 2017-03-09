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
public class OracleSQLTransformerLogic extends BaseSQLTransformerLogic {

	public OracleSQLTransformerLogic(DB db) {
		super(db);

		_functions = new Function[] {
			getBooleanFunction(), _getCastClobTextFunction(),
			getCastLongFunction(), getCastTextFunction(),
			getIntegerDivisionFunction(), getNullDateFunction(),
			_getEscapeFunction(), _getNotEqualsBlankStringFunction()
		};
	}

	@Override
	public Function<String, String>[] getFunctions() {
		return _functions;
	}

	@Override
	protected String replaceCastText(Matcher matcher) {
		return matcher.replaceAll("CAST($1 AS VARCHAR(4000))");
	}

	@Override
	protected String replaceIntegerDivision(Matcher matcher) {
		return matcher.replaceAll("TRUNC($1 / $2)");
	}

	private Function<String, String> _getCastClobTextFunction() {
		Pattern pattern = getCastClobTextPattern();

		return (String sql) -> {
			Matcher matcher = pattern.matcher(sql);

			return matcher.replaceAll("DBMS_LOB.SUBSTR($1, 4000, 1)");
		};
	}

	private Function<String, String> _getEscapeFunction() {
		return (String sql) -> StringUtil.replace(
			sql, "LIKE ?", "LIKE ? ESCAPE '\\'");
	}

	private Function<String, String> _getNotEqualsBlankStringFunction() {
		return (String sql) -> StringUtil.replace(
			sql, " != ''", " IS NOT NULL");
	}

	private final Function<String, String>[] _functions;

}