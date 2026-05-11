/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.rest.internal.util;

import com.liferay.oauth2.provider.model.OAuth2Authorization;
import com.liferay.oauth2.provider.service.OAuth2AuthorizationLocalServiceUtil;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.util.Validator;

import jakarta.servlet.http.HttpServletRequest;

/**
 * @author Christopher Kian
 */
public class OAuth2ApplicationIdResolverUtil {

	public static long resolve(HttpServletRequest httpServletRequest)
		throws PrincipalException {

		String authorization = httpServletRequest.getHeader(
			HttpHeaders.AUTHORIZATION);

		if (Validator.isNull(authorization)) {
			return 0L;
		}

		if (authorization.startsWith("Bearer ")) {
			OAuth2Authorization oAuth2Authorization =
				OAuth2AuthorizationLocalServiceUtil.
					fetchOAuth2AuthorizationByAccessTokenContent(
						authorization.substring(7));

			if (oAuth2Authorization != null) {
				return oAuth2Authorization.getOAuth2ApplicationId();
			}
		}

		throw new PrincipalException(
			"Invalid " + HttpHeaders.AUTHORIZATION + " token");
	}

}