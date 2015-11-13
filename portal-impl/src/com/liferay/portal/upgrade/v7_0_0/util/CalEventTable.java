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
public class CalEventTable {

	public static final String TABLE_NAME = "CalEvent";

	public static final Object[][] TABLE_COLUMNS = {
		{"uuid_", Types.VARCHAR},
		{"eventId", Types.BIGINT},
		{"groupId", Types.BIGINT},
		{"companyId", Types.BIGINT},
		{"userId", Types.BIGINT},
		{"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP},
		{"modifiedDate", Types.TIMESTAMP},
		{"title", Types.VARCHAR},
		{"description", Types.CLOB},
		{"location", Types.VARCHAR},
		{"startDate", Types.TIMESTAMP},
		{"endDate", Types.TIMESTAMP},
		{"durationHour", Types.INTEGER},
		{"durationMinute", Types.INTEGER},
		{"allDay", Types.BOOLEAN},
		{"timeZoneSensitive", Types.BOOLEAN},
		{"type_", Types.VARCHAR},
		{"repeating", Types.BOOLEAN},
		{"recurrence", Types.CLOB},
		{"remindBy", Types.INTEGER},
		{"firstReminder", Types.INTEGER},
		{"secondReminder", Types.INTEGER}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP = new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("uuid_", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("eventId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("title", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("description", Types.CLOB);

TABLE_COLUMNS_MAP.put("location", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("startDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("endDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("durationHour", Types.INTEGER);

TABLE_COLUMNS_MAP.put("durationMinute", Types.INTEGER);

TABLE_COLUMNS_MAP.put("allDay", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("timeZoneSensitive", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("type_", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("repeating", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("recurrence", Types.CLOB);

TABLE_COLUMNS_MAP.put("remindBy", Types.INTEGER);

TABLE_COLUMNS_MAP.put("firstReminder", Types.INTEGER);

TABLE_COLUMNS_MAP.put("secondReminder", Types.INTEGER);

}
	public static final String TABLE_SQL_CREATE = "create table CalEvent (uuid_ VARCHAR(75) null,eventId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,title VARCHAR(75) null,description TEXT null,location STRING null,startDate DATE null,endDate DATE null,durationHour INTEGER,durationMinute INTEGER,allDay BOOLEAN,timeZoneSensitive BOOLEAN,type_ VARCHAR(75) null,repeating BOOLEAN,recurrence TEXT null,remindBy INTEGER,firstReminder INTEGER,secondReminder INTEGER)";

	public static final String TABLE_SQL_DROP = "drop table CalEvent";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_D6FD9496 on CalEvent (companyId)",
		"create index IX_4FDDD2BF on CalEvent (groupId, repeating)",
		"create index IX_FD93CBFA on CalEvent (groupId, type_[$COLUMN_LENGTH:75$], repeating)",
		"create index IX_F6006202 on CalEvent (remindBy)",
		"create index IX_299639C6 on CalEvent (uuid_[$COLUMN_LENGTH:75$], companyId)",
		"create unique index IX_5CCE79C8 on CalEvent (uuid_[$COLUMN_LENGTH:75$], groupId)"
	};

}