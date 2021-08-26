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
import com.liferay.commerce.model.CommerceOrderTypeRel;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.commerce.service.CommerceOrderTypeLocalService;
import com.liferay.commerce.service.CommerceOrderTypeRelLocalService;
import com.liferay.commerce.test.util.CommerceTestUtil;
import com.liferay.headless.commerce.admin.order.client.dto.v1_0.OrderTypeChannel;
import com.liferay.headless.commerce.core.util.DateConfig;
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
public class OrderTypeChannelResourceTest
	extends BaseOrderTypeChannelResourceTestCase {

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

		_commerceOrderType =
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
	}

	@Override
	@Test
	public void testDeleteOrderTypeChannel() throws Exception {
	}

	@Override
	@Test
	public void testGraphQLDeleteOrderTypeChannel() throws Exception {
	}

	@Override
	protected Collection<EntityField> getEntityFields() throws Exception {
		return new ArrayList<>();
	}

	@Override
	protected OrderTypeChannel randomOrderTypeChannel() throws Exception {
		CommerceChannel commerceChannel = CommerceTestUtil.addCommerceChannel(
			RandomTestUtil.randomString());

		return new OrderTypeChannel() {
			{
				channelExternalReferenceCode =
					commerceChannel.getExternalReferenceCode();
				channelId = commerceChannel.getCommerceChannelId();
				orderTypeChannelId = RandomTestUtil.randomLong();
				orderTypeExternalReferenceCode =
					_commerceOrderType.getExternalReferenceCode();
				orderTypeId = _commerceOrderType.getCommerceOrderTypeId();
			}
		};
	}

	@Override
	protected OrderTypeChannel
			testGetOrderTypeByExternalReferenceCodeOrderTypeChannelsPage_addOrderTypeChannel(
				String externalReferenceCode, OrderTypeChannel orderTypeChannel)
		throws Exception {

		return _addOrderTypeChannel(orderTypeChannel);
	}

	@Override
	protected String
			testGetOrderTypeByExternalReferenceCodeOrderTypeChannelsPage_getExternalReferenceCode()
		throws Exception {

		return _commerceOrderType.getExternalReferenceCode();
	}

	@Override
	protected OrderTypeChannel
			testGetOrderTypeIdOrderTypeChannelsPage_addOrderTypeChannel(
				Long id, OrderTypeChannel orderTypeChannel)
		throws Exception {

		return _addOrderTypeChannel(orderTypeChannel);
	}

	@Override
	protected Long testGetOrderTypeIdOrderTypeChannelsPage_getId()
		throws Exception {

		return _commerceOrderType.getCommerceOrderTypeId();
	}

	@Override
	protected OrderTypeChannel
			testPostOrderTypeByExternalReferenceCodeOrderTypeChannel_addOrderTypeChannel(
				OrderTypeChannel orderTypeChannel)
		throws Exception {

		return _addOrderTypeChannel(orderTypeChannel);
	}

	@Override
	protected OrderTypeChannel
			testPostOrderTypeIdOrderTypeChannel_addOrderTypeChannel(
				OrderTypeChannel orderTypeChannel)
		throws Exception {

		return _addOrderTypeChannel(orderTypeChannel);
	}

	private OrderTypeChannel _addOrderTypeChannel(
			OrderTypeChannel orderTypeChannel)
		throws Exception {

		return _toOrderTypeChannel(
			_commerceOrderTypeRelLocalService.addCommerceOrderTypeRel(
				_user.getUserId(), CommerceChannel.class.getName(),
				orderTypeChannel.getChannelId(),
				orderTypeChannel.getOrderTypeId(), _serviceContext));
	}

	private OrderTypeChannel _toOrderTypeChannel(
			CommerceOrderTypeRel commerceOrderTypeRel)
		throws Exception {

		CommerceChannel commerceChannel =
			_commerceChannelLocalService.getCommerceChannel(
				commerceOrderTypeRel.getClassPK());
		CommerceOrderType commerceOrderType =
			_commerceOrderTypeLocalService.fetchCommerceOrderType(
				commerceOrderTypeRel.getCommerceOrderTypeId());

		return new OrderTypeChannel() {
			{
				channelExternalReferenceCode =
					commerceChannel.getExternalReferenceCode();
				channelId = commerceChannel.getCommerceChannelId();
				orderTypeChannelId =
					commerceOrderTypeRel.getCommerceOrderTypeRelId();
				orderTypeExternalReferenceCode =
					commerceOrderType.getExternalReferenceCode();
				orderTypeId = commerceOrderType.getCommerceOrderTypeId();
			}
		};
	}

	@Inject
	private CommerceChannelLocalService _commerceChannelLocalService;

	private CommerceOrderType _commerceOrderType;

	@Inject
	private CommerceOrderTypeLocalService _commerceOrderTypeLocalService;

	@Inject
	private CommerceOrderTypeRelLocalService _commerceOrderTypeRelLocalService;

	private ServiceContext _serviceContext;
	private User _user;

}