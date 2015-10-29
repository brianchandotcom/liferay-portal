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

package com.liferay.dynamic.data.mapping.util.impl;

import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldOptions;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.model.UnlocalizedValue;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.util.DDMBeanTranslator;

import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Leonardo Barros
 */
@Component(immediate = true)
public class DDMBeanTranslatorImpl implements DDMBeanTranslator {

	@Override
	public DDMForm translate(
		com.liferay.portlet.dynamicdatamapping.DDMForm ddmForm) {

		if (ddmForm == null) {
			return null;
		}

		DDMForm translatedDDMForm = new DDMForm();

		translatedDDMForm.setAvailableLocales(ddmForm.getAvailableLocales());
		translatedDDMForm.setDefaultLocale(ddmForm.getDefaultLocale());

		for (com.liferay.portlet.dynamicdatamapping.DDMFormField ddmFormField :
				ddmForm.getDDMFormFields()) {

			DDMFormField translatedDDMFormField = translate(ddmFormField);

			translatedDDMFormField.setDDMForm(translatedDDMForm);

			translatedDDMForm.addDDMFormField(translatedDDMFormField);
		}

		return translatedDDMForm;
	}

	@Override
	public DDMFormField translate(
		com.liferay.portlet.dynamicdatamapping.DDMFormField ddmFormField) {

		if (ddmFormField == null) {
			return null;
		}

		DDMFormField translatedDDMFormField = new DDMFormField(
			ddmFormField.getName(), ddmFormField.getType());

		translatedDDMFormField.setDataType(ddmFormField.getDataType());
		translatedDDMFormField.setFieldNamespace(
			ddmFormField.getFieldNamespace());
		translatedDDMFormField.setIndexType(ddmFormField.getIndexType());
		translatedDDMFormField.setDDMFormFieldOptions(
			translate(ddmFormField.getDDMFormFieldOptions()));
		translatedDDMFormField.setLabel(
			translateLocalizedValue(ddmFormField.getLabel()));
		translatedDDMFormField.setLocalizable(ddmFormField.isLocalizable());
		translatedDDMFormField.setMultiple(ddmFormField.isMultiple());
		translatedDDMFormField.setPredefinedValue(
			translateLocalizedValue(ddmFormField.getPredefinedValue()));
		translatedDDMFormField.setReadOnly(ddmFormField.isReadOnly());
		translatedDDMFormField.setRepeatable(ddmFormField.isRepeatable());
		translatedDDMFormField.setRequired(ddmFormField.isRequired());
		translatedDDMFormField.setShowLabel(ddmFormField.isShowLabel());
		translatedDDMFormField.setStyle(
			translateLocalizedValue(ddmFormField.getStyle()));
		translatedDDMFormField.setTip(
			translateLocalizedValue(ddmFormField.getTip()));

		for (com.liferay.portlet.dynamicdatamapping.DDMFormField
			nestedDDMFormField : ddmFormField.getNestedDDMFormFields()) {

			translatedDDMFormField.addNestedDDMFormField(
				translate(nestedDDMFormField));
		}

		return translatedDDMFormField;
	}

	@Override
	public DDMFormValues translate(
		com.liferay.portlet.dynamicdatamapping.DDMFormValues ddmFormValues) {

		if (ddmFormValues == null) {
			return null;
		}

		DDMFormValues translatedDDMFormValues = new DDMFormValues(
			translate(ddmFormValues.getDDMForm()));

		translatedDDMFormValues.setAvailableLocales(
			ddmFormValues.getAvailableLocales());
		translatedDDMFormValues.setDefaultLocale(
			ddmFormValues.getDefaultLocale());

		for (com.liferay.portlet.dynamicdatamapping.DDMFormFieldValue
			ddmFormFieldValue : ddmFormValues.getDDMFormFieldValues()) {

			translatedDDMFormValues.addDDMFormFieldValue(
				translate(ddmFormFieldValue));
		}

		return translatedDDMFormValues;
	}

	@Override
	public com.liferay.portlet.dynamicdatamapping.DDMForm translate(
		DDMForm ddmForm) {

		if (ddmForm == null) {
			return null;
		}

		com.liferay.portlet.dynamicdatamapping.DDMForm translatedDDMForm =
			new com.liferay.portlet.dynamicdatamapping.DDMForm();

		translatedDDMForm.setAvailableLocales(ddmForm.getAvailableLocales());
		translatedDDMForm.setDefaultLocale(ddmForm.getDefaultLocale());

		for (DDMFormField ddmFormField : ddmForm.getDDMFormFields()) {
			com.liferay.portlet.dynamicdatamapping.DDMFormField
				translatedDDMFormField = translate(ddmFormField);

			translatedDDMFormField.setDDMForm(translatedDDMForm);

			translatedDDMForm.addDDMFormField(translatedDDMFormField);
		}

		return translatedDDMForm;
	}

	@Override
	public com.liferay.portlet.dynamicdatamapping.DDMFormField
		translate(DDMFormField ddmFormField) {

		if (ddmFormField == null) {
			return null;
		}

		com.liferay.portlet.dynamicdatamapping.DDMFormField
			translatedDDMFormField =
				new com.liferay.portlet.dynamicdatamapping.DDMFormField(
					ddmFormField.getName(), ddmFormField.getType());

		translatedDDMFormField.setDataType(ddmFormField.getDataType());
		translatedDDMFormField.setFieldNamespace(
			ddmFormField.getFieldNamespace());
		translatedDDMFormField.setIndexType(ddmFormField.getIndexType());
		translatedDDMFormField.setDDMFormFieldOptions(
			translate(ddmFormField.getDDMFormFieldOptions()));
		translatedDDMFormField.setLabel(
			translateLocalizedValue(ddmFormField.getLabel()));
		translatedDDMFormField.setLocalizable(ddmFormField.isLocalizable());
		translatedDDMFormField.setMultiple(ddmFormField.isMultiple());
		translatedDDMFormField.setPredefinedValue(
			translateLocalizedValue(ddmFormField.getPredefinedValue()));
		translatedDDMFormField.setReadOnly(ddmFormField.isReadOnly());
		translatedDDMFormField.setRepeatable(ddmFormField.isRepeatable());
		translatedDDMFormField.setRequired(ddmFormField.isRequired());
		translatedDDMFormField.setShowLabel(ddmFormField.isShowLabel());
		translatedDDMFormField.setStyle(
			translateLocalizedValue(ddmFormField.getStyle()));
		translatedDDMFormField.setTip(
			translateLocalizedValue(ddmFormField.getTip()));

		for (DDMFormField nestedDDMFormField :
				ddmFormField.getNestedDDMFormFields()) {

			translatedDDMFormField.addNestedDDMFormField(
				translate(nestedDDMFormField));
		}

		return translatedDDMFormField;
	}

	@Override
	public com.liferay.portlet.dynamicdatamapping.DDMFormValues translate(
		DDMFormValues ddmFormValues) {

		if (ddmFormValues == null) {
			return null;
		}

		com.liferay.portlet.dynamicdatamapping.DDMForm translatedDDMForm =
			translate(ddmFormValues.getDDMForm());

		com.liferay.portlet.dynamicdatamapping.DDMFormValues
			translatedDDMFormValues =
				new com.liferay.portlet.dynamicdatamapping.DDMFormValues(
					translatedDDMForm);

		translatedDDMFormValues.setAvailableLocales(
			ddmFormValues.getAvailableLocales());
		translatedDDMFormValues.setDefaultLocale(
			ddmFormValues.getDefaultLocale());

		for (DDMFormFieldValue ddmFormFieldValue :
				ddmFormValues.getDDMFormFieldValues()) {

			translatedDDMFormValues.addDDMFormFieldValue(
				translate(ddmFormFieldValue));
		}

		return translatedDDMFormValues;
	}

	protected DDMFormFieldOptions translate(
		com.liferay.portlet.dynamicdatamapping.DDMFormFieldOptions
			ddmFormFieldOptions) {

		if (ddmFormFieldOptions == null) {
			return null;
		}

		DDMFormFieldOptions translatedDDMFormFieldOptions =
			new DDMFormFieldOptions();

		translatedDDMFormFieldOptions.setDefaultLocale(
			ddmFormFieldOptions.getDefaultLocale());

		Map<String, com.liferay.portlet.dynamicdatamapping.LocalizedValue>
			options = ddmFormFieldOptions.getOptions();

		for (String optionValue : options.keySet()) {
			com.liferay.portlet.dynamicdatamapping.LocalizedValue
				localizedValue = options.get(optionValue);

			for (Locale locale : localizedValue.getAvailableLocales()) {
				translatedDDMFormFieldOptions.addOptionLabel(
					optionValue, locale, localizedValue.getString(locale));
			}
		}

		return translatedDDMFormFieldOptions;
	}

	protected DDMFormFieldValue translate(
		com.liferay.portlet.dynamicdatamapping.DDMFormFieldValue
			ddmFormFieldValue) {

		if (ddmFormFieldValue == null) {
			return null;
		}

		DDMFormFieldValue translatedDDMFormFieldValue = new DDMFormFieldValue();

		translatedDDMFormFieldValue.setInstanceId(
			ddmFormFieldValue.getInstanceId());
		translatedDDMFormFieldValue.setName(ddmFormFieldValue.getName());
		translatedDDMFormFieldValue.setValue(
			translate(ddmFormFieldValue.getValue()));

		for (com.liferay.portlet.dynamicdatamapping.DDMFormFieldValue
			nestedFormFieldValue :
				ddmFormFieldValue.getNestedDDMFormFieldValues()) {

			translatedDDMFormFieldValue.addNestedDDMFormFieldValue(
				translate(nestedFormFieldValue));
		}

		return translatedDDMFormFieldValue;
	}

	protected Value translate(
		com.liferay.portlet.dynamicdatamapping.Value value) {

		if (value == null) {
			return null;
		}

		if (value.isLocalized()) {
			return translateLocalizedValue(value);
		}
		else {
			return translateUnlocalizedValue(value);
		}
	}

	protected com.liferay.portlet.dynamicdatamapping.DDMFormFieldOptions
		translate(DDMFormFieldOptions ddmFormFieldOptions) {

		if (ddmFormFieldOptions == null) {
			return null;
		}

		com.liferay.portlet.dynamicdatamapping.DDMFormFieldOptions
			translatedDDMFormFieldOptions =
				new com.liferay.portlet.dynamicdatamapping.DDMFormFieldOptions();

		translatedDDMFormFieldOptions.setDefaultLocale(
			ddmFormFieldOptions.getDefaultLocale());

		Map<String, LocalizedValue> options = ddmFormFieldOptions.getOptions();

		for (String optionValue : options.keySet()) {
			LocalizedValue localizedValue = options.get(optionValue);

			for (Locale locale : localizedValue.getAvailableLocales()) {
				translatedDDMFormFieldOptions.addOptionLabel(
					optionValue, locale, localizedValue.getString(locale));
			}
		}

		return translatedDDMFormFieldOptions;
	}

	protected com.liferay.portlet.dynamicdatamapping.DDMFormFieldValue
		translate(
			DDMFormFieldValue ddmFormFieldValue) {

		if (ddmFormFieldValue == null) {
			return null;
		}

		com.liferay.portlet.dynamicdatamapping.DDMFormFieldValue
			translatedDDMFormFieldValue =
				new com.liferay.portlet.dynamicdatamapping.DDMFormFieldValue();

		translatedDDMFormFieldValue.setInstanceId(
			ddmFormFieldValue.getInstanceId());
		translatedDDMFormFieldValue.setName(ddmFormFieldValue.getName());
		translatedDDMFormFieldValue.setValue(
			translate(ddmFormFieldValue.getValue()));

		for (DDMFormFieldValue nestedFormFieldValue :
				ddmFormFieldValue.getNestedDDMFormFieldValues()) {

			translatedDDMFormFieldValue.addNestedDDMFormFieldValue(
				translate(nestedFormFieldValue));
		}

		return translatedDDMFormFieldValue;
	}

	protected com.liferay.portlet.dynamicdatamapping.Value translate(
		Value value) {

		if (value == null) {
			return null;
		}

		if (value.isLocalized()) {
			return translateLocalizedValue(value);
		}
		else {
			return translateUnlocalizedValue(value);
		}
	}

	protected LocalizedValue translateLocalizedValue(
		com.liferay.portlet.dynamicdatamapping.Value value) {

		if (value == null) {
			return null;
		}

		LocalizedValue translatedLocalizedValue = new LocalizedValue();

		translatedLocalizedValue.setDefaultLocale(value.getDefaultLocale());

		Map<Locale, String> values = value.getValues();

		for (Locale locale : values.keySet()) {
			translatedLocalizedValue.addString(locale, values.get(locale));
		}

		return translatedLocalizedValue;
	}

	protected com.liferay.portlet.dynamicdatamapping.LocalizedValue
		translateLocalizedValue(Value value) {

		if (value == null) {
			return null;
		}

		com.liferay.portlet.dynamicdatamapping.LocalizedValue
			translatedLocalizedValue =
				new com.liferay.portlet.dynamicdatamapping.LocalizedValue();

		translatedLocalizedValue.setDefaultLocale(value.getDefaultLocale());

		Map<Locale, String> values = value.getValues();

		for (Locale locale : values.keySet()) {
			translatedLocalizedValue.addString(locale, values.get(locale));
		}

		return translatedLocalizedValue;
	}

	protected UnlocalizedValue translateUnlocalizedValue(
		com.liferay.portlet.dynamicdatamapping.Value value) {

		if (value == null) {
			return null;
		}

		return new UnlocalizedValue(value.getString(value.getDefaultLocale()));
	}

	protected com.liferay.portlet.dynamicdatamapping.UnlocalizedValue
		translateUnlocalizedValue(Value value) {

		if (value == null) {
			return null;
		}

		return new com.liferay.portlet.dynamicdatamapping.UnlocalizedValue(
			value.getString(value.getDefaultLocale()));
	}

}