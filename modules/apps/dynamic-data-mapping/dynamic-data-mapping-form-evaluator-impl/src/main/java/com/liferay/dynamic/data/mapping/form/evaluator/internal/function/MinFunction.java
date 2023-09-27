/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.evaluator.internal.function;

import com.liferay.dynamic.data.mapping.expression.DDMExpressionFunction;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Leonardo Barros
 */
public class MinFunction
	implements DDMExpressionFunction.Function1<Object[], BigDecimal> {

	public static final String NAME = "MIN";

	@Override
	public BigDecimal apply(Object[] values) {
		if (values.length == 0) {
			return BigDecimal.ZERO;
		}

		List<BigDecimal> list = new ArrayList<>();

		for (Object value : values) {
			list.add(new BigDecimal(value.toString()));
		}

		return Collections.min(list);
	}

	@Override
	public String getName() {
		return NAME;
	}

}