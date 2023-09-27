/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.admin.order.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.order.rule.model.COREntry;
import com.liferay.commerce.order.rule.service.COREntryLocalService;
import com.liferay.headless.commerce.admin.order.client.dto.v1_0.OrderRule;
import com.liferay.headless.commerce.core.util.DateConfig;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.test.rule.Inject;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alessio Antonio Rendina
 * @author Stefano Motta
 */
@DataGuard(scope = DataGuard.Scope.METHOD)
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

	@Ignore
	@Override
	@Test
	public void testGraphQLGetOrderRuleNotFound() throws Exception {
		super.testGraphQLGetOrderRuleNotFound();
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"active", "name"};
	}

	@Override
	protected OrderRule randomOrderRule() {
		return new OrderRule() {
			{
				active = true;
				description = RandomTestUtil.randomString();
				displayDate = RandomTestUtil.nextDate();
				expirationDate = RandomTestUtil.nextDate();
				externalReferenceCode = RandomTestUtil.randomString();
				id = RandomTestUtil.nextLong();
				name = RandomTestUtil.randomString();
				neverExpire = true;
			}
		};
	}

	@Override
	protected OrderRule testDeleteOrderRule_addOrderRule() throws Exception {
		return _addCOREntry(randomOrderRule());
	}

	@Override
	protected OrderRule
			testDeleteOrderRuleByExternalReferenceCode_addOrderRule()
		throws Exception {

		return _addCOREntry(randomOrderRule());
	}

	@Override
	protected OrderRule testGetOrderRule_addOrderRule() throws Exception {
		return _addCOREntry(randomOrderRule());
	}

	@Override
	protected OrderRule testGetOrderRuleByExternalReferenceCode_addOrderRule()
		throws Exception {

		return _addCOREntry(randomOrderRule());
	}

	@Override
	protected OrderRule testGetOrderRulesPage_addOrderRule(OrderRule orderRule)
		throws Exception {

		return _addCOREntry(orderRule);
	}

	@Override
	protected OrderRule testGraphQLOrderRule_addOrderRule() throws Exception {
		return _addCOREntry(randomOrderRule());
	}

	@Override
	protected OrderRule testPatchOrderRule_addOrderRule() throws Exception {
		return _addCOREntry(randomOrderRule());
	}

	@Override
	protected OrderRule testPatchOrderRuleByExternalReferenceCode_addOrderRule()
		throws Exception {

		return _addCOREntry(randomOrderRule());
	}

	@Override
	protected OrderRule testPostOrderRule_addOrderRule(OrderRule orderRule)
		throws Exception {

		return _addCOREntry(orderRule);
	}

	private OrderRule _addCOREntry(OrderRule orderRule) throws Exception {
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

	@Inject
	private COREntryLocalService _corEntryLocalService;

	private ServiceContext _serviceContext;
	private User _user;

}