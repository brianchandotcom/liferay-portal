/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.link.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;AssetLink&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see AssetLink
 * @generated
 */
public class AssetLinkTable extends BaseTable<AssetLinkTable> {

	public static final AssetLinkTable INSTANCE = new AssetLinkTable();

	public final Column<AssetLinkTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<AssetLinkTable, Long> ctCollectionId = createColumn(
		"ctCollectionId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<AssetLinkTable, Long> linkId = createColumn(
		"linkId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<AssetLinkTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<AssetLinkTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<AssetLinkTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<AssetLinkTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<AssetLinkTable, Long> entryId1 = createColumn(
		"entryId1", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<AssetLinkTable, Long> entryId2 = createColumn(
		"entryId2", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<AssetLinkTable, Integer> type = createColumn(
		"type_", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<AssetLinkTable, Integer> weight = createColumn(
		"weight", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);

	private AssetLinkTable() {
		super("AssetLink", AssetLinkTable::new);
	}

}