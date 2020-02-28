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

package com.liferay.asset.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;AssetEntryUsage&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see AssetEntryUsage
 * @generated
 */
public class AssetEntryUsageTable extends BaseTable<AssetEntryUsageTable> {

	public static final AssetEntryUsageTable INSTANCE =
		new AssetEntryUsageTable();

	public final Column<AssetEntryUsageTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<AssetEntryUsageTable, Long> ctCollectionId =
		createColumn(
			"ctCollectionId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<AssetEntryUsageTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<AssetEntryUsageTable, Long> assetEntryUsageId =
		createColumn(
			"assetEntryUsageId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<AssetEntryUsageTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<AssetEntryUsageTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<AssetEntryUsageTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<AssetEntryUsageTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<AssetEntryUsageTable, Long> assetEntryId = createColumn(
		"assetEntryId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<AssetEntryUsageTable, Long> containerType =
		createColumn(
			"containerType", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<AssetEntryUsageTable, String> containerKey =
		createColumn(
			"containerKey", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<AssetEntryUsageTable, Long> plid = createColumn(
		"plid", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<AssetEntryUsageTable, Integer> type = createColumn(
		"type_", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<AssetEntryUsageTable, Date> lastPublishDate =
		createColumn(
			"lastPublishDate", Date.class, Types.TIMESTAMP,
			Column.FLAG_DEFAULT);

	private AssetEntryUsageTable() {
		super("AssetEntryUsage", AssetEntryUsageTable::new);
	}

}