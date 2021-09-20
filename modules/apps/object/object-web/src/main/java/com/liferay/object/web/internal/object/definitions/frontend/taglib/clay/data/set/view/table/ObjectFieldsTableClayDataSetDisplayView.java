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

package com.liferay.object.web.internal.object.definitions.frontend.taglib.clay.data.set.view.table;

import com.liferay.frontend.taglib.clay.data.set.ClayDataSetDisplayView;
import com.liferay.frontend.taglib.clay.data.set.view.table.BaseTableClayDataSetDisplayView;
import com.liferay.frontend.taglib.clay.data.set.view.table.ClayTableSchema;
import com.liferay.frontend.taglib.clay.data.set.view.table.ClayTableSchemaBuilder;
import com.liferay.frontend.taglib.clay.data.set.view.table.ClayTableSchemaBuilderFactory;
import com.liferay.frontend.taglib.clay.data.set.view.table.ClayTableSchemaField;
import com.liferay.object.web.internal.object.definitions.constants.ObjectDefinitionsClayDataSetDisplayNames;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 * @author Gabriel Albuquerque
 */
@Component(
	property = "clay.data.set.display.name=" + ObjectDefinitionsClayDataSetDisplayNames.OBJECT_FIELDS,
	service = ClayDataSetDisplayView.class
)
public class ObjectFieldsTableClayDataSetDisplayView
	extends BaseTableClayDataSetDisplayView {

	@Override
	public ClayTableSchema getClayTableSchema() {
		ClayTableSchemaBuilder clayTableSchemaBuilder =
			_clayTableSchemaBuilderFactory.create();

		ClayTableSchemaField labelClayTableSchemaField =
			clayTableSchemaBuilder.addClayTableSchemaField(
				"label.LANG", "label");

		labelClayTableSchemaField.setContentRenderer("actionLink");

		clayTableSchemaBuilder.addClayTableSchemaField("type", "type");

		ClayTableSchemaField requiredClayTableSchemaField =
			clayTableSchemaBuilder.addClayTableSchemaField(
				"required", "mandatory");

		requiredClayTableSchemaField.setContentRenderer("boolean");

		return clayTableSchemaBuilder.build();
	}

	@Reference
	private ClayTableSchemaBuilderFactory _clayTableSchemaBuilderFactory;

}