/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.v7_2_x.util;

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
		{"uuid_", Types.VARCHAR}, {"externalReferenceCode", Types.VARCHAR},
		{"categoryId", Types.BIGINT}, {"groupId", Types.BIGINT},
		{"companyId", Types.BIGINT}, {"userId", Types.BIGINT},
		{"userName", Types.VARCHAR}, {"createDate", Types.TIMESTAMP},
		{"modifiedDate", Types.TIMESTAMP}, {"parentCategoryId", Types.BIGINT},
		{"leftCategoryId", Types.BIGINT}, {"rightCategoryId", Types.BIGINT},
		{"name", Types.VARCHAR}, {"title", Types.VARCHAR},
		{"description", Types.VARCHAR}, {"vocabularyId", Types.BIGINT},
		{"lastPublishDate", Types.TIMESTAMP}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
new HashMap<String, Integer>();

static {
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

TABLE_COLUMNS_MAP.put("leftCategoryId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("rightCategoryId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("name", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("title", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("description", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("vocabularyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("lastPublishDate", Types.TIMESTAMP);

}
	public static final String TABLE_SQL_CREATE =
"create table AssetCategory (uuid_ VARCHAR(75) null,externalReferenceCode VARCHAR(75) null,categoryId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,parentCategoryId LONG,leftCategoryId LONG,rightCategoryId LONG,name VARCHAR(75) null,title STRING null,description STRING null,vocabularyId LONG,lastPublishDate DATE null)";

	public static final String TABLE_SQL_DROP = "drop table AssetCategory";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_85E3BB49 on AssetCategory (companyId, externalReferenceCode[$COLUMN_LENGTH:75$])",
		"create index IX_C7F39FCA on AssetCategory (groupId, name[$COLUMN_LENGTH:75$], vocabularyId)",
		"create index IX_852EA801 on AssetCategory (groupId, parentCategoryId, name[$COLUMN_LENGTH:75$], vocabularyId)",
		"create index IX_87603842 on AssetCategory (groupId, parentCategoryId, vocabularyId)",
		"create index IX_2008FACB on AssetCategory (groupId, vocabularyId)",
		"create index IX_D61ABE08 on AssetCategory (name[$COLUMN_LENGTH:75$], vocabularyId)",
		"create unique index IX_BE4DF2BF on AssetCategory (parentCategoryId, name[$COLUMN_LENGTH:75$], vocabularyId)",
		"create index IX_B185E980 on AssetCategory (parentCategoryId, vocabularyId)",
		"create index IX_BBAF6928 on AssetCategory (uuid_[$COLUMN_LENGTH:75$], companyId)",
		"create unique index IX_E8D019AA on AssetCategory (uuid_[$COLUMN_LENGTH:75$], groupId)",
		"create index IX_287B1F89 on AssetCategory (vocabularyId)"
	};

}