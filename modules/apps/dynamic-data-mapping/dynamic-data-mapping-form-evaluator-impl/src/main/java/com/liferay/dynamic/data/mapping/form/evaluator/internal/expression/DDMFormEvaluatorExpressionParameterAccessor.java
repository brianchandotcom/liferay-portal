/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.evaluator.internal.expression;

import com.liferay.dynamic.data.mapping.expression.DDMExpressionParameterAccessor;
import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormEvaluatorEvaluateRequest;
import com.liferay.portal.kernel.json.JSONArray;

import java.util.Locale;

/**
 * @author Rafael Praxedes
 */
public class DDMFormEvaluatorExpressionParameterAccessor
	implements DDMExpressionParameterAccessor {

	public DDMFormEvaluatorExpressionParameterAccessor(
		DDMFormEvaluatorEvaluateRequest ddmFormEvaluatorEvaluateRequest) {

		_ddmFormEvaluatorEvaluateRequest = ddmFormEvaluatorEvaluateRequest;
	}

	@Override
	public long getCompanyId() {
		return _ddmFormEvaluatorEvaluateRequest.getCompanyId();
	}

	@Override
	public String getGooglePlacesAPIKey() {
		return _ddmFormEvaluatorEvaluateRequest.getGooglePlacesAPIKey();
	}

	@Override
	public long getGroupId() {
		return _ddmFormEvaluatorEvaluateRequest.getGroupId();
	}

	@Override
	public Locale getLocale() {
		return _ddmFormEvaluatorEvaluateRequest.getLocale();
	}

	@Override
	public JSONArray getObjectFieldsJSONArray() {
		return _ddmFormEvaluatorEvaluateRequest.getObjectFieldsJSONArray();
	}

	@Override
	public String getTimeZoneId() {
		return _ddmFormEvaluatorEvaluateRequest.getTimeZoneId();
	}

	@Override
	public long getUserId() {
		return _ddmFormEvaluatorEvaluateRequest.getUserId();
	}

	private final DDMFormEvaluatorEvaluateRequest
		_ddmFormEvaluatorEvaluateRequest;

}