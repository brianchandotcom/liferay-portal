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

import com.liferay.asset.kernel.model.AssetCategoryConstants;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.upgrade.AutoBatchPreparedStatementUtil;
import com.liferay.portlet.asset.util.AssetVocabularySettingsHelper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.Arrays;

/**
 * @author Gergely Mathe
 */
public class UpgradeAssetVocabulary extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		updateAssetVocabularies();
	}

	protected long getDDMStructureId(String structureKey) throws Exception {
		try (PreparedStatement ps = connection.prepareStatement(
				"select structureId from DDMStructure where structureKey = " +
					"?")) {

			ps.setString(1, structureKey);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return rs.getLong("structureId");
				}

				return 0;
			}
		}
	}

	protected void updateAssetVocabularies() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer();
			PreparedStatement ps1 = connection.prepareStatement(
				"select vocabularyId, settings_ from AssetVocabulary");
			PreparedStatement ps2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update AssetVocabulary set settings_ = ? where " +
						"vocabularyId = ?");
			ResultSet rs = ps1.executeQuery()) {

			while (rs.next()) {
				long vocabularyId = rs.getLong("vocabularyId");
				String settings = rs.getString("settings_");

				ps2.setString(1, upgradeVocabularySettings(settings));
				ps2.setLong(2, vocabularyId);

				ps2.addBatch();
			}

			ps2.executeBatch();
		}
	}

	protected String upgradeVocabularySettings(String settings) {
		UnicodeProperties properties = new UnicodeProperties(true);

		properties.fastLoad(settings);

		AssetVocabularySettingsHelper vocabularySettingsHelper =
			new AssetVocabularySettingsHelper();

		vocabularySettingsHelper.setMultiValued(
			GetterUtil.getBoolean(properties.getProperty("multiValued"), true));

		long[] classNameIds = StringUtil.split(
			properties.getProperty("selectedClassNameIds"), 0L);

		long[] classTypePKs = new long[classNameIds.length];

		Arrays.fill(classTypePKs, AssetCategoryConstants.ALL_CLASS_TYPE_PK);

		long[] requiredClassNameIds = StringUtil.split(
			properties.getProperty("requiredClassNameIds"), 0L);

		boolean[] requireds = new boolean[classNameIds.length];

		for (int i = 0; i < classNameIds.length; i++) {
			requireds[i] = ArrayUtil.contains(
				requiredClassNameIds, classNameIds[i]);
		}

		vocabularySettingsHelper.setClassNameIdsAndClassTypePKs(
			classNameIds, classTypePKs, requireds);

		return vocabularySettingsHelper.toString();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UpgradeAssetVocabulary.class);

}