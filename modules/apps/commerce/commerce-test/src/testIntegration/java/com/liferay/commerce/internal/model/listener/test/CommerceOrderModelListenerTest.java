/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.model.listener.test;

import com.liferay.account.model.AccountEntry;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.account.test.util.CommerceAccountTestUtil;
import com.liferay.commerce.constants.CommerceAddressConstants;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.service.CommerceCurrencyLocalServiceUtil;
import com.liferay.commerce.currency.test.util.CommerceCurrencyTestUtil;
import com.liferay.commerce.model.CommerceAddress;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceShippingMethod;
import com.liferay.commerce.payment.model.CommercePaymentMethodGroupRel;
import com.liferay.commerce.payment.service.CommercePaymentMethodGroupRelLocalService;
import com.liferay.commerce.payment.test.util.TestCommercePaymentMethod;
import com.liferay.commerce.product.constants.CommerceChannelAccountEntryRelConstants;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CommerceChannelAccountEntryRelLocalService;
import com.liferay.commerce.product.test.util.CPTestUtil;
import com.liferay.commerce.service.CommerceAddressLocalService;
import com.liferay.commerce.service.CommerceOrderLocalService;
import com.liferay.commerce.service.CommerceShippingMethodLocalService;
import com.liferay.commerce.service.CommerceShippingOptionAccountEntryRelService;
import com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOption;
import com.liferay.commerce.shipping.engine.fixed.service.CommerceShippingFixedOptionLocalService;
import com.liferay.commerce.term.constants.CommerceTermEntryConstants;
import com.liferay.commerce.term.model.CommerceTermEntry;
import com.liferay.commerce.term.service.CommerceTermEntryLocalService;
import com.liferay.commerce.test.util.CommerceTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Address;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Region;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.service.CountryLocalService;
import com.liferay.portal.kernel.service.RegionLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.math.BigDecimal;

import java.util.Collections;

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
public class CommerceOrderModelListenerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_company = CompanyLocalServiceUtil.getCompany(_group.getCompanyId());

		_user = UserTestUtil.addUser(_company);

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			_group.getGroupId());

		_accountEntry = CommerceAccountTestUtil.addBusinessAccountEntry(
			_user.getUserId(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString() + "@liferay.com",
			RandomTestUtil.randomString(), new long[] {_user.getUserId()}, null,
			_serviceContext);

		_commerceCurrency =
			CommerceCurrencyLocalServiceUtil.fetchPrimaryCommerceCurrency(
				_group.getCompanyId());

		if (_commerceCurrency == null) {
			_commerceCurrency = CommerceCurrencyTestUtil.addCommerceCurrency(
				_group.getCompanyId());
		}

		_commerceChannel = CommerceTestUtil.addCommerceChannel(
			_group.getGroupId(), _commerceCurrency.getCode());

		_commerceDeliveryTerm =
			_commerceTermEntryLocalService.addCommerceTermEntry(
				RandomTestUtil.randomString(), _user.getUserId(), true,
				Collections.singletonMap(
					LocaleUtil.getSiteDefault(), RandomTestUtil.randomString()),
				1, 1, 2022, 12, 0, 0, 0, 0, 0, 0, true,
				Collections.singletonMap(
					LocaleUtil.getSiteDefault(), RandomTestUtil.randomString()),
				RandomTestUtil.randomString(), 1000,
				CommerceTermEntryConstants.TYPE_DELIVERY_TERMS, null,
				_serviceContext);
		_commercePaymentMethodGroupRel =
			_commercePaymentMethodGroupRelLocalService.
				addCommercePaymentMethodGroupRel(
					_user.getUserId(), _commerceChannel.getGroupId(),
					RandomTestUtil.randomLocaleStringMap(),
					RandomTestUtil.randomLocaleStringMap(), true, null,
					TestCommercePaymentMethod.KEY, 99, null);
		_commercePaymentTerm =
			_commerceTermEntryLocalService.addCommerceTermEntry(
				RandomTestUtil.randomString(), _user.getUserId(), true,
				Collections.singletonMap(
					LocaleUtil.getSiteDefault(), RandomTestUtil.randomString()),
				1, 1, 2022, 12, 0, 0, 0, 0, 0, 0, true,
				Collections.singletonMap(
					LocaleUtil.getSiteDefault(), RandomTestUtil.randomString()),
				RandomTestUtil.randomString(), 1000,
				CommerceTermEntryConstants.TYPE_PAYMENT_TERMS, null,
				_serviceContext);

		_commerceShippingMethod =
			_commerceShippingMethodLocalService.addCommerceShippingMethod(
				_user.getUserId(), _commerceChannel.getGroupId(),
				Collections.singletonMap(
					LocaleUtil.US, RandomTestUtil.randomString()),
				Collections.singletonMap(
					LocaleUtil.US, RandomTestUtil.randomString()),
				true, "fixed", null, 1, RandomTestUtil.randomString());

		_commerceShippingFixedOption =
			_commerceShippingFixedOptionLocalService.
				addCommerceShippingFixedOption(
					_user.getUserId(), _commerceChannel.getGroupId(),
					_commerceShippingMethod.getCommerceShippingMethodId(),
					BigDecimal.valueOf(RandomTestUtil.nextDouble()),
					RandomTestUtil.randomLocaleStringMap(),
					RandomTestUtil.randomString(),
					Collections.singletonMap(
						LocaleUtil.US, RandomTestUtil.randomString()),
					RandomTestUtil.nextDouble());

		_country = _countryLocalService.addCountry(
			"ZZ", "ZZZ", true, true, null, RandomTestUtil.randomString(), "000",
			RandomTestUtil.randomDouble(), true, false, false, _serviceContext);

		_region = _regionLocalService.addRegion(
			_country.getCountryId(), true, RandomTestUtil.randomString(),
			RandomTestUtil.randomDouble(), "ZZ", _serviceContext);
	}

	@Test
	public void testAddCommerceOrder() throws Exception {
		CommerceAddress commerceAddress =
			_commerceAddressLocalService.addCommerceAddress(
				StringPool.BLANK, AccountEntry.class.getName(),
				_accountEntry.getAccountEntryId(), _country.getCountryId(),
				_region.getRegionId(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				StringPool.BLANK,
				CommerceAddressConstants.ADDRESS_TYPE_BILLING_AND_SHIPPING,
				String.valueOf(30133), _serviceContext);

		_commerceChannelAccountEntryRelLocalService.
			addCommerceChannelAccountEntryRel(
				_user.getUserId(), _accountEntry.getAccountEntryId(),
				Address.class.getName(), commerceAddress.getCommerceAddressId(),
				_commerceChannel.getCommerceChannelId(), true, 0,
				CommerceChannelAccountEntryRelConstants.TYPE_BILLING_ADDRESS);
		_commerceChannelAccountEntryRelLocalService.
			addCommerceChannelAccountEntryRel(
				_user.getUserId(), _accountEntry.getAccountEntryId(),
				Address.class.getName(), commerceAddress.getCommerceAddressId(),
				_commerceChannel.getCommerceChannelId(), true, 0,
				CommerceChannelAccountEntryRelConstants.TYPE_SHIPPING_ADDRESS);

		_commerceChannelAccountEntryRelLocalService.
			addCommerceChannelAccountEntryRel(
				_user.getUserId(), _accountEntry.getAccountEntryId(),
				CommercePaymentMethodGroupRel.class.getName(),
				_commercePaymentMethodGroupRel.
					getCommercePaymentMethodGroupRelId(),
				_commerceChannel.getCommerceChannelId(), true, 0,
				CommerceChannelAccountEntryRelConstants.TYPE_PAYMENT);

		_commerceChannelAccountEntryRelLocalService.
			addCommerceChannelAccountEntryRel(
				_user.getUserId(), _accountEntry.getAccountEntryId(),
				CommerceTermEntry.class.getName(),
				_commerceDeliveryTerm.getCommerceTermEntryId(),
				_commerceChannel.getCommerceChannelId(), true, 0,
				CommerceChannelAccountEntryRelConstants.TYPE_DELIVERY_TERM);
		_commerceChannelAccountEntryRelLocalService.
			addCommerceChannelAccountEntryRel(
				_user.getUserId(), _accountEntry.getAccountEntryId(),
				CommerceTermEntry.class.getName(),
				_commercePaymentTerm.getCommerceTermEntryId(),
				_commerceChannel.getCommerceChannelId(), true, 0,
				CommerceChannelAccountEntryRelConstants.TYPE_PAYMENT_TERM);

		_commerceShippingOptionAccountEntryRelService.
			addCommerceShippingOptionAccountEntryRel(
				_accountEntry.getAccountEntryId(),
				_commerceChannel.getCommerceChannelId(),
				_commerceShippingMethod.getEngineKey(),
				_commerceShippingFixedOption.getKey());

		CommerceOrder commerceOrder = CommerceTestUtil.addB2BCommerceOrder(
			_group.getGroupId(), _user.getUserId(),
			_accountEntry.getAccountEntryId(),
			_commerceCurrency.getCommerceCurrencyId());

		Assert.assertEquals(
			commerceAddress.getCommerceAddressId(),
			commerceOrder.getBillingAddressId());
		Assert.assertEquals(0, commerceOrder.getCommerceShippingMethodId());
		Assert.assertEquals(
			_commerceDeliveryTerm.getCommerceTermEntryId(),
			commerceOrder.getDeliveryCommerceTermEntryId());
		Assert.assertEquals(
			_commercePaymentTerm.getCommerceTermEntryId(),
			commerceOrder.getPaymentCommerceTermEntryId());
		Assert.assertEquals(
			commerceAddress.getCommerceAddressId(),
			commerceOrder.getShippingAddressId());
		Assert.assertEquals(
			_commercePaymentMethodGroupRel.getPaymentIntegrationKey(),
			commerceOrder.getCommercePaymentMethodKey());
		Assert.assertEquals(
			StringPool.BLANK, commerceOrder.getShippingOptionName());

		CPInstance cpInstance = CPTestUtil.addCPInstanceWithRandomSku(
			_group.getGroupId(), BigDecimal.valueOf(34.90));

		CommerceTestUtil.addCommerceOrderItem(
			commerceOrder.getCommerceOrderId(), cpInstance.getCPInstanceId(),
			BigDecimal.ONE);

		commerceOrder = _commerceOrderLocalService.getCommerceOrder(
			commerceOrder.getCommerceOrderId());

		Assert.assertEquals(
			_commerceShippingMethod.getCommerceShippingMethodId(),
			commerceOrder.getCommerceShippingMethodId());
		Assert.assertEquals(
			_commerceShippingFixedOption.getKey(),
			commerceOrder.getShippingOptionName());
	}

	private AccountEntry _accountEntry;

	@Inject
	private CommerceAddressLocalService _commerceAddressLocalService;

	private CommerceChannel _commerceChannel;

	@Inject
	private CommerceChannelAccountEntryRelLocalService
		_commerceChannelAccountEntryRelLocalService;

	private CommerceCurrency _commerceCurrency;
	private CommerceTermEntry _commerceDeliveryTerm;

	@Inject
	private CommerceOrderLocalService _commerceOrderLocalService;

	private CommercePaymentMethodGroupRel _commercePaymentMethodGroupRel;

	@Inject
	private CommercePaymentMethodGroupRelLocalService
		_commercePaymentMethodGroupRelLocalService;

	private CommerceTermEntry _commercePaymentTerm;
	private CommerceShippingFixedOption _commerceShippingFixedOption;

	@Inject
	private CommerceShippingFixedOptionLocalService
		_commerceShippingFixedOptionLocalService;

	private CommerceShippingMethod _commerceShippingMethod;

	@Inject
	private CommerceShippingMethodLocalService
		_commerceShippingMethodLocalService;

	@Inject
	private CommerceShippingOptionAccountEntryRelService
		_commerceShippingOptionAccountEntryRelService;

	@Inject
	private CommerceTermEntryLocalService _commerceTermEntryLocalService;

	private Company _company;
	private Country _country;

	@Inject
	private CountryLocalService _countryLocalService;

	private Group _group;
	private Region _region;

	@Inject
	private RegionLocalService _regionLocalService;

	private ServiceContext _serviceContext;
	private User _user;

}