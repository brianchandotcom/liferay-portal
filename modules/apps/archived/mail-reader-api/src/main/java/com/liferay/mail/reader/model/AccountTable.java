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

package com.liferay.mail.reader.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;Mail_Account&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see Account
 * @generated
 */
public class AccountTable extends BaseTable<AccountTable> {

	public static final AccountTable INSTANCE = new AccountTable();

	public final Column<AccountTable, Long> accountId = createColumn(
		"accountId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<AccountTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<AccountTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<AccountTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<AccountTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<AccountTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<AccountTable, String> address = createColumn(
		"address", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<AccountTable, String> personalName = createColumn(
		"personalName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<AccountTable, String> protocol = createColumn(
		"protocol", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<AccountTable, String> incomingHostName = createColumn(
		"incomingHostName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<AccountTable, Integer> incomingPort = createColumn(
		"incomingPort", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<AccountTable, Boolean> incomingSecure = createColumn(
		"incomingSecure", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);
	public final Column<AccountTable, String> outgoingHostName = createColumn(
		"outgoingHostName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<AccountTable, Integer> outgoingPort = createColumn(
		"outgoingPort", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<AccountTable, Boolean> outgoingSecure = createColumn(
		"outgoingSecure", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);
	public final Column<AccountTable, String> login = createColumn(
		"login", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<AccountTable, String> password = createColumn(
		"password_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<AccountTable, Boolean> savePassword = createColumn(
		"savePassword", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);
	public final Column<AccountTable, String> signature = createColumn(
		"signature", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<AccountTable, Boolean> useSignature = createColumn(
		"useSignature", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);
	public final Column<AccountTable, String> folderPrefix = createColumn(
		"folderPrefix", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<AccountTable, Long> inboxFolderId = createColumn(
		"inboxFolderId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<AccountTable, Long> draftFolderId = createColumn(
		"draftFolderId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<AccountTable, Long> sentFolderId = createColumn(
		"sentFolderId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<AccountTable, Long> trashFolderId = createColumn(
		"trashFolderId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<AccountTable, Boolean> defaultSender = createColumn(
		"defaultSender", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);

	private AccountTable() {
		super("Mail_Account", AccountTable::new);
	}

}