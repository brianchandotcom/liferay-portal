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

package com.liferay.oauth2.provider.token.endpoint.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.oauth2.provider.internal.test.PasswordAuthorizationGrant;
import com.liferay.oauth2.provider.internal.test.util.JWTAssertionUtil;
import com.liferay.portal.configuration.test.util.ConfigurationTemporarySwapper;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;

import org.apache.cxf.rs.security.jose.jwk.JwkUtils;
import org.apache.cxf.rs.security.jose.jws.JwsHeaders;
import org.apache.cxf.rs.security.jose.jws.JwsJwtCompactConsumer;
import org.apache.cxf.rs.security.jose.jws.JwsSignatureVerifier;
import org.apache.cxf.rs.security.jose.jws.JwsUtils;
import org.apache.cxf.rs.security.jose.jwt.JwtToken;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.BundleActivator;

/**
 * @author Arthur Chan
 */
@RunWith(Arquillian.class)
public class IssueJWTAccessTokenTest extends BaseTokenEndpointTestCase {

	@Test
	public void testIssuedJWTAccessToken() {
		_verifyJWTAccessToken(
			getAccessToken(
				new PasswordAuthorizationGrant("test@liferay.com", "test"),
				clientAuthentications.get(TEST_CLIENT_ID_1)));
	}

	@Override
	protected BundleActivator getBundleActivator() {
		return new IssueJWTAccessTokenTestPreparatorBundleActivator();
	}

	private void _verifyJWTAccessToken(String jwtAccessToken) {
		JwsJwtCompactConsumer jwsJwtCompactConsumer = new JwsJwtCompactConsumer(
			jwtAccessToken);

		JwtToken jwtToken = jwsJwtCompactConsumer.getJwtToken();

		JwsHeaders jwsHeaders = jwtToken.getJwsHeaders();

		JwsSignatureVerifier jwsSignatureVerifier =
			JwsUtils.getSignatureVerifier(
				JwkUtils.readJwkKey(JWTAssertionUtil.JWK));

		Assert.assertTrue(
			jwsSignatureVerifier.verify(
				jwsHeaders, jwsJwtCompactConsumer.getUnsignedEncodedSequence(),
				jwsJwtCompactConsumer.getDecodedSignature()));
	}

	private static class IssueJWTAccessTokenTestPreparatorBundleActivator
		extends TestPreparatorBundleActivator {

		@Override
		protected void prepareTest() throws Exception {
			autoCloseables.add(
				new ConfigurationTemporarySwapper(
					"com.liferay.oauth2.provider.rest.internal.configuration." +
						"OAuth2AuthorizationServerConfiguration",
					HashMapDictionaryBuilder.<String, Object>put(
						"oauth2.authorization.server.issue.jwt.access.token",
						true
					).put(
						"oauth2.authorization.server.jwt.access.token." +
							"signing.json.web.key",
						JWTAssertionUtil.JWK
					).build()));

			super.prepareTest();
		}

	}

}