/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.entry.rel.internal.upgrade.v1_0_0;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Eudaldo Alonso
 */
public class AssetEntryAssetCategoryRelUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		_upgradeSchema();

		_addAssetEntryAssetCategoryRels();
	}

	private void _addAssetEntryAssetCategoryRels() throws Exception {
		processConcurrently(
			"select entryId, categoryId from AssetEntries_AssetCategories",
			resultSet -> new Object[] {
				resultSet.getLong("entryId"), resultSet.getLong("categoryId")
			},
			values -> {
				long assetEntryId = (Long)values[0];
				long assetCategoryId = (Long)values[1];

				try {
					runSQL(
						StringBundler.concat(
							"insert into AssetEntryAssetCategoryRel (",
							"assetEntryAssetCategoryRelId, assetEntryId, ",
							"assetCategoryId) values (", increment(), ", ",
							assetEntryId, ", ", assetCategoryId, ")"));
				}
				catch (Exception exception) {
					_log.error(
						StringBundler.concat(
							"Unable to add relationship for asset entry ",
							assetEntryId, " and asset category ",
							assetCategoryId),
						exception);

					throw exception;
				}
			},
			"Unable to add relationships between asset entries and asset " +
				"categories");
	}

	private void _upgradeSchema() throws Exception {
		String template = StringUtil.read(
			AssetEntryAssetCategoryRelUpgradeProcess.class.getResourceAsStream(
				"dependencies/update.sql"));

		runSQLTemplateString(template, false);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AssetEntryAssetCategoryRelUpgradeProcess.class);

}