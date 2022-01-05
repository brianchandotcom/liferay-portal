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

package com.liferay.commerce.price.list.change.tracking.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.change.tracking.test.util.BaseTableReferenceDefinitionTestCase;
import com.liferay.commerce.discount.constants.CommerceDiscountConstants;
import com.liferay.commerce.discount.model.CommerceDiscount;
import com.liferay.commerce.discount.service.CommerceDiscountLocalService;
import com.liferay.commerce.price.list.constants.CommercePriceListConstants;
import com.liferay.commerce.price.list.model.CommercePriceList;
import com.liferay.commerce.price.list.service.CommercePriceListDiscountRelLocalService;
import com.liferay.commerce.price.list.test.util.CommercePriceListTestUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.change.tracking.CTModel;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.math.BigDecimal;

import java.util.Calendar;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * @author Cheryl Tang
 */
@RunWith(Arquillian.class)
public class CommercePriceListDiscountRelTableReferenceDefinitionTest
	extends BaseTableReferenceDefinitionTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_commercePriceList = CommercePriceListTestUtil.addCommercePriceList(
			group.getGroupId(), false,
			CommercePriceListConstants.TYPE_PRICE_LIST, 1.0);

		_user = TestPropsValues.getUser();

		Calendar calendar = CalendarFactoryUtil.getCalendar(
			_user.getTimeZone());

		_commerceDiscount = _commerceDiscountLocalService.addCommerceDiscount(
			_user.getUserId(), RandomTestUtil.randomString(),
			CommerceDiscountConstants.TARGET_PRODUCTS, false, null, false,
			BigDecimal.ZERO, BigDecimal.valueOf(RandomTestUtil.randomDouble()),
			BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO,
			CommerceDiscountConstants.LIMITATION_TYPE_UNLIMITED, 0, true,
			calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),
			calendar.get(Calendar.YEAR), calendar.get(Calendar.HOUR_OF_DAY),
			calendar.get(Calendar.MINUTE), calendar.get(Calendar.MONTH),
			calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.YEAR),
			calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE),
			true, ServiceContextTestUtil.getServiceContext(group.getGroupId()));
	}

	@Override
	protected CTModel<?> addCTModel() throws Exception {
		return _commercePriceListDiscountRelLocalService.
			addCommercePriceListDiscountRel(
				_user.getUserId(), _commercePriceList.getCommercePriceListId(),
				_commerceDiscount.getCommerceDiscountId(), 0,
				ServiceContextTestUtil.getServiceContext(group.getGroupId()));
	}

	@Inject
	private static CommerceDiscountLocalService _commerceDiscountLocalService;

	@Inject
	private static CommercePriceListDiscountRelLocalService
		_commercePriceListDiscountRelLocalService;

	private CommerceDiscount _commerceDiscount;
	private CommercePriceList _commercePriceList;
	private User _user;

}