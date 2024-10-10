/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.dynamic.data.mapping.constants.DDMActionKeys;
import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceLocalService;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceService;
import com.liferay.dynamic.data.mapping.test.util.DDMFormInstanceTestUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.AssertUtils;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Carolina Barbosa
 */
@RunWith(Arquillian.class)
public class DDMFormInstanceServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_ddmFormInstance = DDMFormInstanceTestUtil.addDDMFormInstance(
			_groupLocalService.getGroup(TestPropsValues.getGroupId()),
			TestPropsValues.getUserId());
	}

	@Test
	public void testGetFormInstance() throws Exception {
		PermissionChecker originalPermissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		try {
			User user = UserTestUtil.addUser();

			PermissionThreadLocal.setPermissionChecker(
				PermissionCheckerFactoryUtil.create(user));

			Role role = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);

			_resourcePermissionLocalService.setResourcePermissions(
				TestPropsValues.getCompanyId(), DDMFormInstance.class.getName(),
				ResourceConstants.SCOPE_INDIVIDUAL,
				String.valueOf(_ddmFormInstance.getFormInstanceId()),
				role.getRoleId(),
				new String[] {DDMActionKeys.ADD_FORM_INSTANCE_RECORD});

			_userLocalService.addRoleUser(role.getRoleId(), user);

			AssertUtils.assertFailure(
				PrincipalException.MustHavePermission.class,
				StringBundler.concat(
					"User ", user.getUserId(),
					" must have VIEW permission for ",
					"com.liferay.dynamic.data.mapping.model.DDMFormInstance ",
					_ddmFormInstance.getFormInstanceId()),
				() -> _ddmFormInstanceService.getFormInstance(
					_ddmFormInstance.getFormInstanceId()));

			_resourcePermissionLocalService.setResourcePermissions(
				TestPropsValues.getCompanyId(), DDMFormInstance.class.getName(),
				ResourceConstants.SCOPE_INDIVIDUAL,
				String.valueOf(_ddmFormInstance.getFormInstanceId()),
				role.getRoleId(), new String[] {ActionKeys.VIEW});

			_ddmFormInstanceService.getFormInstance(
				_ddmFormInstance.getFormInstanceId());
		}
		finally {
			PermissionThreadLocal.setPermissionChecker(
				originalPermissionChecker);

			_ddmFormInstanceLocalService.deleteDDMFormInstance(
				_ddmFormInstance);
		}
	}

	@DeleteAfterTestRun
	private DDMFormInstance _ddmFormInstance;

	@Inject
	private DDMFormInstanceLocalService _ddmFormInstanceLocalService;

	@Inject
	private DDMFormInstanceService _ddmFormInstanceService;

	@Inject
	private GroupLocalService _groupLocalService;

	@Inject
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	@Inject
	private UserLocalService _userLocalService;

}