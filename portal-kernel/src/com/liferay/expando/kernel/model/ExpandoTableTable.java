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
import com.liferay.petra.sql.dsl.Table;

import java.sql.Types;

/**
 * The table class for the ExpandoTable.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class ExpandoTableTable extends Table<ExpandoTableTable> {

	public static final ExpandoTableTable INSTANCE = new ExpandoTableTable();

	public final Column<ExpandoTableTable, Long> tableId = createColumn(
		"tableId", Long.class, Types.BIGINT);
	public final Column<ExpandoTableTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT);
	public final Column<ExpandoTableTable, Long> classNameId = createColumn(
		"classNameId", Long.class, Types.BIGINT);
	public final Column<ExpandoTableTable, String> name = createColumn(
		"name", String.class, Types.VARCHAR);

	private ExpandoTableTable() {
		super("ExpandoTable", ExpandoTableTable::new);
	}

}