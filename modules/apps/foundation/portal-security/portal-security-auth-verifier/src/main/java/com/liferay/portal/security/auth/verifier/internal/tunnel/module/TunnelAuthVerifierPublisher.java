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

package com.liferay.portal.security.auth.verifier.internal.tunnel.module;

import com.liferay.portal.kernel.security.auth.verifier.AuthVerifier;
import com.liferay.portal.security.auth.verifier.internal.module.BaseAuthVerifierPublisher;
import com.liferay.portal.security.auth.verifier.tunnel.TunnelAuthVerifier;

import java.util.Map;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;

/**
 * @author Tomas Polesovsky
 */
@Component(
	configurationPid = "com.liferay.portal.security.auth.verifier.tunnel.module.configuration.TunnelAuthVerifierConfiguration",
	configurationPolicy = ConfigurationPolicy.REQUIRE
)
public class TunnelAuthVerifierPublisher extends BaseAuthVerifierPublisher {

	@Activate
	@Override
	protected void activate(
		BundleContext bundleContext, Map<String, Object> properties) {

		super.activate(bundleContext, properties);
	}

	@Deactivate
	@Override
	protected void deactivate() {
		super.deactivate();
	}

	@Override
	protected AuthVerifier getAuthVerifierInstance() {
		return _authVerifier;
	}

	@Modified
	@Override
	protected void modified(
		BundleContext bundleContext, Map<String, Object> properties) {

		super.modified(bundleContext, properties);
	}

	@Override
	protected String translateKey(String authVerifierPropertyName, String key) {
		if (key.equals("serviceAccessPolicyName")) {
			key = "service.access.policy.name";
		}

		return super.translateKey(authVerifierPropertyName, key);
	}

	private final AuthVerifier _authVerifier = new TunnelAuthVerifier();

}