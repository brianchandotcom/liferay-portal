/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.on.demand.admin.internal.security.permission.wrapper.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.on.demand.admin.test.util.OnDemandAdminTestUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactory;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Pei-Jung Lan
 */
@RunWith(Arquillian.class)
public class OnDemandAdminPermissionCheckerWrapperTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testHasPermission() throws Exception {
		Company company = CompanyTestUtil.addCompany();

		User companyAdminUser = UserTestUtil.addCompanyAdminUser(company);

		PermissionChecker permissionChecker = _permissionCheckerFactory.create(
			companyAdminUser);

		User onDemandAdminUser = OnDemandAdminTestUtil.addOnDemandAdminUser(
			company);

		Assert.assertFalse(
			permissionChecker.hasPermission(
				company.getGroupId(), User.class.getName(),
				onDemandAdminUser.getUserId(), ActionKeys.DELETE));
		Assert.assertFalse(
			permissionChecker.hasPermission(
				company.getGroupId(), User.class.getName(),
				onDemandAdminUser.getUserId(), ActionKeys.UPDATE));
		Assert.assertFalse(
			permissionChecker.hasPermission(
				company.getGroupId(), User.class.getName(),
				onDemandAdminUser.getUserId(), ActionKeys.VIEW));
	}

	@Inject
	private PermissionCheckerFactory _permissionCheckerFactory;

}