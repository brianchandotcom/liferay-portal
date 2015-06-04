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

package com.liferay.microblogs.upgrade;

import com.liferay.microblogs.upgrade.v1_0_1.UpgradeUserNotificationEvent;
import com.liferay.microblogs.upgrade.v1_0_2.UpgradeSocial;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.service.ReleaseLocalService;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import org.springframework.context.ApplicationContext;

/**
 * @author Ryan Park
 */
@Component(immediate = true, service = MicroblogsServiceUpgrade.class)
public class MicroblogsServiceUpgrade {

	@Reference(
		target =
			"(org.springframework.context.service.name=" +
				"com.liferay.microblogs.service)",
		unbind = "-"
	)
	protected void setApplicationContext(
		ApplicationContext applicationContext) {
	}

	@Reference(unbind = "-")
	protected void setReleaseLocalService(
		ReleaseLocalService releaseLocalService) {

		_releaseLocalService = releaseLocalService;
	}

	@Activate
	protected void upgrade() throws PortalException {
		List<UpgradeProcess> upgradeProcesses = new ArrayList<>();

		upgradeProcesses.add(
			new com.liferay.microblogs.upgrade.v1_0_0.UpgradeMicroblogsEntry());
		upgradeProcesses.add(new UpgradeUserNotificationEvent());
		upgradeProcesses.add(
			new com.liferay.microblogs.upgrade.v1_0_2.UpgradeMicroblogsEntry());
		upgradeProcesses.add(new UpgradeSocial());

		_releaseLocalService.updateRelease(
			"com.liferay.microblogs.service", upgradeProcesses, 1, 1, false);
	}

	private ReleaseLocalService _releaseLocalService;

}