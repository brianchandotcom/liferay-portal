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

package com.liferay.portal.tools.service.builder.test.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;DSLQueryStatusEntry&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see DSLQueryStatusEntry
 * @generated
 */
public class DSLQueryStatusEntryTable
	extends BaseTable<DSLQueryStatusEntryTable> {

	public static final DSLQueryStatusEntryTable INSTANCE =
		new DSLQueryStatusEntryTable();

	public final Column<DSLQueryStatusEntryTable, Long> dslQueryStatusEntryId =
		createColumn(
			"dslQueryStatusEntryId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<DSLQueryStatusEntryTable, Long> dslQueryEntryId =
		createColumn(
			"dslQueryEntryId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<DSLQueryStatusEntryTable, String> status = createColumn(
		"status", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<DSLQueryStatusEntryTable, Date> statusDate =
		createColumn(
			"statusDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);

	private DSLQueryStatusEntryTable() {
		super("DSLQueryStatusEntry", DSLQueryStatusEntryTable::new);
	}

}