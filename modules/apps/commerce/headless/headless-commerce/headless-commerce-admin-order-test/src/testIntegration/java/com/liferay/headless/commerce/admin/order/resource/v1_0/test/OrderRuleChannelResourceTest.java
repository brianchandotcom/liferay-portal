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
import com.liferay.commerce.order.rule.model.COREntryRel;
import com.liferay.commerce.order.rule.service.COREntryLocalService;
import com.liferay.commerce.order.rule.service.COREntryRelLocalService;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.commerce.test.util.CommerceTestUtil;
import com.liferay.headless.commerce.admin.order.client.dto.v1_0.OrderRuleChannel;
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
public class OrderRuleChannelResourceTest
	extends BaseOrderRuleChannelResourceTestCase {

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
	public void testDeleteOrderRuleChannel() throws Exception {
	}

	@Override
	@Test
	public void testGraphQLDeleteOrderRuleChannel() throws Exception {
	}

	@Override
	protected Collection<EntityField> getEntityFields() throws Exception {
		return new ArrayList<>();
	}

	@Override
	protected OrderRuleChannel randomOrderRuleChannel() throws Exception {
		CommerceChannel commerceChannel = CommerceTestUtil.addCommerceChannel(
			RandomTestUtil.randomString());

		return new OrderRuleChannel() {
			{
				channelExternalReferenceCode =
					commerceChannel.getExternalReferenceCode();
				channelId = commerceChannel.getCommerceChannelId();
				orderRuleChannelId = RandomTestUtil.randomLong();
				orderRuleExternalReferenceCode =
					_corEntry.getExternalReferenceCode();
				orderRuleId = _corEntry.getCOREntryId();
			}
		};
	}

	@Override
	protected OrderRuleChannel
			testGetOrderRuleByExternalReferenceCodeOrderRuleChannelsPage_addOrderRuleChannel(
				String externalReferenceCode, OrderRuleChannel orderRuleChannel)
		throws Exception {

		return _addOrderRuleChannel(orderRuleChannel);
	}

	@Override
	protected String
			testGetOrderRuleByExternalReferenceCodeOrderRuleChannelsPage_getExternalReferenceCode()
		throws Exception {

		return _corEntry.getExternalReferenceCode();
	}

	@Override
	protected OrderRuleChannel
			testGetOrderRuleIdOrderRuleChannelsPage_addOrderRuleChannel(
				Long id, OrderRuleChannel orderRuleChannel)
		throws Exception {

		return _addOrderRuleChannel(orderRuleChannel);
	}

	@Override
	protected Long testGetOrderRuleIdOrderRuleChannelsPage_getId()
		throws Exception {

		return _corEntry.getCOREntryId();
	}

	@Override
	protected OrderRuleChannel
			testPostOrderRuleByExternalReferenceCodeOrderRuleChannel_addOrderRuleChannel(
				OrderRuleChannel orderRuleChannel)
		throws Exception {

		return _addOrderRuleChannel(orderRuleChannel);
	}

	@Override
	protected OrderRuleChannel
			testPostOrderRuleIdOrderRuleChannel_addOrderRuleChannel(
				OrderRuleChannel orderRuleChannel)
		throws Exception {

		return _addOrderRuleChannel(orderRuleChannel);
	}

	private OrderRuleChannel _addOrderRuleChannel(
			OrderRuleChannel orderRuleChannel)
		throws Exception {

		return _toOrderRuleChannel(
			_corEntryRelLocalService.addCOREntryRel(
				_user.getUserId(), CommerceChannel.class.getName(),
				orderRuleChannel.getChannelId(),
				orderRuleChannel.getOrderRuleId()));
	}

	private OrderRuleChannel _toOrderRuleChannel(COREntryRel corEntryRel)
		throws Exception {

		CommerceChannel commerceChannel =
			_commerceChannelLocalService.getCommerceChannel(
				corEntryRel.getClassPK());
		COREntry corEntry = _corEntryLocalService.fetchCOREntry(
			corEntryRel.getCOREntryId());

		return new OrderRuleChannel() {
			{
				channelExternalReferenceCode =
					commerceChannel.getExternalReferenceCode();
				channelId = commerceChannel.getCommerceChannelId();
				orderRuleChannelId = corEntryRel.getCOREntryRelId();
				orderRuleExternalReferenceCode =
					corEntry.getExternalReferenceCode();
				orderRuleId = corEntry.getCOREntryId();
			}
		};
	}

	@Inject
	private CommerceChannelLocalService _commerceChannelLocalService;

	private COREntry _corEntry;

	@Inject
	private COREntryLocalService _corEntryLocalService;

	@Inject
	private COREntryRelLocalService _corEntryRelLocalService;

	private ServiceContext _serviceContext;
	private User _user;

}