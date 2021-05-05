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

package com.liferay.batch.planner.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;BatchPlannerLog&quot; database table.
 *
 * @author Igor Beslic
 * @see BatchPlannerLog
 * @generated
 */
public class BatchPlannerLogTable extends BaseTable<BatchPlannerLogTable> {

	public static final BatchPlannerLogTable INSTANCE =
		new BatchPlannerLogTable();

	public final Column<BatchPlannerLogTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<BatchPlannerLogTable, Long> batchPlannerLogId =
		createColumn(
			"batchPlannerLogId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<BatchPlannerLogTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<BatchPlannerLogTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<BatchPlannerLogTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<BatchPlannerLogTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<BatchPlannerLogTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<BatchPlannerLogTable, Long> batchPlannerPlanId =
		createColumn(
			"batchPlannerPlanId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<BatchPlannerLogTable, String> batchEngineTaskERC =
		createColumn(
			"batchEngineTaskERC", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<BatchPlannerLogTable, String> dispatchTriggerERC =
		createColumn(
			"dispatchTriggerERC", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<BatchPlannerLogTable, Integer> size = createColumn(
		"size_", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<BatchPlannerLogTable, Integer> total = createColumn(
		"total", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<BatchPlannerLogTable, Integer> status = createColumn(
		"status", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);

	private BatchPlannerLogTable() {
		super("BatchPlannerLog", BatchPlannerLogTable::new);
	}

}