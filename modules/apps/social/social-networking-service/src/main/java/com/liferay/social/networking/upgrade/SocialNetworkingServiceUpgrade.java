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

package com.liferay.social.networking.upgrade;

import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.social.networking.upgrade.v1_0_0.UpgradeNamespace;
import com.liferay.social.networking.upgrade.v1_0_1.UpgradePortletId;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 * @author Manuel de la Peña
 */
@Component(immediate = true, service = UpgradeStepRegistrator.class)
public class SocialNetworkingServiceUpgrade implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.register(
			"com.liferay.social.networking.service", "1.0.0", "1.0.1",
			new UpgradeNamespace());

		registry.register(
			"com.liferay.social.networking.service", "1.0.1", "1.0.2",
			new UpgradePortletId());
	}

	@Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED, unbind = "-")
	protected void setModuleServiceLifecycle(
		ModuleServiceLifecycle moduleServiceLifecycle) {
	}

}