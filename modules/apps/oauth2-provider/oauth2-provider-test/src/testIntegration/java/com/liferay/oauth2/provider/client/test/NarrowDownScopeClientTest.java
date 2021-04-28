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
import com.liferay.oauth2.provider.internal.test.TestApplication;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Arrays;
import java.util.HashSet;
import java.util.function.Function;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.BundleActivator;

/**
 * @author Carlos Sierra Andrés
 */
@RunWith(Arquillian.class)
public class NarrowDownScopeClientTest extends BaseClientTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void test() throws Exception {
		Assert.assertEquals(
			"GET",
			getToken(
				"oauthTestApplication", null,
				getAuthorizationCodeBiFunction(
					"test@liferay.com", "test", null, "GET"),
				this::parseScopeString));

		Assert.assertEquals(
			"GET",
			getToken(
				"oauthTestApplication", null,
				getClientCredentialsResponseBiFunction("GET"),
				this::parseScopeString));

		Response response = getToken(
			"oauthTestApplication", null,
			getResourceOwnerPasswordBiFunction(
				"test@liferay.com", "test", "GET"),
			Function.identity());

		JSONObject jsonObject = parseJSONObject(response);

		Assert.assertEquals("GET", jsonObject.getString("scope"));

		WebTarget webTarget = getWebTarget("methods");

		Invocation.Builder builder = authorize(
			webTarget.request(), jsonObject.getString("access_token"));

		Response postResponse = builder.post(
			Entity.entity("", MediaType.TEXT_PLAIN_TYPE));

		Assert.assertEquals(403, postResponse.getStatus());

		String scopeString = getToken(
			"oauthTestApplication", null,
			getResourceOwnerPasswordBiFunction("test@liferay.com", "test"),
			this::parseScopeString);

		Assert.assertEquals(
			new HashSet<>(Arrays.asList("GET", "POST")),
			new HashSet<>(Arrays.asList(scopeString.split(" "))));

		Assert.assertEquals(
			"invalid_grant",
			getToken(
				"oauthTestApplication", null,
				getResourceOwnerPasswordBiFunction(
					"test@liferay.com", "test", "GET POST PUT"),
				this::parseError));
	}

	public static class NarrowDownScopeTestPreparatorBundleActivator
		extends BaseTestPreparatorBundleActivator {

		@Override
		protected void prepareTest() throws Exception {
			long defaultCompanyId = PortalUtil.getDefaultCompanyId();

			User user = UserTestUtil.getAdminUser(defaultCompanyId);

			registerJaxRsApplication(
				new TestApplication(), "methods",
				HashMapDictionaryBuilder.<String, Object>put(
					"oauth2.test.application", true
				).build());

			createOAuth2Application(
				defaultCompanyId, user, "oauthTestApplication",
				Arrays.asList(
					GrantType.AUTHORIZATION_CODE, GrantType.CLIENT_CREDENTIALS,
					GrantType.RESOURCE_OWNER_PASSWORD),
				Arrays.asList("GET", "POST"));
		}

	}

	@Override
	protected BundleActivator getBundleActivator() {
		return new NarrowDownScopeTestPreparatorBundleActivator();
	}

}