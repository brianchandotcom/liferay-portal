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

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.order.rule.model.COREntry;
import com.liferay.commerce.order.rule.service.COREntryLocalService;
import com.liferay.headless.commerce.admin.order.client.dto.v1_0.OrderRule;
import com.liferay.headless.commerce.core.util.DateConfig;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.test.rule.Inject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alessio Antonio Rendina
 */
@RunWith(Arquillian.class)
public class OrderRuleResourceTest extends BaseOrderRuleResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_user = UserTestUtil.addUser(testCompany);

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			testCompany.getCompanyId(), testGroup.getGroupId(),
			_user.getUserId());
	}

	@After
	@Override
	public void tearDown() throws Exception {
		super.tearDown();

		Iterator<COREntry> iterator = _corEntries.iterator();

		while (iterator.hasNext()) {
			COREntry corEntry1 = iterator.next();

			COREntry corEntry2 = _corEntryLocalService.fetchCOREntry(
				corEntry1.getCOREntryId());

			if (corEntry2 != null) {
				_corEntryLocalService.deleteCOREntry(corEntry2.getCOREntryId());
			}

			iterator.remove();
		}
	}

	@Override
	@Test
	public void testGraphQLGetOrderRuleNotFound() throws Exception {
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"active", "name"};
	}

	@Override
	protected OrderRule randomOrderRule() {
		return new OrderRule() {
			{
				active = RandomTestUtil.randomBoolean();
				description = RandomTestUtil.randomString();
				displayDate = RandomTestUtil.nextDate();
				expirationDate = RandomTestUtil.nextDate();
				externalReferenceCode = RandomTestUtil.randomString();
				id = RandomTestUtil.nextLong();
				name = RandomTestUtil.randomString();
			}
		};
	}

	@Override
	protected OrderRule randomPatchOrderRule() throws Exception {
		return randomOrderRule();
	}

	@Override
	protected OrderRule testDeleteOrderRule_addOrderRule() throws Exception {
		return _addOrderRule(randomOrderRule());
	}

	@Override
	protected OrderRule
			testDeleteOrderRuleByExternalReferenceCode_addOrderRule()
		throws Exception {

		return _addOrderRule(randomOrderRule());
	}

	@Override
	protected OrderRule testGetOrderRule_addOrderRule() throws Exception {
		return _addOrderRule(randomOrderRule());
	}

	@Override
	protected OrderRule testGetOrderRuleByExternalReferenceCode_addOrderRule()
		throws Exception {

		return _addOrderRule(randomOrderRule());
	}

	@Override
	protected OrderRule testGetOrderRulesPage_addOrderRule(OrderRule orderRule)
		throws Exception {

		return _addOrderRule(orderRule);
	}

	@Override
	protected OrderRule testGraphQLOrderRule_addOrderRule() throws Exception {
		return _addOrderRule(randomOrderRule());
	}

	@Override
	protected OrderRule testPatchOrderRule_addOrderRule() throws Exception {
		return _addOrderRule(randomOrderRule());
	}

	@Override
	protected OrderRule testPatchOrderRuleByExternalReferenceCode_addOrderRule()
		throws Exception {

		return _addOrderRule(randomOrderRule());
	}

	@Override
	protected OrderRule testPostOrderRule_addOrderRule(OrderRule orderRule)
		throws Exception {

		return _addOrderRule(orderRule);
	}

	private OrderRule _addOrderRule(OrderRule orderRule) throws Exception {
		DateConfig displayDateConfig = DateConfig.toDisplayDateConfig(
			orderRule.getDisplayDate(), _user.getTimeZone());
		DateConfig expirationDateConfig = DateConfig.toExpirationDateConfig(
			orderRule.getExpirationDate(), _user.getTimeZone());

		COREntry corEntry = _corEntryLocalService.addCOREntry(
			orderRule.getExternalReferenceCode(), _user.getUserId(),
			GetterUtil.getBoolean(orderRule.getActive()),
			orderRule.getDescription(), displayDateConfig.getMonth(),
			displayDateConfig.getDay(), displayDateConfig.getYear(),
			displayDateConfig.getHour(), displayDateConfig.getMinute(),
			expirationDateConfig.getMonth(), expirationDateConfig.getDay(),
			expirationDateConfig.getYear(), expirationDateConfig.getHour(),
			expirationDateConfig.getMinute(),
			GetterUtil.getBoolean(orderRule.getNeverExpire(), true),
			orderRule.getName(), GetterUtil.getInteger(orderRule.getPriority()),
			orderRule.getType(), orderRule.getTypeSettings(), _serviceContext);

		_corEntries.add(corEntry);

		return _toOrderRule(corEntry);
	}

	private OrderRule _toOrderRule(COREntry corEntry) {
		return new OrderRule() {
			{
				active = corEntry.isActive();
				description = corEntry.getDescription();
				displayDate = corEntry.getDisplayDate();
				expirationDate = corEntry.getExpirationDate();
				externalReferenceCode = corEntry.getExternalReferenceCode();
				id = corEntry.getCOREntryId();
				name = corEntry.getName();
			}
		};
	}

	private final List<COREntry> _corEntries = new ArrayList<>();

	@Inject
	private COREntryLocalService _corEntryLocalService;

	private ServiceContext _serviceContext;
	private User _user;

}