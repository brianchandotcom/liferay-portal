/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.auth.verifier.internal.upgrade.registry;

import com.liferay.portal.configuration.persistence.upgrade.ConfigurationUpgradeStepFactory;
import com.liferay.portal.security.auth.verifier.internal.basic.auth.header.configuration.BasicAuthHeaderAuthVerifierConfiguration;
import com.liferay.portal.security.auth.verifier.internal.configuration.BaseAuthVerifierConfiguration;
import com.liferay.portal.security.auth.verifier.internal.digest.authentication.configuration.DigestAuthenticationAuthVerifierConfiguration;
import com.liferay.portal.security.auth.verifier.internal.portal.session.configuration.PortalSessionAuthVerifierConfiguration;
import com.liferay.portal.security.auth.verifier.internal.request.parameter.configuration.RequestParameterAuthVerifierConfiguration;
import com.liferay.portal.security.auth.verifier.internal.tunnel.configuration.TunnelAuthVerifierConfiguration;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Tom Wang
 */
@Component(service = UpgradeStepRegistrator.class)
public class PortalSecurityAuthVerifierUpgradeStepRegistrator
	implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.register(
			"0.0.0", "1.0.0",
			_configurationUpgradeStepFactory.createUpgradeStep(
				"com.liferay.portal.security.auth.verifier.basic.auth.header." +
					"module.configuration." +
						"BasicAuthHeaderAuthVerifierConfiguration",
				BasicAuthHeaderAuthVerifierConfiguration.class.getName()),
			_configurationUpgradeStepFactory.createUpgradeStep(
				"com.liferay.portal.security.auth.verifier.module." +
					"configuration.BaseAuthVerifierConfiguration",
				BaseAuthVerifierConfiguration.class.getName()),
			_configurationUpgradeStepFactory.createUpgradeStep(
				"com.liferay.portal.security.auth.verifier.digest." +
					"authentication.module.configuration." +
						"DigestAuthenticationAuthVerifierConfiguration",
				DigestAuthenticationAuthVerifierConfiguration.class.getName()),
			_configurationUpgradeStepFactory.createUpgradeStep(
				"com.liferay.portal.security.auth.verifier.portal.session." +
					"module.configuration." +
						"PortalSessionAuthVerifierConfiguration",
				PortalSessionAuthVerifierConfiguration.class.getName()),
			_configurationUpgradeStepFactory.createUpgradeStep(
				"com.liferay.portal.security.auth.verifier.request.parameter." +
					"module.configuration." +
						"RequestParameterAuthVerifierConfiguration",
				RequestParameterAuthVerifierConfiguration.class.getName()),
			_configurationUpgradeStepFactory.createUpgradeStep(
				"com.liferay.portal.security.auth.verifier.tunnel.module." +
					"configuration.TunnelAuthVerifierConfiguration",
				TunnelAuthVerifierConfiguration.class.getName()));
	}

	@Reference
	private ConfigurationUpgradeStepFactory _configurationUpgradeStepFactory;

}