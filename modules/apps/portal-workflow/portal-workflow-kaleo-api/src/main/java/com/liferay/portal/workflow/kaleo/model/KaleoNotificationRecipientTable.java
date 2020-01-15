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
import com.liferay.petra.sql.dsl.Table;

import java.sql.Clob;
import java.sql.Types;

import java.util.Date;

/**
 * The table class for the KaleoNotificationRecipient.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class KaleoNotificationRecipientTable
	extends Table<KaleoNotificationRecipientTable> {

	public static final KaleoNotificationRecipientTable INSTANCE =
		new KaleoNotificationRecipientTable();

	public final Column<KaleoNotificationRecipientTable, Long> mvccVersion =
		createColumn("mvccVersion", Long.class, Types.BIGINT);
	public final Column<KaleoNotificationRecipientTable, Long>
		kaleoNotificationRecipientId = createColumn(
			"kaleoNotificationRecipientId", Long.class, Types.BIGINT);
	public final Column<KaleoNotificationRecipientTable, Long> groupId =
		createColumn("groupId", Long.class, Types.BIGINT);
	public final Column<KaleoNotificationRecipientTable, Long> companyId =
		createColumn("companyId", Long.class, Types.BIGINT);
	public final Column<KaleoNotificationRecipientTable, Long> userId =
		createColumn("userId", Long.class, Types.BIGINT);
	public final Column<KaleoNotificationRecipientTable, String> userName =
		createColumn("userName", String.class, Types.VARCHAR);
	public final Column<KaleoNotificationRecipientTable, Date> createDate =
		createColumn("createDate", Date.class, Types.TIMESTAMP);
	public final Column<KaleoNotificationRecipientTable, Date> modifiedDate =
		createColumn("modifiedDate", Date.class, Types.TIMESTAMP);
	public final Column<KaleoNotificationRecipientTable, Long>
		kaleoDefinitionVersionId = createColumn(
			"kaleoDefinitionVersionId", Long.class, Types.BIGINT);
	public final Column<KaleoNotificationRecipientTable, Long>
		kaleoNotificationId = createColumn(
			"kaleoNotificationId", Long.class, Types.BIGINT);
	public final Column<KaleoNotificationRecipientTable, String>
		recipientClassName = createColumn(
			"recipientClassName", String.class, Types.VARCHAR);
	public final Column<KaleoNotificationRecipientTable, Long>
		recipientClassPK = createColumn(
			"recipientClassPK", Long.class, Types.BIGINT);
	public final Column<KaleoNotificationRecipientTable, Integer>
		recipientRoleType = createColumn(
			"recipientRoleType", Integer.class, Types.INTEGER);
	public final Column<KaleoNotificationRecipientTable, Clob> recipientScript =
		createColumn("recipientScript", Clob.class, Types.CLOB);
	public final Column<KaleoNotificationRecipientTable, String>
		recipientScriptLanguage = createColumn(
			"recipientScriptLanguage", String.class, Types.VARCHAR);
	public final Column<KaleoNotificationRecipientTable, String>
		recipientScriptContexts = createColumn(
			"recipientScriptContexts", String.class, Types.VARCHAR);
	public final Column<KaleoNotificationRecipientTable, String> address =
		createColumn("address", String.class, Types.VARCHAR);
	public final Column<KaleoNotificationRecipientTable, String>
		notificationReceptionType = createColumn(
			"notificationReceptionType", String.class, Types.VARCHAR);

	private KaleoNotificationRecipientTable() {
		super(
			"KaleoNotificationRecipient", KaleoNotificationRecipientTable::new);
	}

}