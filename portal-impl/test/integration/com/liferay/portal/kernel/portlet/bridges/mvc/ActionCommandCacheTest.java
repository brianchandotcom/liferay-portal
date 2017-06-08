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

import com.liferay.portal.kernel.portlet.bridges.mvc.bundle.actioncommandcache.TestActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.bundle.actioncommandcache.TestActionCommand2;
import com.liferay.portal.kernel.portlet.bridges.mvc.bundle.actioncommandcache.TestActionCommandPortlet;
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
public class ActionCommandCacheTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE,
			new SyntheticBundleRule("bundle.actioncommandcache"));

	@BeforeClass
	public static void setUpClass() throws Exception {
		Registry registry = RegistryUtil.getRegistry();

		Filter portletFilter = registry.getFilter(
			"(&(objectClass=javax.portlet.Portlet)(javax.portlet.name=" +
				TestActionCommandPortlet.PORTLET_NAME + "))");

		_portletServiceTracker = registry.trackServices(portletFilter);

		_portletServiceTracker.open();

		_portlet = _portletServiceTracker.getService();
	}

	@AfterClass
	public static void tearDownClass() {
		_portletServiceTracker.close();
	}

	@Test
	public void testProcessAction() throws Exception {
		MockActionRequest actionRequest = new MockActionRequest();

		actionRequest.addParameter(
			ActionRequest.ACTION_NAME,
			TestActionCommand.TEST_ACTION_COMMAND_NAME);

		_portlet.processAction(actionRequest, new MockActionResponse());

		Assert.assertNotNull(
			actionRequest.getAttribute(
				TestActionCommand.TEST_ACTION_COMMAND_ATTRIBUTE));
		Assert.assertEquals(
			TestActionCommand.TEST_ACTION_COMMAND_ATTRIBUTE,
			actionRequest.getAttribute(
				TestActionCommand.TEST_ACTION_COMMAND_ATTRIBUTE));
	}

	@Test
	public void testProcessActionMultipleActionCommands() throws Exception {
		MockActionRequest actionRequest = new MockActionRequest();

		StringBuilder actionName = new StringBuilder(3);

		actionName.append(TestActionCommand.TEST_ACTION_COMMAND_NAME);
		actionName.append(StringPool.COMMA);
		actionName.append(TestActionCommand2.TEST_ACTION_COMMAND_NAME);

		actionRequest.addParameter(
			ActionRequest.ACTION_NAME, actionName.toString());

		_portlet.processAction(actionRequest, new MockActionResponse());

		Assert.assertNotNull(
			actionRequest.getAttribute(
				TestActionCommand.TEST_ACTION_COMMAND_ATTRIBUTE));
		Assert.assertEquals(
			TestActionCommand.TEST_ACTION_COMMAND_ATTRIBUTE,
			actionRequest.getAttribute(
				TestActionCommand.TEST_ACTION_COMMAND_ATTRIBUTE));

		Assert.assertNotNull(
			actionRequest.getAttribute(
				TestActionCommand2.TEST_ACTION_COMMAND_ATTRIBUTE));
		Assert.assertEquals(
			TestActionCommand2.TEST_ACTION_COMMAND_ATTRIBUTE,
			actionRequest.getAttribute(
				TestActionCommand2.TEST_ACTION_COMMAND_ATTRIBUTE));
	}

	private static GenericPortlet _portlet;
	private static ServiceTracker<GenericPortlet, GenericPortlet>
		_portletServiceTracker;

}