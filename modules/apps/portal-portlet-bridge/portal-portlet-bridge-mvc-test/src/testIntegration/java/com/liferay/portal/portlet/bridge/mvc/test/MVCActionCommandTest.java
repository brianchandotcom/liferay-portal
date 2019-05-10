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

package com.liferay.portal.portlet.bridge.mvc.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.portlet.LiferayPortletConfig;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;

import javax.portlet.ActionParameters;
import javax.portlet.ActionRequest;
import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.portlet.PortletResponse;
import javax.portlet.RenderParameters;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.portlet.MockActionRequest;
import org.springframework.mock.web.portlet.MockActionResponse;

/**
 * @author Manuel de la Peña
 */
@RunWith(Arquillian.class)
public class MVCActionCommandTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() {
		Bundle bundle = FrameworkUtil.getBundle(MVCActionCommandTest.class);

		BundleContext bundleContext = bundle.getBundleContext();

		_portlet = new MVCPortlet();

		_portletServiceRegistration = bundleContext.registerService(
			javax.portlet.Portlet.class, _portlet,
			new HashMapDictionary<String, Object>() {
				{
					put(
						"javax.portlet.init-param.copy-request-parameters",
						"false");
					put("javax.portlet.name", _PORTLET_NAME);
				}
			});

		_mvcActionCommandServiceRegistration1 = bundleContext.registerService(
			MVCActionCommand.class,
			(actionRequest, actionResponse) -> {
				actionRequest.setAttribute(
					_TEST_MVC_ACTION_COMMAND_ATTRIBUTE_1,
					_TEST_MVC_ACTION_COMMAND_ATTRIBUTE_1);

				return true;
			},
			new HashMapDictionary<String, Object>() {
				{
					put("javax.portlet.name", _PORTLET_NAME);
					put("mvc.command.name", _TEST_MVC_ACTION_COMMAND_NAME_1);
				}
			});

		_mvcActionCommandServiceRegistration2 = bundleContext.registerService(
			MVCActionCommand.class,
			(actionRequest, actionResponse) -> {
				actionRequest.setAttribute(
					_TEST_MVC_ACTION_COMMAND_ATTRIBUTE_2,
					_TEST_MVC_ACTION_COMMAND_ATTRIBUTE_2);

				return true;
			},
			new HashMapDictionary<String, Object>() {
				{
					put("javax.portlet.name", _PORTLET_NAME);
					put("mvc.command.name", _TEST_MVC_ACTION_COMMAND_NAME_2);
				}
			});
	}

	@AfterClass
	public static void tearDownClass() {
		_portletServiceRegistration.unregister();
		_mvcActionCommandServiceRegistration1.unregister();
		_mvcActionCommandServiceRegistration2.unregister();
	}

	@Test
	public void testMultipleMVCActionCommandsWithMultipleParameters()
		throws Exception {

		MockActionRequest mockActionRequest = new MockLiferayPortletRequest();

		mockActionRequest.addParameter(
			ActionRequest.ACTION_NAME, _TEST_MVC_ACTION_COMMAND_NAME_1);
		mockActionRequest.addParameter(
			ActionRequest.ACTION_NAME, _TEST_MVC_ACTION_COMMAND_NAME_2);

		_portlet.processAction(mockActionRequest, new MockActionResponse());

		Assert.assertNotNull(
			mockActionRequest.getAttribute(
				_TEST_MVC_ACTION_COMMAND_ATTRIBUTE_1));
		Assert.assertEquals(
			_TEST_MVC_ACTION_COMMAND_ATTRIBUTE_1,
			mockActionRequest.getAttribute(
				_TEST_MVC_ACTION_COMMAND_ATTRIBUTE_1));
		Assert.assertNotNull(
			mockActionRequest.getAttribute(
				_TEST_MVC_ACTION_COMMAND_ATTRIBUTE_2));
		Assert.assertEquals(
			_TEST_MVC_ACTION_COMMAND_ATTRIBUTE_2,
			mockActionRequest.getAttribute(
				_TEST_MVC_ACTION_COMMAND_ATTRIBUTE_2));
	}

	@Test
	public void testMultipleMVCActionCommandsWithSingleParameter()
		throws Exception {

		MockActionRequest mockActionRequest = new MockLiferayPortletRequest();

		mockActionRequest.addParameter(
			ActionRequest.ACTION_NAME,
			_TEST_MVC_ACTION_COMMAND_NAME_1 + StringPool.COMMA +
				_TEST_MVC_ACTION_COMMAND_NAME_2);

		_portlet.processAction(mockActionRequest, new MockActionResponse());

		Assert.assertNotNull(
			mockActionRequest.getAttribute(
				_TEST_MVC_ACTION_COMMAND_ATTRIBUTE_1));
		Assert.assertEquals(
			_TEST_MVC_ACTION_COMMAND_ATTRIBUTE_1,
			mockActionRequest.getAttribute(
				_TEST_MVC_ACTION_COMMAND_ATTRIBUTE_1));
		Assert.assertNotNull(
			mockActionRequest.getAttribute(
				_TEST_MVC_ACTION_COMMAND_ATTRIBUTE_2));
		Assert.assertEquals(
			_TEST_MVC_ACTION_COMMAND_ATTRIBUTE_2,
			mockActionRequest.getAttribute(
				_TEST_MVC_ACTION_COMMAND_ATTRIBUTE_2));
	}

	@Test
	public void testSingleMVCActionCommand() throws Exception {
		MockActionRequest mockActionRequest = new MockLiferayPortletRequest();

		mockActionRequest.addParameter(
			ActionRequest.ACTION_NAME, _TEST_MVC_ACTION_COMMAND_NAME_1);

		_portlet.processAction(mockActionRequest, new MockActionResponse());

		Assert.assertNotNull(
			mockActionRequest.getAttribute(
				_TEST_MVC_ACTION_COMMAND_ATTRIBUTE_1));
		Assert.assertEquals(
			_TEST_MVC_ACTION_COMMAND_ATTRIBUTE_1,
			mockActionRequest.getAttribute(
				_TEST_MVC_ACTION_COMMAND_ATTRIBUTE_1));
	}

	private static final String _PORTLET_NAME =
		"com_liferay_portal_kernel_portlet_bridges_mvc_test_" +
			"MVCActionCommandTest_TestPortlet";

	private static final String _TEST_MVC_ACTION_COMMAND_ATTRIBUTE_1 =
		"TEST_MVC_ACTION_COMMAND_ATTRIBUTE_1";

	private static final String _TEST_MVC_ACTION_COMMAND_ATTRIBUTE_2 =
		"TEST_MVC_ACTION_COMMAND_ATTRIBUTE_2";

	private static final String _TEST_MVC_ACTION_COMMAND_NAME_1 =
		"TEST_MVC_ACTION_COMMAND_NAME_1";

	private static final String _TEST_MVC_ACTION_COMMAND_NAME_2 =
		"TEST_MVC_ACTION_COMMAND_NAME_2";

	private static ServiceRegistration<MVCActionCommand>
		_mvcActionCommandServiceRegistration1;
	private static ServiceRegistration<MVCActionCommand>
		_mvcActionCommandServiceRegistration2;
	private static javax.portlet.Portlet _portlet;
	private static ServiceRegistration<javax.portlet.Portlet>
		_portletServiceRegistration;

	private static class MockLiferayPortletRequest
		extends MockActionRequest implements LiferayPortletRequest {

		@Override
		public void addParameter(String name, String value) {
			_mockHttpServletRequest.addParameter(name, value);

			super.addParameter(name, value);
		}

		@Override
		public void cleanUp() {
		}

		@Override
		public Map<String, String[]> clearRenderParameters() {
			return null;
		}

		@Override
		public void defineObjects(
			PortletConfig portletConfig, PortletResponse portletResponse) {
		}

		@Override
		public ActionParameters getActionParameters() {
			return null;
		}

		@Override
		public Object getAttribute(String name) {
			if (name.equals(JavaConstants.JAVAX_PORTLET_CONFIG)) {
				return ProxyUtil.newProxyInstance(
					LiferayPortletConfig.class.getClassLoader(),
					new Class<?>[] {LiferayPortletConfig.class},
					(proxy, method, args) -> {
						if (Objects.equals(method.getName(), "getPortletId")) {
							return "testPortlet";
						}

						return null;
					});
			}

			return super.getAttribute(name);
		}

		@Override
		public long getContentLengthLong() {
			return 0;
		}

		@Override
		public HttpServletRequest getHttpServletRequest() {
			return _mockHttpServletRequest;
		}

		@Override
		public String getLifecycle() {
			return null;
		}

		@Override
		public HttpServletRequest getOriginalHttpServletRequest() {
			return _mockHttpServletRequest;
		}

		@Override
		public Part getPart(String name) {
			return null;
		}

		@Override
		public Collection<Part> getParts() {
			return null;
		}

		@Override
		public long getPlid() {
			return 0;
		}

		@Override
		public Portlet getPortlet() {
			return null;
		}

		@Override
		public PortletContext getPortletContext() {
			return null;
		}

		@Override
		public String getPortletName() {
			return null;
		}

		@Override
		public HttpServletRequest getPortletRequestDispatcherRequest() {
			return null;
		}

		@Override
		public RenderParameters getRenderParameters() {
			return null;
		}

		@Override
		public String getUserAgent() {
			return null;
		}

		@Override
		public void invalidateSession() {
		}

		@Override
		public void setPortletRequestDispatcherRequest(
			HttpServletRequest httpServletRequest) {
		}

		private final MockHttpServletRequest _mockHttpServletRequest =
			new MockHttpServletRequest();

	}

}