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
public class SumFunction
	implements DDMExpressionFunction.Function1<Object[], BigDecimal> {

	public static final String NAME = "sum";

	@Override
	public BigDecimal apply(Object[] values) {
		if (values.length == 0) {
			return BigDecimal.ZERO;
		}

		BigDecimal result = new BigDecimal(0);

		for (Object value : values) {
			result = result.add(new BigDecimal(value.toString()));
		}

		return result;
	}

	@Override
	public String getName() {
		return NAME;
	}

}