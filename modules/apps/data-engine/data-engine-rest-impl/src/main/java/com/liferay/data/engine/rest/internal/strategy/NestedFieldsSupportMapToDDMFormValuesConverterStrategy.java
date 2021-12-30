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

package com.liferay.data.engine.rest.internal.strategy;

import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.util.DDM;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

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

	private void _addMissingValues(
		Map<String, DDMFormField> ddmFormFieldsMap, Locale locale,
		Map<String, Object> values) {

		for (Map.Entry<String, DDMFormField> entry :
				ddmFormFieldsMap.entrySet()) {

			Set<String> keys = values.keySet();

			Stream<String> stream = keys.stream();

			if (stream.anyMatch(
					key -> StringUtil.startsWith(key, entry.getKey()))) {

				continue;
			}

			Object value = StringPool.BLANK;

			DDMFormField ddmFormField = entry.getValue();

			if (ddmFormField.isLocalizable()) {
				value = HashMapBuilder.<String, Object>put(
					LocaleUtil.toLanguageId(locale), StringPool.BLANK
				).build();
			}

			values.put(
				StringBundler.concat(
					entry.getKey(), DDM.INSTANCE_SEPARATOR,
					StringUtil.randomString()),
				HashMapBuilder.<String, Object>put(
					"value", value
				).build());
		}
	}

	private DDMFormFieldValue _createDDMFormFieldValue(
		DDMFormField ddmFormField, Map<String, DDMFormField> ddmFormFields,
		Map<String, Object> fieldInstanceValue, String instanceId,
		Locale locale) {

		DDMFormFieldValue ddmFormFieldValue = new DDMFormFieldValue() {
			{
				setName(ddmFormField.getName());
				setInstanceId(instanceId);
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

			_addMissingValues(
				ddmFormField.getNestedDDMFormFieldsMap(), locale, nestedValues);

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