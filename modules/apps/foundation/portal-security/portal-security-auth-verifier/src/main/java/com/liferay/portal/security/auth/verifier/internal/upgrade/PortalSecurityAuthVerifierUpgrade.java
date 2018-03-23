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

package com.liferay.portal.security.auth.verifier.internal.upgrade;

import com.liferay.portal.configuration.persistence.upgrade.ConfigurationUpgradeStepFactory;
import com.liferay.portal.security.auth.verifier.internal.basic.auth.header.module.configuration.BasicAuthHeaderAuthVerifierConfiguration;
import com.liferay.portal.security.auth.verifier.internal.digest.authentication.module.configuration.DigestAuthenticationAuthVerifierConfiguration;
import com.liferay.portal.security.auth.verifier.internal.module.configuration.BaseAuthVerifierConfiguration;
import com.liferay.portal.security.auth.verifier.internal.portal.session.module.configuration.PortalSessionAuthVerifierConfiguration;
import com.liferay.portal.security.auth.verifier.internal.request.parameter.module.configuration.RequestParameterAuthVerifierConfiguration;
import com.liferay.portal.security.auth.verifier.internal.tunnel.module.configuration.TunnelAuthVerifierConfiguration;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Tom Wang
 */
@Component(immediate = true, service = UpgradeStepRegistrator.class)
public class PortalSecurityAuthVerifierUpgrade
	implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.register(
			"com.liferay.portal.security.auth.verifier", "0.0.0", "1.0.0",
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