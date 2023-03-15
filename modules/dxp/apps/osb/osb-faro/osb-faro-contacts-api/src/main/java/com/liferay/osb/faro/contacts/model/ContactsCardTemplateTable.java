/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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