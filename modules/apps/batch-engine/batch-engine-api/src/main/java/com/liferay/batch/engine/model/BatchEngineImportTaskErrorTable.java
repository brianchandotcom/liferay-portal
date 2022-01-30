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

import java.sql.Clob;
import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;BatchEngineImportTaskError&quot; database table.
 *
 * @author Shuyang Zhou
 * @see BatchEngineImportTaskError
 * @generated
 */
public class BatchEngineImportTaskErrorTable
	extends BaseTable<BatchEngineImportTaskErrorTable> {

	public static final BatchEngineImportTaskErrorTable INSTANCE =
		new BatchEngineImportTaskErrorTable();

	public final Column<BatchEngineImportTaskErrorTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<BatchEngineImportTaskErrorTable, Long>
		batchEngineImportTaskErrorId = createColumn(
			"batchEngineImportTaskErrorId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<BatchEngineImportTaskErrorTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<BatchEngineImportTaskErrorTable, Long> userId =
		createColumn("userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<BatchEngineImportTaskErrorTable, Date> createDate =
		createColumn(
			"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<BatchEngineImportTaskErrorTable, Date> modifiedDate =
		createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<BatchEngineImportTaskErrorTable, Long>
		batchEngineImportTaskId = createColumn(
			"batchEngineImportTaskId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<BatchEngineImportTaskErrorTable, Clob> item =
		createColumn("item", Clob.class, Types.CLOB, Column.FLAG_DEFAULT);
	public final Column<BatchEngineImportTaskErrorTable, Integer> itemIndex =
		createColumn(
			"itemIndex", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<BatchEngineImportTaskErrorTable, Clob> message =
		createColumn("message", Clob.class, Types.CLOB, Column.FLAG_DEFAULT);

	private BatchEngineImportTaskErrorTable() {
		super(
			"BatchEngineImportTaskError", BatchEngineImportTaskErrorTable::new);
	}

}