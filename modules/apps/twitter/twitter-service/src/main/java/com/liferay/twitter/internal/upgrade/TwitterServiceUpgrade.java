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

package com.liferay.twitter.internal.upgrade;

import com.liferay.portal.kernel.upgrade.DummyUpgradeStep;
import com.liferay.portal.kernel.upgrade.UpgradeException;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.upgrade.release.BaseUpgradeServiceModuleRelease;

import org.osgi.service.component.annotations.Component;

/**
 * @author Adolfo Pérez
 */
@Component(immediate = true, service = UpgradeStepRegistrator.class)
public class TwitterServiceUpgrade implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		try {
			BaseUpgradeServiceModuleRelease upgradeServiceModuleRelease =
				new BaseUpgradeServiceModuleRelease() {

					@Override
					protected String getNewBundleSymbolicName() {
						return "com.liferay.twitter.service";
					}

					@Override
					protected String getOldBundleSymbolicName() {
						return "twitter-portlet";
					}

				};

			upgradeServiceModuleRelease.upgrade();
		}
		catch (UpgradeException ue) {
			throw new RuntimeException(ue);
		}

		registry.register(
			"com.liferay.twitter.service", "0.0.1", "1.0.0",
			new DummyUpgradeStep());
	}

}