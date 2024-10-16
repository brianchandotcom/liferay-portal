/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.order.content.web.internal.frontend.data.set.view.table;

import com.liferay.commerce.order.content.web.internal.constants.CommerceOrderFragmentFDSNames;
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
 * @author Gianmarco Brunialti Masera
 */
@Component(
	property = "frontend.data.set.name=" + CommerceOrderFragmentFDSNames.PENDING_ORDERS,
	service = FDSView.class
)
public class PendingCommerceOrderFragmentTableFDSView extends BaseTableFDSView {

	@Override
	public FDSTableSchema getFDSTableSchema(Locale locale) {
		FDSTableSchemaBuilder fdsTableSchemaBuilder =
			_fdsTableSchemaBuilderFactory.create();

		return fdsTableSchemaBuilder.add(
			"id", "order-id",
			fdsTableSchemaField -> {
				fdsTableSchemaField.setContentRenderer("actionLink");
				fdsTableSchemaField.setSortable(true);
			}
		).add(
			"name", "name",
			fdsTableSchemaField -> fdsTableSchemaField.setSortable(true)
		).add(
			"orderType", "order-type",
			fdsTableSchemaField -> fdsTableSchemaField.setSortable(true)
		).add(
			"externalReferenceCode", "erc",
			fdsTableSchemaField -> fdsTableSchemaField.setSortable(true)
		).add(
			"purchaseOrderNumber", "purchase-order-number",
			fdsTableSchemaField -> fdsTableSchemaField.setSortable(true)
		).add(
			"createDate", "date",
			fdsTableSchemaField -> {
				fdsTableSchemaField.setContentRenderer("date");
				fdsTableSchemaField.setSortable(true);
			}
		).add(
			_addAccountNameStringFDSTableSchemaField()
		).add(
			"author", "created-by",
			fdsTableSchemaField -> fdsTableSchemaField.setSortable(true)
		).add(
			"workflowStatusInfo.label_i18n", "status",
			fdsTableSchemaField -> fdsTableSchemaField.setContentRenderer(
				"label")
		).add(
			"summary.totalFormatted", "amount"
		).build();
	}

	private StringFDSTableSchemaField
		_addAccountNameStringFDSTableSchemaField() {

		StringFDSTableSchemaField stringFDSTableSchemaField =
			new StringFDSTableSchemaField();

		stringFDSTableSchemaField.setFieldName(
			"account"
		).setLabel(
			"account"
		).setSortable(
			true
		);

		stringFDSTableSchemaField.setTruncate(true);

		return stringFDSTableSchemaField;
	}

	@Reference
	private FDSTableSchemaBuilderFactory _fdsTableSchemaBuilderFactory;

}