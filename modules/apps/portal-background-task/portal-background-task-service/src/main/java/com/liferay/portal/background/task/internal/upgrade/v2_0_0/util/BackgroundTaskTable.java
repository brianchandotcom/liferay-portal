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

package com.liferay.portal.background.task.internal.upgrade.v2_0_0.util;

import java.sql.Types;

import java.util.HashMap;
import java.util.Map;

/**
 * @author	  Brian Wing Shun Chan
 * @generated
 */
public class BackgroundTaskTable {

	public static final String TABLE_NAME = "BackgroundTask";

	public static final Object[][] TABLE_COLUMNS = {
		{"mvccVersion", Types.BIGINT},
		{"backgroundTaskId", Types.BIGINT},
		{"groupId", Types.BIGINT},
		{"companyId", Types.BIGINT},
		{"userId", Types.BIGINT},
		{"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP},
		{"modifiedDate", Types.TIMESTAMP},
		{"name", Types.VARCHAR},
		{"servletContextNames", Types.VARCHAR},
		{"taskExecutorClassName", Types.VARCHAR},
		{"taskContextMap", Types.CLOB},
		{"completed", Types.BOOLEAN},
		{"completionDate", Types.TIMESTAMP},
		{"status", Types.INTEGER},
		{"statusMessage", Types.CLOB}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP = new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("mvccVersion", Types.BIGINT);

TABLE_COLUMNS_MAP.put("backgroundTaskId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("name", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("servletContextNames", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("taskExecutorClassName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("taskContextMap", Types.CLOB);

TABLE_COLUMNS_MAP.put("completed", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("completionDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("status", Types.INTEGER);

TABLE_COLUMNS_MAP.put("statusMessage", Types.CLOB);

}
	public static final String TABLE_SQL_CREATE = "create table BackgroundTask (mvccVersion LONG default 0 not null,backgroundTaskId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,name VARCHAR(255) null,servletContextNames VARCHAR(255) null,taskExecutorClassName VARCHAR(200) null,taskContextMap TEXT null,completed BOOLEAN,completionDate DATE null,status INTEGER,statusMessage TEXT null)";

	public static final String TABLE_SQL_DROP = "drop table BackgroundTask";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_C5A6C78F on BackgroundTask (companyId)",
		"create index IX_FBF5FAA2 on BackgroundTask (completed)",
		"create index IX_579C63B0 on BackgroundTask (groupId, name[$COLUMN_LENGTH:255$], taskExecutorClassName[$COLUMN_LENGTH:200$], completed)",
		"create index IX_C71C3B7 on BackgroundTask (groupId, status)",
		"create index IX_7A9FF471 on BackgroundTask (groupId, taskExecutorClassName[$COLUMN_LENGTH:200$], completed)",
		"create index IX_7E757D70 on BackgroundTask (groupId, taskExecutorClassName[$COLUMN_LENGTH:200$], status)",
		"create index IX_75638CDF on BackgroundTask (status)",
		"create index IX_2FCFE748 on BackgroundTask (taskExecutorClassName[$COLUMN_LENGTH:200$], status)"
	};

}