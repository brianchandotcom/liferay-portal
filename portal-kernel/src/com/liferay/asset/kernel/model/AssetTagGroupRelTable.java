/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.kernel.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

/**
 * The table class for the &quot;AssetTagGroupRel&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see AssetTagGroupRel
 * @generated
 */
public class AssetTagGroupRelTable extends BaseTable<AssetTagGroupRelTable> {

	public static final AssetTagGroupRelTable INSTANCE =
		new AssetTagGroupRelTable();

	public final Column<AssetTagGroupRelTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<AssetTagGroupRelTable, Long> ctCollectionId =
		createColumn(
			"ctCollectionId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<AssetTagGroupRelTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<AssetTagGroupRelTable, Long> assetTagGroupRelId =
		createColumn(
			"assetTagGroupRelId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<AssetTagGroupRelTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<AssetTagGroupRelTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<AssetTagGroupRelTable, Long> tagId = createColumn(
		"tagId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);

	private AssetTagGroupRelTable() {
		super("AssetTagGroupRel", AssetTagGroupRelTable::new);
	}

}