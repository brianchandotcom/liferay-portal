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

package com.liferay.layout.seo.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;LayoutSEOEntry&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see LayoutSEOEntry
 * @generated
 */
public class LayoutSEOEntryTable extends BaseTable<LayoutSEOEntryTable> {

	public static final LayoutSEOEntryTable INSTANCE =
		new LayoutSEOEntryTable();

	public final Column<LayoutSEOEntryTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<LayoutSEOEntryTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<LayoutSEOEntryTable, Long> layoutSEOEntryId =
		createColumn(
			"layoutSEOEntryId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<LayoutSEOEntryTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<LayoutSEOEntryTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<LayoutSEOEntryTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<LayoutSEOEntryTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<LayoutSEOEntryTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<LayoutSEOEntryTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<LayoutSEOEntryTable, Boolean> privateLayout =
		createColumn(
			"privateLayout", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);
	public final Column<LayoutSEOEntryTable, Long> layoutId = createColumn(
		"layoutId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<LayoutSEOEntryTable, String> canonicalURL =
		createColumn(
			"canonicalURL", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<LayoutSEOEntryTable, Boolean> canonicalURLEnabled =
		createColumn(
			"canonicalURLEnabled", Boolean.class, Types.BOOLEAN,
			Column.FLAG_DEFAULT);
	public final Column<LayoutSEOEntryTable, Long> DDMStorageId = createColumn(
		"DDMStorageId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<LayoutSEOEntryTable, String> openGraphDescription =
		createColumn(
			"openGraphDescription", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<LayoutSEOEntryTable, Boolean>
		openGraphDescriptionEnabled = createColumn(
			"openGraphDescriptionEnabled", Boolean.class, Types.BOOLEAN,
			Column.FLAG_DEFAULT);
	public final Column<LayoutSEOEntryTable, String> openGraphImageAlt =
		createColumn(
			"openGraphImageAlt", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<LayoutSEOEntryTable, Long> openGraphImageFileEntryId =
		createColumn(
			"openGraphImageFileEntryId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<LayoutSEOEntryTable, String> openGraphTitle =
		createColumn(
			"openGraphTitle", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<LayoutSEOEntryTable, Boolean> openGraphTitleEnabled =
		createColumn(
			"openGraphTitleEnabled", Boolean.class, Types.BOOLEAN,
			Column.FLAG_DEFAULT);
	public final Column<LayoutSEOEntryTable, Date> lastPublishDate =
		createColumn(
			"lastPublishDate", Date.class, Types.TIMESTAMP,
			Column.FLAG_DEFAULT);

	private LayoutSEOEntryTable() {
		super("LayoutSEOEntry", LayoutSEOEntryTable::new);
	}

}