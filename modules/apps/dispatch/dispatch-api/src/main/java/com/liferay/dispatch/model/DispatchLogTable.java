/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dispatch.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Clob;
import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;DispatchLog&quot; database table.
 *
 * @author Matija Petanjek
 * @see DispatchLog
 * @generated
 */
public class DispatchLogTable extends BaseTable<DispatchLogTable> {

	public static final DispatchLogTable INSTANCE = new DispatchLogTable();

	public final Column<DispatchLogTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<DispatchLogTable, Long> dispatchLogId = createColumn(
		"dispatchLogId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<DispatchLogTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<DispatchLogTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<DispatchLogTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<DispatchLogTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<DispatchLogTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<DispatchLogTable, Long> dispatchTriggerId =
		createColumn(
			"dispatchTriggerId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<DispatchLogTable, Date> endDate = createColumn(
		"endDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<DispatchLogTable, Clob> error = createColumn(
		"error", Clob.class, Types.CLOB, Column.FLAG_DEFAULT);
	public final Column<DispatchLogTable, Clob> output = createColumn(
		"output_", Clob.class, Types.CLOB, Column.FLAG_DEFAULT);
	public final Column<DispatchLogTable, Date> startDate = createColumn(
		"startDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<DispatchLogTable, Integer> status = createColumn(
		"status", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);

	private DispatchLogTable() {
		super("DispatchLog", DispatchLogTable::new);
	}

}