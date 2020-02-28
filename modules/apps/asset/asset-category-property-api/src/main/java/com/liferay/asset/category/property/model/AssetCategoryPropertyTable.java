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

package com.liferay.asset.category.property.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;AssetCategoryProperty&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see AssetCategoryProperty
 * @generated
 */
public class AssetCategoryPropertyTable
	extends BaseTable<AssetCategoryPropertyTable> {

	public static final AssetCategoryPropertyTable INSTANCE =
		new AssetCategoryPropertyTable();

	public final Column<AssetCategoryPropertyTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<AssetCategoryPropertyTable, Long> ctCollectionId =
		createColumn(
			"ctCollectionId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<AssetCategoryPropertyTable, Long> categoryPropertyId =
		createColumn(
			"categoryPropertyId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<AssetCategoryPropertyTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<AssetCategoryPropertyTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<AssetCategoryPropertyTable, String> userName =
		createColumn(
			"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<AssetCategoryPropertyTable, Date> createDate =
		createColumn(
			"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<AssetCategoryPropertyTable, Date> modifiedDate =
		createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<AssetCategoryPropertyTable, Long> categoryId =
		createColumn(
			"categoryId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<AssetCategoryPropertyTable, String> key = createColumn(
		"key_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<AssetCategoryPropertyTable, String> value =
		createColumn("value", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);

	private AssetCategoryPropertyTable() {
		super("AssetCategoryProperty", AssetCategoryPropertyTable::new);
	}

}