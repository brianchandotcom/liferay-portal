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

import com.liferay.oauth2.provider.test.internal.TestSAPApplication;
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
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Carlos Sierra Andrés
 */
@RunAsClient
@RunWith(Arquillian.class)
public class SAPClientTest extends BaseClientTestCase {

	@Deployment
	public static Archive<?> getArchive() throws Exception {
		return BaseClientTestCase.getArchive(
			SAPTestPreparatorBundleActivator.class);
	}

	@Test
	public void test() throws Exception {
		WebTarget webTarget = getWebTarget("SAP/AUTHORIZED_OAUTH2_SAP");

		Invocation.Builder builder = authorize(
			webTarget.request(), getToken("oauthTestApplication"));

		Assert.assertEquals(true, builder.get(Boolean.class));

		webTarget = getWebTarget("SAP/CUSTOM_SAP");

		builder = authorize(
			webTarget.request(), getToken("oauthTestApplication"));

		Assert.assertEquals(false, builder.get(Boolean.class));

		webTarget = getWebTarget("CUSTOM_SAP/AUTHORIZED_OAUTH2_SAP");

		builder = authorize(
			webTarget.request(), getToken("oauthTestApplication"));

		Assert.assertEquals(false, builder.get(Boolean.class));

		webTarget = getWebTarget("CUSTOM_SAP/CUSTOM_SAP");

		builder = authorize(
			webTarget.request(), getToken("oauthTestApplication"));

		Assert.assertEquals(true, builder.get(Boolean.class));
	}

	public static class SAPTestPreparatorBundleActivator
		extends BaseTestPreparatorBundleActivator {

		@Override
		protected void prepareTest() throws Exception {
			long defaultCompanyId = PortalUtil.getDefaultCompanyId();

			User user = UserTestUtil.getAdminUser(defaultCompanyId);

			Dictionary<String, Object> properties = new HashMapDictionary<>();

			properties.put(
				"osgi.jaxrs.name", TestSAPApplication.class.getName());

			registerJaxRsApplication(
				new TestSAPApplication(), "SAP", properties);

			properties = new HashMapDictionary<>();

			properties.put("oauth2.service.access.policy.name", "CUSTOM_SAP");
			properties.put("osgi.jaxrs.name", "custom-sap-application");

			registerJaxRsApplication(
				new TestSAPApplication(), "CUSTOM_SAP", properties);

			createOAuth2Application(
				defaultCompanyId, user, "oauthTestApplication",
				Collections.singletonList("GET"));

			createServiceAccessProfile(
				user.getUserId(), "#is*", false, true, "CUSTOM_SAP");
		}

	}

}