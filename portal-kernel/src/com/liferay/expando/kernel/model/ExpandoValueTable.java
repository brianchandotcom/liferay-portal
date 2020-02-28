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

/**
 * The table class for the &quot;ExpandoValue&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see ExpandoValue
 * @generated
 */
public class ExpandoValueTable extends BaseTable<ExpandoValueTable> {

	public static final ExpandoValueTable INSTANCE = new ExpandoValueTable();

	public final Column<ExpandoValueTable, Long> valueId = createColumn(
		"valueId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<ExpandoValueTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<ExpandoValueTable, Long> tableId = createColumn(
		"tableId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<ExpandoValueTable, Long> columnId = createColumn(
		"columnId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<ExpandoValueTable, Long> rowId = createColumn(
		"rowId_", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<ExpandoValueTable, Long> classNameId = createColumn(
		"classNameId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<ExpandoValueTable, Long> classPK = createColumn(
		"classPK", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<ExpandoValueTable, Clob> data = createColumn(
		"data_", Clob.class, Types.CLOB, Column.FLAG_DEFAULT);

	private ExpandoValueTable() {
		super("ExpandoValue", ExpandoValueTable::new);
	}

}