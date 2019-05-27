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
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Resource;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.model.impl.ResourceImpl;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Manuel de la Peña
 */
@RunWith(Arquillian.class)
public class ResourcePermissionLocalServiceTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		Role guestRole = _roleLocalService.getRole(
			_group.getCompanyId(), RoleConstants.GUEST);

		_roleIds[0] = guestRole.getRoleId();
	}

	@Test
	public void testShouldFailIfFirstResourceIsNotIndividual()
		throws Exception {

		List<Resource> resources = new ArrayList<>();

		Resource firstResource = new ResourceImpl();

		firstResource.setScope(ResourceConstants.SCOPE_GROUP);

		resources.add(firstResource);

		Resource lastResource = new ResourceImpl();

		lastResource.setScope(ResourceConstants.SCOPE_COMPANY);

		resources.add(lastResource);

		try {
			_resourcePermissionLocalService.hasResourcePermission(
				resources, _roleIds, ActionKeys.VIEW);
		}
		catch (IllegalArgumentException iae) {
			Assert.assertEquals(
				"The first resource must be an individual scope",
				iae.getMessage());
		}
	}

	@Test
	public void testShouldFailIfLastResourceIsNotCompany() throws Exception {
		List<Resource> resources = new ArrayList<>();

		Resource firstResource = new ResourceImpl();

		firstResource.setScope(ResourceConstants.SCOPE_INDIVIDUAL);

		resources.add(firstResource);

		Resource lastResource = new ResourceImpl();

		lastResource.setScope(ResourceConstants.SCOPE_GROUP);

		resources.add(lastResource);

		try {
			_resourcePermissionLocalService.hasResourcePermission(
				resources, _roleIds, ActionKeys.VIEW);
		}
		catch (IllegalArgumentException iae) {
			Assert.assertEquals(
				"The last resource must be a company scope", iae.getMessage());
		}
	}

	@Test
	public void testShouldFailIfResourcesIsLessThanTwo() throws Exception {
		List<Resource> resources = new ArrayList<>();

		resources.add(new ResourceImpl());

		try {
			_resourcePermissionLocalService.hasResourcePermission(
				resources, _roleIds, ActionKeys.VIEW);
		}
		catch (IllegalArgumentException iae) {
			Assert.assertEquals(
				"The list of resources must contain at least two values",
				iae.getMessage());
		}
	}

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	private final long[] _roleIds = new long[1];

	@Inject
	private RoleLocalService _roleLocalService;

}