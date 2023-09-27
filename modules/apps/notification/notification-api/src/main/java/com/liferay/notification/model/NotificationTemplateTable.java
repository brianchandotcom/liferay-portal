/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.notification.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Clob;
import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;NotificationTemplate&quot; database table.
 *
 * @author Gabriel Albuquerque
 * @see NotificationTemplate
 * @generated
 */
public class NotificationTemplateTable
	extends BaseTable<NotificationTemplateTable> {

	public static final NotificationTemplateTable INSTANCE =
		new NotificationTemplateTable();

	public final Column<NotificationTemplateTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<NotificationTemplateTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<NotificationTemplateTable, String>
		externalReferenceCode = createColumn(
			"externalReferenceCode", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<NotificationTemplateTable, Long>
		notificationTemplateId = createColumn(
			"notificationTemplateId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<NotificationTemplateTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<NotificationTemplateTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<NotificationTemplateTable, String> userName =
		createColumn(
			"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<NotificationTemplateTable, Date> createDate =
		createColumn(
			"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<NotificationTemplateTable, Date> modifiedDate =
		createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<NotificationTemplateTable, Long> objectDefinitionId =
		createColumn(
			"objectDefinitionId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<NotificationTemplateTable, Clob> body = createColumn(
		"body", Clob.class, Types.CLOB, Column.FLAG_DEFAULT);
	public final Column<NotificationTemplateTable, String> description =
		createColumn(
			"description", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<NotificationTemplateTable, String> editorType =
		createColumn(
			"editorType", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<NotificationTemplateTable, String> name = createColumn(
		"name", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<NotificationTemplateTable, String> recipientType =
		createColumn(
			"recipientType", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<NotificationTemplateTable, String> subject =
		createColumn(
			"subject", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<NotificationTemplateTable, String> type = createColumn(
		"type_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);

	private NotificationTemplateTable() {
		super("NotificationTemplate", NotificationTemplateTable::new);
	}

}