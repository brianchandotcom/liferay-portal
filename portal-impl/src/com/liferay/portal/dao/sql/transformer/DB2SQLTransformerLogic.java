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

import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Manuel de la Peña
 */
public class DB2SQLTransformerLogic extends BaseSQLTransformerLogic {

	public DB2SQLTransformerLogic(DB db) {
		super(db);

		_functions = new Function[] {
			getBooleanFunction(), getCastClobTextFunction(),
			getCastLongFunction(), getCastTextFunction(),
			getIntegerDivisionFunction(), getNullDateFunction(),
			_getLikeFunction()
		};
	}

	@Override
	public Function<String, String>[] getFunctions() {
		return _functions;
	}

	@Override
	protected String replaceCastText(Matcher matcher) {
		return matcher.replaceAll("CAST($1 AS VARCHAR(254))");
	}

	private Function<String, String> _getLikeFunction() {
		return (String sql) -> {
			Matcher matcher = _likePattern.matcher(sql);

			return matcher.replaceAll(
				"LIKE COALESCE(CAST(? AS VARCHAR(32672)),'')");
		};
	}

	private static final Pattern _likePattern = Pattern.compile(
		"LIKE \\?", Pattern.CASE_INSENSITIVE);

	private final Function<String, String>[] _functions;

}