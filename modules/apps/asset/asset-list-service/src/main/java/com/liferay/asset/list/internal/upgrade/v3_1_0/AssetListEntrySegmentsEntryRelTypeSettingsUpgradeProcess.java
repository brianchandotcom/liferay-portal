/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.list.internal.upgrade.v3_1_0;

import com.liferay.asset.list.internal.util.AssetListFiltersUpgradeUtil;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Joshua Cords
 */
public class AssetListEntrySegmentsEntryRelTypeSettingsUpgradeProcess
	extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				"select ctCollectionId, alEntrySegmentsEntryRelId, " +
					"typeSettings from AssetListEntrySegmentsEntryRel");
			PreparedStatement preparedStatement2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update AssetListEntrySegmentsEntryRel set typeSettings " +
						"= ? where ctCollectionId = ? and " +
							"alEntrySegmentsEntryRelId = ?");
			ResultSet resultSet = preparedStatement1.executeQuery()) {

			while (resultSet.next()) {
				String typeSettings =
					AssetListFiltersUpgradeUtil.toUpgradedTypeSettings(
						resultSet.getString("typeSettings"));

				if (typeSettings == null) {
					continue;
				}

				preparedStatement2.setString(1, typeSettings);
				preparedStatement2.setLong(
					2, resultSet.getLong("ctCollectionId"));
				preparedStatement2.setLong(
					3, resultSet.getLong("alEntrySegmentsEntryRelId"));

				preparedStatement2.addBatch();
			}

			preparedStatement2.executeBatch();
		}
	}

}