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
	property = "frontend.data.set.name=" + CommerceReturnFDSNames.RETURNS,
	service = FDSView.class
)
public class CommerceReturnTableFDSView extends BaseTableFDSView {

	@Override
	public FDSTableSchema getFDSTableSchema(Locale locale) {
		FDSTableSchemaBuilder fdsTableSchemaBuilder =
			_fdsTableSchemaBuilderFactory.create();

		return fdsTableSchemaBuilder.add(
			"id", "return-id",
			fdsTableSchemaField -> fdsTableSchemaField.setContentRenderer(
				"actionLink"
			).setSortable(
				true
			)
		).add(
			"r_accountToCommerceReturns_accountEntry.name", "account"
		).add(
			"channelName", "channel"
		).add(
			"totalAmount", "amount",
			fdsTableSchemaField -> fdsTableSchemaField.setContentRenderer(
				"commerceCurrencyDataRenderer")
		).add(
			"requestedItems", "number-of-items"
		).add(
			"dateCreated", "return-date",
			fdsTableSchemaField -> fdsTableSchemaField.setContentRenderer(
				"dateTime"
			).setSortable(
				true
			)
		).add(
			"r_commerceOrderToCommerceReturns_commerceOrderId", "order-id",
			fdsTableSchemaField -> fdsTableSchemaField.setSortable(true)
		).add(
			"status", "acceptance-workflow-status",
			fdsTableSchemaField -> fdsTableSchemaField.setContentRenderer(
				"commerceStatusDataRenderer")
		).add(
			"returnStatus", "return-status",
			fdsTableSchemaField -> fdsTableSchemaField.setContentRenderer(
				"commerceReturnStatusDataRenderer")
		).build();
	}

	@Reference
	private FDSTableSchemaBuilderFactory _fdsTableSchemaBuilderFactory;

}