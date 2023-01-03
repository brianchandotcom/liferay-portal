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

package com.liferay.headless.commerce.admin.order.resource.v1_0.test;

import com.liferay.account.service.AccountGroupLocalService;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.order.rule.constants.COREntryConstants;
import com.liferay.commerce.order.rule.model.COREntry;
import com.liferay.commerce.order.rule.model.COREntryRel;
import com.liferay.commerce.order.rule.service.COREntryLocalService;
import com.liferay.commerce.order.rule.service.COREntryRelLocalService;
import com.liferay.headless.commerce.admin.order.client.dto.v1_0.AccountGroup;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.test.rule.Inject;

import org.junit.Before;
import org.junit.runner.RunWith;

/**
 * @author Alessio Antonio Rendina
 */
@RunWith(Arquillian.class)
public class AccountGroupResourceTest extends BaseAccountGroupResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_user = UserTestUtil.addUser(testCompany);

		_accountGroup = _accountGroupLocalService.addAccountGroup(
			_user.getUserId(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString());

		_corEntry = _corEntryLocalService.addCOREntry(
			RandomTestUtil.randomString(), _user.getUserId(), true,
			RandomTestUtil.randomString(), 1, 1, 2022, 12, 0, 0, 0, 0, 0, 0,
			true, RandomTestUtil.randomString(), RandomTestUtil.nextInt(),
			COREntryConstants.TYPE_MINIMUM_ORDER_AMOUNT, null,
			ServiceContextTestUtil.getServiceContext(
				testCompany.getCompanyId(), testGroup.getGroupId(),
				_user.getUserId()));

		_corEntryRel = _corEntryRelLocalService.addCOREntryRel(
			_user.getUserId(), AccountGroup.class.getName(),
			_accountGroup.getAccountGroupId(), _corEntry.getCOREntryId());
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"name"};
	}

	@Override
	protected AccountGroup
			testGetOrderRuleAccountGroupAccountGroup_addAccountGroup()
		throws Exception {

		return _toAccountGroup();
	}

	@Override
	protected Long
			testGetOrderRuleAccountGroupAccountGroup_getOrderRuleAccountGroupId()
		throws Exception {

		return _corEntryRel.getCOREntryRelId();
	}

	@Override
	protected AccountGroup testGraphQLAccountGroup_addAccountGroup()
		throws Exception {

		return _toAccountGroup();
	}

	@Override
	protected Long
			testGraphQLGetOrderRuleAccountGroupAccountGroup_getOrderRuleAccountGroupId()
		throws Exception {

		return _corEntryRel.getCOREntryRelId();
	}

	private AccountGroup _toAccountGroup() throws Exception {
		return new AccountGroup() {
			{
				id = _accountGroup.getAccountGroupId();
				name = _accountGroup.getName();
			}
		};
	}

	@Inject
	private static AccountGroupLocalService _accountGroupLocalService;

	@Inject
	private static COREntryLocalService _corEntryLocalService;

	@Inject
	private static COREntryRelLocalService _corEntryRelLocalService;

	@DeleteAfterTestRun
	private com.liferay.account.model.AccountGroup _accountGroup;

	@DeleteAfterTestRun
	private COREntry _corEntry;

	@DeleteAfterTestRun
	private COREntryRel _corEntryRel;

	@DeleteAfterTestRun
	private User _user;

}