/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.internal.security.auth.verifier;

import com.liferay.ai.hub.configuration.AIHubConfiguration;
import com.liferay.ai.hub.security.JWTTokenUtil;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.AccessControlContext;
import com.liferay.portal.kernel.security.auth.AuthException;
import com.liferay.portal.kernel.security.auth.verifier.AuthVerifier;
import com.liferay.portal.kernel.security.auth.verifier.AuthVerifierResult;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Properties;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rafael Praxedes
 */
@Component(
	property = "auth.verifier.AIHubRequestAuthVerifier.urls.includes=*",
	service = AuthVerifier.class
)
public class AIHubRequestAuthVerifier implements AuthVerifier {

	@Override
	public String getAuthType() {
		Class<?> clazz = getClass();

		return clazz.getSimpleName();
	}

	@Override
	public AuthVerifierResult verify(
			AccessControlContext accessControlContext, Properties properties)
		throws AuthException {

		AuthVerifierResult authVerifierResult = new AuthVerifierResult();

		HttpServletRequest httpServletRequest =
			accessControlContext.getRequest();

		try {
			String requestUrl = String.valueOf(
				httpServletRequest.getRequestURL());

			AIHubConfiguration aiHubConfiguration =
				_configurationProvider.getCompanyConfiguration(
					AIHubConfiguration.class,
					_portal.getCompanyId(httpServletRequest));

			if (!requestUrl.startsWith(aiHubConfiguration.serviceURL())) {
				return authVerifierResult;
			}

			String token = httpServletRequest.getHeader(
				"Liferay-AI-Hub-On-Behalf-Of");

			if (Validator.isBlank(token)) {
				return authVerifierResult;
			}

			long userId = JWTTokenUtil.getUserId(token);

			if (userId == 0) {
				authVerifierResult.setState(
					AuthVerifierResult.State.INVALID_CREDENTIALS);

				return authVerifierResult;
			}

			authVerifierResult.setState(AuthVerifierResult.State.SUCCESS);
			authVerifierResult.setUserId(userId);

			return authVerifierResult;
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to verify AI Hub JWT token", exception);
			}

			return authVerifierResult;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AIHubRequestAuthVerifier.class);

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private Portal _portal;

}