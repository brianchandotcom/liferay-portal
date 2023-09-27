/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.form.internal.dto.v1_0.util;

import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.model.UnlocalizedValue;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.headless.form.dto.v1_0.FormFieldValue;
import com.liferay.petra.function.transform.TransformUtil;

import java.util.Locale;
import java.util.Map;

/**
 * @author Victor Oliveira
 */
public class DDMFormValuesUtil {

	public static DDMFormValues createDDMFormValues(
			DDMFormInstance ddmFormInstance, FormFieldValue[] formFieldValues,
			Locale locale)
		throws Exception {

		DDMStructure ddmStructure = ddmFormInstance.getStructure();

		DDMForm ddmForm = ddmStructure.getDDMForm();

		DDMFormValues ddmFormValues = new DDMFormValues(ddmForm);

		ddmFormValues.addAvailableLocale(locale);

		Map<String, DDMFormField> ddmFormFieldsMap =
			ddmForm.getDDMFormFieldsMap(true);

		ddmFormValues.setDDMFormFieldValues(
			TransformUtil.transformToList(
				formFieldValues,
				fieldValue -> _toDDMFormFieldValue(
					ddmFormFieldsMap, fieldValue, locale)));

		ddmFormValues.setDefaultLocale(locale);

		return ddmFormValues;
	}

	private static DDMFormFieldValue _toDDMFormFieldValue(
		Map<String, DDMFormField> ddmFormFieldsMap,
		FormFieldValue formFieldValue, Locale locale) {

		DDMFormFieldValue ddmFormFieldValue = new DDMFormFieldValue();

		ddmFormFieldValue.setName(formFieldValue.getName());

		Value value = _VALUE;

		DDMFormField ddmFormField = ddmFormFieldsMap.get(
			formFieldValue.getName());

		if (ddmFormField != null) {
			String string = formFieldValue.getValue();

			if ((string != null) && ddmFormField.isLocalizable()) {
				value = new LocalizedValue() {
					{
						addString(locale, string);
					}
				};
			}
		}

		ddmFormFieldValue.setValue(value);

		return ddmFormFieldValue;
	}

	private static final Value _VALUE = new UnlocalizedValue((String)null);

}