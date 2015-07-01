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

package com.liferay.dynamic.data.lists.upgrade;

import com.liferay.dynamic.data.lists.service.configuration.configurator.DDLServiceConfigurator;
import com.liferay.dynamic.data.lists.upgrade.v1_0_0.DDLClassNamesUpgradeProcess;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.service.ReleaseLocalService;

import java.util.Collections;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcellus Tavares
 */
@Component(immediate = true, service = DDLServiceUpgrade.class)
public class DDLServiceUpgrade {

	@Reference(unbind = "-")
	protected void setDDLServiceConfigurator(
		DDLServiceConfigurator ddlServiceConfigurator) {
	}

	@Reference(unbind = "-")
	protected void setReleaseLocalService(
		ReleaseLocalService releaseLocalService) {

		_releaseLocalService = releaseLocalService;
	}

	@Activate
	protected void upgrade() throws PortalException {
		UpgradeProcess upgradeProcess = new DDLClassNamesUpgradeProcess();

		_releaseLocalService.updateRelease(
			"com.liferay.dynamic.data.lists.service",
			Collections.<UpgradeProcess>singletonList(upgradeProcess), 1, 0,
			false);
	}

	private ReleaseLocalService _releaseLocalService;

}