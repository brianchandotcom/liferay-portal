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

import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.model.Company;
import com.liferay.portal.service.CompanyLocalService;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserLocalService;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.asset.model.AssetCategory;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.model.AssetVocabulary;
import com.liferay.portlet.asset.service.AssetCategoryLocalService;
import com.liferay.portlet.asset.service.AssetEntryLocalService;
import com.liferay.portlet.asset.service.AssetVocabularyLocalService;
import com.liferay.portlet.asset.util.AssetVocabularySettingsHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Eudaldo Alonso
 */
public class UpgradeJournalArticleType extends UpgradeBaseJournal {

	public UpgradeJournalArticleType(
		AssetCategoryLocalService assetCategoryLocalService,
		AssetEntryLocalService assetEntryLocalService,
		AssetVocabularyLocalService assetVocabularyLocalService,
		CompanyLocalService companyLocalService,
		UserLocalService userLocalService) {

		_assetCategoryLocalService = assetCategoryLocalService;
		_assetEntryLocalService = assetEntryLocalService;
		_assetVocabularyLocalService = assetVocabularyLocalService;
		_companyLocalService = companyLocalService;
		_userLocalService = userLocalService;
	}

	protected AssetCategory addAssetCategory(
			long groupId, long companyId, String title, long assetVocabularyId)
		throws Exception {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);

		long userId = _userLocalService.getDefaultUserId(companyId);

		return _assetCategoryLocalService.addCategory(
			userId, groupId, title, assetVocabularyId, serviceContext);
	}

	protected AssetVocabulary addAssetVocabulary(
			long groupId, long companyId, String title,
			Map<Locale, String> nameMap, Map<Locale, String> descriptionMap)
		throws Exception {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);

		long userId = _userLocalService.getDefaultUserId(companyId);

		AssetVocabularySettingsHelper assetVocabularySettingsHelper =
			new AssetVocabularySettingsHelper();

		assetVocabularySettingsHelper.setMultiValued(false);

		assetVocabularySettingsHelper.setClassNameIdsAndClassTypePKs(
			new long[] {
				PortalUtil.getClassNameId(JournalArticle.class.getName())
			},
			new long[] {-1}, new boolean[] {false});

		return _assetVocabularyLocalService.addVocabulary(
			userId, groupId, title, nameMap, descriptionMap,
			assetVocabularySettingsHelper.toString(), serviceContext);
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

				AssetEntry assetEntry = _assetEntryLocalService.fetchEntry(
					JournalArticle.class.getName(), resourcePrimKey);

				long assetCategoryId =
					journalArticleTypesToAssetCategoryIds.get(type);

				if (assetEntry != null) {
					_assetEntryLocalService.addAssetCategoryAssetEntry(
						assetCategoryId, assetEntry);
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

		List<Company> companies = _companyLocalService.getCompanies();

		for (Company company : companies) {
			AssetVocabulary assetVocabulary = addAssetVocabulary(
				company.getGroupId(), company.getCompanyId(), "type",
				localize(company.getGroupId(), "type"),
				new HashMap<Locale, String>());

			Map<String, Long> journalArticleTypesToAssetCategoryIds =
				new HashMap<>();

			for (String type : types) {
				AssetCategory assetCategory = addAssetCategory(
					company.getGroupId(), company.getCompanyId(), type,
					assetVocabulary.getVocabularyId());

				journalArticleTypesToAssetCategoryIds.put(
					type, assetCategory.getCategoryId());
			}

			updateArticles(
				company.getCompanyId(), journalArticleTypesToAssetCategoryIds);
		}
	}

	private final AssetCategoryLocalService _assetCategoryLocalService;
	private final AssetEntryLocalService _assetEntryLocalService;
	private final AssetVocabularyLocalService _assetVocabularyLocalService;
	private final CompanyLocalService _companyLocalService;
	private final UserLocalService _userLocalService;

}