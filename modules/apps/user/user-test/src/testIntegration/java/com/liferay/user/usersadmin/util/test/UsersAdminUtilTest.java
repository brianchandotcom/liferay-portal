/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.user.usersadmin.util.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.context.ContextUserReplace;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.OrganizationTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.PropsValues;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portlet.usersadmin.util.UsersAdminUtil;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Lianne Louie
 */
@RunWith(Arquillian.class)
public class UsersAdminUtilTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Test
	public void testUpdateFieldPermissionWithInheritedRole() throws Exception {
		String[] fieldEditableUserTypes = PropsValues.FIELD_EDITABLE_USER_TYPES;

		try {
			PropsUtil.set(
				PropsKeys.FIELD_EDITABLE_USER_TYPES, StringPool.BLANK);

			Organization organization = OrganizationTestUtil.addOrganization();
			User user1 = UserTestUtil.addUser();

			_userLocalService.addOrganizationUser(
				organization.getOrganizationId(), user1);

			_roleLocalService.addGroupRole(
				organization.getGroupId(),
				_roleLocalService.getRole(
					TestPropsValues.getCompanyId(), "administrator"));

			PermissionChecker userPermissionChecker =
				PermissionCheckerFactoryUtil.create(user1);

			try (ContextUserReplace contextUserReplace = new ContextUserReplace(
					user1, userPermissionChecker)) {

				Assert.assertTrue(
					UsersAdminUtil.hasUpdateFieldPermission(
						userPermissionChecker, user1, user1, "suffix"));
			}

			User user2 = UserTestUtil.addUser();

			PermissionChecker user2PermissionChecker =
				PermissionCheckerFactoryUtil.create(user2);

			try (ContextUserReplace contextUserReplace = new ContextUserReplace(
					user2, user2PermissionChecker)) {

				Assert.assertFalse(
					UsersAdminUtil.hasUpdateFieldPermission(
						user2PermissionChecker, user2, user2, "suffix"));
			}
		}
		finally {
			PropsUtil.set(
				PropsKeys.FIELD_EDITABLE_USER_TYPES,
				StringUtil.merge(fieldEditableUserTypes, StringPool.COMMA));
		}
	}

	@Inject
	private RoleLocalService _roleLocalService;

	@Inject
	private UserLocalService _userLocalService;

}