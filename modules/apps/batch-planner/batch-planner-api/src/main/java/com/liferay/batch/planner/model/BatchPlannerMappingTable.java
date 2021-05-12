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

import java.sql.Clob;
import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;BatchPlannerMapping&quot; database table.
 *
 * @author Igor Beslic
 * @see BatchPlannerMapping
 * @generated
 */
public class BatchPlannerMappingTable
	extends BaseTable<BatchPlannerMappingTable> {

	public static final BatchPlannerMappingTable INSTANCE =
		new BatchPlannerMappingTable();

	public final Column<BatchPlannerMappingTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<BatchPlannerMappingTable, Long> batchPlannerMappingId =
		createColumn(
			"batchPlannerMappingId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<BatchPlannerMappingTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<BatchPlannerMappingTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<BatchPlannerMappingTable, String> userName =
		createColumn(
			"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<BatchPlannerMappingTable, Date> createDate =
		createColumn(
			"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<BatchPlannerMappingTable, Date> modifiedDate =
		createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<BatchPlannerMappingTable, Long> batchPlannerPlanId =
		createColumn(
			"batchPlannerPlanId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<BatchPlannerMappingTable, String> externalFieldName =
		createColumn(
			"externalFieldName", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<BatchPlannerMappingTable, String> externalFieldType =
		createColumn(
			"externalFieldType", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<BatchPlannerMappingTable, String> internalFieldName =
		createColumn(
			"internalFieldName", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<BatchPlannerMappingTable, String> internalFieldType =
		createColumn(
			"internalFieldType", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<BatchPlannerMappingTable, Clob> script = createColumn(
		"script", Clob.class, Types.CLOB, Column.FLAG_DEFAULT);

	private BatchPlannerMappingTable() {
		super("BatchPlannerMapping", BatchPlannerMappingTable::new);
	}

}