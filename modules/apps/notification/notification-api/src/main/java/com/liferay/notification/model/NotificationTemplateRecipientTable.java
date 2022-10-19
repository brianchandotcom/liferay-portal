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
 * The table class for the &quot;NTemplateRecipient&quot; database table.
 *
 * @author Gabriel Albuquerque
 * @see NotificationTemplateRecipient
 * @generated
 */
public class NotificationTemplateRecipientTable
	extends BaseTable<NotificationTemplateRecipientTable> {

	public static final NotificationTemplateRecipientTable INSTANCE =
		new NotificationTemplateRecipientTable();

	public final Column<NotificationTemplateRecipientTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<NotificationTemplateRecipientTable, Long>
		notificationTemplateRecipientId = createColumn(
			"NTemplateRecipientId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<NotificationTemplateRecipientTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<NotificationTemplateRecipientTable, Long> userId =
		createColumn("userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<NotificationTemplateRecipientTable, String> userName =
		createColumn(
			"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<NotificationTemplateRecipientTable, Date> createDate =
		createColumn(
			"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<NotificationTemplateRecipientTable, Date> modifiedDate =
		createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<NotificationTemplateRecipientTable, Long>
		notificationTemplateId = createColumn(
			"notificationTemplateId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<NotificationTemplateRecipientTable, String> type =
		createColumn("type_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);

	private NotificationTemplateRecipientTable() {
		super("NTemplateRecipient", NotificationTemplateRecipientTable::new);
	}

}