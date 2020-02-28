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
 * The table class for the &quot;LayoutSEOSite&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see LayoutSEOSite
 * @generated
 */
public class LayoutSEOSiteTable extends BaseTable<LayoutSEOSiteTable> {

	public static final LayoutSEOSiteTable INSTANCE = new LayoutSEOSiteTable();

	public final Column<LayoutSEOSiteTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<LayoutSEOSiteTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<LayoutSEOSiteTable, Long> layoutSEOSiteId =
		createColumn(
			"layoutSEOSiteId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<LayoutSEOSiteTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<LayoutSEOSiteTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<LayoutSEOSiteTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<LayoutSEOSiteTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<LayoutSEOSiteTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<LayoutSEOSiteTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<LayoutSEOSiteTable, Boolean> openGraphEnabled =
		createColumn(
			"openGraphEnabled", Boolean.class, Types.BOOLEAN,
			Column.FLAG_DEFAULT);
	public final Column<LayoutSEOSiteTable, String> openGraphImageAlt =
		createColumn(
			"openGraphImageAlt", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<LayoutSEOSiteTable, Long> openGraphImageFileEntryId =
		createColumn(
			"openGraphImageFileEntryId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);

	private LayoutSEOSiteTable() {
		super("LayoutSEOSite", LayoutSEOSiteTable::new);
	}

}