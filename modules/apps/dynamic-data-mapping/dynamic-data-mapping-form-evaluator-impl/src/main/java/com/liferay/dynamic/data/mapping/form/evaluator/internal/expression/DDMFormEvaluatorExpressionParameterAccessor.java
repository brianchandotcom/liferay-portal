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

package com.liferay.dynamic.data.mapping.form.evaluator.internal.expression;

import com.liferay.dynamic.data.mapping.expression.DDMExpressionParameterAccessor;
import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormEvaluatorEvaluateRequest;

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
	public long getGroupId() {
		return _ddmFormEvaluatorEvaluateRequest.getGroupId();
	}

	@Override
	public Locale getLocale() {
		return _ddmFormEvaluatorEvaluateRequest.getLocale();
	}

	@Override
	public long getUserId() {
		return _ddmFormEvaluatorEvaluateRequest.getUserId();
	}

	private final DDMFormEvaluatorEvaluateRequest
		_ddmFormEvaluatorEvaluateRequest;

}