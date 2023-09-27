/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.notification.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Clob;
import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;CommerceNotificationQueueEntry&quot; database table.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceNotificationQueueEntry
 * @generated
 */
public class CommerceNotificationQueueEntryTable
	extends BaseTable<CommerceNotificationQueueEntryTable> {

	public static final CommerceNotificationQueueEntryTable INSTANCE =
		new CommerceNotificationQueueEntryTable();

	public final Column<CommerceNotificationQueueEntryTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<CommerceNotificationQueueEntryTable, Long>
		commerceNotificationQueueEntryId = createColumn(
			"CNotificationQueueEntryId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<CommerceNotificationQueueEntryTable, Long> groupId =
		createColumn("groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceNotificationQueueEntryTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceNotificationQueueEntryTable, Long> userId =
		createColumn("userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceNotificationQueueEntryTable, String> userName =
		createColumn(
			"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommerceNotificationQueueEntryTable, Date> createDate =
		createColumn(
			"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommerceNotificationQueueEntryTable, Date>
		modifiedDate = createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommerceNotificationQueueEntryTable, Long> classNameId =
		createColumn(
			"classNameId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceNotificationQueueEntryTable, Long> classPK =
		createColumn("classPK", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceNotificationQueueEntryTable, Long>
		commerceNotificationTemplateId = createColumn(
			"commerceNotificationTemplateId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<CommerceNotificationQueueEntryTable, String> from =
		createColumn("from_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommerceNotificationQueueEntryTable, String> fromName =
		createColumn(
			"fromName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommerceNotificationQueueEntryTable, String> to =
		createColumn("to_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommerceNotificationQueueEntryTable, String> toName =
		createColumn(
			"toName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommerceNotificationQueueEntryTable, String> cc =
		createColumn("cc", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommerceNotificationQueueEntryTable, String> bcc =
		createColumn("bcc", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommerceNotificationQueueEntryTable, String> subject =
		createColumn(
			"subject", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommerceNotificationQueueEntryTable, Clob> body =
		createColumn("body", Clob.class, Types.CLOB, Column.FLAG_DEFAULT);
	public final Column<CommerceNotificationQueueEntryTable, Double> priority =
		createColumn(
			"priority", Double.class, Types.DOUBLE, Column.FLAG_DEFAULT);
	public final Column<CommerceNotificationQueueEntryTable, Boolean> sent =
		createColumn("sent", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);
	public final Column<CommerceNotificationQueueEntryTable, Date> sentDate =
		createColumn(
			"sentDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);

	private CommerceNotificationQueueEntryTable() {
		super(
			"CommerceNotificationQueueEntry",
			CommerceNotificationQueueEntryTable::new);
	}

}