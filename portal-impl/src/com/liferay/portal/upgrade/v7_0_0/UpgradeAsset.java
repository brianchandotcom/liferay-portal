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
import com.liferay.document.library.kernel.model.DLFileEntryConstants;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.db.DBType;
import com.liferay.portal.kernel.dao.db.DBTypeToSQLMap;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.upgrade.v7_0_0.util.AssetEntryTable;
import com.liferay.portlet.asset.util.AssetVocabularySettingsHelper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.Arrays;

/**
 * @author Gergely Mathe
 */
public class UpgradeAsset extends UpgradeProcess {

	protected void deleteOrphanedAssetEntries() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			long classNameId = PortalUtil.getClassNameId(
				DLFileEntryConstants.getClassName());

			DBTypeToSQLMap dbTypeToSQLMap = new DBTypeToSQLMap(
				StringBundler.concat(
					"delete from AssetEntry where classNameId = ", classNameId,
					" and classPK not in (select fileVersionId from ",
					"DLFileVersion) and classPK not in (select fileEntryId ",
					"from DLFileEntry)"));

			dbTypeToSQLMap.add(
				DBType.POSTGRESQL,
				StringBundler.concat(
					"delete from AssetEntry where classNameId = ", classNameId,
					" and not exists (select null from DLFileVersion where ",
					"fileVersionId = AssetEntry.classPK) and not exists ",
					"(select null from DLFileEntry where fileEntryId = ",
					"AssetEntry.classPK)"));

			runSQL(dbTypeToSQLMap);
		}
	}

	@Override
	protected void doUpgrade() throws Exception {
		alter(
			AssetEntryTable.class,
			new AlterColumnType("description", "TEXT null"),
			new AlterColumnType("summary", "TEXT null"));

		deleteOrphanedAssetEntries();
		updateAssetEntries();
		updateAssetVocabularies();
	}

	protected long getDDMStructureId(String structureKey) throws Exception {
		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"select structureId from DDMStructure where structureKey = " +
					"?")) {

			preparedStatement.setString(1, structureKey);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					return resultSet.getLong("structureId");
				}

				return 0;
			}
		}
	}

	protected void updateAssetEntries() throws Exception {
		long classNameId = PortalUtil.getClassNameId(
			"com.liferay.journal.model.JournalArticle");

		try (LoggingTimer loggingTimer = new LoggingTimer();
			PreparedStatement preparedStatement1 = connection.prepareStatement(
				StringBundler.concat(
					"update AssetEntry set listable = ? where classNameId = ? ",
					"and classPK in (select JournalArticle.resourcePrimKey as ",
					"resourcePrimKey from (select ",
					"JournalArticle.resourcePrimKey as primKey, ",
					"max(JournalArticle.version) as maxVersion from ",
					"JournalArticle group by JournalArticle.resourcePrimKey) ",
					"TEMP_TABLE inner join JournalArticle on ",
					"(JournalArticle.resourcePrimKey = TEMP_TABLE.primKey and ",
					"JournalArticle.indexable = ? and JournalArticle.status = ",
					"0 and JournalArticle.version = ",
					"TEMP_TABLE.maxVersion))"))) {

			preparedStatement1.setBoolean(1, false);
			preparedStatement1.setLong(2, classNameId);
			preparedStatement1.setBoolean(3, false);

			preparedStatement1.execute();
		}
	}

	protected void updateAssetVocabularies() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer();
			PreparedStatement preparedStatement1 = connection.prepareStatement(
				"select vocabularyId, settings_ from AssetVocabulary");
			PreparedStatement preparedStatement2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update AssetVocabulary set settings_ = ? where " +
						"vocabularyId = ?");
			ResultSet resultSet = preparedStatement1.executeQuery()) {

			while (resultSet.next()) {
				long vocabularyId = resultSet.getLong("vocabularyId");

				String settings = resultSet.getString("settings_");

				preparedStatement2.setString(
					1, upgradeVocabularySettings(settings));

				preparedStatement2.setLong(2, vocabularyId);

				preparedStatement2.addBatch();
			}

			preparedStatement2.executeBatch();
		}
	}

	protected String upgradeVocabularySettings(String settings) {
		UnicodeProperties unicodeProperties = UnicodePropertiesBuilder.create(
			true
		).fastLoad(
			settings
		).build();

		AssetVocabularySettingsHelper vocabularySettingsHelper =
			new AssetVocabularySettingsHelper();

		vocabularySettingsHelper.setMultiValued(
			GetterUtil.getBoolean(
				unicodeProperties.getProperty("multiValued"), true));

		long[] classNameIds = StringUtil.split(
			unicodeProperties.getProperty("selectedClassNameIds"), 0L);

		long[] classTypePKs = new long[classNameIds.length];

		Arrays.fill(classTypePKs, AssetCategoryConstants.ALL_CLASS_TYPE_PK);

		long[] requiredClassNameIds = StringUtil.split(
			unicodeProperties.getProperty("requiredClassNameIds"), 0L);

		boolean[] requireds = new boolean[classNameIds.length];

		for (int i = 0; i < classNameIds.length; i++) {
			requireds[i] = ArrayUtil.contains(
				requiredClassNameIds, classNameIds[i]);
		}

		vocabularySettingsHelper.setClassNameIdsAndClassTypePKs(
			classNameIds, classTypePKs, requireds);

		return vocabularySettingsHelper.toString();
	}

}