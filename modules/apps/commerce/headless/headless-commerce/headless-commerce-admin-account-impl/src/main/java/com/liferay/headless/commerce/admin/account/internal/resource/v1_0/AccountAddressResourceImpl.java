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

import com.liferay.commerce.account.exception.NoSuchAccountException;
import com.liferay.commerce.account.model.CommerceAccount;
import com.liferay.commerce.account.service.CommerceAccountService;
import com.liferay.commerce.constants.CommerceAddressConstants;
import com.liferay.commerce.exception.NoSuchAddressException;
import com.liferay.commerce.model.CommerceAddress;
import com.liferay.commerce.service.CommerceAddressService;
import com.liferay.headless.commerce.admin.account.dto.v1_0.Account;
import com.liferay.headless.commerce.admin.account.dto.v1_0.AccountAddress;
import com.liferay.headless.commerce.admin.account.internal.dto.v1_0.converter.AccountAddressDTOConverter;
import com.liferay.headless.commerce.admin.account.resource.v1_0.AccountAddressResource;
import com.liferay.headless.commerce.core.util.ServiceContextHelper;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.model.Region;
import com.liferay.portal.kernel.service.CountryLocalService;
import com.liferay.portal.kernel.service.RegionLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.fields.NestedField;
import com.liferay.portal.vulcan.fields.NestedFieldSupport;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false,
	properties = "OSGI-INF/liferay/rest/v1_0/account-address.properties",
	scope = ServiceScope.PROTOTYPE,
	service = {AccountAddressResource.class, NestedFieldSupport.class}
)
public class AccountAddressResourceImpl
	extends BaseAccountAddressResourceImpl implements NestedFieldSupport {

	@Override
	public Response deleteAccountAddress(Long id) throws Exception {
		_commerceAddressService.deleteCommerceAddress(id);

		Response.ResponseBuilder responseBuilder = Response.noContent();

		return responseBuilder.build();
	}

	@Override
	public Response deleteAccountAddressByExternalReferenceCode(
			String externalReferenceCode)
		throws Exception {

		CommerceAddress commerceAddress =
			_commerceAddressService.fetchByExternalReferenceCode(
				externalReferenceCode, contextCompany.getCompanyId());

		if (commerceAddress == null) {
			throw new NoSuchAddressException(
				"Unable to find account address with external reference code " +
					externalReferenceCode);
		}

		_commerceAddressService.deleteCommerceAddress(
			commerceAddress.getCommerceAddressId());

		Response.ResponseBuilder responseBuilder = Response.noContent();

		return responseBuilder.build();
	}

	@Override
	public AccountAddress getAccountAddress(Long id) throws Exception {
		return _toAccountAddress(
			_commerceAddressService.getCommerceAddress(id));
	}

	@Override
	public AccountAddress getAccountAddressByExternalReferenceCode(
			String externalReferenceCode)
		throws Exception {

		CommerceAddress commerceAddress =
			_commerceAddressService.fetchByExternalReferenceCode(
				externalReferenceCode, contextCompany.getCompanyId());

		if (commerceAddress == null) {
			throw new NoSuchAddressException(
				"Unable to find account address with external reference code " +
					externalReferenceCode);
		}

		return _toAccountAddress(commerceAddress);
	}

	@Override
	public Page<AccountAddress>
			getAccountByExternalReferenceCodeAccountAddressesPage(
				String externalReferenceCode, Pagination pagination)
		throws Exception {

		CommerceAccount commerceAccount =
			_commerceAccountService.fetchByExternalReferenceCode(
				contextCompany.getCompanyId(), externalReferenceCode);

		if (commerceAccount == null) {
			throw new NoSuchAccountException(
				"Unable to find account with external reference code " +
					externalReferenceCode);
		}

		return _getAccountAddressesPage(commerceAccount, pagination);
	}

	@NestedField(parentClass = Account.class, value = "accountAddresses")
	@Override
	public Page<AccountAddress> getAccountIdAccountAddressesPage(
			Long id, Pagination pagination)
		throws Exception {

		return _getAccountAddressesPage(
			_commerceAccountService.getCommerceAccount(id), pagination);
	}

	@Override
	public AccountAddress patchAccountAddress(
			Long id, AccountAddress accountAddress)
		throws Exception {

		CommerceAddress commerceAddress =
			_commerceAddressService.getCommerceAddress(id);

		Country country = _countryService.getCountryByA2(
			commerceAddress.getCompanyId(), accountAddress.getCountryISOCode());

		commerceAddress = _commerceAddressService.updateCommerceAddress(
			commerceAddress.getCommerceAddressId(),
			GetterUtil.getString(
				accountAddress.getName(), commerceAddress.getName()),
			GetterUtil.getString(
				accountAddress.getDescription(),
				commerceAddress.getDescription()),
			GetterUtil.getString(
				accountAddress.getStreet1(), commerceAddress.getStreet1()),
			GetterUtil.getString(
				accountAddress.getStreet2(), commerceAddress.getStreet2()),
			GetterUtil.getString(
				accountAddress.getStreet3(), commerceAddress.getStreet3()),
			GetterUtil.getString(
				accountAddress.getCity(), commerceAddress.getCity()),
			GetterUtil.getString(
				accountAddress.getZip(), commerceAddress.getZip()),
			GetterUtil.getLong(
				_getRegionId(country, accountAddress),
				commerceAddress.getRegionId()),
			GetterUtil.getLong(
				_getCountryId(country), commerceAddress.getCountryId()),
			GetterUtil.getString(
				accountAddress.getPhoneNumber(),
				commerceAddress.getPhoneNumber()),
			GetterUtil.getInteger(
				accountAddress.getType(), commerceAddress.getType()),
			_serviceContextHelper.getServiceContext());

		return _toAccountAddress(commerceAddress);
	}

	@Override
	public Response patchAccountAddressByExternalReferenceCode(
			String externalReferenceCode, AccountAddress accountAddress)
		throws Exception {

		CommerceAddress commerceAddress =
			_commerceAddressService.fetchByExternalReferenceCode(
				externalReferenceCode, contextCompany.getCompanyId());

		if (commerceAddress == null) {
			throw new NoSuchAddressException(
				"Unable to find account address with external reference code " +
					externalReferenceCode);
		}

		Country country = _countryService.getCountryByA2(
			commerceAddress.getCompanyId(), accountAddress.getCountryISOCode());

		_commerceAddressService.updateCommerceAddress(
			commerceAddress.getCommerceAddressId(),
			GetterUtil.getString(
				accountAddress.getName(), commerceAddress.getName()),
			GetterUtil.getString(
				accountAddress.getDescription(),
				commerceAddress.getDescription()),
			GetterUtil.getString(
				accountAddress.getStreet1(), commerceAddress.getStreet1()),
			GetterUtil.getString(
				accountAddress.getStreet2(), commerceAddress.getStreet2()),
			GetterUtil.getString(
				accountAddress.getStreet3(), commerceAddress.getStreet3()),
			GetterUtil.getString(
				accountAddress.getCity(), commerceAddress.getCity()),
			GetterUtil.getString(
				accountAddress.getZip(), commerceAddress.getZip()),
			GetterUtil.getLong(
				_getRegionId(country, accountAddress),
				commerceAddress.getRegionId()),
			GetterUtil.getLong(
				_getCountryId(country), commerceAddress.getCountryId()),
			GetterUtil.getString(
				accountAddress.getPhoneNumber(),
				commerceAddress.getPhoneNumber()),
			GetterUtil.getInteger(
				accountAddress.getType(), commerceAddress.getType()),
			_serviceContextHelper.getServiceContext());

		Response.ResponseBuilder responseBuilder = Response.noContent();

		return responseBuilder.build();
	}

	@Override
	public AccountAddress postAccountByExternalReferenceCodeAccountAddress(
			String externalReferenceCode, AccountAddress accountAddress)
		throws Exception {

		CommerceAccount commerceAccount =
			_commerceAccountService.fetchByExternalReferenceCode(
				contextCompany.getCompanyId(), externalReferenceCode);

		if (commerceAccount == null) {
			throw new NoSuchAccountException(
				"Unable to find account with external reference code " +
					externalReferenceCode);
		}

		CommerceAddress commerceAddress = null;

		if (accountAddress.getId() != null) {
			commerceAddress = _commerceAddressService.fetchCommerceAddress(
				accountAddress.getId());
		}
		else if (accountAddress.getExternalReferenceCode() != null) {
			commerceAddress =
				_commerceAddressService.fetchByExternalReferenceCode(
					accountAddress.getExternalReferenceCode(),
					contextCompany.getCompanyId());
		}

		if (commerceAddress != null) {
			Country country = _countryService.getCountryByA2(
				commerceAddress.getCompanyId(),
				accountAddress.getCountryISOCode());

			return _toAccountAddress(
				_commerceAddressService.updateCommerceAddress(
					commerceAddress.getCommerceAddressId(),
					GetterUtil.getString(accountAddress.getName(), null),
					GetterUtil.getString(accountAddress.getDescription(), null),
					GetterUtil.getString(accountAddress.getStreet1(), null),
					GetterUtil.getString(accountAddress.getStreet2(), null),
					GetterUtil.getString(accountAddress.getStreet3(), null),
					GetterUtil.getString(accountAddress.getCity(), null),
					GetterUtil.getString(accountAddress.getZip(), null),
					GetterUtil.getLong(
						_getRegionId(country, accountAddress),
						commerceAddress.getRegionId()),
					GetterUtil.getLong(
						_getCountryId(country), commerceAddress.getCountryId()),
					GetterUtil.getString(accountAddress.getPhoneNumber(), null),
					GetterUtil.getInteger(
						accountAddress.getType(), commerceAddress.getType()),
					_serviceContextHelper.getServiceContext()));
		}

		return _addAccountAddress(commerceAccount, accountAddress);
	}

	@Override
	public AccountAddress postAccountIdAccountAddress(
			Long id, AccountAddress accountAddress)
		throws Exception {

		return _addAccountAddress(
			_commerceAccountService.getCommerceAccount(id), accountAddress);
	}

	@Override
	public AccountAddress putAccountAddress(
			Long id, AccountAddress accountAddress)
		throws Exception {

		CommerceAddress commerceAddress =
			_commerceAddressService.getCommerceAddress(id);

		Country country = _countryService.getCountryByA2(
			commerceAddress.getCompanyId(), accountAddress.getCountryISOCode());

		commerceAddress = _commerceAddressService.updateCommerceAddress(
			commerceAddress.getCommerceAddressId(),
			GetterUtil.getString(accountAddress.getName()),
			GetterUtil.getString(accountAddress.getDescription()),
			GetterUtil.getString(accountAddress.getStreet1()),
			GetterUtil.getString(accountAddress.getStreet2()),
			GetterUtil.getString(accountAddress.getStreet3()),
			GetterUtil.getString(accountAddress.getCity()),
			GetterUtil.getString(accountAddress.getZip()),
			GetterUtil.getLong(
				_getRegionId(country, accountAddress),
				commerceAddress.getRegionId()),
			GetterUtil.getLong(
				_getCountryId(country), commerceAddress.getCountryId()),
			GetterUtil.getString(accountAddress.getPhoneNumber()),
			GetterUtil.getInteger(accountAddress.getType()),
			_serviceContextHelper.getServiceContext());

		return _toAccountAddress(commerceAddress);
	}

	private AccountAddress _addAccountAddress(
			CommerceAccount commerceAccount, AccountAddress accountAddress)
		throws Exception {

		Country country = _countryService.getCountryByA2(
			commerceAccount.getCompanyId(), accountAddress.getCountryISOCode());

		CommerceAddress commerceAddress =
			_commerceAddressService.addCommerceAddress(
				GetterUtil.getString(
					accountAddress.getExternalReferenceCode(), null),
				commerceAccount.getModelClassName(),
				commerceAccount.getCommerceAccountId(),
				accountAddress.getName(), accountAddress.getDescription(),
				accountAddress.getStreet1(), accountAddress.getStreet2(),
				accountAddress.getStreet3(), accountAddress.getCity(),
				accountAddress.getZip(), _getRegionId(country, accountAddress),
				country.getCountryId(), accountAddress.getPhoneNumber(),
				GetterUtil.getInteger(
					accountAddress.getType(),
					CommerceAddressConstants.ADDRESS_TYPE_BILLING_AND_SHIPPING),
				_serviceContextHelper.getServiceContext());

		return _accountAddressDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				commerceAddress.getCommerceAddressId(),
				contextAcceptLanguage.getPreferredLocale()));
	}

	private Page<AccountAddress> _getAccountAddressesPage(
			CommerceAccount commerceAccount, Pagination pagination)
		throws Exception {

		List<CommerceAddress> commerceAddresses =
			_commerceAddressService.getCommerceAddresses(
				commerceAccount.getModelClassName(),
				commerceAccount.getCommerceAccountId(),
				pagination.getStartPosition(), pagination.getEndPosition(),
				null);

		int totalItems = _commerceAddressService.getCommerceAddressesCount(
			commerceAccount.getModelClassName(),
			commerceAccount.getCommerceAccountId());

		return Page.of(
			_toAccountAddresses(commerceAddresses), pagination, totalItems);
	}

	private long _getCountryId(Country country) {
		if (country == null) {
			return 0;
		}

		return country.getCountryId();
	}

	private long _getRegionId(Country country, AccountAddress accountAddress)
		throws Exception {

		if (Validator.isNull(accountAddress.getRegionISOCode()) ||
			(country == null)) {

			return 0;
		}

		Region region = _regionLocalService.getRegion(
			country.getCountryId(), accountAddress.getRegionISOCode());

		return region.getRegionId();
	}

	private AccountAddress _toAccountAddress(CommerceAddress commerceAddress)
		throws Exception {

		return _accountAddressDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				commerceAddress.getCommerceAddressId(),
				contextAcceptLanguage.getPreferredLocale()));
	}

	private List<AccountAddress> _toAccountAddresses(
			List<CommerceAddress> commerceAddresses)
		throws Exception {

		List<AccountAddress> accountAddresses = new ArrayList<>();

		for (CommerceAddress commerceAddress : commerceAddresses) {
			accountAddresses.add(
				_accountAddressDTOConverter.toDTO(
					new DefaultDTOConverterContext(
						commerceAddress.getCommerceAddressId(),
						contextAcceptLanguage.getPreferredLocale())));
		}

		return accountAddresses;
	}

	@Reference
	private AccountAddressDTOConverter _accountAddressDTOConverter;

	@Reference
	private CommerceAccountService _commerceAccountService;

	@Reference
	private CommerceAddressService _commerceAddressService;

	@Reference
	private CountryLocalService _countryService;

	@Reference
	private RegionLocalService _regionLocalService;

	@Reference
	private ServiceContextHelper _serviceContextHelper;

}