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

package com.liferay.object.web.internal.object.entries.frontend.taglib.clay.data.set.view.table;

import com.liferay.frontend.taglib.clay.data.set.view.table.BaseTableClayDataSetDisplayView;
import com.liferay.frontend.taglib.clay.data.set.view.table.ClayTableSchema;
import com.liferay.frontend.taglib.clay.data.set.view.table.ClayTableSchemaBuilder;
import com.liferay.frontend.taglib.clay.data.set.view.table.ClayTableSchemaBuilderFactory;
import com.liferay.frontend.taglib.clay.data.set.view.table.ClayTableSchemaField;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.portal.kernel.util.Validator;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

/**
 * @author Marco Leo
 * @author Brian Wing Shun Chan
 */
public class ObjectEntriesTableClayDataSetDisplayView
	extends BaseTableClayDataSetDisplayView {

	public ObjectEntriesTableClayDataSetDisplayView(
		ClayTableSchemaBuilderFactory clayTableSchemaBuilderFactory,
		ObjectDefinition objectDefinition,
		ObjectFieldLocalService objectFieldLocalService) {

		_clayTableSchemaBuilderFactory = clayTableSchemaBuilderFactory;
		_objectDefinition = objectDefinition;
		_objectFieldLocalService = objectFieldLocalService;
	}

	@Override
	public ClayTableSchema getClayTableSchema(Locale locale) {
		ClayTableSchemaBuilder clayTableSchemaBuilder =
			_clayTableSchemaBuilderFactory.create();

		ClayTableSchemaField idClayTableSchemaField =
			clayTableSchemaBuilder.addClayTableSchemaField("id", "id");

		idClayTableSchemaField.setContentRenderer("actionLink");

		List<ObjectField> objectFields =
			_objectFieldLocalService.getObjectFields(
				_objectDefinition.getObjectDefinitionId());

		for (ObjectField objectField : objectFields) {
			if (Validator.isNotNull(objectField.getRelationshipType())) {
				continue;
			}

			ClayTableSchemaField clayTableSchemaField =
				clayTableSchemaBuilder.addClayTableSchemaField(
					objectField.getName(), objectField.getLabel(locale, true));

			if (Objects.equals(objectField.getType(), "Boolean")) {
				clayTableSchemaField.setContentRenderer("boolean");
			}

			if (!Objects.equals(objectField.getType(), "Blob") &&
				objectField.isIndexed()) {

				clayTableSchemaField.setSortable(true);
			}
		}

		ClayTableSchemaField statusClayTableSchemaField =
			clayTableSchemaBuilder.addClayTableSchemaField("status", "status");

		statusClayTableSchemaField.setContentRenderer("status");

		clayTableSchemaBuilder.addClayTableSchemaField(
			"creator.name", "author");

		return clayTableSchemaBuilder.build();
	}

	private final ClayTableSchemaBuilderFactory _clayTableSchemaBuilderFactory;
	private final ObjectDefinition _objectDefinition;
	private final ObjectFieldLocalService _objectFieldLocalService;

}