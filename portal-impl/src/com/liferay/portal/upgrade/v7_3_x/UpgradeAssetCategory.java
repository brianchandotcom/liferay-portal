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

package com.liferay.portal.upgrade.v7_3_x;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.dao.orm.common.SQLTransformer;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.upgrade.v7_3_x.util.AssetCategoryTable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Preston Crary
 */
public class UpgradeAssetCategory extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		if (!hasColumn("AssetCategory", "treePath")) {
			alter(
				AssetCategoryTable.class,
				new AlterTableDropColumn("leftCategoryId"),
				new AlterTableDropColumn("rightCategoryId"),
				new AlterTableAddColumn("treePath"));
		}

		try (PreparedStatement ps = connection.prepareStatement(
				SQLTransformer.transform(
					StringBundler.concat(
						"update AssetCategory set treePath = CONCAT('/', ",
						"CAST_TEXT(categoryId), '/') where treePath is null ",
						"and parentCategoryId = 0")))) {

			if (ps.executeUpdate() == 0) {
				return;
			}
		}

		try (PreparedStatement selectPS = connection.prepareStatement(
				StringBundler.concat(
					"select AssetCategory.treePath, AssetCategory.categoryId ",
					"from AssetCategory inner join AssetCategory TEMP_TABLE ",
					"on AssetCategory.categoryId = ",
					"TEMP_TABLE.parentCategoryId and AssetCategory.treePath ",
					"is not null and TEMP_TABLE.treePath is null"));
			PreparedStatement updatePS =
				AutoBatchPreparedStatementUtil.autoBatch(
					connection.prepareStatement(
						SQLTransformer.transform(
							StringBundler.concat(
								"update AssetCategory set treePath = ",
								"CONCAT(?, CAST_TEXT(categoryId), '/') where ",
								"parentCategoryId = ?"))))) {

			while (true) {
				try (ResultSet rs = selectPS.executeQuery()) {
					if (!rs.next()) {
						break;
					}

					do {
						updatePS.setString(1, rs.getString(1));
						updatePS.setLong(2, rs.getLong(2));

						updatePS.addBatch();
					}
					while (rs.next());

					updatePS.executeBatch();
				}
			}
		}
	}

}