/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.evaluator;

import com.liferay.portal.kernel.json.JSON;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

/**
 * @author Leonardo Barros
 */
public final class DDMFormEvaluatorEvaluateResponse {

	@JSON(name = "fields")
	public Map<DDMFormEvaluatorFieldContextKey, Map<String, Object>>
		getDDMFormFieldsPropertyChanges() {

		return _ddmFormFieldsPropertyChanges;
	}

	public Set<Integer> getDisabledPagesIndexes() {
		return _disabledPagesIndexes;
	}

	public static class Builder {

		public static Builder newBuilder(
			Map<DDMFormEvaluatorFieldContextKey, Map<String, Object>>
				ddmFormFieldsPropertyChanges) {

			return new Builder(ddmFormFieldsPropertyChanges);
		}

		public DDMFormEvaluatorEvaluateResponse build() {
			return _ddmFormEvaluatorEvaluateResponse;
		}

		public Builder withDisabledPagesIndexes(
			Set<Integer> disabledPagesIndexes) {

			_ddmFormEvaluatorEvaluateResponse._disabledPagesIndexes =
				Collections.unmodifiableSet(disabledPagesIndexes);

			return this;
		}

		private Builder(
			Map<DDMFormEvaluatorFieldContextKey, Map<String, Object>>
				ddmFormFieldsPropertyChanges) {

			_ddmFormEvaluatorEvaluateResponse._ddmFormFieldsPropertyChanges =
				Collections.unmodifiableMap(ddmFormFieldsPropertyChanges);
		}

		private final DDMFormEvaluatorEvaluateResponse
			_ddmFormEvaluatorEvaluateResponse =
				new DDMFormEvaluatorEvaluateResponse();

	}

	private DDMFormEvaluatorEvaluateResponse() {
	}

	private Map<DDMFormEvaluatorFieldContextKey, Map<String, Object>>
		_ddmFormFieldsPropertyChanges;
	private Set<Integer> _disabledPagesIndexes;

}