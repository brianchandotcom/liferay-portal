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

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Eudaldo Alonso
 */
public class AssetEntryAssetCategoryRelUpgradeProcess extends UpgradeProcess {

	protected void addAssetEntryAssetCategoryRels() throws Exception {
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

	@Override
	protected void doUpgrade() throws Exception {
		upgradeSchema();

		addAssetEntryAssetCategoryRels();
	}

	protected void upgradeSchema() throws Exception {
		String template = StringUtil.read(
			AssetEntryAssetCategoryRelUpgradeProcess.class.getResourceAsStream(
				"dependencies/update.sql"));

		runSQLTemplateString(template, false);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AssetEntryAssetCategoryRelUpgradeProcess.class);

}