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

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMForm;
import com.liferay.portlet.dynamicdatamapping.model.DDMFormField;
import com.liferay.portlet.dynamicdatamapping.model.DDMFormFieldOptions;
import com.liferay.portlet.dynamicdatamapping.model.LocalizedValue;
import com.liferay.portlet.dynamicdatamapping.storage.FieldConstants;

import java.util.Dictionary;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.osgi.service.cm.Configuration;
import org.osgi.service.metatype.AttributeDefinition;
import org.osgi.service.metatype.ObjectClassDefinition;

/**
 * @author Kamesh Sampath
 * @author Raymond Augé
 */
public class ConfigurationConverter {

	public static DDMForm convert(
		ObjectClassDefinition objectClassDefinition,
		Configuration configuration, Locale locale) {

		DDMForm ddmForm = new DDMForm();

		ddmForm.setAvailableLocales(
			SetUtil.fromArray(LanguageUtil.getAvailableLocales()));
		ddmForm.setDefaultLocale(locale);

		AttributeDefinition[] requiredAttributeDefinitions =
			objectClassDefinition.getAttributeDefinitions(
				ObjectClassDefinition.REQUIRED);

		addFieldToForm(
			configuration, ddmForm, requiredAttributeDefinitions, locale, true);

		AttributeDefinition[] optionalAttributeDefinitions =
			objectClassDefinition.getAttributeDefinitions(
				ObjectClassDefinition.OPTIONAL);

		addFieldToForm(
			configuration, ddmForm, optionalAttributeDefinitions, locale,
			false);

		return ddmForm;
	}

	private static DDMFormFieldOptions _getDDMFieldOptions(
		AttributeDefinition attributeDefinition, Locale locale) {

		DDMFormFieldOptions ddmFormFieldOptions = new DDMFormFieldOptions();

		String[] labels = attributeDefinition.getOptionLabels();
		String[] values = attributeDefinition.getOptionValues();

		_setOptionFieldLabelsAndValues(
			ddmFormFieldOptions, labels, values, locale);

		return ddmFormFieldOptions;
	}

	private static void _setOptionFieldLabelsAndValues(
		DDMFormFieldOptions ddmFormFieldOptions, String[] labels,
		String[] values, Locale locale) {

		if ((labels == null) || (values == null)) {
			return;
		}

		for (int i = 0; i < labels.length; i++) {
			String value = values[i];
			String label = labels[i];

			ddmFormFieldOptions.addOption(value);

			ddmFormFieldOptions.addOptionLabel(value, locale, label);
		}
	}

	private static void addFieldToForm(
		Configuration configuration, DDMForm ddmForm,
		AttributeDefinition[] attributeDefinitions, Locale locale,
		boolean required) {

		if (attributeDefinitions == null) {
			return;
		}

		List<DDMFormField> ddmFormFields = ddmForm.getDDMFormFields();

		for (AttributeDefinition attributeDefinition : attributeDefinitions) {
			if (attributeDefinition == null) {
				continue;
			}

			String id = attributeDefinition.getID();
			String type = attributeToDDMType(attributeDefinition, locale);

			DDMFormField ddmFormField = new DDMFormField(id, type);

			ddmFormField.setDataType(
				attributeToDDMDataType(attributeDefinition));

			ddmFormField.setDDMForm(ddmForm);
			ddmFormField.setLabel(
				attributeToLabel(attributeDefinition, locale));
			ddmFormField.setTip(attributeToTip(attributeDefinition, locale));
			ddmFormField.setLocalizable(true);

			// Default values

			LocalizedValue predefinedValue = attributeDefaultValue(
				configuration, attributeDefinition, locale);

			DDMFormFieldOptions ddmFormFieldOptions = _getDDMFieldOptions(
				attributeDefinition, locale);

			if ((type.equals("radio") || type.equals("select")) &&
				_hasDDMFormFieldOptionsAvailable(ddmFormFieldOptions)) {

				_setDDMFormFieldOptions(type, ddmFormField, ddmFormFieldOptions);

				if (predefinedValue!= null) {
					String value = predefinedValue.getValues().get(locale);

					JSONArray defaultValueJSON =
						JSONFactoryUtil.createJSONArray();

					defaultValueJSON.put(value);

					LocalizedValue localizedDefaultValue = new LocalizedValue(
						locale);

					localizedDefaultValue.addString(
						locale, defaultValueJSON.toString());

					ddmFormField.setPredefinedValue(localizedDefaultValue);
				}
			}
			else {
				ddmFormField.setPredefinedValue(predefinedValue);
			}

			if (!type.equals("checkbox")) {
				ddmFormField.setRequired(required);
			}

			ddmFormField.setShowLabel(true);

			int cardinality = attributeDefinition.getCardinality();

			if (cardinality != 0) {
				ddmFormField.setRepeatable(true);
			}

			ddmFormFields.add(ddmFormField);
		}
	}

	private static LocalizedValue attributeDefaultValue(
		Configuration configuration, AttributeDefinition attributeDefinition,
		Locale locale) {

		LocalizedValue value = new LocalizedValue(locale);

		if (configuration != null) {
			String attributeID = attributeDefinition.getID();

			Dictionary<String, Object> properties =
				configuration.getProperties();

			if (properties != null) {
				String propValue = String.valueOf(properties.get(attributeID));

				value.addString(locale, propValue);
			}
		}
		else {
			int cardinality = attributeDefinition.getCardinality();

			String[] attributeValues = null;

			if (cardinality > 1 ) {
				String[] defaultValue = attributeDefinition.getDefaultValue();

				if ((defaultValue != null) && (defaultValue.length > 0)) {
					String temp = attributeDefinition.getDefaultValue()[0];

					attributeValues = StringUtil.split(temp, StringPool.PIPE);
				}
			}
			else {
				attributeValues = attributeDefinition.getDefaultValue();
			}

			if (attributeValues != null) {
				for (String attributeValue : attributeValues) {
					value.addString(locale, attributeValue);
				}
			}
		}

		return value;
	}

	private static String attributeToDDMDataType(
		AttributeDefinition attributeDefinition) {

		int type = attributeDefinition.getType();

		switch (type) {
			case AttributeDefinition.DOUBLE: {
				return FieldConstants.DOUBLE;
			}

			case AttributeDefinition.FLOAT: {
				return FieldConstants.FLOAT;
			}

			case AttributeDefinition.INTEGER: {
				return FieldConstants.INTEGER;
			}

			case AttributeDefinition.LONG: {
				return FieldConstants.LONG;
			}

			case AttributeDefinition.SHORT: {
				return FieldConstants.SHORT;
			}

			case AttributeDefinition.BOOLEAN: {
				return FieldConstants.BOOLEAN;
			}

			default: {
				return FieldConstants.STRING;
			}
		}
	}

	private static String attributeToDDMType(
		AttributeDefinition attributeDefinition, Locale locale) {

		int type = attributeDefinition.getType();

		switch (type) {
			case AttributeDefinition.BOOLEAN: {
				String[] optionLabels = attributeDefinition.getOptionLabels();

				if ((optionLabels == null) || (optionLabels.length == 0)) {
					return "checkbox";
				}

				return "radio";
			}

			default: {
				DDMFormFieldOptions ddmFormFieldOptions = _getDDMFieldOptions(
					attributeDefinition, locale);

				if (_hasDDMFormFieldOptionsAvailable(ddmFormFieldOptions)) {
					return "select";
				}

				return "text";
			}
		}
	}

	private static LocalizedValue attributeToLabel(
		AttributeDefinition attributeDefinition, Locale locale) {

		LocalizedValue localizedValue = new LocalizedValue(locale);

		localizedValue.addString(locale, attributeDefinition.getName());

		return localizedValue;
	}

	private static LocalizedValue attributeToTip(
		AttributeDefinition attributeDefinition, Locale locale) {

		LocalizedValue localizedValue = new LocalizedValue(locale);

		localizedValue.addString(locale, attributeDefinition.getDescription());

		return localizedValue;
	}

	private static boolean _hasDDMFormFieldOptionsAvailable(
		DDMFormFieldOptions ddmFormFieldOptions) {

		Set<String> optionValues = ddmFormFieldOptions.getOptionsValues();

		if (optionValues.isEmpty()) {
			return false;
		}

		return true;
	}

	private static void _setDDMFormFieldOptions(
		String type, DDMFormField ddmFormField,
		DDMFormFieldOptions ddmFormFieldOptions) {

		ddmFormField.setType(type);

		if ("radio".equals(type)) {
			ddmFormField.setDataType(FieldConstants.BOOLEAN);
		}
		else {
			ddmFormField.setDataType(FieldConstants.STRING);
		}

		ddmFormField.setDDMFormFieldOptions(ddmFormFieldOptions);
	}

}