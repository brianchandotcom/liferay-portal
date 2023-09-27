/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.kernel.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

/**
 * The table class for the &quot;AssetEntries_AssetTags&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see AssetEntry
 * @see AssetTag
 * @generated
 */
public class AssetEntries_AssetTagsTable
	extends BaseTable<AssetEntries_AssetTagsTable> {

	public static final AssetEntries_AssetTagsTable INSTANCE =
		new AssetEntries_AssetTagsTable();

	public final Column<AssetEntries_AssetTagsTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<AssetEntries_AssetTagsTable, Long> entryId =
		createColumn("entryId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<AssetEntries_AssetTagsTable, Long> tagId = createColumn(
		"tagId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<AssetEntries_AssetTagsTable, Long> ctCollectionId =
		createColumn(
			"ctCollectionId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<AssetEntries_AssetTagsTable, Boolean> ctChangeType =
		createColumn(
			"ctChangeType", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);

	private AssetEntries_AssetTagsTable() {
		super("AssetEntries_AssetTags", AssetEntries_AssetTagsTable::new);
	}

}