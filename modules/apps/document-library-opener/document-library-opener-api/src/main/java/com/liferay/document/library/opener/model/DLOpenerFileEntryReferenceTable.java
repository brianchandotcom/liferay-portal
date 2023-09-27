/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.opener.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;DLOpenerFileEntryReference&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see DLOpenerFileEntryReference
 * @generated
 */
public class DLOpenerFileEntryReferenceTable
	extends BaseTable<DLOpenerFileEntryReferenceTable> {

	public static final DLOpenerFileEntryReferenceTable INSTANCE =
		new DLOpenerFileEntryReferenceTable();

	public final Column<DLOpenerFileEntryReferenceTable, Long>
		dlOpenerFileEntryReferenceId = createColumn(
			"dlOpenerFileEntryReferenceId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<DLOpenerFileEntryReferenceTable, Long> groupId =
		createColumn("groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<DLOpenerFileEntryReferenceTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<DLOpenerFileEntryReferenceTable, Long> userId =
		createColumn("userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<DLOpenerFileEntryReferenceTable, String> userName =
		createColumn(
			"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<DLOpenerFileEntryReferenceTable, Date> createDate =
		createColumn(
			"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<DLOpenerFileEntryReferenceTable, Date> modifiedDate =
		createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<DLOpenerFileEntryReferenceTable, String> referenceKey =
		createColumn(
			"referenceKey", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<DLOpenerFileEntryReferenceTable, String> referenceType =
		createColumn(
			"referenceType", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<DLOpenerFileEntryReferenceTable, Long> fileEntryId =
		createColumn(
			"fileEntryId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<DLOpenerFileEntryReferenceTable, Integer> type =
		createColumn(
			"type_", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);

	private DLOpenerFileEntryReferenceTable() {
		super(
			"DLOpenerFileEntryReference", DLOpenerFileEntryReferenceTable::new);
	}

}