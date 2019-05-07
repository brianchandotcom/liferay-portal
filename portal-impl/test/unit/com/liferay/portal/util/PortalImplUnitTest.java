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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.security.auth.AlwaysAllowDoAsUser;
import com.liferay.portal.kernel.servlet.PersistentHttpServletRequestWrapper;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.registry.BasicRegistryImpl;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceRegistration;

import java.util.Collection;
import java.util.Collections;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpSession;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Miguel Pastor
 */
public class PortalImplUnitTest {

	@BeforeClass
	public static void setUpClass() {
		RegistryUtil.setRegistry(new BasicRegistryImpl());
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
		PropsUtil.setProps(new PropsImpl());

		Registry registry = RegistryUtil.getRegistry();

		boolean[] calledAlwaysAllowDoAsUser = {false};

		ServiceRegistration<AlwaysAllowDoAsUser> serviceRegistration =
			registry.registerService(
				AlwaysAllowDoAsUser.class,
				new AlwaysAllowDoAsUser() {

					@Override
					public Collection<String> getActionNames() {
						calledAlwaysAllowDoAsUser[0] = true;

						return Collections.singletonList(_ACTION_NAME);
					}

					@Override
					public Collection<String> getMVCRenderCommandNames() {
						calledAlwaysAllowDoAsUser[0] = true;

						return Collections.singletonList(
							_MVC_RENDER_COMMMAND_NAME);
					}

					@Override
					public Collection<String> getPaths() {
						calledAlwaysAllowDoAsUser[0] = true;

						return Collections.singletonList(_PATH);
					}

					@Override
					public Collection<String> getStrutsActions() {
						calledAlwaysAllowDoAsUser[0] = true;

						return Collections.singletonList(_STRUTS_ACTION);
					}

				});

		try {
			MockHttpServletRequest mockHttpServletRequest1 =
				new MockHttpServletRequest();

			mockHttpServletRequest1.setParameter(
				"_TestAlwaysAllowDoAsUser_actionName", _ACTION_NAME);
			mockHttpServletRequest1.setParameter(
				"_TestAlwaysAllowDoAsUser_struts_action", _STRUTS_ACTION);
			mockHttpServletRequest1.setParameter("doAsUserId", "0");
			mockHttpServletRequest1.setParameter(
				"p_p_id", "TestAlwaysAllowDoAsUser");

			Assert.assertEquals(
				0, _portalImpl.getUserId(mockHttpServletRequest1));

			Assert.assertTrue(
				"AlwaysAllowDoAsUser not called", calledAlwaysAllowDoAsUser[0]);

			calledAlwaysAllowDoAsUser[0] = false;

			MockHttpServletRequest mockHttpServletRequest2 =
				new MockHttpServletRequest();

			mockHttpServletRequest2.setParameter("doAsUserId", "0");
			mockHttpServletRequest2.setPathInfo(
				_PATH + RandomTestUtil.randomString());

			Assert.assertEquals(
				0, _portalImpl.getUserId(mockHttpServletRequest2));

			Assert.assertTrue(
				"AlwaysAllowDoAsUser not called", calledAlwaysAllowDoAsUser[0]);
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
		Assert.assertFalse(
			_portalImpl.isValidResourceId("/META-INF/MANIFEST.MF"));
		Assert.assertFalse(
			_portalImpl.isValidResourceId("/META-INF\\MANIFEST.MF"));
		Assert.assertFalse(
			_portalImpl.isValidResourceId("\\META-INF/MANIFEST.MF"));
		Assert.assertFalse(
			_portalImpl.isValidResourceId("\\META-INF\\MANIFEST.MF"));
		Assert.assertFalse(_portalImpl.isValidResourceId("/WEB-INF/web.xml"));
		Assert.assertFalse(_portalImpl.isValidResourceId("/WEB-INF\\web.xml"));
		Assert.assertFalse(_portalImpl.isValidResourceId("\\WEB-INF/web.xml"));
		Assert.assertFalse(_portalImpl.isValidResourceId("\\WEB-INF\\web.xml"));
	}

	@Test
	public void testUpdateRedirectRemoveLayoutURL() {
		HttpUtil httpUtil = new HttpUtil();

		httpUtil.setHttp(
			new HttpImpl() {

				@Override
				public String getParameter(
					String url, String name, boolean escaped) {

					return StringPool.BLANK;
				}

				@Override
				public String getPath(String url) {
					return url;
				}

				@Override
				public String getQueryString(String url) {
					return StringPool.BLANK;
				}

			});

		Assert.assertEquals(
			"/web/group",
			_portalImpl.updateRedirect(
				"/web/group/layout", "/group/layout", "/group"));
	}

	protected HttpServletRequest getWrappedRequest(
		HttpServletRequest httpServletRequest) {

		HttpServletRequestWrapper requestWrapper =
			(HttpServletRequestWrapper)httpServletRequest;

		return (HttpServletRequest)requestWrapper.getRequest();
	}

	protected void setPropsValuesValue(String fieldName, Object value)
		throws Exception {

		ReflectionTestUtil.setFieldValue(PropsValues.class, fieldName, value);
	}

	private static final String _ACTION_NAME =
		"/TestAlwaysAllowDoAsUser/action/name";

	private static final String _MVC_RENDER_COMMMAND_NAME =
		"/TestAlwaysAllowDoAsUser/mvc/render/command/name";

	private static final String _PATH = "/TestAlwaysAllowDoAsUser/";

	private static final String _STRUTS_ACTION =
		"/TestAlwaysAllowDoAsUser/struts/action";

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