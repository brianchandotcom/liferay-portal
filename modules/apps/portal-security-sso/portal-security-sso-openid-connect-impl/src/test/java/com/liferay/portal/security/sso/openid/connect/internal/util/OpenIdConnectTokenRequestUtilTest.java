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
		HTTPResponse mockHTTPResponse = Mockito.mock(HTTPResponse.class);
		_clientAndServer = ClientAndServer.startClientAndServer(63636);

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
				String.valueOf(mockHTTPResponse)
			).withHeader(
				new Header("Content-Type", "application/json")
			).withStatusCode(
				200
			)
		);

		_mockOIDCClientInformation = Mockito.mock(OIDCClientInformation.class);
		_mockOIDCProviderMetadata = Mockito.mock(OIDCProviderMetadata.class);
		_mockRefreshToken = Mockito.mock(RefreshToken.class);
		_openIdConnectRequestParametersUtilMockedStatic = Mockito.mockStatic(
			OpenIdConnectRequestParametersUtil.class);
		HTTPRequest mockHTTPRequest = Mockito.mock(HTTPRequest.class);

		_oidcTokenResponseParserMockedStatic = Mockito.mockStatic(
			OIDCTokenResponseParser.class);
		OIDCTokenResponse mockOIDCTokenResponse = Mockito.mock(
			OIDCTokenResponse.class);
		_mockOIDCTokens = Mockito.mock(OIDCTokens.class);
		TokenRequest mockTokenRequest = Mockito.mock(TokenRequest.class);

		_tokenRequestParameters = "{}";

		Mockito.when(
			_mockOIDCProviderMetadata.getTokenEndpointURI()
		).thenReturn(
			URI.create("http://localhost:63636")
		);

		Mockito.when(
			_mockOIDCProviderMetadata.getJWKSetURI()
		).thenReturn(
			URI.create("http://localhost:63636")
		);

		Mockito.when(
			_mockOIDCProviderMetadata.getIssuer()
		).thenReturn(
			Mockito.mock(Issuer.class)
		);

		Mockito.when(
			_mockOIDCClientInformation.getID()
		).thenReturn(
			new ClientID("clientID")
		);

		Mockito.when(
			_mockOIDCClientInformation.getSecret()
		).thenReturn(
			new Secret("secret")
		);

		OIDCClientMetadata mockOIDClientMetadata = Mockito.mock(
			OIDCClientMetadata.class);

		Mockito.when(
			_mockOIDCClientInformation.getOIDCMetadata()
		).thenReturn(
			mockOIDClientMetadata
		);

		Mockito.when(
			mockOIDClientMetadata.getIDTokenJWSAlg()
		).thenReturn(
			new JWSAlgorithm("algorithm")
		);

		_openIdConnectRequestParametersUtilMockedStatic.when(
			() -> OpenIdConnectRequestParametersUtil.getResourceURIs(
				Mockito.any())
		).thenReturn(
			new URI[] {URI.create("http://localhost:63636")}
		);

		Mockito.when(
			mockTokenRequest.toHTTPRequest()
		).thenReturn(
			mockHTTPRequest
		);

		_oidcTokenResponseParserMockedStatic.when(
			() -> OIDCTokenResponseParser.parse(Mockito.any(HTTPResponse.class))
		).thenReturn(
			mockOIDCTokenResponse
		);

		Mockito.when(
			mockOIDCTokenResponse.getOIDCTokens()
		).thenReturn(
			_mockOIDCTokens
		);

		Mockito.when(
			mockHTTPRequest.send()
		).thenReturn(
			mockHTTPResponse
		);

		Mockito.when(
			_mockOIDCTokens.getIDToken()
		).thenReturn(
			null
		);

		_mockAuthenticationSuccessResponse = Mockito.mock(
			AuthenticationSuccessResponse.class);
		AuthorizationCode mockAuthorizationCode = Mockito.mock(
			AuthorizationCode.class);
		_mockCodeVerifier = Mockito.mock(CodeVerifier.class);
		_mockNonce = Mockito.mock(Nonce.class);

		Mockito.when(
			_mockAuthenticationSuccessResponse.getAuthorizationCode()
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
			_mockAuthenticationSuccessResponse, _mockCodeVerifier, _mockNonce,
			_mockOIDCClientInformation, _mockOIDCProviderMetadata,
			URI.create("http://localhost:63636"), _tokenRequestParameters);
	}

	@Test
	public void testRequestOIDCTokenRefreshWithNullIdToken() throws Exception {
		Assert.assertEquals(
			_mockOIDCTokens,
			OpenIdConnectTokenRequestUtil.request(
				_mockOIDCClientInformation, _mockOIDCProviderMetadata,
				_mockRefreshToken, _tokenRequestParameters));
	}

	private static ClientAndServer _clientAndServer;
	private static AuthenticationSuccessResponse
		_mockAuthenticationSuccessResponse;
	private static CodeVerifier _mockCodeVerifier;
	private static Nonce _mockNonce;
	private static OIDCClientInformation _mockOIDCClientInformation;
	private static OIDCProviderMetadata _mockOIDCProviderMetadata;
	private static OIDCTokens _mockOIDCTokens;
	private static RefreshToken _mockRefreshToken;
	private static MockedStatic<OIDCTokenResponseParser>
		_oidcTokenResponseParserMockedStatic;
	private static MockedStatic<OpenIdConnectRequestParametersUtil>
		_openIdConnectRequestParametersUtilMockedStatic;
	private static String _tokenRequestParameters;

}