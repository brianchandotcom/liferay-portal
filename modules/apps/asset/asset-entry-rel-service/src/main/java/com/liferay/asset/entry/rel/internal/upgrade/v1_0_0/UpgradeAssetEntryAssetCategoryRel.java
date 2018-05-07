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

package com.liferay.asset.entry.rel.internal.upgrade.v1_0_0;

import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringBundler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Eudaldo Alonso
 */
public class UpgradeAssetEntryAssetCategoryRel extends UpgradeProcess {

	protected void addAssetEntryAssetCategoryRels() throws Exception {
		StringBundler sb = new StringBundler(3);

		sb.append("insert into AssetEntryAssetCategoryRel (");
		sb.append("assetEntryAssetCategoryRelId, assetEntryId, ");
		sb.append("assetCategoryId) values (?, ?, ?)");

		try (PreparedStatement ps1 = connection.prepareStatement(
				"select * from AssetEntries_AssetCategories");
			PreparedStatement ps2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection, sb.toString());
			ResultSet rs = ps1.executeQuery()) {

			while (rs.next()) {
				long assetEntryId = rs.getLong("entryId");
				long assetCategoryId = rs.getLong("categoryId");

				ps2.setLong(1, increment());
				ps2.setLong(2, assetEntryId);
				ps2.setLong(3, assetCategoryId);

				ps2.executeUpdate();
			}

			ps2.executeBatch();
		}
	}

	@Override
	protected void doUpgrade() throws Exception {
		addAssetEntryAssetCategoryRels();
	}

}