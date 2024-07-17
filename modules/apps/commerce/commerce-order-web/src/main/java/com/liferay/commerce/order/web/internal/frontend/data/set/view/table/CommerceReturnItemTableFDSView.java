/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.order.web.internal.frontend.data.set.view.table;

import com.liferay.commerce.order.web.internal.constants.CommerceReturnFDSNames;
import com.liferay.frontend.data.set.view.FDSView;
import com.liferay.frontend.data.set.view.table.BaseTableFDSView;
import com.liferay.frontend.data.set.view.table.FDSTableSchema;
import com.liferay.frontend.data.set.view.table.FDSTableSchemaBuilder;
import com.liferay.frontend.data.set.view.table.FDSTableSchemaBuilderFactory;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Stefano Motta
 */
@Component(
	property = "frontend.data.set.name=" + CommerceReturnFDSNames.RETURN_ITEMS,
	service = FDSView.class
)
public class CommerceReturnItemTableFDSView extends BaseTableFDSView {

	@Override
	public FDSTableSchema getFDSTableSchema(Locale locale) {
		FDSTableSchemaBuilder fdsTableSchemaBuilder =
			_fdsTableSchemaBuilderFactory.create();

		return fdsTableSchemaBuilder.add(
			"r_commerceOrderItemToCommerceReturnItems_commerceOrderItem.sku",
			"sku",
			fdsTableSchemaField -> fdsTableSchemaField.setContentRenderer(
				"actionLink")
		).add(
			"r_commerceOrderItemToCommerceReturnItems_commerceOrderItem.name." +
				"LANG",
			"name"
		).add(
			"r_commerceOrderItemToCommerceReturnItems_commerceOrderItem." +
				"unitOfMeasureKey",
			"uom"
		).add(
			"quantity", "requested-quantity"
		).add(
			"returnReason", "return-reason",
			fdsTableSchemaField -> fdsTableSchemaField.setContentRenderer(
				"commerceReturnItemPicklistDataRenderer")
		).add(
			"authorized", "authorized"
		).add(
			"received", "received",
			fdsTableSchemaField -> fdsTableSchemaField.setContentRenderer(
				"commerceReturnItemReceivedDataRenderer")
		).add(
			"returnResolutionMethod", "resolution",
			fdsTableSchemaField -> fdsTableSchemaField.setContentRenderer(
				"commerceReturnItemPicklistDataRenderer")
		).add(
			"returnItemStatus", "status",
			fdsTableSchemaField -> fdsTableSchemaField.setContentRenderer(
				"commerceReturnItemStatusDataRenderer")
		).build();
	}

	@Reference
	private FDSTableSchemaBuilderFactory _fdsTableSchemaBuilderFactory;

}