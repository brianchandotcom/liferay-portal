/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;DynamicQueryEntry&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see DynamicQueryEntry
 * @generated
 */
public class DynamicQueryEntryTable extends BaseTable<DynamicQueryEntryTable> {

	public static final DynamicQueryEntryTable INSTANCE =
		new DynamicQueryEntryTable();

	public final Column<DynamicQueryEntryTable, Long> dynamicQueryEntryId =
		createColumn(
			"dynamicQueryEntryId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<DynamicQueryEntryTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<DynamicQueryEntryTable, Date> modifiedDate =
		createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<DynamicQueryEntryTable, Long> amount = createColumn(
		"amount", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<DynamicQueryEntryTable, String> description =
		createColumn(
			"description", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<DynamicQueryEntryTable, String> name = createColumn(
		"name", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<DynamicQueryEntryTable, Integer> status = createColumn(
		"status", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);

	private DynamicQueryEntryTable() {
		super("DynamicQueryEntry", DynamicQueryEntryTable::new);
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:-387088328