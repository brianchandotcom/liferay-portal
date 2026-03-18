/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.launch.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;LaunchEntry&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see LaunchEntry
 * @generated
 */
public class LaunchEntryTable extends BaseTable<LaunchEntryTable> {

	public static final LaunchEntryTable INSTANCE = new LaunchEntryTable();

	public final Column<LaunchEntryTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<LaunchEntryTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<LaunchEntryTable, String> externalReferenceCode =
		createColumn(
			"externalReferenceCode", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<LaunchEntryTable, Long> launchEntryId = createColumn(
		"launchEntryId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<LaunchEntryTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<LaunchEntryTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<LaunchEntryTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<LaunchEntryTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<LaunchEntryTable, Long> launchSetId = createColumn(
		"launchSetId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<LaunchEntryTable, Long> classNameId = createColumn(
		"classNameId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<LaunchEntryTable, Long> classPK = createColumn(
		"classPK", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<LaunchEntryTable, String> classVersion = createColumn(
		"classVersion", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);

	private LaunchEntryTable() {
		super("LaunchEntry", LaunchEntryTable::new);
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:1076527521