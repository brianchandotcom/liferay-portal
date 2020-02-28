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

package com.liferay.reading.time.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;ReadingTimeEntry&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see ReadingTimeEntry
 * @generated
 */
public class ReadingTimeEntryTable extends BaseTable<ReadingTimeEntryTable> {

	public static final ReadingTimeEntryTable INSTANCE =
		new ReadingTimeEntryTable();

	public final Column<ReadingTimeEntryTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<ReadingTimeEntryTable, Long> readingTimeEntryId =
		createColumn(
			"readingTimeEntryId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<ReadingTimeEntryTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<ReadingTimeEntryTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<ReadingTimeEntryTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<ReadingTimeEntryTable, Date> modifiedDate =
		createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<ReadingTimeEntryTable, Long> classNameId = createColumn(
		"classNameId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<ReadingTimeEntryTable, Long> classPK = createColumn(
		"classPK", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<ReadingTimeEntryTable, Long> readingTime = createColumn(
		"readingTime", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);

	private ReadingTimeEntryTable() {
		super("ReadingTimeEntry", ReadingTimeEntryTable::new);
	}

}