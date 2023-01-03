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

		_commerceOrderType =
			_commerceOrderTypeLocalService.addCommerceOrderType(
				RandomTestUtil.randomString(), _user.getUserId(),
				RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.randomBoolean(), 1, 1, 2022, 12, 0, 0, 0, 0, 0,
				0, 0, true, _serviceContext);
	}

	@Override
	@Test
	public void testDeleteOrderTypeChannel() throws Exception {
		OrderTypeChannel orderTypeChannel = _addCommerceOrderTypeRel(
			randomOrderTypeChannel());

		assertHttpResponseStatusCode(
			204,
			orderTypeChannelResource.deleteOrderTypeChannelHttpResponse(
				orderTypeChannel.getOrderTypeChannelId()));

		assertHttpResponseStatusCode(
			404,
			orderTypeChannelResource.deleteOrderTypeChannelHttpResponse(
				orderTypeChannel.getOrderTypeChannelId()));

		assertHttpResponseStatusCode(
			404,
			orderTypeChannelResource.deleteOrderTypeChannelHttpResponse(
				orderTypeChannel.getOrderTypeChannelId()));
	}

	@Override
	@Test
	public void testGraphQLDeleteOrderTypeChannel() throws Exception {
		OrderTypeChannel orderTypeChannel = _addCommerceOrderTypeRel(
			randomOrderTypeChannel());

		Assert.assertTrue(
			JSONUtil.getValueAsBoolean(
				invokeGraphQLMutation(
					new GraphQLField(
						"deleteOrderTypeChannel",
						HashMapBuilder.<String, Object>put(
							"orderTypeChannelId",
							orderTypeChannel.getOrderTypeChannelId()
						).build())),
				"JSONObject/data", "Object/deleteOrderTypeChannel"));

		JSONArray errorsJSONArray = JSONUtil.getValueAsJSONArray(
			invokeGraphQLQuery(
				new GraphQLField(
					"deleteOrderTypeChannel",
					HashMapBuilder.<String, Object>put(
						"orderTypeChannelId",
						orderTypeChannel.getOrderTypeChannelId()
					).build(),
					new GraphQLField("orderTypeChannelId"))),
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
	protected OrderTypeChannel randomOrderTypeChannel() throws Exception {
		CommerceChannel commerceChannel = CommerceTestUtil.addCommerceChannel(
			RandomTestUtil.randomString());

		_commerceChannels.add(commerceChannel);

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

		return _addCommerceOrderTypeRel(orderTypeChannel);
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

		return _addCommerceOrderTypeRel(orderTypeChannel);
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

		return _addCommerceOrderTypeRel(orderTypeChannel);
	}

	@Override
	protected OrderTypeChannel
			testPostOrderTypeIdOrderTypeChannel_addOrderTypeChannel(
				OrderTypeChannel orderTypeChannel)
		throws Exception {

		return _addCommerceOrderTypeRel(orderTypeChannel);
	}

	private OrderTypeChannel _addCommerceOrderTypeRel(
			OrderTypeChannel orderTypeChannel)
		throws Exception {

		CommerceChannel commerceChannel =
			_commerceChannelLocalService.getCommerceChannel(
				orderTypeChannel.getChannelId());

		CommerceOrderTypeRel commerceOrderTypeRel =
			_commerceOrderTypeRelLocalService.addCommerceOrderTypeRel(
				_user.getUserId(), CommerceChannel.class.getName(),
				orderTypeChannel.getChannelId(),
				orderTypeChannel.getOrderTypeId(), _serviceContext);

		return new OrderTypeChannel() {
			{
				channelExternalReferenceCode =
					commerceChannel.getExternalReferenceCode();
				channelId = commerceChannel.getCommerceChannelId();
				orderTypeChannelId =
					commerceOrderTypeRel.getCommerceOrderTypeRelId();
				orderTypeExternalReferenceCode =
					_commerceOrderType.getExternalReferenceCode();
				orderTypeId = _commerceOrderType.getCommerceOrderTypeId();
			}
		};
	}

	@Inject
	private static CommerceChannelLocalService _commerceChannelLocalService;

	@Inject
	private static CommerceOrderTypeLocalService _commerceOrderTypeLocalService;

	@Inject
	private static CommerceOrderTypeRelLocalService
		_commerceOrderTypeRelLocalService;

	@DeleteAfterTestRun
	private final List<CommerceChannel> _commerceChannels = new ArrayList<>();

	@DeleteAfterTestRun
	private CommerceOrderType _commerceOrderType;

	private ServiceContext _serviceContext;

	@DeleteAfterTestRun
	private User _user;

}