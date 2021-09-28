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
import com.liferay.commerce.price.list.model.CommercePriceList;
import com.liferay.commerce.price.list.model.CommercePriceListAccountRel;
import com.liferay.commerce.price.list.service.CommercePriceListAccountRelService;
import com.liferay.headless.commerce.admin.pricing.dto.v2_0.PriceListAccount;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false,
	property = "dto.class.name=com.liferay.commerce.price.list.model.CommercePriceListAccountRel",
	service = {DTOConverter.class, PriceListAccountDTOConverter.class}
)
public class PriceListAccountDTOConverter
	implements DTOConverter<CommercePriceListAccountRel, PriceListAccount> {

	@Override
	public String getContentType() {
		return PriceListAccount.class.getSimpleName();
	}

	@Override
	public PriceListAccount toDTO(DTOConverterContext dtoConverterContext)
		throws Exception {

		CommercePriceListAccountRel commercePriceListAccountRel =
			_commercePriceListAccountRelService.getCommercePriceListAccountRel(
				(Long)dtoConverterContext.getId());

		CommerceAccount commerceAccount =
			commercePriceListAccountRel.getCommerceAccount();
		CommercePriceList commercePriceList =
			commercePriceListAccountRel.getCommercePriceList();

		return new PriceListAccount() {
			{
				accountExternalReferenceCode =
					commerceAccount.getExternalReferenceCode();
				accountId = commerceAccount.getCommerceAccountId();
				actions = dtoConverterContext.getActions();
				order = commercePriceListAccountRel.getOrder();
				priceListAccountId =
					commercePriceListAccountRel.
						getCommercePriceListAccountRelId();
				priceListExternalReferenceCode =
					commercePriceList.getExternalReferenceCode();
				priceListId = commercePriceList.getCommercePriceListId();
			}
		};
	}

	@Reference
	private CommercePriceListAccountRelService
		_commercePriceListAccountRelService;

}