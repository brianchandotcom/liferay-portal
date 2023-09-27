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
 * The table class for the &quot;ObjectViewColumn&quot; database table.
 *
 * @author Marco Leo
 * @see ObjectViewColumn
 * @generated
 */
public class ObjectViewColumnTable extends BaseTable<ObjectViewColumnTable> {

	public static final ObjectViewColumnTable INSTANCE =
		new ObjectViewColumnTable();

	public final Column<ObjectViewColumnTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<ObjectViewColumnTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<ObjectViewColumnTable, Long> objectViewColumnId =
		createColumn(
			"objectViewColumnId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<ObjectViewColumnTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<ObjectViewColumnTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<ObjectViewColumnTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<ObjectViewColumnTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<ObjectViewColumnTable, Date> modifiedDate =
		createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<ObjectViewColumnTable, Long> objectViewId =
		createColumn(
			"objectViewId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<ObjectViewColumnTable, String> label = createColumn(
		"label", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<ObjectViewColumnTable, String> objectFieldName =
		createColumn(
			"objectFieldName", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<ObjectViewColumnTable, Integer> priority = createColumn(
		"priority", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);

	private ObjectViewColumnTable() {
		super("ObjectViewColumn", ObjectViewColumnTable::new);
	}

}