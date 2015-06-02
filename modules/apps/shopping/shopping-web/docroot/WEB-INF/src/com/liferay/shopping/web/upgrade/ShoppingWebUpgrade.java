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

package com.liferay.shopping.web.upgrade;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.service.ReleaseLocalService;
import com.liferay.shopping.service.configuration.configurator.ShoppingServiceConfigurator;
import com.liferay.shopping.web.upgrade.v1_0_0.UpgradeAdminPortlets;

/**
 * @author Miguel Pastor
 */
@Component(
	immediate = true, service = ShoppingWebUpgrade.class
)
public class ShoppingWebUpgrade {

	@Reference(unbind = "-")
	protected void setShoppingServiceConfigurator(
		ShoppingServiceConfigurator shoppingServiceConfigurator) {
	}

	@Reference(unbind = "-")
	protected void setReleaseLocalService(
		ReleaseLocalService releaseLocalService) {

		_releaseLocalService = releaseLocalService;
	}

	@Activate
	protected void upgrade() throws PortalException {
		List<UpgradeProcess> upgradeProcesses = new ArrayList<>();

		upgradeProcesses.add(new UpgradeAdminPortlets());
		//upgradeProcesses.add(new UpgradePortletPreferences());

		_releaseLocalService.updateRelease(
			"com.liferay.shopping.web", upgradeProcesses, 1, 1, false);
	}

	private ReleaseLocalService _releaseLocalService;

}