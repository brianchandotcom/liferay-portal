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
 * The table class for the &quot;ObjectStateTransition&quot; database table.
 *
 * @author Marco Leo
 * @see ObjectStateTransition
 * @generated
 */
public class ObjectStateTransitionTable
	extends BaseTable<ObjectStateTransitionTable> {

	public static final ObjectStateTransitionTable INSTANCE =
		new ObjectStateTransitionTable();

	public final Column<ObjectStateTransitionTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<ObjectStateTransitionTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<ObjectStateTransitionTable, Long>
		objectStateTransitionId = createColumn(
			"objectStateTransitionId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<ObjectStateTransitionTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<ObjectStateTransitionTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<ObjectStateTransitionTable, String> userName =
		createColumn(
			"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<ObjectStateTransitionTable, Date> createDate =
		createColumn(
			"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<ObjectStateTransitionTable, Date> modifiedDate =
		createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<ObjectStateTransitionTable, Long> objectStateFlowId =
		createColumn(
			"objectStateFlowId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<ObjectStateTransitionTable, Long> sourceObjectStateId =
		createColumn(
			"sourceObjectStateId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<ObjectStateTransitionTable, Long> targetObjectStateId =
		createColumn(
			"targetObjectStateId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);

	private ObjectStateTransitionTable() {
		super("ObjectStateTransition", ObjectStateTransitionTable::new);
	}

}