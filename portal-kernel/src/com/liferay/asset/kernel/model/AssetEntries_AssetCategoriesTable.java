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

package com.liferay.asset.kernel.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

/**
 * The table class for the &quot;AssetEntries_AssetCategories&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see AssetCategory
 * @see AssetEntry
 * @generated
 */
public class AssetEntries_AssetCategoriesTable
	extends BaseTable<AssetEntries_AssetCategoriesTable> {

	public static final AssetEntries_AssetCategoriesTable INSTANCE =
		new AssetEntries_AssetCategoriesTable();

	public final Column<AssetEntries_AssetCategoriesTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<AssetEntries_AssetCategoriesTable, Long> categoryId =
		createColumn(
			"categoryId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<AssetEntries_AssetCategoriesTable, Long> entryId =
		createColumn("entryId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<AssetEntries_AssetCategoriesTable, Long>
		ctCollectionId = createColumn(
			"ctCollectionId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<AssetEntries_AssetCategoriesTable, Boolean>
		ctChangeType = createColumn(
			"ctChangeType", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);

	private AssetEntries_AssetCategoriesTable() {
		super(
			"AssetEntries_AssetCategories",
			AssetEntries_AssetCategoriesTable::new);
	}

}