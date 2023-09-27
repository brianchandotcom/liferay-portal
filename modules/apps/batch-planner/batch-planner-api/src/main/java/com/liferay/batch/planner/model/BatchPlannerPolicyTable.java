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
 * The table class for the &quot;BatchPlannerPolicy&quot; database table.
 *
 * @author Igor Beslic
 * @see BatchPlannerPolicy
 * @generated
 */
public class BatchPlannerPolicyTable
	extends BaseTable<BatchPlannerPolicyTable> {

	public static final BatchPlannerPolicyTable INSTANCE =
		new BatchPlannerPolicyTable();

	public final Column<BatchPlannerPolicyTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<BatchPlannerPolicyTable, Long> batchPlannerPolicyId =
		createColumn(
			"batchPlannerPolicyId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<BatchPlannerPolicyTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<BatchPlannerPolicyTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<BatchPlannerPolicyTable, String> userName =
		createColumn(
			"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<BatchPlannerPolicyTable, Date> createDate =
		createColumn(
			"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<BatchPlannerPolicyTable, Date> modifiedDate =
		createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<BatchPlannerPolicyTable, Long> batchPlannerPlanId =
		createColumn(
			"batchPlannerPlanId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<BatchPlannerPolicyTable, String> name = createColumn(
		"name", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<BatchPlannerPolicyTable, String> value = createColumn(
		"value", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);

	private BatchPlannerPolicyTable() {
		super("BatchPlannerPolicy", BatchPlannerPolicyTable::new);
	}

}