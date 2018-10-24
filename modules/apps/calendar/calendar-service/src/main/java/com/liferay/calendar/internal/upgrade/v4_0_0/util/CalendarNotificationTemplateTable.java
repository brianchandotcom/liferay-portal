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
public class CalendarNotificationTemplateTable {

	public static final String TABLE_NAME = "CalendarNotificationTemplate";

	public static final Object[][] TABLE_COLUMNS = {
		{"uuid_", Types.VARCHAR},
		{"calendarNotificationTemplateId", Types.BIGINT},
		{"groupId", Types.BIGINT},
		{"companyId", Types.BIGINT},
		{"userId", Types.BIGINT},
		{"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP},
		{"modifiedDate", Types.TIMESTAMP},
		{"calendarId", Types.BIGINT},
		{"notificationType", Types.VARCHAR},
		{"notificationTypeSettings", Types.VARCHAR},
		{"notificationTemplateType", Types.VARCHAR},
		{"subject", Types.VARCHAR},
		{"body", Types.CLOB},
		{"lastPublishDate", Types.TIMESTAMP}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP = new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("uuid_", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("calendarNotificationTemplateId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("calendarId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("notificationType", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("notificationTypeSettings", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("notificationTemplateType", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("subject", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("body", Types.CLOB);

TABLE_COLUMNS_MAP.put("lastPublishDate", Types.TIMESTAMP);

}
	public static final String TABLE_SQL_CREATE = "create table CalendarNotificationTemplate (uuid_ VARCHAR(75) null,calendarNotificationTemplateId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,calendarId LONG,notificationType VARCHAR(75) null,notificationTypeSettings VARCHAR(75) null,notificationTemplateType VARCHAR(75) null,subject VARCHAR(75) null,body TEXT null,lastPublishDate DATE null)";

	public static final String TABLE_SQL_DROP = "drop table CalendarNotificationTemplate";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_7727A482 on CalendarNotificationTemplate (calendarId, notificationType[$COLUMN_LENGTH:75$], notificationTemplateType[$COLUMN_LENGTH:75$])",
		"create index IX_4D7D97BD on CalendarNotificationTemplate (uuid_[$COLUMN_LENGTH:75$], companyId)",
		"create unique index IX_4012E97F on CalendarNotificationTemplate (uuid_[$COLUMN_LENGTH:75$], groupId)"
	};

}