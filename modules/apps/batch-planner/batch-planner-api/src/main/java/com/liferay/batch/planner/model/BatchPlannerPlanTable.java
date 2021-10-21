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
 * The table class for the &quot;BatchPlannerPlan&quot; database table.
 *
 * @author Igor Beslic
 * @see BatchPlannerPlan
 * @generated
 */
public class BatchPlannerPlanTable extends BaseTable<BatchPlannerPlanTable> {

	public static final BatchPlannerPlanTable INSTANCE =
		new BatchPlannerPlanTable();

	public final Column<BatchPlannerPlanTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<BatchPlannerPlanTable, Long> batchPlannerPlanId =
		createColumn(
			"batchPlannerPlanId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<BatchPlannerPlanTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<BatchPlannerPlanTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<BatchPlannerPlanTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<BatchPlannerPlanTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<BatchPlannerPlanTable, Date> modifiedDate =
		createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<BatchPlannerPlanTable, Boolean> active = createColumn(
		"active_", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);
	public final Column<BatchPlannerPlanTable, Boolean> export = createColumn(
		"export", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);
	public final Column<BatchPlannerPlanTable, String> externalType =
		createColumn(
			"externalType", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<BatchPlannerPlanTable, String> externalURL =
		createColumn(
			"externalURL", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<BatchPlannerPlanTable, String> internalClassName =
		createColumn(
			"internalClassName", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<BatchPlannerPlanTable, String> name = createColumn(
		"name", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<BatchPlannerPlanTable, String> taskItemDelegateName =
		createColumn(
			"taskItemDelegateName", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<BatchPlannerPlanTable, Boolean> template = createColumn(
		"template", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);

	private BatchPlannerPlanTable() {
		super("BatchPlannerPlan", BatchPlannerPlanTable::new);
	}

}