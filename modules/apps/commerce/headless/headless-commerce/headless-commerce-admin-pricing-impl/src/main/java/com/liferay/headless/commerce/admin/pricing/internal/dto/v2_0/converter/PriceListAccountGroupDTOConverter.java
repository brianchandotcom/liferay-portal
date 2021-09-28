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

import com.liferay.commerce.account.model.CommerceAccountGroup;
import com.liferay.commerce.price.list.model.CommercePriceList;
import com.liferay.commerce.price.list.model.CommercePriceListCommerceAccountGroupRel;
import com.liferay.commerce.price.list.service.CommercePriceListCommerceAccountGroupRelService;
import com.liferay.headless.commerce.admin.pricing.dto.v2_0.PriceListAccountGroup;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false,
	property = "dto.class.name=com.liferay.commerce.price.list.model.CommercePriceListCommerceAccountGroupRel",
	service = {DTOConverter.class, PriceListAccountGroupDTOConverter.class}
)
public class PriceListAccountGroupDTOConverter
	implements DTOConverter
		<CommercePriceListCommerceAccountGroupRel, PriceListAccountGroup> {

	@Override
	public String getContentType() {
		return PriceListAccountGroup.class.getSimpleName();
	}

	@Override
	public PriceListAccountGroup toDTO(DTOConverterContext dtoConverterContext)
		throws Exception {

		CommercePriceListCommerceAccountGroupRel
			commercePriceListCommerceAccountGroupRel =
				_commercePriceListCommerceAccountGroupRelService.
					getCommercePriceListCommerceAccountGroupRel(
						(Long)dtoConverterContext.getId());

		CommerceAccountGroup commerceAccountGroup =
			commercePriceListCommerceAccountGroupRel.getCommerceAccountGroup();
		CommercePriceList commercePriceList =
			commercePriceListCommerceAccountGroupRel.getCommercePriceList();

		return new PriceListAccountGroup() {
			{
				accountGroupExternalReferenceCode =
					commerceAccountGroup.getExternalReferenceCode();
				accountGroupId =
					commerceAccountGroup.getCommerceAccountGroupId();
				actions = dtoConverterContext.getActions();
				order = commercePriceListCommerceAccountGroupRel.getOrder();
				priceListAccountGroupId =
					commercePriceListCommerceAccountGroupRel.
						getCommercePriceListCommerceAccountGroupRelId();
				priceListExternalReferenceCode =
					commercePriceList.getExternalReferenceCode();
				priceListId = commercePriceList.getCommercePriceListId();
			}
		};
	}

	@Reference
	private CommercePriceListCommerceAccountGroupRelService
		_commercePriceListCommerceAccountGroupRelService;

}