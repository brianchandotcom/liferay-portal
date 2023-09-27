/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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