/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.report.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;ExportImportReportEntry&quot; database table.
 *
 * @author Carlos Correa
 * @see ExportImportReportEntry
 * @generated
 */
public class ExportImportReportEntryTable
	extends BaseTable<ExportImportReportEntryTable> {

	public static final ExportImportReportEntryTable INSTANCE =
		new ExportImportReportEntryTable();

	public final Column<ExportImportReportEntryTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<ExportImportReportEntryTable, Long>
		exportImportReportEntryId = createColumn(
			"exportImportReportEntryId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<ExportImportReportEntryTable, Long> groupId =
		createColumn("groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<ExportImportReportEntryTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<ExportImportReportEntryTable, Date> createDate =
		createColumn(
			"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<ExportImportReportEntryTable, Date> modifiedDate =
		createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<ExportImportReportEntryTable, Long> entityClassNameId =
		createColumn(
			"entityClassNameId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<ExportImportReportEntryTable, String>
		entityExternalReferenceCode = createColumn(
			"entityExternalReferenceCode", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<ExportImportReportEntryTable, String> error =
		createColumn("error", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<ExportImportReportEntryTable, String> errorStacktrace =
		createColumn(
			"errorStacktrace", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<ExportImportReportEntryTable, Long>
		exportImportConfigurationId = createColumn(
			"exportImportConfigurationId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<ExportImportReportEntryTable, Boolean> resolved =
		createColumn(
			"resolved", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);
	public final Column<ExportImportReportEntryTable, Integer> type =
		createColumn(
			"type_", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);

	private ExportImportReportEntryTable() {
		super("ExportImportReportEntry", ExportImportReportEntryTable::new);
	}

}