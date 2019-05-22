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

package com.liferay.dynamic.data.mapping.form.evaluator.internal.expression;

import com.liferay.dynamic.data.mapping.expression.DDMExpressionFieldAccessor;
import com.liferay.dynamic.data.mapping.expression.GetFieldPropertyRequest;
import com.liferay.dynamic.data.mapping.expression.GetFieldPropertyResponse;
import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormEvaluatorFieldContextKey;
import com.liferay.dynamic.data.mapping.form.evaluator.internal.helper.DDMFormEvaluatorFormValuesHelper;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeServicesTracker;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldValueAccessor;
import com.liferay.dynamic.data.mapping.form.field.type.DefaultDDMFormFieldValueAccessor;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

/**
 * @author Rafael Praxedes
 */
public class DDMFormEvaluatorExpressionFieldAccessor
	implements DDMExpressionFieldAccessor {

	public DDMFormEvaluatorExpressionFieldAccessor(
		DDMFormEvaluatorFormValuesHelper ddmFormEvaluatorFormValuesHelper,
		Map<String, DDMFormField> ddmFormFieldsMap,
		Map<DDMFormEvaluatorFieldContextKey, Map<String, Object>>
			ddmFormFieldsPropertyChanges,
		DDMFormFieldTypeServicesTracker ddmFormFieldTypeServicesTracker,
		Locale locale) {

		_ddmFormEvaluatorFormValuesHelper = ddmFormEvaluatorFormValuesHelper;
		_ddmFormFieldsMap = ddmFormFieldsMap;
		_ddmFormFieldsPropertyChanges = ddmFormFieldsPropertyChanges;
		_ddmFormFieldTypeServicesTracker = ddmFormFieldTypeServicesTracker;
		_locale = locale;
	}

	@Override
	public GetFieldPropertyResponse getFieldProperty(
		GetFieldPropertyRequest getFieldPropertyRequest) {

		Object fieldProperty = null;

		if (Validator.isNull(getFieldPropertyRequest.getInstanceId())) {
			fieldProperty = getFieldPropertyByFieldName(
				getFieldPropertyRequest.getField(),
				getFieldPropertyRequest.getProperty());
		}
		else {
			fieldProperty = getFieldPropertyByDDMFormFieldContextKey(
				new DDMFormEvaluatorFieldContextKey(
					getFieldPropertyRequest.getField(),
					getFieldPropertyRequest.getInstanceId()),
				getFieldPropertyRequest.getProperty());
		}

		GetFieldPropertyResponse.Builder builder =
			GetFieldPropertyResponse.Builder.newBuilder(fieldProperty);

		return builder.build();
	}

	public Object getFieldPropertyChanged(
		DDMFormEvaluatorFieldContextKey fieldContextKey, String property) {

		Map<String, Object> ddmFormFieldProperties =
			_ddmFormFieldsPropertyChanges.get(fieldContextKey);

		if ((ddmFormFieldProperties != null) &&
			ddmFormFieldProperties.containsKey(property)) {

			return ddmFormFieldProperties.get(property);
		}

		return null;
	}

	public Object getFieldValue(
		DDMFormEvaluatorFieldContextKey ddmFormFieldContextKey) {

		Object value = getFieldPropertyChanged(ddmFormFieldContextKey, "value");

		if (value != null) {
			return value;
		}

		DDMFormFieldValue ddmFormFieldValue =
			_ddmFormEvaluatorFormValuesHelper.getDDMFormFieldValue(
				ddmFormFieldContextKey);

		Value ddmFormFieldValueValue = ddmFormFieldValue.getValue();

		DDMFormFieldValueAccessor<?> ddmFormFieldValueAccessor =
			getDDMFormFieldValueAccessor(ddmFormFieldContextKey.getName());

		return ddmFormFieldValueAccessor.getValue(
			ddmFormFieldValue,
			Optional.ofNullable(
				_locale
			).orElse(
				ddmFormFieldValueValue.getDefaultLocale()
			));
	}

	@Override
	public boolean isField(String parameter) {
		return _ddmFormFieldsMap.containsKey(parameter);
	}

	protected DDMFormFieldValueAccessor<?> getDDMFormFieldValueAccessor(
		String fieldName) {

		DDMFormField ddmFormField = _ddmFormFieldsMap.get(fieldName);

		DDMFormFieldValueAccessor<?> ddmFormFieldValueAccessor =
			_ddmFormFieldTypeServicesTracker.getDDMFormFieldValueAccessor(
				ddmFormField.getType());

		if (ddmFormFieldValueAccessor != null) {
			return ddmFormFieldValueAccessor;
		}

		return _defaultDDMFormFieldValueAccessor;
	}

	protected Object getFieldProperty(String fieldName, String property) {
		Object value = getFieldPropertyChanged(fieldName, property);

		if (value == null) {
			DDMFormField ddmFormField = _ddmFormFieldsMap.get(fieldName);

			if (ddmFormField != null) {
				value = ddmFormField.getProperty(property);
			}
		}

		return value;
	}

	protected Object getFieldPropertyByDDMFormFieldContextKey(
		DDMFormEvaluatorFieldContextKey ddmFormFieldContextKey,
		String property) {

		if (property.equals("value")) {
			return getFieldValue(ddmFormFieldContextKey);
		}

		return getFieldProperty(ddmFormFieldContextKey.getName(), property);
	}

	protected Object getFieldPropertyByFieldName(
		String fieldName, String property) {

		if (property.equals("value")) {
			return getFieldValues(fieldName);
		}

		return getFieldProperty(fieldName, property);
	}

	protected Object getFieldPropertyChanged(
		String fieldName, String property) {

		Set<DDMFormEvaluatorFieldContextKey> ddmFormFieldContextKeyList =
			_ddmFormEvaluatorFormValuesHelper.getDDMFormFieldContextKeySet(
				fieldName);

		if (SetUtil.isEmpty(ddmFormFieldContextKeyList)) {
			return null;
		}

		Iterator<DDMFormEvaluatorFieldContextKey> iterator =
			ddmFormFieldContextKeyList.iterator();

		return getFieldPropertyChanged(iterator.next(), property);
	}

	protected Object getFieldValues(String fieldName) {
		Set<DDMFormEvaluatorFieldContextKey> ddmFormFieldContextKeyList =
			_ddmFormEvaluatorFormValuesHelper.getDDMFormFieldContextKeySet(
				fieldName);

		Stream<DDMFormEvaluatorFieldContextKey> stream =
			ddmFormFieldContextKeyList.stream();

		DDMFormFieldValueAccessor<?> ddmFormFieldValueAccessor =
			getDDMFormFieldValueAccessor(fieldName);

		Object[] values = stream.map(
			this::getFieldValue
		).toArray(
			ddmFormFieldValueAccessor.getArrayGeneratorIntFunction()
		);

		if (ArrayUtil.isNotEmpty(values) && (values.length == 1)) {
			return values[0];
		}

		return values;
	}

	private final DDMFormEvaluatorFormValuesHelper
		_ddmFormEvaluatorFormValuesHelper;
	private final Map<String, DDMFormField> _ddmFormFieldsMap;
	private final Map<DDMFormEvaluatorFieldContextKey, Map<String, Object>>
		_ddmFormFieldsPropertyChanges;
	private final DDMFormFieldTypeServicesTracker
		_ddmFormFieldTypeServicesTracker;
	private final DDMFormFieldValueAccessor<String>
		_defaultDDMFormFieldValueAccessor =
			new DefaultDDMFormFieldValueAccessor();
	private final Locale _locale;

}