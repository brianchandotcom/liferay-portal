/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.list.internal.upgrade.v1_8_0;

import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Ricardo Couso
 */
public class AssetListEntrySegmentsEntryRelUpgradeProcess
	extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
			"select alEntrySegmentsEntryRelId, assetListEntryId from " +
			"AssetListEntrySegmentsEntryRel order by assetListEntryId asc, " +
			"priority asc, createDate desc");

			 PreparedStatement preparedStatement2 =
				 AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					 connection,
					 "update AssetListEntrySegmentsEntryRel set priority = ? " +
					 "where alEntrySegmentsEntryRelId = ?");

			ResultSet resultSet = preparedStatement1.executeQuery()) {

			long priority = 0;
			long previousAssetListEntryId = -1;

			while (resultSet.next()) {
				long assetListEntryId = resultSet.getLong("assetListEntryId");

				if (assetListEntryId != previousAssetListEntryId) {
					priority = 0;
				}

				preparedStatement2.setLong(1, priority);
				preparedStatement2.setLong(
					2, resultSet.getLong("alEntrySegmentsEntryRelId"));

				preparedStatement2.addBatch();

				previousAssetListEntryId = assetListEntryId;
				priority++;
			}

			preparedStatement2.executeBatch();
		}
	}

}