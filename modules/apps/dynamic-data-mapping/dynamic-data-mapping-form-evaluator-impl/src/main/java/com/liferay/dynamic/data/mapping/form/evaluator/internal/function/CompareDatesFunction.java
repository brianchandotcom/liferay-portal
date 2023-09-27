/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.evaluator.internal.function;

import com.liferay.dynamic.data.mapping.expression.DDMExpressionFunction;
import com.liferay.dynamic.data.mapping.form.validation.util.DateParameterUtil;

import java.time.LocalDate;

import java.util.Objects;

/**
 * @author Selton Guedes
 */
public class CompareDatesFunction
	implements DDMExpressionFunction.Function2<Object, Object, Boolean> {

	public static final String NAME = "compareDates";

	@Override
	public Boolean apply(Object date1, Object date2) {
		LocalDate localDate1 = DateParameterUtil.getLocalDate(date1.toString());

		LocalDate localDate2 = DateParameterUtil.getLocalDate(date2.toString());

		if (Objects.isNull(localDate1) || Objects.isNull(localDate2)) {
			return false;
		}

		return localDate1.isEqual(localDate2);
	}

	@Override
	public String getName() {
		return NAME;
	}

}