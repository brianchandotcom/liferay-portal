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
import com.liferay.portal.test.log.CaptureAppender;
import com.liferay.portal.test.log.Log4JLoggerTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.internal.test.util.URLConnectionUtil;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceRegistration;

import java.io.IOException;

import java.net.HttpURLConnection;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Application;

import org.apache.log4j.Level;

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

	@Test(expected = NoSuchGroupException.class)
	public void testCommit() throws Exception {
		Group group = GroupTestUtil.addGroup();

		Assert.assertEquals(
			204,
			_getResponseCode(
				"http://localhost:8080/o/test-vulcan/commit/" +
					group.getGroupId()));
		Assert.assertNull(GroupLocalServiceUtil.getGroup(group.getGroupId()));
	}

	@Test
	public void testRollback() throws Exception {
		Group group = GroupTestUtil.addGroup();

		try (CaptureAppender captureAppender =
				Log4JLoggerTestUtil.configureLog4JLogger(
					_CLASS_NAME_EXCEPTION_MAPPER, Level.ERROR)) {

			Assert.assertEquals(
				500,
				_getResponseCode(
					"http://localhost:8080/o/test-vulcan/rollback/" +
						group.getGroupId()));
			Assert.assertNotNull(
				GroupLocalServiceUtil.getGroup(group.getGroupId()));
		}
	}

	public static class TestApplication extends Application {

		@Override
		public Set<Object> getSingletons() {
			return Collections.singleton(this);
		}

		@DELETE
		@Path("/commit/{siteId}")
		public void testCommit(@PathParam("siteId") long siteId)
			throws Exception {

			GroupLocalServiceUtil.deleteGroup(siteId);
		}

		@DELETE
		@Path("/rollback/{siteId}")
		public void testRollback(@PathParam("siteId") long siteId)
			throws Exception {

			GroupLocalServiceUtil.deleteGroup(siteId);

			throw new RuntimeException();
		}

	}

	private int _getResponseCode(String urlString) throws IOException {
		HttpURLConnection httpURLConnection =
			(HttpURLConnection)URLConnectionUtil.createURLConnection(urlString);

		httpURLConnection.setRequestMethod("DELETE");

		return httpURLConnection.getResponseCode();
	}

	private static final String _CLASS_NAME_EXCEPTION_MAPPER =
		"com.liferay.portal.vulcan.internal.jaxrs.exception.mapper." +
			"ExceptionMapper";

	private ServiceRegistration<Application> _serviceRegistration;

}