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

package com.liferay.portal.workflow.kaleo.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Clob;
import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;KaleoNotificationRecipient&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see KaleoNotificationRecipient
 * @generated
 */
public class KaleoNotificationRecipientTable
	extends BaseTable<KaleoNotificationRecipientTable> {

	public static final KaleoNotificationRecipientTable INSTANCE =
		new KaleoNotificationRecipientTable();

	public final Column<KaleoNotificationRecipientTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<KaleoNotificationRecipientTable, Long> ctCollectionId =
		createColumn(
			"ctCollectionId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<KaleoNotificationRecipientTable, Long>
		kaleoNotificationRecipientId = createColumn(
			"kaleoNotificationRecipientId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<KaleoNotificationRecipientTable, Long> groupId =
		createColumn("groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<KaleoNotificationRecipientTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<KaleoNotificationRecipientTable, Long> userId =
		createColumn("userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<KaleoNotificationRecipientTable, String> userName =
		createColumn(
			"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<KaleoNotificationRecipientTable, Date> createDate =
		createColumn(
			"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<KaleoNotificationRecipientTable, Date> modifiedDate =
		createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<KaleoNotificationRecipientTable, Long>
		kaleoDefinitionId = createColumn(
			"kaleoDefinitionId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<KaleoNotificationRecipientTable, Long>
		kaleoDefinitionVersionId = createColumn(
			"kaleoDefinitionVersionId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<KaleoNotificationRecipientTable, Long>
		kaleoNotificationId = createColumn(
			"kaleoNotificationId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<KaleoNotificationRecipientTable, String>
		recipientClassName = createColumn(
			"recipientClassName", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<KaleoNotificationRecipientTable, Long>
		recipientClassPK = createColumn(
			"recipientClassPK", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<KaleoNotificationRecipientTable, Integer>
		recipientRoleType = createColumn(
			"recipientRoleType", Integer.class, Types.INTEGER,
			Column.FLAG_DEFAULT);
	public final Column<KaleoNotificationRecipientTable, Clob> recipientScript =
		createColumn(
			"recipientScript", Clob.class, Types.CLOB, Column.FLAG_DEFAULT);
	public final Column<KaleoNotificationRecipientTable, String>
		recipientScriptLanguage = createColumn(
			"recipientScriptLanguage", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<KaleoNotificationRecipientTable, String>
		recipientScriptContexts = createColumn(
			"recipientScriptContexts", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<KaleoNotificationRecipientTable, String> address =
		createColumn(
			"address", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<KaleoNotificationRecipientTable, String>
		notificationReceptionType = createColumn(
			"notificationReceptionType", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);

	private KaleoNotificationRecipientTable() {
		super(
			"KaleoNotificationRecipient", KaleoNotificationRecipientTable::new);
	}

}