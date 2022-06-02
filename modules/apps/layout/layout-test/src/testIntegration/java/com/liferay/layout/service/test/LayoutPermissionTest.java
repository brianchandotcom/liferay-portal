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

package com.liferay.layout.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.permission.LayoutPermission;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.props.test.util.PropsTemporarySwapper;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Lourdes Fernández Besada
 */
@RunWith(Arquillian.class)
public class LayoutPermissionTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testContainsWithUpdateLayoutContentPermissions()
		throws Exception {

		PermissionChecker permissionChecker = _getPermissionChecker(
			ActionKeys.UPDATE_LAYOUT_CONTENT);

		Layout layout = LayoutTestUtil.addTypeContentLayout(_group);

		Assert.assertTrue(
			_layoutPermission.contains(
				permissionChecker, layout, ActionKeys.UPDATE_LAYOUT_CONTENT));

		try (PropsTemporarySwapper propsTemporarySwapper =
				new PropsTemporarySwapper(
					"feature.flag.LPS-132571", Boolean.TRUE.toString())) {

			Assert.assertTrue(
				_layoutPermission.containsLayoutUpdatePermission(
					permissionChecker, layout));
		}

		try (PropsTemporarySwapper propsTemporarySwapper =
				new PropsTemporarySwapper(
					"feature.flag.LPS-132571", Boolean.FALSE.toString())) {

			Assert.assertTrue(
				_layoutPermission.containsLayoutUpdatePermission(
					permissionChecker, layout));
		}
	}

	@Test
	public void testContainsWithUpdatePermissions() throws Exception {
		PermissionChecker permissionChecker = _getPermissionChecker(
			ActionKeys.UPDATE);

		Layout layout = LayoutTestUtil.addTypeContentLayout(_group);

		Assert.assertTrue(
			_layoutPermission.contains(
				permissionChecker, layout, ActionKeys.UPDATE));

		try (PropsTemporarySwapper propsTemporarySwapper =
				new PropsTemporarySwapper(
					"feature.flag.LPS-132571", Boolean.TRUE.toString())) {

			Assert.assertTrue(
				_layoutPermission.containsLayoutUpdatePermission(
					permissionChecker, layout));
		}

		try (PropsTemporarySwapper propsTemporarySwapper =
				new PropsTemporarySwapper(
					"feature.flag.LPS-132571", Boolean.FALSE.toString())) {

			Assert.assertTrue(
				_layoutPermission.containsLayoutUpdatePermission(
					permissionChecker, layout));
		}
	}

	private PermissionChecker _getPermissionChecker(String actionId)
		throws Exception {

		Role role = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);

		RoleTestUtil.addResourcePermission(
			role, Layout.class.getName(), ResourceConstants.SCOPE_COMPANY,
			String.valueOf(_group.getCompanyId()), actionId);

		User user = UserTestUtil.addUser();

		_roleLocalService.clearUserRoles(user.getUserId());

		_roleLocalService.addUserRole(user.getUserId(), role);

		return PermissionCheckerFactoryUtil.create(user);
	}

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private LayoutPermission _layoutPermission;

	@Inject
	private RoleLocalService _roleLocalService;

}