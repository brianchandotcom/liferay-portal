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

package com.liferay.marketplace.upgrade;

import com.liferay.marketplace.service.configuration.configurator.MarketplaceServiceConfigurator;
import com.liferay.marketplace.upgrade.v1_0_0.UpgradeExpando;
import com.liferay.marketplace.upgrade.v1_0_1.UpgradeModule;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.service.ReleaseLocalService;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Joan Kim
 */
@Component(immediate = true, service = MarketplaceServiceUpgrade.class)
public class MarketplaceServiceUpgrade {

	@Reference(unbind = "-")
	protected void setMarketplaceServiceConfigurator(
		MarketplaceServiceConfigurator marketplaceServiceConfigurator) {
	}

	@Reference(unbind = "-")
	protected void setReleaseLocalService(
		ReleaseLocalService releaseLocalService) {

		_releaseLocalService = releaseLocalService;
	}

	@Activate
	protected void upgrade() throws PortalException {
		List<UpgradeProcess> upgradeProcesses = new ArrayList<>();

		upgradeProcesses.add(new UpgradeExpando());
		upgradeProcesses.add(new UpgradeModule());

		_releaseLocalService.updateRelease(
			"com.liferay.marketplace.service", upgradeProcesses, 1, 1, false);
	}

	private ReleaseLocalService _releaseLocalService;

}