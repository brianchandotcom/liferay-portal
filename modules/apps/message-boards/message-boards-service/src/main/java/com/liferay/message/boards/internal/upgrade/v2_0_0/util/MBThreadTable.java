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

package com.liferay.message.boards.internal.upgrade.v2_0_0.util;

import java.sql.Types;

import java.util.HashMap;
import java.util.Map;

/**
 * @author	  Brian Wing Shun Chan
 * @generated
 */
public class MBThreadTable {

	public static final String TABLE_NAME = "MBThread";

	public static final Object[][] TABLE_COLUMNS = {
		{"uuid_", Types.VARCHAR},
		{"threadId", Types.BIGINT},
		{"groupId", Types.BIGINT},
		{"companyId", Types.BIGINT},
		{"userId", Types.BIGINT},
		{"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP},
		{"modifiedDate", Types.TIMESTAMP},
		{"categoryId", Types.BIGINT},
		{"rootMessageId", Types.BIGINT},
		{"rootMessageUserId", Types.BIGINT},
		{"title", Types.VARCHAR},
		{"messageCount", Types.INTEGER},
		{"viewCount", Types.INTEGER},
		{"lastPostByUserId", Types.BIGINT},
		{"lastPostDate", Types.TIMESTAMP},
		{"priority", Types.DOUBLE},
		{"question", Types.BOOLEAN},
		{"lastPublishDate", Types.TIMESTAMP},
		{"status", Types.INTEGER},
		{"statusByUserId", Types.BIGINT},
		{"statusByUserName", Types.VARCHAR},
		{"statusDate", Types.TIMESTAMP}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP = new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("uuid_", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("threadId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("categoryId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("rootMessageId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("rootMessageUserId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("title", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("messageCount", Types.INTEGER);

TABLE_COLUMNS_MAP.put("viewCount", Types.INTEGER);

TABLE_COLUMNS_MAP.put("lastPostByUserId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("lastPostDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("priority", Types.DOUBLE);

TABLE_COLUMNS_MAP.put("question", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("lastPublishDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("status", Types.INTEGER);

TABLE_COLUMNS_MAP.put("statusByUserId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("statusByUserName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("statusDate", Types.TIMESTAMP);

}
	public static final String TABLE_SQL_CREATE = "create table MBThread (uuid_ VARCHAR(75) null,threadId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,categoryId LONG,rootMessageId LONG,rootMessageUserId LONG,title VARCHAR(75) null,messageCount INTEGER,viewCount INTEGER,lastPostByUserId LONG,lastPostDate DATE null,priority DOUBLE,question BOOLEAN,lastPublishDate DATE null,status INTEGER,statusByUserId LONG,statusByUserName VARCHAR(75) null,statusDate DATE null)";

	public static final String TABLE_SQL_DROP = "drop table MBThread";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_41F6DC8A on MBThread (categoryId, priority)",
		"create index IX_50F1904A on MBThread (groupId, categoryId, lastPostDate)",
		"create index IX_485F7E98 on MBThread (groupId, categoryId, status)",
		"create index IX_E1E7142B on MBThread (groupId, status)",
		"create index IX_AEDD9CB5 on MBThread (lastPostDate, priority)",
		"create index IX_CC993ECB on MBThread (rootMessageId)",
		"create index IX_F8CA2AB9 on MBThread (uuid_[$COLUMN_LENGTH:75$], companyId)",
		"create unique index IX_3A200B7B on MBThread (uuid_[$COLUMN_LENGTH:75$], groupId)"
	};

}