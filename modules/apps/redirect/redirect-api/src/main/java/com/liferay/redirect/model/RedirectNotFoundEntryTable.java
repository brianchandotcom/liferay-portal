/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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