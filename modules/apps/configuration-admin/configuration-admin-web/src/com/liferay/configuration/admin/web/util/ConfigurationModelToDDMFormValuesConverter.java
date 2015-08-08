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

package com.liferay.configuration.admin.web.util;

import com.liferay.configuration.admin.web.model.ConfigurationModel;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldType;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Dictionary;
import java.util.Locale;
import java.util.Map;
import java.util.Vector;

import org.osgi.service.cm.Configuration;
import org.osgi.service.metatype.AttributeDefinition;

/**
 * @author Marcellus Tavares
 */
public class ConfigurationModelToDDMFormValuesConverter {

	public ConfigurationModelToDDMFormValuesConverter(
		ConfigurationModel configurationModel, DDMForm ddmForm, Locale locale) {

		_configurationModel = configurationModel;
		_ddmForm = ddmForm;
		_ddmFormFieldsMap = ddmForm.getDDMFormFieldsMap(false);
		_locale = locale;
	}

	public DDMFormValues getDDMFormValues() {
		DDMFormValues ddmFormValues = new DDMFormValues(_ddmForm);

		ddmFormValues.addAvailableLocale(_locale);
		ddmFormValues.setDefaultLocale(_locale);

		addDDMFormFieldValues(
			_configurationModel.getAttributeDefinitions(ConfigurationModel.ALL),
			ddmFormValues);

		return ddmFormValues;
	}

	protected void addDDMFormFieldValue(
		String name, Object value, DDMFormValues ddmFormValues) {

		DDMFormFieldValue ddmFormFieldValue = createDDMFormFieldValue(name);

		setDDMFormFieldValueLocalizedValue(
			String.valueOf(value), ddmFormFieldValue);

		ddmFormValues.addDDMFormFieldValue(ddmFormFieldValue);
	}

	protected void addDDMFormFieldValues(
		AttributeDefinition attributeDefinition, DDMFormValues ddmFormValues) {

		Configuration configuration = _configurationModel.getConfiguration();

		if (configuration != null) {
			addDDMFormFieldValues(
				attributeDefinition.getID(),
				attributeDefinition.getCardinality(), configuration,
				ddmFormValues);
		}
		else {
			addDDMFormFieldValues(
				attributeDefinition.getID(),
				attributeDefinition.getDefaultValue(), ddmFormValues);
		}
	}

	protected void addDDMFormFieldValues(
		AttributeDefinition[] attributeDefinitions,
		DDMFormValues ddmFormValues) {

		if (attributeDefinitions == null) {
			return;
		}

		for (AttributeDefinition attributeDefinition : attributeDefinitions) {
			addDDMFormFieldValues(attributeDefinition, ddmFormValues);
		}
	}

	protected void addDDMFormFieldValues(
		String name, int cardinality, Configuration configuration,
		DDMFormValues ddmFormValues) {

		Dictionary<String, Object> properties = configuration.getProperties();

		Object property = properties.get(name);

		if (property == null) {
			return;
		}

		if (cardinality == 0) {
			addDDMFormFieldValue(name, String.valueOf(property), ddmFormValues);
		}
		else if (cardinality > 0) {
			addDDMFormFieldValues(name, (Object[])property, ddmFormValues);
		}
		else {
			addDDMFormFieldValues(name, (Vector<?>)property, ddmFormValues);
		}
	}

	protected void addDDMFormFieldValues(
		String name, Object[] values, DDMFormValues ddmFormValues) {

		for (int i = 0; i < values.length; i++) {
			addDDMFormFieldValue(name, values[i], ddmFormValues);
		}
	}

	protected void addDDMFormFieldValues(
		String name, String[] defaultValues, DDMFormValues ddmFormValues) {

		if (defaultValues == null) {
			addDDMFormFieldValue(name, StringPool.BLANK, ddmFormValues);

			return;
		}

		defaultValues = StringUtil.split(defaultValues[0], StringPool.PIPE);

		for (String defaultValue : defaultValues) {
			addDDMFormFieldValue(name, defaultValue, ddmFormValues);
		}
	}

	protected void addDDMFormFieldValues(
		String name, Vector<?> values, DDMFormValues ddmFormValues) {

		for (Object value : values) {
			addDDMFormFieldValue(name, value, ddmFormValues);
		}
	}

	protected DDMFormFieldValue createDDMFormFieldValue(String name) {
		DDMFormFieldValue ddmFormFieldValue = new DDMFormFieldValue();

		ddmFormFieldValue.setName(name);
		ddmFormFieldValue.setInstanceId(StringUtil.randomString());

		return ddmFormFieldValue;
	}

	protected String getDDMFormFieldType(String ddmFormFieldName) {
		DDMFormField ddmFormField = _ddmFormFieldsMap.get(ddmFormFieldName);

		return ddmFormField.getType();
	}

	protected void setDDMFormFieldValueLocalizedValue(
		String value, DDMFormFieldValue ddmFormFieldValue) {

		String type = getDDMFormFieldType(ddmFormFieldValue.getName());

		if (type.equals(DDMFormFieldType.SELECT)) {
			value = "[\"" + value + "\"]";
		}

		LocalizedValue localizedValue = new LocalizedValue(_locale);

		localizedValue.addString(_locale, value);

		ddmFormFieldValue.setValue(localizedValue);
	}

	private final ConfigurationModel _configurationModel;
	private final DDMForm _ddmForm;
	private final Map<String, DDMFormField> _ddmFormFieldsMap;
	private final Locale _locale;

}