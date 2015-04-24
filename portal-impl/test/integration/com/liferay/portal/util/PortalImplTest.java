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

import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;
import com.liferay.portal.test.rule.SyntheticBundleRule;
import com.liferay.portal.util.bundle.portalimpl.TestAlwaysAllowDoAsUser;
import com.liferay.portal.util.test.AtomicState;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Peter Fellwock
 */
public class PortalImplTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE,
			new SyntheticBundleRule("bundle.portalimpl"));

	@BeforeClass
	public static void setUpClass() {
		_atomicState = new AtomicState();
	}

	@AfterClass
	public static void tearDownClass() {
		_atomicState.close();
	}

	@Test
	public void testGetUserId() {
		_atomicState.reset();

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();
		mockHttpServletRequest.setParameter(
			"_TestAlwaysAllowDoAsUser_actionName",
			TestAlwaysAllowDoAsUser.ACTION_NAME);
		mockHttpServletRequest.setParameter(
			"_TestAlwaysAllowDoAsUser_struts_action",
			TestAlwaysAllowDoAsUser.STRUTS_ACTION);
		mockHttpServletRequest.setParameter(
			"p_p_id", "TestAlwaysAllowDoAsUser");

		Long userId = PortalUtil.getUserId(mockHttpServletRequest);

		Assert.assertEquals(Long.valueOf("0"), new Long(userId));

		Assert.assertTrue(_atomicState.isSet());

		_atomicState.reset();

		mockHttpServletRequest = new MockHttpServletRequest();
		mockHttpServletRequest.setPathInfo(
			"/TestAlwaysAllowDoAsUser/integration/test");

		userId = PortalUtil.getUserId(mockHttpServletRequest);

		Assert.assertEquals(Long.valueOf("0"), new Long(userId));

		Assert.assertTrue(_atomicState.isSet());
	}

	private static AtomicState _atomicState;

}