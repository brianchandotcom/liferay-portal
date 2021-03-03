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
import com.liferay.oauth2.provider.internal.test.TestAnnotatedApplication;
import com.liferay.oauth2.provider.internal.test.TestApplication;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Arrays;
import java.util.Collections;
import java.util.Dictionary;

import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;

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
public class AnnotationsAndHttpPrefixApplicationClientTest
	extends BaseClientTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void test() throws Exception {
		String tokenString = getToken("oauthTestApplication");

		WebTarget webTarget = getWebTarget("/methods");

		Invocation.Builder builder = authorize(
			webTarget.request(), tokenString);

		Assert.assertEquals("get", builder.get(String.class));

		webTarget = getWebTarget("/annotated");

		builder = authorize(webTarget.request(), tokenString);

		Assert.assertEquals("everything.read", builder.get(String.class));

		tokenString = getToken("oauthTestApplicationWrong");

		webTarget = getWebTarget("/methods");

		builder = authorize(webTarget.request(), tokenString);

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				"portal_web.docroot.errors.code_jsp", LoggerTestUtil.WARN)) {

			Assert.assertEquals(
				403,
				builder.get(
				).getStatus());

			webTarget = getWebTarget("/annotated");

			builder = authorize(webTarget.request(), tokenString);

			Assert.assertEquals(
				403,
				builder.get(
				).getStatus());
		}
	}

	public static class AnnotationsAndHttpPrefixTestPreparatorBundleActivator
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
				"oauth2.scope.checker.type", "annotations");
			annotatedApplicationProperties.put("prefix", "annotations");
			annotatedApplicationProperties.put(
				"osgi.jaxrs.name", TestAnnotatedApplication.class.getName());

			Dictionary<String, Object> scopeMapperProperties =
				new HashMapDictionary<>();

			scopeMapperProperties.put(
				"osgi.jaxrs.name", TestApplication.class.getName());

			Dictionary<String, Object> bundlePrefixProperties =
				new HashMapDictionary<>();

			bundlePrefixProperties.put(
				"osgi.jaxrs.name",
				new String[] {
					"com.liferay.oauth2.provider.internal.test.TestApplication",
					"com.liferay.oauth2.provider.internal.test." +
						"TestAnnotatedApplication"
				});

			bundlePrefixProperties.put(
				"service.properties", new String[] {"prefix"});

			bundlePrefixProperties.put("include.bundle.symbolic.name", false);

			createFactoryConfiguration(
				"com.liferay.oauth2.provider.scope.internal.configuration." +
					"ConfigurableScopeMapperConfiguration",
				scopeMapperProperties);

			createFactoryConfiguration(
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

	@Override
	protected BundleActivator getBundleActivator() {
		return new AnnotationsAndHttpPrefixTestPreparatorBundleActivator();
	}

}