/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.payment.web.internal.frontend.data.set.view.table;

import com.liferay.commerce.payment.web.internal.constants.CommercePaymentsFDSNames;
import com.liferay.frontend.data.set.view.FDSView;
import com.liferay.frontend.data.set.view.table.BaseTableFDSView;
import com.liferay.frontend.data.set.view.table.FDSTableSchema;
import com.liferay.frontend.data.set.view.table.FDSTableSchemaBuilder;
import com.liferay.frontend.data.set.view.table.FDSTableSchemaBuilderFactory;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Crescenzo Rega
 */
@Component(
	property = "frontend.data.set.name=" + CommercePaymentsFDSNames.REFUNDS,
	service = FDSView.class
)
public class CommerceRefundTableFDSView extends BaseTableFDSView {

	@Override
	public FDSTableSchema getFDSTableSchema(Locale locale) {
		FDSTableSchemaBuilder fdsTableSchemaBuilder =
			_fdsTableSchemaBuilderFactory.create();

		return fdsTableSchemaBuilder.add(
			"id", "refund-id"
		).add(
			"amountFormatted", "refund-amount",
			fdsTableSchemaField -> fdsTableSchemaField.setSortable(true)
		).add(
			"createDate", "refund-date",
			fdsTableSchemaField -> fdsTableSchemaField.setContentRenderer(
				"dateTime"
			).setSortable(
				true
			)
		).add(
			"reasonName.LANG", "refund-reason"
		).add(
			"paymentStatusStatus", "refund-status",
			fdsTableSchemaField -> fdsTableSchemaField.setContentRenderer(
				"CommercePaymentStatusDataRenderer"
			).setSortable(
				true
			)
		).add(
			"author", "refunded-by"
		).build();
	}

	@Reference
	private FDSTableSchemaBuilderFactory _fdsTableSchemaBuilderFactory;

}