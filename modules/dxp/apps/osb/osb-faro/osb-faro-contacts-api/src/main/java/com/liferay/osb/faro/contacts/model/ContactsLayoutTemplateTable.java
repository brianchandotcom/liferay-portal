/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.contacts.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

/**
 * The table class for the &quot;OSBFaro_ContactsLayoutTemplate&quot; database table.
 *
 * @author Shinn Lok
 * @see ContactsLayoutTemplate
 * @generated
 */
public class ContactsLayoutTemplateTable
	extends BaseTable<ContactsLayoutTemplateTable> {

	public static final ContactsLayoutTemplateTable INSTANCE =
		new ContactsLayoutTemplateTable();

	public final Column<ContactsLayoutTemplateTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<ContactsLayoutTemplateTable, Long>
		contactsLayoutTemplateId = createColumn(
			"contactsLayoutTemplateId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<ContactsLayoutTemplateTable, Long> groupId =
		createColumn("groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<ContactsLayoutTemplateTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<ContactsLayoutTemplateTable, Long> createTime =
		createColumn(
			"createTime", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<ContactsLayoutTemplateTable, Long> userId =
		createColumn("userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<ContactsLayoutTemplateTable, String> userName =
		createColumn(
			"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<ContactsLayoutTemplateTable, Long> modifiedTime =
		createColumn(
			"modifiedTime", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<ContactsLayoutTemplateTable, String>
		headerContactsCardTemplateIds = createColumn(
			"headerContactsCardTemplateIds", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<ContactsLayoutTemplateTable, String> name =
		createColumn("name", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<ContactsLayoutTemplateTable, String> settings =
		createColumn(
			"settings_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<ContactsLayoutTemplateTable, Integer> type =
		createColumn(
			"type_", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);

	private ContactsLayoutTemplateTable() {
		super(
			"OSBFaro_ContactsLayoutTemplate", ContactsLayoutTemplateTable::new);
	}

}