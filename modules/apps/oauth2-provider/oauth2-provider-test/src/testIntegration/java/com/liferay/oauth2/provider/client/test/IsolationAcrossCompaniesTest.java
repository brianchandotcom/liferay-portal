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
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.HashMapDictionary;

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
public class IsolationAcrossCompaniesTest extends BaseClientTestCase {

	@Deployment
	public static Archive<?> getDeployment() throws Exception {
		return BaseClientTestCase.getDeployment(
			IsolationAccrossCompaniesTestPreparator.class);
	}

	@Test
	public void test() throws Exception {
		WebTarget webTarget = getWebTarget("/annotated");

		String tokenString = getToken("oauthTestApplication", "host1.xyz");

		Invocation.Builder builder = authorize(
			webTarget.request(), tokenString);

		builder = builder.header("Host", "host1.xyz");

		Assert.assertEquals("everything.readonly", builder.get(String.class));

		builder = builder.header("Host", "host2.xyz");

		Response response = builder.get();

		Assert.assertEquals(400, response.getStatus());
	}

	public static class IsolationAccrossCompaniesTestPreparator
		extends BaseTestPreparatorBundleActivator {

		@Override
		protected void prepareTest() throws Exception {
			Dictionary<String, Object> properties = new HashMapDictionary<>();

			properties.put("oauth2.scopechecker.type", "annotations");

			registerJaxRsApplication(
				new TestAnnotatedApplication(), "annotated", properties);

			Company company1 = createCompany("host1");

			createOAuth2Application(
				company1.getCompanyId(),
				UserTestUtil.getAdminUser(company1.getCompanyId()),
				"oauthTestApplication");

			Company company2 = createCompany("host2");

			createOAuth2Application(
				company2.getCompanyId(),
				UserTestUtil.getAdminUser(company2.getCompanyId()),
				"oauthTestApplication");
		}

	}

}