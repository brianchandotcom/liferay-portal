/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.auto.tagger.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;AssetAutoTaggerEntry&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see AssetAutoTaggerEntry
 * @generated
 */
public class AssetAutoTaggerEntryTable
	extends BaseTable<AssetAutoTaggerEntryTable> {

	public static final AssetAutoTaggerEntryTable INSTANCE =
		new AssetAutoTaggerEntryTable();

	public final Column<AssetAutoTaggerEntryTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<AssetAutoTaggerEntryTable, Long> ctCollectionId =
		createColumn(
			"ctCollectionId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<AssetAutoTaggerEntryTable, Long>
		assetAutoTaggerEntryId = createColumn(
			"assetAutoTaggerEntryId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<AssetAutoTaggerEntryTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<AssetAutoTaggerEntryTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<AssetAutoTaggerEntryTable, Date> createDate =
		createColumn(
			"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<AssetAutoTaggerEntryTable, Date> modifiedDate =
		createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<AssetAutoTaggerEntryTable, Long> assetEntryId =
		createColumn(
			"assetEntryId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<AssetAutoTaggerEntryTable, Long> assetTagId =
		createColumn(
			"assetTagId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);

	private AssetAutoTaggerEntryTable() {
		super("AssetAutoTaggerEntry", AssetAutoTaggerEntryTable::new);
	}

}