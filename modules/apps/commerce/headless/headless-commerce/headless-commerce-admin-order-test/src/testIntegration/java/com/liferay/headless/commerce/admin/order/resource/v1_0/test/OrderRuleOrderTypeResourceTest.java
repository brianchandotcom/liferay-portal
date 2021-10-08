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
import com.liferay.commerce.model.CommerceOrderType;
import com.liferay.commerce.order.rule.model.COREntry;
import com.liferay.commerce.order.rule.model.COREntryRel;
import com.liferay.commerce.order.rule.service.COREntryLocalService;
import com.liferay.commerce.order.rule.service.COREntryRelLocalService;
import com.liferay.commerce.service.CommerceOrderTypeLocalService;
import com.liferay.headless.commerce.admin.order.client.dto.v1_0.OrderRuleOrderType;
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
public class OrderRuleOrderTypeResourceTest
	extends BaseOrderRuleOrderTypeResourceTestCase {

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
	public void testDeleteOrderRuleOrderType() throws Exception {
	}

	@Override
	@Test
	public void testGraphQLDeleteOrderRuleOrderType() throws Exception {
	}

	@Override
	protected Collection<EntityField> getEntityFields() throws Exception {
		return new ArrayList<>();
	}

	@Override
	protected OrderRuleOrderType randomOrderRuleOrderType() throws Exception {
		DateConfig displayDateConfig = DateConfig.toDisplayDateConfig(
			RandomTestUtil.nextDate(), _user.getTimeZone());
		DateConfig expirationDateConfig = DateConfig.toExpirationDateConfig(
			RandomTestUtil.nextDate(), _user.getTimeZone());

		CommerceOrderType commerceOrderType =
			_commerceOrderTypeLocalService.addCommerceOrderType(
				RandomTestUtil.randomString(), _user.getUserId(),
				RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.randomBoolean(), displayDateConfig.getMonth(),
				displayDateConfig.getDay(), displayDateConfig.getYear(),
				displayDateConfig.getHour(), displayDateConfig.getMinute(), 0,
				expirationDateConfig.getMonth(), expirationDateConfig.getDay(),
				expirationDateConfig.getYear(), expirationDateConfig.getHour(),
				expirationDateConfig.getMinute(), true, _serviceContext);

		return new OrderRuleOrderType() {
			{
				orderRuleExternalReferenceCode =
					_corEntry.getExternalReferenceCode();
				orderRuleId = _corEntry.getCOREntryId();
				orderRuleOrderTypeId = RandomTestUtil.randomLong();
				orderTypeExternalReferenceCode =
					commerceOrderType.getExternalReferenceCode();
				orderTypeId = commerceOrderType.getCommerceOrderTypeId();
			}
		};
	}

	@Override
	protected OrderRuleOrderType
			testGetOrderRuleByExternalReferenceCodeOrderRuleOrderTypesPage_addOrderRuleOrderType(
				String externalReferenceCode,
				OrderRuleOrderType orderRuleOrderType)
		throws Exception {

		return _addOrderRuleOrderType(orderRuleOrderType);
	}

	@Override
	protected String
			testGetOrderRuleByExternalReferenceCodeOrderRuleOrderTypesPage_getExternalReferenceCode()
		throws Exception {

		return _corEntry.getExternalReferenceCode();
	}

	@Override
	protected OrderRuleOrderType
			testGetOrderRuleIdOrderRuleOrderTypesPage_addOrderRuleOrderType(
				Long id, OrderRuleOrderType orderRuleOrderType)
		throws Exception {

		return _addOrderRuleOrderType(orderRuleOrderType);
	}

	@Override
	protected Long testGetOrderRuleIdOrderRuleOrderTypesPage_getId()
		throws Exception {

		return _corEntry.getCOREntryId();
	}

	@Override
	protected OrderRuleOrderType
			testPostOrderRuleByExternalReferenceCodeOrderRuleOrderType_addOrderRuleOrderType(
				OrderRuleOrderType orderRuleOrderType)
		throws Exception {

		return _addOrderRuleOrderType(orderRuleOrderType);
	}

	@Override
	protected OrderRuleOrderType
			testPostOrderRuleIdOrderRuleOrderType_addOrderRuleOrderType(
				OrderRuleOrderType orderRuleOrderType)
		throws Exception {

		return _addOrderRuleOrderType(orderRuleOrderType);
	}

	private OrderRuleOrderType _addOrderRuleOrderType(
			OrderRuleOrderType orderRuleOrderType)
		throws Exception {

		return _toOrderRuleOrderType(
			_corEntryRelLocalService.addCOREntryRel(
				_user.getUserId(), CommerceOrderType.class.getName(),
				orderRuleOrderType.getOrderTypeId(),
				orderRuleOrderType.getOrderRuleId()));
	}

	private OrderRuleOrderType _toOrderRuleOrderType(COREntryRel corEntryRel)
		throws Exception {

		CommerceOrderType commerceOrderType =
			_commerceOrderTypeLocalService.getCommerceOrderType(
				corEntryRel.getClassPK());
		COREntry corEntry = _corEntryLocalService.fetchCOREntry(
			corEntryRel.getCOREntryId());

		return new OrderRuleOrderType() {
			{
				orderRuleExternalReferenceCode =
					corEntry.getExternalReferenceCode();
				orderRuleId = corEntry.getCOREntryId();
				orderRuleOrderTypeId = corEntryRel.getCOREntryRelId();
				orderTypeExternalReferenceCode =
					commerceOrderType.getExternalReferenceCode();
				orderTypeId = commerceOrderType.getCommerceOrderTypeId();
			}
		};
	}

	@Inject
	private CommerceOrderTypeLocalService _commerceOrderTypeLocalService;

	private COREntry _corEntry;

	@Inject
	private COREntryLocalService _corEntryLocalService;

	@Inject
	private COREntryRelLocalService _corEntryRelLocalService;

	private ServiceContext _serviceContext;
	private User _user;

}