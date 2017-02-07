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

package com.liferay.portal.dao.orm.common.transformation.providers;

import com.liferay.portal.dao.orm.common.transformation.PortalSQLTransformer;
import com.liferay.portal.kernel.util.StringPool;

import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Manuel de la Peña
 */
public class MySQLTransformationProvider implements SQLTransformationProvider {

	public MySQLTransformationProvider(
		boolean supportsStringCaseSensitiveQuery) {

		if (!supportsStringCaseSensitiveQuery) {
			_transformations = new Function[] {
				PortalSQLTransformer.bitwiseCheckTransformation,
				PortalSQLTransformer.booleanTransformation,
				PortalSQLTransformer.castClobTextTransformation,
				PortalSQLTransformer.castLongTransformation,
				PortalSQLTransformer.castTextTransformation,
				PortalSQLTransformer.crossJoinDefaultTransformation,
				PortalSQLTransformer.inStrDefaultTransformation,
				_integerDivisionTransformation,
				PortalSQLTransformer.nullDateTransformation,
				PortalSQLTransformer.substrDefaultTransformation,
				_lowerTransformation
			};
		}
		else {
			_transformations = new Function[] {
				PortalSQLTransformer.bitwiseCheckTransformation,
				PortalSQLTransformer.booleanTransformation,
				PortalSQLTransformer.castClobTextTransformation,
				PortalSQLTransformer.castLongTransformation,
				PortalSQLTransformer.castTextTransformation,
				PortalSQLTransformer.crossJoinDefaultTransformation,
				PortalSQLTransformer.inStrDefaultTransformation,
				_integerDivisionTransformation,
				PortalSQLTransformer.nullDateTransformation,
				PortalSQLTransformer.substrDefaultTransformation
			};
		}
	}

	@Override
	public Function<String, String>[] getTransformations() {
		return _transformations;
	}

	private static final String _LOWER_CLOSE = StringPool.CLOSE_PARENTHESIS;

	private static final String _LOWER_OPEN = "lower(";

	private final Function<String, String> _integerDivisionTransformation =
		(String sql) -> {
			Pattern integerDivisionPattern =
				PortalSQLTransformer.integerDivisionPattern;

			Matcher matcher = integerDivisionPattern.matcher(sql);

			return matcher.replaceAll("$1 DIV $2");
		};

	private final Function<String, String> _lowerTransformation =
		(String sql) -> {
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

	private final Function<String, String>[] _transformations;

}