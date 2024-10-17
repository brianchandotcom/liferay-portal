/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.instance.lifecycle.test;

import com.liferay.account.constants.AccountActionKeys;
import com.liferay.account.constants.AccountRoleConstants;
import com.liferay.account.model.AccountEntry;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.instance.lifecycle.PortalInstanceLifecycleListener;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.ResourcePermission;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ResourceActionLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Lianne Louie
 */
@RunWith(Arquillian.class)
public class CommerceServicePortalInstanceLifecycleListenerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_company = CompanyTestUtil.addCompany();

		_role = _roleLocalService.fetchRole(
			_company.getCompanyId(),
			AccountRoleConstants.REQUIRED_ROLE_NAME_ACCOUNT_MANAGER);

		_resourcePermission =
			_resourcePermissionLocalService.fetchResourcePermission(
				_company.getCompanyId(), AccountEntry.class.getName(),
				ResourceConstants.SCOPE_GROUP_TEMPLATE, "0", _role.getRoleId());

		_resourcePermission.removeResourceAction(
			AccountActionKeys.MANAGE_ADDRESSES);

		_resourcePermission =
			_resourcePermissionLocalService.updateResourcePermission(
				_resourcePermission);
	}

	@After
	public void tearDown() throws Exception {
		_companyLocalService.deleteCompany(_company.getCompanyId());
	}

	@Test
	public void testNewCompanyHasDefaultPermissions() throws Exception {
		Company newCompany = CompanyTestUtil.addCompany();

		try {
			_role = _roleLocalService.fetchRole(
				newCompany.getCompanyId(),
				AccountRoleConstants.REQUIRED_ROLE_NAME_ACCOUNT_MANAGER);

			_resourcePermission =
				_resourcePermissionLocalService.fetchResourcePermission(
					newCompany.getCompanyId(), AccountEntry.class.getName(),
					ResourceConstants.SCOPE_GROUP_TEMPLATE, "0",
					_role.getRoleId());

			Assert.assertTrue(
				_resourcePermission.hasActionId(
					AccountActionKeys.MANAGE_ADDRESSES));
		}
		finally {
			if (newCompany != null) {
				_companyLocalService.deleteCompany(newCompany.getCompanyId());
			}
		}
	}

	@Test
	public void testRemovedDefaultRolePermissionsDoNotRevert()
		throws Exception {

		Assert.assertFalse(
			_resourcePermissionLocalService.hasActionId(
				_resourcePermission,
				_resourceActionLocalService.fetchResourceAction(
					AccountEntry.class.getName(),
					AccountActionKeys.MANAGE_ADDRESSES)));

		_portalInstanceLifecycleListener.portalInstanceRegistered(_company);

		_resourcePermission =
			_resourcePermissionLocalService.fetchResourcePermission(
				_company.getCompanyId(), AccountEntry.class.getName(),
				ResourceConstants.SCOPE_GROUP_TEMPLATE, "0", _role.getRoleId());

		Assert.assertFalse(
			_resourcePermission.hasActionId(
				AccountActionKeys.MANAGE_ADDRESSES));
	}

	private Company _company;

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject(
		filter = "component.name=com.liferay.commerce.internal.instance.lifecycle.CommerceServicePortalInstanceLifecycleListener"
	)
	private PortalInstanceLifecycleListener _portalInstanceLifecycleListener;

	@Inject
	private ResourceActionLocalService _resourceActionLocalService;

	private ResourcePermission _resourcePermission;

	@Inject
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	private Role _role;

	@Inject
	private RoleLocalService _roleLocalService;

}