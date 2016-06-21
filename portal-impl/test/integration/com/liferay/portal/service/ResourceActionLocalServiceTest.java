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

package com.liferay.portal.service;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.ResourceAction;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.ResourceActionLocalServiceUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Michael Bowerman
 */
public class ResourceActionLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() {
		List<String> actionIds = new ArrayList<>(3);

		actionIds.add(ActionKeys.VIEW);

		actionIds.add(_ACTION_ID_1);
		actionIds.add(_ACTION_ID_2);

		ResourceActionLocalServiceUtil.checkResourceActions(_NAME, actionIds);

		actionIds.set(2, _ACTION_ID_3);

		ResourceActionLocalServiceUtil.deleteResourceAction(
			ResourceActionLocalServiceUtil.fetchResourceAction(
				_NAME, _ACTION_ID_2));

		ResourceActionLocalServiceUtil.checkResourceActions(_NAME, actionIds);
	}

	@After
	public void tearDown() {
		List<ResourceAction> resourceActions =
			ResourceActionLocalServiceUtil.getResourceActions(_NAME);

		for (ResourceAction resourceAction : resourceActions) {
			ResourceActionLocalServiceUtil.deleteResourceAction(resourceAction);
		}
	}

	@Test(expected = SystemException.class)
	public void testAddMoreThan64Actions() throws Exception {
		List<String> actionIds = new ArrayList<>(65);

		actionIds.add(ActionKeys.VIEW);

		for (int i = 0; i < 64; i++) {
			actionIds.add("actionId" + i);
		}

		ResourceActionLocalServiceUtil.checkResourceActions(_NAME, actionIds);
	}

	@Test
	public void testFirstAvailableBitwiseValueGetsGenerated() throws Exception {
		ResourceAction resourceAction =
			ResourceActionLocalServiceUtil.getResourceAction(
				_NAME, ActionKeys.VIEW);

		Assert.assertEquals(1L, resourceAction.getBitwiseValue());

		resourceAction = ResourceActionLocalServiceUtil.getResourceAction(
			_NAME, _ACTION_ID_1);

		Assert.assertEquals(2L, resourceAction.getBitwiseValue());

		resourceAction = ResourceActionLocalServiceUtil.getResourceAction(
			_NAME, _ACTION_ID_3);

		Assert.assertEquals(4L, resourceAction.getBitwiseValue());
	}

	@Test
	public void testViewActionBitwiseValue() throws Exception {
		ResourceAction viewResourceAction =
			ResourceActionLocalServiceUtil.getResourceAction(
				_NAME, ActionKeys.VIEW);

		Assert.assertEquals(1L, viewResourceAction.getBitwiseValue());
	}

	private static final String _ACTION_ID_1 = "actionId1";

	private static final String _ACTION_ID_2 = "actionId2";

	private static final String _ACTION_ID_3 = "actionId3";

	private static final String _NAME =
		ResourceActionLocalServiceTest.class.getName();

}