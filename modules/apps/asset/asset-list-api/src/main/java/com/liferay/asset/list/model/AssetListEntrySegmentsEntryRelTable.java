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

import java.sql.Clob;
import java.sql.Types;

import java.util.Date;

/**
 * The table class for the AssetListEntrySegmentsEntryRel.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class AssetListEntrySegmentsEntryRelTable
	extends Table<AssetListEntrySegmentsEntryRelTable> {

	public static final AssetListEntrySegmentsEntryRelTable INSTANCE =
		new AssetListEntrySegmentsEntryRelTable();

	public final Column<AssetListEntrySegmentsEntryRelTable, Long> mvccVersion =
		createColumn("mvccVersion", Long.class, Types.BIGINT);
	public final Column<AssetListEntrySegmentsEntryRelTable, String> uuid =
		createColumn("uuid_", String.class, Types.VARCHAR);
	public final Column<AssetListEntrySegmentsEntryRelTable, Long>
		assetListEntrySegmentsEntryRelId = createColumn(
			"alEntrySegmentsEntryRelId", Long.class, Types.BIGINT);
	public final Column<AssetListEntrySegmentsEntryRelTable, Long> groupId =
		createColumn("groupId", Long.class, Types.BIGINT);
	public final Column<AssetListEntrySegmentsEntryRelTable, Long> companyId =
		createColumn("companyId", Long.class, Types.BIGINT);
	public final Column<AssetListEntrySegmentsEntryRelTable, Long> userId =
		createColumn("userId", Long.class, Types.BIGINT);
	public final Column<AssetListEntrySegmentsEntryRelTable, String> userName =
		createColumn("userName", String.class, Types.VARCHAR);
	public final Column<AssetListEntrySegmentsEntryRelTable, Date> createDate =
		createColumn("createDate", Date.class, Types.TIMESTAMP);
	public final Column<AssetListEntrySegmentsEntryRelTable, Date>
		modifiedDate = createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP);
	public final Column<AssetListEntrySegmentsEntryRelTable, Long>
		assetListEntryId = createColumn(
			"assetListEntryId", Long.class, Types.BIGINT);
	public final Column<AssetListEntrySegmentsEntryRelTable, Long>
		segmentsEntryId = createColumn(
			"segmentsEntryId", Long.class, Types.BIGINT);
	public final Column<AssetListEntrySegmentsEntryRelTable, Clob>
		typeSettings = createColumn("typeSettings", Clob.class, Types.CLOB);
	public final Column<AssetListEntrySegmentsEntryRelTable, Date>
		lastPublishDate = createColumn(
			"lastPublishDate", Date.class, Types.TIMESTAMP);

	private AssetListEntrySegmentsEntryRelTable() {
		super(
			"AssetListEntrySegmentsEntryRel",
			AssetListEntrySegmentsEntryRelTable::new);
	}

}