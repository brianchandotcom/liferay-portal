/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.sso.openid.connect.internal;

import com.liferay.oauth.client.persistence.model.OAuthClientEntry;
import com.liferay.oauth.client.persistence.service.OAuthClientEntryLocalService;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.security.sso.openid.connect.internal.session.manager.OfflineOpenIdConnectSessionManager;
import com.liferay.portal.security.sso.openid.connect.internal.util.OpenIdConnectTokenRequestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.oauth2.sdk.id.ClientID;
import com.nimbusds.oauth2.sdk.id.Issuer;
import com.nimbusds.oauth2.sdk.id.State;
import com.nimbusds.oauth2.sdk.pkce.CodeVerifier;
import com.nimbusds.openid.connect.sdk.AuthenticationResponseParser;
import com.nimbusds.openid.connect.sdk.AuthenticationSuccessResponse;
import com.nimbusds.openid.connect.sdk.Nonce;
import com.nimbusds.openid.connect.sdk.op.OIDCProviderMetadata;
import com.nimbusds.openid.connect.sdk.rp.OIDCClientInformation;
import com.nimbusds.openid.connect.sdk.token.OIDCTokens;

import java.net.URI;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.MockedStatic;
import org.mockito.Mockito;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;

/**
 * @author Tamas Biro
 */
public class OpenIdConnectAuthenticationHandlerImplTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		_authenticationResponseParserMockedStatic = Mockito.mockStatic(
			AuthenticationResponseParser.class);
		_oidcClientInformationMockedStatic = Mockito.mockStatic(
			OIDCClientInformation.class);
		_openIdConnectTokenRequestUtilMockedStatic = Mockito.mockStatic(
			OpenIdConnectTokenRequestUtil.class);
		_serviceContextFactoryMockedStatic = Mockito.mockStatic(
			ServiceContextFactory.class);

		_openIdConnectAuthenticationHandlerImpl =
			new OpenIdConnectAuthenticationHandlerImpl();
		_mockHttpServletRequest = new MockHttpServletRequest();
		_mockHttpServletResponse = new MockHttpServletResponse();
		_mockHttpSession = new MockHttpSession();
		_userIdUnsafeConsumer = new UnsafeConsumer<Long, Exception>() {

			@Override
			public void accept(Long aLong) {
			}

		};
	}

	@After
	public void tearDown() {
		_authenticationResponseParserMockedStatic.close();
		_oidcClientInformationMockedStatic.close();
		_openIdConnectTokenRequestUtilMockedStatic.close();
		_serviceContextFactoryMockedStatic.close();
	}

	@Test
	public void testWhenEmailAndEmailsAreNotInJWTClaimSetThenNoNPE()
		throws Exception {

		String jsonRequest = "{\"sub\":\"subject\",\"name\": \"test_account\"}";

		_assertThatNullPointerExceptionIsNotThrownWhenProcessingJWTClaims(
			jsonRequest);
	}

	@Test
	public void testWhenEmailIsInJWTClaimSetThenNoNPE() throws Exception {
		String jsonRequest =
			"{\"sub\":\"subject\",\"name\": \"test_account\",\"email\": " +
				"\"exists@test.com\"}";

		_assertThatNullPointerExceptionIsNotThrownWhenProcessingJWTClaims(
			jsonRequest);
	}

	private void
			_assertThatNullPointerExceptionIsNotThrownWhenProcessingJWTClaims(
				String jsonRequest)
		throws Exception {

		State state = new State("state");

		JWT mockJWT = Mockito.mock(JWT.class);

		_mockJWTClaimSet(jsonRequest, mockJWT);

		_mockHttpServletRequest(state);

		_mockOpenIdConnectAuthenticationSession(state);

		_mockOAuthClientEntry();

		_mockStaticAuthenticationResponseParser(state);

		_mockStaticOIDCClientInformation();

		_mockMetadataResolver();

		_mockPortal();

		_mockStaticOpenIdConnectTokenRequestUti(mockJWT);

		_mockOIDCUserInfoProcessor();

		_mockStaticServiceContextFactory();

		_mockOfflineOpenIdConnectSessionManager();

		try {
			_openIdConnectAuthenticationHandlerImpl.
				processAuthenticationResponse(
					_mockHttpServletRequest, _mockHttpServletResponse,
					_userIdUnsafeConsumer);
		}
		catch (NullPointerException nullPointerException) {
			Assert.fail(
				"NullPointerException was thrown that makes the test fail");
		}
	}

	private void _mockHttpServletRequest(State state) {
		_mockHttpSession.setAttribute(
			OpenIdConnectAuthenticationHandlerImpl.class.getName() +
				"#OPEN_ID_CONNECT_AUTHENTICATION_SESSION",
			new OpenIdConnectAuthenticationSession(
				new CodeVerifier(), new Nonce(), 0L, state));

		_mockHttpServletRequest.setSession(_mockHttpSession);
		_mockHttpServletRequest.setRequestURI("https://example.com");
	}

	private void _mockJWTClaimSet(String jsonRequest, JWT mockJWT)
		throws Exception {

		JWTClaimsSet jwtClaimsSet = JWTClaimsSet.parse(jsonRequest);

		Mockito.when(
			mockJWT.getJWTClaimsSet()
		).thenReturn(
			jwtClaimsSet
		);
	}

	private void _mockMetadataResolver() throws Exception {
		AuthorizationServerMetadataResolver
			mockAuthorizationServerMetadataResolver = Mockito.mock(
				AuthorizationServerMetadataResolver.class);

		OIDCProviderMetadata mockOIDCProviderMetadata = Mockito.mock(
			OIDCProviderMetadata.class);

		Mockito.when(
			mockAuthorizationServerMetadataResolver.resolveOIDCProviderMetadata(
				Mockito.anyString())
		).thenReturn(
			mockOIDCProviderMetadata
		);

		ReflectionTestUtil.setFieldValue(
			_openIdConnectAuthenticationHandlerImpl,
			"_authorizationServerMetadataResolver",
			mockAuthorizationServerMetadataResolver);

		Mockito.when(
			mockOIDCProviderMetadata.getIssuer()
		).thenReturn(
			Mockito.mock(Issuer.class)
		);
	}

	private void _mockOAuthClientEntry() throws Exception {
		OAuthClientEntryLocalService mockOAuthClientEntryLocalService =
			Mockito.mock(OAuthClientEntryLocalService.class);
		OAuthClientEntry mockOAuthClientEntry = Mockito.mock(
			OAuthClientEntry.class);

		Mockito.when(
			mockOAuthClientEntry.getInfoJSON()
		).thenReturn(
			"{\"infoJson\":\"info\"}"
		);
		Mockito.when(
			mockOAuthClientEntry.getAuthServerWellKnownURI()
		).thenReturn(
			"wellKnowUri"
		);
		Mockito.when(
			mockOAuthClientEntry.getTokenRequestParametersJSON()
		).thenReturn(
			"paramJSON"
		);
		Mockito.when(
			mockOAuthClientEntryLocalService.getOAuthClientEntry(
				Mockito.anyLong())
		).thenReturn(
			mockOAuthClientEntry
		);
		Mockito.when(
			mockOAuthClientEntry.getOIDCUserInfoMapperJSON()
		).thenReturn(
			"oidcUserInfoMapper"
		);

		ReflectionTestUtil.setFieldValue(
			_openIdConnectAuthenticationHandlerImpl,
			"_oAuthClientEntryLocalService", mockOAuthClientEntryLocalService);
	}

	private void _mockOfflineOpenIdConnectSessionManager() {
		OfflineOpenIdConnectSessionManager
			mockOfflineOpenIdConnectSessionManager = Mockito.mock(
				OfflineOpenIdConnectSessionManager.class);

		Mockito.when(
			mockOfflineOpenIdConnectSessionManager.startOpenIdConnectSession(
				Mockito.anyString(), Mockito.anyString(),
				Mockito.any(OIDCTokens.class), Mockito.anyLong())
		).thenReturn(
			99L
		);
		ReflectionTestUtil.setFieldValue(
			_openIdConnectAuthenticationHandlerImpl,
			"_offlineOpenIdConnectSessionManager",
			mockOfflineOpenIdConnectSessionManager);
	}

	private void _mockOIDCUserInfoProcessor() {
		OIDCUserInfoProcessor mockOIDCUserInfoProcessor = Mockito.mock(
			OIDCUserInfoProcessor.class);

		ReflectionTestUtil.setFieldValue(
			_openIdConnectAuthenticationHandlerImpl, "_oidcUserInfoProcessor",
			mockOIDCUserInfoProcessor);
	}

	private void _mockOpenIdConnectAuthenticationSession(State state) {
		OpenIdConnectAuthenticationSession
			mockOpenIdConnectAuthenticationSession = Mockito.mock(
				OpenIdConnectAuthenticationSession.class);

		Mockito.when(
			mockOpenIdConnectAuthenticationSession.getState()
		).thenReturn(
			state
		);
	}

	private void _mockPortal() {
		Portal mockPortal = Mockito.mock(Portal.class);

		Mockito.when(
			mockPortal.getPortalURL(_mockHttpServletRequest)
		).thenReturn(
			"https://example.com"
		);
		Mockito.when(
			mockPortal.getPathContext(Mockito.anyString())
		).thenReturn(
			"/pathContext"
		);
		ReflectionTestUtil.setFieldValue(
			_openIdConnectAuthenticationHandlerImpl, "_portal", mockPortal);
	}

	private void _mockStaticAuthenticationResponseParser(State state) {
		AuthenticationSuccessResponse mockSuccessResponse = Mockito.mock(
			AuthenticationSuccessResponse.class);

		Mockito.when(
			mockSuccessResponse.getState()
		).thenReturn(
			state
		);

		_authenticationResponseParserMockedStatic.when(
			() -> AuthenticationResponseParser.parse(Mockito.any(URI.class))
		).thenReturn(
			mockSuccessResponse
		);
	}

	private void _mockStaticOIDCClientInformation() {
		OIDCClientInformation mockOIDCClientInformation = Mockito.mock(
			OIDCClientInformation.class);

		Mockito.when(
			mockOIDCClientInformation.getID()
		).thenReturn(
			Mockito.mock(ClientID.class)
		);

		_oidcClientInformationMockedStatic.when(
			() -> OIDCClientInformation.parse(Mockito.any())
		).thenReturn(
			mockOIDCClientInformation
		);
	}

	private void _mockStaticOpenIdConnectTokenRequestUti(JWT jwt) {
		OIDCTokens mockOIDCTokens = Mockito.mock(OIDCTokens.class);

		Mockito.when(
			mockOIDCTokens.getIDToken()
		).thenReturn(
			jwt
		);

		_openIdConnectTokenRequestUtilMockedStatic.when(
			() -> OpenIdConnectTokenRequestUtil.request(
				Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(),
				Mockito.any(), Mockito.any(), Mockito.any())
		).thenReturn(
			mockOIDCTokens
		);
	}

	private void _mockStaticServiceContextFactory() {
		_serviceContextFactoryMockedStatic.when(
			() -> ServiceContextFactory.getInstance(_mockHttpServletRequest)
		).thenReturn(
			Mockito.mock(ServiceContext.class)
		);
	}

	private MockedStatic _authenticationResponseParserMockedStatic;
	private MockHttpServletRequest _mockHttpServletRequest;
	private MockHttpServletResponse _mockHttpServletResponse;
	private MockHttpSession _mockHttpSession;
	private MockedStatic _oidcClientInformationMockedStatic;
	private OpenIdConnectAuthenticationHandlerImpl
		_openIdConnectAuthenticationHandlerImpl;
	private MockedStatic _openIdConnectTokenRequestUtilMockedStatic;
	private MockedStatic _serviceContextFactoryMockedStatic;
	private UnsafeConsumer<Long, Exception> _userIdUnsafeConsumer;

}