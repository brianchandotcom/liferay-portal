/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.redirect.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;RedirectEntry&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see RedirectEntry
 * @generated
 */
public class RedirectEntryTable extends BaseTable<RedirectEntryTable> {

	public static final RedirectEntryTable INSTANCE = new RedirectEntryTable();

	public final Column<RedirectEntryTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<RedirectEntryTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<RedirectEntryTable, Long> redirectEntryId =
		createColumn(
			"redirectEntryId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<RedirectEntryTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<RedirectEntryTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<RedirectEntryTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<RedirectEntryTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<RedirectEntryTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<RedirectEntryTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<RedirectEntryTable, String> destinationURL =
		createColumn(
			"destinationURL", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<RedirectEntryTable, Date> expirationDate = createColumn(
		"expirationDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<RedirectEntryTable, Date> lastOccurrenceDate =
		createColumn(
			"lastOccurrenceDate", Date.class, Types.TIMESTAMP,
			Column.FLAG_DEFAULT);
	public final Column<RedirectEntryTable, Boolean> permanent = createColumn(
		"permanent_", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);
	public final Column<RedirectEntryTable, String> sourceURL = createColumn(
		"sourceURL", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);

	private RedirectEntryTable() {
		super("RedirectEntry", RedirectEntryTable::new);
	}

}