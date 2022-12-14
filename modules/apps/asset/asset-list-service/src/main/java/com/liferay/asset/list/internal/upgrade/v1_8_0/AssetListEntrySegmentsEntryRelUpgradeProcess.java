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