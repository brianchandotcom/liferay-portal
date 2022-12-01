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

import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryService;
import com.liferay.commerce.account.exception.NoSuchAccountException;
import com.liferay.commerce.exception.NoSuchShippingMethodException;
import com.liferay.commerce.model.CommerceShippingMethod;
import com.liferay.commerce.model.CommerceShippingOptionAccountEntryRel;
import com.liferay.commerce.product.exception.NoSuchChannelException;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CommerceChannelService;
import com.liferay.commerce.service.CommerceShippingMethodService;
import com.liferay.commerce.service.CommerceShippingOptionAccountEntryRelService;
import com.liferay.commerce.shipping.engine.fixed.exception.NoSuchShippingFixedOptionException;
import com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOption;
import com.liferay.commerce.shipping.engine.fixed.service.CommerceShippingFixedOptionService;
import com.liferay.headless.commerce.admin.account.dto.v1_0.AccountChannelShippingOption;
import com.liferay.headless.commerce.admin.account.internal.dto.v1_0.converter.AccountChannelShippingOptionDTOConverter;
import com.liferay.headless.commerce.admin.account.resource.v1_0.AccountChannelShippingOptionResource;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Danny Situ
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/account-channel-shipping-option.properties",
	scope = ServiceScope.PROTOTYPE,
	service = AccountChannelShippingOptionResource.class
)
public class AccountChannelShippingOptionResourceImpl
	extends BaseAccountChannelShippingOptionResourceImpl {

	@Override
	public void deleteAccountChannelShippingOption(Long id) throws Exception {
		_commerceShippingOptionAccountEntryRelService.
			deleteCommerceShippingOptionAccountEntryRel(id);
	}

	@Override
	public Page<AccountChannelShippingOption>
			getAccountByExternalReferenceCodeAccountChannelShippingOptionPage(
				String externalReferenceCode, Pagination pagination)
		throws Exception {

		AccountEntry accountEntry =
			_accountEntryService.fetchAccountEntryByExternalReferenceCode(
				contextCompany.getCompanyId(), externalReferenceCode);

		if (accountEntry == null) {
			throw new NoSuchAccountException();
		}

		return _getPage(accountEntry.getAccountEntryId(), pagination);
	}

	@Override
	public AccountChannelShippingOption getAccountChannelShippingOption(Long id)
		throws Exception {

		return _toAccountChannelShippingOption(
			_commerceShippingOptionAccountEntryRelService.
				getCommerceShippingOptionAccountEntryRel(id));
	}

	@Override
	public Page<AccountChannelShippingOption>
			getAccountIdAccountChannelShippingOptionPage(
				Long id, Pagination pagination)
		throws Exception {

		AccountEntry accountEntry = _accountEntryService.fetchAccountEntry(id);

		if (accountEntry == null) {
			throw new NoSuchAccountException();
		}

		return _getPage(accountEntry.getAccountEntryId(), pagination);
	}

	@Override
	public AccountChannelShippingOption patchAccountChannelShippingOption(
			Long id, AccountChannelShippingOption accountChannelShippingOption)
		throws Exception {

		CommerceShippingOptionAccountEntryRel
			commerceShippingOptionAccountEntryRel =
				_commerceShippingOptionAccountEntryRelService.
					getCommerceShippingOptionAccountEntryRel(id);

		if ((accountChannelShippingOption.getShippingMethodKey() == null) &&
			Validator.isNull(
				accountChannelShippingOption.getShippingMethodId())) {

			accountChannelShippingOption.setShippingMethodKey(
				commerceShippingOptionAccountEntryRel.
					getCommerceShippingMethodKey());
		}
		else {
			CommerceChannel commerceChannel =
				_commerceChannelService.getCommerceChannel(
					commerceShippingOptionAccountEntryRel.
						getCommerceChannelId());

			CommerceShippingMethod commerceShippingMethod =
				_commerceShippingMethodService.fetchCommerceShippingMethod(
					commerceChannel.getGroupId(),
					accountChannelShippingOption.getShippingMethodKey());

			if ((commerceShippingMethod == null) ||
				!commerceShippingMethod.isActive()) {

				commerceShippingMethod =
					_commerceShippingMethodService.fetchCommerceShippingMethod(
						accountChannelShippingOption.getShippingMethodId());

				if ((commerceShippingMethod == null) ||
					!commerceShippingMethod.isActive()) {

					throw new NoSuchShippingMethodException();
				}

				accountChannelShippingOption.setShippingMethodKey(
					commerceShippingMethod.getEngineKey());
			}
		}

		if ((accountChannelShippingOption.getShippingOptionKey() == null) &&
			Validator.isNull(
				accountChannelShippingOption.getShippingOptionId())) {

			accountChannelShippingOption.setShippingOptionKey(
				commerceShippingOptionAccountEntryRel.
					getCommerceShippingOptionKey());
		}
		else {
			CommerceShippingFixedOption commerceShippingFixedOption =
				_commerceShippingFixedOptionService.
					fetchCommerceShippingFixedOption(
						contextCompany.getCompanyId(),
						accountChannelShippingOption.getShippingOptionKey());

			if (commerceShippingFixedOption == null) {
				commerceShippingFixedOption =
					_commerceShippingFixedOptionService.
						fetchCommerceShippingFixedOption(
							accountChannelShippingOption.getShippingOptionId());

				if (commerceShippingFixedOption == null) {
					throw new NoSuchShippingFixedOptionException();
				}

				accountChannelShippingOption.setShippingOptionKey(
					commerceShippingFixedOption.getKey());
			}
		}

		return _toAccountChannelShippingOption(
			_commerceShippingOptionAccountEntryRelService.
				updateCommerceShippingOptionAccountEntryRel(
					commerceShippingOptionAccountEntryRel.
						getCommerceShippingOptionAccountEntryRelId(),
					accountChannelShippingOption.getShippingMethodKey(),
					accountChannelShippingOption.getShippingOptionKey()));
	}

	@Override
	public AccountChannelShippingOption
			postAccountByExternalReferenceCodeAccountChannelShippingOption(
				String externalReferenceCode,
				AccountChannelShippingOption accountChannelShippingOption)
		throws Exception {

		AccountEntry accountEntry =
			_accountEntryService.fetchAccountEntryByExternalReferenceCode(
				contextCompany.getCompanyId(), externalReferenceCode);

		if (accountEntry == null) {
			throw new NoSuchAccountException();
		}

		return postAccountIdAccountChannelShippingOption(
			(Long)accountEntry.getAccountEntryId(),
			accountChannelShippingOption);
	}

	@Override
	public AccountChannelShippingOption
			postAccountIdAccountChannelShippingOption(
				Long id,
				AccountChannelShippingOption accountChannelShippingOption)
		throws Exception {

		AccountEntry accountEntry = _accountEntryService.fetchAccountEntry(id);

		if (accountEntry == null) {
			throw new NoSuchAccountException();
		}

		CommerceChannel commerceChannel =
			_commerceChannelService.getCommerceChannel(
				_getCommerceChannelId(accountChannelShippingOption));

		CommerceShippingMethod commerceShippingMethod =
			_commerceShippingMethodService.fetchCommerceShippingMethod(
				commerceChannel.getGroupId(),
				accountChannelShippingOption.getShippingMethodKey());

		if ((commerceShippingMethod == null) ||
			!commerceShippingMethod.isActive()) {

			commerceShippingMethod =
				_commerceShippingMethodService.fetchCommerceShippingMethod(
					accountChannelShippingOption.getShippingMethodId());

			if ((commerceShippingMethod == null) ||
				!commerceShippingMethod.isActive()) {

				throw new NoSuchShippingMethodException();
			}
		}

		CommerceShippingFixedOption commerceShippingFixedOption =
			_commerceShippingFixedOptionService.
				fetchCommerceShippingFixedOption(
					contextCompany.getCompanyId(),
					accountChannelShippingOption.getShippingOptionKey());

		if (commerceShippingFixedOption == null) {
			commerceShippingFixedOption =
				_commerceShippingFixedOptionService.
					fetchCommerceShippingFixedOption(
						accountChannelShippingOption.getShippingOptionId());

			if (commerceShippingFixedOption == null) {
				throw new NoSuchShippingFixedOptionException();
			}
		}

		CommerceShippingOptionAccountEntryRel
			commerceShippingOptionAccountEntryRel =
				_commerceShippingOptionAccountEntryRelService.
					fetchCommerceShippingOptionAccountEntryRel(
						accountEntry.getAccountEntryId(),
						commerceChannel.getCommerceChannelId());

		if (commerceShippingOptionAccountEntryRel != null) {
			throw new DuplicateCommerceShippingOptionAccountEntryRelException();
		}

		return _toAccountChannelShippingOption(
			_commerceShippingOptionAccountEntryRelService.
				addCommerceShippingOptionAccountEntryRel(
					accountEntry.getAccountEntryId(),
					commerceChannel.getCommerceChannelId(),
					commerceShippingMethod.getEngineKey(),
					commerceShippingFixedOption.getKey()));
	}

	private long _getCommerceChannelId(
			AccountChannelShippingOption accountChannelShippingOption)
		throws Exception {

		CommerceChannel commerceChannel =
			_commerceChannelService.fetchByExternalReferenceCode(
				GetterUtil.getString(
					accountChannelShippingOption.
						getChannelExternalReferenceCode()),
				contextCompany.getCompanyId());

		if (commerceChannel == null) {
			commerceChannel = _commerceChannelService.fetchCommerceChannel(
				GetterUtil.getLong(
					accountChannelShippingOption.getChannelId()));
		}

		if (commerceChannel == null) {
			throw new NoSuchChannelException();
		}

		return commerceChannel.getCommerceChannelId();
	}

	private Page<AccountChannelShippingOption> _getPage(
			Long accountEntryId, Pagination pagination)
		throws Exception {

		return Page.of(
			transform(
				_commerceShippingOptionAccountEntryRelService.
					getCommerceShippingOptionAccountEntryRels(accountEntryId),
				commerceShippingOptionAccountEntryRel ->
					_toAccountChannelShippingOption(
						commerceShippingOptionAccountEntryRel)),
			pagination,
			_commerceShippingOptionAccountEntryRelService.
				getCommerceShippingOptionAccountEntryRelsCount(accountEntryId));
	}

	private AccountChannelShippingOption _toAccountChannelShippingOption(
			CommerceShippingOptionAccountEntryRel
				commerceShippingOptionAccountEntryRel)
		throws Exception {

		return _accountChannelShippingOptionDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				contextAcceptLanguage.isAcceptAllLanguages(), null,
				_dtoConverterRegistry,
				commerceShippingOptionAccountEntryRel.
					getCommerceShippingOptionAccountEntryRelId(),
				contextAcceptLanguage.getPreferredLocale(), contextUriInfo,
				contextUser));
	}

	@Reference
	private AccountChannelShippingOptionDTOConverter
		_accountChannelShippingOptionDTOConverter;

	@Reference
	private AccountEntryService _accountEntryService;

	@Reference
	private CommerceChannelService _commerceChannelService;

	@Reference
	private CommerceShippingFixedOptionService
		_commerceShippingFixedOptionService;

	@Reference
	private CommerceShippingMethodService _commerceShippingMethodService;

	@Reference
	private CommerceShippingOptionAccountEntryRelService
		_commerceShippingOptionAccountEntryRelService;

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;

}