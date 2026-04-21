/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.sso.openid.connect.internal.session.manager;

import com.liferay.oauth.client.persistence.model.OAuthClientEntry;
import com.liferay.oauth.client.persistence.service.OAuthClientEntryLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.security.sso.openid.connect.internal.AuthorizationServerMetadataResolver;
import com.liferay.portal.security.sso.openid.connect.persistence.model.OpenIdConnectSession;
import com.liferay.portal.security.sso.openid.connect.persistence.service.OpenIdConnectSessionLocalService;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import com.nimbusds.oauth2.sdk.Scope;
import com.nimbusds.oauth2.sdk.id.Issuer;
import com.nimbusds.oauth2.sdk.token.AccessToken;
import com.nimbusds.oauth2.sdk.token.AccessTokenType;
import com.nimbusds.oauth2.sdk.token.RefreshToken;
import com.nimbusds.openid.connect.sdk.SubjectType;
import com.nimbusds.openid.connect.sdk.op.OIDCProviderMetadata;
import com.nimbusds.openid.connect.sdk.token.OIDCTokens;

import java.net.URI;

import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Manuele Castro
 */
public class OfflineOpenIdConnectSessionManagerTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testExtendOpenIdConnectSession() throws Exception {
		long oAuthClientEntryCompanyId = RandomTestUtil.randomLong();
		String authServerWellKnownURI =
			"https://idp.example.com/.well-known/openid-configuration";
		String clientId = RandomTestUtil.randomString();

		Assert.assertEquals(
			"Precondition: test thread must have the default (system) " +
				"companyId to simulate a background thread",
			Long.valueOf(0),
			CompanyThreadLocal.getCompanyId());

		OpenIdConnectSession openIdConnectSession = Mockito.mock(
			OpenIdConnectSession.class);

		Mockito.when(
			openIdConnectSession.getCompanyId()
		).thenReturn(
			oAuthClientEntryCompanyId
		);

		Mockito.when(
			openIdConnectSession.getAuthServerWellKnownURI()
		).thenReturn(
			authServerWellKnownURI
		);

		Mockito.when(
			openIdConnectSession.getClientId()
		).thenReturn(
			clientId
		);

		Mockito.when(
			openIdConnectSession.getRefreshToken()
		).thenReturn(
			RandomTestUtil.randomString()
		);

		OAuthClientEntry oAuthClientEntry = Mockito.mock(
			OAuthClientEntry.class);

		Mockito.when(
			oAuthClientEntry.getOAuthClientEntryId()
		).thenReturn(
			RandomTestUtil.randomLong()
		);

		Mockito.when(
			oAuthClientEntry.getCompanyId()
		).thenReturn(
			oAuthClientEntryCompanyId
		);

		Mockito.when(
			oAuthClientEntry.getAuthServerWellKnownURI()
		).thenReturn(
			authServerWellKnownURI
		);

		Mockito.when(
			oAuthClientEntry.getClientId()
		).thenReturn(
			clientId
		);

		Mockito.when(
			oAuthClientEntry.getMetadataCacheInSeconds()
		).thenReturn(
			3600
		);

		OAuthClientEntryLocalService oAuthClientEntryLocalService =
			Mockito.mock(OAuthClientEntryLocalService.class);

		Mockito.when(
			oAuthClientEntryLocalService.fetchOAuthClientEntry(
				oAuthClientEntryCompanyId, authServerWellKnownURI, clientId)
		).thenReturn(
			oAuthClientEntry
		);

		OIDCProviderMetadata oidcProviderMetadata = new OIDCProviderMetadata(
			new Issuer("https://idp.example.com"), List.of(SubjectType.PUBLIC),
			new URI("https://idp.example.com/jwks"));

		oidcProviderMetadata.setTokenEndpointURI(
			new URI("https://idp.example.com/token"));

		AuthorizationServerMetadataResolver
			authorizationServerMetadataResolver = Mockito.mock(
				AuthorizationServerMetadataResolver.class);

		Mockito.when(
			authorizationServerMetadataResolver.resolveOIDCProviderMetadata(
				Mockito.anyString(), Mockito.anyLong(), Mockito.anyInt(),
				Mockito.anyLong())
		).thenReturn(
			oidcProviderMetadata
		);

		ConfigurationAdmin configurationAdmin = Mockito.mock(
			ConfigurationAdmin.class);

		Mockito.when(
			configurationAdmin.listConfigurations(Mockito.anyString())
		).thenReturn(
			null
		);

		OpenIdConnectSessionLocalService openIdConnectSessionLocalService =
			Mockito.mock(OpenIdConnectSessionLocalService.class);

		OfflineOpenIdConnectSessionManager offlineOpenIdConnectSessionManager =
			new OfflineOpenIdConnectSessionManager();

		ReflectionTestUtil.setFieldValue(
			offlineOpenIdConnectSessionManager,
			"_authorizationServerMetadataResolver",
			authorizationServerMetadataResolver);
		ReflectionTestUtil.setFieldValue(
			offlineOpenIdConnectSessionManager, "_configurationAdmin",
			configurationAdmin);
		ReflectionTestUtil.setFieldValue(
			offlineOpenIdConnectSessionManager, "_oAuthClientEntryLocalService",
			oAuthClientEntryLocalService);
		ReflectionTestUtil.setFieldValue(
			offlineOpenIdConnectSessionManager,
			"_openIdConnectSessionLocalService",
			openIdConnectSessionLocalService);

		ReflectionTestUtil.invoke(
			offlineOpenIdConnectSessionManager, "_extendOpenIdConnectSession",
			new Class<?>[] {OpenIdConnectSession.class}, openIdConnectSession);

		ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(
			String.class);

		Mockito.verify(
			configurationAdmin
		).listConfigurations(
			argumentCaptor.capture()
		);

		String filterString = argumentCaptor.getValue();

		Assert.assertTrue(
			StringBundler.concat(
				"Configuration lookup filter must use the OAuthClientEntry's ",
				"companyId (", oAuthClientEntryCompanyId, "). Filter was: ",
				filterString),
			filterString.contains(
				"(companyId=" + oAuthClientEntryCompanyId + ")"));
		Assert.assertFalse(
			StringBundler.concat(
				"Configuration lookup filter must not fall back to ",
				"CompanyThreadLocal's default (0) when ",
				"_extendOpenIdConnectSession runs in a background thread. ",
				"Filter was: ", filterString),
			filterString.contains("(companyId=0)"));
	}

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