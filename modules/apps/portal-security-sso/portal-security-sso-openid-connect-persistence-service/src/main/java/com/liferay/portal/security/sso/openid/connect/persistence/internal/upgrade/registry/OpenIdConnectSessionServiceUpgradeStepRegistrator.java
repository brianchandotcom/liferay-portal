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

package com.liferay.portal.security.sso.openid.connect.persistence.internal.upgrade.registry;

import com.liferay.portal.kernel.upgrade.UpgradeProcessFactory;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Arthur Chan
 */
@Component(service = UpgradeStepRegistrator.class)
public class OpenIdConnectSessionServiceUpgradeStepRegistrator
	implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.register(
			"1.0.0", "1.1.0",
			UpgradeProcessFactory.addColumns(
				"OpenIdConnectSession", "userId LONG",
				"configurationPid VARCHAR(256) null"));

		registry.register(
			"1.1.0", "2.0.0",
			new com.liferay.portal.security.sso.openid.connect.persistence.
				internal.upgrade.v2_0_0.OpenIdConnectSessionUpgradeProcess(
					_configurationAdmin));

		registry.register(
			"2.0.0", "2.1.0",
			new com.liferay.portal.security.sso.openid.connect.persistence.
				internal.upgrade.v2_1_0.OpenIdConnectSessionUpgradeProcess());
	}

	@Reference
	private ConfigurationAdmin _configurationAdmin;

}