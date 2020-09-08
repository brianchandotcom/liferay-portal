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
import com.liferay.portal.json.JSONObjectImpl;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.test.log.CaptureAppender;
import com.liferay.portal.test.log.Log4JLoggerTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Arrays;
import java.util.Collections;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.apache.log4j.Level;

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
public class JsonWebServiceTest extends BaseClientTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void test() throws Exception {
		WebTarget webTarget = getJsonWebTarget(
			"company", "get-company-by-virtual-host");

		Invocation.Builder invocationBuilder = webTarget.request();

		MultivaluedMap<String, String> formData = new MultivaluedHashMap<>();

		formData.putSingle("virtualHost", "testcompany.xyz");

		try (CaptureAppender captureAppender =
				Log4JLoggerTestUtil.configureLog4JLogger(
					"portal_web.docroot.errors.code_jsp", Level.WARN)) {

			Assert.assertEquals(
				403,
				invocationBuilder.post(
					Entity.form(formData)
				).getStatus());
		}

		String tokenString = getToken(
			"oauthTestApplicationRO", null,
			getResourceOwnerPasswordBiFunction("test@liferay.com", "test"),
			this::parseTokenString);

		invocationBuilder = authorize(webTarget.request(), tokenString);

		Response response = invocationBuilder.post(Entity.form(formData));

		JSONObject jsonObject = new JSONObjectImpl(
			response.readEntity(String.class));

		Assert.assertEquals("testcompany", jsonObject.getString("webId"));

		webTarget = getJsonWebTarget("region", "add-region");

		invocationBuilder = authorize(webTarget.request(), tokenString);

		formData = new MultivaluedHashMap<>();

		formData.putSingle("active", "true");
		formData.putSingle("countryId", "0");
		formData.putSingle("name", "'aName'");
		formData.putSingle("regionCode", "'aRegionCode'");

		try (CaptureAppender captureAppender =
				Log4JLoggerTestUtil.configureLog4JLogger(
					"portal_web.docroot.errors.code_jsp", Level.WARN)) {

			response = invocationBuilder.post(Entity.form(formData));

			Assert.assertEquals(403, response.getStatus());
		}

		String token = getToken(
			"oauthTestApplicationRW", null,
			getResourceOwnerPasswordBiFunction(
				"test@liferay.com", "test", "everything.write"),
			this::parseTokenString);

		invocationBuilder = authorize(webTarget.request(), token);

		response = invocationBuilder.post(Entity.form(formData));

		Assert.assertEquals(404, response.getStatus());

		webTarget = getJsonWebTarget("company", "get-company-by-virtual-host");

		invocationBuilder = authorize(webTarget.request(), token);

		formData = new MultivaluedHashMap<>();

		formData.putSingle("virtualHost", "testcompany.xyz");

		try (CaptureAppender captureAppender =
				Log4JLoggerTestUtil.configureLog4JLogger(
					"portal_web.docroot.errors.code_jsp", Level.WARN)) {

			Assert.assertEquals(
				403,
				invocationBuilder.post(
					Entity.form(formData)
				).getStatus());
		}
	}

	public static class JsonWebServiceTestPreparatorBundleActivator
		extends BaseTestPreparatorBundleActivator {

		@Override
		protected void prepareTest() throws Exception {
			long defaultCompanyId = PortalUtil.getDefaultCompanyId();

			User user = UserTestUtil.getAdminUser(defaultCompanyId);

			createCompany("testcompany");

			createOAuth2Application(
				defaultCompanyId, user, "oauthTestApplicationRO",
				Collections.singletonList("everything.read"));

			createOAuth2Application(
				defaultCompanyId, user, "oauthTestApplicationRW",
				Arrays.asList("everything.read", "everything.write"));
		}

	}

	@Override
	protected BundleActivator getBundleActivator() {
		return new JsonWebServiceTestPreparatorBundleActivator();
	}

}