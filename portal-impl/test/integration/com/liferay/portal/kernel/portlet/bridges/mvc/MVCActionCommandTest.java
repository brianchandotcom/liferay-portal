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

package com.liferay.portal.kernel.portlet.bridges.mvc;

import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.bridges.mvc.bundle.mvcactioncommand.TestMVCActionCommand1;
import com.liferay.portal.kernel.portlet.bridges.mvc.bundle.mvcactioncommand.TestMVCActionCommand2;
import com.liferay.portal.kernel.portlet.bridges.mvc.bundle.mvcactioncommand.TestPortlet;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.SyntheticBundleRule;
import com.liferay.registry.Filter;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceTracker;

import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.GenericPortlet;
import javax.portlet.PortletConfig;
import javax.portlet.PortletResponse;

import javax.servlet.http.HttpServletRequest;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.portlet.MockActionRequest;
import org.springframework.mock.web.portlet.MockActionResponse;

/**
 * @author Manuel de la Peña
 */
public class MVCActionCommandTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			new SyntheticBundleRule("bundle.mvcactioncommand"));

	@BeforeClass
	public static void setUpClass() throws Exception {
		Registry registry = RegistryUtil.getRegistry();

		Filter filter = registry.getFilter(
			"(&(javax.portlet.name=" + TestPortlet.PORTLET_NAME +
				")(objectClass=javax.portlet.Portlet))");

		_genericPortletServiceTracker = registry.trackServices(filter);

		_genericPortletServiceTracker.open();

		_genericPortlet = _genericPortletServiceTracker.getService();
	}

	@AfterClass
	public static void tearDownClass() {
		_genericPortletServiceTracker.close();
	}

	@Test
	public void testMultipleMVCActionCommandsWithMultipleParameters()
		throws Exception {

		MockActionRequest mockActionRequest = new MockLiferayPortletRequest();

		mockActionRequest.addParameter(
			ActionRequest.ACTION_NAME,
			TestMVCActionCommand1.TEST_MVC_ACTION_COMMAND_NAME);
		mockActionRequest.addParameter(
			ActionRequest.ACTION_NAME,
			TestMVCActionCommand2.TEST_MVC_ACTION_COMMAND_NAME);

		_genericPortlet.processAction(
			mockActionRequest, new MockActionResponse());

		Assert.assertNotNull(
			mockActionRequest.getAttribute(
				TestMVCActionCommand1.TEST_MVC_ACTION_COMMAND_ATTRIBUTE));
		Assert.assertEquals(
			TestMVCActionCommand1.TEST_MVC_ACTION_COMMAND_ATTRIBUTE,
			mockActionRequest.getAttribute(
				TestMVCActionCommand1.TEST_MVC_ACTION_COMMAND_ATTRIBUTE));
		Assert.assertNotNull(
			mockActionRequest.getAttribute(
				TestMVCActionCommand2.TEST_MVC_ACTION_COMMAND_ATTRIBUTE));
		Assert.assertEquals(
			TestMVCActionCommand2.TEST_MVC_ACTION_COMMAND_ATTRIBUTE,
			mockActionRequest.getAttribute(
				TestMVCActionCommand2.TEST_MVC_ACTION_COMMAND_ATTRIBUTE));
	}

	@Test
	public void testMultipleMVCActionCommandsWithSingleParameter()
		throws Exception {

		MockActionRequest mockActionRequest = new MockLiferayPortletRequest();

		mockActionRequest.addParameter(
			ActionRequest.ACTION_NAME,
			TestMVCActionCommand1.TEST_MVC_ACTION_COMMAND_NAME +
				StringPool.COMMA +
					TestMVCActionCommand2.TEST_MVC_ACTION_COMMAND_NAME);

		_genericPortlet.processAction(
			mockActionRequest, new MockActionResponse());

		Assert.assertNotNull(
			mockActionRequest.getAttribute(
				TestMVCActionCommand1.TEST_MVC_ACTION_COMMAND_ATTRIBUTE));
		Assert.assertEquals(
			TestMVCActionCommand1.TEST_MVC_ACTION_COMMAND_ATTRIBUTE,
			mockActionRequest.getAttribute(
				TestMVCActionCommand1.TEST_MVC_ACTION_COMMAND_ATTRIBUTE));
		Assert.assertNotNull(
			mockActionRequest.getAttribute(
				TestMVCActionCommand2.TEST_MVC_ACTION_COMMAND_ATTRIBUTE));
		Assert.assertEquals(
			TestMVCActionCommand2.TEST_MVC_ACTION_COMMAND_ATTRIBUTE,
			mockActionRequest.getAttribute(
				TestMVCActionCommand2.TEST_MVC_ACTION_COMMAND_ATTRIBUTE));
	}

	@Test
	public void testSingleMVCActionCommand() throws Exception {
		MockActionRequest mockActionRequest = new MockLiferayPortletRequest();

		mockActionRequest.addParameter(
			ActionRequest.ACTION_NAME,
			TestMVCActionCommand1.TEST_MVC_ACTION_COMMAND_NAME);

		_genericPortlet.processAction(
			mockActionRequest, new MockActionResponse());

		Assert.assertNotNull(
			mockActionRequest.getAttribute(
				TestMVCActionCommand1.TEST_MVC_ACTION_COMMAND_ATTRIBUTE));
		Assert.assertEquals(
			TestMVCActionCommand1.TEST_MVC_ACTION_COMMAND_ATTRIBUTE,
			mockActionRequest.getAttribute(
				TestMVCActionCommand1.TEST_MVC_ACTION_COMMAND_ATTRIBUTE));
	}

	private static GenericPortlet _genericPortlet;
	private static ServiceTracker<GenericPortlet, GenericPortlet>
		_genericPortletServiceTracker;

	private static class MockLiferayPortletRequest
		extends MockActionRequest implements LiferayPortletRequest {

		@Override
		public void addParameter(String name, String value) {
			_mockHttpServletRequest.addParameter(name, value);

			super.addParameter(name, value);
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
		public HttpServletRequest getHttpServletRequest() {
			return _mockHttpServletRequest;
		}

		@Override
		public long getPlid() {
			return 0;
		}

		@Override
		public String getPortletName() {
			return null;
		}

		private final MockHttpServletRequest _mockHttpServletRequest =
			new MockHttpServletRequest();

	}

}