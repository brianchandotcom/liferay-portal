/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.list.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Clob;
import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;AssetListEntrySegmentsEntryRel&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see AssetListEntrySegmentsEntryRel
 * @generated
 */
public class AssetListEntrySegmentsEntryRelTable
	extends BaseTable<AssetListEntrySegmentsEntryRelTable> {

	public static final AssetListEntrySegmentsEntryRelTable INSTANCE =
		new AssetListEntrySegmentsEntryRelTable();

	public final Column<AssetListEntrySegmentsEntryRelTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<AssetListEntrySegmentsEntryRelTable, Long>
		ctCollectionId = createColumn(
			"ctCollectionId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<AssetListEntrySegmentsEntryRelTable, String> uuid =
		createColumn("uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<AssetListEntrySegmentsEntryRelTable, Long>
		assetListEntrySegmentsEntryRelId = createColumn(
			"alEntrySegmentsEntryRelId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<AssetListEntrySegmentsEntryRelTable, Long> groupId =
		createColumn("groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<AssetListEntrySegmentsEntryRelTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<AssetListEntrySegmentsEntryRelTable, Long> userId =
		createColumn("userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<AssetListEntrySegmentsEntryRelTable, String> userName =
		createColumn(
			"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<AssetListEntrySegmentsEntryRelTable, Date> createDate =
		createColumn(
			"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<AssetListEntrySegmentsEntryRelTable, Date>
		modifiedDate = createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<AssetListEntrySegmentsEntryRelTable, Long>
		assetListEntryId = createColumn(
			"assetListEntryId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<AssetListEntrySegmentsEntryRelTable, Integer> priority =
		createColumn(
			"priority", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<AssetListEntrySegmentsEntryRelTable, Long>
		segmentsEntryId = createColumn(
			"segmentsEntryId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<AssetListEntrySegmentsEntryRelTable, Clob>
		typeSettings = createColumn(
			"typeSettings", Clob.class, Types.CLOB, Column.FLAG_DEFAULT);
	public final Column<AssetListEntrySegmentsEntryRelTable, Date>
		lastPublishDate = createColumn(
			"lastPublishDate", Date.class, Types.TIMESTAMP,
			Column.FLAG_DEFAULT);

	private AssetListEntrySegmentsEntryRelTable() {
		super(
			"AssetListEntrySegmentsEntryRel",
			AssetListEntrySegmentsEntryRelTable::new);
	}

}