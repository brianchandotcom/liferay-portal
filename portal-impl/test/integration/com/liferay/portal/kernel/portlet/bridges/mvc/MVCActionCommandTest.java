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

import com.liferay.portal.kernel.portlet.bridges.mvc.bundle.actioncommand.TestMVCActionCommand1;
import com.liferay.portal.kernel.portlet.bridges.mvc.bundle.actioncommand.TestMVCActionCommand2;
import com.liferay.portal.kernel.portlet.bridges.mvc.bundle.actioncommand.TestPortlet;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;
import com.liferay.portal.test.rule.SyntheticBundleRule;
import com.liferay.registry.Filter;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceTracker;

import javax.portlet.ActionRequest;
import javax.portlet.GenericPortlet;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

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
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE,
			new SyntheticBundleRule("bundle.actioncommand"));

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
	public void testProcessActionMultipleMVCActionCommands() throws Exception {
		MockActionRequest mockActionRequest = new MockActionRequest();

		mockActionRequest.addParameter(
			ActionRequest.ACTION_NAME,
			TestMVCActionCommand1.TEST_ACTION_COMMAND_NAME + StringPool.COMMA +
				TestMVCActionCommand2.TEST_ACTION_COMMAND_NAME);

		_genericPortlet.processAction(
			mockActionRequest, new MockActionResponse());

		Assert.assertNotNull(
			mockActionRequest.getAttribute(
				TestMVCActionCommand1.TEST_ACTION_COMMAND_ATTRIBUTE));
		Assert.assertEquals(
			TestMVCActionCommand1.TEST_ACTION_COMMAND_ATTRIBUTE,
			mockActionRequest.getAttribute(
				TestMVCActionCommand1.TEST_ACTION_COMMAND_ATTRIBUTE));
		Assert.assertNotNull(
			mockActionRequest.getAttribute(
				TestMVCActionCommand2.TEST_ACTION_COMMAND_ATTRIBUTE));
		Assert.assertEquals(
			TestMVCActionCommand2.TEST_ACTION_COMMAND_ATTRIBUTE,
			mockActionRequest.getAttribute(
				TestMVCActionCommand2.TEST_ACTION_COMMAND_ATTRIBUTE));
	}

	@Test
	public void testProcessActionOneMVCActionCommand() throws Exception {
		MockActionRequest mockActionRequest = new MockActionRequest();

		mockActionRequest.addParameter(
			ActionRequest.ACTION_NAME,
			TestMVCActionCommand1.TEST_ACTION_COMMAND_NAME);

		_genericPortlet.processAction(
			mockActionRequest, new MockActionResponse());

		Assert.assertNotNull(
			mockActionRequest.getAttribute(
				TestMVCActionCommand1.TEST_ACTION_COMMAND_ATTRIBUTE));
		Assert.assertEquals(
			TestMVCActionCommand1.TEST_ACTION_COMMAND_ATTRIBUTE,
			mockActionRequest.getAttribute(
				TestMVCActionCommand1.TEST_ACTION_COMMAND_ATTRIBUTE));
	}

	private static GenericPortlet _genericPortlet;
	private static ServiceTracker<GenericPortlet, GenericPortlet>
		_genericPortletServiceTracker;

}