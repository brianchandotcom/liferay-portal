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

package com.liferay.headless.commerce.admin.inventory.internal.resource.v1_0;

import com.liferay.commerce.inventory.model.CommerceInventoryWarehouseRel;
import com.liferay.commerce.inventory.service.CommerceInventoryWarehouseRelService;
import com.liferay.headless.commerce.admin.inventory.dto.v1_0.OrderType;
import com.liferay.headless.commerce.admin.inventory.dto.v1_0.WarehouseOrderType;
import com.liferay.headless.commerce.admin.inventory.internal.dto.v1_0.OrderTypeDTOConverter;
import com.liferay.headless.commerce.admin.inventory.resource.v1_0.OrderTypeResource;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.fields.NestedField;
import com.liferay.portal.vulcan.fields.NestedFieldSupport;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false,
	properties = "OSGI-INF/liferay/rest/v1_0/order-type.properties",
	scope = ServiceScope.PROTOTYPE,
	service = {NestedFieldSupport.class, OrderTypeResource.class}
)
public class OrderTypeResourceImpl
	extends BaseOrderTypeResourceImpl implements NestedFieldSupport {

	@NestedField(parentClass = WarehouseOrderType.class, value = "orderType")
	@Override
	public OrderType getWarehouseOrderTypeOrderType(Long id) throws Exception {
		CommerceInventoryWarehouseRel commerceInventoryWarehouseRel =
			_commerceInventoryWarehouseRelService.
				getCommerceInventoryWarehouseRel(id);

		return _orderTypeDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				commerceInventoryWarehouseRel.getClassPK(),
				contextAcceptLanguage.getPreferredLocale()));
	}

	@Reference
	private CommerceInventoryWarehouseRelService
		_commerceInventoryWarehouseRelService;

	@Reference
	private OrderTypeDTOConverter _orderTypeDTOConverter;

}