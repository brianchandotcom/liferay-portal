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

package com.liferay.fragment.internal.upgrade.v1_0_1.util;

import java.sql.Types;

import java.util.HashMap;
import java.util.Map;

/**
 * @author	  Brian Wing Shun Chan
 * @generated
 */
public class FragmentEntryTable {

	public static final String TABLE_NAME = "FragmentEntry";

	public static final Object[][] TABLE_COLUMNS = {
		{"uuid_", Types.VARCHAR}, {"fragmentEntryId", Types.BIGINT},
		{"groupId", Types.BIGINT}, {"companyId", Types.BIGINT},
		{"userId", Types.BIGINT}, {"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP}, {"modifiedDate", Types.TIMESTAMP},
		{"fragmentCollectionId", Types.BIGINT},
		{"fragmentEntryKey", Types.VARCHAR}, {"name", Types.VARCHAR},
		{"css", Types.CLOB}, {"html", Types.CLOB}, {"js", Types.CLOB},
		{"previewFileEntryId", Types.BIGINT},
		{"lastPublishDate", Types.TIMESTAMP}, {"status", Types.INTEGER},
		{"statusByUserId", Types.BIGINT}, {"statusByUserName", Types.VARCHAR},
		{"statusDate", Types.TIMESTAMP}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
new HashMap<String, Integer>();

static {
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

TABLE_COLUMNS_MAP.put("previewFileEntryId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("lastPublishDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("status", Types.INTEGER);

TABLE_COLUMNS_MAP.put("statusByUserId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("statusByUserName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("statusDate", Types.TIMESTAMP);

}
	public static final String TABLE_SQL_CREATE =
"create table FragmentEntry (uuid_ VARCHAR(75) null,fragmentEntryId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,fragmentCollectionId LONG,fragmentEntryKey VARCHAR(75) null,name VARCHAR(75) null,css TEXT null,html TEXT null,js TEXT null,previewFileEntryId LONG,lastPublishDate DATE null,status INTEGER,statusByUserId LONG,statusByUserName VARCHAR(75) null,statusDate DATE null)";

	public static final String TABLE_SQL_DROP = "drop table FragmentEntry";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_DDB6278B on FragmentEntry (fragmentCollectionId, status)",
		"create unique index IX_62913C70 on FragmentEntry (groupId, fragmentCollectionId, fragmentEntryKey[$COLUMN_LENGTH:75$])",
		"create index IX_9EC6FEE4 on FragmentEntry (groupId, fragmentCollectionId, name[$COLUMN_LENGTH:75$], status)",
		"create index IX_BD18F965 on FragmentEntry (groupId, fragmentCollectionId, status)",
		"create unique index IX_7F3F0EB3 on FragmentEntry (groupId, fragmentEntryKey[$COLUMN_LENGTH:75$])",
		"create index IX_C65BF31C on FragmentEntry (uuid_[$COLUMN_LENGTH:75$], companyId)",
		"create unique index IX_553E909E on FragmentEntry (uuid_[$COLUMN_LENGTH:75$], groupId)"
	};

}