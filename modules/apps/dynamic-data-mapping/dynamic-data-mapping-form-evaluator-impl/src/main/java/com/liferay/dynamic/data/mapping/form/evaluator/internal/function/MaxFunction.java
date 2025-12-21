/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.evaluator.internal.function;

import com.liferay.dynamic.data.mapping.expression.DDMExpressionFunction;
import com.liferay.petra.function.transform.TransformUtil;

import java.math.BigDecimal;

import java.util.Collections;

/**
 * @author Leonardo Barros
 */
public class MaxFunction
	implements DDMExpressionFunction.Function1<Object[], BigDecimal> {

	public static final String NAME = "MAX";

	@Override
	public BigDecimal apply(Object[] values) {
		if (values.length == 0) {
			return BigDecimal.ZERO;
		}

		return Collections.max(
			TransformUtil.transformToList(
				values, value -> new BigDecimal(value.toString())));
	}

	@Override
	public String getName() {
		return NAME;
	}

}