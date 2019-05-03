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
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

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
public class TokenCompanyTest extends BaseClientTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void test() throws Exception {
		getToken("oauthTestApplication", "myhost.xyz");

		getToken("oauthTestApplicationAllowed", "myhostallowed.xyz");

		Assert.assertEquals(
			"invalid_grant",
			getToken(
				"oauthTestApplicationDefault", "myhostdefaultuser.xyz",
				this::getClientCredentialsResponse, this::parseError));
	}

	public static class AnnotatedApplicationTestPreparatorBundleActivator
		extends BaseTestPreparatorBundleActivator {

		@Override
		protected void prepareTest() throws Exception {
			User user = addUser(createCompany("myhost"));

			createOAuth2Application(
				user.getCompanyId(), user, "oauthTestApplication");

			User adminUser = addAdminUser(createCompany("myhostallowed"));

			createOAuth2Application(
				adminUser.getCompanyId(), adminUser,
				"oauthTestApplicationAllowed");

			Company company = createCompany("myhostdefaultuser");

			createOAuth2Application(
				company.getCompanyId(), company.getDefaultUser(),
				"oauthTestApplicationDefault");
		}

	}

	@Override
	protected BundleActivator getBundleActivator() {
		return new AnnotatedApplicationTestPreparatorBundleActivator();
	}

}