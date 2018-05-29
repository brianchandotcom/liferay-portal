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

import com.liferay.oauth2.provider.test.internal.TestApplication;
import com.liferay.oauth2.provider.test.internal.activator.BaseTestPreparatorBundleActivator;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.PortalUtil;

import java.util.Arrays;
import java.util.Collections;
import java.util.Dictionary;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
public class HttpMethodApplicationClientTest extends BaseClientTest {

	@Deployment
	public static Archive<?> getDeployment() throws Exception {
		return BaseClientTest.getDeployment(
			MethodApplicationBundleActivator.class);
	}

	@Test
	public void test() throws Exception {
		WebTarget applicationTarget = getWebTarget("/methods");

		Invocation.Builder builder = authorize(
			applicationTarget.request(), getToken("oauthTestApplicationAfter"));

		Assert.assertEquals("get", builder.get(String.class));

		Response post = builder.post(
			Entity.entity("post", MediaType.TEXT_PLAIN_TYPE));

		Assert.assertEquals("post", post.readEntity(String.class));

		builder = authorize(
			applicationTarget.request(),
			getToken("oauthTestApplicationBefore"));

		Response response = builder.get();

		Assert.assertEquals(403, response.getStatus());

		builder = authorize(
			applicationTarget.request(), getToken("oauthTestApplicationWrong"));

		response = builder.get();

		Assert.assertEquals(403, response.getStatus());
	}

	public static class MethodApplicationBundleActivator
		extends BaseTestPreparatorBundleActivator {

		@Override
		protected void prepareTest() throws Exception {
			long defaultCompanyId = PortalUtil.getDefaultCompanyId();

			User user = UserTestUtil.getAdminUser(defaultCompanyId);

			Dictionary<String, Object> properties = new HashMapDictionary<>();

			properties.put("oauth2.test.application", true);

			createOAuth2Application(
				defaultCompanyId, user, "oauthTestApplicationBefore",
				Arrays.asList("GET", "POST"));

			registerJaxRsApplication(
				new TestApplication(), "methods", properties);

			createOAuth2Application(
				defaultCompanyId, user, "oauthTestApplicationAfter",
				Arrays.asList("GET", "POST"));

			createOAuth2Application(
				defaultCompanyId, user, "oauthTestApplicationWrong",
				Collections.singletonList("everything"));
		}

	}

}