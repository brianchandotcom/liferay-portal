/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.expression.internal.functions;

import com.liferay.dynamic.data.mapping.expression.DDMExpressionFunction;

import java.math.BigDecimal;

import java.util.Arrays;
import java.util.Collections;

/**
 * @author Rafael Praxedes
 */
public class MaxFunction
	implements DDMExpressionFunction.Function1<BigDecimal[], BigDecimal> {

	@Override
	public BigDecimal apply(BigDecimal[] numbers) {
		return Collections.max(Arrays.asList(numbers));
	}

	@Override
	public String getName() {
		return "max";
	}

}