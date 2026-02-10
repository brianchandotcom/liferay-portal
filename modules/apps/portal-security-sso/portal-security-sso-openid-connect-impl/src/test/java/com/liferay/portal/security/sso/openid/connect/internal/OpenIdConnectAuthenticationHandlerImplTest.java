/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.sso.openid.connect.internal;

import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.security.sso.openid.connect.OpenIdConnectServiceException;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.oauth2.sdk.token.AccessToken;
import com.nimbusds.oauth2.sdk.util.JSONObjectUtils;
import com.nimbusds.openid.connect.sdk.op.OIDCProviderMetadata;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.net.URI;

import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Tamas Biro
 */
public class OpenIdConnectAuthenticationHandlerImplTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testGetUserInfoClaims() throws Exception {
		Map<String, Object> claims = _getUserInfoClaims(
			JSONUtil.put(
				"email", "exists@test.com"
			).put(
				"name", "test_account"
			).put(
				"sub", "subject"
			).toString());

		Assert.assertEquals("exists@test.com", claims.get("email"));

		claims = _getUserInfoClaims(
			JSONUtil.put(
				"name", "test_account"
			).put(
				"sub", "subject"
			).toString());

		Assert.assertNull(claims.get("email"));
	}

	@Test
	public void testRequestUserInfoJSON() throws Exception {
		OIDCProviderMetadata oidcProviderMetadata = Mockito.mock(
			OIDCProviderMetadata.class);

		Mockito.when(
			oidcProviderMetadata.getUserInfoEndpointURI()
		).thenReturn(
			new URI("http://172.17.0.3:18080/")
		);

		OpenIdConnectAuthenticationHandlerImpl
			openIdConnectAuthenticationHandlerImpl =
				new OpenIdConnectAuthenticationHandlerImpl();

		AccessToken accessToken = AccessToken.parse(
			JSONObjectUtils.parse(
				"{\"access_token\": \"test\", \"token_type\":\"Bearer\"}"));

		try {
			Method requestUserInfoJSONMethod = ReflectionTestUtil.getMethod(
				OpenIdConnectAuthenticationHandlerImpl.class,
				"_requestUserInfoJSON", AccessToken.class,
				OIDCProviderMetadata.class);

			requestUserInfoJSONMethod.invoke(
				openIdConnectAuthenticationHandlerImpl, accessToken,
				oidcProviderMetadata);
		}
		catch (InvocationTargetException invocationTargetException) {
			Throwable throwable = invocationTargetException.getCause();

			Assert.assertEquals(
				OpenIdConnectServiceException.UserInfoException.class,
				throwable.getClass());
		}
	}

	private Map<String, Object> _getUserInfoClaims(String claimSetJSON)
		throws Exception {

		OpenIdConnectAuthenticationHandlerImpl
			openIdConnectAuthenticationHandlerImpl =
				new OpenIdConnectAuthenticationHandlerImpl();

		JWT mockJWT = Mockito.mock(JWT.class);

		Mockito.when(
			mockJWT.getJWTClaimsSet()
		).thenReturn(
			JWTClaimsSet.parse(claimSetJSON)
		);

		return openIdConnectAuthenticationHandlerImpl.getUserInfoClaims(
			mockJWT);
	}

}