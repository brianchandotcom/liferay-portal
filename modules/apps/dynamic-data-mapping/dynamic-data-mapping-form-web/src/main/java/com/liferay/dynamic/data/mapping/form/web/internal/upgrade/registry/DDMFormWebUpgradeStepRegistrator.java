/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.web.internal.upgrade.registry;

import com.liferay.dynamic.data.mapping.form.web.internal.upgrade.v1_0_0.UpgradeDDMFormAdminPortletId;
import com.liferay.dynamic.data.mapping.form.web.internal.upgrade.v1_0_0.UpgradeDDMFormPortletId;
import com.liferay.dynamic.data.mapping.form.web.internal.upgrade.v1_0_0.UpgradeDDMFormPortletPreferences;
import com.liferay.portal.kernel.service.PortletPreferencesLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rafael Praxedes
 */
@Component(service = UpgradeStepRegistrator.class)
public class DDMFormWebUpgradeStepRegistrator
	implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.registerInitialization();

		registry.register(
			"0.0.1", "1.0.0", new UpgradeDDMFormAdminPortletId(),
			new UpgradeDDMFormPortletId(),
			new UpgradeDDMFormPortletPreferences());
	}

	@Reference
	private PortletPreferencesLocalService _portletPreferencesLocalService;

	@Reference
	private ResourcePermissionLocalService _resourcePermissionLocalService;

}