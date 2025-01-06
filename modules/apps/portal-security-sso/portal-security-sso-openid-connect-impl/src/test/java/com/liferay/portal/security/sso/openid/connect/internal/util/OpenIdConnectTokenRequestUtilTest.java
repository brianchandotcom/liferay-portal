/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.sso.openid.connect.internal.util;

import com.liferay.portal.test.rule.LiferayUnitTestRule;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.oauth2.sdk.AuthorizationCode;
import com.nimbusds.oauth2.sdk.TokenRequest;
import com.nimbusds.oauth2.sdk.auth.Secret;
import com.nimbusds.oauth2.sdk.http.HTTPRequest;
import com.nimbusds.oauth2.sdk.http.HTTPResponse;
import com.nimbusds.oauth2.sdk.id.ClientID;
import com.nimbusds.oauth2.sdk.id.Issuer;
import com.nimbusds.oauth2.sdk.pkce.CodeVerifier;
import com.nimbusds.oauth2.sdk.token.RefreshToken;
import com.nimbusds.openid.connect.sdk.AuthenticationSuccessResponse;
import com.nimbusds.openid.connect.sdk.Nonce;
import com.nimbusds.openid.connect.sdk.OIDCTokenResponse;
import com.nimbusds.openid.connect.sdk.OIDCTokenResponseParser;
import com.nimbusds.openid.connect.sdk.op.OIDCProviderMetadata;
import com.nimbusds.openid.connect.sdk.rp.OIDCClientInformation;
import com.nimbusds.openid.connect.sdk.rp.OIDCClientMetadata;
import com.nimbusds.openid.connect.sdk.token.OIDCTokens;

import java.net.URI;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.MockedStatic;
import org.mockito.Mockito;

import org.mockserver.client.server.MockServerClient;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.matchers.Times;
import org.mockserver.model.Header;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;

/**
 * @author Christian Moura
 */
public class OpenIdConnectTokenRequestUtilTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@BeforeClass
	public static void setUpClass() throws Exception {
		_authenticationSuccessResponse = Mockito.mock(
			AuthenticationSuccessResponse.class);
		_clientAndServer = ClientAndServer.startClientAndServer(63636);
		_codeVerifier = Mockito.mock(CodeVerifier.class);
		_nonce = Mockito.mock(Nonce.class);
		_oidcClientInformation = Mockito.mock(OIDCClientInformation.class);
		_oidcProviderMetadata = Mockito.mock(OIDCProviderMetadata.class);
		_oidcTokenResponseParserMockedStatic = Mockito.mockStatic(
			OIDCTokenResponseParser.class);
		_oidcTokens = Mockito.mock(OIDCTokens.class);
		_openIdConnectRequestParametersUtilMockedStatic = Mockito.mockStatic(
			OpenIdConnectRequestParametersUtil.class);
		_refreshToken = Mockito.mock(RefreshToken.class);
		_tokenRequestParameters = "{}";

		HTTPResponse httpResponse = Mockito.mock(HTTPResponse.class);

		new MockServerClient(
			"localhost", 63636
		).when(
			HttpRequest.request(
			).withMethod(
				"POST"
			),
			Times.unlimited()
		).respond(
			HttpResponse.response(
			).withBody(
				String.valueOf(httpResponse)
			).withHeader(
				new Header("Content-Type", "application/json")
			).withStatusCode(
				200
			)
		);

		OIDCTokenResponse oidcTokenResponse = Mockito.mock(
			OIDCTokenResponse.class);
		TokenRequest mockTokenRequest = Mockito.mock(TokenRequest.class);

		Mockito.when(
			_oidcProviderMetadata.getTokenEndpointURI()
		).thenReturn(
			URI.create("http://localhost:63636")
		);

		Mockito.when(
			_oidcProviderMetadata.getJWKSetURI()
		).thenReturn(
			URI.create("http://localhost:63636")
		);

		Mockito.when(
			_oidcProviderMetadata.getIssuer()
		).thenReturn(
			Mockito.mock(Issuer.class)
		);

		Mockito.when(
			_oidcClientInformation.getID()
		).thenReturn(
			new ClientID("clientID")
		);

		Mockito.when(
			_oidcClientInformation.getSecret()
		).thenReturn(
			new Secret("secret")
		);

		OIDCClientMetadata oidcClientMetadata = Mockito.mock(
			OIDCClientMetadata.class);

		Mockito.when(
			_oidcClientInformation.getOIDCMetadata()
		).thenReturn(
			oidcClientMetadata
		);

		Mockito.when(
			oidcClientMetadata.getIDTokenJWSAlg()
		).thenReturn(
			new JWSAlgorithm("algorithm")
		);

		_openIdConnectRequestParametersUtilMockedStatic.when(
			() -> OpenIdConnectRequestParametersUtil.getResourceURIs(
				Mockito.any())
		).thenReturn(
			new URI[] {URI.create("http://localhost:63636")}
		);

		HTTPRequest httpRequest = Mockito.mock(HTTPRequest.class);

		Mockito.when(
			mockTokenRequest.toHTTPRequest()
		).thenReturn(
			httpRequest
		);

		_oidcTokenResponseParserMockedStatic.when(
			() -> OIDCTokenResponseParser.parse(Mockito.any(HTTPResponse.class))
		).thenReturn(
			oidcTokenResponse
		);

		Mockito.when(
			oidcTokenResponse.getOIDCTokens()
		).thenReturn(
			_oidcTokens
		);

		Mockito.when(
			httpRequest.send()
		).thenReturn(
			httpResponse
		);

		Mockito.when(
			_oidcTokens.getIDToken()
		).thenReturn(
			null
		);

		AuthorizationCode mockAuthorizationCode = Mockito.mock(
			AuthorizationCode.class);

		Mockito.when(
			_authenticationSuccessResponse.getAuthorizationCode()
		).thenReturn(
			mockAuthorizationCode
		);
	}

	@AfterClass
	public static void tearDownClass() {
		_openIdConnectRequestParametersUtilMockedStatic.close();
		_oidcTokenResponseParserMockedStatic.close();
		_clientAndServer.stop();
	}

	@Test(expected = NullPointerException.class)
	public void testRequestOIDCAuthorizationGrantWithNullIdToken()
		throws Exception {

		OpenIdConnectTokenRequestUtil.request(
			_authenticationSuccessResponse, _codeVerifier, _nonce,
			_oidcClientInformation, _oidcProviderMetadata,
			URI.create("http://localhost:63636"), _tokenRequestParameters);
	}

	@Test
	public void testRequestOIDCTokenRefreshWithNullIdToken() throws Exception {
		Assert.assertEquals(
			_oidcTokens,
			OpenIdConnectTokenRequestUtil.request(
				_oidcClientInformation, _oidcProviderMetadata, _refreshToken,
				_tokenRequestParameters));
	}

	private static AuthenticationSuccessResponse _authenticationSuccessResponse;
	private static ClientAndServer _clientAndServer;
	private static CodeVerifier _codeVerifier;
	private static Nonce _nonce;
	private static OIDCClientInformation _oidcClientInformation;
	private static OIDCProviderMetadata _oidcProviderMetadata;
	private static MockedStatic<OIDCTokenResponseParser>
		_oidcTokenResponseParserMockedStatic;
	private static OIDCTokens _oidcTokens;
	private static MockedStatic<OpenIdConnectRequestParametersUtil>
		_openIdConnectRequestParametersUtilMockedStatic;
	private static RefreshToken _refreshToken;
	private static String _tokenRequestParameters;

}