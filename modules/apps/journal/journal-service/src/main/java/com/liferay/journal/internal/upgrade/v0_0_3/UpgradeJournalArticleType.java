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

package com.liferay.journal.internal.upgrade.v0_0_3;

import com.liferay.asset.entry.rel.service.AssetEntryAssetCategoryRelLocalService;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.asset.kernel.service.AssetVocabularyLocalService;
import com.liferay.journal.model.JournalArticle;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.util.UpgradeProcessUtil;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portlet.asset.util.AssetVocabularySettingsHelper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * @author Eudaldo Alonso
 */
public class UpgradeJournalArticleType extends UpgradeProcess {

	public UpgradeJournalArticleType(
		AssetCategoryLocalService assetCategoryLocalService,
		AssetEntryAssetCategoryRelLocalService
			assetEntryAssetCategoryRelLocalService,
		AssetEntryLocalService assetEntryLocalService,
		AssetVocabularyLocalService assetVocabularyLocalService,
		CompanyLocalService companyLocalService,
		UserLocalService userLocalService) {

		_assetCategoryLocalService = assetCategoryLocalService;
		_assetEntryAssetCategoryRelLocalService =
			assetEntryAssetCategoryRelLocalService;
		_assetEntryLocalService = assetEntryLocalService;
		_assetVocabularyLocalService = assetVocabularyLocalService;
		_companyLocalService = companyLocalService;
		_userLocalService = userLocalService;
	}

	protected AssetCategory addAssetCategory(
			long groupId, long companyId, String title, long assetVocabularyId)
		throws Exception {

		long userId = _userLocalService.getDefaultUserId(companyId);

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);

		return _assetCategoryLocalService.addCategory(
			userId, groupId, title, assetVocabularyId, serviceContext);
	}

	protected AssetVocabulary addAssetVocabulary(
			long groupId, long companyId, String title,
			Map<Locale, String> nameMap, Map<Locale, String> descriptionMap)
		throws Exception {

		long userId = _userLocalService.getDefaultUserId(companyId);

		AssetVocabularySettingsHelper assetVocabularySettingsHelper =
			new AssetVocabularySettingsHelper();

		assetVocabularySettingsHelper.setClassNameIdsAndClassTypePKs(
			new long[] {
				PortalUtil.getClassNameId(JournalArticle.class.getName())
			},
			new long[] {-1}, new boolean[] {false});
		assetVocabularySettingsHelper.setMultiValued(false);

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);

		return _assetVocabularyLocalService.addVocabulary(
			userId, groupId, title, nameMap, descriptionMap,
			assetVocabularySettingsHelper.toString(), serviceContext);
	}

	protected void alterTable() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			runSQL("alter table JournalArticle drop column type_");
		}
	}

	@Override
	protected void doUpgrade() throws Exception {
		updateArticleType();

		alterTable();
	}

	protected Set<String> getArticleTypes() throws Exception {
		try (PreparedStatement ps = connection.prepareStatement(
				"select distinct type_ from JournalArticle");
			ResultSet rs = ps.executeQuery()) {

			Set<String> types = new HashSet<>();

			while (rs.next()) {
				types.add(StringUtil.toLowerCase(rs.getString("type_")));
			}

			return types;
		}
	}

	protected boolean hasSelectedArticleTypes() throws Exception {
		try (PreparedStatement ps = connection.prepareStatement(
				"select count(*) from JournalArticle where type_ != 'general'");
			ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				int count = rs.getInt(1);

				if (count > 0) {
					return true;
				}
			}

			return false;
		}
	}

	protected void updateArticles(
			long companyId,
			Map<String, Long> journalArticleTypesToAssetCategoryIds)
		throws Exception {

		StringBundler sb = new StringBundler(8);

		sb.append("select JournalArticle.resourcePrimKey, ");
		sb.append("JournalArticle.type_ from JournalArticle left join ");
		sb.append("JournalArticle tempJournalArticle on ");
		sb.append("(JournalArticle.groupId = tempJournalArticle.groupId) and ");
		sb.append("(JournalArticle.articleId = tempJournalArticle.articleId) ");
		sb.append("and (JournalArticle.version < tempJournalArticle.version) ");
		sb.append("where JournalArticle.companyId = ? and ");
		sb.append("tempJournalArticle.id_ is null");

		try (PreparedStatement ps = connection.prepareStatement(
				sb.toString())) {

			ps.setLong(1, companyId);

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					long resourcePrimKey = rs.getLong("resourcePrimKey");

					AssetEntry assetEntry = _assetEntryLocalService.fetchEntry(
						JournalArticle.class.getName(), resourcePrimKey);

					if (assetEntry == null) {
						continue;
					}

					String type = StringUtil.toLowerCase(rs.getString("type_"));

					long assetCategoryId =
						journalArticleTypesToAssetCategoryIds.get(type);

					_assetEntryAssetCategoryRelLocalService.
						addAssetEntryAssetCategoryRel(
							assetEntry.getEntryId(), assetCategoryId);
				}
			}
		}
	}

	protected void updateArticleType() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			if (!hasSelectedArticleTypes()) {
				return;
			}

			Set<String> types = getArticleTypes();

			if (types.size() <= 0) {
				return;
			}

			Locale localeThreadLocalDefaultLocale =
				LocaleThreadLocal.getDefaultLocale();

			try {
				List<Company> companies = _companyLocalService.getCompanies();

				for (Company company : companies) {
					LocaleThreadLocal.setDefaultLocale(company.getLocale());

					Set<Locale> locales = LanguageUtil.getAvailableLocales(
						company.getGroupId());

					Locale defaultLocale = LocaleUtil.fromLanguageId(
						UpgradeProcessUtil.getDefaultLanguageId(
							company.getCompanyId()));

					Map<Locale, String> nameMap =
						LocalizationUtil.getLocalizationMap(
							locales, defaultLocale, "type");

					AssetVocabulary assetVocabulary = addAssetVocabulary(
						company.getGroupId(), company.getCompanyId(), "type",
						nameMap, new HashMap<Locale, String>());

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
						company.getCompanyId(),
						journalArticleTypesToAssetCategoryIds);
				}
			}
			finally {
				LocaleThreadLocal.setDefaultLocale(
					localeThreadLocalDefaultLocale);
			}
		}
	}

	private final AssetCategoryLocalService _assetCategoryLocalService;
	private final AssetEntryAssetCategoryRelLocalService
		_assetEntryAssetCategoryRelLocalService;
	private final AssetEntryLocalService _assetEntryLocalService;
	private final AssetVocabularyLocalService _assetVocabularyLocalService;
	private final CompanyLocalService _companyLocalService;
	private final UserLocalService _userLocalService;

}