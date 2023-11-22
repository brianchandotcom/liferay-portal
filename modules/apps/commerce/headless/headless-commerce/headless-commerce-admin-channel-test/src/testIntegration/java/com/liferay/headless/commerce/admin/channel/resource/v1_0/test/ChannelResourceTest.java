/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.admin.channel.resource.v1_0.test;

import com.liferay.account.constants.AccountConstants;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.product.constants.CommerceChannelConstants;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.model.CommerceChannelRel;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.commerce.product.service.CommerceChannelRelLocalService;
import com.liferay.headless.commerce.admin.channel.client.dto.v1_0.Channel;
import com.liferay.portal.kernel.model.Address;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.AddressLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
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
				_commerceChannelLocalService.fetchCommerceChannel(
					commerceChannel1.getCommerceChannelId());

			if (commerceChannel2 != null) {
				_commerceChannelLocalService.deleteCommerceChannel(
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
				_commerceChannelLocalService.fetchCommerceChannel(
					commerceChannel1.getCommerceChannelId());

			if (commerceChannel2 != null) {
				_commerceChannelLocalService.deleteCommerceChannel(
					commerceChannel2.getCommerceChannelId());
			}

			iterator.remove();
		}
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLGetChannelsPage() throws Exception {
		super.testGraphQLGetChannelsPage();
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"currencyCode", "name", "type"};
	}

	@Override
	protected Channel randomChannel() throws Exception {
		return new Channel() {
			{
				accountId = AccountConstants.ACCOUNT_ENTRY_ID_DEFAULT;
				currencyCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				externalReferenceCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				id = RandomTestUtil.randomLong();
				name = StringUtil.toLowerCase(RandomTestUtil.randomString());
				siteGroupId = RandomTestUtil.randomLong();
				type = CommerceChannelConstants.CHANNEL_TYPE_SITE;
			}
		};
	}

	@Override
	protected Channel randomPatchChannel() throws Exception {
		return new Channel() {
			{
				accountId = AccountConstants.ACCOUNT_ENTRY_ID_DEFAULT;
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
	protected Channel testDeleteChannel_addChannel() throws Exception {
		return _addChannel(randomChannel());
	}

	@Override
	protected Channel testDeleteChannelByExternalReferenceCode_addChannel()
		throws Exception {

		return _addChannel(randomChannel());
	}

	@Override
	protected Channel testGetAccountAddressChannelChannel_addChannel()
		throws Exception {

		if (_commerceChannelRel == null) {
			_getCommerceChannelRelId();
		}

		return _toChannel(
			_commerceChannelLocalService.getCommerceChannel(
				_commerceChannelRel.getCommerceChannelId()));
	}

	@Override
	protected Long
			testGetAccountAddressChannelChannel_getAccountAddressChannelId()
		throws Exception {

		return _getCommerceChannelRelId();
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
	protected Channel testGraphQLGetAccountAddressChannelChannel_addChannel()
		throws Exception {

		return testGetAccountAddressChannelChannel_addChannel();
	}

	@Override
	protected Long
			testGraphQLGetAccountAddressChannelChannel_getAccountAddressChannelId()
		throws Exception {

		return _getCommerceChannelRelId();
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
			_commerceChannelLocalService.addCommerceChannel(
				channel.getExternalReferenceCode(),
				AccountConstants.ACCOUNT_ENTRY_ID_DEFAULT,
				channel.getSiteGroupId(), channel.getName(), channel.getType(),
				null, channel.getCurrencyCode(), _serviceContext);

		_commerceChannels.add(commerceChannel);

		return _toChannel(commerceChannel);
	}

	private long _getCommerceChannelRelId() throws Exception {
		if (_accountEntry == null) {
			_accountEntry = _accountEntryLocalService.addAccountEntry(
				_user.getUserId(), 0, RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), null,
				RandomTestUtil.randomString() + "@liferay.com", null,
				RandomTestUtil.randomString(),
				AccountConstants.ACCOUNT_ENTRY_TYPE_BUSINESS,
				WorkflowConstants.STATUS_APPROVED, _serviceContext);
		}

		if (_address == null) {
			_address = _addressLocalService.addAddress(
				RandomTestUtil.randomString(), _user.getUserId(),
				AccountEntry.class.getName(), _accountEntry.getAccountEntryId(),
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), 0, 0, 0, false, false,
				RandomTestUtil.randomString(), _serviceContext);
		}

		Channel channel = _addChannel(randomChannel());

		if (_commerceChannelRel == null) {
			_commerceChannelRel =
				_commerceChannelRelLocalService.addCommerceChannelRel(
					Address.class.getName(), _address.getAddressId(),
					channel.getId(), _serviceContext);
		}

		return _commerceChannelRel.getCommerceChannelRelId();
	}

	private Channel _toChannel(CommerceChannel commerceChannel) {
		return new Channel() {
			{
				accountId = commerceChannel.getAccountEntryId();
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

	@DeleteAfterTestRun
	private AccountEntry _accountEntry;

	@Inject
	private AccountEntryLocalService _accountEntryLocalService;

	@DeleteAfterTestRun
	private Address _address;

	@Inject
	private AddressLocalService _addressLocalService;

	@Inject
	private CommerceChannelLocalService _commerceChannelLocalService;

	@DeleteAfterTestRun
	private CommerceChannelRel _commerceChannelRel;

	@Inject
	private CommerceChannelRelLocalService _commerceChannelRelLocalService;

	private final List<CommerceChannel> _commerceChannels = new ArrayList<>();
	private ServiceContext _serviceContext;
	private User _user;

}