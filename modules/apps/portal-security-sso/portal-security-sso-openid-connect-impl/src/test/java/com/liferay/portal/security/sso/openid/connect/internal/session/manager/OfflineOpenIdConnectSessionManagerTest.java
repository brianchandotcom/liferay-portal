/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.sso.openid.connect.internal.session.manager;

import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.security.sso.openid.connect.persistence.model.OpenIdConnectSession;
import com.liferay.portal.security.sso.openid.connect.persistence.service.OpenIdConnectSessionLocalService;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import com.nimbusds.oauth2.sdk.Scope;
import com.nimbusds.oauth2.sdk.token.AccessToken;
import com.nimbusds.oauth2.sdk.token.AccessTokenType;
import com.nimbusds.oauth2.sdk.token.RefreshToken;
import com.nimbusds.openid.connect.sdk.token.OIDCTokens;

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
	public void testStartOpenIdConnectSession() {
		AccessToken accessToken = new AccessToken(
			new AccessTokenType("Bearer"), RandomTestUtil.randomString(5000),
			60, new Scope("email, groups, openid, profile")) {

			@Override
			public String toAuthorizationHeader() {
				return null;
			}

		};

		String idTokenString = RandomTestUtil.randomString(5000);

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
				new RefreshToken(RandomTestUtil.randomString(1500))),
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

}