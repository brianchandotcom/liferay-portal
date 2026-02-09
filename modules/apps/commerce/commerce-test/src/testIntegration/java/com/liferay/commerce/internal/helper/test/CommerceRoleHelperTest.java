/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.helper.test;

import com.liferay.account.constants.AccountRoleConstants;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.helper.CommerceRoleHelper;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Stefano Motta
 */
@RunWith(Arquillian.class)
public class CommerceRoleHelperTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testCheckCommerceAccountRoles() throws Exception {
		_commerceRoleHelper.checkCommerceAccountRoles(
			ServiceContextTestUtil.getServiceContext(
				TestPropsValues.getGroupId(), TestPropsValues.getUserId()));

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
		Assert.assertNotNull(
			_roleLocalService.fetchRoleByExternalReferenceCode(
				RoleConstants.toSystemRoleExternalReferenceCode(
					AccountRoleConstants.ROLE_NAME_SUPPLIER),
				TestPropsValues.getCompanyId()));
	}

	@Inject(
		filter = "component.name=com.liferay.commerce.internal.helper.CommerceRoleHelperImpl"
	)
	private CommerceRoleHelper _commerceRoleHelper;

	@Inject
	private RoleLocalService _roleLocalService;

}