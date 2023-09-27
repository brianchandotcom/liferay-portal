/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Clob;
import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;ObjectViewFilterColumn&quot; database table.
 *
 * @author Marco Leo
 * @see ObjectViewFilterColumn
 * @generated
 */
public class ObjectViewFilterColumnTable
	extends BaseTable<ObjectViewFilterColumnTable> {

	public static final ObjectViewFilterColumnTable INSTANCE =
		new ObjectViewFilterColumnTable();

	public final Column<ObjectViewFilterColumnTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<ObjectViewFilterColumnTable, String> uuid =
		createColumn("uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<ObjectViewFilterColumnTable, Long>
		objectViewFilterColumnId = createColumn(
			"objectViewFilterColumnId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<ObjectViewFilterColumnTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<ObjectViewFilterColumnTable, Long> userId =
		createColumn("userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<ObjectViewFilterColumnTable, String> userName =
		createColumn(
			"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<ObjectViewFilterColumnTable, Date> createDate =
		createColumn(
			"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<ObjectViewFilterColumnTable, Date> modifiedDate =
		createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<ObjectViewFilterColumnTable, Long> objectViewId =
		createColumn(
			"objectViewId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<ObjectViewFilterColumnTable, String> filterType =
		createColumn(
			"filterType", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<ObjectViewFilterColumnTable, Clob> json = createColumn(
		"json", Clob.class, Types.CLOB, Column.FLAG_DEFAULT);
	public final Column<ObjectViewFilterColumnTable, String> objectFieldName =
		createColumn(
			"objectFieldName", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);

	private ObjectViewFilterColumnTable() {
		super("ObjectViewFilterColumn", ObjectViewFilterColumnTable::new);
	}

}