/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.field.type.internal.checkbox;

import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldValueAccessor;
import com.liferay.dynamic.data.mapping.form.field.type.constants.DDMFormFieldTypeConstants;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;

/**
 * @author Renato Rego
 */
@Component(
	property = "ddm.form.field.type.name=" + DDMFormFieldTypeConstants.CHECKBOX,
	service = DDMFormFieldValueAccessor.class
)
public class CheckboxDDMFormFieldValueAccessor
	implements DDMFormFieldValueAccessor<Boolean> {

	@Override
	public Boolean[] getArrayGenericType() {
		return new Boolean[0];
	}

	@Override
	public Boolean getValue(
		DDMFormFieldValue ddmFormFieldValue, Locale locale) {

		Value value = ddmFormFieldValue.getValue();

		if (value == null) {
			return false;
		}

		return Boolean.valueOf(value.getString(locale));
	}

	@Override
	public Boolean getValueForEvaluation(
		DDMFormFieldValue ddmFormFieldValue, Locale locale) {

		return getValue(ddmFormFieldValue, locale);
	}

	@Override
	public boolean isEmpty(DDMFormFieldValue ddmFormFieldValue, Locale locale) {
		Boolean valueBoolean = getValue(ddmFormFieldValue, locale);

		if (valueBoolean == Boolean.FALSE) {
			return true;
		}

		return false;
	}

}