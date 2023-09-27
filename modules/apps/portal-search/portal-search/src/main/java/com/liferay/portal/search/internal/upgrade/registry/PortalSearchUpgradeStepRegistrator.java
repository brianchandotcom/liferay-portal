/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.upgrade.registry;

import com.liferay.portal.configuration.persistence.upgrade.ConfigurationUpgradeStepFactory;
import com.liferay.portal.kernel.upgrade.DummyUpgradeStep;
import com.liferay.portal.kernel.util.PrefsProps;
import com.liferay.portal.search.internal.upgrade.v1_0_1.ReindexConfigurationUpgradeProcess;
import com.liferay.portal.search.internal.upgrade.v1_1_0.IndexStatusManagerInternalConfigurationUpgradeProcess;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Hugo Huijser
 */
@Component(service = UpgradeStepRegistrator.class)
public class PortalSearchUpgradeStepRegistrator
	implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.registerInitialization();

		registry.register("0.0.1", "0.0.2", new DummyUpgradeStep());

		registry.register(
			"0.0.2", "1.0.0",
			_configurationUpgradeStepFactory.createUpgradeStep(
				"com.liferay.portal.search.internal.index." +
					"IndexStatusManagerInternalConfiguration",
				"com.liferay.portal.search.internal.index.configuration." +
					"IndexStatusManagerInternalConfiguration"));

		registry.register(
			"1.0.0", "1.0.1",
			new ReindexConfigurationUpgradeProcess(
				_configurationAdmin, _prefsProps));

		registry.register(
			"1.0.1", "1.1.0",
			new IndexStatusManagerInternalConfigurationUpgradeProcess(
				_configurationAdmin));
	}

	@Reference
	private ConfigurationAdmin _configurationAdmin;

	@Reference
	private ConfigurationUpgradeStepFactory _configurationUpgradeStepFactory;

	@Reference
	private PrefsProps _prefsProps;

}