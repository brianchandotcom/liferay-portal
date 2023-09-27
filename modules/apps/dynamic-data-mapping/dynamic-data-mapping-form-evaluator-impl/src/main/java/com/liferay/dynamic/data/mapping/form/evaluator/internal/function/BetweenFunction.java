/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.evaluator.internal.function;

import com.liferay.dynamic.data.mapping.expression.DDMExpressionFunction;

import java.math.BigDecimal;

/**
 * @author Leonardo Barros
 */
public class BetweenFunction
	implements DDMExpressionFunction.Function3
		<BigDecimal, BigDecimal, BigDecimal, Boolean> {

	public static final String NAME = "between";

	@Override
	public Boolean apply(
		BigDecimal bigDecimal1, BigDecimal bigDecimal2,
		BigDecimal bigDecimal3) {

		if ((bigDecimal1.compareTo(bigDecimal2) >= 0) &&
			(bigDecimal1.compareTo(bigDecimal3) <= 0)) {

			return true;
		}

		return false;
	}

	@Override
	public String getName() {
		return NAME;
	}

}