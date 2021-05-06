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

package com.liferay.dynamic.data.mapping.form.evaluator.internal;

import com.liferay.dynamic.data.mapping.expression.DDMExpressionFactory;
import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormEvaluator;
import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormEvaluatorEvaluateRequest;
import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormEvaluatorEvaluateResponse;
import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormEvaluatorFieldContextKey;
import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormFieldEvaluationResult;
import com.liferay.dynamic.data.mapping.form.evaluator.internal.helper.DDMFormEvaluatorHelper;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeServicesTracker;
import com.liferay.dynamic.data.mapping.form.page.change.DDMFormPageChangeTracker;
import com.liferay.petra.string.StringBundler;

import java.util.HashMap;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pablo Carvalho
 * @author Leonardo Barros
 */
@Component(immediate = true, service = DDMFormEvaluator.class)
public class DDMFormEvaluatorImpl implements DDMFormEvaluator {

	@Override
	public DDMFormEvaluatorEvaluateResponse evaluate(
		DDMFormEvaluatorEvaluateRequest ddmFormEvaluatorEvaluateRequest) {

		DDMFormEvaluatorHelper formEvaluatorHelper = new DDMFormEvaluatorHelper(
			ddmExpressionFactory, ddmFormEvaluatorEvaluateRequest,
			ddmFormFieldTypeServicesTracker, ddmFormPageChangeTracker);

		return formEvaluatorHelper.evaluate();
	}

	protected Map<String, DDMFormFieldEvaluationResult>
		createDDMFormFieldEvaluationResultsMap(
			Map<DDMFormEvaluatorFieldContextKey, Map<String, Object>>
				ddmFormFieldsPropertyChange) {

		Map<String, DDMFormFieldEvaluationResult> map = new HashMap<>();

		for (Map.Entry<DDMFormEvaluatorFieldContextKey, Map<String, Object>>
				entry : ddmFormFieldsPropertyChange.entrySet()) {

			DDMFormEvaluatorFieldContextKey ddmFormEvaluatorFieldContextKey =
				entry.getKey();

			String key = StringBundler.concat(
				ddmFormEvaluatorFieldContextKey.getName(), "_INSTANCE_",
				ddmFormEvaluatorFieldContextKey.getInstanceId());

			DDMFormFieldEvaluationResult ddmFormFieldEvaluationResult =
				new DDMFormFieldEvaluationResult(
					ddmFormEvaluatorFieldContextKey.getName(),
					ddmFormEvaluatorFieldContextKey.getInstanceId());

			Map<String, Object> value = entry.getValue();

			for (Map.Entry<String, Object> property : value.entrySet()) {
				ddmFormFieldEvaluationResult.setProperty(
					property.getKey(), property.getValue());
			}

			map.put(key, ddmFormFieldEvaluationResult);
		}

		return map;
	}

	@Reference
	protected DDMExpressionFactory ddmExpressionFactory;

	@Reference
	protected DDMFormFieldTypeServicesTracker ddmFormFieldTypeServicesTracker;

	@Reference
	protected DDMFormPageChangeTracker ddmFormPageChangeTracker;

}