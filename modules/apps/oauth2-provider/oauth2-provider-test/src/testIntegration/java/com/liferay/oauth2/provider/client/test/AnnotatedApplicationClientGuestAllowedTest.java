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
import com.liferay.oauth2.provider.test.internal.activator.BaseTestPreparatorBundleActivator;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.PortalUtil;

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
 * @author Marta Medio
 */
@RunAsClient
@RunWith(Arquillian.class)
public class AnnotatedApplicationClientGuestAllowedTest
	extends BaseClientTestCase {

	@Deployment
	public static Archive<?> getDeployment() throws Exception {
		return BaseClientTestCase.getDeployment(
			AnnotatedApplicationTestPreparatorBundleActivator.class);
	}

	@Test
	public void test() throws Exception {
		String invalidToken = "Invalid Token";

		WebTarget webTarget = getWebTarget("/annotated-guest-not-allowed");

		webTarget = webTarget.path("/no-scope");

		Invocation.Builder invocationBuilder = authorize(
			webTarget.request(), invalidToken);

		Response response = invocationBuilder.get();

		Assert.assertEquals(403, response.getStatus());

		invocationBuilder = authorize(
			webTarget.request(), getToken("oauthTestApplication"));

		Assert.assertEquals("no-scope", invocationBuilder.get(String.class));

		webTarget = getWebTarget("/annotated-guest-allowed");

		webTarget = webTarget.path("/no-scope");

		invocationBuilder = authorize(webTarget.request(), invalidToken);

		Assert.assertEquals("no-scope", invocationBuilder.get(String.class));
	}

	public static class AnnotatedApplicationTestPreparatorBundleActivator
		extends BaseTestPreparatorBundleActivator {

		@Override
		protected void prepareTest() throws Exception {
			Dictionary<String, Object> properties = new HashMapDictionary<>();

			properties.put("oauth2.scopechecker.type", "annotations");
			properties.put("auth.verifier.guest.allowed", false);

			registerJaxRsApplication(
				new TestAnnotatedApplication(), "annotated-guest-not-allowed",
				properties);

			properties = new HashMapDictionary<>();

			properties.put("oauth2.scopechecker.type", "annotations");
			properties.put("auth.verifier.guest.allowed", true);

			registerJaxRsApplication(
				new TestAnnotatedApplication(), "annotated-guest-allowed",
				properties);

			long defaultCompanyId = PortalUtil.getDefaultCompanyId();

			User user = UserTestUtil.getAdminUser(defaultCompanyId);

			createOAuth2Application(
				defaultCompanyId, user, "oauthTestApplication");
		}

	}

}