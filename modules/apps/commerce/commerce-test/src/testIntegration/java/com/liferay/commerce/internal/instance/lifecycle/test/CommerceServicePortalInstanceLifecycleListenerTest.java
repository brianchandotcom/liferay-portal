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
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import org.junit.Assert;
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

	@Test
	public void testPortalInstanceRegistered() throws Exception {
		Company company = CompanyTestUtil.addCompany();

		try {
			Role role = _roleLocalService.fetchRole(
				company.getCompanyId(),
				AccountRoleConstants.REQUIRED_ROLE_NAME_ACCOUNT_MANAGER);

			Assert.assertNotNull(role);

			ResourcePermission resourcePermission =
				_resourcePermissionLocalService.fetchResourcePermission(
					company.getCompanyId(), AccountEntry.class.getName(),
					ResourceConstants.SCOPE_GROUP_TEMPLATE, "0",
					role.getRoleId());

			resourcePermission.removeResourceAction(
				AccountActionKeys.MANAGE_ADDRESSES);

			resourcePermission =
				_resourcePermissionLocalService.updateResourcePermission(
					resourcePermission);

			Assert.assertFalse(
				resourcePermission.hasActionId(
					AccountActionKeys.MANAGE_ADDRESSES));

			_portalInstanceLifecycleListener.portalInstanceRegistered(company);

			resourcePermission =
				_resourcePermissionLocalService.fetchResourcePermission(
					company.getCompanyId(), AccountEntry.class.getName(),
					ResourceConstants.SCOPE_GROUP_TEMPLATE, "0",
					role.getRoleId());

			Assert.assertFalse(
				resourcePermission.hasActionId(
					AccountActionKeys.MANAGE_ADDRESSES));
		}
		finally {
			if (company != null) {
				_companyLocalService.deleteCompany(company.getCompanyId());
			}
		}
	}

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject(
		filter = "component.name=com.liferay.commerce.internal.instance.lifecycle.CommerceServicePortalInstanceLifecycleListener"
	)
	private PortalInstanceLifecycleListener _portalInstanceLifecycleListener;

	@Inject
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	@Inject
	private RoleLocalService _roleLocalService;

}