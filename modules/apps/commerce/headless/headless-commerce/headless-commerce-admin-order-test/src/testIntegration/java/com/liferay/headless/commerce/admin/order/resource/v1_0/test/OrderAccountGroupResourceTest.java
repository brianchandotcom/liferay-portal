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

import com.liferay.account.model.AccountGroup;
import com.liferay.account.service.AccountGroupLocalService;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.order.rule.constants.COREntryConstants;
import com.liferay.commerce.order.rule.model.COREntry;
import com.liferay.commerce.order.rule.model.COREntryRel;
import com.liferay.commerce.order.rule.service.COREntryLocalService;
import com.liferay.commerce.order.rule.service.COREntryRelLocalService;
import com.liferay.headless.commerce.admin.order.client.dto.v1_0.OrderAccountGroup;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.test.rule.Inject;

import org.junit.Before;
import org.junit.runner.RunWith;

/**
 * @author Stefano Motta
 */
@DataGuard(scope = DataGuard.Scope.METHOD)
@RunWith(Arquillian.class)
public class OrderAccountGroupResourceTest
	extends BaseOrderAccountGroupResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		User user = UserTestUtil.addUser(testCompany);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				testCompany.getCompanyId(), testGroup.getGroupId(),
				user.getUserId());

		_accountGroup = _accountGroupLocalService.addAccountGroup(
			user.getUserId(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), serviceContext);

		COREntry corEntry = _corEntryLocalService.addCOREntry(
			RandomTestUtil.randomString(), user.getUserId(), true,
			RandomTestUtil.randomString(), 1, 1, 2022, 12, 0, 0, 0, 0, 0, 0,
			true, RandomTestUtil.randomString(), RandomTestUtil.nextInt(),
			COREntryConstants.TYPE_MINIMUM_ORDER_AMOUNT, null, serviceContext);

		_corEntryRel = _corEntryRelLocalService.addCOREntryRel(
			user.getUserId(), AccountGroup.class.getName(),
			_accountGroup.getAccountGroupId(), corEntry.getCOREntryId());
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"name"};
	}

	@Override
	protected OrderAccountGroup
			testGetOrderRuleAccountGroupAccountGroup_addOrderAccountGroup()
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
	protected Long
			testGraphQLGetOrderRuleAccountGroupAccountGroup_getOrderRuleAccountGroupId()
		throws Exception {

		return _corEntryRel.getCOREntryRelId();
	}

	@Override
	protected OrderAccountGroup
			testGraphQLOrderAccountGroup_addOrderAccountGroup()
		throws Exception {

		return _toAccountGroup();
	}

	private OrderAccountGroup _toAccountGroup() throws Exception {
		return new OrderAccountGroup() {
			{
				id = _accountGroup.getAccountGroupId();
				name = _accountGroup.getName();
			}
		};
	}

	private AccountGroup _accountGroup;

	@Inject
	private AccountGroupLocalService _accountGroupLocalService;

	@Inject
	private COREntryLocalService _corEntryLocalService;

	private COREntryRel _corEntryRel;

	@Inject
	private COREntryRelLocalService _corEntryRelLocalService;

}