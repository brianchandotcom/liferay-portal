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
import com.liferay.portal.kernel.util.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Marcellus Tavares
 */
public abstract class BaseDDMFormRuleFunctionTest {

	protected DDMFormFieldEvaluationResult createDDMFormFieldEvaluationResult(
		String fieldName, String propertyName, Object propertyValue) {

		DDMFormFieldEvaluationResult ddmFormFieldEvaluationResult =
			new DDMFormFieldEvaluationResult(
				fieldName, StringUtil.randomString());

		ddmFormFieldEvaluationResult.setProperty(propertyName, propertyValue);

		return ddmFormFieldEvaluationResult;
	}

	protected Map<String, List<DDMFormFieldEvaluationResult>>
		createDDMFormFieldEvaluationResultsMap(
			DDMFormFieldEvaluationResult... ddmFormFieldEvaluationResultArray) {

		Map<String, List<DDMFormFieldEvaluationResult>>
			ddmFormFieldEvaluationResultsMap = new HashMap<>();

		for (DDMFormFieldEvaluationResult ddmFormFieldEvaluationResult :
				ddmFormFieldEvaluationResultArray) {

			List<DDMFormFieldEvaluationResult> ddmFormFieldEvaluationResults =
				ddmFormFieldEvaluationResultsMap.get(
					ddmFormFieldEvaluationResult.getName());

			if (ddmFormFieldEvaluationResults == null) {
				ddmFormFieldEvaluationResults = new ArrayList<>();

				ddmFormFieldEvaluationResultsMap.put(
					ddmFormFieldEvaluationResult.getName(),
					ddmFormFieldEvaluationResults);
			}

			ddmFormFieldEvaluationResults.add(ddmFormFieldEvaluationResult);
		}

		return ddmFormFieldEvaluationResultsMap;
	}

}