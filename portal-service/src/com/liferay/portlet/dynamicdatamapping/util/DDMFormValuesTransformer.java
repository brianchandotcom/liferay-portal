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

package com.liferay.portlet.dynamicdatamapping.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portlet.dynamicdatamapping.model.DDMForm;
import com.liferay.portlet.dynamicdatamapping.model.DDMFormField;
import com.liferay.portlet.dynamicdatamapping.storage.DDMFormFieldValue;
import com.liferay.portlet.dynamicdatamapping.storage.DDMFormValues;

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
			ddmFormFieldValueTransformer.transform(
				ddmFormFieldValue.getValue());
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
		_ddmFormFieldValueTransformersMap =
			new HashMap<String, DDMFormFieldValueTransformer>();
	private final DDMFormValues _ddmFormValues;

}