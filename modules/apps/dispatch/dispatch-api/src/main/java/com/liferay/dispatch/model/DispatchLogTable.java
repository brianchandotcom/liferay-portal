/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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
 * @author Alessio Antonio Rendina
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