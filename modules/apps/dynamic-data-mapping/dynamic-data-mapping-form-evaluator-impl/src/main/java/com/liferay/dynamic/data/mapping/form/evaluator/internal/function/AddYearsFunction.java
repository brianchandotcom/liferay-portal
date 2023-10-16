/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.evaluator.internal.function;

import com.liferay.dynamic.data.mapping.expression.DDMExpressionFunction;
import com.liferay.dynamic.data.mapping.form.validation.util.DateParameterUtil;
import com.liferay.portal.kernel.util.Validator;

import java.time.LocalDate;

/**
 * @author Murilo Stodolni
 */
public class AddYearsFunction
	implements DDMExpressionFunction.Function2<Object, Object, Object> {

	public static final String NAME = "addYears";

	@Override
	public Object apply(Object date, Object years) {
		if (Validator.isNull(date) || Validator.isNull(years)) {
			return null;
		}

		LocalDate localDate = DateParameterUtil.getLocalDate(date.toString());

		return localDate.plusYears(Long.valueOf(years.toString()));
	}

	@Override
	public String getName() {
		return NAME;
	}

}