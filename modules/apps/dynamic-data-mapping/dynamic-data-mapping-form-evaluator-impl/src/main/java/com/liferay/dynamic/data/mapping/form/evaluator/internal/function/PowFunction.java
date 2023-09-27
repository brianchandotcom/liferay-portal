/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.evaluator.internal.function;

import com.liferay.dynamic.data.mapping.expression.DDMExpressionFunction;

import java.math.BigDecimal;

/**
 * @author Selton Guedes
 */
public class PowFunction
	implements DDMExpressionFunction.Function2<Object, Object, BigDecimal> {

	public static final String NAME = "pow";

	@Override
	public BigDecimal apply(Object number, Object exponent) {
		BigDecimal bigDecimal1 = new BigDecimal(number.toString());
		BigDecimal bigDecimal2 = new BigDecimal(exponent.toString());

		return bigDecimal1.pow(bigDecimal2.intValue());
	}

	@Override
	public String getName() {
		return NAME;
	}

}