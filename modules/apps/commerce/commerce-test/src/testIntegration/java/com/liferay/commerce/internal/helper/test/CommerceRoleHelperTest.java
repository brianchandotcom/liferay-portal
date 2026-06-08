/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.helper.test;

import com.liferay.account.constants.AccountRoleConstants;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.helper.CommerceRoleHelper;
import com.liferay.portal.kernel.model.ResourcePermission;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Stefano Motta
 */
@DataGuard(scope = DataGuard.Scope.NONE)
@RunWith(Arquillian.class)
public class CommerceRoleHelperTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testCheckCommerceAccountRoles() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				TestPropsValues.getGroupId(), TestPropsValues.getUserId());

		_commerceRoleHelper.checkCommerceAccountRoles(serviceContext);

		Assert.assertNotNull(
			_roleLocalService.fetchRoleByExternalReferenceCode(
				RoleConstants.toSystemRoleExternalReferenceCode(
					AccountRoleConstants.
						REQUIRED_ROLE_NAME_ACCOUNT_ADMINISTRATOR),
				TestPropsValues.getCompanyId()));
		Assert.assertNotNull(
			_roleLocalService.fetchRoleByExternalReferenceCode(
				RoleConstants.toSystemRoleExternalReferenceCode(
					AccountRoleConstants.REQUIRED_ROLE_NAME_ACCOUNT_MANAGER),
				TestPropsValues.getCompanyId()));
		Assert.assertNotNull(
			_roleLocalService.fetchRoleByExternalReferenceCode(
				RoleConstants.toSystemRoleExternalReferenceCode(
					AccountRoleConstants.REQUIRED_ROLE_NAME_ACCOUNT_MEMBER),
				TestPropsValues.getCompanyId()));
		Assert.assertNotNull(
			_roleLocalService.fetchRoleByExternalReferenceCode(
				RoleConstants.toSystemRoleExternalReferenceCode(
					AccountRoleConstants.ROLE_NAME_ACCOUNT_BUYER),
				TestPropsValues.getCompanyId()));
		Assert.assertNotNull(
			_roleLocalService.fetchRoleByExternalReferenceCode(
				RoleConstants.toSystemRoleExternalReferenceCode(
					AccountRoleConstants.ROLE_NAME_ACCOUNT_ORDER_MANAGER),
				TestPropsValues.getCompanyId()));
		Assert.assertNotNull(
			_roleLocalService.fetchRoleByExternalReferenceCode(
				RoleConstants.toSystemRoleExternalReferenceCode(
					AccountRoleConstants.ROLE_NAME_ACCOUNT_SUPPLIER),
				TestPropsValues.getCompanyId()));
		Assert.assertNotNull(
			_roleLocalService.fetchRoleByExternalReferenceCode(
				RoleConstants.toSystemRoleExternalReferenceCode(
					AccountRoleConstants.ROLE_NAME_ORDER_ADMINISTRATOR),
				TestPropsValues.getCompanyId()));

		Role role = _roleLocalService.fetchRoleByExternalReferenceCode(
			RoleConstants.toSystemRoleExternalReferenceCode(
				AccountRoleConstants.ROLE_NAME_SUPPLIER),
			TestPropsValues.getCompanyId());

		List<ResourcePermission> resourcePermissions =
			_resourcePermissionLocalService.getRoleResourcePermissions(
				role.getRoleId());

		int size = resourcePermissions.size();

		_resourcePermissionLocalService.deleteResourcePermission(
			resourcePermissions.get(0));

		serviceContext.setAttribute("forceReloadPermissions", Boolean.TRUE);

		_commerceRoleHelper.checkCommerceAccountRoles(serviceContext);

		resourcePermissions =
			_resourcePermissionLocalService.getRoleResourcePermissions(
				role.getRoleId());

		Assert.assertEquals(
			resourcePermissions.toString(), size - 1,
			resourcePermissions.size());

		for (ResourcePermission resourcePermission : resourcePermissions) {
			_resourcePermissionLocalService.deleteResourcePermission(
				resourcePermission);
		}

		_commerceRoleHelper.checkCommerceAccountRoles(serviceContext);

		resourcePermissions =
			_resourcePermissionLocalService.getRoleResourcePermissions(
				role.getRoleId());

		Assert.assertEquals(
			resourcePermissions.toString(), size, resourcePermissions.size());
	}

	@Inject(
		filter = "component.name=com.liferay.commerce.internal.helper.CommerceRoleHelperImpl"
	)
	private CommerceRoleHelper _commerceRoleHelper;

	@Inject
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	@Inject
	private RoleLocalService _roleLocalService;

}