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

package com.liferay.trash.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.Table;

import java.sql.Clob;
import java.sql.Types;

import java.util.Date;

/**
 * The table class for the TrashEntry.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class TrashEntryTable extends Table<TrashEntryTable> {

	public static final TrashEntryTable INSTANCE = new TrashEntryTable();

	public final Column<TrashEntryTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT);
	public final Column<TrashEntryTable, Long> entryId = createColumn(
		"entryId", Long.class, Types.BIGINT);
	public final Column<TrashEntryTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT);
	public final Column<TrashEntryTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT);
	public final Column<TrashEntryTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT);
	public final Column<TrashEntryTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR);
	public final Column<TrashEntryTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP);
	public final Column<TrashEntryTable, Long> classNameId = createColumn(
		"classNameId", Long.class, Types.BIGINT);
	public final Column<TrashEntryTable, Long> classPK = createColumn(
		"classPK", Long.class, Types.BIGINT);
	public final Column<TrashEntryTable, Long> systemEventSetKey = createColumn(
		"systemEventSetKey", Long.class, Types.BIGINT);
	public final Column<TrashEntryTable, Clob> typeSettings = createColumn(
		"typeSettings", Clob.class, Types.CLOB);
	public final Column<TrashEntryTable, Integer> status = createColumn(
		"status", Integer.class, Types.INTEGER);

	private TrashEntryTable() {
		super("TrashEntry", TrashEntryTable::new);
	}

}