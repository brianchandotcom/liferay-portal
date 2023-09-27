/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
	public final Column<LayoutSEOSiteTable, Long> ctCollectionId = createColumn(
		"ctCollectionId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
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