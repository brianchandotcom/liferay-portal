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

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSON;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Marcellus Tavares
 */
public class DDMFormEvaluationResult {

	public DDMFormFieldEvaluationResult getDDMFormFieldEvaluationResult(
		String fieldName, String instanceId) {

		return _ddmFormFieldEvaluationResultsMap.get(
			StringBundler.concat(fieldName, "_INSTANCE_", instanceId));
	}

	@JSON(name = "fields")
	public List<DDMFormFieldEvaluationResult>
		getDDMFormFieldEvaluationResults() {

		return _ddmFormFieldEvaluationResults;
	}

	@JSON(include = false)
	public Map<String, DDMFormFieldEvaluationResult>
		getDDMFormFieldEvaluationResultsMap() {

		return _ddmFormFieldEvaluationResultsMap;
	}

	public Set<Integer> getDisabledPagesIndexes() {
		return _disabledPagesIndexes;
	}

	public void setDDMFormFieldEvaluationResults(
		List<DDMFormFieldEvaluationResult> ddmFormFieldEvaluationResults) {

		_ddmFormFieldEvaluationResults = ddmFormFieldEvaluationResults;
	}

	public void setDDMFormFieldEvaluationResultsMap(
		Map<String, DDMFormFieldEvaluationResult>
			ddmFormFieldEvaluationResultsMap) {

		_ddmFormFieldEvaluationResultsMap = ddmFormFieldEvaluationResultsMap;
	}

	public void setDisabledPagesIndexes(Set<Integer> disabledPagesIndexes) {
		_disabledPagesIndexes = disabledPagesIndexes;
	}

	private List<DDMFormFieldEvaluationResult> _ddmFormFieldEvaluationResults =
		new ArrayList<>();
	private Map<String, DDMFormFieldEvaluationResult>
		_ddmFormFieldEvaluationResultsMap = new HashMap<>();
	private Set<Integer> _disabledPagesIndexes = new HashSet<>();

}