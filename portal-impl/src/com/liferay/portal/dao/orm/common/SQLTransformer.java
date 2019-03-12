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

import com.liferay.portal.dao.sql.transformer.HQLToJPQLTransformerLogic;
import com.liferay.portal.dao.sql.transformer.JPQLToHQLTransformerLogic;
import com.liferay.portal.dao.sql.transformer.SQLTransformerFactory;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;

import java.util.Arrays;
import java.util.List;
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
		_sqlTransformer = SQLTransformerFactory.getSQLTransformer(
			DBManagerUtil.getDB());

		_transformedSqls.clear();
	}

	public static String transform(String sql) {
		return _sqlTransformer.transform(sql);
	}

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 *             #transformFromHQLToJQPL(String)}
	 */
	@Deprecated
	public static String transformFromHqlToJpql(String sql) {
		return transformFromHQLToJQPL(sql);
	}

	public static String transformFromHQLToJQPL(String sql) {
		String newSQL = _transformedSqls.get(sql);

		if (newSQL != null) {
			return newSQL;
		}

		newSQL = _sqlTransformer.transform(sql);

		List<Function<String, String>> functions = Arrays.asList(
			HQLToJPQLTransformerLogic::replacePositionalParameter,
			HQLToJPQLTransformerLogic::replaceNotEquals,
			HQLToJPQLTransformerLogic::replaceCompositeIdMarker);

		for (Function<String, String> function : functions) {
			newSQL = function.apply(newSQL);
		}

		_transformedSqls.put(sql, newSQL);

		return newSQL;
	}

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 *             #transformFromJPQLToHQL(String)}
	 */
	@Deprecated
	public static String transformFromJpqlToHql(String sql) {
		return transformFromJPQLToHQL(sql);
	}

	public static String transformFromJPQLToHQL(String sql) {
		String newSQL = _transformedSqls.get(sql);

		if (newSQL != null) {
			return newSQL;
		}

		newSQL = _sqlTransformer.transform(sql);

		newSQL = JPQLToHQLTransformerLogic.replaceCount(newSQL);

		_transformedSqls.put(sql, newSQL);

		return newSQL;
	}

	private static volatile
		com.liferay.portal.dao.sql.transformer.SQLTransformer _sqlTransformer =
			SQLTransformerFactory.getSQLTransformer(DBManagerUtil.getDB());
	private static final Map<String, String> _transformedSqls =
		new ConcurrentHashMap<>();

}