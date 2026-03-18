/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.compat740.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

/**
 * The table class for the &quot;ArrayableEntry&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see ArrayableEntry
 * @generated
 */
public class ArrayableEntryTable extends BaseTable<ArrayableEntryTable> {

	public static final ArrayableEntryTable INSTANCE =
		new ArrayableEntryTable();

	public final Column<ArrayableEntryTable, Long> arrayableEntryId =
		createColumn(
			"arrayableEntryId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<ArrayableEntryTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<ArrayableEntryTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<ArrayableEntryTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<ArrayableEntryTable, Integer> integer = createColumn(
		"integer_", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<ArrayableEntryTable, String> name = createColumn(
		"name", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<ArrayableEntryTable, String> type = createColumn(
		"type_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);

	private ArrayableEntryTable() {
		super("ArrayableEntry", ArrayableEntryTable::new);
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:-1120492133