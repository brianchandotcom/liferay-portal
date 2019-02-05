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
import com.liferay.oauth2.provider.test.internal.TestAnnotatedApplicationInterface;
import com.liferay.oauth2.provider.test.internal.activator.BaseTestPreparatorBundleActivator;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.PortalUtil;

import java.util.Arrays;
import java.util.Dictionary;

import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
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
public class AnnotatedApplicationClientTest extends BaseClientTestCase {

	@Deployment
	public static Archive<?> getArchive() throws Exception {
		return BaseClientTestCase.getArchive(
			AnnotatedApplicationTestPreparatorBundleActivator.class);
	}

	@Test
	public void test() throws Exception {
		testNoScopeAnnotation("/annotated-impl/no-scope");
		testRequiresScopeAnnotation("/annotated-impl");

		testNoScopeAnnotation("/annotated-interface/no-scope");
		testRequiresScopeAnnotation("/annotated-interface");
	}

	public static class AnnotatedApplicationTestPreparatorBundleActivator
		extends BaseTestPreparatorBundleActivator {

		@Override
		protected void prepareTest() throws Exception {
			long defaultCompanyId = PortalUtil.getDefaultCompanyId();

			User user = UserTestUtil.getAdminUser(defaultCompanyId);

			Dictionary<String, Object> properties = new HashMapDictionary<>();

			properties.put("oauth2.scope.checker.type", "annotations");

			registerJaxRsApplication(
				new TestAnnotatedApplicationInterface(), "annotated-interface",
				properties);

			registerJaxRsApplication(
				new TestAnnotatedApplication(), "annotated-impl", properties);

			createOAuth2Application(
				defaultCompanyId, user, "oauthTestApplication",
				Arrays.asList(
					"everything", "everything.read", "everything.write"));
		}

	}

	protected void testNoScopeAnnotation(String path) {
		WebTarget webTarget = getWebTarget(path);

		Invocation.Builder invocationBuilder = webTarget.request();

		Response response = invocationBuilder.get();

		Assert.assertEquals(403, response.getStatus());

		invocationBuilder = authorize(
			invocationBuilder,
			getToken(
				"oauthTestApplication", null,
				getClientCredentialsResponseBiFunction(StringPool.BLANK),
				this::parseTokenString));

		response = invocationBuilder.get();

		Assert.assertEquals("no-scope", invocationBuilder.get(String.class));
	}

	protected void testRequiresScopeAnnotation(String path) {
		WebTarget webTarget = getWebTarget(path);

		Invocation.Builder invocationBuilder = webTarget.request();

		Response response = invocationBuilder.get();

		Assert.assertEquals(403, response.getStatus());

		invocationBuilder = authorize(
			webTarget.request(),
			getToken(
				"oauthTestApplication", null,
				getClientCredentialsResponseBiFunction("everything.write"),
				this::parseTokenString));

		response = invocationBuilder.get();

		Assert.assertEquals(403, response.getStatus());

		invocationBuilder = authorize(
			webTarget.request(),
			getToken(
				"oauthTestApplication", null,
				getClientCredentialsResponseBiFunction("everything.read"),
				this::parseTokenString));

		response = invocationBuilder.get();

		Assert.assertEquals(
			"everything.read", invocationBuilder.get(String.class));
	}

}