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
public class MBMessageTable {

	public static final String TABLE_NAME = "MBMessage";

	public static final Object[][] TABLE_COLUMNS = {
		{"uuid_", Types.VARCHAR},
		{"messageId", Types.BIGINT},
		{"groupId", Types.BIGINT},
		{"companyId", Types.BIGINT},
		{"userId", Types.BIGINT},
		{"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP},
		{"modifiedDate", Types.TIMESTAMP},
		{"classNameId", Types.BIGINT},
		{"classPK", Types.BIGINT},
		{"categoryId", Types.BIGINT},
		{"threadId", Types.BIGINT},
		{"rootMessageId", Types.BIGINT},
		{"parentMessageId", Types.BIGINT},
		{"subject", Types.VARCHAR},
		{"body", Types.CLOB},
		{"format", Types.VARCHAR},
		{"anonymous", Types.BOOLEAN},
		{"priority", Types.DOUBLE},
		{"allowPingbacks", Types.BOOLEAN},
		{"answer", Types.BOOLEAN},
		{"lastPublishDate", Types.TIMESTAMP},
		{"status", Types.INTEGER},
		{"statusByUserId", Types.BIGINT},
		{"statusByUserName", Types.VARCHAR},
		{"statusDate", Types.TIMESTAMP}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP = new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("uuid_", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("messageId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("classNameId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("classPK", Types.BIGINT);

TABLE_COLUMNS_MAP.put("categoryId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("threadId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("rootMessageId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("parentMessageId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("subject", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("body", Types.CLOB);

TABLE_COLUMNS_MAP.put("format", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("anonymous", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("priority", Types.DOUBLE);

TABLE_COLUMNS_MAP.put("allowPingbacks", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("answer", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("lastPublishDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("status", Types.INTEGER);

TABLE_COLUMNS_MAP.put("statusByUserId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("statusByUserName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("statusDate", Types.TIMESTAMP);

}
	public static final String TABLE_SQL_CREATE = "create table MBMessage (uuid_ VARCHAR(75) null,messageId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,classNameId LONG,classPK LONG,categoryId LONG,threadId LONG,rootMessageId LONG,parentMessageId LONG,subject VARCHAR(75) null,body TEXT null,format VARCHAR(75) null,anonymous BOOLEAN,priority DOUBLE,allowPingbacks BOOLEAN,answer BOOLEAN,lastPublishDate DATE null,status INTEGER,statusByUserId LONG,statusByUserName VARCHAR(75) null,statusDate DATE null)";

	public static final String TABLE_SQL_DROP = "drop table MBMessage";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_F6687633 on MBMessage (classNameId, classPK, status)",
		"create index IX_1AD93C16 on MBMessage (companyId, status)",
		"create index IX_4257DB85 on MBMessage (groupId, categoryId, status)",
		"create index IX_CBFDBF0A on MBMessage (groupId, categoryId, threadId, answer)",
		"create index IX_385E123E on MBMessage (groupId, categoryId, threadId, status)",
		"create index IX_ED39AC98 on MBMessage (groupId, status)",
		"create index IX_377858D2 on MBMessage (groupId, userId, status)",
		"create index IX_6A095F16 on MBMessage (parentMessageId, status)",
		"create index IX_9D7C3B23 on MBMessage (threadId, answer)",
		"create index IX_A7038CD7 on MBMessage (threadId, parentMessageId)",
		"create index IX_9DC8E57 on MBMessage (threadId, status)",
		"create index IX_4A4BB4ED on MBMessage (userId, classNameId, classPK, status)",
		"create index IX_3321F142 on MBMessage (userId, classNameId, status)",
		"create index IX_57CA9FEC on MBMessage (uuid_[$COLUMN_LENGTH:75$], companyId)",
		"create unique index IX_8D12316E on MBMessage (uuid_[$COLUMN_LENGTH:75$], groupId)"
	};

}