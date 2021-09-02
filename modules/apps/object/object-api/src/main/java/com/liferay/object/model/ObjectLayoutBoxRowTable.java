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

package com.liferay.object.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;ObjectLayoutBoxRow&quot; database table.
 *
 * @author Marco Leo
 * @see ObjectLayoutBoxRow
 * @generated
 */
public class ObjectLayoutBoxRowTable
	extends BaseTable<ObjectLayoutBoxRowTable> {

	public static final ObjectLayoutBoxRowTable INSTANCE =
		new ObjectLayoutBoxRowTable();

	public final Column<ObjectLayoutBoxRowTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<ObjectLayoutBoxRowTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<ObjectLayoutBoxRowTable, Long> objectLayoutBoxRowId =
		createColumn(
			"objectLayoutBoxRowId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<ObjectLayoutBoxRowTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<ObjectLayoutBoxRowTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<ObjectLayoutBoxRowTable, String> userName =
		createColumn(
			"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<ObjectLayoutBoxRowTable, Date> createDate =
		createColumn(
			"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<ObjectLayoutBoxRowTable, Date> modifiedDate =
		createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<ObjectLayoutBoxRowTable, Long> objectLayoutBoxId =
		createColumn(
			"objectLayoutBoxId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<ObjectLayoutBoxRowTable, Integer> order = createColumn(
		"order_", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);

	private ObjectLayoutBoxRowTable() {
		super("ObjectLayoutBoxRow", ObjectLayoutBoxRowTable::new);
	}

}