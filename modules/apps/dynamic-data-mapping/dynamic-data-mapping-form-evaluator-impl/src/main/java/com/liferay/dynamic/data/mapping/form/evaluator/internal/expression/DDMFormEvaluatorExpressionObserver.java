/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

		Set<DDMFormEvaluatorFieldContextKey> ddmFormFieldContextKeys =
			_ddmFormEvaluatorFormValuesHelper.getDDMFormFieldContextKeys(
				fieldName);

		for (DDMFormEvaluatorFieldContextKey ddmFormFieldContextKey :
				ddmFormFieldContextKeys) {

			updateFieldProperty(ddmFormFieldContextKey, properties);
		}
	}

	private final DDMFormEvaluatorFormValuesHelper
		_ddmFormEvaluatorFormValuesHelper;
	private final Map<DDMFormEvaluatorFieldContextKey, Map<String, Object>>
		_ddmFormFieldsPropertyChanges;

}