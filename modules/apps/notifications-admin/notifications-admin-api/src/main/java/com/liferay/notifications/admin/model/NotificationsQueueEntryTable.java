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

package com.liferay.notifications.admin.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;NotificationsQueueEntry&quot; database table.
 *
 * @author Gabriel Albuquerque
 * @see NotificationsQueueEntry
 * @generated
 */
public class NotificationsQueueEntryTable
	extends BaseTable<NotificationsQueueEntryTable> {

	public static final NotificationsQueueEntryTable INSTANCE =
		new NotificationsQueueEntryTable();

	public final Column<NotificationsQueueEntryTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<NotificationsQueueEntryTable, Long>
		notificationsQueueEntryId = createColumn(
			"notificationsQueueEntryId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<NotificationsQueueEntryTable, Long> groupId =
		createColumn("groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<NotificationsQueueEntryTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<NotificationsQueueEntryTable, Long> userId =
		createColumn("userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<NotificationsQueueEntryTable, String> userName =
		createColumn(
			"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<NotificationsQueueEntryTable, Date> createDate =
		createColumn(
			"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<NotificationsQueueEntryTable, Date> modifiedDate =
		createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<NotificationsQueueEntryTable, Long> classNameId =
		createColumn(
			"classNameId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<NotificationsQueueEntryTable, Long> classPK =
		createColumn("classPK", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<NotificationsQueueEntryTable, Long>
		notificationsTemplateId = createColumn(
			"notificationsTemplateId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<NotificationsQueueEntryTable, String> from =
		createColumn("from_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<NotificationsQueueEntryTable, String> fromName =
		createColumn(
			"fromName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<NotificationsQueueEntryTable, String> to = createColumn(
		"to_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<NotificationsQueueEntryTable, String> toName =
		createColumn(
			"toName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<NotificationsQueueEntryTable, String> cc = createColumn(
		"cc", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<NotificationsQueueEntryTable, String> bcc =
		createColumn("bcc", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<NotificationsQueueEntryTable, String> subject =
		createColumn(
			"subject", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<NotificationsQueueEntryTable, String> body =
		createColumn("body", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<NotificationsQueueEntryTable, Double> priority =
		createColumn(
			"priority", Double.class, Types.DOUBLE, Column.FLAG_DEFAULT);
	public final Column<NotificationsQueueEntryTable, Boolean> sent =
		createColumn("sent", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);
	public final Column<NotificationsQueueEntryTable, Date> sentDate =
		createColumn(
			"sentDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);

	private NotificationsQueueEntryTable() {
		super("NotificationsQueueEntry", NotificationsQueueEntryTable::new);
	}

}