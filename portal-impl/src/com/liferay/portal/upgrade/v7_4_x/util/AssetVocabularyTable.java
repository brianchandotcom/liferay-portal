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

package com.liferay.portal.upgrade.v7_4_x.util;

import java.sql.Types;

import java.util.HashMap;
import java.util.Map;

/**
 * @author	  Brian Wing Shun Chan
 * @generated
 */
public class AssetVocabularyTable {

	public static final String TABLE_NAME = "AssetVocabulary";

	public static final Object[][] TABLE_COLUMNS = {
		{"mvccVersion", Types.BIGINT}, {"ctCollectionId", Types.BIGINT},
		{"uuid_", Types.VARCHAR}, {"externalReferenceCode", Types.VARCHAR},
		{"vocabularyId", Types.BIGINT}, {"groupId", Types.BIGINT},
		{"companyId", Types.BIGINT}, {"userId", Types.BIGINT},
		{"userName", Types.VARCHAR}, {"createDate", Types.TIMESTAMP},
		{"modifiedDate", Types.TIMESTAMP}, {"name", Types.VARCHAR},
		{"title", Types.VARCHAR}, {"description", Types.VARCHAR},
		{"settings_", Types.VARCHAR}, {"visibilityType", Types.INTEGER},
		{"lastPublishDate", Types.TIMESTAMP}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("mvccVersion", Types.BIGINT);

TABLE_COLUMNS_MAP.put("ctCollectionId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("uuid_", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("externalReferenceCode", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("vocabularyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("name", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("title", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("description", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("settings_", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("visibilityType", Types.INTEGER);

TABLE_COLUMNS_MAP.put("lastPublishDate", Types.TIMESTAMP);

}
	public static final String TABLE_SQL_CREATE =
"create table AssetVocabulary (mvccVersion LONG default 0 not null,ctCollectionId LONG default 0 not null,uuid_ VARCHAR(75) null,externalReferenceCode VARCHAR(75) null,vocabularyId LONG not null,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,name VARCHAR(75) null,title STRING null,description STRING null,settings_ STRING null,visibilityType INTEGER,lastPublishDate DATE null,primary key (vocabularyId, ctCollectionId))";

	public static final String TABLE_SQL_DROP = "drop table AssetVocabulary";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_F75DCEEA on AssetVocabulary (companyId, ctCollectionId)",
		"create index IX_6496D38F on AssetVocabulary (companyId, externalReferenceCode[$COLUMN_LENGTH:75$], ctCollectionId)",
		"create index IX_49B3687A on AssetVocabulary (ctCollectionId)",
		"create index IX_4E99C46C on AssetVocabulary (groupId, ctCollectionId)",
		"create index IX_9181CBCD on AssetVocabulary (groupId, externalReferenceCode[$COLUMN_LENGTH:75$], ctCollectionId)",
		"create unique index IX_AE9F73AB on AssetVocabulary (groupId, name[$COLUMN_LENGTH:75$], ctCollectionId)",
		"create index IX_2C944C4C on AssetVocabulary (groupId, visibilityType, ctCollectionId)",
		"create index IX_B955B36E on AssetVocabulary (uuid_[$COLUMN_LENGTH:75$], companyId, ctCollectionId)",
		"create index IX_2F3D2E76 on AssetVocabulary (uuid_[$COLUMN_LENGTH:75$], ctCollectionId)",
		"create unique index IX_8F88F9F0 on AssetVocabulary (uuid_[$COLUMN_LENGTH:75$], groupId, ctCollectionId)"
	};

}