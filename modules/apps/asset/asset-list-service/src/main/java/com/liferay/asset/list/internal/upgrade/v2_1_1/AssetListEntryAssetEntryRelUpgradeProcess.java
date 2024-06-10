/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.list.internal.upgrade.v2_1_1;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.dao.orm.common.SQLTransformer;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LoggingTimer;

/**
 * @author Lourdes Fernández Besada
 */
public class AssetListEntryAssetEntryRelUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		String sql = StringBundler.concat(
			"select distinct ctCollectionId, assetListEntryAssetEntryRelId ",
			"from AssetListEntryAssetEntryRel where not exists (select 1 from ",
			"AssetEntry where entryId = assetEntryId)");

		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			processConcurrently(
				SQLTransformer.transform(sql),
				"delete from AssetListEntryAssetEntryRel where " +
					"ctCollectionId = ? and assetListEntryAssetEntryRelId = ?",
				resultSet -> new Object[] {
					resultSet.getLong("ctCollectionId"),
					resultSet.getLong("assetListEntryAssetEntryRelId")
				},
				(values, preparedStatement) -> {
					long ctCollectionId = (Long)values[0];
					long assetListEntryAssetEntryRelId = (Long)values[1];

					preparedStatement.setLong(1, ctCollectionId);
					preparedStatement.setLong(2, assetListEntryAssetEntryRelId);

					preparedStatement.addBatch();
				},
				"Unable to update AssetListEntryAssetEntryRels");
		}
	}

}