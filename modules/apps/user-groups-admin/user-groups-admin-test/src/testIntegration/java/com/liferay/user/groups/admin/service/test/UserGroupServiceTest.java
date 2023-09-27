/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.user.groups.admin.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.UserGroupLocalService;
import com.liferay.portal.kernel.service.UserGroupService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserGroupTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.LinkedHashMapBuilder;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.service.persistence.constants.UserGroupFinderConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.users.admin.kernel.util.UsersAdminUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Drew Brokke
 */
@RunWith(Arquillian.class)
public class UserGroupServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_role = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);
		_user = UserTestUtil.addUser();

		_userLocalService.addRoleUser(_role.getRoleId(), _user.getUserId());

		UserTestUtil.setUser(_user);
	}

	@Test
	public void testAddOrUpdateUserGroupAddUserGroup() throws Exception {
		_addResourcePermission(PortletKeys.PORTAL, ActionKeys.ADD_USER_GROUP);

		String externalReferenceCode = RandomTestUtil.randomString();

		_userGroupService.addOrUpdateUserGroup(
			externalReferenceCode, RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), null);
	}

	@Test(expected = PrincipalException.MustHavePermission.class)
	public void testAddOrUpdateUserGroupAddUserGroupWithoutPermission()
		throws Exception {

		String externalReferenceCode = RandomTestUtil.randomString();

		_userGroupService.addOrUpdateUserGroup(
			externalReferenceCode, RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), null);
	}

	@Test
	public void testAddOrUpdateUserGroupUpdateUserGroup() throws Exception {
		String externalReferenceCode = RandomTestUtil.randomString();

		_userGroupLocalService.updateExternalReferenceCode(
			UserGroupTestUtil.addUserGroup(), externalReferenceCode);

		_addResourcePermission(UserGroup.class.getName(), ActionKeys.UPDATE);

		_userGroupService.addOrUpdateUserGroup(
			externalReferenceCode, RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), null);
	}

	@Test(expected = PrincipalException.MustHavePermission.class)
	public void testAddOrUpdateUserGroupUpdateUserGroupWithoutPermission()
		throws Exception {

		String externalReferenceCode = RandomTestUtil.randomString();

		_userGroupService.updateExternalReferenceCode(
			UserGroupTestUtil.addUserGroup(), externalReferenceCode);

		_userGroupService.addOrUpdateUserGroup(
			externalReferenceCode, RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), null);
	}

	@Test
	public void testDatabaseSearchPermissionCheck() throws Exception {
		_userGroupLocalService.addUserUserGroup(
			_user.getUserId(), UserGroupTestUtil.addUserGroup());

		_assertSearch(0);

		_addResourcePermission(UserGroup.class.getName(), ActionKeys.VIEW);

		_assertSearch(1);
	}

	@Test
	public void testGetGtUserGroups() throws Exception {
		_addResourcePermission(UserGroup.class.getName(), ActionKeys.VIEW);

		for (int i = 0; i < 10; i++) {
			UserGroupTestUtil.addUserGroup();
		}

		long parentUserGroupId = 0;
		int size = 5;

		List<UserGroup> userGroups1 = _userGroupService.getGtUserGroups(
			0, TestPropsValues.getCompanyId(), parentUserGroupId, size);

		Assert.assertEquals(userGroups1.toString(), size, userGroups1.size());

		UserGroup lastUserGroup = userGroups1.get(userGroups1.size() - 1);

		List<UserGroup> userGroups2 = _userGroupService.getGtUserGroups(
			lastUserGroup.getUserGroupId(), TestPropsValues.getCompanyId(),
			parentUserGroupId, size);

		Assert.assertEquals(userGroups2.toString(), size, userGroups2.size());

		long previousUserGroupId = 0;

		for (UserGroup userGroup : userGroups2) {
			long userGroupId = userGroup.getUserGroupId();

			Assert.assertTrue(userGroupId > lastUserGroup.getUserGroupId());
			Assert.assertTrue(userGroupId > previousUserGroupId);

			previousUserGroupId = userGroupId;
		}
	}

	@Test
	public void testGetUserGroupsLikeName() throws Exception {
		_addResourcePermission(UserGroup.class.getName(), ActionKeys.VIEW);

		List<UserGroup> allUserGroups = new ArrayList<>(
			_userGroupLocalService.getUserGroups(
				TestPropsValues.getCompanyId()));

		allUserGroups.add(UserGroupTestUtil.addUserGroup());

		UserGroup likeNameUserGroup = UserGroupTestUtil.addUserGroup();

		String name = RandomTestUtil.randomString();

		likeNameUserGroup.setName(name + RandomTestUtil.randomString());

		likeNameUserGroup = _userGroupLocalService.updateUserGroup(
			likeNameUserGroup);

		allUserGroups.add(likeNameUserGroup);

		_assertExpectedUserGroups(
			Collections.singletonList(likeNameUserGroup), name + "%");
		_assertExpectedUserGroups(
			Collections.singletonList(likeNameUserGroup),
			StringUtil.toLowerCase(name) + "%");
		_assertExpectedUserGroups(
			Collections.singletonList(likeNameUserGroup),
			StringUtil.toUpperCase(name) + "%");

		_assertExpectedUserGroups(allUserGroups, null);
		_assertExpectedUserGroups(allUserGroups, "");
	}

	private void _addResourcePermission(String resourceName, String actionKey)
		throws Exception {

		RoleTestUtil.addResourcePermission(
			_role, resourceName, ResourceConstants.SCOPE_COMPANY,
			String.valueOf(TestPropsValues.getCompanyId()), actionKey);
	}

	private void _assertExpectedUserGroups(
			List<UserGroup> expectedUserGroups, String nameSearch)
		throws Exception {

		List<UserGroup> actualUserGroups = _userGroupService.getUserGroups(
			TestPropsValues.getCompanyId(), nameSearch, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS);

		Assert.assertEquals(
			actualUserGroups.toString(), expectedUserGroups.size(),
			actualUserGroups.size());
		Assert.assertTrue(
			actualUserGroups.toString(),
			actualUserGroups.containsAll(expectedUserGroups));

		Assert.assertEquals(
			expectedUserGroups.size(),
			_userGroupService.getUserGroupsCount(
				TestPropsValues.getCompanyId(), nameSearch));
	}

	private void _assertSearch(int expected) {
		List<UserGroup> userGroups = _userGroupService.search(
			_user.getCompanyId(), null,
			LinkedHashMapBuilder.<String, Object>put(
				UserGroupFinderConstants.PARAM_KEY_USER_GROUPS_USERS,
				_user.getUserId()
			).build(),
			QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			UsersAdminUtil.getUserGroupOrderByComparator("name", "asc"));

		Assert.assertEquals(userGroups.toString(), expected, userGroups.size());
	}

	private Role _role;
	private User _user;

	@Inject
	private UserGroupLocalService _userGroupLocalService;

	@Inject
	private UserGroupService _userGroupService;

	@Inject
	private UserLocalService _userLocalService;

}