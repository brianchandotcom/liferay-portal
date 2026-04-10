/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.depot.internal.instance.lifecycle.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.depot.constants.DepotRolesConstants;
import com.liferay.depot.model.DepotEntry;
import com.liferay.portal.kernel.exception.NoSuchResourcePermissionException;
import com.liferay.portal.kernel.exception.NoSuchRoleException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Cristina González
 */
@RunWith(Arquillian.class)
public class DepotRolesPortalInstanceLifecycleListenerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@FeatureFlag("LPD-17564")
	@Test
	public void testAddCompany() throws Exception {
		Company company = null;

		try {
			company = CompanyTestUtil.addCompany();

			_assertRole(
				company.getCompanyId(),
				"space-administrators-are-super-users-of-their-space-but-" +
					"cannot-make-other-users-into-space-administrators",
				DepotRolesConstants.ASSET_LIBRARY_ADMINISTRATOR,
				"space-administrator");
			_assertRole(
				company.getCompanyId(),
				"all-users-who-belong-to-a-space-have-this-role-within-that-" +
					"space",
				DepotRolesConstants.ASSET_LIBRARY_MEMBER, "space-member");
			_assertRole(
				company.getCompanyId(),
				"space-owners-are-super-users-of-their-space-and-can-assign-" +
					"space-roles-to-users",
				DepotRolesConstants.ASSET_LIBRARY_OWNER, "space-owner");

			_assertRoleResourcePermissions(
				company.getCompanyId(), DepotEntry.class.getName(),
				DepotRolesConstants.ASSET_LIBRARY_ADMINISTRATOR,
				ResourceActionsUtil.getResourceActions(
					DepotEntry.class.getName()));
			_assertRoleResourcePermissions(
				company.getCompanyId(), DepotEntry.class.getName(),
				DepotRolesConstants.ASSET_LIBRARY_MEMBER,
				List.of(ActionKeys.VIEW));

			for (String name : DepotRolesConstants.DEPOT_ROLE_NAMES) {
				_assertRoleResourcePermissions(
					company.getCompanyId(), Role.class.getName(), name,
					List.of(ActionKeys.VIEW));
			}
		}
		finally {
			if (company != null) {
				_companyLocalService.deleteCompany(company.getCompanyId());
			}
		}
	}

	private void _assertRole(
			long companyId, String descriptionKey, String name, String titleKey)
		throws PortalException {

		try {
			Role role = _roleLocalService.getRole(companyId, name);

			Assert.assertEquals(
				RoleConstants.toSystemRoleExternalReferenceCode(name),
				role.getExternalReferenceCode());
			Assert.assertEquals(
				2,
				_resourcePermissionLocalService.getResourcePermissionsCount(
					companyId, Role.class.getName(),
					ResourceConstants.SCOPE_INDIVIDUAL,
					String.valueOf(role.getRoleId())));

			Locale locale = LocaleUtil.getDefault();

			Assert.assertEquals(
				role.getDescription(locale),
				LanguageUtil.get(locale, descriptionKey));
			Assert.assertEquals(
				role.getTitle(locale), LanguageUtil.get(locale, titleKey));
		}
		catch (NoSuchRoleException noSuchRoleException) {
			throw new AssertionError(noSuchRoleException.getMessage());
		}
		catch (NoSuchResourcePermissionException
					noSuchResourcePermissionException) {

			throw new AssertionError(
				noSuchResourcePermissionException.getMessage());
		}
	}

	private void _assertRoleResourcePermissions(
			long companyId, String resourceName, String roleName,
			List<String> actionIds)
		throws PortalException {

		Role administratorRole = _roleLocalService.getRole(
			companyId, DepotRolesConstants.ASSET_LIBRARY_ADMINISTRATOR);
		Role role = _roleLocalService.getRole(companyId, roleName);

		for (String actionId : actionIds) {
			if (StringUtil.equals(resourceName, DepotEntry.class.getName())) {
				if (Objects.equals(actionId, ActionKeys.ASSIGN_USER_ROLES)) {
					continue;
				}

				Assert.assertTrue(
					_resourcePermissionLocalService.hasResourcePermission(
						companyId, resourceName,
						ResourceConstants.SCOPE_COMPANY,
						String.valueOf(companyId), role.getRoleId(), actionId));

				continue;
			}

			Assert.assertTrue(
				_resourcePermissionLocalService.hasResourcePermission(
					companyId, resourceName, ResourceConstants.SCOPE_INDIVIDUAL,
					String.valueOf(role.getRoleId()),
					administratorRole.getRoleId(), actionId));
		}
	}

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	@Inject
	private RoleLocalService _roleLocalService;

}