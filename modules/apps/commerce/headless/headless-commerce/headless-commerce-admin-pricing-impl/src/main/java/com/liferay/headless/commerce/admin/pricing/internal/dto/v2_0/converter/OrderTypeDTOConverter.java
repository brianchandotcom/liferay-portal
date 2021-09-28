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

import com.liferay.commerce.model.CommerceOrderType;
import com.liferay.commerce.service.CommerceOrderTypeService;
import com.liferay.headless.commerce.admin.pricing.dto.v2_0.OrderType;
import com.liferay.headless.commerce.core.util.LanguageUtils;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false,
	property = "dto.class.name=com.liferay.commerce.model.CommerceOrderType",
	service = {DTOConverter.class, OrderTypeDTOConverter.class}
)
public class OrderTypeDTOConverter
	implements DTOConverter<CommerceOrderType, OrderType> {

	@Override
	public String getContentType() {
		return OrderType.class.getSimpleName();
	}

	@Override
	public OrderType toDTO(DTOConverterContext dtoConverterContext)
		throws Exception {

		CommerceOrderType commerceOrderType =
			_commerceOrderTypeService.getCommerceOrderType(
				(Long)dtoConverterContext.getId());

		return new OrderType() {
			{
				id = commerceOrderType.getCommerceOrderTypeId();
				name = LanguageUtils.getLanguageIdMap(
					commerceOrderType.getNameMap());
			}
		};
	}

	@Reference
	private CommerceOrderTypeService _commerceOrderTypeService;

}