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

package com.liferay.portal.security.permission.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.ResourceAction;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.ResourceActionLocalService;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Michael Bowerman
 */
@RunWith(Arquillian.class)
public class ResourceActionLocalServiceTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() {
		List<String> actionIds = new ArrayList<>(3);

		actionIds.add(ActionKeys.VIEW);
		actionIds.add(_ACTION_ID_1);
		actionIds.add(_ACTION_ID_2);

		_resourceActionLocalService.checkResourceActions(_NAME, actionIds);

		actionIds.set(2, _ACTION_ID_3);

		_resourceActionLocalService.deleteResourceAction(
			_resourceActionLocalService.fetchResourceAction(
				_NAME, _ACTION_ID_2));

		_resourceActionLocalService.checkResourceActions(_NAME, actionIds);
	}

	@After
	public void tearDown() {
		List<ResourceAction> resourceActions =
			_resourceActionLocalService.getResourceActions(_NAME);

		for (ResourceAction resourceAction : resourceActions) {
			_resourceActionLocalService.deleteResourceAction(resourceAction);
		}
	}

	@Test(expected = SystemException.class)
	public void testAddMoreThan64Actions() {
		List<String> actionIds = new ArrayList<>(65);

		actionIds.add(ActionKeys.VIEW);

		for (int i = 0; i < 64; i++) {
			actionIds.add("actionId" + i);
		}

		_resourceActionLocalService.checkResourceActions(_NAME, actionIds);
	}

	@Test
	public void testFirstAvailableBitwiseValueGetsGenerated()
		throws PortalException {

		ResourceAction resourceAction1 =
			_resourceActionLocalService.getResourceAction(
				_NAME, ActionKeys.VIEW);

		Assert.assertEquals(1L, resourceAction1.getBitwiseValue());

		ResourceAction resourceAction2 =
			_resourceActionLocalService.getResourceAction(_NAME, _ACTION_ID_1);

		Assert.assertEquals(2L, resourceAction2.getBitwiseValue());

		ResourceAction resourceAction3 =
			_resourceActionLocalService.getResourceAction(_NAME, _ACTION_ID_3);

		Assert.assertEquals(4L, resourceAction3.getBitwiseValue());
	}

	@Test
	public void testViewActionBitwiseValue() throws PortalException {
		ResourceAction viewResourceAction =
			_resourceActionLocalService.getResourceAction(
				_NAME, ActionKeys.VIEW);

		Assert.assertEquals(1L, viewResourceAction.getBitwiseValue());
	}

	private static final String _ACTION_ID_1 = "actionId1";

	private static final String _ACTION_ID_2 = "actionId2";

	private static final String _ACTION_ID_3 = "actionId3";

	private static final String _NAME =
		ResourceActionLocalServiceTest.class.getName();

	@Inject
	private ResourceActionLocalService _resourceActionLocalService;

}