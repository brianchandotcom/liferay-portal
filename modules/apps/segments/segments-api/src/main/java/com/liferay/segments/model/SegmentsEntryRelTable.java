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
import com.liferay.petra.sql.dsl.Table;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the SegmentsEntryRel.
 *
 * @author Eduardo Garcia
 * @generated
 */
public class SegmentsEntryRelTable extends Table<SegmentsEntryRelTable> {

	public static final SegmentsEntryRelTable INSTANCE =
		new SegmentsEntryRelTable();

	public final Column<SegmentsEntryRelTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT);
	public final Column<SegmentsEntryRelTable, Long> segmentsEntryRelId =
		createColumn("segmentsEntryRelId", Long.class, Types.BIGINT);
	public final Column<SegmentsEntryRelTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT);
	public final Column<SegmentsEntryRelTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT);
	public final Column<SegmentsEntryRelTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT);
	public final Column<SegmentsEntryRelTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR);
	public final Column<SegmentsEntryRelTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP);
	public final Column<SegmentsEntryRelTable, Date> modifiedDate =
		createColumn("modifiedDate", Date.class, Types.TIMESTAMP);
	public final Column<SegmentsEntryRelTable, Long> segmentsEntryId =
		createColumn("segmentsEntryId", Long.class, Types.BIGINT);
	public final Column<SegmentsEntryRelTable, Long> classNameId = createColumn(
		"classNameId", Long.class, Types.BIGINT);
	public final Column<SegmentsEntryRelTable, Long> classPK = createColumn(
		"classPK", Long.class, Types.BIGINT);

	private SegmentsEntryRelTable() {
		super("SegmentsEntryRel", SegmentsEntryRelTable::new);
	}

}