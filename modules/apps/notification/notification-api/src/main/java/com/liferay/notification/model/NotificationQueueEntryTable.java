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

package com.liferay.notification.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Clob;
import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;NotificationQueueEntry&quot; database table.
 *
 * @author Gabriel Albuquerque
 * @see NotificationQueueEntry
 * @generated
 */
public class NotificationQueueEntryTable
	extends BaseTable<NotificationQueueEntryTable> {

	public static final NotificationQueueEntryTable INSTANCE =
		new NotificationQueueEntryTable();

	public final Column<NotificationQueueEntryTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<NotificationQueueEntryTable, Long>
		notificationQueueEntryId = createColumn(
			"notificationQueueEntryId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<NotificationQueueEntryTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<NotificationQueueEntryTable, Long> userId =
		createColumn("userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<NotificationQueueEntryTable, String> userName =
		createColumn(
			"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<NotificationQueueEntryTable, Date> createDate =
		createColumn(
			"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<NotificationQueueEntryTable, Date> modifiedDate =
		createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<NotificationQueueEntryTable, Long>
		notificationTemplateId = createColumn(
			"notificationTemplateId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<NotificationQueueEntryTable, String> bcc = createColumn(
		"bcc", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<NotificationQueueEntryTable, Clob> body = createColumn(
		"body", Clob.class, Types.CLOB, Column.FLAG_DEFAULT);
	public final Column<NotificationQueueEntryTable, String> cc = createColumn(
		"cc", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<NotificationQueueEntryTable, Long> classNameId =
		createColumn(
			"classNameId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<NotificationQueueEntryTable, Long> classPK =
		createColumn("classPK", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<NotificationQueueEntryTable, String> from =
		createColumn("from_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<NotificationQueueEntryTable, String> fromName =
		createColumn(
			"fromName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<NotificationQueueEntryTable, Double> priority =
		createColumn(
			"priority", Double.class, Types.DOUBLE, Column.FLAG_DEFAULT);
	public final Column<NotificationQueueEntryTable, Date> sentDate =
		createColumn(
			"sentDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<NotificationQueueEntryTable, String> subject =
		createColumn(
			"subject", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<NotificationQueueEntryTable, String> to = createColumn(
		"to_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<NotificationQueueEntryTable, String> toName =
		createColumn(
			"toName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<NotificationQueueEntryTable, String> type =
		createColumn("type_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<NotificationQueueEntryTable, Integer> status =
		createColumn(
			"status", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);

	private NotificationQueueEntryTable() {
		super("NotificationQueueEntry", NotificationQueueEntryTable::new);
	}

}