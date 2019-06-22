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

package com.liferay.dynamic.data.lists.internal.upgrade.v1_1_1.util;

import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

/**
 * @author	  Brian Wing Shun Chan
 * @generated
 */
public class DDLRecordSetTable {

	public static final String TABLE_NAME = "DDLRecordSet";

	public static final Object[][] TABLE_COLUMNS = {
		{"uuid_", Types.VARCHAR},
		{"recordSetId", Types.BIGINT},
		{"groupId", Types.BIGINT},
		{"companyId", Types.BIGINT},
		{"userId", Types.BIGINT},
		{"userName", Types.VARCHAR},
		{"versionUserId", Types.BIGINT},
		{"versionUserName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP},
		{"modifiedDate", Types.TIMESTAMP},
		{"DDMStructureId", Types.BIGINT},
		{"recordSetKey", Types.VARCHAR},
		{"version", Types.VARCHAR},
		{"name", Types.VARCHAR},
		{"description", Types.VARCHAR},
		{"minDisplayRows", Types.INTEGER},
		{"scope", Types.INTEGER},
		{"settings_", Types.CLOB},
		{"lastPublishDate", Types.TIMESTAMP}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP = new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("uuid_", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("recordSetId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("versionUserId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("versionUserName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("DDMStructureId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("recordSetKey", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("version", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("name", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("description", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("minDisplayRows", Types.INTEGER);

TABLE_COLUMNS_MAP.put("scope", Types.INTEGER);

TABLE_COLUMNS_MAP.put("settings_", Types.CLOB);

TABLE_COLUMNS_MAP.put("lastPublishDate", Types.TIMESTAMP);

}
	public static final String TABLE_SQL_CREATE = "create table DDLRecordSet (uuid_ VARCHAR(75) null,recordSetId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,versionUserId LONG,versionUserName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,DDMStructureId LONG,recordSetKey VARCHAR(75) null,version VARCHAR(75) null,name STRING null,description STRING null,minDisplayRows INTEGER,scope INTEGER,settings_ TEXT null,lastPublishDate DATE null)";

	public static final String TABLE_SQL_DROP = "drop table DDLRecordSet";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create unique index IX_56DAB121 on DDLRecordSet (groupId, recordSetKey[$COLUMN_LENGTH:75$])",
		"create index IX_5938C39F on DDLRecordSet (uuid_[$COLUMN_LENGTH:75$], companyId)",
		"create unique index IX_270BA5E1 on DDLRecordSet (uuid_[$COLUMN_LENGTH:75$], groupId)"
	};

}