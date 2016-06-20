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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
		List<String> actionIds = new ArrayList<>();

		actionIds.add(_ACTION_ID + 1);
		actionIds.add(_ACTION_ID + 2);
		actionIds.add(_ACTION_ID + 3);
		actionIds.add(_VIEW_ACTION_ID);

		ResourceActionLocalServiceUtil.checkResourceActions(_NAME, actionIds);

		actionIds.remove(_ACTION_ID + 2);

		actionIds.add(_ACTION_ID + 4);

		ResourceActionLocalServiceUtil.deleteResourceAction(
			ResourceActionLocalServiceUtil.fetchResourceAction(
				_NAME, _ACTION_ID + 2));

		ResourceActionLocalServiceUtil.checkResourceActions(_NAME, actionIds);
	}

	@After
	public void tearDown() {
		List<ResourceAction> resourceActions =
			ResourceActionLocalServiceUtil.getResourceActions(_NAME);

		resourceActions.addAll(
			ResourceActionLocalServiceUtil.getResourceActions(_NAME + 2));

		for (ResourceAction resourceAction : resourceActions) {
			ResourceActionLocalServiceUtil.deleteResourceAction(resourceAction);
		}
	}

	@Test(expected = SystemException.class)
	public void testAddMoreThan64Actions() throws Exception {
		List<String> actionIds = new ArrayList<>();

		for (int i = 1; i <= 65; i++) {
			actionIds.add(_ACTION_ID + i);
		}

		ResourceActionLocalServiceUtil.checkResourceActions(
			_NAME + 2, actionIds);
	}

	@Test
	public void testFirstAvailableBitwiseValueGetsGenerated() {
		ResourceAction resourceAction1 =
			ResourceActionLocalServiceUtil.fetchResourceAction(
				_NAME, _ACTION_ID + 1);

		ResourceAction resourceAction3 =
			ResourceActionLocalServiceUtil.fetchResourceAction(
				_NAME, _ACTION_ID + 3);

		ResourceAction resourceAction4 =
			ResourceActionLocalServiceUtil.fetchResourceAction(
				_NAME, _ACTION_ID + 4);

		ResourceAction viewResourceAction =
			ResourceActionLocalServiceUtil.fetchResourceAction(
				_NAME, _VIEW_ACTION_ID);

		Set<Long> bitwiseValues = new HashSet<>();

		bitwiseValues.add(resourceAction1.getBitwiseValue());
		bitwiseValues.add(resourceAction3.getBitwiseValue());
		bitwiseValues.add(resourceAction4.getBitwiseValue());
		bitwiseValues.add(viewResourceAction.getBitwiseValue());

		Assert.assertTrue(
			"Unexpected set of bitwise values: " + bitwiseValues,
			bitwiseValues.contains(1L));

		Assert.assertTrue(
			"Unexpected set of bitwise values: " + bitwiseValues,
			bitwiseValues.contains(2L));

		Assert.assertTrue(
			"Unexpected set of bitwise values: " + bitwiseValues,
			bitwiseValues.contains(4L));

		Assert.assertTrue(
			"Unexpected set of bitwise values: " + bitwiseValues,
			bitwiseValues.contains(8L));
	}

	@Test
	public void testViewActionGetsBitwiseValue1() {
		ResourceAction viewResourceAction =
			ResourceActionLocalServiceUtil.fetchResourceAction(
				_NAME, _VIEW_ACTION_ID);

		Assert.assertEquals(1L, viewResourceAction.getBitwiseValue());
	}

	private static final String _ACTION_ID = "actionId";

	private static final String _NAME = "name";

	private static final String _VIEW_ACTION_ID = ActionKeys.VIEW;

}