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

/**
 * @author Carolina Barbosa
 */
public class DateRangeFunction
	implements DDMExpressionFunction.Function2<Object, Object, Boolean>,
			   DDMExpressionParameterAccessorAware {

	public static final String NAME = "dateRange";

	@Override
	public Boolean apply(Object object1, Object object2) {
		_futureDatesFunction.setDDMExpressionParameterAccessor(
			_ddmExpressionParameterAccessor);
		_pastDatesFunction.setDDMExpressionParameterAccessor(
			_ddmExpressionParameterAccessor);

		if (_futureDatesFunction.apply(object1, object2) &&
			_pastDatesFunction.apply(object1, object2)) {

			return true;
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

	private DDMExpressionParameterAccessor _ddmExpressionParameterAccessor;
	private final FutureDatesFunction _futureDatesFunction =
		new FutureDatesFunction();
	private final PastDatesFunction _pastDatesFunction =
		new PastDatesFunction();

}