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
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Collections;

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
public class GrantedFlowsTest extends BaseClientTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void test() throws Exception {
		String errorString = getToken(
			"oauthTestApplicationPassword", null,
			this::getClientCredentialsResponse, this::parseError);

		Assert.assertEquals("unauthorized_client", errorString);

		String tokenString = getToken(
			"oauthTestApplicationPassword", null,
			getResourceOwnerPasswordBiFunction("test@liferay.com", "test"),
			this::parseTokenString);

		Assert.assertNotNull(tokenString);

		errorString = getToken(
			"oauthTestApplicationClient", null,
			getResourceOwnerPasswordBiFunction("test@liferay.com", "test"),
			this::parseError);

		Assert.assertEquals("unauthorized_client", errorString);

		tokenString = getToken(
			"oauthTestApplicationClient", null,
			this::getClientCredentialsResponse, this::parseTokenString);

		Assert.assertNotNull(tokenString);

		errorString = getToken(
			"oauthTestApplicationNoGrants", null,
			getAuthorizationCodePKCEBiFunction(
				"test@liferay.com", "test", null),
			this::parseError);

		Assert.assertEquals("unauthorized_client", errorString);

		tokenString = getToken(
			"oauthTestApplicationCode", null,
			getAuthorizationCodeBiFunction("test@liferay.com", "test", null),
			this::parseTokenString);

		Assert.assertNotNull(tokenString);

		errorString = getToken(
			"oauthTestApplicationPassword", null,
			getAuthorizationCodeBiFunction("test@liferay.com", "test", null),
			this::parseError);

		Assert.assertEquals("unauthorized_client", errorString);

		tokenString = getToken(
			"oauthTestApplicationCodePKCE", null,
			getAuthorizationCodePKCEBiFunction(
				"test@liferay.com", "test", null),
			this::parseTokenString);

		Assert.assertNotNull(tokenString);
	}

	public static class AnnotatedApplicationTestPreparatorBundleActivator
		extends BaseTestPreparatorBundleActivator {

		@Override
		protected void prepareTest() throws Exception {
			long defaultCompanyId = PortalUtil.getDefaultCompanyId();

			User user = UserTestUtil.getAdminUser(defaultCompanyId);

			createOAuth2Application(
				defaultCompanyId, user, "oauthTestApplicationCode",
				Collections.singletonList(GrantType.AUTHORIZATION_CODE),
				Collections.singletonList("everything"));

			createOAuth2Application(
				defaultCompanyId, user, "oauthTestApplicationCodePKCE", null,
				Collections.singletonList(GrantType.AUTHORIZATION_CODE_PKCE),
				Collections.singletonList("everything"),
				Collections.singletonList("http://redirecturi:8080"));

			createOAuth2Application(
				defaultCompanyId, user, "oauthTestApplicationClient",
				Collections.singletonList(GrantType.CLIENT_CREDENTIALS),
				Collections.singletonList("everything"));

			createOAuth2Application(
				defaultCompanyId, user, "oauthTestApplicationNoGrants", null,
				Collections.emptyList(),
				Collections.singletonList("everything"),
				Collections.singletonList("http://redirecturi:8080"));

			createOAuth2Application(
				defaultCompanyId, user, "oauthTestApplicationPassword",
				Collections.singletonList(GrantType.RESOURCE_OWNER_PASSWORD),
				Collections.singletonList("everything"));
		}

	}

	@Override
	protected BundleActivator getBundleActivator() {
		return new AnnotatedApplicationTestPreparatorBundleActivator();
	}

}