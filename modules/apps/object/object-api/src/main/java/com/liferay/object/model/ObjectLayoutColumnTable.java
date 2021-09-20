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
 * The table class for the &quot;ObjectLayoutColumn&quot; database table.
 *
 * @author Marco Leo
 * @see ObjectLayoutColumn
 * @generated
 */
public class ObjectLayoutColumnTable
	extends BaseTable<ObjectLayoutColumnTable> {

	public static final ObjectLayoutColumnTable INSTANCE =
		new ObjectLayoutColumnTable();

	public final Column<ObjectLayoutColumnTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<ObjectLayoutColumnTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<ObjectLayoutColumnTable, Long> objectLayoutColumnId =
		createColumn(
			"objectLayoutColumnId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<ObjectLayoutColumnTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<ObjectLayoutColumnTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<ObjectLayoutColumnTable, String> userName =
		createColumn(
			"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<ObjectLayoutColumnTable, Date> createDate =
		createColumn(
			"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<ObjectLayoutColumnTable, Date> modifiedDate =
		createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<ObjectLayoutColumnTable, Long> objectFieldId =
		createColumn(
			"objectFieldId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<ObjectLayoutColumnTable, Long> objectLayoutRowId =
		createColumn(
			"objectLayoutRowId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<ObjectLayoutColumnTable, Integer> priority =
		createColumn(
			"priority", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<ObjectLayoutColumnTable, Integer> size = createColumn(
		"size_", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);

	private ObjectLayoutColumnTable() {
		super("ObjectLayoutColumn", ObjectLayoutColumnTable::new);
	}

}