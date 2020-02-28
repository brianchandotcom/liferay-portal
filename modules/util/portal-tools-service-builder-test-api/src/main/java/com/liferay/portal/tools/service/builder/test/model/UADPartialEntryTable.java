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

/**
 * The table class for the &quot;UADPartialEntry&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see UADPartialEntry
 * @generated
 */
public class UADPartialEntryTable extends BaseTable<UADPartialEntryTable> {

	public static final UADPartialEntryTable INSTANCE =
		new UADPartialEntryTable();

	public final Column<UADPartialEntryTable, Long> uadPartialEntryId =
		createColumn(
			"uadPartialEntryId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<UADPartialEntryTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<UADPartialEntryTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<UADPartialEntryTable, String> message = createColumn(
		"message", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);

	private UADPartialEntryTable() {
		super("UADPartialEntry", UADPartialEntryTable::new);
	}

}