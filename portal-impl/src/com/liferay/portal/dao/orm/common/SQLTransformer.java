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

import com.liferay.portal.dao.sql.transformer.JPQLToHQLTransformerLogic;
import com.liferay.portal.dao.sql.transformer.SQLTransformerFunctionFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

/**
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 * @author Manuel de la Peña
 */
public class SQLTransformer {

	public static void reloadSQLTransformer() {
		_sqlTransformerFunction = SQLTransformerFunctionFactory.create();

		_transformedSqls.clear();
	}

	public static String transform(String sql) {
		return _sqlTransformerFunction.apply(sql);
	}

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 *             #transformFromJPQLToHQL(String)}
	 */
	@Deprecated
	public static String transformFromJpqlToHql(String sql) {
		return transformFromJPQLToHQL(sql);
	}

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by {@link #transform(String)}
	 */
	@Deprecated
	public static String transformFromJPQLToHQL(String sql) {
		String newSQL = _transformedSqls.get(sql);

		if (newSQL != null) {
			return newSQL;
		}

		newSQL = _sqlTransformerFunction.apply(sql);

		newSQL = JPQLToHQLTransformerLogic.replaceCount(newSQL);

		_transformedSqls.put(sql, newSQL);

		return newSQL;
	}

	private static volatile Function<String, String> _sqlTransformerFunction =
		SQLTransformerFunctionFactory.create();
	private static final Map<String, String> _transformedSqls =
		new ConcurrentHashMap<>();

}