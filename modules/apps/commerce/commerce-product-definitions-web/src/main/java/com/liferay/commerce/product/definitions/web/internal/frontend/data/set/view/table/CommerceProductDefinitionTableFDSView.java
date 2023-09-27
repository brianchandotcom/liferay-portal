/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.definitions.web.internal.frontend.data.set.view.table;

import com.liferay.commerce.product.definitions.web.internal.constants.CommerceProductFDSNames;
import com.liferay.frontend.data.set.view.FDSView;
import com.liferay.frontend.data.set.view.table.BaseTableFDSView;
import com.liferay.frontend.data.set.view.table.FDSTableSchema;
import com.liferay.frontend.data.set.view.table.FDSTableSchemaBuilder;
import com.liferay.frontend.data.set.view.table.FDSTableSchemaBuilderFactory;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	property = "frontend.data.set.name=" + CommerceProductFDSNames.PRODUCT_DEFINITIONS,
	service = FDSView.class
)
public class CommerceProductDefinitionTableFDSView extends BaseTableFDSView {

	@Override
	public FDSTableSchema getFDSTableSchema(Locale locale) {
		FDSTableSchemaBuilder fdsTableSchemaBuilder =
			_fdsTableSchemaBuilderFactory.create();

		return fdsTableSchemaBuilder.add(
			"thumbnail", "image",
			fdsTableSchemaField -> fdsTableSchemaField.setContentRenderer(
				"image")
		).add(
			"name.LANG", "name",
			fdsTableSchemaField -> {
				fdsTableSchemaField.setContentRenderer("actionLink");
				fdsTableSchemaField.setSortable(true);
			}
		).add(
			"catalog.name", "catalog"
		).add(
			"productTypeI18n", "type"
		).add(
			"workflowStatusInfo", "status",
			fdsTableSchemaField -> fdsTableSchemaField.setContentRenderer(
				"status")
		).add(
			"modifiedDate", "modified-date",
			fdsTableSchemaField -> {
				fdsTableSchemaField.setContentRenderer("dateTime");
				fdsTableSchemaField.setSortable(true);
			}
		).build();
	}

	@Reference
	private FDSTableSchemaBuilderFactory _fdsTableSchemaBuilderFactory;

}