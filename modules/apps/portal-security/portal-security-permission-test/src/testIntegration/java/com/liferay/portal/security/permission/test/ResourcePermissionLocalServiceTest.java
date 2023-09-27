/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.permission.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Resource;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.model.impl.ResourceImpl;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.ArrayList;
import java.util.Collections;

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

		try {
			_resourcePermissionLocalService.hasResourcePermission(
				new ArrayList<Resource>() {
					{
						add(_createResource(ResourceConstants.SCOPE_GROUP));
						add(_createResource(ResourceConstants.SCOPE_COMPANY));
					}
				},
				_roleIds, ActionKeys.VIEW);
		}
		catch (IllegalArgumentException illegalArgumentException) {
			Assert.assertEquals(
				"The first resource must be an individual scope",
				illegalArgumentException.getMessage());
		}
	}

	@Test
	public void testShouldFailIfLastResourceIsNotCompany() throws Exception {
		try {
			_resourcePermissionLocalService.hasResourcePermission(
				new ArrayList<Resource>() {
					{
						add(
							_createResource(
								ResourceConstants.SCOPE_INDIVIDUAL));
						add(_createResource(ResourceConstants.SCOPE_GROUP));
					}
				},
				_roleIds, ActionKeys.VIEW);
		}
		catch (IllegalArgumentException illegalArgumentException) {
			Assert.assertEquals(
				"The last resource must be a company scope",
				illegalArgumentException.getMessage());
		}
	}

	@Test
	public void testShouldFailIfResourcesIsLessThanTwo() throws Exception {
		try {
			_resourcePermissionLocalService.hasResourcePermission(
				Collections.singletonList(new ResourceImpl()), _roleIds,
				ActionKeys.VIEW);
		}
		catch (IllegalArgumentException illegalArgumentException) {
			Assert.assertEquals(
				"The list of resources must contain at least two values",
				illegalArgumentException.getMessage());
		}
	}

	private Resource _createResource(int scope) {
		Resource resource = new ResourceImpl();

		resource.setScope(scope);

		return resource;
	}

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	private final long[] _roleIds = new long[1];

	@Inject
	private RoleLocalService _roleLocalService;

}