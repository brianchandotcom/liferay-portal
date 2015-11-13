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

package com.liferay.portal.upgrade.v7_0_0.util;

import java.sql.Types;

import java.util.HashMap;
import java.util.Map;

/**
 * @author	  Brian Wing Shun Chan
 * @generated
 */
public class DLFolderTable {

	public static final String TABLE_NAME = "DLFolder";

	public static final Object[][] TABLE_COLUMNS = {
		{"uuid_", Types.VARCHAR},
		{"folderId", Types.BIGINT},
		{"groupId", Types.BIGINT},
		{"companyId", Types.BIGINT},
		{"userId", Types.BIGINT},
		{"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP},
		{"modifiedDate", Types.TIMESTAMP},
		{"repositoryId", Types.BIGINT},
		{"mountPoint", Types.BOOLEAN},
		{"parentFolderId", Types.BIGINT},
		{"treePath", Types.VARCHAR},
		{"name", Types.VARCHAR},
		{"description", Types.VARCHAR},
		{"lastPostDate", Types.TIMESTAMP},
		{"defaultFileEntryTypeId", Types.BIGINT},
		{"hidden_", Types.BOOLEAN},
		{"restrictionType", Types.INTEGER},
		{"lastPublishDate", Types.TIMESTAMP},
		{"status", Types.INTEGER},
		{"statusByUserId", Types.BIGINT},
		{"statusByUserName", Types.VARCHAR},
		{"statusDate", Types.TIMESTAMP}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP = new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("uuid_", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("folderId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("repositoryId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("mountPoint", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("parentFolderId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("treePath", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("name", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("description", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("lastPostDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("defaultFileEntryTypeId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("hidden_", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("restrictionType", Types.INTEGER);

TABLE_COLUMNS_MAP.put("lastPublishDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("status", Types.INTEGER);

TABLE_COLUMNS_MAP.put("statusByUserId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("statusByUserName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("statusDate", Types.TIMESTAMP);

}
	public static final String TABLE_SQL_CREATE = "create table DLFolder (uuid_ VARCHAR(75) null,folderId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,repositoryId LONG,mountPoint BOOLEAN,parentFolderId LONG,treePath STRING null,name VARCHAR(255) null,description STRING null,lastPostDate DATE null,defaultFileEntryTypeId LONG,hidden_ BOOLEAN,restrictionType INTEGER,lastPublishDate DATE null,status INTEGER,statusByUserId LONG,statusByUserName VARCHAR(75) null,statusDate DATE null)";

	public static final String TABLE_SQL_DROP = "drop table DLFolder";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_E79BE432 on DLFolder (companyId, status)",
		"create index IX_C88430AB on DLFolder (groupId, mountPoint, parentFolderId, hidden_, status)",
		"create index IX_CE360BF6 on DLFolder (groupId, parentFolderId, hidden_, status)",
		"create unique index IX_902FD874 on DLFolder (groupId, parentFolderId, name[$COLUMN_LENGTH:255$])",
		"create index IX_51556082 on DLFolder (parentFolderId, name[$COLUMN_LENGTH:255$])",
		"create index IX_6F63F140 on DLFolder (repositoryId, mountPoint)",
		"create index IX_6747B2BC on DLFolder (repositoryId, parentFolderId)",
		"create index IX_DA448450 on DLFolder (uuid_[$COLUMN_LENGTH:75$], companyId)",
		"create unique index IX_3CC1DED2 on DLFolder (uuid_[$COLUMN_LENGTH:75$], groupId)"
	};

}