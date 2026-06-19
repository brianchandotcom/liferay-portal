/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.oauth2.provider.dynamic.registration.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.oauth2.provider.client.test.BaseClientTestCase;
import com.liferay.oauth2.provider.client.test.BaseTestPreparatorBundleActivator;
import com.liferay.oauth2.provider.constants.GrantType;
import com.liferay.oauth2.provider.constants.OAuth2ApplicationConstants;
import com.liferay.oauth2.provider.model.OAuth2Application;
import com.liferay.oauth2.provider.service.OAuth2ApplicationLocalService;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.test.util.CompanyConfigurationTemporarySwapper;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserConstants;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MultivaluedHashMap;
import jakarta.ws.rs.core.Response;

import java.util.Arrays;
import java.util.Collections;
import java.util.Dictionary;
import java.util.List;

import org.apache.cxf.rs.security.oauth2.utils.OAuthConstants;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.BundleActivator;

/**
 * @author Jorge García Jiménez
 */
@FeatureFlag("LPD-63416")
@RunWith(Arquillian.class)
public class DynamicRegistrationServiceTest extends BaseClientTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testBearerInOpenMode() throws Exception {
		long companyId = TestPropsValues.getCompanyId();

		WebTarget registerWebTarget = getRegisterWebTarget();

		try (CompanyConfigurationTemporarySwapper
				companyConfigurationTemporarySwapper =
					_createCompanyConfigurationTemporarySwapper(companyId)) {

			Invocation.Builder invocationBuilder = authorize(
				registerWebTarget.request(),
				_getToken(_getDynamicRegistratorOAuth2Application()));

			Response response = invocationBuilder.method(
				"post",
				Entity.json(
					JSONUtil.put(
						_FIELD_CLIENT_NAME, RandomTestUtil.randomString()
					).put(
						_FIELD_GRANT_TYPES,
						new String[] {OAuthConstants.CLIENT_CREDENTIALS_GRANT}
					).put(
						_FIELD_REDIRECT_URIS,
						new String[] {
							"https://" + RandomTestUtil.randomString() +
								".com/callback"
						}
					).toString()));

			Assert.assertEquals(201, response.getStatus());
		}
	}

	@Test
	public void testDelete() throws Exception {
		OAuth2Application oAuth2Application =
			_oAuth2ApplicationLocalService.fetchOAuth2Application(
				TestPropsValues.getCompanyId(), _APPLICATION_NAME_DELETE_ME);

		WebTarget registerWebTarget = getRegisterWebTarget(
			oAuth2Application.getClientId());

		Invocation.Builder invocationBuilder = authorize(
			registerWebTarget.request(),
			_getToken(
				_oAuth2ApplicationLocalService.fetchOAuth2Application(
					TestPropsValues.getCompanyId(),
					_APPLICATION_NAME_DYNAMIC_REGISTER_TEST)));

		Response response = invocationBuilder.delete();

		Assert.assertEquals(403, response.getStatus());

		invocationBuilder = authorize(
			registerWebTarget.request(),
			_getToken(_getDynamicRegistratorOAuth2Application()));

		response = invocationBuilder.delete();

		Assert.assertEquals(204, response.getStatus());

		response = invocationBuilder.delete();

		Assert.assertEquals(401, response.getStatus());
	}

	@Test
	public void testOpenAccepted() throws Exception {
		long companyId = TestPropsValues.getCompanyId();

		WebTarget registerWebTarget = getRegisterWebTarget();

		Invocation.Builder invocationBuilder = registerWebTarget.request();

		String clientName = RandomTestUtil.randomString();

		String body = JSONUtil.put(
			_FIELD_CLIENT_NAME, clientName
		).put(
			_FIELD_GRANT_TYPES,
			new String[] {OAuthConstants.AUTHORIZATION_CODE_GRANT}
		).put(
			_FIELD_REDIRECT_URIS,
			new String[] {
				"https://" + RandomTestUtil.randomString() + ".com/callback"
			}
		).put(
			_FIELD_RESPONSE_TYPES,
			new String[] {OAuthConstants.CODE_RESPONSE_TYPE}
		).toString();

		try (CompanyConfigurationTemporarySwapper
				companyConfigurationTemporarySwapper =
					_createCompanyConfigurationTemporarySwapper(companyId)) {

			Response response = invocationBuilder.method(
				"post", Entity.json(body));

			Assert.assertEquals(201, response.getStatus());

			JSONObject responseJSONObject = parseJSONObject(response);

			Assert.assertEquals(
				clientName, responseJSONObject.getString(_FIELD_CLIENT_NAME));

			String clientId = responseJSONObject.getString(
				OAuthConstants.CLIENT_ID);

			OAuth2Application oAuth2Application =
				_oAuth2ApplicationLocalService.fetchOAuth2Application(
					companyId, clientId);

			User serviceAccountUser = _userLocalService.getUserByScreenName(
				companyId, UserConstants.SCREEN_NAME_DEFAULT_SERVICE_ACCOUNT);

			Assert.assertEquals(
				serviceAccountUser.getUserId(), oAuth2Application.getUserId());

			Assert.assertFalse(oAuth2Application.isTrustedApplication());
		}
	}

	@Test
	public void testOpenEnforcesHostAllowlist() throws Exception {
		String allowedHost = "test-allowed-" + RandomTestUtil.randomString();

		_testOpenEnforcesHostAllowlist(allowedHost, allowedHost, 201);
		_testOpenEnforcesHostAllowlist(
			allowedHost, "test-other-" + RandomTestUtil.randomString(), 403);

		String bracketedHost = "test-bracket-" + RandomTestUtil.randomString();

		_testOpenEnforcesHostAllowlist(
			bracketedHost,
			StringBundler.concat(
				"[", bracketedHost, "]:",
				PortalUtil.getPortalServerPort(false)),
			201);
		_testOpenEnforcesHostAllowlist(
			StringBundler.concat(
				"[", bracketedHost, "]:",
				PortalUtil.getPortalServerPort(false)),
			bracketedHost, 201);

		String portHost = "test-port-" + RandomTestUtil.randomString();

		_testOpenEnforcesHostAllowlist(
			portHost, portHost + ":" + PortalUtil.getPortalServerPort(false),
			201);
	}

	@Test
	public void testOpenRegistrationIsRejected() throws Exception {
		_testOpenRegistrationIsRejected(
			400, "invalid_client_metadata", _body(), _PROPERTY_ALLOWED_SCOPES,
			new String[] {"Liferay.Headless.Delivery.everything"});

		_testOpenRegistrationIsRejected(
			400, OAuthConstants.INVALID_SCOPE,
			JSONUtil.put(
				_FIELD_CLIENT_NAME, RandomTestUtil.randomString()
			).put(
				_FIELD_GRANT_TYPES,
				new String[] {OAuthConstants.CLIENT_CREDENTIALS_GRANT}
			).put(
				_FIELD_SCOPE, "Liferay.Headless.Admin.Site.everything"
			).toString(),
			_PROPERTY_ALLOWED_SCOPES,
			new String[] {"Liferay.Headless.Delivery.everything"});

		_testOpenRegistrationIsRejected(
			400, "invalid_client_metadata",
			JSONUtil.put(
				_FIELD_CLIENT_NAME, RandomTestUtil.randomString()
			).put(
				_FIELD_REDIRECT_URIS,
				new String[] {
					"https://" + RandomTestUtil.randomString() + ".com/callback"
				}
			).toString(),
			_PROPERTY_ALLOWED_GRANT_TYPES,
			new String[] {OAuthConstants.CLIENT_CREDENTIALS_GRANT});

		_testOpenRegistrationIsRejected(
			400, "invalid_redirect_uri",
			_body("https://attacker.test/callback"),
			_PROPERTY_ALLOWED_REDIRECT_URI_PATTERNS,
			new String[] {"https://*.example.org/*"});
		_testOpenRegistrationIsRejected(
			400, "invalid_redirect_uri",
			_body("https://attacker.test/foo.example.org/callback"),
			_PROPERTY_ALLOWED_REDIRECT_URI_PATTERNS,
			new String[] {"https://*.example.org/*"});
		_testOpenRegistrationIsRejected(
			400, "invalid_redirect_uri", _body(""),
			_PROPERTY_ALLOWED_REDIRECT_URI_PATTERNS,
			new String[] {"https://*.example.org/*"});

		_testOpenRegistrationIsRejected(
			401, null,
			JSONUtil.put(
				_FIELD_CLIENT_NAME, RandomTestUtil.randomString()
			).toString(),
			_PROPERTY_REQUIRE_INITIAL_ACCESS_TOKEN, true);
	}

	@Test
	public void testPost() throws Exception {
		WebTarget registerWebTarget = getRegisterWebTarget();

		Invocation.Builder invocationBuilder = authorize(
			registerWebTarget.request(),
			_getToken(
				_oAuth2ApplicationLocalService.fetchOAuth2Application(
					TestPropsValues.getCompanyId(),
					_APPLICATION_NAME_DYNAMIC_REGISTER_TEST)));

		Response response = invocationBuilder.method(
			"post",
			Entity.json(
				JSONUtil.put(
					_FIELD_CLIENT_NAME, RandomTestUtil.randomString()
				).toString()));

		Assert.assertEquals(401, response.getStatus());

		String clientName = RandomTestUtil.randomString();

		String scope =
			RandomTestUtil.randomString() + StringPool.SPACE +
				RandomTestUtil.randomString();

		JSONObject jsonObject = JSONUtil.put(
			_FIELD_CLIENT_NAME, clientName
		).put(
			_FIELD_GRANT_TYPES,
			new String[] {OAuthConstants.CLIENT_CREDENTIALS_GRANT}
		).put(
			_FIELD_LOGO_URI, RandomTestUtil.randomString()
		).put(
			_FIELD_REDIRECT_URIS,
			new String[] {
				StringBundler.concat(
					Http.HTTPS_WITH_SLASH, RandomTestUtil.randomString(),
					StringPool.SLASH, RandomTestUtil.randomString()),
				StringBundler.concat(
					Http.HTTPS_WITH_SLASH, RandomTestUtil.randomString(),
					StringPool.SLASH, RandomTestUtil.randomString())
			}
		).put(
			_FIELD_SCOPE, scope
		);

		response = invocationBuilder.method(
			"post", Entity.json(jsonObject.toString()));

		Assert.assertEquals(401, response.getStatus());

		OAuth2Application oAuth2Application =
			_getDynamicRegistratorOAuth2Application();

		invocationBuilder = authorize(
			registerWebTarget.request(), _getToken(oAuth2Application));

		response = invocationBuilder.method(
			"post", Entity.json(jsonObject.toString()));

		Assert.assertEquals(201, response.getStatus());

		JSONObject responseJSONObject = parseJSONObject(response);

		Assert.assertEquals(
			clientName, responseJSONObject.getString(_FIELD_CLIENT_NAME));

		String[] expectedScopes = StringUtil.split(scope, CharPool.SPACE);

		Arrays.sort(expectedScopes);

		String[] actualScopes = StringUtil.split(
			responseJSONObject.getString("scope"), CharPool.SPACE);

		Arrays.sort(actualScopes);

		Assert.assertArrayEquals(expectedScopes, actualScopes);

		String clientId = responseJSONObject.getString(
			OAuthConstants.CLIENT_ID);

		jsonObject.put(
			_FIELD_RESPONSE_TYPES,
			Collections.singletonList(OAuthConstants.CODE_RESPONSE_TYPE));

		response = invocationBuilder.method(
			"post", Entity.json(jsonObject.toString()));

		Assert.assertEquals(400, response.getStatus());

		Assert.assertEquals("invalid_client_metadata", parseError(response));

		registerWebTarget = getRegisterWebTarget(clientId);

		invocationBuilder = authorize(
			registerWebTarget.request(), _getToken(oAuth2Application));

		invocationBuilder.header("Origin", RandomTestUtil.randomString());

		response = invocationBuilder.get();

		Assert.assertEquals(200, response.getStatus());

		responseJSONObject = parseJSONObject(response);

		Assert.assertEquals(
			clientName, responseJSONObject.getString(_FIELD_CLIENT_NAME));

		Assert.assertNull(
			response.getHeaderString("Access-Control-Allow-Origin"));
	}

	@Test
	public void testPromotesPublicAuthorizationCode() throws Exception {
		long companyId = TestPropsValues.getCompanyId();

		WebTarget registerWebTarget = getRegisterWebTarget();

		String clientName = RandomTestUtil.randomString();

		String body = JSONUtil.put(
			_FIELD_CLIENT_NAME, clientName
		).put(
			_FIELD_GRANT_TYPES,
			new String[] {OAuthConstants.AUTHORIZATION_CODE_GRANT}
		).put(
			_FIELD_REDIRECT_URIS,
			new String[] {
				"https://" + RandomTestUtil.randomString() + ".com/callback"
			}
		).put(
			_FIELD_RESPONSE_TYPES,
			new String[] {OAuthConstants.CODE_RESPONSE_TYPE}
		).put(
			_FIELD_TOKEN_ENDPOINT_AUTH_METHOD,
			OAuthConstants.TOKEN_ENDPOINT_AUTH_NONE
		).toString();

		try (CompanyConfigurationTemporarySwapper
				companyConfigurationTemporarySwapper =
					_createCompanyConfigurationTemporarySwapper(companyId)) {

			Invocation.Builder invocationBuilder = registerWebTarget.request();

			Response response = invocationBuilder.method(
				"post", Entity.json(body));

			Assert.assertEquals(201, response.getStatus());

			JSONObject responseJSONObject = parseJSONObject(response);

			JSONArray grantTypesJSONArray = responseJSONObject.getJSONArray(
				_FIELD_GRANT_TYPES);

			Assert.assertEquals(1, grantTypesJSONArray.length());
			Assert.assertEquals(
				OAuthConstants.AUTHORIZATION_CODE_GRANT,
				grantTypesJSONArray.getString(0));

			String clientId = responseJSONObject.getString(
				OAuthConstants.CLIENT_ID);

			OAuth2Application oAuth2Application =
				_oAuth2ApplicationLocalService.fetchOAuth2Application(
					companyId, clientId);

			Assert.assertEquals(
				Collections.singletonList(GrantType.AUTHORIZATION_CODE_PKCE),
				oAuth2Application.getAllowedGrantTypesList());
		}
	}

	@Test
	public void testPut() throws Exception {
		OAuth2Application oAuth2Application =
			_oAuth2ApplicationLocalService.fetchOAuth2Application(
				TestPropsValues.getCompanyId(), _APPLICATION_NAME_DELETE_ME);

		WebTarget registerWebTarget = getRegisterWebTarget(
			oAuth2Application.getClientId());

		Invocation.Builder invocationBuilder = authorize(
			registerWebTarget.request(),
			_getToken(_getDynamicRegistratorOAuth2Application()));

		String clientName = RandomTestUtil.randomString();

		Response response = invocationBuilder.method(
			"put",
			Entity.json(
				JSONUtil.put(
					_FIELD_CLIENT_NAME, clientName
				).put(
					_FIELD_GRANT_TYPES,
					new String[] {OAuthConstants.CLIENT_CREDENTIALS_GRANT}
				).put(
					_FIELD_LOGO_URI, RandomTestUtil.randomString()
				).put(
					_FIELD_REDIRECT_URIS,
					new String[] {
						StringBundler.concat(
							Http.HTTPS_WITH_SLASH,
							RandomTestUtil.randomString(), StringPool.SLASH,
							RandomTestUtil.randomString()),
						StringBundler.concat(
							Http.HTTPS_WITH_SLASH,
							RandomTestUtil.randomString(), StringPool.SLASH,
							RandomTestUtil.randomString())
					}
				).put(
					_FIELD_SCOPE, RandomTestUtil.randomString()
				).toString()));

		Assert.assertEquals(200, response.getStatus());

		JSONObject jsonObject = parseJSONObject(response);

		Assert.assertEquals(
			clientName, jsonObject.getString(_FIELD_CLIENT_NAME));
	}

	protected static WebTarget getRegisterWebTarget() {
		WebTarget webTarget = getOAuth2WebTarget();

		return webTarget.path("register");
	}

	protected static WebTarget getRegisterWebTarget(String target) {
		WebTarget webTarget = getRegisterWebTarget();

		return webTarget.path(target);
	}

	@Override
	protected BundleActivator getBundleActivator() {
		return new DynamicRegistrationServiceTestPreparatorBundleActivator();
	}

	private String _body() {
		return _body(
			"https://" + RandomTestUtil.randomString() + ".com/callback");
	}

	private String _body(String redirectUri) {
		return JSONUtil.put(
			_FIELD_CLIENT_NAME, RandomTestUtil.randomString()
		).put(
			_FIELD_GRANT_TYPES,
			new String[] {OAuthConstants.AUTHORIZATION_CODE_GRANT}
		).put(
			_FIELD_REDIRECT_URIS, new String[] {redirectUri}
		).put(
			_FIELD_RESPONSE_TYPES,
			new String[] {OAuthConstants.CODE_RESPONSE_TYPE}
		).toString();
	}

	private CompanyConfigurationTemporarySwapper
			_createCompanyConfigurationTemporarySwapper(
				long companyId, Object... overrides)
		throws Exception {

		Dictionary<String, Object> properties =
			HashMapDictionaryBuilder.<String, Object>put(
				_PROPERTY_ALLOWED_GRANT_TYPES, new String[] {"*"}
			).put(
				_PROPERTY_ALLOWED_HOSTS, new String[] {"*"}
			).put(
				_PROPERTY_ALLOWED_REDIRECT_URI_PATTERNS, new String[] {"*"}
			).put(
				_PROPERTY_ALLOWED_SCOPES, new String[] {"*"}
			).put(
				_PROPERTY_REQUIRE_INITIAL_ACCESS_TOKEN, false
			).build();

		for (int i = 0; i < overrides.length; i += 2) {
			properties.put((String)overrides[i], overrides[i + 1]);
		}

		return new CompanyConfigurationTemporarySwapper(
			companyId, _CONFIGURATION_PID, properties);
	}

	private OAuth2Application _getDynamicRegistratorOAuth2Application()
		throws Exception {

		DynamicQuery dynamicQuery =
			_oAuth2ApplicationLocalService.dynamicQuery();

		Property companyIdProperty = PropertyFactoryUtil.forName("companyId");

		dynamicQuery.add(companyIdProperty.eq(TestPropsValues.getCompanyId()));

		Property nameProperty = PropertyFactoryUtil.forName("name");

		dynamicQuery.add(
			nameProperty.eq(
				OAuth2ApplicationConstants.NAME_DYNAMIC_REGISTRATOR));

		List<OAuth2Application> oAuth2Applications =
			_oAuth2ApplicationLocalService.dynamicQuery(dynamicQuery);

		Assert.assertFalse(oAuth2Applications.isEmpty());

		return oAuth2Applications.get(0);
	}

	private String _getToken(OAuth2Application oAuth2Application) {
		WebTarget tokenWebTarget = getTokenWebTarget();

		Invocation.Builder invocationBuilder = tokenWebTarget.request();

		String tokenString = parseTokenString(
			invocationBuilder.post(
				Entity.form(
					new MultivaluedHashMap<>(
						HashMapBuilder.put(
							OAuthConstants.CLIENT_ID,
							oAuth2Application.getClientId()
						).put(
							OAuthConstants.CLIENT_SECRET,
							oAuth2Application.getClientSecret()
						).put(
							OAuthConstants.GRANT_TYPE,
							OAuthConstants.CLIENT_CREDENTIALS_GRANT
						).build()))));

		Assert.assertNotNull(tokenString);

		return tokenString;
	}

	private void _testOpenEnforcesHostAllowlist(
			String allowedHost, String requestHost, int expectedStatus)
		throws Exception {

		long companyId = TestPropsValues.getCompanyId();

		WebTarget registerWebTarget = getRegisterWebTarget();

		try (CompanyConfigurationTemporarySwapper
				companyConfigurationTemporarySwapper =
					_createCompanyConfigurationTemporarySwapper(
						companyId, _PROPERTY_ALLOWED_HOSTS,
						new String[] {allowedHost},
						_PROPERTY_TRUST_PROXY_HEADERS, true)) {

			Invocation.Builder invocationBuilder = registerWebTarget.request();

			invocationBuilder.header("X-Forwarded-For", requestHost);

			Response response = invocationBuilder.method(
				"post", Entity.json(_body()));

			Assert.assertEquals(expectedStatus, response.getStatus());

			if (expectedStatus == 403) {
				Assert.assertEquals("access_denied", parseError(response));
			}
		}
	}

	private void _testOpenRegistrationIsRejected(
			int expectedStatus, String expectedError, String body,
			Object... configOverrides)
		throws Exception {

		WebTarget registerWebTarget = getRegisterWebTarget();

		try (CompanyConfigurationTemporarySwapper
				companyConfigurationTemporarySwapper =
					_createCompanyConfigurationTemporarySwapper(
						TestPropsValues.getCompanyId(), configOverrides)) {

			Invocation.Builder invocationBuilder = registerWebTarget.request();

			Response response = invocationBuilder.method(
				"post", Entity.json(body));

			Assert.assertEquals(expectedStatus, response.getStatus());

			if (expectedError != null) {
				Assert.assertEquals(expectedError, parseError(response));
			}
		}
	}

	private static final String _APPLICATION_NAME_DELETE_ME =
		"oauthDeleteMeApplication";

	private static final String _APPLICATION_NAME_DYNAMIC_REGISTER_TEST =
		"oauthDynamicRegisterTestApplication";

	private static final String _CONFIGURATION_PID =
		"com.liferay.oauth2.provider.rest.internal.configuration." +
			"DynamicRegistrationConfiguration";

	private static final String _FIELD_CLIENT_NAME = "client_name";

	private static final String _FIELD_GRANT_TYPES = "grant_types";

	private static final String _FIELD_LOGO_URI = "logo_uri";

	private static final String _FIELD_REDIRECT_URIS = "redirect_uris";

	private static final String _FIELD_RESPONSE_TYPES = "response_types";

	private static final String _FIELD_SCOPE = "scope";

	private static final String _FIELD_TOKEN_ENDPOINT_AUTH_METHOD =
		"token_endpoint_auth_method";

	private static final String _PROPERTY_ALLOWED_GRANT_TYPES =
		"dynamic.registration.allowed.grant.types";

	private static final String _PROPERTY_ALLOWED_HOSTS =
		"dynamic.registration.allowed.hosts";

	private static final String _PROPERTY_ALLOWED_REDIRECT_URI_PATTERNS =
		"dynamic.registration.allowed.redirect.uri.patterns";

	private static final String _PROPERTY_ALLOWED_SCOPES =
		"dynamic.registration.allowed.scopes";

	private static final String _PROPERTY_REQUIRE_INITIAL_ACCESS_TOKEN =
		"dynamic.registration.require.initial.access.token";

	private static final String _PROPERTY_TRUST_PROXY_HEADERS =
		"dynamic.registration.trust.proxy.headers";

	@Inject
	private OAuth2ApplicationLocalService _oAuth2ApplicationLocalService;

	@Inject
	private UserLocalService _userLocalService;

	private class DynamicRegistrationServiceTestPreparatorBundleActivator
		extends BaseTestPreparatorBundleActivator {

		@Override
		protected void prepareTest() throws Exception {
			long companyId = TestPropsValues.getCompanyId();

			User user = UserTestUtil.getAdminUser(companyId);

			createOAuth2Application(
				companyId, user, _APPLICATION_NAME_DYNAMIC_REGISTER_TEST);
			createOAuth2Application(
				companyId, user, _APPLICATION_NAME_DELETE_ME);
		}

	}

}