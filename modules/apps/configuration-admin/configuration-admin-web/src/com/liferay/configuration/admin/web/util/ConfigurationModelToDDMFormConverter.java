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
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMForm;
import com.liferay.portlet.dynamicdatamapping.model.DDMFormField;
import com.liferay.portlet.dynamicdatamapping.model.DDMFormFieldOptions;
import com.liferay.portlet.dynamicdatamapping.model.DDMFormFieldType;
import com.liferay.portlet.dynamicdatamapping.model.LocalizedValue;
import com.liferay.portlet.dynamicdatamapping.storage.FieldConstants;

import java.util.Dictionary;
import java.util.Locale;

import org.osgi.service.cm.Configuration;
import org.osgi.service.metatype.AttributeDefinition;
import org.osgi.service.metatype.ObjectClassDefinition;

/**
 * @author Kamesh Sampath
 * @author Raymond Augé
 * @author Marcellus Tavares
 */
public class ConfigurationModelToDDMFormConverter {

	public ConfigurationModelToDDMFormConverter(
		ConfigurationModel configurationModel, Locale locale) {

		_configurationModel = configurationModel;
		_locale = locale;
	}

	public DDMForm getDDMForm() {
		DDMForm ddmForm = new DDMForm();

		ddmForm.addAvailableLocale(_locale);
		ddmForm.setDefaultLocale(_locale);

		addRequiredDDMFormFields(ddmForm);
		addOptionalDDMFormFields(ddmForm);

		return ddmForm;
	}

	protected void addDDMFormFields(
		AttributeDefinition[] attributeDefinitions, DDMForm ddmForm,
		boolean required) {

		if (attributeDefinitions == null) {
			return;
		}

		for (AttributeDefinition attributeDefinition : attributeDefinitions) {
			DDMFormField ddmFormField = getDDMFormField(
				attributeDefinition, required);

			ddmForm.addDDMFormField(ddmFormField);
		}
	}

	protected void addOptionalDDMFormFields(DDMForm ddmForm) {
		AttributeDefinition[] optionalAttributeDefinitions =
			_configurationModel.getAttributeDefinitions(
				ObjectClassDefinition.OPTIONAL);

		addDDMFormFields(optionalAttributeDefinitions, ddmForm, false);
	}

	protected void addRequiredDDMFormFields(DDMForm ddmForm) {
		AttributeDefinition[] requiredAttributeDefinitions =
			_configurationModel.getAttributeDefinitions(
				ObjectClassDefinition.REQUIRED);

		addDDMFormFields(requiredAttributeDefinitions, ddmForm, true);
	}

	protected DDMFormFieldOptions getDDMFieldOptions(
		AttributeDefinition attributeDefinition) {

		DDMFormFieldOptions ddmFormFieldOptions = new DDMFormFieldOptions();

		String[] optionLabels = attributeDefinition.getOptionLabels();
		String[] optionValues = attributeDefinition.getOptionValues();

		if ((optionLabels == null) || (optionValues == null)) {
			return ddmFormFieldOptions;
		}

		for (int i = 0; i < optionLabels.length; i++) {
			ddmFormFieldOptions.addOptionLabel(
				optionValues[i], _locale, optionLabels[i]);
		}

		return ddmFormFieldOptions;
	}

	protected DDMFormField getDDMFormField(
		AttributeDefinition attributeDefinition, boolean required) {

		String type = getDDMFormFieldType(attributeDefinition);

		DDMFormField ddmFormField = new DDMFormField(
			attributeDefinition.getID(), type);

		setDDMFormFieldDataType(attributeDefinition, ddmFormField);
		setDDMFormFieldLabel(attributeDefinition, ddmFormField);
		setDDMFormFieldOptions(attributeDefinition, ddmFormField);
		setDDMFormFieldPredefinedValue(attributeDefinition, ddmFormField);
		setDDMFormFieldRequired(attributeDefinition, ddmFormField, required);
		setDDMFormFieldTip(attributeDefinition, ddmFormField);

		ddmFormField.setLocalizable(true);
		ddmFormField.setShowLabel(true);

		setDDMFormFieldRepeatable(attributeDefinition, ddmFormField);

		return ddmFormField;
	}

	protected String getDDMFormFieldDataType(
		AttributeDefinition attributeDefinition) {

		int type = attributeDefinition.getType();

		if (type == AttributeDefinition.BOOLEAN) {
			return FieldConstants.BOOLEAN;
		}
		else if (type == AttributeDefinition.DOUBLE) {
			return FieldConstants.DOUBLE;
		}
		else if (type == AttributeDefinition.FLOAT) {
			return FieldConstants.FLOAT;
		}
		else if (type == AttributeDefinition.INTEGER) {
			return FieldConstants.INTEGER;
		}
		else if (type == AttributeDefinition.LONG) {
			return FieldConstants.LONG;
		}
		else if (type == AttributeDefinition.SHORT) {
			return FieldConstants.SHORT;
		}

		return FieldConstants.STRING;
	}

	protected String getDDMFormFieldType(
		AttributeDefinition attributeDefinition) {

		int type = attributeDefinition.getType();

		if (type == AttributeDefinition.BOOLEAN) {
			String[] optionLabels = attributeDefinition.getOptionLabels();

			if (ArrayUtil.isEmpty(optionLabels)) {
				return DDMFormFieldType.CHECKBOX;
			}

			return DDMFormFieldType.RADIO;
		}

		if ((type == AttributeDefinition.INTEGER) ||
			(type == AttributeDefinition.LONG) ||
			(type == AttributeDefinition.SHORT)) {

			return DDMFormFieldType.INTEGER;
		}

		if ((type == AttributeDefinition.DOUBLE) ||
			(type == AttributeDefinition.FLOAT)) {

			return DDMFormFieldType.DECIMAL;
		}

		if (ArrayUtil.isNotEmpty(attributeDefinition.getOptionLabels()) ||
			ArrayUtil.isNotEmpty(attributeDefinition.getOptionValues())) {

			return DDMFormFieldType.SELECT;
		}

		return DDMFormFieldType.TEXT;
	}

	protected void setDDMFormFieldDataType(
		AttributeDefinition attributeDefinition, DDMFormField ddmFormField) {

		String dataType = getDDMFormFieldDataType(attributeDefinition);

		ddmFormField.setDataType(dataType);
	}

	protected void setDDMFormFieldLabel(
		AttributeDefinition attributeDefinition, DDMFormField ddmFormField) {

		LocalizedValue label = new LocalizedValue(_locale);

		label.addString(_locale, attributeDefinition.getName());

		ddmFormField.setLabel(label);
	}

	protected void setDDMFormFieldOptions(
		AttributeDefinition attributeDefinition, DDMFormField ddmFormField) {

		DDMFormFieldOptions ddmFormFieldOptions = getDDMFieldOptions(
			attributeDefinition);

		ddmFormField.setDDMFormFieldOptions(ddmFormFieldOptions);
	}

	protected void setDDMFormFieldPredefinedValue(
		AttributeDefinition attributeDefinition, DDMFormField ddmFormField) {

		LocalizedValue predefinedValue = new LocalizedValue(_locale);

		Configuration configuration = _configurationModel.getConfiguration();

		if (configuration != null) {
			Dictionary<String, Object> properties =
				configuration.getProperties();

			if (properties != null) {
				String attributeDefinitionID = attributeDefinition.getID();

				String propertyValue = String.valueOf(
					properties.get(attributeDefinitionID));

				predefinedValue.addString(_locale, propertyValue);
			}
		}
		else {
			String[] defaultValues = attributeDefinition.getDefaultValue();

			if (!ArrayUtil.isEmpty(defaultValues)) {
				defaultValues = StringUtil.split(
					attributeDefinition.getDefaultValue()[0], StringPool.PIPE);

				for (String defaultValue : defaultValues) {
					predefinedValue.addString(_locale, defaultValue);
				}
			}
		}

		ddmFormField.setPredefinedValue(predefinedValue);
	}

	protected void setDDMFormFieldRepeatable(
		AttributeDefinition attributeDefinition, DDMFormField ddmFormField) {

		if (attributeDefinition.getCardinality() == 0) {
			return;
		}

		ddmFormField.setRepeatable(true);
	}

	protected void setDDMFormFieldRequired(
		AttributeDefinition attributeDefinition, DDMFormField ddmFormField,
		boolean required) {

		if (DDMFormFieldType.CHECKBOX.equals(ddmFormField.getType())) {
			return;
		}

		ddmFormField.setRequired(required);
	}

	protected void setDDMFormFieldTip(
		AttributeDefinition attributeDefinition, DDMFormField ddmFormField) {

		LocalizedValue tip = new LocalizedValue(_locale);

		tip.addString(_locale, attributeDefinition.getDescription());

		ddmFormField.setTip(tip);
	}

	private final ConfigurationModel _configurationModel;
	private final Locale _locale;

}