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

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

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

		populate(ddmFormValues);

		return ddmFormValues;
	}

	public void populate(DDMFormValues ddmFormValues) {
		_populate(
			ddmFormValues::addDDMFormFieldValue, _ddmForm.getDDMFormFields(),
			ddmFormValues.getDDMFormFieldValuesMap(false));
	}

	private DDMFormFieldValue _createDefaultDDMFormFieldValue(
		DDMFormField ddmFormField) {

		DDMFormFieldValue ddmFormFieldValue = new DDMFormFieldValue();

		ddmFormFieldValue.setFieldReference(ddmFormField.getFieldReference());
		ddmFormFieldValue.setName(ddmFormField.getName());
		ddmFormFieldValue.setValue(_createDefaultValue(ddmFormField));

		for (DDMFormField nestedDDMFormField :
				ddmFormField.getNestedDDMFormFields()) {

			ddmFormFieldValue.addNestedDDMFormFieldValue(
				_createDefaultDDMFormFieldValue(nestedDDMFormField));
		}

		return ddmFormFieldValue;
	}

	private LocalizedValue _createDefaultLocalizedValue(
		String defaultValueString) {

		LocalizedValue value = new LocalizedValue(_ddmForm.getDefaultLocale());

		value.addString(_ddmForm.getDefaultLocale(), defaultValueString);

		return value;
	}

	private Value _createDefaultValue(DDMFormField ddmFormField) {
		LocalizedValue defaultValue = ddmFormField.getPredefinedValue();

		if ((defaultValue == null) ||
			MapUtil.isEmpty(defaultValue.getValues())) {

			defaultValue = Optional.ofNullable(
				(LocalizedValue)ddmFormField.getProperty("initialValue")
			).orElse(
				_createDefaultLocalizedValue(StringPool.BLANK)
			);
		}

		if (ddmFormField.isLocalizable()) {
			return defaultValue;
		}

		return new UnlocalizedValue(
			GetterUtil.getString(
				defaultValue.getString(_ddmForm.getDefaultLocale())));
	}

	private void _populate(
		Consumer<DDMFormFieldValue> consumer, List<DDMFormField> ddmFormFields,
		Map<String, List<DDMFormFieldValue>> ddmFormFieldValuesMap) {

		if (ddmFormFields == null) {
			return;
		}

		ddmFormFields.forEach(
			ddmFormField -> {
				List<DDMFormFieldValue> ddmFormFieldValues =
					ddmFormFieldValuesMap.get(ddmFormField.getName());

				if (ddmFormFieldValues != null) {
					ddmFormFieldValues.forEach(
						ddmFormFieldValue -> _populate(
							ddmFormFieldValue::addNestedDDMFormFieldValue,
							ddmFormField.getNestedDDMFormFields(),
							ddmFormFieldValue.
								getNestedDDMFormFieldValuesMap()));
				}
				else {
					consumer.accept(
						_createDefaultDDMFormFieldValue(ddmFormField));
				}
			});
	}

	private final DDMForm _ddmForm;

}