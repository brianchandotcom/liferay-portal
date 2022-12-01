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

package com.liferay.headless.commerce.admin.account.internal.dto.v1_0.converter;

import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryService;
import com.liferay.commerce.model.CommerceShippingMethod;
import com.liferay.commerce.model.CommerceShippingOptionAccountEntryRel;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CommerceChannelService;
import com.liferay.commerce.service.CommerceShippingMethodService;
import com.liferay.commerce.service.CommerceShippingOptionAccountEntryRelService;
import com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOption;
import com.liferay.commerce.shipping.engine.fixed.service.CommerceShippingFixedOptionService;
import com.liferay.headless.commerce.admin.account.dto.v1_0.AccountChannelShippingOption;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Danny Situ
 */
@Component(
	property = "dto.class.name=com.liferay.commerce.model.CommerceShippingOptionAccountEntryRel",
	service = {
		AccountChannelShippingOptionDTOConverter.class, DTOConverter.class
	}
)
public class AccountChannelShippingOptionDTOConverter
	implements DTOConverter
		<CommerceShippingOptionAccountEntryRel, AccountChannelShippingOption> {

	@Override
	public String getContentType() {
		return AccountChannelShippingOption.class.getSimpleName();
	}

	@Override
	public AccountChannelShippingOption toDTO(
			DTOConverterContext dtoConverterContext)
		throws Exception {

		CommerceShippingOptionAccountEntryRel
			commerceShippingOptionAccountEntryRel =
				_commerceShippingOptionAccountEntryRelService.
					getCommerceShippingOptionAccountEntryRel(
						(Long)dtoConverterContext.getId());

		return new AccountChannelShippingOption() {
			{
				setAccountExternalReferenceCode(
					() -> {
						AccountEntry accountEntry =
							_accountEntryService.fetchAccountEntry(
								commerceShippingOptionAccountEntryRel.
									getAccountEntryId());

						if ((accountEntry != null) &&
							!Validator.isBlank(
								accountEntry.getExternalReferenceCode())) {

							return accountEntry.getExternalReferenceCode();
						}

						return null;
					});
				accountId =
					commerceShippingOptionAccountEntryRel.getAccountEntryId();
				channelId =
					commerceShippingOptionAccountEntryRel.
						getCommerceChannelId();
				id =
					commerceShippingOptionAccountEntryRel.
						getCommerceShippingOptionAccountEntryRelId();

				setShippingMethodId(
					() -> {
						CommerceChannel commerceChannel =
							_commerceChannelService.getCommerceChannel(
								commerceShippingOptionAccountEntryRel.
									getCommerceChannelId());

						CommerceShippingMethod commerceShippingMethod =
							_commerceShippingMethodService.
								fetchCommerceShippingMethod(
									commerceChannel.getGroupId(),
									commerceShippingOptionAccountEntryRel.
										getCommerceShippingMethodKey());

						if (commerceShippingMethod == null) {
							return 0L;
						}

						return commerceShippingMethod.
							getCommerceShippingMethodId();
					});
				shippingMethodKey =
					commerceShippingOptionAccountEntryRel.
						getCommerceShippingMethodKey();

				setShippingOptionId(
					() -> {
						CommerceShippingFixedOption
							commerceShippingFixedOption =
								_commerceShippingFixedOptionService.
									fetchCommerceShippingFixedOption(
										commerceShippingOptionAccountEntryRel.
											getCompanyId(),
										commerceShippingOptionAccountEntryRel.
											getCommerceShippingOptionKey());

						if (commerceShippingFixedOption == null) {
							return 0L;
						}

						return commerceShippingFixedOption.
							getCommerceShippingFixedOptionId();
					});
				shippingOptionKey =
					commerceShippingOptionAccountEntryRel.
						getCommerceShippingOptionKey();
			}
		};
	}

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

}