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

package com.liferay.youtube.web.upgrade;

import com.liferay.counter.kernel.service.CounterLocalService;
import com.liferay.portal.kernel.upgrade.DummyUpgradeStep;
import com.liferay.portal.kernel.upgrade.UpgradeException;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.youtube.web.upgrade.util.UpgradePluginRelease;
import com.liferay.youtube.web.upgrade.v1_0_0.UpgradePortletId;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Peter Fellwock
 */
@Component(immediate = true, service = UpgradeStepRegistrator.class)
public class YouTubeWebUpgrade implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		_upgradeRelease();

		registry.register(
			"com.liferay.youtube.web", "0.0.0", "1.0.0",
			new DummyUpgradeStep());

		registry.register(
			"com.liferay.youtube.web", "0.0.1", "1.0.0",
			new UpgradePortletId());
	}

	@Reference
	protected CounterLocalService counterLocalService;

	private void _upgradeRelease() {
		try {
			UpgradePluginRelease upgradePluginRelease =
				new UpgradePluginRelease(counterLocalService);

			upgradePluginRelease.upgrade();
		}
		catch (UpgradeException ue) {
			throw new RuntimeException(ue);
		}
	}

}