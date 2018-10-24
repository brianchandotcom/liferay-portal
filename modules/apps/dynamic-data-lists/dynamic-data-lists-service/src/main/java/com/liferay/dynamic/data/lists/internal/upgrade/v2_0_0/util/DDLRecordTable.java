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

package com.liferay.dynamic.data.lists.internal.upgrade.v2_0_0.util;

import java.sql.Types;

import java.util.HashMap;
import java.util.Map;

/**
 * @author	  Brian Wing Shun Chan
 * @generated
 */
public class DDLRecordTable {

	public static final String TABLE_NAME = "DDLRecord";

	public static final Object[][] TABLE_COLUMNS = {
		{"uuid_", Types.VARCHAR},
		{"recordId", Types.BIGINT},
		{"groupId", Types.BIGINT},
		{"companyId", Types.BIGINT},
		{"userId", Types.BIGINT},
		{"userName", Types.VARCHAR},
		{"versionUserId", Types.BIGINT},
		{"versionUserName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP},
		{"modifiedDate", Types.TIMESTAMP},
		{"DDMStorageId", Types.BIGINT},
		{"recordSetId", Types.BIGINT},
		{"recordSetVersion", Types.VARCHAR},
		{"version", Types.VARCHAR},
		{"displayIndex", Types.INTEGER},
		{"lastPublishDate", Types.TIMESTAMP}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP = new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("uuid_", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("recordId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("versionUserId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("versionUserName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("DDMStorageId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("recordSetId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("recordSetVersion", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("version", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("displayIndex", Types.INTEGER);

TABLE_COLUMNS_MAP.put("lastPublishDate", Types.TIMESTAMP);

}
	public static final String TABLE_SQL_CREATE = "create table DDLRecord (uuid_ VARCHAR(75) null,recordId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,versionUserId LONG,versionUserName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,DDMStorageId LONG,recordSetId LONG,recordSetVersion VARCHAR(75) null,version VARCHAR(75) null,displayIndex INTEGER,lastPublishDate DATE null)";

	public static final String TABLE_SQL_DROP = "drop table DDLRecord";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_6A6C1C85 on DDLRecord (companyId)",
		"create index IX_F12C61D4 on DDLRecord (recordSetId, recordSetVersion[$COLUMN_LENGTH:75$])",
		"create index IX_AAC564D3 on DDLRecord (recordSetId, userId)",
		"create index IX_384AB6F7 on DDLRecord (uuid_[$COLUMN_LENGTH:75$], companyId)",
		"create unique index IX_B4328F39 on DDLRecord (uuid_[$COLUMN_LENGTH:75$], groupId)"
	};

}