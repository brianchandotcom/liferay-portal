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

package com.liferay.journal.internal.upgrade.v4_4_4;

import com.liferay.asset.entry.rel.service.AssetEntryAssetCategoryRelLocalService;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.asset.kernel.service.AssetVocabularyLocalService;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalFeed;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.util.UpgradeProcessUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Localization;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portlet.asset.util.AssetVocabularySettingsHelper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * @author Georgel Pop
 */
public class JournalFeedTypeUpgradeProcess extends UpgradeProcess {

	public JournalFeedTypeUpgradeProcess(
		AssetCategoryLocalService assetCategoryLocalService,
		AssetEntryAssetCategoryRelLocalService
			assetEntryAssetCategoryRelLocalService,
		AssetEntryLocalService assetEntryLocalService,
		AssetVocabularyLocalService assetVocabularyLocalService,
		CompanyLocalService companyLocalService, Language language,
		Localization localization, Portal portal,
		UserLocalService userLocalService) {

		_assetCategoryLocalService = assetCategoryLocalService;
		_assetEntryAssetCategoryRelLocalService =
			assetEntryAssetCategoryRelLocalService;
		_assetEntryLocalService = assetEntryLocalService;
		_assetVocabularyLocalService = assetVocabularyLocalService;
		_companyLocalService = companyLocalService;
		_language = language;
		_localization = localization;
		_portal = portal;
		_userLocalService = userLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		if (hasColumn("JournalFeed", "type_")) {
			_upgradeFeedType();
			_alterTable();
		}
		else {
			_upgradeFeedsToAssets();
		}
	}

	private AssetCategory _addAssetCategory(
			long groupId, long companyId, String title, long assetVocabularyId)
		throws Exception {

		long userId = _userLocalService.getDefaultUserId(companyId);

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);

		return _assetCategoryLocalService.addCategory(
			userId, groupId, title, assetVocabularyId, serviceContext);
	}

	private AssetVocabulary _addAssetVocabulary(
			long groupId, long companyId, String title,
			Map<Locale, String> nameMap)
		throws Exception {

		long userId = _userLocalService.getDefaultUserId(companyId);

		AssetVocabularySettingsHelper assetVocabularySettingsHelper =
			new AssetVocabularySettingsHelper();

		assetVocabularySettingsHelper.setClassNameIdsAndClassTypePKs(
			new long[] {_portal.getClassNameId(JournalArticle.class.getName())},
			new long[] {-1}, new boolean[] {false});
		assetVocabularySettingsHelper.setMultiValued(false);

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);

		return _assetVocabularyLocalService.addVocabulary(
			userId, groupId, title, nameMap, Collections.emptyMap(),
			assetVocabularySettingsHelper.toString(), serviceContext);
	}

	private void _alterTable() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			alterTableDropColumn("JournalFeed", "type_");
		}
	}

	private Set<String> _getArticleTypes(long companyId) throws Exception {
		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"select distinct type_ from JournalFeed where companyId = " +
					companyId);
			ResultSet resultSet = preparedStatement.executeQuery()) {

			Set<String> types = new HashSet<>();

			while (resultSet.next()) {
				types.add(StringUtil.toLowerCase(resultSet.getString("type_")));
			}

			return types;
		}
	}

	private void _upgradeFeedsToAssets() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			_companyLocalService.forEachCompanyId(
				companyId -> {
					try (PreparedStatement preparedStatement =
							connection.prepareStatement(
								StringBundler.concat(
									"select uuid_,id_,groupid,userid, ",
									"createdate,modifieddate,name,description ",
									"from JournalFeed where companyId = ",
									companyId));
						ResultSet resultSet =
							preparedStatement.executeQuery()) {

						while (resultSet.next()) {
							long id = resultSet.getLong("id_");

							AssetEntry assetEntry =
								_assetEntryLocalService.fetchEntry(
									JournalFeed.class.getName(), id);

							if (assetEntry == null) {
								String uuid = resultSet.getString("uuid_");
								long groupId = resultSet.getLong("groupid");
								long userId = resultSet.getLong("userid");
								Date createDate = resultSet.getDate(
									"createdate");
								Date modifiedDate = resultSet.getDate(
									"modifieddate");
								String name = resultSet.getString("name");
								String description = resultSet.getString(
									"description");

								if (_userLocalService.fetchUser(userId) ==
										null) {

									userId = _userLocalService.getDefaultUserId(
										companyId);
								}

								_assetEntryLocalService.updateEntry(
									userId, groupId, createDate, modifiedDate,
									JournalFeed.class.getName(), id, uuid, 0,
									new long[0], new String[0], true, true,
									null, null, createDate, null,
									ContentTypes.TEXT_PLAIN, name, description,
									null, null, null, 0, 0, 0.0);
							}
						}
					}
				});
		}
	}

	private void _upgradeFeedsToAssets(
			long companyId,
			Map<String, Long> journalArticleTypesToAssetCategoryIds)
		throws Exception {

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				StringBundler.concat(
					"select uuid_,id_,groupid,userid,createdate, ",
					"modifieddate,name,description,type_ from JournalFeed ",
					"where companyId = ", companyId));
			ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				long id = resultSet.getLong("id_");

				AssetEntry assetEntry = _assetEntryLocalService.fetchEntry(
					JournalFeed.class.getName(), id);

				if (assetEntry == null) {
					String uuid = resultSet.getString("uuid_");
					long groupId = resultSet.getLong("groupid");
					long userId = resultSet.getLong("userid");
					Date createDate = resultSet.getDate("createdate");
					Date modifiedDate = resultSet.getDate("modifieddate");
					String name = resultSet.getString("name");
					String description = resultSet.getString("description");

					if (_userLocalService.fetchUser(userId) == null) {
						userId = _userLocalService.getDefaultUserId(companyId);
					}

					assetEntry = _assetEntryLocalService.updateEntry(
						userId, groupId, createDate, modifiedDate,
						JournalFeed.class.getName(), id, uuid, 0, new long[0],
						new String[0], true, true, null, null, createDate, null,
						ContentTypes.TEXT_PLAIN, name, description, null, null,
						null, 0, 0, 0.0);

					String type = resultSet.getString("type_");

					if (Validator.isNotNull(type)) {
						long assetCategoryId =
							journalArticleTypesToAssetCategoryIds.get(type);

						_assetEntryAssetCategoryRelLocalService.
							addAssetEntryAssetCategoryRel(
								assetEntry.getEntryId(), assetCategoryId);
					}
				}
			}
		}
	}

	private void _upgradeFeedType() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			Locale localeThreadLocalDefaultLocale =
				LocaleThreadLocal.getDefaultLocale();

			try {
				_companyLocalService.forEachCompany(
					company -> {
						Set<String> types = _getArticleTypes(
							company.getCompanyId());

						if (SetUtil.isEmpty(types)) {
							return;
						}

						LocaleThreadLocal.setDefaultLocale(company.getLocale());

						AssetVocabulary assetVocabulary =
							_assetVocabularyLocalService.getGroupVocabulary(
								company.getGroupId(), "type");

						if (assetVocabulary == null) {
							assetVocabulary = _addAssetVocabulary(
								company.getGroupId(), company.getCompanyId(),
								"type",
								_localization.getLocalizationMap(
									_language.getAvailableLocales(
										company.getGroupId()),
									LocaleUtil.fromLanguageId(
										UpgradeProcessUtil.getDefaultLanguageId(
											company.getCompanyId())),
									"type"));
						}

						Map<String, Long>
							journalArticleTypesToAssetCategoryIds =
								new HashMap<>();

						for (String type : types) {
							if (Validator.isNull(type)) {
								continue;
							}

							AssetCategory assetCategory =
								_verifyIfAssetCategoryExists(
									assetVocabulary.getCategories(), type);

							if (assetCategory == null) {
								assetCategory = _addAssetCategory(
									company.getGroupId(),
									company.getCompanyId(), type,
									assetVocabulary.getVocabularyId());
							}

							journalArticleTypesToAssetCategoryIds.put(
								type, assetCategory.getCategoryId());
						}

						_upgradeFeedsToAssets(
							company.getCompanyId(),
							journalArticleTypesToAssetCategoryIds);
					});
			}
			finally {
				LocaleThreadLocal.setDefaultLocale(
					localeThreadLocalDefaultLocale);
			}
		}
	}

	private AssetCategory _verifyIfAssetCategoryExists(
		List<AssetCategory> categories, String categoryName) {

		for (AssetCategory category : categories) {
			if (categoryName.equals(category.getName())) {
				return category;
			}
		}

		return null;
	}

	private final AssetCategoryLocalService _assetCategoryLocalService;
	private final AssetEntryAssetCategoryRelLocalService
		_assetEntryAssetCategoryRelLocalService;
	private final AssetEntryLocalService _assetEntryLocalService;
	private final AssetVocabularyLocalService _assetVocabularyLocalService;
	private final CompanyLocalService _companyLocalService;
	private final Language _language;
	private final Localization _localization;
	private final Portal _portal;
	private final UserLocalService _userLocalService;

}