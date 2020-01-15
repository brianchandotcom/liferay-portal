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

import com.liferay.configuration.admin.definition.ConfigurationFieldOptionsProvider;
import com.liferay.configuration.admin.web.internal.model.ConfigurationModel;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldOptions;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldType;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.storage.FieldConstants;
import com.liferay.dynamic.data.mapping.util.DDMFormFactory;
import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.configuration.metatype.definitions.ExtendedAttributeDefinition;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.SetUtil;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.function.Predicate;

import org.osgi.service.metatype.AttributeDefinition;
import org.osgi.service.metatype.ObjectClassDefinition;

/**
 * @author Kamesh Sampath
 * @author Raymond Augé
 * @author Marcellus Tavares
 */
public class ConfigurationModelToDDMFormConverter {

	public ConfigurationModelToDDMFormConverter(
		ConfigurationModel configurationModel, Locale locale,
		ResourceBundle resourceBundle) {

		_configurationModel = configurationModel;
		_locale = locale;
		_resourceBundle = resourceBundle;
	}

	public DDMForm getDDMForm() {
		DDMForm ddmForm = getConfigurationDDMForm();

		if (ddmForm == null) {
			ddmForm = new DDMForm();
		}

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

		Map<String, DDMFormField> ddmFormFieldsMap =
			ddmForm.getDDMFormFieldsMap(false);

		for (AttributeDefinition attributeDefinition : attributeDefinitions) {
			if (!ddmFormFieldsMap.containsKey(attributeDefinition.getID())) {
				DDMFormField ddmFormField = getDDMFormField(
					attributeDefinition, required);

				ddmForm.addDDMFormField(ddmFormField);
			}
		}
	}

	protected void addOptionalDDMFormFields(DDMForm ddmForm) {
		AttributeDefinition[] optionalAttributeDefinitions = ArrayUtil.filter(
			_configurationModel.getAttributeDefinitions(
				ObjectClassDefinition.OPTIONAL),
			_requiredInputPredicate.negate());

		addDDMFormFields(optionalAttributeDefinitions, ddmForm, false);
	}

	protected void addRequiredDDMFormFields(DDMForm ddmForm) {
		AttributeDefinition[] requiredAttributeDefinitions = ArrayUtil.append(
			_configurationModel.getAttributeDefinitions(
				ObjectClassDefinition.REQUIRED),
			ArrayUtil.filter(
				_configurationModel.getAttributeDefinitions(
					ObjectClassDefinition.OPTIONAL),
				_requiredInputPredicate));

		addDDMFormFields(requiredAttributeDefinitions, ddmForm, true);
	}

	protected DDMForm getConfigurationDDMForm() {
		Class<?> formClass =
			ConfigurationDDMFormDeclarationUtil.getConfigurationDDMFormClass(
				_configurationModel);

		if (formClass != null) {
			try {
				return DDMFormFactory.create(formClass);
			}
			catch (IllegalArgumentException illegalArgumentException) {
			}
		}

		return null;
	}

	protected DDMFormFieldOptions getDDMFieldOptions(
		AttributeDefinition attributeDefinition) {

		String pid = _configurationModel.getID();

		if (_configurationModel.isFactory()) {
			pid = _configurationModel.getFactoryPid();
		}

		DDMFormFieldOptions ddmFormFieldOptions = new DDMFormFieldOptions();

		ConfigurationFieldOptionsProvider configurationFieldOptionsProvider =
			ConfigurationFieldOptionsProviderUtil.
				getConfigurationFieldOptionsProvider(
					pid, attributeDefinition.getID());

		if (configurationFieldOptionsProvider != null) {
			for (ConfigurationFieldOptionsProvider.Option option :
					configurationFieldOptionsProvider.getOptions()) {

				ddmFormFieldOptions.addOptionLabel(
					option.getValue(), _locale, option.getLabel(_locale));
			}

			return ddmFormFieldOptions;
		}

		String[] optionLabels = attributeDefinition.getOptionLabels();
		String[] optionValues = attributeDefinition.getOptionValues();

		if ((optionLabels == null) || (optionValues == null)) {
			return ddmFormFieldOptions;
		}

		for (int i = 0; i < optionLabels.length; i++) {
			ddmFormFieldOptions.addOptionLabel(
				optionValues[i], _locale, translate(optionLabels[i]));
		}

		return ddmFormFieldOptions;
	}

	protected DDMFormField getDDMFormField(
		AttributeDefinition attributeDefinition, boolean required) {

		DDMFormFieldOptions ddmFormFieldOptions = getDDMFieldOptions(
			attributeDefinition);

		String type = getDDMFormFieldType(
			attributeDefinition, ddmFormFieldOptions);

		DDMFormField ddmFormField = new DDMFormField(
			attributeDefinition.getID(), type);

		setDDMFormFieldDataType(attributeDefinition, ddmFormField);
		setDDMFormFieldLabel(attributeDefinition, ddmFormField);
		setDDMFormFieldOptions(ddmFormField, ddmFormFieldOptions);
		setDDMFormFieldPredefinedValue(attributeDefinition, ddmFormField);
		setDDMFormFieldRequired(attributeDefinition, ddmFormField, required);
		setDDMFormFieldTip(attributeDefinition, ddmFormField);

		ddmFormField.setLocalizable(true);
		ddmFormField.setShowLabel(true);

		setDDMFormFieldRepeatable(attributeDefinition, ddmFormField);

		setDDMFormFieldDisplayStyle(ddmFormField);

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

	protected String getDDMFormFieldPredefinedValue(
		AttributeDefinition attributeDefinition) {

		String dataType = getDDMFormFieldDataType(attributeDefinition);

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

	protected String getDDMFormFieldType(
		AttributeDefinition attributeDefinition,
		DDMFormFieldOptions ddmFormFieldOptions) {

		int type = attributeDefinition.getType();

		if (type == AttributeDefinition.BOOLEAN) {
			if (SetUtil.isEmpty(ddmFormFieldOptions.getOptionsValues())) {
				return DDMFormFieldType.CHECKBOX;
			}

			return DDMFormFieldType.RADIO;
		}
		else if (type == AttributeDefinition.PASSWORD) {
			return DDMFormFieldType.PASSWORD;
		}

		if (!SetUtil.isEmpty(ddmFormFieldOptions.getOptionsValues())) {
			return DDMFormFieldType.SELECT;
		}

		return DDMFormFieldType.TEXT;
	}

	protected void setDDMFormFieldDataType(
		AttributeDefinition attributeDefinition, DDMFormField ddmFormField) {

		String dataType = getDDMFormFieldDataType(attributeDefinition);

		ddmFormField.setDataType(dataType);
	}

	protected void setDDMFormFieldDisplayStyle(DDMFormField ddmFormField) {
		if (Objects.equals(ddmFormField.getDataType(), FieldConstants.STRING)) {
			ddmFormField.setProperty("displayStyle", "multiline");
		}
	}

	protected void setDDMFormFieldLabel(
		AttributeDefinition attributeDefinition, DDMFormField ddmFormField) {

		LocalizedValue label = new LocalizedValue(_locale);

		Map<String, String> extensionAttributes = _getExtensionAttributes(
			attributeDefinition);

		List<String> nameArguments = StringUtil.split(
			extensionAttributes.get("name-arguments"));

		label.addString(
			_locale, translate(attributeDefinition.getName(), nameArguments));

		ddmFormField.setLabel(label);
	}

	protected void setDDMFormFieldOptions(
		DDMFormField ddmFormField, DDMFormFieldOptions ddmFormFieldOptions) {

		ddmFormField.setDDMFormFieldOptions(ddmFormFieldOptions);
	}

	protected void setDDMFormFieldPredefinedValue(
		AttributeDefinition attributeDefinition, DDMFormField ddmFormField) {

		String type = ddmFormField.getType();

		String predefinedValueString = getDDMFormFieldPredefinedValue(
			attributeDefinition);

		if (type.equals(DDMFormFieldType.SELECT)) {
			predefinedValueString = "[\"" + predefinedValueString + "\"]";
		}

		LocalizedValue predefinedValue = new LocalizedValue(_locale);

		predefinedValue.addString(_locale, predefinedValueString);

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

		Map<String, String> extensionAttributes = _getExtensionAttributes(
			attributeDefinition);

		List<String> descriptionArguments = StringUtil.split(
			extensionAttributes.get("description-arguments"));

		tip.addString(
			_locale,
			translate(
				attributeDefinition.getDescription(), descriptionArguments));

		ddmFormField.setTip(tip);
	}

	protected String translate(String key) {
		return translate(key, Collections.emptyList());
	}

	protected String translate(String key, List<String> arguments) {
		if ((_resourceBundle == null) || (key == null)) {
			return key;
		}

		String value = null;

		if (ListUtil.isEmpty(arguments)) {
			value = LanguageUtil.get(_resourceBundle, key);
		}
		else {
			value = LanguageUtil.format(
				_resourceBundle, key, arguments.toArray(new String[0]));
		}

		if (value == null) {
			return key;
		}

		return value;
	}

	private Map<String, String> _getExtensionAttributes(
		AttributeDefinition attributeDefinition) {

		ExtendedAttributeDefinition extendedAttributeDefinition =
			_configurationModel.getExtendedAttributeDefinition(
				attributeDefinition.getID());

		return extendedAttributeDefinition.getExtensionAttributes(
			com.liferay.portal.configuration.metatype.annotations.
				ExtendedAttributeDefinition.XML_NAMESPACE);
	}

	private final ConfigurationModel _configurationModel;
	private final Locale _locale;

	private Predicate<AttributeDefinition> _requiredInputPredicate =
		attributeDefinition -> {
			Map<String, String> extensionAttributes = _getExtensionAttributes(
				attributeDefinition);

			return Boolean.valueOf(extensionAttributes.get("required-input"));
		};

	private final ResourceBundle _resourceBundle;

}