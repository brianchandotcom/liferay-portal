/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.batch.engine.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;BatchEngineImportReportEntry&quot; database table.
 *
 * @author Shuyang Zhou
 * @see BatchEngineImportReportEntry
 * @generated
 */
public class BatchEngineImportReportEntryTable
	extends BaseTable<BatchEngineImportReportEntryTable> {

	public static final BatchEngineImportReportEntryTable INSTANCE =
		new BatchEngineImportReportEntryTable();

	public final Column<BatchEngineImportReportEntryTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<BatchEngineImportReportEntryTable, Long>
		batchEngineImportReportEntryId = createColumn(
			"batchEngineImportReportEntryId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<BatchEngineImportReportEntryTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<BatchEngineImportReportEntryTable, Date> createDate =
		createColumn(
			"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<BatchEngineImportReportEntryTable, Date> modifiedDate =
		createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<BatchEngineImportReportEntryTable, Long> classNameId =
		createColumn(
			"classNameId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<BatchEngineImportReportEntryTable, Long> classPK =
		createColumn("classPK", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<BatchEngineImportReportEntryTable, Long>
		entityClassNameId = createColumn(
			"entityClassNameId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<BatchEngineImportReportEntryTable, String>
		entityExternalReferenceCode = createColumn(
			"entityExternalReferenceCode", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<BatchEngineImportReportEntryTable, String> error =
		createColumn("error", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<BatchEngineImportReportEntryTable, Boolean> resolved =
		createColumn(
			"resolved", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);
	public final Column<BatchEngineImportReportEntryTable, Integer> type =
		createColumn(
			"type_", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);

	private BatchEngineImportReportEntryTable() {
		super(
			"BatchEngineImportReportEntry",
			BatchEngineImportReportEntryTable::new);
	}

}