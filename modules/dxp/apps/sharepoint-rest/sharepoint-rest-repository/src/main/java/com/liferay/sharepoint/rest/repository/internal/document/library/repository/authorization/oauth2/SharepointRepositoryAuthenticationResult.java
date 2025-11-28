/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.sharepoint.rest.repository.internal.document.library.repository.authorization.oauth2;

import com.liferay.document.library.repository.authorization.oauth2.Token;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONSerializer;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import com.microsoft.aad.msal4j.IAuthenticationResult;

import com.nimbusds.jwt.SignedJWT;

import java.text.ParseException;

import java.util.Map;

/**
 * @author Marco Galluzzi
 */
public class SharepointRepositoryAuthenticationResult {

	public SharepointRepositoryAuthenticationResult(
		String accessToken, IAuthenticationResult iAuthenticationResult) {

		_accessToken = accessToken;
		_iAuthenticationResult = iAuthenticationResult;
	}

	public String getNonce() {
		try {
			Map<String, Object> tokenClaims = SignedJWT.parse(
				_iAuthenticationResult.idToken()
			).getJWTClaimsSet(
			).getClaims();

			return (String)tokenClaims.get("nonce");
		}
		catch (ParseException parseException) {
			_log.error(
				"Unable to get the nonce value from the token claims",
				parseException);

			return null;
		}
	}

	public Token getToken() {
		JSONSerializer jsonSerializer = JSONFactoryUtil.createJSONSerializer();

		return SharepointRepositoryToken.newInstance(
			_accessToken,
			jsonSerializer.serialize(_iAuthenticationResult.account()));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SharepointRepositoryAuthenticationResult.class);

	private final String _accessToken;
	private final IAuthenticationResult _iAuthenticationResult;

}