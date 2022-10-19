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
 * The table class for the &quot;NotificationRecipientSetting&quot; database table.
 *
 * @author Gabriel Albuquerque
 * @see NotificationRecipientSetting
 * @generated
 */
public class NotificationRecipientSettingTable
	extends BaseTable<NotificationRecipientSettingTable> {

	public static final NotificationRecipientSettingTable INSTANCE =
		new NotificationRecipientSettingTable();

	public final Column<NotificationRecipientSettingTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<NotificationRecipientSettingTable, String> uuid =
		createColumn("uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<NotificationRecipientSettingTable, Long>
		notificationRecipientSettingId = createColumn(
			"notificationRecipientSettingId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<NotificationRecipientSettingTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<NotificationRecipientSettingTable, Long> userId =
		createColumn("userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<NotificationRecipientSettingTable, String> userName =
		createColumn(
			"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<NotificationRecipientSettingTable, Date> createDate =
		createColumn(
			"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<NotificationRecipientSettingTable, Date> modifiedDate =
		createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<NotificationRecipientSettingTable, Long>
		notificationRecipientId = createColumn(
			"notificationRecipientId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<NotificationRecipientSettingTable, String> name =
		createColumn("name", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<NotificationRecipientSettingTable, String> value =
		createColumn("value", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);

	private NotificationRecipientSettingTable() {
		super(
			"NotificationRecipientSetting",
			NotificationRecipientSettingTable::new);
	}

}