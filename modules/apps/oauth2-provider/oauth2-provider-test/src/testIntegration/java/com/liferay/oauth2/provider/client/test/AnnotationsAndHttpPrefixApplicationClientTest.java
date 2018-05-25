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

import com.liferay.oauth2.provider.test.internal.TestAnnotatedApplication;
import com.liferay.oauth2.provider.test.internal.TestApplication;
import com.liferay.oauth2.provider.test.internal.activator.BaseTestPreparatorBundleActivator;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.PortalUtil;

import java.util.Arrays;
import java.util.Collections;
import java.util.Dictionary;

import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;

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
public class AnnotationsAndHttpPrefixApplicationClientTest
	extends BaseClientTest {

	@Deployment
	public static Archive<?> getDeployment() throws Exception {
		return BaseClientTest.getDeployment(
			AnnotationsAndHttpPrefixBundleActivator.class);
	}

	@Test
	public void test() throws Exception {
		String tokenString = getToken("oauthTestApplication");

		WebTarget applicationTarget = getWebTarget("/methods");

		Invocation.Builder builder = authorize(
			applicationTarget.request(), tokenString);

		Assert.assertEquals("get", builder.get(String.class));

		applicationTarget = getWebTarget("/annotated");

		builder = authorize(applicationTarget.request(), tokenString);

		Assert.assertEquals("everything.readonly", builder.get(String.class));

		tokenString = getToken("oauthTestApplicationWrong");

		applicationTarget = getWebTarget("/methods");

		builder = authorize(applicationTarget.request(), tokenString);

		Assert.assertEquals(403, builder.get().getStatus());

		applicationTarget = getWebTarget("/annotated");

		builder = authorize(applicationTarget.request(), tokenString);

		Assert.assertEquals(403, builder.get().getStatus());
	}

	public static class AnnotationsAndHttpPrefixBundleActivator
		extends BaseTestPreparatorBundleActivator {

		@Override
		protected void prepareTest() throws Exception {
			long defaultCompanyId = PortalUtil.getDefaultCompanyId();

			User user = UserTestUtil.getAdminUser(defaultCompanyId);

			Dictionary<String, Object> testApplicationProperties =
				new HashMapDictionary<>();

			testApplicationProperties.put("prefix", "methods");
			testApplicationProperties.put(
				"osgi.jaxrs.name", TestApplication.class.getName());

			Dictionary<String, Object> annotatedApplicationProperties =
				new HashMapDictionary<>();

			annotatedApplicationProperties.put(
				"oauth2.scopechecker.type", "annotations");
			annotatedApplicationProperties.put("prefix", "annotations");
			annotatedApplicationProperties.put(
				"osgi.jaxrs.name", TestAnnotatedApplication.class.getName());

			HashMapDictionary<String, Object> scopeMapperProperties =
				new HashMapDictionary<>();

			scopeMapperProperties.put(
				"osgi.jaxrs.name", TestApplication.class.getName());

			HashMapDictionary<String, Object> bundlePrefixProperties =
				new HashMapDictionary<>();

			bundlePrefixProperties.put(
				"osgi.jaxrs.name",
				new String[] {
					"com.liferay.oauth2.provider.test.internal.TestApplication",
					"com.liferay.oauth2.provider.test.internal." +
						"TestAnnotatedApplication"
				});

			bundlePrefixProperties.put(
				"service.properties", new String[] {"prefix"});

			bundlePrefixProperties.put("include.bundle.symbolic.name", false);

			createConfigurationFactory(
				"com.liferay.oauth2.provider.scope.internal.configuration." +
					"ConfigurableScopeMapperConfiguration",
				scopeMapperProperties);

			createConfigurationFactory(
				"com.liferay.oauth2.provider.scope.internal.configuration." +
					"BundlePrefixHandlerFactoryConfiguration",
				bundlePrefixProperties);

			registerJaxRsApplication(
				new TestApplication(), "methods", testApplicationProperties);

			registerJaxRsApplication(
				new TestAnnotatedApplication(), "annotated",
				annotatedApplicationProperties);

			createOAuth2Application(
				defaultCompanyId, user, "oauthTestApplication",
				Arrays.asList("annotations/everything", "methods/everything"));

			createOAuth2Application(
				defaultCompanyId, user, "oauthTestApplicationWrong",
				Collections.singletonList("everything"));
		}

	}

}