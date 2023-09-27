/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.admin.pricing.internal.dto.v2_0.converter;

import com.liferay.account.model.AccountGroup;
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
	property = "dto.class.name=com.liferay.commerce.price.list.model.CommercePriceListCommerceAccountGroupRel",
	service = DTOConverter.class
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

		AccountGroup priceListAccountGroup =
			commercePriceListCommerceAccountGroupRel.getAccountGroup();
		CommercePriceList commercePriceList =
			commercePriceListCommerceAccountGroupRel.getCommercePriceList();

		return new PriceListAccountGroup() {
			{
				accountGroupExternalReferenceCode =
					priceListAccountGroup.getExternalReferenceCode();
				accountGroupId = priceListAccountGroup.getAccountGroupId();
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