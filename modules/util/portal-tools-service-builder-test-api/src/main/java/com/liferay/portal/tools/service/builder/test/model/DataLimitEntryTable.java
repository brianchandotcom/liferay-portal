/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;DataLimitEntry&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see DataLimitEntry
 * @generated
 */
public class DataLimitEntryTable extends BaseTable<DataLimitEntryTable> {

	public static final DataLimitEntryTable INSTANCE =
		new DataLimitEntryTable();

	public final Column<DataLimitEntryTable, Long> dataLimitEntryId =
		createColumn(
			"dataLimitEntryId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<DataLimitEntryTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<DataLimitEntryTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<DataLimitEntryTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<DataLimitEntryTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<DataLimitEntryTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);

	private DataLimitEntryTable() {
		super("DataLimitEntry", DataLimitEntryTable::new);
	}

}