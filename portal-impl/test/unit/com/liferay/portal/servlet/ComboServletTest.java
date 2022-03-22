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

package com.liferay.portal.servlet;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.PortletApp;
import com.liferay.portal.kernel.model.PortletWrapper;
import com.liferay.portal.kernel.service.PortalPreferencesLocalServiceWrapper;
import com.liferay.portal.kernel.service.PortletLocalServiceUtil;
import com.liferay.portal.kernel.service.PortletLocalServiceWrapper;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.language.LanguageImpl;
import com.liferay.portal.model.impl.PortletAppImpl;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.portal.tools.ToolDependencies;
import com.liferay.portal.util.PortalImpl;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portlet.PortalPreferencesWrapper;

import java.util.Objects;

import javax.portlet.PortletPreferences;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;

import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

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

		PortalUtil portalUtil = new PortalUtil();

		portalUtil.setPortal(new PortalImpl());

		ReflectionTestUtil.setFieldValue(
			PrefsPropsUtil.class, "_portalPreferencesLocalService",
			new PortalPreferencesLocalServiceWrapper() {

				@Override
				public PortletPreferences getPreferences(
					long ownerId, int ownerType) {

					return new PortalPreferencesWrapper(null) {

						@Override
						public String getValue(String key, String def) {
							if (PropsKeys.COMBO_ALLOWED_FILE_EXTENSIONS.equals(
									key)) {

								return ".css,.js";
							}

							return null;
						}

					};
				}

			});
	}

	@Before
	public void setUp() throws ServletException {
		MockitoAnnotations.initMocks(this);

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

					return _portletUndeployed;
				}

			});

		setUpComboServlet();

		setUpPortalServletContext();

		setUpPortalPortlet();

		setUpPluginServletContext();

		setUpTestPortlet();

		_portletUndeployed = new PortletWrapper(null) {

			@Override
			public boolean isUndeployedPortlet() {
				return true;
			}

		};

		_mockHttpServletRequest = new MockHttpServletRequest();

		_mockHttpServletRequest.setLocalAddr("localhost");
		_mockHttpServletRequest.setLocalPort(8080);
		_mockHttpServletRequest.setScheme("http");

		_mockHttpServletResponse = new MockHttpServletResponse();
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

	protected ServletConfig getServletConfig() {
		return new MockServletConfig(_portalServletContext);
	}

	protected void setUpComboServlet() throws ServletException {
		_comboServlet = new ComboServlet();

		_comboServlet.init(getServletConfig());
	}

	protected void setUpPluginServletContext() {
		_pluginServletContext = Mockito.spy(new MockServletContext());
	}

	protected void setUpPortalPortlet() {
		_portalPortletApp = new PortletAppImpl(StringPool.BLANK);

		_portalPortletApp.setServletContext(_portalServletContext);

		_portalPortlet = new PortletWrapper(null) {

			@Override
			public String getContextPath() {
				return "portal";
			}

			@Override
			public PortletApp getPortletApp() {
				return _portalPortletApp;
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

	protected void setUpPortalServletContext() {
		_portalServletContext = Mockito.spy(new MockServletContext());

		_portalServletContext.setContextPath("portal");
	}

	protected void setUpTestPortlet() {
		_testPortletApp = new PortletAppImpl(StringPool.BLANK);

		_testPortletApp.setServletContext(_pluginServletContext);

		_testPortlet = new PortletWrapper(null) {

			@Override
			public PortletApp getPortletApp() {
				return _testPortletApp;
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

	private static final String _NONEXISTING_PORTLET_ID = "2345678";

	private static final String _TEST_PORTLET_ID = "TEST_PORTLET_ID";

	private ComboServlet _comboServlet;
	private MockHttpServletRequest _mockHttpServletRequest;
	private MockHttpServletResponse _mockHttpServletResponse;
	private MockServletContext _pluginServletContext;
	private Portlet _portalPortlet;
	private PortletApp _portalPortletApp;
	private MockServletContext _portalServletContext;
	private Portlet _portletUndeployed;
	private Portlet _testPortlet;
	private PortletApp _testPortletApp;

}