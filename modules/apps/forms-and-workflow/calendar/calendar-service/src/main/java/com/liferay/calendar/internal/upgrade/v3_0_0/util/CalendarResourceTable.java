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

package com.liferay.calendar.internal.upgrade.v3_0_0.util;

import java.sql.Types;

import java.util.HashMap;
import java.util.Map;

/**
 * @author	  Preston Crary
 * @generated
 */
public class CalendarResourceTable {

	public static final String TABLE_NAME = "CalendarResource";

	public static final Object[][] TABLE_COLUMNS = {
		{"uuid_", Types.VARCHAR},
		{"calendarResourceId", Types.BIGINT},
		{"groupId", Types.BIGINT},
		{"companyId", Types.BIGINT},
		{"userId", Types.BIGINT},
		{"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP},
		{"modifiedDate", Types.TIMESTAMP},
		{"classNameId", Types.BIGINT},
		{"classPK", Types.BIGINT},
		{"classUuid", Types.VARCHAR},
		{"code_", Types.VARCHAR},
		{"name", Types.VARCHAR},
		{"description", Types.VARCHAR},
		{"active_", Types.BOOLEAN},
		{"lastPublishDate", Types.TIMESTAMP}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP = new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("uuid_", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("calendarResourceId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("classNameId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("classPK", Types.BIGINT);

TABLE_COLUMNS_MAP.put("classUuid", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("code_", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("name", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("description", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("active_", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("lastPublishDate", Types.TIMESTAMP);

}
	public static final String TABLE_SQL_CREATE = "create table CalendarResource (uuid_ VARCHAR(75) null,calendarResourceId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,classNameId LONG,classPK LONG,classUuid VARCHAR(75) null,code_ VARCHAR(75) null,name STRING null,description STRING null,active_ BOOLEAN,lastPublishDate DATE null)";

	public static final String TABLE_SQL_DROP = "drop table CalendarResource";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_76DDD0F7 on CalendarResource (active_)",
		"create unique index IX_16A12327 on CalendarResource (classNameId, classPK)",
		"create index IX_4470A59D on CalendarResource (companyId, code_[$COLUMN_LENGTH:75$], active_)",
		"create index IX_40678371 on CalendarResource (groupId, active_)",
		"create index IX_55C2F8AA on CalendarResource (groupId, code_[$COLUMN_LENGTH:75$])",
		"create index IX_56A06BC6 on CalendarResource (uuid_[$COLUMN_LENGTH:75$], companyId)",
		"create unique index IX_4ABD2BC8 on CalendarResource (uuid_[$COLUMN_LENGTH:75$], groupId)"
	};

}