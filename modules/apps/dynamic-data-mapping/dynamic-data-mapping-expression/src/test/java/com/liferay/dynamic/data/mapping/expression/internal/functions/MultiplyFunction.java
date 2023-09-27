/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.expression.internal.functions;

import com.liferay.dynamic.data.mapping.expression.DDMExpressionFunction;

import java.math.BigDecimal;

/**
 * @author Rafael Praxedes
 */
public class MultiplyFunction
	implements DDMExpressionFunction.Function1<Object[], BigDecimal> {

	@Override
	public BigDecimal apply(Object[] numbers) {
		BigDecimal result = BigDecimal.ONE;

		for (Object object : numbers) {
			result = result.multiply(new BigDecimal(object.toString()));
		}

		return result;
	}

	@Override
	public String getName() {
		return "multiply";
	}

}