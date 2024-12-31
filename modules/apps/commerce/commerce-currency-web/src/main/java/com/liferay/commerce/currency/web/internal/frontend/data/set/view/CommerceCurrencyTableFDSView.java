/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.currency.web.internal.frontend.data.set.view;

import com.liferay.commerce.currency.web.internal.constants.CommerceCurrencyFDSNames;
import com.liferay.frontend.data.set.view.FDSView;
import com.liferay.frontend.data.set.view.table.BaseTableFDSView;
import com.liferay.frontend.data.set.view.table.FDSTableSchema;
import com.liferay.frontend.data.set.view.table.FDSTableSchemaBuilder;
import com.liferay.frontend.data.set.view.table.FDSTableSchemaBuilderFactory;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Luca Pellizzon
 */
@Component(
	property = "frontend.data.set.name=" + CommerceCurrencyFDSNames.COMMERCE_CURRENCIES,
	service = FDSView.class
)
public class CommerceCurrencyTableFDSView extends BaseTableFDSView {

	@Override
	public FDSTableSchema getFDSTableSchema(Locale locale) {
		FDSTableSchemaBuilder fdsTableSchemaBuilder =
			_fdsTableSchemaBuilderFactory.create();

		return fdsTableSchemaBuilder.add(
			"name", "name",
			fdsTableSchemaField -> fdsTableSchemaField.setContentRenderer(
				"actionLink")
		).add(
			"code", "code"
		).add(
			"rate", "rate"
		).add(
			"primary", "primary",
			fdsTableSchemaField -> fdsTableSchemaField.setContentRenderer(
				"boolean")
		).add(
			"active", "active",
			fdsTableSchemaField -> fdsTableSchemaField.setContentRenderer(
				"boolean")
		).add(
			"priority", "priority",
			fdsTableSchemaField -> fdsTableSchemaField.setSortable(true)
		).build();
	}

	@Reference
	private FDSTableSchemaBuilderFactory _fdsTableSchemaBuilderFactory;

}