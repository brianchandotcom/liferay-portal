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
import com.liferay.petra.sql.dsl.Table;

import java.sql.Blob;
import java.sql.Clob;
import java.sql.Types;

import java.util.Date;

/**
 * The table class for the BatchEngineExportTask.
 *
 * @author Shuyang Zhou
 * @generated
 */
public class BatchEngineExportTaskTable
	extends Table<BatchEngineExportTaskTable> {

	public static final BatchEngineExportTaskTable INSTANCE =
		new BatchEngineExportTaskTable();

	public final Column<BatchEngineExportTaskTable, Long> mvccVersion =
		createColumn("mvccVersion", Long.class, Types.BIGINT);
	public final Column<BatchEngineExportTaskTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR);
	public final Column<BatchEngineExportTaskTable, Long>
		batchEngineExportTaskId = createColumn(
			"batchEngineExportTaskId", Long.class, Types.BIGINT);
	public final Column<BatchEngineExportTaskTable, Long> companyId =
		createColumn("companyId", Long.class, Types.BIGINT);
	public final Column<BatchEngineExportTaskTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT);
	public final Column<BatchEngineExportTaskTable, Date> createDate =
		createColumn("createDate", Date.class, Types.TIMESTAMP);
	public final Column<BatchEngineExportTaskTable, Date> modifiedDate =
		createColumn("modifiedDate", Date.class, Types.TIMESTAMP);
	public final Column<BatchEngineExportTaskTable, String> callbackURL =
		createColumn("callbackURL", String.class, Types.VARCHAR);
	public final Column<BatchEngineExportTaskTable, String> className =
		createColumn("className", String.class, Types.VARCHAR);
	public final Column<BatchEngineExportTaskTable, Blob> content =
		createColumn("content", Blob.class, Types.BLOB);
	public final Column<BatchEngineExportTaskTable, String> contentType =
		createColumn("contentType", String.class, Types.VARCHAR);
	public final Column<BatchEngineExportTaskTable, Date> endTime =
		createColumn("endTime", Date.class, Types.TIMESTAMP);
	public final Column<BatchEngineExportTaskTable, String> errorMessage =
		createColumn("errorMessage", String.class, Types.VARCHAR);
	public final Column<BatchEngineExportTaskTable, String> fieldNames =
		createColumn("fieldNames", String.class, Types.VARCHAR);
	public final Column<BatchEngineExportTaskTable, String> executeStatus =
		createColumn("executeStatus", String.class, Types.VARCHAR);
	public final Column<BatchEngineExportTaskTable, Clob> parameters =
		createColumn("parameters", Clob.class, Types.CLOB);
	public final Column<BatchEngineExportTaskTable, Date> startTime =
		createColumn("startTime", Date.class, Types.TIMESTAMP);
	public final Column<BatchEngineExportTaskTable, String> version =
		createColumn("version", String.class, Types.VARCHAR);

	private BatchEngineExportTaskTable() {
		super("BatchEngineExportTask", BatchEngineExportTaskTable::new);
	}

}