/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.evaluator.internal.function;

import com.liferay.dynamic.data.mapping.expression.DDMExpressionFieldAccessor;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionFieldAccessorAware;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionFunction;
import com.liferay.dynamic.data.mapping.expression.GetFieldPropertyRequest;
import com.liferay.dynamic.data.mapping.expression.GetFieldPropertyResponse;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.GetterUtil;

/**
 * @author Carolina Barbosa
 */
public class GetLocalizedValueFunction
	implements DDMExpressionFieldAccessorAware,
			   DDMExpressionFunction.Function1<String, Object> {

	public static final String NAME = "getLocalizedValue";

	@Override
	public Object apply(String field) {
		if ((_ddmExpressionFieldAccessor == null) || !_isFieldVisible(field)) {
			return StringPool.BLANK;
		}

		Object localizedValue = _getFieldPropertyResponseValue(
			field, "localizedValue");

		if (localizedValue == null) {
			return StringPool.BLANK;
		}

		return localizedValue;
	}

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public void setDDMExpressionFieldAccessor(
		DDMExpressionFieldAccessor ddmExpressionFieldAccessor) {

		_ddmExpressionFieldAccessor = ddmExpressionFieldAccessor;
	}

	private Object _getFieldPropertyResponseValue(
		String field, String property) {

		GetFieldPropertyRequest.Builder builder =
			GetFieldPropertyRequest.Builder.newBuilder(field, property);

		GetFieldPropertyResponse getFieldPropertyResponse =
			_ddmExpressionFieldAccessor.getFieldProperty(builder.build());

		return getFieldPropertyResponse.getValue();
	}

	private boolean _isFieldVisible(String field) {
		return GetterUtil.getBoolean(
			_getFieldPropertyResponseValue(field, "visible"), true);
	}

	private DDMExpressionFieldAccessor _ddmExpressionFieldAccessor;

}