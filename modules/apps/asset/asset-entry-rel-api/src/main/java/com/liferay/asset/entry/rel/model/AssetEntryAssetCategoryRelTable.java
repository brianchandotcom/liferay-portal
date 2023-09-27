/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.entry.rel.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

/**
 * The table class for the &quot;AssetEntryAssetCategoryRel&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see AssetEntryAssetCategoryRel
 * @generated
 */
public class AssetEntryAssetCategoryRelTable
	extends BaseTable<AssetEntryAssetCategoryRelTable> {

	public static final AssetEntryAssetCategoryRelTable INSTANCE =
		new AssetEntryAssetCategoryRelTable();

	public final Column<AssetEntryAssetCategoryRelTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<AssetEntryAssetCategoryRelTable, Long> ctCollectionId =
		createColumn(
			"ctCollectionId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<AssetEntryAssetCategoryRelTable, Long>
		assetEntryAssetCategoryRelId = createColumn(
			"assetEntryAssetCategoryRelId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<AssetEntryAssetCategoryRelTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<AssetEntryAssetCategoryRelTable, Long> assetEntryId =
		createColumn(
			"assetEntryId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<AssetEntryAssetCategoryRelTable, Long> assetCategoryId =
		createColumn(
			"assetCategoryId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<AssetEntryAssetCategoryRelTable, Integer> priority =
		createColumn(
			"priority", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);

	private AssetEntryAssetCategoryRelTable() {
		super(
			"AssetEntryAssetCategoryRel", AssetEntryAssetCategoryRelTable::new);
	}

}