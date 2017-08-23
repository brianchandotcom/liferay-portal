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
public class CalendarBookingTable {

	public static final String TABLE_NAME = "CalendarBooking";

	public static final Object[][] TABLE_COLUMNS = {
		{"uuid_", Types.VARCHAR},
		{"calendarBookingId", Types.BIGINT},
		{"groupId", Types.BIGINT},
		{"companyId", Types.BIGINT},
		{"userId", Types.BIGINT},
		{"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP},
		{"modifiedDate", Types.TIMESTAMP},
		{"calendarId", Types.BIGINT},
		{"calendarResourceId", Types.BIGINT},
		{"parentCalendarBookingId", Types.BIGINT},
		{"recurringCalendarBookingId", Types.BIGINT},
		{"vEventUid", Types.VARCHAR},
		{"title", Types.VARCHAR},
		{"description", Types.CLOB},
		{"location", Types.VARCHAR},
		{"startTime", Types.BIGINT},
		{"endTime", Types.BIGINT},
		{"allDay", Types.BOOLEAN},
		{"recurrence", Types.VARCHAR},
		{"firstReminder", Types.BIGINT},
		{"firstReminderType", Types.VARCHAR},
		{"secondReminder", Types.BIGINT},
		{"secondReminderType", Types.VARCHAR},
		{"lastPublishDate", Types.TIMESTAMP},
		{"status", Types.INTEGER},
		{"statusByUserId", Types.BIGINT},
		{"statusByUserName", Types.VARCHAR},
		{"statusDate", Types.TIMESTAMP}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP = new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("uuid_", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("calendarBookingId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("calendarId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("calendarResourceId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("parentCalendarBookingId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("recurringCalendarBookingId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("vEventUid", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("title", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("description", Types.CLOB);

TABLE_COLUMNS_MAP.put("location", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("startTime", Types.BIGINT);

TABLE_COLUMNS_MAP.put("endTime", Types.BIGINT);

TABLE_COLUMNS_MAP.put("allDay", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("recurrence", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("firstReminder", Types.BIGINT);

TABLE_COLUMNS_MAP.put("firstReminderType", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("secondReminder", Types.BIGINT);

TABLE_COLUMNS_MAP.put("secondReminderType", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("lastPublishDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("status", Types.INTEGER);

TABLE_COLUMNS_MAP.put("statusByUserId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("statusByUserName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("statusDate", Types.TIMESTAMP);

}
	public static final String TABLE_SQL_CREATE = "create table CalendarBooking (uuid_ VARCHAR(75) null,calendarBookingId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,calendarId LONG,calendarResourceId LONG,parentCalendarBookingId LONG,recurringCalendarBookingId LONG,vEventUid VARCHAR(255) null,title STRING null,description TEXT null,location STRING null,startTime LONG,endTime LONG,allDay BOOLEAN,recurrence STRING null,firstReminder LONG,firstReminderType VARCHAR(75) null,secondReminder LONG,secondReminderType VARCHAR(75) null,lastPublishDate DATE null,status INTEGER,statusByUserId LONG,statusByUserName VARCHAR(75) null,statusDate DATE null)";

	public static final String TABLE_SQL_DROP = "drop table CalendarBooking";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create unique index IX_113A264E on CalendarBooking (calendarId, parentCalendarBookingId)",
		"create index IX_470170B4 on CalendarBooking (calendarId, status)",
		"create unique index IX_8B23DA0E on CalendarBooking (calendarId, vEventUid[$COLUMN_LENGTH:255$])",
		"create index IX_B198FFC on CalendarBooking (calendarResourceId)",
		"create index IX_F7B8A941 on CalendarBooking (parentCalendarBookingId, status)",
		"create index IX_14ADC52E on CalendarBooking (recurringCalendarBookingId)",
		"create index IX_A21D9FD5 on CalendarBooking (uuid_[$COLUMN_LENGTH:75$], companyId)",
		"create unique index IX_F4C61797 on CalendarBooking (uuid_[$COLUMN_LENGTH:75$], groupId)"
	};

}