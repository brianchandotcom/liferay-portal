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
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.internal.test.util.URLConnectionUtil;

import java.io.IOException;

import java.net.HttpURLConnection;

import java.util.Collections;
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

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;

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
		Bundle bundle = FrameworkUtil.getBundle(
			TransactionContainerRequestFilterTest.class);

		BundleContext bundleContext = bundle.getBundleContext();

		_serviceRegistration = bundleContext.registerService(
			Application.class,
			new TransactionContainerRequestFilterTest.TestApplication(),
			HashMapDictionaryBuilder.<String, Object>put(
				"liferay.auth.verifier", true
			).put(
				"liferay.oauth2", false
			).put(
				"osgi.jaxrs.application.base", "/test-vulcan"
			).put(
				"osgi.jaxrs.extension.select",
				"(osgi.jaxrs.name=Liferay.Vulcan)"
			).build());
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

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				_CLASS_NAME_EXCEPTION_MAPPER, LoggerTestUtil.ERROR)) {

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