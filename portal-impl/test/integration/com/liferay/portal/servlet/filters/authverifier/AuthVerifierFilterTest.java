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

package com.liferay.portal.servlet.filters.authverifier;

import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.kernel.security.access.control.AccessControlUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.PortalImpl;
import com.liferay.portal.util.PropsValues;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockFilterConfig;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * @author Eric Yan
 */
public class AuthVerifierFilterTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_authVerifierFilter = new AuthVerifierFilterTest.TestAuthFilter();
		_mockFilterChain = new MockFilterChain();
		_mockHttpServletRequest = new MockHttpServletRequest();
		_mockHttpServletResponse = new MockHttpServletResponse();
		_portal = PortalUtil.getPortal();
	}

	@After
	public void tearDown() {
		_portalUtil.setPortal(_portal);
		AccessControlUtil.setAccessControlContext(null);
	}

	@Test
	public void testHttpsRequiredDisabled() throws Exception {
		MockFilterConfig mockFilterConfig = new MockFilterConfig();

		mockFilterConfig.addInitParameter("https.required", "false");

		_authVerifierFilter.init(mockFilterConfig);

		_authVerifierFilter.processFilter(
			_mockHttpServletRequest, _mockHttpServletResponse,
			_mockFilterChain);

		String redirectURL = _mockHttpServletResponse.getRedirectedUrl();

		Assert.assertNull(redirectURL);
	}

	@Test
	public void testHttpsRequiredWithHttpRequest() throws Exception {
		MockFilterConfig mockFilterConfig = new MockFilterConfig();

		mockFilterConfig.addInitParameter("https.required", "true");

		_authVerifierFilter.init(mockFilterConfig);

		_authVerifierFilter.processFilter(
			_mockHttpServletRequest, _mockHttpServletResponse,
			_mockFilterChain);

		String redirectURL = _mockHttpServletResponse.getRedirectedUrl();

		String expectedRedirectURL = "https://localhost";

		Assert.assertEquals(expectedRedirectURL, redirectURL);
	}

	@Test
	public void testHttpsRequiredWithHttpRequestAndProxyPath()
		throws Exception {

		String portalProxyPath = PropsValues.PORTAL_PROXY_PATH;

		setPortalProperty("PORTAL_PROXY_PATH", "/liferay123");

		_portalUtil.setPortal(new PortalImpl());

		MockFilterConfig mockFilterConfig = new MockFilterConfig();

		mockFilterConfig.addInitParameter("https.required", "true");

		_authVerifierFilter.init(mockFilterConfig);

		_authVerifierFilter.processFilter(
			_mockHttpServletRequest, _mockHttpServletResponse,
			_mockFilterChain);

		setPortalProperty("PORTAL_PROXY_PATH", portalProxyPath);

		String redirectURL = _mockHttpServletResponse.getRedirectedUrl();

		String expectedRedirectURL = "https://localhost/liferay123";

		Assert.assertEquals(expectedRedirectURL, redirectURL);
	}

	@Test
	public void testHttpsRequiredWithHttpRequestAndProxyPathAndRequestURI()
		throws Exception {

		String portalProxyPath = PropsValues.PORTAL_PROXY_PATH;

		setPortalProperty("PORTAL_PROXY_PATH", "/liferay123");

		_portalUtil.setPortal(new PortalImpl());

		_mockHttpServletRequest.setQueryString("a=1");
		_mockHttpServletRequest.setRequestURI("/abc123");

		MockFilterConfig mockFilterConfig = new MockFilterConfig();

		mockFilterConfig.addInitParameter("https.required", "true");

		_authVerifierFilter.init(mockFilterConfig);

		_authVerifierFilter.processFilter(
			_mockHttpServletRequest, _mockHttpServletResponse,
			_mockFilterChain);

		setPortalProperty("PORTAL_PROXY_PATH", portalProxyPath);

		String redirectURL = _mockHttpServletResponse.getRedirectedUrl();

		String expectedRedirectURL = "https://localhost/liferay123/abc123?a=1";

		Assert.assertEquals(expectedRedirectURL, redirectURL);
	}

	@Test
	public void testHttpsRequiredWithHttpRequestAndXForwardedHostAndPortHeader()
		throws Exception {

		boolean webServerForwardedHostEnabled =
			PropsValues.WEB_SERVER_FORWARDED_HOST_ENABLED;
		boolean webServerForwardedPortEnabled =
			PropsValues.WEB_SERVER_FORWARDED_PORT_ENABLED;

		setPortalProperty("WEB_SERVER_FORWARDED_HOST_ENABLED", Boolean.TRUE);
		setPortalProperty("WEB_SERVER_FORWARDED_PORT_ENABLED", Boolean.TRUE);

		_mockHttpServletRequest.addHeader(
			"X-Forwarded-Host", "test.liferay.com");
		_mockHttpServletRequest.addHeader("X-Forwarded-Port", "1234");

		MockFilterConfig mockFilterConfig = new MockFilterConfig();

		mockFilterConfig.addInitParameter("https.required", "true");

		_authVerifierFilter.init(mockFilterConfig);

		_authVerifierFilter.processFilter(
			_mockHttpServletRequest, _mockHttpServletResponse,
			_mockFilterChain);

		setPortalProperty(
			"WEB_SERVER_FORWARDED_HOST_ENABLED", webServerForwardedHostEnabled);
		setPortalProperty(
			"WEB_SERVER_FORWARDED_PORT_ENABLED", webServerForwardedPortEnabled);

		String redirectURL = _mockHttpServletResponse.getRedirectedUrl();

		String expectedRedirectURL = "https://test.liferay.com:1234";

		Assert.assertEquals(expectedRedirectURL, redirectURL);
	}

	@Test
	public void testHttpsRequiredWithHttpRequestAndXForwardedProtoHeader()
		throws Exception {

		boolean webServerForwardedProtocolEnabled =
			PropsValues.WEB_SERVER_FORWARDED_PROTOCOL_ENABLED;

		setPortalProperty(
			"WEB_SERVER_FORWARDED_PROTOCOL_ENABLED", Boolean.TRUE);

		_mockHttpServletRequest.addHeader("X-Forwarded-Proto", Http.HTTPS);

		MockFilterConfig mockFilterConfig = new MockFilterConfig();

		mockFilterConfig.addInitParameter("https.required", "true");

		_authVerifierFilter.init(mockFilterConfig);

		_authVerifierFilter.processFilter(
			_mockHttpServletRequest, _mockHttpServletResponse,
			_mockFilterChain);

		setPortalProperty(
			"WEB_SERVER_FORWARDED_PROTOCOL_ENABLED",
			webServerForwardedProtocolEnabled);

		String redirectURL = _mockHttpServletResponse.getRedirectedUrl();

		Assert.assertNull(redirectURL);
	}

	@Test
	public void testHttpsRequiredWithHttpsRequest() throws Exception {
		_mockHttpServletRequest.setScheme(Http.HTTPS);

		MockFilterConfig mockFilterConfig = new MockFilterConfig();

		mockFilterConfig.addInitParameter("https.required", "true");

		_authVerifierFilter.init(mockFilterConfig);

		_authVerifierFilter.processFilter(
			_mockHttpServletRequest, _mockHttpServletResponse,
			_mockFilterChain);

		String redirectURL = _mockHttpServletResponse.getRedirectedUrl();

		Assert.assertNull(redirectURL);
	}

	protected void setPortalProperty(String propertyName, Object value)
		throws Exception {

		Field field = ReflectionUtil.getDeclaredField(
			PropsValues.class, propertyName);

		field.setAccessible(true);

		Field modifiersField = Field.class.getDeclaredField("modifiers");

		modifiersField.setAccessible(true);
		modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);

		field.set(null, value);
	}

	private AuthVerifierFilter _authVerifierFilter;
	private MockFilterChain _mockFilterChain;
	private MockHttpServletRequest _mockHttpServletRequest;
	private MockHttpServletResponse _mockHttpServletResponse;
	private Portal _portal;
	private final PortalUtil _portalUtil = new PortalUtil();

	private static class TestAuthFilter extends AuthVerifierFilter {
	}

}