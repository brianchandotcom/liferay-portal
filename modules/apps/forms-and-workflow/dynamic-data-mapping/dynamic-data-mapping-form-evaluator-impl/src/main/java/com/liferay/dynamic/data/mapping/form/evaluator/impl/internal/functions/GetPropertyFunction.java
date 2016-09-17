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

package com.liferay.dynamic.data.mapping.form.evaluator.impl.internal.functions;

import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormFieldEvaluationResult;

import java.util.List;
import java.util.Map;

/**
 * @author Leonardo Barros
 */
public class GetPropertyFunction extends BaseDDMFormRuleFunction {

	public GetPropertyFunction(
		Map<String, List<DDMFormFieldEvaluationResult>>
			ddmFormFieldEvaluationResultsMap, String propertyName) {

		super(ddmFormFieldEvaluationResultsMap);

		_propertyName = propertyName;
	}

	@Override
	public Object evaluate(Object... parameters) {
		if (parameters.length != 1) {
			throw new IllegalArgumentException("One parameter is expected");
		}

		String ddmFormFieldName = parameters[0].toString();

		List<DDMFormFieldEvaluationResult> ddmFormFieldEvaluationResults =
			getDDMFormFieldEvaluationResults(ddmFormFieldName);

		DDMFormFieldEvaluationResult ddmFormFieldEvaluationResult =
			ddmFormFieldEvaluationResults.get(0);

		return ddmFormFieldEvaluationResult.getProperty(_propertyName);
	}

	private final String _propertyName;

}