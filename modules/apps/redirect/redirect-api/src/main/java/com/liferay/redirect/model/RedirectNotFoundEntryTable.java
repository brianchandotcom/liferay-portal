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
 * The table class for the &quot;RedirectNotFoundEntry&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see RedirectNotFoundEntry
 * @generated
 */
public class RedirectNotFoundEntryTable
	extends BaseTable<RedirectNotFoundEntryTable> {

	public static final RedirectNotFoundEntryTable INSTANCE =
		new RedirectNotFoundEntryTable();

	public final Column<RedirectNotFoundEntryTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<RedirectNotFoundEntryTable, Long>
		redirectNotFoundEntryId = createColumn(
			"redirectNotFoundEntryId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<RedirectNotFoundEntryTable, Long> groupId =
		createColumn("groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<RedirectNotFoundEntryTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<RedirectNotFoundEntryTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<RedirectNotFoundEntryTable, String> userName =
		createColumn(
			"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<RedirectNotFoundEntryTable, Date> createDate =
		createColumn(
			"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<RedirectNotFoundEntryTable, Date> modifiedDate =
		createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<RedirectNotFoundEntryTable, Boolean> ignored =
		createColumn(
			"ignored", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);
	public final Column<RedirectNotFoundEntryTable, String> url = createColumn(
		"url", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);

	private RedirectNotFoundEntryTable() {
		super("RedirectNotFoundEntry", RedirectNotFoundEntryTable::new);
	}

}