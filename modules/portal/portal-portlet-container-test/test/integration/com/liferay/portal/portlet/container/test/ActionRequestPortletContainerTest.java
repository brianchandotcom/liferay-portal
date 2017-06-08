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
import com.liferay.portlet.PortletURLImpl;
import com.liferay.portlet.SecurityPortletContainerWrapper;
import com.liferay.util.Encryptor;

import java.io.IOException;
import java.io.PrintWriter;

import java.lang.reflect.Field;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Level;
import org.apache.log4j.spi.LoggingEvent;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Raymond Augé
 */
@RunWith(Arquillian.class)
public class ActionRequestPortletContainerTest
	extends BasePortletContainerTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), TransactionalTestRule.INSTANCE);

	@Test
	public void testActionRequest_AUTH_TOKEN_CHECK_ENABLED() throws Exception {
		Field field = ReflectionUtil.getDeclaredField(
			PropsValues.class, "AUTH_TOKEN_CHECK_ENABLED");

		Object value = field.get(null);

		try {
			field.set(null, Boolean.FALSE.booleanValue());

			setUpPortlet(testPortlet, properties, TEST_PORTLET_ID);

			HttpServletRequest httpServletRequest = getHttpServletRequest();

			PortletURL portletURL = new PortletURLImpl(
				httpServletRequest, TEST_PORTLET_ID, layout.getPlid(),
				PortletRequest.ACTION_PHASE);

			Map<String, List<String>> responseMap = request(
				portletURL.toString());

			Assert.assertEquals("200", getString(responseMap, "code"));
			Assert.assertTrue(map.containsKey("processAction"));
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

			setUpPortlet(testPortlet, properties, TEST_PORTLET_ID);

			HttpServletRequest httpServletRequest = getHttpServletRequest();

			PortletURL portletURL = new PortletURLImpl(
				httpServletRequest, TEST_PORTLET_ID, layout.getPlid(),
				PortletRequest.ACTION_PHASE);

			Map<String, List<String>> responseMap = request(
				portletURL.toString());

			Assert.assertEquals("200", getString(responseMap, "code"));
			Assert.assertTrue(map.containsKey("processAction"));
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
			field.set(null, new String[] {TEST_PORTLET_ID});

			AuthTokenWhitelistUtil.resetPortletCSRFWhitelist();

			setUpPortlet(testPortlet, properties, TEST_PORTLET_ID);

			HttpServletRequest httpServletRequest = getHttpServletRequest();

			PortletURL portletURL = new PortletURLImpl(
				httpServletRequest, TEST_PORTLET_ID, layout.getPlid(),
				PortletRequest.ACTION_PHASE);

			Map<String, List<String>> responseMap = request(
				portletURL.toString());

			Assert.assertEquals("200", getString(responseMap, "code"));
			Assert.assertTrue(map.containsKey("processAction"));
		}
		finally {
			field.set(null, value);

			AuthTokenWhitelistUtil.resetPortletCSRFWhitelist();
		}
	}

	@Test
	public void testActionRequest_initParam() throws Exception {
		properties.put(
			"javax.portlet.init-param.check-auth-token",
			Boolean.FALSE.toString());

		setUpPortlet(testPortlet, properties, TEST_PORTLET_ID);

		HttpServletRequest httpServletRequest = getHttpServletRequest();

		PortletURL portletURL = new PortletURLImpl(
			httpServletRequest, TEST_PORTLET_ID, layout.getPlid(),
			PortletRequest.ACTION_PHASE);

		Map<String, List<String>> responseMap = request(portletURL.toString());

		Assert.assertEquals("200", getString(responseMap, "code"));
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
		setUpPortlet(testPortlet, properties, TEST_PORTLET_ID);

		HttpServletRequest httpServletRequest = getHttpServletRequest();

		PortletURL portletURL = new PortletURLImpl(
			httpServletRequest, TEST_PORTLET_ID, layout.getPlid(),
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
					" and portlet " + TEST_PORTLET_ID,
				loggingEvent.getMessage());

			Assert.assertEquals("200", getString(responseMap, "code"));
			Assert.assertFalse(map.containsKey("processAction"));
		}
	}

	@Test
	public void testActionRequest_p_auth() throws Exception {
		testPortlet = new TestPortlet(map) {

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

		setUpPortlet(testPortlet, properties, TEST_PORTLET_ID);

		HttpServletRequest httpServletRequest = getHttpServletRequest();

		// Get the p_auth by making a resource request

		PortletURL portletURL = new PortletURLImpl(
			httpServletRequest, TEST_PORTLET_ID, layout.getPlid(),
			PortletRequest.RESOURCE_PHASE);

		Map<String, List<String>> responseMap = request(portletURL.toString());

		String p_auth = getString(responseMap, "body");

		List<String> cookies = responseMap.get("Set-Cookie");

		map.clear();

		// Now make the action request using the p_auth parameter

		portletURL = new PortletURLImpl(
			httpServletRequest, TEST_PORTLET_ID, layout.getPlid(),
			PortletRequest.ACTION_PHASE);

		String url = portletURL.toString();

		url = HttpUtil.setParameter(url, "p_auth", p_auth);

		Map<String, List<String>> headers = new HashMap<>();

		headers.put("Cookie", cookies);

		responseMap = request(url, headers);

		Assert.assertEquals("200", getString(responseMap, "code"));
		Assert.assertTrue(map.containsKey("processAction"));
	}

	@Test
	public void testActionRequest_p_auth_secret() throws Exception {
		Field field = ReflectionUtil.getDeclaredField(
			PropsValues.class, "AUTH_TOKEN_SHARED_SECRET");

		Object originalValue = field.get(null);

		try {
			field.set(null, "test");

			setUpPortlet(testPortlet, properties, TEST_PORTLET_ID);

			HttpServletRequest httpServletRequest = getHttpServletRequest();

			PortletURL portletURL = new PortletURLImpl(
				httpServletRequest, TEST_PORTLET_ID, layout.getPlid(),
				PortletRequest.ACTION_PHASE);

			portletURL.setParameter("p_auth_secret", Encryptor.digest("test"));

			Map<String, List<String>> responseMap = request(
				portletURL.toString());

			Assert.assertEquals("200", getString(responseMap, "code"));
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

		setUpPortlet(testPortlet, properties, TEST_PORTLET_ID);

		HttpServletRequest httpServletRequest = getHttpServletRequest();

		PortletURL portletURL = new PortletURLImpl(
			httpServletRequest, TEST_PORTLET_ID, layout.getPlid(),
			PortletRequest.ACTION_PHASE);

		portletURL.setParameter("struts_action", "/test/portlet/1");

		Map<String, List<String>> responseMap = request(portletURL.toString());

		Assert.assertEquals("200", getString(responseMap, "code"));
		Assert.assertTrue(map.containsKey("processAction"));
	}

	@Test
	public void testActionRequest_X_CSRF_Token() throws Exception {
		testPortlet = new TestPortlet(map) {

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

		setUpPortlet(testPortlet, properties, TEST_PORTLET_ID);

		HttpServletRequest httpServletRequest = getHttpServletRequest();

		// Get the p_auth by making a resource request

		PortletURL portletURL = new PortletURLImpl(
			httpServletRequest, TEST_PORTLET_ID, layout.getPlid(),
			PortletRequest.RESOURCE_PHASE);

		Map<String, List<String>> responseMap = request(portletURL.toString());

		String p_auth = getString(responseMap, "body");

		List<String> cookies = responseMap.get("Set-Cookie");

		map.clear();

		// Now make the action request using the p_auth header

		portletURL = new PortletURLImpl(
			httpServletRequest, TEST_PORTLET_ID, layout.getPlid(),
			PortletRequest.ACTION_PHASE);

		String url = portletURL.toString();

		url = HttpUtil.removeParameter(url, "p_auth");

		Map<String, List<String>> headers = new HashMap<>();

		headers.put("Cookie", cookies);
		headers.put("X-CSRF-Token", Collections.singletonList(p_auth));

		responseMap = request(url, headers);

		Assert.assertEquals("200", getString(responseMap, "code"));
		Assert.assertTrue(map.containsKey("processAction"));
	}

}