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

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
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

	@Test
	public void testShouldFailIfFirstResourceIsNotIndividual()
		throws Exception {

		_testResources(
			"The first resource must be an individual scope",
			Arrays.asList(
				_createResource(ResourceConstants.SCOPE_GROUP),
				_createResource(ResourceConstants.SCOPE_COMPANY)));
	}

	@Test
	public void testShouldFailIfLastResourceIsNotCompany() throws Exception {
		_testResources(
			"The last resource must be a company scope",
			Arrays.asList(
				_createResource(ResourceConstants.SCOPE_INDIVIDUAL),
				_createResource(ResourceConstants.SCOPE_GROUP)));
	}

	@Test
	public void testShouldFailIfResourcesIsLessThanTwo() throws Exception {
		_testResources(
			"The list of resources must contain at least two values",
			Arrays.asList(new ResourceImpl()));
	}

	private Resource _createResource(int scope) {
		Resource resource = new ResourceImpl();

		resource.setScope(scope);

		return resource;
	}

	private void _testResources(
			String expectedMessage, List<Resource> resources)
		throws Exception {

		_group = GroupTestUtil.addGroup();

		Role guestRole = _roleLocalService.getRole(
			_group.getCompanyId(), RoleConstants.GUEST);

		try {
			_resourcePermissionLocalService.hasResourcePermission(
				resources, new long[] {guestRole.getRoleId()}, ActionKeys.VIEW);
		}
		catch (IllegalArgumentException illegalArgumentException) {
			Assert.assertEquals(
				expectedMessage, illegalArgumentException.getMessage());
		}
	}

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	@Inject
	private RoleLocalService _roleLocalService;

}