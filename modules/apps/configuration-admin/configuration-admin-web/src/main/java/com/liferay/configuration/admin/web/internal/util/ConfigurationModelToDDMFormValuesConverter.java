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
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.settings.LocationVariableProtocol;
import com.liferay.portal.kernel.settings.LocationVariableResolver;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.cm.Configuration;
import org.osgi.service.metatype.AttributeDefinition;

/**
 * @author Marcellus Tavares
 */
public class ConfigurationModelToDDMFormValuesConverter {

	public ConfigurationModelToDDMFormValuesConverter(
		ConfigurationModel configurationModel, DDMForm ddmForm, Locale locale) {

		this(configurationModel, ddmForm, locale, null);
	}

	public ConfigurationModelToDDMFormValuesConverter(
		ConfigurationModel configurationModel, DDMForm ddmForm, Locale locale,
		LocationVariableResolver locationVariableResolver) {

		_configurationModel = configurationModel;
		_ddmForm = ddmForm;
		_locale = locale;
		_locationVariableResolver = locationVariableResolver;

		_ddmFormFieldsMap = ddmForm.getDDMFormFieldsMap(false);
	}

	public DDMFormValues getDDMFormValues() {
		DDMFormValues ddmFormValues = new DDMFormValues(_ddmForm);

		ddmFormValues.addAvailableLocale(_locale);
		ddmFormValues.setDefaultLocale(_locale);

		_addDDMFormFieldValues(
			_configurationModel.getAttributeDefinitions(ConfigurationModel.ALL),
			ddmFormValues);

		return ddmFormValues;
	}

	protected void addDDMFormFieldValue(
		String name, String value, DDMFormValues ddmFormValues) {

		DDMFormFieldValue ddmFormFieldValue = createDDMFormFieldValue(name);

		_setDDMFormFieldValueLocalizedValue(value, ddmFormFieldValue);

		ddmFormValues.addDDMFormFieldValue(ddmFormFieldValue);
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

	private void _addDDMFormFieldValues(
		AttributeDefinition attributeDefinition, DDMFormValues ddmFormValues) {

		String[] values = null;

		if (attributeDefinition.getType() == AttributeDefinition.PASSWORD) {
			values = _PASSWORD_TYPE_VALUES;
		}
		else {
			Configuration configuration =
				_configurationModel.getConfiguration();

			if (_hasConfigurationAttribute(
					configuration, attributeDefinition)) {

				values = AttributeDefinitionUtil.getPropertyStringArray(
					attributeDefinition, configuration);
			}
			else {
				values = AttributeDefinitionUtil.getDefaultValue(
					attributeDefinition);
			}
		}

		_addDDMFormFieldValues(
			attributeDefinition.getID(), values, ddmFormValues);
	}

	private void _addDDMFormFieldValues(
		AttributeDefinition[] attributeDefinitions,
		DDMFormValues ddmFormValues) {

		if (attributeDefinitions == null) {
			return;
		}

		for (AttributeDefinition attributeDefinition : attributeDefinitions) {
			_addDDMFormFieldValues(attributeDefinition, ddmFormValues);
		}
	}

	private void _addDDMFormFieldValues(
		String name, String[] values, DDMFormValues ddmFormValues) {

		for (String value : values) {
			addDDMFormFieldValue(name, value, ddmFormValues);
		}
	}

	private boolean _hasConfigurationAttribute(
		Configuration configuration, AttributeDefinition attributeDefinition) {

		if (configuration == null) {
			return false;
		}

		Dictionary<String, Object> properties = configuration.getProperties();

		Enumeration<String> enumeration = properties.keys();

		String attributeDefinitionID = attributeDefinition.getID();

		while (enumeration.hasMoreElements()) {
			if (attributeDefinitionID.equals(enumeration.nextElement())) {
				return true;
			}
		}

		return false;
	}

	private void _setDDMFormFieldValueLocalizedValue(
		String value, DDMFormFieldValue ddmFormFieldValue) {

		try {
			if ((_locationVariableResolver != null) &&
				_locationVariableResolver.isLocationVariable(
					value, LocationVariableProtocol.RESOURCE)) {

				value = _locationVariableResolver.resolve(value);
			}
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to resolve the location variable", exception);
			}
		}

		String type = getDDMFormFieldType(ddmFormFieldValue.getName());

		if (type.equals(DDMFormFieldType.LOCALIZABLE_TEXT) &&
			!JSONUtil.isValid(value)) {

			value = String.valueOf(
				JSONUtil.put(LocaleUtil.toLanguageId(_locale), value));
		}
		else if (type.equals(DDMFormFieldType.SELECT)) {
			value = "[\"" + value + "\"]";
		}

		LocalizedValue localizedValue = new LocalizedValue(_locale);

		localizedValue.addString(_locale, value);

		ddmFormFieldValue.setValue(localizedValue);
	}

	private static final String[] _PASSWORD_TYPE_VALUES = {
		Portal.TEMP_OBFUSCATION_VALUE
	};

	private static final Log _log = LogFactoryUtil.getLog(
		ConfigurationModelToDDMFormValuesConverter.class);

	private final ConfigurationModel _configurationModel;
	private final DDMForm _ddmForm;
	private final Map<String, DDMFormField> _ddmFormFieldsMap;
	private final Locale _locale;
	private final LocationVariableResolver _locationVariableResolver;

}