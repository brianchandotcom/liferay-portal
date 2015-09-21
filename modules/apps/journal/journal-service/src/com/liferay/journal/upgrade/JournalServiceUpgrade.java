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

import com.liferay.journal.service.JournalArticleImageLocalService;
import com.liferay.journal.upgrade.v1_0_0.UpgradeClassNames;
import com.liferay.journal.upgrade.v1_0_0.UpgradeJournal;
import com.liferay.journal.upgrade.v1_0_0.UpgradeJournalArticleType;
import com.liferay.journal.upgrade.v1_0_0.UpgradeJournalDisplayPreferences;
import com.liferay.journal.upgrade.v1_0_0.UpgradeLastPublishDate;
import com.liferay.journal.upgrade.v1_0_0.UpgradePortletSettings;
import com.liferay.journal.upgrade.v1_0_0.UpgradeSchema;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.settings.SettingsFactory;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.service.ReleaseLocalService;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eduardo Garcia
 */
@Component(immediate = true, service = JournalServiceUpgrade.class)
public class JournalServiceUpgrade {

	protected void deleteTempImages() throws Exception {
		if (_log.isDebugEnabled()) {
			_log.debug("Delete temporary images");
		}

		DB db = DBFactoryUtil.getDB();

		db.runSQL(
			"delete from Image where imageId IN (SELECT articleImageId FROM " +
				"JournalArticleImage where tempImage = TRUE)");

		db.runSQL("delete from JournalArticleImage where tempImage = TRUE");
	}

	@Reference
	protected void setJournalArticleImageLocalService(
		JournalArticleImageLocalService journalArticleImageLocalService ) {
	}

	@Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED, unbind = "-")
	protected void setModuleServiceLifecycle(
		ModuleServiceLifecycle moduleServiceLifecycle) {
	}

	@Reference(unbind = "-")
	protected void setReleaseLocalService(
		ReleaseLocalService releaseLocalService) {

		_releaseLocalService = releaseLocalService;
	}

	@Reference(unbind = "-")
	protected void setSettingsFactory(SettingsFactory settingsFactory) {
		_settingsFactory = settingsFactory;
	}

	@Activate
	protected void upgrade() throws Exception {
		List<UpgradeProcess> upgradeProcesses = new ArrayList<>();

		upgradeProcesses.add(new UpgradeSchema());

		upgradeProcesses.add(new UpgradeClassNames());
		upgradeProcesses.add(new UpgradeJournal());
		upgradeProcesses.add(new UpgradeJournalArticleType());
		upgradeProcesses.add(new UpgradeJournalDisplayPreferences());
		upgradeProcesses.add(new UpgradeLastPublishDate());
		upgradeProcesses.add(new UpgradePortletSettings(_settingsFactory));

		_releaseLocalService.updateRelease(
			"com.liferay.journal.service", upgradeProcesses, 1, 1, false);

		deleteTempImages();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		JournalServiceUpgrade.class);

	private ReleaseLocalService _releaseLocalService;
	private SettingsFactory _settingsFactory;

}