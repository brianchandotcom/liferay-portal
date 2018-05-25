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
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.PortalUtil;

import java.util.Collections;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONObject;

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
public class JsonWebServiceTest extends BaseClientTest {

	@Deployment
	public static Archive<?> getDeployment() throws Exception {
		return BaseClientTest.getDeployment(JsonWebServiceTestPreparator.class);
	}

	@Test
	public void test() throws Exception {
		WebTarget jsonWebTarget = getJsonWebTarget(
			"company", "get-company-by-virtual-host");

		Invocation.Builder invocationBuilder = jsonWebTarget.request();

		MultivaluedMap<String, String> formData = new MultivaluedHashMap<>();

		formData.putSingle("virtualHost", "testcompany.xyz");

		Assert.assertEquals(
			403, invocationBuilder.post(Entity.form(formData)).getStatus());

		String tokenString = getToken(
			"oauthTestApplicationRO", null,
			getResourceOwnerPassword("test@liferay.com", "test"),
			this::parseTokenString);

		invocationBuilder = authorize(jsonWebTarget.request(), tokenString);

		Response response = invocationBuilder.post(Entity.form(formData));

		JSONObject responseJSONObject = new JSONObject(
			response.readEntity(String.class));

		Assert.assertEquals(
			"testcompany", responseJSONObject.getString("webId"));

		jsonWebTarget = getJsonWebTarget("region", "add-region");

		invocationBuilder = authorize(jsonWebTarget.request(), tokenString);

		formData = new MultivaluedHashMap<>();

		formData.putSingle("countryId", "0");
		formData.putSingle("regionCode", "'aRegionCode'");
		formData.putSingle("name", "'aName'");
		formData.putSingle("active", "true");

		response = invocationBuilder.post(Entity.form(formData));

		Assert.assertEquals(403, response.getStatus());

		invocationBuilder = authorize(
			jsonWebTarget.request(),
			getToken(
				"oauthTestApplicationRW", null,
				getResourceOwnerPassword("test@liferay.com", "test"),
				this::parseTokenString));

		response = invocationBuilder.post(Entity.form(formData));

		String responseString = response.readEntity(String.class);

		Assert.assertTrue(responseString.contains("No Country exists with"));
	}

	public static class JsonWebServiceTestPreparator
		extends BaseTestPreparatorBundleActivator {

		@Override
		protected void prepareTest() throws Exception {
			long defaultCompanyId = PortalUtil.getDefaultCompanyId();

			User user = UserTestUtil.getAdminUser(defaultCompanyId);

			createCompany("testcompany");

			createOAuth2Application(
				defaultCompanyId, user, "oauthTestApplicationRO",
				Collections.singletonList("everything.readonly"));

			createOAuth2Application(
				defaultCompanyId, user, "oauthTestApplicationRW",
				Collections.singletonList("everything"));
		}

	}

}