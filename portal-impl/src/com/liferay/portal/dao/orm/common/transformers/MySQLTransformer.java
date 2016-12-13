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

import com.liferay.portal.kernel.util.StringPool;

import java.util.function.Function;
import java.util.regex.Matcher;

/**
 * @author Manuel de la Peña
 */
public class MySQLTransformer extends BaseSQLTransformer {

	public MySQLTransformer(boolean supportsStringCaseSensitiveQuery) {
		transformations.add(bitwiseCheckTransformation);
		transformations.add(castClobTextTransformation);
		transformations.add(castLongTransformation);
		transformations.add(crossJoinDefaultTransformation);

		if (!supportsStringCaseSensitiveQuery) {
			transformations.add(_lowerTransformation);
		}
	}

	@Override
	protected String replaceIntegerDivision(String sql) {
		Matcher matcher = integerDivisionPattern.matcher(sql);

		return matcher.replaceAll("$1 DIV $2");
	}

	private static final String _LOWER_CLOSE = StringPool.CLOSE_PARENTHESIS;

	private static final String _LOWER_OPEN = "lower(";

	private Function<String, String> _lowerTransformation = (String sql) -> {
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