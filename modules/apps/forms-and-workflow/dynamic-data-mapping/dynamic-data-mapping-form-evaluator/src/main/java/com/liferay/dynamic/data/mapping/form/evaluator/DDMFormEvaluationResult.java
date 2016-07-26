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

package com.liferay.dynamic.data.mapping.form.evaluator;

import com.liferay.portal.kernel.json.JSON;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Marcellus Tavares
 */
public class DDMFormEvaluationResult {

	@JSON(name = "fields")
	public List<DDMFormFieldEvaluationResult>
		getDDMFormFieldEvaluationResults() {

		return _ddmFormFieldEvaluationResults;
	}

	public Map<String, DDMFormFieldEvaluationResult>
		getDDMFormFieldEvaluationResultsMap() {

		Map<String, DDMFormFieldEvaluationResult>
			ddmFormFieldEvaluationResultsMap = new HashMap<>();

		populateDDMFormFieldEvaluationResultsMap(
			_ddmFormFieldEvaluationResults, ddmFormFieldEvaluationResultsMap);

		return ddmFormFieldEvaluationResultsMap;
	}

	public void setDDMFormFieldEvaluationResults(
		List<DDMFormFieldEvaluationResult> ddmFormFieldEvaluationResults) {

		_ddmFormFieldEvaluationResults = ddmFormFieldEvaluationResults;
	}

	protected void populateDDMFormFieldEvaluationResultsMap(
		List<DDMFormFieldEvaluationResult> ddmFormFieldEvaluationResults,
		Map<String, DDMFormFieldEvaluationResult>
			ddmFormFieldEvaluationResultsMap) {

		for (DDMFormFieldEvaluationResult ddmFormFieldEvaluationResult :
				ddmFormFieldEvaluationResults) {

			String key = _getKey(
				ddmFormFieldEvaluationResult.getName(),
				ddmFormFieldEvaluationResult.getInstanceId());

			ddmFormFieldEvaluationResultsMap.put(
				key, ddmFormFieldEvaluationResult);

			populateDDMFormFieldEvaluationResultsMap(
				ddmFormFieldEvaluationResult.
					getNestedDDMFormFieldEvaluationResults(),
				ddmFormFieldEvaluationResultsMap);
		}
	}

	private String _getKey(String fieldName, String instanceId) {
		StringBuilder sb = new StringBuilder();

		sb.append(fieldName);
		sb.append(_INSTANCE_SEPARATOR);
		sb.append(instanceId);

		return sb.toString();
	}

	private static final String _INSTANCE_SEPARATOR = "_INSTANCE_";

	private List<DDMFormFieldEvaluationResult> _ddmFormFieldEvaluationResults =
		new ArrayList<>();

}