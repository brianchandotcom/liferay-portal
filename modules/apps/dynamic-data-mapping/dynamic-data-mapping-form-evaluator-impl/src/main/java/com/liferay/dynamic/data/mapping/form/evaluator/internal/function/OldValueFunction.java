/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.evaluator.internal.function;

import com.liferay.dynamic.data.mapping.expression.DDMExpressionFunction;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionParameterAccessor;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionParameterAccessorAware;

import java.util.Map;

/**
 * @author Paulo Albuquerque
 */
public class OldValueFunction
	implements DDMExpressionFunction.Function1<Object, Object>,
			   DDMExpressionParameterAccessorAware {

	public static final String NAME = "oldValue";

	@Override
	public Object apply(Object fieldName) {
		if (_ddmExpressionParameterAccessor == null) {
			return null;
		}

		Map<String, Object> objectFieldsOldValues =
			_ddmExpressionParameterAccessor.getObjectFieldsOldValues();

		return objectFieldsOldValues.get(fieldName);
	}

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public void setDDMExpressionParameterAccessor(
		DDMExpressionParameterAccessor ddmExpressionParameterAccessor) {

		_ddmExpressionParameterAccessor = ddmExpressionParameterAccessor;
	}

	private DDMExpressionParameterAccessor _ddmExpressionParameterAccessor;

}