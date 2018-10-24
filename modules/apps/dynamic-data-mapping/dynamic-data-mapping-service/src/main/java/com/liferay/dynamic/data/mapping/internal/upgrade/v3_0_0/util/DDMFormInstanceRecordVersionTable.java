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
public class DDMFormInstanceRecordVersionTable {

	public static final String TABLE_NAME = "DDMFormInstanceRecordVersion";

	public static final Object[][] TABLE_COLUMNS = {
		{"formInstanceRecordVersionId", Types.BIGINT},
		{"groupId", Types.BIGINT},
		{"companyId", Types.BIGINT},
		{"userId", Types.BIGINT},
		{"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP},
		{"formInstanceId", Types.BIGINT},
		{"formInstanceVersion", Types.VARCHAR},
		{"formInstanceRecordId", Types.BIGINT},
		{"version", Types.VARCHAR},
		{"status", Types.INTEGER},
		{"statusByUserId", Types.BIGINT},
		{"statusByUserName", Types.VARCHAR},
		{"statusDate", Types.TIMESTAMP},
		{"storageId", Types.BIGINT}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP = new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("formInstanceRecordVersionId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("formInstanceId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("formInstanceVersion", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("formInstanceRecordId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("version", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("status", Types.INTEGER);

TABLE_COLUMNS_MAP.put("statusByUserId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("statusByUserName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("statusDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("storageId", Types.BIGINT);

}
	public static final String TABLE_SQL_CREATE = "create table DDMFormInstanceRecordVersion (formInstanceRecordVersionId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,formInstanceId LONG,formInstanceVersion VARCHAR(75) null,formInstanceRecordId LONG,version VARCHAR(75) null,status INTEGER,statusByUserId LONG,statusByUserName VARCHAR(75) null,statusDate DATE null,storageId LONG)";

	public static final String TABLE_SQL_DROP = "drop table DDMFormInstanceRecordVersion";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_EAAF6D80 on DDMFormInstanceRecordVersion (formInstanceId, formInstanceVersion[$COLUMN_LENGTH:75$])",
		"create index IX_B5A3FAC6 on DDMFormInstanceRecordVersion (formInstanceRecordId, status)",
		"create unique index IX_26623628 on DDMFormInstanceRecordVersion (formInstanceRecordId, version[$COLUMN_LENGTH:75$])",
		"create index IX_57CA016C on DDMFormInstanceRecordVersion (userId, formInstanceId, formInstanceVersion[$COLUMN_LENGTH:75$], status)"
	};

}