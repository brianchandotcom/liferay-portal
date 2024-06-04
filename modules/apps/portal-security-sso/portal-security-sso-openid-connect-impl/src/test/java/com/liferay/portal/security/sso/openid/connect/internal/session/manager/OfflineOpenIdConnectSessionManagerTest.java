/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.sso.openid.connect.internal.session.manager;

import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.URLUtil;
import com.liferay.portal.security.sso.openid.connect.persistence.model.OpenIdConnectSession;
import com.liferay.portal.security.sso.openid.connect.persistence.service.OpenIdConnectSessionLocalService;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import com.nimbusds.oauth2.sdk.Scope;
import com.nimbusds.oauth2.sdk.token.AccessToken;
import com.nimbusds.oauth2.sdk.token.AccessTokenType;
import com.nimbusds.oauth2.sdk.token.RefreshToken;
import com.nimbusds.openid.connect.sdk.token.OIDCTokens;

import java.net.URL;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Manuele Castro
 */
public class OfflineOpenIdConnectSessionManagerTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testStartOpenIdConnectSession() throws Exception {
		JSONFactory jsonFactory = new JSONFactoryImpl();

		JSONObject oidcTokensValuesJSONObject = jsonFactory.createJSONObject(
			URLUtil.toString(_oidcTokensValuesURL));

		AccessToken accessToken = new AccessToken(
			new AccessTokenType("Bearer"),
			oidcTokensValuesJSONObject.getString("accessToken"), 60,
			new Scope("openid, email, profile, groups")) {

			@Override
			public String toAuthorizationHeader() {
				return null;
			}

		};

		String idTokenString = oidcTokensValuesJSONObject.getString(
			"idTokenString");

		OfflineOpenIdConnectSessionManager offlineOpenIdConnectSessionManager =
			new OfflineOpenIdConnectSessionManager();

		OpenIdConnectSessionLocalService openIdConnectSessionLocalService =
			Mockito.mock(OpenIdConnectSessionLocalService.class);

		ReflectionTestUtil.setFieldValue(
			offlineOpenIdConnectSessionManager,
			"_openIdConnectSessionLocalService",
			openIdConnectSessionLocalService);

		OpenIdConnectSession openIdConnectSession = Mockito.mock(
			OpenIdConnectSession.class);

		Mockito.when(
			openIdConnectSessionLocalService.fetchOpenIdConnectSession(
				Mockito.anyLong(), Mockito.anyString(), Mockito.anyString())
		).thenReturn(
			openIdConnectSession
		);

		offlineOpenIdConnectSessionManager.startOpenIdConnectSession(
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			new OIDCTokens(
				idTokenString, accessToken,
				new RefreshToken(
					oidcTokensValuesJSONObject.getString("refreshToken"))),
			RandomTestUtil.randomLong());

		Mockito.verify(
			openIdConnectSession
		).setAccessToken(
			accessToken.toJSONString()
		);
		Mockito.verify(
			openIdConnectSession
		).setIdToken(
			idTokenString
		);
	}

	private static final URL _oidcTokensValuesURL =
		OfflineOpenIdConnectSessionManagerTest.class.getResource(
			"dependencies/oidc-tokens-values.json");

}