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
 * The table class for the &quot;ObjectLayoutBoxColumn&quot; database table.
 *
 * @author Marco Leo
 * @see ObjectLayoutBoxColumn
 * @generated
 */
public class ObjectLayoutBoxColumnTable
	extends BaseTable<ObjectLayoutBoxColumnTable> {

	public static final ObjectLayoutBoxColumnTable INSTANCE =
		new ObjectLayoutBoxColumnTable();

	public final Column<ObjectLayoutBoxColumnTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<ObjectLayoutBoxColumnTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<ObjectLayoutBoxColumnTable, Long>
		objectLayoutBoxColumnId = createColumn(
			"objectLayoutBoxColumnId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<ObjectLayoutBoxColumnTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<ObjectLayoutBoxColumnTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<ObjectLayoutBoxColumnTable, String> userName =
		createColumn(
			"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<ObjectLayoutBoxColumnTable, Date> createDate =
		createColumn(
			"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<ObjectLayoutBoxColumnTable, Date> modifiedDate =
		createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<ObjectLayoutBoxColumnTable, Long> objectFieldId =
		createColumn(
			"objectFieldId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<ObjectLayoutBoxColumnTable, Long> objectLayoutBoxRowId =
		createColumn(
			"objectLayoutBoxRowId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<ObjectLayoutBoxColumnTable, Integer> order =
		createColumn(
			"order_", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);

	private ObjectLayoutBoxColumnTable() {
		super("ObjectLayoutBoxColumn", ObjectLayoutBoxColumnTable::new);
	}

}