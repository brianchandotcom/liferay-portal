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

package com.liferay.asset.list.internal.upgrade.v1_4_0.util;

import java.sql.Types;

import java.util.HashMap;
import java.util.Map;

/**
 * @author	  Víctor Galán
 * @generated
 */
public class AssetListEntryUsageTable {

	public static final String TABLE_NAME = "AssetListEntryUsage";

	public static final Object[][] TABLE_COLUMNS = {
		{"mvccVersion", Types.BIGINT}, {"ctCollectionId", Types.BIGINT},
		{"uuid_", Types.VARCHAR}, {"assetListEntryUsageId", Types.BIGINT},
		{"groupId", Types.BIGINT}, {"companyId", Types.BIGINT},
		{"userId", Types.BIGINT}, {"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP}, {"modifiedDate", Types.TIMESTAMP},
		{"assetListEntryId", Types.BIGINT}, {"classNameId", Types.BIGINT},
		{"classPK", Types.BIGINT}, {"containerKey", Types.VARCHAR},
		{"containerType", Types.BIGINT}, {"key_", Types.VARCHAR},
		{"plid", Types.BIGINT}, {"portletId", Types.VARCHAR},
		{"type_", Types.INTEGER}, {"lastPublishDate", Types.TIMESTAMP}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("mvccVersion", Types.BIGINT);

TABLE_COLUMNS_MAP.put("ctCollectionId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("uuid_", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("assetListEntryUsageId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("assetListEntryId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("classNameId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("classPK", Types.BIGINT);

TABLE_COLUMNS_MAP.put("containerKey", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("containerType", Types.BIGINT);

TABLE_COLUMNS_MAP.put("key_", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("plid", Types.BIGINT);

TABLE_COLUMNS_MAP.put("portletId", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("type_", Types.INTEGER);

TABLE_COLUMNS_MAP.put("lastPublishDate", Types.TIMESTAMP);

}
	public static final String TABLE_SQL_CREATE =
"create table AssetListEntryUsage (mvccVersion LONG default 0 not null,ctCollectionId LONG default 0 not null,uuid_ VARCHAR(75) null,assetListEntryUsageId LONG not null,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,assetListEntryId LONG,classNameId LONG,classPK LONG,containerKey VARCHAR(255) null,containerType LONG,key_ VARCHAR(255) null,plid LONG,portletId VARCHAR(200) null,type_ INTEGER,lastPublishDate DATE null,primary key (assetListEntryUsageId, ctCollectionId))";

	public static final String TABLE_SQL_DROP =
"drop table AssetListEntryUsage";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_76643456 on AssetListEntryUsage (assetListEntryId, classNameId, ctCollectionId)",
		"create index IX_6E1C4974 on AssetListEntryUsage (assetListEntryId, ctCollectionId)",
		"create unique index IX_78341F6B on AssetListEntryUsage (classNameId, classPK, portletId[$COLUMN_LENGTH:200$], ctCollectionId)",
		"create index IX_AF0F2C99 on AssetListEntryUsage (groupId, classNameId, key_[$COLUMN_LENGTH:255$], ctCollectionId)",
		"create index IX_27D73798 on AssetListEntryUsage (groupId, classNameId, key_[$COLUMN_LENGTH:255$], type_, ctCollectionId)",
		"create index IX_4B343E95 on AssetListEntryUsage (uuid_[$COLUMN_LENGTH:75$], companyId, ctCollectionId)",
		"create index IX_1F2615AF on AssetListEntryUsage (uuid_[$COLUMN_LENGTH:75$], ctCollectionId)",
		"create unique index IX_AE0E22D7 on AssetListEntryUsage (uuid_[$COLUMN_LENGTH:75$], groupId, ctCollectionId)"
	};

}