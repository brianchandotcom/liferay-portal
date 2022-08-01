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

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.portlet.InvokerPortlet;
import com.liferay.portal.kernel.security.auth.AlwaysAllowDoAsUser;
import com.liferay.portal.kernel.servlet.DummyHttpServletResponse;
import com.liferay.portal.kernel.servlet.DynamicServletRequest;
import com.liferay.portal.kernel.servlet.PersistentHttpServletRequestWrapper;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.upgrade.MockPortletPreferences;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HttpComponentsUtil;
import com.liferay.portal.kernel.util.LayoutTypePortletFactoryUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.impl.LayoutImpl;
import com.liferay.portal.model.impl.PortletAppImpl;
import com.liferay.portal.model.impl.PortletImpl;
import com.liferay.portal.model.impl.UserImpl;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.portal.theme.ThemeDisplayFactory;
import com.liferay.portlet.ActionRequestFactory;
import com.liferay.portlet.ActionResponseFactory;
import com.liferay.portlet.internal.ActionRequestImpl;
import com.liferay.portlet.internal.ActionResponseImpl;
import com.liferay.portlet.internal.MutableRenderParametersImpl;
import com.liferay.portlet.test.MockLiferayPortletContext;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.EventRequest;
import javax.portlet.EventResponse;
import javax.portlet.HeaderRequest;
import javax.portlet.HeaderResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.portlet.PortletException;
import javax.portlet.PortletMode;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.portlet.WindowState;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

import org.mockito.MockedStatic;
import org.mockito.Mockito;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Miguel Pastor
 */
public class PortalImplUnitTest {

	@ClassRule
	public static LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testCopyRequestParametersWithoutPassword1Password2()
		throws PortletException {

		Map<String, String[]> params = _createMapParams();

		Enumeration<String> enumeration = _createEnumerationParams();

		ActionRequest actionRequest = _createActionRequestMock(
			params, enumeration);

		MockedStatic<PortalUtil> portalUtilMock = Mockito.mockStatic(
			PortalUtil.class);

		ActionResponse actionResponse = _createActionResponse(portalUtilMock);

		_portalImpl.copyRequestParameters(actionRequest, actionResponse);

		portalUtilMock.close();

		_assertActionResponse(actionResponse, params);
	}

	@Test
	public void testCopyRequestParametersWithPassword1Password2()
		throws PortletException {

		Map<String, String[]> params = _createMapParams();

		params.put(_PASSWORD1, new String[] {"abc_123"});
		params.put(_PASSWORD2, new String[] {"def_456"});

		Enumeration<String> enumeration = _createEnumerationParams();

		ActionRequest actionRequestMock = _createActionRequestMock(
			params, enumeration);

		MockedStatic<PortalUtil> portalUtilMock = Mockito.mockStatic(
			PortalUtil.class);

		ActionResponse actionResponse = _createActionResponse(portalUtilMock);

		_portalImpl.copyRequestParameters(actionRequestMock, actionResponse);

		portalUtilMock.close();

		_assertActionResponse(actionResponse, params);
	}

	@Test
	public void testGetForwardedHost() {
		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setServerName("serverName");

		Assert.assertEquals(
			"serverName", _portalImpl.getForwardedHost(mockHttpServletRequest));
	}

	@Test
	public void testGetForwardedHostWithCustomXForwardedHostEnabled()
		throws Exception {

		boolean webServerForwardedHostEnabled =
			PropsValues.WEB_SERVER_FORWARDED_HOST_ENABLED;
		String webServerForwardedHostHeader =
			PropsValues.WEB_SERVER_FORWARDED_HOST_HEADER;

		try {
			setPropsValuesValue("WEB_SERVER_FORWARDED_HOST_ENABLED", true);
			setPropsValuesValue(
				"WEB_SERVER_FORWARDED_HOST_HEADER", "X-Forwarded-Custom-Host");

			MockHttpServletRequest mockHttpServletRequest =
				new MockHttpServletRequest();

			mockHttpServletRequest.addHeader(
				"X-Forwarded-Custom-Host", "forwardedServer");
			mockHttpServletRequest.setServerName("serverName");

			Assert.assertEquals(
				"forwardedServer",
				_portalImpl.getForwardedHost(mockHttpServletRequest));
		}
		finally {
			setPropsValuesValue(
				"WEB_SERVER_FORWARDED_HOST_ENABLED",
				webServerForwardedHostEnabled);
			setPropsValuesValue(
				"WEB_SERVER_FORWARDED_HOST_HEADER",
				webServerForwardedHostHeader);
		}
	}

	@Test
	public void testGetForwardedHostWithXForwardedHostDisabled()
		throws Exception {

		boolean webServerForwardedHostEnabled =
			PropsValues.WEB_SERVER_FORWARDED_HOST_ENABLED;

		try {
			setPropsValuesValue("WEB_SERVER_FORWARDED_HOST_ENABLED", false);

			MockHttpServletRequest mockHttpServletRequest =
				new MockHttpServletRequest();

			mockHttpServletRequest.addHeader(
				"X-Forwarded-Host", "forwardedServer");
			mockHttpServletRequest.setServerName("serverName");

			Assert.assertEquals(
				"serverName",
				_portalImpl.getForwardedHost(mockHttpServletRequest));
		}
		finally {
			setPropsValuesValue(
				"WEB_SERVER_FORWARDED_HOST_ENABLED",
				webServerForwardedHostEnabled);
		}
	}

	@Test
	public void testGetForwardedHostWithXForwardedHostEnabled()
		throws Exception {

		boolean webServerForwardedHostEnabled =
			PropsValues.WEB_SERVER_FORWARDED_HOST_ENABLED;

		try {
			setPropsValuesValue("WEB_SERVER_FORWARDED_HOST_ENABLED", true);

			MockHttpServletRequest mockHttpServletRequest =
				new MockHttpServletRequest();

			mockHttpServletRequest.addHeader(
				"X-Forwarded-Host", "forwardedServer");
			mockHttpServletRequest.setServerName("serverName");

			Assert.assertEquals(
				"forwardedServer",
				_portalImpl.getForwardedHost(mockHttpServletRequest));
		}
		finally {
			setPropsValuesValue(
				"WEB_SERVER_FORWARDED_HOST_ENABLED",
				webServerForwardedHostEnabled);
		}
	}

	@Test
	public void testGetForwardedPort() {
		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setServerPort(8080);

		Assert.assertEquals(
			8080, _portalImpl.getForwardedPort(mockHttpServletRequest));
	}

	@Test
	public void testGetForwardedPortWithCustomXForwardedPort()
		throws Exception {

		boolean webServerForwardedPortEnabled =
			PropsValues.WEB_SERVER_FORWARDED_PORT_ENABLED;
		String webServerForwardedPortHeader =
			PropsValues.WEB_SERVER_FORWARDED_PORT_HEADER;

		try {
			setPropsValuesValue("WEB_SERVER_FORWARDED_PORT_ENABLED", false);
			setPropsValuesValue(
				"WEB_SERVER_FORWARDED_PORT_HEADER", "X-Forwarded-Custom-Port");

			MockHttpServletRequest mockHttpServletRequest =
				new MockHttpServletRequest();

			mockHttpServletRequest.addHeader("X-Forwarded-Custom-Port", 8081);
			mockHttpServletRequest.setServerPort(8080);

			Assert.assertEquals(
				8080, _portalImpl.getForwardedPort(mockHttpServletRequest));
		}
		finally {
			setPropsValuesValue(
				"WEB_SERVER_FORWARDED_PORT_ENABLED",
				webServerForwardedPortEnabled);
			setPropsValuesValue(
				"WEB_SERVER_FORWARDED_PORT_HEADER",
				webServerForwardedPortHeader);
		}
	}

	@Test
	public void testGetForwardedPortWithXForwardedPortDisabled()
		throws Exception {

		boolean webServerForwardedHostEnabled =
			PropsValues.WEB_SERVER_FORWARDED_PORT_ENABLED;

		try {
			setPropsValuesValue("WEB_SERVER_FORWARDED_PORT_ENABLED", false);

			MockHttpServletRequest mockHttpServletRequest =
				new MockHttpServletRequest();

			mockHttpServletRequest.addHeader("X-Forwarded-Port", 8081);
			mockHttpServletRequest.setServerPort(8080);

			Assert.assertEquals(
				8080, _portalImpl.getForwardedPort(mockHttpServletRequest));
		}
		finally {
			setPropsValuesValue(
				"WEB_SERVER_FORWARDED_PORT_ENABLED",
				webServerForwardedHostEnabled);
		}
	}

	@Test
	public void testGetForwardedPortWithXForwardedPortEnabled()
		throws Exception {

		boolean webServerForwardedPortEnabled =
			PropsValues.WEB_SERVER_FORWARDED_PORT_ENABLED;

		try {
			setPropsValuesValue("WEB_SERVER_FORWARDED_PORT_ENABLED", true);

			MockHttpServletRequest mockHttpServletRequest =
				new MockHttpServletRequest();

			mockHttpServletRequest.addHeader("X-Forwarded-Port", "8081");
			mockHttpServletRequest.setServerPort(8080);

			Assert.assertEquals(
				8081, _portalImpl.getForwardedPort(mockHttpServletRequest));
		}
		finally {
			setPropsValuesValue(
				"WEB_SERVER_FORWARDED_PORT_ENABLED",
				webServerForwardedPortEnabled);
		}
	}

	@Test
	public void testGetHost() {
		_assertGetHost("123.1.1.1", "123.1.1.1");
		_assertGetHost("123.1.1.1:80", "123.1.1.1");
		_assertGetHost("[0:0:0:0:0:0:0:1]", "0:0:0:0:0:0:0:1");
		_assertGetHost("[0:0:0:0:0:0:0:1]:80", "0:0:0:0:0:0:0:1");
		_assertGetHost("[::1]", "::1");
		_assertGetHost("[::1]:80", "::1");
		_assertGetHost("abc.com", "abc.com");
		_assertGetHost("abc.com:80", "abc.com");
	}

	@Test
	public void testGetOriginalServletRequest() {
		HttpServletRequest httpServletRequest = new MockHttpServletRequest();

		Assert.assertSame(
			httpServletRequest,
			_portalImpl.getOriginalServletRequest(httpServletRequest));

		HttpServletRequestWrapper requestWrapper1 =
			new HttpServletRequestWrapper(httpServletRequest);

		Assert.assertSame(
			httpServletRequest,
			_portalImpl.getOriginalServletRequest(requestWrapper1));

		HttpServletRequestWrapper requestWrapper2 =
			new HttpServletRequestWrapper(requestWrapper1);

		Assert.assertSame(
			httpServletRequest,
			_portalImpl.getOriginalServletRequest(requestWrapper2));

		HttpServletRequestWrapper requestWrapper3 =
			new PersistentHttpServletRequestWrapper1(requestWrapper2);

		HttpServletRequest originalHttpServletRequest =
			_portalImpl.getOriginalServletRequest(requestWrapper3);

		Assert.assertSame(
			PersistentHttpServletRequestWrapper1.class,
			originalHttpServletRequest.getClass());
		Assert.assertNotSame(requestWrapper3, originalHttpServletRequest);
		Assert.assertSame(
			httpServletRequest, getWrappedRequest(originalHttpServletRequest));

		HttpServletRequestWrapper requestWrapper4 =
			new PersistentHttpServletRequestWrapper2(requestWrapper3);

		originalHttpServletRequest = _portalImpl.getOriginalServletRequest(
			requestWrapper4);

		Assert.assertSame(
			PersistentHttpServletRequestWrapper2.class,
			originalHttpServletRequest.getClass());
		Assert.assertNotSame(requestWrapper4, originalHttpServletRequest);

		originalHttpServletRequest = getWrappedRequest(
			originalHttpServletRequest);

		Assert.assertSame(
			PersistentHttpServletRequestWrapper1.class,
			originalHttpServletRequest.getClass());
		Assert.assertNotSame(requestWrapper3, originalHttpServletRequest);
		Assert.assertSame(
			httpServletRequest, getWrappedRequest(originalHttpServletRequest));
	}

	@Test
	public void testGetUserId() {
		BundleContext bundleContext = SystemBundleUtil.getBundleContext();

		boolean[] calledAlwaysAllowDoAsUser = {false};

		ServiceRegistration<AlwaysAllowDoAsUser> serviceRegistration =
			bundleContext.registerService(
				AlwaysAllowDoAsUser.class,
				(AlwaysAllowDoAsUser)ProxyUtil.newProxyInstance(
					AlwaysAllowDoAsUser.class.getClassLoader(),
					new Class<?>[] {AlwaysAllowDoAsUser.class},
					(proxy, method, args) -> {
						calledAlwaysAllowDoAsUser[0] = true;

						if (Objects.equals(method.getName(), "equals")) {
							return true;
						}

						if (Objects.equals(method.getName(), "hashcode")) {
							return 0;
						}

						return Collections.emptyList();
					}),
				null);

		try {
			MockHttpServletRequest mockHttpServletRequest =
				new MockHttpServletRequest();

			mockHttpServletRequest.setParameter("doAsUserId", "1");

			_portalImpl.getUserId(mockHttpServletRequest);

			Assert.assertTrue(
				"AlwaysAllowDoAsUser not called", calledAlwaysAllowDoAsUser[0]);

			calledAlwaysAllowDoAsUser[0] = false;

			_portalImpl.getUserId(new MockHttpServletRequest());

			Assert.assertFalse(
				"AlwaysAllowDoAsUser should not be called",
				calledAlwaysAllowDoAsUser[0]);
		}
		finally {
			serviceRegistration.unregister();
		}
	}

	@Test
	public void testIsSecureWithHttpsInitialFalse() throws Exception {
		boolean companySecurityAuthRequiresHttps =
			PropsValues.COMPANY_SECURITY_AUTH_REQUIRES_HTTPS;
		boolean sessionEnablePhishingProtection =
			PropsValues.SESSION_ENABLE_PHISHING_PROTECTION;

		try {
			setPropsValuesValue("COMPANY_SECURITY_AUTH_REQUIRES_HTTPS", true);
			setPropsValuesValue("SESSION_ENABLE_PHISHING_PROTECTION", false);

			MockHttpServletRequest mockHttpServletRequest =
				new MockHttpServletRequest();

			mockHttpServletRequest.setSecure(true);

			HttpSession httpSession = mockHttpServletRequest.getSession();

			httpSession.setAttribute(WebKeys.HTTPS_INITIAL, Boolean.FALSE);

			Assert.assertFalse(_portalImpl.isSecure(mockHttpServletRequest));
		}
		finally {
			setPropsValuesValue(
				"COMPANY_SECURITY_AUTH_REQUIRES_HTTPS",
				companySecurityAuthRequiresHttps);
			setPropsValuesValue(
				"SESSION_ENABLE_PHISHING_PROTECTION",
				sessionEnablePhishingProtection);
		}
	}

	@Test
	public void testIsSecureWithHttpsInitialFalseXForwardedHttps()
		throws Exception {

		boolean companySecurityAuthRequiresHttps =
			PropsValues.COMPANY_SECURITY_AUTH_REQUIRES_HTTPS;
		boolean sessionEnablePhishingProtection =
			PropsValues.SESSION_ENABLE_PHISHING_PROTECTION;
		boolean webServerForwardedProtocolEnabled =
			PropsValues.WEB_SERVER_FORWARDED_PROTOCOL_ENABLED;

		try {
			setPropsValuesValue("COMPANY_SECURITY_AUTH_REQUIRES_HTTPS", false);
			setPropsValuesValue("SESSION_ENABLE_PHISHING_PROTECTION", true);
			setPropsValuesValue("WEB_SERVER_FORWARDED_PROTOCOL_ENABLED", true);

			MockHttpServletRequest mockHttpServletRequest =
				new MockHttpServletRequest();

			mockHttpServletRequest.addHeader("X-Forwarded-Proto", "https");
			mockHttpServletRequest.setSecure(false);

			HttpSession httpSession = mockHttpServletRequest.getSession();

			httpSession.setAttribute(WebKeys.HTTPS_INITIAL, Boolean.FALSE);

			Assert.assertTrue(_portalImpl.isSecure(mockHttpServletRequest));
		}
		finally {
			setPropsValuesValue(
				"COMPANY_SECURITY_AUTH_REQUIRES_HTTPS",
				companySecurityAuthRequiresHttps);
			setPropsValuesValue(
				"SESSION_ENABLE_PHISHING_PROTECTION",
				sessionEnablePhishingProtection);
			setPropsValuesValue(
				"WEB_SERVER_FORWARDED_PROTOCOL_ENABLED",
				webServerForwardedProtocolEnabled);
		}
	}

	@Test
	public void testIsSecureWithHttpsInitialTrue() throws Exception {
		boolean companySecurityAuthRequiresHttps =
			PropsValues.COMPANY_SECURITY_AUTH_REQUIRES_HTTPS;
		boolean sessionEnablePhishingProtection =
			PropsValues.SESSION_ENABLE_PHISHING_PROTECTION;

		try {
			setPropsValuesValue("COMPANY_SECURITY_AUTH_REQUIRES_HTTPS", true);
			setPropsValuesValue("SESSION_ENABLE_PHISHING_PROTECTION", false);

			MockHttpServletRequest mockHttpServletRequest =
				new MockHttpServletRequest();

			mockHttpServletRequest.setSecure(true);

			HttpSession httpSession = mockHttpServletRequest.getSession();

			httpSession.setAttribute(WebKeys.HTTPS_INITIAL, Boolean.TRUE);

			Assert.assertTrue(_portalImpl.isSecure(mockHttpServletRequest));
		}
		finally {
			setPropsValuesValue(
				"COMPANY_SECURITY_AUTH_REQUIRES_HTTPS",
				companySecurityAuthRequiresHttps);
			setPropsValuesValue(
				"SESSION_ENABLE_PHISHING_PROTECTION",
				sessionEnablePhishingProtection);
		}
	}

	@Test
	public void testIsSecureWithHttpsInitialTrueCustomXForwardedHttps()
		throws Exception {

		boolean companySecurityAuthRequiresHttps =
			PropsValues.COMPANY_SECURITY_AUTH_REQUIRES_HTTPS;
		boolean sessionEnablePhishingProtection =
			PropsValues.SESSION_ENABLE_PHISHING_PROTECTION;
		boolean webServerForwardedProtocolEnabled =
			PropsValues.WEB_SERVER_FORWARDED_PROTOCOL_ENABLED;

		String webServerForwardedProtocolEnabledHeader =
			PropsValues.WEB_SERVER_FORWARDED_PROTOCOL_HEADER;

		try {
			setPropsValuesValue("COMPANY_SECURITY_AUTH_REQUIRES_HTTPS", true);
			setPropsValuesValue("SESSION_ENABLE_PHISHING_PROTECTION", false);
			setPropsValuesValue("WEB_SERVER_FORWARDED_PROTOCOL_ENABLED", true);
			setPropsValuesValue(
				"WEB_SERVER_FORWARDED_PROTOCOL_HEADER",
				"X-Forwarded-Custom-Proto");

			MockHttpServletRequest mockHttpServletRequest =
				new MockHttpServletRequest();

			mockHttpServletRequest.addHeader(
				"X-Forwarded-Custom-Proto", "https");
			mockHttpServletRequest.setSecure(false);

			Assert.assertTrue(_portalImpl.isSecure(mockHttpServletRequest));
		}
		finally {
			setPropsValuesValue(
				"COMPANY_SECURITY_AUTH_REQUIRES_HTTPS",
				companySecurityAuthRequiresHttps);
			setPropsValuesValue(
				"SESSION_ENABLE_PHISHING_PROTECTION",
				sessionEnablePhishingProtection);
			setPropsValuesValue(
				"WEB_SERVER_FORWARDED_PROTOCOL_ENABLED",
				webServerForwardedProtocolEnabled);
			setPropsValuesValue(
				"WEB_SERVER_FORWARDED_PROTOCOL_HEADER",
				webServerForwardedProtocolEnabledHeader);
		}
	}

	@Test
	public void testIsSecureWithHttpsInitialTrueXForwardedHttps()
		throws Exception {

		boolean companySecurityAuthRequiresHttps =
			PropsValues.COMPANY_SECURITY_AUTH_REQUIRES_HTTPS;
		boolean sessionEnablePhishingProtection =
			PropsValues.SESSION_ENABLE_PHISHING_PROTECTION;
		boolean webServerForwardedProtocolEnabled =
			PropsValues.WEB_SERVER_FORWARDED_PROTOCOL_ENABLED;

		try {
			setPropsValuesValue("COMPANY_SECURITY_AUTH_REQUIRES_HTTPS", true);
			setPropsValuesValue("SESSION_ENABLE_PHISHING_PROTECTION", false);
			setPropsValuesValue("WEB_SERVER_FORWARDED_PROTOCOL_ENABLED", true);

			MockHttpServletRequest mockHttpServletRequest =
				new MockHttpServletRequest();

			mockHttpServletRequest.addHeader("X-Forwarded-Proto", "https");
			mockHttpServletRequest.setSecure(false);

			HttpSession httpSession = mockHttpServletRequest.getSession();

			httpSession.setAttribute(WebKeys.HTTPS_INITIAL, Boolean.TRUE);

			Assert.assertTrue(_portalImpl.isSecure(mockHttpServletRequest));
		}
		finally {
			setPropsValuesValue(
				"COMPANY_SECURITY_AUTH_REQUIRES_HTTPS",
				companySecurityAuthRequiresHttps);
			setPropsValuesValue(
				"SESSION_ENABLE_PHISHING_PROTECTION",
				sessionEnablePhishingProtection);
			setPropsValuesValue(
				"WEB_SERVER_FORWARDED_PROTOCOL_ENABLED",
				webServerForwardedProtocolEnabled);
		}
	}

	@Test
	public void testIsSecureWithSecureRequest() {
		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setSecure(true);

		Assert.assertTrue(_portalImpl.isSecure(mockHttpServletRequest));
	}

	@Test
	public void testIsValidResourceId() {
		Assert.assertTrue(_portalImpl.isValidResourceId("/view.jsp"));
		Assert.assertTrue(_portalImpl.isValidResourceId("%2fview.jsp"));
		Assert.assertTrue(_portalImpl.isValidResourceId("%252fview.jsp"));

		Assert.assertFalse(
			_portalImpl.isValidResourceId("/META-INF/MANIFEST.MF"));
		Assert.assertFalse(
			_portalImpl.isValidResourceId("%2fMETA-INF%2fMANIFEST.MF"));
		Assert.assertFalse(
			_portalImpl.isValidResourceId("%252fMETA-INF%252fMANIFEST.MF"));

		Assert.assertFalse(
			_portalImpl.isValidResourceId("/META-INF\\MANIFEST.MF"));
		Assert.assertFalse(
			_portalImpl.isValidResourceId("%2fMETA-INF%5cMANIFEST.MF"));
		Assert.assertFalse(
			_portalImpl.isValidResourceId("%252fMETA-INF%255cMANIFEST.MF"));

		Assert.assertFalse(
			_portalImpl.isValidResourceId("\\META-INF/MANIFEST.MF"));
		Assert.assertFalse(
			_portalImpl.isValidResourceId("%5cMETA-INF%2fMANIFEST.MF"));
		Assert.assertFalse(
			_portalImpl.isValidResourceId("%255cMETA-INF%252fMANIFEST.MF"));

		Assert.assertFalse(
			_portalImpl.isValidResourceId("\\META-INF\\MANIFEST.MF"));
		Assert.assertFalse(
			_portalImpl.isValidResourceId("%5cMETA-INF%5cMANIFEST.MF"));
		Assert.assertFalse(
			_portalImpl.isValidResourceId("%255cMETA-INF%255cMANIFEST.MF"));

		Assert.assertFalse(_portalImpl.isValidResourceId("/WEB-INF/web.xml"));
		Assert.assertFalse(
			_portalImpl.isValidResourceId("%2fWEB-INF%2fweb.xml"));
		Assert.assertFalse(
			_portalImpl.isValidResourceId("%252fWEB-INF%252fweb.xml"));

		Assert.assertFalse(_portalImpl.isValidResourceId("/WEB-INF\\web.xml"));
		Assert.assertFalse(
			_portalImpl.isValidResourceId("%2fWEB-INF%5cweb.xml"));
		Assert.assertFalse(
			_portalImpl.isValidResourceId("%252fWEB-INF%255cweb.xml"));

		Assert.assertFalse(_portalImpl.isValidResourceId("\\WEB-INF/web.xml"));
		Assert.assertFalse(
			_portalImpl.isValidResourceId("%5cWEB-INF%2fweb.xml"));
		Assert.assertFalse(
			_portalImpl.isValidResourceId("%255cWEB-INF%252fweb.xml"));

		Assert.assertFalse(_portalImpl.isValidResourceId("\\WEB-INF\\web.xml"));
		Assert.assertFalse(
			_portalImpl.isValidResourceId("%5cWEB-INF%5cweb.xml"));
		Assert.assertFalse(
			_portalImpl.isValidResourceId("%255cWEB-INF%255cweb.xml"));

		Assert.assertTrue(_portalImpl.isValidResourceId("%25252525252525252f"));

		StringBundler sb = new StringBundler();

		sb.append("%");

		for (int i = 0; i < 100000; i++) {
			sb.append("25");
		}

		sb.append("2f");

		Assert.assertFalse(_portalImpl.isValidResourceId(sb.toString()));

		try (LogCapture logCapture = LoggerTestUtil.configureJDKLogger(
				HttpComponentsUtil.class.getName(), Level.OFF)) {

			Assert.assertFalse(_portalImpl.isValidResourceId("%view.jsp"));
		}
	}

	@Test
	public void testUpdateRedirectRemoveLayoutURL() {
		Assert.assertEquals(
			"/web/group",
			_portalImpl.updateRedirect(
				"/web/group/layout", "/group/layout", "/group"));
	}

	public static class MockInvokerPortlet implements InvokerPortlet {

		@Override
		public void destroy() {
		}

		@Override
		public Integer getExpCache() {
			return null;
		}

		@Override
		public javax.portlet.Portlet getPortlet() {
			return null;
		}

		@Override
		public ClassLoader getPortletClassLoader() {
			return null;
		}

		@Override
		public PortletConfig getPortletConfig() {
			return null;
		}

		@Override
		public PortletContext getPortletContext() {
			return null;
		}

		@Override
		public javax.portlet.Portlet getPortletInstance() {
			return null;
		}

		@Override
		public void init(PortletConfig arg0) throws PortletException {
		}

		@Override
		public boolean isCheckAuthToken() {
			return false;
		}

		@Override
		public boolean isFacesPortlet() {
			return false;
		}

		@Override
		public boolean isHeaderPortlet() {
			return false;
		}

		@Override
		public void processAction(
				ActionRequest actionRequest, ActionResponse actionResponse)
			throws IOException, PortletException {
		}

		@Override
		public void processEvent(
				EventRequest eventRequest, EventResponse eventResponse)
			throws IOException, PortletException {
		}

		@Override
		public void render(
				RenderRequest renderRequest, RenderResponse renderResponse)
			throws IOException, PortletException {
		}

		@Override
		public void renderHeaders(
				HeaderRequest headerRequest, HeaderResponse headerResponse)
			throws IOException, PortletException {
		}

		@Override
		public void serveResource(
				ResourceRequest resourceRequest,
				ResourceResponse resourceResponse)
			throws IOException, PortletException {
		}

		@Override
		public void setPortletFilters() throws PortletException {
		}

	}

	protected HttpServletRequest getWrappedRequest(
		HttpServletRequest httpServletRequest) {

		HttpServletRequestWrapper requestWrapper =
			(HttpServletRequestWrapper)httpServletRequest;

		return (HttpServletRequest)requestWrapper.getRequest();
	}

	protected void setPropsValuesValue(String fieldName, Object value) {
		ReflectionTestUtil.setFieldValue(PropsValues.class, fieldName, value);
	}

	private void _assertActionResponse(
		ActionResponse actionResponse, Map<String, String[]> params) {

		MutableRenderParametersImpl mutableRenderParametersImpl =
			(MutableRenderParametersImpl)actionResponse.getRenderParameters();

		Assert.assertEquals(
			mutableRenderParametersImpl.getValues(_REDIRECT)[0].toString(),
			params.get(_REDIRECT)[0]);
		Assert.assertEquals(
			mutableRenderParametersImpl.getValues(_P_U_I_D)[0].toString(),
			params.get(_P_U_I_D)[0]);
		Assert.assertEquals(
			mutableRenderParametersImpl.getValues(_PASSWORDRESET)[0].toString(),
			params.get(_PASSWORDRESET)[0]);
		Assert.assertNull(mutableRenderParametersImpl.getValues(_PASSWORD1));
		Assert.assertNull(mutableRenderParametersImpl.getValues(_PASSWORD2));
	}

	private void _assertGetHost(String httpHostHeader, String host) {
		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.addHeader("Host", httpHostHeader);

		Assert.assertEquals(host, _portalImpl.getHost(mockHttpServletRequest));
	}

	private ActionRequest _createActionRequest(PortletMode portletMode) {
		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		HttpServletRequest httpServletRequest = new DynamicServletRequest(
			mockHttpServletRequest, new HashMap<>());

		ThemeDisplay themeDisplay = ThemeDisplayFactory.create();

		httpServletRequest.setAttribute(WebKeys.THEME_DISPLAY, themeDisplay);

		Portlet portlet = new PortletImpl(100L, "test_portlet");

		portlet.setPortletApp(new PortletAppImpl("test_servlet_context"));

		return (ActionRequestImpl)ActionRequestFactory.create(
			httpServletRequest, portlet, new MockInvokerPortlet(),
			new MockLiferayPortletContext("/path"), WindowState.NORMAL,
			portletMode, new MockPortletPreferences(), 4000L);
	}

	private ActionRequest _createActionRequestMock(
		Map<String, String[]> params, Enumeration<String> enumeration) {

		ActionRequest actionRequestMock = Mockito.mock(ActionRequest.class);

		Mockito.when(
			actionRequestMock.getParameterNames()
		).thenReturn(
			enumeration
		);
		Mockito.when(
			actionRequestMock.getParameterValues(_REDIRECT)
		).thenReturn(
			params.get(_REDIRECT)
		);
		Mockito.when(
			actionRequestMock.getParameterValues(_P_U_I_D)
		).thenReturn(
			params.get(_P_U_I_D)
		);
		Mockito.when(
			actionRequestMock.getParameterValues(_PASSWORDRESET)
		).thenReturn(
			params.get(_PASSWORDRESET)
		);
		Mockito.when(
			actionRequestMock.getParameterValues(_PASSWORD1)
		).thenReturn(
			params.get(_PASSWORD1)
		);
		Mockito.when(
			actionRequestMock.getParameterValues(_PASSWORD2)
		).thenReturn(
			params.get(_PASSWORD2)
		);

		return actionRequestMock;
	}

	private ActionResponse _createActionResponse(
			MockedStatic<PortalUtil> portalUtilMock)
		throws PortletException {

		HttpServletResponse httpServletResponse =
			new DummyHttpServletResponse();

		LayoutTypePortletFactoryUtil layoutTypePortletFactoryUtil =
			new LayoutTypePortletFactoryUtil();

		layoutTypePortletFactoryUtil.setLayoutTypePortletFactory(
			new LayoutTypePortletFactoryImpl());

		portalUtilMock.when(
			() -> PortalUtil.updateWindowState(
				Mockito.anyString(), Mockito.any(UserImpl.class),
				Mockito.any(LayoutImpl.class), Mockito.any(WindowState.class),
				Mockito.any(HttpServletRequest.class))
		).thenReturn(
			WindowState.NORMAL
		);

		PortletMode portletMode = Mockito.mock(PortletMode.class);

		Mockito.doReturn(
			null
		).when(
			portletMode
		).toString();

		return (ActionResponseImpl)ActionResponseFactory.create(
			_createActionRequest(portletMode), httpServletResponse,
			new UserImpl(), new LayoutImpl());
	}

	private Enumeration<String> _createEnumerationParams() {
		List<String> arrayList = new ArrayList<>();

		arrayList.add(_REDIRECT);
		arrayList.add(_P_U_I_D);
		arrayList.add(_PASSWORDRESET);
		arrayList.add(_PASSWORD1);
		arrayList.add(_PASSWORD2);

		return Collections.enumeration(arrayList);
	}

	private Map<String, String[]> _createMapParams() {
		return HashMapBuilder.put(
			_P_U_I_D, new String[] {String.valueOf(4200L)}
		).put(
			_PASSWORDRESET, new String[] {Boolean.TRUE.toString()}
		).put(
			_REDIRECT, new String[] {"http://localhost:8080/test"}
		).build();
	}

	private static final String _P_U_I_D = "p_u_i_d";

	private static final String _PASSWORD1 = "password1";

	private static final String _PASSWORD2 = "password2";

	private static final String _PASSWORDRESET = "passwordReset";

	private static final String _REDIRECT = "redirect";

	private final PortalImpl _portalImpl = new PortalImpl();

	private static class PersistentHttpServletRequestWrapper1
		extends PersistentHttpServletRequestWrapper {

		private PersistentHttpServletRequestWrapper1(
			HttpServletRequest httpServletRequest) {

			super(httpServletRequest);
		}

	}

	private static class PersistentHttpServletRequestWrapper2
		extends PersistentHttpServletRequestWrapper {

		private PersistentHttpServletRequestWrapper2(
			HttpServletRequest httpServletRequest) {

			super(httpServletRequest);
		}

	}

}