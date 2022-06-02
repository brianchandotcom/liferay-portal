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

package com.liferay.client.extension.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Clob;
import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;ClientExtensionEntry&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see ClientExtensionEntry
 * @generated
 */
public class ClientExtensionEntryTable
	extends BaseTable<ClientExtensionEntryTable> {

	public static final ClientExtensionEntryTable INSTANCE =
		new ClientExtensionEntryTable();

	public final Column<ClientExtensionEntryTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<ClientExtensionEntryTable, Long> ctCollectionId =
		createColumn(
			"ctCollectionId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<ClientExtensionEntryTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<ClientExtensionEntryTable, String>
		externalReferenceCode = createColumn(
			"externalReferenceCode", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<ClientExtensionEntryTable, Long>
		clientExtensionEntryId = createColumn(
			"clientExtensionEntryId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<ClientExtensionEntryTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<ClientExtensionEntryTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<ClientExtensionEntryTable, String> userName =
		createColumn(
			"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<ClientExtensionEntryTable, Date> createDate =
		createColumn(
			"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<ClientExtensionEntryTable, Date> modifiedDate =
		createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<ClientExtensionEntryTable, Clob> description =
		createColumn(
			"description", Clob.class, Types.CLOB, Column.FLAG_DEFAULT);
	public final Column<ClientExtensionEntryTable, String> name = createColumn(
		"name", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<ClientExtensionEntryTable, Clob> properties =
		createColumn("properties", Clob.class, Types.CLOB, Column.FLAG_DEFAULT);
	public final Column<ClientExtensionEntryTable, String> sourceCodeURL =
		createColumn(
			"sourceCodeURL", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<ClientExtensionEntryTable, String> type = createColumn(
		"type_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<ClientExtensionEntryTable, Clob> typeSettings =
		createColumn(
			"typeSettings", Clob.class, Types.CLOB, Column.FLAG_DEFAULT);
	public final Column<ClientExtensionEntryTable, Integer> status =
		createColumn(
			"status", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<ClientExtensionEntryTable, Long> statusByUserId =
		createColumn(
			"statusByUserId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<ClientExtensionEntryTable, String> statusByUserName =
		createColumn(
			"statusByUserName", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<ClientExtensionEntryTable, Date> statusDate =
		createColumn(
			"statusDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);

	private ClientExtensionEntryTable() {
		super("ClientExtensionEntry", ClientExtensionEntryTable::new);
	}

}