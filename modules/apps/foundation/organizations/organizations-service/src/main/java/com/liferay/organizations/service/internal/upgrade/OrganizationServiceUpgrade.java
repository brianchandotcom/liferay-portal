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

package com.liferay.organizations.service.internal.upgrade;

import com.liferay.organizations.service.internal.upgrade.v1_0_0.UpgradeOrganizationTypesConfiguration;
import com.liferay.portal.kernel.upgrade.DummyUpgradeStep;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Drew Brokke
 */
@Component(
	enabled = false, immediate = true, service = UpgradeStepRegistrator.class
)
public class OrganizationServiceUpgrade implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.register(
			"com.liferay.organizations.service", "0.0.0", "0.0.1",
			new DummyUpgradeStep());

		registry.register(
			"com.liferay.organizations.service", "0.0.1", "1.0.0",
			new UpgradeOrganizationTypesConfiguration(
				_configurationAdmin, _props));
	}

	@Reference
	private ConfigurationAdmin _configurationAdmin;

	@Reference
	private Props _props;

}