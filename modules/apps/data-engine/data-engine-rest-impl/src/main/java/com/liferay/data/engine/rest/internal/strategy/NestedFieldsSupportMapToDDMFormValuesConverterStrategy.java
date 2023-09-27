/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.data.engine.rest.internal.strategy;

import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.util.DDM;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author Rafael Praxedes
 */
public class NestedFieldsSupportMapToDDMFormValuesConverterStrategy
	implements MapToDDMFormValuesConverterStrategy {

	public static NestedFieldsSupportMapToDDMFormValuesConverterStrategy
		getInstance() {

		return _nestedFieldsSupportMapToDDMFormValuesConverterStrategy;
	}

	@Override
	public void setDDMFormFieldValues(
		Map<String, Object> dataRecordValues, DDMForm ddmForm,
		DDMFormValues ddmFormValues, Locale locale) {

		Map<String, DDMFormField> ddmFormFields = ddmForm.getDDMFormFieldsMap(
			true);

		for (Map.Entry<String, Object> entry : dataRecordValues.entrySet()) {
			String[] parts = StringUtil.split(
				entry.getKey(), DDM.INSTANCE_SEPARATOR);

			ddmFormValues.addDDMFormFieldValue(
				_createDDMFormFieldValue(
					ddmFormFields.get(parts[0]), ddmFormFields,
					(Map<String, Object>)entry.getValue(), parts[1], locale));
		}
	}

	private NestedFieldsSupportMapToDDMFormValuesConverterStrategy() {
	}

	private DDMFormFieldValue _createDDMFormFieldValue(
		DDMFormField ddmFormField, Map<String, DDMFormField> ddmFormFields,
		Map<String, Object> fieldInstanceValue, String instanceId,
		Locale locale) {

		DDMFormFieldValue ddmFormFieldValue = new DDMFormFieldValue() {
			{
				setInstanceId(instanceId);
				setName(ddmFormField.getName());
			}
		};

		Object value = fieldInstanceValue.get("value");

		if (!StringUtil.equals(ddmFormField.getType(), "fieldset") &&
			Validator.isNotNull(value)) {

			ddmFormFieldValue.setValue(
				createValue(ddmFormField, locale, value));
		}

		if (ListUtil.isNotEmpty(ddmFormField.getNestedDDMFormFields())) {
			Map<String, Object> nestedValues =
				(Map<String, Object>)GetterUtil.getObject(
					fieldInstanceValue.get("nestedValues"),
					new HashMap<String, Object>());

			if (MapUtil.isEmpty(nestedValues)) {
				return ddmFormFieldValue;
			}

			for (Map.Entry<String, Object> entry : nestedValues.entrySet()) {
				String[] parts = StringUtil.split(
					entry.getKey(), DDM.INSTANCE_SEPARATOR);

				ddmFormFieldValue.addNestedDDMFormFieldValue(
					_createDDMFormFieldValue(
						ddmFormFields.get(parts[0]), ddmFormFields,
						(Map<String, Object>)entry.getValue(), parts[1],
						locale));
			}
		}

		return ddmFormFieldValue;
	}

	private static final NestedFieldsSupportMapToDDMFormValuesConverterStrategy
		_nestedFieldsSupportMapToDDMFormValuesConverterStrategy =
			new NestedFieldsSupportMapToDDMFormValuesConverterStrategy();

}