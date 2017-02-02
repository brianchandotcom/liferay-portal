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
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.ResourceAction;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.ResourcePermission;
import com.liferay.portal.kernel.model.ResourceTypePermission;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.RolePermissions;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.OrganizationLocalServiceUtil;
import com.liferay.portal.kernel.service.ResourceActionLocalServiceUtil;
import com.liferay.portal.kernel.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.kernel.service.ResourceTypePermissionLocalServiceUtil;
import com.liferay.portal.kernel.service.UserGroupLocalServiceUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.service.persistence.GroupFinderUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.OrganizationTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ResourcePermissionTestUtil;
import com.liferay.portal.kernel.test.util.ResourceTypePermissionTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserGroupTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.comparator.GroupNameComparator;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.TransactionalTestRule;
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
			new LiferayIntegrationTestRule(), TransactionalTestRule.INSTANCE);

	@BeforeClass
	public static void setUpClass() throws Exception {
		_group = GroupTestUtil.addGroup();
		_organization = OrganizationTestUtil.addOrganization(true);

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
		GroupLocalServiceUtil.deleteGroup(_userGroupGroup);

		OrganizationLocalServiceUtil.deleteOrganization(_organization);

		ResourcePermissionLocalServiceUtil.deleteResourcePermission(
			_resourcePermission);

		ResourceTypePermissionLocalServiceUtil.deleteResourceTypePermission(
			_resourceTypePermission);

		UserLocalServiceUtil.deleteUser(_userGroupUser);

		UserGroupLocalServiceUtil.deleteUserGroup(_userGroup);
	}

	@Test
	public void testFindByC_C_N_DJoinByRoleResourcePermissions()
		throws Exception {

		boolean exists = false;

		List<Group> groups = findByC_C_N_D(
			_arbitraryResourceAction.getActionId(),
			_resourcePermission.getName(), _resourcePermission.getRoleId());

		for (Group group : groups) {
			if (group.getGroupId() == _group.getGroupId()) {
				exists = true;

				break;
			}
		}

		Assert.assertTrue(
			"The method findByC_C_N_D should have returned the group " +
				_group.getGroupId(),
			exists);
	}

	@Test
	public void testFindByC_C_N_DJoinByRoleResourceTypePermissions()
		throws Exception {

		List<Group> groups = findByC_C_N_D(
			_modelResourceAction.getActionId(),
			_resourceTypePermission.getName(),
			_resourceTypePermission.getRoleId());

		boolean exists = false;

		for (Group group : groups) {
			if (group.getGroupId() == _group.getGroupId()) {
				exists = true;

				break;
			}
		}

		Assert.assertTrue(
			"The method findByC_C_N_D should have returned the group " +
				_group.getGroupId(),
			exists);
	}

	@Test
	public void testFindByC_C_PG_N_D() throws Exception {
		_userGroup = UserGroupTestUtil.addUserGroup();
		_userGroupUser = UserTestUtil.addUser();

		UserGroupLocalServiceUtil.addUserUserGroup(
			_userGroupUser.getUserId(), _userGroup);

		Group group = _organization.getGroup();

		GroupLocalServiceUtil.addUserGroupGroup(
			_userGroup.getUserGroupId(), group.getGroupId());

		LinkedHashMap<String, Object> params = new LinkedHashMap<>();

		params.put("inherit", true);
		params.put("usersGroups", _userGroupUser.getUserId());

		List<Group> groups = GroupFinderUtil.findByC_C_PG_N_D(
			_organization.getCompanyId(), null,
			GroupConstants.DEFAULT_PARENT_GROUP_ID, null, null, params, true,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		Assert.assertTrue(groups.contains(group));
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
	public void testFindByCompanyIdByUserGroupGroup() throws Exception {
		_userGroup = UserGroupTestUtil.addUserGroup();
		_userGroupGroup = GroupTestUtil.addGroup();
		_userGroupUser = UserTestUtil.addUser();

		GroupLocalServiceUtil.addUserGroupGroup(
			_userGroup.getUserGroupId(), _userGroupGroup.getGroupId());

		UserGroupLocalServiceUtil.addUserUserGroup(
			_userGroupUser.getUserId(), _userGroup);

		LinkedHashMap<String, Object> groupParams = new LinkedHashMap<>();

		groupParams.put("inherit", Boolean.TRUE);
		groupParams.put("site", Boolean.TRUE);
		groupParams.put("usersGroups", _userGroupUser.getUserId());

		List<Group> groups = GroupFinderUtil.findByCompanyId(
			TestPropsValues.getCompanyId(), groupParams, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, new GroupNameComparator(true));

		boolean exists = false;

		for (Group group : groups) {
			if (group.getGroupId() == _userGroupGroup.getGroupId()) {
				exists = true;

				break;
			}
		}

		Assert.assertTrue(
			"The method findByCompanyId should have returned the group " +
				_userGroupGroup.getGroupId(),
			exists);
	}

	@Test
	public void testFindByLayouts() throws Exception {
		List<Group> groups = findByLayouts(
			GroupConstants.DEFAULT_PARENT_GROUP_ID);

		int initialGroupCount = groups.size();

		GroupTestUtil.addGroup();

		Group parentGroup = GroupTestUtil.addGroup();

		LayoutTestUtil.addLayout(parentGroup, false);

		Group childGroup1 = GroupTestUtil.addGroup(parentGroup.getGroupId());

		LayoutTestUtil.addLayout(childGroup1, false);

		Group childGroup2 = GroupTestUtil.addGroup(parentGroup.getGroupId());

		LayoutTestUtil.addLayout(childGroup2, true);

		groups = findByLayouts(GroupConstants.DEFAULT_PARENT_GROUP_ID);

		Assert.assertEquals(
			groups.toString(), initialGroupCount + 1, groups.size());

		groups = findByLayouts(parentGroup.getGroupId());

		Assert.assertEquals(groups.toString(), 2, groups.size());

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
		LayoutTestUtil.addLayout(groupId, false);

		LayoutTestUtil.addLayout(groupId, true);
	}

	protected List<Group> findByC_C_N_D(
			String actionId, String name, long roleId)
		throws Exception {

		LinkedHashMap<String, Object> groupParams = new LinkedHashMap<>();

		RolePermissions rolePermissions = new RolePermissions(
			name, ResourceConstants.SCOPE_GROUP, actionId, roleId);

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
	private static Organization _organization;
	private static ResourcePermission _resourcePermission;
	private static ResourceTypePermission _resourceTypePermission;
	private static UserGroup _userGroup;
	private static Group _userGroupGroup;
	private static User _userGroupUser;

}