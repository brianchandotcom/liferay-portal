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

package com.liferay.headless.commerce.admin.channel.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CommerceChannelLocalServiceUtil;
import com.liferay.headless.commerce.admin.channel.client.dto.v1_0.Channel;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;

/**
 * @author Andrea Sbarra
 */
@RunWith(Arquillian.class)
public class ChannelResourceTest extends BaseChannelResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_user = UserTestUtil.addUser(testCompany);

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			testCompany.getCompanyId(), testGroup.getGroupId(),
			_user.getUserId());

		Iterator<CommerceChannel> iterator = _commerceChannels.iterator();

		while (iterator.hasNext()) {
			CommerceChannel commerceChannel1 = iterator.next();

			CommerceChannel commerceChannel2 =
				CommerceChannelLocalServiceUtil.fetchCommerceChannel(
					commerceChannel1.getCommerceChannelId());

			if (commerceChannel2 != null) {
				CommerceChannelLocalServiceUtil.deleteCommerceChannel(
					commerceChannel2.getCommerceChannelId());
			}

			iterator.remove();
		}
	}

	@After
	@Override
	public void tearDown() throws Exception {
		super.tearDown();

		Iterator<CommerceChannel> iterator = _commerceChannels.iterator();

		while (iterator.hasNext()) {
			CommerceChannel commerceChannel1 = iterator.next();

			CommerceChannel commerceChannel2 =
				CommerceChannelLocalServiceUtil.fetchCommerceChannel(
					commerceChannel1.getCommerceChannelId());

			if (commerceChannel2 != null) {
				CommerceChannelLocalServiceUtil.deleteCommerceChannel(
					commerceChannel2.getCommerceChannelId());
			}

			iterator.remove();
		}
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"currencyCode", "name", "type"};
	}

	@Override
	protected Channel randomPatchChannel() throws Exception {
		return new Channel() {
			{
				currencyCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				externalReferenceCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				id = RandomTestUtil.randomLong();
				name = StringUtil.toLowerCase(RandomTestUtil.randomString());
				type = StringUtil.toLowerCase(RandomTestUtil.randomString());
			}
		};
	}

	@Override
	protected Channel testDeleteChannel_addChannel() throws Exception {
		return _addChannel(randomChannel());
	}

	@Override
	protected Channel testDeleteChannelByExternalReferenceCode_addChannel()
		throws Exception {

		return _addChannel(randomChannel());
	}

	@Override
	protected Channel testGetChannel_addChannel() throws Exception {
		return _addChannel(randomChannel());
	}

	@Override
	protected Channel testGetChannelByExternalReferenceCode_addChannel()
		throws Exception {

		return _addChannel(randomChannel());
	}

	@Override
	protected Channel testGetChannelsPage_addChannel(Channel channel)
		throws Exception {

		return _addChannel(channel);
	}

	@Override
	protected Channel testGraphQLChannel_addChannel() throws Exception {
		return _addChannel(randomChannel());
	}

	@Override
	protected Channel testPatchChannel_addChannel() throws Exception {
		return _addChannel(randomChannel());
	}

	@Override
	protected Channel testPatchChannelByExternalReferenceCode_addChannel()
		throws Exception {

		return _addChannel(randomChannel());
	}

	@Override
	protected Channel testPostChannel_addChannel(Channel channel)
		throws Exception {

		return _addChannel(channel);
	}

	@Override
	protected Channel testPutChannel_addChannel() throws Exception {
		return _addChannel(randomChannel());
	}

	@Override
	protected Channel testPutChannelByExternalReferenceCode_addChannel()
		throws Exception {

		return _addChannel(randomChannel());
	}

	private Channel _addChannel(Channel channel) throws Exception {
		CommerceChannel commerceChannel =
			CommerceChannelLocalServiceUtil.addCommerceChannel(
				channel.getExternalReferenceCode(), channel.getSiteGroupId(),
				channel.getName(), channel.getType(), null,
				channel.getCurrencyCode(), _serviceContext);

		_commerceChannels.add(commerceChannel);

		return _toChannel(commerceChannel);
	}

	private Channel _toChannel(CommerceChannel commerceChannel) {
		return new Channel() {
			{
				currencyCode = commerceChannel.getCommerceCurrencyCode();
				externalReferenceCode =
					commerceChannel.getExternalReferenceCode();
				id = commerceChannel.getCommerceChannelId();
				name = commerceChannel.getName();
				siteGroupId = commerceChannel.getSiteGroupId();
				type = commerceChannel.getType();
			}
		};
	}

	private final List<CommerceChannel> _commerceChannels = new ArrayList<>();
	private ServiceContext _serviceContext;
	private User _user;

}