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

package com.liferay.microblogs.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;MicroblogsEntry&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see MicroblogsEntry
 * @generated
 */
public class MicroblogsEntryTable extends BaseTable<MicroblogsEntryTable> {

	public static final MicroblogsEntryTable INSTANCE =
		new MicroblogsEntryTable();

	public final Column<MicroblogsEntryTable, Long> microblogsEntryId =
		createColumn(
			"microblogsEntryId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<MicroblogsEntryTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<MicroblogsEntryTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<MicroblogsEntryTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<MicroblogsEntryTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<MicroblogsEntryTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<MicroblogsEntryTable, Long> creatorClassNameId =
		createColumn(
			"creatorClassNameId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<MicroblogsEntryTable, Long> creatorClassPK =
		createColumn(
			"creatorClassPK", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<MicroblogsEntryTable, String> content = createColumn(
		"content", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<MicroblogsEntryTable, Integer> type = createColumn(
		"type_", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<MicroblogsEntryTable, Long> parentMicroblogsEntryId =
		createColumn(
			"parentMicroblogsEntryId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<MicroblogsEntryTable, Integer> socialRelationType =
		createColumn(
			"socialRelationType", Integer.class, Types.INTEGER,
			Column.FLAG_DEFAULT);

	private MicroblogsEntryTable() {
		super("MicroblogsEntry", MicroblogsEntryTable::new);
	}

}