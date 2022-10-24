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

package com.liferay.site.navigation.internal.upgrade.registry;

import com.liferay.portal.kernel.upgrade.BaseSQLServerDatetimeUpgradeProcess;
import com.liferay.portal.kernel.upgrade.CTModelUpgradeProcess;
import com.liferay.portal.kernel.upgrade.MVCCVersionUpgradeProcess;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.site.navigation.internal.upgrade.v2_0_0.util.SiteNavigationMenuItemTable;
import com.liferay.site.navigation.internal.upgrade.v2_0_0.util.SiteNavigationMenuTable;
import com.liferay.site.navigation.internal.upgrade.v2_3_0.SiteNavigationMenuItemUpgradeProcess;

import org.osgi.service.component.annotations.Component;

/**
 * @author José Ángel Jiménez
 */
@Component(service = UpgradeStepRegistrator.class)
public class SiteNavigationServiceUpgradeStepRegistrator
	implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.register(
			"1.0.0", "2.0.0",
			new BaseSQLServerDatetimeUpgradeProcess(
				new Class<?>[] {
					SiteNavigationMenuItemTable.class,
					SiteNavigationMenuTable.class
				}));

		registry.register(
			"2.0.0", "2.1.0",
			new MVCCVersionUpgradeProcess() {

				@Override
				protected String[] getModuleTableNames() {
					return new String[] {
						"SiteNavigationMenu", "SiteNavigationMenuItem"
					};
				}

			});

		registry.register(
			"2.1.0", "2.2.0",
			new CTModelUpgradeProcess(
				"SiteNavigationMenu", "SiteNavigationMenuItem"));

		registry.register(
			"2.2.0", "2.3.0", new SiteNavigationMenuItemUpgradeProcess());
	}

}