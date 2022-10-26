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
import com.liferay.commerce.product.constants.CommerceChannelAccountEntryRelConstants;
import com.liferay.commerce.product.exception.CommerceChannelAccountEntryRelTypeException;
import com.liferay.commerce.product.exception.NoSuchChannelAccountEntryRelException;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.model.CommerceChannelAccountEntryRel;
import com.liferay.commerce.product.service.CommerceChannelAccountEntryRelService;
import com.liferay.commerce.product.service.CommerceChannelService;
import com.liferay.headless.commerce.admin.account.dto.v1_0.AccountChannelEntry;
import com.liferay.headless.commerce.admin.account.internal.dto.v1_0.converter.AccountChannelEntryDTOConverter;
import com.liferay.headless.commerce.admin.account.resource.v1_0.AccountChannelEntryResource;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Address;
import com.liferay.portal.kernel.model.ListType;
import com.liferay.portal.kernel.service.AddressService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.TransformUtil;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
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
	public Page<AccountChannelEntry>
			getAccountIdAccountChannelBillingAddressesPage(
				Long id, Pagination pagination)
		throws Exception {

		return _getAccountChannelEntryPage(
			id, CommerceChannelAccountEntryRelConstants.TYPE_BILLING_ADDRESS,
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
	public AccountChannelEntry postAccountIdAccountChannelShippingAddress(
			Long id, AccountChannelEntry accountChannelEntry)
		throws Exception {

		AccountEntry accountEntry = _accountEntryService.getAccountEntry(id);

		return _postAccountChannelEntry(
			accountChannelEntry, accountEntry.getAccountEntryId(),
			Address.class.getName(),
			CommerceChannelAccountEntryRelConstants.TYPE_SHIPPING_ADDRESS);
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

		if (type ==
				CommerceChannelAccountEntryRelConstants.TYPE_SHIPPING_ADDRESS) {

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
			commerceChannel = _commerceChannelService.getCommerceChannel(
				GetterUtil.getLong(accountChannelEntry.getChannelId()));
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
	private DTOConverterRegistry _dtoConverterRegistry;

}