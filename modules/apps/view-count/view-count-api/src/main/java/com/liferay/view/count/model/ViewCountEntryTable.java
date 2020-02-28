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

package com.liferay.view.count.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

/**
 * The table class for the &quot;ViewCountEntry&quot; database table.
 *
 * @author Preston Crary
 * @see ViewCountEntry
 * @generated
 */
public class ViewCountEntryTable extends BaseTable<ViewCountEntryTable> {

	public static final ViewCountEntryTable INSTANCE =
		new ViewCountEntryTable();

	public final Column<ViewCountEntryTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<ViewCountEntryTable, Long> classNameId = createColumn(
		"classNameId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<ViewCountEntryTable, Long> classPK = createColumn(
		"classPK", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<ViewCountEntryTable, Long> viewCount = createColumn(
		"viewCount", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);

	private ViewCountEntryTable() {
		super("ViewCountEntry", ViewCountEntryTable::new);
	}

}