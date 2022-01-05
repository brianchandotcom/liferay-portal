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
import com.liferay.commerce.model.CommerceOrderType;
import com.liferay.commerce.price.list.constants.CommercePriceListConstants;
import com.liferay.commerce.price.list.model.CommercePriceList;
import com.liferay.commerce.price.list.service.CommercePriceListOrderTypeRelLocalService;
import com.liferay.commerce.price.list.test.util.CommercePriceListTestUtil;
import com.liferay.commerce.service.CommerceOrderTypeLocalService;
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

import java.util.Calendar;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * @author Cheryl Tang
 */
@RunWith(Arquillian.class)
public class CommercePriceListOrderTypeRelTableReferenceDefinitionTest
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

		_commerceOrderType =
			_commerceOrderTypeLocalService.addCommerceOrderType(
				RandomTestUtil.randomString(), _user.getUserId(),
				RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.randomLocaleStringMap(), true,
				calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DAY_OF_MONTH),
				calendar.get(Calendar.YEAR), calendar.get(Calendar.HOUR_OF_DAY),
				calendar.get(Calendar.MINUTE), 0, calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DAY_OF_MONTH),
				calendar.get(Calendar.YEAR), calendar.get(Calendar.HOUR_OF_DAY),
				calendar.get(Calendar.MINUTE), true,
				ServiceContextTestUtil.getServiceContext(group.getGroupId()));
	}

	@Override
	protected CTModel<?> addCTModel() throws Exception {
		return _commercePriceListOrderTypeRelLocalService.
			addCommercePriceListOrderTypeRel(
				_user.getUserId(), _commercePriceList.getCommercePriceListId(),
				_commerceOrderType.getCommerceOrderTypeId(), 0,
				ServiceContextTestUtil.getServiceContext(group.getGroupId()));
	}

	@Inject
	private static CommerceOrderTypeLocalService _commerceOrderTypeLocalService;

	@Inject
	private static CommercePriceListOrderTypeRelLocalService
		_commercePriceListOrderTypeRelLocalService;

	private CommerceOrderType _commerceOrderType;
	private CommercePriceList _commercePriceList;
	private User _user;

}