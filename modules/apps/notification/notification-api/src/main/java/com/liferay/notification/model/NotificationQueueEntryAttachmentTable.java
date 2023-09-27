/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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