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

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;NQueueEntryRecipient&quot; database table.
 *
 * @author Gabriel Albuquerque
 * @see NotificationQueueEntryRecipient
 * @generated
 */
public class NotificationQueueEntryRecipientTable
	extends BaseTable<NotificationQueueEntryRecipientTable> {

	public static final NotificationQueueEntryRecipientTable INSTANCE =
		new NotificationQueueEntryRecipientTable();

	public final Column<NotificationQueueEntryRecipientTable, Long>
		mvccVersion = createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<NotificationQueueEntryRecipientTable, Long>
		notificationQueueEntryRecipientId = createColumn(
			"NQueueEntryRecipientId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<NotificationQueueEntryRecipientTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<NotificationQueueEntryRecipientTable, Long> userId =
		createColumn("userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<NotificationQueueEntryRecipientTable, String> userName =
		createColumn(
			"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<NotificationQueueEntryRecipientTable, Date> createDate =
		createColumn(
			"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<NotificationQueueEntryRecipientTable, Date>
		modifiedDate = createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<NotificationQueueEntryRecipientTable, Long>
		notificationQueueEntryId = createColumn(
			"notificationQueueEntryId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<NotificationQueueEntryRecipientTable, String> type =
		createColumn("type_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);

	private NotificationQueueEntryRecipientTable() {
		super(
			"NQueueEntryRecipient", NotificationQueueEntryRecipientTable::new);
	}

}