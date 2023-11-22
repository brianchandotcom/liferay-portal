/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.permission.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.document.library.kernel.service.DLFileEntryTypeLocalService;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactory;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserGroupRoleLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.security.permission.test.util.BasePermissionTestCase;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portlet.documentlibrary.constants.DLConstants;

import org.junit.After;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alicia García
 */
@RunWith(Arquillian.class)
public class DLFileEntryTypePermissionCheckerTest
	extends BasePermissionTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@After
	@Override
	public void tearDown() throws Exception {
		super.tearDown();

		PermissionThreadLocal.setPermissionChecker(permissionChecker);
	}

	@Test
	public void testContains() throws Exception {
		Company company = _companyLocalService.getCompany(group.getCompanyId());

		DLFileEntryType dlVideoExternalShortcutDLFileEntryType =
			_dlFileEntryTypeLocalService.getFileEntryType(
				company.getGroupId(), "DL_VIDEO_EXTERNAL_SHORTCUT");

		Assert.assertTrue(
			_dlFileEntryTypeModelResourcePermission.contains(
				permissionChecker, dlVideoExternalShortcutDLFileEntryType,
				ActionKeys.VIEW));

		DLFileEntryType googleDocsDLFileEntryType =
			_dlFileEntryTypeLocalService.getFileEntryType(
				company.getGroupId(), "GOOGLE_DOCS");

		Assert.assertTrue(
			_dlFileEntryTypeModelResourcePermission.contains(
				permissionChecker, googleDocsDLFileEntryType, ActionKeys.VIEW));
	}

	@Override
	protected void doSetUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_user = UserTestUtil.addGroupAdminUser(_group);

		Role role = _roleLocalService.getRole(
			_group.getCompanyId(), RoleConstants.SITE_ADMINISTRATOR);

		_userGroupRoleLocalService.addUserGroupRole(
			_user.getUserId(), _group.getGroupId(), role.getRoleId());

		PermissionThreadLocal.setPermissionChecker(
			_permissionCheckerFactory.create(_user));

		PrincipalThreadLocal.setName(_user.getUserId());
	}

	@Override
	protected String getResourceName() {
		return DLConstants.RESOURCE_NAME;
	}

	@Inject(
		filter = "model.class.name=com.liferay.document.library.kernel.model.DLFileEntryType"
	)
	private static ModelResourcePermission<DLFileEntryType>
		_dlFileEntryTypeModelResourcePermission;

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject
	private DLFileEntryTypeLocalService _dlFileEntryTypeLocalService;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private PermissionCheckerFactory _permissionCheckerFactory;

	@Inject
	private RoleLocalService _roleLocalService;

	@DeleteAfterTestRun
	private User _user;

	@Inject
	private UserGroupRoleLocalService _userGroupRoleLocalService;

}