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

package com.liferay.announcements.kernel.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.Table;

import java.sql.Clob;
import java.sql.Types;

import java.util.Date;

/**
 * The table class for the AnnouncementsEntry.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class AnnouncementsEntryTable extends Table<AnnouncementsEntryTable> {

	public static final AnnouncementsEntryTable INSTANCE =
		new AnnouncementsEntryTable();

	public final Column<AnnouncementsEntryTable, Long> mvccVersion =
		createColumn("mvccVersion", Long.class, Types.BIGINT);
	public final Column<AnnouncementsEntryTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR);
	public final Column<AnnouncementsEntryTable, Long> entryId = createColumn(
		"entryId", Long.class, Types.BIGINT);
	public final Column<AnnouncementsEntryTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT);
	public final Column<AnnouncementsEntryTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT);
	public final Column<AnnouncementsEntryTable, String> userName =
		createColumn("userName", String.class, Types.VARCHAR);
	public final Column<AnnouncementsEntryTable, Date> createDate =
		createColumn("createDate", Date.class, Types.TIMESTAMP);
	public final Column<AnnouncementsEntryTable, Date> modifiedDate =
		createColumn("modifiedDate", Date.class, Types.TIMESTAMP);
	public final Column<AnnouncementsEntryTable, Long> classNameId =
		createColumn("classNameId", Long.class, Types.BIGINT);
	public final Column<AnnouncementsEntryTable, Long> classPK = createColumn(
		"classPK", Long.class, Types.BIGINT);
	public final Column<AnnouncementsEntryTable, String> title = createColumn(
		"title", String.class, Types.VARCHAR);
	public final Column<AnnouncementsEntryTable, Clob> content = createColumn(
		"content", Clob.class, Types.CLOB);
	public final Column<AnnouncementsEntryTable, String> url = createColumn(
		"url", String.class, Types.VARCHAR);
	public final Column<AnnouncementsEntryTable, String> type = createColumn(
		"type_", String.class, Types.VARCHAR);
	public final Column<AnnouncementsEntryTable, Date> displayDate =
		createColumn("displayDate", Date.class, Types.TIMESTAMP);
	public final Column<AnnouncementsEntryTable, Date> expirationDate =
		createColumn("expirationDate", Date.class, Types.TIMESTAMP);
	public final Column<AnnouncementsEntryTable, Integer> priority =
		createColumn("priority", Integer.class, Types.INTEGER);
	public final Column<AnnouncementsEntryTable, Boolean> alert = createColumn(
		"alert", Boolean.class, Types.BOOLEAN);

	private AnnouncementsEntryTable() {
		super("AnnouncementsEntry", AnnouncementsEntryTable::new);
	}

}