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
import com.liferay.dynamic.data.mapping.form.validation.util.DateParameterUtil;
import com.liferay.portal.kernel.util.Validator;

import java.text.SimpleDateFormat;

import java.time.LocalDate;

import java.util.Date;

/**
 * @author Bruno Oliveira
 * @author Carolina Barbosa
 */
public class FutureDatesFunction
	implements DDMExpressionFunction.Function2<Object, Object, Boolean> {

	public static final String NAME = "futureDates";

	@Override
	public Boolean apply(Object object1, Object object2) {
		if (Validator.isNull(object1) || Validator.isNull(object2)) {
			return false;
		}

		LocalDate localDate = DateParameterUtil.getLocalDate(
			object1.toString());

		String dateString = object2.toString();

		if (object2 instanceof Date) {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

			dateString = formatter.format(object2);
		}

		if (localDate.isBefore(DateParameterUtil.getLocalDate(dateString))) {
			return false;
		}

		return true;
	}

	@Override
	public String getName() {
		return NAME;
	}

}