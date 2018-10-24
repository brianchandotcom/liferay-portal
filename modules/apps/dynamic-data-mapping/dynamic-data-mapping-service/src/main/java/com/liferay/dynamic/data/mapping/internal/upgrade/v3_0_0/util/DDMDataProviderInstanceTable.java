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

package com.liferay.dynamic.data.mapping.internal.upgrade.v3_0_0.util;

import java.sql.Types;

import java.util.HashMap;
import java.util.Map;

/**
 * @author	  Brian Wing Shun Chan
 * @generated
 */
public class DDMDataProviderInstanceTable {

	public static final String TABLE_NAME = "DDMDataProviderInstance";

	public static final Object[][] TABLE_COLUMNS = {
		{"uuid_", Types.VARCHAR},
		{"dataProviderInstanceId", Types.BIGINT},
		{"groupId", Types.BIGINT},
		{"companyId", Types.BIGINT},
		{"userId", Types.BIGINT},
		{"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP},
		{"modifiedDate", Types.TIMESTAMP},
		{"name", Types.VARCHAR},
		{"description", Types.CLOB},
		{"definition", Types.CLOB},
		{"type_", Types.VARCHAR}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP = new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("uuid_", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("dataProviderInstanceId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("name", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("description", Types.CLOB);

TABLE_COLUMNS_MAP.put("definition", Types.CLOB);

TABLE_COLUMNS_MAP.put("type_", Types.VARCHAR);

}
	public static final String TABLE_SQL_CREATE = "create table DDMDataProviderInstance (uuid_ VARCHAR(75) null,dataProviderInstanceId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,name STRING null,description TEXT null,definition TEXT null,type_ VARCHAR(75) null)";

	public static final String TABLE_SQL_DROP = "drop table DDMDataProviderInstance";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_DB54A6E5 on DDMDataProviderInstance (companyId)",
		"create index IX_1333A2A7 on DDMDataProviderInstance (groupId)",
		"create index IX_C903C097 on DDMDataProviderInstance (uuid_[$COLUMN_LENGTH:75$], companyId)",
		"create unique index IX_B4E180D9 on DDMDataProviderInstance (uuid_[$COLUMN_LENGTH:75$], groupId)"
	};

}