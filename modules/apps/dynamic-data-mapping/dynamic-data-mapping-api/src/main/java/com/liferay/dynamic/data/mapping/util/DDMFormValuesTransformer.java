/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.util;

import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Marcellus Tavares
 */
public class DDMFormValuesTransformer {

	public DDMFormValuesTransformer(DDMFormValues ddmFormValues) {
		_ddmFormValues = ddmFormValues;
	}

	public void addTransformer(
		DDMFormFieldValueTransformer ddmFormFieldValueTransformer) {

		_ddmFormFieldValueTransformersMap.put(
			ddmFormFieldValueTransformer.getFieldType(),
			ddmFormFieldValueTransformer);
	}

	public void transform() throws PortalException {
		DDMForm ddmForm = _ddmFormValues.getDDMForm();

		traverse(
			ddmForm.getDDMFormFields(),
			_ddmFormValues.getDDMFormFieldValuesMap());
	}

	protected void performTransformation(
			List<DDMFormFieldValue> ddmFormFieldValues,
			DDMFormFieldValueTransformer ddmFormFieldValueTransformer)
		throws PortalException {

		for (DDMFormFieldValue ddmFormFieldValue : ddmFormFieldValues) {
			ddmFormFieldValueTransformer.transform(ddmFormFieldValue);
		}
	}

	protected void traverse(
			List<DDMFormField> ddmFormFields,
			Map<String, List<DDMFormFieldValue>> ddmFormFieldValuesMap)
		throws PortalException {

		for (DDMFormField ddmFormField : ddmFormFields) {
			List<DDMFormFieldValue> ddmFormFieldValues =
				ddmFormFieldValuesMap.get(ddmFormField.getName());

			if (ddmFormFieldValues == null) {
				continue;
			}

			String fieldType = ddmFormField.getType();

			if (_ddmFormFieldValueTransformersMap.containsKey(fieldType)) {
				performTransformation(
					ddmFormFieldValues,
					_ddmFormFieldValueTransformersMap.get(fieldType));
			}

			for (DDMFormFieldValue ddmFormFieldValue : ddmFormFieldValues) {
				traverse(
					ddmFormField.getNestedDDMFormFields(),
					ddmFormFieldValue.getNestedDDMFormFieldValuesMap());
			}
		}
	}

	private final Map<String, DDMFormFieldValueTransformer>
		_ddmFormFieldValueTransformersMap = new HashMap<>();
	private final DDMFormValues _ddmFormValues;

}