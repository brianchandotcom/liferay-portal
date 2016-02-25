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

package com.liferay.portal.kernel.oauth;

/**
 * @author Brian Wing Shun Chan
 */
public interface OAuthFactory {

	public OAuthManager createOAuthManager(
			String key, String secret, String accessURL, String requestURL,
			String callbackURL, String scope)
		throws OAuthException;

	public OAuthRequest createOAuthRequest(Verb verb, String url)
		throws OAuthException;

	public Token createToken(String token, String secret) throws OAuthException;

	public Verifier createVerifier(String verifier) throws OAuthException;

}