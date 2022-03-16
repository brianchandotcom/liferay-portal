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

package com.liferay.commerce.shipment.web.internal.frontend;

import com.liferay.commerce.constants.CommerceShipmentDataSetConstants;
import com.liferay.frontend.taglib.clay.data.set.ClayDataSetDisplayView;
import com.liferay.frontend.taglib.clay.data.set.view.table.BaseTableClayDataSetDisplayView;
import com.liferay.frontend.taglib.clay.data.set.view.table.ClayTableSchema;
import com.liferay.frontend.taglib.clay.data.set.view.table.ClayTableSchemaBuilder;
import com.liferay.frontend.taglib.clay.data.set.view.table.ClayTableSchemaBuilderFactory;
import com.liferay.frontend.taglib.clay.data.set.view.table.ClayTableSchemaField;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alec Sloan
 */
@Component(
	enabled = false, immediate = true,
	property = "clay.data.set.display.name=" + CommerceShipmentDataSetConstants.COMMERCE_DATA_SET_KEY_INVENTORY_WAREHOUSE_ITEM,
	service = ClayDataSetDisplayView.class
)
public class CommerceInventoryWarehouseItemClayTableDataSetDisplayView
	extends BaseTableClayDataSetDisplayView {

	@Override
	public ClayTableSchema getClayTableSchema() {
		ClayTableSchemaBuilder clayTableSchemaBuilder =
			_clayTableSchemaBuilderFactory.create();

		clayTableSchemaBuilder.addClayTableSchemaField("name", "warehouse");

		clayTableSchemaBuilder.addClayTableSchemaField(
			"available", "available");

		ClayTableSchemaField warehouseItemClayTableSchemaField =
			clayTableSchemaBuilder.addClayTableSchemaField(
				"warehouseItem", "quantity-in-shipment");

		warehouseItemClayTableSchemaField.setContentRenderer(
			"quantitySelector");

		return clayTableSchemaBuilder.build();
	}

	@Reference
	private ClayTableSchemaBuilderFactory _clayTableSchemaBuilderFactory;

}