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

package com.liferay.commerce.order.rule.internal.validator.test;

import com.liferay.account.constants.AccountConstants;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.model.AccountGroup;
import com.liferay.account.service.AccountGroupLocalService;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.account.test.util.CommerceAccountTestUtil;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.test.util.CommerceCurrencyTestUtil;
import com.liferay.commerce.exception.CommerceOrderValidatorException;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderType;
import com.liferay.commerce.order.engine.CommerceOrderEngine;
import com.liferay.commerce.order.rule.constants.COREntryConstants;
import com.liferay.commerce.order.rule.model.COREntry;
import com.liferay.commerce.order.rule.service.COREntryLocalService;
import com.liferay.commerce.order.rule.service.COREntryRelLocalService;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.service.CommerceOrderLocalService;
import com.liferay.commerce.service.CommerceOrderTypeLocalService;
import com.liferay.commerce.test.util.CommerceTestUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.Calendar;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Luca Pellizzon
 */
@RunWith(Arquillian.class)
@Sync
public class CORCommerceOrderValidatorTest {

	@ClassRule
	@Rule
	public static AggregateTestRule aggregateTestRule = new AggregateTestRule(
		new LiferayIntegrationTestRule(),
		PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_user = UserTestUtil.addUser();

		_group = GroupTestUtil.addGroup();

		_commerceCurrency = CommerceCurrencyTestUtil.addCommerceCurrency(
			_group.getCompanyId());

		_commerceChannel = CommerceTestUtil.addCommerceChannel(
			_group.getGroupId(), _commerceCurrency.getCode());

		Calendar calendar = Calendar.getInstance();

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			_group.getCompanyId(), _group.getGroupId(), _user.getUserId());

		_accountEntry = CommerceAccountTestUtil.getPersonAccountEntry(
			_user.getUserId());

		_accountGroup = _accountGroupLocalService.addAccountGroup(
			_serviceContext.getUserId(), null, RandomTestUtil.randomString(),
			_serviceContext);

		_accountGroup.setExternalReferenceCode(null);
		_accountGroup.setDefaultAccountGroup(false);
		_accountGroup.setType(AccountConstants.ACCOUNT_GROUP_TYPE_STATIC);
		_accountGroup.setExpandoBridgeAttributes(_serviceContext);

		_accountGroup = _accountGroupLocalService.updateAccountGroup(
			_accountGroup);

		_commerceOrderType =
			_commerceOrderTypeLocalService.addCommerceOrderType(
				RandomTestUtil.randomString(), _user.getUserId(),
				RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.randomLocaleStringMap(), true,
				calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DAY_OF_MONTH),
				calendar.get(Calendar.YEAR), calendar.get(Calendar.HOUR_OF_DAY),
				calendar.get(Calendar.MINUTE), 1, calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DAY_OF_MONTH),
				calendar.get(Calendar.YEAR), calendar.get(Calendar.HOUR_OF_DAY),
				calendar.get(Calendar.MINUTE), true, _serviceContext);
		_corEntry = _corEntryLocalService.addCOREntry(
			RandomTestUtil.randomString(), _user.getUserId(), true,
			RandomTestUtil.randomString(), calendar.get(Calendar.MONTH),
			calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.YEAR),
			calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE),
			calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),
			calendar.get(Calendar.YEAR), calendar.get(Calendar.HOUR_OF_DAY),
			calendar.get(Calendar.MINUTE), true, RandomTestUtil.randomString(),
			100, COREntryConstants.TYPE_MINIMUM_ORDER_AMOUNT,
			UnicodePropertiesBuilder.put(
				COREntryConstants.TYPE_MINIMUM_ORDER_AMOUNT_FIELD_AMOUNT, "20"
			).put(
				COREntryConstants.TYPE_MINIMUM_ORDER_AMOUNT_FIELD_CURRENCY_CODE,
				"EUR"
			).buildString(),
			_serviceContext);
	}

	@Test
	public void testAccountEntry() throws Exception {
		_corEntryRelLocalService.addCOREntryRel(
			_user.getUserId(), AccountEntry.class.getName(),
			_accountEntry.getAccountEntryId(), _corEntry.getCOREntryId());

		CommerceOrder commerceOrder = CommerceTestUtil.addB2BCommerceOrder(
			_group.getGroupId(), _user.getUserId(),
			_accountEntry.getAccountEntryId(),
			_commerceCurrency.getCommerceCurrencyId());

		commerceOrder = CommerceTestUtil.addCheckoutDetailsToCommerceOrder(
			commerceOrder, commerceOrder.getUserId(), false, false, 5.00);

		try {
			_commerceOrderEngine.checkoutCommerceOrder(
				commerceOrder, _user.getUserId());
		}
		catch (PortalException portalException) {
			Throwable throwable = portalException.getCause();

			Assert.assertSame(
				CommerceOrderValidatorException.class, throwable.getClass());
		}
	}

	@Test
	public void testAccountEntryAndCommerceChannel() throws Exception {
		_corEntryRelLocalService.addCOREntryRel(
			_user.getUserId(), AccountEntry.class.getName(),
			_accountEntry.getAccountEntryId(), _corEntry.getCOREntryId());

		_corEntryRelLocalService.addCOREntryRel(
			_user.getUserId(), CommerceChannel.class.getName(),
			_commerceChannel.getCommerceChannelId(), _corEntry.getCOREntryId());

		CommerceOrder commerceOrder = CommerceTestUtil.addB2BCommerceOrder(
			_group.getGroupId(), _user.getUserId(),
			_accountEntry.getAccountEntryId(),
			_commerceCurrency.getCommerceCurrencyId());

		commerceOrder = CommerceTestUtil.addCheckoutDetailsToCommerceOrder(
			commerceOrder, commerceOrder.getUserId(), false, false, 5.00);

		try {
			_commerceOrderEngine.checkoutCommerceOrder(
				commerceOrder, _user.getUserId());
		}
		catch (PortalException portalException) {
			Throwable throwable = portalException.getCause();

			Assert.assertSame(
				CommerceOrderValidatorException.class, throwable.getClass());
		}
	}

	@Test
	public void testAccountEntryAndCommerceChannelAndCommerceOrderType()
		throws Exception {

		_corEntryRelLocalService.addCOREntryRel(
			_user.getUserId(), AccountEntry.class.getName(),
			_accountEntry.getAccountEntryId(), _corEntry.getCOREntryId());

		_corEntryRelLocalService.addCOREntryRel(
			_user.getUserId(), CommerceChannel.class.getName(),
			_commerceChannel.getCommerceChannelId(), _corEntry.getCOREntryId());

		_corEntryRelLocalService.addCOREntryRel(
			_user.getUserId(), CommerceOrderType.class.getName(),
			_commerceOrderType.getCommerceOrderTypeId(),
			_corEntry.getCOREntryId());

		CommerceOrder commerceOrder = CommerceTestUtil.addB2BCommerceOrder(
			_group.getGroupId(), _user.getUserId(),
			_accountEntry.getAccountEntryId(),
			_commerceCurrency.getCommerceCurrencyId());

		commerceOrder.setCommerceOrderTypeId(
			_commerceOrderType.getCommerceOrderTypeId());

		commerceOrder = _commerceOrderLocalService.updateCommerceOrder(
			commerceOrder);

		commerceOrder = CommerceTestUtil.addCheckoutDetailsToCommerceOrder(
			commerceOrder, commerceOrder.getUserId(), false, false, 5.00);

		try {
			_commerceOrderEngine.checkoutCommerceOrder(
				commerceOrder, _user.getUserId());
		}
		catch (PortalException portalException) {
			Throwable throwable = portalException.getCause();

			Assert.assertSame(
				CommerceOrderValidatorException.class, throwable.getClass());
		}
	}

	@Test
	public void testAccountEntryAndCommerceOrderType() throws Exception {
		_corEntryRelLocalService.addCOREntryRel(
			_user.getUserId(), AccountEntry.class.getName(),
			_accountEntry.getAccountEntryId(), _corEntry.getCOREntryId());

		_corEntryRelLocalService.addCOREntryRel(
			_user.getUserId(), CommerceOrderType.class.getName(),
			_commerceOrderType.getCommerceOrderTypeId(),
			_corEntry.getCOREntryId());

		CommerceOrder commerceOrder = CommerceTestUtil.addB2BCommerceOrder(
			_group.getGroupId(), _user.getUserId(),
			_accountEntry.getAccountEntryId(),
			_commerceCurrency.getCommerceCurrencyId());

		commerceOrder.setCommerceOrderTypeId(
			_commerceOrderType.getCommerceOrderTypeId());

		commerceOrder = _commerceOrderLocalService.updateCommerceOrder(
			commerceOrder);

		commerceOrder = CommerceTestUtil.addCheckoutDetailsToCommerceOrder(
			commerceOrder, commerceOrder.getUserId(), false, false, 5.00);

		try {
			_commerceOrderEngine.checkoutCommerceOrder(
				commerceOrder, _user.getUserId());
		}
		catch (PortalException portalException) {
			Throwable throwable = portalException.getCause();

			Assert.assertSame(
				CommerceOrderValidatorException.class, throwable.getClass());
		}
	}

	@Test
	public void testAccountGroups() throws Exception {
		_corEntryRelLocalService.addCOREntryRel(
			_user.getUserId(), AccountGroup.class.getName(),
			_accountGroup.getAccountGroupId(), _corEntry.getCOREntryId());

		CommerceOrder commerceOrder = CommerceTestUtil.addB2BCommerceOrder(
			_group.getGroupId(), _user.getUserId(),
			_accountEntry.getAccountEntryId(),
			_commerceCurrency.getCommerceCurrencyId());

		commerceOrder = CommerceTestUtil.addCheckoutDetailsToCommerceOrder(
			commerceOrder, commerceOrder.getUserId(), false, false, 5.00);

		try {
			_commerceOrderEngine.checkoutCommerceOrder(
				commerceOrder, _user.getUserId());
		}
		catch (PortalException portalException) {
			Throwable throwable = portalException.getCause();

			Assert.assertSame(
				CommerceOrderValidatorException.class, throwable.getClass());
		}
	}

	@Test
	public void testAccountGroupsAndCommerceChannel() throws Exception {
		_corEntryRelLocalService.addCOREntryRel(
			_user.getUserId(), AccountGroup.class.getName(),
			_accountGroup.getAccountGroupId(), _corEntry.getCOREntryId());

		_corEntryRelLocalService.addCOREntryRel(
			_user.getUserId(), CommerceChannel.class.getName(),
			_commerceChannel.getCommerceChannelId(), _corEntry.getCOREntryId());

		CommerceOrder commerceOrder = CommerceTestUtil.addB2BCommerceOrder(
			_group.getGroupId(), _user.getUserId(),
			_accountEntry.getAccountEntryId(),
			_commerceCurrency.getCommerceCurrencyId());

		commerceOrder = CommerceTestUtil.addCheckoutDetailsToCommerceOrder(
			commerceOrder, commerceOrder.getUserId(), false, false, 5.00);

		try {
			_commerceOrderEngine.checkoutCommerceOrder(
				commerceOrder, _user.getUserId());
		}
		catch (PortalException portalException) {
			Throwable throwable = portalException.getCause();

			Assert.assertSame(
				CommerceOrderValidatorException.class, throwable.getClass());
		}
	}

	@Test
	public void testAccountGroupsAndCommerceChannelAndCommerceOrderType()
		throws Exception {

		_corEntryRelLocalService.addCOREntryRel(
			_user.getUserId(), AccountGroup.class.getName(),
			_accountGroup.getAccountGroupId(), _corEntry.getCOREntryId());

		_corEntryRelLocalService.addCOREntryRel(
			_user.getUserId(), CommerceChannel.class.getName(),
			_commerceChannel.getCommerceChannelId(), _corEntry.getCOREntryId());

		_corEntryRelLocalService.addCOREntryRel(
			_user.getUserId(), CommerceOrderType.class.getName(),
			_commerceOrderType.getCommerceOrderTypeId(),
			_corEntry.getCOREntryId());

		CommerceOrder commerceOrder = CommerceTestUtil.addB2BCommerceOrder(
			_group.getGroupId(), _user.getUserId(),
			_accountEntry.getAccountEntryId(),
			_commerceCurrency.getCommerceCurrencyId());

		commerceOrder.setCommerceOrderTypeId(
			_commerceOrderType.getCommerceOrderTypeId());

		commerceOrder = _commerceOrderLocalService.updateCommerceOrder(
			commerceOrder);

		commerceOrder = CommerceTestUtil.addCheckoutDetailsToCommerceOrder(
			commerceOrder, commerceOrder.getUserId(), false, false, 5.00);

		try {
			_commerceOrderEngine.checkoutCommerceOrder(
				commerceOrder, _user.getUserId());
		}
		catch (PortalException portalException) {
			Throwable throwable = portalException.getCause();

			Assert.assertSame(
				CommerceOrderValidatorException.class, throwable.getClass());
		}
	}

	@Test
	public void testAccountGroupsAndCommerceOrderType() throws Exception {
		_corEntryRelLocalService.addCOREntryRel(
			_user.getUserId(), AccountGroup.class.getName(),
			_accountGroup.getAccountGroupId(), _corEntry.getCOREntryId());

		_corEntryRelLocalService.addCOREntryRel(
			_user.getUserId(), CommerceOrderType.class.getName(),
			_commerceOrderType.getCommerceOrderTypeId(),
			_corEntry.getCOREntryId());

		CommerceOrder commerceOrder = CommerceTestUtil.addB2BCommerceOrder(
			_group.getGroupId(), _user.getUserId(),
			_accountEntry.getAccountEntryId(),
			_commerceCurrency.getCommerceCurrencyId());

		commerceOrder.setCommerceOrderTypeId(
			_commerceOrderType.getCommerceOrderTypeId());

		commerceOrder = _commerceOrderLocalService.updateCommerceOrder(
			commerceOrder);

		commerceOrder = CommerceTestUtil.addCheckoutDetailsToCommerceOrder(
			commerceOrder, commerceOrder.getUserId(), false, false, 5.00);

		try {
			_commerceOrderEngine.checkoutCommerceOrder(
				commerceOrder, _user.getUserId());
		}
		catch (PortalException portalException) {
			Throwable throwable = portalException.getCause();

			Assert.assertSame(
				CommerceOrderValidatorException.class, throwable.getClass());
		}
	}

	@Test
	public void testCommerceChannel() throws Exception {
		_corEntryRelLocalService.addCOREntryRel(
			_user.getUserId(), CommerceChannel.class.getName(),
			_commerceChannel.getCommerceChannelId(), _corEntry.getCOREntryId());

		CommerceOrder commerceOrder = CommerceTestUtil.addB2BCommerceOrder(
			_group.getGroupId(), _user.getUserId(),
			_accountEntry.getAccountEntryId(),
			_commerceCurrency.getCommerceCurrencyId());

		commerceOrder = CommerceTestUtil.addCheckoutDetailsToCommerceOrder(
			commerceOrder, commerceOrder.getUserId(), false, false, 5.00);

		try {
			_commerceOrderEngine.checkoutCommerceOrder(
				commerceOrder, _user.getUserId());
		}
		catch (PortalException portalException) {
			Throwable throwable = portalException.getCause();

			Assert.assertSame(
				CommerceOrderValidatorException.class, throwable.getClass());
		}
	}

	@Test
	public void testCommerceChannelAndCommerceOrderType() throws Exception {
		_corEntryRelLocalService.addCOREntryRel(
			_user.getUserId(), CommerceChannel.class.getName(),
			_commerceChannel.getCommerceChannelId(), _corEntry.getCOREntryId());

		_corEntryRelLocalService.addCOREntryRel(
			_user.getUserId(), CommerceOrderType.class.getName(),
			_commerceOrderType.getCommerceOrderTypeId(),
			_corEntry.getCOREntryId());

		CommerceOrder commerceOrder = CommerceTestUtil.addB2BCommerceOrder(
			_group.getGroupId(), _user.getUserId(),
			_accountEntry.getAccountEntryId(),
			_commerceCurrency.getCommerceCurrencyId());

		commerceOrder.setCommerceOrderTypeId(
			_commerceOrderType.getCommerceOrderTypeId());

		commerceOrder = _commerceOrderLocalService.updateCommerceOrder(
			commerceOrder);

		commerceOrder = CommerceTestUtil.addCheckoutDetailsToCommerceOrder(
			commerceOrder, commerceOrder.getUserId(), false, false, 5.00);

		try {
			_commerceOrderEngine.checkoutCommerceOrder(
				commerceOrder, _user.getUserId());
		}
		catch (PortalException portalException) {
			Throwable throwable = portalException.getCause();

			Assert.assertSame(
				CommerceOrderValidatorException.class, throwable.getClass());
		}
	}

	@Test
	public void testCommerceOrderType() throws Exception {
		_corEntryRelLocalService.addCOREntryRel(
			_user.getUserId(), CommerceOrderType.class.getName(),
			_commerceOrderType.getCommerceOrderTypeId(),
			_corEntry.getCOREntryId());

		CommerceOrder commerceOrder = CommerceTestUtil.addB2BCommerceOrder(
			_group.getGroupId(), _user.getUserId(),
			_accountEntry.getAccountEntryId(),
			_commerceCurrency.getCommerceCurrencyId());

		commerceOrder.setCommerceOrderTypeId(
			_commerceOrderType.getCommerceOrderTypeId());

		commerceOrder = _commerceOrderLocalService.updateCommerceOrder(
			commerceOrder);

		commerceOrder = CommerceTestUtil.addCheckoutDetailsToCommerceOrder(
			commerceOrder, commerceOrder.getUserId(), false, false, 5.00);

		try {
			_commerceOrderEngine.checkoutCommerceOrder(
				commerceOrder, _user.getUserId());
		}
		catch (PortalException portalException) {
			Throwable throwable = portalException.getCause();

			Assert.assertSame(
				CommerceOrderValidatorException.class, throwable.getClass());
		}
	}

	private AccountEntry _accountEntry;
	private AccountGroup _accountGroup;

	@Inject
	private AccountGroupLocalService _accountGroupLocalService;

	private CommerceChannel _commerceChannel;
	private CommerceCurrency _commerceCurrency;

	@Inject
	private CommerceOrderEngine _commerceOrderEngine;

	@Inject
	private CommerceOrderLocalService _commerceOrderLocalService;

	private CommerceOrderType _commerceOrderType;

	@Inject
	private CommerceOrderTypeLocalService _commerceOrderTypeLocalService;

	private COREntry _corEntry;

	@Inject
	private COREntryLocalService _corEntryLocalService;

	@Inject
	private COREntryRelLocalService _corEntryRelLocalService;

	private Group _group;
	private ServiceContext _serviceContext;
	private User _user;

}