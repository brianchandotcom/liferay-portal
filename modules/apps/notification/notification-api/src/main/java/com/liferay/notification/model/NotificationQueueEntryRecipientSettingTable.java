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
 * The table class for the &quot;NQueueEntryRecipientSetting&quot; database table.
 *
 * @author Gabriel Albuquerque
 * @see NotificationQueueEntryRecipientSetting
 * @generated
 */
public class NotificationQueueEntryRecipientSettingTable
	extends BaseTable<NotificationQueueEntryRecipientSettingTable> {

	public static final NotificationQueueEntryRecipientSettingTable INSTANCE =
		new NotificationQueueEntryRecipientSettingTable();

	public final Column<NotificationQueueEntryRecipientSettingTable, Long>
		mvccVersion = createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<NotificationQueueEntryRecipientSettingTable, Long>
		notificationQueueEntryRecipientSettingId = createColumn(
			"NQueueEntryRecipientSettingId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<NotificationQueueEntryRecipientSettingTable, Long>
		companyId = createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<NotificationQueueEntryRecipientSettingTable, Long>
		userId = createColumn(
			"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<NotificationQueueEntryRecipientSettingTable, String>
		userName = createColumn(
			"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<NotificationQueueEntryRecipientSettingTable, Date>
		createDate = createColumn(
			"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<NotificationQueueEntryRecipientSettingTable, Date>
		modifiedDate = createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<NotificationQueueEntryRecipientSettingTable, Long>
		notificationQueueEntryRecipientId = createColumn(
			"NQueueEntryRecipientId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<NotificationQueueEntryRecipientSettingTable, String>
		name = createColumn(
			"name", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<NotificationQueueEntryRecipientSettingTable, String>
		value = createColumn(
			"value", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);

	private NotificationQueueEntryRecipientSettingTable() {
		super(
			"NQueueEntryRecipientSetting",
			NotificationQueueEntryRecipientSettingTable::new);
	}

}