/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.servlet;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.PortletApp;
import com.liferay.portal.kernel.model.PortletWrapper;
import com.liferay.portal.kernel.service.PortletLocalServiceUtil;
import com.liferay.portal.kernel.service.PortletLocalServiceWrapper;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.PrefsProps;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.language.LanguageImpl;
import com.liferay.portal.model.impl.PortletAppImpl;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.portal.tools.ToolDependencies;
import com.liferay.portal.util.PortalImpl;
import com.liferay.portal.util.PropsValues;

import java.util.Objects;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;

import org.mockito.Mockito;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletConfig;
import org.springframework.mock.web.MockServletContext;

/**
 * @author Carlos Sierra Andrés
 * @author Raymond Augé
 */
public class ComboServletTest {

	@ClassRule
	public static LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@BeforeClass
	public static void setUpClass() throws Exception {
		ToolDependencies.wireCaches();

		ReflectionTestUtil.setFieldValue(
			PropsValues.class, "COMBO_CHECK_TIMESTAMP", true);
	}

	@Before
	public void setUp() throws ServletException {
		_mockHttpServletRequest = new MockHttpServletRequest();

		_mockHttpServletRequest.setLocalAddr("localhost");
		_mockHttpServletRequest.setLocalPort(8080);
		_mockHttpServletRequest.setScheme("http");

		_mockHttpServletResponse = new MockHttpServletResponse();
		_pluginServletContext = Mockito.spy(new MockServletContext());
		_portalServletContext = _setUpPortalServletContext();

		_portalUtil.setPortal(_portalImpl);

		ReflectionTestUtil.setFieldValue(
			PortletLocalServiceUtil.class, "_service",
			new PortletLocalServiceWrapper() {

				@Override
				public Portlet getPortletById(String portletId) {
					if (Objects.equals(_TEST_PORTLET_ID, portletId)) {
						return _testPortlet;
					}
					else if (Objects.equals(PortletKeys.PORTAL, portletId)) {
						return _portalPortlet;
					}
					else if (Objects.equals(
								_NONEXISTING_PORTLET_ID, portletId)) {

						return null;
					}

					return _undeployedPortlet;
				}

			});

		ReflectionTestUtil.setFieldValue(
			PrefsPropsUtil.class, "_prefsProps", _prefsProps);

		Mockito.when(
			_prefsProps.getStringArray(
				PropsKeys.COMBO_ALLOWED_FILE_EXTENSIONS, StringPool.COMMA)
		).thenReturn(
			new String[] {".css", ".js"}
		);

		_undeployedPortlet = new PortletWrapper(null) {

			@Override
			public boolean isUndeployedPortlet() {
				return true;
			}

		};

		_comboServlet = _setUpComboServlet(_portalServletContext);
		_portalPortlet = _setUpPortalPortlet(_portalServletContext);
		_testPortlet = _setUpTestPortlet(_pluginServletContext);
	}

	@Test
	public void testEmptyParameters() throws Exception {
		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		_comboServlet.service(
			new MockHttpServletRequest(), mockHttpServletResponse);

		Assert.assertEquals(
			HttpServletResponse.SC_NOT_FOUND,
			mockHttpServletResponse.getStatus());
	}

	@Test
	public void testGetResourceRequestDispatcherWithNonexistingPortletId()
		throws Exception {

		RequestDispatcher requestDispatcher =
			_comboServlet.getResourceRequestDispatcher(
				_mockHttpServletRequest, _mockHttpServletResponse,
				_NONEXISTING_PORTLET_ID + ":/js/javascript.js");

		Assert.assertNull(requestDispatcher);
	}

	@Test
	public void testGetResourceRequestDispatcherWithoutPortletId()
		throws Exception {

		String path = "/js/javascript.js";

		_comboServlet.getResourceRequestDispatcher(
			_mockHttpServletRequest, _mockHttpServletResponse,
			"/js/javascript.js");

		Mockito.verify(
			_portalServletContext
		).getRequestDispatcher(
			path
		);
	}

	@Test
	public void testGetResourceWithPortletId() throws Exception {
		_comboServlet.getResourceRequestDispatcher(
			_mockHttpServletRequest, _mockHttpServletResponse,
			_TEST_PORTLET_ID + ":/js/javascript.js");

		Mockito.verify(
			_pluginServletContext
		).getRequestDispatcher(
			"/js/javascript.js"
		);
	}

	@Test
	public void testInvalidResourcePath() throws Exception {
		Assert.assertNull(
			_comboServlet.getResourceRequestDispatcher(
				_mockHttpServletRequest, _mockHttpServletResponse,
				_TEST_PORTLET_ID + ":js/javascript.js"));
	}

	@Test
	public void testMixedExtensionsRequest() throws Exception {
		LanguageUtil languageUtil = new LanguageUtil();

		languageUtil.setLanguage(new LanguageImpl());

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setQueryString(
			"/js/javascript.js&/css/styles.css");

		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		_comboServlet.service(mockHttpServletRequest, mockHttpServletResponse);

		Assert.assertEquals(
			HttpServletResponse.SC_BAD_REQUEST,
			mockHttpServletResponse.getStatus());
	}

	@Test
	public void testServiceWithoutPortletIdButWithContext() throws Exception {
		_testService(
			"/js/javascript.js", "/portal/js/javascript.js",
			_portalServletContext);
	}

	@Test
	public void testServiceWithoutPortletIdButWithProxy() throws Exception {
		_setUpProxy();

		_testService(
			"/js/javascript.js", "/proxyPath/js/javascript.js",
			_portalServletContext);
	}

	@Test
	public void testServiceWithoutPortletIdButWithProxyAndContext()
		throws Exception {

		_setUpProxy();

		_testService(
			"/js/javascript.js", "/proxyPath/portal/js/javascript.js",
			_portalServletContext);
	}

	@Test
	public void testServiceWithPortletIdAndContext() throws Exception {
		_testService(
			"/portal/js/javascript.js",
			_TEST_PORTLET_ID + ":/portal/js/javascript.js",
			_pluginServletContext);
	}

	@Test
	public void testServiceWithPortletIdAndProxy() throws Exception {
		_setUpProxy();

		_testService(
			"/js/javascript.js",
			_TEST_PORTLET_ID + ":/proxyPath/js/javascript.js",
			_pluginServletContext);
	}

	@Test
	public void testServiceWithPortletIdAndProxyAndContext() throws Exception {
		_setUpProxy();

		_testService(
			"/portal/js/javascript.js",
			_TEST_PORTLET_ID + ":/proxyPath/portal/js/javascript.js",
			_pluginServletContext);
	}

	@Test
	public void testValidateInValidModuleExtension() throws Exception {
		boolean valid = _comboServlet.validateModuleExtension(
			_TEST_PORTLET_ID +
				"_INSTANCE_.js:/api/jsonws?discover=true&callback=aaa");

		Assert.assertFalse(valid);
	}

	@Test
	public void testValidateModuleExtensionWithParameterPath()
		throws Exception {

		boolean valid = _comboServlet.validateModuleExtension(
			_TEST_PORTLET_ID +
				"_INSTANCE_.js:/api/jsonws;.js?discover=true&callback=aaa");

		Assert.assertFalse(valid);
	}

	@Test
	public void testValidateValidModuleExtension() throws Exception {
		boolean valid = _comboServlet.validateModuleExtension(
			_TEST_PORTLET_ID + "_INSTANCE_.js:/js/javascript.js");

		Assert.assertTrue(valid);
	}

	private ComboServlet _setUpComboServlet(ServletContext portalServletContext)
		throws ServletException {

		ComboServlet comboServlet = new ComboServlet();

		comboServlet.init(new MockServletConfig(portalServletContext));

		return comboServlet;
	}

	private Portlet _setUpPortalPortlet(ServletContext portalServletContext) {
		PortletApp portletApp = new PortletAppImpl(StringPool.BLANK);

		portletApp.setServletContext(portalServletContext);

		return new PortletWrapper(null) {

			@Override
			public String getContextPath() {
				return "/portal";
			}

			@Override
			public PortletApp getPortletApp() {
				return portletApp;
			}

			@Override
			public String getRootPortletId() {
				return PortletKeys.PORTAL;
			}

			@Override
			public boolean isUndeployedPortlet() {
				return false;
			}

		};
	}

	private ServletContext _setUpPortalServletContext() {
		MockServletContext mockServletContext = Mockito.spy(
			new MockServletContext());

		mockServletContext.setContextPath("/portal");

		return mockServletContext;
	}

	private void _setUpProxy() {
		ReflectionTestUtil.setFieldValue(
			PropsValues.class, "PORTAL_PROXY_PATH", "/proxyPath");

		_portalUtil.setPortal(new PortalImpl());
	}

	private Portlet _setUpTestPortlet(ServletContext pluginServletContext) {
		PortletApp portletApp = new PortletAppImpl(StringPool.BLANK);

		portletApp.setServletContext(pluginServletContext);

		return new PortletWrapper(null) {

			@Override
			public String getContextPath() {
				return "/portal";
			}

			@Override
			public PortletApp getPortletApp() {
				return portletApp;
			}

			@Override
			public String getRootPortletId() {
				return _TEST_PORTLET_ID;
			}

			@Override
			public boolean isUndeployedPortlet() {
				return false;
			}

		};
	}

	private void _testService(
			String path, String queryString, ServletContext servletContext)
		throws Exception {

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setQueryString(queryString);

		_comboServlet.service(
			mockHttpServletRequest, new MockHttpServletResponse());

		Mockito.verify(
			servletContext
		).getRequestDispatcher(
			path
		);

		Mockito.reset(servletContext);
	}

	private static final String _NONEXISTING_PORTLET_ID = "2345678";

	private static final String _TEST_PORTLET_ID = "TEST_PORTLET_ID";

	private static final PortalImpl _portalImpl = new PortalImpl();
	private static final PortalUtil _portalUtil = new PortalUtil();

	private ComboServlet _comboServlet;
	private MockHttpServletRequest _mockHttpServletRequest;
	private MockHttpServletResponse _mockHttpServletResponse;
	private ServletContext _pluginServletContext;
	private Portlet _portalPortlet;
	private ServletContext _portalServletContext;
	private final PrefsProps _prefsProps = Mockito.mock(PrefsProps.class);
	private Portlet _testPortlet;
	private Portlet _undeployedPortlet;

}