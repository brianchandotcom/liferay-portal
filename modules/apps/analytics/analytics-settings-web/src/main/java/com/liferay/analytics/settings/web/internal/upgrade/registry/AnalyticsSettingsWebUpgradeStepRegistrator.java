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

package com.liferay.analytics.settings.web.internal.upgrade.registry;

import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rachael Koestartyo
 */
@Component(service = UpgradeStepRegistrator.class)
public class AnalyticsSettingsWebUpgradeStepRegistrator
	implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.registerInitialization();

		registry.register(
			"0.0.1", "1.0.0",
			new com.liferay.analytics.settings.web.internal.upgrade.v1_0_0.
				AnalyticsConfigurationPreferencesUpgradeProcess(
					_configurationAdmin));

		registry.register(
			"1.0.0", "1.0.1",
			new com.liferay.analytics.settings.web.internal.upgrade.v1_0_1.
				AnalyticsConfigurationPreferencesUpgradeProcess(
					_companyLocalService, _configurationAdmin));
	}

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private ConfigurationAdmin _configurationAdmin;

}