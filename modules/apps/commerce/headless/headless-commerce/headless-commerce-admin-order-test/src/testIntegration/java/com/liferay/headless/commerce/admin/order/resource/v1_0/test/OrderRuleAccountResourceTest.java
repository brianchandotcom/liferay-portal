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
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.account.service.AccountEntryLocalServiceUtil;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.order.rule.model.COREntry;
import com.liferay.commerce.order.rule.model.COREntryRel;
import com.liferay.commerce.order.rule.service.COREntryLocalService;
import com.liferay.commerce.order.rule.service.COREntryRelLocalService;
import com.liferay.headless.commerce.admin.order.client.dto.v1_0.OrderRuleAccount;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.test.rule.Inject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
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

		_corEntry = _corEntryLocalService.addCOREntry(
			RandomTestUtil.randomString(), _user.getUserId(),
			RandomTestUtil.randomBoolean(), RandomTestUtil.randomString(), 1, 1,
			2022, 12, 0, 0, 0, 0, 0, 0, true, RandomTestUtil.randomString(), 0,
			RandomTestUtil.randomString(), StringPool.BLANK, _serviceContext);
	}

	@Override
	@Test
	public void testDeleteOrderRuleAccount() throws Exception {
		OrderRuleAccount orderRuleAccount = _addCOREntryRel(
			randomOrderRuleAccount());

		assertHttpResponseStatusCode(
			204,
			orderRuleAccountResource.deleteOrderRuleAccountHttpResponse(
				orderRuleAccount.getOrderRuleAccountId()));

		assertHttpResponseStatusCode(
			404,
			orderRuleAccountResource.deleteOrderRuleAccountHttpResponse(
				orderRuleAccount.getOrderRuleAccountId()));

		assertHttpResponseStatusCode(
			404,
			orderRuleAccountResource.deleteOrderRuleAccountHttpResponse(
				orderRuleAccount.getOrderRuleAccountId()));
	}

	@Override
	@Test
	public void testGraphQLDeleteOrderRuleAccount() throws Exception {
		OrderRuleAccount orderRuleAccount = _addCOREntryRel(
			randomOrderRuleAccount());

		Assert.assertTrue(
			JSONUtil.getValueAsBoolean(
				invokeGraphQLMutation(
					new GraphQLField(
						"deleteOrderRuleAccount",
						HashMapBuilder.<String, Object>put(
							"orderRuleAccountId",
							orderRuleAccount.getOrderRuleAccountId()
						).build())),
				"JSONObject/data", "Object/deleteOrderRuleAccount"));

		JSONArray errorsJSONArray = JSONUtil.getValueAsJSONArray(
			invokeGraphQLQuery(
				new GraphQLField(
					"orderRuleAccount",
					HashMapBuilder.<String, Object>put(
						"orderRuleAccountId",
						orderRuleAccount.getOrderRuleAccountId()
					).build(),
					new GraphQLField("orderRuleAccountId"))),
			"JSONArray/errors");

		Assert.assertTrue(errorsJSONArray.length() > 0);
	}

	@Override
	protected Collection<EntityField> getEntityFields() throws Exception {
		try {
			return super.getEntityFields();
		}
		catch (NullPointerException nullPointerException) {
			Map<String, EntityField> entityFieldsMap = new HashMap<>();

			return entityFieldsMap.values();
		}
	}

	@Override
	protected OrderRuleAccount randomOrderRuleAccount() throws Exception {
		AccountEntry accountEntry =
			AccountEntryLocalServiceUtil.addAccountEntry(
				_user.getUserId(), 0, RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), null,
				RandomTestUtil.randomString() + "@liferay.com", null,
				RandomTestUtil.randomString(), "business", 1, _serviceContext);

		_accountEntries.add(accountEntry);

		return new OrderRuleAccount() {
			{
				accountExternalReferenceCode =
					accountEntry.getExternalReferenceCode();
				accountId = accountEntry.getAccountEntryId();
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

		return _addCOREntryRel(orderRuleAccount);
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

		return _addCOREntryRel(orderRuleAccount);
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

		return _addCOREntryRel(orderRuleAccount);
	}

	@Override
	protected OrderRuleAccount
			testPostOrderRuleIdOrderRuleAccount_addOrderRuleAccount(
				OrderRuleAccount orderRuleAccount)
		throws Exception {

		return _addCOREntryRel(orderRuleAccount);
	}

	private OrderRuleAccount _addCOREntryRel(OrderRuleAccount orderRuleAccount)
		throws Exception {

		AccountEntry accountEntry = _accountEntryLocalService.getAccountEntry(
			orderRuleAccount.getAccountId());

		COREntryRel corEntryRel = _corEntryRelLocalService.addCOREntryRel(
			_user.getUserId(), AccountEntry.class.getName(),
			orderRuleAccount.getAccountId(), orderRuleAccount.getOrderRuleId());

		return new OrderRuleAccount() {
			{
				accountExternalReferenceCode =
					accountEntry.getExternalReferenceCode();
				accountId = accountEntry.getAccountEntryId();
				orderRuleAccountId = corEntryRel.getCOREntryRelId();
				orderRuleExternalReferenceCode =
					_corEntry.getExternalReferenceCode();
				orderRuleId = _corEntry.getCOREntryId();
			}
		};
	}

	@Inject
	private static AccountEntryLocalService _accountEntryLocalService;

	@Inject
	private static COREntryLocalService _corEntryLocalService;

	@Inject
	private static COREntryRelLocalService _corEntryRelLocalService;

	@DeleteAfterTestRun
	private final List<AccountEntry> _accountEntries = new ArrayList<>();

	@DeleteAfterTestRun
	private COREntry _corEntry;

	private ServiceContext _serviceContext;

	@DeleteAfterTestRun
	private User _user;

}