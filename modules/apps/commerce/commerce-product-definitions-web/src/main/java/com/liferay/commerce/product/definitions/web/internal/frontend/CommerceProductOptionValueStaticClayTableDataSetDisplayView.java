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

package com.liferay.commerce.product.definitions.web.internal.frontend;

import com.liferay.frontend.taglib.clay.data.set.ClayDataSetDisplayView;
import com.liferay.frontend.taglib.clay.data.set.view.table.ClayTableSchemaBuilder;

import org.osgi.service.component.annotations.Component;

/**
 * @author Igor Beslic
 */
@Component(
	immediate = true,
	property = "clay.data.set.display.name=" + CommerceProductDataSetConstants.COMMERCE_DATA_SET_KEY_PRODUCT_OPTION_VALUES_STATIC,
	service = ClayDataSetDisplayView.class
)
public class CommerceProductOptionValueStaticClayTableDataSetDisplayView
	extends BaseCommerceProductOptionValueClayTableDataSetDisplayView {

	protected void addFields(ClayTableSchemaBuilder clayTableSchemaBuilder) {
		clayTableSchemaBuilder.addClayTableSchemaField("key", "key");
		clayTableSchemaBuilder.addClayTableSchemaField("position", "position");
		clayTableSchemaBuilder.addClayTableSchemaField(
			"deltaPrice", "delta-price");
		clayTableSchemaBuilder.addClayTableSchemaField("sku", "linked-product");
	}

}