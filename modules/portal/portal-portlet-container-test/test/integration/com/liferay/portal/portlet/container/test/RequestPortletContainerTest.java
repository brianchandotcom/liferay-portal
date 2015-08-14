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

package com.liferay.portal.portlet.container.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.TransactionalTestRule;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.ReflectionUtil;
import com.liferay.portal.security.auth.AuthTokenWhitelistUtil;
import com.liferay.portal.test.log.CaptureAppender;
import com.liferay.portal.test.log.Log4JLoggerTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.PortletURLFactoryUtil;
import com.liferay.portlet.PortletURLImpl;
import com.liferay.portlet.SecurityPortletContainerWrapper;
import com.liferay.util.Encryptor;

import java.io.IOException;
import java.io.PrintWriter;

import java.lang.reflect.Field;

import java.util.Collections;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.portlet.PortletContext;
import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.portlet.WindowState;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Level;
import org.apache.log4j.spi.LoggingEvent;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Raymond Augé
 */
@RunWith(Arquillian.class)
public class RequestPortletContainerTest extends BasePortletContainerTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), TransactionalTestRule.INSTANCE);

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_testPortlet = new TestPortlet(_map);
	}

	@Test
	public void testActionRequest_AUTH_TOKEN_CHECK_ENABLED() throws Exception {
		Field field = ReflectionUtil.getDeclaredField(
			PropsValues.class, "AUTH_TOKEN_CHECK_ENABLED");

		Object value = field.get(null);

		try {
			field.set(null, Boolean.FALSE.booleanValue());

			setUpPortlet(_testPortlet, _properties, _TEST_PORTLET_ID);

			HttpServletRequest httpServletRequest = getHttpServletRequest();

			PortletURL portletURL = new PortletURLImpl(
				httpServletRequest, _TEST_PORTLET_ID, layout.getPlid(),
				PortletRequest.ACTION_PHASE);

			Map<String, List<String>> responseMap = request(
				portletURL.toString());

			Assert.assertEquals("200", getString(responseMap, "code"));
			Assert.assertTrue(_map.containsKey("processAction"));
		}
		finally {
			field.set(null, value);
		}
	}

	@Test
	public void testActionRequest_AUTH_TOKEN_IGNORE_ORIGINS() throws Exception {
		Field field = ReflectionUtil.getDeclaredField(
			PropsValues.class, "AUTH_TOKEN_IGNORE_ORIGINS");

		Object value = field.get(null);

		try {
			field.set(
				null,
				new String[] {SecurityPortletContainerWrapper.class.getName()});

			AuthTokenWhitelistUtil.resetOriginCSRFWhitelist();

			setUpPortlet(_testPortlet, _properties, _TEST_PORTLET_ID);

			HttpServletRequest httpServletRequest = getHttpServletRequest();

			PortletURL portletURL = new PortletURLImpl(
				httpServletRequest, _TEST_PORTLET_ID, layout.getPlid(),
				PortletRequest.ACTION_PHASE);

			Map<String, List<String>> responseMap = request(
				portletURL.toString());

			Assert.assertEquals("200", getString(responseMap, "code"));
			Assert.assertTrue(_map.containsKey("processAction"));
		}
		finally {
			field.set(null, value);

			AuthTokenWhitelistUtil.resetOriginCSRFWhitelist();
		}
	}

	@Test
	public void testActionRequest_AUTH_TOKEN_IGNORE_PORTLETS()
		throws Exception {

		Field field = ReflectionUtil.getDeclaredField(
			PropsValues.class, "AUTH_TOKEN_IGNORE_PORTLETS");

		Object value = field.get(null);

		try {
			field.set(null, new String[] {_TEST_PORTLET_ID});

			AuthTokenWhitelistUtil.resetPortletCSRFWhitelist();

			setUpPortlet(_testPortlet, _properties, _TEST_PORTLET_ID);

			HttpServletRequest httpServletRequest = getHttpServletRequest();

			PortletURL portletURL = new PortletURLImpl(
				httpServletRequest, _TEST_PORTLET_ID, layout.getPlid(),
				PortletRequest.ACTION_PHASE);

			Map<String, List<String>> responseMap = request(
				portletURL.toString());

			Assert.assertEquals("200", getString(responseMap, "code"));
			Assert.assertTrue(_map.containsKey("processAction"));
		}
		finally {
			field.set(null, value);

			AuthTokenWhitelistUtil.resetPortletCSRFWhitelist();
		}
	}

	@Test
	public void testActionRequest_initParam() throws Exception {
		_properties.put(
			"javax.portlet.init-param.check-auth-token",
			Boolean.FALSE.toString());

		setUpPortlet(_testPortlet, _properties, _TEST_PORTLET_ID);

		HttpServletRequest request = getHttpServletRequest();

		PortletURL portletURL = new PortletURLImpl(
			request, _TEST_PORTLET_ID, layout.getPlid(),
			PortletRequest.ACTION_PHASE);

		Map<String, List<String>> responseMap = request(portletURL.toString());

		Assert.assertEquals("200", getString(responseMap, "code"));
		Assert.assertTrue(_map.containsKey("processAction"));
	}

	/**
	 * This test's action should fail because:
	 * <ol><li>it does not provide a <code>check-auth-token</code> init
	 * parameter equal to <code>true</code></li><li>the portal property
	 * <code>auth.token.check.enabled</code> is <code>true</code> by default
	 * </li><li>it did not provide a request parameter of
	 * <code>p_auth_secret</code> with a value which can be found in the portal
	 * property <code>auth.token.shared.secret</code></li><li>it's origin
	 * PortletContainer class impl was not whitelisted in the portal property
	 * <code>auth.token.ignore.origins</code></li><li>it was not whitelisted by
	 * it's portlet id being listed in the portal property
	 * <code>auth.token.ignore.portlets</code></li><li>it did not provide a
	 * parameter of <code>struts_action</code> who's value was registered on a
	 * service property equal to <code>auth.token.ignore.actions</code> AND is
	 * actually a valid struts action</li><li>it did not provide a request
	 * parameter of <code>p_auth</code> contain a valid CSRF
	 * token generated by the portal and stored in the portal session</li><li>
	 * it did not provide a request header of <code>X-CSRF-Token</code> contain
	 * a valid CSRF token generated by the portal and stored in the portal
	 * session</li>
	 */
	@Test
	public void testActionRequest_noTokens() throws Exception {
		setUpPortlet(_testPortlet, _properties, _TEST_PORTLET_ID);

		HttpServletRequest request = getHttpServletRequest();

		PortletURL portletURL = new PortletURLImpl(
			request, _TEST_PORTLET_ID, layout.getPlid(),
			PortletRequest.ACTION_PHASE);

		String url = portletURL.toString();

		try (CaptureAppender captureAppender =
			Log4JLoggerTestUtil.configureLog4JLogger(
				SecurityPortletContainerWrapper.class.getName(), Level.WARN)) {

			Map<String, List<String>> responseMap = request(url);

			List<LoggingEvent> loggingEvents =
				captureAppender.getLoggingEvents();

			Assert.assertEquals(1, loggingEvents.size());

			LoggingEvent loggingEvent = loggingEvents.get(0);

			String rootUrl = url.substring(0, url.indexOf('?'));

			Assert.assertEquals(
				"User 0 is not allowed to access URL " + rootUrl +
					" and portlet " + _TEST_PORTLET_ID,
				loggingEvent.getMessage());

			Assert.assertEquals("200", getString(responseMap, "code"));
			Assert.assertFalse(_map.containsKey("processAction"));
		}
	}

	@Test
	public void testActionRequest_p_auth() throws Exception {
		_testPortlet = new TestPortlet(_map) {

			@Override
			public void serveResource(
					ResourceRequest resourceRequest,
					ResourceResponse resourceResponse)
				throws IOException {

				PortletURL portletURL = resourceResponse.createActionURL();

				String queryString = HttpUtil.getQueryString(
					portletURL.toString());

				Map<String, String[]> parameterMap = HttpUtil.getParameterMap(
					queryString);

				String p_auth = MapUtil.getString(parameterMap, "p_auth");

				PrintWriter writer = resourceResponse.getWriter();

				writer.write(p_auth);
			}

		};

		setUpPortlet(_testPortlet, _properties, _TEST_PORTLET_ID);

		HttpServletRequest request = getHttpServletRequest();

		// Get the p_auth by making a resource request

		PortletURL portletURL = new PortletURLImpl(
			request, _TEST_PORTLET_ID, layout.getPlid(),
			PortletRequest.RESOURCE_PHASE);

		Map<String, List<String>> responseMap = request(portletURL.toString());

		String p_auth = getString(responseMap, "body");

		List<String> cookies = responseMap.get("Set-Cookie");

		_map.clear();

		// Now make the action request using the p_auth parameter

		portletURL = new PortletURLImpl(
			request, _TEST_PORTLET_ID, layout.getPlid(),
			PortletRequest.ACTION_PHASE);

		String url = portletURL.toString();

		url = HttpUtil.setParameter(url, "p_auth", p_auth);

		Map<String, List<String>> headers = new HashMap<>();

		headers.put("Cookie", cookies);

		responseMap = request(url, headers);

		Assert.assertEquals("200", getString(responseMap, "code"));
		Assert.assertTrue(_map.containsKey("processAction"));
	}

	@Test
	public void testActionRequest_p_auth_secret() throws Exception {
		Field field = ReflectionUtil.getDeclaredField(
			PropsValues.class, "AUTH_TOKEN_SHARED_SECRET");

		Object originalValue = field.get(null);

		try {
			field.set(null, "test");

			setUpPortlet(_testPortlet, _properties, _TEST_PORTLET_ID);

			HttpServletRequest request = getHttpServletRequest();

			PortletURL portletURL = new PortletURLImpl(
				request, _TEST_PORTLET_ID, layout.getPlid(),
				PortletRequest.ACTION_PHASE);

			portletURL.setParameter("p_auth_secret", Encryptor.digest("test"));

			Map<String, List<String>> responseMap = request(
				portletURL.toString());

			Assert.assertEquals("200", getString(responseMap, "code"));
			Assert.assertTrue(_map.containsKey("processAction"));
		}
		finally {
			field.set(null, originalValue);
		}
	}

	@Test
	public void testActionRequest_struts_action() throws Exception {
		_properties.put(PropsKeys.AUTH_TOKEN_IGNORE_ACTIONS, "/test/portlet/1");
		_properties.put("com.liferay.portlet.struts-path", "test/portlet");

		setUpPortlet(_testPortlet, _properties, _TEST_PORTLET_ID);

		HttpServletRequest request = getHttpServletRequest();

		PortletURL portletURL = new PortletURLImpl(
			request, _TEST_PORTLET_ID, layout.getPlid(),
			PortletRequest.ACTION_PHASE);

		portletURL.setParameter("struts_action", "/test/portlet/1");

		Map<String, List<String>> responseMap = request(portletURL.toString());

		Assert.assertEquals("200", getString(responseMap, "code"));
		Assert.assertTrue(_map.containsKey("processAction"));
	}

	@Test
	public void testActionRequest_X_CSRF_Token() throws Exception {
		_testPortlet = new TestPortlet(_map) {

			@Override
			public void serveResource(
					ResourceRequest resourceRequest,
					ResourceResponse resourceResponse)
				throws IOException {

				PortletURL portletURL = resourceResponse.createActionURL();

				String queryString = HttpUtil.getQueryString(
					portletURL.toString());

				Map<String, String[]> parameterMap = HttpUtil.getParameterMap(
					queryString);

				String p_auth = MapUtil.getString(parameterMap, "p_auth");

				PrintWriter writer = resourceResponse.getWriter();

				writer.write(p_auth);
			}

		};

		setUpPortlet(_testPortlet, _properties, _TEST_PORTLET_ID);

		HttpServletRequest request = getHttpServletRequest();

		// Get the p_auth by making a resource request

		PortletURL portletURL = new PortletURLImpl(
			request, _TEST_PORTLET_ID, layout.getPlid(),
			PortletRequest.RESOURCE_PHASE);

		Map<String, List<String>> responseMap = request(portletURL.toString());

		String p_auth = getString(responseMap, "body");

		List<String> cookies = responseMap.get("Set-Cookie");

		_map.clear();

		// Now make the action request using the p_auth header

		portletURL = new PortletURLImpl(
			request, _TEST_PORTLET_ID, layout.getPlid(),
			PortletRequest.ACTION_PHASE);

		String url = portletURL.toString();

		url = HttpUtil.removeParameter(url, "p_auth");

		Map<String, List<String>> headers = new HashMap<>();

		headers.put("Cookie", cookies);
		headers.put("X-CSRF-Token", Collections.singletonList(p_auth));

		responseMap = request(url, headers);

		Assert.assertEquals("200", getString(responseMap, "code"));
		Assert.assertTrue(_map.containsKey("processAction"));
	}

	@Test
	public void testLayoutRequest() throws Exception {
		setUpPortlet(_testPortlet, _properties, _TEST_PORTLET_ID);

		HttpServletRequest request = getHttpServletRequest();

		Map<String, List<String>> responseMap = request(
			layout.getRegularURL(request));

		Assert.assertEquals("200", getString(responseMap, "code"));
		Assert.assertTrue(_map.containsKey("render"));
	}

	@Test
	public void testRenderRequest_invalidPortletId() throws Exception {
		HttpServletRequest request = getHttpServletRequest();

		String url =
			layout.getRegularURL(request) +
				"?p_p_id='\"><script>alert(1)</script>&p_p_lifecycle=0&" +
					"p_p_state=exclusive";

		try (CaptureAppender captureAppender =
			Log4JLoggerTestUtil.configureLog4JLogger(
				SecurityPortletContainerWrapper.class.getName(), Level.WARN)) {

			Map<String, List<String>> responseMap = request(url);

			List<LoggingEvent> loggingEvents =
				captureAppender.getLoggingEvents();

			Assert.assertEquals(1, loggingEvents.size());

			LoggingEvent loggingEvent = loggingEvents.get(0);

			Assert.assertEquals(
				"Invalid portlet ID '\"><script>alert(1)</script>",
				loggingEvent.getMessage());

			Assert.assertEquals("200", getString(responseMap, "code"));
		}
	}

	@Test
	public void testRenderRequest_isAccessGrantedByPortletAuthenticationToken()
		throws Exception {

		final String portletToTarget = "TEST_TARGET";

		_properties.put(
			"com.liferay.portlet.add-default-resource", Boolean.TRUE);
		_properties.put("com.liferay.portlet.system", Boolean.TRUE);

		setUpPortlet(
			new TestPortlet(_map), _properties, portletToTarget, false);

		_properties = new Hashtable<>();

		Map<String, String> ignored = new HashMap<>();

		_testPortlet = new TestPortlet(ignored) {

			@Override
			public void serveResource(
					ResourceRequest resourceRequest,
					ResourceResponse resourceResponse)
				throws IOException {

				PortletURL portletURL = PortletURLFactoryUtil.create(
					resourceRequest, portletToTarget, layout.getPlid(),
					PortletRequest.RENDER_PHASE);

				String queryString = HttpUtil.getQueryString(
					portletURL.toString());

				Map<String, String[]> parameterMap = HttpUtil.getParameterMap(
					queryString);

				String p_p_auth = MapUtil.getString(parameterMap, "p_p_auth");

				PrintWriter writer = resourceResponse.getWriter();

				writer.write(p_p_auth);
			}

		};

		setUpPortlet(_testPortlet, _properties, _TEST_PORTLET_ID);

		HttpServletRequest request = getHttpServletRequest();

		// Get the p_p_auth by making a resource request

		PortletURL portletURL = new PortletURLImpl(
			request, _TEST_PORTLET_ID, layout.getPlid(),
			PortletRequest.RESOURCE_PHASE);

		Map<String, List<String>> responseMap = request(portletURL.toString());

		String p_p_auth = getString(responseMap, "body");

		List<String> cookies = responseMap.get("Set-Cookie");

		_map.clear();

		// Now make the render request to the target portlet using the p_p_auth
		// parameter

		portletURL = new PortletURLImpl(
			request, portletToTarget, layout.getPlid(),
			PortletRequest.RENDER_PHASE);

		portletURL.setWindowState(WindowState.MAXIMIZED);

		String url = portletURL.toString();

		url = HttpUtil.setParameter(url, "p_p_auth", p_p_auth);

		Map<String, List<String>> headers = new HashMap<>();

		headers.put("Cookie", cookies);

		responseMap = request(url, headers);

		Assert.assertEquals("200", getString(responseMap, "code"));
		Assert.assertTrue(_map.containsKey("render"));
	}

	@Test
	public void testRenderRequest_isAccessGrantedByPortletOnPage()
		throws Exception {

		setUpPortlet(_testPortlet, _properties, _TEST_PORTLET_ID);

		HttpServletRequest request = getHttpServletRequest();

		PortletURL portletURL = new PortletURLImpl(
			request, _TEST_PORTLET_ID, layout.getPlid(),
			PortletRequest.RENDER_PHASE);

		Map<String, List<String>> responseMap = request(portletURL.toString());

		Assert.assertEquals("200", getString(responseMap, "code"));
		Assert.assertTrue(_map.containsKey("render"));
	}

	@Test
	public void testRenderRequest_isAccessGrantedByRuntimePortlet()
		throws Exception {

		Map<String, String> ignored = new HashMap<>();

		_testPortlet = new TestPortlet(ignored) {

			@Override
			public void render(
					RenderRequest renderRequest, RenderResponse renderResponse)
				throws IOException, PortletException {

				PortletContext portletContext = getPortletContext();

				PortletRequestDispatcher requestDispatcher =
					portletContext.getRequestDispatcher("/runtime_portlet.jsp");

				requestDispatcher.include(renderRequest, renderResponse);
			}

		};

		String portletToEmbbed = "TEST_EMBEDDED";

		setUpPortlet(
			new TestPortlet(_map), _properties, portletToEmbbed, false);
		setUpPortlet(_testPortlet, _properties, _TEST_PORTLET_ID);

		HttpServletRequest request = getHttpServletRequest();

		PortletURL portletURL = new PortletURLImpl(
			request, _TEST_PORTLET_ID, layout.getPlid(),
			PortletRequest.RENDER_PHASE);

		portletURL.setParameter("portletToEmbbed", portletToEmbbed);

		Map<String, List<String>> responseMap = request(portletURL.toString());

		Assert.assertEquals("200", getString(responseMap, "code"));
		Assert.assertTrue(_map.containsKey("render"));
	}

	@Test
	public void testResourceRequest_invalidPortletId() throws Exception {
		HttpServletRequest request = getHttpServletRequest();

		String rootUrl = layout.getRegularURL(request);
		String url =
			rootUrl + "?p_p_id='\"><script>alert(1)</script>&p_p_lifecycle=2&";

		try (CaptureAppender captureAppender =
			Log4JLoggerTestUtil.configureLog4JLogger(
				SecurityPortletContainerWrapper.class.getName(), Level.WARN)) {

			Map<String, List<String>> responseMap = request(url);

			List<LoggingEvent> loggingEvents =
				captureAppender.getLoggingEvents();

			Assert.assertEquals(2, loggingEvents.size());

			LoggingEvent loggingEvent = loggingEvents.get(0);

			Assert.assertEquals(
				"Invalid portlet ID '\"><script>alert(1)</script>",
				loggingEvent.getMessage());

			loggingEvent = loggingEvents.get(1);

			Assert.assertEquals(
				"Reject serveResource for " + rootUrl +
					" on '\"><script>alert(1)</script>",
				loggingEvent.getMessage());

			Assert.assertEquals("400", getString(responseMap, "code"));
		}
	}

	@Test
	public void
			testResourceRequest_isAccessGrantedByPortletAuthenticationToken()
		throws Exception {

		final String portletToTarget = "TEST_TARGET";

		_properties.put(
			"com.liferay.portlet.add-default-resource", Boolean.TRUE);
		_properties.put("com.liferay.portlet.system", Boolean.TRUE);

		setUpPortlet(
			new TestPortlet(_map), _properties, portletToTarget, false);

		_properties = new Hashtable<>();

		Map<String, String> ignored = new HashMap<>();

		_testPortlet = new TestPortlet(ignored) {

			@Override
			public void serveResource(
					ResourceRequest resourceRequest,
					ResourceResponse resourceResponse)
				throws IOException {

				PortletURL portletURL = PortletURLFactoryUtil.create(
					resourceRequest, portletToTarget, layout.getPlid(),
					PortletRequest.RENDER_PHASE);

				String queryString = HttpUtil.getQueryString(
					portletURL.toString());

				Map<String, String[]> parameterMap = HttpUtil.getParameterMap(
					queryString);

				String p_p_auth = MapUtil.getString(parameterMap, "p_p_auth");

				PrintWriter writer = resourceResponse.getWriter();

				writer.write(p_p_auth);
			}

		};

		setUpPortlet(_testPortlet, _properties, _TEST_PORTLET_ID);

		HttpServletRequest request = getHttpServletRequest();

		// Get the p_p_auth by making a resource request

		PortletURL portletURL = new PortletURLImpl(
			request, _TEST_PORTLET_ID, layout.getPlid(),
			PortletRequest.RESOURCE_PHASE);

		Map<String, List<String>> responseMap = request(portletURL.toString());

		String p_p_auth = getString(responseMap, "body");

		List<String> cookies = responseMap.get("Set-Cookie");

		_map.clear();

		// Now make the render request to the target portlet using the p_p_auth
		// parameter

		portletURL = new PortletURLImpl(
			request, portletToTarget, layout.getPlid(),
			PortletRequest.RESOURCE_PHASE);

		portletURL.setWindowState(WindowState.MAXIMIZED);

		String url = portletURL.toString();

		url = HttpUtil.setParameter(url, "p_p_auth", p_p_auth);

		Map<String, List<String>> headers = new HashMap<>();

		headers.put("Cookie", cookies);

		responseMap = request(url, headers);

		Assert.assertEquals("200", getString(responseMap, "code"));
		Assert.assertTrue(_map.containsKey("serveResource"));
	}

	@Test
	public void testResourceRequest_isAccessGrantedByPortletOnPage()
		throws Exception {

		setUpPortlet(_testPortlet, _properties, _TEST_PORTLET_ID);

		HttpServletRequest request = getHttpServletRequest();

		PortletURL portletURL = new PortletURLImpl(
			request, _TEST_PORTLET_ID, layout.getPlid(),
			PortletRequest.RESOURCE_PHASE);

		Map<String, List<String>> responseMap = request(portletURL.toString());

		Assert.assertEquals("200", getString(responseMap, "code"));
		Assert.assertTrue(_map.containsKey("serveResource"));
	}

	@Test
	public void testResourceRequest_isAccessGrantedByRuntimePortlet()
		throws Exception {

		Map<String, String> ignored = new HashMap<>();

		_testPortlet = new TestPortlet(ignored) {

			@Override
			public void serveResource(
					ResourceRequest resourceRequest,
					ResourceResponse resourceResponse)
				throws IOException, PortletException {

				PortletContext portletContext = getPortletContext();

				PortletRequestDispatcher requestDispatcher =
					portletContext.getRequestDispatcher("/runtime_portlet.jsp");

				requestDispatcher.include(resourceRequest, resourceResponse);
			}

		};

		String portletToEmbbed = "TEST_EMBEDDED";

		setUpPortlet(
			new TestPortlet(_map), _properties, portletToEmbbed, false);
		setUpPortlet(_testPortlet, _properties, _TEST_PORTLET_ID);

		HttpServletRequest request = getHttpServletRequest();

		PortletURL portletURL = new PortletURLImpl(
			request, _TEST_PORTLET_ID, layout.getPlid(),
			PortletRequest.RESOURCE_PHASE);

		portletURL.setParameter("portletToEmbbed", portletToEmbbed);

		Map<String, List<String>> responseMap = request(portletURL.toString());

		Assert.assertEquals("200", getString(responseMap, "code"));
		Assert.assertTrue(_map.containsKey("render"));
	}

	protected String getString(Map<String, List<String>> map, String key) {
		List<String> values = map.get(key);

		return values.get(0);
	}

	private static final String _TEST_PORTLET_ID = "TEST_PORTLET_ID";

	private final Map<String, String> _map = new ConcurrentHashMap<>();
	private Dictionary<String, Object> _properties = new Hashtable<>();
	private TestPortlet _testPortlet;

}