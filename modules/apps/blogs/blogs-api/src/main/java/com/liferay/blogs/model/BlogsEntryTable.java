/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.blogs.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Clob;
import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;BlogsEntry&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see BlogsEntry
 * @generated
 */
public class BlogsEntryTable extends BaseTable<BlogsEntryTable> {

	public static final BlogsEntryTable INSTANCE = new BlogsEntryTable();

	public final Column<BlogsEntryTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<BlogsEntryTable, Long> ctCollectionId = createColumn(
		"ctCollectionId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<BlogsEntryTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<BlogsEntryTable, String> externalReferenceCode =
		createColumn(
			"externalReferenceCode", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<BlogsEntryTable, Long> entryId = createColumn(
		"entryId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<BlogsEntryTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<BlogsEntryTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<BlogsEntryTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<BlogsEntryTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<BlogsEntryTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<BlogsEntryTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<BlogsEntryTable, String> title = createColumn(
		"title", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<BlogsEntryTable, String> subtitle = createColumn(
		"subtitle", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<BlogsEntryTable, String> urlTitle = createColumn(
		"urlTitle", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<BlogsEntryTable, String> description = createColumn(
		"description", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<BlogsEntryTable, Clob> content = createColumn(
		"content", Clob.class, Types.CLOB, Column.FLAG_DEFAULT);
	public final Column<BlogsEntryTable, Date> displayDate = createColumn(
		"displayDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<BlogsEntryTable, Boolean> allowPingbacks = createColumn(
		"allowPingbacks", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);
	public final Column<BlogsEntryTable, Boolean> allowTrackbacks =
		createColumn(
			"allowTrackbacks", Boolean.class, Types.BOOLEAN,
			Column.FLAG_DEFAULT);
	public final Column<BlogsEntryTable, Clob> trackbacks = createColumn(
		"trackbacks", Clob.class, Types.CLOB, Column.FLAG_DEFAULT);
	public final Column<BlogsEntryTable, String> coverImageCaption =
		createColumn(
			"coverImageCaption", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<BlogsEntryTable, Long> coverImageFileEntryId =
		createColumn(
			"coverImageFileEntryId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<BlogsEntryTable, String> coverImageURL = createColumn(
		"coverImageURL", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<BlogsEntryTable, Boolean> smallImage = createColumn(
		"smallImage", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);
	public final Column<BlogsEntryTable, Long> smallImageFileEntryId =
		createColumn(
			"smallImageFileEntryId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<BlogsEntryTable, Long> smallImageId = createColumn(
		"smallImageId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<BlogsEntryTable, String> smallImageURL = createColumn(
		"smallImageURL", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<BlogsEntryTable, Date> lastPublishDate = createColumn(
		"lastPublishDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<BlogsEntryTable, Integer> status = createColumn(
		"status", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<BlogsEntryTable, Long> statusByUserId = createColumn(
		"statusByUserId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<BlogsEntryTable, String> statusByUserName =
		createColumn(
			"statusByUserName", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<BlogsEntryTable, Date> statusDate = createColumn(
		"statusDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);

	private BlogsEntryTable() {
		super("BlogsEntry", BlogsEntryTable::new);
	}

}