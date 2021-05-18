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

import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectField;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.FieldArray;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;

import java.io.Serializable;

import java.math.BigDecimal;

import java.text.Format;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 * @author Brian Wing Shun Chan
 */
@Component(
	immediate = true,
	property = "indexer.class.name=com.liferay.object.model.ObjectEntry",
	service = ModelDocumentContributor.class
)
public class ObjectEntryModelDocumentContributor
	implements ModelDocumentContributor<ObjectEntry> {

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

	private void _contribute(Document document, ObjectEntry objectEntry)
		throws Exception {

		if (_log.isDebugEnabled()) {
			_log.debug("Document " + document);
			_log.debug("Object entry " + objectEntry);
		}

		document.addKeyword(
			"objectDefinitionId", objectEntry.getObjectDefinitionId());

		FieldArray fieldArray = (FieldArray)document.getField(
			"nestedFieldArray");

		if (fieldArray == null) {
			fieldArray = new FieldArray("nestedFieldArray");

			document.add(fieldArray);
		}

		Map<String, Serializable> values = _objectEntryLocalService.getValues(
			objectEntry.getObjectEntryId());

		List<ObjectField> objectFields =
			_objectFieldLocalService.getObjectFields(
				objectEntry.getObjectDefinitionId());

		for (ObjectField objectField : objectFields) {
			_contribute(fieldArray, objectEntry, objectField, values);
		}
	}

	private void _contribute(
		FieldArray fieldArray, ObjectEntry objectEntry, ObjectField objectField,
		Map<String, Serializable> values) {

		if (!objectField.isIndexed()) {
			return;
		}

		String name = objectField.getName();

		Object value = values.get(name);

		if (value == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					StringBundler.concat(
						"Object entry ", objectEntry.getObjectEntryId(),
						" has field \"", name, "\" with a null value"));
			}

			return;
		}

		if ((!Objects.equals(objectField.getType(), "String") ||
			 objectField.isIndexedAsKeyword()) &&
			!Validator.isBlank(objectField.getIndexedLanguageId())) {

			if (_log.isWarnEnabled()) {
				_log.warn(
					StringBundler.concat(
						"Object entry ", objectEntry.getObjectEntryId(),
						" has field \"", name,
						"\" which is not indexed as full-text. Locale ",
						objectField.getIndexedLanguageId(),
						" will be ignored"));
			}
		}

		if (objectField.isIndexedAsKeyword()) {
			_addField(
				fieldArray, name, "value_keyword",
				StringUtil.lowerCase(String.valueOf(value)));
		}
		else if (value instanceof BigDecimal) {
			_addField(fieldArray, name, "value_double", String.valueOf(value));
		}
		else if (value instanceof Boolean) {
			_addField(fieldArray, name, "value_boolean", String.valueOf(value));
		}
		else if (value instanceof Date) {
			_addField(fieldArray, name, "value_date", _getDateString(value));
		}
		else if (value instanceof Double) {
			_addField(fieldArray, name, "value_double", String.valueOf(value));
		}
		else if (value instanceof Integer) {
			_addField(fieldArray, name, "value_integer", String.valueOf(value));
		}
		else if (value instanceof Long) {
			_addField(fieldArray, name, "value_long", String.valueOf(value));
		}
		else if (value instanceof String) {
			if (Validator.isBlank(objectField.getIndexedLanguageId())) {
				_addField(fieldArray, name, "value_text", (String)value);
			}
			else {
				_addField(
					fieldArray, name,
					"value_" + objectField.getIndexedLanguageId(),
					(String)value);
				_addField(
					fieldArray, name,
					"value_" + objectField.getIndexedLanguageId() + "_sortable",
					(String)value);
			}
		}
		else if (value instanceof byte[]) {
			_addField(
				fieldArray, name, "value_binary", Base64.encode((byte[])value));
		}
		else {
			if (_log.isWarnEnabled()) {
				_log.warn(
					StringBundler.concat(
						"Object entry ", objectEntry.getObjectEntryId(),
						" has field \"", name, "\" with unsupported value ",
						value));
			}
		}
	}

	private String _getDateString(Object value) {
		Format format = FastDateFormatFactoryUtil.getSimpleDateFormat(
			"yyyyMMddHHmmss");

		return format.format(value);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ObjectEntryModelDocumentContributor.class);

	@Reference
	private ObjectEntryLocalService _objectEntryLocalService;

	@Reference
	private ObjectFieldLocalService _objectFieldLocalService;

}