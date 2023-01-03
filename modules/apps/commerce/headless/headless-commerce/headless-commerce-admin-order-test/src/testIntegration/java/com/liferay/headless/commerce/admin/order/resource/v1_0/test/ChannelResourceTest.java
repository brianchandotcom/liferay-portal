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
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.constants.CommerceOrderConstants;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.service.CommerceCurrencyLocalService;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderType;
import com.liferay.commerce.model.CommerceOrderTypeRel;
import com.liferay.commerce.order.rule.constants.COREntryConstants;
import com.liferay.commerce.order.rule.model.COREntry;
import com.liferay.commerce.order.rule.model.COREntryRel;
import com.liferay.commerce.order.rule.service.COREntryLocalService;
import com.liferay.commerce.order.rule.service.COREntryRelLocalService;
import com.liferay.commerce.product.constants.CommerceChannelConstants;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.commerce.service.CommerceOrderLocalService;
import com.liferay.commerce.service.CommerceOrderTypeLocalService;
import com.liferay.commerce.service.CommerceOrderTypeRelLocalService;
import com.liferay.headless.commerce.admin.order.client.dto.v1_0.Channel;
import com.liferay.headless.commerce.admin.order.client.serdes.v1_0.ChannelSerDes;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alessio Antonio Rendina
 */
@RunWith(Arquillian.class)
public class ChannelResourceTest extends BaseChannelResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_user = UserTestUtil.addUser(testCompany);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				testCompany.getCompanyId(), testGroup.getGroupId(),
				_user.getUserId());

		_accountEntry = _accountEntryLocalService.addAccountEntry(
			_user.getUserId(), 0, RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), null,
			RandomTestUtil.randomString() + "@liferay.com", null, null,
			"business", 1, serviceContext);

		_commerceChannel = _commerceChannelLocalService.addCommerceChannel(
			RandomTestUtil.randomString(), testGroup.getGroupId(),
			RandomTestUtil.randomString(),
			CommerceChannelConstants.CHANNEL_TYPE_SITE, null,
			RandomTestUtil.randomString(), serviceContext);

		_commerceCurrency = _commerceCurrencyLocalService.addCommerceCurrency(
			_user.getUserId(), RandomTestUtil.randomString(),
			RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomString(), BigDecimal.ONE,
			RandomTestUtil.randomLocaleStringMap(), 2, 2, "HALF_EVEN", false,
			RandomTestUtil.nextDouble(), true);

		_commerceOrder = _commerceOrderLocalService.addCommerceOrder(
			_user.getUserId(), _commerceChannel.getGroupId(),
			_accountEntry.getAccountEntryId(),
			_commerceCurrency.getCommerceCurrencyId(),
			CommerceOrderConstants.TYPE_PK_FULFILLMENT);

		_commerceOrderType =
			_commerceOrderTypeLocalService.addCommerceOrderType(
				RandomTestUtil.randomString(), _user.getUserId(),
				RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.randomLocaleStringMap(), true, 1, 1, 2022, 12, 0,
				RandomTestUtil.nextInt(), 0, 0, 0, 0, 0, true, serviceContext);

		_commerceOrderTypeRel =
			_commerceOrderTypeRelLocalService.addCommerceOrderTypeRel(
				_user.getUserId(), CommerceChannel.class.getName(),
				_commerceChannel.getCommerceChannelId(),
				_commerceOrderType.getCommerceOrderTypeId(), serviceContext);

		_corEntry = _corEntryLocalService.addCOREntry(
			RandomTestUtil.randomString(), _user.getUserId(), true,
			RandomTestUtil.randomString(), 1, 1, 2022, 12, 0, 0, 0, 0, 0, 0,
			true, RandomTestUtil.randomString(), RandomTestUtil.nextInt(),
			COREntryConstants.TYPE_MINIMUM_ORDER_AMOUNT, null, serviceContext);

		_corEntryRel = _corEntryRelLocalService.addCOREntryRel(
			_user.getUserId(), CommerceChannel.class.getName(),
			_commerceChannel.getCommerceChannelId(), _corEntry.getCOREntryId());
	}

	@Override
	@Test
	public void testGetOrderByExternalReferenceCodeChannel() throws Exception {
		Channel postChannel =
			testGetOrderByExternalReferenceCodeChannel_addChannel();

		Channel getChannel =
			channelResource.getOrderByExternalReferenceCodeChannel(
				_commerceOrder.getExternalReferenceCode());

		assertEquals(postChannel, getChannel);
		assertValid(getChannel);
	}

	@Override
	@Test
	public void testGetOrderIdChannel() throws Exception {
		Channel postChannel = testGetOrderIdChannel_addChannel();

		Channel getChannel = channelResource.getOrderIdChannel(
			_commerceOrder.getCommerceOrderId());

		assertEquals(postChannel, getChannel);
		assertValid(getChannel);
	}

	@Override
	@Test
	public void testGraphQLGetOrderByExternalReferenceCodeChannel()
		throws Exception {

		Channel channel =
			testGraphQLGetOrderByExternalReferenceCodeChannel_addChannel();

		String externalReferenceCode =
			"\"" + _commerceOrder.getExternalReferenceCode() + "\"";

		Assert.assertTrue(
			equals(
				channel,
				ChannelSerDes.toDTO(
					JSONUtil.getValueAsString(
						invokeGraphQLQuery(
							new GraphQLField(
								"orderByExternalReferenceCodeChannel",
								HashMapBuilder.<String, Object>put(
									"externalReferenceCode",
									externalReferenceCode
								).build(),
								getGraphQLFields())),
						"JSONObject/data",
						"Object/orderByExternalReferenceCodeChannel"))));
	}

	@Override
	@Test
	public void testGraphQLGetOrderIdChannel() throws Exception {
		Channel channel = testGraphQLGetOrderIdChannel_addChannel();

		Assert.assertTrue(
			equals(
				channel,
				ChannelSerDes.toDTO(
					JSONUtil.getValueAsString(
						invokeGraphQLQuery(
							new GraphQLField(
								"orderIdChannel",
								HashMapBuilder.<String, Object>put(
									"id", _commerceOrder.getCommerceOrderId()
								).build(),
								getGraphQLFields())),
						"JSONObject/data", "Object/orderIdChannel"))));
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"currencyCode", "name"};
	}

	@Override
	protected Channel randomChannel() throws Exception {
		return new Channel() {
			{
				currencyCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				externalReferenceCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				id = RandomTestUtil.randomLong();
				name = StringUtil.toLowerCase(RandomTestUtil.randomString());
				type = CommerceChannelConstants.CHANNEL_TYPE_SITE;
			}
		};
	}

	@Override
	protected Channel testGetOrderByExternalReferenceCodeChannel_addChannel()
		throws Exception {

		return _toChannel();
	}

	@Override
	protected Channel testGetOrderIdChannel_addChannel() throws Exception {
		return _toChannel();
	}

	@Override
	protected Channel testGetOrderRuleChannelChannel_addChannel()
		throws Exception {

		return _toChannel();
	}

	@Override
	protected Long testGetOrderRuleChannelChannel_getOrderRuleChannelId()
		throws Exception {

		return _corEntryRel.getCOREntryRelId();
	}

	@Override
	protected Channel testGetOrderTypeChannelChannel_addChannel()
		throws Exception {

		return _toChannel();
	}

	@Override
	protected Long testGetOrderTypeChannelChannel_getOrderTypeChannelId()
		throws Exception {

		return _commerceOrderTypeRel.getCommerceOrderTypeRelId();
	}

	@Override
	protected Channel testGraphQLChannel_addChannel() throws Exception {
		return _toChannel();
	}

	@Override
	protected Long testGraphQLGetOrderRuleChannelChannel_getOrderRuleChannelId()
		throws Exception {

		return _corEntryRel.getCOREntryRelId();
	}

	@Override
	protected Long testGraphQLGetOrderTypeChannelChannel_getOrderTypeChannelId()
		throws Exception {

		return _commerceOrderTypeRel.getCommerceOrderTypeRelId();
	}

	private Channel _toChannel() throws Exception {
		return new Channel() {
			{
				currencyCode = _commerceChannel.getCommerceCurrencyCode();
				externalReferenceCode =
					_commerceChannel.getExternalReferenceCode();
				id = _commerceChannel.getCommerceChannelId();
				name = _commerceChannel.getName();
				type = _commerceChannel.getType();
			}
		};
	}

	@Inject
	private static AccountEntryLocalService _accountEntryLocalService;

	@Inject
	private static CommerceChannelLocalService _commerceChannelLocalService;

	@Inject
	private static CommerceCurrencyLocalService _commerceCurrencyLocalService;

	@Inject
	private static CommerceOrderLocalService _commerceOrderLocalService;

	@Inject
	private static CommerceOrderTypeLocalService _commerceOrderTypeLocalService;

	@Inject
	private static CommerceOrderTypeRelLocalService
		_commerceOrderTypeRelLocalService;

	@Inject
	private static COREntryLocalService _corEntryLocalService;

	@Inject
	private static COREntryRelLocalService _corEntryRelLocalService;

	@DeleteAfterTestRun
	private AccountEntry _accountEntry;

	@DeleteAfterTestRun
	private CommerceChannel _commerceChannel;

	@DeleteAfterTestRun
	private CommerceCurrency _commerceCurrency;

	@DeleteAfterTestRun
	private CommerceOrder _commerceOrder;

	@DeleteAfterTestRun
	private CommerceOrderType _commerceOrderType;

	@DeleteAfterTestRun
	private CommerceOrderTypeRel _commerceOrderTypeRel;

	@DeleteAfterTestRun
	private COREntry _corEntry;

	@DeleteAfterTestRun
	private COREntryRel _corEntryRel;

	@DeleteAfterTestRun
	private User _user;

}