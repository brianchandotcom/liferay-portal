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

package com.liferay.portal.vulcan.internal.jaxrs.container.request.filter.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.exception.NoSuchGroupException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceRegistration;

import java.io.IOException;

import java.net.HttpURLConnection;
import java.net.URL;

import java.nio.charset.StandardCharsets;

import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Application;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Javier Gamarra
 */
@RunWith(Arquillian.class)
public class TransactionContainerRequestFilterTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() {
		Registry registry = RegistryUtil.getRegistry();

		Map<String, Object> properties = new HashMap<>();

		properties.put("liferay.auth.verifier", true);
		properties.put("liferay.oauth2", false);
		properties.put("osgi.jaxrs.application.base", "/test-vulcan");
		properties.put(
			"osgi.jaxrs.extension.select", "(osgi.jaxrs.name=Liferay.Vulcan)");

		_serviceRegistration = registry.registerService(
			Application.class,
			new TransactionContainerRequestFilterTest.TestApplication(),
			properties);
	}

	@After
	public void tearDown() {
		_serviceRegistration.unregister();
	}

	@Test
	public void testFailureRequest() throws Exception {
		Group group = GroupTestUtil.addGroup();

		URL url = new URL(
			"http://localhost:8080/o/test-vulcan/failure/" +
				group.getGroupId());

		Assert.assertEquals(500, _getHttpResponseCode(url));

		Assert.assertNotNull(
			GroupLocalServiceUtil.getGroup(group.getGroupId()));
	}

	@Test(expected = NoSuchGroupException.class)
	public void testSuccessRequest() throws Exception {
		Group group = GroupTestUtil.addGroup();

		URL url = new URL(
			"http://localhost:8080/o/test-vulcan/success/" +
				group.getGroupId());

		Assert.assertEquals(204, _getHttpResponseCode(url));

		Assert.assertNull(GroupLocalServiceUtil.getGroup(group.getGroupId()));
	}

	public static class TestApplication extends Application {

		@Override
		public Set<Object> getSingletons() {
			return Collections.singleton(this);
		}

		@DELETE
		@Path("/failure/{siteId}")
		public void testFailure(@PathParam("siteId") long siteId)
			throws Exception {

			GroupLocalServiceUtil.deleteGroup(siteId);

			throw new RuntimeException();
		}

		@DELETE
		@Path("/success/{siteId}")
		public void testSuccess(@PathParam("siteId") long siteId)
			throws Exception {

			GroupLocalServiceUtil.deleteGroup(siteId);
		}

	}

	private int _getHttpResponseCode(URL url) throws IOException {
		HttpURLConnection httpURLConnection =
			(HttpURLConnection)url.openConnection();

		Base64.Encoder encoder = Base64.getEncoder();

		String basicAuth = encoder.encodeToString(
			"test@liferay.com:test".getBytes(StandardCharsets.UTF_8));

		httpURLConnection.setRequestMethod("DELETE");
		httpURLConnection.setRequestProperty(
			"Authorization", "Basic " + basicAuth);

		return httpURLConnection.getResponseCode();
	}

	private ServiceRegistration<Application> _serviceRegistration;

}