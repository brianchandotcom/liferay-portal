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
import com.liferay.counter.kernel.service.CounterLocalService;
import com.liferay.dynamic.data.mapping.service.DDMFieldLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStorageLinkLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLinkLocalService;
import com.liferay.dynamic.data.mapping.util.DefaultDDMStructureHelper;
import com.liferay.dynamic.data.mapping.util.FieldsToDDMFormValuesConverter;
import com.liferay.journal.content.compatibility.converter.JournalContentCompatibilityConverter;
import com.liferay.journal.internal.upgrade.helper.JournalArticleImageUpgradeHelper;
import com.liferay.journal.internal.upgrade.v0_0_3.JournalArticleTypeUpgradeProcess;
import com.liferay.journal.internal.upgrade.v0_0_4.SchemaUpgradeProcess;
import com.liferay.journal.internal.upgrade.v0_0_5.JournalUpgradeProcess;
import com.liferay.journal.internal.upgrade.v0_0_5.UpgradeCompanyId;
import com.liferay.journal.internal.upgrade.v0_0_5.UpgradeJournalArticles;
import com.liferay.journal.internal.upgrade.v0_0_5.UpgradeJournalDisplayPreferences;
import com.liferay.journal.internal.upgrade.v0_0_5.UpgradeLastPublishDate;
import com.liferay.journal.internal.upgrade.v0_0_5.UpgradePortletSettings;
import com.liferay.journal.internal.upgrade.v0_0_6.ImageTypeContentAttributesUpgradeProcess;
import com.liferay.journal.internal.upgrade.v0_0_7.JournalArticleDatesUpgradeProcess;
import com.liferay.journal.internal.upgrade.v0_0_7.JournalArticleTreePathUpgradeProcess;
import com.liferay.journal.internal.upgrade.v0_0_8.ArticleAssetsUpgradeProcess;
import com.liferay.journal.internal.upgrade.v0_0_8.ArticleExpirationDateUpgradeProcess;
import com.liferay.journal.internal.upgrade.v0_0_8.ArticleSystemEventsUpgradeProcess;
import com.liferay.journal.internal.upgrade.v1_0_0.JournalArticleImageUpgradeProcess;
import com.liferay.journal.internal.upgrade.v1_0_1.JournalContentSearchUpgradeProcess;
import com.liferay.journal.internal.upgrade.v1_1_0.DocumentLibraryTypeContentUpgradeProcess;
import com.liferay.journal.internal.upgrade.v1_1_0.ImageTypeContentUpgradeProcess;
import com.liferay.journal.internal.upgrade.v1_1_0.JournalArticleLocalizedValuesUpgradeProcess;
import com.liferay.journal.internal.upgrade.v1_1_1.FileUploadsConfigurationUpgradeProcess;
import com.liferay.journal.internal.upgrade.v1_1_2.CheckIntervalConfigurationUpgradeProcess;
import com.liferay.journal.internal.upgrade.v1_1_3.ResourcePermissionsUpgradeProcess;
import com.liferay.journal.internal.upgrade.v1_1_5.ContentImagesUpgradeProcess;
import com.liferay.journal.internal.upgrade.v1_1_6.AssetDisplayPageEntryUpgradeProcess;
import com.liferay.journal.internal.upgrade.v2_0_0.util.JournalArticleTable;
import com.liferay.journal.internal.upgrade.v2_0_0.util.JournalFeedTable;
import com.liferay.journal.internal.upgrade.v2_0_0.util.JournalFolderTable;
import com.liferay.journal.internal.upgrade.v3_3_0.StorageLinksUpgradeProcess;
import com.liferay.journal.internal.upgrade.v3_5_0.JournalArticleContentUpgradeProcess;
import com.liferay.journal.internal.upgrade.v3_5_1.JournalArticleDataFileEntryIdUpgradeProcess;
import com.liferay.journal.internal.upgrade.v4_0_0.JournalArticleDDMFieldsUpgradeProcess;
import com.liferay.journal.internal.upgrade.v4_1_0.JournalArticleExternalReferenceCodeUpgradeProcess;
import com.liferay.journal.internal.upgrade.v4_2_0.JournalFeedUpgradeProcess;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.util.JournalConverter;
import com.liferay.portal.change.tracking.store.CTStoreFactory;
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
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ImageLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.PortletPreferenceValueLocalService;
import com.liferay.portal.kernel.service.PortletPreferencesLocalService;
import com.liferay.portal.kernel.service.ResourceActionLocalService;
import com.liferay.portal.kernel.service.ResourceLocalService;
import com.liferay.portal.kernel.service.SystemEventLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.settings.SettingsFactory;
import com.liferay.portal.kernel.upgrade.BaseSQLServerDatetimeUpgradeProcess;
import com.liferay.portal.kernel.upgrade.CTModelUpgradeProcess;
import com.liferay.portal.kernel.upgrade.DummyUpgradeStep;
import com.liferay.portal.kernel.upgrade.MVCCVersionUpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeStep;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portlet.documentlibrary.store.StoreFactory;
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
		registry.register("0.0.1", "0.0.2", new DummyUpgradeStep());

		registry.register(
			"0.0.2", "0.0.3",
			new JournalArticleTypeUpgradeProcess(
				_assetCategoryLocalService,
				_assetEntryAssetCategoryRelLocalService,
				_assetEntryLocalService, _assetVocabularyLocalService,
				_companyLocalService, _userLocalService));

		registry.register("0.0.3", "0.0.4", new SchemaUpgradeProcess());

		registry.register(
			"0.0.4", "0.0.5", new UpgradeCompanyId(),
			new JournalUpgradeProcess(
				_companyLocalService, _ddmStorageLinkLocalService,
				_ddmStructureLocalService, _ddmTemplateLinkLocalService,
				_defaultDDMStructureHelper, _groupLocalService,
				_resourceActionLocalService, _resourceActions,
				_resourceLocalService, _userLocalService),
			new UpgradeJournalArticles(
				_assetCategoryLocalService, _ddmStructureLocalService,
				_groupLocalService, _layoutLocalService,
				_portletPreferenceValueLocalService,
				_portletPreferencesLocalService),
			new UpgradeJournalDisplayPreferences(),
			new UpgradeLastPublishDate(),
			new UpgradePortletSettings(_settingsFactory),
			new UpgradeStep() {

				@Override
				public void upgrade(DBProcessContext dbProcessContext) {
					try {
						_deleteTempImages();
					}
					catch (Exception exception) {
						exception.printStackTrace(
							new PrintWriter(
								dbProcessContext.getOutputStream(), true));
					}
				}

			});

		registry.register(
			"0.0.5", "0.0.6", new JournalArticleImageUpgradeProcess());

		registry.register(
			"0.0.6", "0.0.7", new ImageTypeContentAttributesUpgradeProcess());

		registry.register(
			"0.0.7", "0.0.8", new JournalArticleDatesUpgradeProcess(),
			new JournalArticleTreePathUpgradeProcess());

		registry.register(
			"0.0.8", "1.0.0",
			new ArticleAssetsUpgradeProcess(
				_assetEntryLocalService, _companyLocalService),
			new ArticleExpirationDateUpgradeProcess(),
			new ArticleSystemEventsUpgradeProcess(_systemEventLocalService));

		registry.register(
			"1.0.0", "1.0.1", new JournalContentSearchUpgradeProcess());

		registry.register("1.0.1", "1.0.2", new DummyUpgradeStep());

		registry.register(
			"1.0.2", "1.1.0",
			new DocumentLibraryTypeContentUpgradeProcess(
				_journalArticleImageUpgradeHelper),
			new ImageTypeContentUpgradeProcess(
				_imageLocalService, _journalArticleImageUpgradeHelper,
				_portletFileRepository),
			new JournalArticleLocalizedValuesUpgradeProcess(
				_counterLocalService));

		registry.register(
			"1.1.0", "1.1.1",
			new FileUploadsConfigurationUpgradeProcess(
				_prefsPropsToConfigurationUpgradeHelper));

		registry.register(
			"1.1.1", "1.1.2",
			new CheckIntervalConfigurationUpgradeProcess(_configurationAdmin));

		registry.register(
			"1.1.2", "1.1.3",
			new ResourcePermissionsUpgradeProcess(_resourceActions));

		registry.register(
			"1.1.3", "1.1.4",
			new com.liferay.journal.internal.upgrade.v1_1_4.
				JournalArticleUpgradeProcess());

		registry.register(
			"1.1.4", "1.1.5",
			new ContentImagesUpgradeProcess(_journalArticleImageUpgradeHelper));

		registry.register(
			"1.1.5", "1.1.6",
			new AssetDisplayPageEntryUpgradeProcess(
				_assetDisplayPageEntryLocalService, _companyLocalService));

		registry.register(
			"1.1.6", "1.1.7",
			new UpgradeDiscussionSubscriptionClassName(
				_classNameLocalService, _subscriptionLocalService,
				JournalArticle.class.getName(),
				UpgradeDiscussionSubscriptionClassName.DeletionMode.UPDATE));

		registry.register(
			"1.1.7", "1.1.8",
			new com.liferay.journal.internal.upgrade.v1_1_8.
				JournalArticleUpgradeProcess());

		registry.register(
			"1.1.8", "2.0.0",
			new BaseSQLServerDatetimeUpgradeProcess(
				new Class<?>[] {
					JournalArticleTable.class, JournalFeedTable.class,
					JournalFolderTable.class
				}));

		registry.register(
			"2.0.0", "3.0.0",
			new com.liferay.journal.internal.upgrade.v3_0_0.
				JournalArticleImageUpgradeProcess(_imageLocalService));

		registry.register("3.0.0", "3.0.1", new DummyUpgradeStep());

		registry.register("3.0.1", "3.0.2", new DummyUpgradeStep());

		registry.register(
			"3.0.2", "3.1.0",
			new MVCCVersionUpgradeProcess() {

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
			new CTModelUpgradeProcess(
				"JournalArticleLocalization", "JournalArticleResource",
				"JournalArticle", "JournalFolder"));

		registry.register("3.2.0", "3.2.1", new DummyUpgradeStep());

		registry.register("3.2.1", "3.2.2", new DummyUpgradeStep());

		registry.register("3.2.2", "3.2.3", new DummyUpgradeStep());

		registry.register("3.2.3", "3.2.4", new DummyUpgradeStep());

		registry.register(
			"3.2.4", "3.3.0",
			new CTModelUpgradeProcess("JournalContentSearch", "JournalFeed"));

		registry.register(
			"3.3.0", "3.4.0",
			new StorageLinksUpgradeProcess(_classNameLocalService));

		registry.register("3.4.0", "3.4.1", new DummyUpgradeStep());

		registry.register(
			"3.4.1", "3.5.0",
			new JournalArticleContentUpgradeProcess(
				_journalContentCompatibilityConverter));

		registry.register(
			"3.5.0", "3.5.1",
			new JournalArticleDataFileEntryIdUpgradeProcess());

		registry.register(
			"3.5.1", "4.0.0",
			new JournalArticleDDMFieldsUpgradeProcess(
				_classNameLocalService, _ddmFieldLocalService,
				_ddmStructureLocalService, _fieldsToDDMFormValuesConverter,
				_journalConverter, _portal));

		registry.register(
			"4.0.0", "4.1.0",
			new JournalArticleExternalReferenceCodeUpgradeProcess());

		registry.register("4.1.0", "4.2.0", new JournalFeedUpgradeProcess());
	}

	@Reference(unbind = "-")
	protected void setPortalCapabilityLocator(
		PortalCapabilityLocator portalCapabilityLocator) {

		// See LPS-82746

	}

	private void _deleteTempImages() throws Exception {
		if (_log.isDebugEnabled()) {
			_log.debug("Delete temporary images");
		}

		DB db = DBManagerUtil.getDB();

		db.runSQL(
			"delete from Image where imageId IN (SELECT articleImageId FROM " +
				"JournalArticleImage where tempImage = TRUE)");

		db.runSQL("delete from JournalArticleImage where tempImage = TRUE");
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
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private ConfigurationAdmin _configurationAdmin;

	@Reference
	private CounterLocalService _counterLocalService;

	@Reference
	private CTStoreFactory _ctStoreFactory;

	@Reference
	private DDMFieldLocalService _ddmFieldLocalService;

	@Reference
	private DDMStorageLinkLocalService _ddmStorageLinkLocalService;

	@Reference
	private DDMStructureLocalService _ddmStructureLocalService;

	@Reference
	private DDMTemplateLinkLocalService _ddmTemplateLinkLocalService;

	@Reference
	private DefaultDDMStructureHelper _defaultDDMStructureHelper;

	@Reference
	private FieldsToDDMFormValuesConverter _fieldsToDDMFormValuesConverter;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private ImageLocalService _imageLocalService;

	@Reference
	private JournalArticleImageUpgradeHelper _journalArticleImageUpgradeHelper;

	@Reference
	private JournalContentCompatibilityConverter
		_journalContentCompatibilityConverter;

	@Reference
	private JournalConverter _journalConverter;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED, unbind = "-")
	private ModuleServiceLifecycle _moduleServiceLifecycle;

	@Reference
	private Portal _portal;

	@Reference
	private PortletFileRepository _portletFileRepository;

	@Reference
	private PortletPreferencesLocalService _portletPreferencesLocalService;

	@Reference
	private PortletPreferenceValueLocalService
		_portletPreferenceValueLocalService;

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

	@Reference(target = "(dl.store.impl.enabled=true)")
	private StoreFactory _storeFactory;

	@Reference
	private SubscriptionLocalService _subscriptionLocalService;

	@Reference
	private SystemEventLocalService _systemEventLocalService;

	@Reference
	private UserLocalService _userLocalService;

}