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

package com.liferay.portal.upgrade.v7_0_6.util;

import java.sql.Types;

import java.util.HashMap;
import java.util.Map;

/**
 * @author	  Brian Wing Shun Chan
 * @generated
 */
public class RepositoryTable {

	public static final String TABLE_NAME = "Repository";

	public static final Object[][] TABLE_COLUMNS = {
		{"mvccVersion", Types.BIGINT},
		{"uuid_", Types.VARCHAR},
		{"repositoryId", Types.BIGINT},
		{"groupId", Types.BIGINT},
		{"companyId", Types.BIGINT},
		{"userId", Types.BIGINT},
		{"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP},
		{"modifiedDate", Types.TIMESTAMP},
		{"classNameId", Types.BIGINT},
		{"name", Types.VARCHAR},
		{"description", Types.VARCHAR},
		{"portletId", Types.VARCHAR},
		{"typeSettings", Types.CLOB},
		{"dlFolderId", Types.BIGINT},
		{"lastPublishDate", Types.TIMESTAMP}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP = new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("mvccVersion", Types.BIGINT);

TABLE_COLUMNS_MAP.put("uuid_", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("repositoryId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("classNameId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("name", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("description", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("portletId", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("typeSettings", Types.CLOB);

TABLE_COLUMNS_MAP.put("dlFolderId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("lastPublishDate", Types.TIMESTAMP);

}
	public static final String TABLE_SQL_CREATE = "create table Repository (mvccVersion LONG default 0 not null,uuid_ VARCHAR(75) null,repositoryId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,classNameId LONG,name VARCHAR(200) null,description STRING null,portletId VARCHAR(200) null,typeSettings TEXT null,dlFolderId LONG,lastPublishDate DATE null)";

	public static final String TABLE_SQL_DROP = "drop table Repository";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create unique index IX_60C8634C on Repository (groupId, name[$COLUMN_LENGTH:200$], portletId[$COLUMN_LENGTH:200$])",
		"create index IX_F543EA4 on Repository (uuid_[$COLUMN_LENGTH:75$], companyId)",
		"create unique index IX_11641E26 on Repository (uuid_[$COLUMN_LENGTH:75$], groupId)"
	};

}