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

package com.liferay.portal.kernel.dao.db;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Mariano Álvaro Sáiz
 */
public class DBTypeToSQLMap {

	public DBTypeToSQLMap(String defaultSQL) {
		_defaultSQL = defaultSQL;
	}

	public void add(DBType dbType, String sql) {
		_sqls.put(dbType, sql);
	}

	public String get(DBType dbType) {
		String sql = _sqls.get(dbType);

		if (sql != null) {
			return sql;
		}

		return _defaultSQL;
	}

	private final String _defaultSQL;
	private final Map<DBType, String> _sqls = new HashMap<>();

}