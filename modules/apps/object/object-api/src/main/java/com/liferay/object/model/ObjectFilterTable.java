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
 * The table class for the &quot;ObjectFilter&quot; database table.
 *
 * @author Marco Leo
 * @see ObjectFilter
 * @generated
 */
public class ObjectFilterTable extends BaseTable<ObjectFilterTable> {

	public static final ObjectFilterTable INSTANCE = new ObjectFilterTable();

	public final Column<ObjectFilterTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<ObjectFilterTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<ObjectFilterTable, Long> objectFilterId = createColumn(
		"objectFilterId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<ObjectFilterTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<ObjectFilterTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<ObjectFilterTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<ObjectFilterTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<ObjectFilterTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<ObjectFilterTable, Long> objectFieldId = createColumn(
		"objectFieldId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<ObjectFilterTable, String> filterBy = createColumn(
		"filterBy", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<ObjectFilterTable, String> filterType = createColumn(
		"filterType", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<ObjectFilterTable, String> json = createColumn(
		"json", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);

	private ObjectFilterTable() {
		super("ObjectFilter", ObjectFilterTable::new);
	}

}