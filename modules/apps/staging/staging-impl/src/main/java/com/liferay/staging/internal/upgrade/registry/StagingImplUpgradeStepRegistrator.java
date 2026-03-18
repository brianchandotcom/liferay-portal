/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.staging.internal.upgrade.registry;

import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.staging.internal.upgrade.v1_0_0.PublishDisplayedContentStagingConfigurationUpgradeProcess;
import com.liferay.staging.internal.upgrade.v1_0_1.PublishParentLayoutsByDefaultStagingConfigurationUpgradeProcess;

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
			new PublishDisplayedContentStagingConfigurationUpgradeProcess(
				_configurationAdmin));

		registry.register(
			"1.0.0", "1.0.1",
			new PublishParentLayoutsByDefaultStagingConfigurationUpgradeProcess(
				_configurationAdmin));
	}

	@Reference
	private ConfigurationAdmin _configurationAdmin;

}