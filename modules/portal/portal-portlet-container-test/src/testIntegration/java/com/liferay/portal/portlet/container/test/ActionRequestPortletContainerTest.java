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
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.ReflectionUtil;
import com.liferay.portal.security.auth.AuthTokenWhitelistUtil;
import com.liferay.portal.test.log.CaptureAppender;
import com.liferay.portal.test.log.Log4JLoggerTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.test.PortletContainerTestUtil;
import com.liferay.portlet.PortletURLImpl;
import com.liferay.portlet.SecurityPortletContainerWrapper;
import com.liferay.util.Encryptor;

import java.io.IOException;
import java.io.PrintWriter;

import java.lang.reflect.Field;

import java.util.Collections;
import java.util.Dictionary;
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
	public void testAuthTokenCheckEnabled() throws Exception {
		Field field = ReflectionUtil.getDeclaredField(
			PropsValues.class, "AUTH_TOKEN_CHECK_ENABLED");

		Object value = field.get(null);

		try {
			field.set(null, Boolean.FALSE.booleanValue());

			setUpPortlet(
				testPortlet, new HashMapDictionary<String, Object>(),
				TEST_PORTLET_ID);

			HttpServletRequest httpServletRequest =
				PortletContainerTestUtil.getHttpServletRequest(group, layout);

			PortletURL portletURL = new PortletURLImpl(
				httpServletRequest, TEST_PORTLET_ID, layout.getPlid(),
				PortletRequest.ACTION_PHASE);

			Map<String, List<String>> responseMap =
				PortletContainerTestUtil.request(portletURL.toString());

			Assert.assertEquals(
				"200", PortletContainerTestUtil.getString(responseMap, "code"));
			Assert.assertTrue(map.containsKey("processAction"));
		}
		finally {
			field.set(null, value);
		}
	}

	@Test
	public void testAuthTokenIgnoreOrigins() throws Exception {
		Field field = ReflectionUtil.getDeclaredField(
			PropsValues.class, "AUTH_TOKEN_IGNORE_ORIGINS");

		Object value = field.get(null);

		try {
			field.set(
				null,
				new String[] {SecurityPortletContainerWrapper.class.getName()});

			AuthTokenWhitelistUtil.resetOriginCSRFWhitelist();

			setUpPortlet(
				testPortlet, new HashMapDictionary<String, Object>(),
				TEST_PORTLET_ID);

			HttpServletRequest httpServletRequest =
				PortletContainerTestUtil.getHttpServletRequest(group, layout);

			PortletURL portletURL = new PortletURLImpl(
				httpServletRequest, TEST_PORTLET_ID, layout.getPlid(),
				PortletRequest.ACTION_PHASE);

			Map<String, List<String>> responseMap =
				PortletContainerTestUtil.request(portletURL.toString());

			Assert.assertEquals(
				"200", PortletContainerTestUtil.getString(responseMap, "code"));
			Assert.assertTrue(map.containsKey("processAction"));
		}
		finally {
			field.set(null, value);

			AuthTokenWhitelistUtil.resetOriginCSRFWhitelist();
		}
	}

	@Test
	public void testAuthTokenIgnorePortlets() throws Exception {
		Field field = ReflectionUtil.getDeclaredField(
			PropsValues.class, "AUTH_TOKEN_IGNORE_PORTLETS");

		Object value = field.get(null);

		try {
			field.set(null, new String[] {TEST_PORTLET_ID});

			AuthTokenWhitelistUtil.resetPortletCSRFWhitelist();

			setUpPortlet(
				testPortlet, new HashMapDictionary<String, Object>(),
				TEST_PORTLET_ID);

			HttpServletRequest httpServletRequest =
				PortletContainerTestUtil.getHttpServletRequest(group, layout);

			PortletURL portletURL = new PortletURLImpl(
				httpServletRequest, TEST_PORTLET_ID, layout.getPlid(),
				PortletRequest.ACTION_PHASE);

			Map<String, List<String>> responseMap =
				PortletContainerTestUtil.request(portletURL.toString());

			Assert.assertEquals(
				"200", PortletContainerTestUtil.getString(responseMap, "code"));
			Assert.assertTrue(map.containsKey("processAction"));
		}
		finally {
			field.set(null, value);

			AuthTokenWhitelistUtil.resetPortletCSRFWhitelist();
		}
	}

	@Test
	public void testInitParam() throws Exception {
		Dictionary<String, Object> properties = new HashMapDictionary<>();

		properties.put(
			"javax.portlet.init-param.check-auth-token",
			Boolean.FALSE.toString());

		setUpPortlet(testPortlet, properties, TEST_PORTLET_ID);

		HttpServletRequest httpServletRequest =
			PortletContainerTestUtil.getHttpServletRequest(group, layout);

		PortletURL portletURL = new PortletURLImpl(
			httpServletRequest, TEST_PORTLET_ID, layout.getPlid(),
			PortletRequest.ACTION_PHASE);

		Map<String, List<String>> responseMap =
			PortletContainerTestUtil.request(portletURL.toString());

		Assert.assertEquals(
			"200", PortletContainerTestUtil.getString(responseMap, "code"));
		Assert.assertTrue(map.containsKey("processAction"));
	}

	@Test
	public void testNoPortalAuthenticationTokens() throws Exception {
		setUpPortlet(
			testPortlet, new HashMapDictionary<String, Object>(),
			TEST_PORTLET_ID);

		HttpServletRequest httpServletRequest =
			PortletContainerTestUtil.getHttpServletRequest(group, layout);

		PortletURL portletURL = new PortletURLImpl(
			httpServletRequest, TEST_PORTLET_ID, layout.getPlid(),
			PortletRequest.ACTION_PHASE);

		String url = portletURL.toString();

		try (CaptureAppender captureAppender =
				Log4JLoggerTestUtil.configureLog4JLogger(
					SecurityPortletContainerWrapper.class.getName(),
					Level.WARN)) {

			Map<String, List<String>> responseMap =
				PortletContainerTestUtil.request(url);

			List<LoggingEvent> loggingEvents =
				captureAppender.getLoggingEvents();

			Assert.assertEquals(1, loggingEvents.size());

			LoggingEvent loggingEvent = loggingEvents.get(0);

			Assert.assertEquals(
				"User 0 is not allowed to access URL " +
					url.substring(0, url.indexOf('?')) + " and portlet " +
						TEST_PORTLET_ID,
				loggingEvent.getMessage());

			Assert.assertEquals(
				"200", PortletContainerTestUtil.getString(responseMap, "code"));
			Assert.assertFalse(map.containsKey("processAction"));
		}
	}

	@Test
	public void testPortalAuthenticationToken() throws Exception {
		testPortlet = new TestPortlet(map) {

			@Override
			public void serveResource(
					ResourceRequest resourceRequest,
					ResourceResponse resourceResponse)
				throws IOException {

				PrintWriter printWriter = resourceResponse.getWriter();

				PortletURL portletURL = resourceResponse.createActionURL();

				String queryString = HttpUtil.getQueryString(
					portletURL.toString());

				Map<String, String[]> parameterMap = HttpUtil.getParameterMap(
					queryString);

				String portalAuthenticationToken = MapUtil.getString(
					parameterMap, "p_auth");

				printWriter.write(portalAuthenticationToken);
			}

		};

		setUpPortlet(
			testPortlet, new HashMapDictionary<String, Object>(),
			TEST_PORTLET_ID);

		HttpServletRequest httpServletRequest =
			PortletContainerTestUtil.getHttpServletRequest(group, layout);

		PortletContainerTestUtil.PortalAuthentication portalAuthentication =
			PortletContainerTestUtil.getPortalAuthentication(
				httpServletRequest, layout, TEST_PORTLET_ID);

		map.clear();

		// Make an action request using the portal authentication token

		PortletURL portletURL = new PortletURLImpl(
			httpServletRequest, TEST_PORTLET_ID, layout.getPlid(),
			PortletRequest.ACTION_PHASE);

		String url = portletURL.toString();

		url = HttpUtil.setParameter(
			url, "p_auth", portalAuthentication.getPortalAuthenticationToken());

		Map<String, List<String>> headers = new HashMap<>();

		headers.put("Cookie", portalAuthentication.getCookies());

		Map<String, List<String>> responseMap =
			PortletContainerTestUtil.request(url, headers);

		Assert.assertEquals(
			"200", PortletContainerTestUtil.getString(responseMap, "code"));
		Assert.assertTrue(map.containsKey("processAction"));
	}

	@Test
	public void testPortalAuthenticationTokenSecret() throws Exception {
		Field field = ReflectionUtil.getDeclaredField(
			PropsValues.class, "AUTH_TOKEN_SHARED_SECRET");

		Object value = field.get(null);

		try {
			field.set(null, "test");

			setUpPortlet(
				testPortlet, new HashMapDictionary<String, Object>(),
				TEST_PORTLET_ID);

			HttpServletRequest httpServletRequest =
				PortletContainerTestUtil.getHttpServletRequest(group, layout);

			PortletURL portletURL = new PortletURLImpl(
				httpServletRequest, TEST_PORTLET_ID, layout.getPlid(),
				PortletRequest.ACTION_PHASE);

			portletURL.setParameter("p_auth_secret", Encryptor.digest("test"));

			Map<String, List<String>> responseMap =
				PortletContainerTestUtil.request(portletURL.toString());

			Assert.assertEquals(
				"200", PortletContainerTestUtil.getString(responseMap, "code"));
			Assert.assertTrue(map.containsKey("processAction"));
		}
		finally {
			field.set(null, value);
		}
	}

	@Test
	public void testStrutsAction() throws Exception {
		Dictionary<String, Object> properties = new HashMapDictionary<>();

		properties.put(PropsKeys.AUTH_TOKEN_IGNORE_ACTIONS, "/test/portlet/1");
		properties.put("com.liferay.portlet.struts-path", "test/portlet");

		setUpPortlet(testPortlet, properties, TEST_PORTLET_ID);

		HttpServletRequest httpServletRequest =
			PortletContainerTestUtil.getHttpServletRequest(group, layout);

		PortletURL portletURL = new PortletURLImpl(
			httpServletRequest, TEST_PORTLET_ID, layout.getPlid(),
			PortletRequest.ACTION_PHASE);

		portletURL.setParameter("struts_action", "/test/portlet/1");

		Map<String, List<String>> responseMap =
			PortletContainerTestUtil.request(portletURL.toString());

		Assert.assertEquals(
			"200", PortletContainerTestUtil.getString(responseMap, "code"));
		Assert.assertTrue(map.containsKey("processAction"));
	}

	@Test
	public void testXCSRFToken() throws Exception {
		testPortlet = new TestPortlet(map) {

			@Override
			public void serveResource(
					ResourceRequest resourceRequest,
					ResourceResponse resourceResponse)
				throws IOException {

				PrintWriter printWriter = resourceResponse.getWriter();

				PortletURL portletURL = resourceResponse.createActionURL();

				String queryString = HttpUtil.getQueryString(
					portletURL.toString());

				Map<String, String[]> parameterMap = HttpUtil.getParameterMap(
					queryString);

				String portalAuthenticationToken = MapUtil.getString(
					parameterMap, "p_auth");

				printWriter.write(portalAuthenticationToken);
			}

		};

		setUpPortlet(
			testPortlet, new HashMapDictionary<String, Object>(),
			TEST_PORTLET_ID);

		HttpServletRequest httpServletRequest =
			PortletContainerTestUtil.getHttpServletRequest(group, layout);

		PortletContainerTestUtil.PortalAuthentication portalAuthentication =
			PortletContainerTestUtil.getPortalAuthentication(
				httpServletRequest, layout, TEST_PORTLET_ID);

		map.clear();

		// Make an action request using the portal authentication token

		PortletURL portletURL = new PortletURLImpl(
			httpServletRequest, TEST_PORTLET_ID, layout.getPlid(),
			PortletRequest.ACTION_PHASE);

		String url = portletURL.toString();

		url = HttpUtil.removeParameter(url, "p_auth");

		Map<String, List<String>> headers = new HashMap<>();

		headers.put("Cookie", portalAuthentication.getCookies());
		headers.put(
			"X-CSRF-Token",
			Collections.singletonList(
				portalAuthentication.getPortalAuthenticationToken()));

		Map<String, List<String>> responseMap =
			PortletContainerTestUtil.request(url, headers);

		Assert.assertEquals(
			"200", PortletContainerTestUtil.getString(responseMap, "code"));
		Assert.assertTrue(map.containsKey("processAction"));
	}

}