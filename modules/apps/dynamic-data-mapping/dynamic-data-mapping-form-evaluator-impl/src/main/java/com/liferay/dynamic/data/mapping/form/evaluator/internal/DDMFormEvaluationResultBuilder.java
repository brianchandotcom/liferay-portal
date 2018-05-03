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

import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormEvaluationResult;
import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormFieldEvaluationResult;
import com.liferay.petra.string.StringBundler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Rafael Praxedes
 */
public class DDMFormEvaluationResultBuilder {

	public static DDMFormEvaluationResult build(
		List<DDMFormFieldEvaluationResult> ddmFormFieldEvaluationResults,
		Set<Integer> disabledPagesIndexes) {

		DDMFormEvaluationResult ddmFormEvaluationResult =
			new DDMFormEvaluationResult();

		ddmFormEvaluationResult.setDDMFormFieldEvaluationResults(
			ddmFormFieldEvaluationResults);
		ddmFormEvaluationResult.setDDMFormFieldEvaluationResultsMap(
			createDDMFormFieldEvaluationResultsMap(
				ddmFormFieldEvaluationResults));

		ddmFormEvaluationResult.setDisabledPagesIndexes(disabledPagesIndexes);

		return ddmFormEvaluationResult;
	}

	protected static Map<String, DDMFormFieldEvaluationResult>
		createDDMFormFieldEvaluationResultsMap(
			List<DDMFormFieldEvaluationResult> ddmFormFieldEvaluationResults) {

		Map<String, DDMFormFieldEvaluationResult>
			ddmFormFieldEvaluationResultsMap = new HashMap<>();

		populateDDMFormFieldEvaluationResultsMap(
			ddmFormFieldEvaluationResults, ddmFormFieldEvaluationResultsMap);

		return ddmFormFieldEvaluationResultsMap;
	}

	protected static void populateDDMFormFieldEvaluationResultsMap(
		List<DDMFormFieldEvaluationResult> ddmFormFieldEvaluationResults,
		Map<String, DDMFormFieldEvaluationResult>
			ddmFormFieldEvaluationResultsMap) {

		for (DDMFormFieldEvaluationResult ddmFormFieldEvaluationResult :
				ddmFormFieldEvaluationResults) {

			String key = StringBundler.concat(
				ddmFormFieldEvaluationResult.getName(), "_INSTANCE_",
				ddmFormFieldEvaluationResult.getInstanceId());

			ddmFormFieldEvaluationResultsMap.put(
				key, ddmFormFieldEvaluationResult);

			populateDDMFormFieldEvaluationResultsMap(
				ddmFormFieldEvaluationResult.
					getNestedDDMFormFieldEvaluationResults(),
				ddmFormFieldEvaluationResultsMap);
		}
	}

}