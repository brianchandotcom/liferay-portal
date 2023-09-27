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
 * The table class for the &quot;CommerceNotificationTemplate&quot; database table.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceNotificationTemplate
 * @generated
 */
public class CommerceNotificationTemplateTable
	extends BaseTable<CommerceNotificationTemplateTable> {

	public static final CommerceNotificationTemplateTable INSTANCE =
		new CommerceNotificationTemplateTable();

	public final Column<CommerceNotificationTemplateTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<CommerceNotificationTemplateTable, String> uuid =
		createColumn("uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommerceNotificationTemplateTable, Long>
		commerceNotificationTemplateId = createColumn(
			"commerceNotificationTemplateId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<CommerceNotificationTemplateTable, Long> groupId =
		createColumn("groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceNotificationTemplateTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceNotificationTemplateTable, Long> userId =
		createColumn("userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceNotificationTemplateTable, String> userName =
		createColumn(
			"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommerceNotificationTemplateTable, Date> createDate =
		createColumn(
			"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommerceNotificationTemplateTable, Date> modifiedDate =
		createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommerceNotificationTemplateTable, String> name =
		createColumn("name", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommerceNotificationTemplateTable, String> description =
		createColumn(
			"description", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommerceNotificationTemplateTable, Clob> from =
		createColumn("from_", Clob.class, Types.CLOB, Column.FLAG_DEFAULT);
	public final Column<CommerceNotificationTemplateTable, String> fromName =
		createColumn(
			"fromName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommerceNotificationTemplateTable, Clob> to =
		createColumn("to_", Clob.class, Types.CLOB, Column.FLAG_DEFAULT);
	public final Column<CommerceNotificationTemplateTable, Clob> cc =
		createColumn("cc", Clob.class, Types.CLOB, Column.FLAG_DEFAULT);
	public final Column<CommerceNotificationTemplateTable, Clob> bcc =
		createColumn("bcc", Clob.class, Types.CLOB, Column.FLAG_DEFAULT);
	public final Column<CommerceNotificationTemplateTable, String> type =
		createColumn("type_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommerceNotificationTemplateTable, Boolean> enabled =
		createColumn(
			"enabled", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);
	public final Column<CommerceNotificationTemplateTable, String> subject =
		createColumn(
			"subject", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommerceNotificationTemplateTable, Clob> body =
		createColumn("body", Clob.class, Types.CLOB, Column.FLAG_DEFAULT);

	private CommerceNotificationTemplateTable() {
		super(
			"CommerceNotificationTemplate",
			CommerceNotificationTemplateTable::new);
	}

}