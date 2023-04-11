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

package com.liferay.commerce.checkout.helper.test;

import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.account.constants.CommerceAccountConstants;
import com.liferay.commerce.account.test.util.CommerceAccountTestUtil;
import com.liferay.commerce.checkout.helper.CommerceCheckoutStepHttpHelper;
import com.liferay.commerce.constants.CommerceAddressConstants;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.test.util.CommerceCurrencyTestUtil;
import com.liferay.commerce.model.CommerceAddress;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.product.constants.CommerceChannelConstants;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.commerce.service.CommerceAddressLocalService;
import com.liferay.commerce.service.CommerceOrderLocalService;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Region;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.CountryLocalService;
import com.liferay.portal.kernel.service.RegionLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.settings.GroupServiceSettingsLocator;
import com.liferay.portal.kernel.settings.ModifiableSettings;
import com.liferay.portal.kernel.settings.Settings;
import com.liferay.portal.kernel.settings.SettingsFactory;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Ivica Cardic
 */
@RunWith(Arquillian.class)
public class CommerceCheckoutStepHttpHelperTest {

	@ClassRule
	@Rule
	public static AggregateTestRule aggregateTestRule = new AggregateTestRule(
		new LiferayIntegrationTestRule(),
		PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
		_user = UserTestUtil.addUser(true);

		_commerceCurrency = CommerceCurrencyTestUtil.addCommerceCurrency(
			_group.getCompanyId());

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			_group.getGroupId());

		_commerceChannel = _commerceChannelLocalService.addCommerceChannel(
			null, _group.getGroupId(), "Test Channel",
			CommerceChannelConstants.CHANNEL_TYPE_SITE, null,
			_commerceCurrency.getCode(), _serviceContext);

		Settings settings = _settingsFactory.getSettings(
			new GroupServiceSettingsLocator(
				_commerceChannel.getGroupId(),
				CommerceAccountConstants.SERVICE_NAME));

		ModifiableSettings modifiableSettings =
			settings.getModifiableSettings();

		modifiableSettings.setValue(
			"commerceSiteType",
			String.valueOf(CommerceAccountConstants.SITE_TYPE_B2B));

		modifiableSettings.store();

		_countryLocalService.deleteCompanyCountries(_group.getCompanyId());
	}

	@After
	public void tearDown() throws Exception {
		_commerceOrderLocalService.deleteCommerceOrders(
			_commerceChannel.getGroupId());
	}

	@Test
	public void testIsActiveBillingAddressCommerceCheckoutStep()
		throws Exception {

		AccountEntry accountEntry =
			CommerceAccountTestUtil.addBusinessAccountEntry(
				_user.getUserId(), "Test Business Account", null, null,
				new long[] {_user.getUserId()}, null, _serviceContext);

		long commerceChannelGroupId = _commerceChannel.getGroupId();

		CommerceOrder commerceOrder =
			_commerceOrderLocalService.addCommerceOrder(
				_user.getUserId(), commerceChannelGroupId,
				accountEntry.getAccountEntryId(),
				_commerceCurrency.getCommerceCurrencyId(), 0);

		CommerceAddress commerceAddress = _addCommerceAddress(
			accountEntry.getAccountEntryId());

		Assert.assertTrue(
			_commerceCheckoutStepHttpHelper.
				isActiveBillingAddressCommerceCheckoutStep(
					null, commerceOrder));

		_accountEntryLocalService.updateDefaultBillingAddressId(
			accountEntry.getAccountEntryId(), 0);
		_accountEntryLocalService.updateDefaultShippingAddressId(
			accountEntry.getAccountEntryId(), 0);

		commerceOrder.setBillingAddressId(
			commerceAddress.getCommerceAddressId());
		commerceOrder.setShippingAddressId(
			commerceAddress.getCommerceAddressId());

		commerceOrder = _commerceOrderLocalService.updateCommerceOrder(
			commerceOrder);

		Assert.assertFalse(
			_commerceCheckoutStepHttpHelper.
				isActiveBillingAddressCommerceCheckoutStep(
					null, commerceOrder));
	}

	private CommerceAddress _addCommerceAddress(long commerceAccountId)
		throws Exception {

		_country = _countryLocalService.fetchCountryByNumber(
			_serviceContext.getCompanyId(), "000");

		if (_country == null) {
			_country = _countryLocalService.addCountry(
				"ZZ", "ZZZ", true, true, null, RandomTestUtil.randomString(),
				"000", RandomTestUtil.randomDouble(), true, false, false,
				_serviceContext);

			_region = _regionLocalService.addRegion(
				_country.getCountryId(), true, RandomTestUtil.randomString(),
				RandomTestUtil.randomDouble(), "ZZ", _serviceContext);
		}
		else {
			_region = _regionLocalService.getRegion(
				_country.getCountryId(), "ZZ");
		}

		return _commerceAddressLocalService.addCommerceAddress(
			AccountEntry.class.getName(), commerceAccountId,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			String.valueOf(30133), _region.getRegionId(),
			_country.getCountryId(), RandomTestUtil.randomString(),
			CommerceAddressConstants.ADDRESS_TYPE_BILLING_AND_SHIPPING,
			_serviceContext);
	}

	@Inject
	private AccountEntryLocalService _accountEntryLocalService;

	@Inject
	private CommerceAddressLocalService _commerceAddressLocalService;

	private CommerceChannel _commerceChannel;

	@Inject
	private CommerceChannelLocalService _commerceChannelLocalService;

	@Inject
	private CommerceCheckoutStepHttpHelper _commerceCheckoutStepHttpHelper;

	private CommerceCurrency _commerceCurrency;

	@Inject
	private CommerceOrderLocalService _commerceOrderLocalService;

	private Country _country;

	@Inject
	private CountryLocalService _countryLocalService;

	private Group _group;
	private Region _region;

	@Inject
	private RegionLocalService _regionLocalService;

	private ServiceContext _serviceContext;

	@Inject
	private SettingsFactory _settingsFactory;

	private User _user;

}