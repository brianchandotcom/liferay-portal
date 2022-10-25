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

				String label = objectViewColumn.getLabel(locale, true);

				if (label.isEmpty()) {
					label = objectField.getLabel(locale, true);
				}

				if ((objectField == null) || objectField.isSystem()) {
					_addNonbjectField(
						fdsTableSchemaBuilder, label,
						objectViewColumn.getObjectFieldName());
				}
				else {
					_addObjectField(fdsTableSchemaBuilder, label, objectField);
				}
			}
		);

		return fdsTableSchemaBuilder.build();
	}

	private void _addAllObjectFields(
		FDSTableSchemaBuilder fdsTableSchemaBuilder, Locale locale) {

		if (_objectDefinition.isDefaultStorageType()) {
			_addNonbjectField(fdsTableSchemaBuilder, "id", "id");
		}
		else {
			_addNonbjectField(
				fdsTableSchemaBuilder, "externalReferenceCode",
				"externalReferenceCode");
		}

		List<ObjectField> objectFields =
			_objectFieldLocalService.getObjectFields(
				_objectDefinition.getObjectDefinitionId());

		objectFields.forEach(
			objectField -> _addObjectField(
				fdsTableSchemaBuilder, objectField.getLabel(locale, true),
				objectField));

		_addNonbjectField(fdsTableSchemaBuilder, "status", "status");
		_addNonbjectField(fdsTableSchemaBuilder, "author", "creator");
	}

	private void _addFDSTableSchemaField(
		String businessType, String contentRenderer, String dbType,
		FDSTableSchemaBuilder fdsTableSchemaBuilder, String fieldName,
		String label, boolean localizeLabel, boolean sortable) {

		FDSTableSchemaField fdsTableSchemaField = new FDSTableSchemaField();

		fdsTableSchemaField.setLocalizeLabel(localizeLabel);

		if (Objects.equals(
				businessType, ObjectFieldConstants.BUSINESS_TYPE_ATTACHMENT) ||
			Objects.equals(dbType, ObjectFieldConstants.DB_TYPE_CLOB) ||
			Objects.equals(dbType, ObjectFieldConstants.DB_TYPE_STRING)) {

			StringFDSTableSchemaField stringFDSTableSchemaField =
				new StringFDSTableSchemaField();

			if (Objects.equals(
					businessType,
					ObjectFieldConstants.BUSINESS_TYPE_ATTACHMENT)) {

				stringFDSTableSchemaField.setContentRenderer("link");
			}
			else if (Objects.equals(
						businessType,
						ObjectFieldConstants.
							BUSINESS_TYPE_MULTISELECT_PICKLIST)) {

				stringFDSTableSchemaField.setContentRenderer(
					"multiselectPicklistDataRenderer");
			}

			stringFDSTableSchemaField.setFieldName(fieldName);
			stringFDSTableSchemaField.setLabel(label);
			stringFDSTableSchemaField.setLocalizeLabel(localizeLabel);
			stringFDSTableSchemaField.setTruncate(true);

			fdsTableSchemaBuilder.add(stringFDSTableSchemaField);

			fdsTableSchemaField = stringFDSTableSchemaField;
		}
		else if (Objects.equals(dbType, ObjectFieldConstants.DB_TYPE_DATE)) {
			DateFDSTableSchemaField dateFDSTableSchemaField =
				new DateFDSTableSchemaField();

			dateFDSTableSchemaField.setFieldName(fieldName);
			dateFDSTableSchemaField.setFormat("short");
			dateFDSTableSchemaField.setLabel(label);
			dateFDSTableSchemaField.setLocalizeLabel(localizeLabel);

			fdsTableSchemaBuilder.add(dateFDSTableSchemaField);

			fdsTableSchemaField = dateFDSTableSchemaField;
		}
		else {
			fdsTableSchemaField.setFieldName(fieldName);
			fdsTableSchemaField.setLabel(label);

			fdsTableSchemaBuilder.add(fdsTableSchemaField);

			if (Objects.equals(dbType, ObjectFieldConstants.DB_TYPE_BOOLEAN)) {
				fdsTableSchemaField.setContentRenderer("boolean");
			}
		}

		if (Validator.isNotNull(contentRenderer)) {
			fdsTableSchemaField.setContentRenderer(contentRenderer);
		}

		if (!Objects.equals(
				businessType, ObjectFieldConstants.BUSINESS_TYPE_AGGREGATION) &&
			!Objects.equals(dbType, ObjectFieldConstants.DB_TYPE_BLOB) &&
			sortable) {

			fdsTableSchemaField.setSortable(true);
		}

		fdsTableSchemaBuilder.add(fdsTableSchemaField);
	}

	private void _addNonbjectField(
		FDSTableSchemaBuilder fdsTableSchemaBuilder, String fieldLabel,
		String fieldName) {

		if (Objects.equals(fieldName, "creator")) {
			_addFDSTableSchemaField(
				null, null, null, fdsTableSchemaBuilder, fieldName + ".name",
				fieldLabel, true, true);
		}
		else if (Objects.equals(fieldName, "createDate")) {
			_addFDSTableSchemaField(
				null, null, "Date", fdsTableSchemaBuilder, "dateCreated",
				fieldLabel, true, true);
		}
		else if (Objects.equals(fieldName, "externalReferenceCode")) {
			_addFDSTableSchemaField(
				null, "actionLink", null, fdsTableSchemaBuilder,
				"externalReferenceCode", fieldLabel, true, true);
		}
		else if (Objects.equals(fieldName, "id")) {
			_addFDSTableSchemaField(
				null, "actionLink", null, fdsTableSchemaBuilder, "id",
				fieldLabel, true, true);
		}
		else if (Objects.equals(fieldName, "modifiedDate")) {
			_addFDSTableSchemaField(
				null, null, "Date", fdsTableSchemaBuilder, "dateModified",
				fieldLabel, true, true);
		}
		else if (Objects.equals(fieldName, "status")) {
			_addFDSTableSchemaField(
				null, "status", null, fdsTableSchemaBuilder, fieldName,
				fieldLabel, true, true);
		}
	}

	private void _addObjectField(
		FDSTableSchemaBuilder fdsTableSchemaBuilder, String label,
		ObjectField objectField) {

		if (objectField.isSystem()) {
			return;
		}

		if (Validator.isNull(objectField.getRelationshipType())) {
			_addFDSTableSchemaField(
				objectField.getBusinessType(), null, objectField.getDBType(),
				fdsTableSchemaBuilder,
				_getFieldName(
					objectField.getBusinessType(), objectField.getName()),
				label, false, objectField.isIndexed());
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

			ObjectField titleObjectField =
				_objectFieldLocalService.fetchObjectField(
					objectDefinition.getTitleObjectFieldId());

			if (titleObjectField == null) {
				_addFDSTableSchemaField(
					objectField.getBusinessType(), null,
					objectField.getDBType(), fdsTableSchemaBuilder,
					objectField.getName(), label, false, false);
			}
			else {
				_addFDSTableSchemaField(
					titleObjectField.getBusinessType(), null,
					titleObjectField.getDBType(), fdsTableSchemaBuilder,
					_getFieldName(
						titleObjectField.getBusinessType(),
						StringBundler.concat(
							StringUtil.replaceLast(
								objectField.getName(), "Id", ""),
							StringPool.PERIOD, titleObjectField.getName())),
					label, false, false);
			}
		}
	}

	private String _getFieldName(String businessType, String fieldName) {
		if (Objects.equals(
				businessType, ObjectFieldConstants.BUSINESS_TYPE_ATTACHMENT)) {

			return fieldName + ".link";
		}

		if (Objects.equals(
				businessType, ObjectFieldConstants.BUSINESS_TYPE_PICKLIST)) {

			return fieldName + ".name";
		}

		if (Objects.equals(
				businessType, ObjectFieldConstants.BUSINESS_TYPE_RICH_TEXT)) {

			return fieldName + "RawText";
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