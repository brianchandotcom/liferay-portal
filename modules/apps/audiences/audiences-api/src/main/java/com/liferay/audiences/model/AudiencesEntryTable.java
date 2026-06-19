/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.audiences.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Clob;
import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;AudiencesEntry&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see AudiencesEntry
 * @generated
 */
public class AudiencesEntryTable extends BaseTable<AudiencesEntryTable> {

	public static final AudiencesEntryTable INSTANCE = new AudiencesEntryTable();

	public final Column<AudiencesEntryTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<AudiencesEntryTable, String> externalReferenceCode =
		createColumn(
			"externalReferenceCode", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<AudiencesEntryTable, Long> audiencesEntryId =
		createColumn(
			"audiencesEntryId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<AudiencesEntryTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<AudiencesEntryTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<AudiencesEntryTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<AudiencesEntryTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<AudiencesEntryTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<AudiencesEntryTable, Clob> json = createColumn(
		"json", Clob.class, Types.CLOB, Column.FLAG_DEFAULT);
	public final Column<AudiencesEntryTable, String> name = createColumn(
		"name", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);

	private AudiencesEntryTable() {
		super("AudiencesEntry", AudiencesEntryTable::new);
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:-1329907185