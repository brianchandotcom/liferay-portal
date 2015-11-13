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
public class ExpandoColumnTable {

	public static final String TABLE_NAME = "ExpandoColumn";

	public static final Object[][] TABLE_COLUMNS = {
		{"columnId", Types.BIGINT},
		{"companyId", Types.BIGINT},
		{"tableId", Types.BIGINT},
		{"name", Types.VARCHAR},
		{"type_", Types.INTEGER},
		{"defaultData", Types.CLOB},
		{"typeSettings", Types.CLOB}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP = new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("columnId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("tableId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("name", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("type_", Types.INTEGER);

TABLE_COLUMNS_MAP.put("defaultData", Types.CLOB);

TABLE_COLUMNS_MAP.put("typeSettings", Types.CLOB);

}
	public static final String TABLE_SQL_CREATE = "create table ExpandoColumn (columnId LONG not null primary key,companyId LONG,tableId LONG,name VARCHAR(75) null,type_ INTEGER,defaultData TEXT null,typeSettings TEXT null)";

	public static final String TABLE_SQL_DROP = "drop table ExpandoColumn";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create unique index IX_FEFC8DA7 on ExpandoColumn (tableId, name[$COLUMN_LENGTH:75$])"
	};

}