/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.staging.internal.upgrade.registry;

import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.staging.internal.upgrade.BaseStagingConfigurationUpgradeProcess;

import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Carlos Correa
 */
@Component(service = UpgradeStepRegistrator.class)
public class StagingImplUpgradeStepRegistrator
	implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.registerInitialization();

		registry.register(
			"0.0.1", "1.0.0",
			new BaseStagingConfigurationUpgradeProcess(_configurationAdmin) {

				@Override
				protected String getPropertyName() {
					return "publishDisplayedContent";
				}

			});

		registry.register(
			"1.0.0", "1.0.1",
			new BaseStagingConfigurationUpgradeProcess(_configurationAdmin) {

				@Override
				protected String getPropertyName() {
					return "publishParentLayoutsByDefault";
				}

			});
	}

	@Reference
	private ConfigurationAdmin _configurationAdmin;

}