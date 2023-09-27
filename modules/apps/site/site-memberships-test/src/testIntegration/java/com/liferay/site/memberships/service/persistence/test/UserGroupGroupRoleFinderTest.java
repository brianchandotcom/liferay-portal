/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.memberships.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.model.UserGroupGroupRole;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.service.UserGroupGroupRoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.persistence.UserGroupGroupRoleFinder;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.kernel.test.util.UserGroupTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.TransactionalTestRule;

import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Preston Crary
 */
@RunWith(Arquillian.class)
public class UserGroupGroupRoleFinderTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), TransactionalTestRule.INSTANCE);

	@Test
	public void testFindByUserGroupsUsers() throws Exception {
		_group = GroupTestUtil.addGroup();
		_role = RoleTestUtil.addRole(RoleConstants.TYPE_SITE);

		_user = UserTestUtil.addUser();
		_userGroup = UserGroupTestUtil.addUserGroup();

		_userLocalService.addUserGroupUser(
			_userGroup.getUserGroupId(), _user.getUserId());

		_userGroupGroupRoleLocalService.addUserGroupGroupRoles(
			_userGroup.getUserGroupId(), _group.getGroupId(),
			new long[] {_role.getRoleId()});

		List<UserGroupGroupRole> userGroupGroupRoles =
			_userGroupGroupRoleFinder.findByUserGroupsUsers(
				_user.getUserId(), _group.getGroupId());

		Assert.assertEquals(
			userGroupGroupRoles.toString(), 1, userGroupGroupRoles.size());

		UserGroupGroupRole userGroupGroupRole = userGroupGroupRoles.get(0);

		Assert.assertEquals(
			_userGroup.getUserGroupId(), userGroupGroupRole.getUserGroupId());
		Assert.assertEquals(
			_group.getGroupId(), userGroupGroupRole.getGroupId());
		Assert.assertEquals(_role.getRoleId(), userGroupGroupRole.getRoleId());
	}

	@DeleteAfterTestRun
	private Group _group;

	@DeleteAfterTestRun
	private Role _role;

	@DeleteAfterTestRun
	private User _user;

	@DeleteAfterTestRun
	private UserGroup _userGroup;

	@Inject
	private UserGroupGroupRoleFinder _userGroupGroupRoleFinder;

	@Inject
	private UserGroupGroupRoleLocalService _userGroupGroupRoleLocalService;

	@Inject
	private UserLocalService _userLocalService;

}