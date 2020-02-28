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

package com.liferay.trash.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Clob;
import java.sql.Types;

/**
 * The table class for the &quot;TrashVersion&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see TrashVersion
 * @generated
 */
public class TrashVersionTable extends BaseTable<TrashVersionTable> {

	public static final TrashVersionTable INSTANCE = new TrashVersionTable();

	public final Column<TrashVersionTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<TrashVersionTable, Long> versionId = createColumn(
		"versionId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<TrashVersionTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<TrashVersionTable, Long> entryId = createColumn(
		"entryId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<TrashVersionTable, Long> classNameId = createColumn(
		"classNameId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<TrashVersionTable, Long> classPK = createColumn(
		"classPK", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<TrashVersionTable, Clob> typeSettings = createColumn(
		"typeSettings", Clob.class, Types.CLOB, Column.FLAG_DEFAULT);
	public final Column<TrashVersionTable, Integer> status = createColumn(
		"status", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);

	private TrashVersionTable() {
		super("TrashVersion", TrashVersionTable::new);
	}

}