/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.audience.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;AudienceEntry&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see AudienceEntry
 * @generated
 */
public class AudienceEntryTable extends BaseTable<AudienceEntryTable> {

	public static final AudienceEntryTable INSTANCE = new AudienceEntryTable();

	public final Column<AudienceEntryTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<AudienceEntryTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<AudienceEntryTable, String> externalReferenceCode =
		createColumn(
			"externalReferenceCode", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<AudienceEntryTable, Long> audienceEntryId =
		createColumn(
			"audienceEntryId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<AudienceEntryTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<AudienceEntryTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<AudienceEntryTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<AudienceEntryTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<AudienceEntryTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<AudienceEntryTable, String> json = createColumn(
		"json", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<AudienceEntryTable, String> name = createColumn(
		"name", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);

	private AudienceEntryTable() {
		super("AudienceEntry", AudienceEntryTable::new);
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:-10870693