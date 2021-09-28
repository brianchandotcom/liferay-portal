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

import com.liferay.commerce.account.model.CommerceAccountGroup;
import com.liferay.commerce.discount.model.CommerceDiscount;
import com.liferay.commerce.discount.model.CommerceDiscountCommerceAccountGroupRel;
import com.liferay.commerce.discount.service.CommerceDiscountCommerceAccountGroupRelService;
import com.liferay.headless.commerce.admin.pricing.dto.v1_0.DiscountAccountGroup;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false,
	property = "dto.class.name=com.liferay.commerce.discount.model.CommerceDiscountCommerceAccountGroupRel",
	service = {DiscountAccountGroupDTOConverter.class, DTOConverter.class}
)
public class DiscountAccountGroupDTOConverter
	implements DTOConverter
		<CommerceDiscountCommerceAccountGroupRel, DiscountAccountGroup> {

	@Override
	public String getContentType() {
		return DiscountAccountGroup.class.getSimpleName();
	}

	@Override
	public DiscountAccountGroup toDTO(DTOConverterContext dtoConverterContext)
		throws Exception {

		CommerceDiscountCommerceAccountGroupRel
			commerceDiscountCommerceAccountGroupRel =
				_commerceDiscountCommerceAccountGroupRelService.
					getCommerceDiscountCommerceAccountGroupRel(
						(Long)dtoConverterContext.getId());

		CommerceAccountGroup commerceAccountGroup =
			commerceDiscountCommerceAccountGroupRel.getCommerceAccountGroup();
		CommerceDiscount commerceDiscount =
			commerceDiscountCommerceAccountGroupRel.getCommerceDiscount();

		return new DiscountAccountGroup() {
			{
				accountGroupExternalReferenceCode =
					commerceAccountGroup.getExternalReferenceCode();
				accountGroupId =
					commerceAccountGroup.getCommerceAccountGroupId();
				discountExternalReferenceCode =
					commerceDiscount.getExternalReferenceCode();
				discountId = commerceDiscount.getCommerceDiscountId();
				id =
					commerceDiscountCommerceAccountGroupRel.
						getCommerceDiscountCommerceAccountGroupRelId();
			}
		};
	}

	@Reference
	private CommerceDiscountCommerceAccountGroupRelService
		_commerceDiscountCommerceAccountGroupRelService;

}