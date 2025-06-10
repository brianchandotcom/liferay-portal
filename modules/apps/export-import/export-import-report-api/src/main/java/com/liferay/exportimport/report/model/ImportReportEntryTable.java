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
 * The table class for the &quot;ImportReportEntry&quot; database table.
 *
 * @author Carlos Correa
 * @see ImportReportEntry
 * @generated
 */
public class ImportReportEntryTable extends BaseTable<ImportReportEntryTable> {

	public static final ImportReportEntryTable INSTANCE =
		new ImportReportEntryTable();

	public final Column<ImportReportEntryTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<ImportReportEntryTable, Long> importReportEntryId =
		createColumn(
			"importReportEntryId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<ImportReportEntryTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<ImportReportEntryTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<ImportReportEntryTable, Date> modifiedDate =
		createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<ImportReportEntryTable, Long> classNameId =
		createColumn(
			"classNameId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<ImportReportEntryTable, Long> classPK = createColumn(
		"classPK", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<ImportReportEntryTable, Long> entityClassNameId =
		createColumn(
			"entityClassNameId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<ImportReportEntryTable, String>
		entityExternalReferenceCode = createColumn(
			"entityExternalReferenceCode", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<ImportReportEntryTable, String> error = createColumn(
		"error", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<ImportReportEntryTable, Boolean> resolved =
		createColumn(
			"resolved", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);
	public final Column<ImportReportEntryTable, Integer> type = createColumn(
		"type_", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);

	private ImportReportEntryTable() {
		super("ImportReportEntry", ImportReportEntryTable::new);
	}

}