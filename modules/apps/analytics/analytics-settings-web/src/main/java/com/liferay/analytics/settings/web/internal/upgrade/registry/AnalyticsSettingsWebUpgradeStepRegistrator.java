/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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