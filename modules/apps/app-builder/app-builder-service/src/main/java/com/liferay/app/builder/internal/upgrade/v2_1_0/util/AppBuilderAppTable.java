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

package com.liferay.app.builder.internal.upgrade.v2_1_0.util;

import java.sql.Types;

import java.util.HashMap;
import java.util.Map;

/**
 * @author	  Brian Wing Shun Chan
 * @generated
 */
public class AppBuilderAppTable {

	public static final String TABLE_NAME = "AppBuilderApp";

	public static final Object[][] TABLE_COLUMNS = {
		{"uuid_", Types.VARCHAR}, {"appBuilderAppId", Types.BIGINT},
		{"groupId", Types.BIGINT}, {"companyId", Types.BIGINT},
		{"userId", Types.BIGINT}, {"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP}, {"modifiedDate", Types.TIMESTAMP},
		{"active_", Types.BOOLEAN}, {"ddmStructureId", Types.BIGINT},
		{"ddmStructureLayoutId", Types.BIGINT},
		{"deDataListViewId", Types.BIGINT}, {"name", Types.VARCHAR},
		{"scope", Types.VARCHAR}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("uuid_", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("appBuilderAppId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("active_", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("ddmStructureId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("ddmStructureLayoutId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("deDataListViewId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("name", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("scope", Types.VARCHAR);

}
	public static final String TABLE_SQL_CREATE =
"create table AppBuilderApp (uuid_ VARCHAR(75) null,appBuilderAppId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,active_ BOOLEAN,ddmStructureId LONG,ddmStructureLayoutId LONG,deDataListViewId LONG,name STRING null,scope VARCHAR(75) null)";

	public static final String TABLE_SQL_DROP = "drop table AppBuilderApp";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_F1A2CD76 on AppBuilderApp (companyId, active_, scope[$COLUMN_LENGTH:75$])",
		"create index IX_EE2A6D09 on AppBuilderApp (companyId, scope[$COLUMN_LENGTH:75$])",
		"create index IX_4F325E62 on AppBuilderApp (ddmStructureId)",
		"create index IX_319C0B20 on AppBuilderApp (groupId, companyId, ddmStructureId)",
		"create index IX_AB656D87 on AppBuilderApp (groupId, scope[$COLUMN_LENGTH:75$])",
		"create index IX_EC1E021 on AppBuilderApp (uuid_[$COLUMN_LENGTH:75$], companyId)",
		"create unique index IX_65D5FAE3 on AppBuilderApp (uuid_[$COLUMN_LENGTH:75$], groupId)"
	};

}