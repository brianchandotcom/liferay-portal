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

package com.liferay.portal.upgrade.v7_0_5.util;

import java.sql.Types;

import java.util.HashMap;
import java.util.Map;

/**
 * @author	  Brian Wing Shun Chan
 * @generated
 */
public class LayoutPrototypeTable {

	public static final String TABLE_NAME = "LayoutPrototype";

	public static final Object[][] TABLE_COLUMNS = {
		{"mvccVersion", Types.BIGINT},
		{"uuid_", Types.VARCHAR},
		{"layoutPrototypeId", Types.BIGINT},
		{"companyId", Types.BIGINT},
		{"userId", Types.BIGINT},
		{"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP},
		{"modifiedDate", Types.TIMESTAMP},
		{"name", Types.CLOB},
		{"description", Types.CLOB},
		{"settings_", Types.VARCHAR},
		{"active_", Types.BOOLEAN}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP = new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("mvccVersion", Types.BIGINT);

TABLE_COLUMNS_MAP.put("uuid_", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("layoutPrototypeId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("name", Types.CLOB);

TABLE_COLUMNS_MAP.put("description", Types.CLOB);

TABLE_COLUMNS_MAP.put("settings_", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("active_", Types.BOOLEAN);

}
	public static final String TABLE_SQL_CREATE = "create table LayoutPrototype (mvccVersion LONG default 0 not null,uuid_ VARCHAR(75) null,layoutPrototypeId LONG not null primary key,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,name TEXT null,description TEXT null,settings_ STRING null,active_ BOOLEAN)";

	public static final String TABLE_SQL_DROP = "drop table LayoutPrototype";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_557A639F on LayoutPrototype (companyId, active_)",
		"create index IX_63ED2532 on LayoutPrototype (uuid_[$COLUMN_LENGTH:75$], companyId)"
	};

}