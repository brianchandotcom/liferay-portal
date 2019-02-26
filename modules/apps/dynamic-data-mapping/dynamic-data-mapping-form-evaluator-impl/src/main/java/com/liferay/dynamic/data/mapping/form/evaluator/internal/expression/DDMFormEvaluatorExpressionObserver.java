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

import com.liferay.dynamic.data.mapping.expression.DDMExpressionObserver;
import com.liferay.dynamic.data.mapping.expression.UpdateFieldPropertyRequest;
import com.liferay.dynamic.data.mapping.expression.UpdateFieldPropertyResponse;
import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormEvaluatorFieldContextKey;
import com.liferay.dynamic.data.mapping.form.evaluator.internal.helper.DDMFormEvaluatorFormValuesHelper;
import com.liferay.portal.kernel.util.Validator;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Rafael Praxedes
 */
public class DDMFormEvaluatorExpressionObserver
	implements DDMExpressionObserver {

	public DDMFormEvaluatorExpressionObserver(
		DDMFormEvaluatorFormValuesHelper ddmFormEvaluatorFormValuesHelper,
		Map<DDMFormEvaluatorFieldContextKey, Map<String, Object>>
			ddmFormFieldsPropertyChanges) {

		_ddmFormEvaluatorFormValuesHelper = ddmFormEvaluatorFormValuesHelper;
		_ddmFormFieldsPropertyChanges = ddmFormFieldsPropertyChanges;
	}

	@Override
	public UpdateFieldPropertyResponse updateFieldProperty(
		UpdateFieldPropertyRequest updateFieldPropertyRequest) {

		if (Validator.isNull(updateFieldPropertyRequest.getInstanceId())) {
			updateFieldProperty(
				updateFieldPropertyRequest.getField(),
				updateFieldPropertyRequest.getProperties());
		}
		else {
			updateFieldProperty(
				new DDMFormEvaluatorFieldContextKey(
					updateFieldPropertyRequest.getField(),
					updateFieldPropertyRequest.getInstanceId()),
				updateFieldPropertyRequest.getProperties());
		}

		UpdateFieldPropertyResponse.Builder builder =
			UpdateFieldPropertyResponse.Builder.newBuilder(true);

		return builder.build();
	}

	protected void updateFieldProperty(
		DDMFormEvaluatorFieldContextKey ddmFormFieldContextKey,
		Map<String, Object> properties) {

		Map<String, Object> ddmFormFieldProperties =
			_ddmFormFieldsPropertyChanges.get(ddmFormFieldContextKey);

		if (ddmFormFieldProperties == null) {
			ddmFormFieldProperties = new HashMap<>();

			_ddmFormFieldsPropertyChanges.put(
				ddmFormFieldContextKey, ddmFormFieldProperties);
		}

		ddmFormFieldProperties.putAll(properties);
	}

	protected void updateFieldProperty(
		String fieldName, Map<String, Object> properties) {

		Set<DDMFormEvaluatorFieldContextKey> ddmFormFieldContextKeySet =
			_ddmFormEvaluatorFormValuesHelper.getDDMFormFieldContextKeySet(
				fieldName);

		for (DDMFormEvaluatorFieldContextKey ddmFormFieldContextKey :
				ddmFormFieldContextKeySet) {

			updateFieldProperty(ddmFormFieldContextKey, properties);
		}
	}

	private final DDMFormEvaluatorFormValuesHelper
		_ddmFormEvaluatorFormValuesHelper;
	private final Map<DDMFormEvaluatorFieldContextKey, Map<String, Object>>
		_ddmFormFieldsPropertyChanges;

}