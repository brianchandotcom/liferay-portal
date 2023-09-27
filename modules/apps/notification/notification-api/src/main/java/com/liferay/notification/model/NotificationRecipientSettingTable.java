/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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