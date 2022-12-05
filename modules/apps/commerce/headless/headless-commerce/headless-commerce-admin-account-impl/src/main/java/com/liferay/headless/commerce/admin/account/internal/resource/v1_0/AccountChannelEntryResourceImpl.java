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

import com.liferay.account.exception.NoSuchEntryException;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryService;
import com.liferay.commerce.account.constants.CommerceAccountActionKeys;
import com.liferay.commerce.constants.CommerceAddressConstants;
import com.liferay.commerce.currency.exception.NoSuchCurrencyException;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.service.CommerceCurrencyService;
import com.liferay.commerce.discount.exception.NoSuchDiscountException;
import com.liferay.commerce.discount.model.CommerceDiscount;
import com.liferay.commerce.discount.service.CommerceDiscountService;
import com.liferay.commerce.model.CommerceAddress;
import com.liferay.commerce.payment.exception.NoSuchPaymentMethodGroupRelException;
import com.liferay.commerce.payment.model.CommercePaymentMethodGroupRel;
import com.liferay.commerce.payment.service.CommercePaymentMethodGroupRelService;
import com.liferay.commerce.price.list.exception.NoSuchPriceListException;
import com.liferay.commerce.price.list.model.CommercePriceList;
import com.liferay.commerce.price.list.service.CommercePriceListService;
import com.liferay.commerce.product.constants.CommerceChannelAccountEntryRelConstants;
import com.liferay.commerce.product.exception.CommerceChannelAccountEntryRelTypeException;
import com.liferay.commerce.product.exception.NoSuchChannelException;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.model.CommerceChannelAccountEntryRel;
import com.liferay.commerce.product.service.CommerceChannelAccountEntryRelService;
import com.liferay.commerce.product.service.CommerceChannelService;
import com.liferay.commerce.service.CommerceAddressService;
import com.liferay.commerce.term.exception.NoSuchTermEntryException;
import com.liferay.commerce.term.model.CommerceTermEntry;
import com.liferay.commerce.term.service.CommerceTermEntryService;
import com.liferay.headless.commerce.admin.account.dto.v1_0.AccountChannelEntry;
import com.liferay.headless.commerce.admin.account.internal.dto.v1_0.converter.AccountChannelEntryDTOConverter;
import com.liferay.headless.commerce.admin.account.resource.v1_0.AccountChannelEntryResource;
import com.liferay.portal.kernel.exception.NoSuchUserException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Address;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.UserService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

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
			_getCommerceChannelAccountEntryRel(
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
			_getCommerceChannelAccountEntryRel(
				id, CommerceChannelAccountEntryRelConstants.TYPE_CURRENCY);

		_commerceChannelAccountEntryRelService.
			deleteCommerceChannelAccountEntryRel(
				commerceChannelAccountEntryRel.
					getCommerceChannelAccountEntryRelId());
	}

	@Override
	public void deleteAccountChannelDeliveryTermId(Long id) throws Exception {
		CommerceChannelAccountEntryRel commerceChannelAccountEntryRel =
			_getCommerceChannelAccountEntryRel(
				id, CommerceChannelAccountEntryRelConstants.TYPE_DELIVERY_TERM);

		_commerceChannelAccountEntryRelService.
			deleteCommerceChannelAccountEntryRel(
				commerceChannelAccountEntryRel.
					getCommerceChannelAccountEntryRelId());
	}

	@Override
	public void deleteAccountChannelDiscountId(Long id) throws Exception {
		CommerceChannelAccountEntryRel commerceChannelAccountEntryRel =
			_getCommerceChannelAccountEntryRel(
				id, CommerceChannelAccountEntryRelConstants.TYPE_DISCOUNT);

		_commerceChannelAccountEntryRelService.
			deleteCommerceChannelAccountEntryRel(
				commerceChannelAccountEntryRel.
					getCommerceChannelAccountEntryRelId());
	}

	@Override
	public void deleteAccountChannelPaymentMethodId(Long id) throws Exception {
		CommerceChannelAccountEntryRel commerceChannelAccountEntryRel =
			_getCommerceChannelAccountEntryRel(
				id, CommerceChannelAccountEntryRelConstants.TYPE_PAYMENT);

		_commerceChannelAccountEntryRelService.
			deleteCommerceChannelAccountEntryRel(
				commerceChannelAccountEntryRel.
					getCommerceChannelAccountEntryRelId());
	}

	@Override
	public void deleteAccountChannelPaymentTermId(Long id) throws Exception {
		CommerceChannelAccountEntryRel commerceChannelAccountEntryRel =
			_getCommerceChannelAccountEntryRel(
				id, CommerceChannelAccountEntryRelConstants.TYPE_PAYMENT_TERM);

		_commerceChannelAccountEntryRelService.
			deleteCommerceChannelAccountEntryRel(
				commerceChannelAccountEntryRel.
					getCommerceChannelAccountEntryRelId());
	}

	@Override
	public void deleteAccountChannelPriceListId(Long id) throws Exception {
		CommerceChannelAccountEntryRel commerceChannelAccountEntryRel =
			_getCommerceChannelAccountEntryRel(
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
			_getCommerceChannelAccountEntryRel(
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
			_getCommerceChannelAccountEntryRel(
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

		return _getPage(
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

		return _getPage(
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

		return _getPage(
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

		return _getPage(
			accountEntry.getAccountEntryId(),
			CommerceChannelAccountEntryRelConstants.TYPE_DISCOUNT, pagination);
	}

	@Override
	public Page<AccountChannelEntry>
			getAccountByExternalReferenceCodeAccountChannelPaymentMethodsPage(
				String externalReferenceCode, Pagination pagination)
		throws Exception {

		AccountEntry accountEntry =
			_accountEntryService.fetchAccountEntryByExternalReferenceCode(
				contextCompany.getCompanyId(), externalReferenceCode);

		if (accountEntry == null) {
			throw new NoSuchEntryException();
		}

		return _getPage(
			accountEntry.getAccountEntryId(),
			CommerceChannelAccountEntryRelConstants.TYPE_PAYMENT, pagination);
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

		return _getPage(
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

		return _getPage(
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

		return _getPage(
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

		return _getPage(
			accountEntry.getAccountEntryId(),
			CommerceChannelAccountEntryRelConstants.TYPE_USER, pagination);
	}

	@Override
	public AccountChannelEntry getAccountChannelBillingAddressId(Long id)
		throws Exception {

		return _toAccountChannelEntry(
			_getCommerceChannelAccountEntryRel(
				id,
				CommerceChannelAccountEntryRelConstants.TYPE_BILLING_ADDRESS));
	}

	@Override
	public AccountChannelEntry getAccountChannelCurrencyId(Long id)
		throws Exception {

		return _toAccountChannelEntry(
			_getCommerceChannelAccountEntryRel(
				id, CommerceChannelAccountEntryRelConstants.TYPE_CURRENCY));
	}

	@Override
	public AccountChannelEntry getAccountChannelDeliveryTermId(Long id)
		throws Exception {

		return _toAccountChannelEntry(
			_getCommerceChannelAccountEntryRel(
				id,
				CommerceChannelAccountEntryRelConstants.TYPE_DELIVERY_TERM));
	}

	@Override
	public AccountChannelEntry getAccountChannelDiscountId(Long id)
		throws Exception {

		return _toAccountChannelEntry(
			_getCommerceChannelAccountEntryRel(
				id, CommerceChannelAccountEntryRelConstants.TYPE_DISCOUNT));
	}

	@Override
	public AccountChannelEntry getAccountChannelPaymentMethodId(Long id)
		throws Exception {

		return _toAccountChannelEntry(
			_getCommerceChannelAccountEntryRel(
				id, CommerceChannelAccountEntryRelConstants.TYPE_PAYMENT));
	}

	@Override
	public AccountChannelEntry getAccountChannelPaymentTermId(Long id)
		throws Exception {

		return _toAccountChannelEntry(
			_getCommerceChannelAccountEntryRel(
				id, CommerceChannelAccountEntryRelConstants.TYPE_PAYMENT_TERM));
	}

	@Override
	public AccountChannelEntry getAccountChannelPriceListId(Long id)
		throws Exception {

		return _toAccountChannelEntry(
			_getCommerceChannelAccountEntryRel(
				id, CommerceChannelAccountEntryRelConstants.TYPE_PRICE_LIST));
	}

	@Override
	public AccountChannelEntry getAccountChannelShippingAddressId(Long id)
		throws Exception {

		return _toAccountChannelEntry(
			_getCommerceChannelAccountEntryRel(
				id,
				CommerceChannelAccountEntryRelConstants.TYPE_SHIPPING_ADDRESS));
	}

	@Override
	public AccountChannelEntry getAccountChannelUserId(Long id)
		throws Exception {

		return _toAccountChannelEntry(
			_getCommerceChannelAccountEntryRel(
				id, CommerceChannelAccountEntryRelConstants.TYPE_USER));
	}

	@Override
	public Page<AccountChannelEntry>
			getAccountIdAccountChannelBillingAddressesPage(
				Long id, Pagination pagination)
		throws Exception {

		return _getPage(
			id, CommerceChannelAccountEntryRelConstants.TYPE_BILLING_ADDRESS,
			pagination);
	}

	@Override
	public Page<AccountChannelEntry> getAccountIdAccountChannelCurrenciesPage(
			Long id, Pagination pagination)
		throws Exception {

		return _getPage(
			id, CommerceChannelAccountEntryRelConstants.TYPE_CURRENCY,
			pagination);
	}

	@Override
	public Page<AccountChannelEntry>
			getAccountIdAccountChannelDeliveryTermsPage(
				Long id, Pagination pagination)
		throws Exception {

		return _getPage(
			id, CommerceChannelAccountEntryRelConstants.TYPE_DELIVERY_TERM,
			pagination);
	}

	@Override
	public Page<AccountChannelEntry> getAccountIdAccountChannelDiscountsPage(
			Long id, Pagination pagination)
		throws Exception {

		return _getPage(
			id, CommerceChannelAccountEntryRelConstants.TYPE_DISCOUNT,
			pagination);
	}

	@Override
	public Page<AccountChannelEntry>
			getAccountIdAccountChannelPaymentMethodsPage(
				Long id, Pagination pagination)
		throws Exception {

		return _getPage(
			id, CommerceChannelAccountEntryRelConstants.TYPE_PAYMENT,
			pagination);
	}

	@Override
	public Page<AccountChannelEntry> getAccountIdAccountChannelPaymentTermsPage(
			Long id, Pagination pagination)
		throws Exception {

		return _getPage(
			id, CommerceChannelAccountEntryRelConstants.TYPE_PAYMENT_TERM,
			pagination);
	}

	@Override
	public Page<AccountChannelEntry> getAccountIdAccountChannelPriceListsPage(
			Long id, Pagination pagination)
		throws Exception {

		return _getPage(
			id, CommerceChannelAccountEntryRelConstants.TYPE_PRICE_LIST,
			pagination);
	}

	@Override
	public Page<AccountChannelEntry>
			getAccountIdAccountChannelShippingAddressesPage(
				Long id, Pagination pagination)
		throws Exception {

		return _getPage(
			id, CommerceChannelAccountEntryRelConstants.TYPE_SHIPPING_ADDRESS,
			pagination);
	}

	@Override
	public Page<AccountChannelEntry> getAccountIdAccountChannelUsersPage(
			Long id, Pagination pagination)
		throws Exception {

		return _getPage(
			id, CommerceChannelAccountEntryRelConstants.TYPE_USER, pagination);
	}

	@Override
	public AccountChannelEntry patchAccountChannelBillingAddressId(
			Long id, AccountChannelEntry accountChannelEntry)
		throws Exception {

		return _patchAccountChannelEntry(
			accountChannelEntry,
			_getCommerceChannelAccountEntryRel(
				id,
				CommerceChannelAccountEntryRelConstants.TYPE_BILLING_ADDRESS),
			CommerceChannelAccountEntryRelConstants.TYPE_BILLING_ADDRESS);
	}

	@Override
	public AccountChannelEntry patchAccountChannelCurrencyId(
			Long id, AccountChannelEntry accountChannelEntry)
		throws Exception {

		return _patchAccountChannelEntry(
			accountChannelEntry,
			_getCommerceChannelAccountEntryRel(
				id, CommerceChannelAccountEntryRelConstants.TYPE_CURRENCY),
			CommerceChannelAccountEntryRelConstants.TYPE_CURRENCY);
	}

	@Override
	public AccountChannelEntry patchAccountChannelDeliveryTermId(
			Long id, AccountChannelEntry accountChannelEntry)
		throws Exception {

		return _patchAccountChannelEntry(
			accountChannelEntry,
			_getCommerceChannelAccountEntryRel(
				id, CommerceChannelAccountEntryRelConstants.TYPE_DELIVERY_TERM),
			CommerceChannelAccountEntryRelConstants.TYPE_DELIVERY_TERM);
	}

	@Override
	public AccountChannelEntry patchAccountChannelDiscountId(
			Long id, AccountChannelEntry accountChannelEntry)
		throws Exception {

		return _patchAccountChannelEntry(
			accountChannelEntry,
			_getCommerceChannelAccountEntryRel(
				id, CommerceChannelAccountEntryRelConstants.TYPE_DISCOUNT),
			CommerceChannelAccountEntryRelConstants.TYPE_DISCOUNT);
	}

	@Override
	public AccountChannelEntry patchAccountChannelPaymentMethodId(
			Long id, AccountChannelEntry accountChannelEntry)
		throws Exception {

		return _patchAccountChannelEntry(
			accountChannelEntry,
			_getCommerceChannelAccountEntryRel(
				id, CommerceChannelAccountEntryRelConstants.TYPE_PAYMENT),
			CommerceChannelAccountEntryRelConstants.TYPE_PAYMENT);
	}

	@Override
	public AccountChannelEntry patchAccountChannelPaymentTermId(
			Long id, AccountChannelEntry accountChannelEntry)
		throws Exception {

		return _patchAccountChannelEntry(
			accountChannelEntry,
			_getCommerceChannelAccountEntryRel(
				id, CommerceChannelAccountEntryRelConstants.TYPE_PAYMENT_TERM),
			CommerceChannelAccountEntryRelConstants.TYPE_PAYMENT_TERM);
	}

	@Override
	public AccountChannelEntry patchAccountChannelPriceListId(
			Long id, AccountChannelEntry accountChannelEntry)
		throws Exception {

		return _patchAccountChannelEntry(
			accountChannelEntry,
			_getCommerceChannelAccountEntryRel(
				id, CommerceChannelAccountEntryRelConstants.TYPE_PRICE_LIST),
			CommerceChannelAccountEntryRelConstants.TYPE_PRICE_LIST);
	}

	@Override
	public AccountChannelEntry patchAccountChannelShippingAddressId(
			Long id, AccountChannelEntry accountChannelEntry)
		throws Exception {

		return _patchAccountChannelEntry(
			accountChannelEntry,
			_getCommerceChannelAccountEntryRel(
				id,
				CommerceChannelAccountEntryRelConstants.TYPE_SHIPPING_ADDRESS),
			CommerceChannelAccountEntryRelConstants.TYPE_SHIPPING_ADDRESS);
	}

	@Override
	public AccountChannelEntry patchAccountChannelUserId(
			Long id, AccountChannelEntry accountChannelEntry)
		throws Exception {

		int type = CommerceChannelAccountEntryRelConstants.TYPE_USER;

		CommerceChannelAccountEntryRel commerceChannelAccountEntryRel =
			_getCommerceChannelAccountEntryRel(id, type);

		long classPK = commerceChannelAccountEntryRel.getClassPK();

		if ((accountChannelEntry.getClassPK() != null) ||
			(accountChannelEntry.getClassExternalReferenceCode() != null)) {

			try {
				classPK = _getClassPK(accountChannelEntry, type);
			}
			catch (Exception exception) {
				if (_log.isDebugEnabled()) {
					_log.debug(exception);
				}
			}
		}

		_checkPermission(_userService.getUserById(classPK));

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

		return _postAccountChannelEntry(
			accountChannelEntry,
			_accountEntryService.fetchAccountEntryByExternalReferenceCode(
				contextCompany.getCompanyId(), externalReferenceCode),
			Address.class.getName(),
			CommerceChannelAccountEntryRelConstants.TYPE_BILLING_ADDRESS);
	}

	@Override
	public AccountChannelEntry
			postAccountByExternalReferenceCodeAccountChannelCurrency(
				String externalReferenceCode,
				AccountChannelEntry accountChannelEntry)
		throws Exception {

		return _postAccountChannelEntry(
			accountChannelEntry,
			_accountEntryService.fetchAccountEntryByExternalReferenceCode(
				contextCompany.getCompanyId(), externalReferenceCode),
			CommerceCurrency.class.getName(),
			CommerceChannelAccountEntryRelConstants.TYPE_CURRENCY);
	}

	@Override
	public AccountChannelEntry
			postAccountByExternalReferenceCodeAccountChannelDeliveryTerm(
				String externalReferenceCode,
				AccountChannelEntry accountChannelEntry)
		throws Exception {

		return _postAccountChannelEntry(
			accountChannelEntry,
			_accountEntryService.fetchAccountEntryByExternalReferenceCode(
				contextCompany.getCompanyId(), externalReferenceCode),
			CommerceTermEntry.class.getName(),
			CommerceChannelAccountEntryRelConstants.TYPE_DELIVERY_TERM);
	}

	@Override
	public AccountChannelEntry
			postAccountByExternalReferenceCodeAccountChannelDiscount(
				String externalReferenceCode,
				AccountChannelEntry accountChannelEntry)
		throws Exception {

		return _postAccountChannelEntry(
			accountChannelEntry,
			_accountEntryService.fetchAccountEntryByExternalReferenceCode(
				contextCompany.getCompanyId(), externalReferenceCode),
			CommerceDiscount.class.getName(),
			CommerceChannelAccountEntryRelConstants.TYPE_DISCOUNT);
	}

	@Override
	public AccountChannelEntry
			postAccountByExternalReferenceCodeAccountChannelPaymentMethod(
				String externalReferenceCode,
				AccountChannelEntry accountChannelEntry)
		throws Exception {

		return _postAccountChannelEntry(
			accountChannelEntry,
			_accountEntryService.fetchAccountEntryByExternalReferenceCode(
				contextCompany.getCompanyId(), externalReferenceCode),
			CommerceTermEntry.class.getName(),
			CommerceChannelAccountEntryRelConstants.TYPE_PAYMENT);
	}

	@Override
	public AccountChannelEntry
			postAccountByExternalReferenceCodeAccountChannelPaymentTerm(
				String externalReferenceCode,
				AccountChannelEntry accountChannelEntry)
		throws Exception {

		return _postAccountChannelEntry(
			accountChannelEntry,
			_accountEntryService.fetchAccountEntryByExternalReferenceCode(
				contextCompany.getCompanyId(), externalReferenceCode),
			CommerceTermEntry.class.getName(),
			CommerceChannelAccountEntryRelConstants.TYPE_PAYMENT_TERM);
	}

	@Override
	public AccountChannelEntry
			postAccountByExternalReferenceCodeAccountChannelPriceList(
				String externalReferenceCode,
				AccountChannelEntry accountChannelEntry)
		throws Exception {

		return _postAccountChannelEntry(
			accountChannelEntry,
			_accountEntryService.fetchAccountEntryByExternalReferenceCode(
				contextCompany.getCompanyId(), externalReferenceCode),
			CommercePriceList.class.getName(),
			CommerceChannelAccountEntryRelConstants.TYPE_PRICE_LIST);
	}

	@Override
	public AccountChannelEntry
			postAccountByExternalReferenceCodeAccountChannelShippingAddress(
				String externalReferenceCode,
				AccountChannelEntry accountChannelEntry)
		throws Exception {

		return _postAccountChannelEntry(
			accountChannelEntry,
			_accountEntryService.fetchAccountEntryByExternalReferenceCode(
				contextCompany.getCompanyId(), externalReferenceCode),
			Address.class.getName(),
			CommerceChannelAccountEntryRelConstants.TYPE_SHIPPING_ADDRESS);
	}

	@Override
	public AccountChannelEntry
			postAccountByExternalReferenceCodeAccountChannelUser(
				String externalReferenceCode,
				AccountChannelEntry accountChannelEntry)
		throws Exception {

		_checkPermission(
			_userService.getUserById(
				_getClassPK(
					accountChannelEntry,
					CommerceChannelAccountEntryRelConstants.TYPE_USER)));

		return _postAccountChannelEntry(
			accountChannelEntry,
			_accountEntryService.fetchAccountEntryByExternalReferenceCode(
				contextCompany.getCompanyId(), externalReferenceCode),
			User.class.getName(),
			CommerceChannelAccountEntryRelConstants.TYPE_USER);
	}

	@Override
	public AccountChannelEntry postAccountIdAccountChannelBillingAddress(
			Long id, AccountChannelEntry accountChannelEntry)
		throws Exception {

		return _postAccountChannelEntry(
			accountChannelEntry, _accountEntryService.getAccountEntry(id),
			Address.class.getName(),
			CommerceChannelAccountEntryRelConstants.TYPE_BILLING_ADDRESS);
	}

	@Override
	public AccountChannelEntry postAccountIdAccountChannelCurrency(
			Long id, AccountChannelEntry accountChannelEntry)
		throws Exception {

		return _postAccountChannelEntry(
			accountChannelEntry, _accountEntryService.getAccountEntry(id),
			CommerceCurrency.class.getName(),
			CommerceChannelAccountEntryRelConstants.TYPE_CURRENCY);
	}

	@Override
	public AccountChannelEntry postAccountIdAccountChannelDeliveryTerm(
			Long id, AccountChannelEntry accountChannelEntry)
		throws Exception {

		return _postAccountChannelEntry(
			accountChannelEntry, _accountEntryService.getAccountEntry(id),
			CommerceTermEntry.class.getName(),
			CommerceChannelAccountEntryRelConstants.TYPE_DELIVERY_TERM);
	}

	@Override
	public AccountChannelEntry postAccountIdAccountChannelDiscount(
			Long id, AccountChannelEntry accountChannelEntry)
		throws Exception {

		return _postAccountChannelEntry(
			accountChannelEntry, _accountEntryService.getAccountEntry(id),
			CommerceDiscount.class.getName(),
			CommerceChannelAccountEntryRelConstants.TYPE_DISCOUNT);
	}

	@Override
	public AccountChannelEntry postAccountIdAccountChannelPaymentMethod(
			Long id, AccountChannelEntry accountChannelEntry)
		throws Exception {

		return _postAccountChannelEntry(
			accountChannelEntry, _accountEntryService.getAccountEntry(id),
			CommerceTermEntry.class.getName(),
			CommerceChannelAccountEntryRelConstants.TYPE_PAYMENT);
	}

	@Override
	public AccountChannelEntry postAccountIdAccountChannelPaymentTerm(
			Long id, AccountChannelEntry accountChannelEntry)
		throws Exception {

		return _postAccountChannelEntry(
			accountChannelEntry, _accountEntryService.getAccountEntry(id),
			CommerceTermEntry.class.getName(),
			CommerceChannelAccountEntryRelConstants.TYPE_PAYMENT_TERM);
	}

	@Override
	public AccountChannelEntry postAccountIdAccountChannelPriceList(
			Long id, AccountChannelEntry accountChannelEntry)
		throws Exception {

		return _postAccountChannelEntry(
			accountChannelEntry, _accountEntryService.getAccountEntry(id),
			CommercePriceList.class.getName(),
			CommerceChannelAccountEntryRelConstants.TYPE_PRICE_LIST);
	}

	@Override
	public AccountChannelEntry postAccountIdAccountChannelShippingAddress(
			Long id, AccountChannelEntry accountChannelEntry)
		throws Exception {

		return _postAccountChannelEntry(
			accountChannelEntry, _accountEntryService.getAccountEntry(id),
			Address.class.getName(),
			CommerceChannelAccountEntryRelConstants.TYPE_SHIPPING_ADDRESS);
	}

	@Override
	public AccountChannelEntry postAccountIdAccountChannelUser(
			Long id, AccountChannelEntry accountChannelEntry)
		throws Exception {

		_checkPermission(
			_userService.getUserById(
				_getClassPK(
					accountChannelEntry,
					CommerceChannelAccountEntryRelConstants.TYPE_USER)));

		return _postAccountChannelEntry(
			accountChannelEntry, _accountEntryService.getAccountEntry(id),
			User.class.getName(),
			CommerceChannelAccountEntryRelConstants.TYPE_USER);
	}

	private void _checkPermission(User user) throws Exception {
		if (!_accountEntryModelResourcePermission.contains(
				PermissionCheckerFactoryUtil.create(user), 0,
				CommerceAccountActionKeys.
					MANAGE_AVAILABLE_ACCOUNTS_VIA_USER_CHANNEL_REL)) {

			throw new CommerceChannelAccountEntryRelTypeException(
				"The user can not be set as account manager");
		}
	}

	private long _getClassPK(AccountChannelEntry accountChannelEntry, int type)
		throws Exception {

		if (type ==
				CommerceChannelAccountEntryRelConstants.TYPE_BILLING_ADDRESS) {

			CommerceAddress commerceAddress =
				_commerceAddressService.fetchByExternalReferenceCode(
					GetterUtil.getString(
						accountChannelEntry.getClassExternalReferenceCode()),
					contextCompany.getCompanyId());

			if (commerceAddress == null) {
				commerceAddress = _commerceAddressService.getCommerceAddress(
					GetterUtil.getLong(accountChannelEntry.getClassPK()));
			}

			int commerceAddressType = commerceAddress.getType();

			if ((CommerceAddressConstants.ADDRESS_TYPE_BILLING ==
					commerceAddressType) ||
				(CommerceAddressConstants.ADDRESS_TYPE_BILLING_AND_SHIPPING ==
					commerceAddressType)) {

				return commerceAddress.getCommerceAddressId();
			}
		}
		else if (type ==
					CommerceChannelAccountEntryRelConstants.TYPE_CURRENCY) {

			CommerceCurrency commerceCurrency =
				_commerceCurrencyService.getCommerceCurrency(
					GetterUtil.getLong(accountChannelEntry.getClassPK()));

			if (!commerceCurrency.isActive()) {
				throw new NoSuchCurrencyException();
			}

			return commerceCurrency.getCommerceCurrencyId();
		}
		else if (type ==
					CommerceChannelAccountEntryRelConstants.
						TYPE_DELIVERY_TERM) {

			CommerceTermEntry commerceTermEntry =
				_commerceTermEntryService.fetchByExternalReferenceCode(
					contextCompany.getCompanyId(),
					GetterUtil.getString(
						accountChannelEntry.getClassExternalReferenceCode()));

			if (commerceTermEntry == null) {
				commerceTermEntry =
					_commerceTermEntryService.getCommerceTermEntry(
						GetterUtil.getLong(accountChannelEntry.getClassPK()));
			}

			if (!commerceTermEntry.isActive()) {
				throw new NoSuchTermEntryException();
			}

			return commerceTermEntry.getCommerceTermEntryId();
		}
		else if (type ==
					CommerceChannelAccountEntryRelConstants.TYPE_DISCOUNT) {

			CommerceDiscount commerceDiscount =
				_commerceDiscountService.fetchByExternalReferenceCode(
					GetterUtil.getString(
						accountChannelEntry.getClassExternalReferenceCode()),
					contextCompany.getCompanyId());

			if (commerceDiscount == null) {
				commerceDiscount = _commerceDiscountService.getCommerceDiscount(
					GetterUtil.getLong(accountChannelEntry.getClassPK()));
			}

			if (!commerceDiscount.isActive()) {
				throw new NoSuchDiscountException();
			}

			return commerceDiscount.getCommerceDiscountId();
		}
		else if (type == CommerceChannelAccountEntryRelConstants.TYPE_PAYMENT) {
			CommercePaymentMethodGroupRel commercePaymentMethodGroupRel =
				_commercePaymentMethodGroupRelService.
					fetchCommercePaymentMethodGroupRel(
						GetterUtil.getLong(accountChannelEntry.getClassPK()));

			if ((commercePaymentMethodGroupRel == null) ||
				!commercePaymentMethodGroupRel.isActive()) {

				CommerceChannel commerceChannel =
					_commerceChannelService.fetchCommerceChannel(
						accountChannelEntry.getChannelId());

				commercePaymentMethodGroupRel =
					_commercePaymentMethodGroupRelService.
						fetchCommercePaymentMethodGroupRel(
							commerceChannel.getGroupId(),
							GetterUtil.getString(
								accountChannelEntry.
									getClassExternalReferenceCode()));

				if ((commercePaymentMethodGroupRel == null) ||
					!commercePaymentMethodGroupRel.isActive()) {

					throw new NoSuchPaymentMethodGroupRelException();
				}
			}

			return commercePaymentMethodGroupRel.
				getCommercePaymentMethodGroupRelId();
		}
		else if (type ==
					CommerceChannelAccountEntryRelConstants.TYPE_PAYMENT_TERM) {

			CommerceTermEntry commerceTermEntry =
				_commerceTermEntryService.fetchByExternalReferenceCode(
					contextCompany.getCompanyId(),
					GetterUtil.getString(
						accountChannelEntry.getClassExternalReferenceCode()));

			if (commerceTermEntry == null) {
				commerceTermEntry =
					_commerceTermEntryService.getCommerceTermEntry(
						GetterUtil.getLong(accountChannelEntry.getClassPK()));
			}

			if (!commerceTermEntry.isActive()) {
				throw new NoSuchTermEntryException();
			}

			return commerceTermEntry.getCommerceTermEntryId();
		}
		else if (type ==
					CommerceChannelAccountEntryRelConstants.TYPE_PRICE_LIST) {

			CommercePriceList commercePriceList =
				_commercePriceListService.fetchByExternalReferenceCode(
					GetterUtil.getString(
						accountChannelEntry.getClassExternalReferenceCode()),
					contextCompany.getCompanyId());

			if (commercePriceList == null) {
				commercePriceList =
					_commercePriceListService.getCommercePriceList(
						GetterUtil.getLong(accountChannelEntry.getClassPK()));
			}

			if (commercePriceList.isInactive()) {
				throw new NoSuchPriceListException();
			}

			return commercePriceList.getCommercePriceListId();
		}
		else if (type ==
					CommerceChannelAccountEntryRelConstants.
						TYPE_SHIPPING_ADDRESS) {

			CommerceAddress commerceAddress =
				_commerceAddressService.fetchByExternalReferenceCode(
					GetterUtil.getString(
						accountChannelEntry.getClassExternalReferenceCode()),
					contextCompany.getCompanyId());

			if (commerceAddress == null) {
				commerceAddress = _commerceAddressService.getCommerceAddress(
					GetterUtil.getLong(accountChannelEntry.getClassPK()));
			}

			int commerceAddressType = commerceAddress.getType();

			if ((CommerceAddressConstants.ADDRESS_TYPE_SHIPPING ==
					commerceAddressType) ||
				(CommerceAddressConstants.ADDRESS_TYPE_BILLING_AND_SHIPPING ==
					commerceAddressType)) {

				return commerceAddress.getCommerceAddressId();
			}
		}
		else if (type == CommerceChannelAccountEntryRelConstants.TYPE_USER) {
			User user = _userService.fetchUserByExternalReferenceCode(
				contextCompany.getCompanyId(),
				accountChannelEntry.getClassExternalReferenceCode());

			if (user == null) {
				user = _userService.getUserById(
					GetterUtil.getLong(accountChannelEntry.getClassPK()));
			}

			if (!user.isActive()) {
				throw new NoSuchUserException();
			}

			return user.getUserId();
		}

		throw new CommerceChannelAccountEntryRelTypeException(
			"Unknown type: " + type);
	}

	private CommerceChannelAccountEntryRel _getCommerceChannelAccountEntryRel(
			Long id, int type)
		throws Exception {

		CommerceChannelAccountEntryRel commerceChannelAccountEntryRel =
			_commerceChannelAccountEntryRelService.
				getCommerceChannelAccountEntryRel(id);

		if (type != commerceChannelAccountEntryRel.getType()) {
			throw new CommerceChannelAccountEntryRelTypeException(
				"Types do not match");
		}

		return commerceChannelAccountEntryRel;
	}

	private long _getCommerceChannelId(
			AccountChannelEntry accountChannelEntry, int type)
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
			if (type != CommerceChannelAccountEntryRelConstants.TYPE_PAYMENT) {
				return 0;
			}

			throw new NoSuchChannelException();
		}

		return commerceChannel.getCommerceChannelId();
	}

	private Page<AccountChannelEntry> _getPage(
			long accountEntryId, int accountEntryType, Pagination pagination)
		throws Exception {

		return Page.of(
			transform(
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

	private AccountChannelEntry _patchAccountChannelEntry(
			AccountChannelEntry accountChannelEntry,
			CommerceChannelAccountEntryRel commerceChannelAccountEntryRel,
			int type)
		throws Exception {

		long commerceChannelId =
			commerceChannelAccountEntryRel.getCommerceChannelId();

		if (accountChannelEntry.getChannelId() != null) {
			try {
				commerceChannelId = _getCommerceChannelId(
					accountChannelEntry,
					commerceChannelAccountEntryRel.getType());
			}
			catch (Exception exception) {
				if (_log.isDebugEnabled()) {
					_log.debug(exception);
				}
			}
		}

		long classPK = commerceChannelAccountEntryRel.getClassPK();

		if ((accountChannelEntry.getClassPK() != null) ||
			(accountChannelEntry.getClassExternalReferenceCode() != null)) {

			try {
				classPK = _getClassPK(accountChannelEntry, type);
			}
			catch (Exception exception) {
				if (_log.isDebugEnabled()) {
					_log.debug(exception);
				}
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
			AccountChannelEntry accountChannelEntry, AccountEntry accountEntry,
			String className, int type)
		throws Exception {

		if (accountEntry == null) {
			throw new NoSuchEntryException();
		}

		return _toAccountChannelEntry(
			_commerceChannelAccountEntryRelService.
				addCommerceChannelAccountEntryRel(
					accountEntry.getAccountEntryId(), className,
					_getClassPK(accountChannelEntry, type),
					_getCommerceChannelId(accountChannelEntry, type),
					GetterUtil.getBoolean(
						accountChannelEntry.getOverrideEligibility()),
					GetterUtil.getDouble(accountChannelEntry.getPriority()),
					type));
	}

	private AccountChannelEntry _toAccountChannelEntry(
			CommerceChannelAccountEntryRel commerceChannelAccountEntryRel)
		throws Exception {

		return _accountChannelEntryDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				contextAcceptLanguage.isAcceptAllLanguages(), null,
				_dtoConverterRegistry,
				commerceChannelAccountEntryRel.
					getCommerceChannelAccountEntryRelId(),
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
	private CommerceAddressService _commerceAddressService;

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
	private CommercePaymentMethodGroupRelService
		_commercePaymentMethodGroupRelService;

	@Reference
	private CommercePriceListService _commercePriceListService;

	@Reference
	private CommerceTermEntryService _commerceTermEntryService;

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;

	@Reference
	private UserService _userService;

}