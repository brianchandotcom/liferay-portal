/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.data.cleanup.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.UserGroupRoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.upgrade.data.cleanup.RoleDataCleanupPreupgradeProcess;

import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Luis Ortiz
 */
@DataGuard(autoDelete = false, scope = DataGuard.Scope.METHOD)
@RunWith(Arquillian.class)
public class RoleDataCleanupPreupgradeProcessTest
	extends RoleDataCleanupPreupgradeProcess {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testUpgrade() throws Exception {
		long groupId = TestPropsValues.getGroupId();

		long roleId = RoleTestUtil.addOrganizationRole(groupId);

		long userId = TestPropsValues.getUserId();

		_userGroupRoleLocalService.addUserGroupRoles(
			new long[] {userId}, groupId, roleId);

		_userLocalService.addRoleUser(roleId, userId);

		runSQL("delete from Role_ where roleId = " + roleId);

		upgrade();

		CacheRegistryUtil.clear();

		Assert.assertNull(
			_userGroupRoleLocalService.fetchUserGroupRole(
				userId, groupId, roleId));

		List<Group> groups = _groupLocalService.getRoleGroups(roleId);

		Assert.assertTrue(groups.toString(), groups.isEmpty());

		List<User> users = _userLocalService.getRoleUsers(roleId);

		Assert.assertTrue(users.toString(), users.isEmpty());
	}

	@Inject
	private GroupLocalService _groupLocalService;

	@Inject
	private UserGroupRoleLocalService _userGroupRoleLocalService;

	@Inject
	private UserLocalService _userLocalService;

}