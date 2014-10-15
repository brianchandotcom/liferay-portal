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

package com.liferay.bookmarks.web.upgrade;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.service.ReleaseLocalService;
import com.liferay.bookmarks.service.configuration.configurator.BookmarksServiceConfigurator;
import com.liferay.bookmarks.web.upgrade.admin.UpgradeAdminPortlets;
import com.liferay.bookmarks.web.upgrade.preferences.UpgradeBookmarksPreferences;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Miguel Pastor
 */
@Component(
	immediate = true, service = BookmarksWebUpgrade.class
)
public class BookmarksWebUpgrade {

	@Reference(unbind = "-")
	protected void setBookmarksServiceConfigurator(
		BookmarksServiceConfigurator bookmarksServiceConfigurator) {
	}

	@Reference(unbind = "-")
	protected void setReleaseLocalService(
		ReleaseLocalService releaseLocalService) {

		_releaseLocalService = releaseLocalService;
	}

	@Activate
	protected void upgrade() throws PortalException {
		List<UpgradeProcess> upgradeProcesses = new ArrayList<>();

		upgradeProcesses.add(new UpgradeBookmarksPreferences());
		upgradeProcesses.add(new UpgradeAdminPortlets());

		_releaseLocalService.updateRelease(
			"com.liferay.bookmarks.web", upgradeProcesses, 1, 0, false);
	}

	private ReleaseLocalService _releaseLocalService;

}