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

package com.liferay.dynamic.data.mapping.util;

import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldOptions;
import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutColumn;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutPage;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutRow;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.model.UnlocalizedValue;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;

import java.util.Locale;
import java.util.Map;

/**
 * @author Leonardo Barros
 */
public class DDMBeanCopyUtil {

	public static DDMForm copyDDMForm(
		com.liferay.portlet.dynamicdatamapping.model.DDMForm ddmForm) {

		if (ddmForm == null) {
			return null;
		}

		DDMForm form = new DDMForm();

		form.setAvailableLocales(ddmForm.getAvailableLocales());
		form.setDefaultLocale(ddmForm.getDefaultLocale());

		for (com.liferay.portlet.dynamicdatamapping.model.DDMFormField
			ddmFormField : ddmForm.getDDMFormFields()) {

			DDMFormField formField = copyDDMFormField(ddmFormField, form);
			form.addDDMFormField(formField);
		}

		return form;
	}

	public static com.liferay.portlet.dynamicdatamapping.model.DDMForm
		copyDDMForm(DDMForm ddmForm) {

		if (ddmForm == null) {
			return null;
		}

		com.liferay.portlet.dynamicdatamapping.model.DDMForm form =
			new com.liferay.portlet.dynamicdatamapping.model.DDMForm();

		form.setAvailableLocales(ddmForm.getAvailableLocales());
		form.setDefaultLocale(ddmForm.getDefaultLocale());

		for (DDMFormField ddmFormField : ddmForm.getDDMFormFields()) {
			com.liferay.portlet.dynamicdatamapping.model.DDMFormField
				formField = copyDDMFormField(ddmFormField, form);
			form.addDDMFormField(formField);
		}

		return form;
	}

	public static com.liferay.portlet.dynamicdatamapping.model.DDMFormField
		copyDDMFormField(
			DDMFormField ddmFormField,
			com.liferay.portlet.dynamicdatamapping.model.DDMForm ddmForm) {

		if (ddmFormField == null) {
			return null;
		}

		com.liferay.portlet.dynamicdatamapping.model.DDMFormField formField =
			new com.liferay.portlet.dynamicdatamapping.model.DDMFormField(
				ddmFormField.getName(), ddmFormField.getType());
		formField.setDataType(ddmFormField.getDataType());
		formField.setFieldNamespace(ddmFormField.getFieldNamespace());
		formField.setIndexType(ddmFormField.getIndexType());
		formField.setDDMFormFieldOptions(
			copyDDMFormFieldOptions(ddmFormField.getDDMFormFieldOptions()));
		formField.setLabel(copyLocalizedValue(ddmFormField.getLabel()));
		formField.setLocalizable(ddmFormField.isLocalizable());
		formField.setMultiple(ddmFormField.isMultiple());
		formField.setPredefinedValue(
			copyLocalizedValue(ddmFormField.getPredefinedValue()));
		formField.setReadOnly(ddmFormField.isReadOnly());
		formField.setRepeatable(ddmFormField.isRepeatable());
		formField.setRequired(ddmFormField.isRequired());
		formField.setShowLabel(ddmFormField.isShowLabel());
		formField.setStyle(copyLocalizedValue(ddmFormField.getStyle()));
		formField.setTip(copyLocalizedValue(ddmFormField.getTip()));
		formField.setVisibilityExpression(
			ddmFormField.getVisibilityExpression());

		for (DDMFormField nestedDDMFormField :
				ddmFormField.getNestedDDMFormFields()) {

			formField.addNestedDDMFormField(
				copyDDMFormField(nestedDDMFormField, ddmForm));
		}

		formField.setDDMForm(ddmForm);

		return formField;
	}

	public static DDMFormFieldValue copyDDMFormFieldValue(
		com.liferay.portlet.dynamicdatamapping.storage.DDMFormFieldValue
			ddmFormFieldValue) {

		if (ddmFormFieldValue == null) {
			return null;
		}

		DDMFormFieldValue formFieldValue = new DDMFormFieldValue();
		formFieldValue.setInstanceId(ddmFormFieldValue.getInstanceId());
		formFieldValue.setName(ddmFormFieldValue.getName());
		formFieldValue.setValue(copyValue(ddmFormFieldValue.getValue()));

		for (com.liferay.portlet.dynamicdatamapping.storage.DDMFormFieldValue
			nestedFormFieldValue :
				ddmFormFieldValue.getNestedDDMFormFieldValues()) {

			formFieldValue.addNestedDDMFormFieldValue(
				copyDDMFormFieldValue(nestedFormFieldValue));
		}

		return formFieldValue;
	}

	public static DDMFormLayout copyDDMFormLayout(
		com.liferay.portlet.dynamicdatamapping.model.DDMFormLayout
			ddmFormLayout) {

		if (ddmFormLayout == null) {
			return null;
		}

		DDMFormLayout formLayout = new DDMFormLayout();
		formLayout.setDefaultLocale(ddmFormLayout.getDefaultLocale());

		for (com.liferay.portlet.dynamicdatamapping.model.DDMFormLayoutPage
			ddmFormLayoutPage : ddmFormLayout.getDDMFormLayoutPages()) {

			formLayout.addDDMFormLayoutPage(
				copyDDMFormLayoutPage(ddmFormLayoutPage));
		}

		return formLayout;
	}

	public static com.liferay.portlet.dynamicdatamapping.model.DDMFormLayout
		copyDDMFormLayout(DDMFormLayout ddmFormLayout) {

		if (ddmFormLayout == null) {
			return null;
		}

		com.liferay.portlet.dynamicdatamapping.model.DDMFormLayout formLayout =
			new com.liferay.portlet.dynamicdatamapping.model.DDMFormLayout();
		formLayout.setDefaultLocale(ddmFormLayout.getDefaultLocale());

		for (DDMFormLayoutPage ddmFormLayoutPage :
				ddmFormLayout.getDDMFormLayoutPages()) {

			formLayout.addDDMFormLayoutPage(
				copyDDMFormLayoutPage(ddmFormLayoutPage));
		}

		return formLayout;
	}

	public static DDMFormValues copyDDMFormValues(
		com.liferay.portlet.dynamicdatamapping.storage.DDMFormValues
			ddmFormValues) {

		if (ddmFormValues == null) {
			return null;
		}

		DDMFormValues formValues = new DDMFormValues(
			copyDDMForm(ddmFormValues.getDDMForm()));
		formValues.setAvailableLocales(ddmFormValues.getAvailableLocales());
		formValues.setDefaultLocale(ddmFormValues.getDefaultLocale());

		for (com.liferay.portlet.dynamicdatamapping.storage.DDMFormFieldValue
			ddmFormFieldValue : ddmFormValues.getDDMFormFieldValues()) {

			formValues.addDDMFormFieldValue(
				copyDDMFormFieldValue(ddmFormFieldValue));
		}

		return formValues;
	}

	public static com.liferay.portlet.dynamicdatamapping.storage.DDMFormValues
		copyDDMFormValues(DDMFormValues ddmFormValues) {

		if (ddmFormValues == null) {
			return null;
		}

		com.liferay.portlet.dynamicdatamapping.storage.DDMFormValues
			formValues =
		new com.liferay.portlet.dynamicdatamapping.storage.DDMFormValues(
			copyDDMForm(ddmFormValues.getDDMForm()));

		formValues.setAvailableLocales(ddmFormValues.getAvailableLocales());
		formValues.setDefaultLocale(ddmFormValues.getDefaultLocale());

		for (DDMFormFieldValue ddmFormFieldValue :
				ddmFormValues.getDDMFormFieldValues()) {

			formValues.addDDMFormFieldValue(
				copyDDMFormFieldValue(ddmFormFieldValue));
		}

		return formValues;
	}

	private static DDMFormField copyDDMFormField(
		com.liferay.portlet.dynamicdatamapping.model.DDMFormField
			ddmFormField, DDMForm ddmForm) {

		if (ddmFormField == null) {
			return null;
		}

		DDMFormField formField = new DDMFormField(
			ddmFormField.getName(), ddmFormField.getType());
		formField.setDataType(ddmFormField.getDataType());
		formField.setFieldNamespace(ddmFormField.getFieldNamespace());
		formField.setIndexType(ddmFormField.getIndexType());
		formField.setDDMFormFieldOptions(
			copyDDMFormFieldOptions(ddmFormField.getDDMFormFieldOptions()));
		formField.setLabel(copyLocalizedValue(ddmFormField.getLabel()));
		formField.setLocalizable(ddmFormField.isLocalizable());
		formField.setMultiple(ddmFormField.isMultiple());
		formField.setPredefinedValue(
			copyLocalizedValue(ddmFormField.getPredefinedValue()));
		formField.setReadOnly(ddmFormField.isReadOnly());
		formField.setRepeatable(ddmFormField.isRepeatable());
		formField.setRequired(ddmFormField.isRequired());
		formField.setShowLabel(ddmFormField.isShowLabel());
		formField.setStyle(copyLocalizedValue(ddmFormField.getStyle()));
		formField.setTip(copyLocalizedValue(ddmFormField.getTip()));
		formField.setVisibilityExpression(
			ddmFormField.getVisibilityExpression());

		for (com.liferay.portlet.dynamicdatamapping.model.DDMFormField
			nestedDDMFormField : ddmFormField.getNestedDDMFormFields()) {

			formField.addNestedDDMFormField(
				copyDDMFormField(nestedDDMFormField, ddmForm));
		}

		formField.setDDMForm(ddmForm);

		return formField;
	}

	private static DDMFormFieldOptions copyDDMFormFieldOptions(
		com.liferay.portlet.dynamicdatamapping.model.DDMFormFieldOptions
			ddmFormFieldOptions) {

		if (ddmFormFieldOptions == null) {
			return null;
		}

		DDMFormFieldOptions formFieldOptions = new DDMFormFieldOptions();

		formFieldOptions.setDefaultLocale(
			ddmFormFieldOptions.getDefaultLocale());

		Map<String, com.liferay.portlet.dynamicdatamapping.model.LocalizedValue>
			options = ddmFormFieldOptions.getOptions();

		for (String optionValue : options.keySet()) {
			com.liferay.portlet.dynamicdatamapping.model.LocalizedValue
				localizedValue = options.get(optionValue);

			for (Locale locale : localizedValue.getAvailableLocales()) {
				formFieldOptions.addOptionLabel(
					optionValue, locale, localizedValue.getString(locale));
			}
		}

		return formFieldOptions;
	}

	private static
		com.liferay.portlet.dynamicdatamapping.model.DDMFormFieldOptions
			copyDDMFormFieldOptions(
				DDMFormFieldOptions ddmFormFieldOptions) {

		if (ddmFormFieldOptions == null) {
			return null;
		}

		com.liferay.portlet.dynamicdatamapping.model.DDMFormFieldOptions
			formFieldOptions =
		new com.liferay.portlet.dynamicdatamapping.model.DDMFormFieldOptions();

		formFieldOptions.setDefaultLocale(
			ddmFormFieldOptions.getDefaultLocale());

		Map<String, LocalizedValue> options = ddmFormFieldOptions.getOptions();

		for (String optionValue : options.keySet()) {
			LocalizedValue localizedValue = options.get(optionValue);

			for (Locale locale : localizedValue.getAvailableLocales()) {
				formFieldOptions.addOptionLabel(
					optionValue, locale, localizedValue.getString(locale));
			}
		}

		return formFieldOptions;
	}

	private static
		com.liferay.portlet.dynamicdatamapping.storage.DDMFormFieldValue
			copyDDMFormFieldValue(DDMFormFieldValue ddmFormFieldValue) {

		if (ddmFormFieldValue == null) {
			return null;
		}

		com.liferay.portlet.dynamicdatamapping.storage.DDMFormFieldValue
			formFieldValue =
		new com.liferay.portlet.dynamicdatamapping.storage.DDMFormFieldValue();

		formFieldValue.setInstanceId(ddmFormFieldValue.getInstanceId());
		formFieldValue.setName(ddmFormFieldValue.getName());
		formFieldValue.setValue(copyValue(ddmFormFieldValue.getValue()));

		for (DDMFormFieldValue nestedFormFieldValue :
				ddmFormFieldValue.getNestedDDMFormFieldValues()) {

			formFieldValue.addNestedDDMFormFieldValue(
				copyDDMFormFieldValue(nestedFormFieldValue));
		}

		return formFieldValue;
	}

	private static DDMFormLayoutColumn copyDDMFormLayoutColumn(
		com.liferay.portlet.dynamicdatamapping.model.DDMFormLayoutColumn
			ddmFormLayoutColumn) {

		if (ddmFormLayoutColumn == null) {
			return null;
		}

		DDMFormLayoutColumn formLayoutColumn = new DDMFormLayoutColumn();
		formLayoutColumn.setDDMFormFieldNames(
			ddmFormLayoutColumn.getDDMFormFieldNames());
		formLayoutColumn.setSize(ddmFormLayoutColumn.getSize());

		return formLayoutColumn;
	}

	private static
		com.liferay.portlet.dynamicdatamapping.model.DDMFormLayoutColumn
			copyDDMFormLayoutColumn(DDMFormLayoutColumn ddmFormLayoutColumn) {

		if (ddmFormLayoutColumn == null) {
			return null;
		}

		com.liferay.portlet.dynamicdatamapping.model.DDMFormLayoutColumn
			formLayoutColumn =
		new com.liferay.portlet.dynamicdatamapping.model.DDMFormLayoutColumn();

		formLayoutColumn.setDDMFormFieldNames(
			ddmFormLayoutColumn.getDDMFormFieldNames());
		formLayoutColumn.setSize(ddmFormLayoutColumn.getSize());

		return formLayoutColumn;
	}

	private static DDMFormLayoutPage copyDDMFormLayoutPage(
		com.liferay.portlet.dynamicdatamapping.model.DDMFormLayoutPage
			ddmFormLayoutPage) {

		if (ddmFormLayoutPage == null) {
			return null;
		}

		DDMFormLayoutPage formLayoutPage = new DDMFormLayoutPage();
		formLayoutPage.setTitle(
			copyLocalizedValue(ddmFormLayoutPage.getTitle()));
		formLayoutPage.setDescription(
			copyLocalizedValue(ddmFormLayoutPage.getDescription()));

		for (com.liferay.portlet.dynamicdatamapping.model.DDMFormLayoutRow
			ddmFormLayoutPageRow : ddmFormLayoutPage.getDDMFormLayoutRows()) {

			formLayoutPage.addDDMFormLayoutRow(
				copyDDMFormLayoutRow(ddmFormLayoutPageRow));
		}

		return formLayoutPage;
	}

	private static
		com.liferay.portlet.dynamicdatamapping.model.DDMFormLayoutPage
			copyDDMFormLayoutPage(
				DDMFormLayoutPage ddmFormLayoutPage) {

		if (ddmFormLayoutPage == null) {
			return null;
		}

		com.liferay.portlet.dynamicdatamapping.model.DDMFormLayoutPage
			formLayoutPage =
		new com.liferay.portlet.dynamicdatamapping.model.DDMFormLayoutPage();

		formLayoutPage.setTitle(
			copyLocalizedValue(ddmFormLayoutPage.getTitle()));
		formLayoutPage.setDescription(
			copyLocalizedValue(ddmFormLayoutPage.getDescription()));

		for (DDMFormLayoutRow ddmFormLayoutPageRow :
				ddmFormLayoutPage.getDDMFormLayoutRows()) {

			formLayoutPage.addDDMFormLayoutRow(
				copyDDMFormLayoutRow(ddmFormLayoutPageRow));
		}

		return formLayoutPage;
	}

	private static DDMFormLayoutRow copyDDMFormLayoutRow(
		com.liferay.portlet.dynamicdatamapping.model.DDMFormLayoutRow
			ddmFormLayoutRow) {

		if (ddmFormLayoutRow == null) {
			return null;
		}

		DDMFormLayoutRow formLayoutRow = new DDMFormLayoutRow();

		for (com.liferay.portlet.dynamicdatamapping.model.DDMFormLayoutColumn
			ddmFormLayoutPageColumn :
				ddmFormLayoutRow.getDDMFormLayoutColumns()) {

			formLayoutRow.addDDMFormLayoutColumn(
				copyDDMFormLayoutColumn(ddmFormLayoutPageColumn));
		}

		return formLayoutRow;
	}

	private static com.liferay.portlet.dynamicdatamapping.model.DDMFormLayoutRow
		copyDDMFormLayoutRow(DDMFormLayoutRow ddmFormLayoutRow) {

		if (ddmFormLayoutRow == null) {
			return null;
		}

		com.liferay.portlet.dynamicdatamapping.model.DDMFormLayoutRow
			formLayoutRow =
		new com.liferay.portlet.dynamicdatamapping.model.DDMFormLayoutRow();

		for (DDMFormLayoutColumn ddmFormLayoutPageColumn :
				ddmFormLayoutRow.getDDMFormLayoutColumns()) {

			formLayoutRow.addDDMFormLayoutColumn(
				copyDDMFormLayoutColumn(ddmFormLayoutPageColumn));
		}

		return formLayoutRow;
	}

	private static LocalizedValue copyLocalizedValue(
		com.liferay.portlet.dynamicdatamapping.model.LocalizedValue value) {

		if (value == null) {
			return null;
		}

		LocalizedValue localizedValue = new LocalizedValue();
		localizedValue.setDefaultLocale(value.getDefaultLocale());

		Map<Locale, String> values = value.getValues();

		for (Locale locale : values.keySet()) {
			localizedValue.addString(locale, values.get(locale));
		}

		return localizedValue;
	}

	private static
		com.liferay.portlet.dynamicdatamapping.model.LocalizedValue
			copyLocalizedValue(LocalizedValue value) {

		if (value == null) {
			return null;
		}

		com.liferay.portlet.dynamicdatamapping.model.LocalizedValue
			localizedValue =
		new com.liferay.portlet.dynamicdatamapping.model.LocalizedValue();

		localizedValue.setDefaultLocale(value.getDefaultLocale());

		Map<Locale, String> values = value.getValues();

		for (Locale locale : values.keySet()) {
			localizedValue.addString(locale, values.get(locale));
		}

		return localizedValue;
	}

	private static UnlocalizedValue copyUnlocalizedValue(
		com.liferay.portlet.dynamicdatamapping.model.UnlocalizedValue value) {

		if (value == null) {
			return null;
		}

		UnlocalizedValue unlocalizedValue = new UnlocalizedValue(
			value.getString(value.getDefaultLocale()));

		Map<Locale, String> values = value.getValues();

		for (Locale locale : values.keySet()) {
			unlocalizedValue.addString(locale, values.get(locale));
		}

		return unlocalizedValue;
	}

	private static
		com.liferay.portlet.dynamicdatamapping.model.UnlocalizedValue
			copyUnlocalizedValue(UnlocalizedValue value) {

		if (value == null) {
			return null;
		}

		com.liferay.portlet.dynamicdatamapping.model.UnlocalizedValue
			unlocalizedValue =
		new com.liferay.portlet.dynamicdatamapping.model.UnlocalizedValue(
			value.getString(value.getDefaultLocale()));

		Map<Locale, String> values = value.getValues();

		for (Locale locale : values.keySet()) {
			unlocalizedValue.addString(locale, values.get(locale));
		}

		return unlocalizedValue;
	}

	private static Value copyValue(
		com.liferay.portlet.dynamicdatamapping.model.Value value) {

		if (value == null) {
			return null;
		}

		if (value instanceof
				com.liferay.portlet.dynamicdatamapping.model.LocalizedValue) {

			return copyLocalizedValue(
				(com.liferay.portlet.dynamicdatamapping.model.LocalizedValue)
					value);
		}
		else {
			return copyUnlocalizedValue(
				(com.liferay.portlet.dynamicdatamapping.model.UnlocalizedValue)
					value);
		}
	}

	private static com.liferay.portlet.dynamicdatamapping.model.Value
		copyValue(Value value) {

		if (value == null) {
			return null;
		}

		if (value instanceof LocalizedValue) {
			return copyLocalizedValue((LocalizedValue)value);
		}
		else {
			return copyUnlocalizedValue((UnlocalizedValue)value);
		}
	}

}