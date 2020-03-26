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

package com.liferay.asset.list.internal.upgrade.v1_3_0.util;

import java.sql.Types;

import java.util.HashMap;
import java.util.Map;

/**
 * @author	  Brian Wing Shun Chan
 * @generated
 */
public class AssetListEntryTable {

	public static final String TABLE_NAME = "AssetListEntry";

	public static final Object[][] TABLE_COLUMNS = {
		{"mvccVersion", Types.BIGINT}, {"ctCollectionId", Types.BIGINT},
		{"uuid_", Types.VARCHAR}, {"assetListEntryId", Types.BIGINT},
		{"groupId", Types.BIGINT}, {"companyId", Types.BIGINT},
		{"userId", Types.BIGINT}, {"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP}, {"modifiedDate", Types.TIMESTAMP},
		{"assetListEntryKey", Types.VARCHAR}, {"title", Types.VARCHAR},
		{"type_", Types.INTEGER}, {"assetEntryType", Types.VARCHAR},
		{"lastPublishDate", Types.TIMESTAMP}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("mvccVersion", Types.BIGINT);

TABLE_COLUMNS_MAP.put("ctCollectionId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("uuid_", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("assetListEntryId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("assetListEntryKey", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("title", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("type_", Types.INTEGER);

TABLE_COLUMNS_MAP.put("assetEntryType", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("lastPublishDate", Types.TIMESTAMP);

}
	public static final String TABLE_SQL_CREATE =
"create table AssetListEntry (mvccVersion LONG default 0 not null,ctCollectionId LONG default 0 not null,uuid_ VARCHAR(75) null,assetListEntryId LONG not null,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,assetListEntryKey VARCHAR(75) null,title VARCHAR(75) null,type_ INTEGER,assetEntryType VARCHAR(255) null,lastPublishDate DATE null,primary key (assetListEntryId, ctCollectionId))";

	public static final String TABLE_SQL_DROP = "drop table AssetListEntry";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create unique index IX_366FAE09 on AssetListEntry (groupId, assetListEntryKey[$COLUMN_LENGTH:75$], ctCollectionId)",
		"create index IX_E5ED21FE on AssetListEntry (groupId, ctCollectionId)",
		"create unique index IX_5B95A9C6 on AssetListEntry (groupId, title[$COLUMN_LENGTH:75$], ctCollectionId)",
		"create index IX_6A225693 on AssetListEntry (groupId, type_, ctCollectionId)",
		"create index IX_AF5D7A1C on AssetListEntry (uuid_[$COLUMN_LENGTH:75$], companyId, ctCollectionId)",
		"create index IX_D48EC888 on AssetListEntry (uuid_[$COLUMN_LENGTH:75$], ctCollectionId)",
		"create unique index IX_B08E941E on AssetListEntry (uuid_[$COLUMN_LENGTH:75$], groupId, ctCollectionId)"
	};

}