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

package com.liferay.oauth2.provider.token.endpoint.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.oauth2.provider.client.test.BaseClientTestCase;
import com.liferay.oauth2.provider.client.test.BaseTestPreparatorBundleActivator;
import com.liferay.oauth2.provider.constants.GrantType;
import com.liferay.oauth2.provider.internal.test.TestAuthorizationGrant;
import com.liferay.oauth2.provider.internal.test.TestClientAuthentication;
import com.liferay.oauth2.provider.internal.test.TestClientPasswordClientAuthentication;
import com.liferay.oauth2.provider.internal.test.TestJWTAssertionAuthorizationGrant;
import com.liferay.oauth2.provider.internal.test.TestJWTAssertionClientAuthentication;
import com.liferay.oauth2.provider.internal.test.util.JWTAssertionUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Arrays;
import java.util.function.Function;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.BundleActivator;

/**
 * @author Arthur Chan
 */
@RunWith(Arquillian.class)
public class JWTAssertAuthorizationGrantTest extends BaseClientTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testClientAuthentications() {
		Assert.assertTrue(
			Validator.isNotNull(
				_getToken(
					_getDefaultAuthorizationGrant(),
					_testClientPasswordClientAuthentication)));
		Assert.assertTrue(
			Validator.isNotNull(
				_getToken(
					_getDefaultAuthorizationGrant(),
					_testJWTAssertionClientAuthentication1)));
		Assert.assertTrue(
			Validator.isNotNull(
				_getToken(
					_getDefaultAuthorizationGrant(),
					_testJWTAssertionClientAuthentication2)));
	}

	@Test
	public void testGrantWithCorrectAudience() throws Exception {
		User user = UserTestUtil.getAdminUser(PortalUtil.getDefaultCompanyId());

		TestJWTAssertionAuthorizationGrant testJWTAssertionAuthorizationGrant =
			new TestJWTAssertionAuthorizationGrant(
				_TEST_CLIENT_ID_1, null, user.getUuid(), getTokenWebTarget());

		Assert.assertTrue(
			Validator.isNotNull(_getToken(testJWTAssertionAuthorizationGrant)));
	}

	@Test
	public void testGrantWithWrongAudience() throws Exception {
		User user = UserTestUtil.getAdminUser(PortalUtil.getDefaultCompanyId());

		TestJWTAssertionAuthorizationGrant testJWTAssertionAuthorizationGrant =
			new TestJWTAssertionAuthorizationGrant(
				_TEST_CLIENT_ID_1, null, user.getUuid(),
				getJsonWebTarget("wrongPath"));

		Assert.assertTrue(
			Validator.isNull(_getToken(testJWTAssertionAuthorizationGrant)));
	}

	@Override
	protected BundleActivator getBundleActivator() {
		return new JWTBearerGrantTestPreparatorBundleActivator();
	}

	private static Invocation.Builder _getInvocationBuilder() {
		return getInvocationBuilder(
			null, getTokenWebTarget(), Function.identity());
	}

	private TestAuthorizationGrant _getDefaultAuthorizationGrant() {
		User user = null;

		try {
			user = UserTestUtil.getAdminUser(PortalUtil.getDefaultCompanyId());

			return new TestJWTAssertionAuthorizationGrant(
				_TEST_CLIENT_ID_1, null, user.getUuid(), getTokenWebTarget());
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	private String _getToken(TestAuthorizationGrant testAuthorizationGrant) {
		return _getToken(
			testAuthorizationGrant, _testClientPasswordClientAuthentication);
	}

	private String _getToken(
		TestAuthorizationGrant testAuthorizationGrant,
		TestClientAuthentication testClientAuthentication) {

		return parseTokenString(
			_getTokenResponse(
				testAuthorizationGrant, testClientAuthentication));
	}

	private Response _getTokenResponse(
		TestAuthorizationGrant testAuthorizationGrant,
		TestClientAuthentication testClientAuthentication) {

		MultivaluedMap<String, String> multivaluedMap =
			new MultivaluedHashMap<>();

		multivaluedMap.putAll(
			testAuthorizationGrant.getAuthorizationGrantParameters());
		multivaluedMap.putAll(
			testClientAuthentication.getClientAuthenticationParameters());

		return _invocationBuilder.post(Entity.form(multivaluedMap));
	}

	private static final String _TEST_CLIENT_ID_1 = "test_client_id_1";

	private static final String _TEST_CLIENT_ID_2 = "test_client_id_2";

	private static final String _TEST_CLIENT_ID_3 = "test_client_id_3";

	private static final String _TEST_CLIENT_SECRET =
		"oauthTestApplicationSecret";

	private static final Invocation.Builder _invocationBuilder =
		_getInvocationBuilder();

	private final TestClientPasswordClientAuthentication
		_testClientPasswordClientAuthentication =
			new TestClientPasswordClientAuthentication(
				_TEST_CLIENT_ID_1,
				JWTAssertAuthorizationGrantTest._TEST_CLIENT_SECRET);
	private final TestJWTAssertionClientAuthentication
		_testJWTAssertionClientAuthentication1 =
			new TestJWTAssertionClientAuthentication(
				getTokenWebTarget(), _TEST_CLIENT_ID_2, false,
				_TEST_CLIENT_ID_2, _TEST_CLIENT_SECRET, true);
	private final TestJWTAssertionClientAuthentication
		_testJWTAssertionClientAuthentication2 =
			new TestJWTAssertionClientAuthentication(
				getTokenWebTarget(), _TEST_CLIENT_ID_3, false,
				_TEST_CLIENT_ID_3, JWTAssertionUtil.JWKS, false);

	private static class JWTBearerGrantTestPreparatorBundleActivator
		extends BaseTestPreparatorBundleActivator {

		@Override
		protected void prepareTest() throws Exception {
			createFactoryConfiguration(
				"com.liferay.oauth2.provider.rest.internal.configuration." +
					"OAuth2InAssertionConfiguration",
				HashMapDictionaryBuilder.<String, Object>put(
					"oauth2.in.assertion.issuer", _TEST_CLIENT_ID_1
				).put(
					"oauth2.in.assertion.signature.json.web.key.set",
					JWTAssertionUtil.JWKS
				).put(
					"oauth2.in.assertion.user.auth.type", "UUID"
				).build());

			User user = UserTestUtil.getAdminUser(
				PortalUtil.getDefaultCompanyId());

			createOAuth2ApplicationWithClientSecretPost(
				user.getCompanyId(), user, _TEST_CLIENT_ID_1,
				_TEST_CLIENT_SECRET, Arrays.asList(GrantType.JWT_BEARER),
				Arrays.asList(
					"everything", "everything.read", "everything.write"));

			createOAuth2ApplicationWithClientSecretJWT(
				user.getCompanyId(), user, _TEST_CLIENT_ID_2,
				_TEST_CLIENT_SECRET, Arrays.asList(GrantType.JWT_BEARER),
				Arrays.asList(
					"everything", "everything.read", "everything.write"));

			createOAuth2ApplicationWithPrivateKeyJWT(
				user.getCompanyId(), user, _TEST_CLIENT_ID_3,
				Arrays.asList(GrantType.JWT_BEARER), JWTAssertionUtil.JWKS,
				Arrays.asList(
					"everything", "everything.read", "everything.write"));
		}

	}

}