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
public class DDMFormInstanceRecordTable {

	public static final String TABLE_NAME = "DDMFormInstanceRecord";

	public static final Object[][] TABLE_COLUMNS = {
		{"uuid_", Types.VARCHAR},
		{"formInstanceRecordId", Types.BIGINT},
		{"groupId", Types.BIGINT},
		{"companyId", Types.BIGINT},
		{"userId", Types.BIGINT},
		{"userName", Types.VARCHAR},
		{"versionUserId", Types.BIGINT},
		{"versionUserName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP},
		{"modifiedDate", Types.TIMESTAMP},
		{"formInstanceId", Types.BIGINT},
		{"formInstanceVersion", Types.VARCHAR},
		{"storageId", Types.BIGINT},
		{"version", Types.VARCHAR},
		{"lastPublishDate", Types.TIMESTAMP}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP = new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("uuid_", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("formInstanceRecordId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("versionUserId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("versionUserName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("formInstanceId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("formInstanceVersion", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("storageId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("version", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("lastPublishDate", Types.TIMESTAMP);

}
	public static final String TABLE_SQL_CREATE = "create table DDMFormInstanceRecord (uuid_ VARCHAR(75) null,formInstanceRecordId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,versionUserId LONG,versionUserName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,formInstanceId LONG,formInstanceVersion VARCHAR(75) null,storageId LONG,version VARCHAR(75) null,lastPublishDate DATE null)";

	public static final String TABLE_SQL_DROP = "drop table DDMFormInstanceRecord";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_5BC982B on DDMFormInstanceRecord (companyId)",
		"create index IX_242301EA on DDMFormInstanceRecord (formInstanceId, formInstanceVersion[$COLUMN_LENGTH:75$])",
		"create index IX_3C8DBDFF on DDMFormInstanceRecord (formInstanceId, userId)",
		"create index IX_E1971FF on DDMFormInstanceRecord (userId, formInstanceId)",
		"create index IX_CF8CF491 on DDMFormInstanceRecord (uuid_[$COLUMN_LENGTH:75$], companyId)",
		"create unique index IX_AA3B6B53 on DDMFormInstanceRecord (uuid_[$COLUMN_LENGTH:75$], groupId)"
	};

}