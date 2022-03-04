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

package com.liferay.object.internal.search.spi.model.index.contributor;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectField;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.FieldArray;
import com.liferay.portal.kernel.service.PersistedModelLocalService;
import com.liferay.portal.kernel.service.PersistedModelLocalServiceRegistry;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;

import java.io.Serializable;

import java.math.BigDecimal;

import java.text.Format;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Marco Leo
 * @author Brian Wing Shun Chan
 */
public class ObjectEntryModelDocumentContributor
	implements ModelDocumentContributor<ObjectEntry> {

	public ObjectEntryModelDocumentContributor(
		String className,
		ObjectDefinitionLocalService objectDefinitionLocalService,
		ObjectEntryLocalService objectEntryLocalService,
		ObjectFieldLocalService objectFieldLocalService,
		PersistedModelLocalServiceRegistry persistedModelLocalServiceRegistry) {

		_className = className;
		_objectDefinitionLocalService = objectDefinitionLocalService;
		_objectEntryLocalService = objectEntryLocalService;
		_objectFieldLocalService = objectFieldLocalService;
		_persistedModelLocalServiceRegistry =
			persistedModelLocalServiceRegistry;
	}

	@Override
	public void contribute(Document document, ObjectEntry objectEntry) {
		try {
			_contribute(document, objectEntry);
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to index object entry " +
						objectEntry.getObjectEntryId(),
					exception);
			}
		}
	}

	private void _addField(
		FieldArray fieldArray, String fieldName, String valueFieldName,
		String value) {

		Field field = new Field("");

		field.addField(new Field("fieldName", fieldName));
		field.addField(new Field("valueFieldName", valueFieldName));
		field.addField(new Field(valueFieldName, value));

		fieldArray.addField(field);
	}

	private void _appendToContent(
		StringBundler sb, String objectFieldName, String valueString) {

		sb.append(objectFieldName);
		sb.append(": ");
		sb.append(valueString);
		sb.append(StringPool.COMMA_AND_SPACE);
	}

	private void _contribute(Document document, ObjectEntry objectEntry)
		throws Exception {

		if (_log.isDebugEnabled()) {
			_log.debug("Document " + document);
			_log.debug("Object entry " + objectEntry);
		}

		FieldArray fieldArray = (FieldArray)document.getField(
			"nestedFieldArray");

		if (fieldArray == null) {
			fieldArray = new FieldArray("nestedFieldArray");

			document.add(fieldArray);
		}

		document.addKeyword(
			"objectDefinitionId", objectEntry.getObjectDefinitionId());

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.fetchObjectDefinition(
				objectEntry.getObjectDefinitionId());

		document.addKeyword(
			"objectDefinitionName", objectDefinition.getShortName());

		document.add(
			new Field(
				Field.getSortableFieldName(Field.ENTRY_CLASS_PK),
				document.get(Field.ENTRY_CLASS_PK)));

		Map<String, Serializable> values = _objectEntryLocalService.getValues(
			objectEntry.getObjectEntryId());

		List<ObjectField> objectFields =
			_objectFieldLocalService.getObjectFields(
				objectEntry.getObjectDefinitionId());

		StringBundler sb = new StringBundler(objectFields.size() * 4);

		for (ObjectField objectField : objectFields) {
			_contribute(fieldArray, objectEntry, objectField, sb, values);
		}

		if (sb.index() > 0) {
			sb.setIndex(sb.index() - 1);
		}

		document.add(new Field("objectEntryContent", sb.toString()));

		document.add(
			new Field("objectEntryTitle", objectEntry.getTitleValue()));

		document.remove(Field.USER_NAME);
	}

	private void _contribute(
		FieldArray fieldArray, ObjectEntry objectEntry, ObjectField objectField,
		StringBundler sb, Map<String, Serializable> values) {

		if (!objectField.isIndexed()) {
			return;
		}

		Object value = values.get(objectField.getName());

		if (value == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					StringBundler.concat(
						"Object entry ", objectEntry.getObjectEntryId(),
						" has object field \"", objectField.getName(),
						"\" with a null value"));
			}

			return;
		}

		if (StringUtil.equals(
				objectField.getBusinessType(),
				ObjectFieldConstants.BUSINESS_TYPE_ATTACHMENT)) {

			value = _getFileName(GetterUtil.getLong(value));
		}

		String objectFieldName = objectField.getName();
		String valueString = String.valueOf(value);

		if (objectField.isIndexedAsKeyword()) {
			_addField(
				fieldArray, objectFieldName, "value_keyword",
				StringUtil.lowerCase(valueString));

			_appendToContent(sb, objectFieldName, valueString);
		}
		else if (value instanceof BigDecimal) {
			_addField(fieldArray, objectFieldName, "value_double", valueString);

			_appendToContent(sb, objectFieldName, valueString);
		}
		else if (value instanceof Boolean) {
			_addField(
				fieldArray, objectFieldName, "value_boolean", valueString);
			_addField(
				fieldArray, objectFieldName, "value_keyword",
				_translate((Boolean)value));

			_appendToContent(sb, objectFieldName, valueString);
		}
		else if (value instanceof Date) {
			_addField(
				fieldArray, objectFieldName, "value_date",
				_getDateString(value));

			_appendToContent(sb, objectFieldName, _getDateString(value));
		}
		else if (value instanceof Double) {
			_addField(fieldArray, objectFieldName, "value_double", valueString);

			_appendToContent(sb, objectFieldName, valueString);
		}
		else if (value instanceof Integer) {
			_addField(
				fieldArray, objectFieldName, "value_integer", valueString);

			_appendToContent(sb, objectFieldName, valueString);
		}
		else if (value instanceof Long) {
			_addField(fieldArray, objectFieldName, "value_long", valueString);

			_appendToContent(sb, objectFieldName, valueString);
		}
		else if (value instanceof String) {
			if (Validator.isBlank(objectField.getIndexedLanguageId())) {
				_addField(
					fieldArray, objectFieldName, "value_text", valueString);
			}
			else {
				_addField(
					fieldArray, objectFieldName,
					"value_" + objectField.getIndexedLanguageId(), valueString);
			}

			_addField(
				fieldArray, objectFieldName, "value_keyword_lowercase",
				_getSortableValue(valueString));

			_appendToContent(sb, objectFieldName, valueString);
		}
		else if (value instanceof byte[]) {
			_addField(
				fieldArray, objectFieldName, "value_binary",
				Base64.encode((byte[])value));
		}
		else {
			if (_log.isWarnEnabled()) {
				_log.warn(
					StringBundler.concat(
						"Object entry ", objectEntry.getObjectEntryId(),
						" has object field \"", objectFieldName,
						"\" with unsupported value ", value));
			}
		}
	}

	private String _getDateString(Object value) {
		Format format = FastDateFormatFactoryUtil.getSimpleDateFormat(
			"yyyyMMddHHmmss");

		return format.format(value);
	}

	private String _getFileName(long dlFileEntryId) {
		try {
			PersistedModelLocalService persistedModelLocalService =
				_persistedModelLocalServiceRegistry.
					getPersistedModelLocalService(DLFileEntry.class.getName());

			DLFileEntry dlFileEntry =
				(DLFileEntry)persistedModelLocalService.getPersistedModel(
					dlFileEntryId);

			return dlFileEntry.getFileName();
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			return StringPool.BLANK;
		}
	}

	private String _getSortableValue(String value) {
		if (value.length() > 256) {
			return value.substring(0, 256);
		}

		return value;
	}

	private String _translate(Boolean value) {
		if (value.booleanValue()) {
			return "yes";
		}

		return "no";
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ObjectEntryModelDocumentContributor.class);

	private final String _className;
	private final ObjectDefinitionLocalService _objectDefinitionLocalService;
	private final ObjectEntryLocalService _objectEntryLocalService;
	private final ObjectFieldLocalService _objectFieldLocalService;
	private final PersistedModelLocalServiceRegistry
		_persistedModelLocalServiceRegistry;

}