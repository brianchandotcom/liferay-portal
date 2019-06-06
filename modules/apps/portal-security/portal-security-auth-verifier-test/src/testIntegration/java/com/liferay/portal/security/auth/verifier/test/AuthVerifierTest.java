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

package com.liferay.portal.security.auth.verifier.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.security.access.control.AccessControlThreadLocal;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.test.log.CaptureAppender;
import com.liferay.portal.test.log.Log4JLoggerTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import java.net.URL;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;
import java.util.function.Supplier;

import javax.servlet.Servlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Level;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.PrototypeServiceFactory;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.http.context.ServletContextHelper;
import org.osgi.service.http.whiteboard.HttpWhiteboardConstants;
import org.osgi.service.jaxrs.whiteboard.JaxrsWhiteboardConstants;

/**
 * @author Marta Medio
 */
@RunWith(Arquillian.class)
public class AuthVerifierTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() {
		Bundle bundle = FrameworkUtil.getBundle(AuthVerifierTest.class);

		_bundleContext = bundle.getBundleContext();

		Dictionary<String, Object> properties = new HashMapDictionary<>();

		properties.put(
			JaxrsWhiteboardConstants.JAX_RS_NAME, "guest-no-allowed");
		properties.put("auth.verifier.guest.allowed", false);
		properties.put(
			"auth-verifier-guest-allowed-test-servlet-context-helper", true);

		_registerServletContextHelper(
			"auth-verifier-guest-allowed-false-test", properties);

		properties = new HashMapDictionary<>();

		properties.put(JaxrsWhiteboardConstants.JAX_RS_NAME, "guest-allowed");
		properties.put("auth.verifier.guest.allowed", true);
		properties.put(
			"auth-verifier-guest-allowed-test-servlet-context-helper", true);

		_registerServletContextHelper(
			"auth-verifier-guest-allowed-true-test", properties);

		properties = new HashMapDictionary<>();

		properties.put(JaxrsWhiteboardConstants.JAX_RS_NAME, "guest-default");
		properties.put(
			"auth-verifier-guest-allowed-test-servlet-context-helper", true);

		_registerServletContextHelper(
			"auth-verifier-guest-allowed-default-test", properties);

		properties = new HashMapDictionary<>();

		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_SERVLET_NAME,
			"cxf-servlet");
		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_SERVLET_PATTERN,
			"/guestAllowed");
		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_SELECT,
			"(auth-verifier-guest-allowed-test-servlet-context-helper=true)");

		_registerServlet(properties, GuestAllowedHttpServlet::new);

		properties = new HashMapDictionary<>();

		properties.put(JaxrsWhiteboardConstants.JAX_RS_NAME, "filter-enabled");
		properties.put("auth.verifier.guest.allowed", true);
		properties.put(
			"auth-verifier-tracker-test-servlet-context-helper", true);

		_registerServletContextHelper(
			"auth-verifier-filter-tracker-enabled-test", properties);

		properties = new HashMapDictionary<>();

		properties.put(
			"auth-verifier-tracker-test-servlet-context-helper", true);
		properties.put("liferay.auth.verifier", false);

		_registerServletContextHelper(
			"auth-verifier-filter-tracker-disabled-test", properties);

		properties = new HashMapDictionary<>();

		properties.put(
			"auth-verifier-tracker-test-servlet-context-helper", true);

		_registerServletContextHelper(
			"auth-verifier-filter-tracker-default-test", properties);

		properties = new HashMapDictionary<>();

		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_SELECT,
			"(auth-verifier-tracker-test-servlet-context-helper=true)");
		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_SERVLET_NAME,
			"cxf-servlet");
		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_SERVLET_PATTERN,
			"/remoteUser");

		_registerServlet(properties, RemoteUserHttpServlet::new);

		properties = new HashMapDictionary<>();

		properties.put(JaxrsWhiteboardConstants.JAX_RS_NAME, "filter-enabled");
		properties.put(
			"auth-verifier-tracker-test-servlet-context-helper", true);

		_registerServletContextHelper(
			"auth-verifier-filter-tracker-remote-access-test", properties);

		properties = new HashMapDictionary<>();

		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_SERVLET_NAME,
			"cxf-servlet");
		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_SERVLET_PATTERN,
			"/remoteAccess");
		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_SELECT,
			"auth-verifier-filter-tracker-remote-access-test");

		_registerServlet(properties, RemoteAccessHttpServlet::new);
	}

	@AfterClass
	public static void tearDownClass() {
		for (ServiceRegistration<?> serviceRegistration :
				_serviceRegistrations) {

			try {
				serviceRegistration.unregister();
			}
			catch (Exception e) {
			}
		}
	}

	@Test
	public void testAllowGuest() throws Exception {
		URL url = new URL(
			"http://localhost:8080/o/auth-verifier-guest-allowed-false-test" +
				"/guestAllowed");

		try (CaptureAppender captureAppender =
				Log4JLoggerTestUtil.configureLog4JLogger(
					"portal_web.docroot.errors.code_jsp", Level.WARN);
			InputStream inputStream = url.openStream()) {

			Assert.fail();
		}
		catch (IOException ioe) {
			String message = ioe.getMessage();

			Assert.assertTrue(
				message.startsWith("Server returned HTTP response code: 403"));
		}

		url = new URL(
			"http://localhost:8080/o/auth-verifier-guest-allowed-true-test" +
				"/guestAllowed");

		try (InputStream inputStream = url.openStream()) {
			Assert.assertEquals("guest-allowed", StringUtil.read(inputStream));
		}

		url = new URL(
			"http://localhost:8080/o/auth-verifier-guest-allowed-default-test" +
				"/guestAllowed");

		try (InputStream inputStream = url.openStream()) {
			Assert.assertEquals("guest-allowed", StringUtil.read(inputStream));
		}
	}

	@Test
	public void testRemoteAccess() throws Exception {
		URL url = new URL(
			"http://localhost:8080/o/auth-verifier-filter-tracker-remote-" +
				"access-test/remoteAccess");

		try (InputStream inputStream = url.openStream()) {
			Assert.assertEquals("true", StringUtil.read(inputStream));
		}
	}

	@Test
	public void testRemoteUser() throws Exception {
		URL url = new URL(
			"http://localhost:8080/o/auth-verifier-filter-tracker-enabled-" +
				"test/remoteUser");

		try (InputStream inputStream = url.openStream()) {
			Assert.assertEquals(
				"remote-user-set", StringUtil.read(inputStream));
		}

		url = new URL(
			"http://localhost:8080/o/auth-verifier-filter-tracker-disabled-" +
				"test/remoteUser");

		try (InputStream inputStream = url.openStream()) {
			Assert.assertEquals("no-remote-user", StringUtil.read(inputStream));
		}

		url = new URL(
			"http://localhost:8080/o/auth-verifier-filter-tracker-default-" +
				"test/remoteUser");

		try (InputStream inputStream = url.openStream()) {
			Assert.assertEquals(
				"remote-user-set", StringUtil.read(inputStream));
		}
	}

	public static class GuestAllowedHttpServlet extends HttpServlet {

		@Override
		protected void doGet(
				HttpServletRequest httpServletRequest,
				HttpServletResponse httpServletResponse)
			throws IOException {

			PrintWriter printWriter = httpServletResponse.getWriter();

			printWriter.write("guest-allowed");
		}

	}

	public static class RemoteAccessHttpServlet extends HttpServlet {

		@Override
		protected void doGet(
				HttpServletRequest httpServletRequest,
				HttpServletResponse httpServletResponse)
			throws IOException {

			PrintWriter printWriter = httpServletResponse.getWriter();

			printWriter.write(
				String.valueOf(AccessControlThreadLocal.isRemoteAccess()));
		}

	}

	public static class RemoteUserHttpServlet extends HttpServlet {

		@Override
		protected void doGet(
				HttpServletRequest httpServletRequest,
				HttpServletResponse httpServletResponse)
			throws IOException {

			PrintWriter printWriter = httpServletResponse.getWriter();

			String remoteUser = httpServletRequest.getRemoteUser();

			if (Validator.isNull(remoteUser)) {
				printWriter.write("no-remote-user");
			}
			else {
				printWriter.write("remote-user-set");
			}
		}

	}

	private static void _registerServlet(
		Dictionary<String, Object> properties, Supplier<Servlet> supplier) {

		_serviceRegistrations.add(
			_bundleContext.registerService(
				Servlet.class,
				new PrototypeServiceFactory<Servlet>() {

					@Override
					public Servlet getService(
						Bundle bundle,
						ServiceRegistration<Servlet> serviceRegistration) {

						return supplier.get();
					}

					@Override
					public void ungetService(
						Bundle bundle,
						ServiceRegistration<Servlet> serviceRegistration,
						Servlet servlet) {
					}

				},
				properties));
	}

	private static void _registerServletContextHelper(
		String servletContextName, Dictionary<String, Object> properties) {

		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_NAME,
			servletContextName);
		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_PATH,
			"/" + servletContextName);

		_serviceRegistrations.add(
			_bundleContext.registerService(
				ServletContextHelper.class,
				new ServletContextHelper(_bundleContext.getBundle()) {
				},
				properties));
	}

	private static BundleContext _bundleContext;
	private static final List<ServiceRegistration<?>> _serviceRegistrations =
		new ArrayList<>();

}