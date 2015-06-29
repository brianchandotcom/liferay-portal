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

import com.liferay.journal.service.configuration.configurator.JournalServiceConfigurator;
import com.liferay.journal.upgrade.v1_0_0.UpgradeClassNames;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.service.ReleaseLocalService;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eduardo Garcia
 */
@Component(immediate = true, service = JournalServiceUpgrade.class)
public class JournalServiceUpgrade {

	@Reference(unbind = "-")
	protected void setJournalServiceConfigurator(
		JournalServiceConfigurator journalServiceConfigurator) {
	}

	@Reference(unbind = "-")
	protected void setReleaseLocalService(
		ReleaseLocalService releaseLocalService) {

		_releaseLocalService = releaseLocalService;
	}

	@Reference(target = "(original.bean=*)", unbind = "-")
	protected void setServletContext(ServletContext servletContext) {
	}

	@Activate
	protected void upgrade() throws Exception {
		List<UpgradeProcess> upgradeProcesses = new ArrayList<>();

		upgradeProcesses.add(new UpgradeClassNames());

		_releaseLocalService.updateRelease(
			"com.liferay.journal.service", upgradeProcesses, 1, 1, false);

		if (_log.isDebugEnabled()) {
			_log.debug("Delete temporary images");
		}

		_deleteTempImages();
	}

	private void _deleteTempImages() throws Exception {
		DB db = DBFactoryUtil.getDB();

		db.runSQL(_DELETE_TEMP_IMAGES_1);
		db.runSQL(_DELETE_TEMP_IMAGES_2);
	}

	private static final String _DELETE_TEMP_IMAGES_1 =
		"delete from Image where imageId IN (SELECT articleImageId FROM " +
			"JournalArticleImage where tempImage = TRUE)";

	private static final String _DELETE_TEMP_IMAGES_2 =
		"delete from JournalArticleImage where tempImage = TRUE";

	private static final Log _log = LogFactoryUtil.getLog(
		JournalServiceUpgrade.class);

	private ReleaseLocalService _releaseLocalService;

}