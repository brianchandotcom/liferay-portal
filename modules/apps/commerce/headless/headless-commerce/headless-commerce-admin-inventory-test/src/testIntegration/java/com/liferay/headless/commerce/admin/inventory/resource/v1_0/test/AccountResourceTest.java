/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.admin.inventory.resource.v1_0.test;

import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.inventory.model.CommerceInventoryWarehouse;
import com.liferay.commerce.inventory.model.CommerceInventoryWarehouseRel;
import com.liferay.commerce.inventory.service.CommerceInventoryWarehouseRelLocalServiceUtil;
import com.liferay.commerce.test.util.CommerceInventoryTestUtil;
import com.liferay.headless.commerce.admin.inventory.client.dto.v1_0.Account;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.test.rule.Inject;

import org.junit.Before;
import org.junit.runner.RunWith;

/**
 * @author Danny Situ
 */
@RunWith(Arquillian.class)
public class AccountResourceTest extends BaseAccountResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_user = UserTestUtil.addUser(testCompany);

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			testCompany.getCompanyId(), testGroup.getGroupId(),
			_user.getUserId());

		_commerceInventoryWarehouse =
			CommerceInventoryTestUtil.addCommerceInventoryWarehouse(
				_serviceContext);
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"logoId", "name"};
	}

	@Override
	protected Account testGetWarehouseAccountAccount_addAccount()
		throws Exception {

		return _addAccount();
	}

	@Override
	protected Long testGetWarehouseAccountAccount_getWarehouseAccountId()
		throws Exception {

		return _getWarehouseAccountId();
	}

	@Override
	protected Account testGraphQLAccount_addAccount() throws Exception {
		return _addAccount();
	}

	@Override
	protected Long testGraphQLGetWarehouseAccountAccount_getWarehouseAccountId()
		throws Exception {

		return _getWarehouseAccountId();
	}

	private Account _addAccount() throws Exception {
		_accountEntry = _accountEntryLocalService.addAccountEntry(
			_user.getUserId(), 0, RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), null,
			RandomTestUtil.randomString() + "@liferay.com", null, null,
			"business", 1, _serviceContext);

		return new Account() {
			{
				id = _accountEntry.getAccountEntryId();
				logoId = _accountEntry.getLogoId();
				name = _accountEntry.getName();
			}
		};
	}

	private long _getWarehouseAccountId() throws Exception {
		if (_commerceInventoryWarehouseRel != null) {
			return _commerceInventoryWarehouseRel.
				getCommerceInventoryWarehouseRelId();
		}

		_commerceInventoryWarehouseRel =
			CommerceInventoryWarehouseRelLocalServiceUtil.
				addCommerceInventoryWarehouseRel(
					_user.getUserId(), AccountEntry.class.getName(),
					_accountEntry.getAccountEntryId(),
					_commerceInventoryWarehouse.
						getCommerceInventoryWarehouseId());

		return _commerceInventoryWarehouseRel.
			getCommerceInventoryWarehouseRelId();
	}

	@DeleteAfterTestRun
	private AccountEntry _accountEntry;

	@Inject
	private AccountEntryLocalService _accountEntryLocalService;

	@DeleteAfterTestRun
	private CommerceInventoryWarehouse _commerceInventoryWarehouse;

	@DeleteAfterTestRun
	private CommerceInventoryWarehouseRel _commerceInventoryWarehouseRel;

	private ServiceContext _serviceContext;

	@DeleteAfterTestRun
	private User _user;

}