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
 * The table class for the &quot;ObjectState&quot; database table.
 *
 * @author Marco Leo
 * @see ObjectState
 * @generated
 */
public class ObjectStateTable extends BaseTable<ObjectStateTable> {

	public static final ObjectStateTable INSTANCE = new ObjectStateTable();

	public final Column<ObjectStateTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<ObjectStateTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<ObjectStateTable, Long> objectStateId = createColumn(
		"objectStateId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<ObjectStateTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<ObjectStateTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<ObjectStateTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<ObjectStateTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<ObjectStateTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<ObjectStateTable, Long> listTypeEntryId = createColumn(
		"listTypeEntryId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<ObjectStateTable, Long> objectStateFlowId =
		createColumn(
			"objectStateFlowId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);

	private ObjectStateTable() {
		super("ObjectState", ObjectStateTable::new);
	}

}