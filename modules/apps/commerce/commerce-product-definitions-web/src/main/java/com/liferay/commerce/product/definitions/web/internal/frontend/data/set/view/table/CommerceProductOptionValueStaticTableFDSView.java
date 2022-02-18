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

package com.liferay.commerce.product.definitions.web.internal.frontend.data.set.view.table;

import com.liferay.commerce.product.definitions.web.internal.constants.CommerceProductFDSNames;
import com.liferay.frontend.data.set.view.FDSView;
import com.liferay.frontend.data.set.view.table.FDSTableSchemaBuilder;

import org.osgi.service.component.annotations.Component;

/**
 * @author Igor Beslic
 */
@Component(
	enabled = false, immediate = true,
	property = "frontend.data.set.name=" + CommerceProductFDSNames.PRODUCT_OPTION_VALUES_STATIC,
	service = FDSView.class
)
public class CommerceProductOptionValueStaticTableFDSView
	extends BaseCommerceProductOptionValueTableFDSView {

	@Override
	protected FDSTableSchemaBuilder addFields(
		FDSTableSchemaBuilder fdsTableSchemaBuilder) {

		return fdsTableSchemaBuilder.add(
			"key", "key"
		).add(
			"position", "position"
		).add(
			"deltaPrice", "delta-price"
		).add(
			"sku", "linked-product"
		);
	}

}