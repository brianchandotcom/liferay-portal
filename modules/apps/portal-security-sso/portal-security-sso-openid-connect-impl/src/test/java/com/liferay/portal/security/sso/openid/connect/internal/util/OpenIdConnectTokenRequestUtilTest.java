/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.sso.openid.connect.internal.util;

import com.liferay.portal.security.sso.openid.connect.OpenIdConnectServiceException;
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

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.MockedStatic;
import org.mockito.Mockito;

import org.mockserver.client.server.MockServerClient;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.matchers.Times;
import org.mockserver.model.Delay;
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

	@Before
	public void setUp() throws Exception {
		Mockito.when(
			_authenticationSuccessResponse.getAuthorizationCode()
		).thenReturn(
			Mockito.mock(AuthorizationCode.class)
		);

		Mockito.when(
			_oidcClientInformation.getID()
		).thenReturn(
			new ClientID("clientID")
		);

		OIDCClientMetadata oidcClientMetadata = Mockito.mock(
			OIDCClientMetadata.class);

		Mockito.when(
			oidcClientMetadata.getIDTokenJWSAlg()
		).thenReturn(
			new JWSAlgorithm("algorithm")
		);

		Mockito.when(
			_oidcClientInformation.getOIDCMetadata()
		).thenReturn(
			oidcClientMetadata
		);

		Mockito.when(
			_oidcClientInformation.getOIDCMetadata()
		).thenReturn(
			oidcClientMetadata
		);

		Mockito.when(
			_oidcClientInformation.getSecret()
		).thenReturn(
			new Secret("secret")
		);

		Mockito.when(
			_oidcProviderMetadata.getIssuer()
		).thenReturn(
			Mockito.mock(Issuer.class)
		);

		Mockito.when(
			_oidcProviderMetadata.getJWKSetURI()
		).thenReturn(
			URI.create("http://localhost:63636")
		);

		Mockito.when(
			_oidcProviderMetadata.getTokenEndpointURI()
		).thenReturn(
			URI.create("http://localhost:63636")
		);

		OIDCTokenResponse oidcTokenResponse = Mockito.mock(
			OIDCTokenResponse.class);

		_oidcTokenResponseParserMockedStatic.when(
			() -> OIDCTokenResponseParser.parse(Mockito.any(HTTPResponse.class))
		).thenReturn(
			oidcTokenResponse
		);

		Mockito.when(
			_oidcTokens.getIDToken()
		).thenReturn(
			null
		);

		Mockito.when(
			oidcTokenResponse.getOIDCTokens()
		).thenReturn(
			_oidcTokens
		);

		_openIdConnectRequestParametersUtilMockedStatic.when(
			() -> OpenIdConnectRequestParametersUtil.getResourceURIs(
				Mockito.any())
		).thenReturn(
			new URI[] {URI.create("http://localhost:63636")}
		);

		HTTPRequest httpRequest = _setUpHttpRequest();

		Mockito.when(
			Mockito.mock(
				TokenRequest.class
			).toHTTPRequest()
		).thenReturn(
			httpRequest
		);
	}

	@After
	public void tearDown() {
		_clientAndServer.stop();
		_oidcTokenResponseParserMockedStatic.close();
		_openIdConnectRequestParametersUtilMockedStatic.close();
	}

	@Test
	public void testRequest() throws Exception {
		try {
			OpenIdConnectTokenRequestUtil.request(
				_authenticationSuccessResponse, _codeVerifier, _nonce,
				_oidcClientInformation, _oidcProviderMetadata,
				URI.create("http://localhost:63636"), 1000,
				_TOKEN_REQUEST_PARAMETERS);

			Assert.fail();
		}
		catch (NullPointerException nullPointerException) {
			Assert.assertNotNull(nullPointerException);
		}

		Assert.assertEquals(
			_oidcTokens,
			OpenIdConnectTokenRequestUtil.request(
				_oidcClientInformation, _oidcProviderMetadata, _refreshToken,
				1000, _TOKEN_REQUEST_PARAMETERS));

		try {
			OpenIdConnectTokenRequestUtil.request(
				_oidcClientInformation, _oidcProviderMetadata, _refreshToken,
				1000, _TOKEN_REQUEST_PARAMETERS);

			Assert.fail();
		}
		catch (OpenIdConnectServiceException.TokenException tokenException) {
			Assert.assertNotNull(tokenException);
		}
	}

	private HTTPRequest _setUpHttpRequest() throws Exception {
		HTTPRequest httpRequest = Mockito.mock(HTTPRequest.class);

		MockServerClient mockServerClient = new MockServerClient(
			"localhost", 63636);

		HTTPResponse httpResponse = Mockito.mock(HTTPResponse.class);

		mockServerClient.when(
			HttpRequest.request(
			).withMethod(
				"POST"
			),
			Times.exactly(2)
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

		mockServerClient.when(
			HttpRequest.request(
			).withMethod(
				"POST"
			),
			Times.once()
		).respond(
			HttpResponse.response(
			).withBody(
				String.valueOf(httpResponse)
			).withHeader(
				new Header("Content-Type", "application/json")
			).withStatusCode(
				200
			).withDelay(
				Delay.seconds(10)
			)
		);

		Mockito.when(
			httpRequest.send()
		).thenReturn(
			httpResponse
		);

		return httpRequest;
	}

	private static final String _TOKEN_REQUEST_PARAMETERS = "{}";

	private final AuthenticationSuccessResponse _authenticationSuccessResponse =
		Mockito.mock(AuthenticationSuccessResponse.class);
	private final ClientAndServer _clientAndServer =
		ClientAndServer.startClientAndServer(63636);
	private final CodeVerifier _codeVerifier = Mockito.mock(CodeVerifier.class);
	private final Nonce _nonce = Mockito.mock(Nonce.class);
	private final OIDCClientInformation _oidcClientInformation = Mockito.mock(
		OIDCClientInformation.class);
	private final OIDCProviderMetadata _oidcProviderMetadata = Mockito.mock(
		OIDCProviderMetadata.class);
	private final MockedStatic<OIDCTokenResponseParser>
		_oidcTokenResponseParserMockedStatic = Mockito.mockStatic(
			OIDCTokenResponseParser.class);
	private final OIDCTokens _oidcTokens = Mockito.mock(OIDCTokens.class);
	private final MockedStatic<OpenIdConnectRequestParametersUtil>
		_openIdConnectRequestParametersUtilMockedStatic = Mockito.mockStatic(
			OpenIdConnectRequestParametersUtil.class);
	private final RefreshToken _refreshToken = Mockito.mock(RefreshToken.class);

}