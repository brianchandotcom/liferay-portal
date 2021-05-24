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
import com.liferay.portal.kernel.util.MapUtil;

import java.util.Optional;

/**
 * @author Marcellus Tavares
 */
public class DefaultDDMFormValuesFactory {

	public DefaultDDMFormValuesFactory(DDMForm ddmForm) {
		_ddmForm = ddmForm;
	}

	public DDMFormValues create() {
		DDMFormValues ddmFormValues = new DDMFormValues(_ddmForm);

		ddmFormValues.setAvailableLocales(_ddmForm.getAvailableLocales());
		ddmFormValues.setDefaultLocale(_ddmForm.getDefaultLocale());

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

	protected LocalizedValue createDefaultLocalizedValue(
		String defaultValueString) {

		LocalizedValue value = new LocalizedValue(_ddmForm.getDefaultLocale());

		value.addString(_ddmForm.getDefaultLocale(), defaultValueString);

		return value;
	}

	protected Value createDefaultValue(DDMFormField ddmFormField) {
		LocalizedValue defaultValue = ddmFormField.getPredefinedValue();

		if ((defaultValue == null) ||
			MapUtil.isEmpty(defaultValue.getValues())) {

			defaultValue = Optional.ofNullable(
				(LocalizedValue)ddmFormField.getProperty("initialValue")
			).orElse(
				createDefaultLocalizedValue(StringPool.BLANK)
			);
		}

		if (ddmFormField.isLocalizable()) {
			return defaultValue;
		}

		return new UnlocalizedValue(
			GetterUtil.getString(
				defaultValue.getString(_ddmForm.getDefaultLocale())));
	}

	private final DDMForm _ddmForm;

}