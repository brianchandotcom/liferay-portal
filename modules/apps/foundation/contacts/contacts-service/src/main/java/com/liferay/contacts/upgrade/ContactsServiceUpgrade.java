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

package com.liferay.contacts.upgrade;

import com.liferay.contacts.upgrade.v2_0_0.UpgradeEntry;
import com.liferay.portal.kernel.upgrade.DummyUpgradeStep;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import org.osgi.service.component.annotations.Component;

/**
 * @author Jonathan Lee
 */
@Component(immediate = true, service = UpgradeStepRegistrator.class)
public class ContactsServiceUpgrade implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.register(
			"com.liferay.contacts.service", "0.0.1", "1.0.0",
			new UpgradeEntry());

		// See LPS-65946

		registry.register(
			"com.liferay.contacts.service", "1.0.0", "2.0.1",
			new DummyUpgradeStep());

		registry.register(
			"com.liferay.contacts.service", "2.0.0", "2.0.1",
			new DummyUpgradeStep());
	}

}