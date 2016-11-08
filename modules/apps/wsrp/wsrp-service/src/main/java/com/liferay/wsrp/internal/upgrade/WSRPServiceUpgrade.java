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

package com.liferay.wsrp.internal.upgrade;

import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.upgrade.DummyUpgradeStep;
import com.liferay.portal.kernel.upgrade.UpgradeException;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.upgrade.release.BaseUpgradeWebModuleRelease;
import com.liferay.wsrp.internal.upgrade.v1_0_0.UpgradeLastPublishDate;
import com.liferay.wsrp.internal.upgrade.v1_0_0.UpgradePortletId;
import com.liferay.wsrp.internal.upgrade.v1_0_0.UpgradeUuid;
import com.liferay.wsrp.internal.upgrade.v1_0_0.UpgradeWSRP;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Peter Fellwock
 */
@Component(immediate = true, service = UpgradeStepRegistrator.class)
public class WSRPServiceUpgrade implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		BaseUpgradeWebModuleRelease upgradeWebModuleRelease =
			new BaseUpgradeWebModuleRelease() {

				@Override
				protected String getBundleSymbolicName() {
					return "com.liferay.wsrp.web";
				}

				@Override
				protected String[] getPortletIds() {
					return new String[] {"2_WAR_wsrpportlet"};
				}

			};

		try {
			upgradeWebModuleRelease.upgrade();
		}
		catch (UpgradeException ue) {
			throw new RuntimeException(ue);
		}

		registry.register(
			"com.liferay.wsrp.web", "0.0.0", "1.0.0", new DummyUpgradeStep());

		registry.register(
			"com.liferay.wsrp.web", "0.0.1", "0.0.2", new UpgradePortletId());

		registry.register(
			"com.liferay.wsrp.web", "0.0.2", "0.0.3",
			new UpgradeLastPublishDate());

		registry.register(
			"com.liferay.wsrp.web", "0.0.3", "0.0.4", new UpgradeUuid());

		registry.register(
			"com.liferay.wsrp.web", "0.0.4", "1.0.0", new UpgradeWSRP());
	}

	@Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED)
	private ModuleServiceLifecycle _moduleServiceLifecycle;

}