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

package com.liferay.style.book.internal.upgrade.v1_2_0.util;

import java.sql.Types;

import java.util.HashMap;
import java.util.Map;

/**
 * @author	  Brian Wing Shun Chan
 * @generated
 */
public class StyleBookEntryVersionTable {

	public static final String TABLE_NAME = "StyleBookEntryVersion";

	public static final Object[][] TABLE_COLUMNS = {
		{"styleBookEntryVersionId", Types.BIGINT}, {"version", Types.INTEGER},
		{"uuid_", Types.VARCHAR}, {"styleBookEntryId", Types.BIGINT},
		{"groupId", Types.BIGINT}, {"companyId", Types.BIGINT},
		{"userId", Types.BIGINT}, {"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP}, {"modifiedDate", Types.TIMESTAMP},
		{"defaultStyleBookEntry", Types.BOOLEAN},
		{"frontendTokensValues", Types.CLOB}, {"name", Types.VARCHAR},
		{"previewFileEntryId", Types.BIGINT},
		{"styleBookEntryKey", Types.VARCHAR}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("styleBookEntryVersionId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("version", Types.INTEGER);

TABLE_COLUMNS_MAP.put("uuid_", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("styleBookEntryId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("defaultStyleBookEntry", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("frontendTokensValues", Types.CLOB);

TABLE_COLUMNS_MAP.put("name", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("previewFileEntryId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("styleBookEntryKey", Types.VARCHAR);

}
	public static final String TABLE_SQL_CREATE =
"create table StyleBookEntryVersion (styleBookEntryVersionId LONG not null primary key,version INTEGER,uuid_ VARCHAR(75) null,styleBookEntryId LONG,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,defaultStyleBookEntry BOOLEAN,frontendTokensValues TEXT null,name VARCHAR(75) null,previewFileEntryId LONG,styleBookEntryKey VARCHAR(75) null)";

	public static final String TABLE_SQL_DROP =
"drop table StyleBookEntryVersion";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_3CA4E523 on StyleBookEntryVersion (groupId, defaultStyleBookEntry, version)",
		"create index IX_7B3A245 on StyleBookEntryVersion (groupId, name[$COLUMN_LENGTH:75$], version)",
		"create unique index IX_5F837D75 on StyleBookEntryVersion (groupId, styleBookEntryKey[$COLUMN_LENGTH:75$], version)",
		"create index IX_9A3EB024 on StyleBookEntryVersion (groupId, version)",
		"create unique index IX_21DCB95B on StyleBookEntryVersion (styleBookEntryId, version)",
		"create index IX_D15CEDCE on StyleBookEntryVersion (uuid_[$COLUMN_LENGTH:75$], companyId, version)",
		"create unique index IX_5D7DD30C on StyleBookEntryVersion (uuid_[$COLUMN_LENGTH:75$], groupId, version)",
		"create index IX_D0242FDA on StyleBookEntryVersion (uuid_[$COLUMN_LENGTH:75$], version)"
	};

}