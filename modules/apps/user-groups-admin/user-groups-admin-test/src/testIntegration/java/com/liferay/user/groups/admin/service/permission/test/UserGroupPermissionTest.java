/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.user.groups.admin.service.permission.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.service.UserGroupLocalService;
import com.liferay.portal.kernel.service.permission.UserGroupPermissionUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Pei-Jung Lan
 */
@RunWith(Arquillian.class)
public class UserGroupPermissionTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testOwnerActionIds() throws Exception {
		User user = UserTestUtil.addUser();

		UserGroup userGroup = _userGroupLocalService.addUserGroup(
			user.getUserId(), user.getCompanyId(),
			RandomTestUtil.randomString(), null,
			ServiceContextTestUtil.getServiceContext());

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(user);

		Assert.assertTrue(
			UserGroupPermissionUtil.contains(
				permissionChecker, userGroup.getUserGroupId(),
				ActionKeys.ASSIGN_MEMBERS));
		Assert.assertTrue(
			UserGroupPermissionUtil.contains(
				permissionChecker, userGroup.getUserGroupId(),
				ActionKeys.DELETE));
		Assert.assertTrue(
			UserGroupPermissionUtil.contains(
				permissionChecker, userGroup.getUserGroupId(),
				ActionKeys.PERMISSIONS));
		Assert.assertTrue(
			UserGroupPermissionUtil.contains(
				permissionChecker, userGroup.getUserGroupId(),
				ActionKeys.UPDATE));
		Assert.assertTrue(
			UserGroupPermissionUtil.contains(
				permissionChecker, userGroup.getUserGroupId(),
				ActionKeys.VIEW));
	}

	@Inject
	private UserGroupLocalService _userGroupLocalService;

}