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

package com.liferay.object.web.internal.frontend.taglib.clay.data.set.view.table;

import com.liferay.frontend.taglib.clay.data.set.view.table.BaseTableClayDataSetDisplayView;
import com.liferay.frontend.taglib.clay.data.set.view.table.ClayTableSchema;
import com.liferay.frontend.taglib.clay.data.set.view.table.ClayTableSchemaBuilder;
import com.liferay.frontend.taglib.clay.data.set.view.table.ClayTableSchemaBuilderFactory;
import com.liferay.frontend.taglib.clay.data.set.view.table.ClayTableSchemaField;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;

import java.util.List;
import java.util.Objects;

/**
 * @author Marco Leo
 * @author Brian Wing Shun Chan
 */
public class ObjectDefinitionTableClayDataSetDisplayView
	extends BaseTableClayDataSetDisplayView {

	public ObjectDefinitionTableClayDataSetDisplayView(
		ClayTableSchemaBuilderFactory clayTableSchemaBuilderFactory,
		ObjectDefinition objectDefinition, List<ObjectField> objectFields) {

		ClayTableSchemaBuilder clayTableSchemaBuilder =
			clayTableSchemaBuilderFactory.create();

		clayTableSchemaBuilder.addClayTableSchemaField("id", "id");

		for (ObjectField objectField : objectFields) {
			ClayTableSchemaField clayTableSchemaField =
				new ClayTableSchemaField();

			if (Objects.equals(objectField.getType(), "Boolean")) {
				clayTableSchemaField.setContentRenderer("boolean");
			}

			clayTableSchemaField.setFieldName(objectField.getName());
			clayTableSchemaField.setLabel(objectField.getName());

			if (!Objects.equals(objectField.getType(), "Blob") &&
				objectField.isIndexed()) {

				clayTableSchemaField.setSortable(true);
			}

			clayTableSchemaBuilder.addClayTableSchemaField(
				clayTableSchemaField);
		}

		_clayTableSchema = clayTableSchemaBuilder.build();
	}

	@Override
	public ClayTableSchema getClayTableSchema() {
		return _clayTableSchema;
	}

	private final ClayTableSchema _clayTableSchema;

}