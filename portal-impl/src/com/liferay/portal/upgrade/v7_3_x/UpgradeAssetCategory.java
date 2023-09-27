/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.v7_3_x;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.dao.orm.common.SQLTransformer;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeProcessFactory;
import com.liferay.portal.kernel.upgrade.UpgradeStep;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Preston Crary
 */
public class UpgradeAssetCategory extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
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
					connection,
					SQLTransformer.transform(
						StringBundler.concat(
							"update AssetCategory set treePath = CONCAT(?, ",
							"CAST_TEXT(categoryId), '/') where ",
							"parentCategoryId = ?")))) {

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

	@Override
	protected UpgradeStep[] getPreUpgradeSteps() {
		return new UpgradeStep[] {
			UpgradeProcessFactory.dropColumns(
				"AssetCategory", "leftCategoryId", "rightCategoryId"),
			UpgradeProcessFactory.addColumns(
				"AssetCategory", "treePath STRING null")
		};
	}

}