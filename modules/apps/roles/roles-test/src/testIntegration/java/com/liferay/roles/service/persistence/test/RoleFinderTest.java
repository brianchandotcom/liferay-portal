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

package com.liferay.roles.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.ResourceAction;
import com.liferay.portal.kernel.model.ResourcePermission;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.service.ResourceActionLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.persistence.RoleFinder;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.ResourcePermissionTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.TransactionalTestRule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alberto Chaparro
 */
@RunWith(Arquillian.class)
public class RoleFinderTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), TransactionalTestRule.INSTANCE);

	@BeforeClass
	public static void setUpClass() throws Exception {
		List<Role> roles = _roleLocalService.getRoles(
			RoleConstants.TYPE_REGULAR, StringPool.BLANK);

		_arbitraryRole = roles.get(0);

		List<ResourceAction> resourceActions =
			_resourceActionLocalService.getResourceActions(0, 1);

		_arbitraryResourceAction = resourceActions.get(0);

		_resourcePermission = ResourcePermissionTestUtil.addResourcePermission(
			_arbitraryResourceAction.getBitwiseValue(),
			_arbitraryResourceAction.getName(), _arbitraryRole.getRoleId());
	}

	@AfterClass
	public static void tearDownClass() {
		_resourcePermissionLocalService.deleteResourcePermission(
			_resourcePermission);
	}

	@Test
	public void testFindByC_N_S_P() throws Exception {
		long companyId = _resourcePermission.getCompanyId();
		String name = _resourcePermission.getName();
		int scope = _resourcePermission.getScope();
		String primKey = _resourcePermission.getPrimKey();

		Map<String, List<String>> actionIdsLists = new HashMap<>();

		List<ResourceAction> resourceActions =
			_resourceActionLocalService.getResourceActions(name);

		for (ResourcePermission resourcePermission :
				_resourcePermissionLocalService.getResourcePermissions(
					companyId, name, scope, primKey)) {

			Role role = _roleLocalService.getRole(
				resourcePermission.getRoleId());

			long actionIds = resourcePermission.getActionIds();

			List<String> actionIdsList = new ArrayList<>();

			for (ResourceAction resourceAction : resourceActions) {
				if ((resourceAction.getBitwiseValue() & actionIds) != 0) {
					actionIdsList.add(resourceAction.getActionId());
				}
			}

			actionIdsLists.put(role.getName(), actionIdsList);
		}

		Assert.assertEquals(
			actionIdsLists,
			_roleFinder.findByC_N_S_P(companyId, name, scope, primKey));
	}

	@Test
	public void testFindByC_N_S_P_A() {
		List<Role> roles = _roleFinder.findByC_N_S_P_A(
			_resourcePermission.getCompanyId(), _resourcePermission.getName(),
			_resourcePermission.getScope(), _resourcePermission.getPrimKey(),
			_arbitraryResourceAction.getActionId());

		Assert.assertTrue(
			"The method findByC_N_S_P_A should have returned the role " +
				_arbitraryRole.getRoleId(),
			roles.contains(_arbitraryRole));
	}

	private static ResourceAction _arbitraryResourceAction;
	private static Role _arbitraryRole;

	@Inject
	private static ResourceActionLocalService _resourceActionLocalService;

	private static ResourcePermission _resourcePermission;

	@Inject
	private static ResourcePermissionLocalService
		_resourcePermissionLocalService;

	@Inject
	private static RoleLocalService _roleLocalService;

	@Inject
	private RoleFinder _roleFinder;

}