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
	public final Column<ContactTable, Long> prefixId = createColumn(
		"prefixId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<ContactTable, Long> suffixId = createColumn(
		"suffixId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
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