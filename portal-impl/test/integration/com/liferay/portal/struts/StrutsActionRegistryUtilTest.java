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

package com.liferay.portal.struts;

import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.SyntheticBundleRule;
import com.liferay.portal.util.test.AtomicState;

import java.util.Map;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Philip Jones
 */
public class StrutsActionRegistryUtilTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			new SyntheticBundleRule("bundle.strutsactionregistryutil"));

	@BeforeClass
	public static void setUpClass() {
		_atomicState = new AtomicState();
	}

	@AfterClass
	public static void tearDownClass() {
		_atomicState.close();
	}

	@Test
	public void testGetActions() throws Exception {
		Map<String, org.apache.struts.action.Action> actions =
			StrutsActionRegistryUtil.getActions();

		ActionAdapter actionAdapter = (ActionAdapter)actions.get(
			"TestStrutsAction");

		Assert.assertNotNull(actionAdapter);

		_atomicState.reset();

		actionAdapter.execute(null, null, null, null);

		Assert.assertTrue(_atomicState.isSet());

		PortletActionAdapter portletActionAdapter =
			(PortletActionAdapter)actions.get("TestStrutsPortletAction");

		Assert.assertNotNull(portletActionAdapter);

		_atomicState.reset();

		portletActionAdapter.isCheckMethodOnProcessAction();

		Assert.assertTrue(_atomicState.isSet());
	}

	@Test
	public void testGetGetAction() throws Exception {
		ActionAdapter actionAdapter =
			(ActionAdapter)StrutsActionRegistryUtil.getAction(
				"TestStrutsAction");

		Assert.assertNotNull(actionAdapter);

		_atomicState.reset();

		actionAdapter.execute(null, null, null, null);

		Assert.assertTrue(_atomicState.isSet());

		PortletActionAdapter portletActionAdapter =
			(PortletActionAdapter)StrutsActionRegistryUtil.getAction(
				"TestStrutsPortletAction");

		Assert.assertNotNull(portletActionAdapter);

		_atomicState.reset();

		portletActionAdapter.isCheckMethodOnProcessAction();

		Assert.assertTrue(_atomicState.isSet());
	}

	private static AtomicState _atomicState;

}