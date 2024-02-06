/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.service.test;

import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryService;
import com.liferay.account.service.test.util.AccountEntryArgs;
import com.liferay.account.service.test.util.AccountEntryTestUtil;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.model.Address;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.ListTypeConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.AddressLocalService;
import com.liferay.portal.kernel.service.ListTypeLocalService;
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Danny Situ
 */
@DataGuard(scope = DataGuard.Scope.METHOD)
@RunWith(Arquillian.class)
public class AccountEntryServiceTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testUpdateInvalidAddressId() throws Exception {
		PermissionChecker originalPermissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		AccountEntry accountEntry1 = AccountEntryTestUtil.addAccountEntry();

		Address address = _addressLocalService.addAddress(
			null, accountEntry1.getUserId(), AccountEntry.class.getName(),
			accountEntry1.getAccountEntryId(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(), null,
			null, RandomTestUtil.randomString(), null, 0, 0,
			_listTypeLocalService.getListTypeId(
				accountEntry1.getCompanyId(), "personal",
				ListTypeConstants.CONTACT_ADDRESS),
			false, false, "1234567890",
			ServiceContextTestUtil.getServiceContext());

		Company company = CompanyTestUtil.addCompany();

		User user = UserTestUtil.addCompanyAdminUser(company);

		AccountEntry accountEntry2 = AccountEntryTestUtil.addAccountEntry(
			AccountEntryArgs.withOwner(user));

		try {
			PermissionThreadLocal.setPermissionChecker(
				PermissionCheckerFactoryUtil.create(user));

			accountEntry2.setDefaultBillingAddressId(address.getAddressId());
			accountEntry2.setDefaultShippingAddressId(address.getAddressId());

			_accountEntryService.updateAccountEntry(accountEntry2);

			Assert.fail();
		}
		catch (Exception exception) {
			String message = exception.getMessage();

			Assert.assertTrue(
				message.contains(
					"User " + user.getUserId() + " must have VIEW permission"));
		}
		finally {
			PermissionThreadLocal.setPermissionChecker(
				originalPermissionChecker);
		}
	}

	@Inject
	private static ListTypeLocalService _listTypeLocalService;

	@Inject
	private AccountEntryService _accountEntryService;

	@Inject
	private AddressLocalService _addressLocalService;

}