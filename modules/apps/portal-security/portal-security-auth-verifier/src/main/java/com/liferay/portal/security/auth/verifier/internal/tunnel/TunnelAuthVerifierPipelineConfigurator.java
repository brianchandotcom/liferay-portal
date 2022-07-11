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

package com.liferay.portal.security.auth.verifier.internal.tunnel;

import com.liferay.portal.kernel.security.auth.verifier.AuthVerifier;
import com.liferay.portal.security.auth.verifier.internal.BaseAuthVerifierPipelineConfigurator;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;

/**
 * @author Tomas Polesovsky
 */
@Component(
	configurationPid = "com.liferay.portal.security.auth.verifier.internal.tunnel.configuration.TunnelAuthVerifierConfiguration",
	configurationPolicy = ConfigurationPolicy.REQUIRE, service = {}
)
public class TunnelAuthVerifierPipelineConfigurator
	extends BaseAuthVerifierPipelineConfigurator {

	@Override
	protected Class<? extends AuthVerifier> getAuthVerifierClass() {
		return TunnelAuthVerifier.class;
	}

	@Override
	protected String translateKey(String authVerifierPropertyName, String key) {
		if (key.equals("serviceAccessPolicyName")) {
			key = "service.access.policy.name";
		}

		return super.translateKey(authVerifierPropertyName, key);
	}

}