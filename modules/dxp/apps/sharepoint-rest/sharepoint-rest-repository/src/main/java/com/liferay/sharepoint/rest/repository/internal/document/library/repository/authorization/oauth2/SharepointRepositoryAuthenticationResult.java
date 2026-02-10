/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.sharepoint.rest.repository.internal.document.library.repository.authorization.oauth2;

import com.liferay.document.library.repository.authorization.oauth2.Token;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import com.microsoft.aad.msal4j.IAuthenticationResult;

import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import java.text.ParseException;

import java.util.Map;

/**
 * @author Marco Galluzzi
 */
public class SharepointRepositoryAuthenticationResult {

	public SharepointRepositoryAuthenticationResult(
		IAuthenticationResult iAuthenticationResult, String tokenCache) {

		_iAuthenticationResult = iAuthenticationResult;
		_tokenCache = tokenCache;
	}

	public String getAccessToken() {
		if (_iAuthenticationResult == null) {
			return null;
		}

		return _iAuthenticationResult.accessToken();
	}

	public String getNonce() {
		if (_iAuthenticationResult == null) {
			return null;
		}

		try {
			SignedJWT signedJWT = SignedJWT.parse(
				_iAuthenticationResult.idToken());

			JWTClaimsSet jwtClaimsSet = signedJWT.getJWTClaimsSet();

			Map<String, Object> claims = jwtClaimsSet.getClaims();

			return (String)claims.get("nonce");
		}
		catch (ParseException parseException) {
			_log.error("Unable to get nonce", parseException);

			return null;
		}
	}

	public Token getToken() {
		return SharepointRepositoryToken.newInstance(_tokenCache);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SharepointRepositoryAuthenticationResult.class);

	private final IAuthenticationResult _iAuthenticationResult;
	private final String _tokenCache;

}