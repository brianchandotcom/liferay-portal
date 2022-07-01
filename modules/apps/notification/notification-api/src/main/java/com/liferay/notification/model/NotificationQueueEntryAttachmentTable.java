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
 * The table class for the &quot;NQueueEntryAttachment&quot; database table.
 *
 * @author Gabriel Albuquerque
 * @see NotificationQueueEntryAttachment
 * @generated
 */
public class NotificationQueueEntryAttachmentTable
	extends BaseTable<NotificationQueueEntryAttachmentTable> {

	public static final NotificationQueueEntryAttachmentTable INSTANCE =
		new NotificationQueueEntryAttachmentTable();

	public final Column<NotificationQueueEntryAttachmentTable, Long>
		mvccVersion = createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<NotificationQueueEntryAttachmentTable, Long>
		notificationQueueEntryAttachmentId = createColumn(
			"NQueueEntryAttachmentId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<NotificationQueueEntryAttachmentTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<NotificationQueueEntryAttachmentTable, Long>
		fileEntryId = createColumn(
			"fileEntryId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<NotificationQueueEntryAttachmentTable, Long>
		notificationQueueEntryId = createColumn(
			"notificationQueueEntryId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);

	private NotificationQueueEntryAttachmentTable() {
		super(
			"NQueueEntryAttachment",
			NotificationQueueEntryAttachmentTable::new);
	}

}