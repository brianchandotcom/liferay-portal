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
 * The table class for the &quot;CommerceTerm&quot; database table.
 *
 * @author Luca Pellizzon
 * @see CommerceTerm
 * @generated
 */
public class CommerceTermTable extends BaseTable<CommerceTermTable> {

	public static final CommerceTermTable INSTANCE = new CommerceTermTable();

	public final Column<CommerceTermTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<CommerceTermTable, String> externalReferenceCode =
		createColumn(
			"externalReferenceCode", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<CommerceTermTable, Long> commerceTermId = createColumn(
		"commerceTermId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<CommerceTermTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceTermTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceTermTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommerceTermTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommerceTermTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommerceTermTable, Boolean> active = createColumn(
		"active_", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);
	public final Column<CommerceTermTable, String> description = createColumn(
		"description", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommerceTermTable, Date> displayDate = createColumn(
		"displayDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommerceTermTable, Date> expirationDate = createColumn(
		"expirationDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommerceTermTable, String> label = createColumn(
		"label", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommerceTermTable, String> name = createColumn(
		"name", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommerceTermTable, Double> priority = createColumn(
		"priority", Double.class, Types.DOUBLE, Column.FLAG_DEFAULT);
	public final Column<CommerceTermTable, String> type = createColumn(
		"type_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommerceTermTable, String> typeSettings = createColumn(
		"typeSettings", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommerceTermTable, Date> lastPublishDate = createColumn(
		"lastPublishDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommerceTermTable, Integer> status = createColumn(
		"status", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<CommerceTermTable, Long> statusByUserId = createColumn(
		"statusByUserId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceTermTable, String> statusByUserName =
		createColumn(
			"statusByUserName", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<CommerceTermTable, Date> statusDate = createColumn(
		"statusDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);

	private CommerceTermTable() {
		super("CommerceTerm", CommerceTermTable::new);
	}

}