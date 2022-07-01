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

/**
 * The table class for the &quot;NTemplateAttachment&quot; database table.
 *
 * @author Gabriel Albuquerque
 * @see NotificationTemplateAttachment
 * @generated
 */
public class NotificationTemplateAttachmentTable
	extends BaseTable<NotificationTemplateAttachmentTable> {

	public static final NotificationTemplateAttachmentTable INSTANCE =
		new NotificationTemplateAttachmentTable();

	public final Column<NotificationTemplateAttachmentTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<NotificationTemplateAttachmentTable, Long>
		notificationTemplateAttachmentId = createColumn(
			"NTemplateAttachmentId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<NotificationTemplateAttachmentTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<NotificationTemplateAttachmentTable, Long>
		notificationTemplateId = createColumn(
			"notificationTemplateId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<NotificationTemplateAttachmentTable, Long>
		objectFieldId = createColumn(
			"objectFieldId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);

	private NotificationTemplateAttachmentTable() {
		super("NTemplateAttachment", NotificationTemplateAttachmentTable::new);
	}

}