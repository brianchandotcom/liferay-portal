/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.kernel.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

/**
 * The table class for the &quot;AssetVocabularyGroupRel&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see AssetVocabularyGroupRel
 * @generated
 */
public class AssetVocabularyGroupRelTable
	extends BaseTable<AssetVocabularyGroupRelTable> {

	public static final AssetVocabularyGroupRelTable INSTANCE =
		new AssetVocabularyGroupRelTable();

	public final Column<AssetVocabularyGroupRelTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<AssetVocabularyGroupRelTable, Long> ctCollectionId =
		createColumn(
			"ctCollectionId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<AssetVocabularyGroupRelTable, String> uuid =
		createColumn("uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<AssetVocabularyGroupRelTable, Long>
		assetVocabularyGroupRelId = createColumn(
			"assetVocabularyGroupRelId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<AssetVocabularyGroupRelTable, Long> groupId =
		createColumn("groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<AssetVocabularyGroupRelTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<AssetVocabularyGroupRelTable, Long> vocabularyId =
		createColumn(
			"vocabularyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<AssetVocabularyGroupRelTable, Integer> depotEntryType =
		createColumn(
			"depotEntryType", Integer.class, Types.INTEGER,
			Column.FLAG_DEFAULT);

	private AssetVocabularyGroupRelTable() {
		super("AssetVocabularyGroupRel", AssetVocabularyGroupRelTable::new);
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:-1754020667