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

package com.liferay.portal.upgrade.v7_3_x.util;

import java.sql.Types;

import java.util.HashMap;
import java.util.Map;

/**
 * @author	  Brian Wing Shun Chan
 * @generated
 */
public class AssetCategoryTable {

	public static final String TABLE_NAME = "AssetCategory";

	public static final Object[][] TABLE_COLUMNS = {
		{"mvccVersion", Types.BIGINT}, {"ctCollectionId", Types.BIGINT},
		{"uuid_", Types.VARCHAR}, {"externalReferenceCode", Types.VARCHAR},
		{"categoryId", Types.BIGINT}, {"groupId", Types.BIGINT},
		{"companyId", Types.BIGINT}, {"userId", Types.BIGINT},
		{"userName", Types.VARCHAR}, {"createDate", Types.TIMESTAMP},
		{"modifiedDate", Types.TIMESTAMP}, {"parentCategoryId", Types.BIGINT},
		{"treePath", Types.VARCHAR}, {"name", Types.VARCHAR},
		{"title", Types.VARCHAR}, {"description", Types.VARCHAR},
		{"vocabularyId", Types.BIGINT}, {"lastPublishDate", Types.TIMESTAMP}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("mvccVersion", Types.BIGINT);

TABLE_COLUMNS_MAP.put("ctCollectionId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("uuid_", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("externalReferenceCode", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("categoryId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("parentCategoryId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("treePath", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("name", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("title", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("description", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("vocabularyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("lastPublishDate", Types.TIMESTAMP);

}
	public static final String TABLE_SQL_CREATE =
"create table AssetCategory (mvccVersion LONG default 0 not null,ctCollectionId LONG default 0 not null,uuid_ VARCHAR(75) null,externalReferenceCode VARCHAR(75) null,categoryId LONG not null,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,parentCategoryId LONG,treePath STRING null,name VARCHAR(255) null,title STRING null,description STRING null,vocabularyId LONG,lastPublishDate DATE null,primary key (categoryId, ctCollectionId))";

	public static final String TABLE_SQL_DROP = "drop table AssetCategory";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_AE8DFA7 on AssetCategory (companyId, externalReferenceCode[$COLUMN_LENGTH:75$], ctCollectionId)",
		"create index IX_1757FA92 on AssetCategory (ctCollectionId)",
		"create index IX_62DC0D54 on AssetCategory (groupId, ctCollectionId)",
		"create index IX_3E49A228 on AssetCategory (groupId, name[$COLUMN_LENGTH:255$], vocabularyId, ctCollectionId)",
		"create index IX_5159C90B on AssetCategory (groupId, parentCategoryId, ctCollectionId)",
		"create index IX_852EA801 on AssetCategory (groupId, parentCategoryId, name[$COLUMN_LENGTH:255$], vocabularyId)",
		"create index IX_51264AA0 on AssetCategory (groupId, parentCategoryId, vocabularyId, ctCollectionId)",
		"create index IX_7EF2DB29 on AssetCategory (groupId, vocabularyId, ctCollectionId)",
		"create index IX_8F988466 on AssetCategory (name[$COLUMN_LENGTH:255$], vocabularyId, ctCollectionId)",
		"create index IX_88D822C9 on AssetCategory (parentCategoryId, ctCollectionId)",
		"create index IX_83C2D848 on AssetCategory (parentCategoryId, name[$COLUMN_LENGTH:255$], ctCollectionId)",
		"create unique index IX_DC516B1D on AssetCategory (parentCategoryId, name[$COLUMN_LENGTH:255$], vocabularyId, ctCollectionId)",
		"create index IX_8CEDBFDE on AssetCategory (parentCategoryId, vocabularyId, ctCollectionId)",
		"create index IX_59B2EF86 on AssetCategory (uuid_[$COLUMN_LENGTH:75$], companyId, ctCollectionId)",
		"create index IX_A9CC915E on AssetCategory (uuid_[$COLUMN_LENGTH:75$], ctCollectionId)",
		"create unique index IX_5B65C08 on AssetCategory (uuid_[$COLUMN_LENGTH:75$], groupId, ctCollectionId)",
		"create index IX_24AFC3E7 on AssetCategory (vocabularyId, ctCollectionId)"
	};

}