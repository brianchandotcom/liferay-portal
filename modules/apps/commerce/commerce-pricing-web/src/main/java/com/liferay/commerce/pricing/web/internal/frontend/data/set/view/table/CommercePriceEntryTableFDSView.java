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

package com.liferay.commerce.pricing.web.internal.frontend.data.set.view.table;

import com.liferay.commerce.pricing.web.internal.constants.CommercePricingFDSNames;
import com.liferay.frontend.data.set.view.FDSView;
import com.liferay.frontend.data.set.view.table.BaseTableFDSView;
import com.liferay.frontend.data.set.view.table.FDSTableSchema;
import com.liferay.frontend.data.set.view.table.FDSTableSchemaBuilder;
import com.liferay.frontend.data.set.view.table.FDSTableSchemaBuilderFactory;
import com.liferay.frontend.data.set.view.table.FDSTableSchemaField;
import com.liferay.petra.string.StringPool;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false, immediate = true,
	property = "frontend.data.set.name=" + CommercePricingFDSNames.PRICE_LIST_ENTRIES,
	service = FDSView.class
)
public class CommercePriceEntryTableFDSView extends BaseTableFDSView {

	@Override
	public FDSTableSchema getFDSTableSchema(Locale locale) {
		FDSTableSchemaBuilder fdsTableSchemaBuilder =
			_fdsTableSchemaBuilderFactory.create();

		FDSTableSchemaField imageField =
			fdsTableSchemaBuilder.addFDSTableSchemaField(
				"product.thumbnail", StringPool.BLANK);

		imageField.setContentRenderer("image");

		FDSTableSchemaField skuField =
			fdsTableSchemaBuilder.addFDSTableSchemaField("sku.name", "sku");

		skuField.setContentRenderer("actionLink");

		fdsTableSchemaBuilder.addFDSTableSchemaField(
			"product.name.LANG", "name");

		fdsTableSchemaBuilder.addFDSTableSchemaField(
			"sku.basePriceFormatted", "base-price");

		fdsTableSchemaBuilder.addFDSTableSchemaField(
			"priceFormatted", "price-list-price");

		fdsTableSchemaBuilder.addFDSTableSchemaField(
			"discountLevelsFormatted", "unit-discount");

		FDSTableSchemaField tieredPrice =
			fdsTableSchemaBuilder.addFDSTableSchemaField(
				"hasTierPrice", "tiered-price");

		tieredPrice.setContentRenderer("boolean");

		return fdsTableSchemaBuilder.build();
	}

	@Reference
	private FDSTableSchemaBuilderFactory _fdsTableSchemaBuilderFactory;

}