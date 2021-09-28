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

package com.liferay.headless.commerce.admin.pricing.internal.dto.v1_0.converter;

import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.price.list.model.CommercePriceList;
import com.liferay.commerce.price.list.service.CommercePriceListService;
import com.liferay.commerce.product.model.CommerceCatalog;
import com.liferay.commerce.product.service.CommerceCatalogService;
import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.headless.commerce.admin.pricing.dto.v1_0.PriceList;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false,
	property = "dto.class.name=com.liferay.commerce.price.list.model.CommercePriceList",
	service = {DTOConverter.class, PriceListDTOConverter.class}
)
public class PriceListDTOConverter
	implements DTOConverter<CommercePriceList, PriceList> {

	@Override
	public String getContentType() {
		return PriceList.class.getSimpleName();
	}

	@Override
	public PriceList toDTO(DTOConverterContext dtoConverterContext)
		throws Exception {

		CommercePriceList commercePriceList =
			_commercePriceListService.getCommercePriceList(
				(Long)dtoConverterContext.getId());

		CommerceCurrency commerceCurrency =
			commercePriceList.getCommerceCurrency();

		ExpandoBridge expandoBridge = commercePriceList.getExpandoBridge();

		return new PriceList() {
			{
				active = !commercePriceList.isInactive();
				catalogId = _getCatalogId(commercePriceList);
				currencyCode = commerceCurrency.getCode();
				customFields = expandoBridge.getAttributes();
				displayDate = commercePriceList.getDisplayDate();
				expirationDate = commercePriceList.getExpirationDate();
				externalReferenceCode =
					commercePriceList.getExternalReferenceCode();
				id = commercePriceList.getCommercePriceListId();
				name = commercePriceList.getName();
				priority = commercePriceList.getPriority();
			}
		};
	}

	private long _getCatalogId(CommercePriceList commercePriceList)
		throws Exception {

		CommerceCatalog commerceCatalog =
			_commerceCatalogService.fetchCommerceCatalogByGroupId(
				commercePriceList.getGroupId());

		if (commerceCatalog == null) {
			return 0L;
		}

		return commerceCatalog.getCommerceCatalogId();
	}

	@Reference
	private CommerceCatalogService _commerceCatalogService;

	@Reference
	private CommercePriceListService _commercePriceListService;

}