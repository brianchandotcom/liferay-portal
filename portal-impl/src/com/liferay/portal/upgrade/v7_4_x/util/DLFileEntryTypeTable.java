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
public class DLFileEntryTypeTable {

	public static final String TABLE_NAME = "DLFileEntryType";

	public static final Object[][] TABLE_COLUMNS = {
		{"mvccVersion", Types.BIGINT}, {"ctCollectionId", Types.BIGINT},
		{"uuid_", Types.VARCHAR}, {"fileEntryTypeId", Types.BIGINT},
		{"groupId", Types.BIGINT}, {"companyId", Types.BIGINT},
		{"userId", Types.BIGINT}, {"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP}, {"modifiedDate", Types.TIMESTAMP},
		{"dataDefinitionId", Types.BIGINT}, {"fileEntryTypeKey", Types.VARCHAR},
		{"name", Types.VARCHAR}, {"description", Types.VARCHAR},
		{"scope", Types.INTEGER}, {"lastPublishDate", Types.TIMESTAMP}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("mvccVersion", Types.BIGINT);

TABLE_COLUMNS_MAP.put("ctCollectionId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("uuid_", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("fileEntryTypeId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("dataDefinitionId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("fileEntryTypeKey", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("name", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("description", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("scope", Types.INTEGER);

TABLE_COLUMNS_MAP.put("lastPublishDate", Types.TIMESTAMP);

}
	public static final String TABLE_SQL_CREATE =
"create table DLFileEntryType (mvccVersion LONG default 0 not null,ctCollectionId LONG default 0 not null,uuid_ VARCHAR(75) null,fileEntryTypeId LONG not null,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,dataDefinitionId LONG,fileEntryTypeKey VARCHAR(75) null,name STRING null,description STRING null,scope INTEGER,lastPublishDate DATE null,primary key (fileEntryTypeId, ctCollectionId))";

	public static final String TABLE_SQL_DROP = "drop table DLFileEntryType";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_C0561BFA on DLFileEntryType (groupId, ctCollectionId)",
		"create unique index IX_B6F21286 on DLFileEntryType (groupId, dataDefinitionId, ctCollectionId)",
		"create unique index IX_402227BD on DLFileEntryType (groupId, fileEntryTypeKey[$COLUMN_LENGTH:75$], ctCollectionId)",
		"create index IX_F147FBA0 on DLFileEntryType (uuid_[$COLUMN_LENGTH:75$], companyId, ctCollectionId)",
		"create index IX_17A61184 on DLFileEntryType (uuid_[$COLUMN_LENGTH:75$], ctCollectionId)",
		"create unique index IX_1773A6A2 on DLFileEntryType (uuid_[$COLUMN_LENGTH:75$], groupId, ctCollectionId)"
	};

}