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
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Marcos Martins
 */
public class GetOptionLabelFunction
	implements DDMExpressionFieldAccessorAware,
			   DDMExpressionFunction.Function2<String, Object, Object>,
			   DDMExpressionParameterAccessorAware {

	public static final String NAME = "getOptionLabel";

	@Override
	public Object apply(String fieldName, Object optionName) {
		if (_ddmExpressionFieldAccessor == null) {
			return StringPool.BLANK;
		}

		GetFieldPropertyRequest.Builder builder =
			GetFieldPropertyRequest.Builder.newBuilder(fieldName, "options");

		GetFieldPropertyResponse getFieldPropertyResponse =
			_ddmExpressionFieldAccessor.getFieldProperty(builder.build());

		DDMFormFieldOptions ddmFormFieldOptions =
			(DDMFormFieldOptions)getFieldPropertyResponse.getValue();

		if (!(optionName instanceof JSONArray)) {
			return _getOptionLabel(
				ddmFormFieldOptions, String.valueOf(optionName));
		}

		List<String> optionLabels = new ArrayList<>();

		JSONArray jsonArray = (JSONArray)optionName;

		for (int i = 0; i < jsonArray.length(); i++) {
			optionLabels.add(
				_getOptionLabel(ddmFormFieldOptions, jsonArray.getString(i)));
		}

		return StringUtil.merge(optionLabels, StringPool.COMMA_AND_SPACE);
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

	private String _getOptionLabel(
		DDMFormFieldOptions ddmFormFieldOptions, String optionName) {

		LocalizedValue localizedValue = ddmFormFieldOptions.getOptionLabels(
			optionName);

		if (localizedValue == null) {
			return optionName;
		}

		if (_ddmExpressionParameterAccessor.getLocale() != null) {
			return localizedValue.getString(
				_ddmExpressionParameterAccessor.getLocale());
		}

		return localizedValue.getString(localizedValue.getDefaultLocale());
	}

	private DDMExpressionFieldAccessor _ddmExpressionFieldAccessor;
	private DDMExpressionParameterAccessor _ddmExpressionParameterAccessor;

}