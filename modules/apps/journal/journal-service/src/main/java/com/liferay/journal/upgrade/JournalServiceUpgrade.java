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

import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLinkLocalService;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalService;
import com.liferay.journal.upgrade.v0_0_2.UpgradeClassNames;
import com.liferay.journal.upgrade.v0_0_3.UpgradeJournalArticleType;
import com.liferay.journal.upgrade.v1_0_0.UpgradeCompanyId;
import com.liferay.journal.upgrade.v1_0_0.UpgradeJournal;
import com.liferay.journal.upgrade.v1_0_0.UpgradeJournalArticles;
import com.liferay.journal.upgrade.v1_0_0.UpgradeJournalDisplayPreferences;
import com.liferay.journal.upgrade.v1_0_0.UpgradeLastPublishDate;
import com.liferay.journal.upgrade.v1_0_0.UpgradePortletSettings;
import com.liferay.journal.upgrade.v1_0_0.UpgradeSchema;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.db.DBProcessContext;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.settings.SettingsFactory;
import com.liferay.portal.kernel.upgrade.UpgradeException;
import com.liferay.portal.kernel.upgrade.UpgradeStep;
import com.liferay.portal.service.CompanyLocalService;
import com.liferay.portal.service.GroupLocalService;
import com.liferay.portal.service.LayoutLocalService;
import com.liferay.portal.service.UserLocalService;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portlet.asset.service.AssetCategoryLocalService;
import com.liferay.portlet.asset.service.AssetEntryLocalService;
import com.liferay.portlet.asset.service.AssetVocabularyLocalService;

import java.io.PrintWriter;

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
			"com.liferay.journal.service", "0.0.3", "1.0.0",
			new UpgradeSchema(), new UpgradeCompanyId(),
			new UpgradeJournal(
				_companyLocalService, _ddmStructureLocalService,
				_ddmTemplateLinkLocalService, _ddmTemplateLocalService,
				_groupLocalService, _userLocalService),
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
	protected void setDDMTemplateLocalService(
		DDMTemplateLocalService ddmTemplateLocalService) {

		_ddmTemplateLocalService = ddmTemplateLocalService;
	}

	@Reference(unbind = "-")
	protected void setGroupLocalService(GroupLocalService groupLocalService) {
		_groupLocalService = groupLocalService;
	}

	@Reference(unbind = "-")
	protected void setLayoutLocalService(
		LayoutLocalService layoutLocalService) {

		_layoutLocalService = layoutLocalService;
	}

	@Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED, unbind = "-")
	protected void setModuleServiceLifecycle(
		ModuleServiceLifecycle moduleServiceLifecycle) {
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

	private volatile AssetCategoryLocalService _assetCategoryLocalService;
	private volatile AssetEntryLocalService _assetEntryLocalService;
	private volatile AssetVocabularyLocalService _assetVocabularyLocalService;
	private volatile CompanyLocalService _companyLocalService;
	private volatile DDMStructureLocalService _ddmStructureLocalService;
	private volatile DDMTemplateLinkLocalService _ddmTemplateLinkLocalService;
	private volatile DDMTemplateLocalService _ddmTemplateLocalService;
	private volatile GroupLocalService _groupLocalService;
	private volatile LayoutLocalService _layoutLocalService;
	private volatile SettingsFactory _settingsFactory;
	private volatile UserLocalService _userLocalService;

}