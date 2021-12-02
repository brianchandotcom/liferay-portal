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

import com.liferay.account.model.AccountEntry;
import com.liferay.account.model.AccountGroup;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.account.model.CommerceAccount;
import com.liferay.commerce.account.model.CommerceAccountGroup;
import com.liferay.commerce.account.service.CommerceAccountGroupLocalService;
import com.liferay.commerce.account.service.CommerceAccountLocalService;
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

		_commerceAccount =
			_commerceAccountLocalService.getPersonalCommerceAccount(
				_user.getUserId());
		_commerceAccountGroup =
			_commerceAccountGroupLocalService.addCommerceAccountGroup(
				_user.getCompanyId(), RandomTestUtil.randomString(), 0, false,
				RandomTestUtil.randomString(),
				ServiceContextTestUtil.getServiceContext());

		_group = GroupTestUtil.addGroup();

		_commerceCurrency = CommerceCurrencyTestUtil.addCommerceCurrency(
			_group.getCompanyId());

		_commerceChannel = CommerceTestUtil.addCommerceChannel(
			_group.getGroupId(), _commerceCurrency.getCode());

		Calendar calendar = Calendar.getInstance();

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			_group.getCompanyId(), _group.getGroupId(), _user.getUserId());

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
			_commerceAccount.getCommerceAccountId(), _corEntry.getCOREntryId());

		CommerceOrder commerceOrder = CommerceTestUtil.addB2BCommerceOrder(
			_group.getGroupId(), _user.getUserId(),
			_commerceAccount.getCommerceAccountId(),
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
			_commerceAccount.getCommerceAccountId(), _corEntry.getCOREntryId());

		_corEntryRelLocalService.addCOREntryRel(
			_user.getUserId(), CommerceChannel.class.getName(),
			_commerceChannel.getCommerceChannelId(), _corEntry.getCOREntryId());

		CommerceOrder commerceOrder = CommerceTestUtil.addB2BCommerceOrder(
			_group.getGroupId(), _user.getUserId(),
			_commerceAccount.getCommerceAccountId(),
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
			_commerceAccount.getCommerceAccountId(), _corEntry.getCOREntryId());

		_corEntryRelLocalService.addCOREntryRel(
			_user.getUserId(), CommerceChannel.class.getName(),
			_commerceChannel.getCommerceChannelId(), _corEntry.getCOREntryId());

		_corEntryRelLocalService.addCOREntryRel(
			_user.getUserId(), CommerceOrderType.class.getName(),
			_commerceOrderType.getCommerceOrderTypeId(),
			_corEntry.getCOREntryId());

		CommerceOrder commerceOrder = CommerceTestUtil.addB2BCommerceOrder(
			_group.getGroupId(), _user.getUserId(),
			_commerceAccount.getCommerceAccountId(),
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
			_commerceAccount.getCommerceAccountId(), _corEntry.getCOREntryId());

		_corEntryRelLocalService.addCOREntryRel(
			_user.getUserId(), CommerceOrderType.class.getName(),
			_commerceOrderType.getCommerceOrderTypeId(),
			_corEntry.getCOREntryId());

		CommerceOrder commerceOrder = CommerceTestUtil.addB2BCommerceOrder(
			_group.getGroupId(), _user.getUserId(),
			_commerceAccount.getCommerceAccountId(),
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
			_commerceAccountGroup.getCommerceAccountGroupId(),
			_corEntry.getCOREntryId());

		CommerceOrder commerceOrder = CommerceTestUtil.addB2BCommerceOrder(
			_group.getGroupId(), _user.getUserId(),
			_commerceAccount.getCommerceAccountId(),
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
			_commerceAccountGroup.getCommerceAccountGroupId(),
			_corEntry.getCOREntryId());

		_corEntryRelLocalService.addCOREntryRel(
			_user.getUserId(), CommerceChannel.class.getName(),
			_commerceChannel.getCommerceChannelId(), _corEntry.getCOREntryId());

		CommerceOrder commerceOrder = CommerceTestUtil.addB2BCommerceOrder(
			_group.getGroupId(), _user.getUserId(),
			_commerceAccount.getCommerceAccountId(),
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
			_commerceAccountGroup.getCommerceAccountGroupId(),
			_corEntry.getCOREntryId());

		_corEntryRelLocalService.addCOREntryRel(
			_user.getUserId(), CommerceChannel.class.getName(),
			_commerceChannel.getCommerceChannelId(), _corEntry.getCOREntryId());

		_corEntryRelLocalService.addCOREntryRel(
			_user.getUserId(), CommerceOrderType.class.getName(),
			_commerceOrderType.getCommerceOrderTypeId(),
			_corEntry.getCOREntryId());

		CommerceOrder commerceOrder = CommerceTestUtil.addB2BCommerceOrder(
			_group.getGroupId(), _user.getUserId(),
			_commerceAccount.getCommerceAccountId(),
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
			_commerceAccountGroup.getCommerceAccountGroupId(),
			_corEntry.getCOREntryId());

		_corEntryRelLocalService.addCOREntryRel(
			_user.getUserId(), CommerceOrderType.class.getName(),
			_commerceOrderType.getCommerceOrderTypeId(),
			_corEntry.getCOREntryId());

		CommerceOrder commerceOrder = CommerceTestUtil.addB2BCommerceOrder(
			_group.getGroupId(), _user.getUserId(),
			_commerceAccount.getCommerceAccountId(),
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
			_commerceAccount.getCommerceAccountId(),
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
			_commerceAccount.getCommerceAccountId(),
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
			_commerceAccount.getCommerceAccountId(),
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

	private CommerceAccount _commerceAccount;
	private CommerceAccountGroup _commerceAccountGroup;

	@Inject
	private CommerceAccountGroupLocalService _commerceAccountGroupLocalService;

	@Inject
	private CommerceAccountLocalService _commerceAccountLocalService;

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