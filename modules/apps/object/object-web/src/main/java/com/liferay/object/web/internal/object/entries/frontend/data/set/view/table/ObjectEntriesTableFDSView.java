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
import com.liferay.frontend.data.set.view.table.DateFDSTableSchemaField;
import com.liferay.frontend.data.set.view.table.FDSTableSchema;
import com.liferay.frontend.data.set.view.table.FDSTableSchemaBuilder;
import com.liferay.frontend.data.set.view.table.FDSTableSchemaBuilderFactory;
import com.liferay.frontend.data.set.view.table.FDSTableSchemaField;
import com.liferay.frontend.data.set.view.table.StringFDSTableSchemaField;
import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.constants.ObjectRelationshipConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.model.ObjectView;
import com.liferay.object.model.ObjectViewColumn;
import com.liferay.object.model.ObjectViewColumnModel;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.object.service.ObjectViewLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * @author Marco Leo
 * @author Brian Wing Shun Chan
 */
public class ObjectEntriesTableFDSView extends BaseTableFDSView {

	public ObjectEntriesTableFDSView(
		FDSTableSchemaBuilderFactory fdsTableSchemaBuilderFactory,
		ObjectDefinition objectDefinition,
		ObjectDefinitionLocalService objectDefinitionLocalService,
		ObjectFieldLocalService objectFieldLocalService,
		ObjectRelationshipLocalService objectRelationshipLocalService,
		ObjectViewLocalService objectViewLocalService) {

		_fdsTableSchemaBuilderFactory = fdsTableSchemaBuilderFactory;
		_objectDefinition = objectDefinition;
		_objectDefinitionLocalService = objectDefinitionLocalService;
		_objectFieldLocalService = objectFieldLocalService;
		_objectRelationshipLocalService = objectRelationshipLocalService;
		_objectViewLocalService = objectViewLocalService;
	}

	@Override
	public FDSTableSchema getFDSTableSchema(Locale locale) {
		FDSTableSchemaBuilder fdsTableSchemaBuilder =
			_fdsTableSchemaBuilderFactory.create();

		ObjectView defaultObjectView =
			_objectViewLocalService.fetchDefaultObjectView(
				_objectDefinition.getObjectDefinitionId());

		if (defaultObjectView == null) {
			_addAllObjectFields(fdsTableSchemaBuilder, locale);

			return fdsTableSchemaBuilder.build();
		}

		List<ObjectViewColumn> objectViewColumns =
			defaultObjectView.getObjectViewColumns();

		Stream<ObjectViewColumn> stream = objectViewColumns.stream();

		stream.sorted(
			Comparator.comparingInt(ObjectViewColumnModel::getPriority)
		).forEach(
			objectViewColumn -> {
				ObjectField objectField =
					_objectFieldLocalService.fetchObjectField(
						_objectDefinition.getObjectDefinitionId(),
						objectViewColumn.getObjectFieldName());

				if (objectField == null) {
					_addNonobjectField(
						fdsTableSchemaBuilder,
						objectViewColumn.getObjectFieldName());
				}
				else {
					_addObjectField(fdsTableSchemaBuilder, locale, objectField);
				}
			}
		);

		return fdsTableSchemaBuilder.build();
	}

	private void _addAllObjectFields(
		FDSTableSchemaBuilder fdsTableSchemaBuilder, Locale locale) {

		_addNonobjectField(fdsTableSchemaBuilder, "id");

		List<ObjectField> objectFields =
			_objectFieldLocalService.getObjectFields(
				_objectDefinition.getObjectDefinitionId());

		objectFields.forEach(
			objectField -> _addObjectField(
				fdsTableSchemaBuilder, locale, objectField));

		_addNonobjectField(fdsTableSchemaBuilder, "status");
		_addNonobjectField(fdsTableSchemaBuilder, "creator");
	}

	private void _addFDSTableSchemaField(
		String businessType, String contentRenderer, String dbType,
		FDSTableSchemaBuilder fdsTableSchemaBuilder, String fieldName,
		String label, boolean sortable) {

		FDSTableSchemaField fdsTableSchemaField = null;

		if (Objects.equals(
				businessType, ObjectFieldConstants.BUSINESS_TYPE_ATTACHMENT) ||
			Objects.equals(dbType, ObjectFieldConstants.DB_TYPE_CLOB) ||
			Objects.equals(dbType, ObjectFieldConstants.DB_TYPE_STRING)) {

			StringFDSTableSchemaField stringFDSTableSchemaField =
				fdsTableSchemaBuilder.addFDSTableSchemaField(
					StringFDSTableSchemaField.class, fieldName, label);

			stringFDSTableSchemaField.setTruncate(true);

			fdsTableSchemaField = stringFDSTableSchemaField;
		}
		else if (Objects.equals(dbType, ObjectFieldConstants.DB_TYPE_DATE)) {
			DateFDSTableSchemaField dateFDSTableSchemaField =
				fdsTableSchemaBuilder.addFDSTableSchemaField(
					DateFDSTableSchemaField.class, fieldName, label);

			dateFDSTableSchemaField.setFormat("short");

			fdsTableSchemaField = dateFDSTableSchemaField;
		}
		else {
			fdsTableSchemaField = fdsTableSchemaBuilder.addFDSTableSchemaField(
				fieldName, label);

			if (Objects.equals(dbType, ObjectFieldConstants.DB_TYPE_BOOLEAN)) {
				fdsTableSchemaField.setContentRenderer("boolean");
			}
		}

		if (Validator.isNotNull(contentRenderer)) {
			fdsTableSchemaField.setContentRenderer(contentRenderer);
		}

		if (!Objects.equals(dbType, ObjectFieldConstants.DB_TYPE_BLOB) &&
			sortable) {

			fdsTableSchemaField.setSortable(true);
		}

		fdsTableSchemaBuilder.addFDSTableSchemaField(fdsTableSchemaField);
	}

	private void _addNonobjectField(
		FDSTableSchemaBuilder fdsTableSchemaBuilder, String fieldName) {

		if (Objects.equals(fieldName, "creator")) {
			_addFDSTableSchemaField(
				null, null, null, fdsTableSchemaBuilder, "creator.name",
				"author", true);
		}
		else if (Objects.equals(fieldName, "dateCreated")) {
			_addFDSTableSchemaField(
				null, null, "Date", fdsTableSchemaBuilder, "dateCreated",
				"created-date", true);
		}
		else if (Objects.equals(fieldName, "dateModified")) {
			_addFDSTableSchemaField(
				null, null, "Date", fdsTableSchemaBuilder, "dateModified",
				"modified-date", true);
		}
		else if (Objects.equals(fieldName, "id")) {
			_addFDSTableSchemaField(
				null, "actionLink", null, fdsTableSchemaBuilder, "id", "id",
				true);
		}
		else if (Objects.equals(fieldName, "status")) {
			_addFDSTableSchemaField(
				null, "status", null, fdsTableSchemaBuilder, "status", "status",
				true);
		}
	}

	private void _addObjectField(
		FDSTableSchemaBuilder fdsTableSchemaBuilder, Locale locale,
		ObjectField objectField) {

		if (Validator.isNull(objectField.getRelationshipType())) {
			_addFDSTableSchemaField(
				objectField.getBusinessType(), null, objectField.getDBType(),
				fdsTableSchemaBuilder,
				_getFieldName(
					objectField.getListTypeDefinitionId(),
					objectField.getName()),
				objectField.getLabel(locale, true), objectField.isIndexed());
		}
		else if (Objects.equals(
					objectField.getRelationshipType(),
					ObjectRelationshipConstants.TYPE_ONE_TO_MANY)) {

			ObjectRelationship objectRelationship =
				_objectRelationshipLocalService.
					fetchObjectRelationshipByObjectFieldId2(
						objectField.getObjectFieldId());

			ObjectDefinition objectDefinition =
				_objectDefinitionLocalService.fetchObjectDefinition(
					objectRelationship.getObjectDefinitionId1());

			if (objectDefinition.isSystem()) {
				return;
			}

			ObjectField titleObjectField =
				_objectFieldLocalService.fetchObjectField(
					objectDefinition.getTitleObjectFieldId());

			if (titleObjectField == null) {
				_addFDSTableSchemaField(
					objectField.getBusinessType(), null,
					objectField.getDBType(), fdsTableSchemaBuilder,
					objectField.getName(), objectField.getLabel(locale, true),
					false);
			}
			else {
				_addFDSTableSchemaField(
					titleObjectField.getBusinessType(), null,
					titleObjectField.getDBType(), fdsTableSchemaBuilder,
					_getFieldName(
						titleObjectField.getListTypeDefinitionId(),
						StringBundler.concat(
							StringUtil.replaceLast(
								objectField.getName(), "Id", ""),
							StringPool.PERIOD, titleObjectField.getName())),
					objectField.getLabel(locale, true), false);
			}
		}
	}

	private String _getFieldName(long listTypeDefinitionId, String fieldName) {
		if (listTypeDefinitionId > 0) {
			return fieldName + ".name";
		}

		return fieldName;
	}

	private final FDSTableSchemaBuilderFactory _fdsTableSchemaBuilderFactory;
	private final ObjectDefinition _objectDefinition;
	private final ObjectDefinitionLocalService _objectDefinitionLocalService;
	private final ObjectFieldLocalService _objectFieldLocalService;
	private final ObjectRelationshipLocalService
		_objectRelationshipLocalService;
	private final ObjectViewLocalService _objectViewLocalService;

}