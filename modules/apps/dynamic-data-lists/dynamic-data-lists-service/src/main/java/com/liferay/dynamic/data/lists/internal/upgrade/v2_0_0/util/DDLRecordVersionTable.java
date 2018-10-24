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
public class DDLRecordVersionTable {

	public static final String TABLE_NAME = "DDLRecordVersion";

	public static final Object[][] TABLE_COLUMNS = {
		{"recordVersionId", Types.BIGINT},
		{"groupId", Types.BIGINT},
		{"companyId", Types.BIGINT},
		{"userId", Types.BIGINT},
		{"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP},
		{"DDMStorageId", Types.BIGINT},
		{"recordSetId", Types.BIGINT},
		{"recordSetVersion", Types.VARCHAR},
		{"recordId", Types.BIGINT},
		{"version", Types.VARCHAR},
		{"displayIndex", Types.INTEGER},
		{"status", Types.INTEGER},
		{"statusByUserId", Types.BIGINT},
		{"statusByUserName", Types.VARCHAR},
		{"statusDate", Types.TIMESTAMP}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP = new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("recordVersionId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("DDMStorageId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("recordSetId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("recordSetVersion", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("recordId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("version", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("displayIndex", Types.INTEGER);

TABLE_COLUMNS_MAP.put("status", Types.INTEGER);

TABLE_COLUMNS_MAP.put("statusByUserId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("statusByUserName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("statusDate", Types.TIMESTAMP);

}
	public static final String TABLE_SQL_CREATE = "create table DDLRecordVersion (recordVersionId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,DDMStorageId LONG,recordSetId LONG,recordSetVersion VARCHAR(75) null,recordId LONG,version VARCHAR(75) null,displayIndex INTEGER,status INTEGER,statusByUserId LONG,statusByUserName VARCHAR(75) null,statusDate DATE null)";

	public static final String TABLE_SQL_DROP = "drop table DDLRecordVersion";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_762ADC7 on DDLRecordVersion (recordId, status)",
		"create unique index IX_C79E347 on DDLRecordVersion (recordId, version[$COLUMN_LENGTH:75$])",
		"create index IX_19AD75F6 on DDLRecordVersion (recordSetId, recordSetVersion[$COLUMN_LENGTH:75$])",
		"create index IX_28202A62 on DDLRecordVersion (userId, recordSetId, recordSetVersion[$COLUMN_LENGTH:75$], status)"
	};

}