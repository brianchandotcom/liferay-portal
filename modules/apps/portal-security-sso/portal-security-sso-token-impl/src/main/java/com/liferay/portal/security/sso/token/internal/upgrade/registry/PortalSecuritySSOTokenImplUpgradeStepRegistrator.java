/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.sso.token.internal.upgrade.registry;

import com.liferay.portal.configuration.persistence.upgrade.ConfigurationUpgradeStepFactory;
import com.liferay.portal.security.sso.token.internal.upgrade.v2_0_0.TokenConfigurationUpgradeProcess;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Tom Wang
 */
@Component(service = UpgradeStepRegistrator.class)
public class PortalSecuritySSOTokenImplUpgradeStepRegistrator
	implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.registerInitialization();

		registry.register(
			"0.0.1", "1.0.0",
			_configurationUpgradeStepFactory.createUpgradeStep(
				"com.liferay.portal.security.sso.token.configuration." +
					"TokenConfiguration",
				"com.liferay.portal.security.sso.token.internal." +
					"configuration.TokenConfiguration"));

		registry.register(
			"1.0.0", "1.0.1", new TokenConfigurationUpgradeProcess());

		registry.register(
			"1.0.1", "2.0.0",
			_configurationUpgradeStepFactory.createUpgradeStep(
				"com.liferay.portal.security.sso.token.internal." +
					"configuration.TokenConfiguration",
				"com.liferay.portal.security.sso.token.configuration." +
					"TokenConfiguration"));
	}

	@Reference
	private ConfigurationUpgradeStepFactory _configurationUpgradeStepFactory;

}