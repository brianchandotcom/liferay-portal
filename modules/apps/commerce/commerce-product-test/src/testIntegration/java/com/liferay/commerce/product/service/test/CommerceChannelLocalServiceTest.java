/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.service.test;

import com.liferay.account.constants.AccountConstants;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.test.util.CommerceCurrencyTestUtil;
import com.liferay.commerce.product.constants.CommerceChannelConstants;
import com.liferay.commerce.product.exception.CommerceChannelNameException;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.AssertUtils;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Michele Vigilante
 */
@RunWith(Arquillian.class)
public class CommerceChannelLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE,
			SynchronousDestinationTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		User user = UserTestUtil.addUser();

		_commerceCurrency = CommerceCurrencyTestUtil.addCommerceCurrency(
			user.getCompanyId());

		_group = GroupTestUtil.addGroup(
			user.getCompanyId(), user.getUserId(),
			GroupConstants.DEFAULT_PARENT_GROUP_ID);

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			user.getCompanyId(), _group.getGroupId(), user.getUserId());
	}

	@Test
	public void testAddCommerceChannel() throws Exception {
		AssertUtils.assertFailure(
			CommerceChannelNameException.class, null,
			() -> _commerceChannelLocalService.addCommerceChannel(
				null, AccountConstants.ACCOUNT_ENTRY_ID_DEFAULT,
				_group.getGroupId(), " ",
				CommerceChannelConstants.CHANNEL_TYPE_SITE, null,
				_commerceCurrency.getCode(), _serviceContext));

		CommerceChannel commerceChannel =
			_commerceChannelLocalService.addCommerceChannel(
				null, AccountConstants.ACCOUNT_ENTRY_ID_DEFAULT,
				_group.getGroupId(), "<Test>Channel",
				CommerceChannelConstants.CHANNEL_TYPE_SITE, null,
				_commerceCurrency.getCode(), _serviceContext);

		Assert.assertEquals("Channel", commerceChannel.getName());
	}

	@Test
	public void testUpdateCommerceChannel() throws Exception {
		CommerceChannel commerceChannel =
			_commerceChannelLocalService.addCommerceChannel(
				null, AccountConstants.ACCOUNT_ENTRY_ID_DEFAULT,
				_group.getGroupId(), "Test Channel",
				CommerceChannelConstants.CHANNEL_TYPE_SITE, null,
				_commerceCurrency.getCode(), _serviceContext);

		commerceChannel.setName("<Test>Channel Updated");

		commerceChannel = _commerceChannelLocalService.updateCommerceChannel(
			commerceChannel.getCommerceChannelId(),
			AccountConstants.ACCOUNT_ENTRY_ID_DEFAULT, _group.getGroupId(),
			commerceChannel.getName(),
			CommerceChannelConstants.CHANNEL_TYPE_SITE,
			commerceChannel.getTypeSettingsUnicodeProperties(),
			commerceChannel.getCommerceCurrencyCode(),
			commerceChannel.getPriceDisplayType(),
			commerceChannel.isDiscountsTargetNetPrice());

		Assert.assertEquals("Channel Updated", commerceChannel.getName());
	}

	@Inject
	private CommerceChannelLocalService _commerceChannelLocalService;

	private CommerceCurrency _commerceCurrency;
	private Group _group;
	private ServiceContext _serviceContext;

}