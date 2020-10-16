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

package com.liferay.user.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactory;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.UserService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Albert Lee
 */
@RunWith(Arquillian.class)
public class UserServiceWhenAddingUserAcrossCompaniesTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testOmniAdminShouldAddUserFromAnotherCompany()
		throws Exception {

		Company company = CompanyTestUtil.addCompany();

		User user = UserTestUtil.addOmniAdminUser();

		PermissionChecker originalPermissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		String originalName = PrincipalThreadLocal.getName();

		try {
			PermissionThreadLocal.setPermissionChecker(
				_permissionCheckerFactory.create(user));

			PrincipalThreadLocal.setName(user.getUserId());

			_addUser(company.getCompanyId());
		}
		finally {
			PermissionThreadLocal.setPermissionChecker(
				originalPermissionChecker);

			PrincipalThreadLocal.setName(originalName);
		}
	}

	@Test
	public void testShouldNotAddUserFromAnotherCompany() throws Exception {
		Company company = CompanyTestUtil.addCompany();

		User user = UserTestUtil.addCompanyAdminUser(company);

		PermissionChecker originalPermissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		String originalName = PrincipalThreadLocal.getName();

		try {
			PermissionThreadLocal.setPermissionChecker(
				_permissionCheckerFactory.create(user));

			PrincipalThreadLocal.setName(user.getUserId());

			_addUser(TestPropsValues.getCompanyId());

			Assert.fail();
		}
		catch (PrincipalException principalException) {
			Assert.assertEquals(
				"Only the omniadmin can add users to another company",
				principalException.getMessage());
		}
		finally {
			PermissionThreadLocal.setPermissionChecker(
				originalPermissionChecker);

			PrincipalThreadLocal.setName(originalName);
		}
	}

	private void _addUser(long companyId) throws Exception {
		String screenName = RandomTestUtil.randomString();
		String password = RandomTestUtil.randomString();
		String emailAddress = RandomTestUtil.randomString() + "@liferay.com";
		String firstName = RandomTestUtil.randomString();
		String lastName = RandomTestUtil.randomString();

		_userService.addUser(
			companyId, false, password, password, false, screenName,
			emailAddress, LocaleUtil.getDefault(), firstName, null, lastName, 0,
			0, true, 1, 1, 2000, null, null, null, null, null, true,
			ServiceContextTestUtil.getServiceContext());
	}

	@Inject
	private PermissionCheckerFactory _permissionCheckerFactory;

	@Inject
	private UserService _userService;

}