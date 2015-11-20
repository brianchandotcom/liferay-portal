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

package com.liferay.blogs.web.upgrade;

import com.liferay.blogs.web.upgrade.v1_0_0.UpgradePortletId;
import com.liferay.blogs.web.upgrade.v1_0_0.UpgradePortletPreferences;
import com.liferay.blogs.web.upgrade.v1_0_0.UpgradePortletSettings;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.settings.SettingsFactory;
import com.liferay.portal.kernel.upgrade.DummyUpgradeStep;
import com.liferay.portal.service.PortletPreferencesLocalService;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sergio González
 */
@Component(immediate = true)
public class BlogsWebUpgrade implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.register(
			"com.liferay.blogs.web", "0.0.0", "1.0.0", new DummyUpgradeStep());
		registry.register(
			"com.liferay.blogs.web", "0.0.1", "1.0.0", new UpgradePortletId(),
			new UpgradePortletPreferences(),
			new UpgradePortletSettings(_settingsFactory));
	}

	@Reference(unbind = "-")
	public void setPortletPreferencesLocalService(
		PortletPreferencesLocalService portletPreferencesLocalService) {

		_portletPreferencesLocalService = portletPreferencesLocalService;
	}

	@Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED, unbind = "-")
	protected void setModuleServiceLifecycle(
		ModuleServiceLifecycle moduleServiceLifecycle) {
	}

	@Reference(unbind = "-")
	protected void setSettingsFactory(SettingsFactory settingsFactory) {
		_settingsFactory = settingsFactory;
	}

	private volatile PortletPreferencesLocalService
		_portletPreferencesLocalService;
	private volatile SettingsFactory _settingsFactory;

}