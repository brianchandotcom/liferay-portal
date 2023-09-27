/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.data.engine.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Clob;
import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;DEDataListView&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see DEDataListView
 * @generated
 */
public class DEDataListViewTable extends BaseTable<DEDataListViewTable> {

	public static final DEDataListViewTable INSTANCE =
		new DEDataListViewTable();

	public final Column<DEDataListViewTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<DEDataListViewTable, Long> ctCollectionId =
		createColumn(
			"ctCollectionId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<DEDataListViewTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<DEDataListViewTable, Long> deDataListViewId =
		createColumn(
			"deDataListViewId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<DEDataListViewTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<DEDataListViewTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<DEDataListViewTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<DEDataListViewTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<DEDataListViewTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<DEDataListViewTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<DEDataListViewTable, Clob> appliedFilters =
		createColumn(
			"appliedFilters", Clob.class, Types.CLOB, Column.FLAG_DEFAULT);
	public final Column<DEDataListViewTable, Long> ddmStructureId =
		createColumn(
			"ddmStructureId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<DEDataListViewTable, Clob> fieldNames = createColumn(
		"fieldNames", Clob.class, Types.CLOB, Column.FLAG_DEFAULT);
	public final Column<DEDataListViewTable, String> name = createColumn(
		"name", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<DEDataListViewTable, String> sortField = createColumn(
		"sortField", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);

	private DEDataListViewTable() {
		super("DEDataListView", DEDataListViewTable::new);
	}

}