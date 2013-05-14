/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.oauth;

import com.liferay.portal.kernel.oauth.OAuthException;
import com.liferay.portal.kernel.oauth.OAuthManager;
import com.liferay.portal.kernel.oauth.OAuthRequest;
import com.liferay.portal.kernel.oauth.Token;
import com.liferay.portal.kernel.oauth.Verifier;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.DefaultApi10a;
import org.scribe.model.OAuthConstants;
import org.scribe.oauth.OAuthService;

/**
 * @author Brian Wing Shun Chan
 */
public class OAuthManagerImpl implements OAuthManager {

	public OAuthManagerImpl(
		String key, String secret, final String accessURL,
		final String requestURL, String callbackURL, String scope) {

		Api api = new DefaultApi10a() {

			@Override
			public String getAccessTokenEndpoint() {
				return accessURL;
			}

			@Override
			public String getRequestTokenEndpoint() {
				return requestURL;
			}

		};

		if (callbackURL == null) {
			callbackURL = OAuthConstants.OUT_OF_BAND;
		}

		_oAuthService = api.createService(key, secret, callbackURL, scope);
	}

	@Override
	public Token getAccessToken(Token requestToken, Verifier verifier)
		throws OAuthException {

		try {
			return new TokenImpl(
				_oAuthService.getAccessToken(
					(org.scribe.model.Token)requestToken.getWrappedToken(),
					(org.scribe.model.Verifier)verifier.getWrappedVerifier()));
		}
		catch (Exception e) {
			throw new OAuthException(e);
		}
	}

	@Override
	public Token getRequestToken() throws OAuthException {
		try {
			return new TokenImpl(_oAuthService.getRequestToken());
		}
		catch (Exception e) {
			throw new OAuthException(e);
		}
	}

	@Override
	public String getVersion() throws OAuthException {
		try {
			return _oAuthService.getVersion();
		}
		catch (Exception e) {
			throw new OAuthException(e);
		}
	}

	@Override
	public void signRequest(Token accessToken, OAuthRequest oAuthRequest)
		throws OAuthException {

		try {
			_oAuthService.signRequest(
				(org.scribe.model.Token)accessToken.getWrappedToken(),
				(org.scribe.model.OAuthRequest)
					oAuthRequest.getWrappedOAuthRequest());
		}
		catch (Exception e) {
			throw new OAuthException(e);
		}
	}

	private OAuthService _oAuthService;

}