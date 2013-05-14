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
import com.liferay.portal.kernel.oauth.OAuthFactory;
import com.liferay.portal.kernel.oauth.OAuthManager;
import com.liferay.portal.kernel.oauth.OAuthRequest;
import com.liferay.portal.kernel.oauth.Token;
import com.liferay.portal.kernel.oauth.Verb;
import com.liferay.portal.kernel.oauth.Verifier;
import com.liferay.portal.kernel.security.pacl.DoPrivileged;

/**
 * @author Brian Wing Shun Chan
 */
@DoPrivileged
public class OAuthFactoryImpl implements OAuthFactory {

	@Override
	public OAuthManager createOAuthManager(
			String key, String secret, String accessURL, String requestURL,
			String callbackURL, String scope)
		throws OAuthException {

		try {
			return new OAuthManagerImpl(
				key, secret, accessURL, requestURL, callbackURL, scope);
		}
		catch (Exception e) {
			throw new OAuthException(e);
		}
	}

	@Override
	public OAuthRequest createOAuthRequest(Verb verb, String url)
		throws OAuthException {

		try {
			return new OAuthRequestImpl(
				new org.scribe.model.OAuthRequest(
					VerbTranslator.translate(verb), url));
		}
		catch (Exception e) {
			throw new OAuthException(e);
		}
	}

	@Override
	public Token createToken(String token, String secret)
		throws OAuthException {

		try {
			return new TokenImpl(new org.scribe.model.Token(token, secret));
		}
		catch (Exception e) {
			throw new OAuthException(e);
		}
	}

	@Override
	public Verifier createVerifier(String verifier) throws OAuthException {
		try {
			return new VerifierImpl(new org.scribe.model.Verifier(verifier));
		}
		catch (Exception e) {
			throw new OAuthException(e);
		}
	}

}