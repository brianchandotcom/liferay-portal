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

package com.liferay.journal.upgrade.v1_0_0;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.util.UpgradeProcessUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.asset.model.AssetCategory;
import com.liferay.portlet.asset.model.AssetCategoryConstants;
import com.liferay.portlet.asset.model.AssetVocabulary;
import com.liferay.portlet.asset.util.AssetVocabularySettingsHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Eudaldo Alonso
 */
public class UpgradeJournalArticleType extends UpgradeBaseJournal {

	protected void addAssetCategory(
			long assetCategoryId, long groupId, long companyId, long userId,
			long rightAssetCategoryId, long leftAssetCategoryId, String name,
			String title, long assetVocabularyId)
		throws Exception {

		Timestamp now = new Timestamp(System.currentTimeMillis());

		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			StringBundler sb = new StringBundler(6);

			sb.append("insert into AssetCategory (uuid_, categoryId, ");
			sb.append("groupId, companyId, userId, userName, createDate, ");
			sb.append("modifiedDate, parentCategoryId, leftCategoryId, ");
			sb.append("rightCategoryId, name, title, description, ");
			sb.append("vocabularyId) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ");
			sb.append("?, ?, ?, ?, ?)");

			String sql = sb.toString();

			ps = con.prepareStatement(sql);

			ps.setString(1, PortalUUIDUtil.generate());
			ps.setLong(2, assetCategoryId);
			ps.setLong(3, groupId);
			ps.setLong(4, companyId);
			ps.setLong(5, userId);
			ps.setString(6, StringPool.BLANK);
			ps.setTimestamp(7, now);
			ps.setTimestamp(8, now);
			ps.setLong(9, AssetCategoryConstants.DEFAULT_PARENT_CATEGORY_ID);
			ps.setLong(10, rightAssetCategoryId);
			ps.setLong(11, leftAssetCategoryId);
			ps.setString(12, name);
			ps.setString(13, title);
			ps.setString(14, StringPool.BLANK);
			ps.setLong(15, assetVocabularyId);

			ps.executeUpdate();

			Map<String, Long> bitwiseValues = getBitwiseValues(
				AssetCategory.class.getName());

			List<String> actionIds = new ArrayList<>();

			actionIds.add(ActionKeys.VIEW);

			long bitwiseValue = getBitwiseValue(bitwiseValues, actionIds);

			addResourcePermission(
				companyId, AssetCategory.class.getName(), assetCategoryId,
				getRoleId(companyId, RoleConstants.GUEST), bitwiseValue);
			addResourcePermission(
				companyId, AssetCategory.class.getName(), assetCategoryId,
				getRoleId(companyId, RoleConstants.SITE_MEMBER), bitwiseValue);
		}
		catch (Exception e) {
			_log.error("Unable to add asset category");

			throw e;
		}
		finally {
			DataAccess.cleanUp(con, ps);
		}
	}

	protected void addAssetEntryToAssetCategory(
			long assetEntryId, long assetCategoryId)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"insert into AssetEntries_AssetCategories (categoryId, " +
					"entryId) values (?, ?)");

			ps.setLong(1, assetCategoryId);
			ps.setLong(2, assetEntryId);

			ps.executeUpdate();
		}
		catch (Exception e) {
			_log.error("Unable to add asset entry to asset category");

			throw e;
		}
		finally {
			DataAccess.cleanUp(con, ps);
		}
	}

	protected void addAssetVocabulary(
			long vocabularyId, long groupId, long companyId, long userId,
			String name, String title, String settings)
		throws Exception {

		Timestamp now = new Timestamp(System.currentTimeMillis());

		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			StringBundler sb = new StringBundler(4);

			sb.append("insert into AssetVocabulary (uuid_, vocabularyId, ");
			sb.append("groupId, companyId, userId, userName, createDate, ");
			sb.append("modifiedDate, name, title, description, settings_) ");
			sb.append("values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

			String sql = sb.toString();

			ps = con.prepareStatement(sql);

			ps.setString(1, PortalUUIDUtil.generate());
			ps.setLong(2, vocabularyId);
			ps.setLong(3, groupId);
			ps.setLong(4, companyId);
			ps.setLong(5, userId);
			ps.setString(6, StringPool.BLANK);
			ps.setTimestamp(7, now);
			ps.setTimestamp(8, now);
			ps.setString(9, name);
			ps.setString(10, title);
			ps.setString(11, StringPool.BLANK);
			ps.setString(12, settings);

			ps.executeUpdate();

			Map<String, Long> bitwiseValues = getBitwiseValues(
				AssetVocabulary.class.getName());

			List<String> actionIds = new ArrayList<>();

			actionIds.add(ActionKeys.VIEW);

			long bitwiseValue = getBitwiseValue(bitwiseValues, actionIds);

			addResourcePermission(
				companyId, AssetVocabulary.class.getName(), vocabularyId,
				getRoleId(companyId, RoleConstants.GUEST), bitwiseValue);
			addResourcePermission(
				companyId, AssetVocabulary.class.getName(), vocabularyId,
				getRoleId(companyId, RoleConstants.SITE_MEMBER), bitwiseValue);
		}
		catch (Exception e) {
			_log.error("Unable to add asset vocabulary");

			throw e;
		}
		finally {
			DataAccess.cleanUp(con, ps);
		}
	}

	@Override
	protected void doUpgrade() throws Exception {
		updateArticleType();
	}

	protected List<String> getArticleTypes() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"select distinct type_ from JournalArticle");

			rs = ps.executeQuery();

			List<String> types = new ArrayList<>();

			while (rs.next()) {
				types.add(rs.getString("type_"));
			}

			return types;
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected long getAssetEntryId(long classPK) throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"select entryId from AssetEntry where classNameId = ? and " +
					"classPK = ?");

			ps.setLong(
				1,
				PortalUtil.getClassNameId(
					"com.liferay.portlet.journal.model.JournalArticle"));
			ps.setLong(2, classPK);

			rs = ps.executeQuery();

			while (rs.next()) {
				return rs.getLong("entryId");
			}

			return 0;
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected boolean hasSelectedArticleTypes() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"select count(*) from JournalArticle where type_ != 'general'");

			rs = ps.executeQuery();

			while (rs.next()) {
				int count = rs.getInt(1);

				if (count > 0) {
					return true;
				}
			}

			return false;
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void updateArticles(
			long companyId,
			Map<String, Long> journalArticleTypesToAssetCategoryIds)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			StringBundler sb = new StringBundler(10);

			sb.append("select JournalArticle.resourcePrimKey, ");
			sb.append("JournalArticle.type_ from JournalArticle ");
			sb.append("left join JournalArticle tempJournalArticle on ");
			sb.append("(JournalArticle.groupId = tempJournalArticle.groupId) ");
			sb.append("and (JournalArticle.articleId = ");
			sb.append("tempJournalArticle.articleId) and ");
			sb.append(" (JournalArticle.version < ");
			sb.append("tempJournalArticle.version) where ");
			sb.append("JournalArticle.companyId = ? and ");
			sb.append("tempJournalArticle.id_ is null");

			ps = con.prepareStatement(sb.toString());

			ps.setLong(1, companyId);

			rs = ps.executeQuery();

			while (rs.next()) {
				long resourcePrimKey = rs.getLong("resourcePrimKey");
				String type = rs.getString("type_");

				long assetEntryId = getAssetEntryId(resourcePrimKey);
				long assetCategoryId =
					journalArticleTypesToAssetCategoryIds.get(type);

				if (assetEntryId > 0) {
					addAssetEntryToAssetCategory(assetEntryId, assetCategoryId);
				}
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void updateArticleType() throws Exception {
		if (!hasSelectedArticleTypes()) {
			return;
		}

		List<String> types = getArticleTypes();

		if (types.size() <= 0) {
			return;
		}

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement("select companyId from Company");

			rs = ps.executeQuery();

			while (rs.next()) {
				long vocabularyId = increment();

				long companyId = rs.getLong("companyId");

				long groupId = getCompanyGroupId(companyId);
				long userId = getDefaultUserId(companyId);

				String defaultLanguageId =
					UpgradeProcessUtil.getDefaultLanguageId(companyId);

				AssetVocabularySettingsHelper assetVocabularySettingsHelper =
					new AssetVocabularySettingsHelper();

				assetVocabularySettingsHelper.setMultiValued(false);

				assetVocabularySettingsHelper.setClassNameIdsAndClassTypePKs(
					new long[] {
						PortalUtil.getClassNameId(
							"com.liferay.portlet.journal.model.JournalArticle")
					},
					new long[] {-1}, new boolean[] {false});

				addAssetVocabulary(
					vocabularyId, groupId, companyId, userId, "type",
					localize(groupId, "type", defaultLanguageId),
					assetVocabularySettingsHelper.toString());

				Map<String, Long> journalArticleTypesToAssetCategoryIds =
					new HashMap<>();

				int i = 1;

				for (String type : types) {
					long assetCategoryId = increment();

					addAssetCategory(
						assetCategoryId, groupId, companyId, userId, i++, i++,
						type, localize(groupId, type, defaultLanguageId),
						vocabularyId);

					journalArticleTypesToAssetCategoryIds.put(
						type, assetCategoryId);
				}

				updateArticles(
					companyId, journalArticleTypesToAssetCategoryIds);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UpgradeJournalArticleType.class);

}