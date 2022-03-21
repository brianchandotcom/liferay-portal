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
import com.liferay.commerce.term.model.CommerceTermEntry;
import com.liferay.commerce.term.model.CommerceTermEntryRel;
import com.liferay.commerce.term.service.CommerceTermEntryLocalService;
import com.liferay.commerce.term.service.CommerceTermEntryRelLocalService;
import com.liferay.headless.commerce.admin.order.client.dto.v1_0.OrderType;
import com.liferay.headless.commerce.core.util.DateConfig;
import com.liferay.headless.commerce.core.util.LanguageUtils;
import com.liferay.petra.string.StringPool;
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
public class OrderTypeResourceTest extends BaseOrderTypeResourceTestCase {

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

		Iterator<CommerceOrderType> iterator = _commerceOrderTypes.iterator();

		while (iterator.hasNext()) {
			CommerceOrderType commerceOrderType1 = iterator.next();

			CommerceOrderType commerceOrderType2 =
				_commerceOrderTypeLocalService.fetchCommerceOrderType(
					commerceOrderType1.getCommerceOrderTypeId());

			if (commerceOrderType2 != null) {
				_commerceOrderTypeLocalService.deleteCommerceOrderType(
					commerceOrderType2.getCommerceOrderTypeId());
			}

			iterator.remove();
		}
	}

	@Override
	@Test
	public void testGetTermOrderTypeOrderType() throws Exception {
		OrderType postOrderType = _addOrderType(randomOrderType());

		CommerceTermEntryRel commerceTermEntryRel = _getCommerceTermEntryRel(
			postOrderType);

		OrderType getOrderType = orderTypeResource.getTermOrderTypeOrderType(
			commerceTermEntryRel.getCommerceTermEntryRelId());

		assertEquals(postOrderType, getOrderType);
		assertValid(getOrderType);
	}

	@Override
	@Test
	public void testGraphQLGetOrderTypeNotFound() throws Exception {
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"active", "name"};
	}

	@Override
	protected OrderType randomOrderType() {
		return new OrderType() {
			{
				active = RandomTestUtil.randomBoolean();
				description = LanguageUtils.getLanguageIdMap(
					RandomTestUtil.randomLocaleStringMap());
				displayDate = RandomTestUtil.nextDate();
				displayOrder = RandomTestUtil.nextInt();
				expirationDate = RandomTestUtil.nextDate();
				externalReferenceCode = RandomTestUtil.randomString();
				id = RandomTestUtil.nextLong();
				name = LanguageUtils.getLanguageIdMap(
					RandomTestUtil.randomLocaleStringMap());
			}
		};
	}

	@Override
	protected OrderType randomPatchOrderType() throws Exception {
		return randomOrderType();
	}

	@Override
	protected OrderType testDeleteOrderType_addOrderType() throws Exception {
		return _addOrderType(randomOrderType());
	}

	@Override
	protected OrderType
			testDeleteOrderTypeByExternalReferenceCode_addOrderType()
		throws Exception {

		return _addOrderType(randomOrderType());
	}

	@Override
	protected OrderType testGetOrderRuleOrderTypeOrderType_addOrderType()
		throws Exception {

		OrderType orderType = _addOrderType(randomOrderType());

		_corEntryRel = _getCOREntryRel(orderType);

		return orderType;
	}

	@Override
	protected Long testGetOrderRuleOrderTypeOrderType_getOrderRuleOrderTypeId()
		throws Exception {

		return _corEntryRel.getCOREntryRelId();
	}

	@Override
	protected OrderType testGetOrderType_addOrderType() throws Exception {
		return _addOrderType(randomOrderType());
	}

	@Override
	protected OrderType testGetOrderTypeByExternalReferenceCode_addOrderType()
		throws Exception {

		return _addOrderType(randomOrderType());
	}

	@Override
	protected OrderType testGetOrderTypesPage_addOrderType(OrderType orderType)
		throws Exception {

		return _addOrderType(orderType);
	}

	@Override
	protected Long
			testGraphQLGetOrderRuleOrderTypeOrderType_getOrderRuleOrderTypeId()
		throws Exception {

		return _corEntryRel.getCOREntryRelId();
	}

	@Override
	protected Long testGraphQLGetTermOrderTypeOrderType_getTermOrderTypeId()
		throws Exception {

		return _commerceTermEntryRel.getCommerceTermEntryRelId();
	}

	@Override
	protected OrderType testGraphQLOrderType_addOrderType() throws Exception {
		OrderType orderType = _addOrderType(randomOrderType());

		_commerceTermEntryRel = _getCommerceTermEntryRel(orderType);
		_corEntryRel = _getCOREntryRel(orderType);

		return orderType;
	}

	@Override
	protected OrderType testPatchOrderType_addOrderType() throws Exception {
		return _addOrderType(randomOrderType());
	}

	@Override
	protected OrderType testPatchOrderTypeByExternalReferenceCode_addOrderType()
		throws Exception {

		return _addOrderType(randomOrderType());
	}

	@Override
	protected OrderType testPostOrderType_addOrderType(OrderType orderType)
		throws Exception {

		return _addOrderType(orderType);
	}

	private OrderType _addOrderType(OrderType orderType) throws Exception {
		DateConfig displayDateConfig = DateConfig.toDisplayDateConfig(
			orderType.getDisplayDate(), _user.getTimeZone());
		DateConfig expirationDateConfig = DateConfig.toExpirationDateConfig(
			orderType.getExpirationDate(), _user.getTimeZone());

		CommerceOrderType commerceOrderType =
			_commerceOrderTypeLocalService.addCommerceOrderType(
				orderType.getExternalReferenceCode(), _user.getUserId(),
				LanguageUtils.getLocalizedMap(orderType.getName()),
				LanguageUtils.getLocalizedMap(orderType.getDescription()),
				GetterUtil.getBoolean(orderType.getActive()),
				displayDateConfig.getMonth(), displayDateConfig.getDay(),
				displayDateConfig.getYear(), displayDateConfig.getHour(),
				displayDateConfig.getMinute(),
				GetterUtil.getInteger(orderType.getDisplayOrder()),
				expirationDateConfig.getMonth(), expirationDateConfig.getDay(),
				expirationDateConfig.getYear(), expirationDateConfig.getHour(),
				expirationDateConfig.getMinute(),
				GetterUtil.getBoolean(orderType.getNeverExpire(), true),
				_serviceContext);

		_commerceOrderTypes.add(commerceOrderType);

		return _toOrderType(commerceOrderType);
	}

	private CommerceTermEntryRel _getCommerceTermEntryRel(OrderType orderType)
		throws Exception {

		DateConfig displayDateConfig = DateConfig.toDisplayDateConfig(
			RandomTestUtil.nextDate(), _user.getTimeZone());
		DateConfig expirationDateConfig = DateConfig.toExpirationDateConfig(
			RandomTestUtil.nextDate(), _user.getTimeZone());

		CommerceTermEntry commerceTermEntry =
			_commerceTermEntryLocalService.addCommerceTermEntry(
				RandomTestUtil.randomString(), _user.getUserId(),
				RandomTestUtil.randomBoolean(),
				RandomTestUtil.randomLocaleStringMap(),
				displayDateConfig.getMonth(), displayDateConfig.getDay(),
				displayDateConfig.getYear(), displayDateConfig.getHour(),
				displayDateConfig.getMinute(), expirationDateConfig.getMonth(),
				expirationDateConfig.getDay(), expirationDateConfig.getYear(),
				expirationDateConfig.getHour(),
				expirationDateConfig.getMinute(), true,
				RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.randomString(), RandomTestUtil.nextDouble(),
				RandomTestUtil.randomString(), StringPool.BLANK,
				_serviceContext);

		return _commerceTermEntryRelLocalService.addCommerceTermEntryRel(
			_user.getUserId(), CommerceOrderType.class.getName(),
			orderType.getId(), commerceTermEntry.getCommerceTermEntryId());
	}

	private COREntryRel _getCOREntryRel(OrderType orderType) throws Exception {
		DateConfig displayDateConfig = DateConfig.toDisplayDateConfig(
			RandomTestUtil.nextDate(), _user.getTimeZone());
		DateConfig expirationDateConfig = DateConfig.toExpirationDateConfig(
			RandomTestUtil.nextDate(), _user.getTimeZone());

		COREntry corEntry = _corEntryLocalService.addCOREntry(
			RandomTestUtil.randomString(), _user.getUserId(),
			RandomTestUtil.randomBoolean(), RandomTestUtil.randomString(),
			displayDateConfig.getMonth(), displayDateConfig.getDay(),
			displayDateConfig.getYear(), displayDateConfig.getHour(),
			displayDateConfig.getMinute(), expirationDateConfig.getMonth(),
			expirationDateConfig.getDay(), expirationDateConfig.getYear(),
			expirationDateConfig.getHour(), expirationDateConfig.getMinute(),
			true, RandomTestUtil.randomString(), 0,
			RandomTestUtil.randomString(), StringPool.BLANK, _serviceContext);

		return _corEntryRelLocalService.addCOREntryRel(
			_user.getUserId(), CommerceOrderType.class.getName(),
			orderType.getId(), corEntry.getCOREntryId());
	}

	private OrderType _toOrderType(CommerceOrderType commerceOrderType) {
		return new OrderType() {
			{
				active = commerceOrderType.isActive();
				description = LanguageUtils.getLanguageIdMap(
					commerceOrderType.getDescriptionMap());
				displayDate = commerceOrderType.getDisplayDate();
				displayOrder = commerceOrderType.getDisplayOrder();
				expirationDate = commerceOrderType.getExpirationDate();
				externalReferenceCode =
					commerceOrderType.getExternalReferenceCode();
				id = commerceOrderType.getCommerceOrderTypeId();
				name = LanguageUtils.getLanguageIdMap(
					commerceOrderType.getNameMap());
			}
		};
	}

	@Inject
	private CommerceOrderTypeLocalService _commerceOrderTypeLocalService;

	private final List<CommerceOrderType> _commerceOrderTypes =
		new ArrayList<>();

	@Inject
	private CommerceTermEntryLocalService _commerceTermEntryLocalService;

	private CommerceTermEntryRel _commerceTermEntryRel;

	@Inject
	private CommerceTermEntryRelLocalService _commerceTermEntryRelLocalService;

	@Inject
	private COREntryLocalService _corEntryLocalService;

	private COREntryRel _corEntryRel;

	@Inject
	private COREntryRelLocalService _corEntryRelLocalService;

	private ServiceContext _serviceContext;
	private User _user;

}