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

package com.liferay.shopping.upgrade;

import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.shopping.upgrade.v1_0_0.UpgradeClassNames;
import com.liferay.shopping.upgrade.v1_0_0.UpgradeCompanyId;
import com.liferay.shopping.upgrade.v1_0_0.UpgradePortletId;
import com.liferay.shopping.upgrade.v1_0_0.UpgradeShopping;
import com.liferay.shopping.upgrade.v1_0_0.UpgradeShoppingPreferences;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Peter Fellwock
 * @author Manuel de la Peña
 */
@Component(immediate = true, service = ShoppingServiceUpgrade.class)
public class ShoppingServiceUpgrade implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.register(
			"com.liferay.shopping.service", "0.0.1", "1.0.0",
			new UpgradePortletId(), new UpgradeClassNames(),
			new UpgradeCompanyId(), new UpgradeShopping(),
			new UpgradeShoppingPreferences());
	}

	@Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED, unbind = "-")
	protected void setModuleServiceLifecycle(
		ModuleServiceLifecycle moduleServiceLifecycle) {
	}

}