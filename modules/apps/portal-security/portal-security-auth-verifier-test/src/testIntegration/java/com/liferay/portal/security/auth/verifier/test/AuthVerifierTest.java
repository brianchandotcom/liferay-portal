/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.auth.verifier.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.security.access.control.AccessControlThreadLocal;
import com.liferay.portal.kernel.security.auth.AccessControlContext;
import com.liferay.portal.kernel.security.auth.AuthException;
import com.liferay.portal.kernel.security.auth.verifier.AuthVerifier;
import com.liferay.portal.kernel.security.auth.verifier.AuthVerifierResult;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import java.net.URL;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;
import java.util.Properties;
import java.util.function.Supplier;

import javax.servlet.Servlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

		_registerServletContextHelper(
			"auth-verifier-guest-allowed-false-test",
			HashMapDictionaryBuilder.<String, Object>put(
				JaxrsWhiteboardConstants.JAX_RS_NAME, "guest-no-allowed"
			).put(
				"auth-verifier-guest-allowed-test-servlet-context-helper", true
			).put(
				"auth.verifier.guest.allowed", false
			).build());

		_registerServletContextHelper(
			"auth-verifier-guest-allowed-true-test",
			HashMapDictionaryBuilder.<String, Object>put(
				JaxrsWhiteboardConstants.JAX_RS_NAME, "guest-allowed"
			).put(
				"auth-verifier-guest-allowed-test-servlet-context-helper", true
			).put(
				"auth.verifier.guest.allowed", true
			).build());

		_registerServletContextHelper(
			"auth-verifier-guest-allowed-default-test",
			HashMapDictionaryBuilder.<String, Object>put(
				JaxrsWhiteboardConstants.JAX_RS_NAME, "guest-default"
			).put(
				"auth-verifier-guest-allowed-test-servlet-context-helper", true
			).build());

		_registerServlet(
			HashMapDictionaryBuilder.<String, Object>put(
				HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_SELECT,
				"(auth-verifier-guest-allowed-test-servlet-context-helper=true)"
			).put(
				HttpWhiteboardConstants.HTTP_WHITEBOARD_SERVLET_NAME,
				"cxf-servlet"
			).put(
				HttpWhiteboardConstants.HTTP_WHITEBOARD_SERVLET_PATTERN,
				"/guestAllowed"
			).build(),
			GuestAllowedHttpServlet::new);

		_registerServletContextHelper(
			"auth-verifier-filter-tracker-enabled-test",
			HashMapDictionaryBuilder.<String, Object>put(
				JaxrsWhiteboardConstants.JAX_RS_NAME, "filter-enabled"
			).put(
				"auth-verifier-tracker-test-servlet-context-helper", true
			).put(
				"auth.verifier.guest.allowed", true
			).build());

		_registerServletContextHelper(
			"auth-verifier-filter-tracker-disabled-test",
			HashMapDictionaryBuilder.<String, Object>put(
				"auth-verifier-tracker-test-servlet-context-helper", true
			).put(
				"liferay.auth.verifier", false
			).build());

		_registerServletContextHelper(
			"auth-verifier-filter-tracker-default-test",
			HashMapDictionaryBuilder.<String, Object>put(
				"auth-verifier-tracker-test-servlet-context-helper", true
			).build());

		_registerServlet(
			HashMapDictionaryBuilder.<String, Object>put(
				HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_SELECT,
				"(auth-verifier-tracker-test-servlet-context-helper=true)"
			).put(
				HttpWhiteboardConstants.HTTP_WHITEBOARD_SERVLET_NAME,
				"cxf-servlet"
			).put(
				HttpWhiteboardConstants.HTTP_WHITEBOARD_SERVLET_PATTERN,
				"/remoteUser"
			).build(),
			RemoteUserHttpServlet::new);

		_registerServletContextHelper(
			"auth-verifier-filter-tracker-remote-access-test",
			HashMapDictionaryBuilder.<String, Object>put(
				JaxrsWhiteboardConstants.JAX_RS_NAME, "filter-enabled"
			).put(
				"auth-verifier-tracker-test-servlet-context-helper", true
			).build());

		_registerServlet(
			HashMapDictionaryBuilder.<String, Object>put(
				HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_SELECT,
				"auth-verifier-filter-tracker-remote-access-test"
			).put(
				HttpWhiteboardConstants.HTTP_WHITEBOARD_SERVLET_NAME,
				"cxf-servlet"
			).put(
				HttpWhiteboardConstants.HTTP_WHITEBOARD_SERVLET_PATTERN,
				"/remoteAccess"
			).build(),
			RemoteAccessHttpServlet::new);

		_registerServletContextHelper(
			"auth-verifier-filter-override-matched-test",
			HashMapDictionaryBuilder.<String, Object>put(
				JaxrsWhiteboardConstants.JAX_RS_NAME,
				"auth-verifier-filter-override-matched"
			).put(
				"auth-verifier-matched-test-auth-verifier-filter-helper", true
			).put(
				"auth.verifier.auth.verifier." +
					"AuthVerifierTest$TestAuthVerifier.urls.includes",
				"*"
			).put(
				"auth.verifier.guest.allowed", true
			).build());

		_registerServletContextHelper(
			"auth-verifier-filter-override-not-matched-test",
			HashMapDictionaryBuilder.<String, Object>put(
				JaxrsWhiteboardConstants.JAX_RS_NAME,
				"auth-verifier-filter-override-not-matched"
			).put(
				"auth-verifier-matched-test-auth-verifier-filter-helper", true
			).put(
				"auth.verifier.auth.verifier." +
					"AuthVerifierTest$TestAuthVerifier.urls.includes",
				"/wrongPath"
			).put(
				"auth.verifier.guest.allowed", true
			).build());

		_registerServletContextHelper(
			"auth-verifier-filter-override-missing-test",
			HashMapDictionaryBuilder.<String, Object>put(
				JaxrsWhiteboardConstants.JAX_RS_NAME,
				"auth-verifier-filter-override-missing"
			).put(
				"auth-verifier-matched-test-auth-verifier-filter-helper", true
			).put(
				"auth.verifier.guest.allowed", true
			).build());

		_registerServlet(
			HashMapDictionaryBuilder.<String, Object>put(
				HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_SELECT,
				"(auth-verifier-matched-test-auth-verifier-filter-helper=true)"
			).put(
				HttpWhiteboardConstants.HTTP_WHITEBOARD_SERVLET_NAME,
				"cxf-servlet"
			).put(
				HttpWhiteboardConstants.HTTP_WHITEBOARD_SERVLET_PATTERN, "/*"
			).build(),
			AuthVerifierMatchedHttpServlet::new);

		_registerAuthVerifier(
			new TestAuthVerifier(),
			HashMapDictionaryBuilder.<String, Object>put(
				"auth.verifier.AuthVerifierTest$TestAuthVerifier.urls.includes",
				"/authVerifierMatched,/attemptMatchRelativeToContextPath"
			).build());
	}

	@AfterClass
	public static void tearDownClass() {
		for (ServiceRegistration<?> serviceRegistration :
				_serviceRegistrations) {

			try {
				serviceRegistration.unregister();
			}
			catch (Exception exception) {
			}
		}
	}

	@Test
	public void testAllowGuest() throws Exception {
		URL url = new URL(
			"http://localhost:8080/o/auth-verifier-guest-allowed-false-test" +
				"/guestAllowed");

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				"portal_web.docroot.errors.code_jsp", LoggerTestUtil.WARN);
			InputStream inputStream = url.openStream()) {

			Assert.fail();
		}
		catch (IOException ioException) {
			String message = ioException.getMessage();

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
	public void testAuthVerifierDoesNotMatchRelativeToContextPath()
		throws Exception {

		URL url = new URL(
			"http://localhost:8080/o" +
				"/auth-verifier-filter-override-missing-test" +
					"/attemptMatchRelativeToContextPath");

		try (InputStream inputStream = url.openStream()) {
			Assert.assertEquals("not-matched", StringUtil.read(inputStream));
		}
	}

	@Test
	public void testAuthVerifierFilterOverridesAuthVerifierURLsIncludes()
		throws Exception {

		URL url = new URL(
			"http://localhost:8080/o" +
				"/auth-verifier-filter-override-not-matched-test" +
					"/authVerifierMatched");

		try (InputStream inputStream = url.openStream()) {
			Assert.assertEquals("not-matched", StringUtil.read(inputStream));
		}

		url = new URL(
			"http://localhost:8080/o" +
				"/auth-verifier-filter-override-matched-test" +
					"/authVerifierNotMatched");

		try (InputStream inputStream = url.openStream()) {
			Assert.assertEquals("matched", StringUtil.read(inputStream));
		}
	}

	@Test
	public void testAuthVerifierNotMatched() throws Exception {
		URL url = new URL(
			"http://localhost:8080/o" +
				"/auth-verifier-filter-override-missing-test" +
					"/authVerifierNotMatched");

		try (InputStream inputStream = url.openStream()) {
			Assert.assertEquals("not-matched", StringUtil.read(inputStream));
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

	@Test
	public void testServletContextRootResourceMatchedByWildcard()
		throws Exception {

		URL url = new URL(
			"http://localhost:8080/o" +
				"/auth-verifier-filter-override-matched-test");

		try (InputStream inputStream = url.openStream()) {
			Assert.assertEquals("matched", StringUtil.read(inputStream));
		}
	}

	public static class AuthVerifierMatchedHttpServlet extends HttpServlet {

		@Override
		protected void doGet(
				HttpServletRequest httpServletRequest,
				HttpServletResponse httpServletResponse)
			throws IOException {

			PrintWriter printWriter = httpServletResponse.getWriter();

			boolean matched = GetterUtil.getBoolean(
				httpServletRequest.getAttribute("MATCHED"));

			if (matched) {
				printWriter.write("matched");
			}
			else {
				printWriter.write("not-matched");
			}
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

			if (Validator.isNull(httpServletRequest.getRemoteUser())) {
				printWriter.write("no-remote-user");
			}
			else {
				printWriter.write("remote-user-set");
			}
		}

	}

	public static class TestAuthVerifier implements AuthVerifier {

		@Override
		public String getAuthType() {
			return HttpServletRequest.FORM_AUTH;
		}

		@Override
		public AuthVerifierResult verify(
				AccessControlContext accessControlContext,
				Properties properties)
			throws AuthException {

			HttpServletRequest httpServletRequest =
				accessControlContext.getRequest();

			httpServletRequest.setAttribute("MATCHED", Boolean.TRUE);

			return new AuthVerifierResult();
		}

	}

	private static void _registerAuthVerifier(
		AuthVerifier authVerifier, Dictionary<String, Object> properties) {

		_serviceRegistrations.add(
			_bundleContext.registerService(
				AuthVerifier.class, authVerifier, properties));
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