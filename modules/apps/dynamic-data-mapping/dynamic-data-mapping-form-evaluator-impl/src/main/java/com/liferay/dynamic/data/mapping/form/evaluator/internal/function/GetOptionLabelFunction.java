/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.evaluator.internal.function;

import com.liferay.dynamic.data.mapping.expression.DDMExpressionFieldAccessor;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionFieldAccessorAware;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionFunction;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionParameterAccessor;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionParameterAccessorAware;
import com.liferay.dynamic.data.mapping.expression.GetFieldPropertyRequest;
import com.liferay.dynamic.data.mapping.expression.GetFieldPropertyResponse;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldOptions;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.petra.string.StringPool;

/**
 * @author Marcos Martins
 */
public class GetOptionLabelFunction
	implements DDMExpressionFieldAccessorAware,
			   DDMExpressionFunction.Function2<String, String, Object>,
			   DDMExpressionParameterAccessorAware {

	public static final String NAME = "getOptionLabel";

	@Override
	public Object apply(String fieldName, String optionName) {
		if (_ddmExpressionFieldAccessor == null) {
			return StringPool.BLANK;
		}

		GetFieldPropertyRequest.Builder builder =
			GetFieldPropertyRequest.Builder.newBuilder(fieldName, "options");

		GetFieldPropertyResponse getFieldPropertyResponse =
			_ddmExpressionFieldAccessor.getFieldProperty(builder.build());

		DDMFormFieldOptions ddmFormFieldOptions =
			(DDMFormFieldOptions)getFieldPropertyResponse.getValue();

		LocalizedValue localizedValue = ddmFormFieldOptions.getOptionLabels(
			optionName);

		if (_ddmExpressionParameterAccessor.getLocale() != null) {
			return localizedValue.getString(
				_ddmExpressionParameterAccessor.getLocale());
		}

		return localizedValue.getString(localizedValue.getDefaultLocale());
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

	@Override
	public void setDDMExpressionParameterAccessor(
		DDMExpressionParameterAccessor ddmExpressionParameterAccessor) {

		_ddmExpressionParameterAccessor = ddmExpressionParameterAccessor;
	}

	private DDMExpressionFieldAccessor _ddmExpressionFieldAccessor;
	private DDMExpressionParameterAccessor _ddmExpressionParameterAccessor;

}