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
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.security.auth.AuthTokenWhitelistUtil;
import com.liferay.portal.test.log.CaptureAppender;
import com.liferay.portal.test.log.Log4JLoggerTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.test.PortletContainerTestUtil;
import com.liferay.portal.util.test.PortletContainerTestUtil.Response;
import com.liferay.portlet.PortletURLImpl;
import com.liferay.portlet.SecurityPortletContainerWrapper;
import com.liferay.util.Encryptor;

import java.io.IOException;
import java.io.PrintWriter;

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
		new LiferayIntegrationTestRule();

	@Test
	public void testAuthTokenCheckEnabled() throws Exception {
		Boolean authTokenCheckEnabled = ReflectionTestUtil.getAndSetFieldValue(
			PropsValues.class, "AUTH_TOKEN_CHECK_ENABLED", Boolean.FALSE);

		try {
			setUpPortlet(
				testPortlet, new HashMapDictionary<String, Object>(),
				TEST_PORTLET_ID);

			HttpServletRequest httpServletRequest =
				PortletContainerTestUtil.getHttpServletRequest(group, layout);

			PortletURL portletURL = new PortletURLImpl(
				httpServletRequest, TEST_PORTLET_ID, layout.getPlid(),
				PortletRequest.ACTION_PHASE);

			Response response = PortletContainerTestUtil.request(
				portletURL.toString());

			Assert.assertEquals(200, response.getCode());
			Assert.assertTrue(testPortlet.isActionCalled());
		}
		finally {
			ReflectionTestUtil.setFieldValue(
				PropsValues.class, "AUTH_TOKEN_CHECK_ENABLED",
				authTokenCheckEnabled);
		}
	}

	@Test
	public void testAuthTokenIgnoreOrigins() throws Exception {
		String[] authTokenIgnoreOrigins =
			ReflectionTestUtil.getAndSetFieldValue(
				PropsValues.class, "AUTH_TOKEN_IGNORE_ORIGINS",
				new String[] {SecurityPortletContainerWrapper.class.getName()});

		try {
			AuthTokenWhitelistUtil.resetOriginCSRFWhitelist();

			setUpPortlet(
				testPortlet, new HashMapDictionary<String, Object>(),
				TEST_PORTLET_ID);

			HttpServletRequest httpServletRequest =
				PortletContainerTestUtil.getHttpServletRequest(group, layout);

			PortletURL portletURL = new PortletURLImpl(
				httpServletRequest, TEST_PORTLET_ID, layout.getPlid(),
				PortletRequest.ACTION_PHASE);

			Response response = PortletContainerTestUtil.request(
				portletURL.toString());

			Assert.assertEquals(200, response.getCode());
			Assert.assertTrue(testPortlet.isActionCalled());
		}
		finally {
			ReflectionTestUtil.setFieldValue(
				PropsValues.class, "AUTH_TOKEN_IGNORE_ORIGINS",
				authTokenIgnoreOrigins);

			AuthTokenWhitelistUtil.resetOriginCSRFWhitelist();
		}
	}

	@Test
	public void testAuthTokenIgnorePortlets() throws Exception {
		String[] authTokenIgnorePortlets =
			ReflectionTestUtil.getAndSetFieldValue(
				PropsValues.class, "AUTH_TOKEN_IGNORE_PORTLETS",
				new String[] {TEST_PORTLET_ID});

		try {
			AuthTokenWhitelistUtil.resetPortletCSRFWhitelist();

			setUpPortlet(
				testPortlet, new HashMapDictionary<String, Object>(),
				TEST_PORTLET_ID);

			HttpServletRequest httpServletRequest =
				PortletContainerTestUtil.getHttpServletRequest(group, layout);

			PortletURL portletURL = new PortletURLImpl(
				httpServletRequest, TEST_PORTLET_ID, layout.getPlid(),
				PortletRequest.ACTION_PHASE);

			Response response = PortletContainerTestUtil.request(
				portletURL.toString());

			Assert.assertEquals(200, response.getCode());
			Assert.assertTrue(testPortlet.isActionCalled());
		}
		finally {
			ReflectionTestUtil.setFieldValue(
				PropsValues.class, "AUTH_TOKEN_IGNORE_PORTLETS",
				authTokenIgnorePortlets);

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

		Response response = PortletContainerTestUtil.request(
			portletURL.toString());

		Assert.assertEquals(200, response.getCode());
		Assert.assertTrue(testPortlet.isActionCalled());
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

			Response response = PortletContainerTestUtil.request(url);

			List<LoggingEvent> loggingEvents =
				captureAppender.getLoggingEvents();

			Assert.assertEquals(1, loggingEvents.size());

			LoggingEvent loggingEvent = loggingEvents.get(0);

			Assert.assertEquals(
				"User 0 is not allowed to access URL " +
					url.substring(0, url.indexOf('?')) + " and portlet " +
						TEST_PORTLET_ID,
				loggingEvent.getMessage());
			Assert.assertEquals(200, response.getCode());
			Assert.assertFalse(testPortlet.isActionCalled());
		}
	}

	@Test
	public void testPortalAuthenticationToken() throws Exception {
		testPortlet = new TestPortlet() {

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

		Response response = PortletContainerTestUtil.getPortalAuthentication(
			httpServletRequest, layout, TEST_PORTLET_ID);

		testPortlet.reset();

		// Make an action request using the portal authentication token

		PortletURL portletURL = new PortletURLImpl(
			httpServletRequest, TEST_PORTLET_ID, layout.getPlid(),
			PortletRequest.ACTION_PHASE);

		String url = portletURL.toString();

		url = HttpUtil.setParameter(url, "p_auth", response.getBody());

		Map<String, List<String>> headers = new HashMap<>();

		headers.put("Cookie", response.getCookies());

		response = PortletContainerTestUtil.request(url, headers);

		Assert.assertEquals(200, response.getCode());
		Assert.assertTrue(testPortlet.isActionCalled());
	}

	@Test
	public void testPortalAuthenticationTokenSecret() throws Exception {
		String authTokenSharedSecret = ReflectionTestUtil.getAndSetFieldValue(
			PropsValues.class, "AUTH_TOKEN_SHARED_SECRET", "test");

		try {
			setUpPortlet(
				testPortlet, new HashMapDictionary<String, Object>(),
				TEST_PORTLET_ID);

			HttpServletRequest httpServletRequest =
				PortletContainerTestUtil.getHttpServletRequest(group, layout);

			PortletURL portletURL = new PortletURLImpl(
				httpServletRequest, TEST_PORTLET_ID, layout.getPlid(),
				PortletRequest.ACTION_PHASE);

			portletURL.setParameter("p_auth_secret", Encryptor.digest("test"));

			Response response = PortletContainerTestUtil.request(
				portletURL.toString());

			Assert.assertEquals(200, response.getCode());
			Assert.assertTrue(testPortlet.isActionCalled());
		}
		finally {
			ReflectionTestUtil.setFieldValue(
				PropsValues.class, "AUTH_TOKEN_SHARED_SECRET",
				authTokenSharedSecret);
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

		Response response = PortletContainerTestUtil.request(
			portletURL.toString());

		Assert.assertEquals(200, response.getCode());
		Assert.assertTrue(testPortlet.isActionCalled());
	}

	@Test
	public void testXCSRFToken() throws Exception {
		testPortlet = new TestPortlet() {

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

		Response response = PortletContainerTestUtil.getPortalAuthentication(
			httpServletRequest, layout, TEST_PORTLET_ID);

		testPortlet.reset();

		// Make an action request using the portal authentication token

		PortletURL portletURL = new PortletURLImpl(
			httpServletRequest, TEST_PORTLET_ID, layout.getPlid(),
			PortletRequest.ACTION_PHASE);

		String url = portletURL.toString();

		url = HttpUtil.removeParameter(url, "p_auth");

		Map<String, List<String>> headers = new HashMap<>();

		headers.put("Cookie", response.getCookies());
		headers.put(
			"X-CSRF-Token", Collections.singletonList(response.getBody()));

		response = PortletContainerTestUtil.request(url, headers);

		Assert.assertEquals(200, response.getCode());
		Assert.assertTrue(testPortlet.isActionCalled());
	}

}