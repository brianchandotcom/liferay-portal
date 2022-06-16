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

package com.liferay.commerce.term.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;CommerceTermEntry&quot; database table.
 *
 * @author Luca Pellizzon
 * @see CommerceTermEntry
 * @generated
 */
public class CommerceTermEntryTable extends BaseTable<CommerceTermEntryTable> {

	public static final CommerceTermEntryTable INSTANCE =
		new CommerceTermEntryTable();

	public final Column<CommerceTermEntryTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<CommerceTermEntryTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommerceTermEntryTable, String> externalReferenceCode =
		createColumn(
			"externalReferenceCode", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<CommerceTermEntryTable, String> defaultLanguageId =
		createColumn(
			"defaultLanguageId", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<CommerceTermEntryTable, Long> commerceTermEntryId =
		createColumn(
			"commerceTermEntryId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<CommerceTermEntryTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceTermEntryTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceTermEntryTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommerceTermEntryTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommerceTermEntryTable, Date> modifiedDate =
		createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommerceTermEntryTable, Boolean> active = createColumn(
		"active_", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);
	public final Column<CommerceTermEntryTable, Date> displayDate =
		createColumn(
			"displayDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommerceTermEntryTable, Date> expirationDate =
		createColumn(
			"expirationDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommerceTermEntryTable, String> name = createColumn(
		"name", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommerceTermEntryTable, Double> priority = createColumn(
		"priority", Double.class, Types.DOUBLE, Column.FLAG_DEFAULT);
	public final Column<CommerceTermEntryTable, String> type = createColumn(
		"type_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommerceTermEntryTable, String> typeSettings =
		createColumn(
			"typeSettings", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommerceTermEntryTable, Date> lastPublishDate =
		createColumn(
			"lastPublishDate", Date.class, Types.TIMESTAMP,
			Column.FLAG_DEFAULT);
	public final Column<CommerceTermEntryTable, Integer> status = createColumn(
		"status", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<CommerceTermEntryTable, Long> statusByUserId =
		createColumn(
			"statusByUserId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceTermEntryTable, String> statusByUserName =
		createColumn(
			"statusByUserName", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<CommerceTermEntryTable, Date> statusDate = createColumn(
		"statusDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);

	private CommerceTermEntryTable() {
		super("CommerceTermEntry", CommerceTermEntryTable::new);
	}

}