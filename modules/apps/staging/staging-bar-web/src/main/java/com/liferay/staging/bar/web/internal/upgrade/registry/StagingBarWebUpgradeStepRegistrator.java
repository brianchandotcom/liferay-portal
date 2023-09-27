/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.staging.bar.web.internal.upgrade.registry;

import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.staging.bar.web.internal.upgrade.v1_0_0.UpgradePortletId;
import com.liferay.staging.bar.web.internal.upgrade.v1_0_1.ResourcePermissionsUpgradeProcess;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Levente Hudák
 */
@Component(service = UpgradeStepRegistrator.class)
public class StagingBarWebUpgradeStepRegistrator
	implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.registerInitialization();

		registry.register("0.0.1", "1.0.0", new UpgradePortletId());

		registry.register(
			"1.0.0", "1.0.1",
			new ResourcePermissionsUpgradeProcess(
				_resourcePermissionLocalService));
	}

	@Reference
	private ResourcePermissionLocalService _resourcePermissionLocalService;

}