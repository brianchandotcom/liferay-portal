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

package com.liferay.oauth2.provider.client.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.oauth2.provider.constants.GrantType;
import com.liferay.oauth2.provider.service.OAuth2AuthorizationLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Collections;
import java.util.Map;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.BundleActivator;

/**
 * @author Marta Medio
 */
@RunWith(Arquillian.class)
public class RememberDeviceApplicationClientTest extends BaseClientTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testCookieResponseApplicationCode() {
		Response response = getCodeResponse(
			"test@liferay.com", "test", null,
			getCodeFunction(
				webTarget -> webTarget.queryParam(
					"client_id", "oauthTestApplicationCode"
				).queryParam(
					"redirect_uri", "http://redirecturi:8080"
				).queryParam(
					"response_type", "code"
				),
				_getExtraParameters(), false));

		Map<String, NewCookie> cookies = response.getCookies();

		Assert.assertNull(cookies.get(_REMEMBER_DEVICE_COOKIE));
	}

	@Test
	public void testCookieResponseApplicationCodePKCE() {
		Response response = getCodeResponse(
			"test@liferay.com", "test", null,
			getCodeFunction(
				webTarget -> webTarget.queryParam(
					"client_id", "oauthTestApplicationCodePKCE"
				).queryParam(
					"code_challenge",
					generateCodeChallenge(RandomTestUtil.randomString())
				).queryParam(
					"response_type", "code"
				).queryParam(
					"redirect_uri", "http://redirecturi:8080"
				),
				_getExtraParameters(), false));

		Map<String, NewCookie> cookies = response.getCookies();

		Assert.assertNull(cookies.get(_REMEMBER_DEVICE_COOKIE));
	}

	@Test
	public void testRememberApplicationCode() {
		String applicationClientId = "oauthTestRememberApplicationCode";

		Response response = getCodeResponse(
			"test@liferay.com", "test", null,
			getCodeFunction(
				webTarget -> webTarget.queryParam(
					"client_id", applicationClientId
				).queryParam(
					"redirect_uri", "http://redirecturi:8080"
				).queryParam(
					"response_type", "code"
				),
				_getExtraParameters(), false));

		Map<String, NewCookie> cookies = response.getCookies();

		NewCookie newCookie1 = cookies.get(_REMEMBER_DEVICE_COOKIE);

		Assert.assertNotNull(newCookie1);

		String authorizationCodeString = parseAuthorizationCodeString(response);

		Assert.assertNotNull(authorizationCodeString);

		getToken(
			applicationClientId, null,
			(clientId, tokenInvocationBuilder) -> {
				MultivaluedMap<String, String> formData =
					new MultivaluedHashMap<>();

				formData.add("client_id", applicationClientId);
				formData.add("client_secret", "oauthTestApplicationSecret");
				formData.add("grant_type", "authorization_code");
				formData.add("code", authorizationCodeString);

				return tokenInvocationBuilder.post(Entity.form(formData));
			},
			this::parseTokenString);

		response = getCodeResponse(
			"test@liferay.com", "test", null,
			getCodeFunction(
				webTarget -> webTarget.queryParam(
					"client_id", applicationClientId
				).queryParam(
					"redirect_uri", "http://redirecturi:8080"
				).queryParam(
					"response_type", "code"
				),
				null, true),
			invocationBuilder -> invocationBuilder.cookie(
				_REMEMBER_DEVICE_COOKIE, newCookie1.getValue()));

		Assert.assertNotNull(parseAuthorizationCodeString(response));

		cookies = response.getCookies();

		NewCookie newCookie2 = cookies.get(_REMEMBER_DEVICE_COOKIE);

		Assert.assertNotEquals(newCookie1.getValue(), newCookie2.getValue());
	}

	@Test
	public void testRememberApplicationCodePKCE() {
		String applicationClientId = "oauthTestRememberApplicationCodePKCE";

		String codeVerifierString = RandomTestUtil.randomString();

		Response response = getCodeResponse(
			"test@liferay.com", "test", null,
			getCodeFunction(
				webTarget -> webTarget.queryParam(
					"client_id", applicationClientId
				).queryParam(
					"code_challenge", generateCodeChallenge(codeVerifierString)
				).queryParam(
					"response_type", "code"
				).queryParam(
					"redirect_uri", "http://redirecturi:8080"
				),
				_getExtraParameters(), false));

		Map<String, NewCookie> cookies = response.getCookies();

		NewCookie newCookie1 = cookies.get(_REMEMBER_DEVICE_COOKIE);

		Assert.assertNotNull(newCookie1);

		String authorizationCodeString = parseAuthorizationCodeString(response);

		Assert.assertNotNull(authorizationCodeString);

		getToken(
			applicationClientId, null,
			(clientId, tokenInvocationBuilder) -> {
				MultivaluedMap<String, String> formData =
					new MultivaluedHashMap<>();

				formData.add("client_id", clientId);
				formData.add("code", authorizationCodeString);
				formData.add("code_verifier", codeVerifierString);
				formData.add("grant_type", "authorization_code");

				return tokenInvocationBuilder.post(Entity.form(formData));
			},
			this::parseTokenString);

		response = getCodeResponse(
			"test@liferay.com", "test", null,
			getCodeFunction(
				webTarget -> webTarget.queryParam(
					"client_id", applicationClientId
				).queryParam(
					"code_challenge", codeVerifierString
				).queryParam(
					"response_type", "code"
				).queryParam(
					"redirect_uri", "http://redirecturi:8080"
				),
				true),
			invocationBuilder -> invocationBuilder.cookie(
				_REMEMBER_DEVICE_COOKIE, newCookie1.getValue()));

		Assert.assertNotNull(parseAuthorizationCodeString(response));

		cookies = response.getCookies();

		NewCookie newCookie2 = cookies.get(_REMEMBER_DEVICE_COOKIE);

		Assert.assertNotEquals(newCookie1.getValue(), newCookie2.getValue());
	}

	@Test
	public void testRequestTokenInvalidatePreviousTokenRememberApplicationCode() {
		String applicationClientId = "oauthTestRememberApplicationCode";

		Response response1 = getCodeResponse(
			"test@liferay.com", "test", null,
			getCodeFunction(
				webTarget -> webTarget.queryParam(
					"client_id", applicationClientId
				).queryParam(
					"redirect_uri", "http://redirecturi:8080"
				).queryParam(
					"response_type", "code"
				),
				_getExtraParameters(), false));

		Map<String, NewCookie> newCookies = response1.getCookies();

		NewCookie newCookie = newCookies.get(_REMEMBER_DEVICE_COOKIE);

		String token = getToken(
			applicationClientId, null,
			(client, tokenInvocationBuilder) -> {
				MultivaluedMap<String, String> formData =
					new MultivaluedHashMap<>();

				formData.add("client_id", applicationClientId);
				formData.add("client_secret", "oauthTestApplicationSecret");
				formData.add("grant_type", "authorization_code");
				formData.add("code", parseAuthorizationCodeString(response1));

				return tokenInvocationBuilder.post(Entity.form(formData));
			},
			this::parseTokenString);

		Assert.assertNotNull(token);

		Response response2 = getCodeResponse(
			"test@liferay.com", "test", null,
			getCodeFunction(
				webTarget -> webTarget.queryParam(
					"client_id", applicationClientId
				).queryParam(
					"redirect_uri", "http://redirecturi:8080"
				).queryParam(
					"response_type", "code"
				),
				true),
			invocationBuilder -> invocationBuilder.cookie(
				_REMEMBER_DEVICE_COOKIE, newCookie.getValue()));

		getToken(
			applicationClientId, null,
			(client, tokenInvocationBuilder) -> {
				MultivaluedMap<String, String> formData =
					new MultivaluedHashMap<>();

				formData.add("client_id", applicationClientId);
				formData.add("client_secret", "oauthTestApplicationSecret");
				formData.add("grant_type", "authorization_code");
				formData.add("code", parseAuthorizationCodeString(response2));

				return tokenInvocationBuilder.post(Entity.form(formData));
			},
			this::parseTokenString);

		Assert.assertNull(
			_oAuth2AuthorizationLocalService.
				fetchOAuth2AuthorizationByAccessTokenContent(token));
	}

	@Test
	public void testRequestTokenInvalidatePreviousTokenRememberApplicationCodePKCE() {
		String applicationClientId = "oauthTestRememberApplicationCodePKCE";

		String codeVerifierString = RandomTestUtil.randomString();

		Response response1 = getCodeResponse(
			"test@liferay.com", "test", null,
			getCodeFunction(
				webTarget -> webTarget.queryParam(
					"client_id", applicationClientId
				).queryParam(
					"code_challenge", generateCodeChallenge(codeVerifierString)
				).queryParam(
					"response_type", "code"
				).queryParam(
					"redirect_uri", "http://redirecturi:8080"
				),
				_getExtraParameters(), false));

		Map<String, NewCookie> cookies = response1.getCookies();

		NewCookie newCookie = cookies.get(_REMEMBER_DEVICE_COOKIE);

		String token = getToken(
			applicationClientId, null,
			(clientId, tokenInvocationBuilder) -> {
				MultivaluedMap<String, String> formData =
					new MultivaluedHashMap<>();

				formData.add("client_id", clientId);
				formData.add("code", parseAuthorizationCodeString(response1));
				formData.add("code_verifier", codeVerifierString);
				formData.add("grant_type", "authorization_code");

				return tokenInvocationBuilder.post(Entity.form(formData));
			},
			this::parseTokenString);

		Assert.assertNotNull(token);

		Response response2 = getCodeResponse(
			"test@liferay.com", "test", null,
			getCodeFunction(
				webTarget -> webTarget.queryParam(
					"client_id", applicationClientId
				).queryParam(
					"code_challenge", generateCodeChallenge(codeVerifierString)
				).queryParam(
					"response_type", "code"
				).queryParam(
					"redirect_uri", "http://redirecturi:8080"
				),
				true),
			invocationBuilder -> invocationBuilder.cookie(
				_REMEMBER_DEVICE_COOKIE, newCookie.getValue()));

		getToken(
			applicationClientId, null,
			(clientId, tokenInvocationBuilder) -> {
				MultivaluedMap<String, String> formData =
					new MultivaluedHashMap<>();

				formData.add("client_id", applicationClientId);
				formData.add("client_secret", "oauthTestApplicationSecret");
				formData.add("grant_type", "authorization_code");
				formData.add("code", parseAuthorizationCodeString(response2));

				return tokenInvocationBuilder.post(Entity.form(formData));
			},
			this::parseTokenString);

		Assert.assertNull(
			_oAuth2AuthorizationLocalService.
				fetchOAuth2AuthorizationByAccessTokenContent(token));
	}

	@Test
	public void testRevokeTokenInvalidateCookieRememberApplicationCode()
		throws PortalException {

		String applicationClientId = "oauthTestRememberApplicationCode";

		Response response = getCodeResponse(
			"test@liferay.com", "test", null,
			getCodeFunction(
				webTarget -> webTarget.queryParam(
					"client_id", applicationClientId
				).queryParam(
					"redirect_uri", "http://redirecturi:8080"
				).queryParam(
					"response_type", "code"
				),
				_getExtraParameters(), false));

		Map<String, NewCookie> cookies = response.getCookies();

		NewCookie newCookie = cookies.get(_REMEMBER_DEVICE_COOKIE);

		_revokeOAuth2AuthorizationByAccessToken(
			getToken(
				applicationClientId, null,
				(clientId, tokenInvocationBuilder) -> {
					MultivaluedMap<String, String> formData =
						new MultivaluedHashMap<>();

					formData.add("client_id", applicationClientId);
					formData.add("client_secret", "oauthTestApplicationSecret");
					formData.add("grant_type", "authorization_code");
					formData.add(
						"code", parseAuthorizationCodeString(response));

					return tokenInvocationBuilder.post(Entity.form(formData));
				},
				this::parseTokenString));

		Assert.assertNull(
			parseAuthorizationCodeString(
				getCodeResponse(
					"test@liferay.com", "test", null,
					getCodeFunction(
						webTarget -> webTarget.queryParam(
							"client_id", applicationClientId
						).queryParam(
							"redirect_uri", "http://redirecturi:8080"
						).queryParam(
							"response_type", "code"
						),
						true),
					invocationBuilder -> invocationBuilder.cookie(
						_REMEMBER_DEVICE_COOKIE, newCookie.getValue()))));
	}

	@Test
	public void testRevokeTokenInvalidateCookieRememberApplicationCodePKCE()
		throws PortalException {

		String applicationClientId = "oauthTestRememberApplicationCodePKCE";

		String codeVerifierString = RandomTestUtil.randomString();

		Response response = getCodeResponse(
			"test@liferay.com", "test", null,
			getCodeFunction(
				webTarget -> webTarget.queryParam(
					"client_id", applicationClientId
				).queryParam(
					"code_challenge", generateCodeChallenge(codeVerifierString)
				).queryParam(
					"response_type", "code"
				).queryParam(
					"redirect_uri", "http://redirecturi:8080"
				),
				_getExtraParameters(), false));

		Map<String, NewCookie> cookies = response.getCookies();

		NewCookie newCookie = cookies.get(_REMEMBER_DEVICE_COOKIE);

		_revokeOAuth2AuthorizationByAccessToken(
			getToken(
				applicationClientId, null,
				(clientId, tokenInvocationBuilder) -> {
					MultivaluedMap<String, String> formData =
						new MultivaluedHashMap<>();

					formData.add("client_id", clientId);
					formData.add(
						"code", parseAuthorizationCodeString(response));
					formData.add("code_verifier", codeVerifierString);
					formData.add("grant_type", "authorization_code");

					return tokenInvocationBuilder.post(Entity.form(formData));
				},
				this::parseTokenString));

		Assert.assertNull(
			parseAuthorizationCodeString(
				getCodeResponse(
					"test@liferay.com", "test", null,
					getCodeFunction(
						webTarget -> webTarget.queryParam(
							"client_id", applicationClientId
						).queryParam(
							"code_challenge",
							generateCodeChallenge(codeVerifierString)
						).queryParam(
							"response_type", "code"
						).queryParam(
							"redirect_uri", "http://redirecturi:8080"
						),
						true),
					invocationBuilder -> invocationBuilder.cookie(
						_REMEMBER_DEVICE_COOKIE, newCookie.getValue()))));
	}

	@Test
	public void testSingleUseCookieRememberApplicationCode() {
		String applicationClientId = "oauthTestRememberApplicationCode";

		Response response1 = getCodeResponse(
			"test@liferay.com", "test", null,
			getCodeFunction(
				webTarget1 -> webTarget1.queryParam(
					"client_id", applicationClientId
				).queryParam(
					"redirect_uri", "http://redirecturi:8080"
				).queryParam(
					"response_type", "code"
				),
				_getExtraParameters(), false));

		Map<String, NewCookie> cookies = response1.getCookies();

		NewCookie newCookie = cookies.get(_REMEMBER_DEVICE_COOKIE);

		getToken(
			applicationClientId, null,
			(client, tokenInvocationBuilder) -> {
				MultivaluedMap<String, String> formData1 =
					new MultivaluedHashMap<>();

				formData1.add("client_id", applicationClientId);
				formData1.add("client_secret", "oauthTestApplicationSecret");
				formData1.add("grant_type", "authorization_code");
				formData1.add("code", parseAuthorizationCodeString(response1));

				return tokenInvocationBuilder.post(Entity.form(formData1));
			},
			this::parseTokenString);

		Response response2 = getCodeResponse(
			"test@liferay.com", "test", null,
			getCodeFunction(
				webTarget -> webTarget.queryParam(
					"client_id", applicationClientId
				).queryParam(
					"redirect_uri", "http://redirecturi:8080"
				).queryParam(
					"response_type", "code"
				),
				true),
			invocationBuilder -> invocationBuilder.cookie(
				_REMEMBER_DEVICE_COOKIE, newCookie.getValue()));

		Assert.assertNotNull(
			getToken(
				applicationClientId, null,
				(client, tokenInvocationBuilder) -> {
					MultivaluedMap<String, String> formData =
						new MultivaluedHashMap<>();

					formData.add("client_id", applicationClientId);
					formData.add("client_secret", "oauthTestApplicationSecret");
					formData.add("grant_type", "authorization_code");
					formData.add(
						"code", parseAuthorizationCodeString(response2));

					return tokenInvocationBuilder.post(Entity.form(formData));
				},
				this::parseTokenString));

		Assert.assertNull(
			parseAuthorizationCodeString(
				getCodeResponse(
					"test@liferay.com", "test", null,
					getCodeFunction(
						webTarget -> webTarget.queryParam(
							"client_id", applicationClientId
						).queryParam(
							"redirect_uri", "http://redirecturi:8080"
						).queryParam(
							"response_type", "code"
						),
						null, true),
					invocationBuilder -> invocationBuilder.cookie(
						_REMEMBER_DEVICE_COOKIE, newCookie.getValue()))));
	}

	@Test
	public void testSingleUseCookieRememberApplicationCodePKCE() {
		String applicationClientId = "oauthTestRememberApplicationCodePKCE";

		final String codeVerifierString = RandomTestUtil.randomString();

		Response response1 = getCodeResponse(
			"test@liferay.com", "test", null,
			getCodeFunction(
				webTarget1 -> webTarget1.queryParam(
					"client_id", applicationClientId
				).queryParam(
					"code_challenge", generateCodeChallenge(codeVerifierString)
				).queryParam(
					"response_type", "code"
				).queryParam(
					"redirect_uri", "http://redirecturi:8080"
				),
				_getExtraParameters(), false));

		Map<String, NewCookie> cookies = response1.getCookies();

		NewCookie newCookie = cookies.get(_REMEMBER_DEVICE_COOKIE);

		getToken(
			applicationClientId, null,
			(clientId, tokenInvocationBuilder) -> {
				MultivaluedMap<String, String> formData =
					new MultivaluedHashMap<>();

				formData.add("client_id", clientId);
				formData.add("code", parseAuthorizationCodeString(response1));
				formData.add("code_verifier", codeVerifierString);
				formData.add("grant_type", "authorization_code");

				return tokenInvocationBuilder.post(Entity.form(formData));
			},
			this::parseTokenString);

		Response response2 = getCodeResponse(
			"test@liferay.com", "test", null,
			getCodeFunction(
				webTarget -> webTarget.queryParam(
					"client_id", applicationClientId
				).queryParam(
					"code_challenge", codeVerifierString
				).queryParam(
					"response_type", "code"
				).queryParam(
					"redirect_uri", "http://redirecturi:8080"
				),
				true),
			invocationBuilder -> invocationBuilder.cookie(
				_REMEMBER_DEVICE_COOKIE, newCookie.getValue()));

		String authorizationCodeString = parseAuthorizationCodeString(
			response2);

		Assert.assertNotNull(authorizationCodeString);

		Assert.assertNotNull(
			getToken(
				applicationClientId, null,
				(clientId, tokenInvocationBuilder) -> {
					MultivaluedMap<String, String> formData =
						new MultivaluedHashMap<>();

					formData.add("client_id", clientId);
					formData.add("code", authorizationCodeString);
					formData.add("code_verifier", codeVerifierString);
					formData.add("grant_type", "authorization_code");

					return tokenInvocationBuilder.post(Entity.form(formData));
				},
				this::parseTokenString));

		Assert.assertNull(
			parseAuthorizationCodeString(
				getCodeResponse(
					"test@liferay.com", "test", null,
					getCodeFunction(
						webTarget -> webTarget.queryParam(
							"client_id", applicationClientId
						).queryParam(
							"code_challenge", codeVerifierString
						).queryParam(
							"response_type", "code"
						).queryParam(
							"redirect_uri", "http://redirecturi:8080"
						),
						true),
					invocationBuilder -> invocationBuilder.cookie(
						_REMEMBER_DEVICE_COOKIE, newCookie.getValue()))));
	}

	public static class RememberApplicationClientTestPreparatorBundleActivator
		extends BaseTestPreparatorBundleActivator {

		@Override
		protected void prepareTest() throws Exception {
			long defaultCompanyId = PortalUtil.getDefaultCompanyId();

			User user = UserTestUtil.getAdminUser(defaultCompanyId);

			createOAuth2Application(
				defaultCompanyId, user, "oauthTestApplicationCode",
				Collections.singletonList(GrantType.AUTHORIZATION_CODE), false,
				Collections.singletonList("everything"), false);
			createOAuth2Application(
				defaultCompanyId, user, "oauthTestApplicationCodePKCE", null,
				Collections.singletonList(GrantType.AUTHORIZATION_CODE_PKCE),
				Collections.singletonList("http://redirecturi:8080"), false,
				Collections.singletonList("everything"), false);
			createOAuth2Application(
				defaultCompanyId, user, "oauthTestRememberApplicationCode",
				Collections.singletonList(GrantType.AUTHORIZATION_CODE), true,
				Collections.singletonList("everything"), false);
			createOAuth2Application(
				defaultCompanyId, user, "oauthTestRememberApplicationCodePKCE",
				null,
				Collections.singletonList(GrantType.AUTHORIZATION_CODE_PKCE),
				Collections.singletonList("http://redirecturi:8080"), true,
				Collections.singletonList("everything"), false);
		}

	}

	@Override
	protected BundleActivator getBundleActivator() {
		return new RememberApplicationClientTestPreparatorBundleActivator();
	}

	private MultivaluedMap<String, String> _getExtraParameters() {
		MultivaluedMap<String, String> multivaluedMap =
			new MultivaluedHashMap<>();

		multivaluedMap.add(_REMEMBER_DEVICE_PARAMETER, StringPool.TRUE);

		return multivaluedMap;
	}

	private void _revokeOAuth2AuthorizationByAccessToken(String token)
		throws PortalException {

		_oAuth2AuthorizationLocalService.deleteOAuth2Authorization(
			_oAuth2AuthorizationLocalService.
				getOAuth2AuthorizationByAccessTokenContent(token));
	}

	private static final String _REMEMBER_DEVICE_COOKIE =
		"OAUTH2_REMEMBER_DEVICE";

	private static final String _REMEMBER_DEVICE_PARAMETER =
		"_com_liferay_oauth2_provider_web_internal_portlet_" +
			"OAuth2AuthorizePortlet_rememberDevice";

	@Inject
	private OAuth2AuthorizationLocalService _oAuth2AuthorizationLocalService;

}