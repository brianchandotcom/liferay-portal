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

package com.liferay.dynamic.data.mapping.form.renderer.internal;

import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.model.UnlocalizedValue;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Locale;

/**
 * @author Marcellus Tavares
 */
public class DefaultDDMFormValuesFactory {

	public DefaultDDMFormValuesFactory(DDMForm ddmForm, Locale locale) {
		_ddmForm = ddmForm;
		_locale = locale;
	}

	public DDMFormValues create() {
		DDMFormValues ddmFormValues = new DDMFormValues(_ddmForm);

		ddmFormValues.addAvailableLocale(_locale);
		ddmFormValues.setDefaultLocale(_locale);

		for (DDMFormField ddmFormField : _ddmForm.getDDMFormFields()) {
			DDMFormFieldValue ddmFormFieldValue =
				createDefaultDDMFormFieldValue(ddmFormField);

			ddmFormValues.addDDMFormFieldValue(ddmFormFieldValue);
		}

		return ddmFormValues;
	}

	protected DDMFormFieldValue createDefaultDDMFormFieldValue(
		DDMFormField ddmFormField) {

		DDMFormFieldValue ddmFormFieldValue = new DDMFormFieldValue();

		ddmFormFieldValue.setFieldReference(ddmFormField.getFieldReference());
		ddmFormFieldValue.setName(ddmFormField.getName());

		ddmFormFieldValue.setValue(createDefaultValue(ddmFormField));

		for (DDMFormField nestedDDMFormField :
				ddmFormField.getNestedDDMFormFields()) {

			ddmFormFieldValue.addNestedDDMFormFieldValue(
				createDefaultDDMFormFieldValue(nestedDDMFormField));
		}

		return ddmFormFieldValue;
	}

	protected Value createDefaultLocalizedValue(String defaultValueString) {
		Value value = new LocalizedValue(_locale);

		value.addString(_locale, defaultValueString);

		return value;
	}

	protected Value createDefaultValue(DDMFormField ddmFormField) {
		String defaultValueString = _getDefaultValueString(
			ddmFormField.getPredefinedValue());

		if (Validator.isNull(defaultValueString)) {
			defaultValueString = _getDefaultValueString(
				(LocalizedValue)ddmFormField.getProperty("initialValue"));
		}

		if (ddmFormField.isLocalizable()) {
			return createDefaultLocalizedValue(defaultValueString);
		}

		return new UnlocalizedValue(defaultValueString);
	}

	private String _getDefaultValueString(LocalizedValue localizedValue) {
		if (localizedValue == null) {
			return StringPool.BLANK;
		}

		return GetterUtil.getString(localizedValue.getString(_locale));
	}

	private final DDMForm _ddmForm;
	private final Locale _locale;

}