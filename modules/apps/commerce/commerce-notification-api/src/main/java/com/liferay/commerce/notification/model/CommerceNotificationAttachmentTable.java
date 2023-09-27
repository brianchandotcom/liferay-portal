/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.notification.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;CNotificationAttachment&quot; database table.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceNotificationAttachment
 * @generated
 */
public class CommerceNotificationAttachmentTable
	extends BaseTable<CommerceNotificationAttachmentTable> {

	public static final CommerceNotificationAttachmentTable INSTANCE =
		new CommerceNotificationAttachmentTable();

	public final Column<CommerceNotificationAttachmentTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<CommerceNotificationAttachmentTable, String> uuid =
		createColumn("uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommerceNotificationAttachmentTable, Long>
		commerceNotificationAttachmentId = createColumn(
			"CNotificationAttachmentId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<CommerceNotificationAttachmentTable, Long> groupId =
		createColumn("groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceNotificationAttachmentTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceNotificationAttachmentTable, Long> userId =
		createColumn("userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceNotificationAttachmentTable, String> userName =
		createColumn(
			"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommerceNotificationAttachmentTable, Date> createDate =
		createColumn(
			"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommerceNotificationAttachmentTable, Date>
		modifiedDate = createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommerceNotificationAttachmentTable, Long>
		commerceNotificationQueueEntryId = createColumn(
			"CNotificationQueueEntryId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<CommerceNotificationAttachmentTable, Long> fileEntryId =
		createColumn(
			"fileEntryId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceNotificationAttachmentTable, Boolean>
		deleteOnSend = createColumn(
			"deleteOnSend", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);

	private CommerceNotificationAttachmentTable() {
		super(
			"CNotificationAttachment",
			CommerceNotificationAttachmentTable::new);
	}

}