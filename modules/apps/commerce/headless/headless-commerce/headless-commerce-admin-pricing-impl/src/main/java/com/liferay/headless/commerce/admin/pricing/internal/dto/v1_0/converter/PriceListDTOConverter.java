/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
	property = "dto.class.name=com.liferay.commerce.price.list.model.CommercePriceList",
	service = DTOConverter.class
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

		return new PriceList() {
			{
				setActive(() -> !commercePriceList.isInactive());
				setCatalogId(() -> _getCatalogId(commercePriceList));
				setCurrencyCode(
					() -> {
						CommerceCurrency commerceCurrency =
							commercePriceList.getCommerceCurrency();

						return commerceCurrency.getCode();
					});
				setCustomFields(
					() -> {
						ExpandoBridge expandoBridge =
							commercePriceList.getExpandoBridge();

						return expandoBridge.getAttributes();
					});
				setDisplayDate(commercePriceList::getDisplayDate);
				setExpirationDate(commercePriceList::getExpirationDate);
				setExternalReferenceCode(
					commercePriceList::getExternalReferenceCode);
				setId(commercePriceList::getCommercePriceListId);
				setName(commercePriceList::getName);
				setPriority(commercePriceList::getPriority);
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