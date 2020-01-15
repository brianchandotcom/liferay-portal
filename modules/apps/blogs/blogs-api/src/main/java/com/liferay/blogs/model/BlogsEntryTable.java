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

package com.liferay.blogs.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.Table;

import java.sql.Clob;
import java.sql.Types;

import java.util.Date;

/**
 * The table class for the BlogsEntry.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class BlogsEntryTable extends Table<BlogsEntryTable> {

	public static final BlogsEntryTable INSTANCE = new BlogsEntryTable();

	public final Column<BlogsEntryTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT);
	public final Column<BlogsEntryTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR);
	public final Column<BlogsEntryTable, Long> entryId = createColumn(
		"entryId", Long.class, Types.BIGINT);
	public final Column<BlogsEntryTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT);
	public final Column<BlogsEntryTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT);
	public final Column<BlogsEntryTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT);
	public final Column<BlogsEntryTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR);
	public final Column<BlogsEntryTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP);
	public final Column<BlogsEntryTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP);
	public final Column<BlogsEntryTable, String> title = createColumn(
		"title", String.class, Types.VARCHAR);
	public final Column<BlogsEntryTable, String> subtitle = createColumn(
		"subtitle", String.class, Types.VARCHAR);
	public final Column<BlogsEntryTable, String> urlTitle = createColumn(
		"urlTitle", String.class, Types.VARCHAR);
	public final Column<BlogsEntryTable, String> description = createColumn(
		"description", String.class, Types.VARCHAR);
	public final Column<BlogsEntryTable, Clob> content = createColumn(
		"content", Clob.class, Types.CLOB);
	public final Column<BlogsEntryTable, Date> displayDate = createColumn(
		"displayDate", Date.class, Types.TIMESTAMP);
	public final Column<BlogsEntryTable, Boolean> allowPingbacks = createColumn(
		"allowPingbacks", Boolean.class, Types.BOOLEAN);
	public final Column<BlogsEntryTable, Boolean> allowTrackbacks =
		createColumn("allowTrackbacks", Boolean.class, Types.BOOLEAN);
	public final Column<BlogsEntryTable, Clob> trackbacks = createColumn(
		"trackbacks", Clob.class, Types.CLOB);
	public final Column<BlogsEntryTable, String> coverImageCaption =
		createColumn("coverImageCaption", String.class, Types.VARCHAR);
	public final Column<BlogsEntryTable, Long> coverImageFileEntryId =
		createColumn("coverImageFileEntryId", Long.class, Types.BIGINT);
	public final Column<BlogsEntryTable, String> coverImageURL = createColumn(
		"coverImageURL", String.class, Types.VARCHAR);
	public final Column<BlogsEntryTable, Boolean> smallImage = createColumn(
		"smallImage", Boolean.class, Types.BOOLEAN);
	public final Column<BlogsEntryTable, Long> smallImageFileEntryId =
		createColumn("smallImageFileEntryId", Long.class, Types.BIGINT);
	public final Column<BlogsEntryTable, Long> smallImageId = createColumn(
		"smallImageId", Long.class, Types.BIGINT);
	public final Column<BlogsEntryTable, String> smallImageURL = createColumn(
		"smallImageURL", String.class, Types.VARCHAR);
	public final Column<BlogsEntryTable, Date> lastPublishDate = createColumn(
		"lastPublishDate", Date.class, Types.TIMESTAMP);
	public final Column<BlogsEntryTable, Integer> status = createColumn(
		"status", Integer.class, Types.INTEGER);
	public final Column<BlogsEntryTable, Long> statusByUserId = createColumn(
		"statusByUserId", Long.class, Types.BIGINT);
	public final Column<BlogsEntryTable, String> statusByUserName =
		createColumn("statusByUserName", String.class, Types.VARCHAR);
	public final Column<BlogsEntryTable, Date> statusDate = createColumn(
		"statusDate", Date.class, Types.TIMESTAMP);

	private BlogsEntryTable() {
		super("BlogsEntry", BlogsEntryTable::new);
	}

}