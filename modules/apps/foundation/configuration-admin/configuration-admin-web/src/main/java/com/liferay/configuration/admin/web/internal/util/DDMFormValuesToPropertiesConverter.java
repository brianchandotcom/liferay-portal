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

package com.liferay.configuration.admin.web.internal.util;

import com.liferay.configuration.admin.web.internal.model.ConfigurationModel;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldType;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.storage.FieldConstants;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.util.ReflectionUtil;
import com.liferay.portal.kernel.util.StringPool;

import java.io.Serializable;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Vector;

import org.osgi.service.metatype.AttributeDefinition;

/**
 * @author Kamesh Sampath
 * @author Raymond Augé
 * @author Marcellus Tavares
 */
public class DDMFormValuesToPropertiesConverter {

	public DDMFormValuesToPropertiesConverter(
		ConfigurationModel configurationModel, DDMFormValues ddmFormValues,
		JSONFactory jsonFactory, Locale locale) {

		DDMForm ddmForm = ddmFormValues.getDDMForm();

		_configurationModel = configurationModel;
		_ddmFormFieldsMap = ddmForm.getDDMFormFieldsMap(false);
		_ddmFormFieldValuesMap = ddmFormValues.getDDMFormFieldValuesMap();
		_jsonFactory = jsonFactory;
		_locale = locale;
	}

	public Dictionary<String, Object> getProperties() {
		Dictionary<String, Object> properties = new Hashtable<>();

		AttributeDefinition[] attributeDefinitions =
			_configurationModel.getAttributeDefinitions(ConfigurationModel.ALL);

		for (AttributeDefinition attributeDefinition : attributeDefinitions) {
			Object value = null;

			List<DDMFormFieldValue> ddmFormFieldValues =
				_ddmFormFieldValuesMap.get(attributeDefinition.getID());

			if (attributeDefinition.getCardinality() == 0) {
				value = toSimpleValue(ddmFormFieldValues.get(0));
			}
			else if (attributeDefinition.getCardinality() > 0) {
				value = toArrayValue(ddmFormFieldValues);
			}
			else if (attributeDefinition.getCardinality() < 0) {
				value = toVectorValue(ddmFormFieldValues);
			}

			properties.put(attributeDefinition.getID(), value);
		}

		return properties;
	}

	protected String getDataTypeDefaultValue(String dataType) {
		if (dataType.equals(FieldConstants.BOOLEAN)) {
			return "false";
		}
		else if (dataType.equals(FieldConstants.DOUBLE) ||
				 dataType.equals(FieldConstants.FLOAT)) {

			return "0.0";
		}
		else if (dataType.equals(FieldConstants.INTEGER) ||
				 dataType.equals(FieldConstants.LONG) ||
				 dataType.equals(FieldConstants.SHORT)) {

			return "0";
		}

		return StringPool.BLANK;
	}

	protected String getDDMFormFieldDataType(String fieldName) {
		DDMFormField ddmFormField = _ddmFormFieldsMap.get(fieldName);

		return ddmFormField.getDataType();
	}

	protected String getDDMFormFieldType(String fieldName) {
		DDMFormField ddmFormField = _ddmFormFieldsMap.get(fieldName);

		return ddmFormField.getType();
	}

	protected String getDDMFormFieldValueString(
		DDMFormFieldValue ddmFormFieldValue) {

		Value value = ddmFormFieldValue.getValue();

		String valueString = value.getString(_locale);

		String type = getDDMFormFieldType(ddmFormFieldValue.getName());

		if (type.equals(DDMFormFieldType.SELECT)) {
			try {
				JSONArray jsonArray = _jsonFactory.createJSONArray(valueString);

				if (jsonArray.length() == 1) {
					valueString = jsonArray.getString(0);
				}
			}
			catch (JSONException jsone) {
				ReflectionUtil.throwException(jsone);
			}
		}

		String dataType = getDDMFormFieldDataType(ddmFormFieldValue.getName());

		if (valueString.equals(StringPool.BLANK)) {
			valueString = getDataTypeDefaultValue(dataType);
		}

		return valueString;
	}

	protected Serializable toArrayValue(
		List<DDMFormFieldValue> ddmFormFieldValues) {

		DDMFormFieldValue ddmFormFieldValue = ddmFormFieldValues.get(0);

		String dataType = getDDMFormFieldDataType(ddmFormFieldValue.getName());

		Vector<Serializable> values = toVectorValue(ddmFormFieldValues);

		return FieldConstants.getSerializable(dataType, values);
	}

	protected Serializable toSimpleValue(DDMFormFieldValue ddmFormFieldValue) {
		String dataType = getDDMFormFieldDataType(ddmFormFieldValue.getName());

		String valueString = getDDMFormFieldValueString(ddmFormFieldValue);

		return FieldConstants.getSerializable(dataType, valueString);
	}

	protected Vector<Serializable> toVectorValue(
		List<DDMFormFieldValue> ddmFormFieldValues) {

		Vector<Serializable> values = new Vector<>();

		for (DDMFormFieldValue ddmFormFieldValue : ddmFormFieldValues) {
			values.add(toSimpleValue(ddmFormFieldValue));
		}

		return values;
	}

	private final ConfigurationModel _configurationModel;
	private final Map<String, DDMFormField> _ddmFormFieldsMap;
	private final Map<String, List<DDMFormFieldValue>> _ddmFormFieldValuesMap;
	private final JSONFactory _jsonFactory;
	private final Locale _locale;

}