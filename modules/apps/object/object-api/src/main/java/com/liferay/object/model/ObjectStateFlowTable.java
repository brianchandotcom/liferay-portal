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
 * The table class for the &quot;ObjectStateFlow&quot; database table.
 *
 * @author Marco Leo
 * @see ObjectStateFlow
 * @generated
 */
public class ObjectStateFlowTable extends BaseTable<ObjectStateFlowTable> {

	public static final ObjectStateFlowTable INSTANCE =
		new ObjectStateFlowTable();

	public final Column<ObjectStateFlowTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<ObjectStateFlowTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<ObjectStateFlowTable, Long> objectStateFlowId =
		createColumn(
			"objectStateFlowId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<ObjectStateFlowTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<ObjectStateFlowTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<ObjectStateFlowTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<ObjectStateFlowTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<ObjectStateFlowTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<ObjectStateFlowTable, Long> objectFieldId =
		createColumn(
			"objectFieldId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);

	private ObjectStateFlowTable() {
		super("ObjectStateFlow", ObjectStateFlowTable::new);
	}

}