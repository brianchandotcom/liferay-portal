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
 * The table class for the &quot;ObjectLayoutBox&quot; database table.
 *
 * @author Marco Leo
 * @see ObjectLayoutBox
 * @generated
 */
public class ObjectLayoutBoxTable extends BaseTable<ObjectLayoutBoxTable> {

	public static final ObjectLayoutBoxTable INSTANCE =
		new ObjectLayoutBoxTable();

	public final Column<ObjectLayoutBoxTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<ObjectLayoutBoxTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<ObjectLayoutBoxTable, Long> objectLayoutBoxId =
		createColumn(
			"objectLayoutBoxId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<ObjectLayoutBoxTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<ObjectLayoutBoxTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<ObjectLayoutBoxTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<ObjectLayoutBoxTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<ObjectLayoutBoxTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<ObjectLayoutBoxTable, Long> objectLayoutTabId =
		createColumn(
			"objectLayoutTabId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<ObjectLayoutBoxTable, Boolean> collapsable =
		createColumn(
			"collapsable", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);
	public final Column<ObjectLayoutBoxTable, String> name = createColumn(
		"name", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<ObjectLayoutBoxTable, Integer> order = createColumn(
		"order_", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);

	private ObjectLayoutBoxTable() {
		super("ObjectLayoutBox", ObjectLayoutBoxTable::new);
	}

}