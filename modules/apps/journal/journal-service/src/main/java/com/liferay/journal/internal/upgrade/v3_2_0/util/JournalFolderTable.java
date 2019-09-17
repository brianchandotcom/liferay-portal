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

package com.liferay.journal.internal.upgrade.v3_2_0.util;

import java.sql.Types;

import java.util.HashMap;
import java.util.Map;

/**
 * @author	  Preston Crary
 * @generated
 */
public class JournalFolderTable {

	public static final String TABLE_NAME = "JournalFolder";

	public static final Object[][] TABLE_COLUMNS = {
		{"mvccVersion", Types.BIGINT}, {"ctCollectionId", Types.BIGINT},
		{"uuid_", Types.VARCHAR}, {"folderId", Types.BIGINT},
		{"groupId", Types.BIGINT}, {"companyId", Types.BIGINT},
		{"userId", Types.BIGINT}, {"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP}, {"modifiedDate", Types.TIMESTAMP},
		{"parentFolderId", Types.BIGINT}, {"treePath", Types.VARCHAR},
		{"name", Types.VARCHAR}, {"description", Types.VARCHAR},
		{"restrictionType", Types.INTEGER},
		{"lastPublishDate", Types.TIMESTAMP}, {"status", Types.INTEGER},
		{"statusByUserId", Types.BIGINT}, {"statusByUserName", Types.VARCHAR},
		{"statusDate", Types.TIMESTAMP}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("mvccVersion", Types.BIGINT);

TABLE_COLUMNS_MAP.put("ctCollectionId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("uuid_", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("folderId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("parentFolderId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("treePath", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("name", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("description", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("restrictionType", Types.INTEGER);

TABLE_COLUMNS_MAP.put("lastPublishDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("status", Types.INTEGER);

TABLE_COLUMNS_MAP.put("statusByUserId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("statusByUserName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("statusDate", Types.TIMESTAMP);

}
	public static final String TABLE_SQL_CREATE =
"create table JournalFolder (mvccVersion LONG default 0 not null,ctCollectionId LONG default 0 not null,uuid_ VARCHAR(75) null,folderId LONG not null,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,parentFolderId LONG,treePath STRING null,name VARCHAR(100) null,description STRING null,restrictionType INTEGER,lastPublishDate DATE null,status INTEGER,statusByUserId LONG,statusByUserName VARCHAR(75) null,statusDate DATE null,primary key (folderId, ctCollectionId))";

	public static final String TABLE_SQL_DROP = "drop table JournalFolder";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_6ADEEEBB on JournalFolder (companyId, ctCollectionId)",
		"create index IX_E6C2F4A1 on JournalFolder (companyId, status, ctCollectionId)",
		"create index IX_3C880149 on JournalFolder (ctCollectionId)",
		"create index IX_6CD2A47D on JournalFolder (groupId, ctCollectionId)",
		"create index IX_39FA42FC on JournalFolder (groupId, name[$COLUMN_LENGTH:100$], ctCollectionId)",
		"create index IX_766B0E24 on JournalFolder (groupId, parentFolderId, ctCollectionId)",
		"create unique index IX_A2109363 on JournalFolder (groupId, parentFolderId, name[$COLUMN_LENGTH:100$], ctCollectionId)",
		"create index IX_BFD19B0A on JournalFolder (groupId, parentFolderId, status, ctCollectionId)",
		"create index IX_95F9567D on JournalFolder (uuid_[$COLUMN_LENGTH:75$], companyId, ctCollectionId)",
		"create index IX_D7A0DEC7 on JournalFolder (uuid_[$COLUMN_LENGTH:75$], ctCollectionId)",
		"create unique index IX_6F8814BF on JournalFolder (uuid_[$COLUMN_LENGTH:75$], groupId, ctCollectionId)"
	};

}