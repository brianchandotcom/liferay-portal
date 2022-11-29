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

package com.liferay.headless.commerce.admin.account.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.account.constants.CommerceAccountConstants;
import com.liferay.commerce.account.model.CommerceAccount;
import com.liferay.commerce.account.service.CommerceAccountLocalServiceUtil;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.service.CommerceCurrencyLocalServiceUtil;
import com.liferay.commerce.discount.constants.CommerceDiscountConstants;
import com.liferay.commerce.discount.model.CommerceDiscount;
import com.liferay.commerce.discount.service.CommerceDiscountLocalServiceUtil;
import com.liferay.commerce.model.CommerceAddress;
import com.liferay.commerce.price.list.constants.CommercePriceListConstants;
import com.liferay.commerce.price.list.model.CommercePriceList;
import com.liferay.commerce.price.list.service.CommercePriceListLocalServiceUtil;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CommerceChannelLocalServiceUtil;
import com.liferay.commerce.service.CommerceAddressLocalServiceUtil;
import com.liferay.commerce.term.constants.CommerceTermEntryConstants;
import com.liferay.commerce.term.model.CommerceTermEntry;
import com.liferay.commerce.term.service.CommerceTermEntryLocalServiceUtil;
import com.liferay.commerce.test.util.CommerceTestUtil;
import com.liferay.headless.commerce.admin.account.client.dto.v1_0.AccountChannelEntry;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.math.BigDecimal;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;

/**
 * @author Alessio Antonio Rendina
 */
@RunWith(Arquillian.class)
public class AccountChannelEntryResourceTest
	extends BaseAccountChannelEntryResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_user = UserTestUtil.addUser(testCompany);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				testCompany.getCompanyId(), testGroup.getGroupId(),
				_user.getUserId());

		_commerceAccount = CommerceAccountLocalServiceUtil.addCommerceAccount(
			RandomTestUtil.randomString(),
			CommerceAccountConstants.DEFAULT_PARENT_ACCOUNT_ID,
			RandomTestUtil.randomString() + "@liferay.com", null,
			CommerceAccountConstants.ACCOUNT_TYPE_BUSINESS, true,
			RandomTestUtil.randomString(), serviceContext);

		_commerceAddress = CommerceTestUtil.addUserCommerceAddress(
			testGroup.getGroupId(), _user.getUserId());

		_commerceChannel1 = CommerceTestUtil.addCommerceChannel(
			testGroup.getGroupId(), RandomTestUtil.randomString());

		_commerceChannel2 = CommerceTestUtil.addCommerceChannel(
			testGroup.getGroupId(), RandomTestUtil.randomString());

		_commerceChannel3 = CommerceTestUtil.addCommerceChannel(
			testGroup.getGroupId(), RandomTestUtil.randomString());

		_commerceChannels = new CommerceChannel[] {
			_commerceChannel1, _commerceChannel2, _commerceChannel3
		};

		_commerceCurrency =
			CommerceCurrencyLocalServiceUtil.addCommerceCurrency(
				_user.getUserId(), RandomTestUtil.randomString(),
				Collections.singletonMap(
					LocaleUtil.getSiteDefault(), RandomTestUtil.randomString()),
				RandomTestUtil.randomString(), BigDecimal.ONE, new HashMap<>(),
				2, 2, "HALF_EVEN", false, 0, true);

		_commerceDeliveryTerm =
			CommerceTermEntryLocalServiceUtil.addCommerceTermEntry(
				RandomTestUtil.randomString(), _user.getUserId(), true,
				Collections.singletonMap(
					LocaleUtil.getSiteDefault(), RandomTestUtil.randomString()),
				1, 1, 2022, 12, 0, 0, 0, 0, 0, 0, true,
				Collections.singletonMap(
					LocaleUtil.getSiteDefault(), RandomTestUtil.randomString()),
				RandomTestUtil.randomString(), 1000,
				CommerceTermEntryConstants.TYPE_DELIVERY_TERMS, null,
				serviceContext);

		_commerceDiscount =
			CommerceDiscountLocalServiceUtil.addCommerceDiscount(
				RandomTestUtil.randomString(), _user.getUserId(),
				RandomTestUtil.randomString(),
				CommerceDiscountConstants.TARGET_CATEGORIES, false, null, true,
				BigDecimal.ZERO, StringPool.BLANK, BigDecimal.TEN,
				BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO,
				CommerceDiscountConstants.LIMITATION_TYPE_UNLIMITED, 0, true,
				true, 1, 1, 2022, 12, 0, 0, 0, 0, 0, 0, true, serviceContext);

		_commercePaymentTerm =
			CommerceTermEntryLocalServiceUtil.addCommerceTermEntry(
				RandomTestUtil.randomString(), _user.getUserId(), true,
				Collections.singletonMap(
					LocaleUtil.getSiteDefault(), RandomTestUtil.randomString()),
				1, 1, 2022, 12, 0, 0, 0, 0, 0, 0, true,
				Collections.singletonMap(
					LocaleUtil.getSiteDefault(), RandomTestUtil.randomString()),
				RandomTestUtil.randomString(), 1000,
				CommerceTermEntryConstants.TYPE_PAYMENT_TERMS, null,
				serviceContext);

		_commercePriceList =
			CommercePriceListLocalServiceUtil.addCommercePriceList(
				RandomTestUtil.randomString(), testGroup.getGroupId(),
				_user.getUserId(), _commerceCurrency.getCommerceCurrencyId(),
				true, CommercePriceListConstants.TYPE_PRICE_LIST, 0, true,
				RandomTestUtil.randomString(), 1000, 1, 1, 2022, 12, 0, 0, 0, 0,
				0, 0, true, serviceContext);

		_commerceUser = UserLocalServiceUtil.addUser(
			_user.getUserId(), testCompany.getCompanyId(), true,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(), true,
			RandomTestUtil.randomString(),
			RandomTestUtil.randomString() + "@liferay.com",
			LocaleUtil.getSiteDefault(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(), 0, 0,
			true, 1, 1, 2022, RandomTestUtil.randomString(), null, null, null,
			null, false, serviceContext);

		Role role = RoleLocalServiceUtil.getRole(
			testCompany.getCompanyId(), RoleConstants.ADMINISTRATOR);

		UserLocalServiceUtil.addRoleUser(
			role.getRoleId(), _commerceUser.getUserId());
	}

	@After
	@Override
	public void tearDown() throws Exception {
		super.tearDown();

		CommerceAddressLocalServiceUtil.deleteCommerceAddress(_commerceAddress);
		CommerceCurrencyLocalServiceUtil.deleteCommerceCurrency(
			_commerceCurrency);
		CommerceAccountLocalServiceUtil.deleteCommerceAccount(_commerceAccount);
		CommerceChannelLocalServiceUtil.deleteCommerceChannel(
			_commerceChannel1);
		CommerceChannelLocalServiceUtil.deleteCommerceChannel(
			_commerceChannel2);
		CommerceChannelLocalServiceUtil.deleteCommerceChannel(
			_commerceChannel3);
		CommerceDiscountLocalServiceUtil.deleteCommerceDiscount(
			_commerceDiscount);
		CommerceTermEntryLocalServiceUtil.deleteCommerceTermEntry(
			_commerceDeliveryTerm);
		CommerceTermEntryLocalServiceUtil.deleteCommerceTermEntry(
			_commercePaymentTerm);
		UserLocalServiceUtil.deleteUser(_commerceUser);
		UserLocalServiceUtil.deleteUser(_user);
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"overrideEligibility", "priority"};
	}

	@Override
	protected AccountChannelEntry randomAccountChannelEntry() throws Exception {
		return new AccountChannelEntry() {
			{
				accountExternalReferenceCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				accountId = RandomTestUtil.randomLong();
				channelExternalReferenceCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				channelId = _getFirstAvailableChannel().getPrimaryKey();
				classExternalReferenceCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				classPK = RandomTestUtil.randomLong();
				id = RandomTestUtil.randomLong();
				overrideEligibility = RandomTestUtil.randomBoolean();
				priority = RandomTestUtil.randomDouble();
			}
		};
	}

	@Override
	protected AccountChannelEntry randomPatchAccountChannelEntry()
		throws Exception {

		return new AccountChannelEntry() {
			{
				accountExternalReferenceCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				accountId = RandomTestUtil.randomLong();
				channelExternalReferenceCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				channelId = RandomTestUtil.randomLong();
				classExternalReferenceCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				id = RandomTestUtil.randomLong();
				overrideEligibility = RandomTestUtil.randomBoolean();
				priority = RandomTestUtil.randomDouble();
			}
		};
	}

	@Override
	protected AccountChannelEntry
			testDeleteAccountChannelBillingAddressId_addAccountChannelEntry()
		throws Exception {

		return _postAccountChannelEntryBillingAddress();
	}

	@Override
	protected AccountChannelEntry
			testDeleteAccountChannelCurrencyId_addAccountChannelEntry()
		throws Exception {

		return _postAccountChannelEntryCurrency();
	}

	@Override
	protected AccountChannelEntry
			testDeleteAccountChannelDeliveryTermId_addAccountChannelEntry()
		throws Exception {

		return _postAccountChannelEntryDeliveryTerm();
	}

	@Override
	protected AccountChannelEntry
			testDeleteAccountChannelDiscountId_addAccountChannelEntry()
		throws Exception {

		return _postAccountChannelEntryDiscount();
	}

	@Override
	protected AccountChannelEntry
			testDeleteAccountChannelPaymentTermId_addAccountChannelEntry()
		throws Exception {

		return _postAccountChannelEntryPaymentTerm();
	}

	@Override
	protected AccountChannelEntry
			testDeleteAccountChannelPriceListId_addAccountChannelEntry()
		throws Exception {

		return _postAccountChannelEntryPriceList();
	}

	@Override
	protected AccountChannelEntry
			testDeleteAccountChannelShippingAddressId_addAccountChannelEntry()
		throws Exception {

		return _postAccountChannelEntryShippingAddress();
	}

	@Override
	protected AccountChannelEntry
			testDeleteAccountChannelUserId_addAccountChannelEntry()
		throws Exception {

		return _postAccountChannelEntryUser();
	}

	@Override
	protected AccountChannelEntry
			testGetAccountByExternalReferenceCodeAccountChannelBillingAddressesPage_addAccountChannelEntry(
				String externalReferenceCode,
				AccountChannelEntry accountChannelEntry)
		throws Exception {

		return _postAccountByExternalReferenceCodeChannelEntryBillingAddress(
			externalReferenceCode, accountChannelEntry);
	}

	@Override
	protected String
			testGetAccountByExternalReferenceCodeAccountChannelBillingAddressesPage_getExternalReferenceCode()
		throws Exception {

		return _commerceAccount.getExternalReferenceCode();
	}

	@Override
	protected AccountChannelEntry
			testGetAccountByExternalReferenceCodeAccountChannelCurrenciesPage_addAccountChannelEntry(
				String externalReferenceCode,
				AccountChannelEntry accountChannelEntry)
		throws Exception {

		return _postAccountByExternalReferenceCodeChannelEntryCurrency(
			externalReferenceCode, accountChannelEntry);
	}

	@Override
	protected String
			testGetAccountByExternalReferenceCodeAccountChannelCurrenciesPage_getExternalReferenceCode()
		throws Exception {

		return _commerceAccount.getExternalReferenceCode();
	}

	@Override
	protected AccountChannelEntry
			testGetAccountByExternalReferenceCodeAccountChannelDeliveryTermsPage_addAccountChannelEntry(
				String externalReferenceCode,
				AccountChannelEntry accountChannelEntry)
		throws Exception {

		return _postAccountByExternalReferenceCodeChannelEntryDeliveryTerm(
			externalReferenceCode, accountChannelEntry);
	}

	@Override
	protected String
			testGetAccountByExternalReferenceCodeAccountChannelDeliveryTermsPage_getExternalReferenceCode()
		throws Exception {

		return _commerceAccount.getExternalReferenceCode();
	}

	@Override
	protected AccountChannelEntry
			testGetAccountByExternalReferenceCodeAccountChannelDiscountsPage_addAccountChannelEntry(
				String externalReferenceCode,
				AccountChannelEntry accountChannelEntry)
		throws Exception {

		return _postAccountByExternalReferenceCodeChannelEntryDiscount(
			externalReferenceCode, accountChannelEntry);
	}

	@Override
	protected String
			testGetAccountByExternalReferenceCodeAccountChannelDiscountsPage_getExternalReferenceCode()
		throws Exception {

		return _commerceAccount.getExternalReferenceCode();
	}

	@Override
	protected AccountChannelEntry
			testGetAccountByExternalReferenceCodeAccountChannelPaymentTermsPage_addAccountChannelEntry(
				String externalReferenceCode,
				AccountChannelEntry accountChannelEntry)
		throws Exception {

		return _postAccountByExternalReferenceCodeChannelEntryPaymentTerm(
			externalReferenceCode, accountChannelEntry);
	}

	@Override
	protected String
			testGetAccountByExternalReferenceCodeAccountChannelPaymentTermsPage_getExternalReferenceCode()
		throws Exception {

		return _commerceAccount.getExternalReferenceCode();
	}

	@Override
	protected AccountChannelEntry
			testGetAccountByExternalReferenceCodeAccountChannelPriceListsPage_addAccountChannelEntry(
				String externalReferenceCode,
				AccountChannelEntry accountChannelEntry)
		throws Exception {

		return _postAccountByExternalReferenceCodeChannelEntryPriceList(
			externalReferenceCode, accountChannelEntry);
	}

	@Override
	protected String
			testGetAccountByExternalReferenceCodeAccountChannelPriceListsPage_getExternalReferenceCode()
		throws Exception {

		return _commerceAccount.getExternalReferenceCode();
	}

	@Override
	protected AccountChannelEntry
			testGetAccountByExternalReferenceCodeAccountChannelShippingAddressesPage_addAccountChannelEntry(
				String externalReferenceCode,
				AccountChannelEntry accountChannelEntry)
		throws Exception {

		return _postAccountByExternalReferenceCodeChannelEntryShippingAddress(
			externalReferenceCode, accountChannelEntry);
	}

	@Override
	protected String
			testGetAccountByExternalReferenceCodeAccountChannelShippingAddressesPage_getExternalReferenceCode()
		throws Exception {

		return _commerceAccount.getExternalReferenceCode();
	}

	@Override
	protected AccountChannelEntry
			testGetAccountByExternalReferenceCodeAccountChannelUsersPage_addAccountChannelEntry(
				String externalReferenceCode,
				AccountChannelEntry accountChannelEntry)
		throws Exception {

		return _postAccountByExternalReferenceCodeChannelEntryUser(
			externalReferenceCode, accountChannelEntry);
	}

	@Override
	protected String
			testGetAccountByExternalReferenceCodeAccountChannelUsersPage_getExternalReferenceCode()
		throws Exception {

		return _commerceAccount.getExternalReferenceCode();
	}

	@Override
	protected AccountChannelEntry
			testGetAccountChannelBillingAddressId_addAccountChannelEntry()
		throws Exception {

		return _postAccountChannelEntryBillingAddress();
	}

	@Override
	protected AccountChannelEntry
			testGetAccountChannelCurrencyId_addAccountChannelEntry()
		throws Exception {

		return _postAccountChannelEntryCurrency();
	}

	@Override
	protected AccountChannelEntry
			testGetAccountChannelDeliveryTermId_addAccountChannelEntry()
		throws Exception {

		return _postAccountChannelEntryDeliveryTerm();
	}

	@Override
	protected AccountChannelEntry
			testGetAccountChannelDiscountId_addAccountChannelEntry()
		throws Exception {

		return _postAccountChannelEntryDiscount();
	}

	@Override
	protected AccountChannelEntry
			testGetAccountChannelPaymentTermId_addAccountChannelEntry()
		throws Exception {

		return _postAccountChannelEntryPaymentTerm();
	}

	@Override
	protected AccountChannelEntry
			testGetAccountChannelPriceListId_addAccountChannelEntry()
		throws Exception {

		return _postAccountChannelEntryPriceList();
	}

	@Override
	protected AccountChannelEntry
			testGetAccountChannelShippingAddressId_addAccountChannelEntry()
		throws Exception {

		return _postAccountChannelEntryShippingAddress();
	}

	@Override
	protected AccountChannelEntry
			testGetAccountChannelUserId_addAccountChannelEntry()
		throws Exception {

		return _postAccountChannelEntryUser();
	}

	@Override
	protected AccountChannelEntry
			testGetAccountIdAccountChannelBillingAddressesPage_addAccountChannelEntry(
				Long id, AccountChannelEntry accountChannelEntry)
		throws Exception {

		return _postAccountIdChannelEntryBillingAddress(
			id, accountChannelEntry);
	}

	@Override
	protected Long testGetAccountIdAccountChannelBillingAddressesPage_getId()
		throws Exception {

		return _commerceAccount.getCommerceAccountId();
	}

	@Override
	protected AccountChannelEntry
			testGetAccountIdAccountChannelCurrenciesPage_addAccountChannelEntry(
				Long id, AccountChannelEntry accountChannelEntry)
		throws Exception {

		return _postAccountIdChannelEntryCurrency(id, accountChannelEntry);
	}

	@Override
	protected Long testGetAccountIdAccountChannelCurrenciesPage_getId()
		throws Exception {

		return _commerceAccount.getCommerceAccountId();
	}

	@Override
	protected AccountChannelEntry
			testGetAccountIdAccountChannelDeliveryTermsPage_addAccountChannelEntry(
				Long id, AccountChannelEntry accountChannelEntry)
		throws Exception {

		return _postAccountIdChannelEntryDeliveryTerm(id, accountChannelEntry);
	}

	@Override
	protected Long testGetAccountIdAccountChannelDeliveryTermsPage_getId()
		throws Exception {

		return _commerceAccount.getCommerceAccountId();
	}

	@Override
	protected AccountChannelEntry
			testGetAccountIdAccountChannelDiscountsPage_addAccountChannelEntry(
				Long id, AccountChannelEntry accountChannelEntry)
		throws Exception {

		return _postAccountIdChannelEntryDiscount(id, accountChannelEntry);
	}

	@Override
	protected Long testGetAccountIdAccountChannelDiscountsPage_getId()
		throws Exception {

		return _commerceAccount.getCommerceAccountId();
	}

	@Override
	protected AccountChannelEntry
			testGetAccountIdAccountChannelPaymentTermsPage_addAccountChannelEntry(
				Long id, AccountChannelEntry accountChannelEntry)
		throws Exception {

		return _postAccountIdChannelEntryPaymentTerm(id, accountChannelEntry);
	}

	@Override
	protected Long testGetAccountIdAccountChannelPaymentTermsPage_getId()
		throws Exception {

		return _commerceAccount.getCommerceAccountId();
	}

	@Override
	protected AccountChannelEntry
			testGetAccountIdAccountChannelPriceListsPage_addAccountChannelEntry(
				Long id, AccountChannelEntry accountChannelEntry)
		throws Exception {

		return _postAccountIdChannelEntryPriceList(id, accountChannelEntry);
	}

	@Override
	protected Long testGetAccountIdAccountChannelPriceListsPage_getId()
		throws Exception {

		return _commerceAccount.getCommerceAccountId();
	}

	@Override
	protected AccountChannelEntry
			testGetAccountIdAccountChannelShippingAddressesPage_addAccountChannelEntry(
				Long id, AccountChannelEntry accountChannelEntry)
		throws Exception {

		return _postAccountIdChannelEntryShippingAddress(
			id, accountChannelEntry);
	}

	@Override
	protected Long testGetAccountIdAccountChannelShippingAddressesPage_getId()
		throws Exception {

		return _commerceAccount.getCommerceAccountId();
	}

	@Override
	protected AccountChannelEntry
			testGetAccountIdAccountChannelUsersPage_addAccountChannelEntry(
				Long id, AccountChannelEntry accountChannelEntry)
		throws Exception {

		return _postAccountIdChannelEntryUser(id, accountChannelEntry);
	}

	@Override
	protected Long testGetAccountIdAccountChannelUsersPage_getId()
		throws Exception {

		return _commerceAccount.getCommerceAccountId();
	}

	@Override
	protected AccountChannelEntry
			testGraphQLGetAccountChannelBillingAddressId_addAccountChannelEntry()
		throws Exception {

		return _postAccountChannelEntryBillingAddress();
	}

	@Override
	protected AccountChannelEntry
			testGraphQLGetAccountChannelCurrencyId_addAccountChannelEntry()
		throws Exception {

		return _postAccountChannelEntryCurrency();
	}

	@Override
	protected AccountChannelEntry
			testGraphQLGetAccountChannelDeliveryTermId_addAccountChannelEntry()
		throws Exception {

		return _postAccountChannelEntryDeliveryTerm();
	}

	@Override
	protected AccountChannelEntry
			testGraphQLGetAccountChannelDiscountId_addAccountChannelEntry()
		throws Exception {

		return _postAccountChannelEntryDiscount();
	}

	@Override
	protected AccountChannelEntry
			testGraphQLGetAccountChannelPaymentTermId_addAccountChannelEntry()
		throws Exception {

		return _postAccountChannelEntryPaymentTerm();
	}

	@Override
	protected AccountChannelEntry
			testGraphQLGetAccountChannelPriceListId_addAccountChannelEntry()
		throws Exception {

		return _postAccountChannelEntryPriceList();
	}

	@Override
	protected AccountChannelEntry
			testGraphQLGetAccountChannelShippingAddressId_addAccountChannelEntry()
		throws Exception {

		return _postAccountChannelEntryShippingAddress();
	}

	@Override
	protected AccountChannelEntry
			testGraphQLGetAccountChannelUserId_addAccountChannelEntry()
		throws Exception {

		return _postAccountChannelEntryUser();
	}

	@Override
	protected AccountChannelEntry
			testPatchAccountChannelBillingAddressId_addAccountChannelEntry()
		throws Exception {

		return _postAccountChannelEntryBillingAddress();
	}

	@Override
	protected AccountChannelEntry
			testPatchAccountChannelCurrencyId_addAccountChannelEntry()
		throws Exception {

		return _postAccountChannelEntryCurrency();
	}

	@Override
	protected AccountChannelEntry
			testPatchAccountChannelDeliveryTermId_addAccountChannelEntry()
		throws Exception {

		return _postAccountChannelEntryDeliveryTerm();
	}

	@Override
	protected AccountChannelEntry
			testPatchAccountChannelDiscountId_addAccountChannelEntry()
		throws Exception {

		return _postAccountChannelEntryDiscount();
	}

	@Override
	protected AccountChannelEntry
			testPatchAccountChannelPaymentTermId_addAccountChannelEntry()
		throws Exception {

		return _postAccountChannelEntryPaymentTerm();
	}

	@Override
	protected AccountChannelEntry
			testPatchAccountChannelPriceListId_addAccountChannelEntry()
		throws Exception {

		return _postAccountChannelEntryPriceList();
	}

	@Override
	protected AccountChannelEntry
			testPatchAccountChannelShippingAddressId_addAccountChannelEntry()
		throws Exception {

		return _postAccountChannelEntryShippingAddress();
	}

	@Override
	protected AccountChannelEntry
			testPatchAccountChannelUserId_addAccountChannelEntry()
		throws Exception {

		return _postAccountChannelEntryUser();
	}

	@Override
	protected AccountChannelEntry
			testPostAccountByExternalReferenceCodeAccountChannelBillingAddress_addAccountChannelEntry(
				AccountChannelEntry accountChannelEntry)
		throws Exception {

		return _postAccountByExternalReferenceCodeChannelEntryBillingAddress(
			_commerceAccount.getExternalReferenceCode(), accountChannelEntry);
	}

	@Override
	protected AccountChannelEntry
			testPostAccountByExternalReferenceCodeAccountChannelCurrency_addAccountChannelEntry(
				AccountChannelEntry accountChannelEntry)
		throws Exception {

		return _postAccountByExternalReferenceCodeChannelEntryCurrency(
			_commerceAccount.getExternalReferenceCode(), accountChannelEntry);
	}

	@Override
	protected AccountChannelEntry
			testPostAccountByExternalReferenceCodeAccountChannelDeliveryTerm_addAccountChannelEntry(
				AccountChannelEntry accountChannelEntry)
		throws Exception {

		return _postAccountByExternalReferenceCodeChannelEntryDeliveryTerm(
			_commerceAccount.getExternalReferenceCode(), accountChannelEntry);
	}

	@Override
	protected AccountChannelEntry
			testPostAccountByExternalReferenceCodeAccountChannelDiscount_addAccountChannelEntry(
				AccountChannelEntry accountChannelEntry)
		throws Exception {

		return _postAccountByExternalReferenceCodeChannelEntryDiscount(
			_commerceAccount.getExternalReferenceCode(), accountChannelEntry);
	}

	@Override
	protected AccountChannelEntry
			testPostAccountByExternalReferenceCodeAccountChannelPaymentTerm_addAccountChannelEntry(
				AccountChannelEntry accountChannelEntry)
		throws Exception {

		return _postAccountByExternalReferenceCodeChannelEntryPaymentTerm(
			_commerceAccount.getExternalReferenceCode(), accountChannelEntry);
	}

	@Override
	protected AccountChannelEntry
			testPostAccountByExternalReferenceCodeAccountChannelPriceList_addAccountChannelEntry(
				AccountChannelEntry accountChannelEntry)
		throws Exception {

		return _postAccountByExternalReferenceCodeChannelEntryPriceList(
			_commerceAccount.getExternalReferenceCode(), accountChannelEntry);
	}

	@Override
	protected AccountChannelEntry
			testPostAccountByExternalReferenceCodeAccountChannelShippingAddress_addAccountChannelEntry(
				AccountChannelEntry accountChannelEntry)
		throws Exception {

		return _postAccountByExternalReferenceCodeChannelEntryShippingAddress(
			_commerceAccount.getExternalReferenceCode(), accountChannelEntry);
	}

	@Override
	protected AccountChannelEntry
			testPostAccountByExternalReferenceCodeAccountChannelUser_addAccountChannelEntry(
				AccountChannelEntry accountChannelEntry)
		throws Exception {

		return _postAccountByExternalReferenceCodeChannelEntryUser(
			_commerceAccount.getExternalReferenceCode(), accountChannelEntry);
	}

	@Override
	protected AccountChannelEntry
			testPostAccountIdAccountChannelBillingAddress_addAccountChannelEntry(
				AccountChannelEntry accountChannelEntry)
		throws Exception {

		return _postAccountIdChannelEntryBillingAddress(
			_commerceAccount.getCommerceAccountId(), accountChannelEntry);
	}

	@Override
	protected AccountChannelEntry
			testPostAccountIdAccountChannelCurrency_addAccountChannelEntry(
				AccountChannelEntry accountChannelEntry)
		throws Exception {

		return _postAccountIdChannelEntryCurrency(
			_commerceAccount.getCommerceAccountId(), accountChannelEntry);
	}

	@Override
	protected AccountChannelEntry
			testPostAccountIdAccountChannelDeliveryTerm_addAccountChannelEntry(
				AccountChannelEntry accountChannelEntry)
		throws Exception {

		return _postAccountIdChannelEntryDeliveryTerm(
			_commerceAccount.getCommerceAccountId(), accountChannelEntry);
	}

	@Override
	protected AccountChannelEntry
			testPostAccountIdAccountChannelDiscount_addAccountChannelEntry(
				AccountChannelEntry accountChannelEntry)
		throws Exception {

		return _postAccountIdChannelEntryDiscount(
			_commerceAccount.getCommerceAccountId(), accountChannelEntry);
	}

	@Override
	protected AccountChannelEntry
			testPostAccountIdAccountChannelPaymentTerm_addAccountChannelEntry(
				AccountChannelEntry accountChannelEntry)
		throws Exception {

		return _postAccountIdChannelEntryPaymentTerm(
			_commerceAccount.getCommerceAccountId(), accountChannelEntry);
	}

	@Override
	protected AccountChannelEntry
			testPostAccountIdAccountChannelPriceList_addAccountChannelEntry(
				AccountChannelEntry accountChannelEntry)
		throws Exception {

		return _postAccountIdChannelEntryPriceList(
			_commerceAccount.getCommerceAccountId(), accountChannelEntry);
	}

	@Override
	protected AccountChannelEntry
			testPostAccountIdAccountChannelShippingAddress_addAccountChannelEntry(
				AccountChannelEntry accountChannelEntry)
		throws Exception {

		return _postAccountIdChannelEntryShippingAddress(
			_commerceAccount.getCommerceAccountId(), accountChannelEntry);
	}

	@Override
	protected AccountChannelEntry
			testPostAccountIdAccountChannelUser_addAccountChannelEntry(
				AccountChannelEntry accountChannelEntry)
		throws Exception {

		return _postAccountIdChannelEntryUser(
			_commerceAccount.getCommerceAccountId(), accountChannelEntry);
	}

	private CommerceChannel _getFirstAvailableChannel() {
		CommerceChannel commerceChannel = _commerceChannels[0];
		_commerceChannels = Arrays.copyOfRange(
			_commerceChannels, 1, _commerceChannels.length);

		return commerceChannel;
	}

	private AccountChannelEntry
			_postAccountByExternalReferenceCodeChannelEntryBillingAddress(
				String externalReferenceCode,
				AccountChannelEntry accountChannelEntry)
		throws Exception {

		return accountChannelEntryResource.
			postAccountByExternalReferenceCodeAccountChannelBillingAddress(
				externalReferenceCode,
				_setChannelEntryAsBillingAddress(accountChannelEntry));
	}

	private AccountChannelEntry
			_postAccountByExternalReferenceCodeChannelEntryCurrency(
				String externalReferenceCode,
				AccountChannelEntry accountChannelEntry)
		throws Exception {

		return accountChannelEntryResource.
			postAccountByExternalReferenceCodeAccountChannelCurrency(
				externalReferenceCode,
				_setChannelEntryAsCurrency(accountChannelEntry));
	}

	private AccountChannelEntry
			_postAccountByExternalReferenceCodeChannelEntryDeliveryTerm(
				String externalReferenceCode,
				AccountChannelEntry accountChannelEntry)
		throws Exception {

		return accountChannelEntryResource.
			postAccountByExternalReferenceCodeAccountChannelDeliveryTerm(
				externalReferenceCode,
				_setChannelEntryAsDeliveryTerm(accountChannelEntry));
	}

	private AccountChannelEntry
			_postAccountByExternalReferenceCodeChannelEntryDiscount(
				String externalReferenceCode,
				AccountChannelEntry accountChannelEntry)
		throws Exception {

		return accountChannelEntryResource.
			postAccountByExternalReferenceCodeAccountChannelDiscount(
				externalReferenceCode,
				_setChannelEntryAsDiscount(accountChannelEntry));
	}

	private AccountChannelEntry
			_postAccountByExternalReferenceCodeChannelEntryPaymentTerm(
				String externalReferenceCode,
				AccountChannelEntry accountChannelEntry)
		throws Exception {

		return accountChannelEntryResource.
			postAccountByExternalReferenceCodeAccountChannelPaymentTerm(
				externalReferenceCode,
				_setChannelEntryAsPaymentTerm(accountChannelEntry));
	}

	private AccountChannelEntry
			_postAccountByExternalReferenceCodeChannelEntryPriceList(
				String externalReferenceCode,
				AccountChannelEntry accountChannelEntry)
		throws Exception {

		return accountChannelEntryResource.
			postAccountByExternalReferenceCodeAccountChannelPriceList(
				externalReferenceCode,
				_setChannelEntryAsPriceList(accountChannelEntry));
	}

	private AccountChannelEntry
			_postAccountByExternalReferenceCodeChannelEntryShippingAddress(
				String externalReferenceCode,
				AccountChannelEntry accountChannelEntry)
		throws Exception {

		return accountChannelEntryResource.
			postAccountByExternalReferenceCodeAccountChannelShippingAddress(
				externalReferenceCode,
				_setChannelEntryAsShippingAddress(accountChannelEntry));
	}

	private AccountChannelEntry
			_postAccountByExternalReferenceCodeChannelEntryUser(
				String externalReferenceCode,
				AccountChannelEntry accountChannelEntry)
		throws Exception {

		return accountChannelEntryResource.
			postAccountByExternalReferenceCodeAccountChannelUser(
				externalReferenceCode,
				_setChannelEntryAsUser(accountChannelEntry));
	}

	private AccountChannelEntry _postAccountChannelEntryBillingAddress()
		throws Exception {

		AccountChannelEntry accountChannelEntry =
			_setChannelEntryAsBillingAddress(randomAccountChannelEntry());

		return accountChannelEntryResource.
			postAccountIdAccountChannelBillingAddress(
				_commerceAccount.getCommerceAccountId(), accountChannelEntry);
	}

	private AccountChannelEntry _postAccountChannelEntryCurrency()
		throws Exception {

		AccountChannelEntry accountChannelEntry = _setChannelEntryAsCurrency(
			randomAccountChannelEntry());

		return accountChannelEntryResource.postAccountIdAccountChannelCurrency(
			_commerceAccount.getCommerceAccountId(), accountChannelEntry);
	}

	private AccountChannelEntry _postAccountChannelEntryDeliveryTerm()
		throws Exception {

		AccountChannelEntry accountChannelEntry =
			_setChannelEntryAsDeliveryTerm(randomAccountChannelEntry());

		return accountChannelEntryResource.
			postAccountIdAccountChannelDeliveryTerm(
				_commerceAccount.getCommerceAccountId(), accountChannelEntry);
	}

	private AccountChannelEntry _postAccountChannelEntryDiscount()
		throws Exception {

		AccountChannelEntry accountChannelEntry = _setChannelEntryAsDiscount(
			randomAccountChannelEntry());

		return accountChannelEntryResource.postAccountIdAccountChannelDiscount(
			_commerceAccount.getCommerceAccountId(), accountChannelEntry);
	}

	private AccountChannelEntry _postAccountChannelEntryPaymentTerm()
		throws Exception {

		AccountChannelEntry accountChannelEntry = _setChannelEntryAsPaymentTerm(
			randomAccountChannelEntry());

		return accountChannelEntryResource.
			postAccountIdAccountChannelPaymentTerm(
				_commerceAccount.getCommerceAccountId(), accountChannelEntry);
	}

	private AccountChannelEntry _postAccountChannelEntryPriceList()
		throws Exception {

		AccountChannelEntry accountChannelEntry = _setChannelEntryAsPriceList(
			randomAccountChannelEntry());

		return accountChannelEntryResource.postAccountIdAccountChannelPriceList(
			_commerceAccount.getCommerceAccountId(), accountChannelEntry);
	}

	private AccountChannelEntry _postAccountChannelEntryShippingAddress()
		throws Exception {

		AccountChannelEntry accountChannelEntry =
			_setChannelEntryAsShippingAddress(randomAccountChannelEntry());

		return accountChannelEntryResource.
			postAccountIdAccountChannelShippingAddress(
				_commerceAccount.getCommerceAccountId(), accountChannelEntry);
	}

	private AccountChannelEntry _postAccountChannelEntryUser()
		throws Exception {

		AccountChannelEntry accountChannelEntry = _setChannelEntryAsUser(
			randomAccountChannelEntry());

		return accountChannelEntryResource.postAccountIdAccountChannelUser(
			_commerceAccount.getCommerceAccountId(), accountChannelEntry);
	}

	private AccountChannelEntry _postAccountIdChannelEntryBillingAddress(
			Long id, AccountChannelEntry accountChannelEntry)
		throws Exception {

		return accountChannelEntryResource.
			postAccountIdAccountChannelBillingAddress(
				id, _setChannelEntryAsBillingAddress(accountChannelEntry));
	}

	private AccountChannelEntry _postAccountIdChannelEntryCurrency(
			Long id, AccountChannelEntry accountChannelEntry)
		throws Exception {

		return accountChannelEntryResource.postAccountIdAccountChannelCurrency(
			id, _setChannelEntryAsCurrency(accountChannelEntry));
	}

	private AccountChannelEntry _postAccountIdChannelEntryDeliveryTerm(
			Long id, AccountChannelEntry accountChannelEntry)
		throws Exception {

		return accountChannelEntryResource.
			postAccountIdAccountChannelDeliveryTerm(
				id, _setChannelEntryAsDeliveryTerm(accountChannelEntry));
	}

	private AccountChannelEntry _postAccountIdChannelEntryDiscount(
			Long id, AccountChannelEntry accountChannelEntry)
		throws Exception {

		return accountChannelEntryResource.postAccountIdAccountChannelDiscount(
			id, _setChannelEntryAsDiscount(accountChannelEntry));
	}

	private AccountChannelEntry _postAccountIdChannelEntryPaymentTerm(
			Long id, AccountChannelEntry accountChannelEntry)
		throws Exception {

		return accountChannelEntryResource.
			postAccountIdAccountChannelPaymentTerm(
				id, _setChannelEntryAsPaymentTerm(accountChannelEntry));
	}

	private AccountChannelEntry _postAccountIdChannelEntryPriceList(
			Long id, AccountChannelEntry accountChannelEntry)
		throws Exception {

		return accountChannelEntryResource.postAccountIdAccountChannelPriceList(
			id, _setChannelEntryAsPriceList(accountChannelEntry));
	}

	private AccountChannelEntry _postAccountIdChannelEntryShippingAddress(
			Long id, AccountChannelEntry accountChannelEntry)
		throws Exception {

		return accountChannelEntryResource.
			postAccountIdAccountChannelShippingAddress(
				id, _setChannelEntryAsShippingAddress(accountChannelEntry));
	}

	private AccountChannelEntry _postAccountIdChannelEntryUser(
			Long id, AccountChannelEntry accountChannelEntry)
		throws Exception {

		return accountChannelEntryResource.postAccountIdAccountChannelUser(
			id, _setChannelEntryAsUser(accountChannelEntry));
	}

	private AccountChannelEntry _setChannelEntryAsBillingAddress(
		AccountChannelEntry accountChannelEntry) {

		accountChannelEntry.setClassPK(_commerceAddress.getPrimaryKey());

		return accountChannelEntry;
	}

	private AccountChannelEntry _setChannelEntryAsCurrency(
		AccountChannelEntry accountChannelEntry) {

		accountChannelEntry.setClassPK(_commerceCurrency.getPrimaryKey());

		return accountChannelEntry;
	}

	private AccountChannelEntry _setChannelEntryAsDeliveryTerm(
		AccountChannelEntry accountChannelEntry) {

		accountChannelEntry.setClassPK(_commerceDeliveryTerm.getPrimaryKey());

		return accountChannelEntry;
	}

	private AccountChannelEntry _setChannelEntryAsDiscount(
		AccountChannelEntry accountChannelEntry) {

		accountChannelEntry.setClassPK(_commerceDiscount.getPrimaryKey());

		return accountChannelEntry;
	}

	private AccountChannelEntry _setChannelEntryAsPaymentTerm(
		AccountChannelEntry accountChannelEntry) {

		accountChannelEntry.setClassPK(_commercePaymentTerm.getPrimaryKey());

		return accountChannelEntry;
	}

	private AccountChannelEntry _setChannelEntryAsPriceList(
		AccountChannelEntry accountChannelEntry) {

		accountChannelEntry.setClassPK(_commercePriceList.getPrimaryKey());

		return accountChannelEntry;
	}

	private AccountChannelEntry _setChannelEntryAsShippingAddress(
		AccountChannelEntry accountChannelEntry) {

		accountChannelEntry.setClassPK(_commerceAddress.getPrimaryKey());

		return accountChannelEntry;
	}

	private AccountChannelEntry _setChannelEntryAsUser(
		AccountChannelEntry accountChannelEntry) {

		accountChannelEntry.setClassPK(_commerceUser.getPrimaryKey());

		return accountChannelEntry;
	}

	private CommerceAccount _commerceAccount;
	private CommerceAddress _commerceAddress;
	private CommerceChannel _commerceChannel1;
	private CommerceChannel _commerceChannel2;
	private CommerceChannel _commerceChannel3;
	private CommerceChannel[] _commerceChannels;
	private CommerceCurrency _commerceCurrency;
	private CommerceTermEntry _commerceDeliveryTerm;
	private CommerceDiscount _commerceDiscount;
	private CommerceTermEntry _commercePaymentTerm;
	private CommercePriceList _commercePriceList;
	private User _commerceUser;
	private User _user;

}