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

package com.liferay.fragment.internal.upgrade.v2_6_0.util;

import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

/**
 * @author	  Brian Wing Shun Chan
 * @generated
 */
public class FragmentEntryVersionTable {

	public static final String TABLE_NAME = "FragmentEntryVersion";

	public static final Object[][] TABLE_COLUMNS = {
		{"fragmentEntryVersionId", Types.BIGINT}, {"version", Types.INTEGER},
		{"uuid_", Types.VARCHAR}, {"fragmentEntryId", Types.BIGINT},
		{"groupId", Types.BIGINT}, {"companyId", Types.BIGINT},
		{"userId", Types.BIGINT}, {"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP}, {"modifiedDate", Types.TIMESTAMP},
		{"fragmentCollectionId", Types.BIGINT},
		{"fragmentEntryKey", Types.VARCHAR}, {"name", Types.VARCHAR},
		{"css", Types.CLOB}, {"html", Types.CLOB}, {"js", Types.CLOB},
		{"cacheable", Types.BOOLEAN}, {"configuration", Types.CLOB},
		{"previewFileEntryId", Types.BIGINT}, {"readOnly", Types.BOOLEAN},
		{"type_", Types.INTEGER}, {"lastPublishDate", Types.TIMESTAMP},
		{"status", Types.INTEGER}, {"statusByUserId", Types.BIGINT},
		{"statusByUserName", Types.VARCHAR}, {"statusDate", Types.TIMESTAMP}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("fragmentEntryVersionId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("version", Types.INTEGER);

TABLE_COLUMNS_MAP.put("uuid_", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("fragmentEntryId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("fragmentCollectionId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("fragmentEntryKey", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("name", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("css", Types.CLOB);

TABLE_COLUMNS_MAP.put("html", Types.CLOB);

TABLE_COLUMNS_MAP.put("js", Types.CLOB);

TABLE_COLUMNS_MAP.put("cacheable", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("configuration", Types.CLOB);

TABLE_COLUMNS_MAP.put("previewFileEntryId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("readOnly", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("type_", Types.INTEGER);

TABLE_COLUMNS_MAP.put("lastPublishDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("status", Types.INTEGER);

TABLE_COLUMNS_MAP.put("statusByUserId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("statusByUserName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("statusDate", Types.TIMESTAMP);

}
	public static final String TABLE_SQL_CREATE =
"create table FragmentEntryVersion (fragmentEntryVersionId LONG not null primary key,version INTEGER,uuid_ VARCHAR(75) null,fragmentEntryId LONG,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,fragmentCollectionId LONG,fragmentEntryKey VARCHAR(75) null,name VARCHAR(75) null,css TEXT null,html TEXT null,js TEXT null,cacheable BOOLEAN,configuration TEXT null,previewFileEntryId LONG,readOnly BOOLEAN,type_ INTEGER,lastPublishDate DATE null,status INTEGER,statusByUserId LONG,statusByUserName VARCHAR(75) null,statusDate DATE null)";

	public static final String TABLE_SQL_DROP =
"drop table FragmentEntryVersion";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_7A6F05CF on FragmentEntryVersion (fragmentCollectionId, version)",
		"create unique index IX_81688FD7 on FragmentEntryVersion (fragmentEntryId, version)",
		"create index IX_A4350E58 on FragmentEntryVersion (groupId, fragmentCollectionId, name[$COLUMN_LENGTH:75$], status, version)",
		"create index IX_3B0C07E on FragmentEntryVersion (groupId, fragmentCollectionId, name[$COLUMN_LENGTH:75$], version)",
		"create index IX_56F26BF7 on FragmentEntryVersion (groupId, fragmentCollectionId, status, version)",
		"create index IX_97E910F8 on FragmentEntryVersion (groupId, fragmentCollectionId, type_, status, version)",
		"create index IX_AE61631E on FragmentEntryVersion (groupId, fragmentCollectionId, type_, version)",
		"create index IX_A9192D1D on FragmentEntryVersion (groupId, fragmentCollectionId, version)",
		"create unique index IX_C3E45C69 on FragmentEntryVersion (groupId, fragmentEntryKey[$COLUMN_LENGTH:75$], version)",
		"create index IX_8648727A on FragmentEntryVersion (groupId, version)",
		"create index IX_17D96C38 on FragmentEntryVersion (uuid_[$COLUMN_LENGTH:75$], companyId, version)",
		"create unique index IX_EFAE3F6 on FragmentEntryVersion (uuid_[$COLUMN_LENGTH:75$], groupId, version)",
		"create index IX_FB468FB0 on FragmentEntryVersion (uuid_[$COLUMN_LENGTH:75$], version)"
	};

}