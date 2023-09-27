/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;ObjectViewSortColumn&quot; database table.
 *
 * @author Marco Leo
 * @see ObjectViewSortColumn
 * @generated
 */
public class ObjectViewSortColumnTable
	extends BaseTable<ObjectViewSortColumnTable> {

	public static final ObjectViewSortColumnTable INSTANCE =
		new ObjectViewSortColumnTable();

	public final Column<ObjectViewSortColumnTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<ObjectViewSortColumnTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<ObjectViewSortColumnTable, Long>
		objectViewSortColumnId = createColumn(
			"objectViewSortColumnId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<ObjectViewSortColumnTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<ObjectViewSortColumnTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<ObjectViewSortColumnTable, String> userName =
		createColumn(
			"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<ObjectViewSortColumnTable, Date> createDate =
		createColumn(
			"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<ObjectViewSortColumnTable, Date> modifiedDate =
		createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<ObjectViewSortColumnTable, Long> objectViewId =
		createColumn(
			"objectViewId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<ObjectViewSortColumnTable, String> objectFieldName =
		createColumn(
			"objectFieldName", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<ObjectViewSortColumnTable, Integer> priority =
		createColumn(
			"priority", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<ObjectViewSortColumnTable, String> sortOrder =
		createColumn(
			"sortOrder", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);

	private ObjectViewSortColumnTable() {
		super("ObjectViewSortColumn", ObjectViewSortColumnTable::new);
	}

}