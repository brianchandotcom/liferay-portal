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

package com.liferay.headless.commerce.admin.pricing.internal.resource.v2_0;

import com.liferay.commerce.price.list.model.CommercePriceListOrderTypeRel;
import com.liferay.commerce.price.list.service.CommercePriceListOrderTypeRelService;
import com.liferay.headless.commerce.admin.pricing.dto.v2_0.OrderType;
import com.liferay.headless.commerce.admin.pricing.dto.v2_0.PriceListOrderType;
import com.liferay.headless.commerce.admin.pricing.internal.dto.v2_0.converter.OrderTypeDTOConverter;
import com.liferay.headless.commerce.admin.pricing.resource.v2_0.OrderTypeResource;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.fields.NestedField;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Zoltán Takács
 */
@Component(
	enabled = false,
	properties = "OSGI-INF/liferay/rest/v2_0/order-type.properties",
	scope = ServiceScope.PROTOTYPE, service = OrderTypeResource.class
)
public class OrderTypeResourceImpl extends BaseOrderTypeResourceImpl {

	@NestedField(parentClass = PriceListOrderType.class, value = "orderType")
	@Override
	public OrderType getPriceListOrderTypeOrderType(Long id) throws Exception {
		CommercePriceListOrderTypeRel commercePriceListOrderTypeRel =
			_commercePriceListOrderTypeRelService.
				getCommercePriceListOrderTypeRel(id);

		return _orderTypeDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				commercePriceListOrderTypeRel.getCommerceOrderTypeId(),
				contextAcceptLanguage.getPreferredLocale()));
	}

	@Reference
	private CommercePriceListOrderTypeRelService
		_commercePriceListOrderTypeRelService;

	@Reference
	private OrderTypeDTOConverter _orderTypeDTOConverter;

}