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

package com.liferay.portlet.service.persistence;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.TransactionalTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ResourcePermissionTestUtil;
import com.liferay.portal.kernel.test.util.ResourceTypePermissionTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.model.ResourceAction;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.ResourcePermission;
import com.liferay.portal.model.ResourceTypePermission;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.ResourceActionLocalServiceUtil;
import com.liferay.portal.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.service.ResourceTypePermissionLocalServiceUtil;
import com.liferay.portal.service.persistence.GroupFinderUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.comparator.GroupNameComparator;
import com.liferay.portal.util.test.LayoutTestUtil;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Alberto Chaparro
 * @author László Csontos
 */
public class GroupFinderTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE,
			TransactionalTestRule.INSTANCE);

	@BeforeClass
	public static void setUpClass() throws Exception {
		_group = GroupTestUtil.addGroup();

		List<ResourceAction> resourceActions =
			ResourceActionLocalServiceUtil.getResourceActions(0, 1);

		_arbitraryResourceAction = resourceActions.get(0);

		_resourcePermission = ResourcePermissionTestUtil.addResourcePermission(
			_arbitraryResourceAction.getBitwiseValue(),
			_arbitraryResourceAction.getName(),
			StringUtil.valueOf(_group.getGroupId()),
			ResourceConstants.SCOPE_GROUP);

		_modelResourceAction = getModelResourceAction();

		_resourceTypePermission =
			ResourceTypePermissionTestUtil.addResourceTypePermission(
				_modelResourceAction.getBitwiseValue(), _group.getGroupId(),
				_modelResourceAction.getName());

		ResourcePermissionTestUtil.addResourcePermission(
			_modelResourceAction.getBitwiseValue(),
			_modelResourceAction.getName(),
			StringUtil.valueOf(_group.getGroupId()),
			_resourceTypePermission.getRoleId(), ResourceConstants.SCOPE_GROUP);
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		GroupLocalServiceUtil.deleteGroup(_group);

		ResourcePermissionLocalServiceUtil.deleteResourcePermission(
			_resourcePermission);

		ResourceTypePermissionLocalServiceUtil.deleteResourceTypePermission(
			_resourceTypePermission);
	}

	@Test
	public void testFindByC_C_N_DJoinByRoleResourcePermissions()
		throws Exception {

		List<Group> groups = findByC_C_N_D(
			_arbitraryResourceAction.getActionId(),
			_resourcePermission.getName(), _resourcePermission.getRoleId());

		for (Group group : groups) {
			if (group.getGroupId() == _group.getGroupId()) {
				return;
			}
		}

		Assert.fail(
			"The method findByC_C_N_D should have returned the group " +
				_group.getGroupId());
	}

	@Test
	public void testFindByC_C_N_DJoinByRoleResourceTypePermissions()
		throws Exception {

		List<Group> groups = findByC_C_N_D(
			_modelResourceAction.getActionId(),
			_resourceTypePermission.getName(),
			_resourceTypePermission.getRoleId());

		for (Group group : groups) {
			if (group.getGroupId() == _group.getGroupId()) {
				return;
			}
		}

		Assert.fail(
			"The method findByC_C_N_D should have returned the group " +
				_group.getGroupId());
	}

	@Test
	public void testFindByCompanyId() throws Exception {
		LinkedHashMap<String, Object> groupParams = new LinkedHashMap<>();

		groupParams.put("inherit", Boolean.TRUE);
		groupParams.put("site", Boolean.TRUE);
		groupParams.put("usersGroups", TestPropsValues.getUserId());

		List<Group> groups = GroupFinderUtil.findByCompanyId(
			TestPropsValues.getCompanyId(), groupParams, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, new GroupNameComparator(true));

		Assert.assertFalse(groups.isEmpty());
	}

	@Test
	public void testFindByLayouts() throws Exception {
		List<Group> groups = findByLayouts(
			GroupConstants.DEFAULT_PARENT_GROUP_ID);

		int initialGroupCount = groups.size();

		GroupTestUtil.addGroup();

		Group parentGroup = GroupTestUtil.addGroup();

		LayoutTestUtil.addLayout(parentGroup);

		Group childGroup1 = GroupTestUtil.addGroup(parentGroup.getGroupId());

		LayoutTestUtil.addLayout(childGroup1);

		Group childGroup2 = GroupTestUtil.addGroup(parentGroup.getGroupId());

		LayoutTestUtil.addLayout(childGroup2);

		groups = findByLayouts(GroupConstants.DEFAULT_PARENT_GROUP_ID);

		Assert.assertEquals(initialGroupCount + 1, groups.size());

		groups = findByLayouts(parentGroup.getGroupId());

		Assert.assertEquals(2, groups.size());

		groups = findByLayouts(childGroup1.getGroupId());

		Assert.assertTrue(groups.isEmpty());
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

	protected void addLayout(long groupId) throws Exception {
		LayoutTestUtil.addLayout(groupId);

		LayoutTestUtil.addLayout(groupId);
	}

	protected List<Group> findByC_C_N_D(
			String actionId, String name, long roleId)
		throws Exception {

		LinkedHashMap<String, Object> groupParams = new LinkedHashMap<>();

		List<Object> rolePermissions = new ArrayList<>();

		rolePermissions.add(name);
		rolePermissions.add(new Integer(ResourceConstants.SCOPE_GROUP));
		rolePermissions.add(actionId);
		rolePermissions.add(roleId);

		groupParams.put("rolePermissions", rolePermissions);

		long[] classNameIds = {PortalUtil.getClassNameId(Group.class)};

		return GroupFinderUtil.findByC_C_PG_N_D(
			TestPropsValues.getCompanyId(), classNameIds,
			GroupConstants.ANY_PARENT_GROUP_ID, new String[] {null},
			new String[] {null}, groupParams, true, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	protected List<Group> findByLayouts(long parentGroupId) throws Exception {
		return GroupFinderUtil.findByLayouts(
			TestPropsValues.getCompanyId(), parentGroupId, true,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			new GroupNameComparator(true));
	}

	private static ResourceAction _arbitraryResourceAction;
	private static Group _group;
	private static ResourceAction _modelResourceAction;
	private static ResourcePermission _resourcePermission;
	private static ResourceTypePermission _resourceTypePermission;

}