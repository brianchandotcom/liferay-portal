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

package com.liferay.expando.kernel.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Clob;
import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;ExpandoColumn&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see ExpandoColumn
 * @generated
 */
public class ExpandoColumnTable extends BaseTable<ExpandoColumnTable> {

	public static final ExpandoColumnTable INSTANCE = new ExpandoColumnTable();

	public final Column<ExpandoColumnTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<ExpandoColumnTable, Long> ctCollectionId = createColumn(
		"ctCollectionId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<ExpandoColumnTable, Long> columnId = createColumn(
		"columnId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<ExpandoColumnTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<ExpandoColumnTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<ExpandoColumnTable, Long> tableId = createColumn(
		"tableId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<ExpandoColumnTable, String> name = createColumn(
		"name", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<ExpandoColumnTable, Integer> type = createColumn(
		"type_", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<ExpandoColumnTable, Clob> defaultData = createColumn(
		"defaultData", Clob.class, Types.CLOB, Column.FLAG_DEFAULT);
	public final Column<ExpandoColumnTable, Clob> typeSettings = createColumn(
		"typeSettings", Clob.class, Types.CLOB, Column.FLAG_DEFAULT);

	private ExpandoColumnTable() {
		super("ExpandoColumn", ExpandoColumnTable::new);
	}

}