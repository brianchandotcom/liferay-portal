/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.seo.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

/**
 * The table class for the &quot;LayoutSEOEntryCustomMetaTag&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see LayoutSEOEntryCustomMetaTag
 * @generated
 */
public class LayoutSEOEntryCustomMetaTagTable
	extends BaseTable<LayoutSEOEntryCustomMetaTagTable> {

	public static final LayoutSEOEntryCustomMetaTagTable INSTANCE =
		new LayoutSEOEntryCustomMetaTagTable();

	public final Column<LayoutSEOEntryCustomMetaTagTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<LayoutSEOEntryCustomMetaTagTable, Long> ctCollectionId =
		createColumn(
			"ctCollectionId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<LayoutSEOEntryCustomMetaTagTable, Long>
		layoutSEOEntryCustomMetaTagId = createColumn(
			"layoutSEOEntryCustomMetaTagId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<LayoutSEOEntryCustomMetaTagTable, Long> groupId =
		createColumn("groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<LayoutSEOEntryCustomMetaTagTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<LayoutSEOEntryCustomMetaTagTable, Long>
		layoutSEOEntryId = createColumn(
			"layoutSEOEntryId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<LayoutSEOEntryCustomMetaTagTable, String> content =
		createColumn(
			"content", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<LayoutSEOEntryCustomMetaTagTable, String> property =
		createColumn(
			"property", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);

	private LayoutSEOEntryCustomMetaTagTable() {
		super(
			"LayoutSEOEntryCustomMetaTag",
			LayoutSEOEntryCustomMetaTagTable::new);
	}

}