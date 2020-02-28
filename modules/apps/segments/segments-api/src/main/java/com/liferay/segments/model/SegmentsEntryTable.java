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

package com.liferay.segments.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Clob;
import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;SegmentsEntry&quot; database table.
 *
 * @author Eduardo Garcia
 * @see SegmentsEntry
 * @generated
 */
public class SegmentsEntryTable extends BaseTable<SegmentsEntryTable> {

	public static final SegmentsEntryTable INSTANCE = new SegmentsEntryTable();

	public final Column<SegmentsEntryTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<SegmentsEntryTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<SegmentsEntryTable, Long> segmentsEntryId =
		createColumn(
			"segmentsEntryId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<SegmentsEntryTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<SegmentsEntryTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<SegmentsEntryTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<SegmentsEntryTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<SegmentsEntryTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<SegmentsEntryTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<SegmentsEntryTable, String> segmentsEntryKey =
		createColumn(
			"segmentsEntryKey", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<SegmentsEntryTable, String> name = createColumn(
		"name", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<SegmentsEntryTable, String> description = createColumn(
		"description", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<SegmentsEntryTable, Boolean> active = createColumn(
		"active_", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);
	public final Column<SegmentsEntryTable, Clob> criteria = createColumn(
		"criteria", Clob.class, Types.CLOB, Column.FLAG_DEFAULT);
	public final Column<SegmentsEntryTable, String> source = createColumn(
		"source", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<SegmentsEntryTable, String> type = createColumn(
		"type_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<SegmentsEntryTable, Date> lastPublishDate =
		createColumn(
			"lastPublishDate", Date.class, Types.TIMESTAMP,
			Column.FLAG_DEFAULT);

	private SegmentsEntryTable() {
		super("SegmentsEntry", SegmentsEntryTable::new);
	}

}