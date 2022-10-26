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

package com.liferay.headless.commerce.admin.account.internal.resource.v1_0;

import com.liferay.account.constants.AccountListTypeConstants;
import com.liferay.account.exception.NoSuchEntryException;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryService;
import com.liferay.commerce.account.constants.CommerceAccountActionKeys;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.service.CommerceCurrencyService;
import com.liferay.commerce.discount.model.CommerceDiscount;
import com.liferay.commerce.discount.service.CommerceDiscountService;
import com.liferay.commerce.price.list.model.CommercePriceList;
import com.liferay.commerce.price.list.service.CommercePriceListService;
import com.liferay.commerce.product.constants.CommerceChannelAccountEntryRelConstants;
import com.liferay.commerce.product.exception.CommerceChannelAccountEntryRelTypeException;
import com.liferay.commerce.product.exception.NoSuchChannelAccountEntryRelException;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.model.CommerceChannelAccountEntryRel;
import com.liferay.commerce.product.service.CommerceChannelAccountEntryRelService;
import com.liferay.commerce.product.service.CommerceChannelService;
import com.liferay.commerce.term.model.CommerceTermEntry;
import com.liferay.commerce.term.service.CommerceTermEntryService;
import com.liferay.headless.commerce.admin.account.dto.v1_0.AccountChannelEntry;
import com.liferay.headless.commerce.admin.account.internal.dto.v1_0.converter.AccountChannelEntryDTOConverter;
import com.liferay.headless.commerce.admin.account.resource.v1_0.AccountChannelEntryResource;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Address;
import com.liferay.portal.kernel.model.ListType;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.AddressService;
import com.liferay.portal.kernel.service.UserService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.TransformUtil;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/account-channel-entry.properties",
	scope = ServiceScope.PROTOTYPE, service = AccountChannelEntryResource.class
)
public class AccountChannelEntryResourceImpl
	extends BaseAccountChannelEntryResourceImpl {

	@Override
	public void deleteAccountChannelBillingAddressId(Long id) throws Exception {
		CommerceChannelAccountEntryRel commerceChannelAccountEntryRel =
			_fetchCommerceChannelAccountEntryRel(
				id,
				CommerceChannelAccountEntryRelConstants.TYPE_BILLING_ADDRESS);

		_commerceChannelAccountEntryRelService.
			deleteCommerceChannelAccountEntryRel(
				commerceChannelAccountEntryRel.
					getCommerceChannelAccountEntryRelId());
	}

	@Override
	public void deleteAccountChannelCurrencyId(Long id) throws Exception {
		CommerceChannelAccountEntryRel commerceChannelAccountEntryRel =
			_fetchCommerceChannelAccountEntryRel(
				id, CommerceChannelAccountEntryRelConstants.TYPE_CURRENCY);

		_commerceChannelAccountEntryRelService.
			deleteCommerceChannelAccountEntryRel(
				commerceChannelAccountEntryRel.
					getCommerceChannelAccountEntryRelId());
	}

	@Override
	public void deleteAccountChannelDeliveryTermId(Long id) throws Exception {
		CommerceChannelAccountEntryRel commerceChannelAccountEntryRel =
			_fetchCommerceChannelAccountEntryRel(
				id, CommerceChannelAccountEntryRelConstants.TYPE_DELIVERY_TERM);

		_commerceChannelAccountEntryRelService.
			deleteCommerceChannelAccountEntryRel(
				commerceChannelAccountEntryRel.
					getCommerceChannelAccountEntryRelId());
	}

	@Override
	public void deleteAccountChannelDiscountId(Long id) throws Exception {
		CommerceChannelAccountEntryRel commerceChannelAccountEntryRel =
			_fetchCommerceChannelAccountEntryRel(
				id, CommerceChannelAccountEntryRelConstants.TYPE_DISCOUNT);

		_commerceChannelAccountEntryRelService.
			deleteCommerceChannelAccountEntryRel(
				commerceChannelAccountEntryRel.
					getCommerceChannelAccountEntryRelId());
	}

	@Override
	public void deleteAccountChannelPaymentTermId(Long id) throws Exception {
		CommerceChannelAccountEntryRel commerceChannelAccountEntryRel =
			_fetchCommerceChannelAccountEntryRel(
				id, CommerceChannelAccountEntryRelConstants.TYPE_PAYMENT_TERM);

		_commerceChannelAccountEntryRelService.
			deleteCommerceChannelAccountEntryRel(
				commerceChannelAccountEntryRel.
					getCommerceChannelAccountEntryRelId());
	}

	@Override
	public void deleteAccountChannelPriceListId(Long id) throws Exception {
		CommerceChannelAccountEntryRel commerceChannelAccountEntryRel =
			_fetchCommerceChannelAccountEntryRel(
				id, CommerceChannelAccountEntryRelConstants.TYPE_PRICE_LIST);

		_commerceChannelAccountEntryRelService.
			deleteCommerceChannelAccountEntryRel(
				commerceChannelAccountEntryRel.
					getCommerceChannelAccountEntryRelId());
	}

	@Override
	public void deleteAccountChannelShippingAddressId(Long id)
		throws Exception {

		CommerceChannelAccountEntryRel commerceChannelAccountEntryRel =
			_fetchCommerceChannelAccountEntryRel(
				id,
				CommerceChannelAccountEntryRelConstants.TYPE_SHIPPING_ADDRESS);

		_commerceChannelAccountEntryRelService.
			deleteCommerceChannelAccountEntryRel(
				commerceChannelAccountEntryRel.
					getCommerceChannelAccountEntryRelId());
	}

	@Override
	public void deleteAccountChannelUserId(Long id) throws Exception {
		CommerceChannelAccountEntryRel commerceChannelAccountEntryRel =
			_fetchCommerceChannelAccountEntryRel(
				id, CommerceChannelAccountEntryRelConstants.TYPE_USER);

		_commerceChannelAccountEntryRelService.
			deleteCommerceChannelAccountEntryRel(
				commerceChannelAccountEntryRel.
					getCommerceChannelAccountEntryRelId());
	}

	@Override
	public Page<AccountChannelEntry>
			getAccountByExternalReferenceCodeAccountChannelBillingAddressesPage(
				String externalReferenceCode, Pagination pagination)
		throws Exception {

		AccountEntry accountEntry =
			_accountEntryService.fetchAccountEntryByExternalReferenceCode(
				contextCompany.getCompanyId(), externalReferenceCode);

		if (accountEntry == null) {
			throw new NoSuchEntryException();
		}

		return _getAccountChannelEntryPage(
			accountEntry.getAccountEntryId(),
			CommerceChannelAccountEntryRelConstants.TYPE_BILLING_ADDRESS,
			pagination);
	}

	@Override
	public Page<AccountChannelEntry>
			getAccountByExternalReferenceCodeAccountChannelCurrenciesPage(
				String externalReferenceCode, Pagination pagination)
		throws Exception {

		AccountEntry accountEntry =
			_accountEntryService.fetchAccountEntryByExternalReferenceCode(
				contextCompany.getCompanyId(), externalReferenceCode);

		if (accountEntry == null) {
			throw new NoSuchEntryException();
		}

		return _getAccountChannelEntryPage(
			accountEntry.getAccountEntryId(),
			CommerceChannelAccountEntryRelConstants.TYPE_CURRENCY, pagination);
	}

	@Override
	public Page<AccountChannelEntry>
			getAccountByExternalReferenceCodeAccountChannelDeliveryTermsPage(
				String externalReferenceCode, Pagination pagination)
		throws Exception {

		AccountEntry accountEntry =
			_accountEntryService.fetchAccountEntryByExternalReferenceCode(
				contextCompany.getCompanyId(), externalReferenceCode);

		if (accountEntry == null) {
			throw new NoSuchEntryException();
		}

		return _getAccountChannelEntryPage(
			accountEntry.getAccountEntryId(),
			CommerceChannelAccountEntryRelConstants.TYPE_DELIVERY_TERM,
			pagination);
	}

	@Override
	public Page<AccountChannelEntry>
			getAccountByExternalReferenceCodeAccountChannelDiscountsPage(
				String externalReferenceCode, Pagination pagination)
		throws Exception {

		AccountEntry accountEntry =
			_accountEntryService.fetchAccountEntryByExternalReferenceCode(
				contextCompany.getCompanyId(), externalReferenceCode);

		if (accountEntry == null) {
			throw new NoSuchEntryException();
		}

		return _getAccountChannelEntryPage(
			accountEntry.getAccountEntryId(),
			CommerceChannelAccountEntryRelConstants.TYPE_DISCOUNT, pagination);
	}

	@Override
	public Page<AccountChannelEntry>
			getAccountByExternalReferenceCodeAccountChannelPaymentTermsPage(
				String externalReferenceCode, Pagination pagination)
		throws Exception {

		AccountEntry accountEntry =
			_accountEntryService.fetchAccountEntryByExternalReferenceCode(
				contextCompany.getCompanyId(), externalReferenceCode);

		if (accountEntry == null) {
			throw new NoSuchEntryException();
		}

		return _getAccountChannelEntryPage(
			accountEntry.getAccountEntryId(),
			CommerceChannelAccountEntryRelConstants.TYPE_PAYMENT_TERM,
			pagination);
	}

	@Override
	public Page<AccountChannelEntry>
			getAccountByExternalReferenceCodeAccountChannelPriceListsPage(
				String externalReferenceCode, Pagination pagination)
		throws Exception {

		AccountEntry accountEntry =
			_accountEntryService.fetchAccountEntryByExternalReferenceCode(
				contextCompany.getCompanyId(), externalReferenceCode);

		if (accountEntry == null) {
			throw new NoSuchEntryException();
		}

		return _getAccountChannelEntryPage(
			accountEntry.getAccountEntryId(),
			CommerceChannelAccountEntryRelConstants.TYPE_PRICE_LIST,
			pagination);
	}

	@Override
	public Page<AccountChannelEntry>
			getAccountByExternalReferenceCodeAccountChannelShippingAddressesPage(
				String externalReferenceCode, Pagination pagination)
		throws Exception {

		AccountEntry accountEntry =
			_accountEntryService.fetchAccountEntryByExternalReferenceCode(
				contextCompany.getCompanyId(), externalReferenceCode);

		if (accountEntry == null) {
			throw new NoSuchEntryException();
		}

		return _getAccountChannelEntryPage(
			accountEntry.getAccountEntryId(),
			CommerceChannelAccountEntryRelConstants.TYPE_SHIPPING_ADDRESS,
			pagination);
	}

	@Override
	public Page<AccountChannelEntry>
			getAccountByExternalReferenceCodeAccountChannelUsersPage(
				String externalReferenceCode, Pagination pagination)
		throws Exception {

		AccountEntry accountEntry =
			_accountEntryService.fetchAccountEntryByExternalReferenceCode(
				contextCompany.getCompanyId(), externalReferenceCode);

		if (accountEntry == null) {
			throw new NoSuchEntryException();
		}

		return _getAccountChannelEntryPage(
			accountEntry.getAccountEntryId(),
			CommerceChannelAccountEntryRelConstants.TYPE_USER, pagination);
	}

	@Override
	public AccountChannelEntry getAccountChannelBillingAddressId(Long id)
		throws Exception {

		CommerceChannelAccountEntryRel commerceChannelAccountEntryRel =
			_fetchCommerceChannelAccountEntryRel(
				id,
				CommerceChannelAccountEntryRelConstants.TYPE_BILLING_ADDRESS);

		return _toAccountChannelEntry(
			commerceChannelAccountEntryRel.
				getCommerceChannelAccountEntryRelId());
	}

	@Override
	public AccountChannelEntry getAccountChannelCurrencyId(Long id)
		throws Exception {

		CommerceChannelAccountEntryRel commerceChannelAccountEntryRel =
			_fetchCommerceChannelAccountEntryRel(
				id, CommerceChannelAccountEntryRelConstants.TYPE_CURRENCY);

		return _toAccountChannelEntry(
			commerceChannelAccountEntryRel.
				getCommerceChannelAccountEntryRelId());
	}

	@Override
	public AccountChannelEntry getAccountChannelDeliveryTermId(Long id)
		throws Exception {

		CommerceChannelAccountEntryRel commerceChannelAccountEntryRel =
			_fetchCommerceChannelAccountEntryRel(
				id, CommerceChannelAccountEntryRelConstants.TYPE_DELIVERY_TERM);

		return _toAccountChannelEntry(
			commerceChannelAccountEntryRel.
				getCommerceChannelAccountEntryRelId());
	}

	@Override
	public AccountChannelEntry getAccountChannelDiscountId(Long id)
		throws Exception {

		CommerceChannelAccountEntryRel commerceChannelAccountEntryRel =
			_fetchCommerceChannelAccountEntryRel(
				id, CommerceChannelAccountEntryRelConstants.TYPE_DISCOUNT);

		return _toAccountChannelEntry(
			commerceChannelAccountEntryRel.
				getCommerceChannelAccountEntryRelId());
	}

	@Override
	public AccountChannelEntry getAccountChannelPaymentTermId(Long id)
		throws Exception {

		CommerceChannelAccountEntryRel commerceChannelAccountEntryRel =
			_fetchCommerceChannelAccountEntryRel(
				id, CommerceChannelAccountEntryRelConstants.TYPE_PAYMENT_TERM);

		return _toAccountChannelEntry(
			commerceChannelAccountEntryRel.
				getCommerceChannelAccountEntryRelId());
	}

	@Override
	public AccountChannelEntry getAccountChannelPriceListId(Long id)
		throws Exception {

		CommerceChannelAccountEntryRel commerceChannelAccountEntryRel =
			_fetchCommerceChannelAccountEntryRel(
				id, CommerceChannelAccountEntryRelConstants.TYPE_PRICE_LIST);

		return _toAccountChannelEntry(
			commerceChannelAccountEntryRel.
				getCommerceChannelAccountEntryRelId());
	}

	@Override
	public AccountChannelEntry getAccountChannelShippingAddressId(Long id)
		throws Exception {

		CommerceChannelAccountEntryRel commerceChannelAccountEntryRel =
			_fetchCommerceChannelAccountEntryRel(
				id,
				CommerceChannelAccountEntryRelConstants.TYPE_SHIPPING_ADDRESS);

		return _toAccountChannelEntry(
			commerceChannelAccountEntryRel.
				getCommerceChannelAccountEntryRelId());
	}

	@Override
	public AccountChannelEntry getAccountChannelUserId(Long id)
		throws Exception {

		CommerceChannelAccountEntryRel commerceChannelAccountEntryRel =
			_fetchCommerceChannelAccountEntryRel(
				id, CommerceChannelAccountEntryRelConstants.TYPE_USER);

		return _toAccountChannelEntry(
			commerceChannelAccountEntryRel.
				getCommerceChannelAccountEntryRelId());
	}

	@Override
	public Page<AccountChannelEntry>
			getAccountIdAccountChannelBillingAddressesPage(
				Long id, Pagination pagination)
		throws Exception {

		return _getAccountChannelEntryPage(
			id, CommerceChannelAccountEntryRelConstants.TYPE_BILLING_ADDRESS,
			pagination);
	}

	@Override
	public Page<AccountChannelEntry> getAccountIdAccountChannelCurrenciesPage(
			Long id, Pagination pagination)
		throws Exception {

		return _getAccountChannelEntryPage(
			id, CommerceChannelAccountEntryRelConstants.TYPE_CURRENCY,
			pagination);
	}

	@Override
	public Page<AccountChannelEntry>
			getAccountIdAccountChannelDeliveryTermsPage(
				Long id, Pagination pagination)
		throws Exception {

		return _getAccountChannelEntryPage(
			id, CommerceChannelAccountEntryRelConstants.TYPE_DELIVERY_TERM,
			pagination);
	}

	@Override
	public Page<AccountChannelEntry> getAccountIdAccountChannelDiscountsPage(
			Long id, Pagination pagination)
		throws Exception {

		return _getAccountChannelEntryPage(
			id, CommerceChannelAccountEntryRelConstants.TYPE_DISCOUNT,
			pagination);
	}

	@Override
	public Page<AccountChannelEntry> getAccountIdAccountChannelPaymentTermsPage(
			Long id, Pagination pagination)
		throws Exception {

		return _getAccountChannelEntryPage(
			id, CommerceChannelAccountEntryRelConstants.TYPE_PAYMENT_TERM,
			pagination);
	}

	@Override
	public Page<AccountChannelEntry> getAccountIdAccountChannelPriceListsPage(
			Long id, Pagination pagination)
		throws Exception {

		return _getAccountChannelEntryPage(
			id, CommerceChannelAccountEntryRelConstants.TYPE_PRICE_LIST,
			pagination);
	}

	@Override
	public Page<AccountChannelEntry>
			getAccountIdAccountChannelShippingAddressesPage(
				Long id, Pagination pagination)
		throws Exception {

		return _getAccountChannelEntryPage(
			id, CommerceChannelAccountEntryRelConstants.TYPE_SHIPPING_ADDRESS,
			pagination);
	}

	@Override
	public Page<AccountChannelEntry> getAccountIdAccountChannelUsersPage(
			Long id, Pagination pagination)
		throws Exception {

		return _getAccountChannelEntryPage(
			id, CommerceChannelAccountEntryRelConstants.TYPE_USER, pagination);
	}

	@Override
	public AccountChannelEntry patchAccountChannelBillingAddressId(
			Long id, AccountChannelEntry accountChannelEntry)
		throws Exception {

		CommerceChannelAccountEntryRel commerceChannelAccountEntryRel =
			_fetchCommerceChannelAccountEntryRel(
				id,
				CommerceChannelAccountEntryRelConstants.TYPE_BILLING_ADDRESS);

		return _patchAccountChannelEntry(
			accountChannelEntry, commerceChannelAccountEntryRel,
			CommerceChannelAccountEntryRelConstants.TYPE_BILLING_ADDRESS);
	}

	@Override
	public AccountChannelEntry patchAccountChannelCurrencyId(
			Long id, AccountChannelEntry accountChannelEntry)
		throws Exception {

		CommerceChannelAccountEntryRel commerceChannelAccountEntryRel =
			_fetchCommerceChannelAccountEntryRel(
				id, CommerceChannelAccountEntryRelConstants.TYPE_CURRENCY);

		return _patchAccountChannelEntry(
			accountChannelEntry, commerceChannelAccountEntryRel,
			CommerceChannelAccountEntryRelConstants.TYPE_CURRENCY);
	}

	@Override
	public AccountChannelEntry patchAccountChannelDeliveryTermId(
			Long id, AccountChannelEntry accountChannelEntry)
		throws Exception {

		CommerceChannelAccountEntryRel commerceChannelAccountEntryRel =
			_fetchCommerceChannelAccountEntryRel(
				id, CommerceChannelAccountEntryRelConstants.TYPE_DELIVERY_TERM);

		return _patchAccountChannelEntry(
			accountChannelEntry, commerceChannelAccountEntryRel,
			CommerceChannelAccountEntryRelConstants.TYPE_DELIVERY_TERM);
	}

	@Override
	public AccountChannelEntry patchAccountChannelDiscountId(
			Long id, AccountChannelEntry accountChannelEntry)
		throws Exception {

		CommerceChannelAccountEntryRel commerceChannelAccountEntryRel =
			_fetchCommerceChannelAccountEntryRel(
				id, CommerceChannelAccountEntryRelConstants.TYPE_DISCOUNT);

		return _patchAccountChannelEntry(
			accountChannelEntry, commerceChannelAccountEntryRel,
			CommerceChannelAccountEntryRelConstants.TYPE_DISCOUNT);
	}

	@Override
	public AccountChannelEntry patchAccountChannelPaymentTermId(
			Long id, AccountChannelEntry accountChannelEntry)
		throws Exception {

		CommerceChannelAccountEntryRel commerceChannelAccountEntryRel =
			_fetchCommerceChannelAccountEntryRel(
				id, CommerceChannelAccountEntryRelConstants.TYPE_PAYMENT_TERM);

		return _patchAccountChannelEntry(
			accountChannelEntry, commerceChannelAccountEntryRel,
			CommerceChannelAccountEntryRelConstants.TYPE_PAYMENT_TERM);
	}

	@Override
	public AccountChannelEntry patchAccountChannelPriceListId(
			Long id, AccountChannelEntry accountChannelEntry)
		throws Exception {

		CommerceChannelAccountEntryRel commerceChannelAccountEntryRel =
			_fetchCommerceChannelAccountEntryRel(
				id, CommerceChannelAccountEntryRelConstants.TYPE_PRICE_LIST);

		return _patchAccountChannelEntry(
			accountChannelEntry, commerceChannelAccountEntryRel,
			CommerceChannelAccountEntryRelConstants.TYPE_PRICE_LIST);
	}

	@Override
	public AccountChannelEntry patchAccountChannelShippingAddressId(
			Long id, AccountChannelEntry accountChannelEntry)
		throws Exception {

		CommerceChannelAccountEntryRel commerceChannelAccountEntryRel =
			_fetchCommerceChannelAccountEntryRel(
				id,
				CommerceChannelAccountEntryRelConstants.TYPE_SHIPPING_ADDRESS);

		return _patchAccountChannelEntry(
			accountChannelEntry, commerceChannelAccountEntryRel,
			CommerceChannelAccountEntryRelConstants.TYPE_SHIPPING_ADDRESS);
	}

	@Override
	public AccountChannelEntry patchAccountChannelUserId(
			Long id, AccountChannelEntry accountChannelEntry)
		throws Exception {

		CommerceChannelAccountEntryRel commerceChannelAccountEntryRel =
			_fetchCommerceChannelAccountEntryRel(
				id, CommerceChannelAccountEntryRelConstants.TYPE_USER);

		_checkUser(
			_getClassPK(
				accountChannelEntry,
				CommerceChannelAccountEntryRelConstants.TYPE_USER));

		return _patchAccountChannelEntry(
			accountChannelEntry, commerceChannelAccountEntryRel,
			CommerceChannelAccountEntryRelConstants.TYPE_USER);
	}

	@Override
	public AccountChannelEntry
			postAccountByExternalReferenceCodeAccountChannelBillingAddress(
				String externalReferenceCode,
				AccountChannelEntry accountChannelEntry)
		throws Exception {

		AccountEntry accountEntry =
			_accountEntryService.fetchAccountEntryByExternalReferenceCode(
				contextCompany.getCompanyId(), externalReferenceCode);

		if (accountEntry == null) {
			throw new NoSuchEntryException();
		}

		return _postAccountChannelEntry(
			accountChannelEntry, accountEntry.getAccountEntryId(),
			Address.class.getName(),
			CommerceChannelAccountEntryRelConstants.TYPE_BILLING_ADDRESS);
	}

	@Override
	public AccountChannelEntry
			postAccountByExternalReferenceCodeAccountChannelCurrency(
				String externalReferenceCode,
				AccountChannelEntry accountChannelEntry)
		throws Exception {

		AccountEntry accountEntry =
			_accountEntryService.fetchAccountEntryByExternalReferenceCode(
				contextCompany.getCompanyId(), externalReferenceCode);

		if (accountEntry == null) {
			throw new NoSuchEntryException();
		}

		return _postAccountChannelEntry(
			accountChannelEntry, accountEntry.getAccountEntryId(),
			CommerceCurrency.class.getName(),
			CommerceChannelAccountEntryRelConstants.TYPE_CURRENCY);
	}

	@Override
	public AccountChannelEntry
			postAccountByExternalReferenceCodeAccountChannelDeliveryTerm(
				String externalReferenceCode,
				AccountChannelEntry accountChannelEntry)
		throws Exception {

		AccountEntry accountEntry =
			_accountEntryService.fetchAccountEntryByExternalReferenceCode(
				contextCompany.getCompanyId(), externalReferenceCode);

		if (accountEntry == null) {
			throw new NoSuchEntryException();
		}

		return _postAccountChannelEntry(
			accountChannelEntry, accountEntry.getAccountEntryId(),
			CommerceTermEntry.class.getName(),
			CommerceChannelAccountEntryRelConstants.TYPE_DELIVERY_TERM);
	}

	@Override
	public AccountChannelEntry
			postAccountByExternalReferenceCodeAccountChannelDiscount(
				String externalReferenceCode,
				AccountChannelEntry accountChannelEntry)
		throws Exception {

		AccountEntry accountEntry =
			_accountEntryService.fetchAccountEntryByExternalReferenceCode(
				contextCompany.getCompanyId(), externalReferenceCode);

		if (accountEntry == null) {
			throw new NoSuchEntryException();
		}

		return _postAccountChannelEntry(
			accountChannelEntry, accountEntry.getAccountEntryId(),
			CommerceDiscount.class.getName(),
			CommerceChannelAccountEntryRelConstants.TYPE_DISCOUNT);
	}

	@Override
	public AccountChannelEntry
			postAccountByExternalReferenceCodeAccountChannelPaymentTerm(
				String externalReferenceCode,
				AccountChannelEntry accountChannelEntry)
		throws Exception {

		AccountEntry accountEntry =
			_accountEntryService.fetchAccountEntryByExternalReferenceCode(
				contextCompany.getCompanyId(), externalReferenceCode);

		if (accountEntry == null) {
			throw new NoSuchEntryException();
		}

		return _postAccountChannelEntry(
			accountChannelEntry, accountEntry.getAccountEntryId(),
			CommerceTermEntry.class.getName(),
			CommerceChannelAccountEntryRelConstants.TYPE_PAYMENT_TERM);
	}

	@Override
	public AccountChannelEntry
			postAccountByExternalReferenceCodeAccountChannelPriceList(
				String externalReferenceCode,
				AccountChannelEntry accountChannelEntry)
		throws Exception {

		AccountEntry accountEntry =
			_accountEntryService.fetchAccountEntryByExternalReferenceCode(
				contextCompany.getCompanyId(), externalReferenceCode);

		if (accountEntry == null) {
			throw new NoSuchEntryException();
		}

		return _postAccountChannelEntry(
			accountChannelEntry, accountEntry.getAccountEntryId(),
			CommercePriceList.class.getName(),
			CommerceChannelAccountEntryRelConstants.TYPE_PRICE_LIST);
	}

	@Override
	public AccountChannelEntry
			postAccountByExternalReferenceCodeAccountChannelShippingAddress(
				String externalReferenceCode,
				AccountChannelEntry accountChannelEntry)
		throws Exception {

		AccountEntry accountEntry =
			_accountEntryService.fetchAccountEntryByExternalReferenceCode(
				contextCompany.getCompanyId(), externalReferenceCode);

		if (accountEntry == null) {
			throw new NoSuchEntryException();
		}

		return _postAccountChannelEntry(
			accountChannelEntry, accountEntry.getAccountEntryId(),
			Address.class.getName(),
			CommerceChannelAccountEntryRelConstants.TYPE_SHIPPING_ADDRESS);
	}

	@Override
	public AccountChannelEntry
			postAccountByExternalReferenceCodeAccountChannelUser(
				String externalReferenceCode,
				AccountChannelEntry accountChannelEntry)
		throws Exception {

		AccountEntry accountEntry =
			_accountEntryService.fetchAccountEntryByExternalReferenceCode(
				contextCompany.getCompanyId(), externalReferenceCode);

		if (accountEntry == null) {
			throw new NoSuchEntryException();
		}

		_checkUser(
			_getClassPK(
				accountChannelEntry,
				CommerceChannelAccountEntryRelConstants.TYPE_USER));

		return _postAccountChannelEntry(
			accountChannelEntry, accountEntry.getAccountEntryId(),
			User.class.getName(),
			CommerceChannelAccountEntryRelConstants.TYPE_USER);
	}

	@Override
	public AccountChannelEntry postAccountIdAccountChannelBillingAddress(
			Long id, AccountChannelEntry accountChannelEntry)
		throws Exception {

		AccountEntry accountEntry = _accountEntryService.getAccountEntry(id);

		return _postAccountChannelEntry(
			accountChannelEntry, accountEntry.getAccountEntryId(),
			Address.class.getName(),
			CommerceChannelAccountEntryRelConstants.TYPE_BILLING_ADDRESS);
	}

	@Override
	public AccountChannelEntry postAccountIdAccountChannelCurrency(
			Long id, AccountChannelEntry accountChannelEntry)
		throws Exception {

		AccountEntry accountEntry = _accountEntryService.getAccountEntry(id);

		return _postAccountChannelEntry(
			accountChannelEntry, accountEntry.getAccountEntryId(),
			CommerceCurrency.class.getName(),
			CommerceChannelAccountEntryRelConstants.TYPE_CURRENCY);
	}

	@Override
	public AccountChannelEntry postAccountIdAccountChannelDeliveryTerm(
			Long id, AccountChannelEntry accountChannelEntry)
		throws Exception {

		AccountEntry accountEntry = _accountEntryService.getAccountEntry(id);

		return _postAccountChannelEntry(
			accountChannelEntry, accountEntry.getAccountEntryId(),
			CommerceTermEntry.class.getName(),
			CommerceChannelAccountEntryRelConstants.TYPE_DELIVERY_TERM);
	}

	@Override
	public AccountChannelEntry postAccountIdAccountChannelDiscount(
			Long id, AccountChannelEntry accountChannelEntry)
		throws Exception {

		AccountEntry accountEntry = _accountEntryService.getAccountEntry(id);

		return _postAccountChannelEntry(
			accountChannelEntry, accountEntry.getAccountEntryId(),
			CommerceDiscount.class.getName(),
			CommerceChannelAccountEntryRelConstants.TYPE_DISCOUNT);
	}

	@Override
	public AccountChannelEntry postAccountIdAccountChannelPaymentTerm(
			Long id, AccountChannelEntry accountChannelEntry)
		throws Exception {

		AccountEntry accountEntry = _accountEntryService.getAccountEntry(id);

		return _postAccountChannelEntry(
			accountChannelEntry, accountEntry.getAccountEntryId(),
			CommerceTermEntry.class.getName(),
			CommerceChannelAccountEntryRelConstants.TYPE_PAYMENT_TERM);
	}

	@Override
	public AccountChannelEntry postAccountIdAccountChannelPriceList(
			Long id, AccountChannelEntry accountChannelEntry)
		throws Exception {

		AccountEntry accountEntry = _accountEntryService.getAccountEntry(id);

		return _postAccountChannelEntry(
			accountChannelEntry, accountEntry.getAccountEntryId(),
			CommercePriceList.class.getName(),
			CommerceChannelAccountEntryRelConstants.TYPE_PRICE_LIST);
	}

	@Override
	public AccountChannelEntry postAccountIdAccountChannelShippingAddress(
			Long id, AccountChannelEntry accountChannelEntry)
		throws Exception {

		AccountEntry accountEntry = _accountEntryService.getAccountEntry(id);

		return _postAccountChannelEntry(
			accountChannelEntry, accountEntry.getAccountEntryId(),
			Address.class.getName(),
			CommerceChannelAccountEntryRelConstants.TYPE_SHIPPING_ADDRESS);
	}

	@Override
	public AccountChannelEntry postAccountIdAccountChannelUser(
			Long id, AccountChannelEntry accountChannelEntry)
		throws Exception {

		AccountEntry accountEntry = _accountEntryService.getAccountEntry(id);

		_checkUser(
			_getClassPK(
				accountChannelEntry,
				CommerceChannelAccountEntryRelConstants.TYPE_USER));

		return _postAccountChannelEntry(
			accountChannelEntry, accountEntry.getAccountEntryId(),
			User.class.getName(),
			CommerceChannelAccountEntryRelConstants.TYPE_USER);
	}

	private void _checkUser(long userId) throws Exception {
		User user = _userService.getUserById(userId);

		_accountEntryModelResourcePermission.contains(
			PermissionCheckerFactoryUtil.create(user), 0,
			CommerceAccountActionKeys.
				MANAGE_AVAILABLE_ACCOUNTS_VIA_USER_CHANNEL_REL);
	}

	private CommerceChannelAccountEntryRel _fetchCommerceChannelAccountEntryRel(
			Long id, int type)
		throws Exception {

		CommerceChannelAccountEntryRel commerceChannelAccountEntryRel =
			_commerceChannelAccountEntryRelService.
				fetchCommerceChannelAccountEntryRel(id);

		if (commerceChannelAccountEntryRel == null) {
			throw new NoSuchChannelAccountEntryRelException();
		}

		if (type != commerceChannelAccountEntryRel.getType()) {
			throw new CommerceChannelAccountEntryRelTypeException();
		}

		return commerceChannelAccountEntryRel;
	}

	private Page<AccountChannelEntry> _getAccountChannelEntryPage(
			long accountEntryId, int accountEntryType, Pagination pagination)
		throws Exception {

		return Page.of(
			TransformUtil.transform(
				_commerceChannelAccountEntryRelService.
					getCommerceChannelAccountEntryRels(
						accountEntryId, accountEntryType,
						pagination.getStartPosition(),
						pagination.getEndPosition(), null),
				commerceChannelAccountEntryRel -> _toAccountChannelEntry(
					commerceChannelAccountEntryRel)),
			pagination,
			_commerceChannelAccountEntryRelService.
				getCommerceChannelAccountEntryRelsCount(
					accountEntryId, accountEntryType));
	}

	private long _getClassPK(AccountChannelEntry accountChannelEntry, int type)
		throws Exception {

		if (type ==
				CommerceChannelAccountEntryRelConstants.TYPE_BILLING_ADDRESS) {

			Address address = _addressService.getAddress(
				GetterUtil.getLong(accountChannelEntry.getEntryId()));

			ListType listType = address.getListType();

			if (AccountListTypeConstants.ACCOUNT_ENTRY_ADDRESS_TYPE_BILLING.
					equals(listType.getType()) ||
				AccountListTypeConstants.
					ACCOUNT_ENTRY_ADDRESS_TYPE_BILLING_AND_SHIPPING.equals(
						listType.getType())) {

				return address.getAddressId();
			}
		}
		else if (type ==
					CommerceChannelAccountEntryRelConstants.TYPE_CURRENCY) {

			CommerceCurrency commerceCurrency =
				_commerceCurrencyService.getCommerceCurrency(
					GetterUtil.getLong(accountChannelEntry.getEntryId()));

			return commerceCurrency.getCommerceCurrencyId();
		}
		else if (type ==
					CommerceChannelAccountEntryRelConstants.
						TYPE_DELIVERY_TERM) {

			CommerceTermEntry commerceTermEntry =
				_commerceTermEntryService.getCommerceTermEntry(
					GetterUtil.getLong(accountChannelEntry.getEntryId()));

			return commerceTermEntry.getCommerceTermEntryId();
		}
		else if (type ==
					CommerceChannelAccountEntryRelConstants.TYPE_DISCOUNT) {

			CommerceDiscount commerceDiscount =
				_commerceDiscountService.getCommerceDiscount(
					GetterUtil.getLong(accountChannelEntry.getEntryId()));

			return commerceDiscount.getCommerceDiscountId();
		}
		else if (type ==
					CommerceChannelAccountEntryRelConstants.TYPE_PAYMENT_TERM) {

			CommerceTermEntry commerceTermEntry =
				_commerceTermEntryService.getCommerceTermEntry(
					GetterUtil.getLong(accountChannelEntry.getEntryId()));

			return commerceTermEntry.getCommerceTermEntryId();
		}
		else if (type ==
					CommerceChannelAccountEntryRelConstants.TYPE_PRICE_LIST) {

			CommercePriceList commercePriceList =
				_commercePriceListService.getCommercePriceList(
					GetterUtil.getLong(accountChannelEntry.getEntryId()));

			return commercePriceList.getCommercePriceListId();
		}
		else if (type ==
					CommerceChannelAccountEntryRelConstants.
						TYPE_SHIPPING_ADDRESS) {

			Address address = _addressService.getAddress(
				GetterUtil.getLong(accountChannelEntry.getEntryId()));

			ListType listType = address.getListType();

			if (AccountListTypeConstants.ACCOUNT_ENTRY_ADDRESS_TYPE_SHIPPING.
					equals(listType.getType()) ||
				AccountListTypeConstants.
					ACCOUNT_ENTRY_ADDRESS_TYPE_BILLING_AND_SHIPPING.equals(
						listType.getType())) {

				return address.getAddressId();
			}
		}
		else if (type == CommerceChannelAccountEntryRelConstants.TYPE_USER) {
			User user = _userService.getUserById(
				GetterUtil.getLong(accountChannelEntry.getEntryId()));

			return user.getUserId();
		}

		throw new CommerceChannelAccountEntryRelTypeException();
	}

	private long _getCommerceChannelId(AccountChannelEntry accountChannelEntry)
		throws Exception {

		CommerceChannel commerceChannel =
			_commerceChannelService.fetchByExternalReferenceCode(
				GetterUtil.getString(
					accountChannelEntry.getChannelExternalReferenceCode()),
				contextCompany.getCompanyId());

		if (commerceChannel == null) {
			commerceChannel = _commerceChannelService.fetchCommerceChannel(
				GetterUtil.getLong(accountChannelEntry.getChannelId()));
		}

		if (commerceChannel == null) {
			return 0;
		}

		return commerceChannel.getCommerceChannelId();
	}

	private AccountChannelEntry _patchAccountChannelEntry(
			AccountChannelEntry accountChannelEntry,
			CommerceChannelAccountEntryRel commerceChannelAccountEntryRel,
			int type)
		throws Exception {

		long commerceChannelId =
			commerceChannelAccountEntryRel.getCommerceChannelId();

		try {
			commerceChannelId = _getCommerceChannelId(accountChannelEntry);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}
		}

		long classPK = commerceChannelAccountEntryRel.getClassPK();

		try {
			classPK = _getClassPK(accountChannelEntry, type);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}
		}

		return _toAccountChannelEntry(
			_commerceChannelAccountEntryRelService.
				updateCommerceChannelAccountEntryRel(
					commerceChannelAccountEntryRel.
						getCommerceChannelAccountEntryRelId(),
					commerceChannelId, classPK,
					GetterUtil.getBoolean(
						accountChannelEntry.getOverrideEligibility(),
						commerceChannelAccountEntryRel.isOverrideEligibility()),
					GetterUtil.getDouble(
						accountChannelEntry.getPriority(),
						commerceChannelAccountEntryRel.getPriority())));
	}

	private AccountChannelEntry _postAccountChannelEntry(
			AccountChannelEntry accountChannelEntry, long accountEntryId,
			String className, int type)
		throws Exception {

		return _toAccountChannelEntry(
			_commerceChannelAccountEntryRelService.
				addCommerceChannelAccountEntryRel(
					accountEntryId, className,
					_getClassPK(accountChannelEntry, type),
					_getCommerceChannelId(accountChannelEntry),
					GetterUtil.getBoolean(
						accountChannelEntry.getOverrideEligibility()),
					GetterUtil.getDouble(accountChannelEntry.getPriority()),
					type));
	}

	private AccountChannelEntry _toAccountChannelEntry(
			CommerceChannelAccountEntryRel commerceChannelAccountEntryRel)
		throws Exception {

		return _toAccountChannelEntry(
			commerceChannelAccountEntryRel.
				getCommerceChannelAccountEntryRelId());
	}

	private AccountChannelEntry _toAccountChannelEntry(
			long commerceChannelAccountEntryRelId)
		throws Exception {

		return _accountChannelEntryDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				contextAcceptLanguage.isAcceptAllLanguages(), null,
				_dtoConverterRegistry, commerceChannelAccountEntryRelId,
				contextAcceptLanguage.getPreferredLocale(), contextUriInfo,
				contextUser));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AccountChannelEntryResourceImpl.class);

	@Reference
	private AccountChannelEntryDTOConverter _accountChannelEntryDTOConverter;

	@Reference(
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		target = "(model.class.name=com.liferay.account.model.AccountEntry)"
	)
	private volatile ModelResourcePermission<AccountEntry>
		_accountEntryModelResourcePermission;

	@Reference
	private AccountEntryService _accountEntryService;

	@Reference
	private AddressService _addressService;

	@Reference
	private CommerceChannelAccountEntryRelService
		_commerceChannelAccountEntryRelService;

	@Reference
	private CommerceChannelService _commerceChannelService;

	@Reference
	private CommerceCurrencyService _commerceCurrencyService;

	@Reference
	private CommerceDiscountService _commerceDiscountService;

	@Reference
	private CommercePriceListService _commercePriceListService;

	@Reference
	private CommerceTermEntryService _commerceTermEntryService;

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;

	@Reference
	private UserService _userService;

}