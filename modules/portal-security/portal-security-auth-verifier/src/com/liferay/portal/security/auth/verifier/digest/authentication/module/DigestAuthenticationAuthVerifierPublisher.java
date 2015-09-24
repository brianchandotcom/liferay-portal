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

package com.liferay.portal.security.auth.verifier.digest.authentication.module;

import com.liferay.portal.kernel.security.auth.verifier.AuthVerifier;
import com.liferay.portal.security.auth.verifier.digest.authentication.DigestAuthenticationAuthVerifier;
import com.liferay.portal.security.auth.verifier.module.AbstractAuthVerifierPublisher;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;

/**
 * @author Tomas Polesovsky
 */
@Component(
	configurationPid = "com.liferay.portal.security.auth.verifier.digest.authentication.module.configuration.DigestAuthenticationAuthVerifierConfiguration",
	configurationPolicy = ConfigurationPolicy.REQUIRE
)
public class DigestAuthenticationAuthVerifierPublisher
	extends AbstractAuthVerifierPublisher {

	@Override
	protected AuthVerifier getAuthVerifierInstance() {
		return _authVerifier;
	}

	protected String translateKey(String authVerifierPropertyName, String key) {
		if (key.equals("forceDigestAuth")) {
			key = "digest_auth";
		}

		return super.translateKey(authVerifierPropertyName, key);
	}

	private final AuthVerifier _authVerifier =
		new DigestAuthenticationAuthVerifier();

}