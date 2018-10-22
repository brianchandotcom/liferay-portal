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

package com.liferay.dynamic.data.mapping.form.web.internal.upgrade;

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
@Component(
	immediate = true,
	service = {DDMFormWebUpgrade.class, UpgradeStepRegistrator.class}
)
public class DDMFormWebUpgrade implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.register(
			"0.0.0", "1.0.0", new UpgradeDDMFormAdminPortletId(),
			new UpgradeDDMFormPortletId(),
			new UpgradeDDMFormPortletPreferences());
	}

	@Reference
	private PortletPreferencesLocalService _portletPreferencesLocalService;

	@Reference
	private ResourcePermissionLocalService _resourcePermissionLocalService;

}