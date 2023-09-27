/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.depot.web.internal.application.list.test;

import com.liferay.application.list.PanelApp;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alejandro Tardín
 */
@RunWith(Arquillian.class)
public class DepotAdminPanelAppTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Test
	public void testIsShow() throws Exception {
		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(TestPropsValues.getUser());

		Assert.assertTrue(
			_depotAdminPanelApp.isShow(
				permissionChecker,
				_groupLocalService.getGroup(TestPropsValues.getGroupId())));
	}

	@Test
	public void testIsShowWithNoAdminUser() throws Exception {
		User user = UserTestUtil.addUser();

		try {
			PermissionChecker permissionChecker =
				PermissionCheckerFactoryUtil.create(user);

			Assert.assertFalse(
				_depotAdminPanelApp.isShow(
					permissionChecker,
					_groupLocalService.getGroup(TestPropsValues.getGroupId())));
		}
		finally {
			_userLocalService.deleteUser(user);
		}
	}

	@Test
	public void testIsShowWithUserWithViewControlPanel() throws Exception {
		User user = UserTestUtil.addUser();

		long roleId = RoleTestUtil.addRegularRole(TestPropsValues.getGroupId());

		try {
			_resourcePermissionLocalService.addResourcePermission(
				TestPropsValues.getCompanyId(),
				_depotAdminPanelApp.getPortletId(),
				ResourceConstants.SCOPE_COMPANY,
				String.valueOf(TestPropsValues.getCompanyId()), roleId,
				ActionKeys.ACCESS_IN_CONTROL_PANEL);

			_userLocalService.setRoleUsers(
				roleId, new long[] {user.getUserId()});

			PermissionChecker permissionChecker =
				PermissionCheckerFactoryUtil.create(user);

			Assert.assertTrue(
				_depotAdminPanelApp.isShow(
					permissionChecker,
					_groupLocalService.getGroup(TestPropsValues.getGroupId())));
		}
		finally {
			_userLocalService.deleteUser(user);

			_roleLocalService.deleteRole(roleId);
		}
	}

	@Inject(
		filter = "component.name=com.liferay.depot.web.internal.application.list.DepotAdminPanelApp"
	)
	private PanelApp _depotAdminPanelApp;

	@Inject
	private GroupLocalService _groupLocalService;

	@Inject
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	@Inject
	private RoleLocalService _roleLocalService;

	@Inject
	private UserLocalService _userLocalService;

}