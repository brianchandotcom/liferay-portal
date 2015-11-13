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

import com.liferay.dynamic.data.lists.upgrade.v1_0_0.UpgradeClassNames;
import com.liferay.dynamic.data.lists.upgrade.v1_0_0.UpgradeDynamicDataLists;
import com.liferay.dynamic.data.lists.upgrade.v1_0_0.UpgradeLastPublishDate;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.service.ReleaseLocalService;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcellus Tavares
 */
@Component(immediate = true, service = DDLServiceUpgrade.class)
public class DDLServiceUpgrade {

	@Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED, unbind = "-")
	protected void setModuleServiceLifecycle(
		ModuleServiceLifecycle moduleServiceLifecycle) {
	}

	@Reference(unbind = "-")
	protected void setReleaseLocalService(
		ReleaseLocalService releaseLocalService) {

		_releaseLocalService = releaseLocalService;
	}

	@Activate
	protected void upgrade() throws PortalException {
		List<UpgradeProcess> upgradeProcesses = new ArrayList<>();

		upgradeProcesses.add(new UpgradeClassNames());
		upgradeProcesses.add(new UpgradeDynamicDataLists());
		upgradeProcesses.add(new UpgradeLastPublishDate());

		for (UpgradeProcess upgradeProcess : upgradeProcesses) {
			if (_log.isDebugEnabled()) {
				_log.debug("Upgrade process " + upgradeProcess);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DDLServiceUpgrade.class);

	private ReleaseLocalService _releaseLocalService;

}