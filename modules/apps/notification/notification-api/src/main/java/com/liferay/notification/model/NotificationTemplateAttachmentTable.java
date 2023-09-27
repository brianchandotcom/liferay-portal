/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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