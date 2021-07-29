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

package com.liferay.dynamic.data.mapping.form.evaluator.internal.function;

import com.liferay.dynamic.data.mapping.expression.DDMExpressionFunction;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionParameterAccessor;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionParameterAccessorAware;
import com.liferay.dynamic.data.mapping.form.evaluator.internal.function.util.DateFunctionsUtil;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Bruno Oliveira
 * @author Carolina Barbosa
 */
public class FutureDatesFunction
	implements DDMExpressionFunction.Function2<Object, Object, Boolean>,
			   DDMExpressionParameterAccessorAware {

	public static final String NAME = "futureDates";

	@Override
	public Boolean apply(Object object1, Object object2) {
		if ((_ddmExpressionParameterAccessor == null) ||
			Validator.isNull(object1) || Validator.isNull(object2)) {

			return false;
		}

		try {
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
				object2.toString());

			JSONObject startsFromJSONObject = jsonObject.getJSONObject(
				"startsFrom");

			if (startsFromJSONObject == null) {
				return false;
			}

			return DateFunctionsUtil.isFutureDate(
				object1.toString(), startsFromJSONObject,
				_ddmExpressionParameterAccessor.getLocale(),
				_ddmExpressionParameterAccessor.getTimeZoneId());
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception, exception);
			}
		}

		return false;
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

	private static final Log _log = LogFactoryUtil.getLog(
		FutureDatesFunction.class);

	private DDMExpressionParameterAccessor _ddmExpressionParameterAccessor;

}