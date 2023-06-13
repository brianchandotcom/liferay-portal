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

package com.liferay.portal.search.internal.upgrade.registry;

import com.liferay.portal.configuration.persistence.upgrade.ConfigurationUpgradeStepFactory;
import com.liferay.portal.kernel.upgrade.DummyUpgradeStep;
import com.liferay.portal.kernel.util.PrefsProps;
import com.liferay.portal.search.internal.upgrade.v1_0_1.ReindexConfigurationUpgradeProcess;
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
	}

	@Reference
	private ConfigurationAdmin _configurationAdmin;

	@Reference
	private ConfigurationUpgradeStepFactory _configurationUpgradeStepFactory;

	@Reference
	private PrefsProps _prefsProps;

}