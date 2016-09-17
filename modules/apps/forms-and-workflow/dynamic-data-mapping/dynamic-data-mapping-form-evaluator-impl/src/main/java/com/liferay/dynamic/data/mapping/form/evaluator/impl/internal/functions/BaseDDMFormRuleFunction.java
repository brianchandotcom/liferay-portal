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

import com.liferay.dynamic.data.mapping.expression.DDMExpressionFunction;
import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormFieldEvaluationResult;

import java.util.List;
import java.util.Map;

/**
 * @author Leonardo Barros
 */
public abstract class BaseDDMFormRuleFunction implements DDMExpressionFunction {

	public BaseDDMFormRuleFunction(
		Map<String, List<DDMFormFieldEvaluationResult>>
			ddmFormFieldEvaluationResultsMap) {

		this.ddmFormFieldEvaluationResultsMap =
			ddmFormFieldEvaluationResultsMap;
	}

	protected List<DDMFormFieldEvaluationResult>
		getDDMFormFieldEvaluationResults(String ddmFormFieldName) {

		if (!ddmFormFieldEvaluationResultsMap.containsKey(ddmFormFieldName)) {
			throw new IllegalArgumentException("Invalid field name");
		}

		return ddmFormFieldEvaluationResultsMap.get(ddmFormFieldName);
	}

	protected final Map<String, List<DDMFormFieldEvaluationResult>>
		ddmFormFieldEvaluationResultsMap;

}