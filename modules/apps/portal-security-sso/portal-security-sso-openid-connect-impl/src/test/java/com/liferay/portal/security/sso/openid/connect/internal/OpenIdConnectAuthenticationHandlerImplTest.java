/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.sso.openid.connect.internal;

import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.security.sso.openid.connect.OpenIdConnectServiceException;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.oauth2.sdk.token.AccessToken;
import com.nimbusds.oauth2.sdk.util.JSONObjectUtils;
import com.nimbusds.openid.connect.sdk.op.OIDCProviderMetadata;

import java.net.SocketTimeoutException;
import java.net.URI;

import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.MockedStatic;
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
		try (MockedStatic<HttpUtil> httpUtilMockedStatic = Mockito.mockStatic(
				HttpUtil.class)) {

			httpUtilMockedStatic.when(
				() -> HttpUtil.URLtoString(Mockito.any(Http.Options.class))
			).thenAnswer(
				invocation -> {
					Http.Options httpOptions = invocation.getArgument(0);

					Http.Response httpResponse = new Http.Response();

					httpResponse.setContentType("application/json");
					httpResponse.setResponseCode(200);

					httpOptions.setResponse(httpResponse);

					return JSONUtil.put(
						"email", "test@example.com"
					).put(
						"sub", "subject"
					).toString();
				}
			);

			String userInfoJSON = _requestUserInfoJSON();

			Assert.assertNotNull(userInfoJSON);
			Assert.assertTrue(
				userInfoJSON,
				userInfoJSON.contains("\"email\":\"test@example.com\""));
			Assert.assertTrue(
				userInfoJSON, userInfoJSON.contains("\"sub\":\"subject\""));
		}

		try (MockedStatic<HttpUtil> httpUtilMockedStatic = Mockito.mockStatic(
				HttpUtil.class)) {

			httpUtilMockedStatic.when(
				() -> HttpUtil.URLtoString(Mockito.any(Http.Options.class))
			).thenThrow(
				new SocketTimeoutException("Read timed out")
			);

			try {
				_requestUserInfoJSON();

				Assert.fail();
			}
			catch (OpenIdConnectServiceException.UserInfoException
						userInfoException) {

				Throwable throwable = userInfoException.getCause();

				Assert.assertTrue(throwable instanceof SocketTimeoutException);
				Assert.assertTrue(
					throwable.getMessage(),
					throwable.getMessage(
					).contains(
						"Read timed out"
					));
			}
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

	private String _requestUserInfoJSON() throws Exception {
		OpenIdConnectAuthenticationHandlerImpl
			openIdConnectAuthenticationHandlerImpl =
				new OpenIdConnectAuthenticationHandlerImpl();

		OIDCProviderMetadata oidcProviderMetadata = Mockito.mock(
			OIDCProviderMetadata.class);

		Mockito.when(
			oidcProviderMetadata.getUserInfoEndpointURI()
		).thenReturn(
			new URI("http://172.17.0.3:18080/")
		);

		AccessToken accessToken = AccessToken.parse(
			JSONObjectUtils.parse(
				"{\"access_token\": \"test\", \"token_type\":\"Bearer\"}"));

		return ReflectionTestUtil.invoke(
			openIdConnectAuthenticationHandlerImpl, "_requestUserInfoJSON",
			new Class<?>[] {AccessToken.class, OIDCProviderMetadata.class},
			accessToken, oidcProviderMetadata);
	}

}