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

package com.liferay.portal.service.persistence;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.TransactionalTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ResourceBlockPermissionTestUtil;
import com.liferay.portal.kernel.test.util.ResourceBlockTestUtil;
import com.liferay.portal.kernel.test.util.ResourcePermissionTestUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.ResourceAction;
import com.liferay.portal.model.ResourceBlock;
import com.liferay.portal.model.ResourceBlockPermission;
import com.liferay.portal.model.ResourcePermission;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.ResourceActionLocalServiceUtil;
import com.liferay.portal.service.ResourceBlockLocalServiceUtil;
import com.liferay.portal.service.ResourceBlockPermissionLocalServiceUtil;
import com.liferay.portal.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Alberto Chaparro
 */
public class RoleFinderTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), TransactionalTestRule.INSTANCE);

	@BeforeClass
	public static void setUpClass() throws Exception {
		List<Role> roles = RoleLocalServiceUtil.getRoles(
			RoleConstants.TYPE_REGULAR, StringPool.BLANK);

		_arbitraryRole = roles.get(0);

		List<ResourceAction> resourceActions =
			ResourceActionLocalServiceUtil.getResourceActions(0, 1);

		_arbitraryResourceAction = resourceActions.get(0);

		_resourcePermission = ResourcePermissionTestUtil.addResourcePermission(
			_arbitraryResourceAction.getBitwiseValue(),
			_arbitraryResourceAction.getName(), _arbitraryRole.getRoleId());

		_modelResourceAction = getModelResourceAction();

		_resourceBlock = ResourceBlockTestUtil.addResourceBlock(
			_modelResourceAction.getName());

		_resourceBlockPermission =
			ResourceBlockPermissionTestUtil.addResourceBlockPermission(
				_resourceBlock.getResourceBlockId(), _arbitraryRole.getRoleId(),
				_modelResourceAction.getBitwiseValue());
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		ResourcePermissionLocalServiceUtil.deleteResourcePermission(
			_resourcePermission);

		ResourceBlockLocalServiceUtil.deleteResourceBlock(_resourceBlock);

		ResourceBlockPermissionLocalServiceUtil.deleteResourceBlockPermission(
			_resourceBlockPermission);
	}

	@Test
	public void testFindByC_N_S_P_A() throws Exception {
		boolean exists = false;

		List<Role> roles = RoleFinderUtil.findByC_N_S_P_A(
			_resourcePermission.getCompanyId(), _resourcePermission.getName(),
			_resourcePermission.getScope(), _resourcePermission.getPrimKey(),
			_arbitraryResourceAction.getActionId());

		for (Role role : roles) {
			if (role.getRoleId() == _arbitraryRole.getRoleId()) {
				exists = true;

				break;
			}
		}

		Assert.assertTrue(
			"The method findByC_N_S_P_A should have returned the role " +
				_arbitraryRole.getRoleId(),
			exists);
	}

	@Test
	public void testFindByR_N_A() throws Exception {
		boolean exists = false;

		List<Role> roles = RoleFinderUtil.findByR_N_A(
			_resourceBlock.getResourceBlockId(), _resourceBlock.getName(),
			_modelResourceAction.getActionId());

		for (Role role : roles) {
			if (role.getRoleId() == _arbitraryRole.getRoleId()) {
				exists = true;

				break;
			}
		}

		Assert.assertTrue(
			"The method findByR_N_A should have returned the role " +
				_arbitraryRole.getRoleId(),
			exists);
	}

	protected static ResourceAction getModelResourceAction()
		throws PortalException {

		String name = RandomTestUtil.randomString() + "Model";

		List<String> actionIds = new ArrayList<>();

		actionIds.add(ActionKeys.UPDATE);
		actionIds.add(ActionKeys.VIEW);

		ResourceActionLocalServiceUtil.checkResourceActions(
			name, actionIds, true);

		return ResourceActionLocalServiceUtil.getResourceAction(
			name, ActionKeys.VIEW);
	}

	private static ResourceAction _arbitraryResourceAction;
	private static Role _arbitraryRole;
	private static ResourceAction _modelResourceAction;
	private static ResourceBlock _resourceBlock;
	private static ResourceBlockPermission _resourceBlockPermission;
	private static ResourcePermission _resourcePermission;

}