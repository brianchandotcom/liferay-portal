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
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.upgrade.v7_0_0.util.AssetEntryTable;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.asset.model.AssetCategoryConstants;
import com.liferay.portlet.asset.util.AssetVocabularySettingsHelper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.Arrays;

/**
 * @author Gergely Mathe
 */
public class UpgradeAsset extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
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

		updateAssetEntries();
		updateAssetVocabularies();
	}

	protected long getDDMStructureId(String structureKey) throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = connection.prepareStatement(
				"select structureId from DDMStructure where structureKey = ?");

			ps.setString(1, structureKey);

			rs = ps.executeQuery();

			if (rs.next()) {
				return rs.getLong("structureId");
			}

			return 0;
		}
		finally {
			DataAccess.cleanUp(null, ps, rs);
		}
	}

	protected void updateAssetEntries() throws Exception {
		long classNameId = PortalUtil.getClassNameId(
			"com.liferay.portlet.journal.model.JournalArticle");

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			connection = DataAccess.getUpgradeOptimizedConnection();

			ps = connection.prepareStatement(
				"select resourcePrimKey, structureId from JournalArticle " +
					"where structureId != ''");

			rs = ps.executeQuery();

			while (rs.next()) {
				long resourcePrimKey = rs.getLong("resourcePrimKey");
				String structureId = rs.getString("structureId");

				long ddmStructureId = getDDMStructureId(structureId);

				runSQL(
					"update AssetEntry set classTypeId = " + ddmStructureId +
						" where classNameId = " + classNameId +
							" and classPK = " + resourcePrimKey);
			}
		}
		finally {
			DataAccess.cleanUp(null, ps, rs);
		}

		try {
			StringBundler sb = new StringBundler(9);

			sb.append("select JournalArticle.resourcePrimKey from (select ");
			sb.append("JournalArticle.resourcePrimkey as primKey, ");
			sb.append("max(JournalArticle.version) as maxVersion from ");
			sb.append("JournalArticle group by ");
			sb.append("JournalArticle.resourcePrimkey) temp_table inner join ");
			sb.append("JournalArticle on (JournalArticle.indexable = ");
			sb.append("[$FALSE$]) and (JournalArticle.status = 0) and ");
			sb.append("(JournalArticle.resourcePrimkey = temp_table.primKey) ");
			sb.append("and (JournalArticle.version = temp_table.maxVersion)");

			ps = connection.prepareStatement(sb.toString());

			rs = ps.executeQuery();

			while (rs.next()) {
				long classPK = rs.getLong("resourcePrimKey");

				runSQL(
					"update AssetEntry set listable = [$FALSE$] where " +
						"classNameId = " + classNameId + " and classPK = " +
							classPK);
			}
		}
		finally {
			DataAccess.cleanUp(null, ps, rs);
		}
	}

	protected void updateAssetVocabularies() throws Exception {
		PreparedStatement statement = null;
		ResultSet result = null;

		try {
			statement = connection.prepareStatement(
				"select vocabularyId, settings_ from AssetVocabulary");

			result = statement.executeQuery();

			while (result.next()) {
				long vocabularyId = result.getLong("vocabularyId");
				String settings = result.getString("settings_");

				updateAssetVocabulary(
					vocabularyId, upgradeVocabularySettings(settings));
			}
		}
		finally {
			DataAccess.cleanUp(null, statement, result);
		}
	}

	protected void updateAssetVocabulary(long vocabularyId, String settings)
		throws Exception {

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = connection.prepareStatement(
				"update AssetVocabulary set settings_ = ? where vocabularyId " +
					"= ?");

			ps.setString(1, settings);
			ps.setLong(2, vocabularyId);

			ps.executeUpdate();
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to update vocabulary " + vocabularyId, e);
			}
		}
		finally {
			DataAccess.cleanUp(null, ps, rs);
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

	private static final Log _log = LogFactoryUtil.getLog(UpgradeAsset.class);

}