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

package com.liferay.sharing.test.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.ClassedModel;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.test.context.ContextUserReplace;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.sharing.security.permission.SharingEntryAction;
import com.liferay.sharing.security.permission.SharingPermissionChecker;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Sergio González
 */
public abstract class BaseSharingPermissionCheckerTestCase
	<T extends ClassedModel> {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_company = CompanyTestUtil.addCompany();

		_user = UserTestUtil.addCompanyAdminUser(_company);

		_group = GroupTestUtil.addGroup(
			_company.getCompanyId(), _user.getUserId(),
			GroupConstants.DEFAULT_PARENT_GROUP_ID);

		_groupUser = UserTestUtil.addGroupUser(
			_group, RoleConstants.POWER_USER);

		_powerUserRole = _roleLocalService.getRole(
			_company.getCompanyId(), RoleConstants.POWER_USER);

		_sharingPermissionChecker = getSharingPermissionChecker();
	}

	@Test
	public void testAdminCanShareWithAddDiscussion() throws Exception {
		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(_user);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, permissionChecker)) {

			T model = getModel(_user, _group);

			Assert.assertTrue(
				_sharingPermissionChecker.hasPermission(
					permissionChecker, (Long)model.getPrimaryKeyObj(),
					_group.getGroupId(),
					Arrays.asList(SharingEntryAction.ADD_DISCUSSION)));
		}
	}

	@Test
	public void testAdminCanShareWithUpdate() throws Exception {
		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(_user);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, permissionChecker)) {

			T model = getModel(_user, _group);

			Assert.assertTrue(
				_sharingPermissionChecker.hasPermission(
					permissionChecker, (Long)model.getPrimaryKeyObj(),
					_group.getGroupId(),
					Arrays.asList(SharingEntryAction.UPDATE)));
		}
	}

	@Test
	public void testAdminCanShareWithView() throws Exception {
		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(_user);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, permissionChecker)) {

			T model = getModel(_user, _group);

			Assert.assertTrue(
				_sharingPermissionChecker.hasPermission(
					permissionChecker, (Long)model.getPrimaryKeyObj(),
					_group.getGroupId(),
					Arrays.asList(SharingEntryAction.VIEW)));
		}
	}

	@Test
	public void testUserWithAddDiscussionPermissionCannotShareWithUpdate()
		throws Exception {

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(_groupUser);

		try (AddModelResourcePermission addModelResourcePermission =
				new AddModelResourcePermission(
					_powerUserRole, ActionKeys.ADD_DISCUSSION);
			ContextUserReplace contextUserReplace = new ContextUserReplace(
				_groupUser, permissionChecker)) {

			T model = getModel(_user, _group);

			Assert.assertFalse(
				_sharingPermissionChecker.hasPermission(
					permissionChecker, (Long)model.getPrimaryKeyObj(),
					_group.getGroupId(),
					Arrays.asList(SharingEntryAction.UPDATE)));
		}
	}

	@Test
	public void testUserWithAddDiscussionPermissionCannotShareWithView()
		throws Exception {

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(_groupUser);

		try (AddModelResourcePermission addModelResourcePermission =
				new AddModelResourcePermission(
					_powerUserRole, ActionKeys.ADD_DISCUSSION);
			ContextUserReplace contextUserReplace = new ContextUserReplace(
				_groupUser, permissionChecker)) {

			T model = getModel(_user, _group);

			Assert.assertFalse(
				_sharingPermissionChecker.hasPermission(
					permissionChecker, (Long)model.getPrimaryKeyObj(),
					_group.getGroupId(),
					Arrays.asList(SharingEntryAction.VIEW)));
		}
	}

	@Test
	public void testUserWithAddDiscussionPermissionCanShareWithAddDiscussion()
		throws Exception {

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(_groupUser);

		try (AddModelResourcePermission addModelResourcePermission =
				new AddModelResourcePermission(
					_powerUserRole, ActionKeys.ADD_DISCUSSION);
			ContextUserReplace contextUserReplace = new ContextUserReplace(
				_groupUser, permissionChecker)) {

			T model = getModel(_user, _group);

			Assert.assertTrue(
				_sharingPermissionChecker.hasPermission(
					permissionChecker, (Long)model.getPrimaryKeyObj(),
					_group.getGroupId(),
					Arrays.asList(SharingEntryAction.ADD_DISCUSSION)));
		}
	}

	@Test
	public void testUserWithoutAddDiscussionPermissionCannotShareWithAddDiscussion()
		throws Exception {

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(_groupUser);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_groupUser, permissionChecker)) {

			T model = getModel(_user, _group);

			Assert.assertFalse(
				_sharingPermissionChecker.hasPermission(
					permissionChecker, (Long)model.getPrimaryKeyObj(),
					_group.getGroupId(),
					Arrays.asList(SharingEntryAction.ADD_DISCUSSION)));
		}
	}

	@Test
	public void testUserWithoutUpdatePermissionCannotShareWithUpdate()
		throws Exception {

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(_groupUser);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_groupUser, permissionChecker)) {

			T model = getModel(_user, _group);

			Assert.assertFalse(
				_sharingPermissionChecker.hasPermission(
					permissionChecker, (Long)model.getPrimaryKeyObj(),
					_group.getGroupId(),
					Arrays.asList(SharingEntryAction.UPDATE)));
		}
	}

	@Test
	public void testUserWithoutViewPermissionCannotShareWithView()
		throws Exception {

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(_groupUser);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_groupUser, permissionChecker)) {

			T model = getModel(_user, _group);

			Assert.assertFalse(
				_sharingPermissionChecker.hasPermission(
					permissionChecker, (Long)model.getPrimaryKeyObj(),
					_group.getGroupId(),
					Arrays.asList(SharingEntryAction.VIEW)));
		}
	}

	@Test
	public void testUserWithUpdatePermissionCannotShareWithAddDiscussion()
		throws Exception {

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(_groupUser);

		try (AddModelResourcePermission addModelResourcePermission =
				new AddModelResourcePermission(
					_powerUserRole, ActionKeys.UPDATE);
			ContextUserReplace contextUserReplace = new ContextUserReplace(
				_groupUser, permissionChecker)) {

			T model = getModel(_user, _group);

			Assert.assertFalse(
				_sharingPermissionChecker.hasPermission(
					permissionChecker, (Long)model.getPrimaryKeyObj(),
					_group.getGroupId(),
					Arrays.asList(SharingEntryAction.ADD_DISCUSSION)));
		}
	}

	@Test
	public void testUserWithUpdatePermissionCannotShareWithView()
		throws Exception {

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(_groupUser);

		try (AddModelResourcePermission addModelResourcePermission =
				new AddModelResourcePermission(
					_powerUserRole, ActionKeys.UPDATE);
			ContextUserReplace contextUserReplace = new ContextUserReplace(
				_groupUser, permissionChecker)) {

			T model = getModel(_user, _group);

			Assert.assertFalse(
				_sharingPermissionChecker.hasPermission(
					permissionChecker, (Long)model.getPrimaryKeyObj(),
					_group.getGroupId(),
					Arrays.asList(SharingEntryAction.VIEW)));
		}
	}

	@Test
	public void testUserWithUpdatePermissionCanShareWithUpdate()
		throws Exception {

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(_groupUser);

		try (AddModelResourcePermission addModelResourcePermission =
				new AddModelResourcePermission(
					_powerUserRole, ActionKeys.UPDATE);
			ContextUserReplace contextUserReplace = new ContextUserReplace(
				_groupUser, permissionChecker)) {

			T model = getModel(_user, _group);

			Assert.assertTrue(
				_sharingPermissionChecker.hasPermission(
					permissionChecker, (Long)model.getPrimaryKeyObj(),
					_group.getGroupId(),
					Arrays.asList(SharingEntryAction.UPDATE)));
		}
	}

	@Test
	public void testUserWithViewPermissionCannotShareWithAddDiscussion()
		throws Exception {

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(_groupUser);

		try (AddModelResourcePermission addModelResourcePermission =
				new AddModelResourcePermission(_powerUserRole, ActionKeys.VIEW);
			ContextUserReplace contextUserReplace = new ContextUserReplace(
				_groupUser, permissionChecker)) {

			T model = getModel(_user, _group);

			Assert.assertFalse(
				_sharingPermissionChecker.hasPermission(
					permissionChecker, (Long)model.getPrimaryKeyObj(),
					_group.getGroupId(),
					Arrays.asList(SharingEntryAction.ADD_DISCUSSION)));
		}
	}

	@Test
	public void testUserWithViewPermissionCannotShareWithUpdate()
		throws Exception {

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(_groupUser);

		try (AddModelResourcePermission addModelResourcePermission =
				new AddModelResourcePermission(_powerUserRole, ActionKeys.VIEW);
			ContextUserReplace contextUserReplace = new ContextUserReplace(
				_groupUser, permissionChecker)) {

			T model = getModel(_user, _group);

			Assert.assertFalse(
				_sharingPermissionChecker.hasPermission(
					permissionChecker, (Long)model.getPrimaryKeyObj(),
					_group.getGroupId(),
					Arrays.asList(SharingEntryAction.UPDATE)));
		}
	}

	@Test
	public void testUserWithViewPermissionCanShareWithView() throws Exception {
		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(_groupUser);

		try (AddModelResourcePermission addModelResourcePermission =
				new AddModelResourcePermission(_powerUserRole, ActionKeys.VIEW);
			ContextUserReplace contextUserReplace = new ContextUserReplace(
				_groupUser, permissionChecker)) {

			T model = getModel(_user, _group);

			Assert.assertTrue(
				_sharingPermissionChecker.hasPermission(
					permissionChecker, (Long)model.getPrimaryKeyObj(),
					_group.getGroupId(),
					Arrays.asList(SharingEntryAction.VIEW)));
		}
	}

	protected abstract String getClassName();

	protected abstract T getModel(User user, Group group)
		throws PortalException;

	protected abstract SharingPermissionChecker getSharingPermissionChecker();

	private static final Log _log = LogFactoryUtil.getLog(
		BaseSharingPermissionCheckerTestCase.class);

	@Inject
	private ClassNameLocalService _classNameLocalService;

	@DeleteAfterTestRun
	private Company _company;

	private Group _group;
	private User _groupUser;
	private Role _powerUserRole;

	@Inject
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	@Inject
	private RoleLocalService _roleLocalService;

	private SharingPermissionChecker _sharingPermissionChecker;
	private User _user;

	private class AddModelResourcePermission implements AutoCloseable {

		public AddModelResourcePermission(Role role, String... actionKeys) {
			_role = role;
			_actionKeys = actionKeys;

			for (String actionKey : actionKeys) {
				try {
					_resourcePermissionLocalService.addResourcePermission(
						role.getCompanyId(), getClassName(),
						ResourceConstants.SCOPE_GROUP_TEMPLATE,
						String.valueOf(GroupConstants.DEFAULT_PARENT_GROUP_ID),
						role.getRoleId(), actionKey);
				}
				catch (PortalException pe) {
					_log.error(pe, pe);
				}
			}
		}

		@Override
		public void close() throws Exception {
			for (String actionKey : _actionKeys) {
				_resourcePermissionLocalService.removeResourcePermission(
					_role.getCompanyId(), getClassName(),
					ResourceConstants.SCOPE_GROUP_TEMPLATE,
					String.valueOf(GroupConstants.DEFAULT_PARENT_GROUP_ID),
					_role.getRoleId(), actionKey);
			}
		}

		private final String[] _actionKeys;
		private final Role _role;

	}

}