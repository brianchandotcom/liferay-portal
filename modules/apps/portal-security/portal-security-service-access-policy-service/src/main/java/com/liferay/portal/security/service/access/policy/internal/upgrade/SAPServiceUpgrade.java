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

package com.liferay.portal.security.service.access.policy.internal.upgrade;

import com.liferay.portal.kernel.upgrade.BaseUpgradeSQLServerDatetime;
import com.liferay.portal.kernel.upgrade.DummyUpgradeStep;
import com.liferay.portal.security.service.access.policy.internal.upgrade.v3_0_0.util.SAPEntryTable;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import org.osgi.service.component.annotations.Component;

/**
 * @author Marcellus Tavares
 */
@Component(immediate = true, service = UpgradeStepRegistrator.class)
public class SAPServiceUpgrade implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.register("2.0.0", "2.0.13", new DummyUpgradeStep());

		registry.register("2.0.1", "2.0.13", new DummyUpgradeStep());

		registry.register("2.0.2", "2.0.13", new DummyUpgradeStep());

		registry.register("2.0.3", "2.0.13", new DummyUpgradeStep());

		registry.register("2.0.4", "2.0.13", new DummyUpgradeStep());

		registry.register("2.0.5", "2.0.13", new DummyUpgradeStep());

		registry.register("2.0.6", "2.0.13", new DummyUpgradeStep());

		registry.register("2.0.7", "2.0.13", new DummyUpgradeStep());

		registry.register("2.0.8", "2.0.13", new DummyUpgradeStep());

		registry.register("2.0.9", "2.0.13", new DummyUpgradeStep());

		registry.register("2.0.10", "2.0.13", new DummyUpgradeStep());

		registry.register("2.0.11", "2.0.13", new DummyUpgradeStep());

		registry.register("2.0.12", "2.0.13", new DummyUpgradeStep());

		registry.register(
			"2.0.13", "3.0.0",
			new BaseUpgradeSQLServerDatetime(
				new Class<?>[] {SAPEntryTable.class}));
	}

}