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

package com.liferay.headless.commerce.admin.pricing.internal.dto.v2_0.converter;

import com.liferay.commerce.account.model.CommerceAccount;
import com.liferay.commerce.discount.model.CommerceDiscount;
import com.liferay.commerce.discount.model.CommerceDiscountAccountRel;
import com.liferay.commerce.discount.service.CommerceDiscountAccountRelService;
import com.liferay.headless.commerce.admin.pricing.dto.v2_0.DiscountAccount;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Riccardo Alberti
 */
@Component(
	enabled = false,
	property = "dto.class.name=com.liferay.commerce.discount.model.CommerceDiscountAccountRel",
	service = {DiscountAccountDTOConverter.class, DTOConverter.class}
)
public class DiscountAccountDTOConverter
	implements DTOConverter<CommerceDiscountAccountRel, DiscountAccount> {

	@Override
	public String getContentType() {
		return DiscountAccount.class.getSimpleName();
	}

	@Override
	public DiscountAccount toDTO(DTOConverterContext dtoConverterContext)
		throws Exception {

		CommerceDiscountAccountRel commerceDiscountAccountRel =
			_commerceDiscountAccountRelService.getCommerceDiscountAccountRel(
				(Long)dtoConverterContext.getId());

		CommerceAccount commerceAccount =
			commerceDiscountAccountRel.getCommerceAccount();
		CommerceDiscount commerceDiscount =
			commerceDiscountAccountRel.getCommerceDiscount();

		return new DiscountAccount() {
			{
				accountExternalReferenceCode =
					commerceAccount.getExternalReferenceCode();
				accountId = commerceAccount.getCommerceAccountId();
				actions = dtoConverterContext.getActions();
				discountAccountId =
					commerceDiscountAccountRel.
						getCommerceDiscountAccountRelId();
				discountExternalReferenceCode =
					commerceDiscount.getExternalReferenceCode();
				discountId = commerceDiscount.getCommerceDiscountId();
			}
		};
	}

	@Reference
	private CommerceDiscountAccountRelService
		_commerceDiscountAccountRelService;

}