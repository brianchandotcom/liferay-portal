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

package com.liferay.object.web.internal.object.entries.frontend.data.set.view.table;

import com.liferay.frontend.data.set.view.table.BaseTableFDSView;
import com.liferay.frontend.data.set.view.table.ClobFDSTableSchemaField;
import com.liferay.frontend.data.set.view.table.DateFDSTableSchemaField;
import com.liferay.frontend.data.set.view.table.FDSTableSchema;
import com.liferay.frontend.data.set.view.table.FDSTableSchemaBuilder;
import com.liferay.frontend.data.set.view.table.FDSTableSchemaBuilderFactory;
import com.liferay.frontend.data.set.view.table.FDSTableSchemaField;
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
public class ObjectEntriesTableFDSView extends BaseTableFDSView {

	public ObjectEntriesTableFDSView(
		FDSTableSchemaBuilderFactory fdsTableSchemaBuilderFactory,
		ObjectDefinition objectDefinition,
		ObjectFieldLocalService objectFieldLocalService) {

		_fdsTableSchemaBuilderFactory = fdsTableSchemaBuilderFactory;
		_objectDefinition = objectDefinition;
		_objectFieldLocalService = objectFieldLocalService;
	}

	@Override
	public FDSTableSchema getFDSTableSchema(Locale locale) {
		FDSTableSchemaBuilder fdsTableSchemaBuilder =
			_fdsTableSchemaBuilderFactory.create();

		FDSTableSchemaField idFDSTableSchemaField =
			fdsTableSchemaBuilder.addFDSTableSchemaField("id", "id");

		idFDSTableSchemaField.setContentRenderer("actionLink");

		List<ObjectField> objectFields =
			_objectFieldLocalService.getObjectFields(
				_objectDefinition.getObjectDefinitionId());

		for (ObjectField objectField : objectFields) {
			if (Validator.isNotNull(objectField.getRelationshipType())) {
				continue;
			}

			String fieldName = objectField.getName();

			if (objectField.getListTypeDefinitionId() > 0) {
				fieldName = fieldName + ".name";
			}

			FDSTableSchemaField fdsTableSchemaField = null;

			if (Objects.equals(objectField.getDBType(), "Clob")) {
				ClobFDSTableSchemaField clobFDSTableSchemaField =
					fdsTableSchemaBuilder.addFDSTableSchemaField(
						ClobFDSTableSchemaField.class, fieldName,
						objectField.getLabel(locale, true));

				clobFDSTableSchemaField.setTruncate(true);

				fdsTableSchemaField = clobFDSTableSchemaField;
			}
			else if (Objects.equals(objectField.getDBType(), "Date")) {
				DateFDSTableSchemaField dateFDSTableSchemaField =
					fdsTableSchemaBuilder.addFDSTableSchemaField(
						DateFDSTableSchemaField.class, fieldName,
						objectField.getLabel(locale, true));

				dateFDSTableSchemaField.setFormat("short");

				fdsTableSchemaField = dateFDSTableSchemaField;
			}
			else {
				fdsTableSchemaField =
					fdsTableSchemaBuilder.addFDSTableSchemaField(
						fieldName, objectField.getLabel(locale, true));

				if (Objects.equals(objectField.getDBType(), "Boolean")) {
					fdsTableSchemaField.setContentRenderer("boolean");
				}
			}

			fdsTableSchemaBuilder.addFDSTableSchemaField(fdsTableSchemaField);

			if (!Objects.equals(objectField.getDBType(), "Blob") &&
				objectField.isIndexed()) {

				fdsTableSchemaField.setSortable(true);
			}
		}

		FDSTableSchemaField statusFDSTableSchemaField =
			fdsTableSchemaBuilder.addFDSTableSchemaField("status", "status");

		statusFDSTableSchemaField.setContentRenderer("status");

		fdsTableSchemaBuilder.addFDSTableSchemaField("creator.name", "author");

		return fdsTableSchemaBuilder.build();
	}

	private final FDSTableSchemaBuilderFactory _fdsTableSchemaBuilderFactory;
	private final ObjectDefinition _objectDefinition;
	private final ObjectFieldLocalService _objectFieldLocalService;

}