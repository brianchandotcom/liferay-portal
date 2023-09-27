/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
	public final Column<BatchPlannerPlanTable, Integer> size = createColumn(
		"size_", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<BatchPlannerPlanTable, String> taskItemDelegateName =
		createColumn(
			"taskItemDelegateName", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<BatchPlannerPlanTable, Integer> total = createColumn(
		"total", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<BatchPlannerPlanTable, Boolean> template = createColumn(
		"template", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);
	public final Column<BatchPlannerPlanTable, Integer> status = createColumn(
		"status", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);

	private BatchPlannerPlanTable() {
		super("BatchPlannerPlan", BatchPlannerPlanTable::new);
	}

}