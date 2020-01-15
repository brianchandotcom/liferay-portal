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

package com.liferay.asset.list.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.Table;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the AssetListEntryAssetEntryRel.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class AssetListEntryAssetEntryRelTable
	extends Table<AssetListEntryAssetEntryRelTable> {

	public static final AssetListEntryAssetEntryRelTable INSTANCE =
		new AssetListEntryAssetEntryRelTable();

	public final Column<AssetListEntryAssetEntryRelTable, Long> mvccVersion =
		createColumn("mvccVersion", Long.class, Types.BIGINT);
	public final Column<AssetListEntryAssetEntryRelTable, String> uuid =
		createColumn("uuid_", String.class, Types.VARCHAR);
	public final Column<AssetListEntryAssetEntryRelTable, Long>
		assetListEntryAssetEntryRelId = createColumn(
			"assetListEntryAssetEntryRelId", Long.class, Types.BIGINT);
	public final Column<AssetListEntryAssetEntryRelTable, Long> groupId =
		createColumn("groupId", Long.class, Types.BIGINT);
	public final Column<AssetListEntryAssetEntryRelTable, Long> companyId =
		createColumn("companyId", Long.class, Types.BIGINT);
	public final Column<AssetListEntryAssetEntryRelTable, Long> userId =
		createColumn("userId", Long.class, Types.BIGINT);
	public final Column<AssetListEntryAssetEntryRelTable, String> userName =
		createColumn("userName", String.class, Types.VARCHAR);
	public final Column<AssetListEntryAssetEntryRelTable, Date> createDate =
		createColumn("createDate", Date.class, Types.TIMESTAMP);
	public final Column<AssetListEntryAssetEntryRelTable, Date> modifiedDate =
		createColumn("modifiedDate", Date.class, Types.TIMESTAMP);
	public final Column<AssetListEntryAssetEntryRelTable, Long>
		assetListEntryId = createColumn(
			"assetListEntryId", Long.class, Types.BIGINT);
	public final Column<AssetListEntryAssetEntryRelTable, Long> assetEntryId =
		createColumn("assetEntryId", Long.class, Types.BIGINT);
	public final Column<AssetListEntryAssetEntryRelTable, Long>
		segmentsEntryId = createColumn(
			"segmentsEntryId", Long.class, Types.BIGINT);
	public final Column<AssetListEntryAssetEntryRelTable, Integer> position =
		createColumn("position", Integer.class, Types.INTEGER);
	public final Column<AssetListEntryAssetEntryRelTable, Date>
		lastPublishDate = createColumn(
			"lastPublishDate", Date.class, Types.TIMESTAMP);

	private AssetListEntryAssetEntryRelTable() {
		super(
			"AssetListEntryAssetEntryRel",
			AssetListEntryAssetEntryRelTable::new);
	}

}