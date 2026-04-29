/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.internal.agent;

import com.liferay.ai.hub.agent.OAuth2ApplicationIdResolver;
import com.liferay.oauth2.provider.model.OAuth2Authorization;
import com.liferay.oauth2.provider.service.OAuth2AuthorizationLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.auth.http.HttpAuthorizationHeader;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.util.Validator;

import jakarta.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Christopher Kian
 */
@Component(service = OAuth2ApplicationIdResolver.class)
public class OAuth2ApplicationIdResolverImpl
	implements OAuth2ApplicationIdResolver {

	@Override
	public long resolve(HttpServletRequest httpServletRequest)
		throws PrincipalException {

		String authorization = httpServletRequest.getHeader(
			HttpHeaders.AUTHORIZATION);

		if (Validator.isNull(authorization)) {
			return 0L;
		}

		if (authorization.startsWith(_BEARER_PREFIX)) {
			OAuth2Authorization oAuth2Authorization =
				_oAuth2AuthorizationLocalService.
					fetchOAuth2AuthorizationByAccessTokenContent(
						authorization.substring(_BEARER_PREFIX.length()));

			if (oAuth2Authorization != null) {
				return oAuth2Authorization.getOAuth2ApplicationId();
			}
		}

		throw new PrincipalException(
			"Invalid " + HttpHeaders.AUTHORIZATION + " token");
	}

	private static final String _BEARER_PREFIX =
		HttpAuthorizationHeader.SCHEME_BEARER + StringPool.SPACE;

	@Reference
	private OAuth2AuthorizationLocalService _oAuth2AuthorizationLocalService;

}