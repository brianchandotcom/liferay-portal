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

package com.liferay.commerce.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;CommerceOrderNote&quot; database table.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceOrderNote
 * @generated
 */
public class CommerceOrderNoteTable extends BaseTable<CommerceOrderNoteTable> {

	public static final CommerceOrderNoteTable INSTANCE =
		new CommerceOrderNoteTable();

	public final Column<CommerceOrderNoteTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<CommerceOrderNoteTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommerceOrderNoteTable, String> externalReferenceCode =
		createColumn(
			"externalReferenceCode", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<CommerceOrderNoteTable, Long> commerceOrderNoteId =
		createColumn(
			"commerceOrderNoteId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<CommerceOrderNoteTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceOrderNoteTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceOrderNoteTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceOrderNoteTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommerceOrderNoteTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommerceOrderNoteTable, Date> modifiedDate =
		createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommerceOrderNoteTable, Long> commerceOrderId =
		createColumn(
			"commerceOrderId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceOrderNoteTable, String> content = createColumn(
		"content", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommerceOrderNoteTable, Boolean> restricted =
		createColumn(
			"restricted", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);

	private CommerceOrderNoteTable() {
		super("CommerceOrderNote", CommerceOrderNoteTable::new);
	}

}