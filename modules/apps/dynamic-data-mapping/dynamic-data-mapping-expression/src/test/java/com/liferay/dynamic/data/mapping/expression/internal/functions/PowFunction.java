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
public class PowFunction
	implements DDMExpressionFunction.Function2
		<BigDecimal, BigDecimal, BigDecimal> {

	@Override
	public BigDecimal apply(BigDecimal n1, BigDecimal n2) {
		return n1.pow(n2.intValue());
	}

	@Override
	public String getName() {
		return "pow";
	}

}