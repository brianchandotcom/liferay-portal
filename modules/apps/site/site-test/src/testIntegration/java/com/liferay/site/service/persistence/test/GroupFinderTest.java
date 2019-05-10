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

package com.liferay.site.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.ResourceAction;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.ResourcePermission;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.security.permission.RolePermissions;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.OrganizationLocalService;
import com.liferay.portal.kernel.service.ResourceActionLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.UserGroupLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.persistence.GroupFinder;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.OrganizationTestUtil;
import com.liferay.portal.kernel.test.util.ResourcePermissionTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserGroupTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.comparator.GroupNameComparator;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.TransactionalTestRule;
import com.liferay.portal.util.test.LayoutTestUtil;

import java.util.LinkedHashMap;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alberto Chaparro
 * @author László Csontos
 */
@RunWith(Arquillian.class)
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
			_resourceActionLocalService.getResourceActions(0, 1);

		_arbitraryResourceAction = resourceActions.get(0);

		_resourcePermission = ResourcePermissionTestUtil.addResourcePermission(
			_arbitraryResourceAction.getBitwiseValue(),
			_arbitraryResourceAction.getName(),
			String.valueOf(_group.getGroupId()), ResourceConstants.SCOPE_GROUP);
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		_groupLocalService.deleteGroup(_group);
		_groupLocalService.deleteGroup(_userGroupGroup);

		_organizationLocalService.deleteOrganization(_organization);

		_resourcePermissionLocalService.deleteResourcePermission(
			_resourcePermission);

		_userLocalService.deleteUser(_userGroupUser);

		_userGroupLocalService.deleteUserGroup(_userGroup);
	}

	@Test
	public void testFindByActiveGroupIds() throws Exception {
		List<Long> groups = _groupFinder.findByActiveGroupIds(
			TestPropsValues.getUserId());

		Assert.assertFalse(groups.toString(), groups.isEmpty());
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
	public void testFindByC_C_PG_N_D() throws Exception {
		_userGroup = UserGroupTestUtil.addUserGroup();
		_userGroupUser = UserTestUtil.addUser();

		_userGroupLocalService.addUserUserGroup(
			_userGroupUser.getUserId(), _userGroup);

		Group group = _organization.getGroup();

		_groupLocalService.addUserGroupGroup(
			_userGroup.getUserGroupId(), group.getGroupId());

		LinkedHashMap<String, Object> params = new LinkedHashMap<>();

		params.put("inherit", true);
		params.put("usersGroups", _userGroupUser.getUserId());

		List<Group> groups = _groupFinder.findByC_C_PG_N_D(
			_organization.getCompanyId(), null,
			GroupConstants.DEFAULT_PARENT_GROUP_ID, null, null, params, true,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		Assert.assertTrue(groups.toString(), groups.contains(group));
	}

	@Test
	public void testFindByCompanyId() throws Exception {
		LinkedHashMap<String, Object> groupParams = new LinkedHashMap<>();

		groupParams.put("inherit", Boolean.TRUE);
		groupParams.put("site", Boolean.TRUE);
		groupParams.put("usersGroups", TestPropsValues.getUserId());

		List<Group> groups = _groupFinder.findByCompanyId(
			TestPropsValues.getCompanyId(), groupParams, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, new GroupNameComparator(true));

		Assert.assertFalse(groups.toString(), groups.isEmpty());
	}

	@Test
	public void testFindByCompanyIdByUserGroupGroup() throws Exception {
		_userGroup = UserGroupTestUtil.addUserGroup();
		_userGroupGroup = GroupTestUtil.addGroup();
		_userGroupUser = UserTestUtil.addUser();

		_groupLocalService.addUserGroupGroup(
			_userGroup.getUserGroupId(), _userGroupGroup.getGroupId());

		_userGroupLocalService.addUserUserGroup(
			_userGroupUser.getUserId(), _userGroup);

		LinkedHashMap<String, Object> groupParams = new LinkedHashMap<>();

		groupParams.put("inherit", Boolean.TRUE);
		groupParams.put("site", Boolean.TRUE);
		groupParams.put("usersGroups", _userGroupUser.getUserId());

		List<Group> groups = _groupFinder.findByCompanyId(
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
	public void testFindByLayouts1() throws Exception {
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

		Assert.assertTrue(groups.toString(), groups.isEmpty());
	}

	@Test
	public void testFindByLayouts2() throws Exception {
		int initialGroupCount = _groupFinder.countByLayouts(
			TestPropsValues.getCompanyId(),
			GroupConstants.DEFAULT_PARENT_GROUP_ID, true, true);

		GroupTestUtil.addGroup();

		Group parentGroup = GroupTestUtil.addGroup();

		LayoutTestUtil.addLayout(parentGroup, false);

		Group childGroup1 = GroupTestUtil.addGroup(parentGroup.getGroupId());

		LayoutTestUtil.addLayout(childGroup1, false);

		Group childGroup2 = GroupTestUtil.addGroup(parentGroup.getGroupId());

		LayoutTestUtil.addLayout(childGroup2, true);

		_groupLocalService.updateGroup(
			parentGroup.getGroupId(), parentGroup.getParentGroupId(),
			parentGroup.getNameMap(), parentGroup.getDescriptionMap(),
			parentGroup.getType(), parentGroup.isManualMembership(),
			parentGroup.getMembershipRestriction(),
			parentGroup.getFriendlyURL(), parentGroup.isInheritContent(), false,
			ServiceContextTestUtil.getServiceContext());

		List<Group> groups = _groupFinder.findByLayouts(
			TestPropsValues.getCompanyId(),
			GroupConstants.DEFAULT_PARENT_GROUP_ID, true, true,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			new GroupNameComparator(true));

		Assert.assertEquals(
			groups.toString(), initialGroupCount, groups.size());

		groups = _groupFinder.findByLayouts(
			TestPropsValues.getCompanyId(), parentGroup.getGroupId(), true,
			true, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			new GroupNameComparator(true));

		Assert.assertEquals(groups.toString(), 2, groups.size());
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

		long[] classNameIds = {_portal.getClassNameId(Group.class)};

		return _groupFinder.findByC_C_PG_N_D(
			TestPropsValues.getCompanyId(), classNameIds,
			GroupConstants.ANY_PARENT_GROUP_ID, new String[] {null},
			new String[] {null}, groupParams, true, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	protected List<Group> findByLayouts(long parentGroupId) throws Exception {
		return _groupFinder.findByLayouts(
			TestPropsValues.getCompanyId(), parentGroupId, true,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			new GroupNameComparator(true));
	}

	private static ResourceAction _arbitraryResourceAction;
	private static Group _group;

	@Inject
	private static GroupLocalService _groupLocalService;

	private static Organization _organization;

	@Inject
	private static OrganizationLocalService _organizationLocalService;

	@Inject
	private static ResourceActionLocalService _resourceActionLocalService;

	private static ResourcePermission _resourcePermission;

	@Inject
	private static ResourcePermissionLocalService
		_resourcePermissionLocalService;

	private static UserGroup _userGroup;
	private static Group _userGroupGroup;

	@Inject
	private static UserGroupLocalService _userGroupLocalService;

	private static User _userGroupUser;

	@Inject
	private static UserLocalService _userLocalService;

	@Inject
	private GroupFinder _groupFinder;

	@Inject
	private Portal _portal;

}