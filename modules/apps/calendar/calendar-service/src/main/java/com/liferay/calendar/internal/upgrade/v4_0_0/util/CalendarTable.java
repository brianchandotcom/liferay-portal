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

package com.liferay.calendar.internal.upgrade.v4_0_0.util;

import java.sql.Types;

import java.util.HashMap;
import java.util.Map;

/**
 * @author	  Brian Wing Shun Chan
 * @generated
 */
public class CalendarTable {

	public static final String TABLE_NAME = "Calendar";

	public static final Object[][] TABLE_COLUMNS = {
		{"uuid_", Types.VARCHAR},
		{"calendarId", Types.BIGINT},
		{"groupId", Types.BIGINT},
		{"companyId", Types.BIGINT},
		{"userId", Types.BIGINT},
		{"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP},
		{"modifiedDate", Types.TIMESTAMP},
		{"calendarResourceId", Types.BIGINT},
		{"name", Types.VARCHAR},
		{"description", Types.VARCHAR},
		{"timeZoneId", Types.VARCHAR},
		{"color", Types.INTEGER},
		{"defaultCalendar", Types.BOOLEAN},
		{"enableComments", Types.BOOLEAN},
		{"enableRatings", Types.BOOLEAN},
		{"lastPublishDate", Types.TIMESTAMP}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP = new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("uuid_", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("calendarId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("calendarResourceId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("name", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("description", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("timeZoneId", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("color", Types.INTEGER);

TABLE_COLUMNS_MAP.put("defaultCalendar", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("enableComments", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("enableRatings", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("lastPublishDate", Types.TIMESTAMP);

}
	public static final String TABLE_SQL_CREATE = "create table Calendar (uuid_ VARCHAR(75) null,calendarId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,calendarResourceId LONG,name STRING null,description STRING null,timeZoneId VARCHAR(75) null,color INTEGER,defaultCalendar BOOLEAN,enableComments BOOLEAN,enableRatings BOOLEAN,lastPublishDate DATE null)";

	public static final String TABLE_SQL_DROP = "drop table Calendar";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_97FC174E on Calendar (groupId, calendarResourceId, defaultCalendar)",
		"create index IX_97656498 on Calendar (uuid_[$COLUMN_LENGTH:75$], companyId)",
		"create unique index IX_3AE311A on Calendar (uuid_[$COLUMN_LENGTH:75$], groupId)"
	};

}