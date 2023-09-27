/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

	public final Column<ReadingTimeEntryTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<ReadingTimeEntryTable, Long> ctCollectionId =
		createColumn(
			"ctCollectionId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
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