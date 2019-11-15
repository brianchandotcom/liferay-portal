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

package com.liferay.journal.internal.upgrade;

import com.liferay.asset.display.page.service.AssetDisplayPageEntryLocalService;
import com.liferay.asset.entry.rel.service.AssetEntryAssetCategoryRelLocalService;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.asset.kernel.service.AssetVocabularyLocalService;
import com.liferay.comment.upgrade.UpgradeDiscussionSubscriptionClassName;
import com.liferay.dynamic.data.mapping.service.DDMStorageLinkLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLinkLocalService;
import com.liferay.dynamic.data.mapping.util.DefaultDDMStructureHelper;
import com.liferay.journal.internal.upgrade.util.JournalArticleImageUpgradeHelper;
import com.liferay.journal.internal.upgrade.v0_0_2.UpgradeClassNames;
import com.liferay.journal.internal.upgrade.v0_0_3.UpgradeJournalArticleType;
import com.liferay.journal.internal.upgrade.v0_0_4.UpgradeSchema;
import com.liferay.journal.internal.upgrade.v0_0_5.UpgradeCompanyId;
import com.liferay.journal.internal.upgrade.v0_0_5.UpgradeJournal;
import com.liferay.journal.internal.upgrade.v0_0_5.UpgradeJournalArticles;
import com.liferay.journal.internal.upgrade.v0_0_5.UpgradeJournalDisplayPreferences;
import com.liferay.journal.internal.upgrade.v0_0_5.UpgradeLastPublishDate;
import com.liferay.journal.internal.upgrade.v0_0_5.UpgradePortletSettings;
import com.liferay.journal.internal.upgrade.v0_0_6.UpgradeImageTypeContentAttributes;
import com.liferay.journal.internal.upgrade.v0_0_7.UpgradeJournalArticleDates;
import com.liferay.journal.internal.upgrade.v0_0_7.UpgradeJournalArticleTreePath;
import com.liferay.journal.internal.upgrade.v0_0_8.UpgradeArticleAssets;
import com.liferay.journal.internal.upgrade.v0_0_8.UpgradeArticleExpirationDate;
import com.liferay.journal.internal.upgrade.v0_0_8.UpgradeArticleSystemEvents;
import com.liferay.journal.internal.upgrade.v1_0_0.UpgradeJournalArticleImage;
import com.liferay.journal.internal.upgrade.v1_0_1.UpgradeJournalContentSearch;
import com.liferay.journal.internal.upgrade.v1_1_0.UpgradeDocumentLibraryTypeContent;
import com.liferay.journal.internal.upgrade.v1_1_0.UpgradeImageTypeContent;
import com.liferay.journal.internal.upgrade.v1_1_0.UpgradeJournalArticleLocalizedValues;
import com.liferay.journal.internal.upgrade.v1_1_1.UpgradeFileUploadsConfiguration;
import com.liferay.journal.internal.upgrade.v1_1_2.UpgradeCheckIntervalConfiguration;
import com.liferay.journal.internal.upgrade.v1_1_3.UpgradeResourcePermissions;
import com.liferay.journal.internal.upgrade.v1_1_4.UpgradeUrlTitle;
import com.liferay.journal.internal.upgrade.v1_1_5.UpgradeContentImages;
import com.liferay.journal.internal.upgrade.v1_1_6.UpgradeAssetDisplayPageEntry;
import com.liferay.journal.internal.upgrade.v2_0_0.util.JournalArticleTable;
import com.liferay.journal.internal.upgrade.v2_0_0.util.JournalFeedTable;
import com.liferay.journal.internal.upgrade.v2_0_0.util.JournalFolderTable;
import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.configuration.upgrade.PrefsPropsToConfigurationUpgradeHelper;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.db.DBProcessContext;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepository;
import com.liferay.portal.kernel.repository.capabilities.PortalCapabilityLocator;
import com.liferay.portal.kernel.security.permission.ResourceActions;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ImageLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ResourceActionLocalService;
import com.liferay.portal.kernel.service.ResourceLocalService;
import com.liferay.portal.kernel.service.SystemEventLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.settings.SettingsFactory;
import com.liferay.portal.kernel.upgrade.BaseUpgradeSQLServerDatetime;
import com.liferay.portal.kernel.upgrade.DummyUpgradeStep;
import com.liferay.portal.kernel.upgrade.UpgradeCTModel;
import com.liferay.portal.kernel.upgrade.UpgradeMVCCVersion;
import com.liferay.portal.kernel.upgrade.UpgradeStep;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.subscription.service.SubscriptionLocalService;

import java.io.PrintWriter;

import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eduardo García
 */
@Component(
	immediate = true,
	service = {JournalServiceUpgrade.class, UpgradeStepRegistrator.class}
)
public class JournalServiceUpgrade implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.register("0.0.1", "0.0.2", new UpgradeClassNames());

		registry.register(
			"0.0.2", "0.0.3",
			new UpgradeJournalArticleType(
				_assetCategoryLocalService,
				_assetEntryAssetCategoryRelLocalService,
				_assetEntryLocalService, _assetVocabularyLocalService,
				_companyLocalService, _userLocalService));

		registry.register("0.0.3", "0.0.4", new UpgradeSchema());

		registry.register(
			"0.0.4", "0.0.5", new UpgradeCompanyId(),
			new UpgradeJournal(
				_companyLocalService, _ddmStorageLinkLocalService,
				_ddmStructureLocalService, _ddmTemplateLinkLocalService,
				_defaultDDMStructureHelper, _groupLocalService,
				_resourceActionLocalService, _resourceActions,
				_resourceLocalService, _userLocalService),
			new UpgradeJournalArticles(
				_assetCategoryLocalService, _ddmStructureLocalService,
				_groupLocalService, _layoutLocalService),
			new UpgradeJournalDisplayPreferences(),
			new UpgradeLastPublishDate(),
			new UpgradePortletSettings(_settingsFactory),
			new UpgradeStep() {

				@Override
				public void upgrade(DBProcessContext dbProcessContext) {
					try {
						deleteTempImages();
					}
					catch (Exception e) {
						e.printStackTrace(
							new PrintWriter(
								dbProcessContext.getOutputStream(), true));
					}
				}

			});

		registry.register("0.0.5", "0.0.6", new UpgradeJournalArticleImage());

		registry.register(
			"0.0.6", "0.0.7", new UpgradeImageTypeContentAttributes());

		registry.register(
			"0.0.7", "0.0.8", new UpgradeJournalArticleDates(),
			new UpgradeJournalArticleTreePath());

		registry.register(
			"0.0.8", "1.0.0",
			new UpgradeArticleAssets(
				_assetEntryLocalService, _companyLocalService),
			new UpgradeArticleExpirationDate(),
			new UpgradeArticleSystemEvents(_systemEventLocalService));

		registry.register("1.0.0", "1.0.1", new UpgradeJournalContentSearch());

		registry.register("1.0.1", "1.0.2", new DummyUpgradeStep());

		registry.register(
			"1.0.2", "1.1.0",
			new UpgradeDocumentLibraryTypeContent(
				_journalArticleImageUpgradeHelper),
			new UpgradeImageTypeContent(
				_imageLocalService, _journalArticleImageUpgradeHelper,
				_portletFileRepository),
			new UpgradeJournalArticleLocalizedValues());

		registry.register(
			"1.1.0", "1.1.1",
			new UpgradeFileUploadsConfiguration(
				_prefsPropsToConfigurationUpgradeHelper));

		registry.register(
			"1.1.1", "1.1.2",
			new UpgradeCheckIntervalConfiguration(_configurationAdmin));

		registry.register(
			"1.1.2", "1.1.3", new UpgradeResourcePermissions(_resourceActions));

		registry.register("1.1.3", "1.1.4", new UpgradeUrlTitle());

		registry.register(
			"1.1.4", "1.1.5",
			new UpgradeContentImages(_journalArticleImageUpgradeHelper));

		registry.register(
			"1.1.5", "1.1.6",
			new UpgradeAssetDisplayPageEntry(
				_assetDisplayPageEntryLocalService, _companyLocalService));

		registry.register(
			"1.1.6", "1.1.7",
			new UpgradeDiscussionSubscriptionClassName(
				_subscriptionLocalService, JournalArticle.class.getName(),
				UpgradeDiscussionSubscriptionClassName.DeletionMode.ADD_NEW));

		registry.register("1.1.7", "1.1.8", new DummyUpgradeStep());

		registry.register(
			"1.1.8", "2.0.0",
			new BaseUpgradeSQLServerDatetime(
				new Class<?>[] {
					JournalArticleTable.class, JournalFeedTable.class,
					JournalFolderTable.class
				}));

		registry.register(
			"2.0.0", "3.0.0",
			new com.liferay.journal.internal.upgrade.v3_0_0.
				UpgradeJournalArticleImage(_imageLocalService));

		registry.register(
			"3.0.0", "3.0.1",
			new com.liferay.journal.internal.upgrade.v3_0_1.
				UpgradeJournalArticle());

		registry.register("3.0.1", "3.0.2", new DummyUpgradeStep());

		registry.register(
			"3.0.2", "3.1.0",
			new UpgradeMVCCVersion() {

				@Override
				protected String[] getModuleTableNames() {
					return new String[] {
						"JournalArticle", "JournalArticleLocalization",
						"JournalArticleResource", "JournalContentSearch",
						"JournalFeed", "JournalFolder"
					};
				}

			});

		registry.register(
			"3.1.0", "3.2.0",
			new UpgradeCTModel(
				"JournalArticleLocalization", "JournalArticleResource",
				"JournalArticle", "JournalFolder"));
	}

	protected void deleteTempImages() throws Exception {
		if (_log.isDebugEnabled()) {
			_log.debug("Delete temporary images");
		}

		DB db = DBManagerUtil.getDB();

		db.runSQL(
			"delete from Image where imageId IN (SELECT articleImageId FROM " +
				"JournalArticleImage where tempImage = TRUE)");

		db.runSQL("delete from JournalArticleImage where tempImage = TRUE");
	}

	@Reference(unbind = "-")
	protected void setPortalCapabilityLocator(
		PortalCapabilityLocator portalCapabilityLocator) {

		// See LPS-82746

	}

	private static final Log _log = LogFactoryUtil.getLog(
		JournalServiceUpgrade.class);

	@Reference
	private AssetCategoryLocalService _assetCategoryLocalService;

	@Reference
	private AssetDisplayPageEntryLocalService
		_assetDisplayPageEntryLocalService;

	@Reference
	private AssetEntryAssetCategoryRelLocalService
		_assetEntryAssetCategoryRelLocalService;

	@Reference
	private AssetEntryLocalService _assetEntryLocalService;

	@Reference
	private AssetVocabularyLocalService _assetVocabularyLocalService;

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private ConfigurationAdmin _configurationAdmin;

	@Reference
	private DDMStorageLinkLocalService _ddmStorageLinkLocalService;

	@Reference
	private DDMStructureLocalService _ddmStructureLocalService;

	@Reference
	private DDMTemplateLinkLocalService _ddmTemplateLinkLocalService;

	@Reference
	private DefaultDDMStructureHelper _defaultDDMStructureHelper;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private ImageLocalService _imageLocalService;

	@Reference
	private JournalArticleImageUpgradeHelper _journalArticleImageUpgradeHelper;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED, unbind = "-")
	private ModuleServiceLifecycle _moduleServiceLifecycle;

	@Reference
	private PortletFileRepository _portletFileRepository;

	@Reference
	private PrefsPropsToConfigurationUpgradeHelper
		_prefsPropsToConfigurationUpgradeHelper;

	@Reference
	private ResourceActionLocalService _resourceActionLocalService;

	@Reference
	private ResourceActions _resourceActions;

	@Reference
	private ResourceLocalService _resourceLocalService;

	@Reference
	private SettingsFactory _settingsFactory;

	@Reference
	private SubscriptionLocalService _subscriptionLocalService;

	@Reference
	private SystemEventLocalService _systemEventLocalService;

	@Reference
	private UserLocalService _userLocalService;

}