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