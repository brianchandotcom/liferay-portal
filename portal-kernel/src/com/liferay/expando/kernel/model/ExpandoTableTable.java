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

import java.sql.Types;

/**
 * The table class for the &quot;ExpandoTable&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see ExpandoTable
 * @generated
 */
public class ExpandoTableTable extends BaseTable<ExpandoTableTable> {

	public static final ExpandoTableTable INSTANCE = new ExpandoTableTable();

	public final Column<ExpandoTableTable, Long> tableId = createColumn(
		"tableId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<ExpandoTableTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<ExpandoTableTable, Long> classNameId = createColumn(
		"classNameId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<ExpandoTableTable, String> name = createColumn(
		"name", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);

	private ExpandoTableTable() {
		super("ExpandoTable", ExpandoTableTable::new);
	}

}