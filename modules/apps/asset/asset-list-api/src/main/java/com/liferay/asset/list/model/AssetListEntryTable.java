/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.list.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;AssetListEntry&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see AssetListEntry
 * @generated
 */
public class AssetListEntryTable extends BaseTable<AssetListEntryTable> {

	public static final AssetListEntryTable INSTANCE =
		new AssetListEntryTable();

	public final Column<AssetListEntryTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<AssetListEntryTable, Long> ctCollectionId =
		createColumn(
			"ctCollectionId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<AssetListEntryTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<AssetListEntryTable, Long> assetListEntryId =
		createColumn(
			"assetListEntryId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<AssetListEntryTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<AssetListEntryTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<AssetListEntryTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<AssetListEntryTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<AssetListEntryTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<AssetListEntryTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<AssetListEntryTable, String> assetListEntryKey =
		createColumn(
			"assetListEntryKey", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<AssetListEntryTable, String> title = createColumn(
		"title", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<AssetListEntryTable, Integer> type = createColumn(
		"type_", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<AssetListEntryTable, String> assetEntrySubtype =
		createColumn(
			"assetEntrySubtype", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<AssetListEntryTable, String> assetEntryType =
		createColumn(
			"assetEntryType", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<AssetListEntryTable, Date> lastPublishDate =
		createColumn(
			"lastPublishDate", Date.class, Types.TIMESTAMP,
			Column.FLAG_DEFAULT);

	private AssetListEntryTable() {
		super("AssetListEntry", AssetListEntryTable::new);
	}

}