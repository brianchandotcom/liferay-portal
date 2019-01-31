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

package com.liferay.sync.internal.upgrade.v2_0_0.util;

import java.sql.Types;

import java.util.HashMap;
import java.util.Map;

/**
 * @author	  Tina Tian
 * @generated
 */
public class SyncDLObjectTable {

	public static final String TABLE_NAME = "SyncDLObject";

	public static final Object[][] TABLE_COLUMNS = {
		{"syncDLObjectId", Types.BIGINT},
		{"companyId", Types.BIGINT},
		{"userId", Types.BIGINT},
		{"userName", Types.VARCHAR},
		{"createTime", Types.BIGINT},
		{"modifiedTime", Types.BIGINT},
		{"repositoryId", Types.BIGINT},
		{"parentFolderId", Types.BIGINT},
		{"treePath", Types.VARCHAR},
		{"name", Types.VARCHAR},
		{"extension", Types.VARCHAR},
		{"mimeType", Types.VARCHAR},
		{"description_", Types.VARCHAR},
		{"changeLog", Types.VARCHAR},
		{"extraSettings", Types.CLOB},
		{"version", Types.VARCHAR},
		{"versionId", Types.BIGINT},
		{"size_", Types.BIGINT},
		{"checksum", Types.VARCHAR},
		{"event", Types.VARCHAR},
		{"lanTokenKey", Types.VARCHAR},
		{"lastPermissionChangeDate", Types.TIMESTAMP},
		{"lockExpirationDate", Types.TIMESTAMP},
		{"lockUserId", Types.BIGINT},
		{"lockUserName", Types.VARCHAR},
		{"type_", Types.VARCHAR},
		{"typePK", Types.BIGINT},
		{"typeUuid", Types.VARCHAR}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP = new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("syncDLObjectId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("createTime", Types.BIGINT);

TABLE_COLUMNS_MAP.put("modifiedTime", Types.BIGINT);

TABLE_COLUMNS_MAP.put("repositoryId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("parentFolderId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("treePath", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("name", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("extension", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("mimeType", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("description_", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("changeLog", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("extraSettings", Types.CLOB);

TABLE_COLUMNS_MAP.put("version", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("versionId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("size_", Types.BIGINT);

TABLE_COLUMNS_MAP.put("checksum", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("event", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("lanTokenKey", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("lastPermissionChangeDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("lockExpirationDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("lockUserId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("lockUserName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("type_", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("typePK", Types.BIGINT);

TABLE_COLUMNS_MAP.put("typeUuid", Types.VARCHAR);

}
	public static final String TABLE_SQL_CREATE = "create table SyncDLObject (syncDLObjectId LONG not null primary key,companyId LONG,userId LONG,userName VARCHAR(75) null,createTime LONG,modifiedTime LONG,repositoryId LONG,parentFolderId LONG,treePath STRING null,name VARCHAR(255) null,extension VARCHAR(75) null,mimeType VARCHAR(75) null,description_ STRING null,changeLog VARCHAR(75) null,extraSettings TEXT null,version VARCHAR(75) null,versionId LONG,size_ LONG,checksum VARCHAR(75) null,event VARCHAR(75) null,lanTokenKey VARCHAR(75) null,lastPermissionChangeDate DATE null,lockExpirationDate DATE null,lockUserId LONG,lockUserName VARCHAR(75) null,type_ VARCHAR(75) null,typePK LONG,typeUuid VARCHAR(75) null)";

	public static final String TABLE_SQL_DROP = "drop table SyncDLObject";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_8D4FDC9F on SyncDLObject (modifiedTime, repositoryId, event[$COLUMN_LENGTH:75$])",
		"create index IX_A3ACE372 on SyncDLObject (modifiedTime, repositoryId, parentFolderId)",
		"create index IX_38C38A09 on SyncDLObject (repositoryId, event[$COLUMN_LENGTH:75$])",
		"create index IX_3BE7BB8D on SyncDLObject (repositoryId, parentFolderId, type_[$COLUMN_LENGTH:75$])",
		"create index IX_57F62914 on SyncDLObject (repositoryId, type_[$COLUMN_LENGTH:75$])",
		"create index IX_EE41CBEB on SyncDLObject (treePath[$COLUMN_LENGTH:4000$], event[$COLUMN_LENGTH:75$])",
		"create unique index IX_E3F57BD6 on SyncDLObject (type_[$COLUMN_LENGTH:75$], typePK)",
		"create index IX_28CD54BB on SyncDLObject (type_[$COLUMN_LENGTH:75$], version[$COLUMN_LENGTH:75$])",
		"create index IX_1CCA3B5 on SyncDLObject (version[$COLUMN_LENGTH:75$], type_[$COLUMN_LENGTH:75$])"
	};

}