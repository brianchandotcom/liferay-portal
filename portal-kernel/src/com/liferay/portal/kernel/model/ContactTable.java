/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;Contact_&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see Contact
 * @generated
 */
public class ContactTable extends BaseTable<ContactTable> {

	public static final ContactTable INSTANCE = new ContactTable();

	public final Column<ContactTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<ContactTable, Long> contactId = createColumn(
		"contactId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<ContactTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<ContactTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<ContactTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<ContactTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<ContactTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<ContactTable, Long> classNameId = createColumn(
		"classNameId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<ContactTable, Long> classPK = createColumn(
		"classPK", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<ContactTable, Long> parentContactId = createColumn(
		"parentContactId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<ContactTable, String> emailAddress = createColumn(
		"emailAddress", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<ContactTable, String> firstName = createColumn(
		"firstName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<ContactTable, String> middleName = createColumn(
		"middleName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<ContactTable, String> lastName = createColumn(
		"lastName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<ContactTable, Long> prefixListTypeId = createColumn(
		"prefixListTypeId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<ContactTable, Long> suffixListTypeId = createColumn(
		"suffixListTypeId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<ContactTable, Boolean> male = createColumn(
		"male", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);
	public final Column<ContactTable, Date> birthday = createColumn(
		"birthday", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<ContactTable, String> smsSn = createColumn(
		"smsSn", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<ContactTable, String> facebookSn = createColumn(
		"facebookSn", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<ContactTable, String> jabberSn = createColumn(
		"jabberSn", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<ContactTable, String> skypeSn = createColumn(
		"skypeSn", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<ContactTable, String> twitterSn = createColumn(
		"twitterSn", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<ContactTable, String> employeeStatusId = createColumn(
		"employeeStatusId", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<ContactTable, String> employeeNumber = createColumn(
		"employeeNumber", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<ContactTable, String> jobTitle = createColumn(
		"jobTitle", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<ContactTable, String> jobClass = createColumn(
		"jobClass", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<ContactTable, String> hoursOfOperation = createColumn(
		"hoursOfOperation", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);

	private ContactTable() {
		super("Contact_", ContactTable::new);
	}

}