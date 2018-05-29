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

import com.liferay.oauth2.provider.test.internal.activator.BaseTestPreparatorBundleActivator;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Carlos Sierra Andrés
 */
@RunAsClient
@RunWith(Arquillian.class)
public class TokenCompanyTest extends BaseClientTest {

	@Deployment
	public static Archive<?> getDeployment() throws Exception {
		return BaseClientTest.getDeployment(
			AnnotatedApplicationBundleActivator.class);
	}

	@Test
	public void test() throws Exception {
		getToken("oauthTestApplication", "myhost.xyz");

		getToken("oauthTestApplicationAllowed", "myhostallowed.xyz");

		Assert.assertEquals(
			"invalid_grant",
			getToken(
				"oauthTestApplicationDefault", "myhostdefaultuser.xyz",
				this::getClientCredentials, this::parseError));
	}

	public static class AnnotatedApplicationBundleActivator
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

}