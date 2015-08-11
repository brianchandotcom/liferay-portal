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

package com.liferay.portlet.container.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.TransactionalTestRule;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.ReflectionUtil;
import com.liferay.portal.security.auth.AuthTokenWhitelistUtil;
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

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Raymond Augé
 */
@RunWith(Arquillian.class)
public class RequestTest extends BaseTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), TransactionalTestRule.INSTANCE);

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		map = new ConcurrentHashMap<>();
		requestTestPortlet = new RequestTestPortlet(map);
		properties = new Hashtable<>();
	}

	@After
	@Override
	public void tearDown() throws Exception {
		super.tearDown();

		map = null;
		requestTestPortlet = null;
		properties = null;
	}

	@Test
	public void testActionRequest_AUTH_TOKEN_CHECK_ENABLED() throws Exception {
		Field field = ReflectionUtil.getDeclaredField(
			PropsValues.class, "AUTH_TOKEN_CHECK_ENABLED");

		Object originalValue = field.get(null);

		try {
			field.set(null, Boolean.FALSE.booleanValue());

			setUpPortlet(
				requestTestPortlet, properties, PortletKeys.TEST_PORTLET);

			MockHttpServletRequest request = getRequest();

			PortletURL portletURL = new PortletURLImpl(
				request, PortletKeys.TEST_PORTLET, layout.getPlid(),
				PortletRequest.ACTION_PHASE);

			Map<String, List<String>> responseMap = request(
				portletURL.toString());

			Assert.assertEquals("200", responseMap.get("responseCode").get(0));
			Assert.assertTrue(map.containsKey("processAction"));
		}
		finally {
			field.set(null, originalValue);
		}
	}

	@Test
	public void testActionRequest_AUTH_TOKEN_IGNORE_ORIGINS() throws Exception {
		Field field = ReflectionUtil.getDeclaredField(
			PropsValues.class, "AUTH_TOKEN_IGNORE_ORIGINS");

		Object originalValue = field.get(null);

		try {
			field.set(
				null,
				new String[] {SecurityPortletContainerWrapper.class.getName()});

			AuthTokenWhitelistUtil.resetOriginCSRFWhitelist();

			setUpPortlet(
				requestTestPortlet, properties, PortletKeys.TEST_PORTLET);

			MockHttpServletRequest request = getRequest();

			PortletURL portletURL = new PortletURLImpl(
				request, PortletKeys.TEST_PORTLET, layout.getPlid(),
				PortletRequest.ACTION_PHASE);

			Map<String, List<String>> responseMap = request(
				portletURL.toString());

			Assert.assertEquals("200", responseMap.get("responseCode").get(0));
			Assert.assertTrue(map.containsKey("processAction"));
		}
		finally {
			field.set(null, originalValue);

			AuthTokenWhitelistUtil.resetOriginCSRFWhitelist();
		}
	}

	@Test
	public void testActionRequest_AUTH_TOKEN_IGNORE_PORTLETS()
		throws Exception {

		Field field = ReflectionUtil.getDeclaredField(
			PropsValues.class, "AUTH_TOKEN_IGNORE_PORTLETS");

		Object originalValue = field.get(null);

		try {
			field.set(null, new String[] {PortletKeys.TEST_PORTLET});

			AuthTokenWhitelistUtil.resetPortletCSRFWhitelist();

			setUpPortlet(
				requestTestPortlet, properties, PortletKeys.TEST_PORTLET);

			MockHttpServletRequest request = getRequest();

			PortletURL portletURL = new PortletURLImpl(
				request, PortletKeys.TEST_PORTLET, layout.getPlid(),
				PortletRequest.ACTION_PHASE);

			Map<String, List<String>> responseMap = request(
				portletURL.toString());

			Assert.assertEquals("200", responseMap.get("responseCode").get(0));
			Assert.assertTrue(map.containsKey("processAction"));
		}
		finally {
			field.set(null, originalValue);

			AuthTokenWhitelistUtil.resetPortletCSRFWhitelist();
		}
	}

	@Test
	public void testActionRequest_initParam() throws Exception {
		properties.put(
			"javax.portlet.init-param.check-auth-token",
			Boolean.FALSE.toString());

		setUpPortlet(requestTestPortlet, properties, PortletKeys.TEST_PORTLET);

		MockHttpServletRequest request = getRequest();

		PortletURL portletURL = new PortletURLImpl(
			request, PortletKeys.TEST_PORTLET, layout.getPlid(),
			PortletRequest.ACTION_PHASE);

		Map<String, List<String>> responseMap = request(portletURL.toString());

		Assert.assertEquals("200", responseMap.get("responseCode").get(0));
		Assert.assertTrue(map.containsKey("processAction"));
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
		setUpPortlet(requestTestPortlet, properties, PortletKeys.TEST_PORTLET);

		MockHttpServletRequest request = getRequest();

		PortletURL portletURL = new PortletURLImpl(
			request, PortletKeys.TEST_PORTLET, layout.getPlid(),
			PortletRequest.ACTION_PHASE);

		Map<String, List<String>> responseMap = request(portletURL.toString());

		Assert.assertEquals("200", responseMap.get("responseCode").get(0));
		Assert.assertFalse(map.containsKey("processAction"));
	}

	@Test
	public void testActionRequest_p_auth() throws Exception {
		requestTestPortlet = new RequestTestPortlet(map) {

			@Override
			public void serveResource(
					ResourceRequest resourceRequest,
					ResourceResponse resourceResponse)
				throws IOException, PortletException {

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

		setUpPortlet(requestTestPortlet, properties, PortletKeys.TEST_PORTLET);

		MockHttpServletRequest request = getRequest();

		// Get the p_auth by making a resource request

		PortletURL portletURL = new PortletURLImpl(
			request, PortletKeys.TEST_PORTLET, layout.getPlid(),
			PortletRequest.RESOURCE_PHASE);

		Map<String, List<String>> responseMap = request(portletURL.toString());

		String p_auth = responseMap.get("responseBody").get(0);

		List<String> cookies = responseMap.get("Set-Cookie");

		map.clear();

		// Now make the action request using the p_auth parameter

		portletURL = new PortletURLImpl(
			request, PortletKeys.TEST_PORTLET, layout.getPlid(),
			PortletRequest.ACTION_PHASE);

		String url = portletURL.toString();

		url = HttpUtil.setParameter(url, "p_auth", p_auth);

		Map<String, List<String>> headers = new HashMap<>();

		headers.put("Cookie", cookies);

		responseMap = request(url, headers);

		Assert.assertEquals("200", responseMap.get("responseCode").get(0));
		Assert.assertTrue(map.containsKey("processAction"));
	}

	@Test
	public void testActionRequest_p_auth_secret() throws Exception {
		Field field = ReflectionUtil.getDeclaredField(
			PropsValues.class, "AUTH_TOKEN_SHARED_SECRET");

		Object originalValue = field.get(null);

		try {
			field.set(null, "test");

			setUpPortlet(
				requestTestPortlet, properties, PortletKeys.TEST_PORTLET);

			MockHttpServletRequest request = getRequest();

			PortletURL portletURL = new PortletURLImpl(
				request, PortletKeys.TEST_PORTLET, layout.getPlid(),
				PortletRequest.ACTION_PHASE);

			portletURL.setParameter("p_auth_secret", Encryptor.digest("test"));

			Map<String, List<String>> responseMap = request(
				portletURL.toString());

			Assert.assertEquals("200", responseMap.get("responseCode").get(0));
			Assert.assertTrue(map.containsKey("processAction"));
		}
		finally {
			field.set(null, originalValue);
		}
	}

	@Test
	public void testActionRequest_struts_action() throws Exception {
		properties.put(PropsKeys.AUTH_TOKEN_IGNORE_ACTIONS, "/test/portlet/1");
		properties.put("com.liferay.portlet.struts-path", "test/portlet");

		setUpPortlet(requestTestPortlet, properties, PortletKeys.TEST_PORTLET);

		MockHttpServletRequest request = getRequest();

		PortletURL portletURL = new PortletURLImpl(
			request, PortletKeys.TEST_PORTLET, layout.getPlid(),
			PortletRequest.ACTION_PHASE);

		portletURL.setParameter("struts_action", "/test/portlet/1");

		Map<String, List<String>> responseMap = request(portletURL.toString());

		Assert.assertEquals("200", responseMap.get("responseCode").get(0));
		Assert.assertTrue(map.containsKey("processAction"));
	}

	@Test
	public void testActionRequest_X_CSRF_Token() throws Exception {
		requestTestPortlet = new RequestTestPortlet(map) {

			@Override
			public void serveResource(
					ResourceRequest resourceRequest,
					ResourceResponse resourceResponse)
				throws IOException, PortletException {

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

		setUpPortlet(requestTestPortlet, properties, PortletKeys.TEST_PORTLET);

		MockHttpServletRequest request = getRequest();

		// Get the p_auth by making a resource request

		PortletURL portletURL = new PortletURLImpl(
			request, PortletKeys.TEST_PORTLET, layout.getPlid(),
			PortletRequest.RESOURCE_PHASE);

		Map<String, List<String>> responseMap = request(portletURL.toString());

		String p_auth = responseMap.get("responseBody").get(0);

		List<String> cookies = responseMap.get("Set-Cookie");

		map.clear();

		// Now make the action request using the p_auth header

		portletURL = new PortletURLImpl(
			request, PortletKeys.TEST_PORTLET, layout.getPlid(),
			PortletRequest.ACTION_PHASE);

		String url = portletURL.toString();

		url = HttpUtil.removeParameter(url, "p_auth");

		Map<String, List<String>> headers = new HashMap<>();

		headers.put("Cookie", cookies);
		headers.put("X-CSRF-Token", Collections.singletonList(p_auth));

		responseMap = request(url, headers);

		Assert.assertEquals("200", responseMap.get("responseCode").get(0));
		Assert.assertTrue(map.containsKey("processAction"));
	}

	@Test
	public void testLayoutRequest() throws Exception {
		setUpPortlet(requestTestPortlet, properties, PortletKeys.TEST_PORTLET);

		MockHttpServletRequest request = getRequest();

		Map<String, List<String>> responseMap = request(
			layout.getRegularURL(request));

		Assert.assertEquals("200", responseMap.get("responseCode").get(0));
		Assert.assertTrue(map.containsKey("render"));
	}

	@Test
	public void testRenderRequest_invalidPortletId() throws Exception {
		MockHttpServletRequest request = getRequest();

		String url =
			layout.getRegularURL(request) +
				"?p_p_id='\"><script>alert(1)</script>&p_p_lifecycle=0&" +
					"p_p_state=exclusive";

		Map<String, List<String>> responseMap = request(url);

		Assert.assertEquals("200", responseMap.get("responseCode").get(0));

		String expected =
			"You do not have the roles required to access this portlet.";

		String responseBody = responseMap.get("responseBody").get(0);

		Assert.assertTrue(
			"Expected to contain <" + expected + "> but was <" + responseBody +
				">",
			responseBody.contains(
				"You do not have the roles required to access this portlet."));
	}

	@Test
	public void testRenderRequest_isAccessGrantedByPortletAuthenticationToken()
		throws Exception {

		final String portletToTarget = "TEST_TARGET";

		properties.put(
			"com.liferay.portlet.add-default-resource", Boolean.TRUE);
		properties.put("com.liferay.portlet.system", Boolean.TRUE);

		setUpPortlet(
			new RequestTestPortlet(map), properties, portletToTarget, false);

		properties = new Hashtable<>();

		Map<String, String> ignored = new HashMap<>();

		requestTestPortlet = new RequestTestPortlet(ignored) {

			@Override
			public void serveResource(
					ResourceRequest resourceRequest,
					ResourceResponse resourceResponse)
				throws IOException, PortletException {

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

		setUpPortlet(requestTestPortlet, properties, PortletKeys.TEST_PORTLET);

		MockHttpServletRequest request = getRequest();

		// Get the p_p_auth by making a resource request

		PortletURL portletURL = new PortletURLImpl(
			request, PortletKeys.TEST_PORTLET, layout.getPlid(),
			PortletRequest.RESOURCE_PHASE);

		Map<String, List<String>> responseMap = request(portletURL.toString());

		String p_p_auth = responseMap.get("responseBody").get(0);

		List<String> cookies = responseMap.get("Set-Cookie");

		map.clear();

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

		Assert.assertEquals("200", responseMap.get("responseCode").get(0));
		Assert.assertTrue(map.containsKey("render"));
	}

	@Test
	public void testRenderRequest_isAccessGrantedByPortletOnPage()
		throws Exception {

		setUpPortlet(requestTestPortlet, properties, PortletKeys.TEST_PORTLET);

		MockHttpServletRequest request = getRequest();

		PortletURL portletURL = new PortletURLImpl(
			request, PortletKeys.TEST_PORTLET, layout.getPlid(),
			PortletRequest.RENDER_PHASE);

		Map<String, List<String>> responseMap = request(portletURL.toString());

		Assert.assertEquals("200", responseMap.get("responseCode").get(0));
		Assert.assertTrue(map.containsKey("render"));
	}

	@Test
	public void testRenderRequest_isAccessGrantedByRuntimePortlet()
		throws Exception {

		Map<String, String> ignored = new HashMap<>();

		requestTestPortlet = new RequestTestPortlet(ignored) {

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
			new RequestTestPortlet(map), properties, portletToEmbbed, false);
		setUpPortlet(requestTestPortlet, properties, PortletKeys.TEST_PORTLET);

		MockHttpServletRequest request = getRequest();

		PortletURL portletURL = new PortletURLImpl(
			request, PortletKeys.TEST_PORTLET, layout.getPlid(),
			PortletRequest.RENDER_PHASE);

		portletURL.setParameter("portletToEmbbed", portletToEmbbed);

		Map<String, List<String>> responseMap = request(portletURL.toString());

		Assert.assertEquals("200", responseMap.get("responseCode").get(0));
		Assert.assertTrue(map.containsKey("render"));
	}

	@Test
	public void testResourceRequest_invalidPortletId() throws Exception {
		MockHttpServletRequest request = getRequest();

		String url =
			layout.getRegularURL(request) +
				"?p_p_id='\"><script>alert(1)</script>&p_p_lifecycle=2&";

		Map<String, List<String>> responseMap = request(url);

		Assert.assertEquals("400", responseMap.get("responseCode").get(0));
	}

	@Test
	public void
			testResourceRequest_isAccessGrantedByPortletAuthenticationToken()
		throws Exception {

		final String portletToTarget = "TEST_TARGET";

		properties.put(
			"com.liferay.portlet.add-default-resource", Boolean.TRUE);
		properties.put("com.liferay.portlet.system", Boolean.TRUE);

		setUpPortlet(
			new RequestTestPortlet(map), properties, portletToTarget, false);

		properties = new Hashtable<>();

		Map<String, String> ignored = new HashMap<>();

		requestTestPortlet = new RequestTestPortlet(ignored) {

			@Override
			public void serveResource(
					ResourceRequest resourceRequest,
					ResourceResponse resourceResponse)
				throws IOException, PortletException {

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

		setUpPortlet(requestTestPortlet, properties, PortletKeys.TEST_PORTLET);

		MockHttpServletRequest request = getRequest();

		// Get the p_p_auth by making a resource request

		PortletURL portletURL = new PortletURLImpl(
			request, PortletKeys.TEST_PORTLET, layout.getPlid(),
			PortletRequest.RESOURCE_PHASE);

		Map<String, List<String>> responseMap = request(portletURL.toString());

		String p_p_auth = responseMap.get("responseBody").get(0);

		List<String> cookies = responseMap.get("Set-Cookie");

		map.clear();

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

		Assert.assertEquals("200", responseMap.get("responseCode").get(0));
		Assert.assertTrue(map.containsKey("serveResource"));
	}

	@Test
	public void testResourceRequest_isAccessGrantedByPortletOnPage()
		throws Exception {

		setUpPortlet(requestTestPortlet, properties, PortletKeys.TEST_PORTLET);

		MockHttpServletRequest request = getRequest();

		PortletURL portletURL = new PortletURLImpl(
			request, PortletKeys.TEST_PORTLET, layout.getPlid(),
			PortletRequest.RESOURCE_PHASE);

		Map<String, List<String>> responseMap = request(portletURL.toString());

		Assert.assertEquals("200", responseMap.get("responseCode").get(0));
		Assert.assertTrue(map.containsKey("serveResource"));
	}

	@Test
	public void testResourceRequest_isAccessGrantedByRuntimePortlet()
		throws Exception {

		Map<String, String> ignored = new HashMap<>();

		requestTestPortlet = new RequestTestPortlet(ignored) {

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
			new RequestTestPortlet(map), properties, portletToEmbbed, false);
		setUpPortlet(requestTestPortlet, properties, PortletKeys.TEST_PORTLET);

		MockHttpServletRequest request = getRequest();

		PortletURL portletURL = new PortletURLImpl(
			request, PortletKeys.TEST_PORTLET, layout.getPlid(),
			PortletRequest.RESOURCE_PHASE);

		portletURL.setParameter("portletToEmbbed", portletToEmbbed);

		Map<String, List<String>> responseMap = request(portletURL.toString());

		Assert.assertEquals("200", responseMap.get("responseCode").get(0));
		Assert.assertTrue(map.containsKey("render"));
	}

	private Map<String, String> map;
	private Dictionary<String, Object> properties;
	private RequestTestPortlet requestTestPortlet;

}