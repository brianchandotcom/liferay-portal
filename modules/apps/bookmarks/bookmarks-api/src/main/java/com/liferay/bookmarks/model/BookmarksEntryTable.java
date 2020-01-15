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

package com.liferay.bookmarks.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.Table;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the BookmarksEntry.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class BookmarksEntryTable extends Table<BookmarksEntryTable> {

	public static final BookmarksEntryTable INSTANCE =
		new BookmarksEntryTable();

	public final Column<BookmarksEntryTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT);
	public final Column<BookmarksEntryTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR);
	public final Column<BookmarksEntryTable, Long> entryId = createColumn(
		"entryId", Long.class, Types.BIGINT);
	public final Column<BookmarksEntryTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT);
	public final Column<BookmarksEntryTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT);
	public final Column<BookmarksEntryTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT);
	public final Column<BookmarksEntryTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR);
	public final Column<BookmarksEntryTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP);
	public final Column<BookmarksEntryTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP);
	public final Column<BookmarksEntryTable, Long> folderId = createColumn(
		"folderId", Long.class, Types.BIGINT);
	public final Column<BookmarksEntryTable, String> treePath = createColumn(
		"treePath", String.class, Types.VARCHAR);
	public final Column<BookmarksEntryTable, String> name = createColumn(
		"name", String.class, Types.VARCHAR);
	public final Column<BookmarksEntryTable, String> url = createColumn(
		"url", String.class, Types.VARCHAR);
	public final Column<BookmarksEntryTable, String> description = createColumn(
		"description", String.class, Types.VARCHAR);
	public final Column<BookmarksEntryTable, Integer> priority = createColumn(
		"priority", Integer.class, Types.INTEGER);
	public final Column<BookmarksEntryTable, Date> lastPublishDate =
		createColumn("lastPublishDate", Date.class, Types.TIMESTAMP);
	public final Column<BookmarksEntryTable, Integer> status = createColumn(
		"status", Integer.class, Types.INTEGER);
	public final Column<BookmarksEntryTable, Long> statusByUserId =
		createColumn("statusByUserId", Long.class, Types.BIGINT);
	public final Column<BookmarksEntryTable, String> statusByUserName =
		createColumn("statusByUserName", String.class, Types.VARCHAR);
	public final Column<BookmarksEntryTable, Date> statusDate = createColumn(
		"statusDate", Date.class, Types.TIMESTAMP);

	private BookmarksEntryTable() {
		super("BookmarksEntry", BookmarksEntryTable::new);
	}

}