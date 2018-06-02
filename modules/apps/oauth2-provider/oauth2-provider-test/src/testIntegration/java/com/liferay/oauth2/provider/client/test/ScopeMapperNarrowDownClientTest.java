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

import java.util.Collections;
import java.util.Dictionary;

import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Carlos Sierra Andrés
 */
@Ignore("TODO: We still need to fix this one")
@RunAsClient
@RunWith(Arquillian.class)
public class ScopeMapperNarrowDownClientTest extends BaseClientTestCase {

	@Deployment
	public static Archive<?> getDeployment() throws Exception {
		return BaseClientTestCase.getDeployment(
			ScopeMapperNarrowDownClientTestPreparatorBundleActivator.class);
	}

	@Test
	public void test() throws Exception {
		WebTarget webTarget = getWebTarget("/annotated");

		Invocation.Builder invocationBuilder = authorize(
			webTarget.request(),
			getToken(
				"oauthTestApplication", null,
				getClientCredentials("everything"), this::parseTokenString));

		Assert.assertEquals(
			"everything.readonly", invocationBuilder.get(String.class));

		String scopeString = getToken(
			"oauthTestApplication", null,
			getClientCredentials("everything.readonly"),
			this::parseScopeString);

		Assert.assertEquals("everything.readonly", scopeString);
	}

	public static class ScopeMapperNarrowDownClientTestPreparatorBundleActivator
		extends BaseTestPreparatorBundleActivator {

		@Override
		protected void prepareTest() throws Exception {
			long defaultCompanyId = PortalUtil.getDefaultCompanyId();

			User user = UserTestUtil.getAdminUser(defaultCompanyId);

			Dictionary<String, Object> annotatedApplicationProperties =
				new HashMapDictionary<>();

			annotatedApplicationProperties.put(
				"oauth2.scopechecker.type", "annotations");

			Dictionary<String, Object> scopeMapperProperties =
				new HashMapDictionary<>();

			scopeMapperProperties.put(
				"osgi.jaxrs.name", TestApplication.class.getName());

			createFactoryConfiguration(
				"com.liferay.oauth2.provider.scope.internal.configuration." +
					"ConfigurableScopeMapperConfiguration",
				scopeMapperProperties);

			Dictionary<String, Object> properties = new HashMapDictionary<>();

			properties.put("osgi.jaxrs.name", TestApplication.class.getName());

			registerJaxRsApplication(
				new TestAnnotatedApplication(), "annotated",
				annotatedApplicationProperties);

			registerJaxRsApplication(
				new TestApplication(), "methods", properties);

			createOAuth2Application(
				defaultCompanyId, user, "oauthTestApplication",
				Collections.singletonList("everything"));
		}

	}

}