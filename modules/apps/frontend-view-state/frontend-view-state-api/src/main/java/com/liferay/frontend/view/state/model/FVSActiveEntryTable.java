/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.view.state.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;FVSActiveEntry&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see FVSActiveEntry
 * @generated
 */
public class FVSActiveEntryTable extends BaseTable<FVSActiveEntryTable> {

	public static final FVSActiveEntryTable INSTANCE =
		new FVSActiveEntryTable();

	public final Column<FVSActiveEntryTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<FVSActiveEntryTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<FVSActiveEntryTable, Long> fvsActiveEntryId =
		createColumn(
			"fvsActiveEntryId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<FVSActiveEntryTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<FVSActiveEntryTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<FVSActiveEntryTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<FVSActiveEntryTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<FVSActiveEntryTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<FVSActiveEntryTable, Long> fvsEntryId = createColumn(
		"fvsEntryId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<FVSActiveEntryTable, String> clayDataSetDisplayId =
		createColumn(
			"clayDataSetDisplayId", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<FVSActiveEntryTable, Long> plid = createColumn(
		"plid", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<FVSActiveEntryTable, String> portletId = createColumn(
		"portletId", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);

	private FVSActiveEntryTable() {
		super("FVSActiveEntry", FVSActiveEntryTable::new);
	}

}