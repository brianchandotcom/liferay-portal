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

package com.liferay.bookmarks.internal.upgrade.v2_0_0.util;

import java.sql.Types;

import java.util.HashMap;
import java.util.Map;

/**
 * @author	  Preston Crary
 * @generated
 */
public class BookmarksEntryTable {

	public static final String TABLE_NAME = "BookmarksEntry";

	public static final Object[][] TABLE_COLUMNS = {
		{"uuid_", Types.VARCHAR},
		{"entryId", Types.BIGINT},
		{"groupId", Types.BIGINT},
		{"companyId", Types.BIGINT},
		{"userId", Types.BIGINT},
		{"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP},
		{"modifiedDate", Types.TIMESTAMP},
		{"folderId", Types.BIGINT},
		{"treePath", Types.VARCHAR},
		{"name", Types.VARCHAR},
		{"url", Types.VARCHAR},
		{"description", Types.VARCHAR},
		{"visits", Types.INTEGER},
		{"priority", Types.INTEGER},
		{"lastPublishDate", Types.TIMESTAMP},
		{"status", Types.INTEGER},
		{"statusByUserId", Types.BIGINT},
		{"statusByUserName", Types.VARCHAR},
		{"statusDate", Types.TIMESTAMP}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP = new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("uuid_", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("entryId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("folderId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("treePath", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("name", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("url", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("description", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("visits", Types.INTEGER);

TABLE_COLUMNS_MAP.put("priority", Types.INTEGER);

TABLE_COLUMNS_MAP.put("lastPublishDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("status", Types.INTEGER);

TABLE_COLUMNS_MAP.put("statusByUserId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("statusByUserName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("statusDate", Types.TIMESTAMP);

}
	public static final String TABLE_SQL_CREATE = "create table BookmarksEntry (uuid_ VARCHAR(75) null,entryId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,folderId LONG,treePath STRING null,name VARCHAR(255) null,url STRING null,description STRING null,visits INTEGER,priority INTEGER,lastPublishDate DATE null,status INTEGER,statusByUserId LONG,statusByUserName VARCHAR(75) null,statusDate DATE null)";

	public static final String TABLE_SQL_DROP = "drop table BookmarksEntry";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_276C8C13 on BookmarksEntry (companyId, status)",
		"create index IX_146382F2 on BookmarksEntry (groupId, folderId, status)",
		"create index IX_416AD7D5 on BookmarksEntry (groupId, status)",
		"create index IX_C78B61AC on BookmarksEntry (groupId, userId, folderId, status)",
		"create index IX_9D9CF70F on BookmarksEntry (groupId, userId, status)",
		"create index IX_89BEDC4F on BookmarksEntry (uuid_[$COLUMN_LENGTH:75$], companyId)",
		"create unique index IX_EAA02A91 on BookmarksEntry (uuid_[$COLUMN_LENGTH:75$], groupId)"
	};

}