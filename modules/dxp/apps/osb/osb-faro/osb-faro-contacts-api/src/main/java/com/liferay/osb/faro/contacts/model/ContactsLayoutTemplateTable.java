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