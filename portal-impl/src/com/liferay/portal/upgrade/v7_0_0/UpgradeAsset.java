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

package com.liferay.portal.upgrade.v7_0_0;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.upgrade.v7_0_0.util.AssetEntryTable;
import com.liferay.portlet.asset.model.AssetCategoryConstants;
import com.liferay.portlet.asset.util.AssetVocabularySettingsHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Gergely Mathe
 */
public class UpgradeAsset extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		doUpgradeAssetEntry();
		doUpgradeAllAssetVocabularies();
	}

	protected void doUpgradeAllAssetVocabularies() throws Exception {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet result = null;

		try {
			connection = DataAccess.getUpgradeOptimizedConnection();

			statement = connection.prepareStatement(
				"SELECT vocabularyId, settings_ FROM AssetVocabulary");

			result = statement.executeQuery();

			while (result.next()) {
				Long key = result.getLong("vocabularyId");
				String settings = result.getString("settings_");

				String newSettings = doUpgradeVocabularySettings(settings);

				runSQL(
					"UPDATE AssetVocabulary SET settings_ = '" + newSettings +
						"' WHERE vocabularyId = " + key);
			}
		}
		finally {
			DataAccess.cleanUp(connection, statement, result);
		}
	}

	protected void doUpgradeAssetEntry() throws Exception {
		try {
			runSQL("alter_column_type AssetEntry description TEXT null");
			runSQL("alter_column_type AssetEntry summary TEXT null");
		}
		catch (SQLException sqle) {
			upgradeTable(
				AssetEntryTable.TABLE_NAME, AssetEntryTable.TABLE_COLUMNS,
				AssetEntryTable.TABLE_SQL_CREATE,
				AssetEntryTable.TABLE_SQL_ADD_INDEXES);
		}
	}

	protected String doUpgradeVocabularySettings(String settings) {
		UnicodeProperties oldProperties = new UnicodeProperties(true);
		oldProperties.fastLoad(settings);

		AssetVocabularySettingsHelper newProperties =
			new AssetVocabularySettingsHelper();

		newProperties.setMultiValued(
			GetterUtil.getBoolean(
				oldProperties.getProperty("multiValued"), true));

		long[] classNameIds = StringUtil.split(
			oldProperties.getProperty("selectedClassNameIds"), 0L);

		long[] requiredClassNameIds = StringUtil.split(
			oldProperties.getProperty("requiredClassNameIds"), 0L);

		long[] classTypeIds = new long[classNameIds.length];

		Arrays.fill(classTypeIds, AssetCategoryConstants.ALL_CLASS_TYPE_IDS);

		boolean[] requireds = new boolean[classNameIds.length];

		for (int i = 0; i < classNameIds.length; i++) {
			requireds[i] = ArrayUtil.contains(
				requiredClassNameIds, classNameIds[i]);
		}

		newProperties.setClassNameAndTypeIds(
			classNameIds, classTypeIds, requireds);

		return newProperties.toString();
	}

}