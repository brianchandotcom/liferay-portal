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

package com.liferay.asset.list.internal.upgrade.v1_5_0.util;

import java.sql.Types;

import java.util.HashMap;
import java.util.Map;

/**
 * @author	  Brian Wing Shun Chan
 * @generated
 */
public class AssetListEntrySegmentsEntryRelTable {

	public static final String TABLE_NAME = "AssetListEntrySegmentsEntryRel";

	public static final Object[][] TABLE_COLUMNS = {
		{"mvccVersion", Types.BIGINT}, {"ctCollectionId", Types.BIGINT},
		{"uuid_", Types.VARCHAR}, {"alEntrySegmentsEntryRelId", Types.BIGINT},
		{"groupId", Types.BIGINT}, {"companyId", Types.BIGINT},
		{"userId", Types.BIGINT}, {"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP}, {"modifiedDate", Types.TIMESTAMP},
		{"assetListEntryId", Types.BIGINT}, {"priority", Types.INTEGER},
		{"segmentsEntryId", Types.BIGINT}, {"typeSettings", Types.CLOB},
		{"lastPublishDate", Types.TIMESTAMP}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("mvccVersion", Types.BIGINT);

TABLE_COLUMNS_MAP.put("ctCollectionId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("uuid_", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("alEntrySegmentsEntryRelId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("assetListEntryId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("priority", Types.INTEGER);

TABLE_COLUMNS_MAP.put("segmentsEntryId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("typeSettings", Types.CLOB);

TABLE_COLUMNS_MAP.put("lastPublishDate", Types.TIMESTAMP);

}
	public static final String TABLE_SQL_CREATE =
"create table AssetListEntrySegmentsEntryRel (mvccVersion LONG default 0 not null,ctCollectionId LONG default 0 not null,uuid_ VARCHAR(75) null,alEntrySegmentsEntryRelId LONG not null,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,assetListEntryId LONG,priority INTEGER,segmentsEntryId LONG,typeSettings TEXT null,lastPublishDate DATE null,primary key (alEntrySegmentsEntryRelId, ctCollectionId))";

	public static final String TABLE_SQL_DROP =
"drop table AssetListEntrySegmentsEntryRel";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_8BB55022 on AssetListEntrySegmentsEntryRel (assetListEntryId, ctCollectionId)",
		"create unique index IX_56302677 on AssetListEntrySegmentsEntryRel (assetListEntryId, segmentsEntryId, ctCollectionId)",
		"create index IX_865F28AA on AssetListEntrySegmentsEntryRel (segmentsEntryId, ctCollectionId)",
		"create index IX_68CD4543 on AssetListEntrySegmentsEntryRel (uuid_[$COLUMN_LENGTH:75$], companyId, ctCollectionId)",
		"create index IX_AB2E6FC1 on AssetListEntrySegmentsEntryRel (uuid_[$COLUMN_LENGTH:75$], ctCollectionId)",
		"create unique index IX_29F4FD05 on AssetListEntrySegmentsEntryRel (uuid_[$COLUMN_LENGTH:75$], groupId, ctCollectionId)"
	};

}