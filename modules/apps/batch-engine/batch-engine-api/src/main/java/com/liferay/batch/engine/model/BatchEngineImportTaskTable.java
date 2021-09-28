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

package com.liferay.batch.engine.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Blob;
import java.sql.Clob;
import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;BatchEngineImportTask&quot; database table.
 *
 * @author Shuyang Zhou
 * @see BatchEngineImportTask
 * @generated
 */
public class BatchEngineImportTaskTable
	extends BaseTable<BatchEngineImportTaskTable> {

	public static final BatchEngineImportTaskTable INSTANCE =
		new BatchEngineImportTaskTable();

	public final Column<BatchEngineImportTaskTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<BatchEngineImportTaskTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<BatchEngineImportTaskTable, Long>
		batchEngineImportTaskId = createColumn(
			"batchEngineImportTaskId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<BatchEngineImportTaskTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<BatchEngineImportTaskTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<BatchEngineImportTaskTable, Date> createDate =
		createColumn(
			"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<BatchEngineImportTaskTable, Date> modifiedDate =
		createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<BatchEngineImportTaskTable, Long> batchSize =
		createColumn(
			"batchSize", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<BatchEngineImportTaskTable, String> callbackURL =
		createColumn(
			"callbackURL", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<BatchEngineImportTaskTable, String> className =
		createColumn(
			"className", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<BatchEngineImportTaskTable, Blob> content =
		createColumn("content", Blob.class, Types.BLOB, Column.FLAG_DEFAULT);
	public final Column<BatchEngineImportTaskTable, String> contentType =
		createColumn(
			"contentType", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<BatchEngineImportTaskTable, Date> endTime =
		createColumn(
			"endTime", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<BatchEngineImportTaskTable, Clob> errorMessage =
		createColumn(
			"errorMessage", Clob.class, Types.CLOB, Column.FLAG_DEFAULT);
	public final Column<BatchEngineImportTaskTable, String> executeStatus =
		createColumn(
			"executeStatus", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<BatchEngineImportTaskTable, Clob> fieldNameMapping =
		createColumn(
			"fieldNameMapping", Clob.class, Types.CLOB, Column.FLAG_DEFAULT);
	public final Column<BatchEngineImportTaskTable, String> operation =
		createColumn(
			"operation", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<BatchEngineImportTaskTable, Clob> parameters =
		createColumn("parameters", Clob.class, Types.CLOB, Column.FLAG_DEFAULT);
	public final Column<BatchEngineImportTaskTable, Integer>
		processedItemsCount = createColumn(
			"processedItemsCount", Integer.class, Types.INTEGER,
			Column.FLAG_DEFAULT);
	public final Column<BatchEngineImportTaskTable, Date> startTime =
		createColumn(
			"startTime", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<BatchEngineImportTaskTable, String>
		taskItemDelegateName = createColumn(
			"taskItemDelegateName", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<BatchEngineImportTaskTable, Integer> totalItemsCount =
		createColumn(
			"totalItemsCount", Integer.class, Types.INTEGER,
			Column.FLAG_DEFAULT);

	private BatchEngineImportTaskTable() {
		super("BatchEngineImportTask", BatchEngineImportTaskTable::new);
	}

}