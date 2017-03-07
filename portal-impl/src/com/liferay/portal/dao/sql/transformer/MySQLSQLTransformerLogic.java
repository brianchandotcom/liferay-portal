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

import java.util.function.Function;
import java.util.regex.Matcher;

/**
 * @author Manuel de la Peña
 */
public class MySQLSQLTransformerLogic extends BaseSQLTransformerLogic {

	public MySQLSQLTransformerLogic(DB db) {
		super(db);

		if (!db.isSupportsStringCaseSensitiveQuery()) {
			_functions = new Function[] {
				getBitwiseCheckFunction(), getBooleanFunction(),
				getCastClobTextFunction(), getCastLongFunction(),
				getCastTextFunction(), getIntegerDivisionFunction(),
				getNullDateFunction(), _getLowerFunction()
			};
		}
		else {
			_functions = new Function[] {
				getBitwiseCheckFunction(), getBooleanFunction(),
				getCastClobTextFunction(), getCastLongFunction(),
				getCastTextFunction(), getIntegerDivisionFunction(),
				getNullDateFunction()
			};
		}
	}

	@Override
	public Function<String, String>[] getFunctions() {
		return _functions;
	}

	@Override
	protected String replaceIntegerDivision(Matcher matcher) {
		return matcher.replaceAll("$1 DIV $2");
	}

	private Function<String, String> _getLowerFunction() {
		return (String sql) -> {
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
		};
	}

	private static final String _LOWER_CLOSE = StringPool.CLOSE_PARENTHESIS;

	private static final String _LOWER_OPEN = "lower(";

	private final Function<String, String>[] _functions;

}