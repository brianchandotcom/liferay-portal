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

package com.liferay.journal.upgrade;

import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.asset.kernel.service.AssetVocabularyLocalService;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStorageLinkLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLinkLocalService;
import com.liferay.dynamic.data.mapping.util.DefaultDDMStructureHelper;
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
import com.liferay.journal.internal.upgrade.v1_0_0.UpgradeJournalArticleImage;
import com.liferay.journal.internal.upgrade.v1_0_1.UpgradeJournalContentSearch;
import com.liferay.journal.internal.upgrade.v1_1_0.UpgradeDocumentLibraryTypeContent;
import com.liferay.journal.internal.upgrade.v1_1_0.UpgradeImageTypeContent;
import com.liferay.journal.internal.upgrade.v1_1_0.UpgradeJournalArticleLocalizedValues;
import com.liferay.journal.internal.upgrade.v1_1_1.UpgradeFileUploadsConfiguration;
import com.liferay.journal.internal.upgrade.v1_1_2.UpgradeCheckIntervalConfiguration;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.db.DBProcessContext;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.permission.ResourceActions;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ImageLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ResourceActionLocalService;
import com.liferay.portal.kernel.service.ResourceLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.settings.SettingsFactory;
import com.liferay.portal.kernel.upgrade.DummyUpgradeStep;
import com.liferay.portal.kernel.upgrade.UpgradeException;
import com.liferay.portal.kernel.upgrade.UpgradeStep;
import com.liferay.portal.kernel.util.PrefsProps;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import java.io.PrintWriter;

import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eduardo Garcia
 */
@Component(immediate = true, service = UpgradeStepRegistrator.class)
public class JournalServiceUpgrade implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.register(
			"com.liferay.journal.service", "0.0.1", "0.0.2",
			new UpgradeClassNames());

		registry.register(
			"com.liferay.journal.service", "0.0.2", "0.0.3",
			new UpgradeJournalArticleType(
				_assetCategoryLocalService, _assetEntryLocalService,
				_assetVocabularyLocalService, _companyLocalService,
				_userLocalService));

		registry.register(
			"com.liferay.journal.service", "0.0.3", "0.0.4",
			new UpgradeSchema());

		registry.register(
			"com.liferay.journal.service", "0.0.4", "0.0.5",
			new UpgradeCompanyId(),
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
				public void upgrade(DBProcessContext dbProcessContext)
					throws UpgradeException {

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

		registry.register(
			"com.liferay.journal.service", "0.0.5", "0.0.6",
			new UpgradeJournalArticleImage());

		registry.register(
			"com.liferay.journal.service", "0.0.6", "0.0.7",
			new UpgradeImageTypeContentAttributes());

		registry.register(
			"com.liferay.journal.service", "0.0.7", "1.0.0",
			new UpgradeJournalArticleDates(),
			new UpgradeJournalArticleTreePath());

		registry.register(
			"com.liferay.journal.service", "1.0.0", "1.0.1",
			new UpgradeJournalContentSearch());

		registry.register(
			"com.liferay.journal.service", "1.0.1", "1.0.2",
			new DummyUpgradeStep());

		registry.register(
			"com.liferay.journal.service", "1.0.2", "1.1.0",
			new UpgradeDocumentLibraryTypeContent(_dlAppLocalService),
			new UpgradeImageTypeContent(_imageLocalService),
			new UpgradeJournalArticleLocalizedValues());

		registry.register(
			"com.liferay.journal.service", "1.1.0", "1.1.1",
			new UpgradeFileUploadsConfiguration(
				_configurationAdmin, _prefsProps));

		registry.register(
			"com.liferay.journal.service", "1.1.1", "1.1.2",
			new UpgradeCheckIntervalConfiguration(_configurationAdmin));
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
	protected void setAssetCategoryLocalService(
		AssetCategoryLocalService assetCategoryLocalService) {

		_assetCategoryLocalService = assetCategoryLocalService;
	}

	@Reference(unbind = "-")
	protected void setAssetEntryLocalService(
		AssetEntryLocalService assetEntryLocalService) {

		_assetEntryLocalService = assetEntryLocalService;
	}

	@Reference(unbind = "-")
	protected void setAssetVocabularyLocalService(
		AssetVocabularyLocalService assetVocabuaryLocalService) {

		_assetVocabularyLocalService = assetVocabuaryLocalService;
	}

	@Reference(unbind = "-")
	protected void setCompanyLocalService(
		CompanyLocalService companyLocalService) {

		_companyLocalService = companyLocalService;
	}

	@Reference(unbind = "-")
	protected void setConfigurationAdmin(
		ConfigurationAdmin configurationAdmin) {

		_configurationAdmin = configurationAdmin;
	}

	@Reference(unbind = "-")
	protected void setDDMStorageLinkLocalService(
		DDMStorageLinkLocalService ddmStorageLinkLocalService) {

		_ddmStorageLinkLocalService = ddmStorageLinkLocalService;
	}

	@Reference(unbind = "-")
	protected void setDDMStructureLocalService(
		DDMStructureLocalService ddmStructureLocalService) {

		_ddmStructureLocalService = ddmStructureLocalService;
	}

	@Reference(unbind = "-")
	protected void setDDMTemplateLinkLocalService(
		DDMTemplateLinkLocalService ddmTemplateLinkLocalService) {

		_ddmTemplateLinkLocalService = ddmTemplateLinkLocalService;
	}

	@Reference(unbind = "-")
	protected void setDefaultDDMStructureHelper(
		DefaultDDMStructureHelper defaultDDMStructureHelper) {

		_defaultDDMStructureHelper = defaultDDMStructureHelper;
	}

	@Reference(unbind = "-")
	protected void setDLAppLocalService(DLAppLocalService dlAppLocalService) {
		_dlAppLocalService = dlAppLocalService;
	}

	@Reference(unbind = "-")
	protected void setGroupLocalService(GroupLocalService groupLocalService) {
		_groupLocalService = groupLocalService;
	}

	@Reference(unbind = "-")
	protected void setImageLocalService(ImageLocalService imageLocalService) {
		_imageLocalService = imageLocalService;
	}

	@Reference(unbind = "-")
	protected void setLayoutLocalService(
		LayoutLocalService layoutLocalService) {

		_layoutLocalService = layoutLocalService;
	}

	@Reference(unbind = "-")
	protected void setPrefsProps(PrefsProps prefsProps) {
		_prefsProps = prefsProps;
	}

	@Reference(unbind = "-")
	protected void setResourceActionLocalService(
		ResourceActionLocalService resourceActionLocalService) {

		_resourceActionLocalService = resourceActionLocalService;
	}

	@Reference(unbind = "-")
	protected void setResourceActions(ResourceActions resourceActions) {
		_resourceActions = resourceActions;
	}

	@Reference(unbind = "-")
	protected void setResourceLocalService(
		ResourceLocalService resourceLocalService) {

		_resourceLocalService = resourceLocalService;
	}

	@Reference(unbind = "-")
	protected void setSettingsFactory(SettingsFactory settingsFactory) {
		_settingsFactory = settingsFactory;
	}

	@Reference(unbind = "-")
	protected void setUserLocalService(UserLocalService userLocalService) {
		_userLocalService = userLocalService;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		JournalServiceUpgrade.class);

	private AssetCategoryLocalService _assetCategoryLocalService;
	private AssetEntryLocalService _assetEntryLocalService;
	private AssetVocabularyLocalService _assetVocabularyLocalService;
	private CompanyLocalService _companyLocalService;
	private ConfigurationAdmin _configurationAdmin;
	private DDMStorageLinkLocalService _ddmStorageLinkLocalService;
	private DDMStructureLocalService _ddmStructureLocalService;
	private DDMTemplateLinkLocalService _ddmTemplateLinkLocalService;
	private DefaultDDMStructureHelper _defaultDDMStructureHelper;
	private DLAppLocalService _dlAppLocalService;
	private GroupLocalService _groupLocalService;
	private ImageLocalService _imageLocalService;
	private LayoutLocalService _layoutLocalService;
	private PrefsProps _prefsProps;
	private ResourceActionLocalService _resourceActionLocalService;
	private ResourceActions _resourceActions;
	private ResourceLocalService _resourceLocalService;
	private SettingsFactory _settingsFactory;
	private UserLocalService _userLocalService;

}