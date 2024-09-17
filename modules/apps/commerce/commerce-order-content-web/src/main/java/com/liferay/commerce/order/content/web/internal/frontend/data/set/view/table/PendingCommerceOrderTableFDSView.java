/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.order.content.web.internal.frontend.data.set.view.table;

import com.liferay.commerce.order.content.web.internal.constants.CommerceOrderFDSNames;
import com.liferay.frontend.data.set.view.FDSView;
import com.liferay.frontend.data.set.view.table.BaseTableFDSView;
import com.liferay.frontend.data.set.view.table.FDSTableSchema;
import com.liferay.frontend.data.set.view.table.FDSTableSchemaBuilder;
import com.liferay.frontend.data.set.view.table.FDSTableSchemaBuilderFactory;
import com.liferay.frontend.data.set.view.table.StringFDSTableSchemaField;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	property = "frontend.data.set.name=" + CommerceOrderFDSNames.PENDING_ORDERS,
	service = FDSView.class
)
public class PendingCommerceOrderTableFDSView extends BaseTableFDSView {

	@Override
	public FDSTableSchema getFDSTableSchema(Locale locale) {
		FDSTableSchemaBuilder fdsTableSchemaBuilder =
			_fdsTableSchemaBuilderFactory.create();

		return fdsTableSchemaBuilder.add(
			"title", "order-id",
			fdsTableSchemaField -> fdsTableSchemaField.setContentRenderer(
				"actionLink")
		).add(
			"name", "name"
		).add(
			"orderType", "order-type"
		).add(
			"externalReferenceCode", "erc"
		).add(
			"purchaseOrderNumber", "purchase-order-number"
		).add(
			"date", "create-date",
			fdsTableSchemaField -> fdsTableSchemaField.setSortable(true)
		).add(
			_addAccountNameStringFDSTableSchemaField()
		).add(
			"author", "created-by"
		).add(
			"status", "status"
		).add(
			"amount", "amount"
		).build();
	}

	private StringFDSTableSchemaField
		_addAccountNameStringFDSTableSchemaField() {

		StringFDSTableSchemaField stringFDSTableSchemaField =
			new StringFDSTableSchemaField();

		stringFDSTableSchemaField.setFieldName(
			"accountName"
		).setLabel(
			"account"
		);

		stringFDSTableSchemaField.setTruncate(true);

		return stringFDSTableSchemaField;
	}

	@Reference
	private FDSTableSchemaBuilderFactory _fdsTableSchemaBuilderFactory;

}