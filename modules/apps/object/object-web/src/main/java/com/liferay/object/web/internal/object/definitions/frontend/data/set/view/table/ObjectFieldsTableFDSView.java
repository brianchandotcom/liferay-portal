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

package com.liferay.object.web.internal.object.definitions.frontend.data.set.view.table;

import com.liferay.frontend.data.set.view.FDSView;
import com.liferay.frontend.data.set.view.table.BaseTableFDSView;
import com.liferay.frontend.data.set.view.table.FDSTableSchema;
import com.liferay.frontend.data.set.view.table.FDSTableSchemaBuilder;
import com.liferay.frontend.data.set.view.table.FDSTableSchemaBuilderFactory;
import com.liferay.frontend.data.set.view.table.FDSTableSchemaField;
import com.liferay.object.web.internal.object.definitions.constants.ObjectDefinitionsFDSNames;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 * @author Gabriel Albuquerque
 */
@Component(
	property = "frontend.data.set.name=" + ObjectDefinitionsFDSNames.OBJECT_FIELDS,
	service = FDSView.class
)
public class ObjectFieldsTableFDSView extends BaseTableFDSView {

	@Override
	public FDSTableSchema getFDSTableSchema(Locale locale) {
		FDSTableSchemaBuilder fdsTableSchemaBuilder =
			_fdsTableSchemaBuilderFactory.create();

		FDSTableSchemaField labelFDSTableSchemaField =
			fdsTableSchemaBuilder.addFDSTableSchemaField("label.LANG", "label");

		fdsTableSchemaBuilder.addFDSTableSchemaField("businessType", "type");

		labelFDSTableSchemaField.setContentRenderer("actionLink");

		FDSTableSchemaField requiredFDSTableSchemaField =
			fdsTableSchemaBuilder.addFDSTableSchemaField(
				"required", "mandatory");

		requiredFDSTableSchemaField.setContentRenderer("boolean");

		return fdsTableSchemaBuilder.build();
	}

	@Reference
	private FDSTableSchemaBuilderFactory _fdsTableSchemaBuilderFactory;

}