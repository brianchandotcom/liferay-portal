/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.contacts.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

/**
 * The table class for the &quot;OSBFaro_ContactsCardTemplate&quot; database table.
 *
 * @author Shinn Lok
 * @see ContactsCardTemplate
 * @generated
 */
public class ContactsCardTemplateTable
	extends BaseTable<ContactsCardTemplateTable> {

	public static final ContactsCardTemplateTable INSTANCE =
		new ContactsCardTemplateTable();

	public final Column<ContactsCardTemplateTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<ContactsCardTemplateTable, Long>
		contactsCardTemplateId = createColumn(
			"contactsCardTemplateId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<ContactsCardTemplateTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<ContactsCardTemplateTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<ContactsCardTemplateTable, Long> createTime =
		createColumn(
			"createTime", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<ContactsCardTemplateTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<ContactsCardTemplateTable, String> userName =
		createColumn(
			"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<ContactsCardTemplateTable, Long> modifiedTime =
		createColumn(
			"modifiedTime", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<ContactsCardTemplateTable, String> name = createColumn(
		"name", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<ContactsCardTemplateTable, String> settings =
		createColumn(
			"settings_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<ContactsCardTemplateTable, Integer> type = createColumn(
		"type_", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);

	private ContactsCardTemplateTable() {
		super("OSBFaro_ContactsCardTemplate", ContactsCardTemplateTable::new);
	}

}