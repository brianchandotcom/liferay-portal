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
import com.liferay.petra.string.StringPool;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	property = "frontend.data.set.name=" + CommercePricingFDSNames.PROMOTION_ENTRIES,
	service = FDSView.class
)
public class CommercePromotionEntriesTableFDSView extends BaseTableFDSView {

	@Override
	public FDSTableSchema getFDSTableSchema(Locale locale) {
		FDSTableSchemaBuilder fdsTableSchemaBuilder =
			_fdsTableSchemaBuilderFactory.create();

		return fdsTableSchemaBuilder.add(
			"product.thumbnail", StringPool.BLANK,
			fdsTableSchemaField -> fdsTableSchemaField.setContentRenderer(
				"image")
		).add(
			"sku.name", "sku",
			fdsTableSchemaField -> fdsTableSchemaField.setContentRenderer(
				"actionLink")
		).add(
			"product.name.LANG", "name"
		).add(
			"sku.basePromoPriceFormatted", "base-promotion-price"
		).add(
			"priceFormatted", "promotion-price"
		).add(
			"discountLevelsFormatted", "unit-discount"
		).add(
			"hasTierPrice", "tiered-price",
			fdsTableSchemaField -> fdsTableSchemaField.setContentRenderer(
				"boolean")
		).add(
			"priceOnApplication", "price-on-application",
			fdsTableSchemaField -> fdsTableSchemaField.setContentRenderer(
				"boolean")
		).build();
	}

	@Reference
	private FDSTableSchemaBuilderFactory _fdsTableSchemaBuilderFactory;

}