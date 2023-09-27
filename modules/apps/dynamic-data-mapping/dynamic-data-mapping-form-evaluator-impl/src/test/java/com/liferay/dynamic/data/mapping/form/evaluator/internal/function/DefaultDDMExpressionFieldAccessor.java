/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.evaluator.internal.function;

import com.liferay.dynamic.data.mapping.expression.DDMExpressionFieldAccessor;
import com.liferay.dynamic.data.mapping.expression.GetFieldPropertyRequest;
import com.liferay.dynamic.data.mapping.expression.GetFieldPropertyResponse;

import java.util.function.Function;

/**
 * @author Rafael Praxedes
 */
public class DefaultDDMExpressionFieldAccessor
	implements DDMExpressionFieldAccessor {

	@Override
	public GetFieldPropertyResponse getFieldProperty(
		GetFieldPropertyRequest getFieldPropertyRequest) {

		return _getFieldPropertyResponseFunction.apply(getFieldPropertyRequest);
	}

	@Override
	public boolean isField(String parameter) {
		return true;
	}

	public void setGetFieldPropertyResponseFunction(
		Function<GetFieldPropertyRequest, GetFieldPropertyResponse>
			getFieldPropertyResponseFunction) {

		_getFieldPropertyResponseFunction = getFieldPropertyResponseFunction;
	}

	private Function<GetFieldPropertyRequest, GetFieldPropertyResponse>
		_getFieldPropertyResponseFunction;

}