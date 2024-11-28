/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.admin.inventory.resource.v1_0.test;

import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.account.service.AccountGroupLocalService;
import com.liferay.account.service.AccountGroupRelLocalServiceUtil;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.inventory.model.CommerceInventoryWarehouse;
import com.liferay.commerce.inventory.model.CommerceInventoryWarehouseRel;
import com.liferay.commerce.inventory.service.CommerceInventoryWarehouseRelLocalServiceUtil;
import com.liferay.commerce.test.util.CommerceInventoryTestUtil;
import com.liferay.headless.commerce.admin.inventory.client.dto.v1_0.AccountGroup;
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
public class AccountGroupResourceTest extends BaseAccountGroupResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_user = UserTestUtil.addUser(testCompany);

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			testCompany.getCompanyId(), testGroup.getGroupId(),
			_user.getUserId());

		_accountEntry = _accountEntryLocalService.addAccountEntry(
			_user.getUserId(), 0, RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), null,
			RandomTestUtil.randomString() + "@liferay.com", null, null,
			"business", 1, _serviceContext);

		_commerceInventoryWarehouse =
			CommerceInventoryTestUtil.addCommerceInventoryWarehouse(
				_serviceContext);
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"name"};
	}

	@Override
	protected AccountGroup
			testGetWarehouseAccountGroupAccountGroup_addAccountGroup()
		throws Exception {

		return _addAccountGroup();
	}

	@Override
	protected Long
			testGetWarehouseAccountGroupAccountGroup_getWarehouseAccountGroupId()
		throws Exception {

		return _getWarehouseAccountGroupId();
	}

	@Override
	protected AccountGroup testGraphQLAccountGroup_addAccountGroup()
		throws Exception {

		return _addAccountGroup();
	}

	@Override
	protected Long
			testGraphQLGetWarehouseAccountGroupAccountGroup_getWarehouseAccountGroupId()
		throws Exception {

		return _getWarehouseAccountGroupId();
	}

	private AccountGroup _addAccountGroup() throws Exception {
		_accountGroup = _accountGroupLocalService.addAccountGroup(
			_user.getUserId(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), _serviceContext);

		AccountGroupRelLocalServiceUtil.addAccountGroupRel(
			_accountGroup.getAccountGroupId(), AccountEntry.class.getName(),
			_accountEntry.getAccountEntryId());

		return new AccountGroup() {
			{
				id = _accountGroup.getAccountGroupId();
				name = _accountGroup.getName();
			}
		};
	}

	private long _getWarehouseAccountGroupId() throws Exception {
		if (_commerceInventoryWarehouseRel != null) {
			return _commerceInventoryWarehouseRel.
				getCommerceInventoryWarehouseRelId();
		}

		_commerceInventoryWarehouseRel =
			CommerceInventoryWarehouseRelLocalServiceUtil.
				addCommerceInventoryWarehouseRel(
					_user.getUserId(),
					com.liferay.account.model.AccountGroup.class.getName(),
					_accountGroup.getAccountGroupId(),
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
	private com.liferay.account.model.AccountGroup _accountGroup;

	@Inject
	private AccountGroupLocalService _accountGroupLocalService;

	@DeleteAfterTestRun
	private CommerceInventoryWarehouse _commerceInventoryWarehouse;

	@DeleteAfterTestRun
	private CommerceInventoryWarehouseRel _commerceInventoryWarehouseRel;

	private ServiceContext _serviceContext;

	@DeleteAfterTestRun
	private User _user;

}