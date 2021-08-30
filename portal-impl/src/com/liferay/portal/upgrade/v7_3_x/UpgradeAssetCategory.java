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

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Preston Crary
 */
public class UpgradeAssetCategory extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		if (!hasColumn("AssetCategory", "treePath")) {
			alterTableDropColumn("AssetCategory", "leftCategoryId");
			alterTableDropColumn("AssetCategory", "rightCategoryId");
			alterTableAddColumn("AssetCategory", "treePath", "STRING null");
		}

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				SQLTransformer.transform(
					StringBundler.concat(
						"update AssetCategory set treePath = CONCAT('/', ",
						"CAST_TEXT(categoryId), '/') where treePath is null ",
						"and parentCategoryId = 0")))) {

			if (preparedStatement.executeUpdate() == 0) {
				return;
			}
		}

		try (PreparedStatement selectPreparedStatement =
				connection.prepareStatement(
					StringBundler.concat(
						"select AssetCategory.treePath, ",
						"AssetCategory.categoryId from AssetCategory inner ",
						"join AssetCategory TEMP_TABLE on ",
						"AssetCategory.categoryId = ",
						"TEMP_TABLE.parentCategoryId and ",
						"AssetCategory.treePath is not null and ",
						"TEMP_TABLE.treePath is null"));
			PreparedStatement updatePreparedStatement =
				AutoBatchPreparedStatementUtil.autoBatch(
					connection.prepareStatement(
						SQLTransformer.transform(
							StringBundler.concat(
								"update AssetCategory set treePath = ",
								"CONCAT(?, CAST_TEXT(categoryId), '/') where ",
								"parentCategoryId = ?"))))) {

			while (true) {
				try (ResultSet resultSet =
						selectPreparedStatement.executeQuery()) {

					if (!resultSet.next()) {
						return;
					}

					do {
						updatePreparedStatement.setString(
							1, resultSet.getString(1));
						updatePreparedStatement.setLong(
							2, resultSet.getLong(2));

						updatePreparedStatement.addBatch();
					}
					while (resultSet.next());

					updatePreparedStatement.executeBatch();
				}
			}
		}
	}

}