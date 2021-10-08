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

import com.liferay.account.model.AccountEntry;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.account.model.CommerceAccount;
import com.liferay.commerce.account.service.CommerceAccountLocalService;
import com.liferay.commerce.account.test.util.CommerceAccountTestUtil;
import com.liferay.commerce.order.rule.model.COREntry;
import com.liferay.commerce.order.rule.model.COREntryRel;
import com.liferay.commerce.order.rule.service.COREntryLocalService;
import com.liferay.commerce.order.rule.service.COREntryRelLocalService;
import com.liferay.headless.commerce.admin.order.client.dto.v1_0.OrderRuleAccount;
import com.liferay.headless.commerce.core.util.DateConfig;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.test.rule.Inject;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alessio Antonio Rendina
 */
@RunWith(Arquillian.class)
public class OrderRuleAccountResourceTest
	extends BaseOrderRuleAccountResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_user = UserTestUtil.addUser(testCompany);

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			testCompany.getCompanyId(), testGroup.getGroupId(),
			_user.getUserId());

		DateConfig displayDateConfig = DateConfig.toDisplayDateConfig(
			RandomTestUtil.nextDate(), _user.getTimeZone());
		DateConfig expirationDateConfig = DateConfig.toExpirationDateConfig(
			RandomTestUtil.nextDate(), _user.getTimeZone());

		_corEntry = _corEntryLocalService.addCOREntry(
			RandomTestUtil.randomString(), _user.getUserId(),
			RandomTestUtil.randomBoolean(), RandomTestUtil.randomString(),
			displayDateConfig.getMonth(), displayDateConfig.getDay(),
			displayDateConfig.getYear(), displayDateConfig.getHour(),
			displayDateConfig.getMinute(), expirationDateConfig.getMonth(),
			expirationDateConfig.getDay(), expirationDateConfig.getYear(),
			expirationDateConfig.getHour(), expirationDateConfig.getMinute(),
			true, RandomTestUtil.randomString(), 0,
			RandomTestUtil.randomString(), StringPool.BLANK, _serviceContext);
	}

	@Override
	@Test
	public void testDeleteOrderRuleAccount() throws Exception {
	}

	@Override
	@Test
	public void testGraphQLDeleteOrderRuleAccount() throws Exception {
	}

	@Override
	protected Collection<EntityField> getEntityFields() throws Exception {
		return new ArrayList<>();
	}

	@Override
	protected OrderRuleAccount randomOrderRuleAccount() throws Exception {
		User defaultUser = testCompany.getDefaultUser();

		CommerceAccount commerceAccount =
			CommerceAccountTestUtil.addBusinessCommerceAccount(
				defaultUser.getUserId(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString() + "@liferay.com",
				RandomTestUtil.randomString(), new long[] {_user.getUserId()},
				null, _serviceContext);

		return new OrderRuleAccount() {
			{
				accountExternalReferenceCode =
					commerceAccount.getExternalReferenceCode();
				accountId = commerceAccount.getCommerceAccountId();
				orderRuleAccountId = RandomTestUtil.randomLong();
				orderRuleExternalReferenceCode =
					_corEntry.getExternalReferenceCode();
				orderRuleId = _corEntry.getCOREntryId();
			}
		};
	}

	@Override
	protected OrderRuleAccount
			testGetOrderRuleByExternalReferenceCodeOrderRuleAccountsPage_addOrderRuleAccount(
				String externalReferenceCode, OrderRuleAccount orderRuleAccount)
		throws Exception {

		return _addOrderRuleAccount(orderRuleAccount);
	}

	@Override
	protected String
			testGetOrderRuleByExternalReferenceCodeOrderRuleAccountsPage_getExternalReferenceCode()
		throws Exception {

		return _corEntry.getExternalReferenceCode();
	}

	@Override
	protected OrderRuleAccount
			testGetOrderRuleIdOrderRuleAccountsPage_addOrderRuleAccount(
				Long id, OrderRuleAccount orderRuleAccount)
		throws Exception {

		return _addOrderRuleAccount(orderRuleAccount);
	}

	@Override
	protected Long testGetOrderRuleIdOrderRuleAccountsPage_getId()
		throws Exception {

		return _corEntry.getCOREntryId();
	}

	@Override
	protected OrderRuleAccount
			testPostOrderRuleByExternalReferenceCodeOrderRuleAccount_addOrderRuleAccount(
				OrderRuleAccount orderRuleAccount)
		throws Exception {

		return _addOrderRuleAccount(orderRuleAccount);
	}

	@Override
	protected OrderRuleAccount
			testPostOrderRuleIdOrderRuleAccount_addOrderRuleAccount(
				OrderRuleAccount orderRuleAccount)
		throws Exception {

		return _addOrderRuleAccount(orderRuleAccount);
	}

	private OrderRuleAccount _addOrderRuleAccount(
			OrderRuleAccount orderRuleAccount)
		throws Exception {

		return _toOrderRuleAccount(
			_corEntryRelLocalService.addCOREntryRel(
				_user.getUserId(), AccountEntry.class.getName(),
				orderRuleAccount.getAccountId(),
				orderRuleAccount.getOrderRuleId()));
	}

	private OrderRuleAccount _toOrderRuleAccount(COREntryRel corEntryRel)
		throws Exception {

		CommerceAccount commerceAccount =
			_commerceAccountLocalService.getCommerceAccount(
				corEntryRel.getClassPK());
		COREntry corEntry = _corEntryLocalService.fetchCOREntry(
			corEntryRel.getCOREntryId());

		return new OrderRuleAccount() {
			{
				accountExternalReferenceCode =
					commerceAccount.getExternalReferenceCode();
				accountId = commerceAccount.getCommerceAccountId();
				orderRuleAccountId = corEntryRel.getCOREntryRelId();
				orderRuleExternalReferenceCode =
					corEntry.getExternalReferenceCode();
				orderRuleId = corEntry.getCOREntryId();
			}
		};
	}

	@Inject
	private CommerceAccountLocalService _commerceAccountLocalService;

	private COREntry _corEntry;

	@Inject
	private COREntryLocalService _corEntryLocalService;

	@Inject
	private COREntryRelLocalService _corEntryRelLocalService;

	private ServiceContext _serviceContext;
	private User _user;

}