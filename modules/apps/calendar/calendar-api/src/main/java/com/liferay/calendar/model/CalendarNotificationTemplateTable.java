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

package com.liferay.calendar.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.Table;

import java.sql.Clob;
import java.sql.Types;

import java.util.Date;

/**
 * The table class for the CalendarNotificationTemplate.
 *
 * @author Eduardo Lundgren
 * @generated
 */
public class CalendarNotificationTemplateTable
	extends Table<CalendarNotificationTemplateTable> {

	public static final CalendarNotificationTemplateTable INSTANCE =
		new CalendarNotificationTemplateTable();

	public final Column<CalendarNotificationTemplateTable, Long> mvccVersion =
		createColumn("mvccVersion", Long.class, Types.BIGINT);
	public final Column<CalendarNotificationTemplateTable, String> uuid =
		createColumn("uuid_", String.class, Types.VARCHAR);
	public final Column<CalendarNotificationTemplateTable, Long>
		calendarNotificationTemplateId = createColumn(
			"calendarNotificationTemplateId", Long.class, Types.BIGINT);
	public final Column<CalendarNotificationTemplateTable, Long> groupId =
		createColumn("groupId", Long.class, Types.BIGINT);
	public final Column<CalendarNotificationTemplateTable, Long> companyId =
		createColumn("companyId", Long.class, Types.BIGINT);
	public final Column<CalendarNotificationTemplateTable, Long> userId =
		createColumn("userId", Long.class, Types.BIGINT);
	public final Column<CalendarNotificationTemplateTable, String> userName =
		createColumn("userName", String.class, Types.VARCHAR);
	public final Column<CalendarNotificationTemplateTable, Date> createDate =
		createColumn("createDate", Date.class, Types.TIMESTAMP);
	public final Column<CalendarNotificationTemplateTable, Date> modifiedDate =
		createColumn("modifiedDate", Date.class, Types.TIMESTAMP);
	public final Column<CalendarNotificationTemplateTable, Long> calendarId =
		createColumn("calendarId", Long.class, Types.BIGINT);
	public final Column<CalendarNotificationTemplateTable, String>
		notificationType = createColumn(
			"notificationType", String.class, Types.VARCHAR);
	public final Column<CalendarNotificationTemplateTable, String>
		notificationTypeSettings = createColumn(
			"notificationTypeSettings", String.class, Types.VARCHAR);
	public final Column<CalendarNotificationTemplateTable, String>
		notificationTemplateType = createColumn(
			"notificationTemplateType", String.class, Types.VARCHAR);
	public final Column<CalendarNotificationTemplateTable, String> subject =
		createColumn("subject", String.class, Types.VARCHAR);
	public final Column<CalendarNotificationTemplateTable, Clob> body =
		createColumn("body", Clob.class, Types.CLOB);
	public final Column<CalendarNotificationTemplateTable, Date>
		lastPublishDate = createColumn(
			"lastPublishDate", Date.class, Types.TIMESTAMP);

	private CalendarNotificationTemplateTable() {
		super(
			"CalendarNotificationTemplate",
			CalendarNotificationTemplateTable::new);
	}

}