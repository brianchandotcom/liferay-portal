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
 * The table class for the &quot;ObjectLayoutBox&quot; database table.
 *
 * @author Marco Leo
 * @see ObjectLayoutBox
 * @generated
 */
public class ObjectLayoutBoxTable extends BaseTable<ObjectLayoutBoxTable> {

	public static final ObjectLayoutBoxTable INSTANCE =
		new ObjectLayoutBoxTable();

	public final Column<ObjectLayoutBoxTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<ObjectLayoutBoxTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<ObjectLayoutBoxTable, Long> objectLayoutBoxId =
		createColumn(
			"objectLayoutBoxId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<ObjectLayoutBoxTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<ObjectLayoutBoxTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<ObjectLayoutBoxTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<ObjectLayoutBoxTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<ObjectLayoutBoxTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<ObjectLayoutBoxTable, Long> objectLayoutTabId =
		createColumn(
			"objectLayoutTabId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<ObjectLayoutBoxTable, Boolean> collapsable =
		createColumn(
			"collapsable", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);
	public final Column<ObjectLayoutBoxTable, String> name = createColumn(
		"name", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<ObjectLayoutBoxTable, Integer> priority = createColumn(
		"priority", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<ObjectLayoutBoxTable, String> type = createColumn(
		"type_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);

	private ObjectLayoutBoxTable() {
		super("ObjectLayoutBox", ObjectLayoutBoxTable::new);
	}

}