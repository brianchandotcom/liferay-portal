/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.auth.verifier.internal.digest.authentication;

import com.liferay.portal.kernel.security.auth.verifier.AuthVerifier;
import com.liferay.portal.security.auth.verifier.internal.BaseAuthVerifierPipelineConfigurator;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;

/**
 * @author Tomas Polesovsky
 * @deprecated As of Cavanaugh (7.4.x), with no direct replacement
 */
@Component(
	configurationPid = "com.liferay.portal.security.auth.verifier.internal.digest.authentication.configuration.DigestAuthenticationAuthVerifierConfiguration",
	configurationPolicy = ConfigurationPolicy.REQUIRE, service = {}
)
@Deprecated
public class DigestAuthenticationAuthVerifierPipelineConfigurator
	extends BaseAuthVerifierPipelineConfigurator {

	@Override
	protected Class<? extends AuthVerifier> getAuthVerifierClass() {
		return DigestAuthenticationAuthVerifier.class;
	}

	@Override
	protected String translateKey(String authVerifierPropertyName, String key) {
		if (key.equals("forceDigestAuth")) {
			key = "digest_auth";
		}

		return super.translateKey(authVerifierPropertyName, key);
	}

}