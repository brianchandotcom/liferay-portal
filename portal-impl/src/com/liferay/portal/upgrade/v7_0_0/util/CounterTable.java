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

package com.liferay.portal.upgrade.v7_0_0.util;

import java.sql.Types;

import java.util.HashMap;
import java.util.Map;

/**
 * @author	  Brian Wing Shun Chan
 * @generated
 */
public class CounterTable {

	public static final String TABLE_NAME = "Counter";

	public static final Object[][] TABLE_COLUMNS = {
		{"name", Types.VARCHAR},
		{"currentId", Types.BIGINT}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP = new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("name", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("currentId", Types.BIGINT);

}
	public static final String TABLE_SQL_CREATE = "create table Counter (name VARCHAR(75) not null primary key,currentId LONG)";

	public static final String TABLE_SQL_DROP = "drop table Counter";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
	};

}