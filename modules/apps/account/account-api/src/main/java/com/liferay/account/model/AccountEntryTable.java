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

package com.liferay.account.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.Table;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the AccountEntry.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class AccountEntryTable extends Table<AccountEntryTable> {

	public static final AccountEntryTable INSTANCE = new AccountEntryTable();

	public final Column<AccountEntryTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT);
	public final Column<AccountEntryTable, Long> accountEntryId = createColumn(
		"accountEntryId", Long.class, Types.BIGINT);
	public final Column<AccountEntryTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT);
	public final Column<AccountEntryTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT);
	public final Column<AccountEntryTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR);
	public final Column<AccountEntryTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP);
	public final Column<AccountEntryTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP);
	public final Column<AccountEntryTable, Long> parentAccountEntryId =
		createColumn("parentAccountEntryId", Long.class, Types.BIGINT);
	public final Column<AccountEntryTable, String> name = createColumn(
		"name", String.class, Types.VARCHAR);
	public final Column<AccountEntryTable, String> description = createColumn(
		"description", String.class, Types.VARCHAR);
	public final Column<AccountEntryTable, String> domains = createColumn(
		"domains", String.class, Types.VARCHAR);
	public final Column<AccountEntryTable, Long> logoId = createColumn(
		"logoId", Long.class, Types.BIGINT);
	public final Column<AccountEntryTable, Integer> status = createColumn(
		"status", Integer.class, Types.INTEGER);

	private AccountEntryTable() {
		super("AccountEntry", AccountEntryTable::new);
	}

}