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
 * The table class for the &quot;ObjectActionEntry&quot; database table.
 *
 * @author Marco Leo
 * @see ObjectActionEntry
 * @generated
 */
public class ObjectActionEntryTable extends BaseTable<ObjectActionEntryTable> {

	public static final ObjectActionEntryTable INSTANCE =
		new ObjectActionEntryTable();

	public final Column<ObjectActionEntryTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<ObjectActionEntryTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<ObjectActionEntryTable, Long> objectActionEntryId =
		createColumn(
			"objectActionEntryId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<ObjectActionEntryTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<ObjectActionEntryTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<ObjectActionEntryTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<ObjectActionEntryTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<ObjectActionEntryTable, Date> modifiedDate =
		createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<ObjectActionEntryTable, Long> objectDefinitionId =
		createColumn(
			"objectDefinitionId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<ObjectActionEntryTable, Boolean> active = createColumn(
		"active_", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);
	public final Column<ObjectActionEntryTable, String> triggerName =
		createColumn(
			"triggerName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<ObjectActionEntryTable, String> name = createColumn(
		"name", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<ObjectActionEntryTable, String> settings = createColumn(
		"settings_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);

	private ObjectActionEntryTable() {
		super("ObjectActionEntry", ObjectActionEntryTable::new);
	}

}