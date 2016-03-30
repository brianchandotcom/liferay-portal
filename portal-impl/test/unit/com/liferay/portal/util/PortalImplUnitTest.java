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

package com.liferay.portal.util;

import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.mockito.ReturnArgumentCalledAnswer;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import javax.servlet.http.HttpSession;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mockito;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Miguel Pastor
 */
@PowerMockIgnore("javax.xml.datatype.*")
@PrepareForTest({HttpUtil.class, PropsValues.class})
@RunWith(PowerMockRunner.class)
public class PortalImplUnitTest extends PowerMockito {

	@Test
	public void testIsSecureWithHttpsInitialFalse() throws Exception {
		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setSecure(true);

		HttpSession mockSession = mockHttpServletRequest.getSession();

		mockSession.setAttribute(WebKeys.HTTPS_INITIAL, Boolean.FALSE);

		boolean originalSecurityAuth =
			PropsValues.COMPANY_SECURITY_AUTH_REQUIRES_HTTPS;
		boolean originalEnablePhising =
			PropsValues.SESSION_ENABLE_PHISHING_PROTECTION;

		try {
			setPropsValuesValue("COMPANY_SECURITY_AUTH_REQUIRES_HTTPS", true);
			setPropsValuesValue("SESSION_ENABLE_PHISHING_PROTECTION", false);

			boolean secure = _portalImpl.isSecure(mockHttpServletRequest);

			Assert.assertFalse(secure);
		}
		finally {
			setPropsValuesValue(
				"COMPANY_SECURITY_AUTH_REQUIRES_HTTPS", originalSecurityAuth);
			setPropsValuesValue(
				"SESSION_ENABLE_PHISHING_PROTECTION", originalEnablePhising);
		}
	}

	@Test
	public void testIsSecureWithHttpsInitialFalseXForwardedHttps()
		throws Exception {

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.addHeader("X-Forwarded-Proto", "https");
		mockHttpServletRequest.setSecure(false);

		HttpSession mockSession = mockHttpServletRequest.getSession();

		mockSession.setAttribute(WebKeys.HTTPS_INITIAL, Boolean.FALSE);

		boolean originalSecurityAuth =
			PropsValues.COMPANY_SECURITY_AUTH_REQUIRES_HTTPS;
		boolean originalEnablePhising =
			PropsValues.SESSION_ENABLE_PHISHING_PROTECTION;
		boolean originalForwardedProto =
			PropsValues.WEB_SERVER_FORWARDED_PROTO_ENABLED;

		try {
			setPropsValuesValue("COMPANY_SECURITY_AUTH_REQUIRES_HTTPS", false);
			setPropsValuesValue("SESSION_ENABLE_PHISHING_PROTECTION", true);
			setPropsValuesValue("WEB_SERVER_FORWARDED_PROTO_ENABLED", true);
			boolean secure = _portalImpl.isSecure(mockHttpServletRequest);

			Assert.assertTrue(secure);
		}
		finally {
			setPropsValuesValue(
				"COMPANY_SECURITY_AUTH_REQUIRES_HTTPS", originalSecurityAuth);
			setPropsValuesValue(
				"SESSION_ENABLE_PHISHING_PROTECTION", originalEnablePhising);
			setPropsValuesValue(
				"WEB_SERVER_FORWARDED_PROTO_ENABLED", originalForwardedProto);
		}
	}

	@Test
	public void testIsSecureWithHttpsInitialTrue() throws Exception {
		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setSecure(true);

		HttpSession mockSession = mockHttpServletRequest.getSession();

		mockSession.setAttribute(WebKeys.HTTPS_INITIAL, Boolean.TRUE);

		boolean originalSecurityAuth =
			PropsValues.COMPANY_SECURITY_AUTH_REQUIRES_HTTPS;
		boolean originalEnablePhising =
			PropsValues.SESSION_ENABLE_PHISHING_PROTECTION;

		try {
			setPropsValuesValue("COMPANY_SECURITY_AUTH_REQUIRES_HTTPS", true);
			setPropsValuesValue("SESSION_ENABLE_PHISHING_PROTECTION", false);

			boolean secure = _portalImpl.isSecure(mockHttpServletRequest);

			Assert.assertTrue(secure);
		}
		finally {
			setPropsValuesValue(
				"COMPANY_SECURITY_AUTH_REQUIRES_HTTPS", originalSecurityAuth);
			setPropsValuesValue(
				"SESSION_ENABLE_PHISHING_PROTECTION", originalEnablePhising);
		}
	}

	@Test
	public void testIsSecureWithHttpsInitialTrueCustomXForwardedHttps()
		throws Exception {

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.addHeader("X-Forwarded-Custom-Proto", "https");
		mockHttpServletRequest.setSecure(false);

		boolean originalSecurityAuth =
			PropsValues.COMPANY_SECURITY_AUTH_REQUIRES_HTTPS;
		boolean originalEnablePhising =
			PropsValues.SESSION_ENABLE_PHISHING_PROTECTION;
		boolean originalForwardedProto =
			PropsValues.WEB_SERVER_FORWARDED_PROTO_ENABLED;

		String originalForwardedProtoHeader =
			PropsValues.WEB_SERVER_FORWARDED_PROTO_HEADER;

		try {
			setPropsValuesValue("COMPANY_SECURITY_AUTH_REQUIRES_HTTPS", true);
			setPropsValuesValue("SESSION_ENABLE_PHISHING_PROTECTION", false);
			setPropsValuesValue("WEB_SERVER_FORWARDED_PROTO_ENABLED", true);
			setPropsValuesValue(
				"WEB_SERVER_FORWARDED_PROTO_HEADER",
				"X-Forwarded-Custom-Proto");

			boolean secure = _portalImpl.isSecure(mockHttpServletRequest);

			Assert.assertTrue(secure);
		}
		finally {
			setPropsValuesValue(
				"COMPANY_SECURITY_AUTH_REQUIRES_HTTPS", originalSecurityAuth);
			setPropsValuesValue(
				"SESSION_ENABLE_PHISHING_PROTECTION", originalEnablePhising);
			setPropsValuesValue(
				"WEB_SERVER_FORWARDED_PROTO_ENABLED", originalForwardedProto);
			setPropsValuesValue(
				"WEB_SERVER_FORWARDED_PROTO_HEADER",
				originalForwardedProtoHeader);
		}
	}

	@Test
	public void testIsSecureWithHttpsInitialTrueXForwardedHttps()
		throws Exception {

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.addHeader("X-Forwarded-Proto", "https");
		mockHttpServletRequest.setSecure(false);

		HttpSession mockSession = mockHttpServletRequest.getSession();

		mockSession.setAttribute(WebKeys.HTTPS_INITIAL, Boolean.TRUE);

		boolean originalSecurityAuth =
			PropsValues.COMPANY_SECURITY_AUTH_REQUIRES_HTTPS;
		boolean originalEnablePhising =
			PropsValues.SESSION_ENABLE_PHISHING_PROTECTION;
		boolean originalForwardedProto =
			PropsValues.WEB_SERVER_FORWARDED_PROTO_ENABLED;

		try {
			setPropsValuesValue("COMPANY_SECURITY_AUTH_REQUIRES_HTTPS", true);
			setPropsValuesValue("SESSION_ENABLE_PHISHING_PROTECTION", false);
			setPropsValuesValue("WEB_SERVER_FORWARDED_PROTO_ENABLED", true);

			boolean secure = _portalImpl.isSecure(mockHttpServletRequest);

			Assert.assertTrue(secure);
		}
		finally {
			setPropsValuesValue(
				"COMPANY_SECURITY_AUTH_REQUIRES_HTTPS", originalSecurityAuth);
			setPropsValuesValue(
				"SESSION_ENABLE_PHISHING_PROTECTION", originalEnablePhising);
			setPropsValuesValue(
				"WEB_SERVER_FORWARDED_PROTO_ENABLED", originalForwardedProto);
		}
	}

	@Test
	public void testIsSecureWithSecureRequest() {
		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setSecure(true);

		boolean secure = _portalImpl.isSecure(mockHttpServletRequest);

		Assert.assertTrue(secure);
	}

	@Test
	public void testPort() {
		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setServerPort(8080);

		int newServerPort = _portalImpl.getForwardedPort(
			mockHttpServletRequest);

		Assert.assertEquals(8080, newServerPort);
	}

	@Test
	public void testPortWithCustomXForwardedPort() throws Exception {
		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.addHeader("X-Forwarded-Custom-Port", 8081);

		mockHttpServletRequest.setServerPort(8080);

		boolean originalForwardedPort =
			PropsValues.WEB_SERVER_FORWARDED_PORT_ENABLED;

		String originalForwardedPortHeader =
			PropsValues.WEB_SERVER_FORWARDED_PORT_HEADER;

		try {
			setPropsValuesValue("WEB_SERVER_FORWARDED_PORT_ENABLED", false);
			setPropsValuesValue(
				"WEB_SERVER_FORWARDED_PORT_HEADER", "X-Forwarded-Custom-Port");

			int newServerPort = _portalImpl.getForwardedPort(
				mockHttpServletRequest);

			Assert.assertEquals(8080, newServerPort);
		}
		finally {
			setPropsValuesValue(
				"WEB_SERVER_FORWARDED_PORT_ENABLED", originalForwardedPort);
			setPropsValuesValue(
				"WEB_SERVER_FORWARDED_PORT_HEADER",
				originalForwardedPortHeader);
		}
	}

	@Test
	public void testPortWithXForwardedPortDisabled() throws Exception {
		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.addHeader("X-Forwarded-Port", 8081);

		mockHttpServletRequest.setServerPort(8080);

		boolean originalForwardedPort =
			PropsValues.WEB_SERVER_FORWARDED_PORT_ENABLED;

		try {
			setPropsValuesValue("WEB_SERVER_FORWARDED_PORT_ENABLED", false);

			int newServerPort = _portalImpl.getForwardedPort(
				mockHttpServletRequest);

			Assert.assertEquals(8080, newServerPort);
		}
		finally {
			setPropsValuesValue(
				"WEB_SERVER_FORWARDED_PORT_ENABLED", originalForwardedPort);
		}
	}

	@Test
	public void testPortWithXForwardedPortEnabled() throws Exception {
		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.addHeader("X-Forwarded-Port", "8081");

		mockHttpServletRequest.setServerPort(8080);

		boolean originalForwardedPort =
			PropsValues.WEB_SERVER_FORWARDED_PORT_ENABLED;

		try {
			setPropsValuesValue("WEB_SERVER_FORWARDED_PORT_ENABLED", true);

			int newServerPort = _portalImpl.getForwardedPort(
				mockHttpServletRequest);

			Assert.assertEquals(8081, newServerPort);
		}
		finally {
			setPropsValuesValue(
				"WEB_SERVER_FORWARDED_PORT_ENABLED", originalForwardedPort);
		}
	}

	@Test
	public void testServerName() {
		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setServerName("serverName");

		String newServerName = _portalImpl.getForwardedHost(
			mockHttpServletRequest);

		Assert.assertEquals("serverName", newServerName);
	}

	@Test
	public void testServerNameWithCustomXForwardedHostEnabled()
		throws Exception {

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.addHeader(
			"X-Forwarded-Custom-Host", "forwardedServer");

		mockHttpServletRequest.setServerName("serverName");

		boolean originalForwardedHost =
			PropsValues.WEB_SERVER_FORWARDED_HOST_ENABLED;

		String originalForwardedHostHeader =
			PropsValues.WEB_SERVER_FORWARDED_HOST_HEADER;

		try {
			setPropsValuesValue("WEB_SERVER_FORWARDED_HOST_ENABLED", true);
			setPropsValuesValue(
				"WEB_SERVER_FORWARDED_HOST_HEADER", "X-Forwarded-Custom-Host");

			String newServerName = _portalImpl.getForwardedHost(
				mockHttpServletRequest);

			Assert.assertEquals("forwardedServer", newServerName);
		}
		finally {
			setPropsValuesValue(
				"WEB_SERVER_FORWARDED_HOST_ENABLED", originalForwardedHost);
			setPropsValuesValue(
				"WEB_SERVER_FORWARDED_HOST_HEADER",
				originalForwardedHostHeader);
		}
	}

	@Test
	public void testServerNameWithXForwardedHostDisabled() throws Exception {
		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.addHeader("X-Forwarded-Host", "forwardedServer");

		mockHttpServletRequest.setServerName("serverName");

		boolean originalForwardedHost =
			PropsValues.WEB_SERVER_FORWARDED_HOST_ENABLED;

		try {
			setPropsValuesValue("WEB_SERVER_FORWARDED_HOST_ENABLED", false);

			String newServerName = _portalImpl.getForwardedHost(
				mockHttpServletRequest);

			Assert.assertEquals("serverName", newServerName);
		}
		finally {
			setPropsValuesValue(
				"WEB_SERVER_FORWARDED_HOST_ENABLED", originalForwardedHost);
		}
	}

	@Test
	public void testServerNameWithXForwardedHostEnabled() throws Exception {
		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.addHeader("X-Forwarded-Host", "forwardedServer");

		mockHttpServletRequest.setServerName("serverName");

		boolean originalForwardedHost =
			PropsValues.WEB_SERVER_FORWARDED_HOST_ENABLED;

		try {
			setPropsValuesValue("WEB_SERVER_FORWARDED_HOST_ENABLED", true);

			String newServerName = _portalImpl.getForwardedHost(
				mockHttpServletRequest);

			Assert.assertEquals("forwardedServer", newServerName);
		}
		finally {
			setPropsValuesValue(
				"WEB_SERVER_FORWARDED_HOST_ENABLED", originalForwardedHost);
		}
	}

	@Test
	public void testUpdateRedirectRemoveLayoutURL() {
		mockStatic(HttpUtil.class);

		when(
			HttpUtil.getQueryString(Mockito.anyString())
		).thenReturn(
			StringPool.BLANK
		);

		when(
			HttpUtil.getParameter(
				Mockito.anyString(), Mockito.anyString(), Mockito.eq(false))
		).thenReturn(
			StringPool.BLANK
		);

		when(
			HttpUtil.encodeURL(Mockito.anyString())
		).thenAnswer(
			new ReturnArgumentCalledAnswer<String>(0)
		);

		when(
			HttpUtil.getPath(Mockito.anyString())
		).thenAnswer(
			new ReturnArgumentCalledAnswer<String>(0)
		);

		Assert.assertEquals(
			"/web/group",
			_portalImpl.updateRedirect(
				"/web/group/layout", "/group/layout", "/group"));

		verifyStatic();
	}

	protected void setPropsValuesValue(String fieldName, Object value)
		throws Exception {

		Field field = field(PropsValues.class, fieldName);

		field.setAccessible(true);

		Field modifiersField = Field.class.getDeclaredField("modifiers");

		modifiersField.setAccessible(true);
		modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);

		field.set(PropsValues.class, value);
	}

	private final PortalImpl _portalImpl = new PortalImpl();

}